--�����ֿ�--

����:��Ҫ�Ȱ�װ��svn
����һ���ֿ�ffzx
����һ��es����ĿΪ����

1.�����ֿ�
  mkdir -p /opt/svn/repository/ffzx
  svnadmin create /opt/svn/repository/ffzx

2.�༭�汾�������ļ�
  vim /opt/svn/repository/ffzx/conf/svnserve.conf
  #�����������Ϊnone��Ҫ��Ȼ���е��û�����Ҫ����Ϳ��Է��ʲֿ��ļ�
  anon-access = none
  auth-access = write
  password-db = haocheng
  authz-db = authz
  realm = ffzx
  use-sasl = true

3.�༭�û�������
  vim /opt/svn/repository/ffzx/conf/passwd
  #����û�zhc������zhcpwd
  zhc = zhcpwd

4.����Ȩ��
  vim /opt/svn/repository/ffzx/conf/authz

  #����һ���û����devTeam��������һ���û���zhc������ж����ô�ö��Ÿ���
  [groups]
  devTeam = zhc

  #�û�zhc�Ը�Ŀ¼���ж�д��Ȩ��
  [/]
  zhc = rw

  #�û���devTeam����Ŀesӵ�ж�д��Ȩ��
  [ffzx:/es]
  @devTeam = rw
  
5.����svn�û�
  useradd svn
  passwd svn
  ���������õ�svn����Ϊsvnpwd

6.����svn�û����Բ���svnĿ¼(��ʵҲû�б�Ҫ��������ֱ����root����svn)
  chown -R svn:svn /opt/svn/repository

7.����svn
  svnserve -d -r /opt/svn/repository

  ��svn�Ƿ������ɹ�
  netstat -tunlp | grep svn

8.���ÿ���������
  vim /etc/rc.d/rc.local
  /opt/svn/subversion/bin/svnserve -d �Clisten-port 3690 -r /opt/svn/repository

9.svn������������Ϳ�����svn����ͣ����
  vim /etc/rc.d/init.d/svn
  �����ı� link-->ͬ��Ŀ¼�� 3.create_svn_service.cc
  ���ı���Ȩ��
  chmod 755 /etc/rc.d/init.d/svn

  svn��ͣ
  service svn stop
  service svn start

  ɱsvn����
  killall svnserve

10.�������ַ�ʽֻ����svn://192.168.22.182/ffzx ���ַ�ʽ����
 
���ÿ��ܻ��е����⣬�����ã�������ʱ���ٵ���