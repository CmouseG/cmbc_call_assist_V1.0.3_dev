INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_options', 1, 1, 'OPT', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_options_level', 1, 1, 'OPTLV', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_survey', 1, 1, 'SUR', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_survey_intent', 1, 1, 'SURINT', null);
INSERT INTO bd_table_sequence (table_name, seq, step, SIGN, now_date) VALUES ('bot_sentence_wechat_user_info', 1, 1, 'WEUS', null);


--初始化ignore_but_domains数据
update bot_sentence_domain set ignore_but_domains = replace(ignore_but_domains, '\"' , '')   where ignore_but_domains is not null;
update bot_sentence_domain set ignore_but_domains = replace(ignore_but_domains, '["' , '')   where ignore_but_domains is not null;
update bot_sentence_domain set ignore_but_domains = replace(ignore_but_domains, ']"' , '')   where ignore_but_domains is not null;
update bot_sentence_domain set ignore_but_domains = replace(ignore_but_domains, ']' , '')   where ignore_but_domains is not null;
update bot_sentence_domain set ignore_but_domains = replace(ignore_but_domains, '[' , '')   where ignore_but_domains is not null;


--初始化意图信息
update bot_sentence_intent set domain_name=SUBSTRING_INDEX(  SUBSTRING_INDEX(name,'_',- 3), '_' , 1) where domain_name is null;
update bot_sentence_intent set name='自定义' where name like '%process%';