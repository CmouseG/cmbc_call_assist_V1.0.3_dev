package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class Tier implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.tid
     *
     * @mbggenerated
     */
    private Long tid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.creator
     *
     * @mbggenerated
     */
    private Long creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.queue_id
     *
     * @mbggenerated
     */
    private Long queueId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.update_user
     *
     * @mbggenerated
     */
    private Long updateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tier.org_code
     *
     * @mbggenerated
     */
    private String orgCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tier
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.tid
     *
     * @return the value of tier.tid
     *
     * @mbggenerated
     */
    public Long getTid() {
        return tid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.tid
     *
     * @param tid the value for tier.tid
     *
     * @mbggenerated
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.create_time
     *
     * @return the value of tier.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.create_time
     *
     * @param createTime the value for tier.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.creator
     *
     * @return the value of tier.creator
     *
     * @mbggenerated
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.creator
     *
     * @param creator the value for tier.creator
     *
     * @mbggenerated
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.queue_id
     *
     * @return the value of tier.queue_id
     *
     * @mbggenerated
     */
    public Long getQueueId() {
        return queueId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.queue_id
     *
     * @param queueId the value for tier.queue_id
     *
     * @mbggenerated
     */
    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.update_time
     *
     * @return the value of tier.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.update_time
     *
     * @param updateTime the value for tier.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.update_user
     *
     * @return the value of tier.update_user
     *
     * @mbggenerated
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.update_user
     *
     * @param updateUser the value for tier.update_user
     *
     * @mbggenerated
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.user_id
     *
     * @return the value of tier.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.user_id
     *
     * @param userId the value for tier.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tier.org_code
     *
     * @return the value of tier.org_code
     *
     * @mbggenerated
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tier.org_code
     *
     * @param orgCode the value for tier.org_code
     *
     * @mbggenerated
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}