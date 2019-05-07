package com.guiji.billing.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserRechargeTotalVo implements Serializable {

    private String accountId;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 充值前金额
     */
    private BigDecimal srcAmount;

    /**
     * 充值后金额
     */
    private BigDecimal toAmount;

    /**
     * 类型  1：充值 2：消费
     */
    private Integer type;

    /**
     * 费用类型  1-银行转账充值  2-在线充值  3-通话消费
     */
    private Integer feeMode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operTime;

    private String operUserName;

    /**
     * 附件快照地址
     */
    private String attachmentSnapshotUrl;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getSrcAmount() {
        return srcAmount;
    }

    public void setSrcAmount(BigDecimal srcAmount) {
        this.srcAmount = srcAmount;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(Integer feeMode) {
        this.feeMode = feeMode;
    }

    public String getAttachmentSnapshotUrl() {
        return attachmentSnapshotUrl;
    }

    public void setAttachmentSnapshotUrl(String attachmentSnapshotUrl) {
        this.attachmentSnapshotUrl = attachmentSnapshotUrl;
    }
}
