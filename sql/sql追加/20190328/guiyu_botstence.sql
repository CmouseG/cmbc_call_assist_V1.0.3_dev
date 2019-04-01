ALTER TABLE guiyu_botstence.bot_sentence_grade_rule MODIFY COLUMN remark VARCHAR(256) COMMENT '意向备注';

ALTER TABLE guiyu_botstence.bot_publish_sentence_log ADD (org_code VARCHAR(32));
ALTER TABLE guiyu_botstence.bot_publish_sentence_log ADD (org_name VARCHAR(64));

ALTER TABLE guiyu_botstence.bot_publish_sentence_log MODIFY COLUMN create_name VARCHAR(64) COMMENT '创建人';
