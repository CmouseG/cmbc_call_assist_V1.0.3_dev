package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SipLineExclusive implements Serializable {
    private Integer id;

    private Integer sipLineId;

    private Integer applyId;

    private Integer agentLineId;

    private Integer lineId;

    private String lineName;

    private Integer lineType;

    private Integer lineStatus;

    private String supplier;

    private Integer callDirec;

    private Integer maxConcurrentCalls;

    private String beginDate;

    private String endDate;

    private Boolean feeOrNot;

    private Integer lineFeeType;

    private BigDecimal univalent;

    private String overtArea;

    private String industrys;

    private String templates;

    private String areas;

    private String belongUser;

    private String belongOrgCode;

    private String exceptAreas;

    private String remark;

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

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getAgentLineId() {
        return agentLineId;
    }

    public void setAgentLineId(Integer agentLineId) {
        this.agentLineId = agentLineId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
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

    public BigDecimal getUnivalent() {
        return univalent;
    }

    public void setUnivalent(BigDecimal univalent) {
        this.univalent = univalent;
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

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates == null ? null : templates.trim();
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    public String getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(String belongUser) {
        this.belongUser = belongUser == null ? null : belongUser.trim();
    }

    public String getBelongOrgCode() {
        return belongOrgCode;
    }

    public void setBelongOrgCode(String belongOrgCode) {
        this.belongOrgCode = belongOrgCode == null ? null : belongOrgCode.trim();
    }

    public String getExceptAreas() {
        return exceptAreas;
    }

    public void setExceptAreas(String exceptAreas) {
        this.exceptAreas = exceptAreas == null ? null : exceptAreas.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", applyId=").append(applyId);
        sb.append(", agentLineId=").append(agentLineId);
        sb.append(", lineId=").append(lineId);
        sb.append(", lineName=").append(lineName);
        sb.append(", lineType=").append(lineType);
        sb.append(", lineStatus=").append(lineStatus);
        sb.append(", supplier=").append(supplier);
        sb.append(", callDirec=").append(callDirec);
        sb.append(", maxConcurrentCalls=").append(maxConcurrentCalls);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", feeOrNot=").append(feeOrNot);
        sb.append(", lineFeeType=").append(lineFeeType);
        sb.append(", univalent=").append(univalent);
        sb.append(", overtArea=").append(overtArea);
        sb.append(", industrys=").append(industrys);
        sb.append(", templates=").append(templates);
        sb.append(", areas=").append(areas);
        sb.append(", belongUser=").append(belongUser);
        sb.append(", belongOrgCode=").append(belongOrgCode);
        sb.append(", exceptAreas=").append(exceptAreas);
        sb.append(", remark=").append(remark);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}