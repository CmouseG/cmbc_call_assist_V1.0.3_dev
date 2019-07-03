package com.guiji.robot.service.vo;

import lombok.Data;

/**
 * @version V1.0
 * @ClassName: UserAiCfgBaseCondition
 * @Description: 机器人配置基本信息查询条件
 * @auth weiyunbo
 * @date 2019年3月17日 下午6:41:48
 */
@Data
public class UserAiCfgBaseCondition {
    //分页-页码
    private int pageNo;
    //分页-每页多少天
    private int pageSize;
    //数据查询权限
    private Integer authLevel;
    //用户
    private String userId;
    //企业
    private String orgCode;
    //所属者查询
    private String qUserId;

    private String qOrgCode;

    private Boolean isSuperAdmin;
}
