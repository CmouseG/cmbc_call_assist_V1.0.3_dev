package com.guiji.billing.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RechargeDto implements Serializable {

    private static final long serialVersionUID = -3772249788057273082L;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 企业公司编码
     */
    private String orgCode;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 充值金额
     */
    private BigDecimal amount;

    /**
     * 附件快照地址
     */
    private String attachmentSnapshotUrl;

    /**
     * 充值操作用户ID
     */
    private String userId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAttachmentSnapshotUrl() {
        return attachmentSnapshotUrl;
    }

    public void setAttachmentSnapshotUrl(String attachmentSnapshotUrl) {
        this.attachmentSnapshotUrl = attachmentSnapshotUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
