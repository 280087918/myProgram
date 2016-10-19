�ο�����
http://88250.b3log.org/rabbitmq-clustering-ha
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
  192.168.22.181 rabbitmq_node1
  192.168.22.182 rabbitmq_node2
  192.168.22.183 rabbitmq_node3

  ��ô�������node1��node2��node3��������Щ����

6.node2��node3�Ϸֱ�ִ�У���node2��node3���뼯Ⱥ
  rabbitmqctl stop_app
  rabbitmqctl join_cluster rabbit@rabbitmq_node1
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

10.ʹ��HAProxy�����ؾ�����
  ���ȸ��ؾ������ŵ�192.168.22.184���棬�����������haproxy�Ĳ���������184�ϲ�����
  yum install -y haproxy

11.�༭haproxy�����ļ�
   Ϊ�˱����������ԭ���������ļ��ȱ���һ��
   cd /etc/haproxy/
   cp haproxy.cfg haproxy.cfg.bak

   ���ԭ�����ļ�
   >haproxy.cfg
   vim haproxy.cfg
   listen rabbitmq_cluster 0.0.0.0:5672
   mode tcp
   balance     roundrobin
   server  rabbitmq_node1 192.168.22.181:5672 check inter 2000 rise 2 fall 3
   server  rabbitmq_node2 192.168.22.182:5672 check inter 2000 rise 2 fall 3
   server  rabbitmq_node3 192.168.22.183:5672 check inter 2000 rise 2 fall 3

12.������ػ�ģʽ����haproxy
   haproxy -f /etc/haproxy/haproxy.cfg -D

13.������ɣ�����ʹ�ÿͻ��˳�����в���
   �ͻ���ֱ������184��̨��������

========================������==============================
1.����ʱ��ʾ��Ҫ���������ڵ������������
      ������������������ĸ��ڵ㶼��һ���ģ�ǿ�г�ʼ��һ�±��ص�mq��������������
      ǿ�г�ʼ�����ڵ�
  rabbitmqctl force_boot
  rabbitmq-server -detached
