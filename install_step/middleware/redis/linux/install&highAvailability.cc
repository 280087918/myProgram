--------------------------------------
单个redis配置
--------------------------------------
1.将安装包redis-3.0.7.tar.gz上传到服务器的/usr/local下

2.解压缩并编译redis包
  cd /usr/local/redis-3.0.7/src
  make
  make install

3.为了比较好管理，将配置文件都复制到系统的一个地方
  cd /
  mkdir redis
  cd /usr/local/redis-3.0.7/
  cp redis.conf /redis/redis_6379.conf

4.到配置文件目录下执行redis服务
  redis-server redis_6379.conf &

--------------------------------------
redis高可用配置(续着上面的步骤)
--------------------------------------
5.多复制一份配置文件到redis文件夹下
  cd /usr/local/redis-3.0.7/
  cp redis.conf /redis/redis_6380.conf

6.redis_6379.conf我们这里是作为master，不必修改配置
   --因为一开始我还不想涉及密码

7.编辑redis_6380.conf
  修改端口，还有指定他是6379的slave
  vi redis_6380.conf
  port 6379 --> port 6380
  新增:slaveof 192.168.1.84 6379

8.复制哨兵文件并修改
  cd /usr/local/redis-3.0.7/
  cp sentinel.conf /redis/sentinel.conf
  cd /redis
  vi sentinel.conf
  就将数据存放地址修改一下
  dir /tmp --> dir /redis
  #指定需要监控的redis服务,至少需要[1]个sentinel确认redis失效，那么就确认该redis失效
  sentinel monitor ...... --> sentinel monitor mymaster 192.168.1.84 6379 1
  #sentinel判断从redis服务上丢失连接30000毫秒，此sentinel确认redis失效,主观下线
  sentinel down-after-milliseconds ...... --> sentinel down-after-milliseconds mymaster 30000
  #sentinel进行故障迁移的超时时间
  sentinel failover-timeout ...... --> sentinel failover-timeout mymaster 180000
  #进行故障迁移的时候一次允许有几个从redis与新的主redis进行数据同步
  sentinel parallel-syncs ...... --> sentinel parallel-syncs mymaster 1

9.测试
  因为刚才6379 master已经启动了。所以现在只需要启动6380的slave和哨兵程序sentinel就可以了
  redis-server redis_6380.conf &
  redis-sentinel sentinel.conf &

--------------------------------------
redis高可用配置+密码配置(续着上面的步骤)
--------------------------------------
10.修改两个redis配置文件的两个密码相关的参数
   masterauth "zhcpwd"
   requirepass "zhcpwd"

11.修改哨兵文件sentinel.conf的一个密码相关的参数
   sentinel auth-pass mymaster zhcpwd

--------------------------------------
题外话
--------------------------------------
12.redis数据持久化
   一般都是使用rdb和aof的方式持久化，这里使用aof
   redis配置文件里面(随便选择一个)
	appendfsync always --每次收到命令就写入磁盘
	appendfsync everysec --每秒执行一次，默认开启这个
	appendfsync no --依赖os，传说性能最好