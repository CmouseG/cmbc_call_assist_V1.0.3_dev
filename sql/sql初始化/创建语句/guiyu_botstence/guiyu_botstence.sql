/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_botstence

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-12 17:11:40
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
-- Table structure for bot_available_template
-- ----------------------------
DROP TABLE IF EXISTS `bot_available_template`;
CREATE TABLE `bot_available_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` varchar(32) NOT NULL,
  `template_name` varchar(32) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `org_code` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bot_publish_sentence_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_publish_sentence_log`;
CREATE TABLE `bot_publish_sentence_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `temp_name` varchar(50) DEFAULT NULL,
  `template_id` varchar(32) NOT NULL,
  `process_id` varchar(32) NOT NULL,
  `create_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` varchar(10) NOT NULL COMMENT '状态1部署中2已上线3部署失败',
  `create_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bot_sentence_addition
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_addition`;
CREATE TABLE `bot_sentence_addition` (
  `process_id` varchar(32) NOT NULL,
  `sim_txt` mediumblob,
  `template_json` varchar(500) DEFAULT NULL,
  `weights_txt` varchar(255) DEFAULT NULL,
  `options_json` varchar(512) DEFAULT NULL,
  `stopwords_txt` varchar(255) DEFAULT NULL,
  `userdict_txt` blob,
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导出模板需要的一些附加信息';

-- ----------------------------
-- Table structure for bot_sentence_branch
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_branch`;
CREATE TABLE `bot_sentence_branch` (
  `branch_id` varchar(32) NOT NULL COMMENT 'branch编号',
  `branch_name` varchar(30) DEFAULT NULL,
  `seq` bigint(20) DEFAULT NULL COMMENT '序号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(30) DEFAULT NULL,
  `response` varchar(1024) DEFAULT NULL COMMENT '机器人应答',
  `NEXT` varchar(256) DEFAULT NULL COMMENT '下一domain',
  `intents` varchar(256) DEFAULT NULL COMMENT '意图列表',
  `END` varchar(256) DEFAULT NULL COMMENT '结束应答',
  `domain` varchar(256) DEFAULT NULL COMMENT '所属domain',
  `key_words` text COMMENT '关键词',
  `is_special_limit_free` varchar(1024) DEFAULT NULL COMMENT 'is_special_limit_free',
  `user_ask` varchar(256) DEFAULT NULL COMMENT '用户问答',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `line_name` varchar(50) DEFAULT NULL,
  `is_show` varchar(32) DEFAULT NULL,
  `respname` varchar(1024) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `agent_intent` varchar(256) DEFAULT NULL,
  `need_agent` varchar(32) DEFAULT NULL COMMENT '是否需要转人工',
  `weight` varchar(32) DEFAULT NULL COMMENT '权重',
  `rule` varchar(8) DEFAULT NULL COMMENT '跳转规则',
  PRIMARY KEY (`branch_id`),
  KEY `bot_sentence_branch_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='branch';

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
  `ignore_user_sentence` tinyint(1) DEFAULT NULL,
  `match_order` varchar(256) DEFAULT NULL,
  `not_match_less4_to` varchar(256) DEFAULT NULL,
  `not_match_to` varchar(256) DEFAULT NULL,
  `no_words_to` varchar(256) DEFAULT NULL,
  `is_special_limit_free` tinyint(1) DEFAULT NULL,
  `ignore_but_negative` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`domain_id`),
  KEY `idx_bot_sentence_domain_process_id` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='domain';

-- ----------------------------
-- Table structure for bot_sentence_grade
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_grade`;
CREATE TABLE `bot_sentence_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `stat_order` varchar(512) DEFAULT NULL COMMENT '意向排序',
  `init_stat` varchar(32) DEFAULT NULL COMMENT '默认意向',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bot_sentence_grade_rule
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_grade_rule`;
CREATE TABLE `bot_sentence_grade_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `rule_no` varchar(32) DEFAULT NULL COMMENT '规则编号',
  `intent_name` varchar(32) DEFAULT NULL COMMENT '意向标签',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `value1` varchar(32) DEFAULT NULL COMMENT '变量1',
  `value2` varchar(32) DEFAULT NULL COMMENT '变量2',
  `value3` varchar(32) DEFAULT NULL COMMENT '变量3',
  `value4` varchar(32) DEFAULT NULL COMMENT '变量4',
  `value5` varchar(32) DEFAULT NULL COMMENT '变量5',
  `show_seq` int(11) DEFAULT NULL COMMENT '显示顺序',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `remark` varchar(32) DEFAULT NULL COMMENT '意向备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2759 DEFAULT CHARSET=utf8 COMMENT='意向规则';

-- ----------------------------
-- Table structure for bot_sentence_industry
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_industry`;
CREATE TABLE `bot_sentence_industry` (
  `industry_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '行业分类编号',
  `industry_name` varchar(30) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`industry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='行业分类';

-- ----------------------------
-- Table structure for bot_sentence_intent
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_intent`;
CREATE TABLE `bot_sentence_intent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '意图ID',
  `name` varchar(100) DEFAULT NULL,
  `keywords` mediumtext COMMENT '关键词列表',
  `industry` varchar(32) DEFAULT NULL COMMENT '所处行业',
  `template_id` varchar(256) DEFAULT NULL COMMENT '话术模板编号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `for_select` int(11) DEFAULT NULL,
  `domain_name` varchar(50) DEFAULT NULL,
  `old_id` bigint(20) DEFAULT NULL,
  `refrence_id` bigint(20) DEFAULT NULL COMMENT '引用意图库ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189812 DEFAULT CHARSET=utf8 COMMENT='意图';

-- ----------------------------
-- Table structure for bot_sentence_keyword_audit_item
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_keyword_audit_item`;
CREATE TABLE `bot_sentence_keyword_audit_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword_audit_id` int(11) NOT NULL COMMENT '关键字审核ID',
  `keyword` varchar(100) NOT NULL COMMENT '关键字',
  `audit_user_id` int(11) DEFAULT NULL COMMENT '审核用户ID',
  `audit_status` int(11) DEFAULT '0' COMMENT '审核状态:0-待审核;1-已加入模板;2-已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `bot_sentence_keyword_audit_item_keyword_audit_id_index` (`keyword_audit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='关键字审核-关键字关联表';

-- ----------------------------
-- Table structure for bot_sentence_keyword_template
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_keyword_template`;
CREATE TABLE `bot_sentence_keyword_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `industry_id` varchar(32) DEFAULT NULL COMMENT '行业编号',
  `industry_name` varchar(64) DEFAULT NULL COMMENT '行业名称',
  `intent_name` varchar(32) DEFAULT NULL COMMENT '意图名称',
  `keywords` mediumtext COMMENT '关键词列表',
  `type` varchar(8) DEFAULT NULL COMMENT '00-通用  01-行业',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10525 DEFAULT CHARSET=utf8 COMMENT='关键词库';

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
-- Table structure for bot_sentence_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_log`;
CREATE TABLE `bot_sentence_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作',
  `param` varchar(512) DEFAULT NULL COMMENT '请求参数',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1426 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Table structure for bot_sentence_options
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_options`;
CREATE TABLE `bot_sentence_options` (
  `options_id` varchar(32) NOT NULL COMMENT 'ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `check_sim` tinyint(1) DEFAULT NULL COMMENT '检查相似词',
  `cur_domain_prior` tinyint(1) DEFAULT NULL COMMENT '优先匹配当前域',
  `use_not_match_logic` tinyint(1) DEFAULT NULL COMMENT '使用未匹配逻辑',
  `not_match_solution` varchar(64) DEFAULT NULL COMMENT '未匹配解决方案',
  `use_endfiles_list` tinyint(1) DEFAULT NULL COMMENT '使用结束列表',
  `trade` varchar(64) DEFAULT NULL COMMENT '模板类型',
  `tempname` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `non_interruptable_start` tinyint(1) DEFAULT NULL COMMENT '是否开启不打断配置',
  `non_interruptable` varchar(256) DEFAULT NULL COMMENT '不打断配置',
  `silence_wait_start` tinyint(1) DEFAULT NULL COMMENT '是否开启静音时间与次数',
  `silence_wait_secs` int(11) DEFAULT NULL COMMENT '静音超时时间',
  `silence_wait_time` int(11) DEFAULT NULL COMMENT '允许静音超时次数',
  `silence_as_empty` tinyint(1) DEFAULT NULL COMMENT '是否开启静音转空规则',
  `user_define_match_order` tinyint(1) DEFAULT NULL COMMENT '是否开启用户自定义匹配优先级',
  `grub_start` tinyint(1) DEFAULT NULL COMMENT '是否启用回答引导',
  `positive_interruptable` tinyint(1) DEFAULT NULL COMMENT '所有关键词中是否增加肯定POSITIVE域的关键词',
  `interruptable_domain_start` tinyint(1) DEFAULT NULL COMMENT '开场白可被解释开场白打断',
  `global_interruptable_domains_start` tinyint(1) DEFAULT NULL COMMENT '是否开启开场白可被全局打断域',
  `global_interruptable_domains` varchar(256) DEFAULT NULL COMMENT '开场白可被全局打断域',
  `ignore_but_domains_start` tinyint(1) DEFAULT NULL COMMENT '是否开启不忽略的域',
  `ignore_user_sentence_start` tinyint(1) DEFAULT NULL COMMENT '是否开启无论说什么话，都继续往下走',
  `ignore_but_negative_start` tinyint(1) DEFAULT NULL COMMENT '是否开启除拒绝之外，都继续往下走',
  `asr_engine` varchar(64) DEFAULT NULL COMMENT '识别引擎',
  `multi_keyword_all` tinyint(1) DEFAULT NULL COMMENT '是否使用多关键词',
  `not_match_less4_to_start` tinyint(1) DEFAULT NULL COMMENT '是否开启每个域未匹配走向配置',
  `not_match_less_num` int(11) DEFAULT NULL COMMENT '字数配置',
  `not_match_to_start` tinyint(1) DEFAULT NULL COMMENT '是否开启未匹配到任何关键词的走向',
  `no_words_to_start` tinyint(1) DEFAULT NULL COMMENT '是否开启无声音的走向',
  `interruption_config_start` tinyint(1) DEFAULT NULL COMMENT '是否开启新打断规则',
  `interrupt_words_num` int(11) DEFAULT NULL COMMENT '允许打断的字数',
  `interrupt_min_interval` int(11) DEFAULT NULL COMMENT '两次打断之间最小间隔的秒数',
  `voice` varchar(256) DEFAULT NULL COMMENT '和voice机器人在打断后重复上句时需要加上的前缀语',
  `special_limit_start` tinyint(1) DEFAULT NULL COMMENT '是否开启重复次数是否受限',
  `special_limit` int(11) DEFAULT NULL COMMENT '限制special被问的次数',
  `survey_start` tinyint(1) DEFAULT NULL COMMENT '是否启用回访规则',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`options_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术options数据';

-- ----------------------------
-- Table structure for bot_sentence_options_level
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_options_level`;
CREATE TABLE `bot_sentence_options_level` (
  `options_level_id` varchar(32) NOT NULL COMMENT '编号',
  `process_id` varchar(32) NOT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `domain` varchar(32) DEFAULT NULL COMMENT 'domain',
  `level1` varchar(32) DEFAULT NULL COMMENT '优先级1',
  `level2` varchar(32) DEFAULT NULL COMMENT '优先级2',
  `level3` varchar(32) DEFAULT NULL COMMENT '优先级3',
  `level4` varchar(32) DEFAULT NULL COMMENT '优先级4',
  `level5` varchar(32) DEFAULT NULL COMMENT '优先级5',
  `level6` varchar(32) DEFAULT NULL COMMENT '优先级6',
  `level7` varchar(32) DEFAULT NULL COMMENT '优先级7',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`options_level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程优先级';

-- ----------------------------
-- Table structure for bot_sentence_process
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_process`;
CREATE TABLE `bot_sentence_process` (
  `process_id` varchar(32) NOT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `template_name` varchar(50) DEFAULT NULL,
  `template_type` varchar(8) DEFAULT NULL COMMENT '00-行业通用模板            \n01-自定义模板',
  `version` varchar(8) DEFAULT NULL COMMENT '版本号',
  `industry` varchar(30) DEFAULT NULL,
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
  `org_code` varchar(32) DEFAULT NULL,
  `org_name` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `industry_id` varchar(32) DEFAULT NULL COMMENT '行业编号',
  `test_state` varchar(8) DEFAULT NULL COMMENT '测试部署状态',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程';

-- ----------------------------
-- Table structure for bot_sentence_sellbot_machine
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_sellbot_machine`;
CREATE TABLE `bot_sentence_sellbot_machine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机器编号',
  `ip` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '机器IP',
  `username` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '密码',
  `path` varchar(256) CHARACTER SET latin1 DEFAULT NULL COMMENT '模板路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sellbot机器信息';

-- ----------------------------
-- Table structure for bot_sentence_share_auth
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_share_auth`;
CREATE TABLE `bot_sentence_share_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `template_name` varchar(256) DEFAULT NULL COMMENT '话术模板名称',
  `type` varchar(8) DEFAULT NULL COMMENT '00-所有用户 00-指定机构',
  `available_org` varchar(256) DEFAULT NULL COMMENT '可见机构',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '分享者昵称',
  `share_count` int(11) DEFAULT NULL COMMENT '使用次数',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `shared` tinyint(1) DEFAULT NULL COMMENT '是否分享',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='话术分享权限';

-- ----------------------------
-- Table structure for bot_sentence_share_history
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_share_history`;
CREATE TABLE `bot_sentence_share_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `template_name` varchar(256) DEFAULT NULL COMMENT '话术模板名称',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '分享者昵称',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='话术分享使用记录';

-- ----------------------------
-- Table structure for bot_sentence_survey
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_survey`;
CREATE TABLE `bot_sentence_survey` (
  `survey_id` varchar(32) NOT NULL COMMENT '回访规则ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `domain` varchar(32) DEFAULT NULL COMMENT 'domain',
  `type` varchar(32) DEFAULT NULL COMMENT 'INTENT            \r\nSENTENCE            \r\nKEY',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程回访规则';

-- ----------------------------
-- Table structure for bot_sentence_survey_intent
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_survey_intent`;
CREATE TABLE `bot_sentence_survey_intent` (
  `survey_intent_id` varchar(32) NOT NULL COMMENT '回访规则ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `domain` varchar(32) DEFAULT NULL COMMENT 'domain',
  `intent_name` varchar(32) DEFAULT NULL COMMENT '意图名称',
  `intent_keys` varchar(15000) DEFAULT NULL COMMENT '意图关键词',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `type` varchar(32) DEFAULT NULL COMMENT '00-默认  01-自定义',
  PRIMARY KEY (`survey_intent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程回访规则意图信息';

-- ----------------------------
-- Table structure for bot_sentence_template
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_template`;
CREATE TABLE `bot_sentence_template` (
  `process_id` varchar(32) NOT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `template_name` varchar(50) DEFAULT NULL,
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
-- Table structure for bot_sentence_trade
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_trade`;
CREATE TABLE `bot_sentence_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `industry_id` varchar(256) DEFAULT NULL COMMENT '行业编号',
  `industry_name` varchar(256) DEFAULT NULL COMMENT '行业名称',
  `parent_id` varchar(256) DEFAULT NULL COMMENT '上级行业编号',
  `parent_name` varchar(256) DEFAULT NULL COMMENT '上级行业名称',
  `level` int(11) DEFAULT NULL,
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 COMMENT='行业分类';

-- ----------------------------
-- Table structure for bot_sentence_tts_backup
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_tts_backup`;
CREATE TABLE `bot_sentence_tts_backup` (
  `backup_id` varchar(32) NOT NULL COMMENT '备份话术ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `volice_id` bigint(20) DEFAULT NULL COMMENT '录音ID',
  `content` varchar(1024) DEFAULT NULL COMMENT '文案内容',
  `url` varchar(512) DEFAULT NULL COMMENT '文案URL',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `times` int(11) DEFAULT NULL COMMENT '录音时长',
  `wav_name` varchar(32) DEFAULT NULL COMMENT '录音文件名',
  PRIMARY KEY (`backup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术模板TTS备份话术';

-- ----------------------------
-- Table structure for bot_sentence_tts_content
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_tts_content`;
CREATE TABLE `bot_sentence_tts_content` (
  `content_id` varchar(32) NOT NULL COMMENT '文案编号',
  `content` varchar(1024) DEFAULT NULL COMMENT '文案内容',
  `url` varchar(512) DEFAULT NULL COMMENT '文案URL',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `sound_type` varchar(32) DEFAULT NULL COMMENT '录音师类型',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术模板TTS文案';

-- ----------------------------
-- Table structure for bot_sentence_tts_param
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_tts_param`;
CREATE TABLE `bot_sentence_tts_param` (
  `param_id` varchar(32) NOT NULL COMMENT '参数ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模板编号',
  `param_key` varchar(32) DEFAULT NULL COMMENT '参数编号',
  `param_type` varchar(8) DEFAULT NULL COMMENT '参数类型',
  `volice_id` bigint(20) DEFAULT NULL COMMENT '录音ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术模板TTS参数';

-- ----------------------------
-- Table structure for bot_sentence_tts_task
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_tts_task`;
CREATE TABLE `bot_sentence_tts_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'TTS合成ID',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `busi_id` varchar(32) DEFAULT NULL COMMENT '业务ID',
  `busi_type` varchar(8) DEFAULT NULL COMMENT '01-通话录音\n            02-备用录音',
  `content` varchar(1024) DEFAULT NULL COMMENT '文案内容',
  `volice_url` varchar(512) DEFAULT NULL COMMENT '文案URL',
  `status` varchar(8) DEFAULT NULL COMMENT '00-新建\n            01-合成中\n            02-已完成',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `sound_type` varchar(32) DEFAULT NULL COMMENT '录音师类型',
  `seq` varchar(32) DEFAULT NULL COMMENT '文案序号',
  `is_param` varchar(32) DEFAULT NULL COMMENT '是否为变量',
  `times` int(11) DEFAULT NULL COMMENT '录音时长',
  `wav_name` varchar(32) DEFAULT NULL COMMENT '录音文件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1580 DEFAULT CHARSET=utf8 COMMENT='TTS合成任务表';

-- ----------------------------
-- Table structure for bot_sentence_wechat_user_info
-- ----------------------------
DROP TABLE IF EXISTS `bot_sentence_wechat_user_info`;
CREATE TABLE `bot_sentence_wechat_user_info` (
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `account_no` varchar(32) DEFAULT NULL COMMENT '账号',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配音小程序用户信息';

-- ----------------------------
-- Table structure for bot_user_available
-- ----------------------------
DROP TABLE IF EXISTS `bot_user_available`;
CREATE TABLE `bot_user_available` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `available_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=642 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tab_administrator
-- ----------------------------
DROP TABLE IF EXISTS `tab_administrator`;
CREATE TABLE `tab_administrator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `userpwd` varchar(50) NOT NULL,
  `tip` varchar(15) DEFAULT NULL,
  `loginline` varchar(11) DEFAULT NULL,
  `add_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `groupid` smallint(3) NOT NULL DEFAULT '1' COMMENT '权限组id',
  `valid_flag` char(1) NOT NULL DEFAULT '0' COMMENT '有效为1',
  `termdate` date DEFAULT NULL COMMENT '有效期',
  `customer_id` int(11) DEFAULT '0' COMMENT '客户群ID',
  `manage_customers` text COMMENT '企业管理的权限',
  `db` int(5) NOT NULL DEFAULT '1',
  `phone` varchar(15) DEFAULT NULL COMMENT '绑定手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '账户绑定的邮件',
  `surplus` double(9,2) NOT NULL DEFAULT '0.00',
  `role` tinyint(3) NOT NULL DEFAULT '1' COMMENT '角色',
  `balance` int(11) DEFAULT '0' COMMENT '单位：分',
  `industrytemp` varchar(1000) DEFAULT NULL,
  `phone_label` varchar(250) DEFAULT 'A,B,C,D,E,F,W' COMMENT '来电可视标签配置',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `code` varchar(32) DEFAULT NULL,
  `port_num` int(11) NOT NULL DEFAULT '0' COMMENT '端口数',
  `serial_number` varchar(128) DEFAULT NULL,
  `machine_code` varchar(30) DEFAULT NULL COMMENT '机器码',
  `operate` text COMMENT '关联用户',
  `temp` text COMMENT '管理模板',
  `phone_port` varchar(255) DEFAULT NULL COMMENT '所用的电话端口',
  `islimit` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1限制工作时间才能拨出（9:00--20:00），2不限制',
  `limit_num` tinyint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_tab_customer_serial_number` (`serial_number`),
  KEY `idx_tab_customer_serial_number_machine_code` (`serial_number`,`machine_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

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
-- Table structure for user_account_trade_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_account_trade_relation`;
CREATE TABLE `user_account_trade_relation` (
  `relation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `account_no` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `account_name` varchar(32) DEFAULT NULL COMMENT '账号名称',
  `industry_id` varchar(1024) DEFAULT NULL COMMENT '行业ID',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号行业权限表';

-- ----------------------------
-- Table structure for volice_info
-- ----------------------------
DROP TABLE IF EXISTS `volice_info`;
CREATE TABLE `volice_info` (
  `volice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '录音ID',
  `volice_url` varchar(512) DEFAULT NULL COMMENT '录音文件URL',
  `process_id` varchar(32) DEFAULT NULL COMMENT '话术流程编号',
  `template_id` varchar(256) DEFAULT NULL COMMENT '话术模板编号',
  `domain_name` varchar(50) DEFAULT NULL,
  `type` varchar(8) DEFAULT NULL COMMENT '类型',
  `num` varchar(32) DEFAULT NULL COMMENT '编号',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crt_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `lst_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `lst_update_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `name` varchar(256) DEFAULT NULL,
  `flag` varchar(32) DEFAULT NULL,
  `old_id` bigint(20) DEFAULT NULL,
  `need_tts` tinyint(1) DEFAULT NULL COMMENT '是否需要tts合成',
  `times` int(11) DEFAULT NULL COMMENT '录音时长',
  `wav_name` varchar(32) DEFAULT NULL COMMENT '录音文件名',
  PRIMARY KEY (`volice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=215981 DEFAULT CHARSET=utf8 COMMENT='录音信息';

-- ----------------------------
-- Function structure for genTabId
-- ----------------------------
DROP FUNCTION IF EXISTS `genTabId`;
DELIMITER ;;
CREATE DEFINER=`botstence`@`%` FUNCTION `genTabId`(SEQ_NAME VARCHAR(64)) RETURNS varchar(32) CHARSET utf8
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
;;
DELIMITER ;
