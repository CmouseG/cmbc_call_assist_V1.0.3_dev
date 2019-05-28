/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-13 11:40:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for black_list
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) DEFAULT NULL,
  `remark` varchar(32) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `user_id` int(11) DEFAULT '0' COMMENT '用户id',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改userid',
  `org_code` varchar(30) NOT NULL COMMENT '批次名字',
  `create_user_name` varchar(32) DEFAULT '0' COMMENT '创建用户名',
  `update_user_name` varchar(32) DEFAULT '0' COMMENT '修改用户名字',
  `status` varchar(8) DEFAULT '0' COMMENT '0未删除1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1738 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for black_list_records
-- ----------------------------
DROP TABLE IF EXISTS `black_list_records`;
CREATE TABLE `black_list_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2232 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_hour
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_hour`;
CREATE TABLE `dispatch_hour` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dispatch_id` varchar(1024) DEFAULT NULL,
  `gmt_create` datetime NOT NULL COMMENT '0未打电话，1打电话，为后面清洗数据做准备',
  `is_call` int(11) DEFAULT NULL COMMENT '是否拨打0未拨打1拨打',
  `hour` int(11) DEFAULT NULL COMMENT '拨打时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_lines_0
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_lines_0`;
CREATE TABLE `dispatch_lines_0` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `planuuid` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `line_amount` decimal(10,4) DEFAULT NULL,
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_lines_0_planuuid` (`planuuid`)
) ENGINE=InnoDB AUTO_INCREMENT=367578 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_lines_1
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_lines_1`;
CREATE TABLE `dispatch_lines_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `planuuid` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `line_amount` decimal(10,4) DEFAULT NULL,
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_lines_1_planuuid` (`planuuid`)
) ENGINE=InnoDB AUTO_INCREMENT=337718 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_lines_2
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_lines_2`;
CREATE TABLE `dispatch_lines_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `planuuid` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `line_amount` decimal(10,4) DEFAULT NULL,
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_lines_2_planuuid` (`planuuid`)
) ENGINE=InnoDB AUTO_INCREMENT=309332 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_lines_3
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_lines_3`;
CREATE TABLE `dispatch_lines_3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `planuuid` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `line_amount` decimal(10,4) DEFAULT NULL,
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_lines_3_planuuid` (`planuuid`)
) ENGINE=InnoDB AUTO_INCREMENT=203838 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_lines_4
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_lines_4`;
CREATE TABLE `dispatch_lines_4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `planuuid` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `line_amount` decimal(10,4) DEFAULT NULL,
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_lines_4_planuuid` (`planuuid`)
) ENGINE=InnoDB AUTO_INCREMENT=83150 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dispatch_log
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_log`;
CREATE TABLE `dispatch_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `module` varchar(18) DEFAULT NULL COMMENT '模块名称',
  `action` varchar(18) DEFAULT NULL COMMENT '模块动作',
  `params` text COMMENT '参数',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户操作日志';

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
  `attach` varchar(255) DEFAULT NULL COMMENT '附加参数，备注',
  `params` varchar(32) DEFAULT NULL COMMENT '参数',
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
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dispatch_plan_plan_uuid` (`plan_uuid`),
  KEY `idx_dispatch_plan_robot` (`robot`),
  KEY `idx_dispatch_plan_batch_id` (`batch_id`),
  KEY `idx_dispatch_plan_call_data` (`call_data`),
  KEY `idx_dispatch_plan_status_plan` (`status_plan`),
  KEY `idx_dispatch_plan_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

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
  `attach` varchar(255) DEFAULT NULL COMMENT '附加参数，备注',
  `params` varchar(32) DEFAULT NULL COMMENT '参数',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(11) DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(64) NOT NULL COMMENT '拨打时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(11) NOT NULL COMMENT '是否tts合成',
  `replay_type` int(11) DEFAULT NULL COMMENT '重播类型 0一般任务 1 重播任务',
  `is_del` int(11) NOT NULL COMMENT '0否1是',
  `username` varchar(32) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL COMMENT '组织编码',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名',
  `city_code` varchar(32) DEFAULT NULL COMMENT '城市code',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dispatch_plan_plan_uuid` (`plan_uuid`) USING BTREE,
  KEY `idx_dispatch_plan_0_plan_uuid` (`plan_uuid`),
  KEY `idx_dispatch_plan_0_robot` (`robot`),
  KEY `idx_dispatch_plan_0_org_code` (`org_code`),
  KEY `idx_dispatch_plan_0_batch_id` (`batch_id`),
  KEY `idx_dispatch_plan_0_call_data` (`call_data`),
  KEY `idx_dispatch_plan_0_status_plan` (`status_plan`),
  KEY `idx_dispatch_plan_0_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=562182 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

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
  `attach` varchar(255) DEFAULT NULL COMMENT '附加参数，备注',
  `params` varchar(32) DEFAULT NULL COMMENT '参数',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(11) DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(64) NOT NULL COMMENT '拨打时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(11) NOT NULL COMMENT '是否tts合成',
  `replay_type` int(11) DEFAULT NULL,
  `is_del` int(11) NOT NULL COMMENT '是否删除0否1是',
  `username` varchar(32) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL COMMENT '组织编码',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名',
  `city_code` varchar(32) DEFAULT NULL COMMENT '城市code',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_dispatch_plan_plan_uuid` (`plan_uuid`) USING BTREE,
  KEY `idx_dispatch_plan_1_plan_uuid` (`plan_uuid`),
  KEY `idx_dispatch_plan_1_robot` (`robot`),
  KEY `idx_dispatch_plan_1_org_code` (`org_code`),
  KEY `idx_dispatch_plan_1_batch_id` (`batch_id`),
  KEY `idx_dispatch_plan_1_call_data` (`call_data`),
  KEY `idx_dispatch_plan_1_status_plan` (`status_plan`),
  KEY `idx_dispatch_plan_1_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=562097 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

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
  `attach` varchar(255) DEFAULT NULL COMMENT '附加参数，备注',
  `params` varchar(32) DEFAULT NULL COMMENT '参数',
  `status_plan` int(1) DEFAULT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` int(1) DEFAULT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` int(1) DEFAULT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) DEFAULT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) DEFAULT NULL COMMENT '呼叫机器人',
  `line` int(11) DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL COMMENT '呼出结果',
  `call_agent` varchar(32) DEFAULT NULL COMMENT '转人工坐席号',
  `clean` int(1) DEFAULT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) DEFAULT NULL COMMENT '外呼日期',
  `call_hour` varchar(64) NOT NULL COMMENT '拨打时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `is_tts` int(11) NOT NULL COMMENT '是否tts合成',
  `replay_type` int(11) DEFAULT NULL,
  `is_del` int(11) NOT NULL COMMENT '是否删除0否1是',
  `username` varchar(30) DEFAULT NULL,
  `line_name` varchar(32) DEFAULT NULL,
  `robot_name` varchar(32) DEFAULT NULL,
  `batch_name` varchar(32) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL COMMENT '组织编码',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名',
  `city_code` varchar(32) DEFAULT NULL COMMENT '城市code',
  `line_type` int(1) NOT NULL DEFAULT '1' COMMENT '线路类型，1-SIP，2-网关',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_dispatch_plan_plan_uuid` (`plan_uuid`) USING BTREE,
  KEY `idx_dispatch_plan_2_plan_uuid` (`plan_uuid`),
  KEY `idx_dispatch_plan_2_robot` (`robot`),
  KEY `idx_dispatch_plan_2_org_code` (`org_code`),
  KEY `idx_dispatch_plan_2_batch_id` (`batch_id`),
  KEY `idx_dispatch_plan_2_call_data` (`call_data`),
  KEY `idx_dispatch_plan_2_status_plan` (`status_plan`),
  KEY `idx_dispatch_plan_2_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=560795 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划任务';

-- ----------------------------
-- Table structure for dispatch_plan_batch
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_plan_batch`;
CREATE TABLE `dispatch_plan_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` varchar(4) DEFAULT NULL COMMENT '批次id',
  `name` varchar(32) DEFAULT NULL COMMENT '批次名',
  `status_show` int(1) DEFAULT NULL COMMENT '是否显示;显示状态1显示0隐藏',
  `status_notify` int(1) DEFAULT NULL COMMENT '通知状态;通知状态1等待2失败3成功',
  `times` int(1) DEFAULT NULL COMMENT '通知次数;通知次数',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间;创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间;更新时间',
  `org_code` varchar(30) NOT NULL COMMENT '组织code',
  `callback_url`  varchar(200) null comment '批次回调地址',
  `total_num`     int          null comment '总数量',
  `single_callback_url` varchar(200) null comment '单个回调地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42311 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划批次信息';

-- ----------------------------
-- Table structure for dispatch_robot_op
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_robot_op`;
CREATE TABLE `dispatch_robot_op` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `botstence_id` varchar(32) DEFAULT NULL COMMENT '话术模板ID',
  `robot_num` int(8) DEFAULT NULL COMMENT '分配模板机器人数',
  `suppl_num` int(8) DEFAULT NULL COMMENT '补充机器人数',
  `suppl_type` int(1) DEFAULT NULL COMMENT '补充标识，1-补充，2-删除',
  `current_num` int(8) DEFAULT NULL COMMENT '当前分配机器人总数',
  `add_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_dispatch_robot_op` (`user_id`,`botstence_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作机器人表';

-- ----------------------------
-- Table structure for file_error_records
-- ----------------------------
DROP TABLE IF EXISTS `file_error_records`;
CREATE TABLE `file_error_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) NOT NULL,
  `attach` varchar(32) NOT NULL,
  `params` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `file_records_id` bigint(20) DEFAULT NULL COMMENT '关联file_records表id',
  `error_type` int(11) DEFAULT NULL COMMENT '错误类型',
  `error_line` int(11) DEFAULT NULL,
  `data_type` int(11) DEFAULT '0' COMMENT '0页面数据，1第三方数据',
  `batch_id` int(11) DEFAULT '0' COMMENT '批次id',
  `batch_name` varchar(32) DEFAULT '0' COMMENT '批次名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341192 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for file_records
-- ----------------------------
DROP TABLE IF EXISTS `file_records`;
CREATE TABLE `file_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batchId` int(11) NOT NULL,
  `batch_name` varchar(32) NOT NULL,
  `file_name` varchar(32) NOT NULL,
  `success_count` int(11) DEFAULT NULL,
  `failure_count` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `org_code` varchar(30) NOT NULL,
  `robot` varchar(32) DEFAULT NULL,
  `line_id` varchar(32) DEFAULT NULL,
  `call_data` varchar(32) NOT NULL,
  `status` varchar(32) DEFAULT NULL,
  `call_hour` varchar(32) NOT NULL,
  `is_clean` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件下载地址',
  `line_name` varchar(32) DEFAULT '0' COMMENT '线路名称',
  `robot_name` varchar(32) DEFAULT '0' COMMENT '机器人名字',
  `user_name` varchar(32) DEFAULT '0' COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for modular_logs
-- ----------------------------
DROP TABLE IF EXISTS `modular_logs`;
CREATE TABLE `modular_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modular_name` int(32) DEFAULT NULL COMMENT '模块名称',
  `status` int(11) DEFAULT NULL COMMENT '处理状态0成功1失败',
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `msg` varchar(128) DEFAULT NULL COMMENT '日志信息',
  `batch_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_PHONE` (`phone`) USING BTREE,
  KEY `IX_PLAN_UUID` (`plan_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3562413 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for modular_logs_20190306
-- ----------------------------
DROP TABLE IF EXISTS `modular_logs_20190306`;
CREATE TABLE `modular_logs_20190306` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modular_name` int(32) DEFAULT NULL COMMENT '模块名称',
  `status` int(11) DEFAULT NULL COMMENT '处理状态0成功1失败',
  `plan_uuid` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `msg` varchar(128) DEFAULT NULL COMMENT '日志信息',
  `batch_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `IX_PHONE` (`phone`) USING BTREE,
  KEY `IX_PLAN_UUID` (`plan_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7191536 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for phone_region
-- ----------------------------
DROP TABLE IF EXISTS `phone_region`;
CREATE TABLE `phone_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone7` varchar(7) DEFAULT NULL COMMENT '手机号前7位',
  `region_code` varchar(10) DEFAULT NULL COMMENT '地区码',
  `region_name` varchar(20) DEFAULT NULL COMMENT '地区名称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_region_idx1` (`phone7`)
) ENGINE=InnoDB AUTO_INCREMENT=86891 DEFAULT CHARSET=utf8 COMMENT='手机号归属地';

-- ----------------------------
-- Table structure for phone_region_error
-- ----------------------------
DROP TABLE IF EXISTS `phone_region_error`;
CREATE TABLE `phone_region_error` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机号归属地异常数据，各种途径都查不到数据后，放到异常表中，然后人工处理';

-- ----------------------------
-- Table structure for push_records
-- ----------------------------
DROP TABLE IF EXISTS `push_records`;
CREATE TABLE `push_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `planuuid` char(32) NOT NULL,
  `phone` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `callback_status` int(11) DEFAULT NULL COMMENT '0代表未回调，1代表已经回调',
  `user_id` int(11) DEFAULT NULL,
  `line` int(11) DEFAULT NULL,
  `robot` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_push_records_planuuid` (`planuuid`),
  KEY `idx_push_records_callback_status` (`callback_status`)
) ENGINE=InnoDB AUTO_INCREMENT=562843 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for send_msg_records
-- ----------------------------
DROP TABLE IF EXISTS `send_msg_records`;
CREATE TABLE `send_msg_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `statusCode` int(11) NOT NULL COMMENT '默认0',
  `statusMsg` varchar(32) NOT NULL COMMENT '状态信息',
  `requestId` varchar(32) NOT NULL COMMENT '请求id',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1074 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '平台id',
  `identification` varchar(50) NOT NULL COMMENT '内部标识',
  `platform_params` varchar(250) NOT NULL COMMENT '配置参数列表',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sms_tunnel
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for third_interface_records
-- ----------------------------
DROP TABLE IF EXISTS `third_interface_records`;
CREATE TABLE `third_interface_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `times` int(32) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1成功 0失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tt1
-- ----------------------------
DROP TABLE IF EXISTS `tt1`;
CREATE TABLE `tt1` (
  `id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_sms_config
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for yw_dispatch_plan_check_result
-- ----------------------------
DROP TABLE IF EXISTS `yw_dispatch_plan_check_result`;
CREATE TABLE `yw_dispatch_plan_check_result` (
  `creatime` datetime NOT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Event structure for yw_dispatch_plan_check_result_event
-- ----------------------------
DROP EVENT IF EXISTS `yw_dispatch_plan_check_result_event`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `yw_dispatch_plan_check_result_event` ON SCHEDULE EVERY 3 MINUTE STARTS '2019-02-28 22:18:09' ON COMPLETION NOT PRESERVE ENABLE DO insert into yw_dispatch_plan_check_result(user_id, user_name,creatime)

SELECT B.user_id,B.username,CURRENT_TIME() FROM(
SELECT A.user_id,A.username FROM dispatch_plan_0 A
WHERE A.call_data=REPLACE(CURDATE(),'-','')
AND  A.is_del=0
AND A.status_plan=1
AND A.flag=2
AND find_in_set(HOUR(CURRENT_TIME()),CONCAT(',',A.call_hour,','))>0
UNION
SELECT A.user_id,A.username FROM dispatch_plan_1 A
WHERE A.call_data=REPLACE(CURDATE(),'-','')
AND  A.is_del=0
AND A.status_plan=1
AND A.flag=2
AND find_in_set(HOUR(CURRENT_TIME()),CONCAT(',',A.call_hour,','))>0
UNION
SELECT A.user_id,A.username FROM dispatch_plan_2 A
WHERE A.call_data=REPLACE(CURDATE(),'-','')
AND  A.is_del=0
AND A.status_plan=1
AND A.flag=2
AND find_in_set(HOUR(CURRENT_TIME()),CONCAT(',',A.call_hour,','))>0
) AS B
WHERE EXISTS
(

SELECT D.user_id 
FROM(
select C.user_id,MAX(C.create_time) AS MAX_TIME
from push_records C
GROUP BY C.user_id
) AS D
WHERE D.user_id=B.user_id
AND timestampdiff(MINUTE,D.MAX_TIME,CURRENT_TIME()) >10
)
;;
DELIMITER ;
