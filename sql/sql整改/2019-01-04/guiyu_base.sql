#切换到guiyu_base
use guiyu_base;
#修改表receive_message
alter table receive_message change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table receive_message change status status int not null default 0 comment '读取状态0未读1已读';
alter table receive_message change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表send_message
alter table send_message change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table send_message change title title varchar(64) default null comment '标题';
alter table send_message change type type int not null default 0 comment '消息类型0公共消息';
alter table send_message change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_app
alter table sys_app change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_app change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
alter table sys_app change name name varchar(32) default null comment 'app名称';
#修改表sys_dict
alter table sys_dict change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
alter table sys_dict change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_dict change pid pid int DEFAULT NULL comment '父级id';
#修改表sys_file
ALTER TABLE sys_file DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
alter table sys_file drop column id;
alter table sys_file add id bigint(20) AUTO_INCREMENT NOT NULL,add primary key(id) comment '主键';
alter table sys_file modify id bigint(20) AUTO_INCREMENT NOT NULL first;
alter table sys_file change busi_id busi_id varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci default null comment '业务id';
alter table sys_file change sys_code sys_code varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci default null comment '系统服务编号,如用户中心01';
alter table sys_file change crt_user crt_user bigint DEFAULT NULL comment '创建人id';
alter table sys_file change lst_update_user lst_update_user bigint DEFAULT NULL comment '更新人id';
alter table sys_file change file_name file_name varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci comment '文件名';
alter table sys_file change busi_type busi_type char(20) CHARACTER SET utf8 COLLATE utf8_general_ci comment '业务类型';
alter table sys_file change file_type file_type char(20) CHARACTER SET utf8 COLLATE utf8_general_ci comment '文件类型';
alter table sys_file change file_size file_size double(10,4) DEFAULT NULL comment '文件大小' ;
alter table sys_file change sk_url sk_url varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci comment '文件上传后下载地址';
alter table sys_file change sk_thumb_image_url sk_thumb_image_url varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci comment '图片缩略图上传后下载地址';
alter table sys_file change crt_time crt_time datetime default null comment '创建时间';
alter table sys_file change lst_update_time lst_update_time datetime default null comment '更新时间';
#修改表sys_menu
alter table sys_menu change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_menu change name name varchar(64) default null comment 'app名称';
alter table sys_menu change pid pid int DEFAULT NULL comment '父级id';
alter table sys_menu change is_show is_show int default 0 comment '是否展示0是1否';
alter table sys_menu change type type int not null comment '资源类型1菜单2按钮';
alter table sys_menu change level level int not null comment '资源层级';
alter table sys_menu change appid appid int default 0 comment 'appid关联sys_app主键';
alter table sys_menu change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_menu_role
alter table sys_menu_role change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_menu_role change role_id role_id int NOT NULL comment '角色id';
alter table sys_menu_role change menu_id menu_id int NOT NULL comment '菜单id';
alter table sys_menu_role change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_organization
alter table sys_organization change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_organization change name name varchar(64) default null comment '组织名称';
alter table sys_organization change code code varchar(8) default null comment '组织编码';
alter table sys_organization change sub_code sub_code varchar(8) default null comment '子编码';
alter table sys_organization change robot robot int default 0 comment '机器人数量';
#老数据存在字符串的脏数据，统一调整为0，后续从页面重新配置线路
update sys_organization set line = 0;
alter table sys_organization change line line int default 0 comment '线路数量';
alter table sys_organization add botstence int default 0 comment '话术数量';
alter table sys_organization change type type int not null comment '组织类型1代理商2企业';
alter table sys_organization change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
alter table sys_organization change open open int not null comment '0未开户1已开户';
#修改表sys_process
alter table sys_process change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_process change ip ip varchar(32) default null comment '进程ip';
alter table sys_process change name name varchar(16) default null comment '进程名称';
alter table sys_process change type type int default null comment '进程类型0:TTS,1:SELLBOT,99:AGENT';
alter table sys_process change status status int default null comment '状态0UP,1DOWN,2BUSY,3MISSING';
#修改表sys_process_task
alter table sys_process_task change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_process_task change process_id process_id int DEFAULT NULL comment '进程id';
alter table sys_process_task change ip ip varchar(32) default null comment '进程ip';
alter table sys_process_task change cmd_type cmd_type int default null comment '命令类型-1UNKNOWN1STOP2HEALTH3RESTART4REGISTER5RESTORE_MODEL6UNREGISTER7AGENTREGISTER8PULBLISH_SELLBOT_BOTSTENCE9PULBLISH_FREESWITCH_BOTSTENCE10START';
alter table sys_process_task change result result int default null comment '命令执行结果';
alter table sys_process_task change process_key process_key varchar(64) default null comment '扩展字段，type为TTS时存模型';
alter table sys_process_task change exec_status exec_status int default null comment '执行状态:0执行结束1执行中';
alter table sys_process_task change req_key req_key varchar(32) default null comment '请求key';
#修改表sys_role
alter table sys_role change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_role change name name varchar(32) default null comment '角色名称';
alter table sys_role change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_role_user
alter table sys_role_user change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_role_user change role_id role_id int NOT NULL comment '角色id';
alter table sys_role_user change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_user
alter table sys_user change id id bigint NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_user change username username varchar(64) NOT NULL comment '用户名';
alter table sys_user change password password varchar(255) NOT NULL comment '密码';
alter table sys_user change status status int default 1 comment '1正常2冻结';
alter table sys_user change push_type push_type int default null comment '1表示平台推送，2表示主动获取';
alter table sys_user change org_code org_code varchar(8) NOT NULL comment '企业code';
alter table sys_user change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_user_action
alter table sys_user_action change id id int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id';
alter table sys_user_action change OperaType opera_type varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型';
alter table sys_user_action change OperaTarget opera_target varchar(32) NOT NULL DEFAULT '' COMMENT '操作类型';
#修改表sys_user_ext
alter table sys_user_ext change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_user_ext change name name varchar(64) NOT NULL comment '姓名';
alter table sys_user_ext change mobile mobile varchar(13) NOT NULL comment '联系电话';
alter table sys_user_ext change del_flag del_flag int not null default 0 comment '删除标识0正常1删除';
#修改表sys_user_login_record
alter table sys_user_login_record change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table sys_user_login_record change login_times login_times int NOT NULL comment '账号登录次数';
alter table sys_user_login_record change last_login_ip last_login_ip varchar(32) DEFAULT NULL comment '最后登录IP';
#修改表user_api
alter table user_api change id id int NOT NULL AUTO_INCREMENT comment '主键';
alter table user_api change username username varchar(64) NOT NULL comment '用户名';
alter table user_api change api_url api_url varchar(255) NOT NULL comment '第三方api地址';

