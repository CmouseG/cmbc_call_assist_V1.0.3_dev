alter table volice_info
	add tts_composite_type int default 1 not null comment 'tts合成类型：1-变量;2-变量加内容' after need_tts;

alter table bot_sentence_process alter column sound_type set default 'mh';