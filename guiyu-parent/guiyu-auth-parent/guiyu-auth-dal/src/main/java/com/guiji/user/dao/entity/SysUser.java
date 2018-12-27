package com.guiji.user.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Integer status;

    private String pushType;

    private String callRecordUrl;

    private String batchRecordUrl;

    private String intenLabel;

    private String accessKey;

    private String secretKey;

    private String orgCode;

    private Long createId;

    private Date createTime;

    private Long updateId;

    private Date updateTime;

    private String delFlag="0";

    private Date vaildTime;

    private Date startTime;
    
    private String orgName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
    }

    public String getCallRecordUrl() {
        return callRecordUrl;
    }

    public void setCallRecordUrl(String callRecordUrl) {
        this.callRecordUrl = callRecordUrl == null ? null : callRecordUrl.trim();
    }

    public String getBatchRecordUrl() {
        return batchRecordUrl;
    }

    public void setBatchRecordUrl(String batchRecordUrl) {
        this.batchRecordUrl = batchRecordUrl == null ? null : batchRecordUrl.trim();
    }

    public String getIntenLabel() {
        return intenLabel;
    }

    public void setIntenLabel(String intenLabel) {
        this.intenLabel = intenLabel == null ? null : intenLabel.trim();
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey == null ? null : accessKey.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getVaildTime() {
        return vaildTime;
    }

    public void setVaildTime(Date vaildTime) {
        this.vaildTime = vaildTime;
    }

    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", pushType=").append(pushType);
        sb.append(", callRecordUrl=").append(callRecordUrl);
        sb.append(", batchRecordUrl=").append(batchRecordUrl);
        sb.append(", intenLabel=").append(intenLabel);
        sb.append(", accessKey=").append(accessKey);
        sb.append(", secretKey=").append(secretKey);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", createId=").append(createId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", vaildTime=").append(vaildTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}