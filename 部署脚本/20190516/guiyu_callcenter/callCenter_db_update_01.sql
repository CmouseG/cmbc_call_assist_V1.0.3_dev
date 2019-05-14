DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `createTablePlan`()
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
			  `call_id` bigint(20) NOT NULL COMMENT '主键',
			  `plan_uuid` varchar(32) DEFAULT NULL COMMENT '计划uuid',
			  `phone_num` varchar(20) DEFAULT NULL COMMENT '电话号码',
			  `customer_id` int(8) DEFAULT NULL COMMENT '客户id',
			  `temp_id` varchar(30) NOT NULL DEFAULT '' COMMENT '模板id',
			  `line_id` int(11) NOT NULL COMMENT '线路id',
			  `serverId` varchar(30) DEFAULT NULL COMMENT '外呼服务id',
			  `agent_id` varchar(30) DEFAULT NULL COMMENT '坐席id',
			  `agent_answer_time` datetime DEFAULT NULL COMMENT '坐席说话时间',
			  `agent_channel_uuid` varchar(40) DEFAULT NULL COMMENT '坐席通道id',
			  `agent_group_id` varchar(30) DEFAULT NULL COMMENT '坐席组id',
			  `agent_start_time` datetime DEFAULT NULL COMMENT '坐席开始时间',
			  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
			  `call_start_time` datetime DEFAULT NULL COMMENT '拨打时间',
			  `hangup_time` datetime DEFAULT NULL COMMENT '挂断时间',
			  `answer_time` datetime DEFAULT NULL COMMENT '接听时间',
			  `duration` int(11) DEFAULT '0' COMMENT '拨打时长',
			  `bill_sec` int(11) DEFAULT '0' COMMENT '接听时长',
			  `call_direction` int(2) DEFAULT NULL COMMENT '呼叫方向',
			  `call_state` int(2) DEFAULT NULL COMMENT '电话状态',
			  `hangup_direction` int(2) DEFAULT NULL COMMENT '挂断方',
			  `accurate_intent` varchar(20) DEFAULT NULL COMMENT '意向标签',
			  `reason` varchar(600) DEFAULT NULL COMMENT '意向备注',
			  `hangup_code` varchar(10) DEFAULT NULL COMMENT '挂断码',
			  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
			  `has_tts` tinyint(1) DEFAULT NULL COMMENT '是否是tts',
			  `ai_id` varchar(50) DEFAULT NULL COMMENT 'F类备注',
			  `freason` int(2) DEFAULT NULL COMMENT '1:占线，2:无人接听,3:主叫停机,4:被叫停机,5:空号,6:关机,7:呼叫限制,8:用户拒接,9:无效号码,10:拒接',
			  `isdel` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除',
			  `isread` int(2) NOT NULL DEFAULT '0' COMMENT '是否已读',
			  `org_code` varchar(30) DEFAULT NULL COMMENT '组织机构码',
			  `org_id` int(11) DEFAULT NULL,
			  `batch_id` int(11) DEFAULT NULL COMMENT '批次id',
			  `talk_num` int(4) DEFAULT '0' COMMENT '对话轮数',
			  `is_cancel` int(1) DEFAULT '0' COMMENT '是否超时',
			  `is_answer` int(1) DEFAULT '0' COMMENT '是否接听',
			  `intervened` tinyint(1) DEFAULT '0' COMMENT '是否已介入',
			  `params` varchar(512) DEFAULT NULL COMMENT '变量参数',
			  PRIMARY KEY (`call_id`),
			  UNIQUE KEY `uk_", tab_name,"_plan_uuid` (`plan_uuid`),
			  KEY `idx_", tab_name,"_phone_num` (`phone_num`),
			  KEY `idx_", tab_name,"_customer_id` (`customer_id`),
			  KEY `idx_", tab_name,"_agent_id` (`agent_id`),
			  KEY `idx_", tab_name,"_agent_answer_time` (`agent_answer_time`),
			  KEY `idx_", tab_name,"_create_time` (`create_time`),
			  KEY `idx_", tab_name,"_call_start_time` (`call_start_time`),
			  KEY `idx_", tab_name,"_call_state` (`call_state`),
			  KEY `idx_", tab_name,"_accurate_intent` (`accurate_intent`),
			  KEY `idx_", tab_name,"_org_id` (`org_id`)
			) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
		");
		
		PREPARE create_sql FROM @create_sql;   
		EXECUTE create_sql; 
		
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

    END$$