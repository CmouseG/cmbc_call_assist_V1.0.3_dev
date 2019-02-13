package com.guiji.billing.entity;

/**
 * 消息通知记录
 */
public class BillingNotifyMsgRecord extends BaseBean {

    private static final long serialVersionUID = 4220033679028137137L;

    private String accountId  ;

    private Integer type        ;

    private Integer notify_type ;

    private String content     ;

    private Integer status      ;

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

    public Integer getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(Integer notify_type) {
        this.notify_type = notify_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
