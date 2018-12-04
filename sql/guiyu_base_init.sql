delete from sys_menu;
INSERT INTO `sys_menu` VALUES ('1', '系统管理', null, '/system', '0', null, null, '2018-11-07 14:15:43', '2018-11-23 14:56:04', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('2', '用户管理', null, '/system/user', '11', null, null, '2018-11-07 14:16:20', '2018-11-07 14:16:20', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('3', '机器人中心', null, '/robotCenter', '0', null, null, '2018-11-07 14:32:06', '2018-11-21 16:25:57', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('4', '呼叫中心', null, '/callCenter', '0', null, null, '2018-11-07 14:47:41', '2018-11-07 14:47:41', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('5', '个人中心', null, '/personCenter', '0', null, null, '2018-11-08 09:44:06', '2018-11-20 17:52:39', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('6', '任务中心', null, '/taskCenter', '0', null, null, '2018-11-14 13:09:25', '2018-11-14 13:09:25', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('7', '任务中心', null, '/taskCenter/phonelist', '25', null, null, '2018-11-14 13:10:00', '2018-11-14 13:10:00', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('8', '线路管理', null, '/callCenter/lineInfoList', '16', null, null, '2018-11-14 13:11:30', '2018-11-23 14:54:19', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('9', '通话记录', null, '/callCenter/callHistory', '16', null, null, '2018-11-14 13:12:07', '2018-11-14 13:12:07', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('10', '权限管理', null, '/system/auth', '11', null, null, '2018-11-14 13:14:57', '2018-11-23 14:55:06', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('11', '菜单维护', null, '/system/menu', '11', null, null, '2018-11-14 13:15:14', '2018-11-14 13:15:14', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('12', '话术市场', null, '/botsentence', '0', null, null, '2018-11-14 14:47:02', '2018-11-14 14:47:02', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('13', '话术制作', null, '/botsentence/botsentence_maker', '35', null, null, '2018-11-14 14:47:27', '2018-11-14 14:47:27', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('14', '话术审核', null, '/botsentence/botsentence_approve', '35', null, null, '2018-11-14 14:48:11', '2018-11-14 14:48:11', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('15', '我的模板', null, '/botsentence/botsentence_mytemplate', '35', null, null, '2018-11-14 14:48:41', '2018-11-14 14:48:41', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('16', '修改密码', null, '/personCenter/revisePsw', '18', null, null, '2018-11-20 17:53:55', '2018-11-20 17:53:55', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('17', '机器人管理', null, '/robotCenter/robotManage', '15', null, null, '2018-11-21 16:29:39', '2018-11-21 16:29:39', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('18', '数据字典', null, '/system/dataDictionaries', '11', null, null, '2018-11-22 11:30:54', '2018-11-22 11:30:54', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('19', '个人信息', null, '/personCenter/myselfInfo', '18', null, null, '2018-11-22 17:14:42', '2018-11-22 17:14:42', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('20', '数据获取', null, '/personCenter/getData', '18', null, null, '2018-11-22 17:14:59', '2018-11-22 17:14:59', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('21', '开户', null, '/system/account', '11', null, null, '2018-11-23 10:39:38', '2018-11-23 10:39:38', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('22', '进程管理', null, '/system/processManage', '11', null, null, '2018-11-23 14:57:15', '2018-11-23 14:57:15', '1', '2', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('23', '首页', null, '/', '0', null, null, '2018-11-23 15:07:54', '2018-11-23 15:07:54', '1', '1', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('24', '首页', null, '/home', '64', null, null, '2018-11-23 15:08:04', '2018-11-23 15:08:04', '1', '2', '0', '0', '0');

delete from sys_role;
INSERT INTO `sys_role` VALUES ('1','管理员', now(), now(), '0', '0', '0');

delete from sys_user;
INSERT INTO `sys_user` VALUES ('1','admin', 'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413', '1', '2', '', '', now(), '2018-12-01 19:32:11', '0', null);

delete from sys_role_user;
INSERT INTO `sys_role_user` VALUES ('1','1', '1', now(), now(), '0');


delete from sys_menu_role;
INSERT INTO `sys_menu_role` VALUES ('1', '1', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('2', '2', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('3', '3', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('4', '4', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('5', '5', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('6', '6', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('7', '7', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('8', '8', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('9', '9', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('10', '10', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('11', '11', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('12', '12', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('13', '13', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('14', '14', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('15', '15', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('16', '16', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('17', '17', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('18', '18', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('19', '19', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('20', '20', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('21', '21', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('22', '22', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('23', '23', '1', now(), now(), '0');
INSERT INTO `sys_menu_role` VALUES ('24', '24', '1', now(), now(), '0');



INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_branch', 1, 1, 'BRN', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_domain', 1, 1, 'DOM', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_intent', 1, 1, 'INT', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_label', 1, 1, 'XXX', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_process', 1, 1, 'PRO', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_template', 1, 1, 'TEM', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_tts_backup', 1, 1, 'BAK', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_tts_param', 1, 1, 'PARAM', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('sys_user', 1, 1, 'USER', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('user_account', 1, 1, 'ACC', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('user_account_industry_relation', 1, 1, 'RELA', '20181012');