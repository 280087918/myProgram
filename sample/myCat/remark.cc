
网上相关资料:http://www.blogjava.net/amigoxie/archive/2014/12/24/421788.html
前言:文档本身主要是想对MyCat的搭建步骤作一个详细的记录。不过先放到最后面
------------------------------------------------------------------------------------------------------------------------------------

这个数据库中间件的目的：单表的数据量过于庞大，在表上面做一些数据库的操作会影响响应速度，从而降低用户体验。
不足的地方可能是表被水平拆分了之后，以后系统就离不开这个中间件了，因为一系列操作都是要通过这个代理去跟数据库进行通讯。
另外一个，myCat有主表和子表的概念，如果我们的表设计结构上不存在关联，但是逻辑上存在关联的话(就像优购这种样子的，不设置主外键)
	那么这个中间件有可能就玩不转，优购DBA建议不给表设置主外键的约束关系。
目前尚无定论，以上说的都是我自己的想法，具体情况可能还需要做一些demo测试才会有更深入的理解??
------------------------------------------------------------------------------------------------------------------------------------

首先要了解以下几个核心概念
dataNode:myCat的逻辑数据节点，是table的分片节点。
	目前的理解是：比如一个用户表(tbl_member)，那么经过MyCat的水平划分之后就有可能变成(tbl_member_1和tbl_member_2)
	那我理解的分片就是这个_1和_2??
	这个dataNode目前理解尚不特别透彻，这个分片是分片到表还是数据库的，目前尚未清楚??

schema:对应实际Mysql里面的Database

table:对应数据库里面存储的表，这里要对应具体的dataNode，而且有一个需要注意的地方，就是子表和父表必须在同一个分片上。
      这里还是不是十分理解这个dataNode

分片规则:通过一个字段返回分片的序号。

dataHost:定义某个数据库的访问地址，用来捆绑到dataNode上
------------------------------------------------------------------------------------------------------------------------------------

myCat需要进行配置的几个文件,在根目录conf里面
schema.xml:定义逻辑库，表，分片节点等内容
rule.xml:定义分片规则
server.xml:定义用户及系统相关变量，端口??
------------------------------------------------------------------------------------------------------------------------------------

myCat的切分模式
垂直切分:按具体某一个模块来切分，若干表在分布在同一个切片上面
	这种模式目前没看到有什么好处，单个数据库的数据容量是少了，但是单个数据表的数据还是可能很大。
	垂直切分要是分布到不同的数据库服务器上面还有一定的好处，有可能对数据库的io平摊到各个服务器上面，减少io的消耗。
	这种模式有不少的坏处，比如说事务处理复杂，表关联可能无法在数据库级别完成，需要程序中进行处理。
	切分到一定程度后，也就是将模块化细分到某一个粒度后，可能就无法扩展了。
	所以目前还没看到垂直切分的适用场景，可能有特殊的适用场景会用到垂直切分
	另外，垂直切分不需要设置rule.xml

水平切分:将某个数据量较大的数据表根据表里面某个字段的某个规则分散到多个表当中
	这种好处就是可以将具体某一张数据量较大的表拆成好几张表。
------------------------------------------------------------------------------------------------------------------------------------	


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
配置步骤(这里是windows版本的操作步骤，linux，ios的应该差异不大)
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

1.在conf/wrapper.conf里面设置JVM参数
	具体设置看机器本身条件

2.mycat启用
	别进入一个思维定势，看见bin目录就去点里面的bat，这里不是这么玩的
	cmd到bin目录
	安装mycat:mycat install  不知道为什么要install，但是步骤就是这样的
	启动mycat:mycat start
	停止mycat:mycat stop

3.具体配置分了两种
	其中source里面是备份原来的设置文件的
	另外ventical里面的是垂直切分配置
	最后一个 horizontal是水平切分