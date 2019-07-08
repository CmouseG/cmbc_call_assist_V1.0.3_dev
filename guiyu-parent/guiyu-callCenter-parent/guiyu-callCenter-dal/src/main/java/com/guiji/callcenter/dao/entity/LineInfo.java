package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class LineInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.line_id
     *
     * @mbggenerated
     */
    private Integer lineId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.customer_id
     *
     * @mbggenerated
     */
    private Integer customerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.line_name
     *
     * @mbggenerated
     */
    private String lineName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.sip_ip
     *
     * @mbggenerated
     */
    private String sipIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.sip_port
     *
     * @mbggenerated
     */
    private String sipPort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.codec
     *
     * @mbggenerated
     */
    private String codec;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.caller_num
     *
     * @mbggenerated
     */
    private String callerNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.callee_prefix
     *
     * @mbggenerated
     */
    private String calleePrefix;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.max_concurrent_calls
     *
     * @mbggenerated
     */
    private Integer maxConcurrentCalls;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.create_date
     *
     * @mbggenerated
     */
    private String createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.update_date
     *
     * @mbggenerated
     */
    private String updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.createt_by
     *
     * @mbggenerated
     */
    private Integer createtBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.update_by
     *
     * @mbggenerated
     */
    private Integer updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.org_code
     *
     * @mbggenerated
     */
    private String orgCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.sip_user
     *
     * @mbggenerated
     */
    private String sipUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.sip_pwd
     *
     * @mbggenerated
     */
    private String sipPwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.line_type
     *
     * @mbggenerated
     */
    private Integer lineType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column line_info.fsagent_id
     *
     * @mbggenerated
     */
    private String fsagentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table line_info
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;


    private String lineLocation;

    private String nonlocalPrefix;

    public String getLineLocation() {
        return lineLocation;
    }

    public void setLineLocation(String lineLocation) {
        this.lineLocation = lineLocation;
    }

    public String getNonlocalPrefix() {
        return nonlocalPrefix;
    }

    public void setNonlocalPrefix(String nonlocalPrefix) {
        this.nonlocalPrefix = nonlocalPrefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.line_id
     *
     * @return the value of line_info.line_id
     *
     * @mbggenerated
     */
    public Integer getLineId() {
        return lineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.line_id
     *
     * @param lineId the value for line_info.line_id
     *
     * @mbggenerated
     */
    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.customer_id
     *
     * @return the value of line_info.customer_id
     *
     * @mbggenerated
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.customer_id
     *
     * @param customerId the value for line_info.customer_id
     *
     * @mbggenerated
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.line_name
     *
     * @return the value of line_info.line_name
     *
     * @mbggenerated
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.line_name
     *
     * @param lineName the value for line_info.line_name
     *
     * @mbggenerated
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.sip_ip
     *
     * @return the value of line_info.sip_ip
     *
     * @mbggenerated
     */
    public String getSipIp() {
        return sipIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.sip_ip
     *
     * @param sipIp the value for line_info.sip_ip
     *
     * @mbggenerated
     */
    public void setSipIp(String sipIp) {
        this.sipIp = sipIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.sip_port
     *
     * @return the value of line_info.sip_port
     *
     * @mbggenerated
     */
    public String getSipPort() {
        return sipPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.sip_port
     *
     * @param sipPort the value for line_info.sip_port
     *
     * @mbggenerated
     */
    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.codec
     *
     * @return the value of line_info.codec
     *
     * @mbggenerated
     */
    public String getCodec() {
        return codec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.codec
     *
     * @param codec the value for line_info.codec
     *
     * @mbggenerated
     */
    public void setCodec(String codec) {
        this.codec = codec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.caller_num
     *
     * @return the value of line_info.caller_num
     *
     * @mbggenerated
     */
    public String getCallerNum() {
        return callerNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.caller_num
     *
     * @param callerNum the value for line_info.caller_num
     *
     * @mbggenerated
     */
    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.callee_prefix
     *
     * @return the value of line_info.callee_prefix
     *
     * @mbggenerated
     */
    public String getCalleePrefix() {
        return calleePrefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.callee_prefix
     *
     * @param calleePrefix the value for line_info.callee_prefix
     *
     * @mbggenerated
     */
    public void setCalleePrefix(String calleePrefix) {
        this.calleePrefix = calleePrefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.max_concurrent_calls
     *
     * @return the value of line_info.max_concurrent_calls
     *
     * @mbggenerated
     */
    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.max_concurrent_calls
     *
     * @param maxConcurrentCalls the value for line_info.max_concurrent_calls
     *
     * @mbggenerated
     */
    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.create_date
     *
     * @return the value of line_info.create_date
     *
     * @mbggenerated
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.create_date
     *
     * @param createDate the value for line_info.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.update_date
     *
     * @return the value of line_info.update_date
     *
     * @mbggenerated
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.update_date
     *
     * @param updateDate the value for line_info.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.remark
     *
     * @return the value of line_info.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.remark
     *
     * @param remark the value for line_info.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.createt_by
     *
     * @return the value of line_info.createt_by
     *
     * @mbggenerated
     */
    public Integer getCreatetBy() {
        return createtBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.createt_by
     *
     * @param createtBy the value for line_info.createt_by
     *
     * @mbggenerated
     */
    public void setCreatetBy(Integer createtBy) {
        this.createtBy = createtBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.update_by
     *
     * @return the value of line_info.update_by
     *
     * @mbggenerated
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.update_by
     *
     * @param updateBy the value for line_info.update_by
     *
     * @mbggenerated
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.org_code
     *
     * @return the value of line_info.org_code
     *
     * @mbggenerated
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.org_code
     *
     * @param orgCode the value for line_info.org_code
     *
     * @mbggenerated
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.sip_user
     *
     * @return the value of line_info.sip_user
     *
     * @mbggenerated
     */
    public String getSipUser() {
        return sipUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.sip_user
     *
     * @param sipUser the value for line_info.sip_user
     *
     * @mbggenerated
     */
    public void setSipUser(String sipUser) {
        this.sipUser = sipUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.sip_pwd
     *
     * @return the value of line_info.sip_pwd
     *
     * @mbggenerated
     */
    public String getSipPwd() {
        return sipPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.sip_pwd
     *
     * @param sipPwd the value for line_info.sip_pwd
     *
     * @mbggenerated
     */
    public void setSipPwd(String sipPwd) {
        this.sipPwd = sipPwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.line_type
     *
     * @return the value of line_info.line_type
     *
     * @mbggenerated
     */
    public Integer getLineType() {
        return lineType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.line_type
     *
     * @param lineType the value for line_info.line_type
     *
     * @mbggenerated
     */
    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column line_info.fsagent_id
     *
     * @return the value of line_info.fsagent_id
     *
     * @mbggenerated
     */
    public String getFsagentId() {
        return fsagentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column line_info.fsagent_id
     *
     * @param fsagentId the value for line_info.fsagent_id
     *
     * @mbggenerated
     */
    public void setFsagentId(String fsagentId) {
        this.fsagentId = fsagentId;
    }
}