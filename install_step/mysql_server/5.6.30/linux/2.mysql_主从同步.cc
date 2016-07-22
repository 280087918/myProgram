=================================
数据库主从同步
主要是同步数据，日后可作数据分离

这个是新版本mysql的配置方式
老的配置方式已经在新版本中已失效

环境:
192.168.1.101 主库
192.168.1.84 从库

http://www.cnblogs.com/cchun/p/3712637.html
http://369369.blog.51cto.com/319630/790921/
=================================

1.前提先安装好数据库，创建数据库,编辑/etc/my.cnf，重启数据库
  log_bin=mysql-v2-binlog
  server_id=1

  # db which going to synchronize(muti db can be seperate by comma)
  binlog_do_db=sync_test

  # db which is not going to synchronize(muti db can be seperate by comma)
  # binlog_ignore_db=mysql

  配置完成后，mysql主库执行命令查看状态是否正常

2.mysql命令行创建用户(一般不使用root账户进行数据同步)
  CREATE USER 'syncuser'@'localhost' IDENTIFIED BY '123456';
  FLUSH PRIVILEGES;

3.主库上执行命令，syncuser用户对从库授权
  这个使用root账户在服务器的客户端执行mysql -uroot -p
  grant replication slave  on *.* to 'syncuser'@'192.168.1.84' identified by '123456' with grant option;
  FLUSH PRIVILEGES;

4.从库测试主库创建的新账户是否能登录
  mysql -u syncuser -h 192.168.1.101 -p
  如果不能，那么就弄到可以访问为止

5.在主库上执行命令，查看主库状态
  show master status;

  结果:
  +------------------------+----------+--------------+------------------+-------------------+
  | File                   | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
  +------------------------+----------+--------------+------------------+-------------------+
  | mysql-v2-binlog.000002 |      682 | sync_test    |                  |                   |
  +------------------------+----------+--------------+------------------+-------------------+

6.在同步之前先锁定主库的表
    第一次设置可以不锁定，后面需要调整的时候需要锁定。因为担心在调整同步的时候有数据流入
  主库锁表，解锁表
    flush tables with read lock;
    unlock tables;

7."从库"配置server_id

8."从库"登录mysql客户端，在从库上建立与主库的关系
  master_log_file和master_log_pos的值对应step4的结果
  change master to master_host='192.168.1.101',master_user='syncuser',master_password='123456',master_log_file='mysql-v2-binlog.000002',master_log_pos=682;
  FLUSH PRIVILEGES;

9.在从库起停同步
  stop slave;
  start slave;

10.步骤6可以进行多次调整，不过调整之前从库要先停止同步

11.从库查看同步状态
  show slave status\G;



=====================================================
进阶:从库宕机一段时间后，重新恢复同步

主库:192.168.1.101
从库:192.168.1.84
=====================================================

---------
场景模拟
---------
1.关闭192.168.1.84的数据库

2.往192.168.1.101上面插入几条数据

--------
问题解决
--------
1.开启从数据库服务
  -_-!没事儿，因为读的是binlog偏移量，所以开启之后数据自动同步过去了。

2.遇到不能同步的情况
  a)锁主库表
  b)在主库dump出数据
  c)在从库导入数据
  d)参照步骤8重新指定binlog以及要同步的位置
  e)开启salve
  f)解除主库表锁定

3.安全删除binlog文件
  purge binary logs to 'binlog.000058';
  移除除binlog.000058之外的所有binlog文件