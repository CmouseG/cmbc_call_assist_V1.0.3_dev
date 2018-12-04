/*
Navicat MySQL Data Transfer

Source Server         : 测试环境
Source Server Version : 50707
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_ai

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-03 17:35:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tts_model
-- ----------------------------
DROP TABLE IF EXISTS `tts_model`;
CREATE TABLE `tts_model` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `model` varchar(20) NOT NULL COMMENT '模型',
  `tts_ip` varchar(50) NOT NULL DEFAULT '' COMMENT 'tts服务器ip',
  `tts_port` varchar(10) NOT NULL DEFAULT '' COMMENT 'tts服务器端口',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态：0启动1停用',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `company` varchar(50) DEFAULT NULL COMMENT '公司',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tts_model
-- ----------------------------

-- ----------------------------
-- Table structure for tts_result
-- ----------------------------
DROP TABLE IF EXISTS `tts_result`;
CREATE TABLE `tts_result` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bus_id` varchar(255) DEFAULT NULL COMMENT '业务id',
  `tts_ip` varchar(50) NOT NULL DEFAULT '' COMMENT 'tts服务器ip',
  `tts_port` varchar(10) NOT NULL DEFAULT '' COMMENT 'tts服务器端口',
  `content` varchar(255) DEFAULT NULL COMMENT '待转换文本内容',
  `model` varchar(20) NOT NULL DEFAULT '' COMMENT '模型',
  `audio_url` varchar(1000) DEFAULT NULL COMMENT '输出音频文件url',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标识：0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tts_status
-- ----------------------------
DROP TABLE IF EXISTS `tts_status`;
CREATE TABLE `tts_status` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bus_id` varchar(255) DEFAULT NULL COMMENT '业务id',
  `model` varchar(255) DEFAULT NULL COMMENT '模型名称',
  `status` char(1) DEFAULT '0' COMMENT '删除标识：0未处理1处理中2已完成',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `text_count` int(8) DEFAULT '0' COMMENT '文本数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tts_status
-- ----------------------------
