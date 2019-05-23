call addCallPlanColumn();

ALTER TABLE call_out_plan
 ADD COLUMN enterprise VARCHAR(64) COMMENT '用户所属企业单位',ADD COLUMN answer_user VARCHAR(32) COMMENT '用户名称',ADD COLUMN import_time DATETIME COMMENT '导入时间';