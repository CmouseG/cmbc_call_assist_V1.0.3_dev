use guiyu_da;
create table robot_call_his
(
   id                   int not null auto_increment,
   seq_id               varchar(50) comment '�Ựid',
   user_id              varchar(50) not null comment '�û����',
   org_code             varchar(8) comment '�������',
   ai_no                varchar(50) comment '�����˱��',
   phone_no             varchar(11) not null comment '�绰����',
   assign_time          datetime comment '����ʱ��',
   template_id          varchar(50) comment '����ģ��',
   call_status          int comment 'ͨ��״̬:2-ͨ�����,1-ͨ����',
   sellbot_callback_json text comment 'sellbot�ص�����',
   crt_date             varchar(10) comment '��������',
   crt_time             datetime comment '����ʱ��',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table robot_call_his comment 'ͨ����ʷ';
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
   user_id              varchar(50) comment '�û����',
   stat_date            varchar(10) comment 'ͳ������',
   template_id          varchar(50) comment 'ģ����',
   ai_answer            varchar(1024) comment 'AI����',
   current_domain       varchar(50) comment '��ǰ��',
   domain_type          varchar(3) comment '������ 1-������;2-һ������;9-����',
   total_stat           int comment '����ͳ��',
   refused_stat         varchar(100) comment '�ܾ�ͳ��(0-���ܾ�,1-�û��ܾ�;9-δӦ��)',
   hangup_stat          varchar(100) comment '�Ҷ�ͳ��(0-δ�Ҷ�   1���û��Ҷ�   2��AI�Ҷ�)',
   match_stat           varchar(100) comment 'ƥ��ͳ��',
   org_code             varchar(8) comment '������',
   crt_time             datetime comment '����ʱ��',
   primary key (id)
);
alter table robot_call_process_stat comment '������ͨ�����̷���';
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