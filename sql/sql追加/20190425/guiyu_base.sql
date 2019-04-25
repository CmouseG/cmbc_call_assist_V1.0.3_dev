
use guiyu_base;

CREATE PROCEDURE timeInit()
	BEGIN 
		DECLARE num INT;
		DECLARE orgCode VARCHAR(30);
		DECLARE startTime DATE;
		DECLARE vaildTime DATE;
		DECLARE orgTime CURSOR FOR SELECT a.org_code, a.start_time, a.vaild_time
															 FROM sys_user a
															 LEFT JOIN sys_role_user b ON a.id = b.user_id
															 LEFT JOIN sys_role c ON c.id = b.role_id
															 LEFT JOIN sys_organization d ON a.org_code = d.`code`
															 WHERE c.`name` = '企业管理员';
		DECLARE EXIT HANDLER FOR NOT FOUND CLOSE orgTime;
		
		SELECT COUNT(1) INTO num 
		FROM sys_user a
		LEFT JOIN sys_role_user b ON a.id = b.user_id
		LEFT JOIN sys_role c ON c.id = b.role_id
		LEFT JOIN sys_organization d ON a.org_code = d.`code`
		WHERE c.`name` = '企业管理员';

		OPEN orgTime;
		REPEAT
			FETCH orgTime INTO orgCode,startTime,vaildTime;
			UPDATE sys_organization SET effective_date = date_format(startTime,'%Y-%m-%d'), invalid_date = date_format(vaildTime,'%Y-%m-%d') WHERE `code` = orgCode;
			SET num = num - 1;
		UNTIL num = 0 END REPEAT;
		CLOSE orgTime;
	END;

CALL timeInit();


UPDATE sys_organization 
SET effective_date = DATE_FORMAT(NOW(), '%Y-%m-%d')
WHERE (effective_date IS NULL OR effective_date = '');

UPDATE sys_organization 
SET invalid_date = DATE_FORMAT(NOW(), '%Y-%m-%d')
WHERE (invalid_date IS NULL OR invalid_date = '');

UPDATE sys_organization SET invalid_date = '2050-12-30' WHERE `code` = '1';


INSERT INTO `guiyu_base`.`sys_menu` (`name`, `description`, `url`, `pid`, `permission`, `is_show`, `create_id`, `create_time`, `update_id`, `update_time`, `type`, `level`, `appid`, `remarks`, `del_flag`, `sys_type`) VALUES ('系统管理-开户管理-延期', NULL, 'system_account_delay', '21', 'system_account_delay', '0', '1', '2019-04-24 18:17:18', '1', '2019-04-24 18:17:52', '2', '3', '0', '0', '0', NULL);
