!!!�ϲ����ʹ��nginx�ķ�ʽ!!!

rabbitmq�ײ�Ĺ�����ʽ�������ĵ�һ�£�����Ϊ�˷��㣬ֱ��copy����
======================================
ͨ��erlang�齨�rabbitmq��Ⱥ

����ʵ����3̨������ϲ���
��:192.168.22.181
��:192.168.22.182
��:192.168.22.183

�����޸ĸ���������������
vim /etc/sysconfig/network
181
   ��HOSTNAME�޸ĳ�rabbitmq_node1������ǻ�����������Ч
   hostname vm1����ʱ��Ч��������û��ǽ���̨��������һ��
182���޸ĳ�rabbitmq_node2��ͬ��183

ע��:Ҫ��֤�����������Ķ����ԣ�Ҫ��Ȼ������ͷ��
������˻�������������ִ��rabbitmqctl force_boot��������mq��Ȼ���������������������

======================================
1.����������������������װ��rabbitmq����ͨ����ҳ��¼���Ƿ���˳����������½
  http://192.168.22.181:15672
  http://192.168.22.182:15672
  http://192.168.22.183:15672

2.�ڸ��������ϲ���.erlang.cookie����ļ�����Ϊ����˵��·�����ұ��ص�·����һ��
  find / -name .erlang.cookie
  ��������ļ�������:
	/root/.erlang.cookie

3.��λ������ļ��Ժ�������Ҫ�����������������mq��.erlang.cookie���Ƶ�������̨��������
  ����ǰ˵��:����ļ���Ϊ������Ҫ����400��Ȩ�ޣ�Ȩ�޸��˲�һ�����á�
	����Ҫ������������̨����������ļ���Ȩ�ޣ������ļ����ơ������ٰ�Ȩ�޽�����
  a)182,183�����ֱ����
    chmod 777 /root/.erlang.cookie
  b)��181�Ͻ�/root/.erlang.cookie���Ƶ�182��183��
    181��ִ��
    scp /root/.erlang.cookie root@192.168.22.182:/root
    scp /root/.erlang.cookie root@192.168.22.183:/root
  c)�ӻ�����Ȩ��182��183�Ϸֱ�ִ��
    chmod 400 /root/.erlang.cookie
    #��Ϊ�Ұ�װrabbitmq��ʱ��û��ָ���û����飬ֱ����root�û��ģ��������������в���ִ�С�
    #chown rabbitmq /root/.erlang.cookie
    #chgrp rabbitmq /root/.erlang.cookie
  
4.���������ڵ�
   182��183�ֱ�ִ��
   #���ͣ������ֱ��kill
   rabbitmqctl stop
   rabbitmq-server -detached

5.����host,Ϊ�˷���ֱ�ӽ��������ø��Ƶ����������нڵ㵱��
  vim /etc/hosts
  192.168.22.181 vm1
  192.168.22.182 vm2
  192.168.22.183 vm3

  ��ô�������node1��node2��node3��������Щ����

6.node2��node3�Ϸֱ�ִ�У���node2��node3���뼯Ⱥ
  rabbitmqctl stop_app
  rabbitmqctl join_cluster rabbit@vm1
  rabbitmqctl start_app

7.����ڵ�鿴��Ⱥ״̬
  rabbitmqctl cluster_status

8.ִ��������������и��Ƶ������ڵ㣬����ڵ����У����ڵ�Ҳ����
  rabbitmqctl set_policy ha-all "^" '{"ha-mode":"all"}'

9.�������֮��ԭ����admin�û���֪��Ϊʲô����ʹ����
  ��������������ڵ����һ��admin�û�
	���ǻ��ǿ�����exchange�����Ϣ������˵�����ˣ�������һ������ֵ�����guest������admin��ز���,-_-!
  rabbitmqctl add_user hcadmin adminpwd
  rabbitmqctl set_user_tags hcadmin administrator

10.nginx��װ(184,185)
  ����ʹ�õ�nginx�汾:nginx-1.9.15.tar.gz
  ��װ������鿴install_step\thirdpart\nginx\linux\1.install_step.cc
	����������һ����Ҫע��ĵط�,�����ʱ��һ��Ҫ����tcp��ģ�飬�������õ�1.9.x�Ѿ�Ƕ����tcpģ���ˡ�
	���Ǳ����ʱ����Ҫ����--with-stream����,Ҳ���Ǳ������
	./configure --prefix=/usr/local/nginx-1.9.15 --conf-path=/usr/local/nginx-1.9.15/nginx.conf --with-stream

11.ngxin��tcp����(184,185)
  ��Ϊ��ָ���Ķ�ȡ�������ļ��ǰ�װĿ¼�µ�nginx.conf�������޸��������nginx.conf
    ������ΪͬĿ¼��:3.cluster_nginx.conf�еĴ��롣

12.184��185����װ��keepalived
      yum install -y keepalived

13.184��185���������ļ�
      vim /etc/keepalived/ng_down.sh
      �����������
      #!/bin/bash
      pkill keepalived
      # chmod +x /etc/keepalived/ng_down.sh #��Ȩ��ִ��Ȩ��

14.�޸�184�µ�/etc/keepalived/keepalived.conf
     ˵���޸ģ���ʵ���Ƚ�ԭ���ı��ݣ�Ȼ�����½�һ�������������ļ�
     ����:mv /etc/keepalived/keepalived.conf /etc/keepalived/keepalived.conf.bk
     ����:vim /etc/keepalived/keepalived.conf
	184����ͬĿ¼�µ�3.cluster_nginx_keepalived_184.conf
	185����ͬĿ¼�µ�3.cluster_nginx_keepalived_185.conf

15.keepalived��ͣ
     service keepalived start
     service keepalived stop
     service keepalived restart