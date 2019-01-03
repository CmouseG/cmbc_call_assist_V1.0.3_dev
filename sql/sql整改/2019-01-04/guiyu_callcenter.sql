use guiyu_callcenter;

/* 修改line_info字段长度*/
ALTER TABLE line_info MODIFY COLUMN createt_by INT(8);
ALTER TABLE line_info MODIFY COLUMN update_by INT(8);
ALTER TABLE line_info MODIFY COLUMN customer_id INT(8);
ALTER TABLE line_info MODIFY COLUMN caller_num VARCHAR(30);


/* 增加主键 */
ALTER TABLE line_count ADD COLUMN id INT  NOT NULL  AUTO_INCREMENT PRIMARY KEY  FIRST;

ALTER TABLE line_config ADD COLUMN id INT  NOT NULL  AUTO_INCREMENT PRIMARY KEY  FIRST;
 
ALTER TABLE fs_bind ADD COLUMN id INT  NOT NULL  AUTO_INCREMENT PRIMARY KEY  FIRST; 

ALTER TABLE error_match ADD COLUMN id INT  NOT NULL  AUTO_INCREMENT PRIMARY KEY  FIRST; 


/*创建call_out_plan临时表*/


DROP TABLE IF EXISTS `call_out_plan_tmp`;

CREATE TABLE `call_out_plan_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` VARCHAR(50) NOT NULL DEFAULT '',
  `phone_num` VARCHAR(30) NOT NULL DEFAULT '',
  `customer_id` VARCHAR(50) NOT NULL DEFAULT '',
  `temp_id` VARCHAR(30) NOT NULL DEFAULT '',
  `line_id` INT(11) NOT NULL,
  `serverId` VARCHAR(30) DEFAULT NULL,
  `agent_id` VARCHAR(30) DEFAULT NULL,
  `agent_answer_time` DATETIME DEFAULT NULL,
  `agent_channel_uuid` VARCHAR(40) DEFAULT NULL,
  `agent_group_id` VARCHAR(30) DEFAULT NULL,
  `agent_start_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `call_start_time` DATETIME DEFAULT NULL,
  `hangup_time` DATETIME DEFAULT NULL,
  `answer_time` DATETIME DEFAULT NULL,
  `duration` INT(11) DEFAULT NULL,
  `bill_sec` INT(11) DEFAULT NULL,
  `call_direction` INT(2) DEFAULT NULL,
  `call_state` INT(2) DEFAULT NULL,
  `hangup_direction` INT(2) DEFAULT NULL,
  `accurate_intent` VARCHAR(20) DEFAULT NULL,
  `reason` VARCHAR(255) DEFAULT NULL,
  `hangup_code` VARCHAR(10) DEFAULT NULL,
  `originate_cmd` VARCHAR(500) DEFAULT NULL,
  `remarks` VARCHAR(255) DEFAULT NULL,
  `has_tts` TINYINT(1) DEFAULT NULL,
  `ai_id` VARCHAR(50) DEFAULT NULL,
  `freason` INT(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` INT(2) NOT NULL DEFAULT '0',
  `isread` INT(2) NOT NULL DEFAULT '0',
  `org_code` VARCHAR(50) DEFAULT NULL,
  `batch_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `call_out_plan_0_tmp`;

CREATE TABLE `call_out_plan_0_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` VARCHAR(50) NOT NULL DEFAULT '',
  `phone_num` VARCHAR(30) NOT NULL DEFAULT '',
  `customer_id` VARCHAR(50) NOT NULL DEFAULT '',
  `temp_id` VARCHAR(30) NOT NULL DEFAULT '',
  `line_id` INT(11) NOT NULL,
  `serverId` VARCHAR(30) DEFAULT NULL,
  `agent_id` VARCHAR(30) DEFAULT NULL,
  `agent_answer_time` DATETIME DEFAULT NULL,
  `agent_channel_uuid` VARCHAR(40) DEFAULT NULL,
  `agent_group_id` VARCHAR(30) DEFAULT NULL,
  `agent_start_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `call_start_time` DATETIME DEFAULT NULL,
  `hangup_time` DATETIME DEFAULT NULL,
  `answer_time` DATETIME DEFAULT NULL,
  `duration` INT(11) DEFAULT NULL,
  `bill_sec` INT(11) DEFAULT NULL,
  `call_direction` INT(2) DEFAULT NULL,
  `call_state` INT(2) DEFAULT NULL,
  `hangup_direction` INT(2) DEFAULT NULL,
  `accurate_intent` VARCHAR(20) DEFAULT NULL,
  `reason` VARCHAR(255) DEFAULT NULL,
  `hangup_code` VARCHAR(10) DEFAULT NULL,
  `originate_cmd` VARCHAR(500) DEFAULT NULL,
  `remarks` VARCHAR(255) DEFAULT NULL,
  `has_tts` TINYINT(1) DEFAULT NULL,
  `ai_id` VARCHAR(50) DEFAULT NULL,
  `freason` INT(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` INT(2) NOT NULL DEFAULT '0',
  `isread` INT(2) NOT NULL DEFAULT '0',
  `org_code` VARCHAR(50) DEFAULT NULL,
  `batch_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `call_out_plan_1_tmp`;

CREATE TABLE `call_out_plan_1_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` VARCHAR(50) NOT NULL DEFAULT '',
  `phone_num` VARCHAR(30) NOT NULL DEFAULT '',
  `customer_id` VARCHAR(50) NOT NULL DEFAULT '',
  `temp_id` VARCHAR(30) NOT NULL DEFAULT '',
  `line_id` INT(11) NOT NULL,
  `serverId` VARCHAR(30) DEFAULT NULL,
  `agent_id` VARCHAR(30) DEFAULT NULL,
  `agent_answer_time` DATETIME DEFAULT NULL,
  `agent_channel_uuid` VARCHAR(40) DEFAULT NULL,
  `agent_group_id` VARCHAR(30) DEFAULT NULL,
  `agent_start_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `call_start_time` DATETIME DEFAULT NULL,
  `hangup_time` DATETIME DEFAULT NULL,
  `answer_time` DATETIME DEFAULT NULL,
  `duration` INT(11) DEFAULT NULL,
  `bill_sec` INT(11) DEFAULT NULL,
  `call_direction` INT(2) DEFAULT NULL,
  `call_state` INT(2) DEFAULT NULL,
  `hangup_direction` INT(2) DEFAULT NULL,
  `accurate_intent` VARCHAR(20) DEFAULT NULL,
  `reason` VARCHAR(255) DEFAULT NULL,
  `hangup_code` VARCHAR(10) DEFAULT NULL,
  `originate_cmd` VARCHAR(500) DEFAULT NULL,
  `remarks` VARCHAR(255) DEFAULT NULL,
  `has_tts` TINYINT(1) DEFAULT NULL,
  `ai_id` VARCHAR(50) DEFAULT NULL,
  `freason` INT(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` INT(2) NOT NULL DEFAULT '0',
  `isread` INT(2) NOT NULL DEFAULT '0',
  `org_code` VARCHAR(50) DEFAULT NULL,
  `batch_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;


INSERT  INTO `call_out_plan_1_tmp`(`call_id`,`phone_num`,`customer_id`,`temp_id`,`line_id`,`serverId`,`agent_id`,`agent_answer_time`,`agent_channel_uuid`,`agent_group_id`,
`agent_start_time`,`create_time`,`call_start_time`,`hangup_time`,`answer_time`,`duration`,`bill_sec`,`call_direction`,`call_state`,`hangup_direction`,`accurate_intent`,
`reason`,`hangup_code`,`originate_cmd`,`remarks`,`has_tts`,`ai_id`,`freason`,`isdel`,`isread`,`org_code`,`batch_id`) 
SELECT `call_id`,`phone_num`,`customer_id`,`temp_id`,`line_id`,`serverId`,`agent_id`,`agent_answer_time`,`agent_channel_uuid`,`agent_group_id`,
`agent_start_time`,`create_time`,`call_start_time`,`hangup_time`,`answer_time`,`duration`,`bill_sec`,`call_direction`,`call_state`,`hangup_direction`,`accurate_intent`,
`reason`,`hangup_code`,`originate_cmd`,`remarks`,`has_tts`,`ai_id`,`freason`,`isdel`,`isread`,`org_code`,`batch_id` FROM 
call_out_plan_1;



INSERT  INTO `call_out_plan_0_tmp`(`call_id`,`phone_num`,`customer_id`,`temp_id`,`line_id`,`serverId`,`agent_id`,`agent_answer_time`,`agent_channel_uuid`,`agent_group_id`,
`agent_start_time`,`create_time`,`call_start_time`,`hangup_time`,`answer_time`,`duration`,`bill_sec`,`call_direction`,`call_state`,`hangup_direction`,`accurate_intent`,
`reason`,`hangup_code`,`originate_cmd`,`remarks`,`has_tts`,`ai_id`,`freason`,`isdel`,`isread`,`org_code`,`batch_id`) 
SELECT `call_id`,`phone_num`,`customer_id`,`temp_id`,`line_id`,`serverId`,`agent_id`,`agent_answer_time`,`agent_channel_uuid`,`agent_group_id`,
`agent_start_time`,`create_time`,`call_start_time`,`hangup_time`,`answer_time`,`duration`,`bill_sec`,`call_direction`,`call_state`,`hangup_direction`,`accurate_intent`,
`reason`,`hangup_code`,`originate_cmd`,`remarks`,`has_tts`,`ai_id`,`freason`,`isdel`,`isread`,`org_code`,`batch_id` FROM 
call_out_plan_0;


/* tmp表替换为正式表 */

 DROP TABLE IF EXISTS `call_out_plan_0`;
 DROP TABLE IF EXISTS `call_out_plan_1`;
 DROP TABLE IF EXISTS `call_out_plan`;
 
 ALTER TABLE call_out_plan_0_tmp RENAME TO call_out_plan_0;
 ALTER TABLE call_out_plan_1_tmp RENAME TO call_out_plan_1;
 ALTER TABLE call_out_plan_tmp RENAME TO call_out_plan;



/* 去掉自增  */
ALTER TABLE call_out_plan_0 CHANGE id id BIGINT UNSIGNED NOT NULL ;
ALTER TABLE call_out_plan_1 CHANGE id id BIGINT UNSIGNED NOT NULL ;
ALTER TABLE call_out_plan CHANGE id id BIGINT UNSIGNED NOT NULL ;

/* 修改2个字段名 */

ALTER TABLE `call_out_plan` CHANGE call_id plan_uuid VARCHAR(50);
ALTER TABLE `call_out_plan_0` CHANGE call_id plan_uuid VARCHAR(50);
ALTER TABLE `call_out_plan_1` CHANGE call_id plan_uuid VARCHAR(50);


ALTER TABLE `call_out_plan` CHANGE id call_id BIGINT(20);
ALTER TABLE `call_out_plan_0` CHANGE id call_id BIGINT(20);
ALTER TABLE `call_out_plan_1` CHANGE id call_id BIGINT(20);


/* 修改call_out_record */

ALTER TABLE call_out_record ADD COLUMN id BIGINT FIRST;
UPDATE call_out_record a INNER JOIN call_out_plan_0 b ON a.call_id = b.call_id SET a.id = b.id;
UPDATE call_out_record a INNER JOIN call_out_plan_1 b ON a.call_id = b.call_id SET a.id = b.id;
ALTER TABLE call_out_record DROP COLUMN call_id;
ALTER TABLE call_out_record CHANGE id call_id BIGINT;

DELETE FROM call_out_record WHERE call_id IS NULL;

ALTER TABLE call_out_record ADD PRIMARY KEY(call_id);



/* 创建detail 的tmp表 */
DROP TABLE IF EXISTS `call_out_detail_tmp`;

CREATE TABLE `call_out_detail_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` varchar(50) DEFAULT NULL,
  `call_detail_id` varchar(50) NOT NULL,
  `accurate_intent` varchar(10) DEFAULT NULL,
  `agent_answer_text` varchar(255) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `ai_duration` int(11) DEFAULT NULL,
  `asr_duration` int(11) DEFAULT NULL,
  `bot_answer_text` varchar(255) DEFAULT NULL,
  `bot_answer_time` datetime DEFAULT NULL,
  `call_detail_type` int(2) NOT NULL,
  `customer_say_text` varchar(255) DEFAULT NULL,
  `customer_say_time` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `call_out_detail_0_tmp`;

CREATE TABLE `call_out_detail_0_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` varchar(50) DEFAULT NULL,
  `call_detail_id` varchar(50) NOT NULL,
  `accurate_intent` varchar(10) DEFAULT NULL,
  `agent_answer_text` varchar(255) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `ai_duration` int(11) DEFAULT NULL,
  `asr_duration` int(11) DEFAULT NULL,
  `bot_answer_text` varchar(255) DEFAULT NULL,
  `bot_answer_time` datetime DEFAULT NULL,
  `call_detail_type` int(2) NOT NULL,
  `customer_say_text` varchar(255) DEFAULT NULL,
  `customer_say_time` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `call_out_detail_1_tmp`;

CREATE TABLE `call_out_detail_1_tmp` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `call_id` varchar(50) DEFAULT NULL,
  `call_detail_id` varchar(50) NOT NULL,
  `accurate_intent` varchar(10) DEFAULT NULL,
  `agent_answer_text` varchar(255) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `ai_duration` int(11) DEFAULT NULL,
  `asr_duration` int(11) DEFAULT NULL,
  `bot_answer_text` varchar(255) DEFAULT NULL,
  `bot_answer_time` datetime DEFAULT NULL,
  `call_detail_type` int(2) NOT NULL,
  `customer_say_text` varchar(255) DEFAULT NULL,
  `customer_say_time` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8;


INSERT  INTO `call_out_detail_1_tmp`(`call_id`,`call_detail_id`,`accurate_intent`,`agent_answer_text`,`agent_answer_time`,`ai_duration`,`asr_duration`,`bot_answer_text`,
`bot_answer_time`,`call_detail_type`,`customer_say_text`,`customer_say_time`,`reason`,`total_duration`,`sharding_value`)
SELECT `call_id`,`call_detail_id`,`accurate_intent`,`agent_answer_text`,`agent_answer_time`,`ai_duration`,`asr_duration`,`bot_answer_text`,
`bot_answer_time`,`call_detail_type`,`customer_say_text`,`customer_say_time`,`reason`,`total_duration`,`sharding_value`
FROM call_out_detail_1;

INSERT  INTO `call_out_detail_0_tmp`(`call_id`,`call_detail_id`,`accurate_intent`,`agent_answer_text`,`agent_answer_time`,`ai_duration`,`asr_duration`,`bot_answer_text`,
`bot_answer_time`,`call_detail_type`,`customer_say_text`,`customer_say_time`,`reason`,`total_duration`,`sharding_value`)
SELECT `call_id`,`call_detail_id`,`accurate_intent`,`agent_answer_text`,`agent_answer_time`,`ai_duration`,`asr_duration`,`bot_answer_text`,
`bot_answer_time`,`call_detail_type`,`customer_say_text`,`customer_say_time`,`reason`,`total_duration`,`sharding_value`
FROM call_out_detail_0;

/* tmp表替换为正式表 */

DROP TABLE IF EXISTS `call_out_detail_0`;
 DROP TABLE IF EXISTS `call_out_detail_1`;
 DROP TABLE IF EXISTS `call_out_detail`;
 
 ALTER TABLE call_out_detail_0_tmp RENAME TO call_out_detail_0;
 ALTER TABLE call_out_detail_1_tmp RENAME TO call_out_detail_1;
 ALTER TABLE call_out_detail_tmp RENAME TO call_out_detail;

/* 修改call_out_detail_record */

ALTER TABLE call_out_detail_record ADD COLUMN id BIGINT FIRST;
UPDATE call_out_detail_record a INNER JOIN call_out_detail_0 b ON a.call_detail_id = b.call_detail_id SET a.id = b.id;
UPDATE call_out_detail_record a INNER JOIN call_out_detail_1 b ON a.call_detail_id = b.call_detail_id SET a.id = b.id;
ALTER TABLE call_out_detail_record DROP COLUMN call_detail_id;
ALTER TABLE call_out_detail_record CHANGE id call_detail_id BIGINT;

DELETE FROM call_out_detail_record WHERE call_detail_id IS NULL;

ALTER TABLE call_out_detail_record ADD PRIMARY KEY(call_detail_id);


/* 修改detail相关字段名*/


ALTER TABLE `call_out_detail` DROP COLUMN call_detail_id;
ALTER TABLE `call_out_detail_0` DROP COLUMN call_detail_id;
ALTER TABLE `call_out_detail_1` DROP COLUMN call_detail_id;

ALTER TABLE `call_out_detail` CHANGE id call_detail_id BIGINT(20);
ALTER TABLE `call_out_detail_0` CHANGE id call_detail_id BIGINT(20);
ALTER TABLE `call_out_detail_1` CHANGE id call_detail_id BIGINT(20);



 
 ALTER TABLE call_out_detail_record ADD COLUMN call_id_tmp BIGINT(20) AFTER call_detail_id;
 UPDATE `call_out_detail_record` a INNER JOIN `call_out_plan_0` b ON a.call_id = b.`plan_uuid` SET a.call_id_tmp = b.call_id
 ALTER TABLE call_out_detail_record DROP COLUMN call_id;
 ALTER TABLE call_out_detail_record CHANGE call_id_tmp call_id BIGINT(20);
  
 
  
 ALTER TABLE call_out_detail_0 ADD COLUMN call_id_tmp BIGINT(20) AFTER call_detail_id;
 UPDATE `call_out_detail_0` a INNER JOIN `call_out_plan_0` b ON a.call_id = b.`plan_uuid` SET a.call_id_tmp = b.call_id;
 UPDATE `call_out_detail_0` a INNER JOIN `call_out_plan_1` b ON a.call_id = b.`plan_uuid` SET a.call_id_tmp = b.call_id;
 ALTER TABLE call_out_detail_0 DROP COLUMN call_id;
 ALTER TABLE call_out_detail_0 CHANGE call_id_tmp call_id BIGINT(20);

 ALTER TABLE call_out_detail_1 ADD COLUMN call_id_tmp BIGINT(20) AFTER call_detail_id;
 UPDATE `call_out_detail_1` a INNER JOIN `call_out_plan_0` b ON a.call_id = b.`plan_uuid` SET a.call_id_tmp = b.call_id;
 UPDATE `call_out_detail_1` a INNER JOIN `call_out_plan_1` b ON a.call_id = b.`plan_uuid` SET a.call_id_tmp = b.call_id;
 ALTER TABLE call_out_detail_1 DROP COLUMN call_id;
 ALTER TABLE call_out_detail_1 CHANGE call_id_tmp call_id BIGINT(20);
 
 
 ALTER TABLE call_out_detail CHANGE call_id call_id BIGINT(20);



/* 呼入相关表修改 */

 ALTER TABLE  `call_in_detail` CHANGE call_id call_id BIGINT(20);
 ALTER TABLE  `call_in_detail_0` CHANGE call_id call_id BIGINT(20);
 ALTER TABLE  `call_in_detail_1` CHANGE call_id call_id BIGINT(20);
 ALTER TABLE  `call_in_detail_record` CHANGE call_id call_id BIGINT(20);
 
 ALTER TABLE  `call_in_detail` CHANGE call_detail_id call_detail_id BIGINT(20);
 ALTER TABLE  `call_in_detail_0` CHANGE call_detail_id call_detail_id BIGINT(20);
 ALTER TABLE  `call_in_detail_1` CHANGE call_detail_id call_detail_id BIGINT(20);
 ALTER TABLE  `call_in_detail_record` CHANGE call_detail_id call_detail_id BIGINT(20);
 
 
  ALTER TABLE `call_in_plan`  CHANGE call_id call_id BIGINT(20);
  ALTER TABLE `call_in_plan_0`  CHANGE call_id call_id BIGINT(20);
  ALTER TABLE `call_in_plan_1`  CHANGE call_id call_id BIGINT(20);
  ALTER TABLE `call_in_record`  CHANGE call_id call_id BIGINT(20);
   
  ALTER TABLE `call_in_plan`  ADD COLUMN plan_uuid VARCHAR(50);
  ALTER TABLE `call_in_plan_0` ADD COLUMN plan_uuid VARCHAR(50);
  ALTER TABLE `call_in_plan_1` ADD COLUMN plan_uuid VARCHAR(50);

/* org_code修改*/


ALTER TABLE call_out_plan MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE call_out_plan_0 MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE call_out_plan_1 MODIFY COLUMN org_code VARCHAR(8);

ALTER TABLE line_info MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE `report_call_day` MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE `report_call_hour` MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE `report_call_today` MODIFY COLUMN org_code VARCHAR(8);


/* 统一一些字段的类型 */


ALTER TABLE call_out_plan MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_out_plan MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_out_plan MODIFY COLUMN plan_uuid VARCHAR(32);
ALTER TABLE call_out_plan_0 MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_out_plan_0 MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_out_plan_0 MODIFY COLUMN plan_uuid VARCHAR(32);
ALTER TABLE call_out_plan_1 MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_out_plan_1 MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_out_plan_1 MODIFY COLUMN plan_uuid VARCHAR(32);

ALTER TABLE call_in_plan MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_in_plan MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_in_plan MODIFY COLUMN plan_uuid VARCHAR(32);
ALTER TABLE call_in_plan_0 MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_in_plan_0 MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_in_plan_0 MODIFY COLUMN plan_uuid VARCHAR(32);
ALTER TABLE call_in_plan_1 MODIFY COLUMN customer_id INT(8);
ALTER TABLE call_in_plan_1 MODIFY COLUMN phone_num VARCHAR(20);
ALTER TABLE call_in_plan_1 MODIFY COLUMN plan_uuid VARCHAR(32);

ALTER TABLE `report_call_day` DROP COLUMN   customer_id;
ALTER TABLE `report_call_hour` DROP COLUMN   customer_id;
ALTER TABLE `report_call_today` DROP COLUMN   customer_id;







