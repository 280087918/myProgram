=====================
��������:
����ʵ��ȫ�������ҵ�������½��У�һ��4̨����
ip�ֱ���:
192.168.1.78��192.168.1.83��192.168.1.84��192.168.1.85

��������ô����:
192.168.1.78 ���ؾ���-����
192.168.1.83 ���ؾ���-����
192.168.1.84 web����1
192.168.1.85 web����2
=====================

1.�ҵ�web��������tomcat�����Ҷ���ʹ��80�˿�
  ��84��85�ϰ�װjdk��tomcat
  ��������һ��index.html,������˵�����response�Ǵ���������
  ����д��this page is from 192.168.1.84

2.����̨web������84��85����������ip(VIP)
  ��һ������û����ʹ�õ�ip,�������õ���120
  2_a)����Ŀ¼/etc/init.d/ ���½��ļ�realserver
	vi realserver
  2_b)������������,����ͬ��Ŀ¼Ѱ��install_step_weblvs.cc,��������Ĵ���
  2_c)����󣬸��ļ���Ȩ��
        chmod 777 realserver
  2_d)Ϊ�˷�ֹ���е�ʱ��˵û��functions��Ȩ�ޣ������ȸ�functionsȨ��
      cd /etc/rc.d/init.d/
      chmod 777 functions
  2_e)����realserver����
      service realserver start

3.����������װkeepalived
  yum install keepalived

4.�޸ĸ���������keepalived����
  cd /etc/keepalived
  Ϊ�˱��գ���ԭ����conf�ļ�����
  cp keepalived.conf keepalived.conf.bak
  ���ԭ���ļ�
  >keepalived.conf
  ��install_step_master.cc����ճ����keepalived.conf�ļ�����ȥ

5.������������keepalived����
  service keepalived start

6.���ر�������������һ�����Ȱ�װ

7.���ر������ø�������������һ�£��и���ط���Ҫ�޸�
  �޸�state,state BACKUP
  ��Ϊ����priority��100�����Ա������ܸ���������ֻ�����99
  �޸�priority,priority 99

8.��������keepalived����
  service keepalived start

9.����
  http://192.168.1.120/index.html
  ���,ctrl+f5 ������ʾ