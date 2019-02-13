package com.guiji.billing.entity;

/**
 * 计费项
 */
public class BillingChargingTermBean extends BaseBean {

    private static final long serialVersionUID = 3725790092038458909L;

    private String chargingItemId;

    /**
     * 类型
     */
    private Integer type;

    private String name;

    private Integer chargingType;

    private Integer status;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChargingType() {
        return chargingType;
    }

    public void setChargingType(Integer chargingType) {
        this.chargingType = chargingType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChargingItemId() {
        return chargingItemId;
    }

    public void setChargingItemId(String chargingItemId) {
        this.chargingItemId = chargingItemId;
    }
}
