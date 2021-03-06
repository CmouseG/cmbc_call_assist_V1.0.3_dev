/*
Navicat MySQL Data Transfer

Source Server         : tBtest
Source Server Version : 50722
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_base

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-11 14:57:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for receive_message
-- ----------------------------
DROP TABLE IF EXISTS `receive_message`;
CREATE TABLE `receive_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) unsigned NOT NULL COMMENT '消息id',
  `receive_by` bigint(20) unsigned NOT NULL COMMENT '接收人',
  `status` char(2) NOT NULL COMMENT '是否读取状态',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息读取表';

-- ----------------------------
-- Table structure for send_message
-- ----------------------------
DROP TABLE IF EXISTS `send_message`;
CREATE TABLE `send_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `type` varchar(10) NOT NULL COMMENT '类型',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dict_key` varchar(20) NOT NULL COMMENT '标签名',
  `dict_value` varchar(255) NOT NULL COMMENT '数据值',
  `dict_type` varchar(20) NOT NULL COMMENT '类型',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `pid` bigint(20) unsigned DEFAULT NULL COMMENT '父级编号',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(64) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `busi_id` varchar(255) DEFAULT NULL,
  `busi_type` char(20) DEFAULT NULL,
  `file_type` char(20) DEFAULT NULL,
  `file_size` double(10,4) DEFAULT NULL,
  `sk_url` varchar(255) DEFAULT NULL,
  `sk_thumb_image_url` varchar(255) DEFAULT NULL,
  `sys_code` varchar(255) DEFAULT NULL,
  `crt_user` varchar(255) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `lst_update_user` varchar(255) DEFAULT NULL,
  `lst_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `is_show` bigint(20) DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` char(2) NOT NULL,
  `level` char(2) NOT NULL,
  `appid` bigint(20) unsigned DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL,
  `menu_id` bigint(20) unsigned NOT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1803 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '企业名称',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `type` varchar(1) NOT NULL COMMENT '类型',
  `robot` varchar(255) DEFAULT NULL COMMENT '机器人数量',
  `line` varchar(255) DEFAULT NULL COMMENT '线路数量',
  `del_flag` varchar(1) NOT NULL COMMENT '删除标志',
  `open` varchar(1) DEFAULT NULL,
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_id` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_process
-- ----------------------------
DROP TABLE IF EXISTS `sys_process`;
CREATE TABLE `sys_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  `port` varchar(10) DEFAULT NULL COMMENT '端口',
  `name` varchar(255) DEFAULT NULL COMMENT '进程名',
  `type` int(10) DEFAULT NULL COMMENT '进程类型：0:TTS 1:SELLBOT 99:AGENT ',
  `process_key` varchar(255) DEFAULT NULL COMMENT '扩展字段，type为TTS时存模型名称',
  `status` int(2) DEFAULT NULL COMMENT '状态0:UP1:DOWN2:BUSY3:MISSING',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1266 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_process_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_process_task`;
CREATE TABLE `sys_process_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_id` bigint(20) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `port` varchar(10) DEFAULT NULL,
  `cmd_type` int(10) DEFAULT NULL COMMENT '命令类型-1UNKNOWN1STOP2HEALTH3RESTART4REGISTER5RESTORE_MODEL6UNREGISTER7AGENTREGISTER8PULBLISH_SELLBOT_BOTSTENCE9PULBLISH_FREESWITCH_BOTSTENCE10START',
  `process_key` varchar(255) DEFAULT NULL COMMENT '扩展字段，type为TTS时存模型',
  `parameters` varchar(255) DEFAULT NULL COMMENT '命令参数',
  `result` varchar(255) DEFAULT NULL COMMENT '执行结果',
  `result_content` varchar(255) DEFAULT NULL,
  `exec_status` int(2) DEFAULT NULL COMMENT '执行状态:0执行结束1执行中',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `req_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1056 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '名称',
  `desc` varchar(255) DEFAULT NULL,
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `init_role` int(1) DEFAULT NULL COMMENT '是否是初始化角色0是1不是，初始化数据不能删除',
  `super_admin` int(1) DEFAULT NULL COMMENT '是否是超级管理员0是1不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `password` varchar(512) NOT NULL COMMENT '密码',
  `status` int(10) DEFAULT '1' COMMENT '1正常2冻结',
  `push_type` varchar(1) DEFAULT NULL COMMENT '1表示平台推送，2表示主动获取',
  `call_record_url` varchar(255) DEFAULT NULL COMMENT '通话记录推送地址',
  `batch_record_url` varchar(255) DEFAULT NULL COMMENT '批次结束推送地址',
  `inten_label` varchar(255) DEFAULT '' COMMENT '意向标签',
  `access_key` varchar(255) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL COMMENT '企业code',
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `vaild_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for sys_user_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_action`;
CREATE TABLE `sys_user_action` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `OperaType` varchar(32) NOT NULL DEFAULT '' COMMENT '操作',
  `OperaTarget` varchar(32) NOT NULL DEFAULT '' COMMENT '操作对象',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户ID',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `data` text COMMENT '用户提交的数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';

-- ----------------------------
-- Table structure for sys_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_ext`;
CREATE TABLE `sys_user_ext` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `api_url` varchar(255) NOT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login_record`;
CREATE TABLE `sys_user_login_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login_times` bigint(20) unsigned NOT NULL COMMENT '账号登录次数',
  `last_login_ip` bigint(20) unsigned NOT NULL COMMENT '最后登录IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登陆记录表';

-- ----------------------------
-- Table structure for user_api
-- ----------------------------
DROP TABLE IF EXISTS `user_api`;
CREATE TABLE `user_api` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `api_url` varchar(512) NOT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
