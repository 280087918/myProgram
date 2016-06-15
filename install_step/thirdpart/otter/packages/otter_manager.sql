CREATE DATABASE /*!32312 IF NOT EXISTS*/ `otter` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `otter`;

create table `alarm_rule` (
  `id` BIGINT(20) UNSIGNED not null auto_increment,
  `monitor_name` VARCHAR(1024) default null,
  `receiver_key` VARCHAR(1024) default null,
  `status` VARCHAR(32) default null,
  `pipeline_id` BIGINT(20) not null,
  `description` VARCHAR(256) default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  `match_value` VARCHAR(1024) default null,
  `parameters` TEXT default null,
  primary key (`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `autokeeper_cluster` (
  `id` BIGINT(20) not null auto_increment,
  `cluster_name` VARCHAR(200) not null,
  `server_list` VARCHAR(1024) not null,
  `description` VARCHAR(200) default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `canal` (
  `id` BIGINT(20) UNSIGNED not null auto_increment,
  `name` VARCHAR(200) default null,
  `description` VARCHAR(200) default null,
  `parameters` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `canalunique` (`name`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `channel` (
  `id` BIGINT(20) not null auto_increment,
  `name` VARCHAR(200) not null,
  `description` VARCHAR(200) default null,
  `parameters` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `channelunique` (`name`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `column_pair` (
  `id` BIGINT(20) not null auto_increment,
  `source_column` VARCHAR(200) default null,
  `target_column` VARCHAR(200) default null,
  `data_media_pair_id` BIGINT(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_data_media_pair_id` (`data_media_pair_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `column_pair_group` (
  `id` BIGINT(20) not null auto_increment,
  `data_media_pair_id` BIGINT(20) not null,
  `column_pair_content` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_data_media_pair_id` (`data_media_pair_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `data_media` (
  `id` BIGINT(20) not null auto_increment,
  `name` VARCHAR(200) not null,
  `namespace` VARCHAR(200) not null,
  `properties` VARCHAR(1000) not null,
  `data_media_source_id` BIGINT(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `datamediaunique` (`name`,`namespace`,`data_media_source_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `data_media_pair` (
  `id` BIGINT(20) not null auto_increment,
  `pullweight` BIGINT(20) default null,
  `pushweight` BIGINT(20) default null,
  `resolver` TEXT default null,
  `filter` TEXT default null,
  `source_data_media_id` BIGINT(20) default null,
  `target_data_media_id` BIGINT(20) default null,
  `pipeline_id` BIGINT(20) not null,
  `column_pair_mode` VARCHAR(20) default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_pIPELINEid` (`pipeline_id`,`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `data_media_source` (
  `id` BIGINT(20) not null auto_increment,
  `name` VARCHAR(200) not null,
  `type` VARCHAR(20) not null,
  `properties` VARCHAR(1000) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `datamediasourceunique` (`name`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `delay_stat` (
  `id` BIGINT(20) not null auto_increment,
  `delay_time` INT(21) not null,
  `delay_number` BIGINT(20) not null,
  `pipeline_id` BIGINT(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_pIPELINEid_gMTmODIFIED_id` (`pipeline_id`,`gmt_modified`,`id`),
  key `IDX_pIPELINE_gMTcREATE` (`pipeline_id`,`gmt_create`),
  key `IDX_gMTcREATE_ID` (`gmt_create`,`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `log_record` (
  `id` BIGINT(20) not null auto_increment,
  `nid` VARCHAR(200) default null,
  `channel_id` VARCHAR(200) not null,
  `pipeline_id` VARCHAR(200) not null,
  `title` VARCHAR(1000) default null,
  `message` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `PIPELINE_ID_RECORD` (`pipeline_id`),
  key `LOGrECORD_PIPELINEiD` (`pipeline_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `node` (
  `id` BIGINT(20) not null auto_increment,
  `name` VARCHAR(200) not null,
  `ip` VARCHAR(200) not null,
  `port` BIGINT(20) not null,
  `description` VARCHAR(200) default null,
  `parameters` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `nodeunique` (`name`,`ip`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `pipeline` (
  `id` BIGINT(20) not null auto_increment,
  `name` VARCHAR(200) not null,
  `description` VARCHAR(200) default null,
  `parameters` TEXT default null,
  `channel_id` BIGINT(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `pipelineunique` (`name`,`channel_id`),
  key `IDX_cHANNELid` (`channel_id`,`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `pipeline_node_relation` (
  `id` BIGINT(20) not null auto_increment,
  `node_id` BIGINT(20) not null,
  `pipeline_id` BIGINT(20) not null,
  `location` VARCHAR(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_pIPELINEid` (`pipeline_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `system_parameter` (
  `id` BIGINT(20) UNSIGNED not null,
  `value` TEXT default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`)
) engine=iNNOdb default charset=UTF8;

create table `table_history_stat` (
  `id` BIGINT(20) UNSIGNED not null auto_increment,
  `file_size` BIGINT(20) default null,
  `file_count` BIGINT(20) default null,
  `insert_count` BIGINT(20) default null,
  `update_count` BIGINT(20) default null,
  `delete_count` BIGINT(20) default null,
  `data_media_pair_id` BIGINT(20) default null,
  `pipeline_id` BIGINT(20) default null,
  `start_time` TIMESTAMP not null default '0000-00-00 00:00:00',
  `end_time` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_data_media_pair_id_end_time` (`data_media_pair_id`,`end_time`),
  key `IDX_gMTcREATE_ID` (`gmt_create`,`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `table_stat` (
  `id` BIGINT(20) not null auto_increment,
  `file_size` BIGINT(20) not null,
  `file_count` BIGINT(20) not null,
  `insert_count` BIGINT(20) not null,
  `update_count` BIGINT(20) not null,
  `delete_count` BIGINT(20) not null,
  `data_media_pair_id` BIGINT(20) not null,
  `pipeline_id` BIGINT(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_pIPELINEid_dATAmEDIApAIRid` (`pipeline_id`,`data_media_pair_id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `throughput_stat` (
  `id` BIGINT(20) not null auto_increment,
  `type` VARCHAR(20) not null,
  `number` BIGINT(20) not null,
  `size` BIGINT(20) not null,
  `pipeline_id` BIGINT(20) not null,
  `start_time` TIMESTAMP not null default '0000-00-00 00:00:00',
  `end_time` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `IDX_pIPELINEid_tYPE_gMTcREATE_id` (`pipeline_id`,`type`,`gmt_create`,`id`),
  key `IDX_pIPELINEid_tYPE_eNDtIME_id` (`pipeline_id`,`type`,`end_time`,`id`),
  key `IDX_gMTcREATE_ID` (`gmt_create`,`id`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table `user` (
  `id` BIGINT(20) not null auto_increment,
  `username` VARCHAR(20) not null,
  `password` VARCHAR(20) not null,
  `authorizetype` VARCHAR(20) not null,
  `department` VARCHAR(20) not null,
  `realname` VARCHAR(20) not null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  unique key `userunique` (`username`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;

create table  `data_matrix` (
  `id` BIGINT(20) not null auto_increment,
  `group_key` VARCHAR(200) default null,
  `master` VARCHAR(200) default null,
  `slave` VARCHAR(200) default null,
  `description` VARCHAR(200) default null,
  `gmt_create` TIMESTAMP not null default '0000-00-00 00:00:00',
  `gmt_modified` TIMESTAMP not null default current_timestamp on update current_timestamp,
  primary key (`id`),
  key `groupkey` (`group_key`)
) engine=iNNOdb auto_increment=1 default charset=UTF8;


insert into user(ID,USERNAME,PASSWORD,AUTHORIZETYPE,DEPARTMENT,REALNAME,GMT_CREATE,GMT_MODIFIED) values(null,'admin','801fc357a5a74743894a','ADMIN','admin','admin',now(),now());
insert into user(ID,USERNAME,PASSWORD,AUTHORIZETYPE,DEPARTMENT,REALNAME,GMT_CREATE,GMT_MODIFIED) values(null,'guest','471e02a154a2121dc577','OPERATOR','guest','guest',now(),now());