package com.guiji.callcenter.dao.entityext;

import java.io.Serializable;
import java.math.BigInteger;

public class CallIdRecordUrl implements Serializable {

    private BigInteger callId;
    private BigInteger callDetailId;

    private String recordUrl;
    private String customerRecordUrl;
    private String botRecordUrl;

    private static final long serialVersionUID = 1L;

    public BigInteger getCallId() {
        return callId;
    }

    public void setCallId(BigInteger callId) {
        this.callId = callId;
    }

    public BigInteger getCallDetailId() {
        return callDetailId;
    }

    public void setCallDetailId(BigInteger callDetailId) {
        this.callDetailId = callDetailId;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getCustomerRecordUrl() {
        return customerRecordUrl;
    }

    public void setCustomerRecordUrl(String customerRecordUrl) {
        this.customerRecordUrl = customerRecordUrl;
    }

    public String getBotRecordUrl() {
        return botRecordUrl;
    }

    public void setBotRecordUrl(String botRecordUrl) {
        this.botRecordUrl = botRecordUrl;
    }

    @Override
    public String toString() {
        return "CallIdRecordUrl{" +
                "callId=" + callId +
                ", callDetailId=" + callDetailId +
                ", recordUrl='" + recordUrl + '\'' +
                ", customerRecordUrl='" + customerRecordUrl + '\'' +
                ", botRecordUrl='" + botRecordUrl + '\'' +
                '}';
    }
}