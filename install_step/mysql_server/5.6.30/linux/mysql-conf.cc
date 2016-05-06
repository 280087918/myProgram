===============
Mysql安装
===============
1.先查看服务器之前有没有安装过mysql
 rpm -qa | grep -i mysql

2.一般会那么显示
  MySQL-server-5.1.49-1.glibc23  
  MySQL-client-5.1.49-1.glibc23 
  或者其他...(mysql-libs-5.1.66-2.el6_3.x86_64)

3.卸载
 rpm -e MySQL-client-5.1.49-1.glibc23
 rpm -e MySQL-server-5.1.49-1.glibc23
 比如我机器上还有这个(mysql-libs-5.1.66-2.el6_3.x86_64)
 直接卸载会出现包依赖而无法卸载成功，这时候就往命令里面添加一个参数.如这样
	rpm -e --nodeps mysql-libs-5.1.66-2.el6_3.x86_64

4.看mysql服务有没有启动
	chkconfig --list | grep -i mysql
  如果有的话就将服务停止掉
	chkconfig --del mysql

5.删除mysql的文件夹
   whereis mysql
   比如显示:mysql: /usr/lib/mysql /usr/share/mysql

6.分别删除目录文件
   rm -rf /usr/lib/mysql/
   rm -rf /usr/share/mysql

通过以上6步可以清楚mysql的安装信息

安装mysql
7.
如果机器没有perl和libaio要先安装依赖
yum -y install perl 
yum -y install libaio


yum -y remove mysql-*
8.下载mysql的client和server
  我用的是这个版本
  MySQL-client-5.5.37-1.linux2.6.x86_64.rpm
  MySQL-server-5.5.37-1.linux2.6.x86_64.rpm
  rpm -ivh

9.安装成功，但是启动报错，这个不一定的，这里说的是如果报错的情况
  查看cd /var/lib/mysql 下面的.err文件,看哪里出问题了
  一开始说是Fatal error: Can't open and lock privilege tables: Table 'mysql.host' doesn't exist
  执行mysql_install_db –usrer=mysql datadir=/var/lib/mysql
  要是还不行，则再次单独执行mysql_install_db

10 Check that you do not already have another mysqld process
   解决办法：
   ps -ef|grep mysql
   可以看到有其它进程占用了mysql的data文件夹
   kill 掉这些进程之后 再次启动 mysql 正常启动
11.完了后启动mysql,service mysql start 还是不行。按照步骤9查看，说是这个问题
   Fatal error: Can't open and lock privilege tables: Can't find file: './mysql/host.frm' (errno: 13)
   进入目录cd /var/lib/mysql/mysql
   发现文件host.frm是在的，网上说的是权限不够，于是在那目录下执行下面三个命令
   chown mysql *
   chgrp mysql *
   chmod ug+rwx *
问题解决，mysql启动成功

12.新增用root密码
 /usr/bin/mysqladmin -u root password root(初始是新增的,‘root’是密码)
 这个是用安装的client登录mysql,命令:mysql -u root -p

13.修改root密码
 弄完上面那个觉得root作为密码很蛋疼吧，执行这个
 /usr/bin/mysqladmin -u root -p password 按照提示修改密码

14 访问mysql拒绝情况处理
	# /etc/init.d/mysql stop
	# mysqld_safe --user=mysql --skip-grant-tables --skip-networking &
	# mysql -u root mysql
	mysql> UPDATE mysql.user SET Password=PASSWORD('111111') where USER='root';
	mysql> FLUSH PRIVILEGES;
	mysql> quit
	# /etc/init.d/mysql restart
	# mysql -u root -p  
	Enter password: <输入新设的密码newpassword> 
14.开启root的对外ip访问权限
  grant all privileges on *.* to 创建的用户名(root) @"%" identified by "密码";
  如:grant all privileges on *.* to root@"%" identified by 'ffzx2016';
  flush privileges;

15.如果是用rpm安装的mysql，在/etc下面是没有my.cnf这个文件的,my.cnf是用来调整mysql的参数，占用内存什么的
   a.进入cd /usr/share/mysql
   b.找到my-large.cnf...my-small.cnf找一个大概符合需求的，将它复制到/etc文件夹下面
   cp -rv my-small.cnf /etc
   c.到/etc下面重命名my-samll.cnf,变成my.cnf
   rename 'my-small' 'my' my-small.cnf
   d.给文件赋权限
   chmod 755 my.cnf
16 如果客户端连接不通
    查看防火墙信息：
	#/etc/init.d/iptables status
	关闭防火墙服务：
	#/etc/init.d/iptables stop
	永久关闭？不知道怎么个永久法：
	#chkconfig -level 35 iptables off