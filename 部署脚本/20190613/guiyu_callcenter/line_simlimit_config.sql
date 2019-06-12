USE `guiyu_callcenter`;

/*Table structure for table `line_simlimit_config` */

DROP TABLE IF EXISTS `line_simlimit_config`;

CREATE TABLE `line_simlimit_config` (
  `line_id` int(11) NOT NULL,
  `call_count_top` int(11) DEFAULT NULL COMMENT '拨打次数上限',
  `call_count_period` int(2) DEFAULT NULL COMMENT '拨打次数上限,周期。0-10分钟、1-20分钟、2-30分钟、3-1小时',
  `connect_count_top` int(11) DEFAULT NULL COMMENT '接通次数上限',
  `connect_count_period` int(2) DEFAULT NULL COMMENT '接通次数上限,周期。0-10分钟、1-20分钟、2-30分钟、3-1小时',
  `connect_time_top` int(11) DEFAULT NULL COMMENT '接通分钟上限',
  `connect_time_period` int(2) DEFAULT NULL COMMENT '接通分钟上限,周期。3-1小时、4-1天、5-1个月',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
  );