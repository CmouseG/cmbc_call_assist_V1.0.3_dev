/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.81
Source Server Version : 50722
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-21 10:21:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `black_list`
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) DEFAULT NULL,
  `remark` varchar(32) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of black_list
-- ----------------------------

-- ----------------------------
-- Table structure for `dispatch_hour`
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
-- Table structure for `dispatch_log`
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
) ENGINE=InnoDB AUTO_INCREMENT=37751 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户操作日志';

-- ----------------------------
-- Records of dispatch_log
-- ----------------------------

-- ----------------------------
-- Table structure for `dispatch_plan`
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
-- Table structure for `dispatch_plan_0`
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
  `org_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67499 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan_0
-- ----------------------------
-- ----------------------------
-- Table structure for `dispatch_plan_1`
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
  `username` varchar(30) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=172048 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Records of dispatch_plan_1
-- ----------------------------

-- ----------------------------
-- Table structure for `dispatch_plan_2`
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
  `org_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=172048 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划任务';
-- ----------------------------
-- Records of dispatch_plan_2
-- ----------------------------

-- ----------------------------
-- Table structure for `dispatch_plan_batch`
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
  `org_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=448 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='计划批次信息';

-- ----------------------------
-- Records of dispatch_plan_batch
-- ----------------------------
-- ----------------------------
-- Table structure for `file_error_records`
-- ----------------------------
DROP TABLE IF EXISTS `file_error_records`;
CREATE TABLE `file_error_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(1024) NOT NULL,
  `phone` varchar(1024) NOT NULL,
  `attach` varchar(1024) NOT NULL,
  `params` varchar(1024) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_error_records
-- ----------------------------

-- ----------------------------
-- Table structure for `file_records`
-- ----------------------------
DROP TABLE IF EXISTS `file_records`;
CREATE TABLE `file_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batchId` int(100) NOT NULL,
  `batch_name` varchar(2048) NOT NULL,
  `file_name` varchar(1024) NOT NULL,
  `success_count` int(255) DEFAULT NULL,
  `failure_count` int(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `org_code` varchar(1024) DEFAULT NULL,
  `robot` varchar(1024) DEFAULT NULL,
  `line_id` varchar(1024) DEFAULT NULL,
  `call_data` varchar(1024) NOT NULL,
  `status` varchar(1024) NOT NULL,
  `call_hour` varchar(1024) NOT NULL,
  `is_clean` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_records
-- ----------------------------

-- ----------------------------
-- Table structure for `modular_logs`
-- ----------------------------
DROP TABLE IF EXISTS `modular_logs`;
CREATE TABLE `modular_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modular_name` int(32) DEFAULT NULL COMMENT '模块名称',
  `status` int(32) DEFAULT NULL COMMENT '处理状态0：成功 1 失败',
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `msg` varchar(21000) DEFAULT '处理异常信息',
  `batch_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_PHONE` (`phone`) USING BTREE,
  KEY `IX_PLAN_UUID` (`plan_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modular_logs
-- ----------------------------

-- ----------------------------
-- Table structure for `send_msg_records`
-- ----------------------------
DROP TABLE IF EXISTS `send_msg_records`;
CREATE TABLE `send_msg_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `statusCode` varchar(1024) NOT NULL,
  `statusMsg` varchar(1024) NOT NULL,
  `requestId` varchar(1024) NOT NULL,
  `phone` varchar(1024) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of send_msg_records
-- ----------------------------
-- ----------------------------
-- Table structure for `sms_platform`
-- ----------------------------
DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '平台名称',
  `identification` varchar(50) NOT NULL COMMENT '内部标识',
  `platform_params` varchar(250) NOT NULL COMMENT '配置参数列表',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sms_platform
-- ----------------------------
INSERT INTO `sms_platform` VALUES ('1', '云讯科技', 'ytx', 'accountSID,authToken,version,appid', '1', '2018-12-19 16:38:30', '1', '2018-12-19 16:38:34');

-- ----------------------------
-- Table structure for `sms_tunnel`
-- ----------------------------
DROP TABLE IF EXISTS `sms_tunnel`;
CREATE TABLE `sms_tunnel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '所属者',
  `platform_id` int(11) DEFAULT NULL COMMENT '短信平台id',
  `platform_config` text COMMENT '平台必须配置参数，json格式',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sms_tunnel
-- ----------------------------
INSERT INTO `sms_tunnel` VALUES ('1', '1', '1', '{\"accountSID\": \"f94481a09e1c4c4d9a40887a71dc299d\",\"authToken\": \"c9e869e1fb8c49188f5efb19f9725f57\",\"version\": \"201512\",\"appid\": \"ce4063be6e334f0387fa6d987e16a87c\"}', '1', '2018-12-19 16:39:13', '1', '2018-12-19 16:39:16');

-- ----------------------------
-- Table structure for `third_interface_records`
-- ----------------------------
DROP TABLE IF EXISTS `third_interface_records`;
CREATE TABLE `third_interface_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `params` varchar(15000) DEFAULT NULL,
  `times` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of third_interface_records
-- ----------------------------

-- ----------------------------
-- Table structure for `user_sms_config`
-- ----------------------------
DROP TABLE IF EXISTS `user_sms_config`;
CREATE TABLE `user_sms_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '所属者',
  `template_id` varchar(50) DEFAULT NULL COMMENT '话术id',
  `call_result` varchar(100) DEFAULT NULL COMMENT '需要发送短信的通话标签',
  `sms_tunnel_id` int(11) DEFAULT NULL COMMENT '短信通道id',
  `sms_content` text COMMENT '普通短信内容',
  `sms_template_id` int(11) DEFAULT NULL COMMENT '模板短信id',
  `sms_template_data` varchar(255) DEFAULT NULL COMMENT '模板短信变量',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_sms_config
-- ----------------------------
INSERT INTO `user_sms_config` VALUES ('1', '1', 'xyxykhs_en', 'A', '1', '【兴业银行】兴业银行为您送上信用卡邀请函，戳 peath.cn:8081/kdeqw9E 完善信息即可申请，回T退订', '2247', '{}', '1', '2018-12-19 16:44:53', '1', '2018-12-19 16:44:57');
