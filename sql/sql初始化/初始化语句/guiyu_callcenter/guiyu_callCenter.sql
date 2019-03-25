
USE `guiyu_callcenter`;


insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (1,0,'无人接听','no_answer','无人接听');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (2,1,'主叫停机','initiative_stop','您的电话已欠费');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (3,1,'主叫停机','initiative_stop','续交话费');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (4,1,'主叫停机','initiative_stop','手机号码已暂停');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (5,1,'主叫停机','initiative_stop','您的手机号码');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (6,1,'主叫停机','initiative_stop','继续使用');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (7,1,'主叫停机','initiative_stop','无法呼出');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (8,1,'主叫停机','initiative_stop','您的电话已被停机');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (9,2,'被叫停机','passive_stop','欠费');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (10,2,'被叫停机','passive_stop','停机');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (11,2,'被叫停机','passive_stop','通话已经被');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (12,2,'被叫停机','passive_stop','所拨打的用户通话');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (13,2,'被叫停机','passive_stop','暂停服务');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (14,2,'被叫停机','passive_stop','未开通语音');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (15,3,'关机或不在服务区','power_off','关机');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (16,3,'关机或不在服务区','power_off','不在服务区');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (17,4,'占线','busy','占线');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (18,4,'占线','busy','通话中');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (19,4,'占线','busy','正在通话');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (20,4,'占线','busy','无法接通');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (21,4,'占线','busy','正忙');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (22,4,'占线','busy','无法接听');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (23,4,'占线','busy','联通秘书');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (24,4,'占线','busy','来电助手');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (25,4,'占线','busy','来电宝');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (26,4,'占线','busy','短信的方式');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (27,4,'占线','busy','短信方式');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (28,5,'空号','vacant_number','空号');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (29,5,'空号','vacant_number','不存在');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (30,5,'空号','vacant_number','不在使用');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (31,6,'呼叫受限','call_limit','越权使用');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (32,6,'呼叫受限','call_limit','呼叫受限');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (33,6,'呼叫受限','call_limit','呼叫该号码');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (34,6,'呼叫受限','call_limit','语音信箱');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (35,7,'用户挂断','user_hangup','用户挂断');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (36,8,'无效号码','Invalid_number','无效号码');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (37,9,'已接通','connected','已接通');
insert  into `error_match`(`id`,`error_type`,`error_name`,`en_name`,`key_word`) values (38,10,'无人接听','no_answer','无人接听');


