drop table  if exists  bot_sentence_deploy ;
CREATE TABLE
    bot_sentence_deploy
    (
        id bigint NOT NULL AUTO_INCREMENT comment '主键id',
        job_id VARCHAR(256) comment '发布任务ID',
        sub_job_id VARCHAR(256) comment '发布子任务ID',
        process_id VARCHAR(256) comment '话术流程编号',
        template_id VARCHAR(32) comment '话术模板编号',
        status VARCHAR(32) comment '子任务部署状态',
        crt_time DATETIME comment '创建时间',
        crt_user VARCHAR(32) comment '创建人',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;