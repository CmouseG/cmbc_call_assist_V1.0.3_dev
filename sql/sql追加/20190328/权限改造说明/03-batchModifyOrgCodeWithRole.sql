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
UPDATE guiyu_dispatch.dispatch_plan_1 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_10 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_100 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_101 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_102 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_103 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_104 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_105 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_106 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_107 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_108 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_109 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_11 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_110 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_111 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_112 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_113 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_114 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_115 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_116 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_117 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_118 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_119 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_12 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_120 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_121 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_122 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_123 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_124 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_125 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_126 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_127 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_128 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_129 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_13 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_130 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_131 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_132 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_133 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_134 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_135 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_136 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_137 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_138 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_139 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_14 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_140 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_141 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_142 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_143 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_144 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_145 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_146 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_147 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_148 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_149 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_15 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_150 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_151 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_152 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_153 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_154 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_16 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_17 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_18 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_19 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_20 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_21 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_22 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_23 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_24 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_25 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_26 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_27 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_28 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_29 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_30 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_31 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_32 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_33 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_34 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_35 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_36 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_37 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_38 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_39 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_40 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_41 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_42 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_43 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_44 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_45 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_46 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_47 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_48 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_49 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_50 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_51 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_52 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_53 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_54 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_55 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_56 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_57 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_58 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_59 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_60 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_61 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_62 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_63 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_64 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_65 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_66 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_67 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_68 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_69 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_7 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_70 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_71 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_72 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_73 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_74 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_75 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_76 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_77 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_78 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_79 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_80 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_81 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_82 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_83 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_84 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_85 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_86 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_87 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_88 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_89 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_90 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_91 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_92 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_93 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_94 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_95 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_96 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_97 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_98 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_99 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_8 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_9 set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_dispatch.dispatch_plan_batch set org_code = left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))) where org_code = orgCode;
UPDATE guiyu_dispatch.file_records set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_robot.user_ai_cfg_base_info set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_billing.billing_acct_charging_record set oper_user_org_code = concat(left(left(oper_user_org_code,length(oper_user_org_code)-1),LENGTH(left(oper_user_org_code,length(oper_user_org_code)-1))-LOCATE('.',REVERSE(left(oper_user_org_code,length(oper_user_org_code)-1)))),'.') where oper_user_org_code = orgCode;
UPDATE guiyu_da.robot_call_his set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;
UPDATE guiyu_da.robot_call_process_stat set org_code = concat(left(left(org_code,length(org_code)-1),LENGTH(left(org_code,length(org_code)-1))-LOCATE('.',REVERSE(left(org_code,length(org_code)-1)))),'.') where org_code = orgCode;



set intNumber=intNumber+1;
end if;
until isDone end repeat;
close orgCodes;
END;
call batchModifyOrgCodeWithRoleIn4Or5();
drop procedure batchModifyOrgCodeWithRoleIn4Or5;


