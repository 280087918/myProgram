��redis 3.0��ʼ��֧�ּ�Ⱥ���м�Ⱥ�ĵط����п����漰�߿��ã����ǹ����л�����

1.����Ҫʵ�����������Ҫ����redis����������

2.ע�ⵥ��redis������Ҫע��ĵط���bind����Ҫд��bind 0.0.0.0

3.�ҵ������������������ļ���ͬһ̨����������redis�������ܵõ�����redis����

4.Ϊ�˷��㣬��redis.conf���Ƶ�/redis�£��������ݣ�����sentinel.conf������redis��ѹ�������ĸ�Ŀ¼��
  cp redis.conf /redis/redis_6379_master.conf
  cp redis.conf /redis/redis_6380_slave.conf
  ע���޸������ļ��Ķ˿�
  cp sentinel.conf /redis

5.�ص��޸�sentinel.conf�ļ������������������redis����߿������õ�
  ������һ��һ�н��
  #����һ����������Ϊmymaster�ķ�����,�������������ip��127.0.0.1���˿���6379�������ʧЧ������Ҫ1��sentinelͬ��
  sentinel monitor mymaster 127.0.0.1 6379 1
  #sentinel��Ϊmymaster����������Ӧ�೤ʱ�����Ϊ�ǹҵ�.
  sentinel down-after-milliseconds mymaster 1000
