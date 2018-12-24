/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.57
Source Server Version : 50707
Source Host           : 192.168.1.57:3306
Source Database       : guiyu_dispatch

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-13 11:21:34
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
