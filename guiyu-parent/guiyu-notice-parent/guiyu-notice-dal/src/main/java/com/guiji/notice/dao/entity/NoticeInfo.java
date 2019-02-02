package com.guiji.notice.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class NoticeInfo implements Serializable {
    private Integer id;

    private String orgCode;

    private Integer noticeType;

    private String mailContent;

    private String smsContent;

    private String emailContent;

    private String emailSubject;

    private String weixinTemplateId;

    private String weixinUrl;

    private String weixinAppId;

    private String weixinPagePath;

    private String weixinData;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent == null ? null : mailContent.trim();
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent == null ? null : emailContent.trim();
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject == null ? null : emailSubject.trim();
    }

    public String getWeixinTemplateId() {
        return weixinTemplateId;
    }

    public void setWeixinTemplateId(String weixinTemplateId) {
        this.weixinTemplateId = weixinTemplateId == null ? null : weixinTemplateId.trim();
    }

    public String getWeixinUrl() {
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl == null ? null : weixinUrl.trim();
    }

    public String getWeixinAppId() {
        return weixinAppId;
    }

    public void setWeixinAppId(String weixinAppId) {
        this.weixinAppId = weixinAppId == null ? null : weixinAppId.trim();
    }

    public String getWeixinPagePath() {
        return weixinPagePath;
    }

    public void setWeixinPagePath(String weixinPagePath) {
        this.weixinPagePath = weixinPagePath == null ? null : weixinPagePath.trim();
    }

    public String getWeixinData() {
        return weixinData;
    }

    public void setWeixinData(String weixinData) {
        this.weixinData = weixinData == null ? null : weixinData.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", noticeType=").append(noticeType);
        sb.append(", mailContent=").append(mailContent);
        sb.append(", smsContent=").append(smsContent);
        sb.append(", emailContent=").append(emailContent);
        sb.append(", emailSubject=").append(emailSubject);
        sb.append(", weixinTemplateId=").append(weixinTemplateId);
        sb.append(", weixinUrl=").append(weixinUrl);
        sb.append(", weixinAppId=").append(weixinAppId);
        sb.append(", weixinPagePath=").append(weixinPagePath);
        sb.append(", weixinData=").append(weixinData);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}