package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryChargingRecordDto extends PageDto {

    private static final long serialVersionUID = -3567111731683908892L;

    private String accountId;

    /**
     * 类型  1：充值 2：消费
     */
    private Integer type;

    private String phone;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
