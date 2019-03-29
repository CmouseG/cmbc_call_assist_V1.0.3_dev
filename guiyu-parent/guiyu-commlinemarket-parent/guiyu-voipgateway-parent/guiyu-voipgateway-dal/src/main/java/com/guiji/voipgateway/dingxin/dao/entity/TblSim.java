package com.guiji.voipgateway.dingxin.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TblSim implements Serializable {
    private Integer uuid;

    private Integer recStatus;

    private String imsi;

    private String alias;

    private Integer adminStatus;

    private Integer oprStatus;

    private Integer runStatus;

    private Integer actionStatus;

    private Integer actionResult;

    private Integer almStatusBits;

    private Integer domainUuid;

    private Integer grpUuid;

    private Integer promotionGrpUuid;

    private Integer origZoneUuid;

    private Integer lastSiteUuid;

    private Integer nextSiteUuid;

    private Integer bkpUuid;

    private Integer localGwpUuid;

    private Integer lockGwpUuid;

    private String iccId;

    private String bindImei;

    private String pin1Code;

    private String pin2Code;

    private String puk1Code;

    private String puk2Code;

    private String operator;

    private String mobile;

    private String simNumber;

    private String smsc;

    private Integer moneyType;

    private Long prepaidFee;

    private Long totalCost;

    private Float lastBalance;

    private Float curBalance;

    private Integer leftCallTime;

    private Integer promCallTime;

    private Date lastGroupTime;

    private Date lastLoadTime;

    private Date createTime;

    private Date lastBindTime;

    private Date lastUsedTime;

    private Date lastPromTime;

    private Date lastBalanceTime;

    private Integer deactiveReason;

    private Integer lastDeactiveReason;

    private Integer blockedFlag;

    private Integer lowBalanceFlag;

    private Integer noBalanceFlag;

    private Integer promotionStatus;

    private Integer promotionCount;

    private Date promotionTime;

    private String promotionReport;

    private Integer hbmAcdShortCount;

    private Integer hbmAcdFailCount;

    private Integer hbmAcdSmsCount;

    private Integer hbmSmsFailCount;

    private Integer hbmCallFailCount;

    private Integer hbmDtmfFailCount;

    private Integer hbmRegFailCount;

    private Integer simRechargedFlag;

    private Integer paidListUuid;

    private Integer localSimFlag;

    private String detailDesc;

    private Float setCurBalance;

    private static final long serialVersionUID = 1L;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Integer getOprStatus() {
        return oprStatus;
    }

    public void setOprStatus(Integer oprStatus) {
        this.oprStatus = oprStatus;
    }

    public Integer getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Integer runStatus) {
        this.runStatus = runStatus;
    }

    public Integer getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Integer actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Integer getActionResult() {
        return actionResult;
    }

    public void setActionResult(Integer actionResult) {
        this.actionResult = actionResult;
    }

    public Integer getAlmStatusBits() {
        return almStatusBits;
    }

    public void setAlmStatusBits(Integer almStatusBits) {
        this.almStatusBits = almStatusBits;
    }

    public Integer getDomainUuid() {
        return domainUuid;
    }

    public void setDomainUuid(Integer domainUuid) {
        this.domainUuid = domainUuid;
    }

    public Integer getGrpUuid() {
        return grpUuid;
    }

    public void setGrpUuid(Integer grpUuid) {
        this.grpUuid = grpUuid;
    }

    public Integer getPromotionGrpUuid() {
        return promotionGrpUuid;
    }

    public void setPromotionGrpUuid(Integer promotionGrpUuid) {
        this.promotionGrpUuid = promotionGrpUuid;
    }

    public Integer getOrigZoneUuid() {
        return origZoneUuid;
    }

    public void setOrigZoneUuid(Integer origZoneUuid) {
        this.origZoneUuid = origZoneUuid;
    }

    public Integer getLastSiteUuid() {
        return lastSiteUuid;
    }

    public void setLastSiteUuid(Integer lastSiteUuid) {
        this.lastSiteUuid = lastSiteUuid;
    }

    public Integer getNextSiteUuid() {
        return nextSiteUuid;
    }

    public void setNextSiteUuid(Integer nextSiteUuid) {
        this.nextSiteUuid = nextSiteUuid;
    }

    public Integer getBkpUuid() {
        return bkpUuid;
    }

    public void setBkpUuid(Integer bkpUuid) {
        this.bkpUuid = bkpUuid;
    }

    public Integer getLocalGwpUuid() {
        return localGwpUuid;
    }

    public void setLocalGwpUuid(Integer localGwpUuid) {
        this.localGwpUuid = localGwpUuid;
    }

    public Integer getLockGwpUuid() {
        return lockGwpUuid;
    }

    public void setLockGwpUuid(Integer lockGwpUuid) {
        this.lockGwpUuid = lockGwpUuid;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId == null ? null : iccId.trim();
    }

    public String getBindImei() {
        return bindImei;
    }

    public void setBindImei(String bindImei) {
        this.bindImei = bindImei == null ? null : bindImei.trim();
    }

    public String getPin1Code() {
        return pin1Code;
    }

    public void setPin1Code(String pin1Code) {
        this.pin1Code = pin1Code == null ? null : pin1Code.trim();
    }

    public String getPin2Code() {
        return pin2Code;
    }

    public void setPin2Code(String pin2Code) {
        this.pin2Code = pin2Code == null ? null : pin2Code.trim();
    }

    public String getPuk1Code() {
        return puk1Code;
    }

    public void setPuk1Code(String puk1Code) {
        this.puk1Code = puk1Code == null ? null : puk1Code.trim();
    }

    public String getPuk2Code() {
        return puk2Code;
    }

    public void setPuk2Code(String puk2Code) {
        this.puk2Code = puk2Code == null ? null : puk2Code.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber == null ? null : simNumber.trim();
    }

    public String getSmsc() {
        return smsc;
    }

    public void setSmsc(String smsc) {
        this.smsc = smsc == null ? null : smsc.trim();
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Long getPrepaidFee() {
        return prepaidFee;
    }

    public void setPrepaidFee(Long prepaidFee) {
        this.prepaidFee = prepaidFee;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Float getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(Float lastBalance) {
        this.lastBalance = lastBalance;
    }

    public Float getCurBalance() {
        return curBalance;
    }

    public void setCurBalance(Float curBalance) {
        this.curBalance = curBalance;
    }

    public Integer getLeftCallTime() {
        return leftCallTime;
    }

    public void setLeftCallTime(Integer leftCallTime) {
        this.leftCallTime = leftCallTime;
    }

    public Integer getPromCallTime() {
        return promCallTime;
    }

    public void setPromCallTime(Integer promCallTime) {
        this.promCallTime = promCallTime;
    }

    public Date getLastGroupTime() {
        return lastGroupTime;
    }

    public void setLastGroupTime(Date lastGroupTime) {
        this.lastGroupTime = lastGroupTime;
    }

    public Date getLastLoadTime() {
        return lastLoadTime;
    }

    public void setLastLoadTime(Date lastLoadTime) {
        this.lastLoadTime = lastLoadTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastBindTime() {
        return lastBindTime;
    }

    public void setLastBindTime(Date lastBindTime) {
        this.lastBindTime = lastBindTime;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Date getLastPromTime() {
        return lastPromTime;
    }

    public void setLastPromTime(Date lastPromTime) {
        this.lastPromTime = lastPromTime;
    }

    public Date getLastBalanceTime() {
        return lastBalanceTime;
    }

    public void setLastBalanceTime(Date lastBalanceTime) {
        this.lastBalanceTime = lastBalanceTime;
    }

    public Integer getDeactiveReason() {
        return deactiveReason;
    }

    public void setDeactiveReason(Integer deactiveReason) {
        this.deactiveReason = deactiveReason;
    }

    public Integer getLastDeactiveReason() {
        return lastDeactiveReason;
    }

    public void setLastDeactiveReason(Integer lastDeactiveReason) {
        this.lastDeactiveReason = lastDeactiveReason;
    }

    public Integer getBlockedFlag() {
        return blockedFlag;
    }

    public void setBlockedFlag(Integer blockedFlag) {
        this.blockedFlag = blockedFlag;
    }

    public Integer getLowBalanceFlag() {
        return lowBalanceFlag;
    }

    public void setLowBalanceFlag(Integer lowBalanceFlag) {
        this.lowBalanceFlag = lowBalanceFlag;
    }

    public Integer getNoBalanceFlag() {
        return noBalanceFlag;
    }

    public void setNoBalanceFlag(Integer noBalanceFlag) {
        this.noBalanceFlag = noBalanceFlag;
    }

    public Integer getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(Integer promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public Integer getPromotionCount() {
        return promotionCount;
    }

    public void setPromotionCount(Integer promotionCount) {
        this.promotionCount = promotionCount;
    }

    public Date getPromotionTime() {
        return promotionTime;
    }

    public void setPromotionTime(Date promotionTime) {
        this.promotionTime = promotionTime;
    }

    public String getPromotionReport() {
        return promotionReport;
    }

    public void setPromotionReport(String promotionReport) {
        this.promotionReport = promotionReport == null ? null : promotionReport.trim();
    }

    public Integer getHbmAcdShortCount() {
        return hbmAcdShortCount;
    }

    public void setHbmAcdShortCount(Integer hbmAcdShortCount) {
        this.hbmAcdShortCount = hbmAcdShortCount;
    }

    public Integer getHbmAcdFailCount() {
        return hbmAcdFailCount;
    }

    public void setHbmAcdFailCount(Integer hbmAcdFailCount) {
        this.hbmAcdFailCount = hbmAcdFailCount;
    }

    public Integer getHbmAcdSmsCount() {
        return hbmAcdSmsCount;
    }

    public void setHbmAcdSmsCount(Integer hbmAcdSmsCount) {
        this.hbmAcdSmsCount = hbmAcdSmsCount;
    }

    public Integer getHbmSmsFailCount() {
        return hbmSmsFailCount;
    }

    public void setHbmSmsFailCount(Integer hbmSmsFailCount) {
        this.hbmSmsFailCount = hbmSmsFailCount;
    }

    public Integer getHbmCallFailCount() {
        return hbmCallFailCount;
    }

    public void setHbmCallFailCount(Integer hbmCallFailCount) {
        this.hbmCallFailCount = hbmCallFailCount;
    }

    public Integer getHbmDtmfFailCount() {
        return hbmDtmfFailCount;
    }

    public void setHbmDtmfFailCount(Integer hbmDtmfFailCount) {
        this.hbmDtmfFailCount = hbmDtmfFailCount;
    }

    public Integer getHbmRegFailCount() {
        return hbmRegFailCount;
    }

    public void setHbmRegFailCount(Integer hbmRegFailCount) {
        this.hbmRegFailCount = hbmRegFailCount;
    }

    public Integer getSimRechargedFlag() {
        return simRechargedFlag;
    }

    public void setSimRechargedFlag(Integer simRechargedFlag) {
        this.simRechargedFlag = simRechargedFlag;
    }

    public Integer getPaidListUuid() {
        return paidListUuid;
    }

    public void setPaidListUuid(Integer paidListUuid) {
        this.paidListUuid = paidListUuid;
    }

    public Integer getLocalSimFlag() {
        return localSimFlag;
    }

    public void setLocalSimFlag(Integer localSimFlag) {
        this.localSimFlag = localSimFlag;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc == null ? null : detailDesc.trim();
    }

    public Float getSetCurBalance() {
        return setCurBalance;
    }

    public void setSetCurBalance(Float setCurBalance) {
        this.setCurBalance = setCurBalance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", recStatus=").append(recStatus);
        sb.append(", imsi=").append(imsi);
        sb.append(", alias=").append(alias);
        sb.append(", adminStatus=").append(adminStatus);
        sb.append(", oprStatus=").append(oprStatus);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", actionStatus=").append(actionStatus);
        sb.append(", actionResult=").append(actionResult);
        sb.append(", almStatusBits=").append(almStatusBits);
        sb.append(", domainUuid=").append(domainUuid);
        sb.append(", grpUuid=").append(grpUuid);
        sb.append(", promotionGrpUuid=").append(promotionGrpUuid);
        sb.append(", origZoneUuid=").append(origZoneUuid);
        sb.append(", lastSiteUuid=").append(lastSiteUuid);
        sb.append(", nextSiteUuid=").append(nextSiteUuid);
        sb.append(", bkpUuid=").append(bkpUuid);
        sb.append(", localGwpUuid=").append(localGwpUuid);
        sb.append(", lockGwpUuid=").append(lockGwpUuid);
        sb.append(", iccId=").append(iccId);
        sb.append(", bindImei=").append(bindImei);
        sb.append(", pin1Code=").append(pin1Code);
        sb.append(", pin2Code=").append(pin2Code);
        sb.append(", puk1Code=").append(puk1Code);
        sb.append(", puk2Code=").append(puk2Code);
        sb.append(", operator=").append(operator);
        sb.append(", mobile=").append(mobile);
        sb.append(", simNumber=").append(simNumber);
        sb.append(", smsc=").append(smsc);
        sb.append(", moneyType=").append(moneyType);
        sb.append(", prepaidFee=").append(prepaidFee);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", lastBalance=").append(lastBalance);
        sb.append(", curBalance=").append(curBalance);
        sb.append(", leftCallTime=").append(leftCallTime);
        sb.append(", promCallTime=").append(promCallTime);
        sb.append(", lastGroupTime=").append(lastGroupTime);
        sb.append(", lastLoadTime=").append(lastLoadTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastBindTime=").append(lastBindTime);
        sb.append(", lastUsedTime=").append(lastUsedTime);
        sb.append(", lastPromTime=").append(lastPromTime);
        sb.append(", lastBalanceTime=").append(lastBalanceTime);
        sb.append(", deactiveReason=").append(deactiveReason);
        sb.append(", lastDeactiveReason=").append(lastDeactiveReason);
        sb.append(", blockedFlag=").append(blockedFlag);
        sb.append(", lowBalanceFlag=").append(lowBalanceFlag);
        sb.append(", noBalanceFlag=").append(noBalanceFlag);
        sb.append(", promotionStatus=").append(promotionStatus);
        sb.append(", promotionCount=").append(promotionCount);
        sb.append(", promotionTime=").append(promotionTime);
        sb.append(", promotionReport=").append(promotionReport);
        sb.append(", hbmAcdShortCount=").append(hbmAcdShortCount);
        sb.append(", hbmAcdFailCount=").append(hbmAcdFailCount);
        sb.append(", hbmAcdSmsCount=").append(hbmAcdSmsCount);
        sb.append(", hbmSmsFailCount=").append(hbmSmsFailCount);
        sb.append(", hbmCallFailCount=").append(hbmCallFailCount);
        sb.append(", hbmDtmfFailCount=").append(hbmDtmfFailCount);
        sb.append(", hbmRegFailCount=").append(hbmRegFailCount);
        sb.append(", simRechargedFlag=").append(simRechargedFlag);
        sb.append(", paidListUuid=").append(paidListUuid);
        sb.append(", localSimFlag=").append(localSimFlag);
        sb.append(", detailDesc=").append(detailDesc);
        sb.append(", setCurBalance=").append(setCurBalance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}