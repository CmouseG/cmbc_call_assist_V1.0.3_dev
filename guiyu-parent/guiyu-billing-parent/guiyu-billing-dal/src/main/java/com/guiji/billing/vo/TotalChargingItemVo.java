package com.guiji.billing.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalChargingItemVo implements Serializable {

    /**
     * 统计日期
     */
    private String totalDate;

    /**
     * 计费项ID(线路ID)
     */
    private String chargingItemId;

    /**
     * 计费项名称(线路名称)
      */
    private String chargingItemName;

    /**
     * 接通数量
     */
    private Integer callCount;

    /**
     * 通话时长
     */
    private Long duration;

    /**
     * 实际扣费金额
     */
    private BigDecimal totalAmout;

    /**
     * 计费金额
     */
    private BigDecimal totalChargingAmout;

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
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

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }

    public BigDecimal getTotalChargingAmout() {
        return totalChargingAmout;
    }

    public void setTotalChargingAmout(BigDecimal totalChargingAmout) {
        this.totalChargingAmout = totalChargingAmout;
    }
}
