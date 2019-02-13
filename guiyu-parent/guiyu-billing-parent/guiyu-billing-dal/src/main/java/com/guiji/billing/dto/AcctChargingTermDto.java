package com.guiji.billing.dto;

import com.guiji.billing.sys.BaseDto;

import java.math.BigDecimal;

/**
 * 账户计费项
 */
public class AcctChargingTermDto extends BaseDto {

    private static final long serialVersionUID = -2508566823042889242L;

    private String userChargingId;

    private String accountId;

    private String userId;

    private String chargingItemId;

    private String targetKey;

    private String targetName;

    private BigDecimal unitPrice;

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

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getUserChargingId() {
        return userChargingId;
    }

    public void setUserChargingId(String userChargingId) {
        this.userChargingId = userChargingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
