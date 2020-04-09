<center><h2>代码生成器使用文档</h2></center>
## 说明

- 文档内容：代码生成器使用说明
- 项目地址：https://gitee.com/laureler/MyCodeRobotX

## 使用步骤

+ 下载MyCodeRobotX项目，使用开发工具导入，配置运行环境。IDE：Intellij IDEA，JDK 8 (支持JDK 10以下)。

+ 程序执行入口为：MyCodeRobotX\src\com\ivyb2b\marshall\view\Main.java中的main()方法。配置运行环境之后启动main()方法即可。

+ 程序启动之后可视化界面如下图：

  <img src=".\image\代码生成器界面.jpg"/>

+ 代码生成器生成的代码是基于指定数据表生成对应的SSM框架所需符合MVC模式的各层次代码以及配置文件，所以配置信息中就是配置指定的数据表所在的数据库信息，包括类型、地址、用户、密码等。其次是所生成的代码包结构以及模块名称等。（注：web目录配置暂时未生成）

+ 配置信息默认值为：

  ``` java
  // 数据库用户名
  	public static final String DATABASE_USERNAME = "BDC_WWYSQ";
  	// 数据库密码
  	public static final String DATABASE_PASSWORD = "sa123";
  	// 数据库连接地址
  	public static final String DATABASE_URL = "jdbc:oracle:thin:@//192.168.10.32:1521/orcl";
  	// 模块路径 例如 com.baidu
  	public static final String PACKAGE_PATH = "com.southgis.ibase";
  	// 模块名称，例如 mainWebService
  	public static final String MODULE_NAME = "parent";
  	// 源代码根路径文件夹名称，默认为src
  	public static final String RESOURCE_TARGET = "src";
  	// web服务的文件夹 默认为webapp
  	public static final String WEBAPP_PATH = "webapp";
  	// jdbc 数据库驱动
  	public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
  ```

  

+ 数据库信息配置好并测试连接通过，即可选择需要生成代码的数据表，配置符合SSM的模板，一键生成即可在生成路径下找到生成的项目。

