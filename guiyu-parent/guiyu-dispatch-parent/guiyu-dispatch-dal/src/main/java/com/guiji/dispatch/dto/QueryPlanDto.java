package com.guiji.dispatch.dto;


import com.guiji.dispatch.sys.PageDto;

public class QueryPlanDto extends PageDto {

    private static final long serialVersionUID = 6684278788342403419L;

    /**
     * 企业组织CODE
     */
    private String orgCode;

    /**
     * 开始日期时间
     */
    private String beginDate;

    /**
     * 结束日期时间
     */
    private String endDate;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
