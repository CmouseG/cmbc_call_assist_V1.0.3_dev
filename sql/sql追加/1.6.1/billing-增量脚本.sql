ALTER TABLE billing_acct_charging_record
ADD charging_amount      DECIMAL(16,2) COMMENT '�Ʒѽ��';

UPDATE billing_acct_charging_record
SET charging_amount = amount;

/*
	����ͳ�ƻ���
*/
DELIMITER $$

USE `guiyu_billing`$$

DROP PROCEDURE IF EXISTS `totalChargingByMonth`$$

CREATE DEFINER=`billing`@`%` PROCEDURE `totalChargingByMonth`(IN total_month VARCHAR(16), IN begin_date VARCHAR(16), IN end_date VARCHAR(16))
BEGIN
		DECLARE account_id VARCHAR(32);			-- �˻�ID
		DECLARE total_mode INT;				--	ͳ�Ʒ�ʽ��1-��  2-��
		
		DECLARE recharge_amount DECIMAL(16,2);		--	ͳ�Ƽ����ֵ���
		DECLARE consume_amount DECIMAL(16,2);		--	ͳ�Ƽ������ѽ��
		
		-- ���������־��Ĭ��false
		DECLARE done INT DEFAULT FALSE;
	
		-- �����α�	��ѯͳ��ÿ�ճ�ֵ�����ѽ��	
		DECLARE cur_account CURSOR FOR
			SELECT  r.account_id,
				SUM(CASE WHEN r.type = 1 THEN r.amount ELSE 0 END ) AS ra,
				SUM(CASE WHEN r.type = 2 THEN r.amount ELSE 0 END ) AS ca 
			FROM billing_acct_charging_record r 
			WHERE r.create_time BETWEEN CONCAT(begin_date, ' 00:00:00') AND CONCAT(end_date, ' 23:59:59') 
			GROUP BY r.account_id;
		
		-- ��������־�󶨵��α�
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		SET total_mode = 2;
		
		SELECT total_month, begin_date, end_date FROM DUAL;
		-- ɾ��֮ǰͳ�Ƶ�total_month����
		DELETE c.* FROM billing_total_charging c WHERE c.total_mode=2 AND c.total_month= total_month;
		 	
		-- ���α�
		OPEN cur_account;
			
		-- ����
		read_loop : LOOP
			-- ȡֵ ȡ����ֶ�
			FETCH  NEXT FROM cur_account INTO account_id, recharge_amount, consume_amount;
			IF done THEN
				LEAVE read_loop;
			END IF;

			-- �������ͳ�Ʊ�
			INSERT INTO billing_total_charging(account_id, total_mode, recharge_amount, consume_amount, total_month, create_time, del_flag) 
			VALUES (account_id, total_mode, recharge_amount, consume_amount, total_month, NOW(), 0);
		
		END LOOP;	-- ��������
		-- �ر��α�
		CLOSE cur_account;
	
	END$$

DELIMITER ;