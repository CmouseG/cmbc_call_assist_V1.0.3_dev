package com.guiji.botsentence.api.entity;

import java.io.Serializable;

/**
 * bot_sentence_keyword_audit_item
 * @author 
 */
public class KeywordTemplateReqVO implements Serializable {

    private static final long serialVersionUID = 6448125458560541648L;
    /**
     * 行业Id
     */
    private String industryId;

    /**
     * 关键字模板类型 00-通用 01-行业
     */
    private String templateType;

    /**
     * 意图名称
     */
    private String intentName;

    private String userId;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}