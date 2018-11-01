package com.guiji.ccmanager.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description= "线路信息")
public class LineInfoVO implements Serializable {

    @ApiModelProperty(value = "线路id")
    private int lineId;
    @ApiModelProperty(value = "客户id")
    private String customerId;
    @ApiModelProperty(value = "线路名称")
    private String lineName;
    @ApiModelProperty(value = "sip线路IP地址")
    private String sipIp;
    @ApiModelProperty(value = "sip线路端口")
    private String sipPort;
    @ApiModelProperty(value = "编码")
    private String codec;
    @ApiModelProperty(value = "呼叫好吗")
    private String callerNum;
    @ApiModelProperty(value = "区号")
    private String calleePrefix;
    @ApiModelProperty(value = "最大并发数")
    private Integer maxConcurrentCalls;
    @ApiModelProperty(value = "备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "LineInfoVO{" +
                "lineId=" + lineId +
                ", customerId='" + customerId + '\'' +
                ", lineName='" + lineName + '\'' +
                ", sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                ", codec='" + codec + '\'' +
                ", callerNum='" + callerNum + '\'' +
                ", calleePrefix='" + calleePrefix + '\'' +
                ", maxConcurrentCalls=" + maxConcurrentCalls +
                ", remark='" + remark + '\'' +
                '}';
    }
}