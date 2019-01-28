CREATE TABLE
    bot_sentence_grade
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT '编号',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        type VARCHAR(32) COMMENT '类型',
        stat_order VARCHAR(512) COMMENT '意向排序',
        init_stat VARCHAR(32) COMMENT '默认意向',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
CREATE TABLE
    bot_sentence_grade_rule
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'id',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        rule_no VARCHAR(32) COMMENT '规则编号',
        intent_name VARCHAR(32) COMMENT '意向标签',
        type VARCHAR(32) COMMENT '类型',
        value1 VARCHAR(32) COMMENT '变量1',
        value2 VARCHAR(32) COMMENT '变量2',
        value3 VARCHAR(32) COMMENT '变量3',
        value4 VARCHAR(32) COMMENT '变量4',
        value5 VARCHAR(32) COMMENT '变量5',
        show_seq INT COMMENT '显示顺序',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        remark VARCHAR(32) COMMENT '意向备注',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意向规则';
	
	
CREATE TABLE
    bot_sentence_keyword_template
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT '编号',
        industry_id VARCHAR(32) COMMENT '行业编号',
        industry_name VARCHAR(64) COMMENT '行业名称',
        intent_name VARCHAR(32) COMMENT '意图名称',
        keywords text COMMENT '关键字',
        type VARCHAR(8) COMMENT '00-通用  01-行业',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关键词库';
	

CREATE TABLE
    bot_sentence_log
    (
        ID INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
        operator VARCHAR(64) COMMENT '操作',
        param VARCHAR(512) COMMENT '请求参数',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        PRIMARY KEY (ID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';
	
	
CREATE TABLE
    bot_sentence_options
    (
        options_id VARCHAR(32) NOT NULL COMMENT 'ID',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        check_sim TINYINT(1) COMMENT '检查相似词',
        cur_domain_prior TINYINT(1) COMMENT '优先匹配当前域',
        use_not_match_logic TINYINT(1) COMMENT '使用未匹配逻辑',
        not_match_solution VARCHAR(64) COMMENT '未匹配解决方案',
        use_endfiles_list TINYINT(1) COMMENT '使用结束列表',
        trade VARCHAR(64) COMMENT '模板类型',
        tempname VARCHAR(64) COMMENT '模板名称',
        non_interruptable_start TINYINT(1) COMMENT '是否开启不打断配置',
        non_interruptable VARCHAR(256) COMMENT '不打断配置',
        silence_wait_start TINYINT(1) COMMENT '是否开启静音时间与次数',
        silence_wait_secs INT COMMENT '静音超时时间',
        silence_wait_time INT COMMENT '允许静音超时次数',
        silence_as_empty TINYINT(1) COMMENT '是否开启静音转空规则',
        user_define_match_order TINYINT(1) COMMENT '是否开启用户自定义匹配优先级',
        grub_start TINYINT(1) COMMENT '是否启用回答引导',
        positive_interruptable TINYINT(1) COMMENT '所有关键词中是否增加肯定POSITIVE域的关键词',
        interruptable_domain_start TINYINT(1) COMMENT '开场白可被解释开场白打断',
        global_interruptable_domains_start TINYINT(1) COMMENT '是否开启开场白可被全局打断域',
        global_interruptable_domains VARCHAR(256) COMMENT '开场白可被全局打断域',
        ignore_but_domains_start TINYINT(1) COMMENT '是否开启不忽略的域',
        ignore_user_sentence_start TINYINT(1) COMMENT '是否开启无论说什么话，都继续往下走',
        ignore_but_negative_start TINYINT(1) COMMENT '是否开启除拒绝之外，都继续往下走',
        asr_engine VARCHAR(64) COMMENT '识别引擎',
        multi_keyword_all TINYINT(1) COMMENT '是否使用多关键词',
        not_match_less4_to_start TINYINT(1) COMMENT '是否开启每个域未匹配走向配置',
        not_match_less_num INT COMMENT '字数配置',
        not_match_to_start TINYINT(1) COMMENT '是否开启未匹配到任何关键词的走向',
        no_words_to_start TINYINT(1) COMMENT '是否开启无声音的走向',
        interruption_config_start TINYINT(1) COMMENT '是否开启新打断规则',
        interrupt_words_num INT COMMENT '允许打断的字数',
        interrupt_min_interval INT COMMENT '两次打断之间最小间隔的秒数',
        voice VARCHAR(256) COMMENT '和voice机器人在打断后重复上句时需要加上的前缀语',
        special_limit_start TINYINT(1) COMMENT '是否开启重复次数是否受限',
        special_limit INT COMMENT '限制special被问的次数',
        survey_start TINYINT(1) COMMENT '是否启用回访规则',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        PRIMARY KEY (options_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术options数据';
	
	
CREATE TABLE
    bot_sentence_options_level
    (
        options_level_id VARCHAR(32) NOT NULL COMMENT '编号',
        process_id VARCHAR(32) NOT NULL COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        domain VARCHAR(32) COMMENT 'domain',
        level1 VARCHAR(32) COMMENT '优先级1',
        level2 VARCHAR(32) COMMENT '优先级2',
        level3 VARCHAR(32) COMMENT '优先级3',
        level4 VARCHAR(32) COMMENT '优先级4',
        level5 VARCHAR(32) COMMENT '优先级5',
        level6 VARCHAR(32) COMMENT '优先级6',
        level7 VARCHAR(32) COMMENT '优先级7',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        PRIMARY KEY (options_level_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程优先级';
	
	
CREATE TABLE
    bot_sentence_survey
    (
        survey_id VARCHAR(32) NOT NULL COMMENT '回访规则ID',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        domain VARCHAR(32) COMMENT 'domain',
        type VARCHAR(32) COMMENT 'INTENT            
SENTENCE            
KEY',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        PRIMARY KEY (survey_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程回访规则';
	

CREATE TABLE
    bot_sentence_survey_intent
    (
        survey_intent_id VARCHAR(32) NOT NULL COMMENT '回访规则ID',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        domain VARCHAR(32) COMMENT 'domain',
        intent_name VARCHAR(32) COMMENT '意图名称',
        intent_keys VARCHAR(15000) COMMENT '意图关键词',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        type VARCHAR(32) COMMENT '00-默认  01-自定义',
        PRIMARY KEY (survey_intent_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术流程回访规则意图信息';
	
	
CREATE TABLE
    bot_sentence_trade
    (
        id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
        industry_id VARCHAR(256) COMMENT '行业编号',
        industry_name VARCHAR(256) COMMENT '行业名称',
        parent_id VARCHAR(256) COMMENT '上级行业编号',
        parent_name VARCHAR(256) COMMENT '上级行业名称',
        level INT,
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行业分类';
	
	
CREATE TABLE
    bot_sentence_wechat_user_info
    (
        user_id VARCHAR(32) NOT NULL COMMENT '用户id',
        account_no VARCHAR(32) COMMENT '账号',
        password VARCHAR(512) COMMENT '密码',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        PRIMARY KEY (user_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配音小程序用户信息';
	
	
CREATE TABLE
    user_account_trade_relation
    (
        relation_id bigint NOT NULL AUTO_INCREMENT COMMENT '关系ID',
        account_no VARCHAR(32) COMMENT '用户账号',
        account_name VARCHAR(32) COMMENT '账号名称',
        industry_id VARCHAR(1024) COMMENT '行业ID',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        PRIMARY KEY (relation_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号行业权限表';
	

