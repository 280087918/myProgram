============================================
ʵ�鱳��:���������
ʵ��Ŀ��:ʵ��zookeeper��Ⱥ

Ч��:
�����ڱ���������ip�����ü�Ⱥ
�����1:master(192.168.1.78)
�����2:node1(192.168.1.92)
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
  ��Ϊ�ҵ�dataDir���������Ĭ�ϵ�Ŀ¼������vi zoo.cfg
  �޸�dataDirΪ����
  dataDir=/usr/local/zookeeper-3.4.8/tmp/zookeeper

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
7.������master(192.168.1.78)
  Ҳ���Ǹղŵ��Ǹ��ļ���
  ��ֹͣ������������master
  cd /usr/local/zookeeper-3.4.8/conf
  vi zoo.cfg
  ���ļ����������¼���
  #clouster config
  server.1=192.168.1.78:2888:3888
  server.2=192.168.1.92:2888:3888

8.�޸�master��serverid
  ����zookeeper��dataDirĿ¼
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  ����myid�ļ�
  vim myid
  ����master��id
  1
  �����˳�
  
9.��zookeeper��Ŀ¼��������͵�����node��������Ϊ�ڵ������
  cd /usr/local/
  tar -zcvf zookeeper-3.4.8.tar.gz zookeeper-3.4.8.tar.gz
  scp zookeeper-3.4.8.tar.gz root@192.168.1.92:/usr/local

10.����master��zookeeper������Ҳ����192.168.1.78��zookeeper
  ��־��zookeeper��bin�ļ�����
  tail -f zookeeper.out
  ���������ˡ�˵�в����ڵ㣬������ȷ�ģ���Ϊ�ӽڵ㻹û����������

11.ssh���ӽڵ�ķ�����(192.168.1.92)�����ҵ���մ��������·��
  cd /usr/local
  ��ѹ���մ������İ�
  tar -xzvf zookeeper-3.4.8.tar.gz

12.�޸�myid
  cd /usr/local/zookeeper-3.4.8/tmp/zookeeper
  vim myid
  ����2,�����Ƴ�

13.�����ڵ�zookeeper

ע:
1.zookeeper��Ⱥ�������������л��ģ������������߿��õ�
  ���Էֱ�������������д��Ĳ��ԣ�����д��ӣ���Ҳ��ͬ��������