package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportCallHour implements Serializable {
    private Long id;

    private Date callTime;

    private Integer outCount;

    private Integer connectCount;

    private Long duration;

    private String customerId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
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
        sb.append(", customerId=").append(customerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}