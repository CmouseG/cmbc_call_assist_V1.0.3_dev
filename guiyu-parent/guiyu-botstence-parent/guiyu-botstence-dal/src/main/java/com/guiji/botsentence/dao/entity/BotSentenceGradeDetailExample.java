package com.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotSentenceGradeDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceGradeDetailExample() {
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

        public Criteria andProcessIdIsNull() {
            addCriterion("process_id is null");
            return (Criteria) this;
        }

        public Criteria andProcessIdIsNotNull() {
            addCriterion("process_id is not null");
            return (Criteria) this;
        }

        public Criteria andProcessIdEqualTo(String value) {
            addCriterion("process_id =", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotEqualTo(String value) {
            addCriterion("process_id <>", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThan(String value) {
            addCriterion("process_id >", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThanOrEqualTo(String value) {
            addCriterion("process_id >=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThan(String value) {
            addCriterion("process_id <", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThanOrEqualTo(String value) {
            addCriterion("process_id <=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLike(String value) {
            addCriterion("process_id like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotLike(String value) {
            addCriterion("process_id not like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdIn(List<String> values) {
            addCriterion("process_id in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotIn(List<String> values) {
            addCriterion("process_id not in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdBetween(String value1, String value2) {
            addCriterion("process_id between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotBetween(String value1, String value2) {
            addCriterion("process_id not between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("template_id like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("template_id not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andShowInfoIsNull() {
            addCriterion("show_info is null");
            return (Criteria) this;
        }

        public Criteria andShowInfoIsNotNull() {
            addCriterion("show_info is not null");
            return (Criteria) this;
        }

        public Criteria andShowInfoEqualTo(String value) {
            addCriterion("show_info =", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoNotEqualTo(String value) {
            addCriterion("show_info <>", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoGreaterThan(String value) {
            addCriterion("show_info >", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoGreaterThanOrEqualTo(String value) {
            addCriterion("show_info >=", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoLessThan(String value) {
            addCriterion("show_info <", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoLessThanOrEqualTo(String value) {
            addCriterion("show_info <=", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoLike(String value) {
            addCriterion("show_info like", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoNotLike(String value) {
            addCriterion("show_info not like", value, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoIn(List<String> values) {
            addCriterion("show_info in", values, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoNotIn(List<String> values) {
            addCriterion("show_info not in", values, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoBetween(String value1, String value2) {
            addCriterion("show_info between", value1, value2, "showInfo");
            return (Criteria) this;
        }

        public Criteria andShowInfoNotBetween(String value1, String value2) {
            addCriterion("show_info not between", value1, value2, "showInfo");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNull() {
            addCriterion("domain_type is null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNotNull() {
            addCriterion("domain_type is not null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeEqualTo(String value) {
            addCriterion("domain_type =", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotEqualTo(String value) {
            addCriterion("domain_type <>", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThan(String value) {
            addCriterion("domain_type >", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThanOrEqualTo(String value) {
            addCriterion("domain_type >=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThan(String value) {
            addCriterion("domain_type <", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThanOrEqualTo(String value) {
            addCriterion("domain_type <=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLike(String value) {
            addCriterion("domain_type like", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotLike(String value) {
            addCriterion("domain_type not like", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIn(List<String> values) {
            addCriterion("domain_type in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotIn(List<String> values) {
            addCriterion("domain_type not in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeBetween(String value1, String value2) {
            addCriterion("domain_type between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotBetween(String value1, String value2) {
            addCriterion("domain_type not between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainNameIsNull() {
            addCriterion("domain_name is null");
            return (Criteria) this;
        }

        public Criteria andDomainNameIsNotNull() {
            addCriterion("domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andDomainNameEqualTo(String value) {
            addCriterion("domain_name =", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotEqualTo(String value) {
            addCriterion("domain_name <>", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThan(String value) {
            addCriterion("domain_name >", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("domain_name >=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThan(String value) {
            addCriterion("domain_name <", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThanOrEqualTo(String value) {
            addCriterion("domain_name <=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLike(String value) {
            addCriterion("domain_name like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotLike(String value) {
            addCriterion("domain_name not like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameIn(List<String> values) {
            addCriterion("domain_name in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotIn(List<String> values) {
            addCriterion("domain_name not in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameBetween(String value1, String value2) {
            addCriterion("domain_name between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotBetween(String value1, String value2) {
            addCriterion("domain_name not between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeIsNull() {
            addCriterion("refuse_type is null");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeIsNotNull() {
            addCriterion("refuse_type is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeEqualTo(String value) {
            addCriterion("refuse_type =", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeNotEqualTo(String value) {
            addCriterion("refuse_type <>", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeGreaterThan(String value) {
            addCriterion("refuse_type >", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("refuse_type >=", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeLessThan(String value) {
            addCriterion("refuse_type <", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeLessThanOrEqualTo(String value) {
            addCriterion("refuse_type <=", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeLike(String value) {
            addCriterion("refuse_type like", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeNotLike(String value) {
            addCriterion("refuse_type not like", value, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeIn(List<String> values) {
            addCriterion("refuse_type in", values, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeNotIn(List<String> values) {
            addCriterion("refuse_type not in", values, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeBetween(String value1, String value2) {
            addCriterion("refuse_type between", value1, value2, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTypeNotBetween(String value1, String value2) {
            addCriterion("refuse_type not between", value1, value2, "refuseType");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIsNull() {
            addCriterion("refuse_times is null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIsNotNull() {
            addCriterion("refuse_times is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesEqualTo(String value) {
            addCriterion("refuse_times =", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotEqualTo(String value) {
            addCriterion("refuse_times <>", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesGreaterThan(String value) {
            addCriterion("refuse_times >", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesGreaterThanOrEqualTo(String value) {
            addCriterion("refuse_times >=", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesLessThan(String value) {
            addCriterion("refuse_times <", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesLessThanOrEqualTo(String value) {
            addCriterion("refuse_times <=", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesLike(String value) {
            addCriterion("refuse_times like", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotLike(String value) {
            addCriterion("refuse_times not like", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIn(List<String> values) {
            addCriterion("refuse_times in", values, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotIn(List<String> values) {
            addCriterion("refuse_times not in", values, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesBetween(String value1, String value2) {
            addCriterion("refuse_times between", value1, value2, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotBetween(String value1, String value2) {
            addCriterion("refuse_times not between", value1, value2, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeIsNull() {
            addCriterion("dialog_times_type is null");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeIsNotNull() {
            addCriterion("dialog_times_type is not null");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeEqualTo(String value) {
            addCriterion("dialog_times_type =", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeNotEqualTo(String value) {
            addCriterion("dialog_times_type <>", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeGreaterThan(String value) {
            addCriterion("dialog_times_type >", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dialog_times_type >=", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeLessThan(String value) {
            addCriterion("dialog_times_type <", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeLessThanOrEqualTo(String value) {
            addCriterion("dialog_times_type <=", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeLike(String value) {
            addCriterion("dialog_times_type like", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeNotLike(String value) {
            addCriterion("dialog_times_type not like", value, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeIn(List<String> values) {
            addCriterion("dialog_times_type in", values, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeNotIn(List<String> values) {
            addCriterion("dialog_times_type not in", values, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeBetween(String value1, String value2) {
            addCriterion("dialog_times_type between", value1, value2, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesTypeNotBetween(String value1, String value2) {
            addCriterion("dialog_times_type not between", value1, value2, "dialogTimesType");
            return (Criteria) this;
        }

        public Criteria andDialogTimesIsNull() {
            addCriterion("dialog_times is null");
            return (Criteria) this;
        }

        public Criteria andDialogTimesIsNotNull() {
            addCriterion("dialog_times is not null");
            return (Criteria) this;
        }

        public Criteria andDialogTimesEqualTo(String value) {
            addCriterion("dialog_times =", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesNotEqualTo(String value) {
            addCriterion("dialog_times <>", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesGreaterThan(String value) {
            addCriterion("dialog_times >", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesGreaterThanOrEqualTo(String value) {
            addCriterion("dialog_times >=", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesLessThan(String value) {
            addCriterion("dialog_times <", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesLessThanOrEqualTo(String value) {
            addCriterion("dialog_times <=", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesLike(String value) {
            addCriterion("dialog_times like", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesNotLike(String value) {
            addCriterion("dialog_times not like", value, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesIn(List<String> values) {
            addCriterion("dialog_times in", values, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesNotIn(List<String> values) {
            addCriterion("dialog_times not in", values, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesBetween(String value1, String value2) {
            addCriterion("dialog_times between", value1, value2, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogTimesNotBetween(String value1, String value2) {
            addCriterion("dialog_times not between", value1, value2, "dialogTimes");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeIsNull() {
            addCriterion("dialog_duration_type is null");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeIsNotNull() {
            addCriterion("dialog_duration_type is not null");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeEqualTo(String value) {
            addCriterion("dialog_duration_type =", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeNotEqualTo(String value) {
            addCriterion("dialog_duration_type <>", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeGreaterThan(String value) {
            addCriterion("dialog_duration_type >", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dialog_duration_type >=", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeLessThan(String value) {
            addCriterion("dialog_duration_type <", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeLessThanOrEqualTo(String value) {
            addCriterion("dialog_duration_type <=", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeLike(String value) {
            addCriterion("dialog_duration_type like", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeNotLike(String value) {
            addCriterion("dialog_duration_type not like", value, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeIn(List<String> values) {
            addCriterion("dialog_duration_type in", values, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeNotIn(List<String> values) {
            addCriterion("dialog_duration_type not in", values, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeBetween(String value1, String value2) {
            addCriterion("dialog_duration_type between", value1, value2, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationTypeNotBetween(String value1, String value2) {
            addCriterion("dialog_duration_type not between", value1, value2, "dialogDurationType");
            return (Criteria) this;
        }

        public Criteria andDialogDurationIsNull() {
            addCriterion("dialog_duration is null");
            return (Criteria) this;
        }

        public Criteria andDialogDurationIsNotNull() {
            addCriterion("dialog_duration is not null");
            return (Criteria) this;
        }

        public Criteria andDialogDurationEqualTo(String value) {
            addCriterion("dialog_duration =", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationNotEqualTo(String value) {
            addCriterion("dialog_duration <>", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationGreaterThan(String value) {
            addCriterion("dialog_duration >", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationGreaterThanOrEqualTo(String value) {
            addCriterion("dialog_duration >=", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationLessThan(String value) {
            addCriterion("dialog_duration <", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationLessThanOrEqualTo(String value) {
            addCriterion("dialog_duration <=", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationLike(String value) {
            addCriterion("dialog_duration like", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationNotLike(String value) {
            addCriterion("dialog_duration not like", value, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationIn(List<String> values) {
            addCriterion("dialog_duration in", values, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationNotIn(List<String> values) {
            addCriterion("dialog_duration not in", values, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationBetween(String value1, String value2) {
            addCriterion("dialog_duration between", value1, value2, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andDialogDurationNotBetween(String value1, String value2) {
            addCriterion("dialog_duration not between", value1, value2, "dialogDuration");
            return (Criteria) this;
        }

        public Criteria andAskTypeIsNull() {
            addCriterion("ask_type is null");
            return (Criteria) this;
        }

        public Criteria andAskTypeIsNotNull() {
            addCriterion("ask_type is not null");
            return (Criteria) this;
        }

        public Criteria andAskTypeEqualTo(String value) {
            addCriterion("ask_type =", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeNotEqualTo(String value) {
            addCriterion("ask_type <>", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeGreaterThan(String value) {
            addCriterion("ask_type >", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ask_type >=", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeLessThan(String value) {
            addCriterion("ask_type <", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeLessThanOrEqualTo(String value) {
            addCriterion("ask_type <=", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeLike(String value) {
            addCriterion("ask_type like", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeNotLike(String value) {
            addCriterion("ask_type not like", value, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeIn(List<String> values) {
            addCriterion("ask_type in", values, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeNotIn(List<String> values) {
            addCriterion("ask_type not in", values, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeBetween(String value1, String value2) {
            addCriterion("ask_type between", value1, value2, "askType");
            return (Criteria) this;
        }

        public Criteria andAskTypeNotBetween(String value1, String value2) {
            addCriterion("ask_type not between", value1, value2, "askType");
            return (Criteria) this;
        }

        public Criteria andAskNameIsNull() {
            addCriterion("ask_name is null");
            return (Criteria) this;
        }

        public Criteria andAskNameIsNotNull() {
            addCriterion("ask_name is not null");
            return (Criteria) this;
        }

        public Criteria andAskNameEqualTo(String value) {
            addCriterion("ask_name =", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameNotEqualTo(String value) {
            addCriterion("ask_name <>", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameGreaterThan(String value) {
            addCriterion("ask_name >", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameGreaterThanOrEqualTo(String value) {
            addCriterion("ask_name >=", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameLessThan(String value) {
            addCriterion("ask_name <", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameLessThanOrEqualTo(String value) {
            addCriterion("ask_name <=", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameLike(String value) {
            addCriterion("ask_name like", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameNotLike(String value) {
            addCriterion("ask_name not like", value, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameIn(List<String> values) {
            addCriterion("ask_name in", values, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameNotIn(List<String> values) {
            addCriterion("ask_name not in", values, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameBetween(String value1, String value2) {
            addCriterion("ask_name between", value1, value2, "askName");
            return (Criteria) this;
        }

        public Criteria andAskNameNotBetween(String value1, String value2) {
            addCriterion("ask_name not between", value1, value2, "askName");
            return (Criteria) this;
        }

        public Criteria andAskTimesIsNull() {
            addCriterion("ask_times is null");
            return (Criteria) this;
        }

        public Criteria andAskTimesIsNotNull() {
            addCriterion("ask_times is not null");
            return (Criteria) this;
        }

        public Criteria andAskTimesEqualTo(String value) {
            addCriterion("ask_times =", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesNotEqualTo(String value) {
            addCriterion("ask_times <>", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesGreaterThan(String value) {
            addCriterion("ask_times >", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesGreaterThanOrEqualTo(String value) {
            addCriterion("ask_times >=", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesLessThan(String value) {
            addCriterion("ask_times <", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesLessThanOrEqualTo(String value) {
            addCriterion("ask_times <=", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesLike(String value) {
            addCriterion("ask_times like", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesNotLike(String value) {
            addCriterion("ask_times not like", value, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesIn(List<String> values) {
            addCriterion("ask_times in", values, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesNotIn(List<String> values) {
            addCriterion("ask_times not in", values, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesBetween(String value1, String value2) {
            addCriterion("ask_times between", value1, value2, "askTimes");
            return (Criteria) this;
        }

        public Criteria andAskTimesNotBetween(String value1, String value2) {
            addCriterion("ask_times not between", value1, value2, "askTimes");
            return (Criteria) this;
        }

        public Criteria andEvaluateIsNull() {
            addCriterion("evaluate is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateIsNotNull() {
            addCriterion("evaluate is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateEqualTo(String value) {
            addCriterion("evaluate =", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateNotEqualTo(String value) {
            addCriterion("evaluate <>", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateGreaterThan(String value) {
            addCriterion("evaluate >", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateGreaterThanOrEqualTo(String value) {
            addCriterion("evaluate >=", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateLessThan(String value) {
            addCriterion("evaluate <", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateLessThanOrEqualTo(String value) {
            addCriterion("evaluate <=", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateLike(String value) {
            addCriterion("evaluate like", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateNotLike(String value) {
            addCriterion("evaluate not like", value, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateIn(List<String> values) {
            addCriterion("evaluate in", values, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateNotIn(List<String> values) {
            addCriterion("evaluate not in", values, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateBetween(String value1, String value2) {
            addCriterion("evaluate between", value1, value2, "evaluate");
            return (Criteria) this;
        }

        public Criteria andEvaluateNotBetween(String value1, String value2) {
            addCriterion("evaluate not between", value1, value2, "evaluate");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIsNull() {
            addCriterion("crt_time is null");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIsNotNull() {
            addCriterion("crt_time is not null");
            return (Criteria) this;
        }

        public Criteria andCrtTimeEqualTo(Date value) {
            addCriterion("crt_time =", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotEqualTo(Date value) {
            addCriterion("crt_time <>", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeGreaterThan(Date value) {
            addCriterion("crt_time >", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("crt_time >=", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeLessThan(Date value) {
            addCriterion("crt_time <", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeLessThanOrEqualTo(Date value) {
            addCriterion("crt_time <=", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIn(List<Date> values) {
            addCriterion("crt_time in", values, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotIn(List<Date> values) {
            addCriterion("crt_time not in", values, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeBetween(Date value1, Date value2) {
            addCriterion("crt_time between", value1, value2, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotBetween(Date value1, Date value2) {
            addCriterion("crt_time not between", value1, value2, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtUserIsNull() {
            addCriterion("crt_user is null");
            return (Criteria) this;
        }

        public Criteria andCrtUserIsNotNull() {
            addCriterion("crt_user is not null");
            return (Criteria) this;
        }

        public Criteria andCrtUserEqualTo(String value) {
            addCriterion("crt_user =", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotEqualTo(String value) {
            addCriterion("crt_user <>", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThan(String value) {
            addCriterion("crt_user >", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThanOrEqualTo(String value) {
            addCriterion("crt_user >=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThan(String value) {
            addCriterion("crt_user <", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThanOrEqualTo(String value) {
            addCriterion("crt_user <=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLike(String value) {
            addCriterion("crt_user like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotLike(String value) {
            addCriterion("crt_user not like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserIn(List<String> values) {
            addCriterion("crt_user in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotIn(List<String> values) {
            addCriterion("crt_user not in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserBetween(String value1, String value2) {
            addCriterion("crt_user between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotBetween(String value1, String value2) {
            addCriterion("crt_user not between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNull() {
            addCriterion("lst_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNotNull() {
            addCriterion("lst_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeEqualTo(Date value) {
            addCriterion("lst_update_time =", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotEqualTo(Date value) {
            addCriterion("lst_update_time <>", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThan(Date value) {
            addCriterion("lst_update_time >", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lst_update_time >=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThan(Date value) {
            addCriterion("lst_update_time <", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("lst_update_time <=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIn(List<Date> values) {
            addCriterion("lst_update_time in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotIn(List<Date> values) {
            addCriterion("lst_update_time not in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("lst_update_time between", value1, value2, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("lst_update_time not between", value1, value2, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNull() {
            addCriterion("lst_update_user is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNotNull() {
            addCriterion("lst_update_user is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserEqualTo(String value) {
            addCriterion("lst_update_user =", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotEqualTo(String value) {
            addCriterion("lst_update_user <>", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThan(String value) {
            addCriterion("lst_update_user >", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("lst_update_user >=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThan(String value) {
            addCriterion("lst_update_user <", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("lst_update_user <=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLike(String value) {
            addCriterion("lst_update_user like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotLike(String value) {
            addCriterion("lst_update_user not like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIn(List<String> values) {
            addCriterion("lst_update_user in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotIn(List<String> values) {
            addCriterion("lst_update_user not in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserBetween(String value1, String value2) {
            addCriterion("lst_update_user between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotBetween(String value1, String value2) {
            addCriterion("lst_update_user not between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andGradeOrderIsNull() {
            addCriterion("grade_order is null");
            return (Criteria) this;
        }

        public Criteria andGradeOrderIsNotNull() {
            addCriterion("grade_order is not null");
            return (Criteria) this;
        }

        public Criteria andGradeOrderEqualTo(String value) {
            addCriterion("grade_order =", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderNotEqualTo(String value) {
            addCriterion("grade_order <>", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderGreaterThan(String value) {
            addCriterion("grade_order >", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderGreaterThanOrEqualTo(String value) {
            addCriterion("grade_order >=", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderLessThan(String value) {
            addCriterion("grade_order <", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderLessThanOrEqualTo(String value) {
            addCriterion("grade_order <=", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderLike(String value) {
            addCriterion("grade_order like", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderNotLike(String value) {
            addCriterion("grade_order not like", value, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderIn(List<String> values) {
            addCriterion("grade_order in", values, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderNotIn(List<String> values) {
            addCriterion("grade_order not in", values, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderBetween(String value1, String value2) {
            addCriterion("grade_order between", value1, value2, "gradeOrder");
            return (Criteria) this;
        }

        public Criteria andGradeOrderNotBetween(String value1, String value2) {
            addCriterion("grade_order not between", value1, value2, "gradeOrder");
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