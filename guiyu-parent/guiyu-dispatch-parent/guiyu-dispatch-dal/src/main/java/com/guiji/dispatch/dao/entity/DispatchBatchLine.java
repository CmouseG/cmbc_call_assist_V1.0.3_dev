package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DispatchBatchLine implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer batchId;

    private Integer lineId;

    private Integer userId;

    private Integer orgId;

    private Integer lineType;

    private String lineName;

    private BigDecimal lineAmount;

    private String overtarea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public BigDecimal getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(BigDecimal lineAmount) {
        this.lineAmount = lineAmount;
    }

    public String getOvertarea() {
        return overtarea;
    }

    public void setOvertarea(String overtarea) {
        this.overtarea = overtarea == null ? null : overtarea.trim();
    }

    @Override
    public String toString() {
        return "DispatchBatchLine{" +
                "id=" + id +
                ", batchId=" + batchId +
                ", lineId=" + lineId +
                ", userId=" + userId +
                ", orgId=" + orgId +
                ", lineType=" + lineType +
                ", lineName='" + lineName + '\'' +
                ", lineAmount=" + lineAmount +
                ", overtarea='" + overtarea + '\'' +
                '}';
    }
}