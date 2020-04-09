# Spring 和 SpringMVC 的比较

**Spring 在web容器中怎么启动起来的？**

spring的启动过程其实就是其IoC容器的启动过程，对于web程序，IoC容器启动过程即是建立上下文的过程。

> spring的启动过程：

+ 1、首先，对于一个web应用，其部署在web容器中，web容器提供其一个全局的上下文环境，这个上下文就是ServletContext，其为后面的spring IoC容器提供宿主环境；

+ 2、其次，在web.xml中会提供有contextLoaderListener。在web容器启动时，会触发容器初始化事件，此时contextLoaderListener会监听到这个事件，其contextInitialized方法会被调用，在这个方法中，spring会初始化一个启动上下文，这个上下文被称为根上下文，即WebApplicationContext，这是一个接口类，确切的说，其实际的实现类是XmlWebApplicationContext。这个就是spring的IoC容器，其对应的Bean定义的配置由web.xml中的context-param标签指定。在这个IoC容器初始化完毕后，spring以WebApplicationContext.ROOTWEBAPPLICATIONCONTEXTATTRIBUTE为属性Key，将其存储到ServletContext中，便于获取；

+ 3、再次，contextLoaderListener监听器初始化完毕后，开始初始化web.xml中配置的Servlet，这个servlet可以配置多个，以最常见的DispatcherServlet为例，这个servlet实际上是一个标准的前端控制器，用以转发、匹配、处理每个servlet请求。DispatcherServlet上下文在初始化的时候会建立自己的IoC上下文，用以持有spring mvc相关的bean。在建立DispatcherServlet自己的IoC上下文时，会利用WebApplicationContext.ROOTWEBAPPLICATIONCONTEXTATTRIBUTE先从ServletContext中获取之前的根上下文(即WebApplicationContext)作为自己上下文的parent上下文。有了这个parent上下文之后，再初始化自己持有的上下文。这个DispatcherServlet初始化自己上下文的工作在其initStrategies方法中可以看到，大概的工作就是初始化处理器映射、视图解析等。这个servlet自己持有的上下文默认实现类也是mlWebApplicationContext。初始化完毕后，spring以与servlet的名字相关(此处不是简单的以servlet名为Key，而是通过一些转换，具体可自行查看源码)的属性为属性Key，也将其存到ServletContext中，以便后续使用。这样每个servlet就持有自己的上下文，即拥有自己独立的bean空间，同时各个servlet共享相同的bean，即根上下文(第2步中初始化的上下文)定义的那些bean。
说完了spring上下文的初始化过程，这三个上下文的关系应该就了解了。如还是不太清楚，我就爱莫能助了，只能自行看代码去了。


## Spring 和 SpringMVC 的容器存在父子关系

&emsp;&emsp; 往往一个项目中，容器不一定只有一个。Spring中可以包括多个容器，而且容器有上下层关系（常见的是Spring[父] 和 SpringMVC[子]）。

`Spring`是父容器，`SpringMV`C是其子容器，并且在Spring父容器中注册的Bean对于SpringMVC容器中是可见的，而在SpringMVC容器中注册的Bean对于Spring父容器中是不可见的，也就是子容器可以看见父容器中的注册的Bean，反之就不行。

统一的如下注解配置来对Bean进行批量注册，而不需要再给每个Bean单独使用xml的方式进行配置。

`<context:component-scan base-package="com.lwhtarena.www" />`    

<font size=3 color=red>从Spring提供的参考手册中我们得知该配置的功能是扫描配置的base-package包下的所有使用了`@Component`注解的类，并且将它们自动注册到容器中，同时也扫描`@Controller`，`@Service`，`@Respository`这三个注解，因为他们是继承自`@Component`。</font>

在项目中我们经常见到还有如下这个配置，其实有了上面的配置，这个是可以省略掉的，因为上面的配置会默认打开以下配置。以下配置会默认声明`了@Required`、`@Autowired`、 `@PostConstruct`、`@PersistenceContext`、`@Resource`、`@PreDestroy`等注解。

`<context:annotation-config/>`   

<font color=red>以下是SpringMVC必须要配置的，因为它声明了@RequestMapping、@RequestBody、@ResponseBody等。并且，该配置默认加载很多的参数绑定方法，比如json转换解析器等。</font>

`<mvc:annotation-driven />`

### Spring 和SpringMVC 冲突的场景

我们共有Spring和SpringMVC两个容器，它们的配置文件分别为applicationContext.xml和applicationContext-MVC.xml。

+ 1.在applicationContext.xml中配置了<context:component-scan base-package=“com.lwhtarena.www" />，负责所有需要注册的Bean的扫描和注册工作。

+ 2.在applicationContext-MVC.xml中配置<mvc:annotation-driven />，负责SpringMVC相关注解的使用。

+ 3.启动项目我们发现SpringMVC无法进行跳转，将log的日志打印级别设置为DEBUG进行调试，发现SpringMVC容器中的请求好像没有映射到具体controller中。

+ 4.在applicationContext-MVC.xml中配置<context:component-scan base-package=“com.lwhtarena.www" />，重启后，验证成功，springMVC跳转有效。

【解决方案：】
实际工程中会包括很多配置，我们按照官方推荐根据不同的业务模块来划分不同容器中注册不同类型的Bean：Spring父容器负责所有其他非@Controller注解的Bean的注册，而SpringMVC只负责@Controller注解的Bean的注册，使得他们各负其责、明确边界。配置方式如下

1.在applicationContext.xml中配置:

	<!-- Spring容器中注册非@controller注解的Bean -->
	<context:component-scan base-package="com.lwhtarena.www">
	   <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>
　　
2.applicationContext-MVC.xml中配置

	<!-- SpringMVC容器中只注册带有@controller注解的Bean -->
	<context:component-scan base-package="com.lwhtarena.www" use-default-filters="false">
	   <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
	</context:component-scan>

关于use-default-filters="false"的作用，请参见另一篇博客[http://www.cnblogs.com/hafiz/p/5875770.html](http://www.cnblogs.com/hafiz/p/5875770.html)

## Spring容器、SpringMVC容器与ServletContext之间的关系

```xml
<web-app>

    ...

    <!-- 利用Spring提供的ContextLoaderListener监听器去监听ServletContext对象的创建，并初始化WebApplicationContext对象 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- Context Configuration locations for Spring XML files(默认查找/WEB-INF/applicationContext.xml) -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>

    <!-- 配置Spring MVC的前端控制器：DispatchcerServlet -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    ...

</web-app>
```

<font size=4 color=#D9534F>Spring的启动过程其实就是其Spring IOC容器的启动过程。</font><font size=4 color=#67B73C>特别地，对于web程序而言，IOC容器启动过程即是建立上下文的过程。</font>

**spring 启动过程**

(1) 对于一个web应用，其部署在web容器中，web容器提供其一个全局的上下文环境，<font color=#67B73C size=4>这个上下文就是ServletContext，其为后面的spring IoC容器提供宿主环境；</font>

(2) web.xml提供`contextLoaderListener`。在web容器启动时，会触发容器初始化事件，此时<font color=#67B73C size=4>contextLoaderListener</font>会监听到这个事件，其`contextInitialized方法`会被调用。在这个方法中，spring会初始化一个启动上下文，这个上下文被称为根上下文，即<font color=#67B73C size=4>WebApplicationContext</font>。WebApplicationContext是一个接口类，确切的说，其实际的实现类是<font color=#67B73C size=4>XmlWebApplicationContext</font>(即是Spring IOC容器)，其对应的Bean定义的配置由web.xml中的<context-param>标签指定。在这个IoC容器初始化完毕后，Spring以<font color=#67B73C size=4>WebApplicationContext.ROOTWEBAPPLICATIONCONTEXTATTRIBUTE</font>为属性Key，将其存储到ServletContext中，便于获取；

(3)ContextLoaderListener监听器初始化完毕后，开始初始化web.xml中配置的Servlet，这个servlet可以配置多个，以最常见的DispatcherServlet为例，这个servlet实际上是一个标准的前端控制器，用以`转发、匹配、处理`每个servlet请求。
```
DispatcherServlet上下文在初始化的时候会建立自己的IoC上下文，用以持有spring mvc相关
的bean。特别地，在建立DispatcherServlet自己的IoC上下文前，会利用
WebApplicationContext.ROOTWEBAPPLICATIONCONTEXTATTRIBUTE先从ServletContext中获
取之前的根上下文(即WebApplicationContext)作为自己上下文的parent上下文。有了这个
parent上下文之后，再初始化自己持有的上下文。
```

### Spring容器与SpringMVC的容器联系与区别

<font color=#67B73C  size=4>Spring Framework本身没有Web功能，Spring MVC使用WebApplicationContext类扩展ApplicationContext，使得拥有web功能。</font>

- ContextLoaderListener监听创建Spring容器(Spring)

ContextLoaderListener中创建Spring容器主要用于整个Web应用程序需要共享的一些组件，比如DAO、数据库的ConnectionFactory等；

- DispatcherServlet创建的SpringMVC的容器(SpringmMVC)

由DispatcherServlet创建的SpringMVC的容器主要用于和该Servlet相关的一些组件，比如Controller、ViewResovler等。

(1). 作用范围

　　子容器(SpringMVC容器)可以访问父容器(Spring容器)的Bean，父容器(Spring容器)不能访问子容器(SpringMVC容器)的Bean。也就是说，当在SpringMVC容器中getBean时，如果在自己的容器中找不到对应的bean，则会去父容器中去找，这也解释了为什么由SpringMVC容器创建的Controller可以获取到Spring容器创建的Service组件的原因。

(2). 具体实现

　　在Spring的具体实现上，子容器和父容器都是通过ServletContext的setAttribute方法放到ServletContext中的。但是，ContextLoaderListener会先于DispatcherServlet创建ApplicationContext，DispatcherServlet在创建ApplicationContext时会先找到由ContextLoaderListener所创建的ApplicationContext，再将后者的ApplicationContext作为参数传给DispatcherServlet的ApplicationContext的setParent()方法。也就是说，<font color=#67B73C  size=4>子容器的创建依赖于父容器的创建，父容器先于子容器创建</font>。在Spring源代码中，你可以在FrameServlet.java中找到如下代码：

`wac.setParent(parent);`

其中，wac即为由DisptcherServlet创建的ApplicationContext，而parent则为有ContextLoaderListener创建的ApplicationContext。此后，框架又会调用ServletContext的setAttribute()方法将wac加入到ServletContext中。

**Spring MVC启动过程大致分为两个过程：**

- ContextLoaderListener初始化，实例化IoC容器，并将此容器实例注册到ServletContext中；

- DispatcherServlet初始化；




| 比较 | Spring容器 | SpringMVC |共同点|
|:------|:------|------:|----:|
| 关系 | 父| 子 ||
| 作用范围 | <font color=red size=3>父容器(Spring容器)不能访问子容器(SpringMVC容器)的Bean</font> | <font color=red size=3>子容器(SpringMVC容器)可以访问父容器(Spring容器)的Bean</font>||
|具体实现|ContextLoaderListener会先于DispatcherServlet创建ApplicationContext|DispatcherServlet在创建ApplicationContext时会先找到由ContextLoaderListener所创建的ApplicationContext，再将后者的ApplicationContext作为参数传给DispatcherServlet的ApplicationContext的setParent()方法|在Spring的具体实现上，子容器和父容器都是通过ServletContext的setAttribute方法放到ServletContext中的。<font color=#67B73C  size=3>子容器的创建依赖于父容器的创建，父容器先于子容器创建。</font>|

### Spring容器和SpringMVC容器的配置

在Spring整体框架的核心概念中，容器是核心思想，就是用来管理Bean的整个生命周期的，而在一个项目中，容器不一定只有一个，Spring中可以包括多个容器，而且容器间有上下层关系，目前最常见的一种场景就是在一个项目中引入Spring和SpringMVC这两个框架，其实就是两个容器：Spring是根容器，SpringMVC是其子容器。在上文中，我们提到，SpringMVC容器可以访问Spring容器中的Bean，Spring容器不能访问SpringMVC容器的Bean。但是，若开发者对Spring容器和SpringMVC容器之间的关系了解不够深入，常常会因配置失当而导致同时配置Spring和SpringMVC时出现一些奇怪的异常，比如Controller的方法无法拦截、Bean被多次加载等问题。

在实际工程中，一个项目中会包括很多配置，根据不同的业务模块来划分，我们一般思路是各负其责，明确边界，即：Spring根容器负责所有其他非controller的Bean的注册，而SpringMVC只负责controller相关的Bean的注册，下面我们演示这种配置方案。

**(1). Spring容器配置**

Spring根容器负责所有其他非controller的Bean的注册：

```xml
<!-- 启用注解扫描，并定义组件查找规则 ，除了@controller，扫描所有的Bean -->
    <context:component-scan base-package="cn.edu.tju.rico">
        <context:exclude-filter type="annotation"
            expression="org.springframework.stereotype.Controller" />
    </context:component-scan>
```

**(2). SpringMVC容器配置**

SpringMVC只负责controller相关的Bean的注册，其中@ControllerAdvice用于对控制器进行增强，常用于实现全局的异常处理类：

```xml
   <!-- 启用注解扫描，并定义组件查找规则 ，mvc层只负责扫描@Controller、@ControllerAdvice -->
    <!-- base-package 如果多个，用“,”分隔 -->
    <context:component-scan base-package="cn.edu.tju.rico"
        use-default-filters="false">
        <!-- 扫描@Controller -->
        <context:include-filter type="annotation"
            expression="org.springframework.stereotype.Controller" />
        <!--控制器增强，使一个Contoller成为全局的异常处理类，类中用@ExceptionHandler方法注解的方法可以处理所有Controller发生的异常 -->
        <context:include-filter type="annotation"
            expression="org.springframework.web.bind.annotation.ControllerAdvice" />
    </context:component-scan>
```

　　在`<context:component-scan>`中可以添加use-default-filters，Spring配置中的use-default-filters用来指示是否自动扫描带有@Component、@Repository、@Service和@Controller的类。默认为true，即默认扫描。如果想要过滤其中这四个注解中的一个，比如@Repository，可以添加<context:exclude-filter />子标签，如下：

```xml
 <context:component-scan base-package="cn.edu.tju.rico" scoped-proxy="targetClass" use-default-filters="true">  
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>  
</context:component-scan>  
```

而`<context:include-filter/>`子标签是用来添加扫描注解的：

```xml
<context:component-scan base-package="cn.edu.tju.rico" scoped-proxy="targetClass" use-default-filters="false">  
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Repository"/>  
</context:component-scan>  
```

### Spring容器和SpringMVC容器的配置失当带来的问题

**场景：**

在一个项目中，想使用Spring AOP在Controller中切入一些逻辑，但发现不能切入到Controller的中，但可以切入到Service中。最初的配置情形如下：

1). Spring的配置文件application.xml包含了开启AOP自动代理、Service扫描配置以及Aspect的自动扫描配置，如下所示：

```xml
<aop:aspectj-autoproxy/>
<context:component-scan base-package="cn.edu.tju.rico">
```

2). Spring MVC的配置文件spring-mvc.xml主要内容是Controller层的自动扫描配置。

```xml
<context:component-scan base-package="cn.edu.tju.rico.controller" />
```
3). 增强代码为如下:

```java
@Component
@Aspect
public class SecurityAspect {

    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    private TokenManager tokenManager;

    @Resource(name = "tokenManager")
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // 若目标方法忽略了安全性检查，则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            System.out
                    .println("method.isAnnotationPresent(IgnoreSecurity.class) : "
                            + method.isAnnotationPresent(IgnoreSecurity.class));
            return pjp.proceed();
        }
        // 从 request header 中获取当前 token
        String token = WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
        // 检查 token 有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("token [%s] is invalid", token);
            throw new TokenException(message);
        }
        // 调用目标方法
        return pjp.proceed();
    }
}
```

4). 需要被代理的Controller如下:

```java
@RestController
@RequestMapping("/tokens")
public class TokenController {

    private UserService userService;
    private TokenManager tokenManager;

    public UserService getUserService() {
        return userService;
    }

    @Resource(name = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    @Resource(name = "tokenManager")
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @RequestMapping(method = RequestMethod.POST)
    @IgnoreSecurity
    public Response login(@RequestParam("uname") String uname,
            @RequestParam("passwd") String passwd) {
        boolean flag = userService.login(uname, passwd);
        if (flag) {
            String token = tokenManager.createToken(uname);
            System.out.println("**** Token **** : " + token);
            return new Response().success("Login Success...");
        }
        return new Response().failure("Login Failure...");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @IgnoreSecurity
    public Response logout(@RequestParam("uname") String uname) {
        tokenManager.deleteToken(uname);
        return new Response().success("Logout Success...");
    }
}
```

　　在运行过程中，发现这样配置并没有起作用，AOP配置不生效，没有生成TokenController的代理。

**解决方案**

Spring容器先于SpringMVC容器进行创建，并且SpringMVC容器的创建依赖于Spring容器。在SpringMVC容器创建TokenController时，由于其没有启用AOP代理，并且父容器的配置与子容器配置的独立性，导致SpringMVC容器没有为TokenController生成代理，所以没有生效。我们只需要在SpringMVC的配置文件中添加Aspect的自动扫描配置即可：

```xml
<aop:aspectj-autoproxy/>
<context:component-scan base-package="com.hodc.sdk.controller" />
```