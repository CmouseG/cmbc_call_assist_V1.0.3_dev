use guiyu_robot;
CREATE TEMPORARY TABLE user_ai_cfg_base_info_temp SELECT * FROM user_ai_cfg_base_info;
drop index user_ai_cfg_base_info_idx2 on user_ai_cfg_base_info;
drop index user_ai_cfg_base_info_idx1 on user_ai_cfg_base_info;
drop table if exists user_ai_cfg_base_info;
create table user_ai_cfg_base_info
(
   id                   int not null auto_increment,
   user_id              varchar(50) not null comment '�û����',
   org_code             varchar(8) not null comment '�������',
   ai_total_num         int comment '����������',
   template_ids         varchar(200) comment '����ģ��',
   crt_time             datetime comment '����ʱ��',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_base_info comment '�û��������˻�������Ϣ';
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
   user_id              varchar(50) not null comment '�û����',
   ai_num               int comment '����������',
   assign_level         int comment 'Ԥ���䣬�ߣ���',
   template_ids         varchar(200) comment '����ģ��',
   open_date            varchar(10) comment '��������',
   invalid_date         varchar(10) comment 'ʧЧ����',
   status               int not null comment '״̬��1-������0-ʧЧ',
   invalid_policy       varchar(10) comment 'ʧЧ����:NUM:100',
   crt_time             datetime comment '����ʱ��',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_info comment '�û�����������Ϣ';
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
   busi_id              int not null comment 'ҵ����',
   user_id              varchar(50) not null comment '�û����',
   ai_num               int comment '����������',
   assign_level         int comment 'Ԥ���䣬�ߣ���',
   template_id          varchar(200) comment '����ģ��',
   open_date            varchar(10) comment '��������',
   invalid_date         varchar(10) comment 'ʧЧ����',
   status               int comment '״̬��1-������0-ʧЧ',
   invalid_policy       varchar(10) comment 'ʧЧ����:NUM:100',
   handle_type          int comment '��������:1-����;2-����;0-ɾ��',
   crt_time             datetime comment '����ʱ��',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table user_ai_cfg_his_info comment '�û��������ñ����ʷ��Ϣ';
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
   busi_id              varchar(32) not null comment 'ҵ��id,����tts��Ψһ����id',
   template_id          varchar(32) comment '����ģ����',
   tts_json_data        text comment 'TTS�ϳɵ�����JSON',
   status               int not null comment '״̬: 1-���, 0-ʧ��',
   error_msg            varchar(1024) comment 'ʧ����־',
   crt_time             datetime comment '����ʱ��',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tts_callback_his comment 'TTS�ϳɻص�����';
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
   seq_id               varchar(32) not null comment '�ỰId',
   busi_id              varchar(32) not null comment 'ҵ��id,����tts��Ψһ����id',
   template_id          varchar(32) comment '����ģ����',
   req_params           varchar(1024) comment 'TTS�ϳ��������',
   tts_txt_json_data    varchar(1024) comment '��Ҫ�ϳɵ��ı�JSON',
   tts_json_data        text not null comment 'TTS�ϳɵ�����JSON',
   status               int comment '״̬: 2-�ϳ���,1-���, 0-ʧ��',
   error_msg            varchar(1024) comment 'ʧ����־',
   error_type           int comment 'ʧ������:1-����ʧ�ܣ�2-TTS�ӿڻص�ʧ�ܣ�3-TTS�ص��󱾵ش���ʧ��',
   error_try_num        int comment 'ʧ�ܳ��Դ���',
   crt_time             datetime comment '����ʱ��',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table tts_wav_his comment 'TTS�����ϳ�����';
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
   user_id              varchar(50) not null comment '�û����',
   ai_no                varchar(50) not null comment '�����˱��',
   ai_name              varchar(50) comment '�������ǳ�',
   template_id          varchar(50) comment '����ģ��',
   assign_date          varchar(10) comment '��������',
   assign_time          varchar(20) comment '����ʱ��',
   taskback_date        varchar(10) comment '�ջ�����',
   taskback_time        varchar(20) comment '�ջ�ʱ��',
   call_num             bigint comment '��������',
   crt_time             datetime,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table ai_cycle_his comment '�������������ڼ�¼';
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