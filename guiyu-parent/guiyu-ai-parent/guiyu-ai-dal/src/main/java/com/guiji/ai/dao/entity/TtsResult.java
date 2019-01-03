package com.guiji.ai.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsResult implements Serializable {
    private Integer id;

    private String busId;

    private String ttsIp;

    private String ttsPort;

    private String content;

    private String model;

    private String audioUrl;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getTtsIp() {
        return ttsIp;
    }

    public void setTtsIp(String ttsIp) {
        this.ttsIp = ttsIp == null ? null : ttsIp.trim();
    }

    public String getTtsPort() {
        return ttsPort;
    }

    public void setTtsPort(String ttsPort) {
        this.ttsPort = ttsPort == null ? null : ttsPort.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl == null ? null : audioUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", busId=").append(busId);
        sb.append(", ttsIp=").append(ttsIp);
        sb.append(", ttsPort=").append(ttsPort);
        sb.append(", content=").append(content);
        sb.append(", model=").append(model);
        sb.append(", audioUrl=").append(audioUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}