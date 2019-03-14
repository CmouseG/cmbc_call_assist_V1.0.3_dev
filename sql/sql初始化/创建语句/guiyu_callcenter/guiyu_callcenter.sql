
CREATE DATABASE  IF NOT EXISTS `guiyu_callcenter`   DEFAULT CHARACTER SET utf8 ;

USE `guiyu_callcenter`;

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crm_login_id` varchar(50) DEFAULT NULL,
  `answer_type` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(255) DEFAULT NULL,
  `user_role` int(11) DEFAULT NULL,
  `user_state` int(11) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10142 DEFAULT CHARSET=utf8;

/*Table structure for table `call_in_detail` */

DROP TABLE IF EXISTS `call_in_detail`;

CREATE TABLE `call_in_detail` (
  `call_id` bigint(20) DEFAULT NULL,
  `call_detail_id` bigint(20) NOT NULL,
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

/*Table structure for table `call_in_detail_0` */

DROP TABLE IF EXISTS `call_in_detail_0`;

CREATE TABLE `call_in_detail_0` (
  `call_id` bigint(20) DEFAULT NULL,
  `call_detail_id` bigint(20) NOT NULL,
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

/*Table structure for table `call_in_detail_1` */

DROP TABLE IF EXISTS `call_in_detail_1`;

CREATE TABLE `call_in_detail_1` (
  `call_id` bigint(20) DEFAULT NULL,
  `call_detail_id` bigint(20) NOT NULL,
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

/*Table structure for table `call_in_detail_record` */

DROP TABLE IF EXISTS `call_in_detail_record`;

CREATE TABLE `call_in_detail_record` (
  `call_id` bigint(20) DEFAULT NULL,
  `call_detail_id` bigint(20) NOT NULL,
  `agent_record_file` varchar(30) DEFAULT NULL,
  `agent_record_url` varchar(30) DEFAULT NULL,
  `bot_record_file` varchar(30) DEFAULT NULL,
  `bot_record_url` varchar(30) DEFAULT NULL,
  `customer_record_file` varchar(30) DEFAULT NULL,
  `customer_record_url` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_in_plan` */

DROP TABLE IF EXISTS `call_in_plan`;

CREATE TABLE `call_in_plan` (
  `call_id` bigint(20) NOT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `isread` int(2) NOT NULL DEFAULT '0',
  `plan_uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_in_plan_0` */

DROP TABLE IF EXISTS `call_in_plan_0`;

CREATE TABLE `call_in_plan_0` (
  `call_id` bigint(20) NOT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `isread` int(2) NOT NULL DEFAULT '0',
  `plan_uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_in_plan_1` */

DROP TABLE IF EXISTS `call_in_plan_1`;

CREATE TABLE `call_in_plan_1` (
  `call_id` bigint(20) NOT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `isread` int(2) NOT NULL DEFAULT '0',
  `plan_uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_in_record` */

DROP TABLE IF EXISTS `call_in_record`;

CREATE TABLE `call_in_record` (
  `call_id` bigint(20) NOT NULL,
  `record_file` varchar(50) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_line_day_report` */

DROP TABLE IF EXISTS `call_line_day_report`;

CREATE TABLE `call_line_day_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `success_count` int(11) DEFAULT NULL,
  `all_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

/*Table structure for table `call_line_result` */

DROP TABLE IF EXISTS `call_line_result`;

CREATE TABLE `call_line_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_id` bigint(20) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `successed` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31927 DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_detail` */

DROP TABLE IF EXISTS `call_out_detail`;

CREATE TABLE `call_out_detail` (
  `call_detail_id` bigint(20) NOT NULL,
  `call_id` bigint(20) DEFAULT NULL,
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
  `reason` varchar(600) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  `isupdate` int(1) DEFAULT '0' COMMENT '是否修改过',
  `word_segment_result` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_detail_0` */

DROP TABLE IF EXISTS `call_out_detail_0`;

CREATE TABLE `call_out_detail_0` (
  `call_detail_id` bigint(20) NOT NULL,
  `call_id` bigint(20) DEFAULT NULL,
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
  `reason` varchar(600) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  `isupdate` int(1) DEFAULT '0' COMMENT '是否修改过',
  `word_segment_result` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_detail_1` */

DROP TABLE IF EXISTS `call_out_detail_1`;

CREATE TABLE `call_out_detail_1` (
  `call_detail_id` bigint(20) NOT NULL,
  `call_id` bigint(20) DEFAULT NULL,
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
  `reason` varchar(600) DEFAULT NULL,
  `total_duration` int(11) DEFAULT NULL,
  `sharding_value` int(2) NOT NULL DEFAULT '0',
  `isupdate` int(1) DEFAULT '0' COMMENT '是否修改过',
  `word_segment_result` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_detail_log` */

DROP TABLE IF EXISTS `call_out_detail_log`;

CREATE TABLE `call_out_detail_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_detail_id` bigint(20) DEFAULT NULL,
  `customer_say_text_new` varchar(255) DEFAULT NULL,
  `customer_say_text` varchar(255) DEFAULT NULL,
  `update_by` int(8) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_detail_record` */

DROP TABLE IF EXISTS `call_out_detail_record`;

CREATE TABLE `call_out_detail_record` (
  `call_detail_id` bigint(20) NOT NULL,
  `call_id` bigint(20) DEFAULT NULL,
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
  `call_id` bigint(20) NOT NULL,
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `duration` int(11) DEFAULT '0',
  `bill_sec` int(11) DEFAULT '0',
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(600) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) NOT NULL DEFAULT '0',
  `isread` int(2) NOT NULL DEFAULT '0',
  `org_code` varchar(30) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
  `intervened` tinyint(1) DEFAULT '0' COMMENT '是否已介入',
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_plan_0` */

DROP TABLE IF EXISTS `call_out_plan_0`;

CREATE TABLE `call_out_plan_0` (
  `call_id` bigint(20) NOT NULL,
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `duration` int(11) DEFAULT '0',
  `bill_sec` int(11) DEFAULT '0',
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(600) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) NOT NULL DEFAULT '0',
  `isread` int(2) NOT NULL DEFAULT '0',
  `org_code` varchar(30) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
  `intervened` tinyint(1) DEFAULT '0' COMMENT '是否已介入',
  PRIMARY KEY (`call_id`),
  UNIQUE KEY `uk_call_out_plan_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_call_state` (`call_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_plan_1` */

DROP TABLE IF EXISTS `call_out_plan_1`;

CREATE TABLE `call_out_plan_1` (
  `call_id` bigint(20) NOT NULL,
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `customer_id` int(8) DEFAULT NULL,
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
  `duration` int(11) DEFAULT '0',
  `bill_sec` int(11) DEFAULT '0',
  `call_direction` int(2) DEFAULT NULL,
  `call_state` int(2) DEFAULT NULL,
  `hangup_direction` int(2) DEFAULT NULL,
  `accurate_intent` varchar(20) DEFAULT NULL,
  `reason` varchar(600) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `originate_cmd` varchar(500) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `has_tts` tinyint(1) DEFAULT NULL,
  `ai_id` varchar(50) DEFAULT NULL,
  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
  `isdel` int(2) NOT NULL DEFAULT '0',
  `isread` int(2) NOT NULL DEFAULT '0',
  `org_code` varchar(30) DEFAULT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
  `intervened` tinyint(1) DEFAULT '0' COMMENT '是否已介入',
  PRIMARY KEY (`call_id`),
  UNIQUE KEY `uk_call_out_plan_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_call_state` (`call_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `call_out_record` */

DROP TABLE IF EXISTS `call_out_record`;

CREATE TABLE `call_out_record` (
  `call_id` bigint(20) NOT NULL,
  `record_file` varchar(255) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `error_match` */

DROP TABLE IF EXISTS `error_match`;

CREATE TABLE `error_match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error_type` int(2) DEFAULT NULL,
  `error_name` varchar(100) DEFAULT NULL,
  `en_name` varchar(100) DEFAULT NULL,
  `key_word` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Table structure for table `fs_bind` */

DROP TABLE IF EXISTS `fs_bind`;

CREATE TABLE `fs_bind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(20) NOT NULL DEFAULT '',
  `service_name` varchar(50) DEFAULT NULL,
  `fs_agent_id` varchar(30) NOT NULL DEFAULT '',
  `fs_agent_addr` varchar(30) DEFAULT NULL,
  `fs_esl_port` varchar(20) DEFAULT NULL,
  `fs_esl_pwd` varchar(20) DEFAULT NULL,
  `fs_in_port` varchar(20) DEFAULT NULL,
  `fs_out_port` varchar(20) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Table structure for table `line_config` */

DROP TABLE IF EXISTS `line_config`;

CREATE TABLE `line_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_id` varchar(30) DEFAULT NULL,
  `file_type` varchar(30) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_data` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1799 DEFAULT CHARSET=utf8;

/*Table structure for table `line_count` */

DROP TABLE IF EXISTS `line_count`;

CREATE TABLE `line_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calloutserver_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `max_concurrent_calls` int(11) DEFAULT NULL,
  `used_concurrent_calls` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `line_info` */

DROP TABLE IF EXISTS `line_info`;

CREATE TABLE `line_info` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(8) DEFAULT NULL,
  `line_name` varchar(30) DEFAULT NULL,
  `sip_ip` varchar(20) DEFAULT NULL,
  `sip_port` varchar(20) DEFAULT NULL,
  `codec` varchar(20) DEFAULT NULL,
  `caller_num` varchar(30) DEFAULT NULL,
  `callee_prefix` varchar(20) DEFAULT NULL,
  `max_concurrent_calls` int(11) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  `update_date` varchar(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `createt_by` int(8) DEFAULT NULL,
  `update_by` int(8) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1353 DEFAULT CHARSET=utf8;

/*Table structure for table `notice_send_label` */

DROP TABLE IF EXISTS `notice_send_label`;

CREATE TABLE `notice_send_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(30) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `phone` */

DROP TABLE IF EXISTS `phone`;

CREATE TABLE `phone` (
  `pref` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `isp` varchar(30) DEFAULT NULL,
  `post_code` varchar(30) DEFAULT NULL,
  `city_code` varchar(30) DEFAULT NULL,
  `area_code` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `queue` */

DROP TABLE IF EXISTS `queue`;

CREATE TABLE `queue` (
  `queue_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_name` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  `line_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30079 DEFAULT CHARSET=utf8;

/*Table structure for table `registration` */

DROP TABLE IF EXISTS `registration`;

CREATE TABLE `registration` (
  `reg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `customer_addr` varchar(255) DEFAULT NULL,
  `customer_mobile` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `plan_uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`reg_id`)
) ENGINE=MyISAM AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;

/*Table structure for table `report_call_day` */

DROP TABLE IF EXISTS `report_call_day`;

CREATE TABLE `report_call_day` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_date` varchar(10) DEFAULT NULL,
  `duration_type` int(2) DEFAULT NULL,
  `intent` varchar(10) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `call_count` int(11) DEFAULT NULL,
  `duration_all` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3536 DEFAULT CHARSET=utf8;

/*Table structure for table `report_call_hour` */

DROP TABLE IF EXISTS `report_call_hour`;

CREATE TABLE `report_call_hour` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_time` datetime DEFAULT NULL,
  `out_count` int(11) DEFAULT NULL,
  `connect_count` int(11) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=788 DEFAULT CHARSET=utf8;

/*Table structure for table `report_call_today` */

DROP TABLE IF EXISTS `report_call_today`;

CREATE TABLE `report_call_today` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration_type` int(2) DEFAULT NULL,
  `intent` varchar(10) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `call_count` int(11) DEFAULT NULL,
  `duration_all` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  `call_date` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36890 DEFAULT CHARSET=utf8;

/*Table structure for table `report_line_code` */

DROP TABLE IF EXISTS `report_line_code`;

CREATE TABLE `report_line_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `hangup_code` varchar(10) DEFAULT NULL,
  `is_cancel` int(1) DEFAULT NULL,
  `total_calls` int(11) DEFAULT NULL,
  `answer_calls` int(11) DEFAULT NULL,
  `phone_num` varchar(1024) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13270 DEFAULT CHARSET=utf8;

/*Table structure for table `report_line_status` */

DROP TABLE IF EXISTS `report_line_status`;

CREATE TABLE `report_line_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `line_id` int(11) DEFAULT NULL,
  `answer_num` int(11) DEFAULT NULL,
  `total_num` int(11) DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5050 DEFAULT CHARSET=utf8;

/*Table structure for table `sim_gateway` */

DROP TABLE IF EXISTS `sim_gateway`;

CREATE TABLE `sim_gateway` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sim_gateway_id` varchar(50) DEFAULT NULL,
  `start_count` int(10) DEFAULT NULL,
  `counts_step` int(10) DEFAULT NULL,
  `start_pwd` int(10) DEFAULT NULL,
  `pwd_step` int(10) DEFAULT NULL,
  `count_num` int(10) DEFAULT NULL,
  `sim_agent_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Table structure for table `tier` */

DROP TABLE IF EXISTS `tier`;

CREATE TABLE `tier` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

