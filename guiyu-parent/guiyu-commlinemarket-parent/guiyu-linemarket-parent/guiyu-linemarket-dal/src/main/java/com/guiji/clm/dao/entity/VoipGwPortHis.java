package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VoipGwPortHis implements Serializable {
    private Integer id;

    private Integer companyId;

    private Integer devId;

    private Integer port;

    private Integer lineId;

    private String alias;

    private Integer gwId;

    private Integer sipAccount;

    private Integer sipPwd;

    private BigDecimal univalent;

    private String phoneNo;

    private String userId;

    private String orgCode;

    private String crtUser;

    private Date crtTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getGwId() {
        return gwId;
    }

    public void setGwId(Integer gwId) {
        this.gwId = gwId;
    }

    public Integer getSipAccount() {
        return sipAccount;
    }

    public void setSipAccount(Integer sipAccount) {
        this.sipAccount = sipAccount;
    }

    public Integer getSipPwd() {
        return sipPwd;
    }

    public void setSipPwd(Integer sipPwd) {
        this.sipPwd = sipPwd;
    }

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", devId=").append(devId);
        sb.append(", port=").append(port);
        sb.append(", lineId=").append(lineId);
        sb.append(", alias=").append(alias);
        sb.append(", gwId=").append(gwId);
        sb.append(", sipAccount=").append(sipAccount);
        sb.append(", sipPwd=").append(sipPwd);
        sb.append(", univalent=").append(univalent);
        sb.append(", phoneNo=").append(phoneNo);
        sb.append(", userId=").append(userId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}