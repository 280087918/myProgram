===============================
redis�߿�������
===============================

��redis 3.0��ʼ��֧�ּ�Ⱥ���м�Ⱥ�ĵط����п����漰�߿��ã����ǹ����л�����

1.����Ҫʵ�����������Ҫ����redis����������

2.ע�ⵥ��redis������Ҫע��ĵط���bind����Ҫд��bind 0.0.0.0

3.����˵��һ�¹�ϵ
  �ұ��ش�������,ip��192.168.1.78
  ��������Ͽ�������redis����,�˿ڷֱ�Ϊ6379��6380
  master:6379
  slave:6380

4.copy���������ļ���/redisĿ¼��,���û�����Ŀ¼������ǰ����
  cd redis��ѹ�ĸ�Ŀ¼
  cp redis.conf /redis/redis_6379_master.conf
  cp redis.conf /redis/redis_6380_slave.conf
  cp sentinel.conf /redis/sentinel.conf

5.��/redisĿ¼���޸ĸո��ƹ�ȥ�ļ��������ļ�
  redis_6379_master.conf�����޸�
  redis_6380_slave.conf
	���˿��޸�Ϊ6380
	slaveof 192.168.1.78 6379

6.�޸�sentinel.conf
          �޸�Ϊ
  #���ݴ�ŵĵ�ַ
  dir /tmp --> dir /redis
  #ָ����Ҫ��ص�redis����,������Ҫ[1]��sentinelȷ��redisʧЧ����ô��ȷ�ϸ�redisʧЧ
  sentinel monitor ...... --> sentinel monitor mymaster 127.0.0.1 6379 1
  #sentinel�жϴ�redis�����϶�ʧ����30000���룬��sentinelȷ��redisʧЧ,��������
  sentinel down-after-milliseconds ...... --> sentinel down-after-milliseconds mymaster 30000
  #sentinel���й���Ǩ�Ƶĳ�ʱʱ��
  sentinel failover-timeout ...... --> sentinel failover-timeout mymaster 180000
  #���й���Ǩ�Ƶ�ʱ��һ�������м�����redis���µ���redis��������ͬ��
  sentinel parallel-syncs ...... --> sentinel parallel-syncs mymaster 1

7.��������
  ����Ŀ¼ִ��
  redis-server /redis/redis_6379_master.conf &
  redis-server /redis/redis_6380_slave.conf &
  redis-sentinel sentinel.conf &
-----------------------------------------------------------------------------------------------
��������ʵ����redis�߿��õ����ã�����˵��ô��֤�������������һ��취.to be continue