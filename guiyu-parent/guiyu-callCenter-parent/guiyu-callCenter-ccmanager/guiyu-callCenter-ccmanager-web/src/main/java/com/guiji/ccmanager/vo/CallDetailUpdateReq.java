package com.guiji.ccmanager.vo;

import javax.validation.constraints.NotNull;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/10 0010 18:01
 * @Description:
 */
public class CallDetailUpdateReq {

    @NotNull(message = "id不能为空")
    private String callDetailId;
    @NotNull(message = "用户说话内容不能为空")
    private String customerSayText;

    public String getCallDetailId() {
        return callDetailId;
    }

    public void setCallDetailId(String callDetailId) {
        this.callDetailId = callDetailId;
    }

    public String getCustomerSayText() {
        return customerSayText;
    }

    public void setCustomerSayText(String customerSayText) {
        this.customerSayText = customerSayText;
    }
}
