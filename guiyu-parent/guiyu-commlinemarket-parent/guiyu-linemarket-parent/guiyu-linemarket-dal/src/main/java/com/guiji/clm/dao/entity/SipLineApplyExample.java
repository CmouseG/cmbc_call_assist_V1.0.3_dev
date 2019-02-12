package com.guiji.clm.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SipLineApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SipLineApplyExample() {
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

        public Criteria andSipLineIdIsNull() {
            addCriterion("sip_line_id is null");
            return (Criteria) this;
        }

        public Criteria andSipLineIdIsNotNull() {
            addCriterion("sip_line_id is not null");
            return (Criteria) this;
        }

        public Criteria andSipLineIdEqualTo(Integer value) {
            addCriterion("sip_line_id =", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdNotEqualTo(Integer value) {
            addCriterion("sip_line_id <>", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdGreaterThan(Integer value) {
            addCriterion("sip_line_id >", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sip_line_id >=", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdLessThan(Integer value) {
            addCriterion("sip_line_id <", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdLessThanOrEqualTo(Integer value) {
            addCriterion("sip_line_id <=", value, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdIn(List<Integer> values) {
            addCriterion("sip_line_id in", values, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdNotIn(List<Integer> values) {
            addCriterion("sip_line_id not in", values, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdBetween(Integer value1, Integer value2) {
            addCriterion("sip_line_id between", value1, value2, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andSipLineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sip_line_id not between", value1, value2, "sipLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdIsNull() {
            addCriterion("agent_line_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdIsNotNull() {
            addCriterion("agent_line_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdEqualTo(Integer value) {
            addCriterion("agent_line_id =", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdNotEqualTo(Integer value) {
            addCriterion("agent_line_id <>", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdGreaterThan(Integer value) {
            addCriterion("agent_line_id >", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_line_id >=", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdLessThan(Integer value) {
            addCriterion("agent_line_id <", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_line_id <=", value, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdIn(List<Integer> values) {
            addCriterion("agent_line_id in", values, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdNotIn(List<Integer> values) {
            addCriterion("agent_line_id not in", values, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_line_id between", value1, value2, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andAgentLineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_line_id not between", value1, value2, "agentLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdIsNull() {
            addCriterion("up_sip_line_id is null");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdIsNotNull() {
            addCriterion("up_sip_line_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdEqualTo(Integer value) {
            addCriterion("up_sip_line_id =", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdNotEqualTo(Integer value) {
            addCriterion("up_sip_line_id <>", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdGreaterThan(Integer value) {
            addCriterion("up_sip_line_id >", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("up_sip_line_id >=", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdLessThan(Integer value) {
            addCriterion("up_sip_line_id <", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdLessThanOrEqualTo(Integer value) {
            addCriterion("up_sip_line_id <=", value, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdIn(List<Integer> values) {
            addCriterion("up_sip_line_id in", values, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdNotIn(List<Integer> values) {
            addCriterion("up_sip_line_id not in", values, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdBetween(Integer value1, Integer value2) {
            addCriterion("up_sip_line_id between", value1, value2, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andUpSipLineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("up_sip_line_id not between", value1, value2, "upSipLineId");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNull() {
            addCriterion("line_name is null");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNotNull() {
            addCriterion("line_name is not null");
            return (Criteria) this;
        }

        public Criteria andLineNameEqualTo(String value) {
            addCriterion("line_name =", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotEqualTo(String value) {
            addCriterion("line_name <>", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThan(String value) {
            addCriterion("line_name >", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThanOrEqualTo(String value) {
            addCriterion("line_name >=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThan(String value) {
            addCriterion("line_name <", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThanOrEqualTo(String value) {
            addCriterion("line_name <=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLike(String value) {
            addCriterion("line_name like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotLike(String value) {
            addCriterion("line_name not like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameIn(List<String> values) {
            addCriterion("line_name in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotIn(List<String> values) {
            addCriterion("line_name not in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameBetween(String value1, String value2) {
            addCriterion("line_name between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotBetween(String value1, String value2) {
            addCriterion("line_name not between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNull() {
            addCriterion("supplier is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNotNull() {
            addCriterion("supplier is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualTo(String value) {
            addCriterion("supplier =", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(String value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(String value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(String value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(String value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(String value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLike(String value) {
            addCriterion("supplier like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotLike(String value) {
            addCriterion("supplier not like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierIn(List<String> values) {
            addCriterion("supplier in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotIn(List<String> values) {
            addCriterion("supplier not in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierBetween(String value1, String value2) {
            addCriterion("supplier between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotBetween(String value1, String value2) {
            addCriterion("supplier not between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andOvertAreaIsNull() {
            addCriterion("overt_area is null");
            return (Criteria) this;
        }

        public Criteria andOvertAreaIsNotNull() {
            addCriterion("overt_area is not null");
            return (Criteria) this;
        }

        public Criteria andOvertAreaEqualTo(String value) {
            addCriterion("overt_area =", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaNotEqualTo(String value) {
            addCriterion("overt_area <>", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaGreaterThan(String value) {
            addCriterion("overt_area >", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaGreaterThanOrEqualTo(String value) {
            addCriterion("overt_area >=", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaLessThan(String value) {
            addCriterion("overt_area <", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaLessThanOrEqualTo(String value) {
            addCriterion("overt_area <=", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaLike(String value) {
            addCriterion("overt_area like", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaNotLike(String value) {
            addCriterion("overt_area not like", value, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaIn(List<String> values) {
            addCriterion("overt_area in", values, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaNotIn(List<String> values) {
            addCriterion("overt_area not in", values, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaBetween(String value1, String value2) {
            addCriterion("overt_area between", value1, value2, "overtArea");
            return (Criteria) this;
        }

        public Criteria andOvertAreaNotBetween(String value1, String value2) {
            addCriterion("overt_area not between", value1, value2, "overtArea");
            return (Criteria) this;
        }

        public Criteria andCallDirecIsNull() {
            addCriterion("call_direc is null");
            return (Criteria) this;
        }

        public Criteria andCallDirecIsNotNull() {
            addCriterion("call_direc is not null");
            return (Criteria) this;
        }

        public Criteria andCallDirecEqualTo(Integer value) {
            addCriterion("call_direc =", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecNotEqualTo(Integer value) {
            addCriterion("call_direc <>", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecGreaterThan(Integer value) {
            addCriterion("call_direc >", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_direc >=", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecLessThan(Integer value) {
            addCriterion("call_direc <", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecLessThanOrEqualTo(Integer value) {
            addCriterion("call_direc <=", value, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecIn(List<Integer> values) {
            addCriterion("call_direc in", values, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecNotIn(List<Integer> values) {
            addCriterion("call_direc not in", values, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecBetween(Integer value1, Integer value2) {
            addCriterion("call_direc between", value1, value2, "callDirec");
            return (Criteria) this;
        }

        public Criteria andCallDirecNotBetween(Integer value1, Integer value2) {
            addCriterion("call_direc not between", value1, value2, "callDirec");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsIsNull() {
            addCriterion("max_concurrent_calls is null");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsIsNotNull() {
            addCriterion("max_concurrent_calls is not null");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsEqualTo(Integer value) {
            addCriterion("max_concurrent_calls =", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsNotEqualTo(Integer value) {
            addCriterion("max_concurrent_calls <>", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsGreaterThan(Integer value) {
            addCriterion("max_concurrent_calls >", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_concurrent_calls >=", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsLessThan(Integer value) {
            addCriterion("max_concurrent_calls <", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsLessThanOrEqualTo(Integer value) {
            addCriterion("max_concurrent_calls <=", value, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsIn(List<Integer> values) {
            addCriterion("max_concurrent_calls in", values, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsNotIn(List<Integer> values) {
            addCriterion("max_concurrent_calls not in", values, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsBetween(Integer value1, Integer value2) {
            addCriterion("max_concurrent_calls between", value1, value2, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andMaxConcurrentCallsNotBetween(Integer value1, Integer value2) {
            addCriterion("max_concurrent_calls not between", value1, value2, "maxConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andTemplatesIsNull() {
            addCriterion("templates is null");
            return (Criteria) this;
        }

        public Criteria andTemplatesIsNotNull() {
            addCriterion("templates is not null");
            return (Criteria) this;
        }

        public Criteria andTemplatesEqualTo(String value) {
            addCriterion("templates =", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesNotEqualTo(String value) {
            addCriterion("templates <>", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesGreaterThan(String value) {
            addCriterion("templates >", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesGreaterThanOrEqualTo(String value) {
            addCriterion("templates >=", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesLessThan(String value) {
            addCriterion("templates <", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesLessThanOrEqualTo(String value) {
            addCriterion("templates <=", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesLike(String value) {
            addCriterion("templates like", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesNotLike(String value) {
            addCriterion("templates not like", value, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesIn(List<String> values) {
            addCriterion("templates in", values, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesNotIn(List<String> values) {
            addCriterion("templates not in", values, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesBetween(String value1, String value2) {
            addCriterion("templates between", value1, value2, "templates");
            return (Criteria) this;
        }

        public Criteria andTemplatesNotBetween(String value1, String value2) {
            addCriterion("templates not between", value1, value2, "templates");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNull() {
            addCriterion("begin_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNotNull() {
            addCriterion("begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginDateEqualTo(String value) {
            addCriterion("begin_date =", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotEqualTo(String value) {
            addCriterion("begin_date <>", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThan(String value) {
            addCriterion("begin_date >", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThanOrEqualTo(String value) {
            addCriterion("begin_date >=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThan(String value) {
            addCriterion("begin_date <", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThanOrEqualTo(String value) {
            addCriterion("begin_date <=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLike(String value) {
            addCriterion("begin_date like", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotLike(String value) {
            addCriterion("begin_date not like", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIn(List<String> values) {
            addCriterion("begin_date in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotIn(List<String> values) {
            addCriterion("begin_date not in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateBetween(String value1, String value2) {
            addCriterion("begin_date between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotBetween(String value1, String value2) {
            addCriterion("begin_date not between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(String value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(String value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(String value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(String value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(String value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(String value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLike(String value) {
            addCriterion("end_date like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotLike(String value) {
            addCriterion("end_date not like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<String> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<String> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(String value1, String value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(String value1, String value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andBelongUserIsNull() {
            addCriterion("belong_user is null");
            return (Criteria) this;
        }

        public Criteria andBelongUserIsNotNull() {
            addCriterion("belong_user is not null");
            return (Criteria) this;
        }

        public Criteria andBelongUserEqualTo(String value) {
            addCriterion("belong_user =", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotEqualTo(String value) {
            addCriterion("belong_user <>", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserGreaterThan(String value) {
            addCriterion("belong_user >", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserGreaterThanOrEqualTo(String value) {
            addCriterion("belong_user >=", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLessThan(String value) {
            addCriterion("belong_user <", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLessThanOrEqualTo(String value) {
            addCriterion("belong_user <=", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLike(String value) {
            addCriterion("belong_user like", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotLike(String value) {
            addCriterion("belong_user not like", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserIn(List<String> values) {
            addCriterion("belong_user in", values, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotIn(List<String> values) {
            addCriterion("belong_user not in", values, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserBetween(String value1, String value2) {
            addCriterion("belong_user between", value1, value2, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotBetween(String value1, String value2) {
            addCriterion("belong_user not between", value1, value2, "belongUser");
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

        public Criteria andUnivalentIsNull() {
            addCriterion("univalent is null");
            return (Criteria) this;
        }

        public Criteria andUnivalentIsNotNull() {
            addCriterion("univalent is not null");
            return (Criteria) this;
        }

        public Criteria andUnivalentEqualTo(BigDecimal value) {
            addCriterion("univalent =", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotEqualTo(BigDecimal value) {
            addCriterion("univalent <>", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentGreaterThan(BigDecimal value) {
            addCriterion("univalent >", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("univalent >=", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentLessThan(BigDecimal value) {
            addCriterion("univalent <", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("univalent <=", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentIn(List<BigDecimal> values) {
            addCriterion("univalent in", values, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotIn(List<BigDecimal> values) {
            addCriterion("univalent not in", values, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("univalent between", value1, value2, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("univalent not between", value1, value2, "univalent");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNull() {
            addCriterion("apply_user is null");
            return (Criteria) this;
        }

        public Criteria andApplyUserIsNotNull() {
            addCriterion("apply_user is not null");
            return (Criteria) this;
        }

        public Criteria andApplyUserEqualTo(String value) {
            addCriterion("apply_user =", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotEqualTo(String value) {
            addCriterion("apply_user <>", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThan(String value) {
            addCriterion("apply_user >", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserGreaterThanOrEqualTo(String value) {
            addCriterion("apply_user >=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThan(String value) {
            addCriterion("apply_user <", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLessThanOrEqualTo(String value) {
            addCriterion("apply_user <=", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserLike(String value) {
            addCriterion("apply_user like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotLike(String value) {
            addCriterion("apply_user not like", value, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserIn(List<String> values) {
            addCriterion("apply_user in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotIn(List<String> values) {
            addCriterion("apply_user not in", values, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserBetween(String value1, String value2) {
            addCriterion("apply_user between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyUserNotBetween(String value1, String value2) {
            addCriterion("apply_user not between", value1, value2, "applyUser");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeIsNull() {
            addCriterion("apply_org_code is null");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeIsNotNull() {
            addCriterion("apply_org_code is not null");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeEqualTo(String value) {
            addCriterion("apply_org_code =", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeNotEqualTo(String value) {
            addCriterion("apply_org_code <>", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeGreaterThan(String value) {
            addCriterion("apply_org_code >", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("apply_org_code >=", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeLessThan(String value) {
            addCriterion("apply_org_code <", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("apply_org_code <=", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeLike(String value) {
            addCriterion("apply_org_code like", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeNotLike(String value) {
            addCriterion("apply_org_code not like", value, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeIn(List<String> values) {
            addCriterion("apply_org_code in", values, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeNotIn(List<String> values) {
            addCriterion("apply_org_code not in", values, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeBetween(String value1, String value2) {
            addCriterion("apply_org_code between", value1, value2, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyOrgCodeNotBetween(String value1, String value2) {
            addCriterion("apply_org_code not between", value1, value2, "applyOrgCode");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("apply_date is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("apply_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(String value) {
            addCriterion("apply_date =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(String value) {
            addCriterion("apply_date <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(String value) {
            addCriterion("apply_date >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(String value) {
            addCriterion("apply_date >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(String value) {
            addCriterion("apply_date <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(String value) {
            addCriterion("apply_date <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLike(String value) {
            addCriterion("apply_date like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotLike(String value) {
            addCriterion("apply_date not like", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<String> values) {
            addCriterion("apply_date in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<String> values) {
            addCriterion("apply_date not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(String value1, String value2) {
            addCriterion("apply_date between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(String value1, String value2) {
            addCriterion("apply_date not between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIsNull() {
            addCriterion("apply_type is null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIsNotNull() {
            addCriterion("apply_type is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeEqualTo(Integer value) {
            addCriterion("apply_type =", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotEqualTo(Integer value) {
            addCriterion("apply_type <>", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThan(Integer value) {
            addCriterion("apply_type >", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_type >=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThan(Integer value) {
            addCriterion("apply_type <", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("apply_type <=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIn(List<Integer> values) {
            addCriterion("apply_type in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotIn(List<Integer> values) {
            addCriterion("apply_type not in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeBetween(Integer value1, Integer value2) {
            addCriterion("apply_type between", value1, value2, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_type not between", value1, value2, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIsNull() {
            addCriterion("apply_status is null");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIsNotNull() {
            addCriterion("apply_status is not null");
            return (Criteria) this;
        }

        public Criteria andApplyStatusEqualTo(Integer value) {
            addCriterion("apply_status =", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotEqualTo(Integer value) {
            addCriterion("apply_status <>", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusGreaterThan(Integer value) {
            addCriterion("apply_status >", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_status >=", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusLessThan(Integer value) {
            addCriterion("apply_status <", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusLessThanOrEqualTo(Integer value) {
            addCriterion("apply_status <=", value, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusIn(List<Integer> values) {
            addCriterion("apply_status in", values, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotIn(List<Integer> values) {
            addCriterion("apply_status not in", values, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusBetween(Integer value1, Integer value2) {
            addCriterion("apply_status between", value1, value2, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApplyStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_status not between", value1, value2, "applyStatus");
            return (Criteria) this;
        }

        public Criteria andApproveUserIsNull() {
            addCriterion("approve_user is null");
            return (Criteria) this;
        }

        public Criteria andApproveUserIsNotNull() {
            addCriterion("approve_user is not null");
            return (Criteria) this;
        }

        public Criteria andApproveUserEqualTo(String value) {
            addCriterion("approve_user =", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotEqualTo(String value) {
            addCriterion("approve_user <>", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserGreaterThan(String value) {
            addCriterion("approve_user >", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserGreaterThanOrEqualTo(String value) {
            addCriterion("approve_user >=", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLessThan(String value) {
            addCriterion("approve_user <", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLessThanOrEqualTo(String value) {
            addCriterion("approve_user <=", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserLike(String value) {
            addCriterion("approve_user like", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotLike(String value) {
            addCriterion("approve_user not like", value, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserIn(List<String> values) {
            addCriterion("approve_user in", values, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotIn(List<String> values) {
            addCriterion("approve_user not in", values, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserBetween(String value1, String value2) {
            addCriterion("approve_user between", value1, value2, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveUserNotBetween(String value1, String value2) {
            addCriterion("approve_user not between", value1, value2, "approveUser");
            return (Criteria) this;
        }

        public Criteria andApproveDateIsNull() {
            addCriterion("approve_date is null");
            return (Criteria) this;
        }

        public Criteria andApproveDateIsNotNull() {
            addCriterion("approve_date is not null");
            return (Criteria) this;
        }

        public Criteria andApproveDateEqualTo(String value) {
            addCriterion("approve_date =", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateNotEqualTo(String value) {
            addCriterion("approve_date <>", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateGreaterThan(String value) {
            addCriterion("approve_date >", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateGreaterThanOrEqualTo(String value) {
            addCriterion("approve_date >=", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateLessThan(String value) {
            addCriterion("approve_date <", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateLessThanOrEqualTo(String value) {
            addCriterion("approve_date <=", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateLike(String value) {
            addCriterion("approve_date like", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateNotLike(String value) {
            addCriterion("approve_date not like", value, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateIn(List<String> values) {
            addCriterion("approve_date in", values, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateNotIn(List<String> values) {
            addCriterion("approve_date not in", values, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateBetween(String value1, String value2) {
            addCriterion("approve_date between", value1, value2, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveDateNotBetween(String value1, String value2) {
            addCriterion("approve_date not between", value1, value2, "approveDate");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNull() {
            addCriterion("approve_time is null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIsNotNull() {
            addCriterion("approve_time is not null");
            return (Criteria) this;
        }

        public Criteria andApproveTimeEqualTo(Date value) {
            addCriterion("approve_time =", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotEqualTo(Date value) {
            addCriterion("approve_time <>", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThan(Date value) {
            addCriterion("approve_time >", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approve_time >=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThan(Date value) {
            addCriterion("approve_time <", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeLessThanOrEqualTo(Date value) {
            addCriterion("approve_time <=", value, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeIn(List<Date> values) {
            addCriterion("approve_time in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotIn(List<Date> values) {
            addCriterion("approve_time not in", values, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeBetween(Date value1, Date value2) {
            addCriterion("approve_time between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveTimeNotBetween(Date value1, Date value2) {
            addCriterion("approve_time not between", value1, value2, "approveTime");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkIsNull() {
            addCriterion("approve_remark is null");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkIsNotNull() {
            addCriterion("approve_remark is not null");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkEqualTo(String value) {
            addCriterion("approve_remark =", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotEqualTo(String value) {
            addCriterion("approve_remark <>", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkGreaterThan(String value) {
            addCriterion("approve_remark >", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("approve_remark >=", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLessThan(String value) {
            addCriterion("approve_remark <", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLessThanOrEqualTo(String value) {
            addCriterion("approve_remark <=", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkLike(String value) {
            addCriterion("approve_remark like", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotLike(String value) {
            addCriterion("approve_remark not like", value, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkIn(List<String> values) {
            addCriterion("approve_remark in", values, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotIn(List<String> values) {
            addCriterion("approve_remark not in", values, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkBetween(String value1, String value2) {
            addCriterion("approve_remark between", value1, value2, "approveRemark");
            return (Criteria) this;
        }

        public Criteria andApproveRemarkNotBetween(String value1, String value2) {
            addCriterion("approve_remark not between", value1, value2, "approveRemark");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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