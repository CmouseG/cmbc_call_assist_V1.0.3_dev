create index bot_sentence_intent_process_id_index
	on bot_sentence_intent (process_id);


create table bot_sentence_keyword_audit
(
  id             int auto_increment
    primary key,
  process_id     varchar(32)   not null comment '话术ID',
  intent_id      int           not null comment '关键词所属意图ID',
  user_id        int           not null comment '提交审核用户ID',
  keywords_count int default 0 not null comment '待审核关键字数量',
  create_time    datetime      not null comment '创建时间'
)
  comment '关键字审核表';

create table bot_sentence_keyword_audit_item
(
  id               int auto_increment
    primary key,
  keyword_audit_id int           not null comment '关键字审核ID',
  keyword          varchar(100)  not null comment '关键字',
  audit_user_id    int           null comment '审核用户ID',
  audit_status     int default 0 null comment '审核状态:0-待审核;1-已加入模板;2-已删除',
  template_id      int           null comment '关键词模板ID',
  create_time      datetime      not null comment '创建时间',
  last_update_time datetime      not null comment '更新时间'
)
  comment '关键字审核-关键字关联表';

create index bot_sentence_keyword_audit_item_keyword_audit_id_index
  on bot_sentence_keyword_audit_item (keyword_audit_id);