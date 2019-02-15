ALTER TABLE `sms_task_detail`
ADD COLUMN `planuuid`  varchar(32) NULL COMMENT '挂机短信唯一标识' AFTER `id`,
ADD COLUMN `sms_content`  text NULL COMMENT '短信内容' AFTER `phone`;
