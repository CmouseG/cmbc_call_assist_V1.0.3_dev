USE guiyu_billing;

ALTER TABLE billing_acct_charging_record
ADD charging_amount      DECIMAL(16,2) COMMENT '计费金额';

UPDATE billing_acct_charging_record
SET charging_amount = amount;

/*
	按月统计汇总
*/
DELIMITER $$

USE `guiyu_billing`$$

DROP PROCEDURE IF EXISTS `totalChargingByMonth`$$

CREATE DEFINER=`billing`@`%` PROCEDURE `totalChargingByMonth`(IN total_month VARCHAR(16), IN begin_date VARCHAR(16), IN end_date VARCHAR(16))
BEGIN
		DECLARE account_id VARCHAR(32);			-- 账户ID
		DECLARE total_mode INT;				--	统计方式：1-日  2-月
		
		DECLARE recharge_amount DECIMAL(16,2);		--	统计计算充值金额
		DECLARE consume_amount DECIMAL(16,2);		--	统计计算消费金额
		
		-- 定义遍历标志，默认false
		DECLARE done INT DEFAULT FALSE;
	
		-- 定义游标	查询统计每日充值、消费金额	
		DECLARE cur_account CURSOR FOR
			SELECT  r.account_id,
				SUM(CASE WHEN r.type = 1 THEN r.amount ELSE 0 END ) AS ra,
				SUM(CASE WHEN r.type = 2 THEN r.amount ELSE 0 END ) AS ca 
			FROM billing_acct_charging_record r 
			WHERE r.create_time BETWEEN CONCAT(begin_date, ' 00:00:00') AND CONCAT(end_date, ' 23:59:59') 
			GROUP BY r.account_id;
		
		-- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		SET total_mode = 2;
		
		SELECT total_month, begin_date, end_date FROM DUAL;
		-- 删除之前统计的total_month数据
		DELETE c.* FROM billing_total_charging c WHERE c.total_mode=2 AND c.total_month= total_month;
		 	
		-- 打开游标
		OPEN cur_account;
			
		-- 遍历
		read_loop : LOOP
			-- 取值 取多个字段
			FETCH  NEXT FROM cur_account INTO account_id, recharge_amount, consume_amount;
			IF done THEN
				LEAVE read_loop;
			END IF;

			-- 插入费用统计表
			INSERT INTO billing_total_charging(account_id, total_mode, recharge_amount, consume_amount, total_month, create_time, del_flag) 
			VALUES (account_id, total_mode, recharge_amount, consume_amount, total_month, NOW(), 0);
		
		END LOOP;	-- 遍历结束
		-- 关闭游标
		CLOSE cur_account;
	
	END$$

DELIMITER ;