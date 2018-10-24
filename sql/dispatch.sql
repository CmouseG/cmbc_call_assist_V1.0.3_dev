


DROP TABLE IF EXISTS `dispatch_log`;
CREATE TABLE `dispatch_log`  (
  `id` int(11) NOT NULL COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块',
  `action` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '动作',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作日志' ROW_FORMAT = Dynamic;




DROP TABLE IF EXISTS `dispatch_plan`;
CREATE TABLE `dispatch_plan`  (
  `id` int(11) NOT NULL COMMENT 'Id',
  `plan_uuid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务UUID;任务全局唯一ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `batch_id` int(11) NOT NULL COMMENT '批次ID;批次ID',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `attach` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附加参数;可以作为第三方系统的唯一标识',
  `params` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量;多个变量用|分隔',
  `status_plan` tinyint(1) NOT NULL COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
  `status_sync` tinyint(1) NOT NULL COMMENT '同步状态;0未同步1已同步',
  `recall` tinyint(1) NOT NULL COMMENT '重播;0不重播非0表示重播次数',
  `recall_params` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '重播条件;重播次数json格式',
  `robot` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '呼叫机器人',
  `call_agent` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '转人工坐席号',
  `clean` tinyint(1) NOT NULL COMMENT '当日清除;当日夜间清除未完成计划',
  `call_data` int(11) NOT NULL COMMENT '外呼日期',
  `call_hour` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '外呼时间',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计划任务' ROW_FORMAT = Dynamic;




DROP TABLE IF EXISTS `dispatch_plan_batch`;
CREATE TABLE `dispatch_plan_batch`  (
  `id` int(11) NOT NULL COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID;用户ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '批次号;批次号',
  `status_show` tinyint(1) NOT NULL COMMENT '是否显示;显示状态1显示0隐藏',
  `status_notify` tinyint(1) NOT NULL COMMENT '通知状态;通知状态1等待2失败3成功',
  `times` tinyint(1) NOT NULL COMMENT '通知次数;通知次数',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间;创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '更新时间;更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计划批次信息' ROW_FORMAT = Dynamic;
