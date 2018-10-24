CREATE TABLE `call_out_plan` (   
    `uuid` varchar(32) NOT NULL,   
    `phone_num` varchar(30) DEFAULT NULL,​   
    `customer_id` varchar(255) DEFAULT NULL,​   
    `temp_id` varchar(255) DEFAULT NULL,​   
    `lineId` varchar(255) DEFAULT NULL, ​​   
    `agent_id` varchar(255) DEFAULT NULL,​   
    `agent_answer_time` varchar(255) DEFAULT NULL,   
    `agent_channel_uuid` varchar(255) DEFAULT NULL,   
    `agent_group_id` varchar(255) DEFAULT NULL,   
    `agent_start_time` varchar(255) DEFAULT NULL, ​ 
    `create_time` varchar(255) DEFAULT NULL,​ 
    `schedule_time` varchar(255) DEFAULT NULL,​ 
    `hangup_time` varchar(255) DEFAULT NULL,​   
    `answer_time` varchar(255) DEFAULT NULL,   
    `duration` int(11) DEFAULT NULL,​   
    `bill_sec` int(11) DEFAULT NULL, ​   
    `call_direction` int(11) DEFAULT NULL,   
    `call_state` int(11) DEFAULT NULL, 
    `hangup_direction` 机器人挂断/用户挂断 ！！！​  
    `accurate_intent​` varchar(20) DEFAULT NULL,   
    `hangup_code` varchar(255) DEFAULT NULL,   
    `originate_cmd` varchar(500) DEFAULT NULL,   
    `record_file` varchar(255) DEFAULT NULL,   
    `record_file_url` varchar(255) DEFAULT NULL,   
    `remarks` varchar(255) DEFAULT NULL,     
    PRIMARY KEY (`uuid`) 
) ENGINE=Innodb DEFAULT CHARSET=utf8;

CREATE TABLE `call_in_plan` (   
    `uuid` varchar(32) NOT NULL,   
    `phone_num` varchar(30) DEFAULT NULL,​   
    `customer_id` varchar(255) DEFAULT NULL,​   
    `temp_id` varchar(255) DEFAULT NULL,​   
    `lineId` varchar(255) DEFAULT NULL, ​​   
    `agent_id` varchar(255) DEFAULT NULL,​   
    `agent_answer_time` varchar(255) DEFAULT NULL,   
    `agent_channel_uuid` varchar(255) DEFAULT NULL,   
    `agent_group_id` varchar(255) DEFAULT NULL,   
    `agent_start_time` varchar(255) DEFAULT NULL, ​ 
    `create_time` varchar(255) DEFAULT NULL,​ 
    `schedule_time` varchar(255) DEFAULT NULL,​ 
    `hangup_time` varchar(255) DEFAULT NULL,​   
    `answer_time` varchar(255) DEFAULT NULL,   
    `duration` int(11) DEFAULT NULL,​   
    `bill_sec` int(11) DEFAULT NULL, ​   
    `call_direction` int(11) DEFAULT NULL,   
    `call_state` int(11) DEFAULT NULL, 
    `hangup_direction` 机器人挂断/用户挂断 ！！！​  
    `accurate_intent​` varchar(20) DEFAULT NULL,   
    `hangup_code` varchar(255) DEFAULT NULL,   
    `originate_cmd` varchar(500) DEFAULT NULL,   
    `record_file` varchar(255) DEFAULT NULL,   
    `record_file_url` varchar(255) DEFAULT NULL,   
    `remarks` varchar(255) DEFAULT NULL,     
    PRIMARY KEY (`uuid`) 
) ENGINE=Innodb DEFAULT CHARSET=utf8;

CREATE TABLE `call_out_detail` (   
    `cid` bigint(20) NOT NULL,   
    `accurate_intent` varchar(255) DEFAULT NULL,   
    `agent_answer_text` varchar(255) DEFAULT NULL,   
    `agent_answer_time` varchar(255) DEFAULT NULL,   
    `agent_record_file` varchar(255) DEFAULT NULL,   
    `agent_record_url` varchar(255) DEFAULT NULL,   
    `ai_duration` bigint(20) DEFAULT NULL,   
    `asr_duration` bigint(20) DEFAULT NULL,   
    `bot_answer_text` varchar(800) DEFAULT NULL,   
    `bot_answer_time` varchar(255) DEFAULT NULL,   
    `bot_answer_wav_file` varchar(255) DEFAULT NULL,   
    `call_detail_type` int(11) DEFAULT NULL,   
    `call_plan_id` varchar(255) DEFAULT NULL,   
    `customer_say_text` varchar(255) DEFAULT NULL,   
    `customer_say_time` varchar(255) DEFAULT NULL,   
    `customer_say_wav_file` varchar(255) DEFAULT NULL,   
    `customer_say_wav_file_url` varchar(255) DEFAULT NULL,  
    `reason` varchar(255) DEFAULT NULL,   
    `total_duration` bigint(20) DEFAULT NULL,   
    PRIMARY KEY (`cid`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `call_in_detail` (   
    `cid` bigint(20) NOT NULL,   
    `accurate_intent` varchar(255) DEFAULT NULL,   
    `agent_answer_text` varchar(255) DEFAULT NULL,   
    `agent_answer_time` varchar(255) DEFAULT NULL,   
    `agent_record_file` varchar(255) DEFAULT NULL,   
    `agent_record_url` varchar(255) DEFAULT NULL,   
    `ai_duration` bigint(20) DEFAULT NULL,   
    `asr_duration` bigint(20) DEFAULT NULL,   
    `bot_answer_text` varchar(800) DEFAULT NULL,   
    `bot_answer_time` varchar(255) DEFAULT NULL,   
    `bot_answer_wav_file` varchar(255) DEFAULT NULL,   
    `call_detail_type` int(11) DEFAULT NULL,   
    `call_plan_id` varchar(255) DEFAULT NULL,   
    `customer_say_text` varchar(255) DEFAULT NULL,   
    `customer_say_time` varchar(255) DEFAULT NULL,   
    `customer_say_wav_file` varchar(255) DEFAULT NULL,   
    `customer_say_wav_file_url` varchar(255) DEFAULT NULL,   
    `reason` varchar(255) DEFAULT NULL,   
    `total_duration` bigint(20) DEFAULT NULL,   
    PRIMARY KEY (`cid`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8;