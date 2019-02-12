package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryAcctRecDto extends PageDto {

    private static final long serialVersionUID = 2052973787438631903L;

    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
