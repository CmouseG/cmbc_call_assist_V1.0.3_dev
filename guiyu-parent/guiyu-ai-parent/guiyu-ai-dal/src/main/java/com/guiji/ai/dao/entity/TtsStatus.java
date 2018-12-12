package com.guiji.ai.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsStatus implements Serializable {
    private Integer id;

    private String busId;

    private String model;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Integer textCount;

    private String jumpFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Integer getTextCount() {
        return textCount;
    }

    public void setTextCount(Integer textCount) {
        this.textCount = textCount;
    }

    public String getJumpFlag() {
        return jumpFlag;
    }

    public void setJumpFlag(String jumpFlag) {
        this.jumpFlag = jumpFlag == null ? null : jumpFlag.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", busId=").append(busId);
        sb.append(", model=").append(model);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", textCount=").append(textCount);
        sb.append(", jumpFlag=").append(jumpFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}