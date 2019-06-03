package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * volice_info
 * @author 
 */
public class VoliceInfo implements Serializable {
    /**
     * 录音ID
     */
    private Long voliceId;

    /**
     * 录音文件URL
     */
    private String voliceUrl;

    /**
     * 话术流程编号
     */
    private String processId;

    /**
     * 话术模板编号
     */
    private String templateId;

    private String domainName;

    /**
     * 类型
     */
    private String type;

    /**
     * 编号
     */
    private String num;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 最后修改时间
     */
    private Date lstUpdateTime;

    /**
     * 最后修改人
     */
    private String lstUpdateUser;

    private String name;

    private String flag;

    private Long oldId;

    /**
     * 是否需要tts合成
     */
    private Boolean needTts;

    /**
     * tts合成类型：1-变量;2-变量加内容
     */
    private Integer ttsCompositeType;

    /**
     * 录音时长
     */
    private Integer times;

    /**
     * 录音文件名
     */
    private String wavName;

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
        this.voliceUrl = voliceUrl;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        this.crtUser = crtUser;
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
        this.lstUpdateUser = lstUpdateUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public Integer getTtsCompositeType() {
        return ttsCompositeType;
    }

    public void setTtsCompositeType(Integer ttsCompositeType) {
        this.ttsCompositeType = ttsCompositeType;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getWavName() {
        return wavName;
    }

    public void setWavName(String wavName) {
        this.wavName = wavName;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VoliceInfo other = (VoliceInfo) that;
        return (this.getVoliceId() == null ? other.getVoliceId() == null : this.getVoliceId().equals(other.getVoliceId()))
            && (this.getVoliceUrl() == null ? other.getVoliceUrl() == null : this.getVoliceUrl().equals(other.getVoliceUrl()))
            && (this.getProcessId() == null ? other.getProcessId() == null : this.getProcessId().equals(other.getProcessId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getDomainName() == null ? other.getDomainName() == null : this.getDomainName().equals(other.getDomainName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCrtTime() == null ? other.getCrtTime() == null : this.getCrtTime().equals(other.getCrtTime()))
            && (this.getCrtUser() == null ? other.getCrtUser() == null : this.getCrtUser().equals(other.getCrtUser()))
            && (this.getLstUpdateTime() == null ? other.getLstUpdateTime() == null : this.getLstUpdateTime().equals(other.getLstUpdateTime()))
            && (this.getLstUpdateUser() == null ? other.getLstUpdateUser() == null : this.getLstUpdateUser().equals(other.getLstUpdateUser()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getOldId() == null ? other.getOldId() == null : this.getOldId().equals(other.getOldId()))
            && (this.getNeedTts() == null ? other.getNeedTts() == null : this.getNeedTts().equals(other.getNeedTts()))
            && (this.getTtsCompositeType() == null ? other.getTtsCompositeType() == null : this.getTtsCompositeType().equals(other.getTtsCompositeType()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()))
            && (this.getWavName() == null ? other.getWavName() == null : this.getWavName().equals(other.getWavName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVoliceId() == null) ? 0 : getVoliceId().hashCode());
        result = prime * result + ((getVoliceUrl() == null) ? 0 : getVoliceUrl().hashCode());
        result = prime * result + ((getProcessId() == null) ? 0 : getProcessId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getDomainName() == null) ? 0 : getDomainName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCrtTime() == null) ? 0 : getCrtTime().hashCode());
        result = prime * result + ((getCrtUser() == null) ? 0 : getCrtUser().hashCode());
        result = prime * result + ((getLstUpdateTime() == null) ? 0 : getLstUpdateTime().hashCode());
        result = prime * result + ((getLstUpdateUser() == null) ? 0 : getLstUpdateUser().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getOldId() == null) ? 0 : getOldId().hashCode());
        result = prime * result + ((getNeedTts() == null) ? 0 : getNeedTts().hashCode());
        result = prime * result + ((getTtsCompositeType() == null) ? 0 : getTtsCompositeType().hashCode());
        result = prime * result + ((getTimes() == null) ? 0 : getTimes().hashCode());
        result = prime * result + ((getWavName() == null) ? 0 : getWavName().hashCode());
        return result;
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
        sb.append(", ttsCompositeType=").append(ttsCompositeType);
        sb.append(", times=").append(times);
        sb.append(", wavName=").append(wavName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}