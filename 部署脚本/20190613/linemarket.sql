create table guiyu_linemarket.voip_gw_port_limit
(
  id          int auto_increment comment '自增主键'
    primary key,
  port_id     int      null comment 'voip_gw_port表的id',
  line_id     int      not null comment '线路id',
  time_length int      null comment '时间长度',
  max_limit   int      null comment '限值',
  limit_type  int      null comment '1-拨打次数 2-接通次数 3-接通分钟',
  crt_time    datetime null comment '创建时间',
  crt_user    int      null comment '创建者id',
  update_time datetime null comment '更新时间',
  update_user int      null comment '更新者用户id'
);
