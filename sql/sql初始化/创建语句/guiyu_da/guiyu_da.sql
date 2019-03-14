/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_da

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-13 11:39:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cust_pre_info
-- ----------------------------
DROP TABLE IF EXISTS `cust_pre_info`;
CREATE TABLE `cust_pre_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) DEFAULT NULL COMMENT '电话号码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名(未知/具体姓名/具体姓氏)',
  `seq_id` varchar(50) DEFAULT NULL COMMENT '会话id',
  `need` varchar(30) DEFAULT NULL COMMENT '是否有需求(未知/有/没有)主要是卖东西的领域',
  `is_self` varchar(30) DEFAULT NULL COMMENT '是否是本人(未知/是/不是)主要是银行通知类的领域',
  `agree_connect` varchar(50) DEFAULT NULL COMMENT '是否同意邀约/是否同意再次联系/是否同意过来体验产品(未知/不同意/同意)',
  `age` varchar(30) DEFAULT NULL COMMENT '年纪(未知/普通/老人/学生/或者具体的岁数)',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别(未知/男/女)',
  `addr` varchar(100) DEFAULT NULL COMMENT '地址',
  `home` varchar(30) DEFAULT NULL COMMENT '住房状况(未知/有房/无房/租房/豪华别墅)',
  `car` varchar(30) DEFAULT NULL COMMENT '车辆状况(未知/有车/无车/自行车/劳斯莱斯/宝马)',
  `marry` varchar(10) DEFAULT NULL COMMENT '婚姻状况(未知/已婚/未婚)',
  `job` varchar(30) DEFAULT NULL COMMENT '职业状况(未知/上班/做生意/无业/公务员)',
  `salary` varchar(30) DEFAULT NULL COMMENT '工资状况(未知/打到银行/发现金/具体的数额)',
  `warranty` varchar(30) DEFAULT NULL COMMENT '保单状况(未知/有/没有)',
  `appointed_time` varchar(30) DEFAULT NULL COMMENT '另约时间(null/当前没空,约定时间)',
  `crt_date` varchar(10) DEFAULT NULL COMMENT '创建日期',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cust_pre_info_idx1` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息';

-- ----------------------------
-- Table structure for robot_call_detail
-- ----------------------------
DROP TABLE IF EXISTS `robot_call_detail`;
CREATE TABLE `robot_call_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_id` varchar(50) DEFAULT NULL COMMENT '会话id',
  `scene` varchar(50) DEFAULT NULL COMMENT '当前场景',
  `human_wav` varchar(100) DEFAULT NULL COMMENT '客户音频',
  `human_say` varchar(512) DEFAULT NULL COMMENT '客户所说内容',
  `say_time` varchar(30) DEFAULT NULL COMMENT '客户通话时间',
  `robot_say` varchar(512) DEFAULT NULL COMMENT '机器人通话内容',
  `type` varchar(10) DEFAULT NULL COMMENT '当前通话状态【共三类:begin,chart,end】',
  `robot_wav` varchar(100) DEFAULT NULL COMMENT '机器人音频',
  `ai_scene` varchar(512) DEFAULT NULL COMMENT 'ai回复信息列表',
  `ai_intent` varchar(30) DEFAULT NULL COMMENT '飞龙识别的意图',
  `domain_type` int(11) DEFAULT NULL COMMENT '(兼容sellbot)流程类型： 1: 主流程   2：一般问题   9：其他',
  `is_refused` int(11) DEFAULT NULL COMMENT '应答类型,0: 不拒绝   1：用户拒绝   9：未应答',
  `hangup_type` int(11) DEFAULT NULL COMMENT '挂断类型 0: 未挂断   1：用户挂断   2：AI挂断',
  `match_type` int(11) DEFAULT NULL COMMENT '匹配类型：0:识别为空,1:未匹配,2：关键词匹配,3：静音超时,4：忽略,5：轮数超限,10：无匹配默认肯定,11：说''啊''默认肯定,12：无匹配且小于4个字默认肯定,99：未分类',
  `wav_id` varchar(30) DEFAULT NULL COMMENT '机器人音频id',
  `crt_date` varchar(10) DEFAULT NULL COMMENT '创建日期',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cust_pre_info_idx1` (`seq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通话详情';

-- ----------------------------
-- Table structure for robot_call_his
-- ----------------------------
DROP TABLE IF EXISTS `robot_call_his`;
CREATE TABLE `robot_call_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_id` varchar(50) DEFAULT NULL COMMENT '会话id',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `org_code` varchar(30) NOT NULL COMMENT '机构编号',
  `ai_no` varchar(50) DEFAULT NULL COMMENT '机器人编号',
  `phone_no` varchar(11) NOT NULL COMMENT '电话号码',
  `assign_time` datetime DEFAULT NULL COMMENT '分配时间',
  `call_date` varchar(10) DEFAULT NULL COMMENT '通话日期',
  `template_id` varchar(50) DEFAULT NULL COMMENT '话术模板',
  `call_status` int(11) DEFAULT NULL COMMENT '通话状态:2-通话完成,1-通话中',
  `is_tts` int(11) DEFAULT NULL COMMENT '是否需要TTS',
  `dialogCount` int(11) DEFAULT NULL,
  `industry` varchar(50) DEFAULT NULL COMMENT '行业',
  `model_id` int(11) DEFAULT NULL COMMENT '模型编号',
  `intent_level` varchar(3) DEFAULT NULL COMMENT '意向标签',
  `reason` varchar(100) DEFAULT NULL,
  `call_wav` varchar(100) DEFAULT NULL COMMENT '通话录音',
  `crt_date` varchar(10) DEFAULT NULL COMMENT '创建日期',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `robot_callback_his_idx1` (`seq_id`),
  KEY `robot_callback_his_idx2` (`assign_time`),
  KEY `robot_callback_his_idx3` (`user_id`),
  KEY `robot_callback_his_idx4` (`phone_no`)
) ENGINE=InnoDB AUTO_INCREMENT=553284 DEFAULT CHARSET=utf8 COMMENT='通话历史';

-- ----------------------------
-- Table structure for robot_call_his_20190117
-- ----------------------------
DROP TABLE IF EXISTS `robot_call_his_20190117`;
CREATE TABLE `robot_call_his_20190117` (
  `id` int(11) NOT NULL,
  `seq_id` varchar(50) DEFAULT NULL COMMENT '会话id',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `org_code` varchar(50) DEFAULT NULL COMMENT '机构号',
  `ai_no` varchar(50) DEFAULT NULL COMMENT '机器人编号',
  `assign_time` datetime DEFAULT NULL COMMENT '分配时间',
  `template_id` varchar(50) DEFAULT NULL COMMENT '话术模板',
  `call_status` varchar(3) DEFAULT NULL COMMENT '通话状态:S-通话完成,I-通话中',
  `sellbot_callback_json` text COMMENT 'sellbot回调报文',
  `crt_date` varchar(10) DEFAULT NULL COMMENT '创建日期',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for robot_call_process_stat
-- ----------------------------
DROP TABLE IF EXISTS `robot_call_process_stat`;
CREATE TABLE `robot_call_process_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `stat_date` varchar(10) DEFAULT NULL COMMENT '统计日期',
  `template_id` varchar(50) DEFAULT NULL COMMENT '模板编号',
  `ai_answer` varchar(1024) DEFAULT NULL COMMENT 'AI话术',
  `current_domain` varchar(50) DEFAULT NULL COMMENT '当前域',
  `domain_type` varchar(3) DEFAULT NULL COMMENT '域类型 1-主流程;2-一般问题;9-其他',
  `total_stat` int(11) DEFAULT NULL COMMENT '总数统计',
  `refused_stat` varchar(100) DEFAULT NULL COMMENT '拒绝统计(0-不拒绝,1-用户拒绝;9-未应答)',
  `hangup_stat` varchar(100) DEFAULT NULL COMMENT '挂断统计(0-未挂断   1：用户挂断   2：AI挂断)',
  `match_stat` varchar(100) DEFAULT NULL COMMENT '匹配统计',
  `org_code` varchar(30) NOT NULL COMMENT '机构号',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `robot_call_process_stat_idx1` (`user_id`),
  KEY `robot_call_process_stat_idx2` (`template_id`),
  KEY `robot_call_process_stat_idx3` (`stat_date`),
  KEY `robot_call_process_stat_idx4` (`org_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3198 DEFAULT CHARSET=utf8 COMMENT='机器人通话流程分析';
