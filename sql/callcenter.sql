

CREATE DATABASE  IF NOT EXISTS `guiyu_callcenter` DEFAULT CHARACTER SET utf8 ;

USE `guiyu_callcenter`;

/*Table structure for table `call_in_detail` */

DROP TABLE IF EXISTS `call_in_detail`;

CREATE TABLE `call_in_detail` (
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
  `reason` varchar(20) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_detail` */

/*Table structure for table `call_in_detail_0` */

DROP TABLE IF EXISTS `call_in_detail_0`;

CREATE TABLE `call_in_detail_0` (
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
  `reason` varchar(20) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_detail_0` */

/*Table structure for table `call_in_detail_1` */

DROP TABLE IF EXISTS `call_in_detail_1`;

CREATE TABLE `call_in_detail_1` (
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
  `reason` varchar(20) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_detail_1` */

/*Table structure for table `call_in_detail_record` */

DROP TABLE IF EXISTS `call_in_detail_record`;

CREATE TABLE `call_in_detail_record` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `call_detail_id` varchar(50) NOT NULL,
  `agent_record_file` varchar(30) DEFAULT NULL,
  `agent_record_url` varchar(30) DEFAULT NULL,
  `bot_record_file` varchar(30) DEFAULT NULL,
  `bot_record_url` varchar(30) DEFAULT NULL,
  `customer_record_file` varchar(30) DEFAULT NULL,
  `customer_record_url` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_detail_record` */

/*Table structure for table `call_in_plan` */

DROP TABLE IF EXISTS `call_in_plan`;

CREATE TABLE `call_in_plan` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(30) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_plan` */

/*Table structure for table `call_in_plan_0` */

DROP TABLE IF EXISTS `call_in_plan_0`;

CREATE TABLE `call_in_plan_0` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(30) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_plan_0` */

/*Table structure for table `call_in_plan_1` */

DROP TABLE IF EXISTS `call_in_plan_1`;

CREATE TABLE `call_in_plan_1` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(30) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_in_record` */

DROP TABLE IF EXISTS `call_in_record`;

CREATE TABLE `call_in_record` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `record_file` varchar(50) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_in_record` */

/*Table structure for table `call_out_detail` */

DROP TABLE IF EXISTS `call_out_detail`;

CREATE TABLE `call_out_detail` (
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
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_out_detail` */

/*Table structure for table `call_out_detail_0` */

DROP TABLE IF EXISTS `call_out_detail_0`;

CREATE TABLE `call_out_detail_0` (
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
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_out_detail_1` */

DROP TABLE IF EXISTS `call_out_detail_1`;

CREATE TABLE `call_out_detail_1` (
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
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_out_detail_record` */

DROP TABLE IF EXISTS `call_out_detail_record`;

CREATE TABLE `call_out_detail_record` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `call_detail_id` varchar(50) NOT NULL,
  `agent_record_file` varchar(100) DEFAULT NULL,
  `agent_record_url` varchar(255) DEFAULT NULL,
  `bot_record_file` varchar(100) DEFAULT NULL,
  `bot_record_url` varchar(255) DEFAULT NULL,
  `customer_record_file` varchar(100) DEFAULT NULL,
  `customer_record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_out_plan` */

DROP TABLE IF EXISTS `call_out_plan`;

CREATE TABLE `call_out_plan` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `call_out_plan` */

/*Table structure for table `call_out_plan_0` */

DROP TABLE IF EXISTS `call_out_plan_0`;

CREATE TABLE `call_out_plan_0` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_out_plan_1` */

DROP TABLE IF EXISTS `call_out_plan_1`;

CREATE TABLE `call_out_plan_1` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(30) NOT NULL DEFAULT '',
  `customer_id` varchar(50) NOT NULL DEFAULT '',
  `temp_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `serverId` varchar(30) DEFAULT NULL,
  `agent_id` varchar(30) DEFAULT NULL,
  `agent_answer_time` datetime DEFAULT NULL,
  `agent_channel_uuid` varchar(40) DEFAULT NULL,
  `agent_group_id` varchar(30) DEFAULT NULL,
  `agent_start_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `call_start_time` datetime DEFAULT NULL,
  `hangup_time` datetime DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `bill_sec` int(11) DEFAULT NULL,
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) DEFAULT '0',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `call_out_record` */

DROP TABLE IF EXISTS `call_out_record`;

CREATE TABLE `call_out_record` (
  `call_id` varchar(50) NOT NULL DEFAULT '',
  `record_file` varchar(50) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `error_match` */

DROP TABLE IF EXISTS `error_match`;

CREATE TABLE `error_match` (
  `error_type` int(2) DEFAULT NULL,
  `error_name` varchar(100) DEFAULT NULL,
  `key_word` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `error_match` */

insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (0,'无人接听','无人接听');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','您的电话已欠费');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','续交话费');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','手机号码已暂停');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','您的手机号码');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','继续使用');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','无法呼出');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (1,'主叫停机','您的电话已被停机');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','欠费');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','停机');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','通话已经被');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','所拨打的用户通话');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','暂停服务');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (2,'被叫停机','未开通语音');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (3,'关机或不在服务区','关机');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (3,'关机或不在服务区','不在服务区');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','占线');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','通话中');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','正在通话');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','无法接通');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','正忙');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','无法接听');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','联通秘书');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','来电助手');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','来电宝');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','短信的方式');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (4,'占线','短信方式');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (5,'空号','空号');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (5,'空号','不存在');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (5,'空号','不在使用');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (6,'呼叫受限','越权使用');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (6,'呼叫受限','呼叫受限');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (6,'呼叫受限','呼叫该号码');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (6,'呼叫受限','语音信箱');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (7,'用户挂断','用户挂断');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (8,'无效号码','无效号码');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (9,'已接通','已接通');
insert  into `error_match`(`error_type`,`error_name`,`key_word`) values (10,'无人接听','无人接听');

/*Table structure for table `fs_bind` */

DROP TABLE IF EXISTS `fs_bind`;

CREATE TABLE `fs_bind` (
  `service_id` varchar(20) NOT NULL DEFAULT '',
  `service_name` varchar(50) DEFAULT NULL,
  `fs_agent_id` varchar(30) NOT NULL DEFAULT '',
  `fs_agent_addr` varchar(30) DEFAULT NULL,
  `fs_esl_port` varchar(20) DEFAULT NULL,
  `fs_esl_pwd` varchar(20) DEFAULT NULL,
  `fs_in_port` varchar(20) DEFAULT NULL,
  `fs_out_port` varchar(20) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `line_config` */

DROP TABLE IF EXISTS `line_config`;

CREATE TABLE `line_config` (
  `line_id` varchar(30) DEFAULT NULL,
  `file_type` varchar(30) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_data` varchar(800) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `line_count` */

DROP TABLE IF EXISTS `line_count`;

CREATE TABLE `line_count` (
  `calloutserver_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `max_concurrent_calls` int(11) DEFAULT NULL,
  `used_concurrent_calls` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*Table structure for table `line_info` */

DROP TABLE IF EXISTS `line_info`;

CREATE TABLE `line_info` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(11) DEFAULT NULL,
  `line_name` varchar(30) DEFAULT NULL,
  `sip_ip` varchar(20) DEFAULT NULL,
  `sip_port` varchar(20) DEFAULT NULL,
  `codec` varchar(20) DEFAULT NULL,
  `caller_num` varchar(100) DEFAULT NULL,
  `callee_prefix` varchar(20) DEFAULT NULL,
  `max_concurrent_calls` int(11) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `createt_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;
