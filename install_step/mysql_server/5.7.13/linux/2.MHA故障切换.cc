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
  3)