===============================
redis高可用配置
===============================

从redis 3.0开始就支持集群，有集群的地方就有可能涉及高可用，就是故障切换主从

1.我们要实现这个起码需要两个redis服务在运行

2.注意单个redis配置需要注意的地方，bind里面要写成bind 0.0.0.0

3.首先说明一下关系
  我本地搭建了虚拟机,ip是192.168.1.78
  在虚拟机上开启两个redis服务,端口分别为6379和6380
  master:6379
  slave:6380

4.copy两套配置文件到/redis目录下,如果没有这个目录则先提前创建
  cd redis解压的根目录
  cp redis.conf /redis/redis_6379_master.conf
  cp redis.conf /redis/redis_6380_slave.conf
  cp sentinel.conf /redis/sentinel.conf

5.到/redis目录下修改刚复制过去的几个配置文件
  redis_6379_master.conf不用修改
  redis_6380_slave.conf
	将端口修改为6380
	slaveof 192.168.1.78 6379

6.修改sentinel.conf
          修改为
  #数据存放的地址
  dir /tmp --> dir /redis
  #指定需要监控的redis服务,至少需要[1]个sentinel确认redis失效，那么就确认该redis失效
  sentinel monitor ...... --> sentinel monitor mymaster 127.0.0.1 6379 1
  #sentinel判断从redis服务上丢失连接30000毫秒，此sentinel确认redis失效,主观下线
  sentinel down-after-milliseconds ...... --> sentinel down-after-milliseconds mymaster 30000
  #sentinel进行故障迁移的超时时间
  sentinel failover-timeout ...... --> sentinel failover-timeout mymaster 180000
  #进行故障迁移的时候一次允许有几个从redis与新的主redis进行数据同步
  sentinel parallel-syncs ...... --> sentinel parallel-syncs mymaster 1

7.启动测试
  任意目录执行
  redis-server /redis/redis_6379_master.conf &
  redis-server /redis/redis_6380_slave.conf &
  redis-sentinel sentinel.conf &
-----------------------------------------------------------------------------------------------
以上配置实现了redis高可用的配置，但是说怎么验证？这个接下来想一想办法.to be continue