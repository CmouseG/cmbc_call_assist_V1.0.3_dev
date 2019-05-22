ALTER TABLE dispatch_plan_batch
 ADD COLUMN callback_url VARCHAR(64) COMMENT '批次回调url',
 ADD COLUMN total_num VARCHAR(32) COMMENT '总导入数',
 ADD COLUMN single_callback_url DATETIME COMMENT '单个回调url';