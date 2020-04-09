# Mybatis

> 疑惑

&emsp;&emsp;到底该用`resultMap`还是用`resultType`？如果我想返回`String、Map、List`等等，该如何写？


## 注解 VS XML

> XML使用场景

- 条件不定的查询（eg.下边代码中的getAdminByConditions方法）
- 增加对象返回自增主键
- 在一个Mapper接口中，出现多个select查询（>=3个），且每个查询都需要写相同的返回@Results内容（这一部分内容通常很多），这样的话，为了使Mapper接口比较整洁，重复代码比较少，我们会将这些select方法的具体实现写在xml文件中，因为在xml文件的顶部我们就会配置与注解@Results异曲同工的东西。（当然，这一点如果嫌配置xml麻烦，这一点可忽略）

**注意：前两条是硬性的，是注解所解决不了的，而第三条只是建议。**

**映射文件的作用就相当于是定义Dao接口的实现类如何工作。**(这就是我们使用Mybatis时编写的最多的关键文件)

## Mybatis传多个参数（三种解决方案）

1) 方案一 (占位)

```java
//DAO层的函数方法
PublicUserselectUser(Stringname,String area);
```

```xml
<!--对应的Mapper.xml-->
<selectid="selectUser" resultMap="BaseResultMap">
    select * fromuser_user_t  where user_name = #{0} and user_area=#{1}
</select>
<!--其中，#{0}代表接收的是dao层中的第一个参数，#{1}代表dao层中第二参数，更多参数一致往后加即可。 -->
```

2) 方案二 ( 此方法采用Map传多参数 )
```java
    //Dao层的函数方法
    PublicUserselectUser(Map paramMap);
```

```xml
<!--对应的Mapper.xml-->
<selectid=" selectUser" resultMap="BaseResultMap">
   select * from user_user_t  
   where user_name = #{userName，jdbcType=VARCHAR} 
        and user_area=#{userArea,jdbcType=VARCHAR}
</select>
```

```java
//Service层调用
Private User xxxSelectUser(){
    Map paramMap=new hashMap();
    paramMap.put(“userName”,”对应具体的参数值”);
    paramMap.put(“userArea”,”对应具体的参数值”);
    Useruser=xxx. selectUser(paramMap);
}
//个人认为此方法不够直观，见到接口方法不能直接的知道要传的参数是什么。
```

3) 方案三 ( <font color=#32B560 size=4>用`@Param`注解，这方法这样在调用dao层就知道需要哪些参数，很直观。</font>)
```java
Public User selectUser(
    @Param(“userName”) String name,
    @Param(“userArea”) String area );
```
```xml
<selectid=" selectUser" resultMap="BaseResultMap">
   select * fromuser_user_t  
   where user_name = #{userName，jdbcType=VARCHAR}
    and user_area=#{userArea,jdbcType=VARCHAR}
</select> 
<!--个人觉得这种方法比较好，能让开发者看到dao层方法就知道该传什么
样的参数，比较直观，个人推荐用此种方案。-->
```


## Mybatis 自动扫描，将Mapper接口生成代理注入到Spring

1) Mybatis在与Spring集成的时候可以配置MapperFactoryBean来生成Mapper接口的代理.(<font color=red size=4>缺点，就是系统有很多的配置文件时,全部需要手动编写</font>)
```xml
<bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
  <property name="mapperInterface"
     value="org.mybatis.spring.sample.mapper.UserMapper" />
  <property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
<!--MapperFactoryBean 创建的代理类实现了 UserMapper 接口,并且注入到应用程序中。
 因为代理创建在运行时环境中(Runtime,译者注) ,那么指定的映射器必须是一个接口,而
 不是一个具体的实现类。-->
```

2) 使用一个`MapperScannerConfigurer`, 它将会查找类路径下的映射器并自动将它们创建成`MapperFactoryBean`。(必要在Spring的XML配置文件中注册所有的映射器)

```xml
<!--创建MapperScannerConfigurer,可以在 Spring 的配置中添加如下代码-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
  <!--basePackage 属性是让你为映射器接口文件设置基本的包路径。 你可以使用分号`;`或逗号`,` 作为分隔符设置多于一个的包路径。每个映射器将会在指定的包路径中递归地被搜索到。-->
  <property name="basePackage" value="org.mybatis.spring.sample.mapper" />
</bean>
```

注意,没有必要去指定`SqlSessionFactory` 或 `SqlSessionTemplate` , 因 为 `MapperScannerConfigurer` 将会创建 `MapperFactoryBean`,之后自动装配。但是,如果你使用了一个以上的`DataSource`,那么自动装配可能会失效。这种情况下,你可以使用 `sqlSessionFactoryBeanName` 或 `sqlSessionTemplateBeanName` 属性来设置正确bean名称来使用。这就是它如何来配置的,注意 bean 的名称是必须的,而不是 bean 的引用,因 此,value 属性在这里替代通常的 ref:
```xml
<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
```

##  MyBatis mapperLocations，basePackage 多数据路径配置

+ 单数据路径配置

```xml
  <bean id="sqlSessionFactory2" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource2" />
        <property name="mapperLocations" value="classpath*:com/loongshawn/dao/impl/mapper2/pmp/*.xml" />
        <property name="typeAliasesPackage" value="com.autonavi.domain" />
    </bean>

    <!-- mybatis.spring自动映射 -->
    <bean id="mybatisMapperScanner2" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.loongshawn.pmp" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory2" />
    </bean>
```

+ 多数据源路径配置
```xml
   <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="mapperLocations">
            <array>
                <value>classpath:com/loongshawn/dao/impl/mapper/*.xml</value>
                <value>classpath:com/loongshawn/dao/impl/mapper3/pmc/*.xml</value>
            </array>
        </property>
        <property name="typeAliasesPackage" value="com.autonavi.domain" />
    </bean>

    <!-- mybatis.spring自动映射 -->
    <bean id="mybatisMapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.loongshawn.dao.impl.mapper,com.loongshawn.dao.impl.mapper3" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>
```

**mapperLocations**属性多路径配置如下：
```xml
<property name="mapperLocations">
    <array>
        <value>classpath:com/autonavi/dao/impl/mapper/*.xml</value>
        <value>classpath:com/autonavi/dao/impl/mapper3/pmc/*.xml</value>
    </array>
</property>
```

## mybatis如何从接口映射到xml？

明确的是，所有的ORM框架都基于JAVA原生的JDBC API做了封装。

1) 注册驱动

2) 建立connection

3) 创建操作语句statement

4) 执行statement

5) 封装结果resultset

![](./mybatis/002.jpg)

&nbsp;&nbsp;&nbsp;&nbsp; **在mybatis容器初始化的时候，会自动进行驱动注册，并把xml中配置的sql语句按照命名空间（就是接口名）加sql ID的方式作为key，sql语句作为value放入hashMap中存储起来，等到使用的时候从hashmap中取出，经过反射处理得到原生的sql语句，在使用jdbc executor进行执行！**

&nbsp;&nbsp;&nbsp;&nbsp;**执行过程中，如果有parameterType映射错误，或者SQL语句错误，则会抛出异常到应用层！**

&nbsp;&nbsp;&nbsp;&nbsp;**得到数据操作结果以后，使用resultmap中的映射关系把数据映射到JAVA实体类中，并创建相应的实例对象！**

![](./mybatis/003.jpg)
![](./mybatis/004.jpg)

> 总结的mybatis的常用功能:
```
1，使用xml文件配置使用映射
2，使用typeAliases修改类型别名
3，使用插件进行方法拦截
4，使用类型句柄（typehandlers)匹配java的参数或者返回值类型
5，使用环境（environments)配置多个不同的环境，以便使用不同的数据库
6，使用事务管理器（Transaction）管理事务
7，使用动态SQL
8，处理一对一关系使用联合（association）,处理一对多使用聚集（cellection）
9，使用识别器（discriminator）对产生的结果集进行筛选（类似switch语句）
10，使用cache开启缓存
11，使用缓存引用res-cache（让不同命名空间都能使用同一个缓存机制）
```

## MyBatis中#和$占位符有什么区别？

- `#`占位符将传入的数据都当成一个字符串，会对传入的数据自动加上单引号；
- `$`占位符直接将传入的数据显示在SQL中；

使用`$`占位符可能会导致SQL注射攻击，所以，能用`#`占位符的地方就不要使用`$`占位符，同时写order by子句的时候应该用`$`占位符而不是`#`占位符。

## 好标签`<trim>`
Mybatis好标签，可以去掉多年的 1=1
```
<trim prefix="" suffix="" suffixOverrides="" prefixOverrides=""></trim>
```
**prefix:** 在trim标签内sql语句加上前缀。

**suffix:** 在trim标签内sql语句加上后缀。

**suffixOverrides:** 指定去除多余的后缀内容，如：suffixOverrides=","，去除trim标签内sql语句多余的后缀","。

**prefixOverrides:** 指定去除多余的前缀内容

![](./Mybatis/005.jpeg)