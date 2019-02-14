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

#前端增加md5加密密码，所有账号密码重置为123456,各用户自行修改密码
update sys_user set password = '9970f16668b0ce09b694293b5164ae2b211fb9a23e9026bb4d0d1aef370f192120dd5f5a8e78c06d57fa036de0975c09b528ea7dc49262aee10c3247e62964fa';

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('1', '意向客户', 'noticeType', '消息类型，意向客户', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('2', '任务完成', 'noticeType', '消息类型，任务完成', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('3', '连续未接通警报', 'noticeType', '消息类型，连续未接通警报', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('4', '线路报错', 'noticeType', '消息类型，线路报错', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('5', '公告', 'noticeType', '消息类型，公告', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('6', '余额不足', 'noticeType', '消息类型，余额不足', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('7', '充值到账', 'noticeType', '消息类型，充值到账', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');

INSERT INTO `guiyu_base`.`sys_dict` ( `dict_key`, `dict_value`, `dict_type`, `description`, `pid`, `remarks`, `del_flag`, `create_time`, `update_time`, `create_id`, `update_id`) 
VALUES ('8', '账户到期', 'noticeType', '消息类型，账户到期', NULL, NULL, '0', '2019-01-29 17:32:01', '2019-01-29 17:32:01', '1', '1');