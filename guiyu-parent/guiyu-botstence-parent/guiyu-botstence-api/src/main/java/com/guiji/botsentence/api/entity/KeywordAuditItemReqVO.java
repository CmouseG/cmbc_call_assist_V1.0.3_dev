package com.guiji.botsentence.api.entity;

import java.io.Serializable;

/**
 * bot_sentence_keyword_audit_item
 * @author 
 */
public class KeywordAuditItemReqVO implements Serializable {

    private static final long serialVersionUID = -8603123437282901401L;

    /**
     * 关键字审核ID
     */
    private Integer keywordAuditId;

    /**
     * 审核状态:0-待审核;1-已加入模板;2-已删除
     */
    private Integer auditStatus = 0;

    public Integer getKeywordAuditId() {
        return keywordAuditId;
    }

    public void setKeywordAuditId(Integer keywordAuditId) {
        this.keywordAuditId = keywordAuditId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}