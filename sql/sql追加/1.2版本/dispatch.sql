/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.81
Source Server Version : 50722
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-01-11 14:37:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file_error_records` 当前表之前没数据 可以删除新增
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_error_records
-- ----------------------------

-- ----------------------------
-- Table structure for `file_records`  当前表之前没数据 可以删除新增
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
  `org_code` varchar(32) DEFAULT NULL,
  `robot` varchar(32) DEFAULT NULL,
  `line_id` varchar(32) DEFAULT NULL,
  `call_data` varchar(32) NOT NULL,
  `status` varchar(32) DEFAULT NULL,
  `call_hour` varchar(32) NOT NULL,
  `is_clean` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_records
-- ----------------------------
-- ----------------------------
-- Table structure for `push_records`
-- ----------------------------
CREATE TABLE `push_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `planuuid` char(32) NOT NULL,
  `phone` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `callback_status` int(11) DEFAULT NULL COMMENT '0代表未回调，1代表已经回调',
  `user_id` int(11) DEFAULT NULL,
  `line` int(11) DEFAULT NULL,
  `robot` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16596 DEFAULT CHARSET=utf8;

