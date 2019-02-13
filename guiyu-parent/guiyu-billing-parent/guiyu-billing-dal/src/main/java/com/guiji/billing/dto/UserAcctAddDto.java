package com.guiji.billing.dto;

import com.guiji.billing.sys.BaseDto;

public class UserAcctAddDto extends BaseDto {

    private static final long serialVersionUID = 8517680710312003844L;

    private String companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业组织编码（唯一）
     */
    private String orgCode;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
