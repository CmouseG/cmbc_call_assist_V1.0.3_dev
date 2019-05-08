package com.guiji.fsmanager.entity;

import java.io.Serializable;

public class LineInfoVO implements Serializable {
    private String lineId;

    private String sipIp;

    private String sipPort;

    private String codec;

    private String callerNum;

    private String calleePrefix;

    private LineTypeEnum lineTypeEnum;

    private static final long serialVersionUID = 1L;


    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp;
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    public String getCalleePrefix() {
        return calleePrefix;
    }

    public void setCalleePrefix(String calleePrefix) {
        this.calleePrefix = calleePrefix;
    }

    public LineTypeEnum getLineTypeEnum() {
        return lineTypeEnum;
    }

    public void setLineTypeEnum(LineTypeEnum lineTypeEnum) {
        this.lineTypeEnum = lineTypeEnum;
    }

    @Override
    public String toString() {
        return "LineInfoVO{" +
                "lineId='" + lineId + '\'' +
                ", sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                ", codec='" + codec + '\'' +
                ", callerNum='" + callerNum + '\'' +
                ", calleePrefix='" + calleePrefix + '\'' +
                ", lineTypeEnum=" + lineTypeEnum +
                '}';
    }
}