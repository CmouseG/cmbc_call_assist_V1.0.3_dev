ALTER TABLE `sys_organization`
ADD COLUMN `start_date`  varchar(32) NULL COMMENT '机器人有效起始时间' AFTER `robot`,
ADD COLUMN `end_date`  varchar(32) NULL COMMENT '机器人有效截止时间' AFTER `start_date`,
ADD COLUMN `effective_date`  varchar(32) NULL COMMENT '授权起始时间' AFTER `usable`,
ADD COLUMN `invalid_date`  varchar(32) NULL COMMENT '授权失效时间' AFTER `effective_date`;