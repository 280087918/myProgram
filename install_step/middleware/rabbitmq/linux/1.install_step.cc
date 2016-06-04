----------------------------
rabbitMq server的安装
http://www.cnblogs.com/shanyou/p/3902905.html
----------------------------
1.rabbitMq前置条件是安装erlang
  到官网http://www.erlang.org/downloads/下载对应版本的erlang
  目前最新版OTP 18.3 Source File
	就是说下载http://erlang.org/download/otp_src_18.3.tar.gz

2.下载rabbitmq server
   http://www.rabbitmq.com/releases/rabbitmq-server
   下载v3.6.2版本的rabbitmq-server-3.6.2.tar.xz

3.想解压缩这个xz的包需要安装xz解压缩插件
    yum install xz

4.解压缩rabbitmq-server-3.6.2.tar.xz
  xz -d rabbitmq-server-3.6.2.tar.xz

  完了之后就会变成rabbitmq-server-3.6.2.tar
  继续解压缩这个包
  tar -xvf rabbitmq-server-3.6.2.tar

3.下载rabbitmq client
   http://www.rabbitmq.com/releases/rabbitmq-java-client/
   下载v3.6.2版本的rabbitmq-java-client-3.6.2.tar.gz