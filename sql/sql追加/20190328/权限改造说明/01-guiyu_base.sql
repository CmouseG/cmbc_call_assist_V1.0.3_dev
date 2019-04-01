use guiyu_base;

CREATE TABLE
    sys_product
    (
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(50) COMMENT '产品名称',
        product_desc VARCHAR(512) COMMENT '产品描述',
        product_status INT COMMENT '产品状态:1-正常 0-删除',
        style VARCHAR(20) COMMENT '系统风格',
        sys_name VARCHAR(20) COMMENT '系统名称',
        logo VARCHAR(150) COMMENT 'logo图片',
        crt_user INT(20) COMMENT '创建人',
        crt_time DATETIME COMMENT '创建时间',
        update_time DATETIME,
        update_user INT(20),
        PRIMARY KEY (id),
        INDEX sys_product_idx1 (product_status)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统产品表';
    
CREATE TABLE
    sys_privilege
    (
        id INT NOT NULL AUTO_INCREMENT,
        auth_id VARCHAR(30) COMMENT '授权对象编号',
        auth_type INT COMMENT '授权对象类型: 1-产品 2-企业 3-角色 4-用户',
        resource_id VARCHAR(30) COMMENT '资源编号',
        resource_type INT COMMENT '资源类型:1-菜单 2-行业 3-模板',
        org_code VARCHAR(30) COMMENT '创建人组织编号',
        crt_user INT(20) COMMENT '创建人',
        crt_time DATETIME COMMENT '创建时间',
        update_time DATETIME,
        update_user INT(20),
        PRIMARY KEY (id),
        INDEX sys_privilege_idx1 (auth_id, auth_type, resource_type),
        INDEX sys_privilege_idx2 (auth_type, resource_id, resource_type),
        INDEX sys_privilege_idx3 (org_code)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限绑定';
    
CREATE TABLE
    sys_privilege_his
    (
        id INT NOT NULL AUTO_INCREMENT,
        privilege_id INT COMMENT '授权编号',
        auth_id VARCHAR(30) COMMENT '授权对象编号',
        auth_type INT COMMENT '授权对象类型: 1-产品 2-企业 3-角色 4-用户',
        resource_id VARCHAR(30) COMMENT '资源编号',
        org_code VARCHAR(30) COMMENT '创建人组织编号',
        resource_type INT COMMENT '资源类型 :1-菜单 2-行业 3-模板',
        crt_user INT(20) COMMENT '创建人',
        crt_time DATETIME COMMENT '创建时间',
        update_time DATETIME,
        update_user INT(20),
        do_user INT(20) COMMENT '触发人',
        do_time DATETIME COMMENT '触发时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限绑定操作历史表（暂时只保存下删除的关系）';

ALTER TABLE sys_role ADD (data_auth_level int comment '数据查询权限（1-本人;2-本组织;3-本组织及下级组织）');
ALTER TABLE sys_role ADD (org_code varchar(32) comment '真实企业编号');
ALTER TABLE sys_menu ADD (sys_type int comment '系统菜单标识：1-系统菜单 0-其他');


update sys_role set data_auth_level=1;
update sys_role set data_auth_level=3 where id in (1,3);

update sys_organization set sub_code=concat(sub_code,'.') where sub_code is not null;

update sys_organization set code = CONCAT(code,'.') where id <> 1;

update sys_user set org_code='1' where org_code='1.';

update sys_menu set sys_type=1 where url in ('/system/menu','/system/dataDictionaries','/system/processManage','/system/processTask','/robotCenter/simList');

insert into sys_privilege(auth_id,auth_type,resource_id,resource_type,org_code,crt_user,crt_time,update_time,update_user) select org.id,2,temp.template_id,2,org.code,1,'2019-03-16 00:00:00','2019-03-16 00:00:00',1 from guiyu_base.sys_organization_industry rel,guiyu_base.sys_organization org,guiyu_botstence.bot_sentence_template temp where rel.organization_id=org.id and rel.industry_id=left(temp.industry_id, 4);

INSERT INTO sys_menu (id, name, description, url, pid, permission, is_show, create_id, create_time, update_id, update_time, type, level, appid, remarks, del_flag) select 50,'人工坐席',null,'callCenter_workPlatform_agent',id,'callCenter_workPlatform_agent',1,1,'2019-03-16 00:00:00',1,'2019-03-16 00:00:00',2,3,0,0,0 from sys_menu where url='/callCenter/workPlatform';    

INSERT INTO sys_product (id, name, product_desc, product_status, style, sys_name, logo, crt_user, crt_time, update_time, update_user) VALUES (0, '大B', '大B', 1, null, null, null, 1, '2019-03-27 00:00:00', '2019-03-27 00:00:00', 1);
INSERT INTO sys_product (id, name, product_desc, product_status, style, sys_name, logo, crt_user, crt_time, update_time, update_user) VALUES (1, '放款王', '放款王', 1, null, null, null, 1, '2019-03-27 00:00:00', '2019-03-27 00:00:00', 1);
INSERT INTO sys_product (id, name, product_desc, product_status, style, sys_name, logo, crt_user, crt_time, update_time, update_user) VALUES (2, '房产王', '房产王', 1, null, null, null, 1, '2019-03-27 00:00:00', '2019-03-27 00:00:00', 1);
INSERT INTO sys_product (id, name, product_desc, product_status, style, sys_name, logo, crt_user, crt_time, update_time, update_user) VALUES (3, '招行-马拉松', '招行-马拉松', 1, null, null, null, 1, '2019-03-27 00:00:00', '2019-03-27 00:00:00', 1);

UPDATE sys_organization t SET t.`open` = 1 WHERE t.type = 1
