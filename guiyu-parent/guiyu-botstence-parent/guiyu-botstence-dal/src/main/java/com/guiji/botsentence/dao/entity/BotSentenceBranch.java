package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceBranch implements Serializable {
    private String branchId;

    private String branchName;

    private Long seq;

    private String processId;

    private String templateId;

    private String response;

    private String next;

    private String intents;

    private String end;

    private String domain;

    private String isSpecialLimitFree;

    private String userAsk;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String lineName;

    private String isShow;

    private String respname;

    private String type;

    private String agentIntent;

    private String needAgent;

    private String weight;

    private String rule;

    private String keyWords;

    private static final long serialVersionUID = 1L;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next == null ? null : next.trim();
    }

    public String getIntents() {
        return intents;
    }

    public void setIntents(String intents) {
        this.intents = intents == null ? null : intents.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getIsSpecialLimitFree() {
        return isSpecialLimitFree;
    }

    public void setIsSpecialLimitFree(String isSpecialLimitFree) {
        this.isSpecialLimitFree = isSpecialLimitFree == null ? null : isSpecialLimitFree.trim();
    }

    public String getUserAsk() {
        return userAsk;
    }

    public void setUserAsk(String userAsk) {
        this.userAsk = userAsk == null ? null : userAsk.trim();
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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
    }

    public String getRespname() {
        return respname;
    }

    public void setRespname(String respname) {
        this.respname = respname == null ? null : respname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAgentIntent() {
        return agentIntent;
    }

    public void setAgentIntent(String agentIntent) {
        this.agentIntent = agentIntent == null ? null : agentIntent.trim();
    }

    public String getNeedAgent() {
        return needAgent;
    }

    public void setNeedAgent(String needAgent) {
        this.needAgent = needAgent == null ? null : needAgent.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", branchId=").append(branchId);
        sb.append(", branchName=").append(branchName);
        sb.append(", seq=").append(seq);
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", response=").append(response);
        sb.append(", next=").append(next);
        sb.append(", intents=").append(intents);
        sb.append(", end=").append(end);
        sb.append(", domain=").append(domain);
        sb.append(", isSpecialLimitFree=").append(isSpecialLimitFree);
        sb.append(", userAsk=").append(userAsk);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", lineName=").append(lineName);
        sb.append(", isShow=").append(isShow);
        sb.append(", respname=").append(respname);
        sb.append(", type=").append(type);
        sb.append(", agentIntent=").append(agentIntent);
        sb.append(", needAgent=").append(needAgent);
        sb.append(", weight=").append(weight);
        sb.append(", rule=").append(rule);
        sb.append(", keyWords=").append(keyWords);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}