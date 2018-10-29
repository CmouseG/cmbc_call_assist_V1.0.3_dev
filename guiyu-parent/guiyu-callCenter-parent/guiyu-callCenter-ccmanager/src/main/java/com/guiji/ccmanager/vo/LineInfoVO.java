package com.guiji.ccmanager.vo;

import java.io.Serializable;
import java.util.Date;

public class LineInfoVO implements Serializable {

    private Integer lineid;
    private String linename;
    private String sipip;
    private String sipport;
    private String codec;
    private String callernum;
    private String calleeprefix;
    private Integer maxconcurrentcalls;
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getLineid() {
        return lineid;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getSipip() {
        return sipip;
    }

    public void setSipip(String sipip) {
        this.sipip = sipip;
    }

    public String getSipport() {
        return sipport;
    }

    public void setSipport(String sipport) {
        this.sipport = sipport;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getCallernum() {
        return callernum;
    }

    public void setCallernum(String callernum) {
        this.callernum = callernum;
    }

    public String getCalleeprefix() {
        return calleeprefix;
    }

    public void setCalleeprefix(String calleeprefix) {
        this.calleeprefix = calleeprefix;
    }

    public Integer getMaxconcurrentcalls() {
        return maxconcurrentcalls;
    }

    public void setMaxconcurrentcalls(Integer maxconcurrentcalls) {
        this.maxconcurrentcalls = maxconcurrentcalls;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}