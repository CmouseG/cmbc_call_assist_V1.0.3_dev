package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SipLineBaseInfo implements Serializable {
    private Integer id;

    private String lineName;

    private Integer lineId;

    private String supplier;

    private Integer supplierType;

    private Integer lineStatus;

    private String sipIp;

    private Integer sipPort;

    private String sipDomain;

    private String sipAccount;

    private String sipPsd;

    private String codec;

    private Boolean regFlag;

    private String callerNum;

    private String belongOrgCode;

    private String nonLocalPrefix;

    private String destinationPrefix;

    private Integer maxConcurrentCalls;

    private Integer useConcurrentCalls;

    private Integer callDirec;

    private String beginDate;

    private String endDate;

    private String timeBegin;

    private String timeEnd;

    private String overtArea;

    private String industrys;

    private String areas;

    private String exceptAreas;

    private BigDecimal contractUnivalent;

    private BigDecimal univalent;

    private Boolean feeOrNot;

    private Integer lineFeeType;

    private String remark;

    private Integer sipShareId;

    private String orgCode;

    private String crtUser;

    private Date crtTime;

    private Date updateTime;

    private String updateUser;

    private String belongUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
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

    public String getSipDomain() {
        return sipDomain;
    }

    public void setSipDomain(String sipDomain) {
        this.sipDomain = sipDomain == null ? null : sipDomain.trim();
    }

    public String getSipAccount() {
        return sipAccount;
    }

    public void setSipAccount(String sipAccount) {
        this.sipAccount = sipAccount == null ? null : sipAccount.trim();
    }

    public String getSipPsd() {
        return sipPsd;
    }

    public void setSipPsd(String sipPsd) {
        this.sipPsd = sipPsd == null ? null : sipPsd.trim();
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec == null ? null : codec.trim();
    }

    public Boolean getRegFlag() {
        return regFlag;
    }

    public void setRegFlag(Boolean regFlag) {
        this.regFlag = regFlag;
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum == null ? null : callerNum.trim();
    }

    public String getBelongOrgCode() {
        return belongOrgCode;
    }

    public void setBelongOrgCode(String belongOrgCode) {
        this.belongOrgCode = belongOrgCode == null ? null : belongOrgCode.trim();
    }

    public String getNonLocalPrefix() {
        return nonLocalPrefix;
    }

    public void setNonLocalPrefix(String nonLocalPrefix) {
        this.nonLocalPrefix = nonLocalPrefix == null ? null : nonLocalPrefix.trim();
    }

    public String getDestinationPrefix() {
        return destinationPrefix;
    }

    public void setDestinationPrefix(String destinationPrefix) {
        this.destinationPrefix = destinationPrefix == null ? null : destinationPrefix.trim();
    }

    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public Integer getUseConcurrentCalls() {
        return useConcurrentCalls;
    }

    public void setUseConcurrentCalls(Integer useConcurrentCalls) {
        this.useConcurrentCalls = useConcurrentCalls;
    }

    public Integer getCallDirec() {
        return callDirec;
    }

    public void setCallDirec(Integer callDirec) {
        this.callDirec = callDirec;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin == null ? null : timeBegin.trim();
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd == null ? null : timeEnd.trim();
    }

    public String getOvertArea() {
        return overtArea;
    }

    public void setOvertArea(String overtArea) {
        this.overtArea = overtArea == null ? null : overtArea.trim();
    }

    public String getIndustrys() {
        return industrys;
    }

    public void setIndustrys(String industrys) {
        this.industrys = industrys == null ? null : industrys.trim();
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    public String getExceptAreas() {
        return exceptAreas;
    }

    public void setExceptAreas(String exceptAreas) {
        this.exceptAreas = exceptAreas == null ? null : exceptAreas.trim();
    }

    public BigDecimal getContractUnivalent() {
        return contractUnivalent;
    }

    public void setContractUnivalent(BigDecimal contractUnivalent) {
        this.contractUnivalent = contractUnivalent;
    }

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
    }

    public Boolean getFeeOrNot() {
        return feeOrNot;
    }

    public void setFeeOrNot(Boolean feeOrNot) {
        this.feeOrNot = feeOrNot;
    }

    public Integer getLineFeeType() {
        return lineFeeType;
    }

    public void setLineFeeType(Integer lineFeeType) {
        this.lineFeeType = lineFeeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSipShareId() {
        return sipShareId;
    }

    public void setSipShareId(Integer sipShareId) {
        this.sipShareId = sipShareId;
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

    public String getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(String belongUser) {
        this.belongUser = belongUser == null ? null : belongUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lineName=").append(lineName);
        sb.append(", lineId=").append(lineId);
        sb.append(", supplier=").append(supplier);
        sb.append(", supplierType=").append(supplierType);
        sb.append(", lineStatus=").append(lineStatus);
        sb.append(", sipIp=").append(sipIp);
        sb.append(", sipPort=").append(sipPort);
        sb.append(", sipDomain=").append(sipDomain);
        sb.append(", sipAccount=").append(sipAccount);
        sb.append(", sipPsd=").append(sipPsd);
        sb.append(", codec=").append(codec);
        sb.append(", regFlag=").append(regFlag);
        sb.append(", callerNum=").append(callerNum);
        sb.append(", belongOrgCode=").append(belongOrgCode);
        sb.append(", nonLocalPrefix=").append(nonLocalPrefix);
        sb.append(", destinationPrefix=").append(destinationPrefix);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", useConcurrentCalls=").append(useConcurrentCalls);
        sb.append(", callDirec=").append(callDirec);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", timeBegin=").append(timeBegin);
        sb.append(", timeEnd=").append(timeEnd);
        sb.append(", overtArea=").append(overtArea);
        sb.append(", industrys=").append(industrys);
        sb.append(", areas=").append(areas);
        sb.append(", exceptAreas=").append(exceptAreas);
        sb.append(", contractUnivalent=").append(contractUnivalent);
        sb.append(", univalent=").append(univalent);
        sb.append(", feeOrNot=").append(feeOrNot);
        sb.append(", lineFeeType=").append(lineFeeType);
        sb.append(", remark=").append(remark);
        sb.append(", sipShareId=").append(sipShareId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", belongUser=").append(belongUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}