参考资料:http://www.iyunv.com/thread-21790-1-1.html


1.到官网下载MHA的manager和node包
  https://code.google.com/p/mysql-master-ha
  我这次下载的版本是:
  mha4mysql-manager-0.56-0.el6.noarch.rpm
  mha4mysql-node-0.56-0.el6.noarch.rpm

2.背景介绍
  manager:192.168.1.85
  写数据库:192.168.1.98
  读数据库:192.168.1.81
  读数据库:192.168.1.84

3.服务器间通过公钥配置相互间的信任
  1)在manager服务器输入命令:
    192.168.1.85:
    ssh-keygen -t rsa
    一路回车
    查看生成的公钥
    ls -l ~/.ssh/
  2)将manager的公钥信息传递到各个mysql服务器
    192.168.1.85:
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.98
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.84
  3)写节点传递信任信息到两个读节点
    192.168.1.98:
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.84
  4)抽取读数据库84看一下信任信息
    192.168.1.84:
    cd ~/.ssh/
    sudo cat authorized_keys
  5)抽取manager对读节点84的授权信息
    ssh 192.168.1.84 "/sbin/ifconfig |grep 'inet addr' |head -1"
        显示:inet addr:10.0.2.15  Bcast:10.0.2.255  Mask:255.255.255.0

4.manager服务器安装依赖
  192.168.1.85:
  sudo yum -y install perl-DBD-MySQL.x86_64 erl-Log-Dispatch perl-Config-Tiny perl-Parallel-ForkManager

5.manager服务器安装manager
  192.168.1.85:
  rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm --nodeps

6.manager创建目录
  mkdir /etc/masterha
  mkdir -p /masterha/app1

7.创建文件vim /etc/masterha/app1.cnf
  文件内容见同级目录

8.manager安装MHA的rpm包
  1)先安装node的包，要不然会报异常
    rpm -ivh mha4mysql-node-0.56-0.el6.noarch.rpm
  2)安装manager的包，发现会有一堆的依赖提示
    rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm
    因为包太多了，所以最好建一个文件夹管理，我这里就全部放到mhadeps下
    wget http://mirror.centos.org/centos/6/os/x86_64/Packages/perl-Time-HiRes-1.9721-136.el6.x86_64.rpm
    rpm -ivh perl-Time-HiRes-1.9721-136.el6.x86_64.rpm

    wget http://dl.fedoraproject.org/pub/epel/6/i386/perl-Mail-Sender-0.8.16-3.el6.noarch.rpm
    rpm -ivh perl-Mail-Sender-0.8.16-3.el6.noarch.rpm

    wget http://dl.fedoraproject.org/pub/epel/6/i386/perl-Mail-Sendmail-0.79-12.el6.noarch.rpm
    rpm -ivh perl-Mail-Sendmail-0.79-12.el6.noarch.rpm

    wget http://downloads.naulinux.ru/pub/NauLinux/6x/i386/sites/School/RPMS/perl-Log-Dispatch-2.27-1.el6.noarch.rpm
    rpm -ivh perl-Log-Dispatch-2.27-1.el6.noarch.rpm

    wget http://dl.Fedoraproject.org/pub/epel/6/i386/perl-Parallel-ForkManager-0.7.9-1.el6.noarch.rpm
    rpm -ivh perl-Parallel-ForkManager-0.7.9-1.el6.noarch.rpm

    还有perl-Time-HiRes网上提供的连接下载不了，可以这样安装
    yum install -y perl-Time-HiRes

    这样再次执行manager的rpm包的安装就通过了
    rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm

9.node节点安装MHA的node包
  1)先安装perl的依赖
      yum install perl-DBD-MySQL
  2)安装rpm包
      sudo rpm -ivh mha4mysql-node-0.56-0.el6.noarch.rpm

10.manager上执行脚本，看各个节点相互间，与manager相互间的ssh通讯是否正常
    masterha_check_ssh --conf=/etc/masterha/app1.cnf
    要全部都ok才可以，如果有如下信息
	Connecting via SSH from root@192.168.1.84(192.168.1.84:22) to root@192.168.1.81(192.168.1.81:22)..
	Permission denied (publickey,gssapi-keyex,gssapi-with-mic,password).
    那么就是说84到81的通讯有问题，那么就在81上创建公钥，并信任到81，如下:
        192.168.1.84上执行
	ssh-keygen -t rsa
	ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    如此类推，有同样的异常就这么解决

12.各个从节点上配置mysqlbinlog，和mysql的软连接
   192.168.1.81,192.168.1.81上执行:
   ln -s /usr/local/mysql/bin/mysqlbinlog /usr/bin/mysqlbinlog
   ln -s /usr/local/mysql/bin/mysql /usr/bin

11.manager上执行脚本，看各个节点相互间，与manager相互间的复制是否正常
    masterha_check_repl --conf=/etc/masterha/app1.cnf
    1)报异常，复制有问题，先保证有可能要升级为master的节点都开启binlog，我这里是都要开启
    2)错误提示如下:
	192.168.1.84(192.168.1.84:3306): User repluser does not exist or does not have REPLICATION SLAVE privilege
	解决:在master端(192.168.1.98)上创建slave用户
	grant replication slave  on *.* to 'repluser'@'192.168.1.81' identified by '123456' with grant option;
	grant replication slave  on *.* to 'repluser'@'192.168.1.84' identified by '123456' with grant option;
	FLUSH PRIVILEGES;
    3)错误提示如下:
        Connecting to root@192.168.1.81(192.168.1.81:22)..
	Can't exec "mysqlbinlog": 没有那个文件或目录 at /usr/share/perl5/vendor_perl/MHA/BinlogManager.pm line 106.
	在192.168.1.81上操作
	那个perl脚本读的是/usr/bin/的mysqlbinlog,先看我们现在的mysqlbinlog在哪里
	  which mysqlbinlog
	然后再做一个软连接
          ln -s /usr/local/mysql/bin/mysqlbinlog /usr/bin/mysqlbinlog
    4)错误提示如下:
	mysql command failed with rc 127:0!
	在从节点上都做上mysql启动的软连接
	ln -s /usr/local/mysql/bin/mysql /usr/bin
    5)剩下的错误跟上面一样，各个节点上修复

12.启动manager节点
   manager(192.168.1.85)上执行命令:
   masterha_manager --conf=/etc/masterha/app1.cnf &
   在/masterha/app1/manager.log上可以查看到日志

13.检查状态
   manager(192.168.1.85)上执行命令:
   masterha_check_status --conf=/etc/masterha/app1.cnf

========================测试========================
1.停止主库98发现已经切换过去到备库81上去
    不过有一个问题，那个MHA线程被终止了。不知道这是否属于正常现象

2.还原98主库
    首先，98要启动起来
    a)在从库81上要授权98用户
	grant replication slave  on *.* to 'repluser'@'192.168.1.98' identified by '123456' with grant option;
    b)98临时变为81的从库
        98上执行
	reset master;
	#下面这个命令可以参考新主库81上的状态进行配置:show master status;
	change master to master_host='192.168.1.81', master_port=3306, master_user='repluser', master_password='123456', master_log_file='mysql-v2-binlog.000001', master_log_pos=154;
	start slave;
    c)manager上执行
        masterha_master_switch --master_state=alive --conf=/etc/masterha/app1.cnf
    d)新的主库会丢失同步
        81上执行
        change master to master_host='192.168.1.98', master_port=3306, master_user='repluser', master_password='123456', master_log_file='mysql-v1-binlog.000001', master_log_pos=154;
	start slave;
    e)如果想多次实验，那么在重新启动manager节点之前先删除下面文件
	rm -f /masterha/app1app1.failover.complete
    f)现在还不知道为什么重新启动过后，会启动一堆进程，并且启动不起来，必须杀掉再启
        ps -ef|grep master
	杀前三个就可以了
	然后再启动
	masterha_manager --conf=/etc/masterha/app1.cnf &

====小结
MHA只能做主备的故障切换，是在内部切换，没有代理一说，程序实行切换还需其他辅助手段，比如lvs。
MHA切换成功一次后，检测线程就此终止，待完全修复后，方可重启MHA线程