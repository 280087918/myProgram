从redis 3.0开始就支持集群，有集群的地方就有可能涉及高可用，就是故障切换主从

1.我们要实现这个起码需要两个redis服务在运行

2.注意单个redis配置需要注意的地方，bind里面要写成bind 0.0.0.0

3.我的设想是以两个配置文件在同一台机器上启动redis，这样能得到两个redis服务

4.为了方便，将redis.conf复制到/redis下，复制两份，还有sentinel.conf，都在redis解压缩出来的根目录下
  cp redis.conf /redis/redis_6379_master.conf
  cp redis.conf /redis/redis_6380_slave.conf
  注意修改两个文件的端口
  cp sentinel.conf /redis

5.重点修改sentinel.conf文件，这个是用来做两个redis服务高可用配置的
  下面来一行一行解读
  #监视一个服务器名为mymaster的服务器,这个主服务器的ip是127.0.0.1，端口是6379，这个主失效至少需要1个sentinel同意
  sentinel monitor mymaster 127.0.0.1 6379 1
  #sentinel认为mymaster的主服务不响应多长时间就认为是挂掉.
  sentinel down-after-milliseconds mymaster 1000
