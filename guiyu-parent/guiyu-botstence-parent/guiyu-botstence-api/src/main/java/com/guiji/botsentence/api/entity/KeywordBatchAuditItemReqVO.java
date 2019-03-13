package com.guiji.botsentence.api.entity;

import java.io.Serializable;
import java.util.List;

/**
 * bot_sentence_keyword_audit_item
 * @author 
 */
public class KeywordBatchAuditItemReqVO implements Serializable {

    private static final long serialVersionUID = 7950605621374948643L;

    /**
     * 关键字审核ID
     */
    private Integer keywordAuditId;

    /**
     * 关键字模板ID
     */
    private Integer keywordTemplateId;

    /**
     * 关键字ID list
     */
    private List<Integer> keywordAuditItemIds;

    /**
     * 审核用户ID
     */
    private Integer auditUserId;

    /**
     * 审核状态:0-待审核;1-已加入模板;2-已删除
     */
    private Integer auditStatus = 0;

    public Integer getKeywordTemplateId() {
        return keywordTemplateId;
    }

    public void setKeywordTemplateId(Integer keywordTemplateId) {
        this.keywordTemplateId = keywordTemplateId;
    }

    public List<Integer> getKeywordAuditItemIds() {
        return keywordAuditItemIds;
    }

    public void setKeywordAuditItemIds(List<Integer> keywordAuditItemIds) {
        this.keywordAuditItemIds = keywordAuditItemIds;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

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