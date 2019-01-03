package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsCallbackHis implements Serializable {
    private Integer id;

    private String busiId;

    private String templateId;

    private Integer status;

    private String errorMsg;

    private Date crtTime;

    private String ttsJsonData;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId == null ? null : busiId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
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
        sb.append(", busiId=").append(busiId);
        sb.append(", templateId=").append(templateId);
        sb.append(", status=").append(status);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", ttsJsonData=").append(ttsJsonData);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}