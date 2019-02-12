/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/2/12 9:51:16                            */
/*==============================================================*/


drop index idx_acct_charging_record on billing_acct_charging_record;

drop table if exists billing_acct_charging_record;

drop index idx_acct_charging_term on billing_acct_charging_term;

drop index idx_charging_term_id on billing_acct_charging_term;

drop table if exists billing_acct_charging_term;

drop index idx_acct_charging_total on billing_acct_charging_total;

drop table if exists billing_acct_charging_total;

drop index idx_acct_reconciliation on billing_acct_reconciliation;

drop table if exists billing_acct_reconciliation;

drop index idx_acct_set on billing_acct_set;

drop table if exists billing_acct_set;

drop index idx_charging_term on billing_charging_term;

drop table if exists billing_charging_term;

drop index idx_exception_retry_record on billing_exception_retry_record;

drop table if exists billing_exception_retry_record;

drop index idx_notify_busi_record on billing_notify_busi_record;

drop table if exists billing_notify_busi_record;

drop index idx_notify_msg_record on billing_notify_msg_record;

drop table if exists billing_notify_msg_record;

drop index idx_total_charging_month on billing_total_charging;

drop index idx_total_charging_date on billing_total_charging;

drop table if exists billing_total_charging;

drop index idx_user_acct_code on billing_user_acct;

drop index idx_billing_user_acct on billing_user_acct;

drop table if exists billing_user_acct;

/*==============================================================*/
/* Table: billing_acct_charging_record                          */
/*==============================================================*/
create table billing_acct_charging_record
(
   id                   int(11) not null auto_increment comment '����',
   charging_id          varchar(32) comment '�Ʒ�ID',
   account_id           varchar(32) comment '�˻�ID',
   oper_user_id         varchar(32) comment '�û�ID',
   oper_user_name       varchar(32) comment '�û�����',
   oper_user_org_code   varchar(32) comment '�û�����',
   oper_begin_time      datetime comment 'ͨ����ʼʱ��',
   oper_end_time        datetime comment 'ͨ������ʱ��',
   oper_duration        bigint(16) not null default 0 comment 'ͨ��ʱ����',
   oper_duration_m      bigint(16) not null default 0 comment 'ͨ��ʱ��������������',
   oper_duration_str    varchar(16) default '00:00' comment 'ͨ��ʱ������',
   oper_status          int(2) comment '״̬',
   oper_details         varchar(255) comment '��ϸ��Ϣ',
   type                 int(2) comment '����  1����ֵ 2������',
   fee_mode             int(2) comment '��������  1-����ת�˳�ֵ  2-���߳�ֵ  3-ͨ������',
   user_charging_id     varchar(32) comment '�û��Ʒ���ID',
   amount               decimal(16,2) comment '���',
   src_amount           decimal(16,2) comment '�����Դ',
   to_amount            decimal(16,2) comment '���ȥ��',
   evidence             varchar(128) comment 'ƾ֤',
   planuuid             varchar(32),
   phone                varchar(32) comment '�ֻ�����',
   attachment_snapshot_url varchar(512) comment '�������յ�ַ�����ͼƬ��(Ӣ��)���ŷָ�',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_acct_charging_record comment '�û��Ʒ���ˮ��¼��';

/*==============================================================*/
/* Index: idx_acct_charging_record                              */
/*==============================================================*/
create index idx_acct_charging_record on billing_acct_charging_record
(
   account_id
);

/*==============================================================*/
/* Table: billing_acct_charging_term                            */
/*==============================================================*/
create table billing_acct_charging_term
(
   id                   int(11) not null auto_increment comment '����',
   user_charging_id     varchar(32) comment '�û��Ʒ���ID',
   user_id              varchar(32) comment '�û�ID',
   account_id           varchar(32) comment '�˻�ID',
   charging_item_id     varchar(32) comment '�Ʒ���ID',
   charging_item_name   varchar(128) comment '�Ʒ�������',
   charging_type        int(2) comment '�Ʒ������� 1-ʱ�� 2-·�� 3-�¶�',
   target_key           varchar(32) comment '�û�����key����',
   target_name          varchar(32) comment '�û�����������',
   price                decimal(16,2) not null default 0.00 comment '����',
   unit_price           int(1) comment '�۸�λ  1-�� 2-���� 3-Сʱ 4-�� 5-�� 6-��',
   is_deducted          int(1) not null default 0 comment '�۷ѱ�ʶ 0-�۷� 1-���۷�',
   status               int(1) not null default 0 comment '״̬  0-ͣ�� 1-����',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_acct_charging_term comment '�˻��Ʒ����';

/*==============================================================*/
/* Index: idx_charging_term_id                                  */
/*==============================================================*/
create index idx_charging_term_id on billing_acct_charging_term
(
   charging_item_id
);

/*==============================================================*/
/* Index: idx_acct_charging_term                                */
/*==============================================================*/
create index idx_acct_charging_term on billing_acct_charging_term
(
   account_id
);

/*==============================================================*/
/* Table: billing_acct_charging_total                           */
/*==============================================================*/
create table billing_acct_charging_total
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   type                 int(1) comment '���� 1-��ֵ 2-����',
   call_duration        bigint(16) not null default 0 comment 'ͨ��ʱ��',
   consume_amount       decimal(16,2) comment 'ͳ�Ƽ�������ѽ��',
   total_date           varchar(16) comment 'ͳ�����ڣ����磺yyyy-MM-dd',
   total_month          varchar(16) comment 'ͳ���·ݣ���ʽyyyy-MM',
   call_time            varchar(32) comment 'ͨ��ʱ��',
   stat_time            datetime,
   stat_status          int(1),
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_acct_charging_total comment '�˻�ͳ�Ʊ�';

/*==============================================================*/
/* Index: idx_acct_charging_total                               */
/*==============================================================*/
create index idx_acct_charging_total on billing_acct_charging_total
(
   account_id
);

/*==============================================================*/
/* Table: billing_acct_reconciliation                           */
/*==============================================================*/
create table billing_acct_reconciliation
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   oper_time            datetime comment 'ִ��ʱ��',
   oper_status          int(2) comment 'oper״̬',
   oper_details         varchar(255) comment '��ϸ��Ϣ',
   start_time           datetime comment '��ʼʱ��',
   end_time             datetime comment '����ʱ��',
   my_charging_id       varchar(32) comment '�û��Ʒ�ID',
   service_amount       decimal(16,2) comment 'ҵ����',
   charging_center_amount decimal(16,2) comment '�Ʒ����Ľ��',
   status               int(1) comment '״̬',
   fix_user_id          varchar(32) comment '�û�ID',
   fix_details          varchar(255) comment '����',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_acct_reconciliation comment '���˼�¼��';

/*==============================================================*/
/* Index: idx_acct_reconciliation                               */
/*==============================================================*/
create index idx_acct_reconciliation on billing_acct_reconciliation
(
   account_id
);

/*==============================================================*/
/* Table: billing_acct_set                                      */
/*==============================================================*/
create table billing_acct_set
(
   id                   int(11) not null auto_increment comment '����',
   acct_set_id          varchar(32) comment '�˻�����ID',
   account_id           varchar(32) comment '�˻�ID',
   set_key              varchar(32) comment '����key',
   set_value            varchar(32) comment '����ֵ',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_acct_set comment '�û��˻����ñ�';

/*==============================================================*/
/* Index: idx_acct_set                                          */
/*==============================================================*/
create index idx_acct_set on billing_acct_set
(
   account_id
);

/*==============================================================*/
/* Table: billing_charging_term                                 */
/*==============================================================*/
create table billing_charging_term
(
   id                   int(11) not null auto_increment comment '����',
   charging_item_id     varchar(32) comment '�Ʒ���ID',
   type                 int(2) comment '����',
   name                 varchar(64) comment '�Ʒ�������',
   charging_type        int(2) comment '�Ʒ������� 1-ʱ�� 2-·�� 3-�¶�',
   status               int(1) not null default 0 comment '״̬  0-ͣ�� 1-����',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_charging_term comment '�Ʒ����';

/*==============================================================*/
/* Index: idx_charging_term                                     */
/*==============================================================*/
create index idx_charging_term on billing_charging_term
(
   charging_type
);

/*==============================================================*/
/* Table: billing_exception_retry_record                        */
/*==============================================================*/
create table billing_exception_retry_record
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   type                 int(1) comment '���� 1-��ˮ 2-ͳ�� 3-���� 4-֪ͨ',
   key_id               varchar(32) comment '��ӦID',
   retry_times          datetime comment '����ʱ��',
   retry_status         int(1) comment '����״̬  0-�ɹ� 1-ʧ��',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_exception_retry_record comment '�쳣���Լ�¼��';

/*==============================================================*/
/* Index: idx_exception_retry_record                            */
/*==============================================================*/
create index idx_exception_retry_record on billing_exception_retry_record
(
   account_id
);

/*==============================================================*/
/* Table: billing_notify_busi_record                            */
/*==============================================================*/
create table billing_notify_busi_record
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   type                 int(1) comment '���� 1-Ƿ�� 2-�ѽɷ� 3-����',
   return_status        int(1) comment '֪ͨ״̬  0-�ɹ� 1-ʧ��',
   return_details       varchar(255) comment '֪ͨ����',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_notify_busi_record comment '֪ͨ��ҵ��Ԫ��¼��';

/*==============================================================*/
/* Index: idx_notify_busi_record                                */
/*==============================================================*/
create index idx_notify_busi_record on billing_notify_busi_record
(
   account_id
);

/*==============================================================*/
/* Table: billing_notify_msg_record                             */
/*==============================================================*/
create table billing_notify_msg_record
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   type                 int(1) comment '���� 1-Ƿ�� 2-�ѽɷ� 3-����',
   notify_type          int(1) comment '֪ͨ����  0-mq',
   content              varchar(255) comment '֪ͨ����',
   status               int(1) comment '״̬ 0-�ɹ� 1-ʧ��',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_notify_msg_record comment '��Ϣ֪ͨ��¼��';

/*==============================================================*/
/* Index: idx_notify_msg_record                                 */
/*==============================================================*/
create index idx_notify_msg_record on billing_notify_msg_record
(
   account_id
);

/*==============================================================*/
/* Table: billing_total_charging                                */
/*==============================================================*/
create table billing_total_charging
(
   id                   int(11) not null auto_increment comment '����',
   total_mode           int(1) comment 'ͳ�Ʒ�ʽ��1-��  2-��',
   recharge_amount      decimal(16,2) comment 'ͳ�Ƽ������ֵ���',
   consume_amount       decimal(16,2) comment 'ͳ�Ƽ�������ѽ��',
   total_date           varchar(16) comment 'ͳ�����ڣ����磺yyyy-MM-dd',
   total_month          varchar(16) comment 'ͳ���·ݣ���ʽyyyy-MM',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_total_charging comment '����ͳ�Ʊ�';

/*==============================================================*/
/* Index: idx_total_charging_date                               */
/*==============================================================*/
create index idx_total_charging_date on billing_total_charging
(
   total_date
);

/*==============================================================*/
/* Index: idx_total_charging_month                              */
/*==============================================================*/
create index idx_total_charging_month on billing_total_charging
(
   total_month
);

/*==============================================================*/
/* Table: billing_user_acct                                     */
/*==============================================================*/
create table billing_user_acct
(
   id                   int(11) not null auto_increment comment '����',
   account_id           varchar(32) comment '�˻�ID',
   company_id           varchar(32) comment '��˾ID',
   company_name         varchar(255) comment '��˾����',
   org_code             varchar(64) comment '��˾����',
   amount               decimal(16,2) not null default 0.00 comment '���',
   available_balance    decimal(16,2) comment '����ʣ����',
   freezing_amount      decimal(16,2) comment '������',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '����ʱ��',
   update_time          datetime comment '����ʱ��',
   del_flag             int(1) not null default 0 comment 'ɾ����־ 0-���� 1-ɾ��',
   primary key (id)
);

alter table billing_user_acct comment '�Ʒ��û��˻���';

/*==============================================================*/
/* Index: idx_billing_user_acct                                 */
/*==============================================================*/
create index idx_billing_user_acct on billing_user_acct
(
   account_id
);

/*==============================================================*/
/* Index: idx_user_acct_code                                    */
/*==============================================================*/
create index idx_user_acct_code on billing_user_acct
(
   org_code
);

