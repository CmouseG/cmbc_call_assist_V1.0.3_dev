package com.guiji.dispatch.model;

import java.util.List;

public class Schedule {

    private String id;

    //任务id
    private String planUuid;

    //用户id
    private int userId;

    //批次id
    private int batchId;

    //手机号
    private List<String> phones;

    private String attach;

    //变量
    private String params;

    //计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
    private Byte statusPlan;

    //同步状态;0未同步1已同步',
    private Byte statusSync;

    //重播;0不重播非0表示重播次数',
    private Byte recall;

    //重播条件;重播次数json格式
    private String recallParams;

    //机器人模板
    private String robot;

    //线路
    private String line;

    //转人工坐席号
    private String callAgent;

    //当日清除;当日夜间清除未完成计划
    private Byte clean;

    private int callDate;

    private String callHour;

    //每次查询的任务数
    private String taskNum;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
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
        this.recallParams = recallParams;
    }

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCallAgent() {
        return callAgent;
    }

    public void setCallAgent(String callAgent) {
        this.callAgent = callAgent;
    }

    public Byte getClean() {
        return clean;
    }

    public void setClean(Byte clean) {
        this.clean = clean;
    }

    public int getCallDate() {
        return callDate;
    }

    public void setCallDate(int callDate) {
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

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", planUuid='" + planUuid + '\'' +
                ", userId=" + userId +
                ", batchId=" + batchId +
                ", phones=" + phones +
                ", attach='" + attach + '\'' +
                ", params='" + params + '\'' +
                ", statusPlan=" + statusPlan +
                ", statusSync=" + statusSync +
                ", recall=" + recall +
                ", recallParams='" + recallParams + '\'' +
                ", robot='" + robot + '\'' +
                ", line='" + line + '\'' +
                ", callAgent='" + callAgent + '\'' +
                ", clean=" + clean +
                ", callDate=" + callDate +
                ", callHour='" + callHour + '\'' +
                ", taskNum='" + taskNum + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", gmtUpdate='" + gmtUpdate + '\'' +
                '}';
    }
}