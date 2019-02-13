package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VoipGwInfo implements Serializable {
    private Integer id;

    private String gwName;

    private String gwBrand;

    private String gwVersion;

    private Integer companyId;

    private Integer devId;

    private Integer portNum;

    private String sipIp;

    private Integer sipPort;

    private Integer startSipAccount;

    private Integer startSipPwd;

    private Integer sipAccountStep;

    private Integer sipPwdStep;

    private BigDecimal univalent;

    private Integer regType;

    private Integer gwRegStatus;

    private String gwIp;

    private String userId;

    private String orgCode;

    private String crtUser;

    private Date crtTime;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGwName() {
        return gwName;
    }

    public void setGwName(String gwName) {
        this.gwName = gwName == null ? null : gwName.trim();
    }

    public String getGwBrand() {
        return gwBrand;
    }

    public void setGwBrand(String gwBrand) {
        this.gwBrand = gwBrand == null ? null : gwBrand.trim();
    }

    public String getGwVersion() {
        return gwVersion;
    }

    public void setGwVersion(String gwVersion) {
        this.gwVersion = gwVersion == null ? null : gwVersion.trim();
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

    public Integer getPortNum() {
        return portNum;
    }

    public void setPortNum(Integer portNum) {
        this.portNum = portNum;
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp == null ? null : sipIp.trim();
    }

    public Integer getSipPort() {
        return sipPort;
    }

    public void setSipPort(Integer sipPort) {
        this.sipPort = sipPort;
    }

    public Integer getStartSipAccount() {
        return startSipAccount;
    }

    public void setStartSipAccount(Integer startSipAccount) {
        this.startSipAccount = startSipAccount;
    }

    public Integer getStartSipPwd() {
        return startSipPwd;
    }

    public void setStartSipPwd(Integer startSipPwd) {
        this.startSipPwd = startSipPwd;
    }

    public Integer getSipAccountStep() {
        return sipAccountStep;
    }

    public void setSipAccountStep(Integer sipAccountStep) {
        this.sipAccountStep = sipAccountStep;
    }

    public Integer getSipPwdStep() {
        return sipPwdStep;
    }

    public void setSipPwdStep(Integer sipPwdStep) {
        this.sipPwdStep = sipPwdStep;
    }

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }

    public Integer getGwRegStatus() {
        return gwRegStatus;
    }

    public void setGwRegStatus(Integer gwRegStatus) {
        this.gwRegStatus = gwRegStatus;
    }

    public String getGwIp() {
        return gwIp;
    }

    public void setGwIp(String gwIp) {
        this.gwIp = gwIp == null ? null : gwIp.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gwName=").append(gwName);
        sb.append(", gwBrand=").append(gwBrand);
        sb.append(", gwVersion=").append(gwVersion);
        sb.append(", companyId=").append(companyId);
        sb.append(", devId=").append(devId);
        sb.append(", portNum=").append(portNum);
        sb.append(", sipIp=").append(sipIp);
        sb.append(", sipPort=").append(sipPort);
        sb.append(", startSipAccount=").append(startSipAccount);
        sb.append(", startSipPwd=").append(startSipPwd);
        sb.append(", sipAccountStep=").append(sipAccountStep);
        sb.append(", sipPwdStep=").append(sipPwdStep);
        sb.append(", univalent=").append(univalent);
        sb.append(", regType=").append(regType);
        sb.append(", gwRegStatus=").append(gwRegStatus);
        sb.append(", gwIp=").append(gwIp);
        sb.append(", userId=").append(userId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}