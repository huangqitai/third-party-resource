# Redis

Redis（REmote DIctionary Server）是一个key-value存储系统，是当下互联网公司最常用的NoSQL数据库之一，是进入互联网行业的Java开发工程师必备技术。

redis是什么？能干什么？如何用？

## nosql


3v+3高

- 大数据时代的3v
 - 海量Volume
 - 多样Variety
 - 实时Velocity

- 互联网需求的3高
 - 高并发
 - 高可扩
 - 高性能

== 传统的关系型数据库你如何设计？ ER图(1:1/1:N/N:N,主外键等常见)

== Nosql，应如何设计？

- 什么是BSON
	 BSON（ ）是一种类json的一种二进制形式的存储格式，简称Binary JSON，它和JSON一样，支持内嵌的文档对象和数组对象

- 用BSon画出构建的数据模型

		{
		 "customer":{
		   "id":1136,
		   "name":"Z3",
		   "billingAddress":[{"city":"beijing"}],
		   "orders":[
		    {
		      "id":17,
		      "customerId":1136,
		      "orderItems":[{"productId":27,"price":77.5,"productName":"thinking in java"}],
		      "shippingAddress":[{"city":"beijing"}]
		      "orderPayment":[{"ccinfo":"111-222-333","txnid":"asdfadcd334","billingAddress":{"city":"beijing"}}],
		      }
		    ]
		  }
		}
