package com.guiji.billing.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysRechargeTotalVo implements Serializable {

    private static final long serialVersionUID = -4681835471345608645L;

    private Integer id;

    /**
     * 计费ID
     */
    private String chargingId;

    /**
     * 企业账户ID
     */
    private String accountId;

    /**
     * 公司企业编码
     */
    private String orgCode;

    /**
     * 公司名称
     */
    private String companyName;

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

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 操作人名称
     */
    private String operUserName;

    /**
     * 附件快照地址
     */
    private String attachmentSnapshotUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
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

    public String getChargingId() {
        return chargingId;
    }

    public void setChargingId(String chargingId) {
        this.chargingId = chargingId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAttachmentSnapshotUrl() {
        return attachmentSnapshotUrl;
    }

    public void setAttachmentSnapshotUrl(String attachmentSnapshotUrl) {
        this.attachmentSnapshotUrl = attachmentSnapshotUrl;
    }
}
