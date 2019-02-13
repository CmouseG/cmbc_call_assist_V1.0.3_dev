package com.guiji.billing.dto;

import java.io.Serializable;
import java.util.Date;

public class CallChargingNotifyDto implements Serializable {

    private static final long serialVersionUID = 1617728883189467403L;

/*    *//**
     * 企业公司ID
     *//*
    private String companyId;*/
    /**
     * 企业用户ID
     */
    private Integer userId;

    /**
     * 号码
     */
    private String phone;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 通话时长 “秒”为单位
     */
    private Integer billSec;

    /**
     * 话务中心线路ID
     */
    private Integer lineId;

/*    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }*/

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBillSec() {
        return billSec;
    }

    public void setBillSec(Integer billSec) {
        this.billSec = billSec;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }
}
