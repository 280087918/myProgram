1.�����������־��ʾ�Ҳ���binlog��ʶ�ļ������������Ϣ����
  at com.alibaba.otter.canal.parse.inbound.mysql.dbsync.DirectLogFetcher.fetch(DirectLogFetcher.java:89)
  ��ô���п�����binlog֮ǰ��ɾ�����ˣ���ȷʵ����ô�ɵġ�
  otter��ͬ��ָ��洢��zookeeper����ġ�
  �������
  1)ֹͣotterͬ��
  2)zookeeperʹ��client��¼������ls /������û��otter����Ϣ
    �еĻ���ɾ�� rmr /otter
  3)����otterͬ��

2.mysqlִ��һ��ʱ��֮��binlog����,ɾ��binlog
  ��mysql�ͻ���ִ�У�purge binary logs to 'mysql-126-binlog.000028';
  ����mysql-126-binlog.000028�����һ��binlog��־
  ������otter��otter�ڵ㲻ֹͣ�������ִ���������