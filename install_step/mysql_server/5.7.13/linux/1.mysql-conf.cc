#�ο����� http://www.jb51.net/article/87160.htm
1.��������5.7.13
  mysql-5.7.13-linux-glibc2.5-x86_64.tar

2.��ѹ�������
  tar -xvf mysql-5.7.13-linux-glibc2.5-x86_64.tar
    �õ����������ֱ���
    mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz
    mysql-test-5.7.13-linux-glibc2.5-x86_64.tar.gz�������Ŀǰ����֪����ʲô��

3.��ѹ��mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz
  tar -xzvf mysql-5.7.13-linux-glibc2.5-x86_64.tar.gz -C /usr/local

4.Ĭ�Ͻ�ѹ���������ļ���̫����������һ��
  mv mysql-5.7.13-linux-glibc2.5-x86_64/ mysql

5.�����ֿ�Ŀ¼����־Ŀ¼
  mkdir -p /data/mysql
  mkdir -p /data/log/mysql

6.�����û��鼰�û�
  �������mysql�û�����ô��ɾ��
  userdel mysql
  groupdel mysql

  ���mysql�û�
  groupadd -g 888 mysql
  useradd -r -s /sbin/nologin -g mysql mysql -d /usr/local/mysql

7.�ı�Ŀ¼��������
  cd /usr/local/mysql
  chown -R mysql .
  chgrp -R mysql .
  chown -R mysql /data/mysql

8.��װ�������
  yum install -y libaio*

9.���ò���
  bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/data/mysql

10.��ס�Զ����ɵ��������
  dwwdYYuPi0<j

11.��װ
   bin/mysql_ssl_rsa_setup --datadir=/data/mysql

12.���������ļ��������ļ�
   cd /usr/local/mysql/support-files
   cp my-default.cnf /etc/my.cnf
   cp mysql.server /etc/init.d/mysql

13.�༭mysql�ļ����޸���������
   basedir=/usr/local/mysql
   datadir=/data/mysql

14.�޸�my.cnf
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

15.��ȫģʽ����mysql
   bin/mysqld_safe --user=mysql &
   bin/mysql --user=root -p
   ����ղż�¼����ʱ�������

16.��������
   set password=password('ffzx2016');
   grant all privileges on *.* to root@'%' identified by 'ffzx2016' with grant option;
   flush privileges;
   
17.���ϵͳ·��
   vim /etc/profile
   ���
   export PATH=/usr/local/mysql/bin:$PATH
   #ִ�������������������Ч
   source /etc/profile

18.���ÿ�������
   chmod 755 /etc/init.d/mysql
   chkconfig --add mysql
   chkconfig --level 345 mysql on

19 ����ͻ������Ӳ�ͨ
    �鿴����ǽ��Ϣ��
	#/etc/init.d/iptables status
	��ͣ����ǽ����ʱ��Ч��������ʧЧ:
	service iptables start
	service iptables stop
	��ͣ����ǽ����������Ч:
	chkconfig iptables on
	chkconfig iptables off