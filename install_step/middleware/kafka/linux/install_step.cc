官网下载http://kafka.apache.org/downloads.html
搭建环境:虚拟机3,192.168.1.84

1.官网下载最新版本的压缩包
  我这次用的是
  kafka_2.11-0.10.0.0.tgz

2.解压缩文件
  不要被后缀吓到，其实是tar.gz的缩写，可以直接有tar命令解压

3.创建新的文件夹放置配置文件
  cd /usr/local/kafka_2.11-0.10.0.0
  mkdir mycfg

4.复制配置文件到新的文件夹里面
  cd /usr/local/kafka_2.11-0.10.0.0/config
  cp server.properties ../mycfg/server-1.properties

5.编辑复制过去的文件，修改以下内容
  #服务器id,集群的时候标识
  broker.id=1
  #日志文件的存放地址
  log.dirs=/usr/local/kafka_2.11-0.10.0.0/kafka-logs-1
  #修改服务端口号
  listeners=PLAINTEXT://:9093
  #修改zookeeper的连接地址不要修改

6.启动节点
  kafka根目录启动
  bin/zookeeper-server-start.sh config/zookeeper.properties
  JMX_PORT=9997 bin/kafka-server-start.sh mycfg/server-1.properties &

8.停止服务
  虽然有停止脚本，不过不知道怎么用，先直接kill

==================集群配置==================
1.先停止原来的kafka服务

2.到config目录下复制一个新的文件到mycfg目录下
  cp server.properties ../mycfg/server-2.properties

3.修改复制过去的配置文件
  broker.id用2，端口用9094，修改一下日志文件路径
  其他参照上面步骤进行修改即可

4.将原来的配置文件原封不动复制过来
  cp server.properties ../mycfg
  也把配置文件修改一下,这个作为master，连server.id都不用改的

5.集群启动
  如果zookeeper没启动要启动zookeeper
  bin/zookeeper-server-start.sh config/zookeeper.properties

  先启动kafka1
  JMX_PORT=9997 bin/kafka-server-start.sh mycfg/server-1.properties &
  JMX_PORT=9998 bin/kafka-server-start.sh mycfg/server-2.properties &
  这个相当于master
  JMX_PORT=9999 bin/kafka-server-start.sh mycfg/server.properties &

====================测试====================
1.创建一个主题
  bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

2.查看kafka下都有什么主题
  bin/kafka-topics.sh --list --zookeeper localhost:2181

3.开启一个客户端监听主题test的消息
  bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning

4.新开一个控制台，并且给kafka发送消息
  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

5.在发送消息控制台输入信息，在消息监听控制台看消息有没有顺利过来