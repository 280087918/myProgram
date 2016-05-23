=============
安装
=============
1.首先redis的安装很简单，redis本身没有针对windows版本的。不过windows平台下的redis服务在github上有提供
下载zip解压缩版的，不要mis版的。(因为mis版的直接在windows下安装了服务，侵入性太强)

2.解压缩后，直接运行文件夹下面的redis-server.exe即可


=============
主从
=============
本实验是同一台机器下的两个redis服务

1.将解压缩的文件分别放进两个文件夹内，一个文件夹为master，另一个为slave

2.修改salve文件夹下的redis.windows.conf
  将端口从6379修改为6380
  port 6380

3.修改slaveof参数，表明，是谁的从节点，也就是要指定master的信息
  slaveof 127.0.0.1 6379

4.master不做任何修改，稳妥起见，先启动master，再启动slave
  启动时要指定我们修改的conf文件地址,通过cmd运行
  启动master:redis-server.exe redis.windows.conf
  启动slave:同上