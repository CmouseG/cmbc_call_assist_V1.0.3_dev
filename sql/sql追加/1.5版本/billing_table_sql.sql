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
   id                   int(11) not null auto_increment comment '主键',
   charging_id          varchar(32) comment '计费ID',
   account_id           varchar(32) comment '账户ID',
   oper_user_id         varchar(32) comment '用户ID',
   oper_user_name       varchar(32) comment '用户名称',
   oper_user_org_code   varchar(32) comment '用户编码',
   oper_begin_time      datetime comment '通话开始时间',
   oper_end_time        datetime comment '通话结束时间',
   oper_duration        bigint(16) not null default 0 comment '通话时长秒',
   oper_duration_m      bigint(16) not null default 0 comment '通话时长，按分钟整算',
   oper_duration_str    varchar(16) default '00:00' comment '通话时长描述',
   oper_status          int(2) comment '状态',
   oper_details         varchar(255) comment '详细信息',
   type                 int(2) comment '类型  1：充值 2：消费',
   fee_mode             int(2) comment '费用类型  1-银行转账充值  2-在线充值  3-通话消费',
   user_charging_id     varchar(32) comment '用户计费项ID',
   amount               decimal(16,2) comment '金额',
   src_amount           decimal(16,2) comment '金额来源',
   to_amount            decimal(16,2) comment '金额去处',
   evidence             varchar(128) comment '凭证',
   planuuid             varchar(32),
   phone                varchar(32) comment '手机号码',
   attachment_snapshot_url varchar(512) comment '附件快照地址，多个图片用(英文)逗号分割',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_acct_charging_record comment '用户计费流水记录表';

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
   id                   int(11) not null auto_increment comment '主键',
   user_charging_id     varchar(32) comment '用户计费项ID',
   user_id              varchar(32) comment '用户ID',
   account_id           varchar(32) comment '账户ID',
   charging_item_id     varchar(32) comment '计费项ID',
   charging_item_name   varchar(128) comment '计费项名称',
   charging_type        int(2) comment '计费项类型 1-时长 2-路数 3-月度',
   target_key           varchar(32) comment '用户设置key名称',
   target_name          varchar(32) comment '用户设置项名称',
   price                decimal(16,2) not null default 0.00 comment '单价',
   unit_price           int(1) comment '价格单位  1-秒 2-分钟 3-小时 4-天 5-月 6-年',
   is_deducted          int(1) not null default 0 comment '扣费标识 0-扣费 1-不扣费',
   status               int(1) not null default 0 comment '状态  0-停用 1-启用',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_acct_charging_term comment '账户计费项表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   type                 int(1) comment '类型 1-充值 2-消费',
   call_duration        bigint(16) not null default 0 comment '通话时长',
   consume_amount       decimal(16,2) comment '统计计算金额，消费金额',
   total_date           varchar(16) comment '统计日期，例如：yyyy-MM-dd',
   total_month          varchar(16) comment '统计月份，格式yyyy-MM',
   call_time            varchar(32) comment '通话时间',
   stat_time            datetime,
   stat_status          int(1),
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_acct_charging_total comment '账户统计表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   oper_time            datetime comment '执行时间',
   oper_status          int(2) comment 'oper状态',
   oper_details         varchar(255) comment '详细信息',
   start_time           datetime comment '开始时间',
   end_time             datetime comment '结束时间',
   my_charging_id       varchar(32) comment '用户计费ID',
   service_amount       decimal(16,2) comment '业务金额',
   charging_center_amount decimal(16,2) comment '计费中心金额',
   status               int(1) comment '状态',
   fix_user_id          varchar(32) comment '用户ID',
   fix_details          varchar(255) comment '详情',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_acct_reconciliation comment '对账记录表';

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
   id                   int(11) not null auto_increment comment '主键',
   acct_set_id          varchar(32) comment '账户设置ID',
   account_id           varchar(32) comment '账户ID',
   set_key              varchar(32) comment '配置key',
   set_value            varchar(32) comment '配置值',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_acct_set comment '用户账户设置表';

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
   id                   int(11) not null auto_increment comment '主键',
   charging_item_id     varchar(32) comment '计费项ID',
   type                 int(2) comment '类型',
   name                 varchar(64) comment '计费项名称',
   charging_type        int(2) comment '计费项类型 1-时长 2-路数 3-月度',
   status               int(1) not null default 0 comment '状态  0-停用 1-启用',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_charging_term comment '计费项表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   type                 int(1) comment '类型 1-流水 2-统计 3-对账 4-通知',
   key_id               varchar(32) comment '对应ID',
   retry_times          datetime comment '重试时间',
   retry_status         int(1) comment '重试状态  0-成功 1-失败',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_exception_retry_record comment '异常重试记录表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   type                 int(1) comment '类型 1-欠费 2-已缴费 3-冻结',
   return_status        int(1) comment '通知状态  0-成功 1-失败',
   return_details       varchar(255) comment '通知详情',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_notify_busi_record comment '通知各业务单元记录表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   type                 int(1) comment '类型 1-欠费 2-已缴费 3-冻结',
   notify_type          int(1) comment '通知类型  0-mq',
   content              varchar(255) comment '通知内容',
   status               int(1) comment '状态 0-成功 1-失败',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_notify_msg_record comment '消息通知记录表';

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
   id                   int(11) not null auto_increment comment '主键',
   total_mode           int(1) comment '统计方式：1-日  2-月',
   recharge_amount      decimal(16,2) comment '统计计算金额，充值金额',
   consume_amount       decimal(16,2) comment '统计计算金额，消费金额',
   total_date           varchar(16) comment '统计日期，例如：yyyy-MM-dd',
   total_month          varchar(16) comment '统计月份，格式yyyy-MM',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_total_charging comment '费用统计表';

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
   id                   int(11) not null auto_increment comment '主键',
   account_id           varchar(32) comment '账户ID',
   company_id           varchar(32) comment '公司ID',
   company_name         varchar(255) comment '公司名称',
   org_code             varchar(64) comment '公司编码',
   amount               decimal(16,2) not null default 0.00 comment '金额',
   available_balance    decimal(16,2) comment '可用剩余金额',
   freezing_amount      decimal(16,2) comment '冻结金额',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_user_acct comment '计费用户账户表';

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

