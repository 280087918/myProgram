============================================
ʵ�鱳��:���������
ʵ��Ŀ��:ʵ��zookeeper��Ⱥ

ע��:zookeeper�ļ�Ⱥ����Ҫ���������㣬Ҫ��Ȼѡ�ٲ��������ڵ�

Ч��:
�����ڱ���������ip�����ü�Ⱥ
�����1:master(192.168.22.181)
�����2:node1(192.168.22.182)
�����3:node3(192.168.22.183)
============================================

1.��apache����������Ӧ�汾��zookeeper
  http://apache.fayea.com/zookeeper/zookeeper-3.4.8/zookeeper-3.4.8.tar.gz

2.��������������ֱ��wget�ͺ���
  �����ص���/usr/local/soft

3.��ѹ����/usr/localĿ¼
  tar -xzvf zookeeper-3.4.8.tar.gz -C /usr/local

4.��ģ�������ļ�����һ�ݳ��������ʽ�����ļ�
  cd /usr/local/zookeeper-3.4.8/conf
  cp zoo_sample.cfg zoo.cfg
  ��Ϊ��һ���Ҵ���ֱ��������master�����Զ˿��Ҷ�������
  ��Ϊ�ҵ�dataDir���������Ĭ�ϵ�Ŀ¼��
  ������zookeeperĿ¼�´���dataĿ¼������vi zoo.cfg
  �޸�dataDirΪ����
  dataDir=/usr/local/zookeeper-3.4.8/data

5.����zookeeper
  ��binĿ¼��ִ��
  ./zkServer.sh start

6.����
  ����binĿ¼�µĿͻ��˳���
  ./zkCli.sh
  a)���һ��Ŀ¼
    create /zhc hello
  b)�о�ĳ��·���µ�Ŀ¼
    ls /
  c)ɾ��Ŀ¼
    delete /zhc
  d)�˳�client�ͻ���
    quit

---------------
��Ⱥ����
---------------
7.������master(192.168.2.181)
  Ҳ���Ǹղŵ��Ǹ��ļ���
  ��ֹͣ������������master
  cd /usr/local/zookeeper-3.4.8/conf
  vi zoo.cfg
  ���ļ����������¼���
  2888�������뼯Ⱥ��leader������������Ϣ�Ķ˿�
  3888������ִ��ѡ�ٵķ���˿�
  #cluster config
  server.1=192.168.22.181:2888:3888
  server.2=192.168.22.182:2888:3888
  server.3=192.168.22.183:2888:3888

8.�޸�master��serverid
  ����zookeeper��dataDirĿ¼
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  ����myid�ļ�
  vim myid
  ����master��id
  1
  �����˳�
  
9.ͬ�������ڵ�Ҳ��һ����ֻ����myid��һ��
  ����server.2�Ľڵ�myid��Ϊ2

10.�����ڵ�zookeeper

11.�鿴��Ⱥ״̬
   zookeeper_root/bin/zkServer.sh status
   ���Կ�����ǰ�ڵ�ļ�Ⱥ״̬�����Կ��ǲ���leader

ע:
1.zookeeper��Ⱥ�������������л��ģ������������߿��õ�
  ���Էֱ�������������д��Ĳ��ԣ�����д��ӣ���Ҳ��ͬ��������