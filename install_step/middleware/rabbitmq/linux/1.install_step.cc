----------------------------
rabbitMq server�İ�װ
http://www.cnblogs.com/shanyou/p/3902905.html
----------------------------
1.��װrabbitMq�������
  yum install -y xmlto rsync zip nc

2.��װsimplejson
  �ñ��صİ������߷�ǽ�����µİ�https://pypi.python.org/pypi/simplejson/
  ����ʹ�õ�ǰ���°汾:simplejson-3.8.2.tar.gz
  tar -xzvf simplejson-3.8.2.tar.gz -C /usr/local/
  cd /usr/local/simplejson-3.8.2
  python setup.py build
  python setup.py install

2.rabbitMqǰ�������ǰ�װerlang
  ������http://www.erlang.org/downloads/���ض�Ӧ�汾��erlang
  Ŀǰ���°�OTP 18.3 Source File
	����˵����http://erang.org/download/otp_src_18.3.tar.gz
  tar -xzvf otp_src_18.3.tar.gz -C /usr/local/
  cd /usr/local/otp_src_18.3
  ./configure
  make
  make install

3.����erlang��û����ȷ��װ
  ����mysql�������������";"��erlang����Ľ�������"."
  ����erlang:erl
  ����9+3.
      ������ǲ���12������һ�㶼���ˡ�
  �˳�erlang:halt().
      �ǵ�"."������

4.����rabbitmq server
   http://www.rabbitmq.com/releases/rabbitmq-server
   ����v3.6.2�汾��rabbitmq-server-3.6.2.tar.xz

5.���ѹ�����xz�İ���Ҫ��װxz��ѹ�����
    yum install -y xz

6.��ѹ��rabbitmq-server-3.6.2.tar.xz
  xz -d rabbitmq-server-3.6.2.tar.xz

  ����֮��ͻ���rabbitmq-server-3.6.2.tar
  ������ѹ�������
  tar -xvf rabbitmq-server-3.6.2.tar
  mv rabbitmq-server-3.6.2/ /usr/local

7.����rabbitmq client
   http://www.rabbitmq.com/releases/rabbitmq-java-client/
   ����v3.6.2�汾��rabbitmq-java-client-3.6.2.tar.gz�����ϴ���linux������
   tar -xzvf rabbitmq-java-client-3.6.2.tar.gz -C /usr/local

8.����rabbitmq serverĿ¼�������б���
   cd /usr/local/rabbitmq-server-3.6.2
   make install TARGET_DIR=/opt/mq/rabbitmq SBIN_DIR=/opt/mq/rabbitmq/sbin MAN_DIR=/opt/mq/rabbitmq/man

9.rabbitmq�������
  ����:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmq-server start &
  �鿴mq״̬:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmqctl status
  �ر�mq:/usr/local/rabbitmq-server-3.6.2/scripts/rabbitmqctl stop
  ����������·��̫���������޸�ϵͳ��������:
  vi /etc/profile
  �ļ�������
  #rabbitmq enviroment
  export PATH=$PATH:/usr/local/rabbitmq-server-3.6.2/scripts
  ������������������Ч:
  source /etc/profile
  
  ��ô�Ժ�Ϳ���ֱ��������rabbitmq-server start &
  �����������������ף�һ����ͻ��Զ��ر��ˣ����������������ʹ����һ���ں�̨����
  rabbitmq-server -detached

10.����rabbit����̨���
   mkdir /etc/rabbitmq
   cd /usr/local/rabbitmq-server-3.6.2/scripts
   rabbitmq-plugins enable rabbitmq_management

11.��¼rabbitmq����̨
   ˵��:guest/guest��localhost�û���¼�ģ������Զ�̵�¼�����޸������ļ�/etc/rabbitmq/rabbitmq.config
   ���û�оʹ���
   �������ļ���������
   [
    {rabbit, [{tcp_listeners, [5672]}, {loopback_users, ["guest"]}]}
   ].
   ����rabbitmq���������Ϳ�����guest��¼��

12.����ֻ��guest�û���û��administrator�û���ô�죬�������²��贴��
   rabbitmqctl add_user admin 123456
   rabbitmqctl set_user_tags admin administrator
   rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"