USER guiyu_dispatch;


/*==============================================================*/
/* Table: export_file_record                                    */
/*==============================================================*/
CREATE TABLE export_file_record
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   record_id            VARCHAR(32) COMMENT '记录ID',
   busi_type            VARCHAR(2) COMMENT '业务类型 02-任务中心 03-呼叫中心',
   busi_id              VARCHAR(32) COMMENT '关联唯一标识ID',
   file_type            INT(1) COMMENT '文件类型 1-execl文件  2-音频文件 3-视频文件',
   file_size            VARCHAR(16) COMMENT '文件大小',
   file_original_url    VARCHAR(255) COMMENT '文件原始路径',
   file_generate_url    VARCHAR(255) COMMENT '下载生成目录',
   batch_id             VARCHAR(32) COMMENT '批次号',
   batch_name           VARCHAR(32) COMMENT '批次名称',
   total_num            INT(8) COMMENT '总数',
   success_num          INT(8) COMMENT '成功数量',
   fail_num             INT(8) COMMENT '失败数量',
   STATUS               INT(1) NOT NULL DEFAULT 0 COMMENT '状态 0-进行中 1-完成  2-取消 3-删除',
   user_id              VARCHAR(32) COMMENT '操作员ID',
   user_name            VARCHAR(32) COMMENT '操作员名称',
   org_code             VARCHAR(32) COMMENT '操作员组织CODE',
   create_name          VARCHAR(32) COMMENT '创建者',
   create_time          VARCHAR(32) COMMENT '创建时间',
   add_time             DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   upd_time             DATETIME COMMENT '更新时间',
   del_flag             INT(1) NOT NULL DEFAULT 0 COMMENT '删除标志 0-正常 1-删除',
   PRIMARY KEY (id)
);

ALTER TABLE export_file_record COMMENT '导出文件记录表';

/*==============================================================*/
/* Index: idx_export_file_type                                  */
/*==============================================================*/
CREATE INDEX idx_export_file_type ON export_file_record
(
   busi_type
);

/*==============================================================*/
/* Index: idx_export_file_user                                  */
/*==============================================================*/
CREATE INDEX idx_export_file_user ON export_file_record
(
   user_name
);

/*==============================================================*/
/* Index: idx_export_file_time                                  */
/*==============================================================*/
CREATE INDEX idx_export_file_time ON export_file_record
(
   add_time
);
