package com.guiji.botsentence.api.entity;

import java.io.Serializable;

/**
 * bot_sentence_keyword_audit_item
 * @author 
 */
public class KeywordAuditItemRpsVO implements Serializable {

    private static final long serialVersionUID = 7510573301907030536L;

    /**
     * 关键字ID
     */
    private Integer id;

    /**
     * 关键字审核ID
     */
    private Integer keywordAuditId;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 审核状态:0-待审核;1-已加入模板;2-已删除
     */
    private Integer auditStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKeywordAuditId() {
        return keywordAuditId;
    }

    public void setKeywordAuditId(Integer keywordAuditId) {
        this.keywordAuditId = keywordAuditId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}