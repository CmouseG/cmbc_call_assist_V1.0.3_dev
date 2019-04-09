CREATE DEFINER=`root`@`%` PROCEDURE `insertPlanTable`()
BEGIN
    
	DECLARE org_id INT;				-- 组织ID	
	DECLARE tab_num INT;
	DECLARE tab_name VARCHAR(32);
-- DECLARE @create_sql varchar(1024);
-- DECLARE @index_sql VARCHAR(1024);
	
	-- 定义遍历标志，默认false
	DECLARE done INT DEFAULT FALSE;
	
	-- 定义游标	查询组织ID
	DECLARE cur_org_id CURSOR FOR
		SELECT DISTINCT org_id from plan_uuid_create 	;
	
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
		
		-- 删除之前表数据
		SET @del_sql = CONCAT("truncate ",tab_name);		
		PREPARE del_sql FROM @del_sql;   
		EXECUTE del_sql; 
		
		-- 插入新数据
		SET @insert_sql = CONCAT(
		'INSERT INTO ', tab_name,
		"
		(
			   plan_uuid,
			   user_id         , 
			   batch_id        , 
			   phone           , 
			   attach          , 
			   params          , 
			   status_plan     , 
			   status_sync     , 
			   recall          , 
			   recall_params   , 
			   robot           , 
			   line            , 
			   result          , 
			   call_agent      , 
			   clean           , 
			   call_data       , 
			   call_hour       , 
			   gmt_create      , 
			   gmt_modified    , 
			   is_tts          , 
			   replay_type     , 
			   is_del          , 
			   username        , 
			   line_name       , 
			   robot_name      , 
			   batch_name      , 
			   flag            , 
			   org_code        , 
			   org_id,		    
			   city_name       , 
			   city_code       , 
			   line_type       
			)
			SELECT
				q.planuuid_new , 
				a.user_id         , 
				a.batch_id        , 
				a.phone           , 
				a.attach          , 
				a.params          , 
				a.status_plan     , 
				a.status_sync     , 
				a.recall          , 
				a.recall_params   , 
				a.robot           , 
				a.line            , 
				a.result          , 
				a.call_agent      , 
				a.clean           , 
				a.call_data       , 
				a.call_hour       , 
				a.gmt_create      , 
				a.gmt_modified    , 
				a.is_tts          , 
				a.replay_type     , 
				a.is_del          , 
				a.username        , 
				a.line_name       , 
				a.robot_name      , 
				a.batch_name      , 
				a.flag            , 
				a.org_code        , 
				" , org_id , " AS org_id,
				a.city_name       , 
				a.city_code       , 
				a.line_type      
			FROM 
			(
			SELECT 
				p.plan_uuid  , 
				p.user_id         , 
				p.batch_id        , 
				p.phone           , 
				p.attach          , 
				p.params          , 
				p.status_plan     , 
				p.status_sync     , 
				p.recall          , 
				p.recall_params   , 
				p.robot           , 
				p.line            , 
				p.result          , 
				p.call_agent      , 
				p.clean           , 
				p.call_data       , 
				p.call_hour       , 
				p.gmt_create      , 
				p.gmt_modified    , 
				p.is_tts          , 
				p.replay_type     , 
				p.is_del          , 
				p.username        , 
				p.line_name       , 
				p.robot_name      , 
				p.batch_name      , 
				p.flag            , 
				p.org_code        ,
				" , org_id , " AS org_id, 
				p.city_name       , 
				p.city_code       , 
				p.line_type       
			FROM dispatch_plan_0_tmp p 
				WHERE p.org_id = ",org_id,"
				
			UNION
			SELECT 
				p.plan_uuid  , 
				p.user_id         , 
				p.batch_id        , 
				p.phone           , 
				p.attach          , 
				p.params          , 
				p.status_plan     , 
				p.status_sync     , 
				p.recall          , 
				p.recall_params   , 
				p.robot           , 
				p.line            , 
				p.result          , 
				p.call_agent      , 
				p.clean           , 
				p.call_data       , 
				p.call_hour       , 
				p.gmt_create      , 
				p.gmt_modified    , 
				p.is_tts          , 
				p.replay_type     , 
				p.is_del          , 
				p.username        , 
				p.line_name       , 
				p.robot_name      , 
				p.batch_name      , 
				p.flag            , 
				p.org_code        , 
				" , org_id , " AS org_id,
				p.city_name       , 
				p.city_code       , 
				p.line_type      
			FROM dispatch_plan_1_tmp p 
				WHERE p.org_id = ",org_id,"
				
			UNION
			SELECT 
			
				p.plan_uuid  , 
				p.user_id         , 
				p.batch_id        , 
				p.phone           , 
				p.attach          , 
				p.params          , 
				p.status_plan     , 
				p.status_sync     , 
				p.recall          , 
				p.recall_params   , 
				p.robot           , 
				p.line            , 
				p.result          , 
				p.call_agent      , 
				p.clean           , 
				p.call_data       , 
				p.call_hour       , 
				p.gmt_create      , 
				p.gmt_modified    , 
				p.is_tts          , 
				p.replay_type     , 
				p.is_del          , 
				p.username        , 
				p.line_name       , 
				p.robot_name      , 
				p.batch_name      , 
				p.flag            , 
				p.org_code        , 
				" , org_id , " AS org_id,
				p.city_name       , 
				p.city_code       , 
				p.line_type      
			FROM dispatch_plan_2_tmp p
				WHERE p.org_id = ",org_id,"	
			) a	 INNER  JOIN plan_uuid_create q on a.plan_uuid=q.planuuid_old  and q.planuuid_new>0
			order by a.gmt_create asc
		");
		
		PREPARE insert_sql FROM @insert_sql;   
		EXECUTE insert_sql; 
		
		
		
	END LOOP;	-- 遍历结束
	-- 关闭游标
	CLOSE cur_org_id;	
	
	SELECT tab_num FROM DUAL;

  END
