package com.guiji.botsentence.vo;

public class TtsBackupReqVO {
    private String backupId;

    private String processId;

    private String templateId;

    private Long voliceId;

    private String content;

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId == null ? null : backupId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Long getVoliceId() {
        return voliceId;
    }

    public void setVoliceId(Long voliceId) {
        this.voliceId = voliceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}