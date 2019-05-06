DELIMITER $$
CREATE DEFINER=`root`@`%` PROCEDURE `insertData`()
BEGIN
    
	DECLARE org_id INT;				--	组织ID	
	DECLARE tab_num INT;
	DECLARE tab_name_plan VARCHAR(32);
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
		
		-- 插入数据call_out_plan
		SET tab_name_plan = CONCAT('call_out_plan_',org_id);
		SELECT tab_name_plan FROM DUAL;
		
		-- 插入数据
		SET @insert_plan_sql_0 = CONCAT(
		'insert into ', tab_name_plan,
		" select * from call_out_plan_0_tmp where org_id = ", org_id, ";");
		
		PREPARE insert_plan_sql_0 FROM @insert_plan_sql_0;   
		EXECUTE insert_plan_sql_0; 

		SET @insert_plan_sql_1 = CONCAT(
		'insert into ', tab_name_plan,
		" select * from call_out_plan_1_tmp where org_id = ", org_id, ";");
		
		PREPARE insert_plan_sql_1 FROM @insert_plan_sql_1;   
		EXECUTE insert_plan_sql_1; 
		
		-- 插入数据call_out_detail
		SET tab_name_detail = CONCAT('call_out_detail_',org_id);
		SELECT tab_name_detail FROM DUAL;
		
		-- 插入数据
		SET @insert_detail_sql_0 = CONCAT(
		'insert into ', tab_name_detail,
		" select * from call_out_detail_0_tmp where org_id = ", org_id, ";");
		
		PREPARE insert_detail_sql_0 FROM @insert_detail_sql_0;   
		EXECUTE insert_detail_sql_0; 

		SET @insert_detail_sql_1 = CONCAT(
		'insert into ', tab_name_detail,
		" select * from call_out_detail_1_tmp where org_id = ", org_id, ";");
		
		PREPARE insert_detail_sql_1 FROM @insert_detail_sql_1;   
		EXECUTE insert_detail_sql_1;
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

    END$$