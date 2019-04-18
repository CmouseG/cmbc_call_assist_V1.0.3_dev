package com.guiji.dispatch.vo;

import java.io.Serializable;

public class JoinPlanDataVo implements Serializable {

    private String phone;

    private String attach;

    private String params;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
