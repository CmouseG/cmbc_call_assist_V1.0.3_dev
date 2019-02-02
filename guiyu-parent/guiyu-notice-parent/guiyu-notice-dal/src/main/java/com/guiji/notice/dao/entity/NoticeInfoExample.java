package com.guiji.notice.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public NoticeInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNull() {
            addCriterion("org_code is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("org_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("org_code =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("org_code <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("org_code >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("org_code >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("org_code <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("org_code <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("org_code like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("org_code not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("org_code in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("org_code not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("org_code between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("org_code not between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNull() {
            addCriterion("notice_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNotNull() {
            addCriterion("notice_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeEqualTo(Integer value) {
            addCriterion("notice_type =", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotEqualTo(Integer value) {
            addCriterion("notice_type <>", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThan(Integer value) {
            addCriterion("notice_type >", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_type >=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThan(Integer value) {
            addCriterion("notice_type <", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("notice_type <=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIn(List<Integer> values) {
            addCriterion("notice_type in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotIn(List<Integer> values) {
            addCriterion("notice_type not in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeBetween(Integer value1, Integer value2) {
            addCriterion("notice_type between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_type not between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andMailContentIsNull() {
            addCriterion("mail_content is null");
            return (Criteria) this;
        }

        public Criteria andMailContentIsNotNull() {
            addCriterion("mail_content is not null");
            return (Criteria) this;
        }

        public Criteria andMailContentEqualTo(String value) {
            addCriterion("mail_content =", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotEqualTo(String value) {
            addCriterion("mail_content <>", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentGreaterThan(String value) {
            addCriterion("mail_content >", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentGreaterThanOrEqualTo(String value) {
            addCriterion("mail_content >=", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLessThan(String value) {
            addCriterion("mail_content <", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLessThanOrEqualTo(String value) {
            addCriterion("mail_content <=", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentLike(String value) {
            addCriterion("mail_content like", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotLike(String value) {
            addCriterion("mail_content not like", value, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentIn(List<String> values) {
            addCriterion("mail_content in", values, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotIn(List<String> values) {
            addCriterion("mail_content not in", values, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentBetween(String value1, String value2) {
            addCriterion("mail_content between", value1, value2, "mailContent");
            return (Criteria) this;
        }

        public Criteria andMailContentNotBetween(String value1, String value2) {
            addCriterion("mail_content not between", value1, value2, "mailContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentIsNull() {
            addCriterion("sms_content is null");
            return (Criteria) this;
        }

        public Criteria andSmsContentIsNotNull() {
            addCriterion("sms_content is not null");
            return (Criteria) this;
        }

        public Criteria andSmsContentEqualTo(String value) {
            addCriterion("sms_content =", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentNotEqualTo(String value) {
            addCriterion("sms_content <>", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentGreaterThan(String value) {
            addCriterion("sms_content >", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentGreaterThanOrEqualTo(String value) {
            addCriterion("sms_content >=", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentLessThan(String value) {
            addCriterion("sms_content <", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentLessThanOrEqualTo(String value) {
            addCriterion("sms_content <=", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentLike(String value) {
            addCriterion("sms_content like", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentNotLike(String value) {
            addCriterion("sms_content not like", value, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentIn(List<String> values) {
            addCriterion("sms_content in", values, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentNotIn(List<String> values) {
            addCriterion("sms_content not in", values, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentBetween(String value1, String value2) {
            addCriterion("sms_content between", value1, value2, "smsContent");
            return (Criteria) this;
        }

        public Criteria andSmsContentNotBetween(String value1, String value2) {
            addCriterion("sms_content not between", value1, value2, "smsContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentIsNull() {
            addCriterion("email_content is null");
            return (Criteria) this;
        }

        public Criteria andEmailContentIsNotNull() {
            addCriterion("email_content is not null");
            return (Criteria) this;
        }

        public Criteria andEmailContentEqualTo(String value) {
            addCriterion("email_content =", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotEqualTo(String value) {
            addCriterion("email_content <>", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentGreaterThan(String value) {
            addCriterion("email_content >", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentGreaterThanOrEqualTo(String value) {
            addCriterion("email_content >=", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLessThan(String value) {
            addCriterion("email_content <", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLessThanOrEqualTo(String value) {
            addCriterion("email_content <=", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentLike(String value) {
            addCriterion("email_content like", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotLike(String value) {
            addCriterion("email_content not like", value, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentIn(List<String> values) {
            addCriterion("email_content in", values, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotIn(List<String> values) {
            addCriterion("email_content not in", values, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentBetween(String value1, String value2) {
            addCriterion("email_content between", value1, value2, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailContentNotBetween(String value1, String value2) {
            addCriterion("email_content not between", value1, value2, "emailContent");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIsNull() {
            addCriterion("email_subject is null");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIsNotNull() {
            addCriterion("email_subject is not null");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectEqualTo(String value) {
            addCriterion("email_subject =", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotEqualTo(String value) {
            addCriterion("email_subject <>", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectGreaterThan(String value) {
            addCriterion("email_subject >", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("email_subject >=", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLessThan(String value) {
            addCriterion("email_subject <", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLessThanOrEqualTo(String value) {
            addCriterion("email_subject <=", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLike(String value) {
            addCriterion("email_subject like", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotLike(String value) {
            addCriterion("email_subject not like", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIn(List<String> values) {
            addCriterion("email_subject in", values, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotIn(List<String> values) {
            addCriterion("email_subject not in", values, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectBetween(String value1, String value2) {
            addCriterion("email_subject between", value1, value2, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotBetween(String value1, String value2) {
            addCriterion("email_subject not between", value1, value2, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdIsNull() {
            addCriterion("weixin_template_id is null");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdIsNotNull() {
            addCriterion("weixin_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdEqualTo(String value) {
            addCriterion("weixin_template_id =", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdNotEqualTo(String value) {
            addCriterion("weixin_template_id <>", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdGreaterThan(String value) {
            addCriterion("weixin_template_id >", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_template_id >=", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdLessThan(String value) {
            addCriterion("weixin_template_id <", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("weixin_template_id <=", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdLike(String value) {
            addCriterion("weixin_template_id like", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdNotLike(String value) {
            addCriterion("weixin_template_id not like", value, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdIn(List<String> values) {
            addCriterion("weixin_template_id in", values, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdNotIn(List<String> values) {
            addCriterion("weixin_template_id not in", values, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdBetween(String value1, String value2) {
            addCriterion("weixin_template_id between", value1, value2, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinTemplateIdNotBetween(String value1, String value2) {
            addCriterion("weixin_template_id not between", value1, value2, "weixinTemplateId");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlIsNull() {
            addCriterion("weixin_url is null");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlIsNotNull() {
            addCriterion("weixin_url is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlEqualTo(String value) {
            addCriterion("weixin_url =", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlNotEqualTo(String value) {
            addCriterion("weixin_url <>", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlGreaterThan(String value) {
            addCriterion("weixin_url >", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_url >=", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlLessThan(String value) {
            addCriterion("weixin_url <", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlLessThanOrEqualTo(String value) {
            addCriterion("weixin_url <=", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlLike(String value) {
            addCriterion("weixin_url like", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlNotLike(String value) {
            addCriterion("weixin_url not like", value, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlIn(List<String> values) {
            addCriterion("weixin_url in", values, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlNotIn(List<String> values) {
            addCriterion("weixin_url not in", values, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlBetween(String value1, String value2) {
            addCriterion("weixin_url between", value1, value2, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinUrlNotBetween(String value1, String value2) {
            addCriterion("weixin_url not between", value1, value2, "weixinUrl");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdIsNull() {
            addCriterion("weixin_app_id is null");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdIsNotNull() {
            addCriterion("weixin_app_id is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdEqualTo(String value) {
            addCriterion("weixin_app_id =", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdNotEqualTo(String value) {
            addCriterion("weixin_app_id <>", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdGreaterThan(String value) {
            addCriterion("weixin_app_id >", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_app_id >=", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdLessThan(String value) {
            addCriterion("weixin_app_id <", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdLessThanOrEqualTo(String value) {
            addCriterion("weixin_app_id <=", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdLike(String value) {
            addCriterion("weixin_app_id like", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdNotLike(String value) {
            addCriterion("weixin_app_id not like", value, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdIn(List<String> values) {
            addCriterion("weixin_app_id in", values, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdNotIn(List<String> values) {
            addCriterion("weixin_app_id not in", values, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdBetween(String value1, String value2) {
            addCriterion("weixin_app_id between", value1, value2, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinAppIdNotBetween(String value1, String value2) {
            addCriterion("weixin_app_id not between", value1, value2, "weixinAppId");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathIsNull() {
            addCriterion("weixin_page_path is null");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathIsNotNull() {
            addCriterion("weixin_page_path is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathEqualTo(String value) {
            addCriterion("weixin_page_path =", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathNotEqualTo(String value) {
            addCriterion("weixin_page_path <>", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathGreaterThan(String value) {
            addCriterion("weixin_page_path >", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_page_path >=", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathLessThan(String value) {
            addCriterion("weixin_page_path <", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathLessThanOrEqualTo(String value) {
            addCriterion("weixin_page_path <=", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathLike(String value) {
            addCriterion("weixin_page_path like", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathNotLike(String value) {
            addCriterion("weixin_page_path not like", value, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathIn(List<String> values) {
            addCriterion("weixin_page_path in", values, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathNotIn(List<String> values) {
            addCriterion("weixin_page_path not in", values, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathBetween(String value1, String value2) {
            addCriterion("weixin_page_path between", value1, value2, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinPagePathNotBetween(String value1, String value2) {
            addCriterion("weixin_page_path not between", value1, value2, "weixinPagePath");
            return (Criteria) this;
        }

        public Criteria andWeixinDataIsNull() {
            addCriterion("weixin_data is null");
            return (Criteria) this;
        }

        public Criteria andWeixinDataIsNotNull() {
            addCriterion("weixin_data is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinDataEqualTo(String value) {
            addCriterion("weixin_data =", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataNotEqualTo(String value) {
            addCriterion("weixin_data <>", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataGreaterThan(String value) {
            addCriterion("weixin_data >", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_data >=", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataLessThan(String value) {
            addCriterion("weixin_data <", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataLessThanOrEqualTo(String value) {
            addCriterion("weixin_data <=", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataLike(String value) {
            addCriterion("weixin_data like", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataNotLike(String value) {
            addCriterion("weixin_data not like", value, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataIn(List<String> values) {
            addCriterion("weixin_data in", values, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataNotIn(List<String> values) {
            addCriterion("weixin_data not in", values, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataBetween(String value1, String value2) {
            addCriterion("weixin_data between", value1, value2, "weixinData");
            return (Criteria) this;
        }

        public Criteria andWeixinDataNotBetween(String value1, String value2) {
            addCriterion("weixin_data not between", value1, value2, "weixinData");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}