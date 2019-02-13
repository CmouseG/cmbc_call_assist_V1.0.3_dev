package com.guiji.billing.entity;

/**
 * 通知各业务单元记录
 */
public class BillingNotifyBusiRecord extends BaseBean {

    private static final long serialVersionUID = 8382109329376191156L;

    private String accountId    ;

    private Integer type          ;

    private Integer returnStatus ;

    private String returnDetails;

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

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnDetails() {
        return returnDetails;
    }

    public void setReturnDetails(String returnDetails) {
        this.returnDetails = returnDetails;
    }
}
