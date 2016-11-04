============================================
实验背景:本地虚拟机
实验目的:实现zookeeper集群

注意:zookeeper的集群至少要部署三个点，要不然选举不出来主节点

效果:
打算在本机和其他ip都配置集群
虚拟机1:master(192.168.22.181)
虚拟机2:node1(192.168.22.182)
虚拟机3:node3(192.168.22.183)
============================================

1.到apache官网下载相应版本的zookeeper
  http://apache.fayea.com/zookeeper/zookeeper-3.4.8/zookeeper-3.4.8.tar.gz

2.到具体的虚拟机上直接wget就好了
  我下载到了/usr/local/soft

3.解压缩到/usr/local目录
  tar -xzvf zookeeper-3.4.8.tar.gz -C /usr/local

4.将模版配置文件复制一份出来变成正式配置文件
  cd /usr/local/zookeeper-3.4.8/conf
  cp zoo_sample.cfg zoo.cfg
  因为第一份我打算直接用来做master，所以端口我都不改了
  因为我的dataDir不想用这个默认的目录，
  所以在zookeeper目录下创建data目录，并且vi zoo.cfg
  修改dataDir为如下
  dataDir=/usr/local/zookeeper-3.4.8/data

5.运行zookeeper
  到bin目录下执行
  ./zkServer.sh start

6.测试
  启动bin目录下的客户端程序
  ./zkCli.sh
  a)添加一个目录
    create /zhc hello
  b)列举某个路径下的目录
    ls /
  c)删除目录
    delete /zhc
  d)退出client客户端
    quit

---------------
集群配置
---------------
7.先配置master(192.168.2.181)
  也就是刚才的那个文件夹
  先停止刚启动起来的master
  cd /usr/local/zookeeper-3.4.8/conf
  vi zoo.cfg
  在文件最后添加以下几行
  2888是用来与集群中leader服务器交换信息的端口
  3888是用来执行选举的服务端口
  #cluster config
  server.1=192.168.22.181:2888:3888
  server.2=192.168.22.182:2888:3888
  server.3=192.168.22.183:2888:3888

8.修改master的serverid
  到达zookeeper的dataDir目录
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  创建myid文件
  vim myid
  输入master的id
  1
  保存退出
  
9.同理，其他节点也是一样，只不过myid不一样
  比如server.2的节点myid就为2

10.启动节点zookeeper

11.查看集群状态
   zookeeper_root/bin/zkServer.sh status
   可以看到当前节点的集群状态，可以看是不是leader

注:
1.zookeeper集群是用来做主从切换的，不是用来做高可用的
  可以分别在主从上面做写入的测试，发现写入从，主也能同步到数据