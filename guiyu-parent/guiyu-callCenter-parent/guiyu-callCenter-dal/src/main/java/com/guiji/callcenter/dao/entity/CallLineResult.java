package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class CallLineResult implements Serializable {
    private Long id;

    private BigInteger callId;

    private Integer lineId;

    private Boolean successed;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getCallId() {
        return callId;
    }

    public void setCallId(BigInteger callId) {
        this.callId = callId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Boolean getSuccessed() {
        return successed;
    }

    public void setSuccessed(Boolean successed) {
        this.successed = successed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callId=").append(callId);
        sb.append(", lineId=").append(lineId);
        sb.append(", successed=").append(successed);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}