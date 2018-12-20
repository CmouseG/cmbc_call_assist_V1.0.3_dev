create table user_ai_cfg_base_info
(
   id                   varchar(32) not null,
   user_id              varchar(50) not null comment '用户编号',
   org_code             varchar(50),
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
create table user_ai_cfg_info
(
   id                   varchar(32) not null,
   user_id              varchar(50) comment '用户编号',
   ai_num               int comment '机器人数量',
   assign_level         char(1) comment '预分配，高，低',
   template_ids         varchar(200) comment '话术模板',
   open_date            varchar(10) comment '开户日期',
   invalid_date         varchar(10) comment '失效日期',
   status               varchar(5) comment '状态：S-正常，V-失效',
   invalid_policy       varchar(10) comment '失效策略:NUM:100',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table user_ai_cfg_info comment '用户机器配置信息';
create index user_ai_cfg_info_idx1 on user_ai_cfg_info
(
   user_id
);

create index user_ai_cfg_info_idx2 on user_ai_cfg_info
(
   status
);

create table user_ai_cfg_his_info
(
   id                   varchar(32) not null,
   busi_id              varchar(32) comment '业务编号',
   user_id              varchar(50) comment '用户编号',
   ai_num               int comment '机器人数量',
   assign_level         char(1) comment '预分配，高，低',
   template_id          varchar(200) comment '话术模板',
   open_date            varchar(10) comment '开户日期',
   invalid_date         varchar(10) comment '失效日期',
   status               varchar(5) comment '状态：S-正常，V-失效',
   invalid_policy       varchar(10) comment '失效策略:NUM:100',
   handle_type          char(1) comment '操作类型:A-新增;U-更新;D-删除',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50),
   update_time          datetime,
   update_user          varchar(50),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
create table tts_callback_his
(
   id                   varchar(32) not null,
   busi_id              varchar(32) comment '业务id,调用tts的唯一请求id',
   template_id          varchar(32) comment '话术模板编号',
   tts_json_data        text comment 'TTS合成的语音JSON',
   status               char(1) comment '状态: S-完成, F-失败',
   error_msg            varchar(1024) comment '失败日志',
   crt_time             datetime comment '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table tts_callback_his comment 'TTS合成回调数据';
create index tts_callback_his_idx1 on tts_callback_his
(
   busi_id
);
create index tts_callback_his_idx2 on tts_callback_his
(
   status
);
create table tts_wav_his
(
   id                   varchar(32) not null,
   seq_id               varchar(32) comment '会话Id',
   busi_id              varchar(32) comment '业务id,调用tts的唯一请求id',
   template_id          varchar(32) comment '话术模板编号',
   req_params           varchar(1024) comment 'TTS合成请求参数',
   tts_txt_json_data    varchar(1024) comment '需要合成的文本JSON',
   tts_json_data        text comment 'TTS合成的语音JSON',
   status               char(1) comment '状态: P-合成中,S-完成, F-失败',
   error_msg            varchar(1024) comment '失败日志',
   error_type           char(1) comment '失败类型:P-调用失败；T-TTS接口回调失败；L-TTS回调后本地处理失败',
   error_try_num        int comment '失败尝试次数',
   crt_time             datetime comment '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

create table ai_cycle_his
(
   id                   varchar(32) not null,
   user_id              varchar(50) comment '用户编号',
   ai_no                varchar(50) comment '机器人编号',
   ai_name              varchar(50) comment '机器人昵称',
   template_id          varchar(50) comment '话术模板',
   assign_date          varchar(10) comment '分配日期',
   assign_time          varchar(20) comment '分配时间',
   taskback_date        varchar(10) comment '收回日期',
   taskback_time        varchar(20) comment '收回时间',
   call_num             bigint comment '拨打数量',
   crt_time             datetime,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

create table robot_call_his
(
   id                   varchar(32) not null,
   seq_id               varchar(50) comment '会话id',
   user_id              varchar(50) not null comment '用户编号',
   ai_no                varchar(50) comment '机器人编号',
   assign_time          datetime comment '分配时间',
   template_id          varchar(50) comment '话术模板',
   call_status          varchar(3) comment '通话状态:S-通话完成,I-通话中',
   sellbot_callback_json text comment 'sellbot回调报文',
   crt_date             varchar(10) comment '创建日期',
   crt_time             datetime comment '创建时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table robot_call_his comment '通话历史';

create index robot_call_his_idx1 on robot_call_his
(
   seq_id
);

create index robot_call_his_idx2 on robot_call_his
(
   assign_time
);

create index robot_call_his_idx3 on robot_call_his
(
   user_id
);


create table robot_call_process_stat
(
   id                   varchar(32) not null,
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


CREATE TABLE `bd_table_sequence` (
  `table_name` varchar(64) NOT NULL,
  `seq` bigint(12) DEFAULT NULL,
  `step` int(6) DEFAULT NULL,
  `sign` varchar(6) DEFAULT NULL,
  `now_date` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`table_name`),
  KEY `index` (`table_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('ai_cycle_his', 1, 1, 'ACH', '20181126');
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('tts_callback_his', 1, 1, 'TCH', '20181128');
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('tts_wav_his', 1, 1, 'TTS', '20181130');
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('user_ai_cfg_base_info', 0, 1, 'UAB', '20181201');
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('user_ai_cfg_his_info', 1, 1, 'UCH', '20181202');
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('user_ai_cfg_info', 1, 1, 'UAH', '20181202');
INSERT INTO bd_table_sequence (table_name, seq, step, sign, now_date) VALUES ('robot_call_process_stat', 1, 1, 'PS', '20181206');
INSERT INTO bd_table_sequence (table_name, seq, step, sign, now_date) VALUES ('robot_call_his', 1, 1, 'CH', '20181206');


DROP FUNCTION genTabId;
--/
CREATE DEFINER=`root`@`%` FUNCTION genTabId(SEQ_NAME VARCHAR(64)) RETURNS varchar(32) CHARSET utf8
    READS SQL DATA
BEGIN
	DECLARE TMP VARCHAR(14);
	DECLARE TMP1 BIGINT;
	DECLARE TMP2 VARCHAR(6);
	DECLARE TMP3 VARCHAR(8);

	UPDATE bd_table_sequence SET seq = seq + step WHERE table_name = SEQ_NAME;
	SET TMP = DATE_FORMAT(NOW(),'%Y%m%d');
	SELECT seq,sign,now_date INTO TMP1,TMP2,TMP3 FROM bd_table_sequence WHERE table_name=SEQ_NAME;
  IF TMP3 = '' THEN
		SET TMP3 = '19700101';
	ELSEIF  TMP3 = NULL  THEN
		SET TMP3 = '19700101';
	END IF;
	IF TMP3 <> TMP THEN 
		UPDATE bd_table_sequence SET seq = 0 ,now_date = TMP WHERE table_name = SEQ_NAME;
	END IF;
	RETURN CONCAT(TMP,TMP2, LPAD(TMP1, 7, '0'),RIGHT(UUID_SHORT(),4));
END
/
