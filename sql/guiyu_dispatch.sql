/*
Navicat MySQL Data Transfer

Source Server         : 测试环境
Source Server Version : 50707
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-03 17:36:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dispatch_hour
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_hour`;
CREATE TABLE `dispatch_hour` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dispatch_id` varchar(1024) DEFAULT NULL,
  `gmt_create` datetime NOT NULL COMMENT '0未打电话，1打电话，为后面清洗数据做准备',
  `is_call` int(11) NOT NULL,
  `hour` int(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dispatch_hour
-- ----------------------------

-- ----------------------------
-- Table structure for dispatch_log
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_log`;
CREATE TABLE `dispatch_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `module` varchar(1024) NOT NULL COMMENT '模块',
  `action` varchar(1024) NOT NULL COMMENT '动作',
  `params` text COMMENT '参数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13201 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户操作日志';

-- ----------------------------
-- Records of dispatch_log
-- ----------------------------

-- ----------------------------
-- Table structure for dispatch_plan
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan`;
CREATE TABLE `dispatch_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `plan_uuid` char(32) DEFAULT NULL COMMENT '任务UUID;任务全局唯一ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `batch_id` int(11) DEFAULT NULL COMMENT '批次ID;批次ID',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `attach` varchar(50) DEFAULT NULL COMMENT '附加参数;可以作为第三方系统的唯一标识',
  `params` varchar(200) DEFAULT NULL COMMENT '变量;多个变量用|分隔',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(32) DEFAULT NULL COMMENT '呼叫线路',
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(1024) DEFAULT NULL COMMENT '外呼时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(4) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `replay_type` int(11) DEFAULT NULL,
  `is_del` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan
-- ----------------------------

-- ----------------------------
-- Table structure for dispatch_plan_0
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan_0`;
CREATE TABLE `dispatch_plan_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `plan_uuid` char(32) DEFAULT NULL COMMENT '任务UUID;任务全局唯一ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `batch_id` int(11) DEFAULT NULL COMMENT '批次ID;批次ID',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `attach` varchar(50) DEFAULT NULL COMMENT '附加参数;可以作为第三方系统的唯一标识',
  `params` varchar(200) DEFAULT NULL COMMENT '变量;多个变量用|分隔',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(32) DEFAULT NULL COMMENT '呼叫线路',
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(1024) DEFAULT NULL COMMENT '外呼时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(4) DEFAULT '0',
  `replay_type` int(11) DEFAULT NULL COMMENT '重播类型 0一般任务 1 重播任务',
  `is_del` int(11) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=438 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan_0
-- ----------------------------
INSERT INTO `dispatch_plan_0` VALUES ('434', 'e33df51210e842d492d7ef754ef8d094', '16', '231', '13961289123', null, '123|123', '1', '0', null, null, 'ttscs_46188_en', '189', null, null, '0', '20181130', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-11-29 17:10:38', '2018-11-30 18:02:10', '0', '0', '1', 'admin', '一条线路2211', 'tts测试', '12321', '1');
INSERT INTO `dispatch_plan_0` VALUES ('435', 'e33df51210e842d492d7ef754ef8d094', '16', '231', '13961289123', null, '123|123', '1', '0', null, null, 'ttscs_46188_en', '189', null, null, '0', '20181130', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-11-30 17:58:51', '2018-11-30 18:02:10', '0', '0', '1', 'admin', '一条线路2211', 'tts测试', '12321', '1');
INSERT INTO `dispatch_plan_0` VALUES ('436', 'e33df51210e842d492d7ef754ef8d094', '16', '235', '13961289123', null, '1111|2222', '1', '0', null, null, 'ttscs_46188_en', '181', null, null, '0', '20181203', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-12-03 16:53:38', '2018-12-03 16:53:38', '0', '0', '0', null, '测试001', 'tts测试', '11123', '1');
INSERT INTO `dispatch_plan_0` VALUES ('437', 'e33df51210e842d492d7ef754ef8d094', '16', '236', '13961289123', null, '1232', '1', '0', null, null, 'bb_16744_en', '181', null, null, '0', '20181203', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-12-03 17:13:27', '2018-12-03 17:13:27', '0', '0', '0', 'admin', '测试001', '用这个测试模板', '12321312', '1');

-- ----------------------------
-- Table structure for dispatch_plan_1
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan_1`;
CREATE TABLE `dispatch_plan_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `plan_uuid` char(32) DEFAULT NULL COMMENT '任务UUID;任务全局唯一ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `batch_id` int(11) DEFAULT NULL COMMENT '批次ID;批次ID',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `attach` varchar(50) DEFAULT NULL COMMENT '附加参数;可以作为第三方系统的唯一标识',
  `params` varchar(200) DEFAULT NULL COMMENT '变量;多个变量用|分隔',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(32) DEFAULT NULL COMMENT '呼叫线路',
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(1024) DEFAULT NULL COMMENT '外呼时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(4) DEFAULT '0',
  `replay_type` int(11) DEFAULT NULL,
  `is_del` int(11) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan_1
-- ----------------------------

-- ----------------------------
-- Table structure for dispatch_plan_2
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan_2`;
CREATE TABLE `dispatch_plan_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `plan_uuid` char(32) DEFAULT NULL COMMENT '任务UUID;任务全局唯一ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `batch_id` int(11) DEFAULT NULL COMMENT '批次ID;批次ID',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `attach` varchar(50) DEFAULT NULL COMMENT '附加参数;可以作为第三方系统的唯一标识',
  `params` varchar(200) DEFAULT NULL COMMENT '变量;多个变量用|分隔',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(32) DEFAULT NULL COMMENT '呼叫线路',
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(1024) DEFAULT NULL COMMENT '外呼时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(4) DEFAULT '0',
  `replay_type` int(11) DEFAULT NULL,
  `is_del` int(11) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan_2
-- ----------------------------
INSERT INTO `dispatch_plan_2` VALUES ('151', 'e33df51210e842d492d7ef754ef8d094', '16', '231', '18913828775', null, '1111;2222', '1', '0', null, null, 'asda_27953_en', '192', null, null, '0', '20181129', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-11-29 17:10:39', '2018-11-30 18:02:10', '0', '0', '1', 'admin', 'asdasdsa', 'asda', '2018112917', '1');
INSERT INTO `dispatch_plan_2` VALUES ('152', 'e33df51210e842d492d7ef754ef8d094', '16', '231', '18913828775', null, '1111;2222', '1', '0', null, null, 'asda_27953_en', '192', null, null, '0', '20181129', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-11-30 18:01:43', '2018-11-30 18:01:43', '0', '0', '0', 'admin', 'asdasdsa', 'asda', '2018112917', '1');
INSERT INTO `dispatch_plan_2` VALUES ('153', 'a9c18b4d79b24584a0a6b2813797421a', '16', '237', '18913828775', null, '1111;2222', '3', '0', null, null, 'asda_27953_en', '192', null, null, '0', '20181129', '9,10,11,12,13,14,15,16,17,18,19,20', '2018-12-03 17:30:35', '2018-12-03 17:34:56', '0', '0', '0', 'admin', 'asdasdsa', 'asda', '12321', '0');

-- ----------------------------
-- Table structure for dispatch_plan_batch
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan_batch`;
CREATE TABLE `dispatch_plan_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID;用户ID',
  `name` varchar(128) DEFAULT NULL COMMENT '批次号;批次号',
  `status_show` int(1) DEFAULT NULL COMMENT '是否显示;显示状态1显示0隐藏',
  `status_notify` int(1) DEFAULT NULL COMMENT '通知状态;通知状态1等待2失败3成功',
  `times` int(1) DEFAULT NULL COMMENT '通知次数;通知次数',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间;创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间;更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划批次信息';
