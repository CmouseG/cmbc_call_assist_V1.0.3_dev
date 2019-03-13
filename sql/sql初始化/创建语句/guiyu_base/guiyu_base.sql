/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_base

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-12 17:07:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for receive_message
-- ----------------------------
DROP TABLE IF EXISTS `receive_message`;
CREATE TABLE `receive_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `message_id` bigint(20) unsigned NOT NULL COMMENT '消息id',
  `receive_by` bigint(20) unsigned NOT NULL COMMENT '接收人',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '读取状态0未读1已读',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息读取表';

-- ----------------------------
-- Table structure for send_message
-- ----------------------------
DROP TABLE IF EXISTS `send_message`;
CREATE TABLE `send_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '消息类型0公共消息',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for sys_announcement
-- ----------------------------
DROP TABLE IF EXISTS `sys_announcement`;
CREATE TABLE `sys_announcement` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(150) DEFAULT NULL COMMENT '公告标题',
  `type` int(11) DEFAULT '0' COMMENT '公告类型:0无类型1喜报2升级通知3服务通知',
  `content` varchar(200) DEFAULT NULL COMMENT '公告详情',
  `content_style` varchar(3000) DEFAULT NULL,
  `read_count` int(11) DEFAULT '0' COMMENT '阅读量',
  `file_paths` varchar(2000) DEFAULT NULL COMMENT '文件下载地址集合，多个文件|分隔',
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标识:0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统公告';

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT 'app名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_key` varchar(20) NOT NULL COMMENT '标签名',
  `dict_value` varchar(255) NOT NULL COMMENT '数据值',
  `dict_type` varchar(20) NOT NULL COMMENT '类型',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `busi_id` varchar(128) DEFAULT NULL COMMENT '业务id',
  `busi_type` char(20) DEFAULT NULL COMMENT '业务类型',
  `file_type` char(20) DEFAULT NULL COMMENT '文件类型',
  `file_size` double(10,4) DEFAULT NULL COMMENT '文件大小',
  `sk_url` varchar(255) DEFAULT NULL COMMENT '文件上传后下载地址',
  `sk_thumb_image_url` varchar(255) DEFAULT NULL COMMENT '图片缩略图上传后下载地址',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统服务编号,如用户中心01',
  `crt_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `lst_update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `lst_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) COMMENT '主键'
) ENGINE=InnoDB AUTO_INCREMENT=491689 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT 'app名称',
  `description` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `is_show` int(11) DEFAULT '0' COMMENT '是否展示0是1否',
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '资源类型1菜单2按钮',
  `level` int(11) NOT NULL COMMENT '资源层级',
  `appid` int(11) DEFAULT '0' COMMENT 'appid关联sys_app主键',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4684 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '组织名称',
  `code` varchar(32) DEFAULT NULL COMMENT '组织编码',
  `type` int(11) NOT NULL COMMENT '组织类型1代理商2企业',
  `robot` int(11) DEFAULT '0' COMMENT '机器人数量',
  `line` int(11) DEFAULT '0' COMMENT '线路数量',
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `open` int(11) NOT NULL COMMENT '0未开户1已开户',
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_id` bigint(20) NOT NULL,
  `update_time` datetime NOT NULL,
  `sub_code` varchar(32) DEFAULT NULL COMMENT '子编码',
  `botstence` int(11) DEFAULT '0' COMMENT '话术数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organization_industry
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_industry`;
CREATE TABLE `sys_organization_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `industry_id` varchar(32) NOT NULL COMMENT '行业id',
  `organization_id` int(11) NOT NULL COMMENT '企业id',
  `org_code` varchar(30) NOT NULL COMMENT '企业code',
  `create_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2820 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_organization_product
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_product`;
CREATE TABLE `sys_organization_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `organization_id` int(11) NOT NULL COMMENT '组织id',
  `product` int(11) NOT NULL COMMENT '产品id',
  `create_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_process
-- ----------------------------
DROP TABLE IF EXISTS `sys_process`;
CREATE TABLE `sys_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(32) DEFAULT NULL COMMENT '进程ip',
  `port` varchar(10) DEFAULT NULL COMMENT '端口',
  `name` varchar(16) DEFAULT NULL COMMENT '进程名称',
  `type` int(11) DEFAULT NULL COMMENT '进程类型0:TTS,1:SELLBOT,99:AGENT',
  `process_key` varchar(255) DEFAULT NULL COMMENT '扩展字段，type为TTS时存模型名称',
  `status` int(11) DEFAULT NULL COMMENT '状态0UP,1DOWN,2BUSY,3MISSING',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_id` bigint(20) DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2051 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_process_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_process_task`;
CREATE TABLE `sys_process_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `process_id` int(11) DEFAULT NULL COMMENT '进程id',
  `ip` varchar(32) DEFAULT NULL COMMENT '进程ip',
  `port` varchar(10) DEFAULT NULL,
  `cmd_type` int(11) DEFAULT NULL COMMENT '命令类型-1UNKNOWN1STOP2HEALTH3RESTART4REGISTER5RESTORE_MODEL6UNREGISTER7AGENTREGISTER8PULBLISH_SELLBOT_BOTSTENCE9PULBLISH_FREESWITCH_BOTSTENCE10START',
  `process_key` varchar(64) DEFAULT NULL COMMENT '扩展字段，type为TTS时存模型',
  `parameters` varchar(255) DEFAULT NULL COMMENT '命令参数',
  `result` int(11) DEFAULT NULL COMMENT '命令执行结果',
  `result_content` varchar(255) DEFAULT NULL,
  `exec_status` int(11) DEFAULT NULL COMMENT '执行状态:0执行结束1执行中',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `req_key` varchar(32) DEFAULT NULL COMMENT '请求key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3460 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(255) DEFAULT NULL,
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `init_role` int(1) DEFAULT NULL COMMENT '是否是初始化角色0是1不是，初始化数据不能删除',
  `super_admin` int(1) DEFAULT NULL COMMENT '是否是超级管理员0是1不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=563 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `status` int(11) DEFAULT '1' COMMENT '1正常2冻结',
  `push_type` int(11) DEFAULT NULL COMMENT '1表示平台推送，2表示主动获取',
  `call_record_url` varchar(255) DEFAULT NULL COMMENT '通话记录推送地址',
  `batch_record_url` varchar(255) DEFAULT NULL COMMENT '批次结束推送地址',
  `inten_label` varchar(255) DEFAULT '' COMMENT '意向标签',
  `access_key` varchar(255) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `org_code` varchar(30) NOT NULL COMMENT '企业code',
  `create_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `vaild_time` date DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `is_desensitization` int(11) DEFAULT '0' COMMENT '号码是否脱敏0需要脱敏1不需要脱敏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_action`;
CREATE TABLE `sys_user_action` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opera_type` varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型',
  `opera_target` varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户ID',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `data` text COMMENT '用户提交的数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2199 DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';

-- ----------------------------
-- Table structure for sys_user_ext
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_ext`;
CREATE TABLE `sys_user_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(13) DEFAULT NULL COMMENT '联系电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `api_url` varchar(255) DEFAULT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信号',
  `wechat_openid` varchar(64) DEFAULT NULL COMMENT '微信openid',
  `wechat_status` int(11) DEFAULT '0' COMMENT '微信绑定状态0未绑定1已绑定2已解绑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login_record`;
CREATE TABLE `sys_user_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_times` int(11) NOT NULL COMMENT '账号登录次数',
  `last_login_ip` varchar(32) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登陆记录表';

-- ----------------------------
-- Table structure for user_api
-- ----------------------------
DROP TABLE IF EXISTS `user_api`;
CREATE TABLE `user_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `api_url` varchar(255) NOT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
