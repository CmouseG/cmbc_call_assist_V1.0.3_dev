package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SipLineApply implements Serializable {
    private Integer id;

    private Integer sipLineId;

    private Integer agentLineId;

    private Integer upSipLineId;

    private String lineName;

    private String supplier;

    private String overtArea;

    private Integer callDirec;

    private Integer maxConcurrentCalls;

    private String templates;

    private String beginDate;

    private String endDate;

    private String belongUser;

    private String orgCode;

    private BigDecimal univalent;

    private String remark;

    private String applyUser;

    private String applyOrgCode;

    private String applyDate;

    private Date applyTime;

    private Integer applyType;

    private Integer applyStatus;

    private String approveUser;

    private String approveDate;

    private Date approveTime;

    private String approveRemark;

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

    public Integer getSipLineId() {
        return sipLineId;
    }

    public void setSipLineId(Integer sipLineId) {
        this.sipLineId = sipLineId;
    }

    public Integer getAgentLineId() {
        return agentLineId;
    }

    public void setAgentLineId(Integer agentLineId) {
        this.agentLineId = agentLineId;
    }

    public Integer getUpSipLineId() {
        return upSipLineId;
    }

    public void setUpSipLineId(Integer upSipLineId) {
        this.upSipLineId = upSipLineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getOvertArea() {
        return overtArea;
    }

    public void setOvertArea(String overtArea) {
        this.overtArea = overtArea == null ? null : overtArea.trim();
    }

    public Integer getCallDirec() {
        return callDirec;
    }

    public void setCallDirec(Integer callDirec) {
        this.callDirec = callDirec;
    }

    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates == null ? null : templates.trim();
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

    public String getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(String belongUser) {
        this.belongUser = belongUser == null ? null : belongUser.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser == null ? null : applyUser.trim();
    }

    public String getApplyOrgCode() {
        return applyOrgCode;
    }

    public void setApplyOrgCode(String applyOrgCode) {
        this.applyOrgCode = applyOrgCode == null ? null : applyOrgCode.trim();
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser == null ? null : approveUser.trim();
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate == null ? null : approveDate.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark == null ? null : approveRemark.trim();
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
        sb.append(", sipLineId=").append(sipLineId);
        sb.append(", agentLineId=").append(agentLineId);
        sb.append(", upSipLineId=").append(upSipLineId);
        sb.append(", lineName=").append(lineName);
        sb.append(", supplier=").append(supplier);
        sb.append(", overtArea=").append(overtArea);
        sb.append(", callDirec=").append(callDirec);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", templates=").append(templates);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", belongUser=").append(belongUser);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", univalent=").append(univalent);
        sb.append(", remark=").append(remark);
        sb.append(", applyUser=").append(applyUser);
        sb.append(", applyOrgCode=").append(applyOrgCode);
        sb.append(", applyDate=").append(applyDate);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", applyType=").append(applyType);
        sb.append(", applyStatus=").append(applyStatus);
        sb.append(", approveUser=").append(approveUser);
        sb.append(", approveDate=").append(approveDate);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", approveRemark=").append(approveRemark);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}