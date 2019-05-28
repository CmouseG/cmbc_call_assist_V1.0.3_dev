package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class VoipGwPortLimit implements Serializable {
    private Integer id;

    private Integer portId;

    private Integer lineId;

    private Integer timeLength;

    private Integer maxLimit;

    private Integer limitType;

    private Date crtTime;

    private Integer crtUser;

    private Date updateTime;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", portId=").append(portId);
        sb.append(", lineId=").append(lineId);
        sb.append(", timeLength=").append(timeLength);
        sb.append(", maxLimit=").append(maxLimit);
        sb.append(", limitType=").append(limitType);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}