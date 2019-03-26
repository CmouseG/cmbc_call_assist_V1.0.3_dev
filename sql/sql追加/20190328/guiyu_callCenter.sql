use guiyu_callcenter;

alter table agent add customer_id bigint(20);

update guiyu_callcenter.agent,
       guiyu_base.sys_user
set guiyu_callcenter.agent.customer_id = guiyu_base.sys_user.id
where guiyu_callcenter.agent.crm_login_id = guiyu_base.sys_user.username;





/*   注意需要跨库查询 */

/*   注意此sql需要在guiyu_auth库刷数据之前执行 */
ALTER TABLE `report_call_day` ADD COLUMN user_id INT(8);
ALTER TABLE `report_call_hour` ADD COLUMN user_id INT(8);
ALTER TABLE `report_call_today` ADD COLUMN user_id INT(8);
ALTER TABLE `report_line_code` ADD COLUMN user_id INT(8);
ALTER TABLE `report_line_status` ADD COLUMN user_id INT(8);



UPDATE report_call_day a INNER JOIN guiyu_base.sys_user b ON a.org_code = b.org_code SET a.user_id = b.id;

UPDATE report_call_hour a INNER JOIN guiyu_base.sys_user b ON a.org_code = b.org_code SET a.user_id = b.id;

UPDATE report_call_today a INNER JOIN guiyu_base.sys_user b ON a.org_code = b.org_code SET a.user_id = b.id;

UPDATE report_line_code a INNER JOIN guiyu_base.sys_user b ON a.org_code = b.org_code SET a.user_id = b.id;

UPDATE report_line_status a INNER JOIN guiyu_base.sys_user b ON a.org_code = b.org_code SET a.user_id = b.id;
