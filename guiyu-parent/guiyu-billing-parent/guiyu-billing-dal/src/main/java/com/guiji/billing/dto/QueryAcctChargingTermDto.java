package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryAcctChargingTermDto extends PageDto {

    private static final long serialVersionUID = -6266416874749437025L;

    private String accountId;

    private String chargingItemId;

    private String targetKey;

    private Integer isDeducted;

    private Integer status;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getChargingItemId() {
        return chargingItemId;
    }

    public void setChargingItemId(String chargingItemId) {
        this.chargingItemId = chargingItemId;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public Integer getIsDeducted() {
        return isDeducted;
    }

    public void setIsDeducted(Integer isDeducted) {
        this.isDeducted = isDeducted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
