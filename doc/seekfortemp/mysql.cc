读写分离，性能优化，调优，索引策略

1.利用查询缓存
  大多Mysql数据库都开启查询缓存，如果相同的查询被执行了多次，就会走Mysql的缓存。如果使用Mysql内置函数就不会走缓存了
  所以杜绝在语句中使用CURDATE()等函数，使用变量去接收参数

2.模糊匹配走索引
  后置百分号是会走索引的，但是前置%并不会走索引。

3.外键加索引
  如果有一个查询语句，A表的b_id需要join B表的id,那么在A表的b_id上加上索引会加快搜索效率，因为没加之前，用执行计划看主表在执行语句的时候需要更多的rows去匹配

4.索引
  如果有上万条记录，建立索引之后，数据库可以精准定位到数据，而不用遍历上万条记录.
	比如，如果在user_name上建立了索引:select * from tbl_user where user_name='admin'，这样的查询会款很多
  普通索引:CREATE INDEX indexName ON mytable(username(length));
  唯一索引:与普通索引类似，不同的是列值必须唯一，但允许有空值
	CREATE UNIQUE INDEX indexName ON mytable(username(length)) 
  主键索引:一种特殊的唯一索引，他不允许有空值，一般是建表的时候语句里面指定的(PRIMARY KEY)
  组合索引:
	ALTER TABLE mytable ADD INDEX name_city_age (user_name(10),city,age);
	如果查询条件里面包含索引里面几个条件的话，效率要比单个索引要高。
	他分别创建了三组索引,user_name-city-age, user_name-city, user_name.唯独没有city-age的组合，因为Mysql的组合索引是最左前缀索引。
	所以查询条件里面必须包含这几组索引，否则将不走索引
  延伸:
	a)如果where里面用了索引的话，order by是不会走索引的
	b)如果需要多列进行排序，最好做成组合索引

5.使用limit 1加快搜索速度
  在搜索字段不是索引的前提下，如果知道该结果只会有一条，那么就在查询语句后面加上limit 1
  我实验过在3千多行记录里面，加上limit 1会比没有加的查询速度快1毫秒。
  原理是如果查询定位到一条数据之后，就不会再往后匹配数据了

6.不要select *
  养成良好的习惯，用什么字段就写什么字段。

7.索引
  对于少量的数据，没有合适的索引影响不是很大，但是，当随着数据量的增加，性能会急剧下降。
  ALTER TABLE `table_name` ADD INDEX index_name (`column`);

8.Not in 和<>不走索引

9.left join, right join ,inner join区别
  left join:以左表为基准，如果右表没有匹配上的数据就用null标识
  right join:以右表为基准，如果左表没有合适的数据就用null标识
  inner join:on两边的数据都存在的情况下才会出现。

10.Mysql数据库引擎
  MyISAM：不支持事务，用于只读程序提高性能
  InnoDB：支持ACID事务、行级锁、并发
  所以我个人理解可以将从库设置为MyISAM

11.乐观锁和悲观锁
  a)乐观锁:认为数据不会被更新，所以直接根据自定义的某个字段做比对，比如(version)，去进行数据更新。
  b)悲观锁:认为数据肯定会被其他线程更新，所以就悲观的把数据行锁定了。select xxxx for update

12.数据库锁
  Mysql的几种引擎:MyISAM, BDB, InnoDB
  mysql的锁机制比较简单，最显著的特点就是不同的存储引擎支持不同的锁机制。
  锁具体类型分为
  表锁:开销小，加锁快；不会出现死锁；锁定力度大，发生锁冲突概率高，并发度最低
  行锁:开销大，加锁慢；会出现死锁；锁定粒度小，发生锁冲突的概率低，并发度高
  页锁:开销和加锁速度介于表锁和行锁之间；会出现死锁；锁定粒度介于表锁和行锁之间，并发度一般

  表锁:所有的引擎都支持表锁
  页锁:只有BDB引擎才支持页锁
  行锁:只有InnoDB才支持行锁

  表锁实验(InnoDB):
  使用navicat的查询开启两个窗口(也就是开启了两个会话)
  会话A执行:lock TABLE tbl_student WRITE;
  会话B执行:select * from tbl_student;
  这时候可以看到会话B是在等待状态，没有返回结果的。除非在会话A中执行:unlock tables;这时候会话B就会马上返回查询结果
  一个值得注意的地方，当前会话如果锁定了某张表。那么对于这张表的别名本会话是不能访问的，会提示表已经被锁定
  比如会话A:
	LOCK TABLE tbl_score READ;
	select * from tbl_score t;
  这个虽然在一个会话里面，但是会提示表已经被锁定，不允许查询，这时候需要对表的别名分别锁定
	lock table tbl_score as t read,tbl_score as s read;

  MyISAM:MyISAM存储引擎只支持表锁
	对MyISAM表的读操作，不会阻塞其他用户对同一表的读请求，但会阻塞对同一表的写请求；
	对 MyISAM表的写操作，则会阻塞其他用户对同一表的读和写操作；
  MyISAM在执行查询语句（SELECT）前，会自动给涉及的所有表加读锁，在执行更新操作（UPDATE、DELETE、INSERT等）前，会自动给涉及的表加写锁，这个过程并不需要用户干预，因此，用户一般不需要直接用LOCK TABLE命令给MyISAM表显式加锁。

  
  InnnoDB:
  包含4个锁:共享锁, 排他锁, 意向共享锁, 意向排他锁
	共享锁:我读的时候你可以读，但是不可以写
	排他锁:我写的时候你不能读也不能写。
	意向共享锁IS:表示事务准备加入共享锁，也就是说一个数据加共享锁之前必须先取得表的IS锁
	意向排他锁IX:标识事务准备加入排他锁，也就是说一个数据行加入排他锁前必须先获得表的IX锁
  共享锁:select * from table_name where ...... lock in share mode;
  排他锁:select * from table_name where ...... for update
  共享锁一般是用来确认某行记录是否存在，确保没有人对这行数据进行操作，如果他本身需要对数据进行操作的话，很有可能会造成死锁, 因为有可能其他线程在执行数据的操作，只不过在等待当前这个共享锁。所以一般这种会用for update的排他锁

  InnoDB行锁:
	InnoDB行锁是通过索引项实现加锁，如果没有表没有索引的话，那么InnoDB将对表中所有的数据加锁，实际效果跟表锁无异。
	行锁分三种:
		Record Lock:对索引项加锁，即锁定一条记录
		Gap Lock:对索引项之间的间隙加锁，就是索引项前面的记录和索引项后面的记录之间加锁，但是不包含索引本身。
		Next-key Lock:锁定一个范围，包含记录本身，就是涵盖了Record Lock和Gap Lock
	比如有这么一张表:id(primary key), age(Key,也就是普通的索引),里面有三条记录，age分别是3,6,9
	那么事务执行如下语句select * from table_name where age=6 for update;那么3-6之间和6-9之间分别会上锁。解决了一区间段的数据幻读问题。
	如果查询区间不在数据范围内，比如说比9还大的话，那么加锁范围是9后面的范围进行加锁。

  如何避免死锁:
	MyISAM是表级锁，所以不会造成死锁。其他线程只能等待。
	a)如果程序并发访问多张表，那么可以约定以相同的顺序进行表的访问。
	b)同一个事务中，尽可能做到一次性锁定所有的资源。
	c)对于非常容易产生死锁的部分，可以考虑粗粒度锁，比如表级锁。
  
13.mybatis的缓存
   mybatis一级缓存是基于session的缓存，不过在与spring集成的时候一级缓存并不起作用。
   mybatis二级缓存是基于global caching的缓存，超出了session范围，是所有session共享的。
   mybatis二级缓存的开启只需要在mybatis的配置文件开启，在settings中加入如下配置
	<setting name="cacheEnabled" value="true" />
   有一点值得注意的是mybatis的二级缓存是基于nameSpace的。

14.mybatis中#与$的区别s
   #{param}会产生PreparedStatment，传入参数的时候不会重新产生sql语句，安全性高。
   ${param}会将里面的参数当成字符串，会有一定的sql注入问题.不过他也有一定的使用场景，好比order by的时候可以用来接收字符串

15.mysql查询缓存
   以下情况mysql将不走查询缓存
   a)使用了mysql的函数:CURDATE(), RAND(), GET_LOCK()等。
   b)使用了数据库锁的语句，比如
	select ...... in share mode;
	select ...... for update
   c)导出数据语句:select .... into
   d)事务隔离级别是Serializable的情况
   e)临时表的查询

16.mybatis的resultType和resultMap
   mybatis查询结果返回的是一个map，根据对象属性进行赋值
   resultMap提前在xml中定义好字段跟名称的映射关系即可
   resultType需指定vo的详细类路径。

17.
  
