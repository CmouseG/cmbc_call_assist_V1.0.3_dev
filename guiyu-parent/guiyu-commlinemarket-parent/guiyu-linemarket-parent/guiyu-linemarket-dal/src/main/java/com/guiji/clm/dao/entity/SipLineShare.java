package com.guiji.clm.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SipLineShare implements Serializable {
    private Integer id;

    private Integer lineStatus;

    private String lineName;

    private String supplier;

    private Integer callDirec;

    private String beginDate;

    private String endDate;

    private BigDecimal univalent;

    private String overtArea;

    private String industrys;

    private String areas;

    private String exceptAreas;

    private Integer applyNum;

    private String remark;

    private String belongUser;

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

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
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

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", lineStatus=").append(lineStatus);
        sb.append(", lineName=").append(lineName);
        sb.append(", supplier=").append(supplier);
        sb.append(", callDirec=").append(callDirec);
        sb.append(", beginDate=").append(beginDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", univalent=").append(univalent);
        sb.append(", overtArea=").append(overtArea);
        sb.append(", industrys=").append(industrys);
        sb.append(", areas=").append(areas);
        sb.append(", exceptAreas=").append(exceptAreas);
        sb.append(", applyNum=").append(applyNum);
        sb.append(", remark=").append(remark);
        sb.append(", belongUser=").append(belongUser);
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