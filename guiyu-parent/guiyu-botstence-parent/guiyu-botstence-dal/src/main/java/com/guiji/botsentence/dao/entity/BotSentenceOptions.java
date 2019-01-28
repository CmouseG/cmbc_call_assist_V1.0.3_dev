package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceOptions implements Serializable {
    private String optionsId;

    private String processId;

    private String templateId;

    private Boolean checkSim;

    private Boolean curDomainPrior;

    private Boolean useNotMatchLogic;

    private String notMatchSolution;

    private Boolean useEndfilesList;

    private String trade;

    private String tempname;

    private Boolean nonInterruptableStart;

    private String nonInterruptable;

    private Boolean silenceWaitStart;

    private Integer silenceWaitSecs;

    private Integer silenceWaitTime;

    private Boolean silenceAsEmpty;

    private Boolean userDefineMatchOrder;

    private Boolean grubStart;

    private Boolean positiveInterruptable;

    private Boolean interruptableDomainStart;

    private Boolean globalInterruptableDomainsStart;

    private String globalInterruptableDomains;

    private Boolean ignoreButDomainsStart;

    private Boolean ignoreUserSentenceStart;

    private Boolean ignoreButNegativeStart;

    private String asrEngine;

    private Boolean multiKeywordAll;

    private Boolean notMatchLess4ToStart;

    private Integer notMatchLessNum;

    private Boolean notMatchToStart;

    private Boolean noWordsToStart;

    private Boolean interruptionConfigStart;

    private Integer interruptWordsNum;

    private Integer interruptMinInterval;

    private String voice;

    private Boolean specialLimitStart;

    private Integer specialLimit;

    private Boolean surveyStart;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private static final long serialVersionUID = 1L;

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId == null ? null : optionsId.trim();
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

    public Boolean getCheckSim() {
        return checkSim;
    }

    public void setCheckSim(Boolean checkSim) {
        this.checkSim = checkSim;
    }

    public Boolean getCurDomainPrior() {
        return curDomainPrior;
    }

    public void setCurDomainPrior(Boolean curDomainPrior) {
        this.curDomainPrior = curDomainPrior;
    }

    public Boolean getUseNotMatchLogic() {
        return useNotMatchLogic;
    }

    public void setUseNotMatchLogic(Boolean useNotMatchLogic) {
        this.useNotMatchLogic = useNotMatchLogic;
    }

    public String getNotMatchSolution() {
        return notMatchSolution;
    }

    public void setNotMatchSolution(String notMatchSolution) {
        this.notMatchSolution = notMatchSolution == null ? null : notMatchSolution.trim();
    }

    public Boolean getUseEndfilesList() {
        return useEndfilesList;
    }

    public void setUseEndfilesList(Boolean useEndfilesList) {
        this.useEndfilesList = useEndfilesList;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade == null ? null : trade.trim();
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public Boolean getNonInterruptableStart() {
        return nonInterruptableStart;
    }

    public void setNonInterruptableStart(Boolean nonInterruptableStart) {
        this.nonInterruptableStart = nonInterruptableStart;
    }

    public String getNonInterruptable() {
        return nonInterruptable;
    }

    public void setNonInterruptable(String nonInterruptable) {
        this.nonInterruptable = nonInterruptable == null ? null : nonInterruptable.trim();
    }

    public Boolean getSilenceWaitStart() {
        return silenceWaitStart;
    }

    public void setSilenceWaitStart(Boolean silenceWaitStart) {
        this.silenceWaitStart = silenceWaitStart;
    }

    public Integer getSilenceWaitSecs() {
        return silenceWaitSecs;
    }

    public void setSilenceWaitSecs(Integer silenceWaitSecs) {
        this.silenceWaitSecs = silenceWaitSecs;
    }

    public Integer getSilenceWaitTime() {
        return silenceWaitTime;
    }

    public void setSilenceWaitTime(Integer silenceWaitTime) {
        this.silenceWaitTime = silenceWaitTime;
    }

    public Boolean getSilenceAsEmpty() {
        return silenceAsEmpty;
    }

    public void setSilenceAsEmpty(Boolean silenceAsEmpty) {
        this.silenceAsEmpty = silenceAsEmpty;
    }

    public Boolean getUserDefineMatchOrder() {
        return userDefineMatchOrder;
    }

    public void setUserDefineMatchOrder(Boolean userDefineMatchOrder) {
        this.userDefineMatchOrder = userDefineMatchOrder;
    }

    public Boolean getGrubStart() {
        return grubStart;
    }

    public void setGrubStart(Boolean grubStart) {
        this.grubStart = grubStart;
    }

    public Boolean getPositiveInterruptable() {
        return positiveInterruptable;
    }

    public void setPositiveInterruptable(Boolean positiveInterruptable) {
        this.positiveInterruptable = positiveInterruptable;
    }

    public Boolean getInterruptableDomainStart() {
        return interruptableDomainStart;
    }

    public void setInterruptableDomainStart(Boolean interruptableDomainStart) {
        this.interruptableDomainStart = interruptableDomainStart;
    }

    public Boolean getGlobalInterruptableDomainsStart() {
        return globalInterruptableDomainsStart;
    }

    public void setGlobalInterruptableDomainsStart(Boolean globalInterruptableDomainsStart) {
        this.globalInterruptableDomainsStart = globalInterruptableDomainsStart;
    }

    public String getGlobalInterruptableDomains() {
        return globalInterruptableDomains;
    }

    public void setGlobalInterruptableDomains(String globalInterruptableDomains) {
        this.globalInterruptableDomains = globalInterruptableDomains == null ? null : globalInterruptableDomains.trim();
    }

    public Boolean getIgnoreButDomainsStart() {
        return ignoreButDomainsStart;
    }

    public void setIgnoreButDomainsStart(Boolean ignoreButDomainsStart) {
        this.ignoreButDomainsStart = ignoreButDomainsStart;
    }

    public Boolean getIgnoreUserSentenceStart() {
        return ignoreUserSentenceStart;
    }

    public void setIgnoreUserSentenceStart(Boolean ignoreUserSentenceStart) {
        this.ignoreUserSentenceStart = ignoreUserSentenceStart;
    }

    public Boolean getIgnoreButNegativeStart() {
        return ignoreButNegativeStart;
    }

    public void setIgnoreButNegativeStart(Boolean ignoreButNegativeStart) {
        this.ignoreButNegativeStart = ignoreButNegativeStart;
    }

    public String getAsrEngine() {
        return asrEngine;
    }

    public void setAsrEngine(String asrEngine) {
        this.asrEngine = asrEngine == null ? null : asrEngine.trim();
    }

    public Boolean getMultiKeywordAll() {
        return multiKeywordAll;
    }

    public void setMultiKeywordAll(Boolean multiKeywordAll) {
        this.multiKeywordAll = multiKeywordAll;
    }

    public Boolean getNotMatchLess4ToStart() {
        return notMatchLess4ToStart;
    }

    public void setNotMatchLess4ToStart(Boolean notMatchLess4ToStart) {
        this.notMatchLess4ToStart = notMatchLess4ToStart;
    }

    public Integer getNotMatchLessNum() {
        return notMatchLessNum;
    }

    public void setNotMatchLessNum(Integer notMatchLessNum) {
        this.notMatchLessNum = notMatchLessNum;
    }

    public Boolean getNotMatchToStart() {
        return notMatchToStart;
    }

    public void setNotMatchToStart(Boolean notMatchToStart) {
        this.notMatchToStart = notMatchToStart;
    }

    public Boolean getNoWordsToStart() {
        return noWordsToStart;
    }

    public void setNoWordsToStart(Boolean noWordsToStart) {
        this.noWordsToStart = noWordsToStart;
    }

    public Boolean getInterruptionConfigStart() {
        return interruptionConfigStart;
    }

    public void setInterruptionConfigStart(Boolean interruptionConfigStart) {
        this.interruptionConfigStart = interruptionConfigStart;
    }

    public Integer getInterruptWordsNum() {
        return interruptWordsNum;
    }

    public void setInterruptWordsNum(Integer interruptWordsNum) {
        this.interruptWordsNum = interruptWordsNum;
    }

    public Integer getInterruptMinInterval() {
        return interruptMinInterval;
    }

    public void setInterruptMinInterval(Integer interruptMinInterval) {
        this.interruptMinInterval = interruptMinInterval;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice == null ? null : voice.trim();
    }

    public Boolean getSpecialLimitStart() {
        return specialLimitStart;
    }

    public void setSpecialLimitStart(Boolean specialLimitStart) {
        this.specialLimitStart = specialLimitStart;
    }

    public Integer getSpecialLimit() {
        return specialLimit;
    }

    public void setSpecialLimit(Integer specialLimit) {
        this.specialLimit = specialLimit;
    }

    public Boolean getSurveyStart() {
        return surveyStart;
    }

    public void setSurveyStart(Boolean surveyStart) {
        this.surveyStart = surveyStart;
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
        sb.append(", optionsId=").append(optionsId);
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", checkSim=").append(checkSim);
        sb.append(", curDomainPrior=").append(curDomainPrior);
        sb.append(", useNotMatchLogic=").append(useNotMatchLogic);
        sb.append(", notMatchSolution=").append(notMatchSolution);
        sb.append(", useEndfilesList=").append(useEndfilesList);
        sb.append(", trade=").append(trade);
        sb.append(", tempname=").append(tempname);
        sb.append(", nonInterruptableStart=").append(nonInterruptableStart);
        sb.append(", nonInterruptable=").append(nonInterruptable);
        sb.append(", silenceWaitStart=").append(silenceWaitStart);
        sb.append(", silenceWaitSecs=").append(silenceWaitSecs);
        sb.append(", silenceWaitTime=").append(silenceWaitTime);
        sb.append(", silenceAsEmpty=").append(silenceAsEmpty);
        sb.append(", userDefineMatchOrder=").append(userDefineMatchOrder);
        sb.append(", grubStart=").append(grubStart);
        sb.append(", positiveInterruptable=").append(positiveInterruptable);
        sb.append(", interruptableDomainStart=").append(interruptableDomainStart);
        sb.append(", globalInterruptableDomainsStart=").append(globalInterruptableDomainsStart);
        sb.append(", globalInterruptableDomains=").append(globalInterruptableDomains);
        sb.append(", ignoreButDomainsStart=").append(ignoreButDomainsStart);
        sb.append(", ignoreUserSentenceStart=").append(ignoreUserSentenceStart);
        sb.append(", ignoreButNegativeStart=").append(ignoreButNegativeStart);
        sb.append(", asrEngine=").append(asrEngine);
        sb.append(", multiKeywordAll=").append(multiKeywordAll);
        sb.append(", notMatchLess4ToStart=").append(notMatchLess4ToStart);
        sb.append(", notMatchLessNum=").append(notMatchLessNum);
        sb.append(", notMatchToStart=").append(notMatchToStart);
        sb.append(", noWordsToStart=").append(noWordsToStart);
        sb.append(", interruptionConfigStart=").append(interruptionConfigStart);
        sb.append(", interruptWordsNum=").append(interruptWordsNum);
        sb.append(", interruptMinInterval=").append(interruptMinInterval);
        sb.append(", voice=").append(voice);
        sb.append(", specialLimitStart=").append(specialLimitStart);
        sb.append(", specialLimit=").append(specialLimit);
        sb.append(", surveyStart=").append(surveyStart);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}