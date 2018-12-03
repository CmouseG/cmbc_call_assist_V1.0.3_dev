package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class LineInfo implements Serializable {
    private Integer lineId;

    private String customerId;

    private String lineName;

    private String sipIp;

    private String sipPort;

    private String codec;

    private String callerNum;

    private String calleePrefix;

    private Integer maxConcurrentCalls;

    private String createDate;

    private String updateDate;

    private String remark;

    private String createtBy;

    private String updateBy;

    private static final long serialVersionUID = 1L;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp == null ? null : sipIp.trim();
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort == null ? null : sipPort.trim();
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec == null ? null : codec.trim();
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum == null ? null : callerNum.trim();
    }

    public String getCalleePrefix() {
        return calleePrefix;
    }

    public void setCalleePrefix(String calleePrefix) {
        this.calleePrefix = calleePrefix == null ? null : calleePrefix.trim();
    }

    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreatetBy() {
        return createtBy;
    }

    public void setCreatetBy(String createtBy) {
        this.createtBy = createtBy == null ? null : createtBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lineId=").append(lineId);
        sb.append(", customerId=").append(customerId);
        sb.append(", lineName=").append(lineName);
        sb.append(", sipIp=").append(sipIp);
        sb.append(", sipPort=").append(sipPort);
        sb.append(", codec=").append(codec);
        sb.append(", callerNum=").append(callerNum);
        sb.append(", calleePrefix=").append(calleePrefix);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remark=").append(remark);
        sb.append(", createtBy=").append(createtBy);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}