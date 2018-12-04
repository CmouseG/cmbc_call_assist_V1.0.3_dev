/*
Navicat MySQL Data Transfer

Source Server         : 测试环境
Source Server Version : 50707
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_base

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-03 17:35:21
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
-- Records of receive_message
-- ----------------------------

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
-- Records of send_message
-- ----------------------------

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
-- Records of sys_app
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '0', 'TTS', 'process_type', '资源类型_TTS', null, null, '0', '2018-12-03 14:29:31', '2018-12-03 14:29:31');
INSERT INTO `sys_dict` VALUES ('2', '1', 'SELLBOT', 'process_type', '资源类型_SELLBOT', null, null, '0', '2018-12-03 14:29:59', '2018-12-03 14:29:59');

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
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` char(2) NOT NULL,
  `level` char(2) NOT NULL,
  `appid` bigint(20) unsigned DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('11', '系统管理', null, '/system', '0', null, null, '2018-11-07 14:15:43', '2018-11-23 14:56:04', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('12', '用户管理', null, '/system/user', '11', null, null, '2018-11-07 14:16:20', '2018-11-07 14:16:20', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('15', '机器人中心', null, '/robotCenter', '0', null, null, '2018-11-07 14:32:06', '2018-11-21 16:25:57', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('16', '呼叫中心', null, '/callCenter', '0', null, null, '2018-11-07 14:47:41', '2018-11-07 14:47:41', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('18', '个人中心', null, '/personCenter', '0', null, null, '2018-11-08 09:44:06', '2018-11-20 17:52:39', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('25', '任务中心', null, '/taskCenter', '0', null, null, '2018-11-14 13:09:25', '2018-11-14 13:09:25', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('26', '任务中心', null, '/taskCenter/phonelist', '25', null, null, '2018-11-14 13:10:00', '2018-11-14 13:10:00', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('28', '线路管理', null, '/callCenter/lineInfoList', '16', null, null, '2018-11-14 13:11:30', '2018-11-23 14:54:19', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('29', '通话记录', null, '/callCenter/callHistory', '16', null, null, '2018-11-14 13:12:07', '2018-11-14 13:12:07', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('31', '权限管理', null, '/system/auth', '11', null, null, '2018-11-14 13:14:57', '2018-11-23 14:55:06', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('32', '菜单维护', null, '/system/menu', '11', null, null, '2018-11-14 13:15:14', '2018-11-14 13:15:14', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('35', '话术市场', null, '/botsentence', '0', null, null, '2018-11-14 14:47:02', '2018-11-14 14:47:02', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('36', '话术制作', null, '/botsentence/botsentence_maker', '35', null, null, '2018-11-14 14:47:27', '2018-11-14 14:47:27', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('37', '话术审核', null, '/botsentence/botsentence_approve', '35', null, null, '2018-11-14 14:48:11', '2018-11-14 14:48:11', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('38', '我的模板', null, '/botsentence/botsentence_mytemplate', '35', null, null, '2018-11-14 14:48:41', '2018-11-14 14:48:41', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('44', '修改密码', null, '/personCenter/revisePsw', '18', null, null, '2018-11-20 17:53:55', '2018-11-20 17:53:55', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('45', '机器人管理', null, '/robotCenter/robotManage', '15', null, null, '2018-11-21 16:29:39', '2018-11-21 16:29:39', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('47', '数据字典', null, '/system/dataDictionaries', '11', null, null, '2018-11-22 11:30:54', '2018-11-22 11:30:54', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('48', '个人信息', null, '/personCenter/myselfInfo', '18', null, null, '2018-11-22 17:14:42', '2018-11-22 17:14:42', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('49', '数据获取', null, '/personCenter/getData', '18', null, null, '2018-11-22 17:14:59', '2018-11-22 17:14:59', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('53', '开户', null, '/system/account', '11', null, null, '2018-11-23 10:39:38', '2018-11-23 10:39:38', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('58', '进程管理', null, '/system/processManage', '11', null, null, '2018-11-23 14:57:15', '2018-11-23 14:57:15', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('64', '首页', null, '/', '0', null, null, '2018-11-23 15:07:54', '2018-11-23 15:07:54', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('65', '首页', null, '/home', '64', null, null, '2018-11-23 15:08:04', '2018-11-23 15:08:04', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('67', 'test', null, 'test', '0', null, null, '2018-12-01 18:35:30', '2018-12-01 18:35:30', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('68', '测试', null, '对方水电费是', '0', null, null, '2018-12-02 10:00:38', '2018-12-02 10:00:38', '1', '1', '0', '0', '0');

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL,
  `menu_id` bigint(20) unsigned NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1181 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES ('1', '1', '1', '2018-11-05 16:56:00', '2018-11-05 16:56:02', '0');
INSERT INTO `sys_menu_role` VALUES ('2', '18', '1', '2018-11-08 02:14:12', '2018-11-08 02:14:12', '0');
INSERT INTO `sys_menu_role` VALUES ('3', '18', '2', '2018-11-08 02:14:12', '2018-11-08 02:14:12', '0');
INSERT INTO `sys_menu_role` VALUES ('4', '18', '3', '2018-11-08 02:14:12', '2018-11-08 02:14:12', '0');
INSERT INTO `sys_menu_role` VALUES ('5', '23', '1', '2018-11-08 03:24:58', '2018-11-08 03:24:58', '0');
INSERT INTO `sys_menu_role` VALUES ('6', '23', '2', '2018-11-08 03:24:58', '2018-11-08 03:24:58', '0');
INSERT INTO `sys_menu_role` VALUES ('7', '23', '3', '2018-11-08 03:24:58', '2018-11-08 03:24:58', '0');
INSERT INTO `sys_menu_role` VALUES ('8', '23', '4', '2018-11-08 03:24:58', '2018-11-08 03:24:58', '0');
INSERT INTO `sys_menu_role` VALUES ('9', '24', '9', '2018-11-08 06:15:38', '2018-11-08 06:15:38', '0');
INSERT INTO `sys_menu_role` VALUES ('10', '24', '11', '2018-11-08 06:15:38', '2018-11-08 06:15:38', '0');
INSERT INTO `sys_menu_role` VALUES ('11', '24', '12', '2018-11-08 06:15:38', '2018-11-08 06:15:38', '0');
INSERT INTO `sys_menu_role` VALUES ('12', '24', '16', '2018-11-08 06:15:38', '2018-11-08 06:15:38', '0');
INSERT INTO `sys_menu_role` VALUES ('27', '28', '9', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('28', '28', '11', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('29', '28', '12', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('30', '28', '16', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('31', '28', '9', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('32', '28', '11', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('33', '28', '12', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('34', '28', '15', '2018-11-08 07:31:28', '2018-11-08 07:31:28', '0');
INSERT INTO `sys_menu_role` VALUES ('41', '31', '9', '2018-11-08 07:58:10', '2018-11-08 07:58:10', '0');
INSERT INTO `sys_menu_role` VALUES ('42', '31', '11', '2018-11-08 07:58:10', '2018-11-08 07:58:10', '0');
INSERT INTO `sys_menu_role` VALUES ('43', '31', '12', '2018-11-08 07:58:10', '2018-11-08 07:58:10', '0');
INSERT INTO `sys_menu_role` VALUES ('44', '31', '15', '2018-11-08 07:58:10', '2018-11-08 07:58:10', '0');
INSERT INTO `sys_menu_role` VALUES ('45', '32', '11', '2018-11-08 07:59:05', '2018-11-08 07:59:05', '0');
INSERT INTO `sys_menu_role` VALUES ('46', '32', '12', '2018-11-08 07:59:05', '2018-11-08 07:59:05', '0');
INSERT INTO `sys_menu_role` VALUES ('47', '32', '15', '2018-11-08 07:59:05', '2018-11-08 07:59:05', '0');
INSERT INTO `sys_menu_role` VALUES ('48', '33', '9', '2018-11-08 07:59:48', '2018-11-08 07:59:48', '0');
INSERT INTO `sys_menu_role` VALUES ('49', '33', '11', '2018-11-08 07:59:48', '2018-11-08 07:59:48', '0');
INSERT INTO `sys_menu_role` VALUES ('50', '33', '12', '2018-11-08 07:59:48', '2018-11-08 07:59:48', '0');
INSERT INTO `sys_menu_role` VALUES ('51', '33', '15', '2018-11-08 07:59:48', '2018-11-08 07:59:48', '0');
INSERT INTO `sys_menu_role` VALUES ('52', '34', '9', '2018-11-08 08:01:43', '2018-11-08 08:01:43', '0');
INSERT INTO `sys_menu_role` VALUES ('53', '34', '11', '2018-11-08 08:01:43', '2018-11-08 08:01:43', '0');
INSERT INTO `sys_menu_role` VALUES ('54', '34', '12', '2018-11-08 08:01:43', '2018-11-08 08:01:43', '0');
INSERT INTO `sys_menu_role` VALUES ('55', '34', '15', '2018-11-08 08:01:43', '2018-11-08 08:01:43', '0');
INSERT INTO `sys_menu_role` VALUES ('56', '35', '9', '2018-11-08 08:04:55', '2018-11-08 08:04:55', '0');
INSERT INTO `sys_menu_role` VALUES ('57', '35', '11', '2018-11-08 08:04:55', '2018-11-08 08:04:55', '0');
INSERT INTO `sys_menu_role` VALUES ('58', '35', '12', '2018-11-08 08:04:55', '2018-11-08 08:04:55', '0');
INSERT INTO `sys_menu_role` VALUES ('59', '35', '15', '2018-11-08 08:04:55', '2018-11-08 08:04:55', '0');
INSERT INTO `sys_menu_role` VALUES ('60', '36', '9', '2018-11-08 08:05:40', '2018-11-08 08:05:40', '0');
INSERT INTO `sys_menu_role` VALUES ('61', '36', '11', '2018-11-08 08:05:40', '2018-11-08 08:05:40', '0');
INSERT INTO `sys_menu_role` VALUES ('62', '36', '12', '2018-11-08 08:05:40', '2018-11-08 08:05:40', '0');
INSERT INTO `sys_menu_role` VALUES ('63', '36', '15', '2018-11-08 08:05:40', '2018-11-08 08:05:40', '0');
INSERT INTO `sys_menu_role` VALUES ('64', '37', '16', '2018-11-08 08:09:42', '2018-11-08 08:09:42', '0');
INSERT INTO `sys_menu_role` VALUES ('65', '37', '18', '2018-11-08 08:09:42', '2018-11-08 08:09:42', '0');
INSERT INTO `sys_menu_role` VALUES ('113', '39', '14', '2018-11-09 05:57:56', '2018-11-09 05:57:56', '0');
INSERT INTO `sys_menu_role` VALUES ('114', '39', '15', '2018-11-09 05:57:56', '2018-11-09 05:57:56', '0');
INSERT INTO `sys_menu_role` VALUES ('134', '40', '14', '2018-11-09 11:19:59', '2018-11-09 11:19:59', '0');
INSERT INTO `sys_menu_role` VALUES ('152', '26', '9', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('153', '26', '24', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('154', '26', '16', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('155', '26', '28', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('156', '26', '29', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('157', '26', '30', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('158', '26', '18', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('159', '26', '25', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('160', '26', '26', '2018-11-14 05:58:39', '2018-11-14 05:58:39', '0');
INSERT INTO `sys_menu_role` VALUES ('197', '41', '9', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('198', '41', '11', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('199', '41', '12', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('200', '41', '31', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('201', '41', '32', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('202', '41', '15', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('203', '41', '18', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('204', '41', '25', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('205', '41', '26', '2018-11-14 08:28:24', '2018-11-14 08:28:24', '0');
INSERT INTO `sys_menu_role` VALUES ('206', '12', '11', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('207', '12', '12', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('208', '12', '31', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('209', '12', '32', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('210', '12', '14', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('211', '12', '15', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('212', '12', '16', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('213', '12', '28', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('214', '12', '29', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('215', '12', '30', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('216', '12', '18', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('217', '12', '25', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('218', '12', '26', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('219', '12', '35', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('220', '12', '36', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('221', '12', '37', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('222', '12', '38', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('223', '12', '41', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('224', '12', '42', '2018-11-14 13:24:46', '2018-11-14 13:24:46', '0');
INSERT INTO `sys_menu_role` VALUES ('244', '45', '11', '2018-11-16 02:47:23', '2018-11-16 02:47:23', '0');
INSERT INTO `sys_menu_role` VALUES ('245', '45', '12', '2018-11-16 02:47:23', '2018-11-16 02:47:23', '0');
INSERT INTO `sys_menu_role` VALUES ('246', '45', '31', '2018-11-16 02:47:23', '2018-11-16 02:47:23', '0');
INSERT INTO `sys_menu_role` VALUES ('247', '45', '32', '2018-11-16 02:47:23', '2018-11-16 02:47:23', '0');
INSERT INTO `sys_menu_role` VALUES ('248', '52', '14', '2018-11-18 23:03:10', '2018-11-18 23:03:10', '0');
INSERT INTO `sys_menu_role` VALUES ('254', '27', '15', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('255', '27', '18', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('256', '27', '25', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('257', '27', '26', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('258', '27', '41', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('259', '27', '42', '2018-11-20 00:23:05', '2018-11-20 00:23:05', '0');
INSERT INTO `sys_menu_role` VALUES ('1093', '25', '15', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1094', '25', '45', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1095', '25', '16', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1096', '25', '28', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1097', '25', '29', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1098', '25', '18', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1099', '25', '44', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1100', '25', '48', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1101', '25', '49', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1102', '25', '25', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1103', '25', '26', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1104', '25', '35', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1105', '25', '36', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1106', '25', '64', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1107', '25', '65', '2018-11-26 17:42:00', '2018-11-26 17:42:00', '0');
INSERT INTO `sys_menu_role` VALUES ('1108', '29', '11', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1109', '29', '12', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1110', '29', '31', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1111', '29', '32', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1112', '29', '47', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1113', '29', '53', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1114', '29', '58', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1115', '29', '15', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1116', '29', '45', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1117', '29', '16', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1118', '29', '28', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1119', '29', '29', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1120', '29', '18', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1121', '29', '44', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1122', '29', '48', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1123', '29', '49', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1124', '29', '25', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1125', '29', '26', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1126', '29', '35', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1127', '29', '36', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1128', '29', '37', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1129', '29', '38', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1130', '29', '64', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1131', '29', '65', '2018-11-27 19:19:28', '2018-11-27 19:19:28', '0');
INSERT INTO `sys_menu_role` VALUES ('1132', '30', '11', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1133', '30', '12', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1134', '30', '31', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1135', '30', '32', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1136', '30', '47', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1137', '30', '53', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1138', '30', '58', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1139', '30', '15', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1140', '30', '45', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1141', '30', '16', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1142', '30', '28', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1143', '30', '29', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1144', '30', '18', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1145', '30', '44', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1146', '30', '48', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1147', '30', '49', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1148', '30', '25', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1149', '30', '26', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1150', '30', '35', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1151', '30', '36', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1152', '30', '37', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1153', '30', '38', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1154', '30', '64', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1155', '30', '65', '2018-11-27 23:30:08', '2018-11-27 23:30:08', '0');
INSERT INTO `sys_menu_role` VALUES ('1156', '10', '11', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1157', '10', '12', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1158', '10', '31', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1159', '10', '32', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1160', '10', '47', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1161', '10', '53', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1162', '10', '58', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1163', '10', '15', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1164', '10', '45', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1165', '10', '16', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1166', '10', '28', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1167', '10', '29', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1168', '10', '18', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1169', '10', '44', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1170', '10', '48', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1171', '10', '49', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1172', '10', '25', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1173', '10', '26', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1174', '10', '35', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1175', '10', '36', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1176', '10', '37', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1177', '10', '38', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1178', '10', '64', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1179', '10', '65', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');
INSERT INTO `sys_menu_role` VALUES ('1180', '10', '67', '2018-12-01 02:35:53', '2018-12-01 02:35:53', '0');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1170 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_process_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_process_log`;
CREATE TABLE `sys_process_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_id` bigint(20) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `port` varchar(10) DEFAULT NULL,
  `cmd_type` int(10) DEFAULT NULL COMMENT '命令类型-1UNKNOWN1STOP2HEALTH3RESTART4REGISTER5RESTORE_MODEL6UNREGISTER7AGENTREGISTER8PULBLISH_SELLBOT_BOTSTENCE9PULBLISH_FREESWITCH_BOTSTENCE10START',
  `process_key` varchar(255) DEFAULT NULL COMMENT '扩展字段，type为TTS时存模型',
  `parameters` varchar(255) DEFAULT NULL COMMENT '命令参数',
  `result` varchar(255) DEFAULT NULL COMMENT '执行结果',
  `result_content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `init_role` int(1) DEFAULT NULL COMMENT '是否是初始化角色0是1不是，初始化数据不能删除',
  `super_admin` int(1) DEFAULT NULL COMMENT '是否是超级管理员0是1不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('10', '管理员', null, '2018-12-01 18:36:06', '0', '0', '0');
INSERT INTO `sys_role` VALUES ('25', '运营组', null, '2018-11-27 09:42:06', '0', '1', '1');
INSERT INTO `sys_role` VALUES ('28', '测试', '2018-11-28 11:15:20', '2018-11-28 11:15:20', '0', '1', '1');
INSERT INTO `sys_role` VALUES ('29', '测试', '2018-11-28 11:18:18', '2018-11-28 11:18:18', '0', '1', '1');
INSERT INTO `sys_role` VALUES ('30', 'ces', '2018-11-28 15:30:16', '2018-11-28 15:30:16', '0', '1', '1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '1', '2018-11-05 16:52:59', '2018-11-05 16:53:01', '0');
INSERT INTO `sys_role_user` VALUES ('2', '2', '1', '2018-11-05 16:52:59', '2018-11-05 16:53:01', '0');
INSERT INTO `sys_role_user` VALUES ('3', '4', '2', '2018-11-07 10:40:32', '2018-11-07 10:40:32', '0');
INSERT INTO `sys_role_user` VALUES ('4', '5', '1', '2018-09-01 19:06:04', '2018-11-07 19:06:12', '0');
INSERT INTO `sys_role_user` VALUES ('5', '10', '12', '2018-11-08 05:50:37', '2018-11-08 05:50:37', '0');
INSERT INTO `sys_role_user` VALUES ('6', '11', '1', '2018-11-08 05:51:02', '2018-11-08 05:51:02', '0');
INSERT INTO `sys_role_user` VALUES ('7', '12', '12', '2018-11-08 05:51:58', '2018-11-08 05:51:58', '0');
INSERT INTO `sys_role_user` VALUES ('9', '14', '25', '2018-11-08 08:43:06', '2018-11-08 08:43:06', '0');
INSERT INTO `sys_role_user` VALUES ('10', '15', '12', '2018-11-08 08:57:43', '2018-11-08 08:57:43', '0');
INSERT INTO `sys_role_user` VALUES ('17', '21', '12', '2018-11-14 08:21:16', '2018-11-14 08:21:16', '0');
INSERT INTO `sys_role_user` VALUES ('19', '13', '10', '2018-11-16 02:50:48', null, '0');
INSERT INTO `sys_role_user` VALUES ('23', '17', '26', '2018-11-16 03:08:18', null, '0');
INSERT INTO `sys_role_user` VALUES ('27', '24', '26', '2018-11-19 16:51:39', '2018-11-19 16:51:39', '0');
INSERT INTO `sys_role_user` VALUES ('28', '25', '26', '2018-11-19 16:51:51', '2018-11-19 16:51:51', '0');
INSERT INTO `sys_role_user` VALUES ('29', '26', '10', '2018-11-20 01:04:09', '2018-11-20 01:04:09', '0');
INSERT INTO `sys_role_user` VALUES ('31', '22', '10', '2018-11-20 20:14:01', null, '0');
INSERT INTO `sys_role_user` VALUES ('32', '16', '10', '2018-11-20 20:14:14', null, '0');
INSERT INTO `sys_role_user` VALUES ('35', '23', '25', '2018-11-21 07:31:27', null, '0');
INSERT INTO `sys_role_user` VALUES ('37', '28', '10', '2018-11-22 14:13:37', '2018-11-22 14:13:37', '0');
INSERT INTO `sys_role_user` VALUES ('40', '27', '10', '2018-11-25 17:52:15', null, '0');
INSERT INTO `sys_role_user` VALUES ('41', '20', '10', '2018-11-25 18:23:54', null, '0');
INSERT INTO `sys_role_user` VALUES ('42', '29', '25', '2018-11-26 17:51:58', '2018-11-26 17:51:58', '0');
INSERT INTO `sys_role_user` VALUES ('43', '30', '10', '2018-11-26 22:14:43', '2018-11-26 22:14:43', '0');
INSERT INTO `sys_role_user` VALUES ('44', '31', '25', '2018-11-26 22:15:27', '2018-11-26 22:15:27', '0');
INSERT INTO `sys_role_user` VALUES ('45', '32', '25', '2018-11-26 22:21:31', '2018-11-26 22:21:31', '0');
INSERT INTO `sys_role_user` VALUES ('46', '33', '25', '2018-11-26 22:28:33', '2018-11-26 22:28:33', '0');
INSERT INTO `sys_role_user` VALUES ('47', '34', '25', '2018-11-26 22:33:18', '2018-11-26 22:33:18', '0');
INSERT INTO `sys_role_user` VALUES ('48', '35', '10', '2018-11-26 22:46:08', '2018-11-26 22:46:08', '0');
INSERT INTO `sys_role_user` VALUES ('49', '36', '10', '2018-11-26 22:51:08', '2018-11-26 22:51:08', '0');
INSERT INTO `sys_role_user` VALUES ('50', '37', '10', '2018-11-26 22:52:11', '2018-11-26 22:52:11', '0');
INSERT INTO `sys_role_user` VALUES ('51', '38', '10', '2018-11-26 22:52:41', '2018-11-26 22:52:41', '0');
INSERT INTO `sys_role_user` VALUES ('52', '39', '10', '2018-11-26 22:53:23', '2018-11-26 22:53:23', '0');
INSERT INTO `sys_role_user` VALUES ('53', '40', '10', '2018-11-26 22:54:12', '2018-11-26 22:54:12', '0');
INSERT INTO `sys_role_user` VALUES ('54', '41', '10', '2018-11-27 00:02:31', '2018-11-27 00:02:31', '0');
INSERT INTO `sys_role_user` VALUES ('55', '42', '10', '2018-11-27 00:19:29', '2018-11-27 00:19:29', '0');
INSERT INTO `sys_role_user` VALUES ('56', '43', '10', '2018-11-27 00:35:42', '2018-11-27 00:35:42', '0');
INSERT INTO `sys_role_user` VALUES ('57', '44', '10', '2018-11-27 00:58:08', '2018-11-27 00:58:08', '0');
INSERT INTO `sys_role_user` VALUES ('58', '45', '10', '2018-11-27 01:08:59', '2018-11-27 01:08:59', '0');
INSERT INTO `sys_role_user` VALUES ('59', '46', '10', '2018-11-27 01:09:41', '2018-11-27 01:09:41', '0');
INSERT INTO `sys_role_user` VALUES ('60', '47', '10', '2018-11-27 01:11:36', '2018-11-27 01:11:36', '0');
INSERT INTO `sys_role_user` VALUES ('61', '48', '10', '2018-11-27 01:13:40', '2018-11-27 01:13:40', '0');
INSERT INTO `sys_role_user` VALUES ('62', '49', '10', '2018-11-27 01:20:09', '2018-11-27 01:20:09', '0');
INSERT INTO `sys_role_user` VALUES ('63', '50', '10', '2018-11-27 01:20:59', '2018-11-27 01:20:59', '0');
INSERT INTO `sys_role_user` VALUES ('64', '51', '10', '2018-11-27 01:22:26', '2018-11-27 01:22:26', '0');
INSERT INTO `sys_role_user` VALUES ('65', '52', '10', '2018-11-27 01:26:40', '2018-11-27 01:26:40', '0');
INSERT INTO `sys_role_user` VALUES ('66', '53', '10', '2018-11-27 01:29:09', '2018-11-27 01:29:09', '0');
INSERT INTO `sys_role_user` VALUES ('67', '54', '10', '2018-11-27 01:44:04', '2018-11-27 01:44:04', '0');
INSERT INTO `sys_role_user` VALUES ('68', '55', '10', '2018-11-27 01:55:19', '2018-11-27 01:55:19', '0');
INSERT INTO `sys_role_user` VALUES ('69', '56', '10', '2018-11-27 01:57:12', '2018-11-27 01:57:12', '0');
INSERT INTO `sys_role_user` VALUES ('70', '57', '10', '2018-11-27 01:59:57', '2018-11-27 01:59:57', '0');
INSERT INTO `sys_role_user` VALUES ('71', '61', '10', '2018-11-27 02:07:43', '2018-11-27 02:07:43', '0');
INSERT INTO `sys_role_user` VALUES ('72', '62', '10', '2018-11-27 02:13:12', '2018-11-27 02:13:12', '0');
INSERT INTO `sys_role_user` VALUES ('73', '63', '10', '2018-11-27 02:14:06', '2018-11-27 02:14:06', '0');
INSERT INTO `sys_role_user` VALUES ('74', '64', '10', '2018-11-27 02:16:11', '2018-11-27 02:16:11', '0');
INSERT INTO `sys_role_user` VALUES ('75', '65', '10', '2018-11-27 02:21:09', '2018-11-27 02:21:09', '0');
INSERT INTO `sys_role_user` VALUES ('76', '66', '10', '2018-11-27 02:24:23', '2018-11-27 02:24:23', '0');
INSERT INTO `sys_role_user` VALUES ('77', '67', '10', '2018-11-27 02:25:45', '2018-11-27 02:25:45', '0');
INSERT INTO `sys_role_user` VALUES ('78', '68', '10', '2018-11-27 02:26:49', '2018-11-27 02:26:49', '0');
INSERT INTO `sys_role_user` VALUES ('79', '69', '10', '2018-11-27 02:57:13', '2018-11-27 02:57:13', '0');
INSERT INTO `sys_role_user` VALUES ('80', '70', '10', '2018-11-27 02:58:23', '2018-11-27 02:58:23', '0');
INSERT INTO `sys_role_user` VALUES ('81', '71', '10', '2018-11-27 03:40:16', '2018-11-27 03:40:16', '0');
INSERT INTO `sys_role_user` VALUES ('82', '72', '10', '2018-11-27 03:42:25', '2018-11-27 03:42:25', '0');
INSERT INTO `sys_role_user` VALUES ('83', '73', '10', '2018-11-27 03:44:01', '2018-11-27 03:44:01', '0');
INSERT INTO `sys_role_user` VALUES ('84', '74', '10', '2018-11-27 03:47:54', '2018-11-27 03:47:54', '0');
INSERT INTO `sys_role_user` VALUES ('85', '75', '10', '2018-11-27 03:48:33', '2018-11-27 03:48:33', '0');
INSERT INTO `sys_role_user` VALUES ('86', '76', '10', '2018-11-27 03:50:58', '2018-11-27 03:50:58', '0');
INSERT INTO `sys_role_user` VALUES ('87', '77', '10', '2018-11-27 03:59:29', '2018-11-27 03:59:29', '0');
INSERT INTO `sys_role_user` VALUES ('88', '78', '10', '2018-11-27 04:00:07', '2018-11-27 04:00:07', '0');
INSERT INTO `sys_role_user` VALUES ('89', '79', '10', '2018-11-27 04:01:25', '2018-11-27 04:01:25', '0');
INSERT INTO `sys_role_user` VALUES ('90', '80', '10', '2018-11-27 04:02:40', '2018-11-27 04:02:40', '0');
INSERT INTO `sys_role_user` VALUES ('91', '81', '10', '2018-11-27 04:07:40', '2018-11-27 04:07:40', '0');
INSERT INTO `sys_role_user` VALUES ('92', '82', '10', '2018-11-27 04:10:40', '2018-11-27 04:10:40', '0');
INSERT INTO `sys_role_user` VALUES ('93', '83', '10', '2018-11-27 04:12:22', '2018-11-27 04:12:22', '0');
INSERT INTO `sys_role_user` VALUES ('94', '84', '10', '2018-11-27 04:19:53', '2018-11-27 04:19:53', '0');
INSERT INTO `sys_role_user` VALUES ('95', '85', '10', '2018-11-27 04:21:52', '2018-11-27 04:21:52', '0');
INSERT INTO `sys_role_user` VALUES ('96', '86', '10', '2018-11-27 04:22:19', '2018-11-27 04:22:19', '0');
INSERT INTO `sys_role_user` VALUES ('97', '87', '10', '2018-11-27 04:23:19', '2018-11-27 04:23:19', '0');
INSERT INTO `sys_role_user` VALUES ('98', '88', '10', '2018-11-27 04:23:41', '2018-11-27 04:23:41', '0');
INSERT INTO `sys_role_user` VALUES ('99', '89', '10', '2018-11-27 04:24:44', '2018-11-27 04:24:44', '0');
INSERT INTO `sys_role_user` VALUES ('100', '90', '10', '2018-11-27 18:48:36', '2018-11-27 18:48:36', '0');
INSERT INTO `sys_role_user` VALUES ('101', '91', '25', '2018-11-27 19:46:05', '2018-11-27 19:46:05', '0');
INSERT INTO `sys_role_user` VALUES ('102', '92', '28', '2018-11-27 22:04:31', '2018-11-27 22:04:31', '0');
INSERT INTO `sys_role_user` VALUES ('103', '93', '28', '2018-11-27 22:06:25', '2018-11-27 22:06:25', '0');
INSERT INTO `sys_role_user` VALUES ('104', '94', '25', '2018-11-27 22:51:00', '2018-11-27 22:51:00', '0');
INSERT INTO `sys_role_user` VALUES ('105', '95', '28', '2018-11-27 22:54:18', '2018-11-27 22:54:18', '0');
INSERT INTO `sys_role_user` VALUES ('106', '96', '29', '2018-11-28 00:42:13', '2018-11-28 00:42:13', '0');
INSERT INTO `sys_role_user` VALUES ('107', '97', '28', '2018-11-28 18:14:33', '2018-11-28 18:14:33', '0');
INSERT INTO `sys_role_user` VALUES ('108', '98', '10', '2018-11-29 01:59:25', '2018-11-29 01:59:25', '0');
INSERT INTO `sys_role_user` VALUES ('109', '99', '10', '2018-11-29 23:05:07', '2018-11-29 23:05:07', '0');
INSERT INTO `sys_role_user` VALUES ('111', '100', '25', '2018-11-29 23:10:37', null, '0');
INSERT INTO `sys_role_user` VALUES ('112', '101', '28', '2018-11-29 23:17:11', '2018-11-29 23:17:11', '0');
INSERT INTO `sys_role_user` VALUES ('113', '102', '28', '2018-11-29 23:29:36', '2018-11-29 23:29:36', '0');
INSERT INTO `sys_role_user` VALUES ('114', '103', '10', '2018-11-29 23:45:49', '2018-11-29 23:45:49', '0');
INSERT INTO `sys_role_user` VALUES ('115', '104', '10', '2018-11-29 23:47:16', '2018-11-29 23:47:16', '0');
INSERT INTO `sys_role_user` VALUES ('116', '105', '28', '2018-11-30 00:12:49', '2018-11-30 00:12:49', '0');
INSERT INTO `sys_role_user` VALUES ('117', '106', '30', '2018-12-01 01:59:00', '2018-12-01 01:59:00', '0');
INSERT INTO `sys_role_user` VALUES ('118', '107', '30', '2018-12-01 02:40:12', '2018-12-01 02:40:12', '0');
INSERT INTO `sys_role_user` VALUES ('119', '108', '30', '2018-12-01 02:49:44', '2018-12-01 02:49:44', '0');
INSERT INTO `sys_role_user` VALUES ('120', '109', '25', '2018-12-01 18:52:01', '2018-12-01 18:52:01', '0');

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
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `vaild_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('16', 'admin', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', '2', '', '', null, '2018-12-01 19:32:11', '0', null);
INSERT INTO `sys_user` VALUES ('20', 'testxrw', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, null, '2018-11-26 10:23:59', '0', null);
INSERT INTO `sys_user` VALUES ('22', 'guiji', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, null, '2018-11-20 15:13:38', '0', null);
INSERT INTO `sys_user` VALUES ('26', 'ttest3', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-19 20:03:46', '2018-11-19 20:03:46', '0', null);
INSERT INTO `sys_user` VALUES ('27', 'test', 'b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d', '2', null, null, null, '2018-11-22 15:34:29', '2018-11-26 09:52:20', '0', null);
INSERT INTO `sys_user` VALUES ('28', 'weic', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-22 17:13:14', '2018-11-22 17:13:14', '0', null);
INSERT INTO `sys_user` VALUES ('29', 'zhangwei1', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-27 09:50:47', '2018-11-27 09:50:47', '0', null);
INSERT INTO `sys_user` VALUES ('30', '12345', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:14:49', '2018-11-27 14:14:49', '0', null);
INSERT INTO `sys_user` VALUES ('31', '123455', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:15:34', '2018-11-27 14:15:34', '0', null);
INSERT INTO `sys_user` VALUES ('32', '1111', '33275a8aa48ea918bd53a9181aa975f15ab0d0645398f5918a006d08675c1cb27d5c645dbd084eee56e675e25ba4019f2ecea37ca9e2995b49fcb12c096a032e', '1', null, null, null, '2018-11-27 14:20:21', '2018-11-27 14:20:21', '0', null);
INSERT INTO `sys_user` VALUES ('33', '11111', '22e7e9d85b7fe6004f7b9f3aa592ea9ec9ce098682e8192fa83785f1784c768d1d1ac3b8afcae88666f66aec24739ac133e9d4adc7506f1a5f1f6078cb27c674', '1', null, null, null, '2018-11-27 14:28:39', '2018-11-27 14:28:39', '0', null);
INSERT INTO `sys_user` VALUES ('34', '用户测试', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:33:24', '2018-11-27 14:33:24', '0', null);
INSERT INTO `sys_user` VALUES ('35', '开户测试', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:46:14', '2018-11-27 14:46:14', '0', null);
INSERT INTO `sys_user` VALUES ('36', '用户测试1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:51:15', '2018-11-27 14:51:15', '0', null);
INSERT INTO `sys_user` VALUES ('37', '测试账户1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:52:17', '2018-11-27 14:52:17', '0', null);
INSERT INTO `sys_user` VALUES ('38', '测试账户2', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:52:48', '2018-11-27 14:52:48', '0', null);
INSERT INTO `sys_user` VALUES ('39', '测试账户3', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:53:29', '2018-11-27 14:53:29', '0', null);
INSERT INTO `sys_user` VALUES ('40', '测试账号', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 14:54:18', '2018-11-27 14:54:18', '0', null);
INSERT INTO `sys_user` VALUES ('41', '测试用户1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 16:02:37', '2018-11-27 16:02:37', '0', null);
INSERT INTO `sys_user` VALUES ('42', '用户测试2', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 16:19:35', '2018-11-27 16:19:35', '0', null);
INSERT INTO `sys_user` VALUES ('43', '用户测试3', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 16:35:49', '2018-11-27 16:35:49', '0', null);
INSERT INTO `sys_user` VALUES ('44', '测试账号5', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 16:58:15', '2018-11-27 16:58:15', '0', null);
INSERT INTO `sys_user` VALUES ('45', '账号测试1', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 17:09:05', '2018-11-27 17:09:05', '0', null);
INSERT INTO `sys_user` VALUES ('46', '测试账号2', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 17:09:48', '2018-11-27 17:09:48', '0', null);
INSERT INTO `sys_user` VALUES ('47', 'ceshi', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 17:11:43', '2018-11-27 17:11:43', '0', null);
INSERT INTO `sys_user` VALUES ('48', 'cece', '3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79', '1', null, null, null, '2018-11-27 17:13:46', '2018-11-27 17:13:46', '0', null);
INSERT INTO `sys_user` VALUES ('49', 'sadasd', 'd89e830e4ff760a0d5acbcf8de00c9bc0dd2eff4a3a8d3c528f9248a9c12800f599228ad3941158705b1f435d30ffec84b2855c498783dc547c78ecba0b11848', '1', null, null, null, '2018-11-27 17:20:16', '2018-11-27 17:20:16', '0', null);
INSERT INTO `sys_user` VALUES ('50', 'sdasd', '22ebd1f0f9ad5358a7205f1b177996d32f894b3299cfc72f31bb50a76bec6e2b55b3e8558cc3b0f2247c847331c3ecf9a67fae914c32d1b1941680f95c051912', '1', null, null, null, '2018-11-27 17:21:06', '2018-11-27 17:21:06', '0', null);
INSERT INTO `sys_user` VALUES ('51', 'adasdas', '7eee1a54b1590c20f51c6a0c13b8a71a4367fcf9231d6bf82195a913e894941c2b15d9a147e0d73db801b09d8e06fad21a5b1dc41a2c623f9d6fb9232f75fe6a', null, null, null, null, '2018-11-27 17:22:32', '2018-11-27 17:22:32', '0', null);
INSERT INTO `sys_user` VALUES ('52', 'sadasdsa', '65ef52861ff6ecb057ca6dabb76764109b1a851fcdb3541492d3a5e3336da7528ab1fc35d56b2caccddf00375eff6fec1334b320530bab7f69231e4e80564f0b', null, null, null, null, '2018-11-27 17:26:47', '2018-11-27 17:26:47', '0', null);
INSERT INTO `sys_user` VALUES ('53', 'sasdasd', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 17:29:16', '2018-11-27 17:29:16', '0', null);
INSERT INTO `sys_user` VALUES ('54', 'sadad', '7621770eae0880e21dbf3939f045b9a44013f190df54243f0a4b3d28806d2c55c5de651d4fd4160e09ec3af805695275da1933044a70677a3efa361943644577', null, null, null, null, '2018-11-27 17:44:11', '2018-11-27 17:44:11', '0', null);
INSERT INTO `sys_user` VALUES ('55', 'ddddd', 'aeae379a6e857728e44164267fdb7a0e27b205d757cc19899586c89dbb221930f1813d02ff93a661859bc17065eac4d6edf3c38a034e6283a84754d52917e5b0', null, null, null, null, '2018-11-27 17:55:26', '2018-11-27 17:55:26', '0', null);
INSERT INTO `sys_user` VALUES ('56', 'sdasdasd', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 17:57:19', '2018-11-27 17:57:19', '0', null);
INSERT INTO `sys_user` VALUES ('57', 'csacasc', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 18:00:04', '2018-11-27 18:00:04', '0', null);
INSERT INTO `sys_user` VALUES ('58', 'sdsadasas', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 18:07:13', '2018-11-27 18:07:13', '0', null);
INSERT INTO `sys_user` VALUES ('59', 'xvxcvxc', 'b1e2ac704a7b9e2580f5bcf288599bb53b6023f223ab6da7b0fb91eb8241e08d01f4a26f7e3435f5159d4e70f081b95de4bc1a6bc507236911ea5764eb795ad0', null, null, null, null, '2018-11-27 18:07:30', '2018-11-27 18:07:30', '0', null);
INSERT INTO `sys_user` VALUES ('60', 'wqeqeq', 'b1e2ac704a7b9e2580f5bcf288599bb53b6023f223ab6da7b0fb91eb8241e08d01f4a26f7e3435f5159d4e70f081b95de4bc1a6bc507236911ea5764eb795ad0', null, null, null, null, '2018-11-27 18:07:36', '2018-11-27 18:07:36', '0', null);
INSERT INTO `sys_user` VALUES ('61', 'etwetwet', 'b1e2ac704a7b9e2580f5bcf288599bb53b6023f223ab6da7b0fb91eb8241e08d01f4a26f7e3435f5159d4e70f081b95de4bc1a6bc507236911ea5764eb795ad0', null, null, null, null, '2018-11-27 18:07:50', '2018-11-27 18:07:50', '0', null);
INSERT INTO `sys_user` VALUES ('62', 'fsafas', 'dddc4d556da9d7402353c36922e280704b0f819913d12b81b705ca6aee14b29383ef3c14d648b03c172eae976819c08e08cbb47b938ecf44d3dc50ecdb5495b8', null, null, null, null, '2018-11-27 18:13:18', '2018-11-27 18:13:18', '0', null);
INSERT INTO `sys_user` VALUES ('63', 'fsfsdf', '2f8f105e5b85b7e298b6c12400c4e2f1d863fd741300891148378947b0f3d21a9b3ad2d39b05f89a503080a84c3d0a99499678e8c0aa9de2e78dca0fe5d59d27', null, null, null, null, '2018-11-27 18:14:12', '2018-11-27 18:14:12', '0', null);
INSERT INTO `sys_user` VALUES ('64', 'asdasd', 'aeae379a6e857728e44164267fdb7a0e27b205d757cc19899586c89dbb221930f1813d02ff93a661859bc17065eac4d6edf3c38a034e6283a84754d52917e5b0', null, null, null, null, '2018-11-27 18:16:17', '2018-11-27 18:16:17', '0', null);
INSERT INTO `sys_user` VALUES ('65', 'dadaad', 'ca551d09d45d220735d9b936f9d9aa3e1062893bc22e3f0abab014ad797deea55a40aa35a5d01d48e6e95910554790f7187bbc7b861a1c7c38c89b99589ca4f7', null, null, null, null, '2018-11-27 18:21:16', '2018-11-27 18:21:16', '0', null);
INSERT INTO `sys_user` VALUES ('66', 'dsada', '8061fd1e0fe7b78dfc90e35c740873a59413da216ee12cfd67694b13626d3978056909b84961b8411dff6466831964166a40b3e65eda3e718cdf478efda64076', null, null, null, null, '2018-11-27 18:24:30', '2018-11-27 18:24:30', '0', null);
INSERT INTO `sys_user` VALUES ('67', 'fasfasf', '0fa9644263e365c4d264888825f22b7d7b104f5455861641e619b225aae8f23bcf6586be45d2090ccc9aa649a23ff55340f6bf4ebff733cfcea971a1b5d4b3d2', null, null, null, null, '2018-11-27 18:25:51', '2018-11-27 18:25:51', '0', null);
INSERT INTO `sys_user` VALUES ('68', 'rgergre', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 18:26:56', '2018-11-27 18:26:56', '0', null);
INSERT INTO `sys_user` VALUES ('69', 'fddgg', '449ea168825dd1e8751bc50142d684102ed4e7c859e3d9f0f5f7b6d0b8d3b0b6bcefec627dfe3ce66126c0fa87e5f534dbdfad03ca3a05ede4743aff7cc96c74', '1', null, null, null, '2018-11-27 18:57:20', '2018-11-27 18:57:20', '0', null);
INSERT INTO `sys_user` VALUES ('70', 'wqwewq', '29adae72ab962f65857d101204401e8d102c12d27f65a39dd075ea3e7853cb4973bd675d5a12e79e6476a4ae4239fcabc57b742ddef458cf46d51693ca99c67c', null, null, null, null, '2018-11-27 18:58:30', '2018-11-27 18:58:30', '0', null);
INSERT INTO `sys_user` VALUES ('71', 'fsdfdsdf', '8061fd1e0fe7b78dfc90e35c740873a59413da216ee12cfd67694b13626d3978056909b84961b8411dff6466831964166a40b3e65eda3e718cdf478efda64076', null, null, null, null, '2018-11-27 19:40:22', '2018-11-27 19:40:22', '0', null);
INSERT INTO `sys_user` VALUES ('72', 'asdads', '8061fd1e0fe7b78dfc90e35c740873a59413da216ee12cfd67694b13626d3978056909b84961b8411dff6466831964166a40b3e65eda3e718cdf478efda64076', null, null, null, null, '2018-11-27 19:42:32', '2018-11-27 19:42:32', '0', null);
INSERT INTO `sys_user` VALUES ('73', 'sdasdas', '7621770eae0880e21dbf3939f045b9a44013f190df54243f0a4b3d28806d2c55c5de651d4fd4160e09ec3af805695275da1933044a70677a3efa361943644577', null, null, null, null, '2018-11-27 19:44:08', '2018-11-27 19:44:08', '0', null);
INSERT INTO `sys_user` VALUES ('74', 'vdsvd', '8b58ba24942f65f14bdd712b6e08ba9d26b1ecc094f557acf1d06f652f486d34187dacd547df574028461be7e3abd1eb7f551dff8092093e0ef90f088992f4fc', null, null, null, null, '2018-11-27 19:48:01', '2018-11-27 19:48:01', '0', null);
INSERT INTO `sys_user` VALUES ('75', 'ryeye', 'b6527b47304ff4f5f5b2ed4634b9b717b168213649f2b371a1782375f028c39b3f6ee86b2ebf5e594e69b17660e7c8c605881d833c8e3a48d6c43f670ca94418', null, null, null, null, '2018-11-27 19:48:40', '2018-11-27 19:48:40', '0', null);
INSERT INTO `sys_user` VALUES ('76', 'hhrth', 'e09cc8bfd9bbfbb2a102f366d3e1e946bb449df69028eb97746bcb16658b64ed6527d8cdaa56d84d769e8a795f450120a9a70c8869ea136c17e852fd1ca8b1a9', null, null, null, null, '2018-11-27 19:51:05', '2018-11-27 19:51:05', '0', null);
INSERT INTO `sys_user` VALUES ('77', 'asdasdd', '0db84ddd15b982dd681ef85e3ebd378d0f706e0929bd347e13cbfc754708adbfdc1608214e1faf8cb931dce74f00180a2167f24cdbdd20d88499aaf9c2870491', null, null, null, null, '2018-11-27 19:59:35', '2018-11-27 19:59:35', '0', null);
INSERT INTO `sys_user` VALUES ('78', 'bfdb', '7c8f68f416948c3d0a71a394c5abba7e3daa5c5af6ef97ac8f9e5d297e36f3583d224353a9d3082a44e95ffe662e9f2eeb8b6e929894ce4301ec531bd70c46fe', null, null, null, null, '2018-11-27 20:00:13', '2018-11-27 20:00:13', '0', null);
INSERT INTO `sys_user` VALUES ('79', 'hfdfhd', '9fa914e002c2f35a3994e8d7d114d4fce2328fb67369a19a7ab4cbed8cfd957df85142ac9a10d5f01d8e46d98982d964bac996191ae9032b58c4917c049ec018', null, null, null, null, '2018-11-27 20:01:32', '2018-11-27 20:01:32', '0', null);
INSERT INTO `sys_user` VALUES ('80', 'gnfgnfg', '39f4fe9e25bcfa5915804c2fe9e58547d8f670092c63299fc2f862ff471ae1c95eac16333bf4fffe97fa245b2373c9cabbda3ffbef0baeb794cd2816f89703a0', null, null, null, null, '2018-11-27 20:02:47', '2018-11-27 20:02:47', '0', null);
INSERT INTO `sys_user` VALUES ('81', 'fsdfsdf', '867565663d7bf777bada2b4263f901cb1383ffc428f3a22d0f25ff71f058f54b25bfa5ff9a28681c845b2751486db3c138ea78aa98a89a37fb97f7015f809902', null, null, null, null, '2018-11-27 20:07:47', '2018-11-27 20:07:47', '0', null);
INSERT INTO `sys_user` VALUES ('82', 'fdgdfg', '7a79020a9dbc3b0c481ec0c9e2fef81130d0b11cfed12e33c3f098beb2bb40889b94572e50ea609566577da646c74478a8a0778f95341df971a1da40836af70f', null, null, null, null, '2018-11-27 20:10:47', '2018-11-27 20:10:47', '0', null);
INSERT INTO `sys_user` VALUES ('83', 'cacsa', '2f8f105e5b85b7e298b6c12400c4e2f1d863fd741300891148378947b0f3d21a9b3ad2d39b05f89a503080a84c3d0a99499678e8c0aa9de2e78dca0fe5d59d27', null, null, null, null, '2018-11-27 20:12:29', '2018-11-27 20:12:29', '0', null);
INSERT INTO `sys_user` VALUES ('84', 'fdsfs', '59b26af186d65926f1a963269e36e5569d6f2e8bf4c9b572512e755766865e41945338d454d2e251f2ce2d0cdcfe89d1f275b00e4ce00ee90b284a684f1d311f', null, null, null, null, '2018-11-27 20:19:59', '2018-11-27 20:19:59', '0', null);
INSERT INTO `sys_user` VALUES ('85', 'dasdsa', '22ebd1f0f9ad5358a7205f1b177996d32f894b3299cfc72f31bb50a76bec6e2b55b3e8558cc3b0f2247c847331c3ecf9a67fae914c32d1b1941680f95c051912', null, null, null, null, '2018-11-27 20:21:59', '2018-11-27 20:21:59', '0', null);
INSERT INTO `sys_user` VALUES ('86', 'dfgdg', 'c0813a6e2949bb06dbb2b3cff6eb03cd183e9f79259fe69781c35caab75a981bee20b3cb65e8165367b7791e04b12e856f34d72e10e511d29fdcddc72c09539d', null, null, null, null, '2018-11-27 20:22:26', '2018-11-27 20:22:26', '0', null);
INSERT INTO `sys_user` VALUES ('87', 'ihihiu', '8f265200ca70a2130a482329b81eebf31aea722e59e438c2d55d9b7e9c15f0dbff19a53cc0da8ae3050756da047c47dc70b6a0868fb56eff6f243d5f4cee5c41', null, null, null, null, '2018-11-27 20:23:26', '2018-11-27 20:23:26', '0', null);
INSERT INTO `sys_user` VALUES ('88', 'dtdt', 'eabe92ea1ebd816f98687a208eb46974c92ea79df4606a6cef762f053d3bc9a61c9c41e5cede24b9b43a1966523cee7e136866dc1c66fdd4076a3d28c32f0f63', null, null, null, null, '2018-11-27 20:23:48', '2018-11-27 20:23:48', '0', null);
INSERT INTO `sys_user` VALUES ('89', 'asdasd1', 'f38a3b5aa7c40c4ecf03bc895fc7fac9d51bbf3cc0aabc27d53b9c56896bcf8d294ebdc2d7f9a34516e55c06663d6a4b3c380ee326be1d1a2ea1eb4e0602b94b', null, null, null, null, '2018-11-27 20:24:51', '2018-11-27 20:24:51', '0', null);
INSERT INTO `sys_user` VALUES ('90', 'test-zyf', 'b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d', '1', null, null, null, '2018-11-28 10:48:44', '2018-11-28 10:48:44', '0', null);
INSERT INTO `sys_user` VALUES ('91', 'caochang', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', '1', null, null, null, '2018-11-28 11:46:13', '2018-11-28 11:46:13', '0', null);
INSERT INTO `sys_user` VALUES ('92', 'fdgdf', '5b46047e96b8cf6c4e3ae220b69c9e20a34a2e7dbf3790423f66c273a29cdb1399ea733deeac7970d7525e31406586bb6e50cc4eb20f0e24bc8c4a7a3c9a7328', null, null, null, null, '2018-11-28 14:04:39', '2018-11-28 14:04:39', '0', null);
INSERT INTO `sys_user` VALUES ('93', 'thehe', '8514ecc6f30dd9617cb1d20a467b0461ff8c939e93f9dcbdd3474bb71c392f016561c6ebeb1c1269bda4119afdece02fc60827e915aff9e80dd2a4d86c8ad738', null, null, null, null, '2018-11-28 14:06:32', '2018-11-28 14:06:32', '0', null);
INSERT INTO `sys_user` VALUES ('94', 'testzq', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-28 14:51:07', '2018-11-28 14:51:07', '0', null);
INSERT INTO `sys_user` VALUES ('95', 'ceshi8', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-28 14:54:26', '2018-11-28 14:54:26', '0', null);
INSERT INTO `sys_user` VALUES ('96', 'cehi9', '12b03226a6d8be9c6e8cd5e55dc6c7920caaa39df14aab92d5e3ea9340d1c8a4d3d0b8e4314f1f6ef131ba4bf1ceb9186ab87c801af0d5c95b1befb8cedae2b9', '2', null, null, null, '2018-11-28 16:42:21', '2018-11-28 16:42:21', '0', null);
INSERT INTO `sys_user` VALUES ('97', '187832783', 'fa585d89c851dd338a70dcf535aa2a92fee7836dd6aff1226583e88e0996293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe', '2', null, null, null, '2018-11-29 10:14:42', '2018-11-29 10:14:42', '0', null);
INSERT INTO `sys_user` VALUES ('98', '123', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2', null, null, null, null, '2018-11-29 17:59:35', '2018-11-29 17:59:35', '0', null);
INSERT INTO `sys_user` VALUES ('99', 'test111', 'b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d', '1', null, null, null, '2018-11-30 15:05:18', '2018-11-30 15:05:18', '0', null);
INSERT INTO `sys_user` VALUES ('100', 'test123', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-11-30 15:09:25', '2018-11-30 15:10:47', '0', null);
INSERT INTO `sys_user` VALUES ('101', 'test1111', 'b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d', '1', null, null, null, '2018-11-30 15:17:21', '2018-11-30 15:17:21', '0', null);
INSERT INTO `sys_user` VALUES ('102', 'sdasdad', 'aeae379a6e857728e44164267fdb7a0e27b205d757cc19899586c89dbb221930f1813d02ff93a661859bc17065eac4d6edf3c38a034e6283a84754d52917e5b0', null, null, null, null, '2018-11-30 15:29:47', '2018-11-30 15:29:47', '0', null);
INSERT INTO `sys_user` VALUES ('105', 'tewt123', '33275a8aa48ea918bd53a9181aa975f15ab0d0645398f5918a006d08675c1cb27d5c645dbd084eee56e675e25ba4019f2ecea37ca9e2995b49fcb12c096a032e', '1', null, null, null, '2018-11-30 16:13:00', '2018-11-30 16:13:00', '0', null);
INSERT INTO `sys_user` VALUES ('106', 'abudula', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-12-01 17:59:12', '2018-12-01 17:59:12', '0', null);
INSERT INTO `sys_user` VALUES ('107', 'abudula1', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-12-01 18:40:24', '2018-12-01 18:40:24', '0', null);
INSERT INTO `sys_user` VALUES ('108', 'abudula2', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', null, null, null, '2018-12-01 18:49:56', '2018-12-01 18:49:56', '0', null);
INSERT INTO `sys_user` VALUES ('109', '123123', 'b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d', null, null, null, null, '2018-12-02 10:52:14', '2018-12-02 10:52:14', '0', null);

-- ----------------------------
-- Table structure for sys_user_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_action`;
CREATE TABLE `sys_user_action` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `action_name` varchar(255) NOT NULL DEFAULT '' COMMENT '行为名称',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '操作用户ID',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  `data` text COMMENT '用户提交的数据',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '操作URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';

-- ----------------------------
-- Records of sys_user_action
-- ----------------------------

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
-- Records of sys_user_ext
-- ----------------------------

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
-- Records of sys_user_login_record
-- ----------------------------

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

-- ----------------------------
-- Records of user_api
-- ----------------------------
