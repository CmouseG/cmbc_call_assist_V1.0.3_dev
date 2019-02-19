package com.guiji.billing.entity;

import java.math.BigDecimal;

/**
 * 账户计费项
 */
public class BillingAcctChargingTerm extends BaseBean {

    private static final long serialVersionUID = 8898358578965572584L;

    private String userChargingId;

    /**
     * 企业下员工用户ID
     */
    private String userId;

    private String accountId;

    private String chargingItemId;

    private String chargingItemName;

    /**
     * 计费项类型 1-时长 2-路数 3-月度
     */
    private Integer chargingType;

    private String targetKey;

    private String targetName;

    /**
     * 单价:分
     */
    private BigDecimal price;

    /**
     * 价格单位  1-秒 2-分钟 3-小时 4-天 5-月 6-年
     */
    private Integer unitPrice;

    /**
     * 扣费标识 0-扣费 1-不扣费
     */
    private Integer isDeducted;

    /**
     * 状态  0-停用 1-启用
     */
    private Integer status;

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

    public String getChargingItemName() {
        return chargingItemName;
    }

    public void setChargingItemName(String chargingItemName) {
        this.chargingItemName = chargingItemName;
    }

    public Integer getChargingType() {
        return chargingType;
    }

    public void setChargingType(Integer chargingType) {
        this.chargingType = chargingType;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
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
}
