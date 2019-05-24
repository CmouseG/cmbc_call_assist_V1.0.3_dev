ALTER TABLE dispatch_plan_batch
 ADD COLUMN callback_url VARCHAR(200) COMMENT '批次回调url',
 ADD COLUMN total_num int(11) COMMENT '总导入数',
 ADD COLUMN single_callback_url VARCHAR(200) COMMENT '单个回调url';