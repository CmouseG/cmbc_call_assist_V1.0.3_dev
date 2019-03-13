package com.guiji.botsentence.message;

import java.io.Serializable;

public class SpeechAuditMessage implements Serializable {

    private static final long serialVersionUID = 1986477783011471101L;

    private String processId;

    private String userId;

    public SpeechAuditMessage() {
    }

    public SpeechAuditMessage(String processId, String userId) {
        this.processId = processId;
        this.userId = userId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
