use guiyu_base;

DROP TABLE IF EXISTS `sys_announcement`;
CREATE TABLE `sys_announcement` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(150) DEFAULT NULL COMMENT '公告标题',
  `type` int(11) DEFAULT '0' COMMENT '公告类型:0无类型1喜报2升级通知3服务通知',
  `content` varchar(200) DEFAULT NULL COMMENT '公告详情',
  `content_style` varchar(3000) DEFAULT NULL,
  `read_count` int(11) DEFAULT '0' COMMENT '阅读量',
  `file_paths` varchar(2000) DEFAULT NULL COMMENT '文件下载地址集合，多个文件|分隔',
  `create_id` int(11) DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标识:0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统公告';


DROP TABLE IF EXISTS `sys_organization_product`;
CREATE TABLE `sys_organization_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `organization_id` int NOT NULL COMMENT '组织id',
  `product` int NOT NULL COMMENT '产品id',
  `create_id` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` int DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#老组织数据默认产品系列为0
INSERT INTO sys_organization_product (organization_id,product,create_time,create_id,update_id,update_time,del_flag) SELECT b.id,0,now(),1,1,NOW(),0 FROM sys_organization b;

