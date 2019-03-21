 
use guiyu_callcenter;


 
ALTER TABLE `call_out_plan` DROP COLUMN originate_cmd;

ALTER TABLE `call_out_plan_0` DROP COLUMN originate_cmd;

ALTER TABLE `call_out_plan_1` DROP COLUMN originate_cmd;
 
ALTER TABLE `call_out_plan` ADD COLUMN params VARCHAR(32) COMMENT '变量参数';

ALTER TABLE `call_out_plan_0` ADD COLUMN params VARCHAR(32) COMMENT '变量参数';

ALTER TABLE `call_out_plan_1` ADD COLUMN params VARCHAR(32) COMMENT '变量参数';