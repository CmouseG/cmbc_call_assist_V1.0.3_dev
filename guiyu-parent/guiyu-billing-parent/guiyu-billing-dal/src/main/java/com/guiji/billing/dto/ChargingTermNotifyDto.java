package com.guiji.billing.dto;

import java.io.Serializable;

public class ChargingTermNotifyDto implements Serializable {

    private static final long serialVersionUID = -6491136895038233400L;

    /**
     * 计费项ID（例如：话务路由线路ID）
     */
    private String chargingItemId;

    /**
     * 计费项名称
     */
    private String chargingItemName;

    /**
     * 计费项类型 1-时长 2-路数 3-月度
     */
    private Integer chargingType;

    /**
     * 计费项单价，以“分”为单位
     */
    private String price;

    /**
     * 价格单位：1-秒 2-分钟 3-小时 4-天 5-月 6-年
     */
    private Integer unitPrice;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 扣费标识 0-扣费 1-不扣费
     */
    private Integer isDeducted;

    /**
     * 状态  0-停用 1-启用
     */
    private Integer status;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
