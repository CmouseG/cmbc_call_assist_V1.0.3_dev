package com.guiji.fsagent.entity;

public class RecordReqVO {
    private String sysCode;
    private String busiId;
    private String busiType;
    private String fileName;
    private Long userId;
    private String recordId;

    private RecordType recordType;

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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "RecordReqVO{" +
                "sysCode='" + sysCode + '\'' +
                ", busiId='" + busiId + '\'' +
                ", busiType='" + busiType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userId=" + userId +
                ", recordId='" + recordId + '\'' +
                ", recordType=" + recordType +
                '}';
    }
}
