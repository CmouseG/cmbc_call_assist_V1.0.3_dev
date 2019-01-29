use guiyu_dispatch;

CREATE TABLE `black_list_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  `org_code` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

ALTER TABLE  `black_list` ADD COLUMN user_id INT(11) DEFAULT 0 COMMENT '用户id';
ALTER TABLE  `black_list` ADD COLUMN update_user_id INT(11) DEFAULT 0 COMMENT '修改userid';
ALTER TABLE  `black_list` ADD COLUMN org_code varchar(8) DEFAULT 0 COMMENT '批次名字';
ALTER TABLE  `black_list` ADD COLUMN create_user_name varchar(32) DEFAULT 0 COMMENT '创建用户名';
ALTER TABLE  `black_list` ADD COLUMN update_user_name varchar(32) DEFAULT 0 COMMENT '修改用户名字';
ALTER TABLE  `black_list` ADD COLUMN status varchar(8) DEFAULT 0 COMMENT '0未删除1删除';


ALTER TABLE  `file_records` ADD COLUMN line_name varchar(32) DEFAULT 0 COMMENT '线路名称';
ALTER TABLE  `file_records` ADD COLUMN robot_name varchar(32) DEFAULT 0 COMMENT '机器人名字';
ALTER TABLE  `file_records` ADD COLUMN user_name varchar(32) DEFAULT 0 COMMENT '用户名';