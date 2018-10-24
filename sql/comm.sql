DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `sys_user_ext`;
DROP TABLE IF EXISTS `sys_role`;
DROP TABLE IF EXISTS `sys_menu`;
DROP TABLE IF EXISTS `sys_role_user`;
DROP TABLE IF EXISTS `sys_menu_role`;
DROP TABLE IF EXISTS `sys_file`;
DROP TABLE IF EXISTS `sys_dict`;
DROP TABLE IF EXISTS `sys_app`;
DROP TABLE IF EXISTS `sys_file`;
DROP TABLE IF EXISTS `sys_dict`;
DROP TABLE IF EXISTS `send_message`;
DROP TABLE IF EXISTS `receive_message`;
CREATE TABLE `sys_user`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(200) NOT NULL COMMENT '用户名',
  `password` VARCHAR(512) NOT NULL COMMENT '密码',
  `status` int(10) DEFAULT '1' COMMENT '1正常2冻结',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `sys_user_ext`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED  NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `api_url` VARCHAR(255) NOT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `sys_role`(
`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(200) NOT NULL COMMENT '名称',
`create_time` datetime DEFAULT NULL,
`update_time` datetime DEFAULT NULL,
`del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE `sys_menu`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `url` VARCHAR(255) NOT NULL,
  `pid` BIGINT DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `is_show` BIGINT DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` char(2) NOT NULL,
  `level` char(2) NOT NULL,
  `appid` BIGINT UNSIGNED,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;



CREATE TABLE `sys_role_user`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `role_id` BIGINT UNSIGNED NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;



CREATE TABLE `sys_menu_role`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` BIGINT UNSIGNED NOT NULL,
  `menu_id` BIGINT UNSIGNED NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE `sys_app`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) DEFAULT NULL, 
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;



CREATE TABLE `sys_file`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255),
  `busi_id` VARCHAR(255),
  `busi_type` char(2),
  `file_type` char(2),
  `file_size` VARCHAR(20),
  `url` VARCHAR(255),
  `sys_code` VARCHAR(255),
  `create_by` BIGINT UNSIGNED,
  `create_time` datetime,
  `update_by` BIGINT UNSIGNED,
  `update_time` datetime,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `sys_dict` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,  
  `dict_key` varchar(20) NOT NULL COMMENT '标签名',
  `dict_value` varchar(255) NOT NULL COMMENT '数据值',
  `dict_type` varchar(20) NOT NULL COMMENT '类型',
  `description` varchar(255) NOT NULL COMMENT '描述', 
  `pid` BIGINT UNSIGNED COMMENT '父级编号',  
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='字典表';


CREATE TABLE `send_message` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,  
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `type` varchar(10) NOT NULL COMMENT '类型',
  `create_by` BIGINT UNSIGNED NOT NULL COMMENT '创建人', 
  `create_time` datetime COMMENT '创建时间', 
  `update_time` datetime DEFAULT NULL,  
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',  
  PRIMARY KEY (`id`) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='消息表';

CREATE TABLE `receive_message` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,  
  `message_id` BIGINT UNSIGNED NOT NULL COMMENT '消息id',
  `receive_by` BIGINT UNSIGNED NOT NULL COMMENT '接收人',
  `status` char(2) NOT NULL COMMENT '是否读取状态',
  `read_time` datetime COMMENT '读取时间',  
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',  
  PRIMARY KEY (`id`) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='消息读取表';

CREATE TABLE `sys_user_action` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `action_name` varchar(255) NOT NULL DEFAULT '' COMMENT '行为名称',
  `user_id` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '操作用户ID',
  `add_time` datetime NOT NULL DEFAULT '0' COMMENT '操作时间',
  `data` text COMMENT '用户提交的数据',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '操作URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='用户操作日志表';

CREATE TABLE `sys_user_login_record` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login_times` BIGINT UNSIGNED NOT NULL COMMENT '账号登录次数',
  `last_login_ip` BIGINT UNSIGNED NOT NULL COMMENT '最后登录IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='用户登陆记录表';
