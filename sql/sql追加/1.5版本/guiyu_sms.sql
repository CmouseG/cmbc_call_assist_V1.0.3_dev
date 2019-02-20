ALTER TABLE `sms_task_detail`
ADD COLUMN `planuuid`  varchar(32) NULL COMMENT '挂机短信唯一标识' AFTER `id`,
ADD COLUMN `sms_content`  text NULL COMMENT '短信内容' AFTER `phone`;

ALTER TABLE `sms_platform`
MODIFY COLUMN `platform_params`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置参数列表' AFTER `platform_name`;
