# Elasticsearch

**概念对比**

关系型数据库（如MySQL） | Elasticsearch
----------------------| -------------
数据库 Database | 索引 Index，支持全文检索
表 Table | 类型 Type
数据行 Row | 文档 Document，但不需要固定结构，不同文档可以具有不同字段集合
数据列 Column | 字段 Field
模式 Schema | 映像 Mapping

## 基础概念

+ index
 
  类似于mysql数据库中的database

+ Type

  类似于mysql数据库中的table表，es中可以在index中建立type(table),通过mapping进行映射

+ Document

  由于es存储的数据是文档的，一条数据对应一篇文档即相当于mysql数据库中的一行数据row，一个文档中可以有多个字段也就是mysql数据库一行可以有多列。 Field es中一个文档中对应的多个列于mysql数据库中每一列对应

+ Mapping

  可以理解为mysql或者solr对应的schema，只不过有些时候es中的mappping增加了动态识别功能，感觉很强大的样子，其实实际生产环境不建议使用，最好还是开始制定好了对应的schema为主。

+ indexed

  就是名义上的建立索引。mysql中一般会对经常使用的列增加相应的索引用于提高查询速度，而在es中默认都是会加上索引的，除非你特殊制定不建立索引只是进行存储用于展示，这个需要看你具体的需求和业务进行设定了。

+ Query DSL
 
  类似于mysql的sql语句，只不过在es中是使用的json格式的查询语句，专业术语就叫：QueryDSL

+ GET/PUT/POST/DELETE

  分别类似于mysql的select/update/delete

## 安装部署

解压elasticsearch的压缩包在 `/opt` 目录下

```shell
## 创建用户组
groupadd esgroup

## 创建用户
useradd esuser -g esgroup -p espassword

## 更改elasticsearch文件及内部文件的所属用户及组
cd /opt 

chown -R esuser.esgroup elasticsearch-7.0.0

## 切换用户并运行
su esuser 

./bin/elasticsearch
./bin/elasticsearch -d // 或后台运行

## 实现远程访问：需要对config/elasticsearch.yml进行配置：network.host:192.168.56.101

node.name: node-1 //这样设置，kibana才能发现
cluster.initial_master_nodes: ["node-1"] // 这里一定要这样设置，没有这样设置出问题

## 配置elasticseach允许跨域访问
//在最后加上这两句，要不然，外面浏览器就访问不了
http.cors.enabled: true
http.cors.allow-origin: "*"

## 处理错误
vim /etc/security/limits.conf 增加如下：
esuser soft nofile 65536
esuser hard nofile 65536
esuser soft nproc 4096
esuser hard nproc 4096

vim /etc/security/limits.d/20-nproc.conf 修改为：
esuser soft nproc 4096

vim /etc/sysctl.conf 增加如下：
vm.max_map_count=655360
执行以下命令生效：sysctl -p

关闭防火墙：systemctl stop firewalld.service

## centos6.x 安装出现错误
ERROR: bootstrap checks failed
system call filters failed to install; check the logs and fix your configuration or disable system call filters at your own risk

原因：
这是在因为Centos6不支持SecComp，而ES5.2.0默认bootstrap.system_call_filter为true进行检测，所以导致检测失败，失败后直接导致ES不能启动。
解决：
在elasticsearch.yml中配置bootstrap.system_call_filter为false，注意要在Memory下面:
bootstrap.memory_lock: false
bootstrap.system_call_filter: false


## centos7 开放端口
firewall-cmd --state  //显示状态
firewall-cmd --zone=public --list-ports //查看所有打开的端口
firewall-cmd --zone=public --add-port=80/tcp --permanent    （--permanent永久生效，没有此参数重启后失效） //增加端口
firewall-cmd --reload //重新载入
firewall-cmd --zone= public --remove-port=80/tcp --permanent //删除

## 查看是否启动成功
netstat -ant
```

## 安装Head插件

Head是elasticsearch的集群管理工具，可以用于数据的浏览和查询
```shell
## 启动head插件服务（后台运行）
/elasticsearch-head/node_modules/grunt/bin/grunt server &
```

## 安装Kibana

Kibana 是一个针对Elastcseach的开源分析及可视化平台，使用Kibana可以查询、查看并存储在ES索引的数据进行交互操作，使用Kibana能执行高级的数据分析，并能以图表、表格和地图的形式查看数据

```shell
1、把下载好的压缩包拷贝到/soft目录下，解压缩，并把解压后的目录移动到`/usr/local/kibana`

2、编辑Kibana配置文件
vim /usr/local/kibana/config/kibana.yml 修改如下：

server.port: 5601
server.host: "192.168.56.101"
elasticsearch.hosts: ["http://192.168.56.101:9200"]

## Kibana后台启动
bin/kibana &

## 查看线程，kill掉
ps  -aux | grep  ela

```

## 安装中文分词器

```shell
创建 /opt/elasticsearch-7.0.0/plugins/ik
## 解压缩elasticsearch-analysis-ik-7.0.0.zip 到 /opt/elasticsearch-7.0.0/plugins/ik 下
unzip ./elasticsearch-analysis-ik-7.0.0.zip
```

## 使用ElasticSearch API 实现CRUD

1、添加索引 `PUT /lib/`
```
PUT /lib/
{
    "settings":{
        "index":{
            "number_of_shards":5,
            "number_of_replicas":1
        }
    }
}
```

2、查看索引 `GET /lib/_settings`

3、添加文档 `PUT /lib/usr/1`
```
PUT /lib/usr/1
{
    "first_name":"lwh",
    "last_name":"tarena",
    "age":25,
    "about":"我喜欢搜索引擎",
    "interests":["music"]
}

POST /lib/usr/
{
    "first_name":"Douglas",
    "last_name":"dog",
    "age":17,
    "about":"I like to build cabinets",
    "interests":["forestry"]
}

//查询
GET /lib/usr/1?_source=age,about
```

4、更新文档
```
## 直接覆盖
PUT /lib/usr/1
{
    "first_name":"Jane",
    "last_name":"Smith",
    "age":26,
    "about":"我喜欢搜索引擎",
    "interests":["music"]
}

## 直接修改
POST /lib/usr/1/_update  
{
    "doc":{
    "age":26,
    }
}
```
5、删除文档
```
## 删除一个文档
DELETE /lib/user/1 
## 删除索引
DELETE /lib
```
6、批量获取文档

使用ES提供的Multi Get API:

使用Multi Get API可以通过索引名、类型名、文档id一次得到一个文档集合，文档可以来自同一个索引库，也可以来自不同的索引库

```shell

curl 'http://192.168.56.101:9200/_mget' -d '
{
    "docs":[
        {
            "_index":"lib",
            "_type":"user",
            "_id":1
        },
        {
           "_index":"lib",
            "_type":"user",
            "_id":2
        }
    ]
}'

在使用Kibana 客户端工具中

GET /_mget 
{
    "docs":[
        {
            "_index":"lib",
            "_type":"user",
            "_id":1
        },
        {
           "_index":"lib",
            "_type":"user",
            "_id":2
        }
    ]
}

可以指定具体的字段
GET /_mget 
{
    "docs":[
        {
            "_index":"lib",
            "_type":"user",
            "_id":1,
            "_source":"interests"
        },
        {
           "_index":"lib",
            "_type":"user",
            "_id":2,
            "_source":["age","interests"]
        }
    ]
}

获取同索引同类型下的不同文档：
GET /lib/usr/_mget 
{
    "docs":[
        {
            "_id":1,
        },
        {
            "_type":"user",
            "_id":2,
        }
    ]
}
或
GET /lib/usr/_mget 
{
    "ids":["1","2","3"]
}
```

7、使用Bull API 实现批量操作

bulk 的格式：
```
{ action:{ metadata } }\n
{ requestbody}\n
```
action:行为
create:文档不存在时创建
update：更新文档
index：创建新文档或替换已有文档
delete：删除一个文档
metadata：_index,_type,_id

create和index的区别：
如果数据存在，使用create操作失败，会提示文档已经存在，使用index则可以成功执行。

```shell
## 批量添加文档
POST /lib2/books/_bulk
{"index":{"_id":1}}
{"title":"java","price":55}
{"index":{"_id":2}}
{"title":"html5","price":45}
{"index":{"_id":3}}
{"title":"php","price":35}
{"index":{"_id":4}}
{"title":"python","price":50}

## 批量获取
POST /lib2/books/_mget 
{
    "ids":["1","2","3"]
}

## 删除，没有请求体
POST /lib2/books/_bulk
{"delete":{"_index":"lib2","_type":"books","_id":4}}
{"create":{"_index":"tt","_type":"ttt","_id":100}}
{"name":"lisi"}
{"index":{"_index":"tt","_type":"ttt"}}
{"name":"zhaosi"}
{"update":{"_index":"lib2","_type":"books","_id":4}}
{"doc":{"price":58}}
```
## 什么是Mapping
```shell
## 查看es自动创建的mapping
GET /myindex/article/_mapping

备注：es自动创建了index、type，以及type对应的mapping

## 什么是映射：mapping定义了type中的每个字段的数据类型以及这些字段如何分词等相关属性
```

**核心数据类型**
+ 字符类型：string 
  + text
  + keyword
+ 数据类型：long、integer、short、byte、double、float
+ 日期类型:date
+ 布尔类型：boolean
+ 二进制：binary

## 基本查询（Query查询）




