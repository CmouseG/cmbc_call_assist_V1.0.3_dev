


CREATE DATABASE IF NOT EXISTS `guiyu_notice`  ;

USE `guiyu_notice`;

/*Table structure for table `notice_info` */

DROP TABLE IF EXISTS `notice_info`;

CREATE TABLE `notice_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(8) DEFAULT NULL COMMENT '企业编码',
  `notice_type` int(2) DEFAULT NULL,
  `mail_content` varchar(1024) DEFAULT NULL,
  `sms_content` varchar(1024) DEFAULT NULL,
  `email_content` varchar(1024) DEFAULT NULL,
  `email_subject` varchar(255) DEFAULT NULL,
  `weixin_template_id` varchar(255) DEFAULT NULL,
  `weixin_url` varchar(255) DEFAULT NULL,
  `weixin_app_id` varchar(255) DEFAULT NULL,
  `weixin_page_path` varchar(255) DEFAULT NULL,
  `weixin_data` varchar(2048) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Table structure for table `notice_mail_info` */

DROP TABLE IF EXISTS `notice_mail_info`;

CREATE TABLE `notice_mail_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `receive_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `read_time` datetime DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `notice_setting` */

DROP TABLE IF EXISTS `notice_setting`;

CREATE TABLE `notice_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(8) DEFAULT NULL COMMENT '企业编码',
  `notice_over_type` int(1) DEFAULT NULL,
  `notice_type` int(2) DEFAULT NULL,
  `is_send_mail` tinyint(1) DEFAULT '0',
  `is_send_weixin` tinyint(1) DEFAULT '0',
  `is_send_email` tinyint(1) DEFAULT '0',
  `is_send_sms` tinyint(1) DEFAULT '0',
  `receivers` varchar(255) DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `createa_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) 
