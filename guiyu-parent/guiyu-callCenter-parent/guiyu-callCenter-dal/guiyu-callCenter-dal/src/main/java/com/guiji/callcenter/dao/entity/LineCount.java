package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class LineCount implements Serializable {
    private Integer id;

    private String calloutserverId;

    private Integer lineId;

    private Integer maxConcurrentCalls;

    private Integer usedConcurrentCalls;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalloutserverId() {
        return calloutserverId;
    }

    public void setCalloutserverId(String calloutserverId) {
        this.calloutserverId = calloutserverId == null ? null : calloutserverId.trim();
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public Integer getUsedConcurrentCalls() {
        return usedConcurrentCalls;
    }

    public void setUsedConcurrentCalls(Integer usedConcurrentCalls) {
        this.usedConcurrentCalls = usedConcurrentCalls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", calloutserverId=").append(calloutserverId);
        sb.append(", lineId=").append(lineId);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", usedConcurrentCalls=").append(usedConcurrentCalls);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}