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
  3)