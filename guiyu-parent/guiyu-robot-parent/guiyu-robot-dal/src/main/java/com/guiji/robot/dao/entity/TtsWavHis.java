package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsWavHis implements Serializable {
    private Integer id;

    private String seqId;

    private String busiId;

    private String templateId;

    private String reqParams;

    private String ttsTxtJsonData;

    private Integer status;

    private String errorMsg;

    private Integer errorType;

    private Integer errorTryNum;

    private Date crtTime;

    private String ttsJsonData;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
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

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams == null ? null : reqParams.trim();
    }

    public String getTtsTxtJsonData() {
        return ttsTxtJsonData;
    }

    public void setTtsTxtJsonData(String ttsTxtJsonData) {
        this.ttsTxtJsonData = ttsTxtJsonData == null ? null : ttsTxtJsonData.trim();
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

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public Integer getErrorTryNum() {
        return errorTryNum;
    }

    public void setErrorTryNum(Integer errorTryNum) {
        this.errorTryNum = errorTryNum;
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
        sb.append(", busiId=").append(busiId);
        sb.append(", templateId=").append(templateId);
        sb.append(", reqParams=").append(reqParams);
        sb.append(", ttsTxtJsonData=").append(ttsTxtJsonData);
        sb.append(", status=").append(status);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", errorType=").append(errorType);
        sb.append(", errorTryNum=").append(errorTryNum);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", ttsJsonData=").append(ttsJsonData);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}