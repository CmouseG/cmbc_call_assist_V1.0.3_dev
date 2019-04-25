
use guiyu_sms;

ALTER TABLE `sms_platform`
DROP COLUMN `update_id`,
DROP COLUMN `update_time`,
MODIFY COLUMN `id`  int(11) NOT NULL AUTO_INCREMENT FIRST ,
MODIFY COLUMN `platform_name`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '平台名称' AFTER `id`,
MODIFY COLUMN `platform_params`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台参数' AFTER `platform_name`,
MODIFY COLUMN `identification`  varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内部标识' AFTER `platform_params`,
MODIFY COLUMN `org_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组织代码' AFTER `identification`,
MODIFY COLUMN `create_id`  int(11) NULL DEFAULT NULL COMMENT '创建人id' AFTER `org_code`,
ADD COLUMN `content_type`  int(1) NULL COMMENT '内容形式：1-短信内容；2-短信模版' AFTER `identification`,
ADD COLUMN `create_name`  varchar(32) NULL COMMENT '创建人名称' AFTER `create_id`;


ALTER TABLE `sms_tunnel`
DROP COLUMN `company_id`,
DROP COLUMN `update_id`,
DROP COLUMN `update_time`,
MODIFY COLUMN `platform_name`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台名称' AFTER `id`,
MODIFY COLUMN `org_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织代码' AFTER `platform_config`,
CHANGE COLUMN `company_name` `org_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称' AFTER `org_code`,
MODIFY COLUMN `create_id`  int(11) NULL DEFAULT NULL COMMENT '创建人id' AFTER `org_name`,
ADD COLUMN `content_type`  int(1) NULL COMMENT '内容形式：1-短信内容；2-短信模版' AFTER `platform_config`,
ADD COLUMN `create_name`  varchar(32) NULL COMMENT '创建人名称' AFTER `create_id`;


ALTER TABLE `sms_config`
DROP COLUMN `sms_template_id`,
DROP COLUMN `sms_template_data`,
DROP COLUMN `company_id`,
DROP COLUMN `user_id`,
MODIFY COLUMN `sms_content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '短信内容（短信模版/自定义短信内容）' AFTER `intention_tag`,
MODIFY COLUMN `org_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '组织编码' AFTER `run_status`,
CHANGE COLUMN `company_name` `org_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称' AFTER `org_code`,
MODIFY COLUMN `create_id`  int(11) NULL DEFAULT NULL COMMENT '创建人id' AFTER `org_name`,
MODIFY COLUMN `update_id`  int(11) NULL DEFAULT NULL COMMENT '更新人id' AFTER `create_time`,
ADD COLUMN `content_type`  int(1) NULL COMMENT '内容形式：1-短信内容；2-短信模版' AFTER `intention_tag`,
ADD COLUMN `create_name`  varchar(32) NULL COMMENT '创建人名称' AFTER `create_id`,
ADD COLUMN `update_name`  varchar(32) NULL COMMENT '更新人名称' AFTER `update_id`;


ALTER TABLE sms_task_detail RENAME TO sms_send_detail;
ALTER TABLE `sms_send_detail`
DROP COLUMN `planuuid`,
DROP COLUMN `send_type`,
DROP COLUMN `update_id`,
DROP COLUMN `update_time`,
MODIFY COLUMN `org_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织代码' AFTER `id`,
CHANGE COLUMN `company_name` `org_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称' AFTER `org_code`,
MODIFY COLUMN `tunnel_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通道名称' AFTER `org_name`,
MODIFY COLUMN `phone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码' AFTER `task_name`,
MODIFY COLUMN `sms_content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '短信内容（短信模版/自定义短信内容）' AFTER `phone`,
MODIFY COLUMN `send_time`  datetime NULL DEFAULT NULL COMMENT '发送时间' AFTER `sms_content`,
MODIFY COLUMN `create_id`  int(11) NULL DEFAULT NULL COMMENT '创建人id' AFTER `send_status`,
CHANGE COLUMN `user_name` `create_name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称' AFTER `create_id`,
ADD COLUMN `fail_reason`  varchar(64) NULL COMMENT '发送失败原因' AFTER `send_status`;


ALTER TABLE `sms_task`
DROP COLUMN `sms_template_id`,
DROP COLUMN `file_name`,
DROP COLUMN `company_id`,
MODIFY COLUMN `tunnel_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通道名称' AFTER `task_name`,
MODIFY COLUMN `sms_content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '短信内容（短信模版/自定义短信内容）' AFTER `tunnel_name`,
MODIFY COLUMN `send_type`  int(1) NULL DEFAULT NULL COMMENT '发送方式：1-立即发送；2-定时发送' AFTER `phone_num`,
MODIFY COLUMN `send_status`  int(1) NULL DEFAULT 0 COMMENT '发送状态：0-未发送；1-发送中；2-已发送；3-发送失败' AFTER `send_type`,
CHANGE COLUMN `send_date` `send_time`  varchar(32) NULL DEFAULT NULL COMMENT '发送时间' AFTER `send_status`,
MODIFY COLUMN `auditing_status`  int(1) NULL DEFAULT NULL COMMENT '审核状态：0-未审核；1-已审核（短信内容需审核）' AFTER `send_time`,
MODIFY COLUMN `run_status`  int(1) NULL DEFAULT 1 COMMENT '运行状态：0-一键停止；1-正常运行（定时未发送任务可以一键停止）' AFTER `auditing_status`,
MODIFY COLUMN `org_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织代码' AFTER `run_status`,
CHANGE COLUMN `company_name` `org_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称' AFTER `org_code`,
MODIFY COLUMN `create_id`  int(11) NULL DEFAULT NULL COMMENT '创建人id' AFTER `org_name`,
MODIFY COLUMN `update_id`  int(11) NULL DEFAULT NULL COMMENT '更新人id' AFTER `create_time`,
ADD COLUMN `content_type`  int(1) NULL COMMENT '内容形式：1-短信内容；2-短信模版' AFTER `tunnel_name`,
ADD COLUMN `create_name`  varchar(32) NULL COMMENT '创建人名称' AFTER `create_id`,
ADD COLUMN `update_name`  varchar(32) NULL COMMENT '更新人名称' AFTER `update_id`;


UPDATE sms_platform t SET t.content_type = 1;
UPDATE sms_platform t SET t.content_type = 2 WHERE t.platform_name = '云讯科技';

UPDATE sms_tunnel t SET t.content_type = 1;
UPDATE sms_tunnel t SET t.content_type = 2 WHERE t.platform_name = '云讯科技';

UPDATE sms_config t SET t.content_type = 1;
UPDATE sms_config t SET t.content_type = 2 WHERE t.tunnel_name LIKE '云讯科技%';

UPDATE sms_task t SET t.content_type = 1;
UPDATE sms_task t SET t.content_type = 2 WHERE t.content_type LIKE '云讯科技%';