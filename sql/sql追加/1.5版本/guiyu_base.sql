use guiyu_base;
DROP TABLE sys_user_ext;

CREATE TABLE `sys_user_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(13) DEFAULT NULL COMMENT '联系电话',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `api_url` varchar(255) DEFAULT NULL COMMENT '第三方api地址',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  `wechat` varchar(32) DEFAULT NULL COMMENT '微信号',
  `wechat_openid` varchar(64) DEFAULT NULL COMMENT '微信openid',
  `wechat_status` int(11) DEFAULT '0' COMMENT '微信绑定状态0未绑定1已绑定2已解绑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into sys_user_ext(user_id,create_time,update_time) select id,create_time,update_time from sys_user;

ALTER TABLE sys_user ADD is_desensitization int default 0 comment '号码是否脱敏0需要脱敏1不需要脱敏';