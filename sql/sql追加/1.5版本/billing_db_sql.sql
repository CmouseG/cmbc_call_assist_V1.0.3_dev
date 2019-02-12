/*
SQLyog Ultimate v12.5.1 (64 bit)
MySQL - 5.7.22-log : Database - guiyu_billing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`guiyu_billing` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `guiyu_billing`;

/*Table structure for table `billing_acct_charging_record` */

DROP TABLE IF EXISTS `billing_acct_charging_record`;

CREATE TABLE `billing_acct_charging_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charging_id` varchar(32) DEFAULT NULL COMMENT '计费ID',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `oper_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `oper_user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `oper_user_org_code` varchar(32) DEFAULT NULL COMMENT '用户编码',
  `oper_begin_time` datetime DEFAULT NULL COMMENT '通话开始时间',
  `oper_end_time` datetime DEFAULT NULL COMMENT '通话结束时间',
  `oper_duration` bigint(16) NOT NULL DEFAULT '0' COMMENT '通话时长秒',
  `oper_duration_m` bigint(16) NOT NULL DEFAULT '0' COMMENT '通话时长，按分钟整算',
  `oper_duration_str` varchar(16) DEFAULT '00:00' COMMENT '通话时长描述',
  `oper_status` int(2) DEFAULT NULL COMMENT '状态',
  `oper_details` varchar(255) DEFAULT NULL COMMENT '详细信息',
  `type` int(2) DEFAULT NULL COMMENT '类型  1：充值 2：消费',
  `fee_mode` int(2) DEFAULT NULL COMMENT '费用类型  1-银行转账充值  2-在线充值  3-通话消费',
  `user_charging_id` varchar(32) DEFAULT NULL COMMENT '用户计费项ID',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '金额',
  `src_amount` decimal(16,2) DEFAULT NULL COMMENT '金额来源',
  `to_amount` decimal(16,2) DEFAULT NULL COMMENT '金额去处',
  `evidence` varchar(128) DEFAULT NULL COMMENT '凭证',
  `planuuid` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `attachment_snapshot_url` varchar(512) DEFAULT NULL COMMENT '附件快照地址，多个图片用(英文)逗号分割',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_acct_charging_record` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户计费流水记录表';

/*Table structure for table `billing_acct_charging_term` */

DROP TABLE IF EXISTS `billing_acct_charging_term`;

CREATE TABLE `billing_acct_charging_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_charging_id` varchar(32) DEFAULT NULL COMMENT '用户计费项ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `charging_item_id` varchar(32) DEFAULT NULL COMMENT '计费项ID',
  `charging_item_name` varchar(128) DEFAULT NULL COMMENT '计费项名称',
  `charging_type` int(2) DEFAULT NULL COMMENT '计费项类型 1-时长 2-路数 3-月度',
  `target_key` varchar(32) DEFAULT NULL COMMENT '用户设置key名称',
  `target_name` varchar(32) DEFAULT NULL COMMENT '用户设置项名称',
  `price` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `unit_price` int(1) DEFAULT NULL COMMENT '价格单位  1-秒 2-分钟 3-小时 4-天 5-月 6-年',
  `is_deducted` int(1) NOT NULL DEFAULT '0' COMMENT '扣费标识 0-扣费 1-不扣费',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态  0-停用 1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_charging_term_id` (`charging_item_id`),
  KEY `idx_acct_charging_term` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户计费项表';

/*Table structure for table `billing_acct_charging_total` */

DROP TABLE IF EXISTS `billing_acct_charging_total`;

CREATE TABLE `billing_acct_charging_total` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `type` int(1) DEFAULT NULL COMMENT '类型 1-充值 2-消费',
  `call_duration` bigint(16) NOT NULL DEFAULT '0' COMMENT '通话时长',
  `consume_amount` decimal(16,2) DEFAULT NULL COMMENT '统计计算金额，消费金额',
  `total_date` varchar(16) DEFAULT NULL COMMENT '统计日期，例如：yyyy-MM-dd',
  `total_month` varchar(16) DEFAULT NULL COMMENT '统计月份，格式yyyy-MM',
  `call_time` varchar(32) DEFAULT NULL COMMENT '通话时间',
  `stat_time` datetime DEFAULT NULL,
  `stat_status` int(1) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_acct_charging_total` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户统计表';

/*Table structure for table `billing_acct_charing_record` */

DROP TABLE IF EXISTS `billing_acct_charing_record`;

CREATE TABLE `billing_acct_charing_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charging_id` varchar(32) DEFAULT NULL COMMENT '计费ID',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `oper_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `oper_user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `oper_user_org_code` varchar(32) DEFAULT NULL COMMENT '用户编码',
  `oper_time` datetime DEFAULT NULL COMMENT '执行时间',
  `oper_status` int(2) DEFAULT NULL COMMENT '状态',
  `oper_details` varchar(255) DEFAULT NULL COMMENT '详细信息',
  `type` int(2) DEFAULT NULL COMMENT '类型',
  `my_charing_id` varchar(32) DEFAULT NULL COMMENT '用户计费ID',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '金额',
  `src_amount` decimal(16,2) DEFAULT NULL COMMENT '金额来源',
  `to_amount` decimal(16,2) DEFAULT NULL COMMENT '金额去处',
  `evidence` varchar(128) DEFAULT NULL COMMENT '凭证',
  `planuuid` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_acct_charing_record` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户计费流水记录表';

/*Table structure for table `billing_acct_reconciliation` */

DROP TABLE IF EXISTS `billing_acct_reconciliation`;

CREATE TABLE `billing_acct_reconciliation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `oper_time` datetime DEFAULT NULL COMMENT '执行时间',
  `oper_status` int(2) DEFAULT NULL COMMENT 'oper状态',
  `oper_details` varchar(255) DEFAULT NULL COMMENT '详细信息',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `my_charging_id` varchar(32) DEFAULT NULL COMMENT '用户计费ID',
  `service_amount` decimal(16,2) DEFAULT NULL COMMENT '业务金额',
  `charging_center_amount` decimal(16,2) DEFAULT NULL COMMENT '计费中心金额',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `fix_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `fix_details` varchar(255) DEFAULT NULL COMMENT '详情',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_acct_reconciliation` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账记录表';

/*Table structure for table `billing_acct_set` */

DROP TABLE IF EXISTS `billing_acct_set`;

CREATE TABLE `billing_acct_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `acct_set_id` varchar(32) DEFAULT NULL COMMENT '账户设置ID',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `set_key` varchar(32) DEFAULT NULL COMMENT '配置key',
  `set_value` varchar(32) DEFAULT NULL COMMENT '配置值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_acct_set` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户设置表';

/*Table structure for table `billing_charging_term` */

DROP TABLE IF EXISTS `billing_charging_term`;

CREATE TABLE `billing_charging_term` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `charging_item_id` varchar(32) DEFAULT NULL COMMENT '计费项ID',
  `type` int(2) DEFAULT NULL COMMENT '类型',
  `name` varchar(64) DEFAULT NULL COMMENT '计费项名称',
  `charging_type` int(2) DEFAULT NULL COMMENT '计费项类型 1-时长 2-路数 3-月度',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态  0-停用 1-启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_charging_term` (`charging_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费项表';

/*Table structure for table `billing_exception_retry_record` */

DROP TABLE IF EXISTS `billing_exception_retry_record`;

CREATE TABLE `billing_exception_retry_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `type` int(1) DEFAULT NULL COMMENT '类型 1-流水 2-统计 3-对账 4-通知',
  `key_id` varchar(32) DEFAULT NULL COMMENT '对应ID',
  `retry_times` datetime DEFAULT NULL COMMENT '重试时间',
  `retry_status` int(1) DEFAULT NULL COMMENT '重试状态  0-成功 1-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_exception_retry_record` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常重试记录表';

/*Table structure for table `billing_notify_busi_record` */

DROP TABLE IF EXISTS `billing_notify_busi_record`;

CREATE TABLE `billing_notify_busi_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `type` int(1) DEFAULT NULL COMMENT '类型 1-欠费 2-已缴费 3-冻结',
  `return_status` int(1) DEFAULT NULL COMMENT '通知状态  0-成功 1-失败',
  `return_details` varchar(255) DEFAULT NULL COMMENT '通知详情',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_notify_busi_record` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知各业务单元记录表';

/*Table structure for table `billing_notify_msg_record` */

DROP TABLE IF EXISTS `billing_notify_msg_record`;

CREATE TABLE `billing_notify_msg_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `type` int(1) DEFAULT NULL COMMENT '类型 1-欠费 2-已缴费 3-冻结',
  `notify_type` int(1) DEFAULT NULL COMMENT '通知类型  0-mq',
  `content` varchar(255) DEFAULT NULL COMMENT '通知内容',
  `status` int(1) DEFAULT NULL COMMENT '状态 0-成功 1-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_notify_msg_record` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息通知记录表';

/*Table structure for table `billing_total_charging` */

DROP TABLE IF EXISTS `billing_total_charging`;

CREATE TABLE `billing_total_charging` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `total_mode` int(1) DEFAULT NULL COMMENT '统计方式：1-日  2-月',
  `recharge_amount` decimal(16,2) DEFAULT NULL COMMENT '统计计算金额，充值金额',
  `consume_amount` decimal(16,2) DEFAULT NULL COMMENT '统计计算金额，消费金额',
  `total_date` varchar(16) DEFAULT NULL COMMENT '统计日期，例如：yyyy-MM-dd',
  `total_month` varchar(16) DEFAULT NULL COMMENT '统计月份，格式yyyy-MM',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_total_charging_date` (`total_date`),
  KEY `idx_total_charging_month` (`total_month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费用统计表';

/*Table structure for table `billing_user_acct` */

DROP TABLE IF EXISTS `billing_user_acct`;

CREATE TABLE `billing_user_acct` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` varchar(32) DEFAULT NULL COMMENT '账户ID',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `org_code` varchar(64) DEFAULT NULL COMMENT '公司编码',
  `amount` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `available_balance` decimal(16,2) DEFAULT NULL COMMENT '可用剩余金额',
  `freezing_amount` decimal(16,2) DEFAULT NULL COMMENT '冻结金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志 0-正常 1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_billing_user_acct` (`account_id`),
  KEY `idx_user_acct_code` (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费用户账户表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
