use guiyu_dispatch;
ALTER TABLE  `file_error_records` ADD COLUMN data_type INT(11) DEFAULT 0 COMMENT '0ҳ�����ݣ�1����������';
ALTER TABLE  `file_error_records` ADD COLUMN batch_id INT(11) DEFAULT 0 COMMENT '����id';
ALTER TABLE  `file_error_records` ADD COLUMN batch_name varchar(32) DEFAULT 0 COMMENT '��������';

CREATE TABLE `third_interface_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `times` int(32) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1�ɹ� 0ʧ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;