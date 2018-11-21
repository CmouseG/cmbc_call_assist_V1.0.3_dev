package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsWavHis implements Serializable {
    private String id;

    private String templateId;

    private String ttsKey;

    private String ttsParamKeys;

    private String ttsParamValues;

    private String ttsUrl;

    private Date crtTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTtsKey() {
        return ttsKey;
    }

    public void setTtsKey(String ttsKey) {
        this.ttsKey = ttsKey == null ? null : ttsKey.trim();
    }

    public String getTtsParamKeys() {
        return ttsParamKeys;
    }

    public void setTtsParamKeys(String ttsParamKeys) {
        this.ttsParamKeys = ttsParamKeys == null ? null : ttsParamKeys.trim();
    }

    public String getTtsParamValues() {
        return ttsParamValues;
    }

    public void setTtsParamValues(String ttsParamValues) {
        this.ttsParamValues = ttsParamValues == null ? null : ttsParamValues.trim();
    }

    public String getTtsUrl() {
        return ttsUrl;
    }

    public void setTtsUrl(String ttsUrl) {
        this.ttsUrl = ttsUrl == null ? null : ttsUrl.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateId=").append(templateId);
        sb.append(", ttsKey=").append(ttsKey);
        sb.append(", ttsParamKeys=").append(ttsParamKeys);
        sb.append(", ttsParamValues=").append(ttsParamValues);
        sb.append(", ttsUrl=").append(ttsUrl);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}