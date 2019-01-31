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


--初始化存量话术模板的行业
update bot_sentence_process set industry='信用卡发卡', industry_id='060405' where industry='营销/金融';
update bot_sentence_process set industry='贷款平台', industry_id='010101' where industry='贷款_青岛贷款模板';
update bot_sentence_process set industry='美容', industry_id='012001' where industry='美容';
update bot_sentence_process set industry='酒水', industry_id='010602' where industry='营销-酒水';
update bot_sentence_process set industry='住宅', industry_id='010201' where industry='房产';
update bot_sentence_process set industry='家装', industry_id='011901' where industry='装修';
update bot_sentence_process set industry='信用卡发卡', industry_id='060405' where industry='营销金融';
update bot_sentence_process set industry='酒水', industry_id='010602' where template_name='吕孝明酒水';
update bot_sentence_process set industry='学历提升', industry_id='011602' where template_name='成都翔飞航空招生';
update bot_sentence_process set industry='小贷公司', industry_id='010103' where template_name='直面金融';
