/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.57
Source Server Version : 50707
Source Host           : 192.168.1.57:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-13 11:23:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `modular_logs`
-- ----------------------------
DROP TABLE IF EXISTS `modular_logs`;
CREATE TABLE `modular_logs` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `modular_name` int(32) NOT NULL COMMENT '模块名称',
  `status` int(32) NOT NULL COMMENT '处理状态0：成功 1 失败',
  `plan_uuid` varchar(32) NOT NULL,
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `msg` varchar(32) NOT NULL DEFAULT '处理异常信息',
  `batch_name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_PHONE` (`phone`) USING BTREE,
  KEY `IX_PLAN_UUID` (`plan_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of modular_logs
-- ----------------------------

-- ----------------------------
-- Table structure for `third_interface_records`
-- ----------------------------
DROP TABLE IF EXISTS `third_interface_records`;
CREATE TABLE `third_interface_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` date NOT NULL,
  `url` varchar(1024) NOT NULL,
  `params` varchar(15000) NOT NULL,
  `times` int(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
