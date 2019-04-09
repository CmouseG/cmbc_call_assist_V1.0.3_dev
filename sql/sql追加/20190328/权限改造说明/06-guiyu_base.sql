##给存量数据组织增加权限关联关系
CREATE PROCEDURE inst()
	BEGIN 
		DECLARE resourceId INT;
		DECLARE orgId INT;
		DECLARE orgCode VARCHAR(30);
		DECLARE num_org INT;
		DECLARE num_menu INT;
		DECLARE org CURSOR FOR SELECT id FROM sys_organization where id <> 1;
		DECLARE menuId CURSOR FOR SELECT id FROM sys_menu;
		DECLARE EXIT HANDLER FOR NOT FOUND CLOSE org;

		SELECT COUNT(1) FROM sys_organization INTO num_org;
		
		OPEN org;
		REPEAT
			FETCH org INTO orgId;
				OPEN menuId;
				SELECT COUNT(1) FROM sys_menu INTO num_menu;
				REPEAT
					FETCH menuId INTO resourceId;
					SELECT code INTO orgCode FROM sys_organization where id = orgId;
					INSERT INTO sys_privilege(auth_id,auth_type,resource_id,resource_type,org_code) VALUES (orgId,2,resourceId,1,orgCode);
					SET num_menu = num_menu - 1;
				UNTIL num_menu = 0 END REPEAT;
				CLOSE menuId;
			SET num_org = num_org - 1;
		UNTIL num_org = 0 END REPEAT;
		CLOSE org;
	END;

CALL inst();



##新增数据刷操作人和操作时间
use guiyu_base;
UPDATE sys_privilege t 
SET t.crt_user = 1,t.crt_time = NOW(),t.update_user = 1,t.update_time = NOW()
where t.update_time IS NULL;