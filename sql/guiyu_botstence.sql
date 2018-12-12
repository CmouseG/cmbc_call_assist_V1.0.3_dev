/*
Navicat MySQL Data Transfer

Source Server         : 测试环境
Source Server Version : 50707
Source Host           : 192.168.1.81:3306
Source Database       : guiyu_botstence

Target Server Type    : MYSQL
Target Server Version : 50707
File Encoding         : 65001

Date: 2018-12-03 17:35:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bd_table_sequence
-- ----------------------------
DROP TABLE IF EXISTS `bd_table_sequence`;
CREATE TABLE `bd_table_sequence` (
  `table_name` varchar(64) NOT NULL,
  `seq` bigint(12) DEFAULT NULL,
  `step` int(6) DEFAULT NULL,
  `SIGN` varchar(6) DEFAULT NULL,
  `now_date` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`table_name`),
  KEY `table_name` (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bot_sentence_addition
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_addition`;
CREATE TABLE `bot_sentence_addition` (
  `process_id` varchar(32) NOT NULL,
  `sim_txt` mediumblob,
  `template_json` varchar(500) DEFAULT NULL,
  `weights_txt` varchar(255) DEFAULT NULL,
  `options_json` varchar(255) DEFAULT NULL,
  `stopwords_txt` varchar(255) DEFAULT NULL,
  `userdict_txt` blob,
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导出模板需要的一些附加信息';

-- ----------------------------
-- Table structure for bot_sentence_branch
-- ----------------------------
DROP TABLE IF EXISTS bot_sentence_branch;
CREATE TABLE bot_sentence_branch (branch_id varchar(32) NOT NULL COMMENT 'branch编号', branch_name varchar(256) COMMENT 'branch名称', seq bigint COMMENT '序号', process_id varchar(32) COMMENT '话术流程编号', template_id varchar(256) COMMENT '话术模板编号', response varchar(1024) COMMENT '机器人应答', NEXT varchar(256) COMMENT '下一domain', intents varchar(256) COMMENT '意图列表', END varchar(256) COMMENT '结束应答', domain varchar(256) COMMENT '所属domain', key_words text COMMENT '关键词', is_special_limit_free varchar(1024) COMMENT 'is_special_limit_free', user_ask varchar(256) COMMENT '用户问答', crt_time datetime COMMENT '创建时间', crt_user varchar(32) COMMENT '创建人', lst_update_time datetime COMMENT '最后修改时间', lst_update_user varchar(32) COMMENT '最后修改人', line_name varchar(256), is_show varchar(32), respname varchar(1024), type varchar(32), PRIMARY KEY (branch_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='branch';


-- ----------------------------
-- Table structure for bot_sentence_domain
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_domain`;
CREATE TABLE `bot_sentence_domain` (
  `domain_id` varchar(32) NOT NULL COMMENT '节点编号',
  `domain_name` varchar(256) DEFAULT NULL COMMENT 'domain名称',
  `template_id` varchar(256) DEFAULT NULL COMMENT '话术模板编号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `com_domain` varchar(256) DEFAULT NULL COMMENT '下一主流程domain',
  `category` varchar(8) DEFAULT NULL COMMENT '1-主流程            \n2-业务问答            \n3-通用对话',
  `ignore_but_domains` varchar(1024) DEFAULT NULL COMMENT 'ignore_but_domains',
  `is_interrupt` varchar(8) DEFAULT NULL COMMENT '01-是            \n02-否',
  `is_main_flow` varchar(8) DEFAULT NULL COMMENT '01-是            \n02-否',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `type` varchar(32) DEFAULT NULL,
  `parent` varchar(256) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `position_x` int(11) DEFAULT NULL,
  `position_y` int(11) DEFAULT NULL,
  PRIMARY KEY (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='domain';

-- ----------------------------
-- Table structure for bot_sentence_industry
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_industry`;
CREATE TABLE `bot_sentence_industry` (
  `industry_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '行业分类编号',
  `industry_name` varchar(256) DEFAULT NULL COMMENT '行业名称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`industry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='行业分类';

-- ----------------------------
-- Table structure for bot_sentence_intent
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_intent`;
CREATE TABLE `bot_sentence_intent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '意图ID',
  `name` varchar(256) DEFAULT NULL COMMENT '意图名',
  `keywords` text COMMENT '关键词列表',
  `industry` varchar(32) DEFAULT NULL COMMENT '所处行业',
  `template_id` varchar(256) DEFAULT NULL COMMENT '话术模板编号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `for_select` int(11) DEFAULT NULL,
  `domain_name` varchar(256) DEFAULT NULL,
  `old_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3258 DEFAULT CHARSET=utf8 COMMENT='意图';


-- ----------------------------
-- Table structure for bot_sentence_label
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_label`;
CREATE TABLE `bot_sentence_label` (
  `label_id` varchar(32) NOT NULL,
  `process_id` varchar(32) DEFAULT NULL,
  `label_name` varchar(10) DEFAULT NULL,
  `conversation_count` int(11) DEFAULT NULL,
  `keywords` varchar(15000) DEFAULT NULL,
  `display_keywords` varchar(1000) DEFAULT NULL,
  `display_keywords_before` varchar(1000) DEFAULT NULL,
  `special_count` int(11) DEFAULT NULL,
  `used_time_s` int(11) DEFAULT NULL,
  `deny_count` int(11) DEFAULT NULL,
  `busy_count` int(11) DEFAULT NULL,
  `score_up` double DEFAULT NULL,
  `score_low` double DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `crt_user` varchar(32) DEFAULT NULL,
  `lst_update_time` datetime DEFAULT NULL,
  `lst_update_user` varchar(32) DEFAULT NULL,
  `show_name` varchar(255) DEFAULT NULL,
  `help_detail` varchar(500) DEFAULT NULL,
  `annotation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意向标签';


-- ----------------------------
-- Table structure for bot_sentence_process
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_process`;
CREATE TABLE `bot_sentence_process` (
  `process_id` varchar(32) NOT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `template_name` varchar(256) DEFAULT NULL COMMENT '模板名称',
  `template_type` varchar(8) DEFAULT NULL COMMENT '00-行业通用模板            \n01-自定义模板',
  `version` varchar(8) DEFAULT NULL COMMENT '版本号',
  `industry` varchar(256) DEFAULT NULL COMMENT '所属行业',
  `account_no` varchar(32) DEFAULT NULL COMMENT '所属账号',
  `old_process_id` varchar(32) DEFAULT NULL COMMENT '原话术流程编号',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `state` varchar(8) DEFAULT NULL COMMENT '00-制作中            \n01-审核中            \n02-审核通过            \n03-审核不通过            \n04-已上线',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_user` varchar(32) DEFAULT NULL COMMENT '审批人',
  `approve_notes` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `sound_type` varchar(32) DEFAULT NULL COMMENT '录音师编号',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程';

-- ----------------------------
-- Table structure for bot_sentence_sellbot_machine
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_sellbot_machine`;
CREATE TABLE `bot_sentence_sellbot_machine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机器编号',
  `ip` varchar(64) DEFAULT NULL COMMENT '机器IP',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `path` varchar(256) DEFAULT NULL COMMENT '模板路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='sellbot机器信息';


-- ----------------------------
-- Table structure for bot_sentence_template
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_template`;
CREATE TABLE `bot_sentence_template` (
  `process_id` varchar(32) NOT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `template_name` varchar(256) DEFAULT NULL COMMENT '模板名称',
  `version` varchar(32) DEFAULT NULL COMMENT '版本号',
  `template_type` varchar(8) DEFAULT NULL COMMENT '01-公开            \n02-个人',
  `state` varchar(8) DEFAULT NULL COMMENT '状态',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `account_no` varchar(256) DEFAULT NULL,
  `industry_id` varchar(256) DEFAULT NULL,
  `industry_name` varchar(256) DEFAULT NULL,
  `host` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已生效话术模板';


-- ----------------------------
-- Table structure for bot_sentence_tts_backup
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_tts_backup`;
CREATE TABLE `bot_sentence_tts_backup` (
  `backup_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '备份话术ID',
  `process_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '话术模板编号',
  `volice_id` bigint(20) DEFAULT NULL COMMENT '录音ID',
  `content` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案内容',
  `url` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案URL',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`backup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='话术模板TTS备份话术';


DROP TABLE IF EXISTS `bot_sentence_tts_content`;
CREATE TABLE `bot_sentence_tts_content` (
  `content_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '文案编号',
  `content` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案内容',
  `url` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案URL',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `sound_type` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '录音师类型',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='话术模板TTS文案';


DROP TABLE IF EXISTS `bot_sentence_tts_param`;
CREATE TABLE `bot_sentence_tts_param` (
  `param_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '参数ID',
  `process_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '话术模板编号',
  `param_key` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '参数编号',
  `param_type` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '参数类型',
  `volice_id` bigint(20) DEFAULT NULL COMMENT '录音ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='话术模板TTS参数';

DROP TABLE IF EXISTS `bot_sentence_tts_task`;
CREATE TABLE `bot_sentence_tts_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'TTS合成ID',
  `process_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '话术流程编号',
  `busi_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务ID',
  `busi_type` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '01-通话录音\n            02-备用录音',
  `content` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案内容',
  `volice_url` varchar(512) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案URL',
  `status` varchar(8) CHARACTER SET utf8 DEFAULT NULL COMMENT '00-新建\n            01-合成中\n            02-已完成',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后修改人',
  `sound_type` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '录音师类型',
  `seq` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '文案序号',
  `is_param` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否为变量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1 COMMENT='TTS合成任务表';


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编码',
  `p_code` varchar(255) DEFAULT NULL COMMENT '菜单父编码',
  `p_id` varchar(255) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `is_menu` varchar(2) DEFAULT NULL COMMENT '是否是菜单',
  `level` varchar(2) DEFAULT NULL COMMENT '菜单层级',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `status` varchar(2) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for sys_org_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_info`;
CREATE TABLE `sys_org_info` (
  `id` varchar(32) NOT NULL,
  `org_id` varchar(20) DEFAULT NULL COMMENT '机构号',
  `org_name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `org_level` varchar(2) DEFAULT NULL COMMENT '00-总部            \n01-区域            \n02-城市            \n03-门店',
  `up_org_id` varchar(20) DEFAULT NULL COMMENT '上级机构号',
  `org_seq` varchar(100) DEFAULT NULL COMMENT '机构序列，上级机构,分割',
  `status` varchar(2) DEFAULT NULL COMMENT 'S-正常, F-失效',
  `crt_user` varchar(20) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL,
  `lst_update_user` varchar(20) DEFAULT NULL,
  `lst_update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `role_id` varchar(32) NOT NULL,
  `menu_id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `tips` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `userid` varchar(32) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `loginid` varchar(20) DEFAULT NULL,
  `password` varchar(96) DEFAULT NULL,
  `token_password` varchar(96) DEFAULT NULL,
  `org_id` varchar(20) DEFAULT NULL,
  `up_userid` varchar(20) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `head_ico_url` varchar(200) DEFAULT NULL,
  `personal_card_url` varchar(200) DEFAULT NULL,
  `status` varchar(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tab_administrator
-- ----------------------------
DROP TABLE IF EXISTS `tab_administrator`;
CREATE TABLE `tab_administrator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `userpwd` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `tip` varchar(15) CHARACTER SET utf8 DEFAULT NULL,
  `loginline` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `add_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `groupid` smallint(3) NOT NULL DEFAULT '1' COMMENT '权限组id',
  `valid_flag` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '有效为1',
  `termdate` date DEFAULT NULL COMMENT '有效期',
  `customer_id` int(11) DEFAULT '0' COMMENT '客户群ID',
  `manage_customers` text COLLATE utf8_unicode_ci COMMENT '企业管理的权限',
  `db` int(5) NOT NULL DEFAULT '1',
  `phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '绑定手机号码',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户绑定的邮件',
  `surplus` double(9,2) NOT NULL DEFAULT '0.00',
  `role` tinyint(3) NOT NULL DEFAULT '1' COMMENT '角色',
  `balance` int(11) DEFAULT '0' COMMENT '单位：分',
  `industrytemp` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_label` varchar(250) COLLATE utf8_unicode_ci DEFAULT 'A,B,C,D,E,F,W' COMMENT '来电可视标签配置',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Table structure for tab_customer
-- ----------------------------
DROP TABLE IF EXISTS `tab_customer`;
CREATE TABLE `tab_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `adddate` date DEFAULT NULL COMMENT '注册日期',
  `termdate` date DEFAULT NULL COMMENT '有效期',
  `code` varchar(255) DEFAULT NULL COMMENT '机器mac地址',
  `port_num` int(11) NOT NULL DEFAULT '0' COMMENT '端口数',
  `serial_number` varchar(255) DEFAULT NULL COMMENT '序列号',
  `machine_code` varchar(30) DEFAULT NULL COMMENT '机器码',
  `operate` text COMMENT '关联用户',
  `temp` text COMMENT '管理模板',
  `phone_port` varchar(255) DEFAULT NULL COMMENT '所用的电话端口',
  `islimit` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1限制工作时间才能拨出（9:00--20:00），2不限制',
  `limit_num` tinyint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `serial_number` (`serial_number`),
  KEY `serial_number_2` (`serial_number`,`machine_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Records of tab_customer
-- ----------------------------

-- ----------------------------
-- Table structure for tab_industry
-- ----------------------------
DROP TABLE IF EXISTS `tab_industry`;
CREATE TABLE `tab_industry` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '行业名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_industry
-- ----------------------------

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `user_no` varchar(32) DEFAULT NULL COMMENT '账号组编号',
  `account_no` varchar(32) DEFAULT NULL COMMENT '账号编号',
  `machine_code` varchar(128) DEFAULT NULL COMMENT '机器码',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `host` varchar(32) DEFAULT NULL,
  `full_host` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号信息';

-- ----------------------------
-- Records of user_account
-- ----------------------------

-- ----------------------------
-- Table structure for user_account_industry_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_account_industry_relation`;
CREATE TABLE `user_account_industry_relation` (
  `relation_id` varchar(32) NOT NULL COMMENT '关系ID',
  `account_no` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `account_name` varchar(32) DEFAULT NULL COMMENT '账号名称',
  `industry_id` varchar(32) DEFAULT NULL COMMENT '行业ID',
  `industry_name` varchar(32) DEFAULT NULL COMMENT '行业名称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号行业权限表';

-- ----------------------------
-- Records of user_account_industry_relation
-- ----------------------------
INSERT INTO `user_account_industry_relation` VALUES ('20151011RELA00000776375', '16', '209-zhouqi', '20181115TEM00002071917', '离线测试模板', '2018-11-20 09:42:29', '');
INSERT INTO `user_account_industry_relation` VALUES ('20151011RELA00000776376', '16', 'admin', '20181121TEM00000029793', '离线测试模板', '2018-11-20 09:42:29', '');

-- ----------------------------
-- Table structure for volice_info
-- ----------------------------
DROP TABLE IF EXISTS volice_info;
CREATE TABLE volice_info (volice_id bigint NOT NULL AUTO_INCREMENT COMMENT '录音ID', volice_url varchar(512) COMMENT '录音文件URL', process_id varchar(32) COMMENT '话术流程编号', template_id varchar(256) COMMENT '话术模板编号', domain_name varchar(256) COMMENT '所属domain', type varchar(8) COMMENT '类型', num varchar(32) COMMENT '编号', content varchar(1024) COMMENT '内容', crt_time datetime COMMENT '创建时间', crt_user varchar(32) COMMENT '创建人', lst_update_time datetime COMMENT '最后修改时间', lst_update_user varchar(32) COMMENT '最后修改人', name varchar(256), flag varchar(32), old_id bigint, PRIMARY KEY (volice_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='录音信息';

DROP TABLE IF EXISTS bot_available_template;
CREATE TABLE `bot_available_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` varchar(32) NOT NULL,
  `template_name` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS bot_publish_sentence_log;
CREATE TABLE `bot_publish_sentence_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `temp_name` varchar(255) DEFAULT NULL,
  `template_id` varchar(32) NOT NULL,
  `process_id` varchar(32) NOT NULL,
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` varchar(10) NOT NULL COMMENT '状态1部署中2已上线3部署失败',
  `create_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;


drop FUNCTION IF EXISTS `genTabId`;
CREATE FUNCTION `genTabId`(SEQ_NAME VARCHAR(64)) RETURNS varchar(32) CHARSET utf8
    READS SQL DATA
BEGIN
	DECLARE TMP VARCHAR(14);
	DECLARE TMP1 BIGINT;
	DECLARE TMP2 VARCHAR(6);
	DECLARE TMP3 VARCHAR(8);

	UPDATE bd_table_sequence SET seq = seq + step WHERE table_name = SEQ_NAME;
	SET TMP = DATE_FORMAT(NOW(),'%Y%m%d');
	SELECT seq,sign,now_date INTO TMP1,TMP2,TMP3 FROM bd_table_sequence WHERE table_name=SEQ_NAME;
  IF TMP3 = '' THEN
		SET TMP3 = '19700101';
	ELSEIF  TMP3 = NULL  THEN
		SET TMP3 = '19700101';
	END IF;
	IF TMP3 <> TMP THEN 
		UPDATE bd_table_sequence SET seq = 0 ,now_date = TMP WHERE table_name = SEQ_NAME;
	END IF;
	RETURN CONCAT(TMP,TMP2, LPAD(TMP1, 7, '0'),RIGHT(UUID_SHORT(),4));
END