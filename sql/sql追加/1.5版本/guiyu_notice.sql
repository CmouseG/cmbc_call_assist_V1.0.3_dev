/*创建数据库*/

CREATE DATABASE IF NOT EXISTS guiyu_notice DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
GRANT ALL ON guiyu_notice.* TO notice@'%' IDENTIFIED BY 'notice@1234' WITH GRANT OPTION; 
GRANT ALL PRIVILEGES ON guiyu_notice.* TO 'notice'@'%' IDENTIFIED BY 'notice@1234' WITH GRANT OPTION;

USE `guiyu_notice`;

/* 建表 */

DROP TABLE IF EXISTS `notice_info`;

CREATE TABLE `notice_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `org_code` VARCHAR(8) DEFAULT NULL COMMENT '企业编码',
  `notice_type` INT(2) DEFAULT NULL,
  `mail_content` VARCHAR(1024) DEFAULT NULL,
  `sms_content` VARCHAR(1024) DEFAULT NULL,
  `email_content` VARCHAR(1024) DEFAULT NULL,
  `email_subject` VARCHAR(255) DEFAULT NULL,
  `weixin_template_id` VARCHAR(255) DEFAULT NULL,
  `weixin_url` VARCHAR(255) DEFAULT NULL,
  `weixin_app_id` VARCHAR(255) DEFAULT NULL,
  `weixin_page_path` VARCHAR(255) DEFAULT NULL,
  `weixin_data` VARCHAR(2048) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `notice_mail_info`;

CREATE TABLE `notice_mail_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `info_id` INT(11) DEFAULT NULL,
  `receiver_id` INT(11) DEFAULT NULL,
  `is_read` TINYINT(1) DEFAULT '0',
  `receive_time` DATETIME DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `read_time` DATETIME DEFAULT NULL,
  `is_del` TINYINT(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `notice_setting`;

CREATE TABLE `notice_setting` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `org_code` VARCHAR(8) DEFAULT NULL COMMENT '企业编码',
  `notice_over_type` INT(1) DEFAULT NULL,
  `notice_type` INT(2) DEFAULT NULL,
  `is_send_mail` TINYINT(1) DEFAULT '0',
  `is_send_weixin` TINYINT(1) DEFAULT '0',
  `is_send_email` TINYINT(1) DEFAULT '0',
  `is_send_sms` TINYINT(1) DEFAULT '0',
  `receivers` VARCHAR(255) DEFAULT NULL,
  `update_user` INT(11) DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  `createa_user` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=INNODB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
  
  
  /* 数据初始化 */
  
  INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,0 AS notice_over_type,1 AS notice_type,0 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,0 AS notice_over_type,2 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,0 AS notice_over_type,3 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,0 AS notice_over_type,4 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,1 AS notice_over_type,5 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,2 AS notice_over_type,6 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,2 AS notice_over_type,7 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;
INSERT  INTO guiyu_notice.`notice_setting`(`org_code`,`notice_over_type`,`notice_type`,`is_send_mail`,`is_send_weixin`,`is_send_email`,`is_send_sms`,`create_time`) 
SELECT  `code`,2 AS notice_over_type,8 AS notice_type,1 AS is_send_mail,0 AS is_send_weixin,0 AS is_send_email,0 AS is_send_sms,'2019-02-19 00:00:00' AS create_time 
FROM guiyu_base.sys_organization;


UPDATE guiyu_notice.`notice_setting` m 
INNER JOIN (
SELECT GROUP_CONCAT(b.id) AS receivers ,b.org_code
FROM guiyu_base.`sys_role_user` a,guiyu_base.`sys_user` b
WHERE a.role_id =3 AND a.del_flag = 0
AND a.user_id = b.id
GROUP BY b.`org_code`
) n
ON m.`org_code` = n.org_code
SET m.receivers = n.receivers;
