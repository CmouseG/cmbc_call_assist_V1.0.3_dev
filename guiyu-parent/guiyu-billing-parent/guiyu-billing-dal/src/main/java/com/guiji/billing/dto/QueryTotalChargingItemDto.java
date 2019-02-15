package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryTotalChargingItemDto extends PageDto {

    /**
     * 企业组织编码
     */
    private String orgCode;

    /**
     * 操作员ID
     */
    private String operUserId;

    /**
     * 1：按日   2：按月
     */
    private Integer type;

    private String beginDate;

    private String endDate;

    private String chargingItemId;

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getChargingItemId() {
        return chargingItemId;
    }

    public void setChargingItemId(String chargingItemId) {
        this.chargingItemId = chargingItemId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
