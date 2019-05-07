package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryTotalChargingItemDto extends PageDto {

    /**
     * 1：按日   2：按月
     */
    private Integer type;

    private String beginDate;

    private String endDate;

    /**
     * 线路ID
     */
    private String chargingItemId;

    /**
     * 搜索用户过滤
     */
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
