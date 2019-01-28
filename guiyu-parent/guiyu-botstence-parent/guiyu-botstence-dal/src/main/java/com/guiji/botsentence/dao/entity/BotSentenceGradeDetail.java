package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceGradeDetail implements Serializable {
    private Integer id;

    private String processId;

    private String templateId;

    private String name;

    private String showInfo;

    private String domainType;

    private String domainName;

    private String refuseType;

    private String refuseTimes;

    private String dialogTimesType;

    private String dialogTimes;

    private String dialogDurationType;

    private String dialogDuration;

    private String askType;

    private String askName;

    private String askTimes;

    private String evaluate;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String gradeOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo == null ? null : showInfo.trim();
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType == null ? null : domainType.trim();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType == null ? null : refuseType.trim();
    }

    public String getRefuseTimes() {
        return refuseTimes;
    }

    public void setRefuseTimes(String refuseTimes) {
        this.refuseTimes = refuseTimes == null ? null : refuseTimes.trim();
    }

    public String getDialogTimesType() {
        return dialogTimesType;
    }

    public void setDialogTimesType(String dialogTimesType) {
        this.dialogTimesType = dialogTimesType == null ? null : dialogTimesType.trim();
    }

    public String getDialogTimes() {
        return dialogTimes;
    }

    public void setDialogTimes(String dialogTimes) {
        this.dialogTimes = dialogTimes == null ? null : dialogTimes.trim();
    }

    public String getDialogDurationType() {
        return dialogDurationType;
    }

    public void setDialogDurationType(String dialogDurationType) {
        this.dialogDurationType = dialogDurationType == null ? null : dialogDurationType.trim();
    }

    public String getDialogDuration() {
        return dialogDuration;
    }

    public void setDialogDuration(String dialogDuration) {
        this.dialogDuration = dialogDuration == null ? null : dialogDuration.trim();
    }

    public String getAskType() {
        return askType;
    }

    public void setAskType(String askType) {
        this.askType = askType == null ? null : askType.trim();
    }

    public String getAskName() {
        return askName;
    }

    public void setAskName(String askName) {
        this.askName = askName == null ? null : askName.trim();
    }

    public String getAskTimes() {
        return askTimes;
    }

    public void setAskTimes(String askTimes) {
        this.askTimes = askTimes == null ? null : askTimes.trim();
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
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

    public String getGradeOrder() {
        return gradeOrder;
    }

    public void setGradeOrder(String gradeOrder) {
        this.gradeOrder = gradeOrder == null ? null : gradeOrder.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", name=").append(name);
        sb.append(", showInfo=").append(showInfo);
        sb.append(", domainType=").append(domainType);
        sb.append(", domainName=").append(domainName);
        sb.append(", refuseType=").append(refuseType);
        sb.append(", refuseTimes=").append(refuseTimes);
        sb.append(", dialogTimesType=").append(dialogTimesType);
        sb.append(", dialogTimes=").append(dialogTimes);
        sb.append(", dialogDurationType=").append(dialogDurationType);
        sb.append(", dialogDuration=").append(dialogDuration);
        sb.append(", askType=").append(askType);
        sb.append(", askName=").append(askName);
        sb.append(", askTimes=").append(askTimes);
        sb.append(", evaluate=").append(evaluate);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", gradeOrder=").append(gradeOrder);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}