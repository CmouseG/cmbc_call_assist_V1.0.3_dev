package com.guiji.notice.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class NoticeSetting implements Serializable {
    private Integer id;

    private String orgCode;

    private Integer noticeOverType;

    private Integer noticeType;

    private Boolean isSendMail;

    private Boolean isSendWeixin;

    private Boolean isSendEmail;

    private Boolean isSendSms;

    private String receivers;

    private Integer updateUser;

    private Date updateTime;

    private Integer createaUser;

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

    public Integer getNoticeOverType() {
        return noticeOverType;
    }

    public void setNoticeOverType(Integer noticeOverType) {
        this.noticeOverType = noticeOverType;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Boolean getIsSendMail() {
        return isSendMail;
    }

    public void setIsSendMail(Boolean isSendMail) {
        this.isSendMail = isSendMail;
    }

    public Boolean getIsSendWeixin() {
        return isSendWeixin;
    }

    public void setIsSendWeixin(Boolean isSendWeixin) {
        this.isSendWeixin = isSendWeixin;
    }

    public Boolean getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(Boolean isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public Boolean getIsSendSms() {
        return isSendSms;
    }

    public void setIsSendSms(Boolean isSendSms) {
        this.isSendSms = isSendSms;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers == null ? null : receivers.trim();
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateaUser() {
        return createaUser;
    }

    public void setCreateaUser(Integer createaUser) {
        this.createaUser = createaUser;
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
        sb.append(", noticeOverType=").append(noticeOverType);
        sb.append(", noticeType=").append(noticeType);
        sb.append(", isSendMail=").append(isSendMail);
        sb.append(", isSendWeixin=").append(isSendWeixin);
        sb.append(", isSendEmail=").append(isSendEmail);
        sb.append(", isSendSms=").append(isSendSms);
        sb.append(", receivers=").append(receivers);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createaUser=").append(createaUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}