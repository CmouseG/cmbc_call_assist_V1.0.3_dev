drop table if exists billing_bill_notify_log;

/*==============================================================*/
/* Table: billing_bill_notify_log                               */
/*==============================================================*/
create table billing_bill_notify_log
(
   id                   int(11) not null auto_increment comment '主键',
   user_id              varchar(32) comment '用户ID',
   bill_sec             int(11) comment '通话时长：秒',
   line_id              varchar(32) comment '线路ID',
   phone                varchar(32) comment '手机号码',
   begin_time           datetime comment '开始时间',
   end_time             datetime comment '结束时间',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '新增时间',
   update_time          datetime comment '更新时间',
   del_flag             int(1) not null default 0 comment '删除标志 0-正常 1-删除',
   primary key (id)
);

alter table billing_bill_notify_log comment '话单同步日志';
