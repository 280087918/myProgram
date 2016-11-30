/** 用户表 **/
CREATE TABLE `tbl_user` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) DEFAULT NULL,
  `cn_name` varchar(32) DEFAULT NULL,
  `password` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** 任务主表 **/
CREATE TABLE `tbl_task` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '任务id',
  `create_date` datetime DEFAULT NULL COMMENT '任务创建时间',
  `begin_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `total_date_count` int(11) DEFAULT NULL COMMENT '总计天数',
  `already_date_count` int(11) DEFAULT NULL COMMENT '已计天数',
  `per_date_count` int(11) DEFAULT NULL COMMENT '每天可记录次数',
  `create_user` varchar(32) NOT NULL DEFAULT '' COMMENT '创建用户',
  `delete_flag` int(11) DEFAULT '0' COMMENT '是否已删除，1：已删除，0:未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务主表';

/** 签到明细 **/
CREATE TABLE `tbl_task_detail` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '任务id',
  `opt_time` datetime DEFAULT NULL COMMENT '操作时间',
  `is_sign` int(11) DEFAULT NULL COMMENT '是否签到，1：签到，0：清除',
  `task_id` varchar(32) NOT NULL DEFAULT '' COMMENT '冗余字段，任务主表id',
  `sign_user` varchar(32) NOT NULL DEFAULT '' COMMENT '签到用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到明细表';

/** 数据初始化 **/
insert into `tbl_user`(`id`, `name`, `cn_name`, `password`) values('d4fbe05297844d87875d72cc8b88a64f', 'zhanghc', '号子', '6xQdFxPQHNi/mL4J4p/x1g==');

insert into `tbl_task`(`id`,`create_date`,`begin_date`,`end_date`,`total_date_count`,`already_date_count`,`per_date_count`,`create_user`)
	values('d06375d6cf194127ac9c7d553a556ced','2016-11-30 00:00:00','2016-11-30 00:00:00','2016-12-05 00:00:00',5,0,6,'zhanghc');

insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('f363888a66c54aaba37bbf5b66360617','2016-11-30 08:00:00',1,'d06375d6cf194127ac9c7d553a556ced','zhanghc');
insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('1c887fc75fe747de85c1cfddfc2e3360','2016-11-30 10:01:00',0,'d06375d6cf194127ac9c7d553a556ced','zhanghc');
insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('8b6b8dcb3e6c47ff970772f163a3b72e','2016-11-30 14:10:00',1,'d06375d6cf194127ac9c7d553a556ced','zhanghc');
insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('ae72624c45bd43ea94611abfbda4968a','2016-11-30 16:05:00',0,'d06375d6cf194127ac9c7d553a556ced','zhanghc');
insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('8dc09112eb944f429b8ebbc161473051','2016-11-30 18:00:00',1,'d06375d6cf194127ac9c7d553a556ced','zhanghc');
insert into `tbl_task_detail`(`id`,`opt_time`,`is_sign`,`task_id`,`sign_user`)
	values('7b803916c3af4e408386dda62ee0779d','2016-11-30 20:00:00',1,'d06375d6cf194127ac9c7d553a556ced','zhanghc');