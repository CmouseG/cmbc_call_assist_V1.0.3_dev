package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportCallHour implements Serializable {
    private Long id;

    private Date callTime;

    private Integer outCount;

    private Integer connectCount;

    private Long duration;

    private String tempid;

    private String orgCode;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(Integer connectCount) {
        this.connectCount = connectCount;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callTime=").append(callTime);
        sb.append(", outCount=").append(outCount);
        sb.append(", connectCount=").append(connectCount);
        sb.append(", duration=").append(duration);
        sb.append(", tempid=").append(tempid);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}