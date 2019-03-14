/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_robot

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-13 11:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ai_cycle_his
-- ----------------------------
DROP TABLE IF EXISTS `ai_cycle_his`;
CREATE TABLE `ai_cycle_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `ai_no` varchar(50) NOT NULL COMMENT '机器人编号',
  `ai_name` varchar(50) DEFAULT NULL COMMENT '机器人昵称',
  `template_id` varchar(50) DEFAULT NULL COMMENT '话术模板',
  `assign_date` varchar(10) DEFAULT NULL COMMENT '分配日期',
  `assign_time` varchar(20) DEFAULT NULL COMMENT '分配时间',
  `taskback_date` varchar(10) DEFAULT NULL COMMENT '收回日期',
  `taskback_time` varchar(20) DEFAULT NULL COMMENT '收回时间',
  `call_num` bigint(20) DEFAULT NULL COMMENT '拨打数量',
  `crt_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ai_cycle_his_idx1` (`user_id`),
  KEY `ai_cycle_his_idx2` (`ai_no`),
  KEY `ai_cycle_his_idx3` (`taskback_date`),
  KEY `ai_cycle_his_idx4` (`taskback_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2416 DEFAULT CHARSET=utf8 COMMENT='机器人生命周期记录';

-- ----------------------------
-- Table structure for tts_callback_his
-- ----------------------------
DROP TABLE IF EXISTS `tts_callback_his`;
CREATE TABLE `tts_callback_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `busi_id` varchar(32) NOT NULL COMMENT '业务id,调用tts的唯一请求id',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `tts_json_data` text COMMENT 'TTS合成的语音JSON',
  `status` int(11) NOT NULL COMMENT '状态: 1-完成, 0-失败',
  `error_msg` varchar(1024) DEFAULT NULL COMMENT '失败日志',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `tts_callback_his_idx1` (`busi_id`),
  KEY `tts_callback_his_idx2` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='TTS合成回调数据';

-- ----------------------------
-- Table structure for tts_wav_his
-- ----------------------------
DROP TABLE IF EXISTS `tts_wav_his`;
CREATE TABLE `tts_wav_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_id` varchar(32) NOT NULL COMMENT '会话Id',
  `busi_id` varchar(32) NOT NULL COMMENT '业务id,调用tts的唯一请求id',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `req_params` varchar(1024) DEFAULT NULL COMMENT 'TTS合成请求参数',
  `tts_txt_json_data` varchar(1024) DEFAULT NULL COMMENT '需要合成的文本JSON',
  `tts_json_data` text COMMENT 'TTS合成的语音JSON',
  `status` int(11) DEFAULT NULL COMMENT '状态: 2-合成中,1-完成, 0-失败',
  `error_msg` varchar(1024) DEFAULT NULL COMMENT '失败日志',
  `error_type` int(11) DEFAULT NULL COMMENT '失败类型:1-调用失败，2-TTS接口回调失败，3-TTS回调后本地处理失败',
  `error_try_num` int(11) DEFAULT NULL COMMENT '失败尝试次数',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `tts_wav_his_idx1` (`seq_id`),
  KEY `tts_wav_his_idx2` (`busi_id`),
  KEY `tts_wav_his_idx3` (`status`),
  KEY `tts_wav_his_idx4` (`crt_time`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='TTS语音合成数据';

-- ----------------------------
-- Table structure for user_ai_cfg_base_info
-- ----------------------------
DROP TABLE IF EXISTS `user_ai_cfg_base_info`;
CREATE TABLE `user_ai_cfg_base_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `org_code` varchar(30) NOT NULL COMMENT '机构编号',
  `ai_total_num` int(11) DEFAULT NULL COMMENT '机器人数量',
  `template_ids` varchar(200) DEFAULT NULL COMMENT '话术模板',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ai_cfg_base_info_idx1` (`user_id`),
  KEY `user_ai_cfg_base_info_idx2` (`org_code`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='用户机器人账户基本信息';

-- ----------------------------
-- Table structure for user_ai_cfg_base_info_20181228
-- ----------------------------
DROP TABLE IF EXISTS `user_ai_cfg_base_info_20181228`;
CREATE TABLE `user_ai_cfg_base_info_20181228` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `org_code` varchar(50) DEFAULT NULL,
  `ai_total_num` int(11) DEFAULT NULL COMMENT '机器人数量',
  `template_ids` varchar(200) DEFAULT NULL COMMENT '话术模板',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ai_cfg_base_info_idx1` (`user_id`),
  KEY `user_ai_cfg_base_info_idx2` (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户机器人账户基本信息';

-- ----------------------------
-- Table structure for user_ai_cfg_his_info
-- ----------------------------
DROP TABLE IF EXISTS `user_ai_cfg_his_info`;
CREATE TABLE `user_ai_cfg_his_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `busi_id` int(11) DEFAULT NULL COMMENT '业务编号',
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `ai_num` int(11) DEFAULT NULL COMMENT '机器人数量',
  `assign_level` int(11) DEFAULT NULL COMMENT '预分配，高，低',
  `template_id` varchar(200) DEFAULT NULL COMMENT '话术模板',
  `open_date` varchar(10) DEFAULT NULL COMMENT '开户日期',
  `invalid_date` varchar(10) DEFAULT NULL COMMENT '失效日期',
  `status` int(11) DEFAULT NULL COMMENT '状态：1-正常，0-失效',
  `invalid_policy` varchar(10) DEFAULT NULL COMMENT '失效策略:NUM:100',
  `handle_type` int(11) DEFAULT NULL COMMENT '操作类型:1-新增;2-更新;0-删除',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ai_cfg_his_info_idx1` (`busi_id`),
  KEY `user_ai_cfg_his_info_idx2` (`user_id`),
  KEY `user_ai_cfg_his_info_idx3` (`crt_time`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8 COMMENT='用户机器配置变更历史信息';

-- ----------------------------
-- Table structure for user_ai_cfg_info
-- ----------------------------
DROP TABLE IF EXISTS `user_ai_cfg_info`;
CREATE TABLE `user_ai_cfg_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `ai_num` int(11) DEFAULT NULL COMMENT '机器人数量',
  `assign_level` int(11) DEFAULT NULL COMMENT '预分配，高，低',
  `template_ids` varchar(200) DEFAULT NULL COMMENT '话术模板',
  `open_date` varchar(10) DEFAULT NULL COMMENT '开户日期',
  `invalid_date` varchar(10) DEFAULT NULL COMMENT '失效日期',
  `status` int(11) NOT NULL COMMENT '状态：1-正常，0-失效',
  `invalid_policy` varchar(10) DEFAULT NULL COMMENT '失效策略:NUM:100',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ai_cfg_info_idx1` (`user_id`),
  KEY `user_ai_cfg_info_idx2` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='用户机器配置信息';
