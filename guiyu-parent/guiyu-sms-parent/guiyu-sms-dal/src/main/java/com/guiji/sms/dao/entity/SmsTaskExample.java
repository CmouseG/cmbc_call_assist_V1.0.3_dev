package com.guiji.sms.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SmsTaskExample() {
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

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNull() {
            addCriterion("phone_num is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIsNotNull() {
            addCriterion("phone_num is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumEqualTo(Integer value) {
            addCriterion("phone_num =", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotEqualTo(Integer value) {
            addCriterion("phone_num <>", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThan(Integer value) {
            addCriterion("phone_num >", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("phone_num >=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThan(Integer value) {
            addCriterion("phone_num <", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumLessThanOrEqualTo(Integer value) {
            addCriterion("phone_num <=", value, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumIn(List<Integer> values) {
            addCriterion("phone_num in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotIn(List<Integer> values) {
            addCriterion("phone_num not in", values, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumBetween(Integer value1, Integer value2) {
            addCriterion("phone_num between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andPhoneNumNotBetween(Integer value1, Integer value2) {
            addCriterion("phone_num not between", value1, value2, "phoneNum");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNull() {
            addCriterion("send_status is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("send_status is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(Integer value) {
            addCriterion("send_status =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(Integer value) {
            addCriterion("send_status <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(Integer value) {
            addCriterion("send_status >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_status >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(Integer value) {
            addCriterion("send_status <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(Integer value) {
            addCriterion("send_status <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<Integer> values) {
            addCriterion("send_status in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<Integer> values) {
            addCriterion("send_status not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(Integer value1, Integer value2) {
            addCriterion("send_status between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("send_status not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendTypeIsNull() {
            addCriterion("send_type is null");
            return (Criteria) this;
        }

        public Criteria andSendTypeIsNotNull() {
            addCriterion("send_type is not null");
            return (Criteria) this;
        }

        public Criteria andSendTypeEqualTo(Integer value) {
            addCriterion("send_type =", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeNotEqualTo(Integer value) {
            addCriterion("send_type <>", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeGreaterThan(Integer value) {
            addCriterion("send_type >", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_type >=", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeLessThan(Integer value) {
            addCriterion("send_type <", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeLessThanOrEqualTo(Integer value) {
            addCriterion("send_type <=", value, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeIn(List<Integer> values) {
            addCriterion("send_type in", values, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeNotIn(List<Integer> values) {
            addCriterion("send_type not in", values, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeBetween(Integer value1, Integer value2) {
            addCriterion("send_type between", value1, value2, "sendType");
            return (Criteria) this;
        }

        public Criteria andSendTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("send_type not between", value1, value2, "sendType");
            return (Criteria) this;
        }

        public Criteria andTunnelNameIsNull() {
            addCriterion("tunnel_name is null");
            return (Criteria) this;
        }

        public Criteria andTunnelNameIsNotNull() {
            addCriterion("tunnel_name is not null");
            return (Criteria) this;
        }

        public Criteria andTunnelNameEqualTo(String value) {
            addCriterion("tunnel_name =", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameNotEqualTo(String value) {
            addCriterion("tunnel_name <>", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameGreaterThan(String value) {
            addCriterion("tunnel_name >", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameGreaterThanOrEqualTo(String value) {
            addCriterion("tunnel_name >=", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameLessThan(String value) {
            addCriterion("tunnel_name <", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameLessThanOrEqualTo(String value) {
            addCriterion("tunnel_name <=", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameLike(String value) {
            addCriterion("tunnel_name like", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameNotLike(String value) {
            addCriterion("tunnel_name not like", value, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameIn(List<String> values) {
            addCriterion("tunnel_name in", values, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameNotIn(List<String> values) {
            addCriterion("tunnel_name not in", values, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameBetween(String value1, String value2) {
            addCriterion("tunnel_name between", value1, value2, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andTunnelNameNotBetween(String value1, String value2) {
            addCriterion("tunnel_name not between", value1, value2, "tunnelName");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdIsNull() {
            addCriterion("sms_template_id is null");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdIsNotNull() {
            addCriterion("sms_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdEqualTo(Integer value) {
            addCriterion("sms_template_id =", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdNotEqualTo(Integer value) {
            addCriterion("sms_template_id <>", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdGreaterThan(Integer value) {
            addCriterion("sms_template_id >", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sms_template_id >=", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdLessThan(Integer value) {
            addCriterion("sms_template_id <", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("sms_template_id <=", value, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdIn(List<Integer> values) {
            addCriterion("sms_template_id in", values, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdNotIn(List<Integer> values) {
            addCriterion("sms_template_id not in", values, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("sms_template_id between", value1, value2, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSmsTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sms_template_id not between", value1, value2, "smsTemplateId");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("send_date is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("send_date is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterion("send_date =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterion("send_date <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterion("send_date >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterion("send_date >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterion("send_date <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterion("send_date <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterion("send_date in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterion("send_date not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterion("send_date between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterion("send_date not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIsNull() {
            addCriterion("auditing_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIsNotNull() {
            addCriterion("auditing_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusEqualTo(Integer value) {
            addCriterion("auditing_status =", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotEqualTo(Integer value) {
            addCriterion("auditing_status <>", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusGreaterThan(Integer value) {
            addCriterion("auditing_status >", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auditing_status >=", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusLessThan(Integer value) {
            addCriterion("auditing_status <", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auditing_status <=", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIn(List<Integer> values) {
            addCriterion("auditing_status in", values, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotIn(List<Integer> values) {
            addCriterion("auditing_status not in", values, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusBetween(Integer value1, Integer value2) {
            addCriterion("auditing_status between", value1, value2, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auditing_status not between", value1, value2, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNull() {
            addCriterion("run_status is null");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNotNull() {
            addCriterion("run_status is not null");
            return (Criteria) this;
        }

        public Criteria andRunStatusEqualTo(Integer value) {
            addCriterion("run_status =", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotEqualTo(Integer value) {
            addCriterion("run_status <>", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThan(Integer value) {
            addCriterion("run_status >", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("run_status >=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThan(Integer value) {
            addCriterion("run_status <", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThanOrEqualTo(Integer value) {
            addCriterion("run_status <=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusIn(List<Integer> values) {
            addCriterion("run_status in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotIn(List<Integer> values) {
            addCriterion("run_status not in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusBetween(Integer value1, Integer value2) {
            addCriterion("run_status between", value1, value2, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("run_status not between", value1, value2, "runStatus");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Integer value) {
            addCriterion("company_id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Integer value) {
            addCriterion("company_id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Integer value) {
            addCriterion("company_id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Integer value) {
            addCriterion("company_id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
            addCriterion("company_id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Integer> values) {
            addCriterion("company_id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Integer> values) {
            addCriterion("company_id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
            addCriterion("company_id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("company_id not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Integer value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Integer value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Integer value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Integer value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Integer> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Integer> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Integer value1, Integer value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_id not between", value1, value2, "createId");
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

        public Criteria andUpdateIdIsNull() {
            addCriterion("update_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateIdIsNotNull() {
            addCriterion("update_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateIdEqualTo(Integer value) {
            addCriterion("update_id =", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotEqualTo(Integer value) {
            addCriterion("update_id <>", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdGreaterThan(Integer value) {
            addCriterion("update_id >", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_id >=", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdLessThan(Integer value) {
            addCriterion("update_id <", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_id <=", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdIn(List<Integer> values) {
            addCriterion("update_id in", values, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotIn(List<Integer> values) {
            addCriterion("update_id not in", values, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdBetween(Integer value1, Integer value2) {
            addCriterion("update_id between", value1, value2, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_id not between", value1, value2, "updateId");
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