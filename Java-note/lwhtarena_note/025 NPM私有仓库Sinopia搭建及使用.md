# NPM私有仓库Sinopia搭建及使用


使用sinopia五步快速完成本地npm搭建
原文  http://ued.fanxing.com/shi-yong-sinopiawu-bu-wan-cheng-ben-di-npmda-jian/
主题 服务器
为什么要搭建私有npm？

不知道大家在安装npm包的时候有没有出现过下载速度慢和下载异常的情况，反正是我经常遇到！ 这也是cnpm这类国内镜像出现的原因，但是毕竟还是在使用在使用别人的服务，如果有条件搭建自己的npm包管理服务器的话，还是有诸多好处的：

1、下载依赖速度够快

2、不会因为npm官方镜像挂掉而影响开发

3、私有模块管理

今天看到白大神部署了我们公司的本地npm私服，终于有幸认识到sinopia！一款可以快速搭建本地npm镜像的服务器： https://github.com/rlidwka/sinopia 。

**Quick Start**

> 第一步，安装 sinopia

`$ npm install -g sinopia`

> 第二步，启动 sinopia

`$ sinopia`

sinopia启动之后可以看到配置文件在 C:\Users\Administrator\AppData\Roaming\sinopia\config.xml，

本地默认服务器地址是 http://localhost:4873/，访问一下： 

> 第三步，创建新用户

`$ npm adduser --registry http://localhost:4873`****

第四步，发布npm包

$ npm publish
接下来就是发布资源包到服务器了，我们创建一个繁星基类库的包文件：

{
  "name": "fx.fx",
  "version": "1.0.0",
  "main": "fx.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "gary gao",
  "license": "ISC",
  "description": "繁星基类库fx.js   \r 安装方法：npm install fx.fx",
  "readme": "繁星基类库fx.js   \r\n安装方法：npm install fx.fx \r\n",
  "readmeFilename": "README.md",
  "_id": "fx.fx@1.0.0",
  "_from": "fx.fx@"
}
用户登录成功之后就可以发布新包了： 
第五步，安装npm包

$ npm install fx.fx
我们现在看看是否能安装成功： 至此，已经完成私有npm的搭建，接下来就是维护的事情啦。

-----------------------------------------------------------------------

> 出现这样的错误：UNMET PEER DEPENDENCY

查了一下发现是跟旧版 npm 有关的问题:

方案一：

	rm -rf node_modules/    # 删除已安装的模块
	npm cache clean         # 清除 npm 内部缓存
	npm install             # 重新安装

方案二：

	rm -rf node_modules/    # 删除已安装的模块
	sudo npm update -g npm  # 更新 npm
	npm install             # 重新安装

方案三:

npm list 命令查看之后跑到缺失模块的目录下, 手动修复部分出错的模块：

可以 cd 到具体出问题的模块目录下手动 npm install 重新安装也可以解决.

### window安装 msiexec /package "E:\node-v7.9.0-x64.msi"

### 加快npm的下载速度

1、关闭npm的https `npm config set strict-ssl false`

2、设置npm的获取地址 `npm config set registry “http://registry.npmjs.org”`

3、设置npm获取的代理服务器地址：`npm config set proxy=http：//代理服务器ip:代理服务器端口`

清除npm的代理命令如下：
	npm config delete http-proxy
	npm config delete https-proxy

也可以单独为这次npm下载定义代理 npm install -g pomelo --proxy http://代理服务器ip:代理服务器端口

或者 使用阿里镜像`npm install -gd express --registry=http://registry.npm.taobao.org`

永久`npm config set registry http://registry.npm.taobao.org`

		Microsoft Windows [版本 10.0.14393]
		(c) 2016 Microsoft Corporation。保留所有权利。
		
		C:\Users\Administrator>node -v
		v7.9.0
		
		C:\Users\Administrator>npm -v
		4.2.0
		
		C:\Users\Administrator>npm config list -l
		; cli configs
		long = true
		scope = ""
		user-agent = "npm/4.2.0 node/v7.9.0 win32 x64"
		
		; userconfig C:\Users\Administrator\.npmrc
		registry = "https://registry.npmjs.org/rxjs"
		sass_binary_site = "https://npm.taobao.org/mirrors/node-sass/"
		strict-ssl = true
		
		; builtin config undefined
		prefix = "C:\\Users\\Administrator\\AppData\\Roaming\\npm"
		
		; default values
		access = null
		also = null
		always-auth = false
		bin-links = true
		browser = null
		ca = null
		cache = "C:\\Users\\Administrator\\AppData\\Roaming\\npm-cache"
		cache-lock-retries = 10
		cache-lock-stale = 60000
		cache-lock-wait = 10000
		cache-max = null
		cache-min = 10
		cafile = undefined
		cert = null
		color = true
		depth = null
		description = true
		dev = false
		dry-run = false
		editor = "notepad.exe"
		engine-strict = false
		fetch-retries = 2
		fetch-retry-factor = 10
		fetch-retry-maxtimeout = 60000
		fetch-retry-mintimeout = 10000
		force = false
		git = "git"
		git-tag-version = true
		global = false
		global-style = false
		globalconfig = "C:\\Users\\Administrator\\AppData\\Roaming\\npm\\etc\\npmrc"
		globalignorefile = "C:\\Users\\Administrator\\AppData\\Roaming\\npm\\etc\\npmignore"
		group = 0
		heading = "npm"
		https-proxy = null
		if-present = false
		ignore-scripts = false
		init-author-email = ""
		init-author-name = ""
		init-author-url = ""
		init-license = "ISC"
		init-module = "C:\\Users\\Administrator\\.npm-init.js"
		init-version = "1.0.0"
		json = false
		key = null
		legacy-bundling = false
		link = false
		local-address = undefined
		loglevel = "warn"
		logs-max = 10
		; long = false (overridden)
		maxsockets = 50
		message = "%s"
		metrics-registry = "https://registry.npmjs.org/"
		node-version = "7.9.0"
		onload-script = null
		only = null
		optional = true
		parseable = false
		; prefix = "C:\\Program Files\\nodejs" (overridden)
		production = false
		progress = true
		proprietary-attribs = true
		proxy = null
		rebuild-bundle = true
		; registry = "https://registry.npmjs.org/" (overridden)
		rollback = true
		save = false
		save-bundle = false
		save-dev = false
		save-exact = false
		save-optional = false
		save-prefix = "^"
		scope = ""
		scripts-prepend-node-path = "warn-only"
		searchexclude = null
		searchlimit = 20
		searchopts = ""
		searchstaleness = 900
		send-metrics = false
		shell = "C:\\windows\\system32\\cmd.exe"
		shrinkwrap = true
		sign-git-tag = false
		strict-ssl = true
		tag = "latest"
		tag-version-prefix = "v"
		tmp = "C:\\Users\\Administrator\\AppData\\Local\\Temp"
		umask = 0
		unicode = false
		unsafe-perm = true
		usage = false
		user = 0
		; user-agent = "npm/{npm-version} node/{node-version} {platform} {arch}" (overridden)
		userconfig = "C:\\Users\\Administrator\\.npmrc"
		version = false
		versions = false
		viewer = "browser"

