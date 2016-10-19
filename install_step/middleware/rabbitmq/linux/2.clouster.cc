参考资料
http://88250.b3log.org/rabbitmq-clustering-ha
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
  192.168.22.181 rabbitmq_node1
  192.168.22.182 rabbitmq_node2
  192.168.22.183 rabbitmq_node3

  那么下面就以node1，node2，node3来表述这些机器

6.node2和node3上分别执行，让node2和node3加入集群
  rabbitmqctl stop_app
  rabbitmqctl join_cluster rabbit@rabbitmq_node1
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

10.使用HAProxy做负载均衡器
  首先负载均衡器放到192.168.22.184上面，所以下面对于haproxy的操作都是在184上操作的
  yum install -y haproxy

11.编辑haproxy配置文件
   为了保险起见，将原来的配置文件先备份一下
   cd /etc/haproxy/
   cp haproxy.cfg haproxy.cfg.bak

   清空原配置文件
   >haproxy.cfg
   vim haproxy.cfg
   listen rabbitmq_cluster 0.0.0.0:5672
   mode tcp
   balance     roundrobin
   server  rabbitmq_node1 192.168.22.181:5672 check inter 2000 rise 2 fall 3
   server  rabbitmq_node2 192.168.22.182:5672 check inter 2000 rise 2 fall 3
   server  rabbitmq_node3 192.168.22.183:5672 check inter 2000 rise 2 fall 3

12.保存后守护模式运行haproxy
   haproxy -f /etc/haproxy/haproxy.cfg -D

13.配置完成，可以使用客户端程序进行测试
   客户端直接连接184这台机器即可

========================问题解决==============================
1.启动时提示需要依赖其他节点先启动的情况
      这种情况无论先启动哪个节点都是一样的，强行初始化一下本地的mq环境再启动即可
      强行初始化主节点
  rabbitmqctl force_boot
  rabbitmq-server -detached
