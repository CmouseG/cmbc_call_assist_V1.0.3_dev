package com.guiji.notice.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public NoticeSettingExample() {
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

        public Criteria andNoticeOverTypeIsNull() {
            addCriterion("notice_over_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeIsNotNull() {
            addCriterion("notice_over_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeEqualTo(Integer value) {
            addCriterion("notice_over_type =", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeNotEqualTo(Integer value) {
            addCriterion("notice_over_type <>", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeGreaterThan(Integer value) {
            addCriterion("notice_over_type >", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_over_type >=", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeLessThan(Integer value) {
            addCriterion("notice_over_type <", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeLessThanOrEqualTo(Integer value) {
            addCriterion("notice_over_type <=", value, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeIn(List<Integer> values) {
            addCriterion("notice_over_type in", values, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeNotIn(List<Integer> values) {
            addCriterion("notice_over_type not in", values, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeBetween(Integer value1, Integer value2) {
            addCriterion("notice_over_type between", value1, value2, "noticeOverType");
            return (Criteria) this;
        }

        public Criteria andNoticeOverTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_over_type not between", value1, value2, "noticeOverType");
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

        public Criteria andIsSendMailIsNull() {
            addCriterion("is_send_mail is null");
            return (Criteria) this;
        }

        public Criteria andIsSendMailIsNotNull() {
            addCriterion("is_send_mail is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendMailEqualTo(Boolean value) {
            addCriterion("is_send_mail =", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailNotEqualTo(Boolean value) {
            addCriterion("is_send_mail <>", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailGreaterThan(Boolean value) {
            addCriterion("is_send_mail >", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_send_mail >=", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailLessThan(Boolean value) {
            addCriterion("is_send_mail <", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailLessThanOrEqualTo(Boolean value) {
            addCriterion("is_send_mail <=", value, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailIn(List<Boolean> values) {
            addCriterion("is_send_mail in", values, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailNotIn(List<Boolean> values) {
            addCriterion("is_send_mail not in", values, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_mail between", value1, value2, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendMailNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_mail not between", value1, value2, "isSendMail");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIsNull() {
            addCriterion("is_send_weixin is null");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIsNotNull() {
            addCriterion("is_send_weixin is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinEqualTo(Boolean value) {
            addCriterion("is_send_weixin =", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotEqualTo(Boolean value) {
            addCriterion("is_send_weixin <>", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinGreaterThan(Boolean value) {
            addCriterion("is_send_weixin >", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_send_weixin >=", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinLessThan(Boolean value) {
            addCriterion("is_send_weixin <", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinLessThanOrEqualTo(Boolean value) {
            addCriterion("is_send_weixin <=", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIn(List<Boolean> values) {
            addCriterion("is_send_weixin in", values, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotIn(List<Boolean> values) {
            addCriterion("is_send_weixin not in", values, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_weixin between", value1, value2, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_weixin not between", value1, value2, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIsNull() {
            addCriterion("is_send_email is null");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIsNotNull() {
            addCriterion("is_send_email is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailEqualTo(Boolean value) {
            addCriterion("is_send_email =", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotEqualTo(Boolean value) {
            addCriterion("is_send_email <>", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailGreaterThan(Boolean value) {
            addCriterion("is_send_email >", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_send_email >=", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailLessThan(Boolean value) {
            addCriterion("is_send_email <", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailLessThanOrEqualTo(Boolean value) {
            addCriterion("is_send_email <=", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIn(List<Boolean> values) {
            addCriterion("is_send_email in", values, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotIn(List<Boolean> values) {
            addCriterion("is_send_email not in", values, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_email between", value1, value2, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_email not between", value1, value2, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsIsNull() {
            addCriterion("is_send_sms is null");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsIsNotNull() {
            addCriterion("is_send_sms is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsEqualTo(Boolean value) {
            addCriterion("is_send_sms =", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsNotEqualTo(Boolean value) {
            addCriterion("is_send_sms <>", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsGreaterThan(Boolean value) {
            addCriterion("is_send_sms >", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_send_sms >=", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsLessThan(Boolean value) {
            addCriterion("is_send_sms <", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsLessThanOrEqualTo(Boolean value) {
            addCriterion("is_send_sms <=", value, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsIn(List<Boolean> values) {
            addCriterion("is_send_sms in", values, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsNotIn(List<Boolean> values) {
            addCriterion("is_send_sms not in", values, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_sms between", value1, value2, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andIsSendSmsNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_send_sms not between", value1, value2, "isSendSms");
            return (Criteria) this;
        }

        public Criteria andReceiversIsNull() {
            addCriterion("receivers is null");
            return (Criteria) this;
        }

        public Criteria andReceiversIsNotNull() {
            addCriterion("receivers is not null");
            return (Criteria) this;
        }

        public Criteria andReceiversEqualTo(String value) {
            addCriterion("receivers =", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotEqualTo(String value) {
            addCriterion("receivers <>", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversGreaterThan(String value) {
            addCriterion("receivers >", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversGreaterThanOrEqualTo(String value) {
            addCriterion("receivers >=", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLessThan(String value) {
            addCriterion("receivers <", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLessThanOrEqualTo(String value) {
            addCriterion("receivers <=", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversLike(String value) {
            addCriterion("receivers like", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotLike(String value) {
            addCriterion("receivers not like", value, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversIn(List<String> values) {
            addCriterion("receivers in", values, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotIn(List<String> values) {
            addCriterion("receivers not in", values, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversBetween(String value1, String value2) {
            addCriterion("receivers between", value1, value2, "receivers");
            return (Criteria) this;
        }

        public Criteria andReceiversNotBetween(String value1, String value2) {
            addCriterion("receivers not between", value1, value2, "receivers");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateaUserIsNull() {
            addCriterion("createa_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateaUserIsNotNull() {
            addCriterion("createa_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateaUserEqualTo(Integer value) {
            addCriterion("createa_user =", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserNotEqualTo(Integer value) {
            addCriterion("createa_user <>", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserGreaterThan(Integer value) {
            addCriterion("createa_user >", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("createa_user >=", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserLessThan(Integer value) {
            addCriterion("createa_user <", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserLessThanOrEqualTo(Integer value) {
            addCriterion("createa_user <=", value, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserIn(List<Integer> values) {
            addCriterion("createa_user in", values, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserNotIn(List<Integer> values) {
            addCriterion("createa_user not in", values, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserBetween(Integer value1, Integer value2) {
            addCriterion("createa_user between", value1, value2, "createaUser");
            return (Criteria) this;
        }

        public Criteria andCreateaUserNotBetween(Integer value1, Integer value2) {
            addCriterion("createa_user not between", value1, value2, "createaUser");
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