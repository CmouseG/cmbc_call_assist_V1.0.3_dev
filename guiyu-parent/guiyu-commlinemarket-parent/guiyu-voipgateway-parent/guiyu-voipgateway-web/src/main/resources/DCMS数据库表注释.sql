-- 告警内容表
-- 表名：warncontenttbl
`WarnContentId` int(11) NOT NULL auto_increment	-- 告警内容ID
`WarnContent` varchar(128) NOT NULL	-- 告警内容中文版
`WarnContentEn` varchar(128) NOT NULL	-- 告警内容英文版
-- 默认告警内容信息
(WarnContent,WarnContentEn)
("设备服务异常重启","SMGSvr Reboot Abnormal"),
("端口异常","Port Abnormal"),
("注册失败","Registration Failed"),
("设备网络断开","Network Disconnected"),
("CPU过高","High CPU Utilization"),
("内存占用过高","High Memory Occupancy"),
("接通率过低","Low Connection Rate"),
("并发呼叫过高","High Rate of Concurrent Call"),
("设备系统异常重启","System Reboot Abnormal"),
("CPU温度过高","High CPU Temperature");

-- 日志类型表
-- 表名：logtypetbl
`LogTypeId` int(11) NOT NULL auto_increment	-- 日志类型ID
`LogTypeName` varchar(32) NOT NULL	-- 日志类型中文名
`LogTypeNameEn` varchar(32) NOT NULL	-- 日志类型英文名
-- 默认日志类型信息
(LogTypeName,LogTypeNameEn)
("升级","Upgrade"),
("配置修改","Configuration Modification"),
("告警","Alarm"),
("操作","Operate");

-- 日志级别表
-- 表名：logleveltbl
`LogLevelId` int(11) NOT NULL auto_increment	-- 日志级别ID
`LogLevelName` varchar(32) NOT NULL	-- 日志级别中文名
`LogLevelNameEn` varchar(32) NOT NULL	-- 日志级别英文名
-- 默认日志级别信息
(LogLevelName,LogLevelNameEn)
("致命","Fatal"),
("严重","Critical"),
("警告","Warning"),
("信息","Info"),
("调试","Debug");

-- 工作状态信息表
-- 表名：workstatustbl
`WorkStatusId` int(11) NOT NULL auto_increment	-- 工作状态ID
`WorkStatusName` varchar(32) NOT NULL	-- 工作状态中文名
`WorkStatusNameEn` varchar(32) NOT NULL	-- 工作状态英文名
-- 默认工作状态信息
(WorkStatusName,WorkStatusNameEn)
("可用","Available"),
("不可用","Unavailable"),
("停用","Stopped"),
("断线","Disconnected"),
("启用","Enabled"),
("锁定","Locked"),
("正常","Normal"),
("不同步","Asynchronous"),
("远端阻断","Remote Blocked"),
("闭塞","Blocked"),
("其它","Others"),
("在线","Online"),
("离线","Offline");

-- 注册状态信息表
-- 表名：regstatustbl
`RegStatusId` int(11) NOT NULL auto_increment	-- 注册状态ID
`RegStatusName` varchar(32) NOT NULL	-- 注册状态中文名
`RegStatusNameEn` varchar(32) NOT NULL	-- 注册状态英文名
-- 默认注册状态信息
(RegStatusName,RegStatusNameEn)
("注册成功","Registered"),
("注册失败","Registration Failed"),
("注册中","Registering"),
("未注册","Unregistered");

-- 协议类型表
-- 表名：protocoltypetbl
`ProtocolTypeId` int(11) NOT NULL auto_increment	-- 协议类型ID
`ProtocolTypeName` varchar(32) NOT NULL	-- 协议名称
-- 默认协议信息
(ProtocolTypeName)
("SIP"),
("SS1"),
("ISDN"),
("Ss7");

-- 端口类型表
-- 表名：porttypetbl
`PortTypeId` int(11) NOT NULL auto_increment	-- 端口类型ID
`PortTypeName` varchar(32) NOT NULL	-- 端口类型名称
-- 默认端口信息
(PortTypeName)
("FXO"),
("FXS"),
("E1"),
("T1"),
("GSM"),
("CDMA"),
("WCDMA"),
("WCDMA-A"),
("WCDMA-T");

-- 设备类型表
-- 表名：devtypetbl
`DevTypeId` int(11) NOT NULL auto_increment	-- 设备类型ID
`DevTypeName` varchar(32) NOT NULL	-- 设备类型名
`Devcategorycode` varchar(32) NOT NULL	-- 类型代码
`DevTypecode` varchar(32) NOT NULL	-- 型号代码
-- 默认设备类型
(DevTypeName,Devcategorycode,DevTypecode)
("SMG1032","0x01A4","0x0007"),
("SMG1016","0x01A4","0x0107"),
("SMG1008","0x01A4","0x0207"),
("SMG1032A2","0x01A4","0x1007"),
("SMG1032A2+","0x01A4","0x1307"),
("SMG1016A2","0x01A4","0x1107"),
("SMG1008A2","0x01A4","0x1207"),
("SMG1032A4","0x01A4","0x2007"),
("SMG1032A4-32O","0x01A4","0x2107"),
("SMG1032A4-16S16O","0x01A4","0x2207"),
("SMG1032A4-16S","0x01A4","0x2307"),
("SMG1032A4-16O","0x01A4","0x2407"),
("SMG1032A4-8S8O","0x01A4","0x2507"),
("SMG1032A4-8S","0x01A4","0x2607"),
("SMG1032A4-8O","0x01A4","0x2707"),
("SMG1032A4-4S4O","0x01A4","0x2807"),
("SMG1004B-4S","0x02A4","0x3007"),
("SMG1004B-4O","0x02A4","0x3107"),
("SMG1004B-2S2O","0x02A4","0x3207"),
("SMG1008B-8S","0x02A4","0x2607"),
("SMG1008B-8O","0x02A4","0x2707"),
("SMG1008B-4S4O","0x02A4","0x2807"),
("SMG1016B4-16S","0x02A4","0x2307"),
("SMG1016B4-16O","0x02A4","0x2407"),
("SMG1016B4-8S8O","0x02A4","0x2507"),
("SMG1032B4-32S","0x02A4","0x2007"),
("SMG1032B4-32O","0x02A4","0x2107"),
("SMG1032B4-16S16O","0x02A4","0x2207"),
("SMG1032B4-24S8O","0x02A4","0x2907"),
("SMG2030","2120","1"),
("SMG2060","2120","2"),
("SMG2120","2120","4"),
("SMG3008","3016","8"),
("SMG3016","3016","16"),
("SMG3064","3064","64"),
("SMG4004-4G","0x02A4","0x5001"),
("SMG4008-8G","0x02A4","0x4001"),
("SMG4016-16G","0x02A4","0x4301"),
("SMG4032-32G","0x02A4","0x4601"),
("UMG4004-4G","0x02A4","0x6307"),
("UMG4008-8G","0x02A4","0x6007"),
("SMG4004-4W","0x02A4","0x5101"),
("SMG4008-8W","0x02A4","0x4101"),
("SMG4016-16W","0x02A4","0x4401"),
("SMG4032-32W","0x02A4","0x4701"),
("UMG4008-8W","0x02A4","0x6107"),
("UMG4004-4W","0x02A4","0x6407"),
("SMG4004-4C","0x02A4","0x5201"),
("SMG4008-8C","0x02A4","0x4201"),
("SMG4016-16C","0x02A4","0x4501"),
("SMG4032-32C","0x02A4","0x4801"),
("UMG4008-8C","0x02A4","0x6207"),
("UMG4004-4C","0x02A4","0x6507");

-- 节点表
-- 表名：nodetbl
`NodeId` int(11) NOT NULL auto_increment	-- 节点ID
`NodeTag` varchar(64) NOT NULL	-- 节点标识
`CompanyId` int(11) NOT NULL	-- 公司ID
`NodeName` varchar(64) NOT NULL	-- 节点名称
`FirstFloorNodeNum` int(11) NOT NULL default '0'	-- 第一层子节点数(即当前节点包含多少个子节点)
`Description` varchar(128) NOT NULL default ''	-- 描述信息
`PolicyGroupId` int(11) NOT NULL default '1'	-- 对应策略组信息

-- 公司表，注册时填写的公司名决定 (很多表都关联公司ID，用户判断信息是属于那个注册用户的)
--表名：companytbl
`CompanyId` int(11) NOT NULL auto_increment	-- 公司ID，递增，每注册一个用户，增加1，安装完成后的第一个用户认为是超级管理员
`CompanyIdName` varchar(128) NOT NULL	-- 公司名
`AuthorCode` varchar(64) NOT NULL default ''	-- 授权码

-- 把动态创建的表放在后面注释
-- # 每十个客户一张表 告警设备表
-- 表名：(公司ID/10)_warndevtbl
`WarnId` int(11) NOT NULL auto_increment	-- 告警ID
`WarningStartTime` datetime NOT NULL	-- 告警开始时间
`WarnContentId` int(11) NOT NULL	-- 告警内容ID
`ParentDevId` int(11) NOT NULL	-- 告警从属设备ID
`ShouldWarning` tinyint(1) NOT NULL default '0'	-- 是否推送告警
`WarningLastTime` bigint(20) NOT NULL	-- 告警持续时间
`SendWarningTimes` int(11) NOT NULL default '0'	-- 告警发送次数
`CompanyId` int(11) NOT NULL	-- 所属公司ID
`WarningInfo` bigint(20) NOT NULL default '0'	-- 告警信息
`NodeId` varchar(64) NOT NULL default ''	-- 节点标识
`WarningRegInfo` varchar(1024) NOT NULL default ''	-- 注册失败告警内容

-- #每十个客户一张表 日志表
-- 表名：(公司ID/10)_logtbl
`LogId` int(11) NOT NULL auto_increment	-- 日志ID
`RecordTime` datetime NOT NULL	-- 日志记录时间
`ParentDevId` int(11) NOT NULL	-- 记录日志所属设备(设备操作日志，告警才有，系统设置日志没有)
`OperateUserName` varchar(32) NOT NULL	-- 操作用户(告警没有)
`LogLevelId` int(11) NOT NULL	-- 日志级别
`LogTypeId` int(11) NOT NULL	-- 日志类型
`LogContent` varchar(512) NOT NULL	-- 日志内容中文版
`CompanyId` int(11) NOT NULL	-- 日志所属公司ID
`LogContentEn` varchar(512) NOT NULL	-- 日志内容英文版
`SeqNum` varchar(32) NOT NULL	-- 记录日志所属设备对应序列号
`RemoteIp` varchar(32) NOT NULL	-- 操作用户对应的远端IP(目前暂时没有记录，为以后扩展使用)

-- #每个客户一张表 端口信息表
-- 表名：(公司ID)_porttbl
`Id` int(11) NOT NULL auto_increment	-- 端口表索引ID
`PortId` int(11) NOT NULL	--  端口序号
`PortTypeId` int(11) NOT NULL default '-1'	-- 端口类型ID(对应端口类型表)
`ParentDevId` int(11) NOT NULL	-- 所属设备ID(对应端口类型表)
`RegStatusId` int(11) NOT NULL default '-1'	-- 注册状态ID(对应注册状态信息表)
`WorkStatusId` int(11) NOT NULL default '-1'	-- 工作状态ID(对应工作状态表)
`ProtocolTypeId` int(11) NOT NULL default '-1'	-- 协议类型ID(对应协议类型表)
`LoadType` int(11) NOT NULL default '-1'	-- 负荷(数字网关才有)
`CompanyId` int(11) NOT NULL	-- 所属公司ID
`NodeId` varchar(64) NOT NULL default ''	-- 设备所属节点标识
`PortNumber` varchar(128) default ''	-- 端口号码
`RegStatusCode` int(11) NOT NULL default '-1'	-- 注册状态码
`ConnectionStatus` int(11) NOT NULL default '0'	-- 基站连接状态(无线网关使用)
`PhoneNumber` varchar(64) default ''	-- 手机号码(无线网关使用)

-- #每个客户一张表 SIP中继表 (SIP中继，注册服务器信息)
-- 表名：(公司ID)_siptrunktbl
`Id` int(11) NOT NULL auto_increment	--  SIP中继表索引ID
`SipTrunkId` int(11) NOT NULL	-- SIP中继/注册服务器ID
`ParentDevId` int(11) NOT NULL	-- 所属设备ID
`CompanyId` int(11) NOT NULL	-- 所属公司ID
`RemoteIpAddr` varchar(32) NOT NULL default ''	-- 远端IP地址
`RemotePort` int(11) NOT NULL default '-1'	-- 远端端口
`RegStatusId` int(11) NOT NULL default '-1'	-- 注册状态
`WorkStatusId` int(11) NOT NULL default '-1'	-- 工作状态
`LoadType` int(11) NOT NULL default '-1'	-- 负荷
`NodeId` varchar(64) NOT NULL default ''	-- 设备所属节点标识
`UsingNum` int(11) NOT NULL default '0'	-- 资源使用数量
`ResourceNum` int(11) NOT NULL default '0'	-- 资源总数

-- #每十个客户一张表 设备信息表
-- 表名：(公司ID/10)_devtbl
`DevId` int(11) NOT NULL auto_increment	-- 设备ID
`SeqNum` varchar(32) NOT NULL	-- 设备序列号
`CompanyId` int(11) NOT NULL	-- 设备所属公司ID
`DevName` varchar(32) NOT NULL default ''	-- 设备名称(设备类型表-设备类型名)
`DevType` varchar(32) NOT NULL	-- 设备类型(设备类型表-类型代码)
`DevVersion` varchar(32) NOT NULL	-- 设备型号(设备类型表-型号代码)
`SoftwareVersion` varchar(32) NOT NULL	-- 设备当前软件版本号
`CpldVersion` varchar(32) NOT NULL	-- 设备当前CPLD版本号
`UBOOTVersion` varchar(32) NOT NULL	-- 设备当前UBoot版本号
`KernelVersion` varchar(64) NOT NULL	-- 设备当前内核版本号
`CpuVersion` varchar(64) NOT NULL default ''	-- 设备CPU型号
`CpuBasicFreq` int(11) NOT NULL default '-1'	-- 设备CPU主频
`TotalMem` int(11) NOT NULL default '-1'	-- 设备内存大小
`TotalFlash` int(11) NOT NULL default '-1'	-- 设备Flash大小
`Eth0Status` int(11) NOT NULL default '-1'	-- 设备Eth0连接状态
`Eth0IpAddr` varchar(32) NOT NULL default ''	-- 设备Eth0的IP地址
`Eth1Status` int(11) NOT NULL default '-1'	-- 设备Eth1连接状态
`Eth1IpAddr` varchar(32) NOT NULL default ''	-- 设备Eth1的IP地址
`MACAddr1` varchar(32) NOT NULL default ''	-- 设备Eth0 MAC地址
`DNS1` varchar(32) NOT NULL default ''	-- 设备Eth0 DNS信息
`MASK1` varchar(32) NOT NULL default ''	-- 设备Eth0 子网掩码
`NETMODE1` varchar(32) NOT NULL default ''	-- 设备Eth0 工作模式
`NETINFO1` varchar(128) NOT NULL default ''	-- 设备Eth0 收发包状态
`MACAddr2` varchar(32) NOT NULL default ''	-- 设备Eth1 MAC地址
`DNS2` varchar(32) NOT NULL default ''	-- 设备Eth1 DNS信息
`MASK2` varchar(32) NOT NULL default ''	-- 设备Eth1 子网掩码
`NETMODE2` varchar(32) NOT NULL default ''	-- 设备Eth1 工作模式
`NETINFO2` varchar(128) NOT NULL default ''	-- 设备Eth1 收发包状态
`IPAddr` varchar(32) NOT NULL	-- 设备IP地址
`NodeId` varchar(64) NOT NULL default ''	-- 设备所属节点标识
`IdleChNum` int(11) NOT NULL default '-1'	-- 设备空闲通道数
`ChUseNum` int(11) NOT NULL default '-1'	-- 设备使用通道数
`WorkStatusId` int(11) NOT NULL default '-1'	-- 设备工作状态
`BeManage` tinyint(1) NOT NULL default '1'	-- 设备是否受管理
-- 以下几个字段为升级策略中的信息(目前没有用到)
`AutoUpLatestVersion` tinyint(1) NOT NULL default '0'	-- 是否升级到最新版本
`AllowStartUptime1` time NOT NULL default '00:00:00'	-- 允许升级时间段1开始时间
`AllowEndUptime1` time NOT NULL default '08:00:00'	-- 允许升级时间段1结束时间
`AllowStartUptime2` time NOT NULL default '11:30:00'	-- 允许升级时间段2开始时间
`AllowEndUptime2` time NOT NULL default '12:30:00'	-- 允许升级时间段2结束时间
`AllowStartUptime3` time NOT NULL default '18:00:00'	-- 允许升级时间段3开始时间
`AllowEndUptime3` time NOT NULL default '23:59:00'	-- 允许升级时间段3结束时间
-- 升级策略字段结束
`BeLocked` tinyint(1) NOT NULL default '0'	-- 设备是否锁定(设备锁定状态)
`LoadSvrId` int(11) NOT NULL	-- 设备所挂载的服务器ID
`PublicIpAddr` varchar(32) NOT NULL default ''	-- 设备对应外网IP
`ManagePort` int(11) NOT NULL default '-1'	-- 设备SNMP管理对应外网端口
`Description` varchar(128) NOT NULL default ''	-- 设备描述
`ReadSnmpCode` varchar(32) NOT NULL	-- SNMP读共同体
`WriteSnmpCode` varchar(32) NOT NULL	-- SNMP写共同体
`PortNum` int(11) NOT NULL default '0'	-- 设备端口数量
`SipTrunkNum` int(11) NOT NULL default '0'	-- 设备SIP中继/注册服务器数量
`SnmpVersion` int(11) NOT NULL default '2'	-- SNMP版本
`SnmpSecurityName` varchar(32) NOT NULL default ''	-- SNMP账户
`SnmpSecurityLevel` int(11) NOT NULL default '0'	-- SNMP安全级别
`SnmpAuthPassword` varchar(32) NOT NULL default ''	-- SNMP认证密码
`SnmpPrivPassword` varchar(32) NOT NULL default ''	-- SNMP加密密码
`SnmpListenPort` int(11) NOT NULL default '161'	-- SNMP监听端口
`BeEnable` tinyint(1) NOT NULL default '1'	-- 设备是否启用
`LoadType` int(11) NOT NULL default '0'	-- 网关负荷
`DevConnectTime` datetime NOT NULL	-- 设备首次连接时间
`WorkStatusChangeTime` datetime NOT NULL	-- 设备工作状态变更时间
`LastInfoUpdateTime` datetime NOT NULL	-- 设备信息最后一次更新时间
`BeBackupTiming` tinyint(1) NOT NULL default '0'	-- 是否启用自动备份配置功能
`BackupDay` int(11) NOT NULL default '30'	-- 自动备份周期对应天
`BackupHour` int(11) NOT NULL default '0'	-- 自动备份周期对应小时
`BeEnableNewLock` tinyint(1) NOT NULL default '0'	-- 设备是否使用新版锁定
`UpgradeResult` int(11) default '0'	-- 升级结果
`UpgradePercent` int(11) default '0'	-- 升级进度
`UpgradeSpeed` varchar(32) default '0 B/S'	-- 软件升级包传输速度
`UpgradeFailReason` varchar(64) default ''	-- 升级失败原因

-- #每十个客户一张表 实时设备信息表
-- 表名：(公司ID/10)_realtimedevtbl
`DevId` int(11) NOT NULL auto_increment	-- 设备ID
`SeqNum` varchar(32) NOT NULL	-- 设备序列号
`CompanyId` int(11) NOT NULL	-- 设备所属公司ID
`CpuUsage` int(11) NOT NULL default '-1'	-- 设备CPU使用率
`CpuTemperature` int(11) NOT NULL default '-1'	-- 设备CPU温度
`MemUsage` int(11) NOT NULL default '-1'	-- 设备内存使用率
`FlashUsage` int(11) NOT NULL default '-1'	-- 设备Flash使用率
`temperature` int(11) NOT NULL default '-1'	-- 设备温度(目前暂时没有使用)
`SurvivalTime` int(11) NOT NULL default '-1'	-- 设备运行时间
`OnLineTime` int(11) NOT NULL default '-1'	-- 设备在线时间
`DevName` varchar(32) NOT NULL default ''	-- 设备名称
`NodeId` varchar(64) NOT NULL default ''	-- 设备所属节点标识
`RTPLossPacketRate` varchar(64) NOT NULL default '0.00'	-- 设备丢包率
`Eth0TotalRcvPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 接收总包数
`Eth0RcvErrPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 接收错包数
`Eth0RcvLostPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 接收丢包数
`Eth0RcvRate` int(11) NOT NULL default '0'	-- 设备Eth0 接收丢包率
`Eth0TotalSndPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 发送总包数
`Eth0SndErrPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 发送错报数
`Eth0SndLostPackNum` int(11) NOT NULL default '0'	-- 设备Eth0 发送丢包数
`Eth0SndRate` int(11) NOT NULL default '0'	-- 设备Eth0 发送丢包率
`Eth1TotalRcvPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 接收总包数
`Eth1RcvErrPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 接收错包数
`Eth1RcvLostPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 接收丢包数
`Eth1RcvRate` int(11) NOT NULL default '0'	-- 设备Eth1 接收丢包率
`Eth1TotalSndPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 发送总包数
`Eth1SndErrPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 发送错报数
`Eth1SndLostPackNum` int(11) NOT NULL default '0'	-- 设备Eth1 发送丢包数
`Eth1SndRate` int(11) NOT NULL default '0'	-- 设备Eth1 发送丢包率

-- #每个客户一张表 话务统计表
-- 表名：(公司ID)_callworktbl
`CallWorkId` int(11) NOT NULL auto_increment	-- 话务ID
`CalcDateTime` datetime NOT NULL	-- 记录时间
`ParentDevId` int(11) NOT NULL	-- 所属设备ID
`CallTimes` int(11) NOT NULL	-- 呼叫时长
`CallDoneTimes` int(11) NOT NULL	-- 接通次数
`CallLastTime` int(11) NOT NULL	-- 通话总时长
`CompanyId` int(11) NOT NULL	-- 所属公司ID



