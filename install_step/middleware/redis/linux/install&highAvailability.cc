--------------------------------------
����redis����
--------------------------------------
1.����װ��redis-3.0.7.tar.gz�ϴ�����������/usr/local��

2.��ѹ��������redis��
  cd /usr/local/redis-3.0.7/src
  make
  make install

3.Ϊ�˱ȽϺù����������ļ������Ƶ�ϵͳ��һ���ط�
  cd /
  mkdir redis
  cd /usr/local/redis-3.0.7/
  cp redis.conf /redis/redis_6379.conf

4.�������ļ�Ŀ¼��ִ��redis����
  redis-server redis_6379.conf &

--------------------------------------
redis�߿�������(��������Ĳ���)
--------------------------------------
5.�ิ��һ�������ļ���redis�ļ�����
  cd /usr/local/redis-3.0.7/
  cp redis.conf /redis/redis_6380.conf

6.redis_6379.conf������������Ϊmaster�������޸�����
   --��Ϊһ��ʼ�һ������漰����

7.�༭redis_6380.conf
  �޸Ķ˿ڣ�����ָ������6379��slave
  vi redis_6380.conf
  port 6379 --> port 6380
  ����:slaveof 192.168.1.84 6379

8.�����ڱ��ļ����޸�
  cd /usr/local/redis-3.0.7/
  cp sentinel.conf /redis/sentinel.conf
  cd /redis
  vi sentinel.conf
  �ͽ����ݴ�ŵ�ַ�޸�һ��
  dir /tmp --> dir /redis
  #ָ����Ҫ��ص�redis����,������Ҫ[1]��sentinelȷ��redisʧЧ����ô��ȷ�ϸ�redisʧЧ
  sentinel monitor ...... --> sentinel monitor mymaster 192.168.1.84 6379 1
  #sentinel�жϴ�redis�����϶�ʧ����30000���룬��sentinelȷ��redisʧЧ,��������
  sentinel down-after-milliseconds ...... --> sentinel down-after-milliseconds mymaster 30000
  #sentinel���й���Ǩ�Ƶĳ�ʱʱ��
  sentinel failover-timeout ...... --> sentinel failover-timeout mymaster 180000
  #���й���Ǩ�Ƶ�ʱ��һ�������м�����redis���µ���redis��������ͬ��
  sentinel parallel-syncs ...... --> sentinel parallel-syncs mymaster 1

9.����
  ��Ϊ�ղ�6379 master�Ѿ������ˡ���������ֻ��Ҫ����6380��slave���ڱ�����sentinel�Ϳ�����
  redis-server redis_6380.conf &
  redis-sentinel sentinel.conf &

--------------------------------------
redis�߿�������+��������(��������Ĳ���)
--------------------------------------
10.�޸�����redis�����ļ�������������صĲ���
   masterauth "zhcpwd"
   requirepass "zhcpwd"

11.�޸��ڱ��ļ�sentinel.conf��һ��������صĲ���
   sentinel auth-pass mymaster zhcpwd

--------------------------------------
���⻰
--------------------------------------
12.redis���ݳ־û�
   һ�㶼��ʹ��rdb��aof�ķ�ʽ�־û�������ʹ��aof
   redis�����ļ�����(���ѡ��һ��)
	appendfsync always --ÿ���յ������д�����
	appendfsync everysec --ÿ��ִ��һ�Σ�Ĭ�Ͽ������
	appendfsync no --����os����˵�������