alter table sip_line_base_info
	add non_local_prefix varchar(20) null comment '外显归属地前缀' after belong_org_code;