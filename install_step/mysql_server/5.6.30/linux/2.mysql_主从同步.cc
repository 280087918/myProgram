=================================
���ݿ�����ͬ��
��Ҫ��ͬ�����ݣ��պ�������ݷ���

������°汾mysql�����÷�ʽ
�ϵ����÷�ʽ�Ѿ����°汾����ʧЧ

����:
192.168.1.101 ����
192.168.1.84 �ӿ�

http://www.cnblogs.com/cchun/p/3712637.html
http://369369.blog.51cto.com/319630/790921/
=================================

1.ǰ���Ȱ�װ�����ݿ⣬�������ݿ�,�༭/etc/my.cnf���������ݿ�
  log_bin=mysql-v2-binlog
  server_id=1

  # db which going to synchronize(muti db can be seperate by comma)
  binlog_do_db=sync_test

  # db which is not going to synchronize(muti db can be seperate by comma)
  # binlog_ignore_db=mysql

  ������ɺ�mysql����ִ������鿴״̬�Ƿ�����

2.mysql�����д����û�(һ�㲻ʹ��root�˻���������ͬ��)
  CREATE USER 'syncuser'@'localhost' IDENTIFIED BY '123456';
  FLUSH PRIVILEGES;

3.������ִ�����syncuser�û��Դӿ���Ȩ
  ���ʹ��root�˻��ڷ������Ŀͻ���ִ��mysql -uroot -p
  grant replication slave  on *.* to 'syncuser'@'192.168.1.84' identified by '123456' with grant option;
  FLUSH PRIVILEGES;

4.�ӿ�������ⴴ�������˻��Ƿ��ܵ�¼
  mysql -u syncuser -h 192.168.1.101 -p
  ������ܣ���ô��Ū�����Է���Ϊֹ

5.��������ִ������鿴����״̬
  show master status;

  ���:
  +------------------------+----------+--------------+------------------+-------------------+
  | File                   | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
  +------------------------+----------+--------------+------------------+-------------------+
  | mysql-v2-binlog.000002 |      682 | sync_test    |                  |                   |
  +------------------------+----------+--------------+------------------+-------------------+

6.��ͬ��֮ǰ����������ı�
    ��һ�����ÿ��Բ�������������Ҫ������ʱ����Ҫ��������Ϊ�����ڵ���ͬ����ʱ������������
  ��������������
    flush tables with read lock;
    unlock tables;

7."�ӿ�"����server_id

8."�ӿ�"��¼mysql�ͻ��ˣ��ڴӿ��Ͻ���������Ĺ�ϵ
  master_log_file��master_log_pos��ֵ��Ӧstep4�Ľ��
  change master to master_host='192.168.1.101',master_user='syncuser',master_password='123456',master_log_file='mysql-v2-binlog.000002',master_log_pos=682;
  FLUSH PRIVILEGES;

9.�ڴӿ���ͣͬ��
  stop slave;
  start slave;

10.����6���Խ��ж�ε�������������֮ǰ�ӿ�Ҫ��ֹͣͬ��

11.�ӿ�鿴ͬ��״̬
  show slave status\G;



=====================================================
����:�ӿ�崻�һ��ʱ������»ָ�ͬ��

����:192.168.1.101
�ӿ�:192.168.1.84
=====================================================

---------
����ģ��
---------
1.�ر�192.168.1.84�����ݿ�

2.��192.168.1.101������뼸������

--------
������
--------
1.���������ݿ����
  -_-!û�¶�����Ϊ������binlogƫ���������Կ���֮�������Զ�ͬ����ȥ�ˡ�

2.��������ͬ�������
  a)�������
  b)������dump������
  c)�ڴӿ⵼������
  d)���ղ���8����ָ��binlog�Լ�Ҫͬ����λ��
  e)����salve
  f)������������

3.��ȫɾ��binlog�ļ�
  purge binary logs to 'binlog.000058';
  �Ƴ���binlog.000058֮�������binlog�ļ�