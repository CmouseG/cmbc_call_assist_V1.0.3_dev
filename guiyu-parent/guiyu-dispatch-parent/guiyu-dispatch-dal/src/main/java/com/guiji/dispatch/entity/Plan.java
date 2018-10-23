package com.guiji.dispatch.entity;

import java.util.Date;

public class Plan {
    private Integer id;

    private String planUuid;

    private Integer userId;

    private Integer batchId;

    private String phone;

    private String attach;

    private String params;

    private Byte statusPlan;

    private Byte statusSync;

    private Byte recall;

    private String recallParams;

    private String robot;

    private String callAgent;

    private Byte clean;

    private Integer callData;

    private String callHour;

    private Date gmtCreate;

    private Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanUuid() {
        return planUuid;
    }

    public void setPlanUuid(String planUuid) {
        this.planUuid = planUuid == null ? null : planUuid.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Byte getStatusPlan() {
        return statusPlan;
    }

    public void setStatusPlan(Byte statusPlan) {
        this.statusPlan = statusPlan;
    }

    public Byte getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(Byte statusSync) {
        this.statusSync = statusSync;
    }

    public Byte getRecall() {
        return recall;
    }

    public void setRecall(Byte recall) {
        this.recall = recall;
    }

    public String getRecallParams() {
        return recallParams;
    }

    public void setRecallParams(String recallParams) {
        this.recallParams = recallParams == null ? null : recallParams.trim();
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot == null ? null : robot.trim();
    }

    public String getCallAgent() {
        return callAgent;
    }

    public void setCallAgent(String callAgent) {
        this.callAgent = callAgent == null ? null : callAgent.trim();
    }

    public Byte getClean() {
        return clean;
    }

    public void setClean(Byte clean) {
        this.clean = clean;
    }

    public Integer getCallData() {
        return callData;
    }

    public void setCallData(Integer callData) {
        this.callData = callData;
    }

    public String getCallHour() {
        return callHour;
    }

    public void setCallHour(String callHour) {
        this.callHour = callHour == null ? null : callHour.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}