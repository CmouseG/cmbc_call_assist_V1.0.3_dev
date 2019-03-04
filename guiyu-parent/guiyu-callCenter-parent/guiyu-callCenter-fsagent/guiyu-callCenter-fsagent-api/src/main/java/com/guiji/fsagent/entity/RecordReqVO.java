package com.guiji.fsagent.entity;

public class RecordReqVO {
    private String sysCode;
    private String busiId;
    private String busiType;
    private String fileName;
    private Long userId;
    private int duration;
    private int billsec;

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBillsec() {
        return billsec;
    }

    public void setBillsec(int billsec) {
        this.billsec = billsec;
    }

    @Override
    public String toString() {
        return "RecordReqVO{" +
                "sysCode='" + sysCode + '\'' +
                ", busiId='" + busiId + '\'' +
                ", busiType='" + busiType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userId=" + userId +
                ", duration=" + duration +
                ", billsec=" + billsec +
                '}';
    }
}
