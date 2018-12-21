package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class UserSmsConfig implements Serializable {
    private Integer id;

    private Integer userId;

    private String templateId;

    private String callResult;

    //不同的队列根据这个来区分
    private Integer smsTunnelId;

    private Integer smsTemplateId;

    private String smsTemplateData;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult == null ? null : callResult.trim();
    }

    public Integer getSmsTunnelId() {
        return smsTunnelId;
    }

    public void setSmsTunnelId(Integer smsTunnelId) {
        this.smsTunnelId = smsTunnelId;
    }

    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Integer smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getSmsTemplateData() {
        return smsTemplateData;
    }

    public void setSmsTemplateData(String smsTemplateData) {
        this.smsTemplateData = smsTemplateData == null ? null : smsTemplateData.trim();
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
        sb.append(", userId=").append(userId);
        sb.append(", templateId=").append(templateId);
        sb.append(", callResult=").append(callResult);
        sb.append(", smsTunnelId=").append(smsTunnelId);
        sb.append(", smsTemplateId=").append(smsTemplateId);
        sb.append(", smsTemplateData=").append(smsTemplateData);
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