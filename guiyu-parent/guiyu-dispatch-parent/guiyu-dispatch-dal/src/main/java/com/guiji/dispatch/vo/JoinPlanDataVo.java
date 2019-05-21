package com.guiji.dispatch.vo;

import java.io.Serializable;

public class JoinPlanDataVo implements Serializable {

    private String phone;

    private String attach;

    private String params;

    /**
     * 号码客户名称
     */
    private String custName;

    /**
     * 号码客户所属单位
     */
    private String custCompany;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany;
    }
}
