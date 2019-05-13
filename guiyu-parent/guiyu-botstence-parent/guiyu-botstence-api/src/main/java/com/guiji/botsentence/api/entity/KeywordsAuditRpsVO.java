package com.guiji.botsentence.api.entity;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class KeywordsAuditRpsVO implements Serializable {

    private static final long serialVersionUID = -68952352825435552L;

    /**
     * 关键字审核ID
     */
    private Integer keywordAuditId;

    /**
     * 话术ID
     */
    private String processId;

    /**
     * 关键词所属意图ID
     */
    private Integer intentId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 行业名称
     */
    private String industryId;

    /**
     * 行业名称
     */
    private String industry;

    /**
     * 提交审核用户ID
     */
    private Integer produceUserId;

    /**
     * 提交审核用户名
     */
    private String produceUserName;

    /**
     * 关键字List
     */
    private List<KeywordAuditItemRpsVO> auditItems;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 待审核关键字数量
     */
    private Integer keywordsCount;

    /**
     * 审核状态
     */
    private String auditStatus;


    public Integer getKeywordAuditId() {
        return keywordAuditId;
    }

    public void setKeywordAuditId(Integer keywordAuditId) {
        this.keywordAuditId = keywordAuditId;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getProduceUserId() {
        return produceUserId;
    }

    public void setProduceUserId(Integer produceUserId) {
        this.produceUserId = produceUserId;
    }

    public String getProduceUserName() {
        return produceUserName;
    }

    public void setProduceUserName(String produceUserName) {
        this.produceUserName = produceUserName;
    }

    public List<KeywordAuditItemRpsVO> getAuditItems() {
        return auditItems;
    }

    public void setAuditItems(List<KeywordAuditItemRpsVO> auditItems) {
        this.auditItems = auditItems;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getKeywordsCount() {
        return keywordsCount;
    }

    public void setKeywordsCount(Integer keywordsCount) {
        this.keywordsCount = keywordsCount;
        if(this.keywordsCount > 0){
            setAuditStatus("待审核");
        }else {
            setAuditStatus("已审核");
        }
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getKeywords(){
        if(CollectionUtils.isEmpty(auditItems)){
            return null;
        }
        List<String> keywordList = Lists.newArrayList();
        auditItems.forEach(item -> keywordList.add(item.getKeyword()));
        String keywords = keywordList.toString();
        return keywords.substring(1, keywords.length() -1);
    }
}
