!!!上层访问使用nginx的方式!!!

rabbitmq底层的构建方式与上面文档一致，这里为了方便，直接copy过来
======================================
通过erlang组建搭建rabbitmq集群

本次实验在3台虚拟机上测试
主:192.168.22.181
从:192.168.22.182
从:192.168.22.183

首先修改各个机器的主机名
vim /etc/sysconfig/network
181
   将HOSTNAME修改成rabbitmq_node1，这个是机器重启后生效
   hostname vm1，即时生效，不过最好还是将几台机器重启一下
182的修改成rabbitmq_node2，同理183

注意:要保证各个机器名的独立性，要不然后面会很头疼
如果改了机器名，必须先执行rabbitmqctl force_boot，再启动mq。然后再做后续的排序操作。

======================================
1.首先在那三个服务器都安装上rabbitmq，并通过网页登录看是否能顺利开启并登陆
  http://192.168.22.181:15672
  http://192.168.22.182:15672
  http://192.168.22.183:15672

2.在各个机器上查找.erlang.cookie这个文件，因为网上说的路径跟我本地的路径不一致
  find / -name .erlang.cookie
  发现这个文件在这里:
	/root/.erlang.cookie

3.定位好这个文件以后，我们需要做以这个操作，将主mq的.erlang.cookie复制到另外两台机器里面
  复制前说明:这个文件因为运行需要，是400的权限，权限高了不一定有用。
	所以要先升高另外两台机器的这个文件的权限，方便文件复制。后面再把权限降下来
  a)182,183机器分别操作
    chmod 777 /root/.erlang.cookie
  b)在181上将/root/.erlang.cookie复制到182和183上
    181上执行
    scp /root/.erlang.cookie root@192.168.22.182:/root
    scp /root/.erlang.cookie root@192.168.22.183:/root
  c)从机器降权，182和183上分别执行
    chmod 400 /root/.erlang.cookie
    #因为我安装rabbitmq的时候没有指定用户和组，直接是root用户的，所以下面这两行不必执行。
    #chown rabbitmq /root/.erlang.cookie
    #chgrp rabbitmq /root/.erlang.cookie
  
4.重启各个节点
   182和183分别执行
   #如果停不到就直接kill
   rabbitmqctl stop
   rabbitmq-server -detached

5.配置host,为了方便直接将下面配置复制到主还有所有节点当中
  vim /etc/hosts
  192.168.22.181 vm1
  192.168.22.182 vm2
  192.168.22.183 vm3

  那么下面就以node1，node2，node3来表述这些机器

6.node2和node3上分别执行，让node2和node3加入集群
  rabbitmqctl stop_app
  rabbitmqctl join_cluster rabbit@vm1
  rabbitmqctl start_app

7.任意节点查看集群状态
  rabbitmqctl cluster_status

8.执行以下命令，将队列复制到各个节点，任意节点运行，主节点也可以
  rabbitmqctl set_policy ha-all "^" '{"ha-mode":"all"}'

9.配置完成之后，原来的admin用户不知道为什么不能使用了
  解决方案，各个节点添加一个admin用户
	但是还是看不见exchange相关信息，更别说操作了，不过有一个很奇怪的现象，guest可以做admin相关操作,-_-!
  rabbitmqctl add_user hcadmin adminpwd
  rabbitmqctl set_user_tags hcadmin administrator

10.nginx安装(184,185)
  本次使用的nginx版本:nginx-1.9.15.tar.gz
  安装方法请查看install_step\thirdpart\nginx\linux\1.install_step.cc
	不过这里有一个需要注意的地方,编译的时候一定要加上tcp的模块，不过我用的1.9.x已经嵌入了tcp模块了。
	但是编译的时候需要加上--with-stream参数,也就是变成这样
	./configure --prefix=/usr/local/nginx-1.9.15 --conf-path=/usr/local/nginx-1.9.15/nginx.conf --with-stream

11.ngxin的tcp配置(184,185)
  因为我指定的读取的配置文件是安装目录下的nginx.conf，所以修改这里面的nginx.conf
    并调整为同目录的:3.cluster_nginx.conf中的代码。

12.184和185都安装好keepalived
      yum install -y keepalived

13.184和185上面新增文件
      vim /etc/keepalived/ng_down.sh
      输入下面代码
      #!/bin/bash
      pkill keepalived
      # chmod +x /etc/keepalived/ng_down.sh #授权可执行权限

14.修改184下的/etc/keepalived/keepalived.conf
     说是修改，其实是先将原来的备份，然后再新建一个这样的配置文件
     备份:mv /etc/keepalived/keepalived.conf /etc/keepalived/keepalived.conf.bk
     新增:vim /etc/keepalived/keepalived.conf
	184输入同目录下的3.cluster_nginx_keepalived_184.conf
	185输入同目录下的3.cluster_nginx_keepalived_185.conf

15.keepalived启停
     service keepalived start
     service keepalived stop
     service keepalived restart