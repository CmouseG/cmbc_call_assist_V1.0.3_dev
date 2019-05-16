update sip_line_base_info set univalent = contract_univalent where id in (select id where contract_univalent != 0 and univalent=0 and fee_or_not=1);

update sip_line_base_info set univalent =0.03 where id=19;

update sip_line_exclusive set univalent=0.1	 where id=388 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=390 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=391 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=392 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=393 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=394 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=395 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=396 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=397 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=398 and univalent=0;
update sip_line_exclusive set univalent=0.12 where id=399 and univalent=0;
update sip_line_exclusive set univalent=0.1	 where id=400 and univalent=0;
update sip_line_exclusive set univalent=0.1	 where id=401 and univalent=0;
update sip_line_exclusive set univalent=0.1	 where id=402 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=403 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=404 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=405 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=406 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=407 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=408 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=409 and univalent=0;
update sip_line_exclusive set univalent=0.08 where id=410 and univalent=0;
update sip_line_exclusive set univalent=0.15 where id=411 and univalent=0;


#### 同步sip_line_share

update sip_line_share set univalent=0.15 where id = 16;

