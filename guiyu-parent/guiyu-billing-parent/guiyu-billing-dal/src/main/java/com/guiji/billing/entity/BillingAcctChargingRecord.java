package com.guiji.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计费流水记录
 */
public class BillingAcctChargingRecord extends BaseBean {

    private static final long serialVersionUID = -250337199304746615L;

    private String chargingId;

    /**
     * 企业账户ID
     */
    private String accountId;

    /**
     * 消费操作员用户ID
     */
    private String operUserId;

    /**
     * 消费操作员用户名
     */
    private String operUserName;

    /**
     * 操作员用户企业编码
     */
    private String operOrgCode;

    /**
     * 被充值/消费 企业orgCode
     */
    private String operUserOrgCode;

    /**
     * 通话开始时间
     */
    private Date operBeginTime;

    /**
     * 通话结束时间
     */
    private Date operEndTime;

    /**
     * 通话时长秒
     */
    private Long operDuration;

    /**
     * 通话时长分钟
     */
    private Long operDurationM;

    /**
     * 通话时间描述
     */
    private String operDurationStr;

    private Integer operStatus ;

    private String operDetails;

    /**
     * 类型  1：充值 2：消费
     */
    private Integer type ;

    /**
     * 消费方式 1-银行转账充值  2-在线充值  3-通话消费
     */
    private Integer feeMode;

    /**
     * 用户计费项
     */
    private String userChargingId;

    /**
     * 扣费金额
     */
    private BigDecimal amount;

    /**
     * 计费金额
     */
    private BigDecimal chargingAmount;

    /**
     * 消费前企业账户可用金额
     */
    private BigDecimal srcAmount;

    /**
     * 消费后企业账户可用金额
     */
    private BigDecimal toAmount;

    private String evidence;

    private String planuuid;

    /**
     * 消费操作员用户号码
     */
    private String phone;

    /**
     * 附件快照地址
     */
    private String attachmentSnapshotUrl;

    public String getChargingId() {
        return chargingId;
    }

    public void setChargingId(String chargingId) {
        this.chargingId = chargingId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getOperUserOrgCode() {
        return operUserOrgCode;
    }

    public void setOperUserOrgCode(String operUserOrgCode) {
        this.operUserOrgCode = operUserOrgCode;
    }

    public Date getOperBeginTime() {
        return operBeginTime;
    }

    public void setOperBeginTime(Date operBeginTime) {
        this.operBeginTime = operBeginTime;
    }

    public Date getOperEndTime() {
        return operEndTime;
    }

    public void setOperEndTime(Date operEndTime) {
        this.operEndTime = operEndTime;
    }

    public Long getOperDuration() {
        return operDuration;
    }

    public void setOperDuration(Long operDuration) {
        this.operDuration = operDuration;
    }

    public Long getOperDurationM() {
        return operDurationM;
    }

    public void setOperDurationM(Long operDurationM) {
        this.operDurationM = operDurationM;
    }

    public String getOperDurationStr() {
        return operDurationStr;
    }

    public void setOperDurationStr(String operDurationStr) {
        this.operDurationStr = operDurationStr;
    }

    public Integer getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(Integer operStatus) {
        this.operStatus = operStatus;
    }

    public String getOperDetails() {
        return operDetails;
    }

    public void setOperDetails(String operDetails) {
        this.operDetails = operDetails;
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

    public String getUserChargingId() {
        return userChargingId;
    }

    public void setUserChargingId(String userChargingId) {
        this.userChargingId = userChargingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getPlanuuid() {
        return planuuid;
    }

    public void setPlanuuid(String planuuid) {
        this.planuuid = planuuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttachmentSnapshotUrl() {
        return attachmentSnapshotUrl;
    }

    public void setAttachmentSnapshotUrl(String attachmentSnapshotUrl) {
        this.attachmentSnapshotUrl = attachmentSnapshotUrl;
    }

    public BigDecimal getChargingAmount() {
        return chargingAmount;
    }

    public void setChargingAmount(BigDecimal chargingAmount) {
        this.chargingAmount = chargingAmount;
    }

    public String getOperOrgCode() {
        return operOrgCode;
    }

    public void setOperOrgCode(String operOrgCode) {
        this.operOrgCode = operOrgCode;
    }
}
