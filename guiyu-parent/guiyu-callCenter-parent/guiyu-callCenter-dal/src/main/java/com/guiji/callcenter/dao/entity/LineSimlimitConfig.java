package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class LineSimlimitConfig implements Serializable {
    private Integer lineId;

    private Integer callCountTop;

    private Integer callCountPeriod;

    private Integer connectCountTop;

    private Integer connectCountPeriod;

    private Integer connectTimeTop;

    private Integer connectTimePeriod;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getCallCountTop() {
        return callCountTop;
    }

    public void setCallCountTop(Integer callCountTop) {
        this.callCountTop = callCountTop;
    }

    public Integer getCallCountPeriod() {
        return callCountPeriod;
    }

    public void setCallCountPeriod(Integer callCountPeriod) {
        this.callCountPeriod = callCountPeriod;
    }

    public Integer getConnectCountTop() {
        return connectCountTop;
    }

    public void setConnectCountTop(Integer connectCountTop) {
        this.connectCountTop = connectCountTop;
    }

    public Integer getConnectCountPeriod() {
        return connectCountPeriod;
    }

    public void setConnectCountPeriod(Integer connectCountPeriod) {
        this.connectCountPeriod = connectCountPeriod;
    }

    public Integer getConnectTimeTop() {
        return connectTimeTop;
    }

    public void setConnectTimeTop(Integer connectTimeTop) {
        this.connectTimeTop = connectTimeTop;
    }

    public Integer getConnectTimePeriod() {
        return connectTimePeriod;
    }

    public void setConnectTimePeriod(Integer connectTimePeriod) {
        this.connectTimePeriod = connectTimePeriod;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lineId=").append(lineId);
        sb.append(", callCountTop=").append(callCountTop);
        sb.append(", callCountPeriod=").append(callCountPeriod);
        sb.append(", connectCountTop=").append(connectCountTop);
        sb.append(", connectCountPeriod=").append(connectCountPeriod);
        sb.append(", connectTimeTop=").append(connectTimeTop);
        sb.append(", connectTimePeriod=").append(connectTimePeriod);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}