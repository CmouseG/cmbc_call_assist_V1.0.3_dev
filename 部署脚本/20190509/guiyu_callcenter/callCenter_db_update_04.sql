
-- ----------------------------
-- 1、更新数据库表名并添加ORG_ID
-- ----------------------------

alter table `call_out_plan_0` rename `call_out_plan_0_tmp`;
alter table `call_out_plan_1` rename `call_out_plan_1_tmp`;


ALTER TABLE `call_out_plan_0_tmp`
ADD COLUMN `org_id`  int NULL AFTER `org_code`;

ALTER TABLE `call_out_plan_1_tmp`
ADD COLUMN `org_id`  int NULL AFTER `org_code`;

ALTER TABLE `call_out_plan`
ADD COLUMN `org_id`  int NULL AFTER `org_code`;

alter table `call_out_detail_0` rename `call_out_detail_0_tmp`;
alter table `call_out_detail_1` rename `call_out_detail_1_tmp`;


ALTER TABLE `call_out_detail_0_tmp`
ADD COLUMN `org_id`  int NULL AFTER `keywords`;

ALTER TABLE `call_out_detail_1_tmp`
ADD COLUMN `org_id`  int NULL AFTER `keywords`;

ALTER TABLE `call_out_detail`
ADD COLUMN `org_id`  int NULL AFTER `keywords`;

ALTER TABLE `call_out_detail_0_tmp`
drop COLUMN `sharding_value`;

ALTER TABLE `call_out_detail_1_tmp`
drop COLUMN `sharding_value`;

ALTER TABLE `call_out_detail`
drop COLUMN `sharding_value`;

-- ----------------------------
-- 2.1、设置call_out_plan的org_id
-- ----------------------------

UPDATE call_out_plan_0_tmp x, guiyu_base.sys_organization y, guiyu_base.sys_user z
SET x.ORG_ID = y.id
WHERE x.customer_id = z.id and z.org_code = y.code;

UPDATE call_out_plan_1_tmp x, guiyu_base.sys_organization y, guiyu_base.sys_user z
SET x.ORG_ID = y.id
WHERE x.customer_id = z.id and z.org_code = y.code;

-- ----------------------------
-- 2.1、设置call_out_detail的org_id
-- ----------------------------

UPDATE call_out_detail_0_tmp x, call_out_plan_0_tmp y
SET x.ORG_ID = y.ORG_ID
WHERE x.call_id = y.call_id;

UPDATE call_out_detail_0_tmp x, call_out_plan_1_tmp y
SET x.ORG_ID = y.ORG_ID
WHERE x.call_id = y.call_id;

UPDATE call_out_detail_1_tmp x, call_out_plan_0_tmp y
SET x.ORG_ID = y.ORG_ID
WHERE x.call_id = y.call_id;

UPDATE call_out_detail_1_tmp x, call_out_plan_1_tmp y
SET x.ORG_ID = y.ORG_ID
WHERE x.call_id = y.call_id;


-- ----------------------------
-- 3、执行存储过程
-- ----------------------------

call createTablePlan();
call createTableDetail();
call insertData();


















