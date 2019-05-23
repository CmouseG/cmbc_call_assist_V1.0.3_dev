ALTER TABLE guiyu_dispatch.file_error_records
	ADD cust_name            VARCHAR(32) COMMENT '客户姓名',
	ADD cust_company         VARCHAR(64) COMMENT '客户所属单位';