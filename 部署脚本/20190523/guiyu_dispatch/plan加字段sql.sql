DELIMITER $$

USE `guiyu_dispatch`$$

DROP PROCEDURE IF EXISTS `alterPlanTable`$$

CREATE DEFINER=`root`@`%` PROCEDURE `alterPlanTable`()
BEGIN
    
	DECLARE org_id INT;				-- 	组织ID	
	DECLARE tab_num INT;
	DECLARE tab_name VARCHAR(32);
	
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
		
		-- 获取动态表名 dispatch_plan_ + org_id
		SET tab_name = CONCAT('dispatch_plan_',org_id);
		SELECT tab_name FROM DUAL;
		
		
		-- 修改plan表
		SET @alter_sql = CONCAT(
		'ALTER TABLE ', tab_name,
		"
			ADD line_id              varchar(16) comment '实际拨打线路ID',
			ADD remark               varchar(255) comment '备注描述',
			ADD cust_name            varchar(32) comment '客户姓名',
			ADD cust_company         varchar(64) comment '客户所属单位'
		   	
		");
		
		PREPARE alter_sql FROM @alter_sql;   
		EXECUTE alter_sql; 
				
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

    END$$

DELIMITER ;