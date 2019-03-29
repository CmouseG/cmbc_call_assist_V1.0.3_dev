package com.guiji.voipgateway.dingxin.dao.entity;

import java.io.Serializable;

public class TblGw implements Serializable {
    private Integer uuid;

    private Integer recStatus;

    private Integer neUuid;

    private String alias;

    private Integer domainUuid;

    private Integer defaultGrpUuid;

    private Integer virtualSimFlag;

    private Integer gwpNum;

    private Integer workMode;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public Integer getDomainUuid() {
        return domainUuid;
    }

    public void setDomainUuid(Integer domainUuid) {
        this.domainUuid = domainUuid;
    }

    public Integer getDefaultGrpUuid() {
        return defaultGrpUuid;
    }

    public void setDefaultGrpUuid(Integer defaultGrpUuid) {
        this.defaultGrpUuid = defaultGrpUuid;
    }

    public Integer getVirtualSimFlag() {
        return virtualSimFlag;
    }

    public void setVirtualSimFlag(Integer virtualSimFlag) {
        this.virtualSimFlag = virtualSimFlag;
    }

    public Integer getGwpNum() {
        return gwpNum;
    }

    public void setGwpNum(Integer gwpNum) {
        this.gwpNum = gwpNum;
    }

    public Integer getWorkMode() {
        return workMode;
    }

    public void setWorkMode(Integer workMode) {
        this.workMode = workMode;
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
        sb.append(", alias=").append(alias);
        sb.append(", domainUuid=").append(domainUuid);
        sb.append(", defaultGrpUuid=").append(defaultGrpUuid);
        sb.append(", virtualSimFlag=").append(virtualSimFlag);
        sb.append(", gwpNum=").append(gwpNum);
        sb.append(", workMode=").append(workMode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}