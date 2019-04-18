
use guiyu_tts;

ALTER TABLE `tts_result`
DROP COLUMN `tts_ip`,
DROP COLUMN `tts_port`,
DROP COLUMN `update_time`,
DROP COLUMN `del_flag`,
MODIFY COLUMN `model`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模型' AFTER `bus_id`,
MODIFY COLUMN `content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '待转换文本内容' AFTER `model`,
MODIFY COLUMN `audio_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输出音频文件url' AFTER `content`;


ALTER TABLE tts_status RENAME TO tts_task;
ALTER TABLE `tts_task`
DROP COLUMN `update_time`,
DROP COLUMN `jump_flag`,
MODIFY COLUMN `model`  varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模型' AFTER `bus_id`,
MODIFY COLUMN `status`  int(1) NULL DEFAULT NULL COMMENT '任务状态：0-未处理；1-处理中；2-已完成；3-处理失败' AFTER `model`,
MODIFY COLUMN `text_count`  int(8) NULL DEFAULT 0 COMMENT '文本数量' AFTER `status`;




