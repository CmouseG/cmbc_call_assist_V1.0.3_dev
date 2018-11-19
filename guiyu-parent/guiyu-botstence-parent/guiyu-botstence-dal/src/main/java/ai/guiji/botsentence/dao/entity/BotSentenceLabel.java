package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceLabel implements Serializable {
    private String labelId;

    private String processId;

    private String labelName;

    private Integer conversationCount;

    private String keywords;

    private String displayKeywords;

    private String displayKeywordsBefore;

    private Integer specialCount;

    private Integer usedTimeS;

    private Integer denyCount;

    private Integer busyCount;

    private Double scoreUp;

    private Double scoreLow;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String showName;

    private String helpDetail;

    private String annotation;

    private static final long serialVersionUID = 1L;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId == null ? null : labelId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getConversationCount() {
        return conversationCount;
    }

    public void setConversationCount(Integer conversationCount) {
        this.conversationCount = conversationCount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getDisplayKeywords() {
        return displayKeywords;
    }

    public void setDisplayKeywords(String displayKeywords) {
        this.displayKeywords = displayKeywords == null ? null : displayKeywords.trim();
    }

    public String getDisplayKeywordsBefore() {
        return displayKeywordsBefore;
    }

    public void setDisplayKeywordsBefore(String displayKeywordsBefore) {
        this.displayKeywordsBefore = displayKeywordsBefore == null ? null : displayKeywordsBefore.trim();
    }

    public Integer getSpecialCount() {
        return specialCount;
    }

    public void setSpecialCount(Integer specialCount) {
        this.specialCount = specialCount;
    }

    public Integer getUsedTimeS() {
        return usedTimeS;
    }

    public void setUsedTimeS(Integer usedTimeS) {
        this.usedTimeS = usedTimeS;
    }

    public Integer getDenyCount() {
        return denyCount;
    }

    public void setDenyCount(Integer denyCount) {
        this.denyCount = denyCount;
    }

    public Integer getBusyCount() {
        return busyCount;
    }

    public void setBusyCount(Integer busyCount) {
        this.busyCount = busyCount;
    }

    public Double getScoreUp() {
        return scoreUp;
    }

    public void setScoreUp(Double scoreUp) {
        this.scoreUp = scoreUp;
    }

    public Double getScoreLow() {
        return scoreLow;
    }

    public void setScoreLow(Double scoreLow) {
        this.scoreLow = scoreLow;
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

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getHelpDetail() {
        return helpDetail;
    }

    public void setHelpDetail(String helpDetail) {
        this.helpDetail = helpDetail == null ? null : helpDetail.trim();
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation == null ? null : annotation.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", labelId=").append(labelId);
        sb.append(", processId=").append(processId);
        sb.append(", labelName=").append(labelName);
        sb.append(", conversationCount=").append(conversationCount);
        sb.append(", keywords=").append(keywords);
        sb.append(", displayKeywords=").append(displayKeywords);
        sb.append(", displayKeywordsBefore=").append(displayKeywordsBefore);
        sb.append(", specialCount=").append(specialCount);
        sb.append(", usedTimeS=").append(usedTimeS);
        sb.append(", denyCount=").append(denyCount);
        sb.append(", busyCount=").append(busyCount);
        sb.append(", scoreUp=").append(scoreUp);
        sb.append(", scoreLow=").append(scoreLow);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", showName=").append(showName);
        sb.append(", helpDetail=").append(helpDetail);
        sb.append(", annotation=").append(annotation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}