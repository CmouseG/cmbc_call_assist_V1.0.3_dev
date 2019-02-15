package com.guiji.billing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guiji.billing.sys.PageDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QueryRechargeDto extends PageDto {

    private static final long serialVersionUID = -8960597242513657421L;

    /**
     * 充值ID
     */
    private String chargingId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 类型  1：充值 2：消费
     */
    private Integer type;

    /**
     * 费用类型  1-银行转账充值  2-在线充值  3-通话消费
     */
    private Integer feeMode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
 //   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
 //   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Integer getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(Integer feeMode) {
        this.feeMode = feeMode;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getChargingId() {
        return chargingId;
    }

    public void setChargingId(String chargingId) {
        this.chargingId = chargingId;
    }
}
