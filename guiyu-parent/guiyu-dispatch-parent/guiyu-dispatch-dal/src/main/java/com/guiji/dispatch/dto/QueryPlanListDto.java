package com.guiji.dispatch.dto;


import com.guiji.dispatch.sys.PageDto;

import java.util.List;

public class QueryPlanListDto extends PageDto {

    private static final long serialVersionUID = 6684278788342403419L;

    private String phone;

    /**
     * 计划状态
     */
    private String planStatus;

    private String startTime;

    private String endTime;

    private Integer batchId;

    private String replayType;

    private String startCallData;

    private String endCallData;

    private String userId;

    /**
     * 意向标签条件列表
     */
    private List<String> resultList;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
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
}
