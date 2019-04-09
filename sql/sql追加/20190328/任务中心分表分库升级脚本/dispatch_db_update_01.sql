SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 1、更新数据库表名并添加ORG_ID
-- ----------------------------

alter table `dispatch_plan_0` rename `dispatch_plan_0_tmp`;
alter table `dispatch_plan_1` rename `dispatch_plan_1_tmp`;
alter table `dispatch_plan_2` rename `dispatch_plan_2_tmp`;


ALTER TABLE `dispatch_plan_0_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_plan_1_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_plan_2_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

alter table `dispatch_lines_0` rename `dispatch_lines_0_tmp`;
alter table `dispatch_lines_1` rename `dispatch_lines_1_tmp`;
alter table `dispatch_lines_2` rename `dispatch_lines_2_tmp`;
alter table `dispatch_lines_3` rename `dispatch_lines_3_tmp`;
alter table `dispatch_lines_4` rename `dispatch_lines_4_tmp`;


ALTER TABLE `dispatch_lines_0_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_lines_1_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_lines_2_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_lines_3_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;

ALTER TABLE `dispatch_lines_4_tmp`
ADD COLUMN `org_id`  int NULL AFTER `line_type`;


DELETE B from dispatch_plan_0_tmp A, dispatch_plan_1_tmp B
WHERE A.plan_uuid=B.plan_uuid;

DELETE B from dispatch_plan_0_tmp A, dispatch_plan_2_tmp B
WHERE A.plan_uuid=B.plan_uuid;

DELETE B from dispatch_plan_1_tmp A, dispatch_plan_2_tmp B
WHERE A.plan_uuid=B.plan_uuid;

-- ----------------------------
-- 2.1、设置dispatch_plan_0_tmp的ORG_CODE
-- ----------------------------
UPDATE dispatch_plan_0_tmp O,(
select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE, B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (1,2,3)


)N
ON CONCAT(M.`code`,'.') =N.ORG_CODE
AND M.ID IS NOT NULL
AND M.ID <>''

UNION

select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE,
SUBSTRING_INDEX(B.ORG_CODE,'.',(LENGTH(B.ORG_CODE) - LENGTH( REPLACE(B.ORG_CODE,'.','') )-1)) AS ORG_CODE_ACT,
 B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (4,5)


)N
ON M.`code` =N.ORG_CODE_ACT
AND M.ID IS NOT NULL
AND M.ID <>''


)P
SET  O.ORG_ID=P.ORG_ID
WHERE O.USER_ID=P.USER_ID;



-- ----------------------------
-- 2.2、设置dispatch_plan_1_tmp的ORG_CODE
-- ----------------------------
UPDATE dispatch_plan_1_tmp O,(
select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE, B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (1,2,3)


)N
ON CONCAT(M.`code`,'.') =N.ORG_CODE
AND M.ID IS NOT NULL
AND M.ID <>''

UNION

select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE,
SUBSTRING_INDEX(B.ORG_CODE,'.',(LENGTH(B.ORG_CODE) - LENGTH( REPLACE(B.ORG_CODE,'.','') )-1)) AS ORG_CODE_ACT,
 B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (4,5)


)N
ON M.`code` =N.ORG_CODE_ACT
AND M.ID IS NOT NULL
AND M.ID <>''


)P
SET  O.ORG_ID=P.ORG_ID
WHERE O.USER_ID=P.USER_ID;



-- ----------------------------
-- 2.3、设置dispatch_plan_2_tmp的ORG_CODE
-- ----------------------------
UPDATE dispatch_plan_2_tmp O,(
select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE, B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (1,2,3)


)N
ON CONCAT(M.`code`,'.') =N.ORG_CODE
AND M.ID IS NOT NULL
AND M.ID <>''

UNION

select N.USER_ID, M.ID AS ORG_ID from guiyu_base.sys_organization M 
RIGHT  JOIN
(

select B.ORG_CODE,
SUBSTRING_INDEX(B.ORG_CODE,'.',(LENGTH(B.ORG_CODE) - LENGTH( REPLACE(B.ORG_CODE,'.','') )-1)) AS ORG_CODE_ACT,
 B.id AS USER_ID from guiyu_base.sys_role_user A, guiyu_base.sys_user B
WHERE A.USER_ID= B.ID
AND A.ROLE_ID IN (4,5)


)N
ON M.`code` =N.ORG_CODE_ACT
AND M.ID IS NOT NULL
AND M.ID <>''


)P
SET  O.ORG_ID=P.ORG_ID
WHERE O.USER_ID=P.USER_ID;


-- ----------------------------
-- 3.1、设置dispatch_lines_0_tmp的ORG_CODE
-- ----------------------------

UPDATE dispatch_lines_0_tmp O,dispatch_plan_0_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_0_tmp O,dispatch_plan_1_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_0_tmp O,dispatch_plan_2_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;


-- ----------------------------
-- 3.2、设置dispatch_lines_1_tmp的ORG_CODE
-- ----------------------------
UPDATE dispatch_lines_1_tmp O,dispatch_plan_0_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_1_tmp O,dispatch_plan_1_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_1_tmp O,dispatch_plan_2_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;


-- ----------------------------
-- 3.3、设置dispatch_lines_2_tmp的ORG_CODE
-- ----------------------------

UPDATE dispatch_lines_2_tmp O,dispatch_plan_0_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_2_tmp O,dispatch_plan_1_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_2_tmp O,dispatch_plan_2_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

-- ----------------------------
-- 3.4、设置dispatch_lines_3_tmp的ORG_CODE
-- ----------------------------

UPDATE dispatch_lines_3_tmp O,dispatch_plan_0_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_3_tmp O,dispatch_plan_1_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_3_tmp O,dispatch_plan_2_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

-- ----------------------------
-- 3.5、设置dispatch_lines_4_tmp的ORG_CODE
-- ----------------------------

UPDATE dispatch_lines_4_tmp O,dispatch_plan_0_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_4_tmp O,dispatch_plan_1_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

UPDATE dispatch_lines_4_tmp O,dispatch_plan_2_tmp P
set O.org_id=P.org_id
WHERE O.planuuid=P.plan_uuid;

-- ----------------------------
-- 4、初始化 plan_uuid_create表， 可以提前几天执行
-- ----------------------------
























