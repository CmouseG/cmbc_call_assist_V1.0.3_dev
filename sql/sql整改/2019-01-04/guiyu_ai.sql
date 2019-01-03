use guiyu_ai;
ALTER TABLE `tts_model` DROP COLUMN `create_by`;
ALTER TABLE `tts_model` DROP COLUMN `update_by`;
ALTER TABLE `tts_model` MODIFY `status` INT(1) NOT NULL DEFAULT 0 COMMENT '状态：0启动1停用';
ALTER TABLE `tts_model` MODIFY `tts_ip` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'tts服务器ip';
ALTER TABLE `tts_model` MODIFY `create_time` DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `tts_model` MODIFY `update_time` DATETIME DEFAULT NULL COMMENT '更新时间';

ALTER TABLE `tts_result` DROP COLUMN `create_by`;
ALTER TABLE `tts_result` DROP COLUMN `update_by`;
ALTER TABLE `tts_result` MODIFY `bus_id` VARCHAR(64) DEFAULT NULL COMMENT '业务id';
ALTER TABLE `tts_result` MODIFY `tts_ip` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'tts服务器ip';
ALTER TABLE `tts_result` MODIFY `del_flag` INT(1) DEFAULT 0 COMMENT '删除标识：0正常1删除';
ALTER TABLE `tts_result` MODIFY `create_time` DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `tts_result` MODIFY `update_time` DATETIME DEFAULT NULL COMMENT '更新时间';

ALTER TABLE `tts_status` MODIFY `bus_id` VARCHAR(64) DEFAULT NULL COMMENT '业务id';
ALTER TABLE `tts_status` MODIFY `model` VARCHAR(20) DEFAULT NULL COMMENT '模型';
ALTER TABLE `tts_status` MODIFY `jump_flag` INT(1) DEFAULT 0 COMMENT '任务优先处理标识：0未优先，1已优先';
ALTER TABLE `tts_status` MODIFY `status` INT(1) DEFAULT NULL COMMENT '处理状态：0未处理1处理中2已完成';
ALTER TABLE `tts_status` MODIFY `create_time` DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `tts_status` MODIFY `update_time` DATETIME DEFAULT NULL COMMENT '更新时间';