package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DispatchLines implements Serializable {
    private Integer id;

    private String planuuid;

    private Date createTime;

    private Integer lineId;

    private String lineName;

    private BigDecimal lineAmount;

    private String overtarea;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanuuid() {
        return planuuid;
    }

    public void setPlanuuid(String planuuid) {
        this.planuuid = planuuid == null ? null : planuuid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", planuuid=").append(planuuid);
        sb.append(", createTime=").append(createTime);
        sb.append(", lineId=").append(lineId);
        sb.append(", lineName=").append(lineName);
        sb.append(", lineAmount=").append(lineAmount);
        sb.append(", overtarea=").append(overtarea);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}