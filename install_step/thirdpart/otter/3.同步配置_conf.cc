1.首先要开启mysql的binlog
  如果不确定开了没有，那么就用navicat->F6，输入show binary logs;
	如果提示You are not using binary logging,那么就是没开启binlog

2.开启binlog
  vi /etc/my.ini
  修改:log_bin=mysql-126-binlog
  添加:binlog_format=ROW
  #同步主库要添加servier_id
  server_id=1
  记得重启mysql
  完了在执行步骤(1)看看


======================开始配置=================
资料:
原库:  jdbc:mysql://192.168.1.126:3306/otter_test_db
目标库 jdbc:mysql://192.168.1.194:3306/otter_test_db

源数据库表:otter_test_db.car
目标数据库表:otter_test_db.scar

建表语句:
CREATE TABLE `car` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(128) DEFAULT NULL COMMENT '车辆品牌',
  `model` varchar(128) DEFAULT NULL COMMENT '车辆型号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';

194上面，也就是目标哭上面的表改表明scar

1.新建数据源
  打开otter控制台，"配置管理"->"数据源配置"->"添加"

  source配置:
  数据源名字:db126
  用户名:root
  密码:xxxxxx
  URL:jdbc:mysql://192.168.1.126:3306/otter_test_db
  编码:UTF8

  target配置
  数据源名字:db194
  用户名:root
  密码:xxxxxx
  URL:jdbc:mysql://192.168.1.194:3306/otter_test_db
  编码:UTF8

2.添加canal
  otter控制台，"配置管理"->"canal配置"->"添加"
  canal名称:myCanal
  数据库地址:192.168.1.126:3306;
  数据库帐号:root
  数据库密码:xxxxxx

3.添加需同步的表
  otter控制台，"配置管理"->"数据表配置"->"添加"
  
  126配置
  schema name:otter_test_db  --注意，这个必须为数据库名，不能乱写
  table name:car
  数据源:db126

  194配置
  schema name:otter_test_db  --注意，这个必须为数据库名，不能乱写
  table name:scar
  数据源:db194

4.添加channel
  Channel Name:myChannel
  其他默认
  描述:my test channel

5.添加pipeLine
  在Channel列表点击刚创建的myChannel
  Pipeline名字:myPipeline
  Select机器:local
  Load机器:local

6.添加映射关系
  在pipeLine列表点击刚床紧挨年的pipeLine ->添加
  源数据表:car
  目标数据表:scar

7.启动
  点击同步管理，看到Channel列表选择myChannel的启动
