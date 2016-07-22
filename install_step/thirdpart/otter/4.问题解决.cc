1.如果启动后，日志显示找不到binlog初识文件，具体错误信息如下
  at com.alibaba.otter.canal.parse.inbound.mysql.dbsync.DirectLogFetcher.fetch(DirectLogFetcher.java:89)
  那么就有可能是binlog之前被删除过了，我确实是那么干的。
  otter的同步指针存储在zookeeper里面的。
  解决方法
  1)停止otter同步
  2)zookeeper使用client登录，并且ls /，看有没有otter的信息
    有的话就删除 rmr /otter
  3)启动otter同步

2.mysql执行一段时间之后binlog过大,删除binlog
  在mysql客户端执行：purge binary logs to 'mysql-126-binlog.000028';
  其中mysql-126-binlog.000028是最后一个binlog日志
  可以在otter跟otter节点不停止的情况下执行这个操作