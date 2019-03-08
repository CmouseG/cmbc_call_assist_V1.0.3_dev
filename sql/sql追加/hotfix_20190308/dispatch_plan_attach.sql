USE guiyu_dispatch;

ALTER TABLE dispatch_plan_0
MODIFY attach               VARCHAR(255) COMMENT '附加参数，备注',
MODIFY params               VARCHAR(32) DEFAULT NULL COMMENT '参数';


ALTER TABLE dispatch_plan_1
MODIFY attach               VARCHAR(255) COMMENT '附加参数，备注',
MODIFY params               VARCHAR(32) DEFAULT NULL COMMENT '参数';


ALTER TABLE dispatch_plan_2
MODIFY attach               VARCHAR(255) COMMENT '附加参数，备注',
MODIFY params               VARCHAR(32) DEFAULT NULL COMMENT '参数';



ALTER TABLE dispatch_plan
MODIFY attach               VARCHAR(255) COMMENT '附加参数，备注',
MODIFY params               VARCHAR(32) DEFAULT NULL COMMENT '参数';
