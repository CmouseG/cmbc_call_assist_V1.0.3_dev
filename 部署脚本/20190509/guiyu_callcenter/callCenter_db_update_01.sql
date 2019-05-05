DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `createTable`()
	BEGIN DECLARE org_id INT;
	DECLARE tab_num INT;
	DECLARE tab_name VARCHAR(32);
	DECLARE tab_name_detail VARCHAR(32);
	
	-- 定义遍历标志，默认false
	DECLARE done INT DEFAULT FALSE;
	
	-- 定义游标	查询组织ID
	DECLARE cur_org_id CURSOR FOR
		SELECT o.id FROM guiyu_base.sys_organization o	;
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	SET tab_num = 0;
		
	-- 打开游标
	OPEN cur_org_id;
		
	-- 遍历
	read_loop : LOOP
		-- 取值 取多个字段
		FETCH  NEXT FROM cur_org_id INTO org_id;
		IF done THEN
			LEAVE read_loop;
		END IF;
		
		-- 累加数
		SET tab_num = tab_num + 1;
		
		-- 获取动态表名 call_out_plan_ + org_id
		SET tab_name = CONCAT('call_out_plan_',org_id);
		SELECT tab_name FROM DUAL;
		
		-- 删除之前表
		SET @del_table_sql = CONCAT("DROP TABLE IF EXISTS ",tab_name);		
		PREPARE del_table_sql FROM @del_table_sql;   
		EXECUTE del_table_sql; 
		
		-- 创建plan表
		SET @create_sql = CONCAT(
		'CREATE TABLE ', tab_name,
		"
		(   
			  `call_id` bigint(20) NOT NULL,
			  `plan_uuid` varchar(32) DEFAULT NULL,
			  `phone_num` varchar(20) DEFAULT NULL,
			  `customer_id` int(8) DEFAULT NULL,
			  `temp_id` varchar(30) NOT NULL DEFAULT '',
			  `line_id` int(11) NOT NULL,
			  `serverId` varchar(30) DEFAULT NULL,
			  `agent_id` varchar(30) DEFAULT NULL,
			  `agent_answer_time` datetime DEFAULT NULL,
			  `agent_channel_uuid` varchar(40) DEFAULT NULL,
			  `agent_group_id` varchar(30) DEFAULT NULL,
			  `agent_start_time` datetime DEFAULT NULL,
			  `create_time` datetime DEFAULT NULL,
			  `call_start_time` datetime DEFAULT NULL,
			  `hangup_time` datetime DEFAULT NULL,
			  `answer_time` datetime DEFAULT NULL,
			  `duration` int(11) DEFAULT '0',
			  `bill_sec` int(11) DEFAULT '0',
			  `call_direction` int(2) DEFAULT NULL,
			  `call_state` int(2) DEFAULT NULL,
			  `hangup_direction` int(2) DEFAULT NULL,
			  `accurate_intent` varchar(20) DEFAULT NULL,
			  `reason` varchar(600) DEFAULT NULL,
			  `hangup_code` varchar(10) DEFAULT NULL,
			  `remarks` varchar(255) DEFAULT NULL,
			  `has_tts` tinyint(1) DEFAULT NULL,
			  `ai_id` varchar(50) DEFAULT NULL,
			  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
			  `isdel` int(2) NOT NULL DEFAULT '0',
			  `isread` int(2) NOT NULL DEFAULT '0',
			  `org_code` varchar(30) DEFAULT NULL,
			  `org_id` int(11) DEFAULT NULL,
			  `batch_id` int(11) DEFAULT NULL,
			  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
			  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
			  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
			  `intervened` tinyint(1) DEFAULT '0' COMMENT '是否已介入',
			  `params` varchar(32) DEFAULT NULL COMMENT '变量参数',
			  PRIMARY KEY (`call_id`),
			  UNIQUE KEY `uk_call_out_plan_plan_uuid` (`plan_uuid`),
			  KEY `idx_call_out_plan_phone_num` (`phone_num`),
			  KEY `idx_call_out_plan_customer_id` (`customer_id`),
			  KEY `idx_call_out_plan_agent_id` (`agent_id`),
			  KEY `idx_call_out_plan_agent_answer_time` (`agent_answer_time`),
			  KEY `idx_call_out_plan_create_time` (`create_time`),
			  KEY `idx_call_out_plan_call_start_time` (`call_start_time`),
			  KEY `idx_call_out_plan_call_state` (`call_state`),
			  KEY `idx_call_out_plan_accurate_intent` (`accurate_intent`),
			  KEY `idx_call_out_plan_org_id` (`org_id`)
			) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
		");
		
		PREPARE create_sql FROM @create_sql;   
		EXECUTE create_sql; 
		
			-- 获取动态表名 call_out_detail_ + org_id
		SET tab_name_detail = CONCAT('call_out_detail_',org_id);
		SELECT tab_name_detail FROM DUAL;
		
		-- 删除之前表
		SET @del_table_sql2 = CONCAT("DROP TABLE IF EXISTS ",tab_name_detail);		
		PREPARE del_table_sql2 FROM @del_table_sql2;   
		EXECUTE del_table_sql2; 
		
		-- 创建plan表
		SET @create_sql2 = CONCAT(
		'CREATE TABLE ', tab_name_detail,
		"
		(   
		  `call_detail_id` bigint(20) NOT NULL,
		  `call_id` bigint(20) DEFAULT NULL,
		  `accurate_intent` varchar(10) DEFAULT NULL,
		  `agent_answer_text` varchar(255) DEFAULT NULL,
		  `agent_answer_time` datetime DEFAULT NULL,
		  `ai_duration` int(11) DEFAULT NULL,
		  `asr_duration` int(11) DEFAULT NULL,
		  `bot_answer_text` varchar(255) DEFAULT NULL,
		  `bot_answer_time` datetime DEFAULT NULL,
		  `call_detail_type` int(2) NOT NULL,
		  `customer_say_text` varchar(255) DEFAULT NULL,
		  `customer_say_time` datetime DEFAULT NULL,
		  `reason` varchar(600) DEFAULT NULL,
		  `total_duration` int(11) DEFAULT NULL,
		  `isupdate` int(1) DEFAULT '0' COMMENT '是否修改过',
		  `word_segment_result` varchar(255) DEFAULT NULL,
		  `keywords` varchar(255) DEFAULT NULL,
		  `org_id` int(11) DEFAULT NULL,
		  PRIMARY KEY (`call_detail_id`),
		  KEY `idx_call_out_detail_call_id` (`call_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		");
		
		PREPARE create_sql2 FROM @create_sql2;   
		EXECUTE create_sql2; 
				
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

    END$$