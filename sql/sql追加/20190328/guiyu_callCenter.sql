use guiyu_callcenter;

alter table agent add customer_id bigint(20);

update guiyu_callcenter.agent,
       guiyu_base.sys_user
set guiyu_callcenter.agent.customer_id = guiyu_base.sys_user.id
where guiyu_callcenter.agent.crm_login_id = guiyu_base.sys_user.username;