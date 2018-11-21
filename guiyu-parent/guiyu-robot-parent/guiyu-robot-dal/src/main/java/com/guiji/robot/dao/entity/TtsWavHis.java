package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsWavHis implements Serializable {
    private String id;

    private String seqId;

    private String templateId;

    private String status;

    private Date crtTime;

    private String ttsJsonData;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getTtsJsonData() {
        return ttsJsonData;
    }

    public void setTtsJsonData(String ttsJsonData) {
        this.ttsJsonData = ttsJsonData == null ? null : ttsJsonData.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seqId=").append(seqId);
        sb.append(", templateId=").append(templateId);
        sb.append(", status=").append(status);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", ttsJsonData=").append(ttsJsonData);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}