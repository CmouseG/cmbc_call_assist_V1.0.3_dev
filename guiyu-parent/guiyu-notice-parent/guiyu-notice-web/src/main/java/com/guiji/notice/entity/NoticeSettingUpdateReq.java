package com.guiji.notice.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

public class NoticeSettingUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Boolean isSendMail;

    private Boolean isSendWeixin;

    private Boolean isSendEmail;

    private Boolean isSendSms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "NoticeSettingUpdateReq{" +
                "id=" + id +
                ", isSendMail=" + isSendMail +
                ", isSendWeixin=" + isSendWeixin +
                ", isSendEmail=" + isSendEmail +
                ", isSendSms=" + isSendSms +
                '}';
    }
}
