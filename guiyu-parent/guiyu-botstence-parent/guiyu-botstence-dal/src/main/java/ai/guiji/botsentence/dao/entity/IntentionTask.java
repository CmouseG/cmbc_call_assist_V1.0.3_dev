package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class IntentionTask implements Serializable {
    private String intentionId;

    private String processId;

    private String templateId;

    private String templateName;

    private String version;

    private String intentionType;

    private Integer dialogueTimes;

    private String keyWords;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private static final long serialVersionUID = 1L;

    public String getIntentionId() {
        return intentionId;
    }

    public void setIntentionId(String intentionId) {
        this.intentionId = intentionId == null ? null : intentionId.trim();
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getIntentionType() {
        return intentionType;
    }

    public void setIntentionType(String intentionType) {
        this.intentionType = intentionType == null ? null : intentionType.trim();
    }

    public Integer getDialogueTimes() {
        return dialogueTimes;
    }

    public void setDialogueTimes(Integer dialogueTimes) {
        this.dialogueTimes = dialogueTimes;
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
        sb.append(", intentionId=").append(intentionId);
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", templateName=").append(templateName);
        sb.append(", version=").append(version);
        sb.append(", intentionType=").append(intentionType);
        sb.append(", dialogueTimes=").append(dialogueTimes);
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