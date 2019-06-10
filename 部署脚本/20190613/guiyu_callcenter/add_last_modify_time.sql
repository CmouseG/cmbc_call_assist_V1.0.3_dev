
DELIMITER $$

USE `guiyu_callcenter`$$

DROP PROCEDURE IF EXISTS `add_last_modify_time`$$

CREATE DEFINER=`root`@`%` PROCEDURE `add_last_modify_time`()
BEGIN

	DECLARE tab_num INT;
	DECLARE tab_name VARCHAR(32);
	
	-- 定义遍历标志，默认false
	DECLARE done INT DEFAULT FALSE;
	
	-- 定义游标	查询组织ID
	DECLARE cur_table_name CURSOR FOR
		SELECT table_name FROM information_schema.TABLES WHERE table_schema= 'guiyu_callcenter' AND  table_name LIKE 'call_out_plan%';
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	SET tab_num = 0;
		
	-- 打开游标
	OPEN cur_table_name;
		
	-- 遍历
	read_loop : LOOP
		-- 取值 取多个字段
		FETCH  NEXT FROM cur_table_name INTO tab_name; 
		IF done THEN
			LEAVE read_loop;
		END IF;
		
		-- 累加数
		SET tab_num = tab_num + 1;
		
		-- 增加字段last_modify_time
		SET @alter_sql = CONCAT(
		'ALTER TABLE ', tab_name,
		" ADD COLUMN last_modify_time DATETIME ON UPDATE CURRENT_TIMESTAMP; ");
		
		PREPARE alter_sql FROM @alter_sql;   
		EXECUTE alter_sql; 
		
		-- 初始化数据last_modify_time
		SET @update_sql = CONCAT(
		'UPDATE ', tab_name,
		" SET last_modify_time = create_time; ");
		
		PREPARE update_sql FROM @update_sql;   
		EXECUTE update_sql; 
				
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_table_name;	
	
	SELECT tab_num FROM DUAL;

    END$$

DELIMITER ;
