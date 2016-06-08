============================================
实验背景:本地虚拟机
实验目的:实现zookeeper集群

效果:
打算在本机和其他ip都配置集群
虚拟机1:master(192.168.1.78)
虚拟机2:node1(192.168.1.92)
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
  因为我的dataDir不想用这个默认的目录，所以vi zoo.cfg
  修改dataDir为如下
  dataDir=/usr/local/zookeeper-3.4.8/tmp/zookeeper

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
7.先配置master(192.168.1.78)
  也就是刚才的那个文件夹
  先停止刚启动起来的master
  cd /usr/local/zookeeper-3.4.8/conf
  vi zoo.cfg
  在文件最后添加以下几行
  #clouster config
  server.1=192.168.1.78:2888:3888
  server.2=192.168.1.92:2888:3888

8.修改master的serverid
  到达zookeeper的dataDir目录
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  创建myid文件
  vim myid
  输入master的id
  1
  保存退出
  
9.将zookeeper跟目录打包并发送到其他node服务器做为节点服务器
  cd /usr/local/
  tar -zcvf zookeeper-3.4.8.tar.gz zookeeper-3.4.8.tar.gz
  scp zookeeper-3.4.8.tar.gz root@192.168.1.92:/usr/local

10.启动master的zookeeper，这里也就是192.168.1.78的zookeeper
  日志在zookeeper的bin文件夹下
  tail -f zookeeper.out
  看到报错了。说招不到节点，这是正确的，因为子节点还没有启动起来

11.ssh到子节点的服务器(192.168.1.92)，并且到达刚传输过来的路径
  cd /usr/local
  解压缩刚传过来的包
  tar -xzvf zookeeper-3.4.8.tar.gz

12.修改myid
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  vim myid
  输入2,保存推出

13.启动节点zookeeper

注:
1.zookeeper集群是用来做主从切换的，不是用来做高可用的
  可以分别在主从上面做写入的测试，发现写入从，主也能同步到数据