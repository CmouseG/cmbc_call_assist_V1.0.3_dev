--573替换成上线新增的按钮id，具体到时候让前端薛仁伟先配置按钮，用配置的按钮产生的id替换573
--新增按钮刷老权限组
insert into sys_privilege(auth_id,auth_type,resource_id,resource_type,org_code,crt_user,crt_time,update_time,update_user)
select a.id,3,'',1,a.org_code,1,'2019-04-09 00:00:00','2019-04-09 00:00:00',1 from sys_role a, sys_privilege b where a.id = b.auth_id and b.resource_id = '551' and b.resource_type = 1 and b.auth_type = 3;
--新增按钮刷老组织
insert into sys_privilege(auth_id,auth_type,resource_id,resource_type,org_code,crt_user,crt_time,update_time,update_user)
select distinct org.id,2,'573',1,org.code,1,'2019-04-09 00:00:00','2019-04-09 00:00:00',1 from sys_organization org, sys_role a, sys_privilege b where org.code=a.org_code and a.id = b.auth_id and b.resource_id = '551' and b.resource_type = 1 and b.auth_type = 3;
