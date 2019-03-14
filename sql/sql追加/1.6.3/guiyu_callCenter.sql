

use guiyu_callcenter;
 

ALTER TABLE `call_out_plan` ADD COLUMN intervened TINYINT(1) DEFAULT 0 COMMENT '是否已介入';
ALTER TABLE `call_out_plan_0` ADD COLUMN intervened TINYINT(1) DEFAULT 0 COMMENT '是否已介入';
ALTER TABLE `call_out_plan_1` ADD COLUMN intervened TINYINT(1) DEFAULT 0 COMMENT '是否已介入';



UPDATE  call_out_plan_0 SET  call_state = 8 WHERE  call_state=5 AND create_time < DATE_SUB(NOW(),INTERVAL 20 MINUTE);
UPDATE  call_out_plan_1 SET  call_state = 8 WHERE  call_state=5 AND create_time < DATE_SUB(NOW(),INTERVAL 20 MINUTE);
