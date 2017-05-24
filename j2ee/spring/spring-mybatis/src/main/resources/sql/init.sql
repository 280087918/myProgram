CREATE TABLE `car` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(128) DEFAULT NULL COMMENT '车辆品牌',
  `model` varchar(128) DEFAULT NULL COMMENT '车辆型号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';