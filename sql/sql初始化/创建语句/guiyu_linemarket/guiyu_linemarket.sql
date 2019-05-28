/*
Navicat MySQL Data Transfer

Source Server         : TX-B04
Source Server Version : 50722
Source Host           : 212.64.98.47:3306
Source Database       : guiyu_linemarket

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-03-13 11:40:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sip_line_apply
-- ----------------------------
DROP TABLE IF EXISTS `sip_line_apply`;
CREATE TABLE `sip_line_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sip_line_id` int(11) DEFAULT NULL,
  `agent_line_id` int(11) DEFAULT NULL,
  `up_sip_line_id` int(11) DEFAULT NULL COMMENT '变更sip线路编号(变更时用)',
  `line_name` varchar(30) DEFAULT NULL COMMENT '线路名称',
  `supplier` varchar(30) DEFAULT NULL COMMENT '供应商',
  `overt_area` varchar(30) DEFAULT NULL COMMENT '外显归属地',
  `call_direc` int(11) DEFAULT NULL COMMENT '呼叫方向:1-呼出;2-呼入',
  `max_concurrent_calls` int(11) DEFAULT NULL COMMENT '并发数',
  `templates` varchar(100) DEFAULT NULL COMMENT '话术模板编号',
  `begin_date` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `end_date` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `belong_user` varchar(50) DEFAULT NULL COMMENT '虚拟线路归属人',
  `org_code` varchar(30) NOT NULL COMMENT '虚拟线路归属企业',
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `apply_user` varchar(50) DEFAULT NULL COMMENT '申请人',
  `apply_org_code` varchar(30) NOT NULL COMMENT '申请企业',
  `apply_date` varchar(10) DEFAULT NULL COMMENT '申请日期',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `apply_type` int(11) DEFAULT NULL COMMENT '申请类型:1-新线路;2-业务数据变更;3-线路变更',
  `apply_status` int(11) DEFAULT NULL COMMENT '申请状态:1-申请中,2-审批通过;3-审批拒绝',
  `approve_user` varchar(50) DEFAULT NULL COMMENT '审批人',
  `approve_date` varchar(10) DEFAULT NULL COMMENT '审批日期',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_remark` varchar(128) DEFAULT NULL COMMENT '审批备注',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sip_line_apply_idx1` (`agent_line_id`),
  KEY `sip_line_apply_idx3` (`up_sip_line_id`),
  KEY `sip_line_apply_idx4` (`apply_user`),
  KEY `sip_line_apply_idx5` (`apply_status`),
  KEY `sip_line_apply_idx6` (`approve_user`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='SIP线路申请';

-- ----------------------------
-- Table structure for sip_line_base_info
-- ----------------------------
DROP TABLE IF EXISTS `sip_line_base_info`;
CREATE TABLE `sip_line_base_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(30) DEFAULT NULL COMMENT '线路名称',
  `line_id` int(11) DEFAULT NULL COMMENT '线路编号',
  `supplier` varchar(30) DEFAULT NULL COMMENT '供应商',
  `supplier_type` int DEFAULT NULL comment '是否供应商，0非1是',
  `line_status` int(11) DEFAULT NULL COMMENT '线路状态:0-初始未提交,1-正常;2-到期;3-失效',
  `sip_ip` varchar(15) DEFAULT NULL COMMENT 'sip服务器ip',
  `sip_port` int(11) DEFAULT NULL COMMENT 'sip服务器端口',
  `sip_domain` varchar(20) DEFAULT NULL COMMENT 'sip域',
  `sip_account` varchar(20) DEFAULT NULL COMMENT 'sip账号',
  `sip_psd` varchar(20) DEFAULT NULL COMMENT 'sip密码',
  `codec` varchar(10) DEFAULT NULL COMMENT '编解码',
  `reg_flag` tinyint(1) DEFAULT NULL COMMENT '是否注册-fs是否需注册',
  `caller_num` varchar(20) DEFAULT NULL COMMENT '主叫号码',
  `belong_org_code` varchar(30) DEFAULT NULL COMMENT '分配企业',
  `destination_prefix` varchar(20) DEFAULT NULL COMMENT '被叫前缀',
  `max_concurrent_calls` int(11) DEFAULT NULL COMMENT '并发数',
  `use_concurrent_calls` int(11) DEFAULT NULL COMMENT '已用并发数',
  `call_direc` int(11) DEFAULT NULL COMMENT '呼叫方向:1-呼出;2-呼入',
  `begin_date` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `end_date` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `time_begin` varchar(20) DEFAULT NULL COMMENT '拨打时间开始',
  `time_end` varchar(20) DEFAULT NULL COMMENT '拨打时间结束',
  `overt_area` varchar(30) DEFAULT NULL COMMENT '外显归属地',
  `industrys` varchar(100) DEFAULT NULL COMMENT '行业限制',
  `areas` varchar(100) DEFAULT NULL COMMENT '地区限制',
  `except_areas` varchar(300) DEFAULT NULL COMMENT '地区盲区',
  `contract_univalent` decimal(8,3) DEFAULT NULL COMMENT '合同价',
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `fee_or_not` tinyint(1) null comment '0免费1收费',
  `line_fee_type` int(11) DEFAULT NULL COMMENT '线路计费类型:1-自营线路(扣费),2-客户自备线路(不扣费)',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `sip_share_id` int(11) DEFAULT NULL COMMENT '虚拟共享sip线路编号(根据)',
  `org_code` varchar(30) NOT NULL COMMENT '归属企业',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `belong_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sip_line_base_info_idx1` (`crt_user`),
  KEY `sip_line_base_info_idx2` (`org_code`),
  KEY `sip_line_base_info_idx3` (`line_status`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 COMMENT='代理第三方线路';

-- ----------------------------
-- Table structure for sip_line_exclusive
-- ----------------------------
DROP TABLE IF EXISTS `sip_line_exclusive`;
CREATE TABLE `sip_line_exclusive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sip_line_id` int(11) DEFAULT NULL,
  `apply_id` int(11) DEFAULT NULL COMMENT '申请编号',
  `agent_line_id` int(11) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL COMMENT '线路编号',
  `line_name` varchar(30) DEFAULT NULL COMMENT '线路名称',
  `line_type` int(11) DEFAULT NULL COMMENT '线路类型:1-自营线路;2-代理线路',
  `line_status` int(11) DEFAULT NULL COMMENT '线路状态:1-正常;2-到期;3-失效',
  `supplier` varchar(30) DEFAULT NULL COMMENT '供应商',
  `call_direc` int(11) DEFAULT NULL COMMENT '呼叫方向:1-呼出;2-呼入',
  `max_concurrent_calls` int(11) DEFAULT NULL COMMENT '并发数',
  `begin_date` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `end_date` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `fee_or_not` tinyint(1) null comment '0免费1收费',
  `line_fee_type` int(11) DEFAULT NULL COMMENT '线路计费类型:1-自营线路(扣费),2-客户自备线路(不扣费)',
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `overt_area` varchar(30) DEFAULT NULL COMMENT '外显归属地',
  `industrys` varchar(100) DEFAULT NULL COMMENT '行业限制',
  `templates` varchar(100) DEFAULT NULL COMMENT '模板限制',
  `areas` varchar(100) DEFAULT NULL COMMENT '地区限制',
  `belong_user` varchar(50) DEFAULT NULL COMMENT '归属人',
  `belong_org_code` varchar(30) NOT NULL COMMENT '分配企业',
  `except_areas` varchar(300) DEFAULT NULL COMMENT '地区盲区',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sip_line_exclusive_idx1` (`sip_line_id`),
  KEY `sip_line_exclusive_idx2` (`agent_line_id`),
  KEY `sip_line_exclusive_idx3` (`line_type`),
  KEY `sip_line_exclusive_idx4` (`line_status`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='sip线路(独享)';

-- ----------------------------
-- Table structure for sip_line_share
-- ----------------------------
DROP TABLE IF EXISTS `sip_line_share`;
CREATE TABLE `sip_line_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_status` int(11) DEFAULT NULL COMMENT '线路状态:1-正常;2-到期;3-失效',
  `line_name` varchar(30) DEFAULT NULL COMMENT '线路名称',
  `supplier` varchar(30) DEFAULT NULL COMMENT '供应商',
  `call_direc` int(11) DEFAULT NULL COMMENT '呼叫方向:1-呼出;2-呼入',
  `begin_date` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `end_date` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `overt_area` varchar(30) DEFAULT NULL COMMENT '外显归属地',
  `industrys` varchar(100) DEFAULT NULL COMMENT '行业限制',
  `areas` varchar(100) DEFAULT NULL COMMENT '地区限制',
  `except_areas` varchar(300) DEFAULT NULL COMMENT '地区盲区',
  `apply_num` int(11) DEFAULT NULL COMMENT '申请次数',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `belong_user` varchar(50) DEFAULT NULL COMMENT '归属人',
  `org_code` varchar(30) NOT NULL COMMENT '归属企业',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sip_line_share_idx1` (`call_direc`,`univalent`,`overt_area`,`industrys`),
  KEY `sip_line_share_idx2` (`belong_user`),
  KEY `sip_line_share_idx3` (`org_code`),
  KEY `sip_line_share_idx4` (`line_status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='sip线路(共享)';

-- ----------------------------
-- Table structure for sip_route_item
-- ----------------------------
DROP TABLE IF EXISTS `sip_route_item`;
CREATE TABLE `sip_route_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(10) DEFAULT NULL COMMENT '规则项编号',
  `item_name` varchar(20) DEFAULT NULL COMMENT '规则项名称',
  `seq` int(11) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='sip线路路由规则项';

-- ----------------------------
-- Table structure for sip_route_rule
-- ----------------------------
DROP TABLE IF EXISTS `sip_route_rule`;
CREATE TABLE `sip_route_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `rule_type` int(11) DEFAULT NULL COMMENT '规则类型:1-优先级',
  `rule_content` varchar(1024) DEFAULT NULL COMMENT '规则内容',
  `status` int(11) DEFAULT NULL COMMENT '规则状态:1-启用,2-失效',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sip_route_rule_idx1` (`user_id`),
  KEY `sip_route_rule_idx2` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='线路路由规则';

-- ----------------------------
-- Table structure for voip_gw_info
-- ----------------------------
DROP TABLE IF EXISTS `voip_gw_info`;
CREATE TABLE `voip_gw_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gw_name` varchar(20) NOT NULL COMMENT '网关名称(对应网关配置中的描述信息,用来匹配数据使用)',
  `gw_brand` varchar(20) DEFAULT NULL COMMENT '网关品牌',
  `gw_version` varchar(30) DEFAULT NULL COMMENT '网关型号',
  `company_Id` int(11) DEFAULT NULL COMMENT '网关公司号',
  `dev_id` int(11) DEFAULT NULL COMMENT '网关设备号',
  `port_num` int(11) DEFAULT NULL COMMENT '端口数',
  `gw_status` int(11) DEFAULT NULL COMMENT '1-正常,0-失效',
  `sip_ip` varchar(15) DEFAULT NULL COMMENT '注册服务ip',
  `sip_port` int(11) DEFAULT NULL COMMENT '注册服务端口',
  `line_port` int(11) DEFAULT NULL COMMENT '线路端口(给呼叫中心使用)',
  `start_sip_account` int(11) DEFAULT NULL COMMENT '起始sip账号',
  `start_sip_pwd` int(11) DEFAULT NULL COMMENT '起始sip密码',
  `sip_account_step` int(11) DEFAULT NULL COMMENT 'sip账号步长',
  `sip_pwd_step` int(11) DEFAULT NULL COMMENT 'sip密码步长',
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `reg_type` int(11) DEFAULT NULL COMMENT '语音网关注册类型:1-反向注册(gw-fs);2-正向注册(fs-gw)',
  `gw_reg_status` int(11) DEFAULT NULL COMMENT '语音网关注册状态: 0-初始化,1-确认',
  `gw_ip` varchar(15) DEFAULT NULL COMMENT '网关外网ip',
  `user_id` varchar(50) DEFAULT NULL COMMENT '归属人',
  `org_code` varchar(8) DEFAULT NULL COMMENT '归属企业',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `voip_gw_info_idx1` (`gw_name`),
  KEY `voip_gw_info_idx2` (`org_code`),
  KEY `voip_gw_info_idx3` (`company_Id`),
  KEY `voip_gw_info_idx4` (`dev_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='语音网关配置';

-- ----------------------------
-- Table structure for voip_gw_port
-- ----------------------------
DROP TABLE IF EXISTS `voip_gw_port`;
CREATE TABLE `voip_gw_port` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `port` int(11) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL COMMENT '线路编号',
  `gw_status` int(11) DEFAULT NULL COMMENT '1-正常,0-失效',
  `alias` varchar(30) DEFAULT NULL COMMENT '别名',
  `company_Id` int(11) DEFAULT NULL COMMENT '网关公司号',
  `dev_id` int(11) DEFAULT NULL COMMENT '网关设备号',
  `gw_id` int(11) DEFAULT NULL,
  `sip_account` int(11) DEFAULT NULL,
  `sip_pwd` int(11) DEFAULT NULL,
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `phone_no` varchar(20) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '归属人',
  `org_code` varchar(8) DEFAULT NULL COMMENT '归属企业',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `voip_gw_port_idx1` (`gw_id`),
  KEY `voip_gw_port_idx2` (`port`),
  KEY `voip_gw_port_idx3` (`user_id`),
  KEY `voip_gw_port_idx4` (`org_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='语音网关端口配置';

-- ----------------------------
-- Table structure for voip_gw_port_his
-- ----------------------------
DROP TABLE IF EXISTS `voip_gw_port_his`;
CREATE TABLE `voip_gw_port_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_Id` int(11) DEFAULT NULL COMMENT '网关公司号',
  `dev_id` int(11) DEFAULT NULL COMMENT '网关设备号',
  `port` int(11) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL COMMENT '线路编号',
  `gw_status` int(11) DEFAULT NULL COMMENT '1-正常,0-失效',
  `alias` varchar(30) DEFAULT NULL COMMENT '别名',
  `gw_id` int(11) DEFAULT NULL,
  `sip_account` int(11) DEFAULT NULL,
  `sip_pwd` int(11) DEFAULT NULL,
  `univalent` decimal(8,3) DEFAULT NULL COMMENT '单价(元)',
  `phone_no` varchar(20) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL COMMENT '归属人',
  `org_code` varchar(8) DEFAULT NULL COMMENT '归属企业',
  `crt_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `voip_gw_port_his_idx1` (`gw_id`),
  KEY `voip_gw_port_his_idx2` (`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='语音网关端口配置变更历史';

DROP TABLE IF EXISTS `voip_gw_port_limit`;
create table guiyu_linemarket.voip_gw_port_limit
(
  `id` int auto_increment comment '自增主键'
    primary key,
  `port_id` int      null comment 'voip_gw_port表的id',
  `line_id` int      not null comment '线路id',
  `time_length` int      null comment '时间长度',
  `max_limit` int      null comment '限值',
  `limit_type` int      null comment '1-拨打次数 2-接通次数 3-接通分钟',
  `crt_time` datetime null comment '创建时间',
  `crt_user` int      null comment '创建者id',
  `update_time` datetime null comment '更新时间',
  `update_user` int      null comment '更新者用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拨打限制表';
