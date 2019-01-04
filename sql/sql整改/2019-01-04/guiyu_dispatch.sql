use guiyu_dispatch;

#修改表编码格式
alter table dispatch_plan convert to character set utf8;
alter table dispatch_plan_0 convert to character set utf8;
alter table dispatch_plan_1 convert to character set utf8;
alter table dispatch_plan_2 convert to character set utf8;
alter table dispatch_plan_batch convert to character set utf8;
alter table sms_platform convert to character set utf8;
alter table user_sms_config convert to character set utf8;
alter table dispatch_log convert to character set utf8;
alter table sms_tunnel convert to character set utf8;


alter table dispatch_hour modify column is_call int comment '是否拨打0未拨打1拨打';
alter table dispatch_hour modify column hour int comment '拨打时间';

alter table dispatch_log modify column module varchar(18) comment'模块名称';
alter table dispatch_log modify column action varchar(18) comment '模块动作';


alter table dispatch_plan_0 modify column params varchar(32) comment '参数';
alter table dispatch_plan_0 modify column attach varchar(32) comment '附加参数';
alter table dispatch_plan_0 modify column call_hour varchar(64) not null comment '拨打时间';
alter table dispatch_plan_0 modify column org_code varchar(8)not null comment '组织编码';
alter table dispatch_plan_0 modify column line int not null comment '线路id';
alter table dispatch_plan_0 modify column is_tts int not null comment '是否tts合成';
alter table dispatch_plan_0 modify column is_del int not null comment '0否1是';

alter table dispatch_plan_1 modify column params varchar(32) comment '参数';
alter table dispatch_plan_1 modify column attach varchar(32) comment '附加参数';
alter table dispatch_plan_1 modify column call_hour varchar(64) not null comment '拨打时间';
alter table dispatch_plan_1 modify column org_code varchar(8) not null comment '组织编码';
alter table dispatch_plan_1 modify column line int not null comment '线路id';
alter table dispatch_plan_1 modify column is_tts int not null comment '是否tts合成';
alter table dispatch_plan_1 modify column is_del int not null comment '是否删除0否1是';



alter table dispatch_plan_2 modify column params varchar(32) comment '参数';
alter table dispatch_plan_2 modify column attach varchar(32) comment '附加参数';
alter table dispatch_plan_2 modify column call_hour varchar(64) not null comment '拨打时间';
alter table dispatch_plan_2 modify column org_code varchar(8)not null comment '组织编码';
alter table dispatch_plan_2 modify column line int not null comment '线路id';
alter table dispatch_plan_2 modify column is_tts int not null comment '是否tts合成';
alter table dispatch_plan_2 modify column is_del int not null comment '是否删除0否1是';



alter table dispatch_plan_batch modify column name varchar(32) comment '批次名';
alter table dispatch_plan_batch modify column user_id varchar(4) comment '批次id';
alter table dispatch_plan_batch modify column org_code varchar(8) comment'组织code';


alter table modular_logs modify column msg varchar(128) comment '日志信息';
alter table modular_logs modify column status int comment '处理状态0成功1失败';
	

alter table send_msg_records modify column statusCode int not null comment '默认0';

alter table send_msg_records modify column statusMsg varchar(32) not null comment '状态信息';

alter table send_msg_records modify column requestId varchar(32) not null comment '请求id';

alter table send_msg_records modify column phone varchar(32) not null comment '手机号';

alter table sms_platform modify column name varchar(32) not null comment '平台id';