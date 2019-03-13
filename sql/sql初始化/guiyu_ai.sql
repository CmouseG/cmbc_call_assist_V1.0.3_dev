CREATE DATABASE IF NOT EXISTS guiyu_ai DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use guiyu_ai;

grant all on guiyu_ai.* to ai@'%' identified by 'ai@1234' with grant option; 
grant all privileges on guiyu_ai.* to 'ai'@'%' identified by 'ai@1234' with grant option;

DROP TABLE IF EXISTS `ai_model_factory`;
CREATE TABLE `ai_model_factory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(64) DEFAULT NULL COMMENT '模型名称',
  `factory` int(2) DEFAULT NULL COMMENT '厂商标志：1-硅基',
  `url` varchar(128) DEFAULT NULL COMMENT '服务地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
