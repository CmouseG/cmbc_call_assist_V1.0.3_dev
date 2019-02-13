package com.guiji.billing.dto;

import com.guiji.billing.sys.BaseDto;

/**
 * 计费项
 */
public class ChargingTermDto extends BaseDto {

    private static final long serialVersionUID = -2608953934639056389L;

    private String chargingItemId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 计费项名称
     */
    private String name;

    /**
     * 计费项类型 1-时长 2-路数 3-月度
     */
    private Integer chargingType;

    /**
     * 状态  0-停用 1-启用
     */
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
