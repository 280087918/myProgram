1.�������ļ����Ƶ�Զ��
  scp /usr/local/soft/jdk-7u79-linux-x64.tar.gz root@192.168.1.84:/usr/local/soft/

2.��Զ���ļ����Ƶ�����
  scp root@192.168.1.78:/usr/local/soft/apache-tomcat-7.0.69.zip /usr/local/soft

3.�鿴�˿���û�п���
  netstat -tunlp|grep 9991

4.�޸ķ�����ʱ��
  date  -s "2015-5-8 19:48:00"

5.�޸ķ���������
  �޸���֮�������п�ͷ���� [root@vm1 ~]# 
  vim /etc/sysconfig/network
  ����HOSTNAME=vm1����������Ч
  hostname vm1����ʱ��Ч

6��ͣ����ǽ����ʱ��Ч��������ʧЧ:
  service iptables start
  service iptables stop
  ��ͣ����ǽ����������Ч:
  chkconfig iptables on
  chkconfig iptables off
