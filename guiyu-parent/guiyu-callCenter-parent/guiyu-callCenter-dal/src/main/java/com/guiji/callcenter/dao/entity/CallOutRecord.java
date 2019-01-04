package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class CallOutRecord implements Serializable {
    private BigInteger callId;

    private String recordFile;

    private String recordUrl;

    private static final long serialVersionUID = 1L;

    public BigInteger getCallId() {
        return callId;
    }

    public void setCallId(BigInteger callId) {
        this.callId = callId;
    }

    public String getRecordFile() {
        return recordFile;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile == null ? null : recordFile.trim();
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl == null ? null : recordUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", callId=").append(callId);
        sb.append(", recordFile=").append(recordFile);
        sb.append(", recordUrl=").append(recordUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}