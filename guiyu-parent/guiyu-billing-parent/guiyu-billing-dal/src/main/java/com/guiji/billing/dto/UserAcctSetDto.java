package com.guiji.billing.dto;

import com.guiji.billing.sys.BaseDto;

/**
 * 用户账户设置
 */
public class UserAcctSetDto extends BaseDto {

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
