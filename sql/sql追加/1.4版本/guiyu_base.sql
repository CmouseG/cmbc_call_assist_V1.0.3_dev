use guiyu_base;

CREATE TABLE `sys_organization_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `industry_id` varchar(32) NOT NULL COMMENT '行业id',
  `organization_id` int(11) NOT NULL COMMENT '企业id',
  `org_code` varchar(8) NOT NULL COMMENT '企业code',
  `create_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `guiyu_base`.`sys_role` (`id`, `name`, `desc`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `init_role`, `super_admin`) VALUES ('5', '企业客服', '企业客服', '1', '2018-12-17 14:05:06', '1', '2019-01-28 10:11:31', '0', '0', '1');

