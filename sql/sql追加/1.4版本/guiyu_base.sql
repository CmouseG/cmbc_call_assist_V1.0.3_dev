use guiyu_base;

#增加组织和行业关系表
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

#初始化增加企业客服角色
INSERT INTO `guiyu_base`.`sys_role` (`id`, `name`, `desc`, `create_id`, `create_time`, `update_id`, `update_time`, `del_flag`, `init_role`, `super_admin`) VALUES ('5', '企业客服', '企业客服', '1', '2018-12-17 14:05:06', '1', '2019-01-28 10:11:31', '0', '0', '1');

#初始化存量企业数据，赋予所有行业
drop procedure if exists initOrgIndustry;
create procedure initOrgIndustry()
BEGIN
declare isDone int default 0;
declare intNumber int default 30000;
declare industryId varchar(32);


declare industryIds cursor for select A.industry_id from guiyu_botstence.bot_sentence_trade as A where A.`level` = 2;/*取出来所有需要循环的数据*/
declare continue handler for not FOUND set isDone = 1;
open industryIds;


REPEAT
fetch industryIds into industryId;
if not isDone THEN
INSERT INTO sys_organization_industry (
	industry_id,
	organization_id,
	org_code,
  create_id,
  create_time,
  update_id,
  update_time
) SELECT
	industryId,
	id,
	code,
  create_id,
  create_time,
  update_id,
  update_time
FROM
	sys_organization where id != 25;
set intNumber=intNumber+1;
end if;
until isDone end repeat;
close industryIds;
END;
call initOrgIndustry();
drop procedure initOrgIndustry;



