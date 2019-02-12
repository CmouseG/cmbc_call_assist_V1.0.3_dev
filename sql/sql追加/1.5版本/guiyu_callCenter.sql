

use guiyu_callcenter;
 

CREATE TABLE `call_line_day_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day_time` datetime DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `success_count` int(11) DEFAULT NULL,
  `all_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;



CREATE TABLE `call_line_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_id` bigint(20) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `successed` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=633 DEFAULT CHARSET=utf8;


ALTER TABLE `report_line_code` ADD COLUMN org_code VARCHAR(12);
ALTER TABLE `report_line_status` ADD COLUMN org_code VARCHAR(12);



CREATE TABLE `notice_send_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_code` varchar(12) DEFAULT NULL,
  `label` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;