===============
Mysql��װ
===============
1.�Ȳ鿴������֮ǰ��û�а�װ��mysql
 rpm -qa | grep -i mysql

2.һ�����ô��ʾ
  MySQL-server-5.1.49-1.glibc23  
  MySQL-client-5.1.49-1.glibc23 
  ��������...(mysql-libs-5.1.66-2.el6_3.x86_64)

3.ж��
 rpm -e MySQL-client-5.1.49-1.glibc23
 rpm -e MySQL-server-5.1.49-1.glibc23
 �����һ����ϻ������(mysql-libs-5.1.66-2.el6_3.x86_64)
 ֱ��ж�ػ���ְ��������޷�ж�سɹ�����ʱ����������������һ������.������
	rpm -e --nodeps mysql-libs-5.1.66-2.el6_3.x86_64

4.��mysql������û������
	chkconfig --list | grep -i mysql
  ����еĻ��ͽ�����ֹͣ��
	chkconfig --del mysql

5.ɾ��mysql���ļ���
   whereis mysql
   ������ʾ:mysql: /usr/lib/mysql /usr/share/mysql

6.�ֱ�ɾ��Ŀ¼�ļ�
   rm -rf /usr/lib/mysql/
   rm -rf /usr/share/mysql

ͨ������6���������mysql�İ�װ��Ϣ

��װmysql
7.
�������û��perl��libaioҪ�Ȱ�װ����
yum -y install perl 
yum -y install libaio


yum -y remove mysql-*
8.����mysql��client��server
  ���õ�������汾
  MySQL-client-5.5.37-1.linux2.6.x86_64.rpm
  MySQL-server-5.5.37-1.linux2.6.x86_64.rpm
  rpm -ivh

9.��װ�ɹ��������������������һ���ģ�����˵���������������
  �鿴cd /var/lib/mysql �����.err�ļ�,�������������
  һ��ʼ˵��Fatal error: Can't open and lock privilege tables: Table 'mysql.host' doesn't exist
  ִ��mysql_install_db �Cusrer=mysql datadir=/var/lib/mysql
  Ҫ�ǻ����У����ٴε���ִ��mysql_install_db

10 Check that you do not already have another mysqld process
   ����취��
   ps -ef|grep mysql
   ���Կ�������������ռ����mysql��data�ļ���
   kill ����Щ����֮�� �ٴ����� mysql ��������
11.���˺�����mysql,service mysql start ���ǲ��С����ղ���9�鿴��˵���������
   Fatal error: Can't open and lock privilege tables: Can't find file: './mysql/host.frm' (errno: 13)
   ����Ŀ¼cd /var/lib/mysql/mysql
   �����ļ�host.frm���ڵģ�����˵����Ȩ�޲�������������Ŀ¼��ִ��������������
   chown mysql *
   chgrp mysql *
   chmod ug+rwx *
��������mysql�����ɹ�

12.������root����
 /usr/bin/mysqladmin -u root password root(��ʼ��������,��root��������)
 ������ð�װ��client��¼mysql,����:mysql -u root -p

13.�޸�root����
 Ū�������Ǹ�����root��Ϊ����ܵ��۰ɣ�ִ�����
 /usr/bin/mysqladmin -u root -p password ������ʾ�޸�����

14 ����mysql�ܾ��������
	# /etc/init.d/mysql stop
	# mysqld_safe --user=mysql --skip-grant-tables --skip-networking &
	# mysql -u root mysql
	mysql> UPDATE mysql.user SET Password=PASSWORD('111111') where USER='root';
	mysql> FLUSH PRIVILEGES;
	mysql> quit
	# /etc/init.d/mysql restart
	# mysql -u root -p  
	Enter password: <�������������newpassword> 
14.����root�Ķ���ip����Ȩ��
  grant all privileges on *.* to �������û���(root) @"%" identified by "����";
  ��:grant all privileges on *.* to root@"%" identified by 'ffzx2016';
  flush privileges;

15.�������rpm��װ��mysql����/etc������û��my.cnf����ļ���,my.cnf����������mysql�Ĳ�����ռ���ڴ�ʲô��
   a.����cd /usr/share/mysql
   b.�ҵ�my-large.cnf...my-small.cnf��һ����ŷ�������ģ��������Ƶ�/etc�ļ�������
   cp -rv my-small.cnf /etc
   c.��/etc����������my-samll.cnf,���my.cnf
   rename 'my-small' 'my' my-small.cnf
   d.���ļ���Ȩ��
   chmod 755 my.cnf
16 ����ͻ������Ӳ�ͨ
    �鿴����ǽ��Ϣ��
	#/etc/init.d/iptables status
	�رշ���ǽ����
	#/etc/init.d/iptables stop
	���ùرգ���֪����ô�����÷���
	#chkconfig -level 35 iptables off