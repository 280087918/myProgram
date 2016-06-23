��������http://kafka.apache.org/downloads.html
�����:�����3,192.168.1.84

1.�����������°汾��ѹ����
  ������õ���
  kafka_2.11-0.10.0.0.tgz

2.��ѹ���ļ�
  ��Ҫ����׺�ŵ�����ʵ��tar.gz����д������ֱ����tar�����ѹ

3.�����µ��ļ��з��������ļ�
  cd /usr/local/kafka_2.11-0.10.0.0
  mkdir mycfg

4.���������ļ����µ��ļ�������
  cd /usr/local/kafka_2.11-0.10.0.0/config
  cp server.properties ../mycfg/server-1.properties

5.�༭���ƹ�ȥ���ļ����޸���������
  #������id,��Ⱥ��ʱ���ʶ
  broker.id=1
  #��־�ļ��Ĵ�ŵ�ַ
  log.dirs=/usr/local/kafka_2.11-0.10.0.0/kafka-logs-1
  #�޸ķ���˿ں�
  listeners=PLAINTEXT://:9093
  #�޸�zookeeper�����ӵ�ַ��Ҫ�޸�

6.�����ڵ�
  kafka��Ŀ¼����
  bin/zookeeper-server-start.sh config/zookeeper.properties
  JMX_PORT=9997 bin/kafka-server-start.sh mycfg/server-1.properties &

8.ֹͣ����
  ��Ȼ��ֹͣ�ű���������֪����ô�ã���ֱ��kill

==================��Ⱥ����==================
1.��ֹͣԭ����kafka����

2.��configĿ¼�¸���һ���µ��ļ���mycfgĿ¼��
  cp server.properties ../mycfg/server-2.properties

3.�޸ĸ��ƹ�ȥ�������ļ�
  broker.id��2���˿���9094���޸�һ����־�ļ�·��
  �����������沽������޸ļ���

4.��ԭ���������ļ�ԭ�ⲻ�����ƹ���
  cp server.properties ../mycfg
  Ҳ�������ļ��޸�һ��,�����Ϊmaster����server.id�����øĵ�

5.��Ⱥ����
  ���zookeeperû����Ҫ����zookeeper
  bin/zookeeper-server-start.sh config/zookeeper.properties

  ������kafka1
  JMX_PORT=9997 bin/kafka-server-start.sh mycfg/server-1.properties &
  JMX_PORT=9998 bin/kafka-server-start.sh mycfg/server-2.properties &
  ����൱��master
  JMX_PORT=9999 bin/kafka-server-start.sh mycfg/server.properties &

====================����====================
1.����һ������
  bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

2.�鿴kafka�¶���ʲô����
  bin/kafka-topics.sh --list --zookeeper localhost:2181

3.����һ���ͻ��˼�������test����Ϣ
  bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning

4.�¿�һ������̨�����Ҹ�kafka������Ϣ
  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

5.�ڷ�����Ϣ����̨������Ϣ������Ϣ��������̨����Ϣ��û��˳������