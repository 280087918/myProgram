#构建数据库
CREATE TABLE `car` (
	`id` varchar(32) primary key comment 'id',
	`model` varchar(256) COMMENT '型号',
	`brand` varchar(256) COMMENT '品牌',
	`market_date` datetime NOT NULL COMMENT '上市日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;