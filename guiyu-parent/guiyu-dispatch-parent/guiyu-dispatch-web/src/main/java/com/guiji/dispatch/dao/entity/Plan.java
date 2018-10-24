package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class Plan implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.plan_uuid
     *
     * @mbggenerated
     */
    private String planUuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.batch_id
     *
     * @mbggenerated
     */
    private Integer batchId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.attach
     *
     * @mbggenerated
     */
    private String attach;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.params
     *
     * @mbggenerated
     */
    private String params;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.status_plan
     *
     * @mbggenerated
     */
    private Byte statusPlan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.status_sync
     *
     * @mbggenerated
     */
    private Byte statusSync;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.recall
     *
     * @mbggenerated
     */
    private Byte recall;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.recall_params
     *
     * @mbggenerated
     */
    private String recallParams;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.robot
     *
     * @mbggenerated
     */
    private String robot;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.call_agent
     *
     * @mbggenerated
     */
    private String callAgent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.clean
     *
     * @mbggenerated
     */
    private Byte clean;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.call_data
     *
     * @mbggenerated
     */
    private Integer callData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.call_hour
     *
     * @mbggenerated
     */
    private String callHour;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.gmt_create
     *
     * @mbggenerated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dispatch_plan.gmt_modified
     *
     * @mbggenerated
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dispatch_plan
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.id
     *
     * @return the value of dispatch_plan.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.id
     *
     * @param id the value for dispatch_plan.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.plan_uuid
     *
     * @return the value of dispatch_plan.plan_uuid
     *
     * @mbggenerated
     */
    public String getPlanUuid() {
        return planUuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.plan_uuid
     *
     * @param planUuid the value for dispatch_plan.plan_uuid
     *
     * @mbggenerated
     */
    public void setPlanUuid(String planUuid) {
        this.planUuid = planUuid == null ? null : planUuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.user_id
     *
     * @return the value of dispatch_plan.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.user_id
     *
     * @param userId the value for dispatch_plan.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.batch_id
     *
     * @return the value of dispatch_plan.batch_id
     *
     * @mbggenerated
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.batch_id
     *
     * @param batchId the value for dispatch_plan.batch_id
     *
     * @mbggenerated
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.phone
     *
     * @return the value of dispatch_plan.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.phone
     *
     * @param phone the value for dispatch_plan.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.attach
     *
     * @return the value of dispatch_plan.attach
     *
     * @mbggenerated
     */
    public String getAttach() {
        return attach;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.attach
     *
     * @param attach the value for dispatch_plan.attach
     *
     * @mbggenerated
     */
    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.params
     *
     * @return the value of dispatch_plan.params
     *
     * @mbggenerated
     */
    public String getParams() {
        return params;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.params
     *
     * @param params the value for dispatch_plan.params
     *
     * @mbggenerated
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.status_plan
     *
     * @return the value of dispatch_plan.status_plan
     *
     * @mbggenerated
     */
    public Byte getStatusPlan() {
        return statusPlan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.status_plan
     *
     * @param statusPlan the value for dispatch_plan.status_plan
     *
     * @mbggenerated
     */
    public void setStatusPlan(Byte statusPlan) {
        this.statusPlan = statusPlan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.status_sync
     *
     * @return the value of dispatch_plan.status_sync
     *
     * @mbggenerated
     */
    public Byte getStatusSync() {
        return statusSync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.status_sync
     *
     * @param statusSync the value for dispatch_plan.status_sync
     *
     * @mbggenerated
     */
    public void setStatusSync(Byte statusSync) {
        this.statusSync = statusSync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.recall
     *
     * @return the value of dispatch_plan.recall
     *
     * @mbggenerated
     */
    public Byte getRecall() {
        return recall;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.recall
     *
     * @param recall the value for dispatch_plan.recall
     *
     * @mbggenerated
     */
    public void setRecall(Byte recall) {
        this.recall = recall;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.recall_params
     *
     * @return the value of dispatch_plan.recall_params
     *
     * @mbggenerated
     */
    public String getRecallParams() {
        return recallParams;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.recall_params
     *
     * @param recallParams the value for dispatch_plan.recall_params
     *
     * @mbggenerated
     */
    public void setRecallParams(String recallParams) {
        this.recallParams = recallParams == null ? null : recallParams.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.robot
     *
     * @return the value of dispatch_plan.robot
     *
     * @mbggenerated
     */
    public String getRobot() {
        return robot;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.robot
     *
     * @param robot the value for dispatch_plan.robot
     *
     * @mbggenerated
     */
    public void setRobot(String robot) {
        this.robot = robot == null ? null : robot.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.call_agent
     *
     * @return the value of dispatch_plan.call_agent
     *
     * @mbggenerated
     */
    public String getCallAgent() {
        return callAgent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.call_agent
     *
     * @param callAgent the value for dispatch_plan.call_agent
     *
     * @mbggenerated
     */
    public void setCallAgent(String callAgent) {
        this.callAgent = callAgent == null ? null : callAgent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.clean
     *
     * @return the value of dispatch_plan.clean
     *
     * @mbggenerated
     */
    public Byte getClean() {
        return clean;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.clean
     *
     * @param clean the value for dispatch_plan.clean
     *
     * @mbggenerated
     */
    public void setClean(Byte clean) {
        this.clean = clean;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.call_data
     *
     * @return the value of dispatch_plan.call_data
     *
     * @mbggenerated
     */
    public Integer getCallData() {
        return callData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.call_data
     *
     * @param callData the value for dispatch_plan.call_data
     *
     * @mbggenerated
     */
    public void setCallData(Integer callData) {
        this.callData = callData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.call_hour
     *
     * @return the value of dispatch_plan.call_hour
     *
     * @mbggenerated
     */
    public String getCallHour() {
        return callHour;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.call_hour
     *
     * @param callHour the value for dispatch_plan.call_hour
     *
     * @mbggenerated
     */
    public void setCallHour(String callHour) {
        this.callHour = callHour == null ? null : callHour.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.gmt_create
     *
     * @return the value of dispatch_plan.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.gmt_create
     *
     * @param gmtCreate the value for dispatch_plan.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dispatch_plan.gmt_modified
     *
     * @return the value of dispatch_plan.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dispatch_plan.gmt_modified
     *
     * @param gmtModified the value for dispatch_plan.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dispatch_plan
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", planUuid=").append(planUuid);
        sb.append(", userId=").append(userId);
        sb.append(", batchId=").append(batchId);
        sb.append(", phone=").append(phone);
        sb.append(", attach=").append(attach);
        sb.append(", params=").append(params);
        sb.append(", statusPlan=").append(statusPlan);
        sb.append(", statusSync=").append(statusSync);
        sb.append(", recall=").append(recall);
        sb.append(", recallParams=").append(recallParams);
        sb.append(", robot=").append(robot);
        sb.append(", callAgent=").append(callAgent);
        sb.append(", clean=").append(clean);
        sb.append(", callData=").append(callData);
        sb.append(", callHour=").append(callHour);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}