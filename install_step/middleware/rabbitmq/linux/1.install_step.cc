----------------------------
rabbitMq server的安装
http://www.cnblogs.com/shanyou/p/3902905.html
----------------------------
1.安装rabbitMq依赖插件
  yum install -y xmlto rsync zip nc

2.安装simplejson
  用本地的包，或者翻墙找最新的包https://pypi.python.org/pypi/simplejson/
  本次使用当前最新版本:simplejson-3.8.2.tar.gz
  tar -xzvf simplejson-3.8.2.tar.gz -C /usr/local/
  cd /usr/local/simplejson-3.8.2
  python setup.py build
  python setup.py install

2.rabbitMq前置条件是安装erlang
  到官网http://www.erlang.org/downloads/下载对应版本的erlang
  目前最新版OTP 18.3 Source File
	就是说下载http://erang.org/download/otp_src_18.3.tar.gz
  tar -xzvf otp_src_18.3.tar.gz -C /usr/local/
  cd /usr/local/otp_src_18.3
  ./configure
  make
  make install

3.测试erlang有没有正确安装
  首先mysql的命令结束符是";"，erlang命令的结束符是"."
  启动erlang:erl
  输入9+3.
      看结果是不是12，尼玛一般都是了。
  退出erlang:halt().
      记得"."结束符

4.下载rabbitmq server
   http://www.rabbitmq.com/releases/rabbitmq-server
   下载v3.6.2版本的rabbitmq-server-3.6.2.tar.xz

5.想解压缩这个xz的包需要安装xz解压缩插件
    yum install -y xz

6.解压缩rabbitmq-server-3.6.2.tar.xz
  xz -d rabbitmq-server-3.6.2.tar.xz

  完了之后就会变成rabbitmq-server-3.6.2.tar
  继续解压缩这个包
  tar -xvf rabbitmq-server-3.6.2.tar
  mv rabbitmq-server-3.6.2/ /usr/local

7.下载rabbitmq client
   http://www.rabbitmq.com/releases/rabbitmq-java-client/
   下载v3.6.2版本的rabbitmq-java-client-3.6.2.tar.gz，并上传到linux服务器
   tar -xzvf rabbitmq-java-client-3.6.2.tar.gz -C /usr/local

8.进入rabbitmq server目录，并进行编译
   cd /usr/local/rabbitmq-server-3.6.2
   make install TARGET_DIR=/opt/mq/rabbitmq SBIN_DIR=/opt/mq/rabbitmq/sbin MAN_DIR=/opt/mq/rabbitmq/man

9.rabbitmq相关命令
  启动:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmq-server start &
  查看mq状态:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmqctl status
  关闭mq:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmqctl stop
  如果嫌上面的路径太长，可以修改系统环境变量:
  vi /etc/profile
  文件最后加入
  #rabbitmq enviroment
  export PATH=$PATH:/usr/local/rabbitmq-server-3.6.2/scripts
  环境变量设置马上生效:
  source /etc/profile
  
  那么以后就可以直接启动了rabbitmq-server start &
  上面这个启动命令不靠谱，一会儿就会自动关闭了，用下面这个命令能使启动一致在后台运行
  rabbitmq-server -detached

10.启用rabbit控制台插件
   mkdir /etc/rabbitmq
   cd /usr/local/rabbitmq-server-3.6.2/scripts
   rabbitmq-plugins enable rabbitmq_management

11.登录rabbitmq控制台
   说明:guest/guest是localhost用户登录的，如果想远程登录，就修改配置文件/etc/rabbitmq/rabbitmq.config
   如果没有就创建
   在配置文件里面输入
   [
    {rabbit, [{tcp_listeners, [5672]}, {loopback_users, ["guest"]}]}
   ].
   重启rabbitmq服务，这样就可以用guest登录了

12.现在只有guest用户，没有administrator用户怎么办，按照以下步骤创建
   rabbitmqctl add_user admin 123456
   rabbitmqctl set_user_tags admin administrator
   rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"