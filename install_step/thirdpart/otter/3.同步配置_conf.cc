1.����Ҫ����mysql��binlog
  �����ȷ������û�У���ô����navicat->F6������show binary logs;
	�����ʾYou are not using binary logging,��ô����û����binlog

2.����binlog
  vi /etc/my.ini
  �޸�:log_bin=mysql-126-binlog
  ���:binlog_format=ROW
  #ͬ������Ҫ���servier_id
  server_id=1
  �ǵ�����mysql
  ������ִ�в���(1)����


======================��ʼ����=================
����:
ԭ��:  jdbc:mysql://192.168.1.126:3306/otter_test_db
Ŀ��� jdbc:mysql://192.168.1.194:3306/otter_test_db

Դ���ݿ��:otter_test_db.car
Ŀ�����ݿ��:otter_test_db.scar

�������:
CREATE TABLE `car` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(128) DEFAULT NULL COMMENT '����Ʒ��',
  `model` varchar(128) DEFAULT NULL COMMENT '�����ͺ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='������';

194���棬Ҳ����Ŀ�������ı�ı���scar

1.�½�����Դ
  ��otter����̨��"���ù���"->"����Դ����"->"���"

  source����:
  ����Դ����:db126
  �û���:root
  ����:xxxxxx
  URL:jdbc:mysql://192.168.1.126:3306/otter_test_db
  ����:UTF8

  target����
  ����Դ����:db194
  �û���:root
  ����:xxxxxx
  URL:jdbc:mysql://192.168.1.194:3306/otter_test_db
  ����:UTF8

2.���canal
  otter����̨��"���ù���"->"canal����"->"���"
  canal����:myCanal
  ���ݿ��ַ:192.168.1.126:3306;
  ���ݿ��ʺ�:root
  ���ݿ�����:xxxxxx

3.�����ͬ���ı�
  otter����̨��"���ù���"->"���ݱ�����"->"���"
  
  126����
  schema name:otter_test_db  --ע�⣬�������Ϊ���ݿ�����������д
  table name:car
  ����Դ:db126

  194����
  schema name:otter_test_db  --ע�⣬�������Ϊ���ݿ�����������д
  table name:scar
  ����Դ:db194

4.���channel
  Channel Name:myChannel
  ����Ĭ��
  ����:my test channel

5.���pipeLine
  ��Channel�б����մ�����myChannel
  Pipeline����:myPipeline
  Select����:local
  Load����:local

6.���ӳ���ϵ
  ��pipeLine�б����մ��������pipeLine ->���
  Դ���ݱ�:car
  Ŀ�����ݱ�:scar

7.����
  ���ͬ����������Channel�б�ѡ��myChannel������
