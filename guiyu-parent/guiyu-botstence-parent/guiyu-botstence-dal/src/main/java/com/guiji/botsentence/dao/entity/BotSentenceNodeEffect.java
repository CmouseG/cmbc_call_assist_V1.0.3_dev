package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceNodeEffect implements Serializable {
    private String nodeId;

    private String nodeName;

    private String processId;

    private String nodeType;

    private String templateId;

    private String version;

    private String content;

    private Long voliceId;

    private String voliceUrl;

    private String isInterrupt;

    private String isMainFlow;

    private String keyWords;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private static final long serialVersionUID = 1L;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getVoliceId() {
        return voliceId;
    }

    public void setVoliceId(Long voliceId) {
        this.voliceId = voliceId;
    }

    public String getVoliceUrl() {
        return voliceUrl;
    }

    public void setVoliceUrl(String voliceUrl) {
        this.voliceUrl = voliceUrl == null ? null : voliceUrl.trim();
    }

    public String getIsInterrupt() {
        return isInterrupt;
    }

    public void setIsInterrupt(String isInterrupt) {
        this.isInterrupt = isInterrupt == null ? null : isInterrupt.trim();
    }

    public String getIsMainFlow() {
        return isMainFlow;
    }

    public void setIsMainFlow(String isMainFlow) {
        this.isMainFlow = isMainFlow == null ? null : isMainFlow.trim();
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public Date getLstUpdateTime() {
        return lstUpdateTime;
    }

    public void setLstUpdateTime(Date lstUpdateTime) {
        this.lstUpdateTime = lstUpdateTime;
    }

    public String getLstUpdateUser() {
        return lstUpdateUser;
    }

    public void setLstUpdateUser(String lstUpdateUser) {
        this.lstUpdateUser = lstUpdateUser == null ? null : lstUpdateUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nodeId=").append(nodeId);
        sb.append(", nodeName=").append(nodeName);
        sb.append(", processId=").append(processId);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", templateId=").append(templateId);
        sb.append(", version=").append(version);
        sb.append(", content=").append(content);
        sb.append(", voliceId=").append(voliceId);
        sb.append(", voliceUrl=").append(voliceUrl);
        sb.append(", isInterrupt=").append(isInterrupt);
        sb.append(", isMainFlow=").append(isMainFlow);
        sb.append(", keyWords=").append(keyWords);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}