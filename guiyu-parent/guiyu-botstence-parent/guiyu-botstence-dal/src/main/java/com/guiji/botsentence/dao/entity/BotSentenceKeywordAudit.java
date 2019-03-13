package com.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * bot_sentence_keyword_audit
 * @author 
 */
public class BotSentenceKeywordAudit implements Serializable {
    private Integer id;

    /**
     * 话术ID
     */
    private String processId;

    /**
     * 关键词所属意图ID
     */
    private Integer intentId;

    /**
     * 提交审核用户ID
     */
    private Integer userId;

    /**
     * 待审核关键字数量
     */
    private Integer keywordsCount;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getIntentId() {
        return intentId;
    }

    public void setIntentId(Integer intentId) {
        this.intentId = intentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKeywordsCount() {
        return keywordsCount;
    }

    public void setKeywordsCount(Integer keywordsCount) {
        this.keywordsCount = keywordsCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BotSentenceKeywordAudit other = (BotSentenceKeywordAudit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProcessId() == null ? other.getProcessId() == null : this.getProcessId().equals(other.getProcessId()))
            && (this.getIntentId() == null ? other.getIntentId() == null : this.getIntentId().equals(other.getIntentId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getKeywordsCount() == null ? other.getKeywordsCount() == null : this.getKeywordsCount().equals(other.getKeywordsCount()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProcessId() == null) ? 0 : getProcessId().hashCode());
        result = prime * result + ((getIntentId() == null) ? 0 : getIntentId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getKeywordsCount() == null) ? 0 : getKeywordsCount().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", processId=").append(processId);
        sb.append(", intentId=").append(intentId);
        sb.append(", userId=").append(userId);
        sb.append(", keywordsCount=").append(keywordsCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}