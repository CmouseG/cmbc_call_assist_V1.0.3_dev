package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class CallOutDetailLog implements Serializable {
    private Long id;

    private BigInteger callDetailId;

    private String customerSayTextNew;

    private String customerSayText;

    private Integer updateBy;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getCallDetailId() {
        return callDetailId;
    }

    public void setCallDetailId(BigInteger callDetailId) {
        this.callDetailId = callDetailId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCustomerSayTextNew() {
        return customerSayTextNew;
    }

    public void setCustomerSayTextNew(String customerSayTextNew) {
        this.customerSayTextNew = customerSayTextNew == null ? null : customerSayTextNew.trim();
    }

    public String getCustomerSayText() {
        return customerSayText;
    }

    public void setCustomerSayText(String customerSayText) {
        this.customerSayText = customerSayText == null ? null : customerSayText.trim();
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callDetailId=").append(callDetailId);
        sb.append(", customerSayTextNew=").append(customerSayTextNew);
        sb.append(", customerSayText=").append(customerSayText);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}