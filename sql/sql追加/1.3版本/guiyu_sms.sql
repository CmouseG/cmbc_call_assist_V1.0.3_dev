CREATE DATABASE IF NOT EXISTS guiyu_sms DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use guiyu_sms;

grant all on guiyu_sms.* to sms@'%' identified by 'sms@1234' with grant option; 
grant all privileges on guiyu_sms.* to 'sms'@'%' identified by 'sms@1234' with grant option;

CREATE TABLE `sms_platform` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(128) NOT NULL COMMENT '平台名称',
  `platform_params` varchar(250) NOT NULL COMMENT '配置参数列表',
  `identification` varchar(50) NOT NULL COMMENT '内部标识',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_tunnel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(128) DEFAULT NULL COMMENT '平台名称',
  `tunnel_name` varchar(128) DEFAULT NULL COMMENT '通道名称',
  `platform_config` text COMMENT '平台必须配置参数，json格式',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tunnel_name` varchar(128) DEFAULT NULL COMMENT '通道名称',
  `template_id` varchar(32) DEFAULT NULL COMMENT '话术模版id',
  `template_name` varchar(128) DEFAULT NULL COMMENT '话术模版名称',
  `intention_tag` varchar(64) DEFAULT NULL COMMENT '意向标签',
  `sms_content` text COMMENT '自定义短信内容',
  `sms_template_id` int(11) DEFAULT NULL COMMENT '短信模板id',
  `sms_template_data` varchar(255) DEFAULT NULL COMMENT '短信模板变量',
  `auditing_status` int(1) DEFAULT NULL COMMENT '审核状态：0-待审核；1-已审核',
  `run_status` int(1) DEFAULT NULL COMMENT '运行状态：0-停止；1-启动',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `org_code` varchar(8) DEFAULT NULL COMMENT '组织代码',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(128) DEFAULT NULL COMMENT '平台名称',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `send_status` int(11) DEFAULT NULL COMMENT '发送状态：0-发送失败；1-发送成功',
  `status_code` varchar(64) DEFAULT NULL COMMENT '发送结果状态码',
  `status_msg` varchar(255) DEFAULT NULL COMMENT '发送结果状态描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(64) DEFAULT NULL COMMENT '任务名称',
  `phone_num` int(11) DEFAULT NULL COMMENT '号码数量',
  `send_status` int(1) DEFAULT NULL COMMENT '发送状态：0-未开始；1-进行中；2-已结束',
  `send_type` int(1) DEFAULT NULL COMMENT '发送方式：0-手动发送；1-定时发送',
  `tunnel_name` varchar(128) DEFAULT NULL COMMENT '通道名称',
  `sms_content` text COMMENT '自定义短信内容',
  `sms_template_id` int(11) DEFAULT NULL COMMENT '短信模版id',
  `send_date` datetime DEFAULT NULL COMMENT '发送日期',
  `auditing_status` int(1) DEFAULT NULL COMMENT '审核状态：0-待审核；1-已审核',
  `run_status` int(1) DEFAULT NULL COMMENT '运行状态：0-一键停止；1-正常运行',
  `file_name` varchar(128) DEFAULT NULL COMMENT '上传文件名称',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_task_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) DEFAULT NULL COMMENT '任务名称',
  `phone` varchar(11) DEFAULT NULL COMMENT '客户电话',
  `send_type` int(1) DEFAULT NULL COMMENT '发送方式：0-手动发送；1-定时发送',
  `send_status` int(1) DEFAULT NULL COMMENT '发送状态：0-发送失败；1-发送成功',
  `company_name` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `tunnel_name` varchar(128) DEFAULT NULL COMMENT '平台通道',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `user_name` varchar(32) DEFAULT NULL COMMENT '操作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


