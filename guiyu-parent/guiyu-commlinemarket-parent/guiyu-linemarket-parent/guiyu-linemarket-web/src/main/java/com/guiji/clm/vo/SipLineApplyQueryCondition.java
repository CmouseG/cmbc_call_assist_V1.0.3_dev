package com.guiji.clm.vo;

import lombok.Data;

/**
 * @version V1.0
 * @Description: 线路申请、审批查询条件
 * @Author: weiyunbo
 * @date 2019年1月23日 上午10:40:47
 */
@Data
public class SipLineApplyQueryCondition {
    private int pageNo;
    private int pageSize;
    //代理线路编号
    private Integer agentLineId;
    //变更sip线路编号(更新数据时使用)
    private Integer upSipLineId;
    //线路名称(包装后)
    private String lineName;
    //话术模板编号
    private String template;
    //线路归属企业
    private String orgCode;
    //申请人
    private String applyUserId;
    //申请企业编号
    private String applyOrgCode;
    //申请日期-起始
    private String applyDateBegin;
    //申请日期-结束
    private String applyDateEnd;
    //申请类型(1-新线路;2-业务数据变更;3-线路变更)
    private Integer applyType;
    //申请状态(1-申请中,2-审批通过;3-审批拒绝)
    private Integer applyStatus;
    //审批人
    private String approveUser;
    //审批日期-起始
    private String approveDateBegin;
    //审批日期-结束
    private String approveDateEnd;
    //登录用户id
    private String userId;
    //登录用户的权限级别
    private Integer authLevel;
}
