package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class VoliceInfo implements Serializable {
    private Long voliceId;

    private String voliceUrl;

    private String processId;

    private String templateId;

    private String domainName;

    private String type;

    private String num;

    private String content;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String name;

    private String flag;

    private Long oldId;

    private Boolean needTts;

    private static final long serialVersionUID = 1L;

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

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public Boolean getNeedTts() {
        return needTts;
    }

    public void setNeedTts(Boolean needTts) {
        this.needTts = needTts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", voliceId=").append(voliceId);
        sb.append(", voliceUrl=").append(voliceUrl);
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", domainName=").append(domainName);
        sb.append(", type=").append(type);
        sb.append(", num=").append(num);
        sb.append(", content=").append(content);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", name=").append(name);
        sb.append(", flag=").append(flag);
        sb.append(", oldId=").append(oldId);
        sb.append(", needTts=").append(needTts);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}