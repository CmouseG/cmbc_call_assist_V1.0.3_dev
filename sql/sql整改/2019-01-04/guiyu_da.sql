use guiyu_da;
create table robot_call_his
(
   id                   int not null auto_increment,
   seq_id               varchar(50) comment '会话id',
   user_id              varchar(50) not null comment '用户编号',
   org_code             varchar(8) comment '机构编号',
   ai_no                varchar(50) comment '机器人编号',
   phone_no             varchar(11) not null comment '电话号码',
   assign_time          datetime comment '分配时间',
   template_id          varchar(50) comment '话术模板',
   call_status          int comment '通话状态:2-通话完成,1-通话中',
   sellbot_callback_json text comment 'sellbot回调报文',
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

create table robot_call_process_stat
(
   id                   int not null auto_increment,
   user_id              varchar(50) comment '用户编号',
   stat_date            varchar(10) comment '统计日期',
   template_id          varchar(50) comment '模板编号',
   ai_answer            varchar(1024) comment 'AI话术',
   current_domain       varchar(50) comment '当前域',
   domain_type          varchar(3) comment '域类型 1-主流程;2-一般问题;9-其他',
   total_stat           int comment '总数统计',
   refused_stat         varchar(100) comment '拒绝统计(0-不拒绝,1-用户拒绝;9-未应答)',
   hangup_stat          varchar(100) comment '挂断统计(0-未挂断   1：用户挂断   2：AI挂断)',
   match_stat           varchar(100) comment '匹配统计',
   org_code             varchar(8) comment '机构号',
   crt_time             datetime comment '创建时间',
   primary key (id)
);
alter table robot_call_process_stat comment '机器人通话流程分析';
create index robot_call_process_stat_idx1 on robot_call_process_stat
(
   user_id
);
create index robot_call_process_stat_idx2 on robot_call_process_stat
(
   template_id
);
create index robot_call_process_stat_idx3 on robot_call_process_stat
(
   stat_date
);
create index robot_call_process_stat_idx4 on robot_call_process_stat
(
   org_code
);