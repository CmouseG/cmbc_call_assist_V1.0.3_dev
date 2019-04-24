package com.guiji.sms.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SmsConfig implements Serializable {
    private Integer id;

    private String tunnelName;

    private String templateId;

    private String templateName;

    private String intentionTag;

    private Integer contentType;

    private Integer auditingStatus;

    private Integer runStatus;

    private String orgCode;

    private String orgName;

    private Integer createId;

    private String createName;

    private Date createTime;

    private Integer updateId;

    private String updateName;

    private Date updateTime;

    private String smsContent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName == null ? null : tunnelName.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getIntentionTag() {
        return intentionTag;
    }

    public void setIntentionTag(String intentionTag) {
        this.intentionTag = intentionTag == null ? null : intentionTag.trim();
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
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

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
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
        sb.append(", tunnelName=").append(tunnelName);
        sb.append(", templateId=").append(templateId);
        sb.append(", templateName=").append(templateName);
        sb.append(", intentionTag=").append(intentionTag);
        sb.append(", contentType=").append(contentType);
        sb.append(", auditingStatus=").append(auditingStatus);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", orgName=").append(orgName);
        sb.append(", createId=").append(createId);
        sb.append(", createName=").append(createName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", updateName=").append(updateName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", smsContent=").append(smsContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}