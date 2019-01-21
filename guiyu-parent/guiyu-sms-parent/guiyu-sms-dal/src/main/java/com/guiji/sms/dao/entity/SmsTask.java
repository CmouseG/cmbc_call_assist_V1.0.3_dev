package com.guiji.sms.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SmsTask implements Serializable {
    private Integer id;

    private String taskName;

    private Integer phoneNum;

    private Integer sendStatus;

    private Integer sendType;

    private String tunnelName;

    private Integer smsTemplateId;

    private Date sendDate;

    private Integer auditingStatus;

    private Integer runStatus;

    private Integer companyId;

    private String companyName;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    private String smsContent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName == null ? null : tunnelName.trim();
    }

    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Integer smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(Integer auditingStatus) {
        this.auditingStatus = auditingStatus;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskName=").append(taskName);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", sendType=").append(sendType);
        sb.append(", tunnelName=").append(tunnelName);
        sb.append(", smsTemplateId=").append(smsTemplateId);
        sb.append(", sendDate=").append(sendDate);
        sb.append(", auditingStatus=").append(auditingStatus);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", companyId=").append(companyId);
        sb.append(", companyName=").append(companyName);
        sb.append(", createId=").append(createId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", smsContent=").append(smsContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}