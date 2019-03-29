package com.guiji.voipgateway.dingxin.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TblNe implements Serializable {
    private Integer uuid;

    private Integer recStatus;

    private String alias;

    private Integer adminStatus;

    private Integer oprStatus;

    private Integer runStatus;

    private Integer actionStatus;

    private Integer actionResult;

    private Integer almStatusBits;

    private Integer domainUuid;

    private Integer siteUuid;

    private Integer policyUuid;

    private Integer vendorId;

    private Integer productId;

    private String productName;

    private String packageVersion;

    private Date packageBuildTime;

    private String detailVer;

    private String sipAgent;

    private String sipOwner;

    private String cliPrompt;

    private Integer dhcpDefault;

    private Integer ipType;

    private String ipAddr;

    private Integer macAddrNum;

    private String madeFactory;

    private String madeSite;

    private String madeDate;

    private String testSite;

    private String testDate;

    private String password;

    private Integer encryptType;

    private String outerIpAddr;

    private String innerIpAddr;

    private Date createTime;

    private Date updateTime;

    private Date lastRegTime;

    private Integer regFailCount;

    private Integer upgradeType;

    private Integer upgradeForceFlag;

    private String targetSoftwareVer;

    private Integer upgradeStatus;

    private Integer lastUpgradeResult;

    private Date lastUpgradeTime;

    private Integer portTotalCount;

    private Integer portWorkCount;

    private String detailDesc;

    private Integer nextNeAlarmSn;

    private Integer syslogStatus;

    private Integer logSysUuid;

    private Date syslogBeginDate;

    private Date syslogEndDate;

    private Integer syslogDebugLevel;

    private Integer cdrLogFlag;

    private Integer signalLogFlag;

    private Integer mediaLogFlag;

    private Integer systemLogFlag;

    private Integer mngLogFlag;

    private Integer cdrLogFalg;

    private Integer sipsrvLockFlag;

    private String primarySipServer;

    private Integer primarySipsrvPort;

    private String secondarySipServer;

    private Integer secondarySipsrvPort;

    private Integer ntpStatus;

    private Integer autoRebootFlag;

    private Integer switchChipStatus;

    private Integer curCpu;

    private Integer avgCpu5;

    private Integer avgCpu60;

    private Integer avgCpu600;

    private Integer freeMem;

    private Integer totalMem;

    private Integer memUsage;

    private Date devTime;

    private Integer alarmFlag;

    private Integer locMcc;

    private Integer locMnc;

    private Integer locLac;

    private Integer locCellId;

    private Integer locStatus;

    private Float locLat;

    private Float locLng;

    private Integer upgradeFlag;

    private Integer timeChipStatus;

    private Integer allocMem;

    private Integer alarmShortPeriod;

    private Integer alarmLongPeriod;

    private Integer statusShortPeriod;

    private Integer statusLongPeriod;

    private Integer staticsPeriod;

    private Integer lockStatus;

    private Integer memAosUsage;

    private Integer memLinuxUsage;

    private Integer natMode;

    private Integer natStatus;

    private Integer storeUsage;

    private Integer ethLinkMode;

    private Integer portType1;

    private Integer portCount1;

    private Integer portType2;

    private Integer portCount2;

    private Integer portType3;

    private Integer portCount3;

    private Integer portType4;

    private Integer portCount4;

    private Integer portType5;

    private Integer portCount5;

    private Integer portType6;

    private Integer portCount6;

    private Integer portType7;

    private Integer portCount7;

    private Integer portType8;

    private Integer portCount8;

    private byte[] productSn;

    private byte[] macAddrBegin;

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

    public Integer getSiteUuid() {
        return siteUuid;
    }

    public void setSiteUuid(Integer siteUuid) {
        this.siteUuid = siteUuid;
    }

    public Integer getPolicyUuid() {
        return policyUuid;
    }

    public void setPolicyUuid(Integer policyUuid) {
        this.policyUuid = policyUuid;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion == null ? null : packageVersion.trim();
    }

    public Date getPackageBuildTime() {
        return packageBuildTime;
    }

    public void setPackageBuildTime(Date packageBuildTime) {
        this.packageBuildTime = packageBuildTime;
    }

    public String getDetailVer() {
        return detailVer;
    }

    public void setDetailVer(String detailVer) {
        this.detailVer = detailVer == null ? null : detailVer.trim();
    }

    public String getSipAgent() {
        return sipAgent;
    }

    public void setSipAgent(String sipAgent) {
        this.sipAgent = sipAgent == null ? null : sipAgent.trim();
    }

    public String getSipOwner() {
        return sipOwner;
    }

    public void setSipOwner(String sipOwner) {
        this.sipOwner = sipOwner == null ? null : sipOwner.trim();
    }

    public String getCliPrompt() {
        return cliPrompt;
    }

    public void setCliPrompt(String cliPrompt) {
        this.cliPrompt = cliPrompt == null ? null : cliPrompt.trim();
    }

    public Integer getDhcpDefault() {
        return dhcpDefault;
    }

    public void setDhcpDefault(Integer dhcpDefault) {
        this.dhcpDefault = dhcpDefault;
    }

    public Integer getIpType() {
        return ipType;
    }

    public void setIpType(Integer ipType) {
        this.ipType = ipType;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    public Integer getMacAddrNum() {
        return macAddrNum;
    }

    public void setMacAddrNum(Integer macAddrNum) {
        this.macAddrNum = macAddrNum;
    }

    public String getMadeFactory() {
        return madeFactory;
    }

    public void setMadeFactory(String madeFactory) {
        this.madeFactory = madeFactory == null ? null : madeFactory.trim();
    }

    public String getMadeSite() {
        return madeSite;
    }

    public void setMadeSite(String madeSite) {
        this.madeSite = madeSite == null ? null : madeSite.trim();
    }

    public String getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(String madeDate) {
        this.madeDate = madeDate == null ? null : madeDate.trim();
    }

    public String getTestSite() {
        return testSite;
    }

    public void setTestSite(String testSite) {
        this.testSite = testSite == null ? null : testSite.trim();
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate == null ? null : testDate.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(Integer encryptType) {
        this.encryptType = encryptType;
    }

    public String getOuterIpAddr() {
        return outerIpAddr;
    }

    public void setOuterIpAddr(String outerIpAddr) {
        this.outerIpAddr = outerIpAddr == null ? null : outerIpAddr.trim();
    }

    public String getInnerIpAddr() {
        return innerIpAddr;
    }

    public void setInnerIpAddr(String innerIpAddr) {
        this.innerIpAddr = innerIpAddr == null ? null : innerIpAddr.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastRegTime() {
        return lastRegTime;
    }

    public void setLastRegTime(Date lastRegTime) {
        this.lastRegTime = lastRegTime;
    }

    public Integer getRegFailCount() {
        return regFailCount;
    }

    public void setRegFailCount(Integer regFailCount) {
        this.regFailCount = regFailCount;
    }

    public Integer getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(Integer upgradeType) {
        this.upgradeType = upgradeType;
    }

    public Integer getUpgradeForceFlag() {
        return upgradeForceFlag;
    }

    public void setUpgradeForceFlag(Integer upgradeForceFlag) {
        this.upgradeForceFlag = upgradeForceFlag;
    }

    public String getTargetSoftwareVer() {
        return targetSoftwareVer;
    }

    public void setTargetSoftwareVer(String targetSoftwareVer) {
        this.targetSoftwareVer = targetSoftwareVer == null ? null : targetSoftwareVer.trim();
    }

    public Integer getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(Integer upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public Integer getLastUpgradeResult() {
        return lastUpgradeResult;
    }

    public void setLastUpgradeResult(Integer lastUpgradeResult) {
        this.lastUpgradeResult = lastUpgradeResult;
    }

    public Date getLastUpgradeTime() {
        return lastUpgradeTime;
    }

    public void setLastUpgradeTime(Date lastUpgradeTime) {
        this.lastUpgradeTime = lastUpgradeTime;
    }

    public Integer getPortTotalCount() {
        return portTotalCount;
    }

    public void setPortTotalCount(Integer portTotalCount) {
        this.portTotalCount = portTotalCount;
    }

    public Integer getPortWorkCount() {
        return portWorkCount;
    }

    public void setPortWorkCount(Integer portWorkCount) {
        this.portWorkCount = portWorkCount;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc == null ? null : detailDesc.trim();
    }

    public Integer getNextNeAlarmSn() {
        return nextNeAlarmSn;
    }

    public void setNextNeAlarmSn(Integer nextNeAlarmSn) {
        this.nextNeAlarmSn = nextNeAlarmSn;
    }

    public Integer getSyslogStatus() {
        return syslogStatus;
    }

    public void setSyslogStatus(Integer syslogStatus) {
        this.syslogStatus = syslogStatus;
    }

    public Integer getLogSysUuid() {
        return logSysUuid;
    }

    public void setLogSysUuid(Integer logSysUuid) {
        this.logSysUuid = logSysUuid;
    }

    public Date getSyslogBeginDate() {
        return syslogBeginDate;
    }

    public void setSyslogBeginDate(Date syslogBeginDate) {
        this.syslogBeginDate = syslogBeginDate;
    }

    public Date getSyslogEndDate() {
        return syslogEndDate;
    }

    public void setSyslogEndDate(Date syslogEndDate) {
        this.syslogEndDate = syslogEndDate;
    }

    public Integer getSyslogDebugLevel() {
        return syslogDebugLevel;
    }

    public void setSyslogDebugLevel(Integer syslogDebugLevel) {
        this.syslogDebugLevel = syslogDebugLevel;
    }

    public Integer getCdrLogFlag() {
        return cdrLogFlag;
    }

    public void setCdrLogFlag(Integer cdrLogFlag) {
        this.cdrLogFlag = cdrLogFlag;
    }

    public Integer getSignalLogFlag() {
        return signalLogFlag;
    }

    public void setSignalLogFlag(Integer signalLogFlag) {
        this.signalLogFlag = signalLogFlag;
    }

    public Integer getMediaLogFlag() {
        return mediaLogFlag;
    }

    public void setMediaLogFlag(Integer mediaLogFlag) {
        this.mediaLogFlag = mediaLogFlag;
    }

    public Integer getSystemLogFlag() {
        return systemLogFlag;
    }

    public void setSystemLogFlag(Integer systemLogFlag) {
        this.systemLogFlag = systemLogFlag;
    }

    public Integer getMngLogFlag() {
        return mngLogFlag;
    }

    public void setMngLogFlag(Integer mngLogFlag) {
        this.mngLogFlag = mngLogFlag;
    }

    public Integer getCdrLogFalg() {
        return cdrLogFalg;
    }

    public void setCdrLogFalg(Integer cdrLogFalg) {
        this.cdrLogFalg = cdrLogFalg;
    }

    public Integer getSipsrvLockFlag() {
        return sipsrvLockFlag;
    }

    public void setSipsrvLockFlag(Integer sipsrvLockFlag) {
        this.sipsrvLockFlag = sipsrvLockFlag;
    }

    public String getPrimarySipServer() {
        return primarySipServer;
    }

    public void setPrimarySipServer(String primarySipServer) {
        this.primarySipServer = primarySipServer == null ? null : primarySipServer.trim();
    }

    public Integer getPrimarySipsrvPort() {
        return primarySipsrvPort;
    }

    public void setPrimarySipsrvPort(Integer primarySipsrvPort) {
        this.primarySipsrvPort = primarySipsrvPort;
    }

    public String getSecondarySipServer() {
        return secondarySipServer;
    }

    public void setSecondarySipServer(String secondarySipServer) {
        this.secondarySipServer = secondarySipServer == null ? null : secondarySipServer.trim();
    }

    public Integer getSecondarySipsrvPort() {
        return secondarySipsrvPort;
    }

    public void setSecondarySipsrvPort(Integer secondarySipsrvPort) {
        this.secondarySipsrvPort = secondarySipsrvPort;
    }

    public Integer getNtpStatus() {
        return ntpStatus;
    }

    public void setNtpStatus(Integer ntpStatus) {
        this.ntpStatus = ntpStatus;
    }

    public Integer getAutoRebootFlag() {
        return autoRebootFlag;
    }

    public void setAutoRebootFlag(Integer autoRebootFlag) {
        this.autoRebootFlag = autoRebootFlag;
    }

    public Integer getSwitchChipStatus() {
        return switchChipStatus;
    }

    public void setSwitchChipStatus(Integer switchChipStatus) {
        this.switchChipStatus = switchChipStatus;
    }

    public Integer getCurCpu() {
        return curCpu;
    }

    public void setCurCpu(Integer curCpu) {
        this.curCpu = curCpu;
    }

    public Integer getAvgCpu5() {
        return avgCpu5;
    }

    public void setAvgCpu5(Integer avgCpu5) {
        this.avgCpu5 = avgCpu5;
    }

    public Integer getAvgCpu60() {
        return avgCpu60;
    }

    public void setAvgCpu60(Integer avgCpu60) {
        this.avgCpu60 = avgCpu60;
    }

    public Integer getAvgCpu600() {
        return avgCpu600;
    }

    public void setAvgCpu600(Integer avgCpu600) {
        this.avgCpu600 = avgCpu600;
    }

    public Integer getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(Integer freeMem) {
        this.freeMem = freeMem;
    }

    public Integer getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(Integer totalMem) {
        this.totalMem = totalMem;
    }

    public Integer getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(Integer memUsage) {
        this.memUsage = memUsage;
    }

    public Date getDevTime() {
        return devTime;
    }

    public void setDevTime(Date devTime) {
        this.devTime = devTime;
    }

    public Integer getAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(Integer alarmFlag) {
        this.alarmFlag = alarmFlag;
    }

    public Integer getLocMcc() {
        return locMcc;
    }

    public void setLocMcc(Integer locMcc) {
        this.locMcc = locMcc;
    }

    public Integer getLocMnc() {
        return locMnc;
    }

    public void setLocMnc(Integer locMnc) {
        this.locMnc = locMnc;
    }

    public Integer getLocLac() {
        return locLac;
    }

    public void setLocLac(Integer locLac) {
        this.locLac = locLac;
    }

    public Integer getLocCellId() {
        return locCellId;
    }

    public void setLocCellId(Integer locCellId) {
        this.locCellId = locCellId;
    }

    public Integer getLocStatus() {
        return locStatus;
    }

    public void setLocStatus(Integer locStatus) {
        this.locStatus = locStatus;
    }

    public Float getLocLat() {
        return locLat;
    }

    public void setLocLat(Float locLat) {
        this.locLat = locLat;
    }

    public Float getLocLng() {
        return locLng;
    }

    public void setLocLng(Float locLng) {
        this.locLng = locLng;
    }

    public Integer getUpgradeFlag() {
        return upgradeFlag;
    }

    public void setUpgradeFlag(Integer upgradeFlag) {
        this.upgradeFlag = upgradeFlag;
    }

    public Integer getTimeChipStatus() {
        return timeChipStatus;
    }

    public void setTimeChipStatus(Integer timeChipStatus) {
        this.timeChipStatus = timeChipStatus;
    }

    public Integer getAllocMem() {
        return allocMem;
    }

    public void setAllocMem(Integer allocMem) {
        this.allocMem = allocMem;
    }

    public Integer getAlarmShortPeriod() {
        return alarmShortPeriod;
    }

    public void setAlarmShortPeriod(Integer alarmShortPeriod) {
        this.alarmShortPeriod = alarmShortPeriod;
    }

    public Integer getAlarmLongPeriod() {
        return alarmLongPeriod;
    }

    public void setAlarmLongPeriod(Integer alarmLongPeriod) {
        this.alarmLongPeriod = alarmLongPeriod;
    }

    public Integer getStatusShortPeriod() {
        return statusShortPeriod;
    }

    public void setStatusShortPeriod(Integer statusShortPeriod) {
        this.statusShortPeriod = statusShortPeriod;
    }

    public Integer getStatusLongPeriod() {
        return statusLongPeriod;
    }

    public void setStatusLongPeriod(Integer statusLongPeriod) {
        this.statusLongPeriod = statusLongPeriod;
    }

    public Integer getStaticsPeriod() {
        return staticsPeriod;
    }

    public void setStaticsPeriod(Integer staticsPeriod) {
        this.staticsPeriod = staticsPeriod;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getMemAosUsage() {
        return memAosUsage;
    }

    public void setMemAosUsage(Integer memAosUsage) {
        this.memAosUsage = memAosUsage;
    }

    public Integer getMemLinuxUsage() {
        return memLinuxUsage;
    }

    public void setMemLinuxUsage(Integer memLinuxUsage) {
        this.memLinuxUsage = memLinuxUsage;
    }

    public Integer getNatMode() {
        return natMode;
    }

    public void setNatMode(Integer natMode) {
        this.natMode = natMode;
    }

    public Integer getNatStatus() {
        return natStatus;
    }

    public void setNatStatus(Integer natStatus) {
        this.natStatus = natStatus;
    }

    public Integer getStoreUsage() {
        return storeUsage;
    }

    public void setStoreUsage(Integer storeUsage) {
        this.storeUsage = storeUsage;
    }

    public Integer getEthLinkMode() {
        return ethLinkMode;
    }

    public void setEthLinkMode(Integer ethLinkMode) {
        this.ethLinkMode = ethLinkMode;
    }

    public Integer getPortType1() {
        return portType1;
    }

    public void setPortType1(Integer portType1) {
        this.portType1 = portType1;
    }

    public Integer getPortCount1() {
        return portCount1;
    }

    public void setPortCount1(Integer portCount1) {
        this.portCount1 = portCount1;
    }

    public Integer getPortType2() {
        return portType2;
    }

    public void setPortType2(Integer portType2) {
        this.portType2 = portType2;
    }

    public Integer getPortCount2() {
        return portCount2;
    }

    public void setPortCount2(Integer portCount2) {
        this.portCount2 = portCount2;
    }

    public Integer getPortType3() {
        return portType3;
    }

    public void setPortType3(Integer portType3) {
        this.portType3 = portType3;
    }

    public Integer getPortCount3() {
        return portCount3;
    }

    public void setPortCount3(Integer portCount3) {
        this.portCount3 = portCount3;
    }

    public Integer getPortType4() {
        return portType4;
    }

    public void setPortType4(Integer portType4) {
        this.portType4 = portType4;
    }

    public Integer getPortCount4() {
        return portCount4;
    }

    public void setPortCount4(Integer portCount4) {
        this.portCount4 = portCount4;
    }

    public Integer getPortType5() {
        return portType5;
    }

    public void setPortType5(Integer portType5) {
        this.portType5 = portType5;
    }

    public Integer getPortCount5() {
        return portCount5;
    }

    public void setPortCount5(Integer portCount5) {
        this.portCount5 = portCount5;
    }

    public Integer getPortType6() {
        return portType6;
    }

    public void setPortType6(Integer portType6) {
        this.portType6 = portType6;
    }

    public Integer getPortCount6() {
        return portCount6;
    }

    public void setPortCount6(Integer portCount6) {
        this.portCount6 = portCount6;
    }

    public Integer getPortType7() {
        return portType7;
    }

    public void setPortType7(Integer portType7) {
        this.portType7 = portType7;
    }

    public Integer getPortCount7() {
        return portCount7;
    }

    public void setPortCount7(Integer portCount7) {
        this.portCount7 = portCount7;
    }

    public Integer getPortType8() {
        return portType8;
    }

    public void setPortType8(Integer portType8) {
        this.portType8 = portType8;
    }

    public Integer getPortCount8() {
        return portCount8;
    }

    public void setPortCount8(Integer portCount8) {
        this.portCount8 = portCount8;
    }

    public byte[] getProductSn() {
        return productSn;
    }

    public void setProductSn(byte[] productSn) {
        this.productSn = productSn;
    }

    public byte[] getMacAddrBegin() {
        return macAddrBegin;
    }

    public void setMacAddrBegin(byte[] macAddrBegin) {
        this.macAddrBegin = macAddrBegin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", recStatus=").append(recStatus);
        sb.append(", alias=").append(alias);
        sb.append(", adminStatus=").append(adminStatus);
        sb.append(", oprStatus=").append(oprStatus);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", actionStatus=").append(actionStatus);
        sb.append(", actionResult=").append(actionResult);
        sb.append(", almStatusBits=").append(almStatusBits);
        sb.append(", domainUuid=").append(domainUuid);
        sb.append(", siteUuid=").append(siteUuid);
        sb.append(", policyUuid=").append(policyUuid);
        sb.append(", vendorId=").append(vendorId);
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", packageVersion=").append(packageVersion);
        sb.append(", packageBuildTime=").append(packageBuildTime);
        sb.append(", detailVer=").append(detailVer);
        sb.append(", sipAgent=").append(sipAgent);
        sb.append(", sipOwner=").append(sipOwner);
        sb.append(", cliPrompt=").append(cliPrompt);
        sb.append(", dhcpDefault=").append(dhcpDefault);
        sb.append(", ipType=").append(ipType);
        sb.append(", ipAddr=").append(ipAddr);
        sb.append(", macAddrNum=").append(macAddrNum);
        sb.append(", madeFactory=").append(madeFactory);
        sb.append(", madeSite=").append(madeSite);
        sb.append(", madeDate=").append(madeDate);
        sb.append(", testSite=").append(testSite);
        sb.append(", testDate=").append(testDate);
        sb.append(", password=").append(password);
        sb.append(", encryptType=").append(encryptType);
        sb.append(", outerIpAddr=").append(outerIpAddr);
        sb.append(", innerIpAddr=").append(innerIpAddr);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", lastRegTime=").append(lastRegTime);
        sb.append(", regFailCount=").append(regFailCount);
        sb.append(", upgradeType=").append(upgradeType);
        sb.append(", upgradeForceFlag=").append(upgradeForceFlag);
        sb.append(", targetSoftwareVer=").append(targetSoftwareVer);
        sb.append(", upgradeStatus=").append(upgradeStatus);
        sb.append(", lastUpgradeResult=").append(lastUpgradeResult);
        sb.append(", lastUpgradeTime=").append(lastUpgradeTime);
        sb.append(", portTotalCount=").append(portTotalCount);
        sb.append(", portWorkCount=").append(portWorkCount);
        sb.append(", detailDesc=").append(detailDesc);
        sb.append(", nextNeAlarmSn=").append(nextNeAlarmSn);
        sb.append(", syslogStatus=").append(syslogStatus);
        sb.append(", logSysUuid=").append(logSysUuid);
        sb.append(", syslogBeginDate=").append(syslogBeginDate);
        sb.append(", syslogEndDate=").append(syslogEndDate);
        sb.append(", syslogDebugLevel=").append(syslogDebugLevel);
        sb.append(", cdrLogFlag=").append(cdrLogFlag);
        sb.append(", signalLogFlag=").append(signalLogFlag);
        sb.append(", mediaLogFlag=").append(mediaLogFlag);
        sb.append(", systemLogFlag=").append(systemLogFlag);
        sb.append(", mngLogFlag=").append(mngLogFlag);
        sb.append(", cdrLogFalg=").append(cdrLogFalg);
        sb.append(", sipsrvLockFlag=").append(sipsrvLockFlag);
        sb.append(", primarySipServer=").append(primarySipServer);
        sb.append(", primarySipsrvPort=").append(primarySipsrvPort);
        sb.append(", secondarySipServer=").append(secondarySipServer);
        sb.append(", secondarySipsrvPort=").append(secondarySipsrvPort);
        sb.append(", ntpStatus=").append(ntpStatus);
        sb.append(", autoRebootFlag=").append(autoRebootFlag);
        sb.append(", switchChipStatus=").append(switchChipStatus);
        sb.append(", curCpu=").append(curCpu);
        sb.append(", avgCpu5=").append(avgCpu5);
        sb.append(", avgCpu60=").append(avgCpu60);
        sb.append(", avgCpu600=").append(avgCpu600);
        sb.append(", freeMem=").append(freeMem);
        sb.append(", totalMem=").append(totalMem);
        sb.append(", memUsage=").append(memUsage);
        sb.append(", devTime=").append(devTime);
        sb.append(", alarmFlag=").append(alarmFlag);
        sb.append(", locMcc=").append(locMcc);
        sb.append(", locMnc=").append(locMnc);
        sb.append(", locLac=").append(locLac);
        sb.append(", locCellId=").append(locCellId);
        sb.append(", locStatus=").append(locStatus);
        sb.append(", locLat=").append(locLat);
        sb.append(", locLng=").append(locLng);
        sb.append(", upgradeFlag=").append(upgradeFlag);
        sb.append(", timeChipStatus=").append(timeChipStatus);
        sb.append(", allocMem=").append(allocMem);
        sb.append(", alarmShortPeriod=").append(alarmShortPeriod);
        sb.append(", alarmLongPeriod=").append(alarmLongPeriod);
        sb.append(", statusShortPeriod=").append(statusShortPeriod);
        sb.append(", statusLongPeriod=").append(statusLongPeriod);
        sb.append(", staticsPeriod=").append(staticsPeriod);
        sb.append(", lockStatus=").append(lockStatus);
        sb.append(", memAosUsage=").append(memAosUsage);
        sb.append(", memLinuxUsage=").append(memLinuxUsage);
        sb.append(", natMode=").append(natMode);
        sb.append(", natStatus=").append(natStatus);
        sb.append(", storeUsage=").append(storeUsage);
        sb.append(", ethLinkMode=").append(ethLinkMode);
        sb.append(", portType1=").append(portType1);
        sb.append(", portCount1=").append(portCount1);
        sb.append(", portType2=").append(portType2);
        sb.append(", portCount2=").append(portCount2);
        sb.append(", portType3=").append(portType3);
        sb.append(", portCount3=").append(portCount3);
        sb.append(", portType4=").append(portType4);
        sb.append(", portCount4=").append(portCount4);
        sb.append(", portType5=").append(portType5);
        sb.append(", portCount5=").append(portCount5);
        sb.append(", portType6=").append(portType6);
        sb.append(", portCount6=").append(portCount6);
        sb.append(", portType7=").append(portType7);
        sb.append(", portCount7=").append(portCount7);
        sb.append(", portType8=").append(portType8);
        sb.append(", portCount8=").append(portCount8);
        sb.append(", productSn=").append(productSn);
        sb.append(", macAddrBegin=").append(macAddrBegin);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}