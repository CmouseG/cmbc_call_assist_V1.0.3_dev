package com.guiji.botsentence.dao.entity;

import java.io.Serializable;

public class AccountGroup implements Serializable {
    private String accountGroupNo;

    private String accountNo;

    private static final long serialVersionUID = 1L;

    public String getAccountGroupNo() {
        return accountGroupNo;
    }

    public void setAccountGroupNo(String accountGroupNo) {
        this.accountGroupNo = accountGroupNo == null ? null : accountGroupNo.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountGroupNo=").append(accountGroupNo);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}