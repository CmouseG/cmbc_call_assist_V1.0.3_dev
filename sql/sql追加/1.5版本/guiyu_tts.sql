CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `busi_id` varchar(128) DEFAULT NULL COMMENT '业务id',
  `busi_type` char(20) DEFAULT NULL COMMENT '业务类型',
  `file_type` char(20) DEFAULT NULL COMMENT '文件类型',
  `file_size` double(10,4) DEFAULT NULL COMMENT '文件大小',
  `sk_url` varchar(255) DEFAULT NULL COMMENT '文件上传后下载地址',
  `sk_thumb_image_url` varchar(255) DEFAULT NULL COMMENT '图片缩略图上传后下载地址',
  `sys_code` varchar(32) DEFAULT NULL COMMENT '系统服务编号,如用户中心01',
  `crt_user` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `lst_update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `lst_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) COMMENT '主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tts_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(20) NOT NULL COMMENT '模型',
  `tts_ip` varchar(20) NOT NULL DEFAULT '' COMMENT 'tts服务器ip',
  `tts_port` varchar(10) NOT NULL DEFAULT '' COMMENT 'tts服务器端口',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态：0启动1停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `company` varchar(50) DEFAULT NULL COMMENT '公司',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tts_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bus_id` varchar(64) DEFAULT NULL COMMENT '业务id',
  `tts_ip` varchar(20) NOT NULL DEFAULT '' COMMENT 'tts服务器ip',
  `tts_port` varchar(10) NOT NULL DEFAULT '' COMMENT 'tts服务器端口',
  `content` varchar(255) DEFAULT NULL COMMENT '待转换文本内容',
  `model` varchar(20) NOT NULL DEFAULT '' COMMENT '模型',
  `audio_url` varchar(1000) DEFAULT NULL COMMENT '输出音频文件url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标识：0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tts_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bus_id` varchar(64) DEFAULT NULL COMMENT '业务id',
  `model` varchar(20) DEFAULT NULL COMMENT '模型',
  `status` int(1) DEFAULT NULL COMMENT '处理状态：0未处理1处理中2已完成3处理失败',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `text_count` int(8) DEFAULT '0' COMMENT '文本数量',
  `jump_flag` int(1) DEFAULT '0' COMMENT '任务优先处理标识：0未优先，1已优先',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
