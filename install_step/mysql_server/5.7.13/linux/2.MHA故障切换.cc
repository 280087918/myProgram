�ο�����:http://www.iyunv.com/thread-21790-1-1.html


1.����������MHA��manager��node��
  https://code.google.com/p/mysql-master-ha
  ��������صİ汾��:
  mha4mysql-manager-0.56-0.el6.noarch.rpm
  mha4mysql-node-0.56-0.el6.noarch.rpm

2.��������
  manager:192.168.1.85
  д���ݿ�:192.168.1.98
  �����ݿ�:192.168.1.81
  �����ݿ�:192.168.1.84

3.��������ͨ����Կ�����໥�������
  1)��manager��������������:
    192.168.1.85:
    ssh-keygen -t rsa
    һ·�س�
    �鿴���ɵĹ�Կ
    ls -l ~/.ssh/
  2)��manager�Ĺ�Կ��Ϣ���ݵ�����mysql������
    192.168.1.85:
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.98
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.84
  3)д�ڵ㴫��������Ϣ���������ڵ�
    192.168.1.98:
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.84
  4)��ȡ�����ݿ�84��һ��������Ϣ
    192.168.1.84:
    cd ~/.ssh/
    sudo cat authorized_keys
  5)��ȡmanager�Զ��ڵ�84����Ȩ��Ϣ
    ssh 192.168.1.84 "/sbin/ifconfig |grep 'inet addr' |head -1"
        ��ʾ:inet addr:10.0.2.15  Bcast:10.0.2.255  Mask:255.255.255.0

4.manager��������װ����
  192.168.1.85:
  sudo yum -y install perl-DBD-MySQL.x86_64 erl-Log-Dispatch perl-Config-Tiny perl-Parallel-ForkManager

5.manager��������װmanager
  192.168.1.85:
  rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm --nodeps

6.manager����Ŀ¼
  mkdir /etc/masterha
  mkdir -p /masterha/app1

7.�����ļ�vim /etc/masterha/app1.cnf
  �ļ����ݼ�ͬ��Ŀ¼

8.manager��װMHA��rpm��
  1)�Ȱ�װnode�İ���Ҫ��Ȼ�ᱨ�쳣
    rpm -ivh mha4mysql-node-0.56-0.el6.noarch.rpm
  2)��װmanager�İ������ֻ���һ�ѵ�������ʾ
    rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm
    ��Ϊ��̫���ˣ�������ý�һ���ļ��й����������ȫ���ŵ�mhadeps��
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

    ����perl-Time-HiRes�����ṩ���������ز��ˣ�����������װ
    yum install -y perl-Time-HiRes

    �����ٴ�ִ��manager��rpm���İ�װ��ͨ����
    rpm -ivh mha4mysql-manager-0.56-0.el6.noarch.rpm

9.node�ڵ㰲װMHA��node��
  1)�Ȱ�װperl������
      yum install perl-DBD-MySQL
  2)��װrpm��
      sudo rpm -ivh mha4mysql-node-0.56-0.el6.noarch.rpm

10.manager��ִ�нű����������ڵ��໥�䣬��manager�໥���sshͨѶ�Ƿ�����
    masterha_check_ssh --conf=/etc/masterha/app1.cnf
    Ҫȫ����ok�ſ��ԣ������������Ϣ
	Connecting via SSH from root@192.168.1.84(192.168.1.84:22) to root@192.168.1.81(192.168.1.81:22)..
	Permission denied (publickey,gssapi-keyex,gssapi-with-mic,password).
    ��ô����˵84��81��ͨѶ�����⣬��ô����81�ϴ�����Կ�������ε�81������:
        192.168.1.84��ִ��
	ssh-keygen -t rsa
	ssh-copy-id -i /root/.ssh/id_rsa.pub root@192.168.1.81
    ������ƣ���ͬ�����쳣����ô���

12.�����ӽڵ�������mysqlbinlog����mysql��������
   192.168.1.81,192.168.1.81��ִ��:
   ln -s /usr/local/mysql/bin/mysqlbinlog /usr/bin/mysqlbinlog
   ln -s /usr/local/mysql/bin/mysql /usr/bin

11.manager��ִ�нű����������ڵ��໥�䣬��manager�໥��ĸ����Ƿ�����
    masterha_check_repl --conf=/etc/masterha/app1.cnf
    1)���쳣�����������⣬�ȱ�֤�п���Ҫ����Ϊmaster�Ľڵ㶼����binlog���������Ƕ�Ҫ����
    2)������ʾ����:
	192.168.1.84(192.168.1.84:3306): User repluser does not exist or does not have REPLICATION SLAVE privilege
	���:��master��(192.168.1.98)�ϴ���slave�û�
	grant replication slave  on *.* to 'repluser'@'192.168.1.81' identified by '123456' with grant option;
	grant replication slave  on *.* to 'repluser'@'192.168.1.84' identified by '123456' with grant option;
	FLUSH PRIVILEGES;
    3)������ʾ����:
        Connecting to root@192.168.1.81(192.168.1.81:22)..
	Can't exec "mysqlbinlog": û���Ǹ��ļ���Ŀ¼ at /usr/share/perl5/vendor_perl/MHA/BinlogManager.pm line 106.
	��192.168.1.81�ϲ���
	�Ǹ�perl�ű�������/usr/bin/��mysqlbinlog,�ȿ��������ڵ�mysqlbinlog������
	  which mysqlbinlog
	Ȼ������һ��������
          ln -s /usr/local/mysql/bin/mysqlbinlog /usr/bin/mysqlbinlog
    4)������ʾ����:
	mysql command failed with rc 127:0!
	�ڴӽڵ��϶�����mysql������������
	ln -s /usr/local/mysql/bin/mysql /usr/bin
    5)ʣ�µĴ��������һ���������ڵ����޸�

12.����manager�ڵ�
   manager(192.168.1.85)��ִ������:
   masterha_manager --conf=/etc/masterha/app1.cnf &
   ��/masterha/app1/manager.log�Ͽ��Բ鿴����־

13.���״̬
   manager(192.168.1.85)��ִ������:
   masterha_check_status --conf=/etc/masterha/app1.cnf

========================����========================
1.ֹͣ����98�����Ѿ��л���ȥ������81��ȥ
    ������һ�����⣬�Ǹ�MHA�̱߳���ֹ�ˡ���֪�����Ƿ�������������

2.��ԭ98����
    ���ȣ�98Ҫ��������
    a)�ڴӿ�81��Ҫ��Ȩ98�û�
	grant replication slave  on *.* to 'repluser'@'192.168.1.98' identified by '123456' with grant option;
    b)98��ʱ��Ϊ81�Ĵӿ�
        98��ִ��
	reset master;
	#�������������Բο�������81�ϵ�״̬��������:show master status;
	change master to master_host='192.168.1.81', master_port=3306, master_user='repluser', master_password='123456', master_log_file='mysql-v2-binlog.000001', master_log_pos=154;
	start slave;
    c)manager��ִ��
        masterha_master_switch --master_state=alive --conf=/etc/masterha/app1.cnf
    d)�µ�����ᶪʧͬ��
        81��ִ��
        change master to master_host='192.168.1.98', master_port=3306, master_user='repluser', master_password='123456', master_log_file='mysql-v1-binlog.000001', master_log_pos=154;
	start slave;
    e)�������ʵ�飬��ô����������manager�ڵ�֮ǰ��ɾ�������ļ�
	rm -f /masterha/app1app1.failover.complete
    f)���ڻ���֪��Ϊʲô�����������󣬻�����һ�ѽ��̣���������������������ɱ������
        ps -ef|grep master
	ɱǰ�����Ϳ�����
	Ȼ��������
	masterha_manager --conf=/etc/masterha/app1.cnf &

====С��
MHAֻ���������Ĺ����л��������ڲ��л���û�д���һ˵������ʵ���л��������������ֶΣ�����lvs��
MHA�л��ɹ�һ�κ󣬼���߳̾ʹ���ֹ������ȫ�޸��󣬷�������MHA�߳�