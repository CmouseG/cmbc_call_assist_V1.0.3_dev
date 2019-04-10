use guiyu_sms;
ALTER TABLE `sms_task_detail`
ADD COLUMN `create_id`  int(11) NULL COMMENT '创建人' AFTER `org_code`,
ADD COLUMN `create_time`  datetime NULL COMMENT '创建时间' AFTER `create_id`,
ADD COLUMN `update_id`  int(11) NULL COMMENT '更新人' AFTER `create_time`,
ADD COLUMN `update_time`  datetime NULL COMMENT '更新时间' AFTER `update_id`;

UPDATE guiyu_sms.sms_task_detail t1, guiyu_base.sys_user t2
SET t1.create_id = t2.id
WHERE t1.user_name = t2.username;