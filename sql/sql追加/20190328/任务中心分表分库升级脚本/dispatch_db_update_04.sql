SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `dispatch_batch_line`;
CREATE TABLE `dispatch_batch_line` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` int(11) NOT NULL COMMENT '批次id',
  `line_id` int(11) NOT NULL COMMENT '线路id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `line_type` int(1)  COMMENT '线路类型：1-SIP；2-网关',
  `line_name` varchar(64) NOT NULL COMMENT '线路名称',
  `line_amount` decimal(10,4) DEFAULT NULL COMMENT '线路数量',
  `overtArea` varchar(32) DEFAULT NULL COMMENT '线路外呼归属地',
  PRIMARY KEY (`id`),
  KEY `Index_dispatch_batch_line_batch_id` (`batch_id`),
  KEY `Index_dispatch_batch_line_id` (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO dispatch_batch_line(batch_id,line_id,line_name,user_id,org_id,line_amount,overtArea)

select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_0_tmp A,dispatch_plan_0_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_0_tmp A,dispatch_plan_1_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_0_tmp A,dispatch_plan_2_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_1_tmp A,dispatch_plan_0_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_1_tmp A,dispatch_plan_1_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_1_tmp A,dispatch_plan_2_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_2_tmp A,dispatch_plan_0_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_2_tmp A,dispatch_plan_1_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_2_tmp A,dispatch_plan_2_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_3_tmp A,dispatch_plan_0_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_3_tmp A,dispatch_plan_1_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_3_tmp A,dispatch_plan_2_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_4_tmp A,dispatch_plan_0_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_4_tmp A,dispatch_plan_1_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea
UNION
select B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea from dispatch_lines_4_tmp A,dispatch_plan_2_tmp B
WHERE A.planuuid=B.plan_uuid
group by B.batch_id,A.line_id,A.line_name,B.user_id,B.org_id,A.line_amount,A.overtArea;
