#DA库先加. 上一次遗漏的用户org_code
UPDATE guiyu_da.robot_call_his set org_code=concat(org_code,'.');
UPDATE guiyu_da.robot_call_process_stat set org_code=concat(org_code,'.');
#业务表数据org_code刷新跟企业code一致，最后一位加.
update guiyu_billing.billing_user_acct set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_callcenter.notice_send_label set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_notice.notice_setting set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_sms.sms_config set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_sms.sms_task set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_sms.sms_task_detail set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_sms.sms_tunnel set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.sip_line_apply set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.sip_line_apply set apply_org_code = concat(apply_org_code,'.') where org_code <> '1';
update guiyu_linemarket.sip_line_base_info set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.sip_line_base_info set belong_org_code = concat(belong_org_code,'.') where org_code <> '1';
update guiyu_linemarket.sip_line_exclusive set belong_org_code = concat(belong_org_code,'.') where belong_org_code <> '1';
update guiyu_linemarket.sip_line_share set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.voip_gw_info set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.voip_gw_port set org_code = concat(org_code,'.') where org_code <> '1';
update guiyu_linemarket.voip_gw_port_his set org_code = concat(org_code,'.') where org_code <> '1';

##张朋的话术市场bot_sentence_share_auth表中orgcode含逗号，需要找张朋单独手工处理
##张朋的话术市场bot_sentence_share_auth表中orgcode含逗号，需要找张朋单独手工处理
##张朋的话术市场bot_sentence_share_auth表中orgcode含逗号，需要找张朋单独手工处理

#批量修改角色为企业操作员或者客服的账号orgCode
drop procedure if exists batchModifyOrgCodeWithRoleIn4Or5;
create procedure batchModifyOrgCodeWithRoleIn4Or5()
BEGIN
declare isDone int default 0;
declare intNumber int default 1;
declare orgCode varchar(32);


declare orgCodes cursor for select a.org_code from guiyu_base.sys_user a,guiyu_base.sys_role_user b where a.id = b.user_id and b.role_id in(4,5);/*取出来所有需要循环的数据*/
declare continue handler for not FOUND set isDone = 1;
open orgCodes;


REPEAT
fetch orgCodes into orgCode;
if not isDone THEN
UPDATE guiyu_botstence.bot_available_template set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_botstence.bot_sentence_process set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.agent set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.call_out_plan set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.call_out_plan_0 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.call_out_plan_1 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.line_info set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.queue set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.report_call_day set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.report_call_hour set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.report_call_today set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.report_line_code set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.report_line_status set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_callcenter.tier set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.black_list set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.black_list_records set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_0 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_1 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_2 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_batch set org_code = left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))) where org_code = orgCode;
UPDATE guiyu_dispatch.file_records set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_robot.user_ai_cfg_base_info set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_billing.billing_acct_charging_record set concat(oper_user_org_code = left(left(oper_user_org_code,length(oper_user_org_code)-1),LENGTH(left(oper_user_org_code,length(oper_user_org_code)-1))-LOCATE('.',REVERSE(left(oper_user_org_code,length(oper_user_org_code)-1)))),'.') where oper_user_org_code = orgCode;
UPDATE guiyu_da.robot_call_his set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_da.robot_call_process_stat set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;



set intNumber=intNumber+1;
end if;
until isDone end repeat;
close orgCodes;
END;
call batchModifyOrgCodeWithRoleIn4Or5();
drop procedure batchModifyOrgCodeWithRoleIn4Or5;

