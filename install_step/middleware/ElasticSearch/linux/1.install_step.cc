参考资料:
官方网站:https://www.elastic.co
http://www.cnblogs.com/tianjixiaoying/p/4316011.html

实验背景:
服务器:192.168.22.181
本次使用的是elasticsearch-2.3.4.zip
    为什么不用最新版本2.3.5？因为ik分词器目前只支持到2.3.4

1.解压缩elasticsearch-2.3.4.zip
  unzip elasticsearch-2.3.4.zip -d /usr/local

2.创建jdk的软连接
  ln -s /usr/local/jdk1.7.0_79/bin/java /bin/java

3.es因为安全问题不允许root直接运行，创建elasticsearch账户
  groupadd -g 883 elasticsearch
  useradd -r -s /bin/bash -g elasticsearch elasticsearch -d /usr/local/elasticsearch-2.3.4/
  passwd elasticsearch
  
  用这个账户登录一下:su elasticsearch
  退出:exit

4.在elasticsearch根目录下创建两个文件夹
  mkdir data logs

5.root用户登录将elasticsearch账户给es安装目录授权
  chown -R elasticsearch /usr/local/elasticsearch-2.3.4/

6.修改config/elasticsearch.yml
  修改下面几行:
  path.data: /usr/local/elasticsearch-2.3.4/data
  path.logs: /usr/local/elasticsearch-2.3.4/logs
  network.host: 192.168.22.181
  http.port: 9200
  transport.tcp.port: 9300

7.用elasticsearch用户运行es
  su elasticsearch
  直接运行:bin/elasticsearch
  --这时候能看到程序传输的时候用的是9300的端口
    http访问的是9200的端口

8.浏览器直接输入:
  http://192.168.22.181:9200/
  要是能访问就说明安装成功

9.安装head插件
  elasticsearch用户执行:
  cd bin
  ./plugin install mobz/elasticsearch-head
  浏览器直接输入
  http://192.168.22.181:9200/_plugin/head/
  访问成功即head插件安装成功

10.关闭进程，以守护进程启动es
  bin/elasticsearch -d

------问题汇总------
JDK版本不够，提示升级，可以这么解决
编辑bin/elasticsearch脚本，添加
JAVA_OPTS='-XX:-UseSuperWord'

------集群搭建(很简单)------
修改config/elasticsearch.yml
cluster.name: my-cluster
node.name: node-1
node.rack: r1

其他机器，只要是同一个内网里面，并且集群名称一样的话，就会组成一个集群
(不知道为什么，集群没有组建成功，先放一放。后面再研究)
查看集群的状态
http://192.168.22.181:9200/_cluster/health?pretty

安装完header之后
  运行起来看集群的状态是green的

安装完ik分词器之后呢？
  安装好，重启es后还是green的

我写点数据看怎么样
  写数据之后就边yellow了

顺便试了一下，我在两个虚拟机中搭建es还是不能构成集群
怀疑是由于我现在是桥接网络的问题
貌似解决了。elasticsearch.yml上面要做如下配置
discovery.zen.ping.unicast.hosts: ["192.168.22.181:9300", "192.168.22.181:9301"]
