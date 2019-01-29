use guiyu_callcenter;
CREATE TABLE `agent` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `crm_login_id` varchar(50) DEFAULT NULL,
  `answer_type` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_pwd` varchar(255) DEFAULT NULL,
  `user_role` int(11) DEFAULT NULL,
  `user_state` int(11) DEFAULT NULL,
  `org_code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `queue` (
  `queue_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_name` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `line_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`queue_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30000 DEFAULT CHARSET=utf8;


CREATE TABLE `registration` (
  `reg_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `customer_addr` varchar(255) DEFAULT NULL,
  `customer_mobile` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`reg_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `tier` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `queue_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `org_code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `phone` (
  `phone` varchar(255) NOT NULL,
  `area_code` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `city_code` varchar(255) DEFAULT NULL,
  `isp` varchar(255) DEFAULT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `pref` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
