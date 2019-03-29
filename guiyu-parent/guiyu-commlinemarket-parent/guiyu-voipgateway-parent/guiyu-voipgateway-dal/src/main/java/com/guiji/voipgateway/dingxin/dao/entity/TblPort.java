package com.guiji.voipgateway.dingxin.dao.entity;

import java.io.Serializable;

public class TblPort implements Serializable {
    private Integer uuid;

    private Integer recStatus;

    private Integer neUuid;

    private Integer shelfNo;

    private Integer slotNo;

    private Integer portNo;

    private Integer type;

    private String alias;

    private Integer adminStatus;

    private Integer oprStatus;

    private Integer runStatus;

    private Integer actionStatus;

    private Integer actionResult;

    private Integer almStatusBits;

    private Integer domainUuid;

    private Integer portPolicyUuid;

    private Integer portGrpUuid;

    private Integer lockPortUuid;

    private Integer lockSimUuid;

    private Integer lockBkpUuid;

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

    public Integer getNeUuid() {
        return neUuid;
    }

    public void setNeUuid(Integer neUuid) {
        this.neUuid = neUuid;
    }

    public Integer getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(Integer shelfNo) {
        this.shelfNo = shelfNo;
    }

    public Integer getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(Integer slotNo) {
        this.slotNo = slotNo;
    }

    public Integer getPortNo() {
        return portNo;
    }

    public void setPortNo(Integer portNo) {
        this.portNo = portNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getPortPolicyUuid() {
        return portPolicyUuid;
    }

    public void setPortPolicyUuid(Integer portPolicyUuid) {
        this.portPolicyUuid = portPolicyUuid;
    }

    public Integer getPortGrpUuid() {
        return portGrpUuid;
    }

    public void setPortGrpUuid(Integer portGrpUuid) {
        this.portGrpUuid = portGrpUuid;
    }

    public Integer getLockPortUuid() {
        return lockPortUuid;
    }

    public void setLockPortUuid(Integer lockPortUuid) {
        this.lockPortUuid = lockPortUuid;
    }

    public Integer getLockSimUuid() {
        return lockSimUuid;
    }

    public void setLockSimUuid(Integer lockSimUuid) {
        this.lockSimUuid = lockSimUuid;
    }

    public Integer getLockBkpUuid() {
        return lockBkpUuid;
    }

    public void setLockBkpUuid(Integer lockBkpUuid) {
        this.lockBkpUuid = lockBkpUuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", recStatus=").append(recStatus);
        sb.append(", neUuid=").append(neUuid);
        sb.append(", shelfNo=").append(shelfNo);
        sb.append(", slotNo=").append(slotNo);
        sb.append(", portNo=").append(portNo);
        sb.append(", type=").append(type);
        sb.append(", alias=").append(alias);
        sb.append(", adminStatus=").append(adminStatus);
        sb.append(", oprStatus=").append(oprStatus);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", actionStatus=").append(actionStatus);
        sb.append(", actionResult=").append(actionResult);
        sb.append(", almStatusBits=").append(almStatusBits);
        sb.append(", domainUuid=").append(domainUuid);
        sb.append(", portPolicyUuid=").append(portPolicyUuid);
        sb.append(", portGrpUuid=").append(portGrpUuid);
        sb.append(", lockPortUuid=").append(lockPortUuid);
        sb.append(", lockSimUuid=").append(lockSimUuid);
        sb.append(", lockBkpUuid=").append(lockBkpUuid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}