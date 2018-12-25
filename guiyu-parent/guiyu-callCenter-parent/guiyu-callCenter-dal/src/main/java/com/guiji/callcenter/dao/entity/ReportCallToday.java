package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class ReportCallToday implements Serializable {
    private Long id;

    private Integer durationType;

    private String intent;

    private String reason;

    private Integer callCount;

    private Long durationAll;

    private String customerId;

    private String tempid;

    private String orgCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent == null ? null : intent.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Long getDurationAll() {
        return durationAll;
    }

    public void setDurationAll(Long durationAll) {
        this.durationAll = durationAll;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", durationType=").append(durationType);
        sb.append(", intent=").append(intent);
        sb.append(", reason=").append(reason);
        sb.append(", callCount=").append(callCount);
        sb.append(", durationAll=").append(durationAll);
        sb.append(", customerId=").append(customerId);
        sb.append(", tempid=").append(tempid);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}