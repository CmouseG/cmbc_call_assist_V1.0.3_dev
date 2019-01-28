use guiyu_base;

CREATE TABLE `sys_organization_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����',
  `industry_id` varchar(32) NOT NULL COMMENT '��ҵid',
  `organization_id` int(11) NOT NULL COMMENT '��ҵid',
  `org_code` varchar(8) NOT NULL COMMENT '��ҵcode',
  `create_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) NOT NULL DEFAULT '0' COMMENT 'ɾ����ʶ0����1ɾ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
