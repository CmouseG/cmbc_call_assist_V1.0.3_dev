package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceDomain implements Serializable {
    private String domainId;

    private String domainName;

    private String templateId;

    private String processId;

    private String comDomain;

    private String category;

    private String ignoreButDomains;

    private String isInterrupt;

    private String isMainFlow;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String type;

    private String parent;

    private String parentId;

    private Integer positionX;

    private Integer positionY;

    private static final long serialVersionUID = 1L;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getComDomain() {
        return comDomain;
    }

    public void setComDomain(String comDomain) {
        this.comDomain = comDomain == null ? null : comDomain.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getIgnoreButDomains() {
        return ignoreButDomains;
    }

    public void setIgnoreButDomains(String ignoreButDomains) {
        this.ignoreButDomains = ignoreButDomains == null ? null : ignoreButDomains.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", domainId=").append(domainId);
        sb.append(", domainName=").append(domainName);
        sb.append(", templateId=").append(templateId);
        sb.append(", processId=").append(processId);
        sb.append(", comDomain=").append(comDomain);
        sb.append(", category=").append(category);
        sb.append(", ignoreButDomains=").append(ignoreButDomains);
        sb.append(", isInterrupt=").append(isInterrupt);
        sb.append(", isMainFlow=").append(isMainFlow);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", type=").append(type);
        sb.append(", parent=").append(parent);
        sb.append(", parentId=").append(parentId);
        sb.append(", positionX=").append(positionX);
        sb.append(", positionY=").append(positionY);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}