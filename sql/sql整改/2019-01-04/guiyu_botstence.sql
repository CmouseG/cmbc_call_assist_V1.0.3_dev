use guiyu_botstence;


ALTER TABLE bot_available_template MODIFY COLUMN org_code VARCHAR(8);
ALTER TABLE bot_available_template MODIFY COLUMN id INT AUTO_INCREMENT;


ALTER TABLE bot_publish_sentence_log MODIFY COLUMN temp_name VARCHAR(50);

ALTER TABLE bot_sentence_industry MODIFY COLUMN industry_name VARCHAR(30);



ALTER TABLE bot_sentence_branch MODIFY COLUMN branch_name VARCHAR(30);
ALTER TABLE bot_sentence_branch MODIFY COLUMN template_id VARCHAR(30);
ALTER TABLE bot_sentence_branch MODIFY COLUMN line_name VARCHAR(50);
ALTER TABLE bot_sentence_branch MODIFY COLUMN `type` VARCHAR(10);



ALTER TABLE bot_sentence_intent MODIFY COLUMN `name` VARCHAR(100);
ALTER TABLE bot_sentence_intent MODIFY COLUMN `domain_name` VARCHAR(50);


ALTER TABLE bot_sentence_process MODIFY COLUMN `template_name` VARCHAR(50);
ALTER TABLE bot_sentence_process MODIFY COLUMN `industry` VARCHAR(30);
ALTER TABLE bot_sentence_process MODIFY COLUMN `org_code` VARCHAR(8);
ALTER TABLE bot_sentence_process MODIFY COLUMN `org_name` VARCHAR(64);
ALTER TABLE bot_sentence_process MODIFY COLUMN `user_name` VARCHAR(64);


DROP TABLE IF EXISTS `bot_sentence_sellbot_machine`;
CREATE TABLE `bot_sentence_sellbot_machine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机器编号',
  `ip` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '机器IP',
  `username` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '密码',
  `path` varchar(256) CHARACTER SET latin1 DEFAULT NULL COMMENT '模板路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sellbot机器信息';


ALTER TABLE bot_sentence_template MODIFY COLUMN `template_name` VARCHAR(50);
ALTER TABLE bot_sentence_template MODIFY COLUMN `account_no` VARCHAR(32);
ALTER TABLE bot_sentence_template MODIFY COLUMN `industry_id` INT;
ALTER TABLE bot_sentence_template MODIFY COLUMN `industry_name` VARCHAR(30);


ALTER TABLE bot_sentence_tts_backup DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE bot_sentence_tts_content DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE bot_sentence_tts_param DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE bot_sentence_tts_task DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE `sys_menu`;
DROP TABLE `sys_org_info`;
DROP TABLE `sys_privilege`;
DROP TABLE `sys_role`;
DROP TABLE `sys_user`;
DROP TABLE `sys_user_role`;


ALTER TABLE tab_administrator CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;


ALTER TABLE tab_customer MODIFY COLUMN `code` VARCHAR(32);
ALTER TABLE tab_customer MODIFY COLUMN `serial_number` VARCHAR(128);

ALTER TABLE tab_customer DROP INDEX serial_number;
ALTER TABLE tab_customer ADD INDEX idx_tab_customer_serial_number(serial_number);
ALTER TABLE tab_customer DROP INDEX serial_number_2;
ALTER TABLE tab_customer ADD INDEX idx_tab_customer_serial_number_machine_code(serial_number, machine_code);


ALTER TABLE volice_info MODIFY COLUMN `domain_name` VARCHAR(50);

DROP TABLE user_account_industry_relation;


