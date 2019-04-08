alter table sip_line_base_info
	add fee_or_not tinyint(1) null comment '0免费1收费' after univalent;


alter table sip_line_exclusive
	add fee_or_not tinyint(1) null comment '0免费1收费' after end_date;
