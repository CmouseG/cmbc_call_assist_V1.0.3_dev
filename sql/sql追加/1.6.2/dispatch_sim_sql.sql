USE guiyu_dispatch;

ALTER TABLE dispatch_plan
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_plan_0
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_plan_1
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_plan_2
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_lines
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';

ALTER TABLE dispatch_lines_0
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_lines_1
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_lines_2
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';

ALTER TABLE dispatch_lines_3
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';


ALTER TABLE dispatch_lines_4
ADD line_type            INT(1) NOT NULL DEFAULT 1 COMMENT '线路类型，1-SIP，2-网关';




DROP INDEX idx_dispatch_robot_op ON dispatch_robot_op;

DROP TABLE IF EXISTS dispatch_robot_op;

/*==============================================================*/
/* Table: dispatch_robot_op                                     */
/*==============================================================*/
CREATE TABLE dispatch_robot_op
(
   id                   INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   user_id              VARCHAR(32) COMMENT '用户ID',
   botstence_id         VARCHAR(32) COMMENT '话术模板ID',
   robot_num            INT(8) COMMENT '分配模板机器人数',
   suppl_num            INT(8) COMMENT '补充机器人数',
   suppl_type           INT(1) COMMENT '补充标识，1-补充，2-删除',
   current_num          INT(8) COMMENT '当前分配机器人总数',
   add_time             DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
   upd_time             DATETIME COMMENT '更新时间',
   del_flag             INT(1) NOT NULL DEFAULT 0 COMMENT '删除标志 0-正常 1-删除',
   PRIMARY KEY (id)
);

ALTER TABLE dispatch_robot_op COMMENT '操作机器人表';

/*==============================================================*/
/* Index: idx_dispatch_robot_op                                 */
/*==============================================================*/
CREATE INDEX idx_dispatch_robot_op ON dispatch_robot_op
(
   user_id,
   botstence_id
);
