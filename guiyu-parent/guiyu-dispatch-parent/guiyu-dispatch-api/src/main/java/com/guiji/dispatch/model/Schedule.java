package com.guiji.dispatch.model;

public class Schedule {

    private String id;

    //任务id
    private String planUuid;

    //用户id
    private String userId;

    //批次id
    private String batchId;

    //手机号
    private String phone;

    //附加参数，第三方系统唯一标识
    private String attach;

    //变量，多个参数用|分割
    private String params;

    //计划状态，0：未计划 1：计划中 2：计划完成 3：暂停计划 4：停止计划
    private String statusPlan;

    //同步状态，0：未同步 1：已同步
    private String statusSync;

    //重播，0：不重播 非0为重播次数
    private String recall;

    //重播条件
    private String recallParams;

    //呼叫机器人
    private String robot;

    //转人工坐席号
    private String callAgent;

    //当日清除未完成计划
    private String clean;

    //呼叫日期
    private String callDate;

    //呼叫时间
    private String callHour;

    private String gmtCreate;

    private String gmtUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanUuid() {
        return planUuid;
    }

    public void setPlanUuid(String planUuid) {
        this.planUuid = planUuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStatusPlan() {
        return statusPlan;
    }

    public void setStatusPlan(String statusPlan) {
        this.statusPlan = statusPlan;
    }

    public String getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(String statusSync) {
        this.statusSync = statusSync;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public String getRecallParams() {
        return recallParams;
    }

    public void setRecallParams(String recallParams) {
        this.recallParams = recallParams;
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getCallAgent() {
        return callAgent;
    }

    public void setCallAgent(String callAgent) {
        this.callAgent = callAgent;
    }

    public String getClean() {
        return clean;
    }

    public void setClean(String clean) {
        this.clean = clean;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallHour() {
        return callHour;
    }

    public void setCallHour(String callHour) {
        this.callHour = callHour;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(String gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
