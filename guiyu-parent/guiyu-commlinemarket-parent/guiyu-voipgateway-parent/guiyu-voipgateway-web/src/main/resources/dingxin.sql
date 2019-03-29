create table tbl_gw
(
  uuid             int(10) auto_increment
    primary key,
  rec_status       int(3)   null comment '0-null,1-add(web),2-delete(web),3-update(web),11-add(server),12-delete(server),13-update(server)',
  ne_uuid          int(10)  not null comment '隶属的网元ID',
  alias            char(31) null,
  domain_uuid      int(10)  not null comment '冗余字段, 必需',
  default_grp_uuid int(10)  null comment '默认卡组, 用于本地SIM卡',
  virtual_sim_flag int(3)   null comment '虚拟SIM卡标识，是否使用虚拟的IMSI标识',
  gwp_num          int(5)   null comment 'gw_port数目',
  work_mode        int(3)   null comment '"0 - local sim card;
1 - remote sim box;2 - remote sim server"',
  constraint idx1
    unique (ne_uuid)
);

create index idx2
  on tbl_gw (domain_uuid);

create table tbl_gwp
(
  uuid             int(10) auto_increment
    primary key,
  rec_status       int(3)            null comment '0-null,1-add(web),2-delete(web),3-update(web),11-add(server),12-delete(server),13-update(server)',
  port_uuid        int(10)           not null comment '隶属的port端口',
  alias            char(31)          null,
  bkp_uuid         int(10) default 0 null comment '关联的sim_bank端口, 动态绑定',
  local_sim_uuid   int(10)           null comment '关联的本地SIM卡标识, 动态绑定',
  domain_uuid      int(10)           not null comment '冗余字段, 必需',
  local_imei       char(31)          null comment '端口本地IMEI',
  current_imei     char(31)          null comment '当前IMEI，使用本地或者动态IMEI',
  local_imsi       char(16)          null comment '本地SIM卡的IMSI',
  work_mode        int(3)            null comment '"端口工作模式:
0 - 本地卡;
1 - 远程卡;"',
  mod_type         int(3)            null comment '"端口无线模块类型:
0 - GSM;
1 - CDMA;"',
  mod_status       int(3)            null comment '端口无线模块状态',
  mod_signal_val   int(3)            null comment '无线模块的信号强度',
  mod_signal_level int(3)            null comment '无线模块的信号级别: 0~5',
  mod_ber_val      int(3)            null comment '无线模块的误码率',
  mod_error_count  int(10)           null comment '无线模块的错误统计, 包括注册失败, 控制超时等',
  last_bind_time   datetime          null comment '端口最后一次绑定SIM卡的时间',
  last_used_time   datetime          null comment '端口最后一次使用SIM卡的时间',
  cur_call_status  int(3)            null comment '呼叫工作标志: 0 - IDLE; 1 - BUSY;',
  cur_sms_status   int(3)            null comment '短信工作标志: 0 - IDLE; 1 - BUSY;',
  cur_ussd_status  int(3)            null,
  cur_call_sn      int(10)           null comment '当前呼叫流水号',
  cur_sms_sn       int(10)           null comment '当前短信流水号',
  cur_ussd_sn      int(10)           null comment '当前USSD流水号',
  round_trip_delay int(5)            null comment 'gateway端口和sim_bank端口的双向时延, 毫秒',
  packet_all       int(10)           null comment 'gateway端口和sim_bank端口的报文总数',
  packet_retries   int(10)           null comment 'gateway端口和sim_bank端口的重传次数',
  packet_timeout   int(10)           null comment 'gateway端口和sim_bank端口的无响应次数',
  constraint idx1
    unique (port_uuid)
);

create index idx2
  on tbl_gwp (domain_uuid);


create table tbl_ne
(
  uuid                  int(10) auto_increment
    primary key,
  rec_status            int(3)  default 0     not null comment '0-null,1-add(web),2-delete(web),3-update(web),11-add(server),12-delete(server),13-update(server)',
  product_sn            binary(8)             not null comment '网元设备序列号',
  alias                 char(31)              null,
  admin_status          int(3)  default 1     null,
  opr_status            int(3)  default 0     null,
  run_status            int(3)  default 0     null,
  action_status         int(3)                null,
  action_result         int(10)               null,
  alm_status_bits       int(10)               null,
  domain_uuid           int(10)               not null comment '隶属的域名, 冗余字段',
  site_uuid             int(10)               not null comment '隶属的站点',
  policy_uuid           int(10) default 0     null comment '配置的策略标识',
  vendor_id             int(3)                null comment '设备/OEM厂商名称',
  product_id            int(3)                null comment '设备类型, 两个字节标识, dinstar统一编号',
  product_name          char(31)              null comment '设备/OEM产品名称, against ne_product_type',
  package_version       char(31)              null,
  package_build_time    datetime              null comment 'hardware version: 1.1',
  detail_ver            varchar(512)          null comment '附加的详细版本信息',
  sip_agent             char(31)              null comment 'SIP Agent名称',
  sip_owner             char(31)              null comment 'SIP Owner名称',
  cli_prompt            char(31)              null comment '设备命令行提示符',
  dhcp_default          int(3)                null comment '"设备默认的DHCP设置:
0 - disabled;
1 - enabled;"',
  ip_type               int(3)                null comment '"设备默认的IP地址类型:
0 - ipv4;
1 - ipv6;"',
  ip_addr               char(31)              null comment '设备默认的IP地址',
  mac_addr_begin        binary(6)             null comment '设备默认MAC起始地址, 出厂分配',
  mac_addr_num          int(5)                null comment '设备默认MAC地址数目',
  made_factory          char(31)              null,
  made_site             char(31)              null,
  made_date             char(31)              null,
  test_site             char(31)              null,
  test_date             char(31)              null,
  password              char(31)              null comment '挑战认证, 不传送明文',
  encrypt_type          int(3)                null comment '"网元设备的通信加密类型:
0 - 不加密;
1 - 默认加密方式;"',
  outer_ip_addr         char(63) charset ucs2 null comment '网元设备的公网IP',
  inner_ip_addr         char(31)              null comment '网元设备的内网IP',
  create_time           datetime              null comment '网元设备的创建时间',
  update_time           datetime              null,
  last_reg_time         datetime              null comment '网元设备的最后一次注册时间',
  reg_fail_count        int(10)               null comment '网元设备的注册认证失败次数, 注册成功则清零',
  upgrade_type          int(3)  default 1     null comment '"网元设备的升级类型:
0 - 禁止升级;
1 - 升级到测试版本;
2 - 升级到发布版本;"',
  upgrade_force_flag    int(3)                null comment '强制升级标志: 0 - 正常升级; 1 - 强制升级;',
  target_software_ver   char(31)              null comment '目标软件版本',
  upgrade_status        int(3)                null comment '设备升级状态:
0 - NA;
1 - 等待升级;
2 - 正在升级;',
  last_upgrade_result   int(10)               null comment '设备升级结果:
0-NULL; 1-升级成功;
 2-升级失败;',
  last_upgrade_time     datetime              null comment '网元设备的上次升级时间',
  port_total_count      int(10)               null,
  port_work_count       int(10)               null,
  detail_desc           char(255)             null comment '设备详细描述',
  next_ne_alarm_sn      int(10)               null comment '设备上报的下一条日志分配的期望索引，0-2^31-1',
  syslog_status         int(3)                null comment 'syslog状态,0,1,2',
  log_sys_uuid          int(10)               null comment '选择syslog服务器所在的系统',
  syslog_begin_date     datetime              null comment '设备配置的syslog数据开始时间',
  syslog_end_date       datetime              null comment '设备配置的syslog数据过期时间',
  syslog_debug_level    int(3)  default 7     null comment 'syslog日志级别[-1-7]',
  cdr_log_flag          int(3)                null comment 'syslog是否发送CDR 记录标记,',
  signal_log_flag       int(3)                null comment 'syslog是否发送signal log 标记',
  media_log_flag        int(3)                null comment 'syslog是否发送media log 标记',
  system_log_flag       int(3)                null comment 'syslog是否发送system log 标记',
  mng_log_flag          int(3)                null comment 'syslog是否发送management log 标记',
  cdr_log_falg          int(3)                null comment 'DELETED!!!',
  sipsrv_lock_flag      int(3)                null comment 'sip服务器的锁定设置标志',
  primary_sip_server    char(127)             null comment '设备的主sip服务器',
  primary_sipsrv_port   int(5)                null comment '主sip服务器的端口',
  secondary_sip_server  char(127)             null comment '设备的备sip服务器',
  secondary_sipsrv_port int(5)                null comment '备sip服务器的端口',
  ntp_status            int(3)                null comment '设备ntp同步功能是否开启，0=SYN, 1=NOSYN',
  auto_reboot_flag      int(3)                null comment '自动重启设置状态,TRUE/FALSE',
  switch_chip_status    int(3)                null comment '交换网状态(IDEL/FAIL)',
  cur_cpu               int(5)                null comment '当前CPU占用率',
  avg_cpu_5             int(5)                null comment '5sCPU平均占用率',
  avg_cpu_60            int(5)                null comment '60sCPU平均占用率',
  avg_cpu_600           int(5)                null comment '600sCPU平均占用率',
  free_mem              int(10)               null comment 'DELETE!!!空闲内存Byte',
  total_mem             int(10)               null comment 'DELETE!!!全部内存Byte',
  mem_usage             int(5)                null comment 'DELETE!!!内存使用率',
  dev_time              datetime              null comment '设备日期时间',
  alarm_flag            int(10) default 0     null comment 'ne及其port子对象都无告警为0，否则为1',
  loc_mcc               int(5)                null,
  loc_mnc               int(5)                null,
  loc_lac               int(5)                null,
  loc_cell_id           int(10)               null,
  loc_status            int(3)  default 0     null comment '0 - NULL; 1 - REQ; 2 - OK;',
  loc_lat               float(10, 6)          null,
  loc_lng               float(10, 6)          null,
  upgrade_flag          int(3)                null comment 'DELETED!',
  time_chip_status      int(3)                null comment 'DELETE!!!',
  alloc_mem             int(3)                null comment 'DELETE!!!',
  alarm_short_period    int(10)               null comment 'DELETE!!!',
  alarm_long_period     int(10)               null comment 'DELETE!!!',
  status_short_period   int(10)               null comment 'DELETE!!!',
  status_long_period    int(10)               null comment 'DELETE!!!',
  statics_period        int(10)               null comment 'DELETE!!!',
  lock_status           int(3)  default 0     null comment '锁状态',
  mem_aos_usage         int(3)  default 0     null comment 'aos系统内存使用率',
  mem_linux_usage       int(3)  default 0     null comment 'linux系统使用率',
  nat_mode              int(3)  default 0     null comment 'nat穿透模式',
  nat_status            int(3)  default 0     null comment 'nat穿透状态',
  store_usage           int(3)  default 0     null comment '存储空间使用率',
  eth_link_mode         int(3)  default 0     null comment '网口连接模式桥接/路由',
  port_type1            int(10)               null comment '端口类型',
  port_count1           int(5)                null comment '类型1的端口数量',
  port_type2            int(10)               null comment '端口类型',
  port_count2           int(5)                null comment '类型2的端口数量',
  port_type3            int(10)               null comment '端口类型',
  port_count3           int(5)                null comment '类型3的端口数量',
  port_type4            int(10)               null comment '端口类型',
  port_count4           int(5)                null comment '类型4的端口数量',
  port_type5            int(10)               null comment '端口类型',
  port_count5           int(5)                null comment '类型5的端口数量',
  port_type6            int(10)               null comment '端口类型',
  port_count6           int(5)                null comment '类型6的端口数量',
  port_type7            int(10)               null comment '端口类型',
  port_count7           int(5)                null comment '类型7的端口数量',
  port_type8            int(10)               null comment '端口类型',
  port_count8           int(5)                null comment '类型8的端口数量',
  constraint idx1
    unique (product_sn)
);

create index idx2
  on tbl_ne (domain_uuid);

create table tbl_port
(
  uuid             int(10) auto_increment
    primary key,
  rec_status       int(3)           null comment '0-null,1-add(web),2-delete(web),3-update(web),11-add(server),12-delete(server),13-update(server)',
  ne_uuid          int(10)          not null comment '隶属的ne网元',
  shelf_no         int(3)           not null comment '机框号, 盒式设备缺省为0',
  slot_no          int(3)           not null comment '槽位号, 盒式设备缺省为0',
  port_no          int(5)           not null comment 'rename, 端口号',
  type             int(10)          not null comment '端口类型: 0x2401 - gateway port; 0x2402 - sim-bank port;',
  alias            char(31)         null,
  admin_status     int(3) default 1 null,
  opr_status       int(3) default 0 null,
  run_status       int(3) default 0 null,
  action_status    int(3)           null,
  action_result    int(10)          null,
  alm_status_bits  int(10)          null,
  domain_uuid      int(10)          not null comment '冗余字段, 必需',
  port_policy_uuid int(10)          null comment 'port policy_uuid for DWG',
  port_grp_uuid    int(10)          null comment 'port grp_uuid for SIMBANK',
  lock_port_uuid   int(10)          null comment '指定SIMBANK的BKP端口, 用于静态绑定',
  lock_sim_uuid    int(10)          null comment '指定SIM卡的uuid, 用于静态绑定',
  lock_bkp_uuid    int(10)          null comment 'DELETED!!!',
  constraint idx1
    unique (ne_uuid, shelf_no, slot_no, port_no, type)
);

create index idx2
  on tbl_port (domain_uuid, ne_uuid, type);


create table tbl_sim
(
  uuid                 int(10) auto_increment
    primary key,
  rec_status           int(3)            null comment '0-null,1-add(web),2-delete(web),3-update(web),11-add(server),12-delete(server),13-update(server)',
  imsi                 char(31)          not null comment '卡标识',
  alias                char(31)          null,
  admin_status         int(3)  default 1 null,
  opr_status           int(3)  default 0 null comment '"disable with force flag --- ""force_forbidden""
check --- ""balance check"""',
  run_status           int(3)  default 0 null,
  action_status        int(3)            null,
  action_result        int(10)           null,
  alm_status_bits      int(10)           null,
  domain_uuid          int(10)           not null comment '冗余字段, 必需',
  grp_uuid             int(10)           not null comment '隶属的卡组ID',
  promotion_grp_uuid   int(10)           null comment '隶属的套餐管理卡组',
  orig_zone_uuid       int(10)           null,
  last_site_uuid       int(10) default 0 null comment '最后一次关联的站点ID, 自动识别绑定, 根据对应的gwport',
  next_site_uuid       int(10)           null,
  bkp_uuid             int(10) default 0 null comment '当前关联的sim_bank端口ID, 自动识别绑定',
  local_gwp_uuid       int(10)           null comment '当前关联的DWG端口ID, 自动识别绑定, 用于本地SIM卡',
  lock_gwp_uuid        int(10)           null comment '当前指定绑定的GWP标识，主机刷新字段',
  icc_id               char(31)          null comment 'SIM卡上电后设备自动获取,icc是运营商定义信息',
  bind_imei            char(31)          null comment 'SIM卡绑定一个模拟的IMEI, 根据gwport决定是否启用',
  pin1_code            char(31)          null comment 'SIM卡访问密钥, PIN1',
  pin2_code            char(31)          null comment 'SIM卡访问密钥, PIN2',
  puk1_code            char(31)          null comment 'SIM卡访问密钥, PUK1',
  puk2_code            char(31)          null comment 'SIM卡访问密钥, PUK2',
  operator             char(31)          null comment 'SIM卡对应的移动运营商名称',
  mobile               char(31)          null comment 'SIM卡当前的移动号码，允许人工配置',
  sim_number           char(31)          null comment 'SIM卡上报的移动号码',
  smsc                 char(31)          null,
  money_type           int(5)            null comment '币种',
  prepaid_fee          decimal           null comment '初始化预充值金额，多次充值多次累加',
  total_cost           decimal           null comment '累计消费金额',
  last_balance         float(10, 2)      null comment '上次查询余额',
  cur_balance          float(10, 2)      null comment '当前余额',
  left_call_time       int(10)           null comment '剩余分钟数',
  prom_call_time       int(10)           null comment '套餐呼叫分钟数',
  last_group_time      datetime          null comment 'SIM卡归属卡组的起始时间',
  last_load_time       datetime          null comment 'SIM卡被sim_bank加载的最近一次时间',
  create_time          datetime          null comment 'SIM卡创建时间',
  last_bind_time       datetime          null comment 'SIM卡被gateway端口绑定的最近一次时间',
  last_used_time       datetime          null comment 'SIM卡被gateway端口使用的最近一次时间',
  last_prom_time       datetime          null comment 'SIM卡上次套餐申请时间',
  last_balance_time    datetime          null comment 'SIM卡上次余额刷新时间',
  deactive_reason      int(10)           null comment 'SIM卡去激活原因码',
  last_deactive_reason int(10)           null comment '上一次SIM卡去激活原因码',
  blocked_flag         int(3)            null comment '"SIM卡锁卡标志:
0 - normal, 正常;
1 - locked_once, 当次锁卡, 本次余额用完, 禁止调度;
2 - locked_day, 当天锁卡, 当天余额用完, 禁止调度;
3 - locked_month, 当月锁卡, 当月余额用完, 禁止调度;
4 - locked_all, 完全锁卡, 全部余额用完, 提醒充值; "',
  low_balance_flag     int(3)            null comment '"SIM卡余额偏低标志:
0 - 正常;
1 - 余额偏低;"',
  no_balance_flag      int(3)            null comment '"SIM卡余额不足标志:
0 - 正常;
1 - 余额不足;"',
  promotion_status     int(3)            null comment 'Promotion Apply Status',
  promotion_count      int(3)            null comment '套餐申请次数',
  promotion_time       datetime          null comment 'Promotion Update Time',
  promotion_report     char(63)          null comment 'Promotion Report',
  hbm_acd_short_count  int(3)            null comment 'ACD SHORT失败次数',
  hbm_acd_fail_count   int(3)            null comment 'ACD FAIL失败次数',
  hbm_acd_sms_count    int(3)            null comment 'ACDR 异常SMS监视',
  hbm_sms_fail_count   int(3)            null comment 'SMS测试失败次数',
  hbm_call_fail_count  int(3)            null comment 'CALL测试失败次数',
  hbm_dtmf_fail_count  int(3)            null comment 'DTMF测试失败次数',
  hbm_reg_fail_count   int(3)            null comment 'SIM卡连续注册失败次数',
  sim_recharged_flag   int(3)            null comment 'SIM已经充值标志',
  paid_list_uuid       int(10)           null comment 'SIM充值信息标识',
  local_sim_flag       int(3)            null comment 'DWG本地SIM卡标识',
  detail_desc          char(31)          null comment 'SIM卡Mark信息',
  set_cur_balance      float(10, 3)      null comment '设置当前余额',
  constraint idx1
    unique (domain_uuid, imsi)
);