alter table sip_line_base_info
	add fee_or_not tinyint(1) null comment '0免费1收费' after univalent;


alter table sip_line_exclusive
	add fee_or_not tinyint(1) null comment '0免费1收费' after end_date;


update sip_line_exclusive set fee_or_not = true where line_fee_type=1;
update sip_line_base_info set fee_or_not = true where line_fee_type=1;

update sip_line_exclusive set fee_or_not = false where line_fee_type=2;
update sip_line_base_info set fee_or_not = false where line_fee_type=2;
