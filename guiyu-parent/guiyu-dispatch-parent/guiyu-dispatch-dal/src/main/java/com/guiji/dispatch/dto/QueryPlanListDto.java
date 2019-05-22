package com.guiji.dispatch.dto;


import com.guiji.dispatch.sys.PageDto;

import java.util.List;

public class QueryPlanListDto extends PageDto {

    private static final long serialVersionUID = 6684278788342403419L;

    /**
     * 号码
     */
    private String phone;

    /**
     * 计划状态
     */
    private List<Integer> planStatusList;

    /**
     * 操作开始时间
     */
    private String startTime;

    /**
     * 操作结束时间
     */
    private String endTime;

    /**
     * 批次
     */
    private Integer batchId;

    /**
     * 重播标识
     */
    private String replayType;

    /**
     * 计划开始日期
     */
    private String startCallData;

    /**
     * 计划结束日期
     */
    private String endCallData;

    /**
     * 所属者用户ID
     */
    private String userId;

    /**
     * 企业组织Code
     */
    private String orgCode;

    /**
     * 选择组织ID
     */
    private List<Integer> orgIdList;

    /**
     * 意向标签条件列表
     */
    private List<String> resultList;

    /**
     * 客户姓名
     */
    private String custName;

    /**
     * 客户所属单位
     */
    private String custCompany;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getPlanStatusList() {
        return planStatusList;
    }

    public void setPlanStatusList(List<Integer> planStatusList) {
        this.planStatusList = planStatusList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getReplayType() {
        return replayType;
    }

    public void setReplayType(String replayType) {
        this.replayType = replayType;
    }

    public String getStartCallData() {
        return startCallData;
    }

    public void setStartCallData(String startCallData) {
        this.startCallData = startCallData;
    }

    public String getEndCallData() {
        return endCallData;
    }

    public void setEndCallData(String endCallData) {
        this.endCallData = endCallData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public List<Integer> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Integer> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany;
    }
}
