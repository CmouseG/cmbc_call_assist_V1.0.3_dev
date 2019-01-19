use guiyu_da;
create table robot_call_his_20190117
(
   id                   int not null,
   seq_id               varchar(50) comment '会话id',
   user_id              varchar(50) comment '用户编号',
   org_code             varchar(50) comment '机构号',
   ai_no                varchar(50) comment '机器人编号',
   assign_time          datetime comment '分配时间',
   template_id          varchar(50) comment '话术模板',
   call_status          varchar(3) comment '通话状态:S-通话完成,I-通话中',
   sellbot_callback_json text comment 'sellbot回调报文',
   crt_date             varchar(10) comment '创建日期',
   crt_time             datetime comment '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into robot_call_his_10190117(id,seq_id,user_id,org_code,ai_no,assign_time,template_id,call_status,sellbot_callback_json,crt_date,crt_time) select id,seq_id,user_id,org_code,ai_no,assign_time,template_id,call_status,sellbot_callback_json,crt_date,crt_time from robot_call_his;

drop index robot_callback_his_idx4 on robot_call_his;
drop index robot_callback_his_idx3 on robot_call_his;
drop index robot_callback_his_idx2 on robot_call_his;
drop index robot_callback_his_idx1 on robot_call_his;
drop table if exists robot_call_his;
create table robot_call_his
(
   id                   int not null auto_increment,
   seq_id               varchar(50) comment '会话id',
   user_id              varchar(50) comment '用户编号',
   org_code             varchar(8) comment '机构编号',
   ai_no                varchar(50) comment '机器人编号',
   phone_no             varchar(11) not null comment '电话号码',
   assign_time          datetime comment '分配时间',
   call_date            varchar(10) comment '通话日期',
   template_id          varchar(50) comment '话术模板',
   call_status          int comment '通话状态:2-通话完成,1-通话中',
   is_tts               int comment '是否需要TTS',
   dialogCount          int,
   industry             varchar(50) comment '行业',
   model_id             int comment '模型编号',
   intent_level         varchar(3) comment '意向标签',
   reason               varchar(100),
   call_wav             varchar(100) comment '通话录音',
   crt_date             varchar(10) comment '创建日期',
   crt_time             datetime comment '创建时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table robot_call_his comment '通话历史';
create index robot_callback_his_idx1 on robot_call_his
(
   seq_id
);
create index robot_callback_his_idx2 on robot_call_his
(
   assign_time
);
create index robot_callback_his_idx3 on robot_call_his
(
   user_id
);
create index robot_callback_his_idx4 on robot_call_his
(
   phone_no
);


create table cust_pre_info
(
   id                   int not null auto_increment,
   phone                varchar(11) comment '电话号码',
   name                 varchar(50) comment '姓名(未知/具体姓名/具体姓氏)',
   seq_id               varchar(50) comment '会话id',
   need                 varchar(30) comment '是否有需求(未知/有/没有)主要是卖东西的领域',
   is_self              varchar(30) comment '是否是本人(未知/是/不是)主要是银行通知类的领域',
   agree_connect        varchar(50) comment '是否同意邀约/是否同意再次联系/是否同意过来体验产品(未知/不同意/同意)',
   age                  varchar(30) comment '年纪(未知/普通/老人/学生/或者具体的岁数)',
   gender               varchar(10) comment '性别(未知/男/女)',
   addr                 varchar(100) comment '地址',
   home                 varchar(30) comment '住房状况(未知/有房/无房/租房/豪华别墅)',
   car                  varchar(30) comment '车辆状况(未知/有车/无车/自行车/劳斯莱斯/宝马)',
   marry                varchar(10) comment '婚姻状况(未知/已婚/未婚)',
   job                  varchar(30) comment '职业状况(未知/上班/做生意/无业/公务员)',
   salary               varchar(30) comment '工资状况(未知/打到银行/发现金/具体的数额)',
   warranty             varchar(30) comment '保单状况(未知/有/没有)',
   appointed_time       varchar(30) comment '另约时间(null/当前没空,约定时间)',
   crt_date             varchar(10) comment '创建日期',
   crt_time             datetime comment '创建时间',
   update_time          datetime,
   primary key (id)
);
alter table cust_pre_info comment '客户信息';
create index cust_pre_info_idx1 on cust_pre_info
(
   phone
);


create table robot_call_detail
(
   id                   int not null auto_increment,
   seq_id               varchar(50) comment '会话id',
   scene                varchar(50) comment '当前场景',
   human_wav            varchar(100) comment '客户音频',
   human_say            varchar(512) comment '客户所说内容',
   say_time             varchar(30) comment '客户通话时间',
   robot_say            varchar(512) comment '机器人通话内容',
   type                 varchar(10) comment '当前通话状态【共三类:begin,chart,end】',
   robot_wav            varchar(100) comment '机器人音频',
   ai_scene             varchar(512) comment 'ai回复信息列表',
   ai_intent            varchar(30) comment '飞龙识别的意图',
   domain_type          int comment '(兼容sellbot)流程类型： 1: 主流程   2：一般问题   9：其他',
   is_refused           int comment '应答类型,0: 不拒绝   1：用户拒绝   9：未应答',
   hangup_type          int comment '挂断类型 0: 未挂断   1：用户挂断   2：AI挂断',
   match_type           int comment '匹配类型：0:识别为空,1:未匹配,2：关键词匹配,3：静音超时,4：忽略,5：轮数超限,10：无匹配默认肯定,11：说''啊''默认肯定,12：无匹配且小于4个字默认肯定,99：未分类',
   wav_id               varchar(30) comment '机器人音频id',
   crt_date             varchar(10) comment '创建日期',
   crt_time             datetime comment '创建时间',
   update_time          datetime,
   primary key (id)
);
alter table robot_call_detail comment '通话详情';
create index cust_pre_info_idx1 on robot_call_detail
(
   seq_id
);