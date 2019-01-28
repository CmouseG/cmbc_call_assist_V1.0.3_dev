alter table bot_sentence_branch add column ( 
agent_intent	varchar(256) comment ''
, need_agent	varchar(32) comment '是否需要转人工'
, weight varchar(32) comment '权重'
, rule varchar(8) comment '跳转规则'
);



alter table bot_sentence_domain add column (
ignore_user_sentence	tinyint(1),
match_order	varchar(256),
not_match_less4_to	varchar(256),
not_match_to	varchar(256),
no_words_to	varchar(256),
is_special_limit_free	tinyint(1),
ignore_but_negative	tinyint(1)
);


alter table bot_sentence_intent add column (
refrence_id	bigint(20) comment '引用意图库ID'
);


alter table bot_sentence_process add column (
industry_id	varchar(32) comment '行业编号',
test_state	varchar(8) comment '测试部署状态'
);


alter table bot_sentence_template modify column industry_id varchar(256);
alter table bot_sentence_template modify column industry_name	varchar(256);
alter table bot_sentence_template modify column account_no varchar(256);


alter table bot_sentence_tts_backup add column (
times	int(11) comment '录音时长',
wav_name	varchar(32) comment '录音文件名'
);


alter table bot_sentence_tts_task add column (
times	int(11) comment '录音时长',
wav_name	varchar(32) comment '录音文件名'
);


alter table volice_info add column (
times	int(11) comment '录音时长',
wav_name	varchar(32) comment '录音文件名'
);
