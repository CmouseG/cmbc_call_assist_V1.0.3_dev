package com.guiji.ccmanager.vo;

import java.io.Serializable;

public class LineInfoVO implements Serializable {

    private String customerId;

    private String lineName;

    private String sipIp;

    private String sipPort;

    private String codec;

    private String callerNum;

    private String calleePrefix;

    private Integer maxConcurrentCalls;

    private String remark;

    private static final long serialVersionUID = 1L;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", customerId=").append(customerId);
        sb.append(", lineName=").append(lineName);
        sb.append(", sipIp=").append(sipIp);
        sb.append(", sipPort=").append(sipPort);
        sb.append(", codec=").append(codec);
        sb.append(", callerNum=").append(callerNum);
        sb.append(", calleePrefix=").append(calleePrefix);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}