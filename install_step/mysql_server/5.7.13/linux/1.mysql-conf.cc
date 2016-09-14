#参考资料 http://www.jb51.net/article/87160.htm
1.官网下载5.7.13
  mysql-5.7.13-linux-glibc2.5-x86_64.tar

2.解压缩这个包
  tar -xvf mysql-5.7.13-linux-glibc2.5-x86_64.tar
    得到两个包，分别是
    mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz
    mysql-test-5.7.13-linux-glibc2.5-x86_64.tar.gz，这个包目前还不知道有什么用

3.解压缩mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz
  tar -xzvf mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz -C /usr/local

4.默认解压缩出来的文件名太长，重命名一下
  mv mysql-5.7.13-linux-glibc2.5-x86_64/ mysql

5.创建仓库目录和日志目录
  mkdir -p /data/mysql
  mkdir -p /data/log/mysql

6.创建用户组及用户
  如果存在mysql用户，那么先删除
  userdel mysql
  groupdel mysql

  添加mysql用户
  groupadd -g 888 mysql
  useradd -r -s /sbin/nologin -g mysql mysql -d /usr/local/mysql

7.改变目录的所有者
  cd /usr/local/mysql
  chown -R mysql .
  chgrp -R mysql .
  chown -R mysql /data/mysql

8.安装其他插件
  yum install -y libaio*

9.配置参数
  bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/data/mysql

10.记住自动生成的随机密码
  dwwdYYuPi0<j

11.安装
   bin/mysql_ssl_rsa_setup --datadir=/data/mysql

12.复制配置文件和启动文件
   cd /usr/local/mysql/support-files
   cp my-default.cnf /etc/my.cnf
   cp mysql.server /etc/init.d/mysql

13.编辑mysql文件，修改下面两行
   basedir=/usr/local/mysql
   datadir=/data/mysql

14.修改my.cnf
   [client]
   port = 3306
   socket = /usr/local/mysql/mysql.sock
   
   [mysqld]
   basedir = /usr/local/mysql
   datadir = /data/mysql
   socket = /usr/local/mysql/mysql.sock

   [mysqld_safe]
   open-file-limit = 8192
   log-error = /data/log/mysql/mysql_3306.err

15.安全模式启动mysql
   bin/mysqld_safe --user=mysql &
   bin/mysql --user=root -p
   输入刚才记录的临时随机密码

16.设置密码
   set password=password('ffzx2016');
   grant all privileges on *.* to root@'%' identified by 'ffzx2016' with grant option;
   flush privileges;
   
17.添加系统路径
   vim /etc/profile
   添加
   export PATH=/usr/local/mysql/bin:$PATH
   #执行命令，环境变量马上生效
   source /etc/profile

18.配置开机启动
   chmod 755 /etc/init.d/mysql
   chkconfig --add mysql
   chkconfig --level 345 mysql on

19 如果客户端连接不通
    查看防火墙信息：
	#/etc/init.d/iptables status
	起停防火墙，即时生效，重启后失效:
	service iptables start
	service iptables stop
	起停防火墙，重启后生效:
	chkconfig iptables on
	chkconfig iptables off