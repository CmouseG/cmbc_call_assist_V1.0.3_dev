package com.guiji.voipgateway.dingxin.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TblGwp implements Serializable {
    private Integer uuid;

    private Integer recStatus;

    private Integer portUuid;

    private String alias;

    private Integer bkpUuid;

    private Integer localSimUuid;

    private Integer domainUuid;

    private String localImei;

    private String currentImei;

    private String localImsi;

    private Integer workMode;

    private Integer modType;

    private Integer modStatus;

    private Integer modSignalVal;

    private Integer modSignalLevel;

    private Integer modBerVal;

    private Integer modErrorCount;

    private Date lastBindTime;

    private Date lastUsedTime;

    private Integer curCallStatus;

    private Integer curSmsStatus;

    private Integer curUssdStatus;

    private Integer curCallSn;

    private Integer curSmsSn;

    private Integer curUssdSn;

    private Integer roundTripDelay;

    private Integer packetAll;

    private Integer packetRetries;

    private Integer packetTimeout;

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

    public Integer getPortUuid() {
        return portUuid;
    }

    public void setPortUuid(Integer portUuid) {
        this.portUuid = portUuid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getBkpUuid() {
        return bkpUuid;
    }

    public void setBkpUuid(Integer bkpUuid) {
        this.bkpUuid = bkpUuid;
    }

    public Integer getLocalSimUuid() {
        return localSimUuid;
    }

    public void setLocalSimUuid(Integer localSimUuid) {
        this.localSimUuid = localSimUuid;
    }

    public Integer getDomainUuid() {
        return domainUuid;
    }

    public void setDomainUuid(Integer domainUuid) {
        this.domainUuid = domainUuid;
    }

    public String getLocalImei() {
        return localImei;
    }

    public void setLocalImei(String localImei) {
        this.localImei = localImei == null ? null : localImei.trim();
    }

    public String getCurrentImei() {
        return currentImei;
    }

    public void setCurrentImei(String currentImei) {
        this.currentImei = currentImei == null ? null : currentImei.trim();
    }

    public String getLocalImsi() {
        return localImsi;
    }

    public void setLocalImsi(String localImsi) {
        this.localImsi = localImsi == null ? null : localImsi.trim();
    }

    public Integer getWorkMode() {
        return workMode;
    }

    public void setWorkMode(Integer workMode) {
        this.workMode = workMode;
    }

    public Integer getModType() {
        return modType;
    }

    public void setModType(Integer modType) {
        this.modType = modType;
    }

    public Integer getModStatus() {
        return modStatus;
    }

    public void setModStatus(Integer modStatus) {
        this.modStatus = modStatus;
    }

    public Integer getModSignalVal() {
        return modSignalVal;
    }

    public void setModSignalVal(Integer modSignalVal) {
        this.modSignalVal = modSignalVal;
    }

    public Integer getModSignalLevel() {
        return modSignalLevel;
    }

    public void setModSignalLevel(Integer modSignalLevel) {
        this.modSignalLevel = modSignalLevel;
    }

    public Integer getModBerVal() {
        return modBerVal;
    }

    public void setModBerVal(Integer modBerVal) {
        this.modBerVal = modBerVal;
    }

    public Integer getModErrorCount() {
        return modErrorCount;
    }

    public void setModErrorCount(Integer modErrorCount) {
        this.modErrorCount = modErrorCount;
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

    public Integer getCurCallStatus() {
        return curCallStatus;
    }

    public void setCurCallStatus(Integer curCallStatus) {
        this.curCallStatus = curCallStatus;
    }

    public Integer getCurSmsStatus() {
        return curSmsStatus;
    }

    public void setCurSmsStatus(Integer curSmsStatus) {
        this.curSmsStatus = curSmsStatus;
    }

    public Integer getCurUssdStatus() {
        return curUssdStatus;
    }

    public void setCurUssdStatus(Integer curUssdStatus) {
        this.curUssdStatus = curUssdStatus;
    }

    public Integer getCurCallSn() {
        return curCallSn;
    }

    public void setCurCallSn(Integer curCallSn) {
        this.curCallSn = curCallSn;
    }

    public Integer getCurSmsSn() {
        return curSmsSn;
    }

    public void setCurSmsSn(Integer curSmsSn) {
        this.curSmsSn = curSmsSn;
    }

    public Integer getCurUssdSn() {
        return curUssdSn;
    }

    public void setCurUssdSn(Integer curUssdSn) {
        this.curUssdSn = curUssdSn;
    }

    public Integer getRoundTripDelay() {
        return roundTripDelay;
    }

    public void setRoundTripDelay(Integer roundTripDelay) {
        this.roundTripDelay = roundTripDelay;
    }

    public Integer getPacketAll() {
        return packetAll;
    }

    public void setPacketAll(Integer packetAll) {
        this.packetAll = packetAll;
    }

    public Integer getPacketRetries() {
        return packetRetries;
    }

    public void setPacketRetries(Integer packetRetries) {
        this.packetRetries = packetRetries;
    }

    public Integer getPacketTimeout() {
        return packetTimeout;
    }

    public void setPacketTimeout(Integer packetTimeout) {
        this.packetTimeout = packetTimeout;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", recStatus=").append(recStatus);
        sb.append(", portUuid=").append(portUuid);
        sb.append(", alias=").append(alias);
        sb.append(", bkpUuid=").append(bkpUuid);
        sb.append(", localSimUuid=").append(localSimUuid);
        sb.append(", domainUuid=").append(domainUuid);
        sb.append(", localImei=").append(localImei);
        sb.append(", currentImei=").append(currentImei);
        sb.append(", localImsi=").append(localImsi);
        sb.append(", workMode=").append(workMode);
        sb.append(", modType=").append(modType);
        sb.append(", modStatus=").append(modStatus);
        sb.append(", modSignalVal=").append(modSignalVal);
        sb.append(", modSignalLevel=").append(modSignalLevel);
        sb.append(", modBerVal=").append(modBerVal);
        sb.append(", modErrorCount=").append(modErrorCount);
        sb.append(", lastBindTime=").append(lastBindTime);
        sb.append(", lastUsedTime=").append(lastUsedTime);
        sb.append(", curCallStatus=").append(curCallStatus);
        sb.append(", curSmsStatus=").append(curSmsStatus);
        sb.append(", curUssdStatus=").append(curUssdStatus);
        sb.append(", curCallSn=").append(curCallSn);
        sb.append(", curSmsSn=").append(curSmsSn);
        sb.append(", curUssdSn=").append(curUssdSn);
        sb.append(", roundTripDelay=").append(roundTripDelay);
        sb.append(", packetAll=").append(packetAll);
        sb.append(", packetRetries=").append(packetRetries);
        sb.append(", packetTimeout=").append(packetTimeout);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}