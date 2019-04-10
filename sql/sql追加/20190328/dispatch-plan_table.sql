use guiyu_dispatch;
DROP TABLE IF EXISTS dispatch_plan;        
        
        CREATE TABLE dispatch_plan
        (
           plan_uuid            BIGINT(32) COMMENT '任务UUID;任务全局唯一ID',
           user_id              INT(11) COMMENT '用户ID',
           batch_id             INT(11) COMMENT '批次ID;批次ID',
           phone                VARCHAR(32) NOT NULL COMMENT '手机号',
           attach               VARCHAR(255) COMMENT '附加参数，备注',
           params               VARCHAR(255) COMMENT '参数',
           status_plan          INT(1) COMMENT '计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
           status_sync          INT(1) COMMENT '同步状态;0未同步1已同步',
           recall               INT(1) COMMENT '重播;0不重播非0表示重播次数',
           recall_params        VARCHAR(32) COMMENT '重播条件;重播次数json格式',
           robot                VARCHAR(32) COMMENT '呼叫机器人',
           line                 INT(11) COMMENT '线路ID集合',
           result               VARCHAR(32) COMMENT '呼出结果',
           call_agent           VARCHAR(32) COMMENT '转人工坐席号',
           clean                INT(1) COMMENT '当日清除;当日夜间清除未完成计划',
           call_data            INT(11) COMMENT '外呼日期',
           call_hour            VARCHAR(64) NOT NULL COMMENT '拨打时间',
           gmt_create           DATETIME COMMENT '创建时间',
           gmt_modified         DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
           is_tts               INT(11) COMMENT '是否tts合成',
           replay_type          INT(11) COMMENT '重播类型 0一般任务 1 重播任务',
           is_del               INT(11) NOT NULL DEFAULT 0 COMMENT '0否1是',
           username             VARCHAR(32) COMMENT '用户名称',
           line_name            VARCHAR(32) COMMENT '线路名称',
           robot_name           VARCHAR(32) COMMENT 'robot名称',
           batch_name           VARCHAR(32) COMMENT '批次名称',
           flag                 VARCHAR(32) COMMENT '洗号码标识',
           org_code             VARCHAR(64) NOT NULL COMMENT '组织编码',
           org_id               INT(32) COMMENT '组织ID',
           city_name            VARCHAR(32) COMMENT '城市名',
           city_code            VARCHAR(32) COMMENT '城市code',
           line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关',
           PRIMARY KEY (plan_uuid)
        )
        ENGINE=INNODB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='计划任务表'