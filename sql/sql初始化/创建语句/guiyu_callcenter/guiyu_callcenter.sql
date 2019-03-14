/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_callcenter

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-13 11:39:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for agent
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=10033 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_in_detail
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_detail_0
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_detail_1
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_detail_record
-- ----------------------------
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
  PRIMARY KEY (`call_detail_id`),
  KEY `call_in_detail_record_call_id` (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_in_plan
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_plan_0
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_plan_1
-- ----------------------------
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

-- ----------------------------
-- Table structure for call_in_record
-- ----------------------------
DROP TABLE IF EXISTS `call_in_record`;
CREATE TABLE `call_in_record` (
  `call_id` bigint(20) NOT NULL,
  `record_file` varchar(50) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_line_day_report
-- ----------------------------
DROP TABLE IF EXISTS `call_line_day_report`;
CREATE TABLE `call_line_day_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `success_count` int(11) DEFAULT NULL,
  `all_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_line_result
-- ----------------------------
DROP TABLE IF EXISTS `call_line_result`;
CREATE TABLE `call_line_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_id` bigint(20) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `successed` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=399875 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_detail
-- ----------------------------
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
  PRIMARY KEY (`call_detail_id`),
  KEY `idx_call_out_detail_call_id` (`call_id`),
  KEY `idx_call_out_detail_0_call_id` (`call_id`),
  KEY `idx_call_out_detail_1_call_id` (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_detail_0
-- ----------------------------
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
  PRIMARY KEY (`call_detail_id`),
  KEY `idx_call_out_detail_0_call_id` (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_detail_1
-- ----------------------------
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
  PRIMARY KEY (`call_detail_id`),
  KEY `idx_call_out_detail_1_call_id` (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_detail_log
-- ----------------------------
DROP TABLE IF EXISTS `call_out_detail_log`;
CREATE TABLE `call_out_detail_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_detail_id` bigint(20) DEFAULT NULL,
  `customer_say_text_new` varchar(255) DEFAULT NULL,
  `customer_say_text` varchar(255) DEFAULT NULL,
  `update_by` int(8) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_detail_record
-- ----------------------------
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
  PRIMARY KEY (`call_detail_id`),
  KEY `call_out_detail_record_call_id` (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_plan
-- ----------------------------
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
  `org_code` varchar(30) NOT NULL,
  `batch_id` int(11) DEFAULT NULL,
  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
  PRIMARY KEY (`call_id`),
  KEY `idx_call_out_plan_agent_id` (`agent_id`),
  KEY `idx_call_out_plan_agent_answer_time` (`agent_answer_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_plan_0
-- ----------------------------
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
  PRIMARY KEY (`call_id`),
  UNIQUE KEY `uk_call_out_plan_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_0_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_call_state` (`call_state`),
  KEY `idx_call_out_plan_0_agent_id` (`agent_id`),
  KEY `idx_call_out_plan_0_agent_answer_time` (`agent_answer_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_plan_1
-- ----------------------------
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
  PRIMARY KEY (`call_id`),
  UNIQUE KEY `uk_call_out_plan_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_1_plan_uuid` (`plan_uuid`),
  KEY `idx_call_out_plan_call_state` (`call_state`),
  KEY `idx_call_out_plan_1_agent_id` (`agent_id`),
  KEY `idx_call_out_plan__1agent_answer_time` (`agent_answer_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for call_out_record
-- ----------------------------
DROP TABLE IF EXISTS `call_out_record`;
CREATE TABLE `call_out_record` (
  `call_id` bigint(20) NOT NULL,
  `record_file` varchar(255) DEFAULT NULL,
  `record_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`call_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for error_match
-- ----------------------------
DROP TABLE IF EXISTS `error_match`;
CREATE TABLE `error_match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error_type` int(2) DEFAULT NULL,
  `error_name` varchar(100) DEFAULT NULL,
  `en_name` varchar(100) DEFAULT NULL,
  `key_word` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fs_bind
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for line_config
-- ----------------------------
DROP TABLE IF EXISTS `line_config`;
CREATE TABLE `line_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_id` varchar(30) DEFAULT NULL,
  `file_type` varchar(30) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_data` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for line_count
-- ----------------------------
DROP TABLE IF EXISTS `line_count`;
CREATE TABLE `line_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calloutserver_id` varchar(30) NOT NULL DEFAULT '',
  `line_id` int(11) NOT NULL,
  `max_concurrent_calls` int(11) DEFAULT NULL,
  `used_concurrent_calls` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for line_info
-- ----------------------------
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
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=393 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notice_send_label
-- ----------------------------
DROP TABLE IF EXISTS `notice_send_label`;
CREATE TABLE `notice_send_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(30) NOT NULL,
  `label` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for phone
-- ----------------------------
DROP TABLE IF EXISTS `phone`;
CREATE TABLE `phone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pref` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `isp` varchar(30) DEFAULT NULL,
  `post_code` varchar(30) DEFAULT NULL,
  `city_code` varchar(30) DEFAULT NULL,
  `area_code` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=415808 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for queue
-- ----------------------------
DROP TABLE IF EXISTS `queue`;
CREATE TABLE `queue` (
  `queue_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_name` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  `line_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30031 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for registration
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_call_count
-- ----------------------------
DROP TABLE IF EXISTS `report_call_count`;
CREATE TABLE `report_call_count` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_date` varchar(20) NOT NULL,
  `count_a` int(11) DEFAULT '0',
  `count_b` int(11) DEFAULT '0',
  `count_c` int(11) DEFAULT '0',
  `count_d` int(11) DEFAULT '0',
  `count_e` int(11) DEFAULT '0',
  `count_f` int(11) DEFAULT '0',
  `count_u` int(11) DEFAULT '0',
  `count_v` int(11) DEFAULT '0',
  `count_w` int(11) DEFAULT '0',
  `count_all` int(11) DEFAULT '0',
  `accurate_intent` varchar(10) DEFAULT NULL,
  `customer_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_call_day
-- ----------------------------
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
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3960 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_call_day_20190304
-- ----------------------------
DROP TABLE IF EXISTS `report_call_day_20190304`;
CREATE TABLE `report_call_day_20190304` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_date` varchar(10) DEFAULT NULL,
  `duration_type` int(2) DEFAULT NULL,
  `intent` varchar(10) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `call_count` int(11) DEFAULT NULL,
  `duration_all` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2060 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_call_hour
-- ----------------------------
DROP TABLE IF EXISTS `report_call_hour`;
CREATE TABLE `report_call_hour` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_time` datetime DEFAULT NULL,
  `out_count` int(11) DEFAULT NULL,
  `connect_count` int(11) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1359 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_call_today
-- ----------------------------
DROP TABLE IF EXISTS `report_call_today`;
CREATE TABLE `report_call_today` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration_type` int(2) DEFAULT NULL,
  `intent` varchar(10) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `call_count` int(11) DEFAULT NULL,
  `duration_all` bigint(20) DEFAULT NULL,
  `tempid` varchar(50) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  `call_date` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174604 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_line_code
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=39100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for report_line_status
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=9502 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sim_gateway
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tier
-- ----------------------------
DROP TABLE IF EXISTS `tier`;
CREATE TABLE `tier` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for liyang
-- ----------------------------
DROP PROCEDURE IF EXISTS `liyang`;
DELIMITER ;;
CREATE DEFINER=`guiji`@`%` PROCEDURE `liyang`(IN a INT)
BEGIN
   
SET a=0;WHILE a < 61 DO 
 
UPDATE `report_call_day` a 
INNER JOIN (
SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY),'%Y-%m-%d') AS callDate ,
org_code AS orgCode,accurate_intent AS intent,
IF(accurate_intent = 'F' OR accurate_intent = 'W',LEFT(reason,49),'已接通') AS reason,
3 AS durationType, COUNT(*) AS callCount,SUM(bill_sec) AS durationAll,temp_id AS tempid
FROM
call_out_plan_0
WHERE
create_time >= DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY)
AND create_time < DATE_SUB(CURRENT_DATE(),INTERVAL a DAY)
AND bill_sec =30
GROUP BY org_code,accurate_intent,reason,temp_id
) b
SET
a.call_count = a.call_count + b.callCount, a.duration_all = a.duration_all + b.durationAll
WHERE a.call_date =b.callDate AND a.org_code = b.orgCode AND a.intent =b.intent AND a.reason = b.reason AND a.tempid = b.tempid;
SET a=a+1;
END WHILE;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for liyang1
-- ----------------------------
DROP PROCEDURE IF EXISTS `liyang1`;
DELIMITER ;;
CREATE DEFINER=`guiji`@`%` PROCEDURE `liyang1`(IN a INT)
BEGIN
   
SET a=0;WHILE a < 61 DO 
 
UPDATE `report_call_day` a 
INNER JOIN (
SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY),'%Y-%m-%d') AS callDate ,
org_code AS orgCode,accurate_intent AS intent,
IF(accurate_intent = 'F' OR accurate_intent = 'W',LEFT(reason,49),'已接通') AS reason,
3 AS durationType, COUNT(*) AS callCount,SUM(bill_sec) AS durationAll,temp_id AS tempid
FROM
call_out_plan_1
WHERE
create_time >= DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY)
AND create_time < DATE_SUB(CURRENT_DATE(),INTERVAL a DAY)
AND bill_sec =30
GROUP BY org_code,accurate_intent,reason,temp_id
) b
SET
a.call_count = a.call_count + b.callCount, a.duration_all = a.duration_all + b.durationAll
WHERE a.call_date =b.callDate AND a.org_code = b.orgCode AND a.intent =b.intent AND a.reason = b.reason AND a.tempid = b.tempid;
SET a=a+1;
END WHILE;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for liyang3
-- ----------------------------
DROP PROCEDURE IF EXISTS `liyang3`;
DELIMITER ;;
CREATE DEFINER=`guiji`@`%` PROCEDURE `liyang3`(IN a INT)
BEGIN
   
SET a=0;WHILE a < 60 DO 

DELETE FROM report_call_day WHERE duration_type =3 AND call_date =DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d');
 
INSERT INTO  `report_call_day` (call_date,duration_type,intent,reason,call_count,duration_all,tempid,org_code)

SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d') AS call_date ,3 AS duration_type,
accurate_intent AS intent,
IF(accurate_intent = 'F' OR accurate_intent = 'W',LEFT(reason,49),'已接通') AS reason,
 COUNT(*) AS call_count,SUM(bill_sec) AS duration_all,temp_id AS tempid,org_code
FROM
call_out_plan_0
WHERE
create_time >= DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY)
AND create_time < DATE_SUB(CURRENT_DATE(),INTERVAL a DAY)
AND bill_sec >=30
GROUP BY org_code,accurate_intent,reason,temp_id
;
SET a=a+1;
END WHILE;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for liyang44
-- ----------------------------
DROP PROCEDURE IF EXISTS `liyang44`;
DELIMITER ;;
CREATE DEFINER=`guiji`@`%` PROCEDURE `liyang44`(IN a INT)
BEGIN
   
SET a=0;WHILE a < 60 DO 

DELETE FROM report_call_day WHERE duration_type =3 AND call_date =DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d');
 
INSERT INTO  `report_call_day` (call_date,duration_type,intent,reason,call_count,duration_all,tempid,org_code)
SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d') AS call_date ,
3 AS duration_type,intent,reason,SUM(call_count) AS call_count,SUM(duration_all) AS duration_all,tempid,org_code
FROM
(
SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d') AS call_date ,3 AS duration_type,
accurate_intent AS intent,
IF(accurate_intent = 'F' OR accurate_intent = 'W',LEFT(reason,49),'已接通') AS reason,
 COUNT(*) AS call_count,SUM(bill_sec) AS duration_all,temp_id AS tempid,org_code
FROM
call_out_plan_0
WHERE
create_time >= DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY)
AND create_time < DATE_SUB(CURRENT_DATE(),INTERVAL a DAY)
AND bill_sec >=30
GROUP BY org_code,accurate_intent,reason,temp_id

UNION ALL

SELECT DATE_FORMAT(DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY),'%Y-%m-%d') AS call_date ,3 AS duration_type,
accurate_intent AS intent,
IF(accurate_intent = 'F' OR accurate_intent = 'W',LEFT(reason,49),'已接通') AS reason,
 COUNT(*) AS call_count,SUM(bill_sec) AS duration_all,temp_id AS tempid,org_code
FROM
call_out_plan_1
WHERE
create_time >= DATE_SUB(CURRENT_DATE(),INTERVAL 1+a DAY)
AND create_time < DATE_SUB(CURRENT_DATE(),INTERVAL a DAY)
AND bill_sec >=30
GROUP BY org_code,accurate_intent,reason,temp_id
)m GROUP BY  org_code,intent,reason,tempid
;
SET a=a+1;
END WHILE;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for genTabId
-- ----------------------------
DROP FUNCTION IF EXISTS `genTabId`;
DELIMITER ;;
CREATE DEFINER=`callcenter`@`%` FUNCTION `genTabId`(SEQ_NAME VARCHAR(64)) RETURNS varchar(32) CHARSET utf8
    READS SQL DATA
BEGIN
	DECLARE TMP VARCHAR(14);
	DECLARE TMP1 BIGINT;
	DECLARE TMP2 VARCHAR(6);
	DECLARE TMP3 VARCHAR(8);

	UPDATE bd_table_sequence SET seq = seq + step WHERE table_name = SEQ_NAME;
	SET TMP = DATE_FORMAT(NOW(),'%Y%m%d');
	SELECT seq,sign,now_date INTO TMP1,TMP2,TMP3 FROM bd_table_sequence WHERE table_name=SEQ_NAME;
  IF TMP3 = '' THEN
		SET TMP3 = '19700101';
	ELSEIF  TMP3 = NULL  THEN
		SET TMP3 = '19700101';
	END IF;
	IF TMP3 <> TMP THEN 
		UPDATE bd_table_sequence SET seq = 0 ,now_date = TMP WHERE table_name = SEQ_NAME;
	END IF;
	RETURN CONCAT(TMP,TMP2, LPAD(TMP1, 7, '0'),RIGHT(UUID_SHORT(),4));
END
;;
DELIMITER ;
