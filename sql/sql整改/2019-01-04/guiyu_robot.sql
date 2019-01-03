use guiyu_robot;
CREATE TEMPORARY TABLE user_ai_cfg_base_info_temp SELECT * FROM user_ai_cfg_base_info;
drop index user_ai_cfg_base_info_idx2 on user_ai_cfg_base_info;
drop index user_ai_cfg_base_info_idx1 on user_ai_cfg_base_info;
drop table if exists user_ai_cfg_base_info;
create table user_ai_cfg_base_info
(
   id                   int not null auto_increment,
   user_id              varchar(50) not null comment '用户编号',
   org_code             varchar(8) not null comment '机构编号',
   ai_total_num         int comment '机器人数量',
   template_ids         varchar(200) comment '话术模板',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_base_info comment '用户机器人账户基本信息';
create index user_ai_cfg_base_info_idx1 on user_ai_cfg_base_info
(
   user_id
);
create index user_ai_cfg_base_info_idx2 on user_ai_cfg_base_info
(
   org_code
);
insert into user_ai_cfg_base_info(user_id,org_code,ai_total_num,template_ids,crt_time,crt_user,update_time,update_user)select user_id,org_code,ai_total_num,template_ids,crt_time,crt_user,update_time,update_user from user_ai_cfg_base_info_temp;

CREATE TEMPORARY TABLE user_ai_cfg_info_temp SELECT * FROM user_ai_cfg_info;
drop index user_ai_cfg_info_idx2 on user_ai_cfg_info;
drop index user_ai_cfg_info_idx1 on user_ai_cfg_info;
drop table if exists user_ai_cfg_info;
create table user_ai_cfg_info
(
   id                   int not null auto_increment,
   user_id              varchar(50) not null comment '用户编号',
   ai_num               int comment '机器人数量',
   assign_level         int comment '预分配，高，低',
   template_ids         varchar(200) comment '话术模板',
   open_date            varchar(10) comment '开户日期',
   invalid_date         varchar(10) comment '失效日期',
   status               int not null comment '状态：1-正常，0-失效',
   invalid_policy       varchar(10) comment '失效策略:NUM:100',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_info comment '用户机器配置信息';
create index user_ai_cfg_info_idx1 on user_ai_cfg_info
(
   user_id
);
create index user_ai_cfg_info_idx2 on user_ai_cfg_info
(
   status
);
update user_ai_cfg_info_temp set status = (case `status` when 'S' then 1 when 'V' then 0 end);
insert into user_ai_cfg_info(user_id,ai_num,assign_level,template_ids,open_date,invalid_date,status,invalid_policy,crt_time,crt_user,update_time,update_user) select user_id,ai_num,assign_level,template_ids,open_date,invalid_date,status,invalid_policy,crt_time,crt_user,update_time,update_user from user_ai_cfg_info_temp;

drop index user_ai_cfg_his_info_idx3 on user_ai_cfg_his_info;
drop index user_ai_cfg_his_info_idx2 on user_ai_cfg_his_info;
drop index user_ai_cfg_his_info_idx1 on user_ai_cfg_his_info;
drop table if exists user_ai_cfg_his_info;
create table user_ai_cfg_his_info
(
   id                   int not null auto_increment,
   busi_id              int not null comment '业务编号',
   user_id              varchar(50) not null comment '用户编号',
   ai_num               int comment '机器人数量',
   assign_level         int comment '预分配，高，低',
   template_id          varchar(200) comment '话术模板',
   open_date            varchar(10) comment '开户日期',
   invalid_date         varchar(10) comment '失效日期',
   status               int comment '状态：1-正常，0-失效',
   invalid_policy       varchar(10) comment '失效策略:NUM:100',
   handle_type          int comment '操作类型:1-新增;2-更新;0-删除',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_his_info comment '用户机器配置变更历史信息';
create index user_ai_cfg_his_info_idx1 on user_ai_cfg_his_info
(
   busi_id
);
create index user_ai_cfg_his_info_idx2 on user_ai_cfg_his_info
(
   user_id
);
create index user_ai_cfg_his_info_idx3 on user_ai_cfg_his_info
(
   crt_time
);


CREATE TEMPORARY TABLE tts_callback_his_temp SELECT * FROM tts_callback_his;
drop index tts_callback_his_idx2 on tts_callback_his;
drop index tts_callback_his_idx1 on tts_callback_his;
drop table if exists tts_callback_his;
create table tts_callback_his
(
   id                   int not null auto_increment,
   busi_id              varchar(32) not null comment '业务id,调用tts的唯一请求id',
   template_id          varchar(32) comment '话术模板编号',
   tts_json_data        text comment 'TTS合成的语音JSON',
   status               int not null comment '状态: 1-完成, 0-失败',
   error_msg            varchar(1024) comment '失败日志',
   crt_time             datetime comment '创建时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tts_callback_his comment 'TTS合成回调数据';
create index tts_callback_his_idx1 on tts_callback_his
(
   busi_id
);
create index tts_callback_his_idx2 on tts_callback_his
(
   status
);
update tts_callback_his_temp set status = (case `status` when 'S' then 1 when 'F' then 0 end);
insert into tts_callback_his(busi_id,template_id,tts_json_data,status,error_msg,crt_time) select busi_id,template_id,tts_json_data,status,error_msg,crt_time from tts_callback_his_temp;

CREATE TEMPORARY TABLE tts_wav_his_temp SELECT * FROM tts_wav_his;
drop index tts_wav_his_idx4 on tts_wav_his;
drop index tts_wav_his_idx3 on tts_wav_his;
drop index tts_wav_his_idx2 on tts_wav_his;
drop index tts_wav_his_idx1 on tts_wav_his;
drop table if exists tts_wav_his;
create table tts_wav_his
(
   id                   int not null auto_increment,
   seq_id               varchar(32) not null comment '会话Id',
   busi_id              varchar(32) not null comment '业务id,调用tts的唯一请求id',
   template_id          varchar(32) comment '话术模板编号',
   req_params           varchar(1024) comment 'TTS合成请求参数',
   tts_txt_json_data    varchar(1024) comment '需要合成的文本JSON',
   tts_json_data        text not null comment 'TTS合成的语音JSON',
   status               int comment '状态: 2-合成中,1-完成, 0-失败',
   error_msg            varchar(1024) comment '失败日志',
   error_type           int comment '失败类型:1-调用失败，2-TTS接口回调失败，3-TTS回调后本地处理失败',
   error_try_num        int comment '失败尝试次数',
   crt_time             datetime comment '创建时间',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tts_wav_his comment 'TTS语音合成数据';
create index tts_wav_his_idx1 on tts_wav_his
(
   seq_id
);
create index tts_wav_his_idx2 on tts_wav_his
(
   busi_id
);
create index tts_wav_his_idx3 on tts_wav_his
(
   status
);
create index tts_wav_his_idx4 on tts_wav_his
(
   crt_time
);
update tts_wav_his_temp set status = (case `status` when 'P' then 2 when 'S' then 1 when 'F' then 0 end);
update tts_wav_his_temp set error_type = (case `error_type` when 'P' then 1 when 'T' then 2 when 'L' then 3 end);
insert into tts_wav_his(seq_id,busi_id,template_id,req_params,tts_txt_json_data,tts_json_data,status,error_msg,error_type,error_try_num,crt_time) select seq_id,busi_id,template_id,req_params,tts_txt_json_data,tts_json_data,status,error_msg,error_type,error_try_num,crt_time from tts_callback_his_temp;


drop index ai_cycle_his_idx4 on ai_cycle_his;
drop index ai_cycle_his_idx3 on ai_cycle_his;
drop index ai_cycle_his_idx2 on ai_cycle_his;
drop index ai_cycle_his_idx1 on ai_cycle_his;
drop table if exists ai_cycle_his;
create table ai_cycle_his
(
   id                   int not null auto_increment,
   user_id              varchar(50) not null comment '用户编号',
   ai_no                varchar(50) not null comment '机器人编号',
   ai_name              varchar(50) comment '机器人昵称',
   template_id          varchar(50) comment '话术模板',
   assign_date          varchar(10) comment '分配日期',
   assign_time          varchar(20) comment '分配时间',
   taskback_date        varchar(10) comment '收回日期',
   taskback_time        varchar(20) comment '收回时间',
   call_num             bigint comment '拨打数量',
   crt_time             datetime,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table ai_cycle_his comment '机器人生命周期记录';
create index ai_cycle_his_idx1 on ai_cycle_his
(
   user_id
);
create index ai_cycle_his_idx2 on ai_cycle_his
(
   ai_no
);
create index ai_cycle_his_idx3 on ai_cycle_his
(
   taskback_date
);
create index ai_cycle_his_idx4 on ai_cycle_his
(
   taskback_time
);

drop table robot_call_process_stat;
drop table robot_call_his;
drop table bd_table_sequence;
drop function genTabId;