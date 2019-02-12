use guiyu_base;
DROP TABLE sys_user_ext;

CREATE TABLE `sys_user_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` bigint(20) unsigned NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '����',
  `email` varchar(100) DEFAULT NULL COMMENT '����',
  `mobile` varchar(13) DEFAULT NULL COMMENT '��ϵ�绰',
  `remarks` varchar(255) DEFAULT NULL COMMENT '��ע��Ϣ',
  `api_url` varchar(255) DEFAULT NULL COMMENT '������api��ַ',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT 'ɾ����ʶ0����1ɾ��',
  `wechat` varchar(32) DEFAULT NULL COMMENT '΢�ź�',
  `wechat_openid` varchar(64) DEFAULT NULL COMMENT '΢��openid',
  `wechat_status` int(11) DEFAULT '0' COMMENT '΢�Ű�״̬0δ��1�Ѱ�2�ѽ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into sys_user_ext(user_id,create_time,update_time) select id,create_time,update_time from sys_user;

ALTER TABLE sys_user ADD is_desensitization int default 0 comment '�����Ƿ�����0��Ҫ����1����Ҫ����';