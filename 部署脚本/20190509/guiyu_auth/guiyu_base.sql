ALTER TABLE `sys_privilege`
ADD COLUMN `update_flag`  int(1) NULL DEFAULT NULL COMMENT '更新标志：null-有且选中的权限；1-有但未选中的权限' AFTER `org_code`;