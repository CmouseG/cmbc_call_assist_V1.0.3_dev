package com.guiji.notice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NoticeSettingVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    List<User> userList;

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
        this.orgCode = orgCode;
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
        this.receivers = receivers;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
