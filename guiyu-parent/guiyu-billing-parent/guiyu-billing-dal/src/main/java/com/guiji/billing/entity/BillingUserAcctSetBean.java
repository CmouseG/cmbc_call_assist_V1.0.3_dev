package com.guiji.billing.entity;

/**
 * 用户账户设置
 */
public class BillingUserAcctSetBean extends BaseBean {

    private static final long serialVersionUID = -137547273777160971L;

    private String acctSetId;

    private String accountId;

    private String setKey;

    private String setValue;

    public String getAcctSetId() {
        return acctSetId;
    }

    public void setAcctSetId(String acctSetId) {
        this.acctSetId = acctSetId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }
}
