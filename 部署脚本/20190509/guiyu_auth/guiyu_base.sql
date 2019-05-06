ALTER TABLE `sys_privilege`
ADD COLUMN `update_flag`  int(1) NULL DEFAULT NULL COMMENT '更新标志：区别更新权限人是否为自己' AFTER `org_code`;