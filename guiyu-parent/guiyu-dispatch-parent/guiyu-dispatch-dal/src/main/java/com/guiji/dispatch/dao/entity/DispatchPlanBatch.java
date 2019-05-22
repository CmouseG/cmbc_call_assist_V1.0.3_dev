package com.guiji.dispatch.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class DispatchPlanBatch implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Integer statusShow;

    private Integer statusNotify;

    private Integer times;

    private Date gmtCreate;

    private Date gmtModified;

    private String orgCode;

    private String callbackUrl;

    private Integer totalNum;

    private String singleCallbackUrl;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatusShow() {
        return statusShow;
    }

    public void setStatusShow(Integer statusShow) {
        this.statusShow = statusShow;
    }

    public Integer getStatusNotify() {
        return statusNotify;
    }

    public void setStatusNotify(Integer statusNotify) {
        this.statusNotify = statusNotify;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getSingleCallbackUrl() {
        return singleCallbackUrl;
    }

    public void setSingleCallbackUrl(String singleCallbackUrl) {
        this.singleCallbackUrl = singleCallbackUrl == null ? null : singleCallbackUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", statusShow=").append(statusShow);
        sb.append(", statusNotify=").append(statusNotify);
        sb.append(", times=").append(times);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", callbackUrl=").append(callbackUrl);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", singleCallbackUrl=").append(singleCallbackUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}