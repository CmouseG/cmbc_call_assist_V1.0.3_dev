CREATE TABLE
    bot_sentence_share_auth
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        template_name VARCHAR(256) COMMENT '话术模板名称',
        type VARCHAR(8) COMMENT '00-所有用户 00-指定机构',
        available_org VARCHAR(256) COMMENT '可见机构',
        nick_name VARCHAR(32) COMMENT '分享者昵称',
        share_count INT COMMENT '使用次数',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        lst_update_time DATETIME COMMENT '最后修改时间',
        lst_update_user VARCHAR(32) COMMENT '最后修改人',
        shared TINYINT(1) COMMENT '是否分享',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术分享权限';
	
	
	
CREATE TABLE
    bot_sentence_share_history
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT '主键编号',
        process_id VARCHAR(32) COMMENT '话术流程编号',
        template_id VARCHAR(32) COMMENT '话术模板编号',
        template_name VARCHAR(256) COMMENT '话术模板名称',
        nick_name VARCHAR(32) COMMENT '分享者昵称',
        crt_time DATETIME COMMENT '创建时间',
        crt_user VARCHAR(32) COMMENT '创建人',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话术分享使用记录';