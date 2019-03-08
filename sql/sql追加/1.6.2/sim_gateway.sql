user guiyu_callcenter;
CREATE TABLE `sim_gateway` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sim_gateway_id` varchar(50) DEFAULT NULL,
  `start_count` int(10) DEFAULT NULL,
  `counts_step` int(10) DEFAULT NULL,
  `start_pwd` int(10) DEFAULT NULL,
  `pwd_step` int(10) DEFAULT NULL,
  `count_num` int(10) DEFAULT NULL,
  `sim_agent_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;