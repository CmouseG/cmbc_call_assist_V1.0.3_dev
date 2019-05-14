DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `createTableDetail`()
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
		  `call_detail_id` bigint(20) NOT NULL COMMENT '明细表id',
		  `call_id` bigint(20) DEFAULT NULL COMMENT '通话记录id',
		  `accurate_intent` varchar(10) DEFAULT NULL COMMENT '意向标签',
		  `agent_answer_text` varchar(255) DEFAULT NULL COMMENT '坐席说话内容',
		  `agent_answer_time` datetime DEFAULT NULL COMMENT '坐席说话时间',
		  `ai_duration` int(11) DEFAULT NULL COMMENT '机器人说话时长',
		  `asr_duration` int(11) DEFAULT NULL COMMENT '客户说话时长',
		  `bot_answer_text` varchar(255) DEFAULT NULL COMMENT '机器人说话内容',
		  `bot_answer_time` datetime DEFAULT NULL COMMENT '机器人说话时间',
		  `call_detail_type` int(2) NOT NULL COMMENT '明细类别',
		  `customer_say_text` varchar(255) DEFAULT NULL COMMENT '客户说话内容',
		  `customer_say_time` datetime DEFAULT NULL COMMENT '客户说话时间',
		  `reason` varchar(600) DEFAULT NULL COMMENT '意向备注',
		  `total_duration` int(11) DEFAULT NULL COMMENT '总时长',
		  `isupdate` int(1) DEFAULT '0' COMMENT '是否修改过',
		  `word_segment_result` varchar(255) DEFAULT NULL COMMENT '分词结果',
		  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
		  `org_id` int(11) DEFAULT NULL,
		  PRIMARY KEY (`call_detail_id`),
		  KEY `idx_", tab_name_detail,"_call_id` (`call_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		");
		
		PREPARE create_sql2 FROM @create_sql2;   
		EXECUTE create_sql2; 
				
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

    END$$