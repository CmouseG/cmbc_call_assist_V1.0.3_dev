-- 查看扣减金额
	SELECT
		a.company_name AS '企业名称',
		a.available_balance/100 AS '扣减之前的金额',
		b.total_amount/100 AS '扣减金额',
		(a.available_balance - b.total_amount)/100 AS '扣减之后的金额'
	FROM 
	(	
		SELECT
			c.org_code,
			SUM(c.am) AS total_amount
		FROM 
		(
			SELECT
				b.org_code,
				b.con * b.oper_duration_m AS am
			FROM	
			(	
				SELECT
					
					CASE 
					 WHEN MOD(l.`bill_sec`, 60)>0 THEN (l.`bill_sec` DIV 60)+1
					 ELSE (l.`bill_sec` DIV 60)
					END AS oper_duration_m,
					i.contract_univalent*100 AS con,
					u.org_code
				FROM guiyu_billing.billing_bill_notify_log l, 
				guiyu_base.sys_user u , guiyu_linemarket.sip_line_base_info i
				WHERE 1=1
					AND l.line_id = i.line_id
					AND l.user_id = u.id 
					AND l.line_id IN 
					(
						1919,
						1920,
						1921,
						1922,
						1923,
						1924,
						1925,
						1926,
						1927,
						1928,
						1929,
						1930,
						1931,
						1932,
						1933,
						1934,
						1935,
						1936,
						1937,
						1938,
						1939,
						1940,
						1941,
						1942
					) 
			)b
		) c
		GROUP BY c.org_code
	)b,
	billing_user_acct a 	
	WHERE b.org_code = a.org_code;
	


--	生成计费项
INSERT INTO billing_acct_charging_term 
(
	user_charging_id, user_id, account_id, 
	charging_item_id, charging_item_name, charging_type, 
	price, unit_price, is_deducted, STATUS,
	create_time, update_time
)
SELECT  
	CAST(CEILING(RAND()*900000000000000000+100000000000000000) AS CHAR) AS user_charging_id,
	e.belong_user AS user_id, a.account_id,
	l.line_id, l.line_name, 1,
	l.contract_univalent*100 , 2, 0, 1,
	NOW(), NOW()
FROM guiyu_linemarket.sip_line_base_info l, guiyu_linemarket.`sip_line_exclusive` e,
	guiyu_billing.billing_user_acct a 
	WHERE 1=1
	AND e.belong_org_code = a.org_code
	AND l.line_id = e.line_id
	AND l.line_id IN 
	(
	1919,
	1920,
	1921,
	1922,
	1923,
	1924,
	1925,
	1926,
	1927,
	1928,
	1929,
	1930,
	1931,
	1932,
	1933,
	1934,
	1935,
	1936,
	1937,
	1938,
	1939,
	1940,
	1941,
	1942
	);
	
	
--------------
-- 生成话费记录

INSERT INTO billing_acct_charging_record 
(
	charging_id, account_id, 
	oper_user_id, oper_user_name, oper_user_org_code, oper_begin_time, oper_end_time, 
	oper_duration, oper_duration_m, oper_duration_str, 
	TYPE, fee_mode, 
	user_charging_id,amount,charging_amount,phone, 
	create_time, update_time, del_flag
)

SELECT 
	CAST(CEILING(RAND()*900000000000000000+100000000000000000) AS CHAR) AS charging_id,
	a.account_id,
	a.oper_user_id, a.oper_user_name, a.oper_user_org_code, a.oper_begin_time, a.oper_end_time,
	a.oper_duration, a.oper_duration_m, a.oper_duration_str, 
	a.type, a.fee_mode,
	a.user_charging_id,
	a.oper_duration_m * a.contract_univalent AS amount,
	a.oper_duration_m * a.contract_univalent AS charging_amount,
	a.phone,
	NOW(),NOW(), 0
FROM 
(
	SELECT
		i.contract_univalent * 100 AS contract_univalent, 
		a.account_id, u.id AS oper_user_id , u.username AS oper_user_name, u.org_code AS oper_user_org_code, a.org_code, l.begin_time AS oper_begin_time,l.end_time AS oper_end_time,
		l.bill_sec AS oper_duration,
		CASE 
		 WHEN MOD(l.`bill_sec`, 60)>0 THEN (l.`bill_sec` DIV 60)+1
		 ELSE (l.`bill_sec` DIV 60)
		END AS oper_duration_m,
		CONCAT('0',(l.`bill_sec` DIV 60), ':', MOD(l.`bill_sec`, 60)) AS oper_duration_str,
		2 AS TYPE, 3 AS fee_mode,
		t.user_charging_id,
		l.phone
	FROM guiyu_billing.billing_bill_notify_log l,  guiyu_billing.billing_user_acct a, guiyu_billing.billing_acct_charging_term t,
	guiyu_base.sys_user u , guiyu_linemarket.sip_line_base_info i
	WHERE 1=1
		AND t.status = 1
		AND l.line_id = i.line_id
		AND l.line_id = t.charging_item_id
		AND u.org_code = a.org_code 
		AND l.user_id = u.id 
		AND l.line_id IN 
		(
			1919,
			1920,
			1921,
			1922,
			1923,
			1924,
			1925,
			1926,
			1927,
			1928,
			1929,
			1930,
			1931,
			1932,
			1933,
			1934,
			1935,
			1936,
			1937,
			1938,
			1939,
			1940,
			1941,
			1942
		) 
)a
;

-----


-- 扣减账户余额
	UPDATE 
	(	
		SELECT
			c.org_code,
			SUM(c.am) AS total_amount
		FROM 
		(
			SELECT
				b.org_code,
				b.con * b.oper_duration_m AS am
			FROM	
			(	
				SELECT
					
					CASE 
					 WHEN MOD(l.`bill_sec`, 60)>0 THEN (l.`bill_sec` DIV 60)+1
					 ELSE (l.`bill_sec` DIV 60)
					END AS oper_duration_m,
					i.contract_univalent*100 AS con,
					u.org_code
				FROM guiyu_billing.billing_bill_notify_log l, 
				guiyu_base.sys_user u , guiyu_linemarket.sip_line_base_info i
				WHERE 1=1
					AND l.line_id = i.line_id
					AND l.user_id = u.id 
					AND l.line_id IN 
					(
						1919,
						1920,
						1921,
						1922,
						1923,
						1924,
						1925,
						1926,
						1927,
						1928,
						1929,
						1930,
						1931,
						1932,
						1933,
						1934,
						1935,
						1936,
						1937,
						1938,
						1939,
						1940,
						1941,
						1942
					) 
			)b
		) c
		GROUP BY c.org_code
	)b,
	billing_user_acct a 
	SET
		a.amount = a.amount - b.total_amount,
		a.available_balance = a.available_balance - b.total_amount
	WHERE b.org_code = a.org_code;



	