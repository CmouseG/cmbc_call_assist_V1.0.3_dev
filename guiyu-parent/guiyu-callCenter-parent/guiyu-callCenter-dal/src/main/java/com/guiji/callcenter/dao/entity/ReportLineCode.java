package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportLineCode implements Serializable {
    private Long id;

    private Date createTime;

    private Integer lineId;

    private String hangupCode;

    private Integer isCancel;

    private Integer totalCalls;

    private Integer answerCalls;

    private String phoneNum;

    private String orgCode;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getHangupCode() {
        return hangupCode;
    }

    public void setHangupCode(String hangupCode) {
        this.hangupCode = hangupCode == null ? null : hangupCode.trim();
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Integer getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(Integer totalCalls) {
        this.totalCalls = totalCalls;
    }

    public Integer getAnswerCalls() {
        return answerCalls;
    }

    public void setAnswerCalls(Integer answerCalls) {
        this.answerCalls = answerCalls;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", lineId=").append(lineId);
        sb.append(", hangupCode=").append(hangupCode);
        sb.append(", isCancel=").append(isCancel);
        sb.append(", totalCalls=").append(totalCalls);
        sb.append(", answerCalls=").append(answerCalls);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}