package com.guiji.clm.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SipLineExclusiveExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SipLineExclusiveExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(Integer value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(Integer value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(Integer value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(Integer value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<Integer> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<Integer> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
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

        public Criteria andLineIdIsNull() {
            addCriterion("line_id is null");
            return (Criteria) this;
        }

        public Criteria andLineIdIsNotNull() {
            addCriterion("line_id is not null");
            return (Criteria) this;
        }

        public Criteria andLineIdEqualTo(Integer value) {
            addCriterion("line_id =", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotEqualTo(Integer value) {
            addCriterion("line_id <>", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdGreaterThan(Integer value) {
            addCriterion("line_id >", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_id >=", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdLessThan(Integer value) {
            addCriterion("line_id <", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdLessThanOrEqualTo(Integer value) {
            addCriterion("line_id <=", value, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdIn(List<Integer> values) {
            addCriterion("line_id in", values, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotIn(List<Integer> values) {
            addCriterion("line_id not in", values, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdBetween(Integer value1, Integer value2) {
            addCriterion("line_id between", value1, value2, "lineId");
            return (Criteria) this;
        }

        public Criteria andLineIdNotBetween(Integer value1, Integer value2) {
            addCriterion("line_id not between", value1, value2, "lineId");
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

        public Criteria andLineTypeIsNull() {
            addCriterion("line_type is null");
            return (Criteria) this;
        }

        public Criteria andLineTypeIsNotNull() {
            addCriterion("line_type is not null");
            return (Criteria) this;
        }

        public Criteria andLineTypeEqualTo(Integer value) {
            addCriterion("line_type =", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotEqualTo(Integer value) {
            addCriterion("line_type <>", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThan(Integer value) {
            addCriterion("line_type >", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_type >=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThan(Integer value) {
            addCriterion("line_type <", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeLessThanOrEqualTo(Integer value) {
            addCriterion("line_type <=", value, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeIn(List<Integer> values) {
            addCriterion("line_type in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotIn(List<Integer> values) {
            addCriterion("line_type not in", values, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeBetween(Integer value1, Integer value2) {
            addCriterion("line_type between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("line_type not between", value1, value2, "lineType");
            return (Criteria) this;
        }

        public Criteria andLineStatusIsNull() {
            addCriterion("line_status is null");
            return (Criteria) this;
        }

        public Criteria andLineStatusIsNotNull() {
            addCriterion("line_status is not null");
            return (Criteria) this;
        }

        public Criteria andLineStatusEqualTo(Integer value) {
            addCriterion("line_status =", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusNotEqualTo(Integer value) {
            addCriterion("line_status <>", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusGreaterThan(Integer value) {
            addCriterion("line_status >", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_status >=", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusLessThan(Integer value) {
            addCriterion("line_status <", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusLessThanOrEqualTo(Integer value) {
            addCriterion("line_status <=", value, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusIn(List<Integer> values) {
            addCriterion("line_status in", values, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusNotIn(List<Integer> values) {
            addCriterion("line_status not in", values, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusBetween(Integer value1, Integer value2) {
            addCriterion("line_status between", value1, value2, "lineStatus");
            return (Criteria) this;
        }

        public Criteria andLineStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("line_status not between", value1, value2, "lineStatus");
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

        public Criteria andFeeOrNotIsNull() {
            addCriterion("fee_or_not is null");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotIsNotNull() {
            addCriterion("fee_or_not is not null");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotEqualTo(Boolean value) {
            addCriterion("fee_or_not =", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotNotEqualTo(Boolean value) {
            addCriterion("fee_or_not <>", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotGreaterThan(Boolean value) {
            addCriterion("fee_or_not >", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotGreaterThanOrEqualTo(Boolean value) {
            addCriterion("fee_or_not >=", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotLessThan(Boolean value) {
            addCriterion("fee_or_not <", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotLessThanOrEqualTo(Boolean value) {
            addCriterion("fee_or_not <=", value, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotIn(List<Boolean> values) {
            addCriterion("fee_or_not in", values, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotNotIn(List<Boolean> values) {
            addCriterion("fee_or_not not in", values, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotBetween(Boolean value1, Boolean value2) {
            addCriterion("fee_or_not between", value1, value2, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andFeeOrNotNotBetween(Boolean value1, Boolean value2) {
            addCriterion("fee_or_not not between", value1, value2, "feeOrNot");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeIsNull() {
            addCriterion("line_fee_type is null");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeIsNotNull() {
            addCriterion("line_fee_type is not null");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeEqualTo(Integer value) {
            addCriterion("line_fee_type =", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeNotEqualTo(Integer value) {
            addCriterion("line_fee_type <>", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeGreaterThan(Integer value) {
            addCriterion("line_fee_type >", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("line_fee_type >=", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeLessThan(Integer value) {
            addCriterion("line_fee_type <", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("line_fee_type <=", value, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeIn(List<Integer> values) {
            addCriterion("line_fee_type in", values, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeNotIn(List<Integer> values) {
            addCriterion("line_fee_type not in", values, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeBetween(Integer value1, Integer value2) {
            addCriterion("line_fee_type between", value1, value2, "lineFeeType");
            return (Criteria) this;
        }

        public Criteria andLineFeeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("line_fee_type not between", value1, value2, "lineFeeType");
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

        public Criteria andIndustrysIsNull() {
            addCriterion("industrys is null");
            return (Criteria) this;
        }

        public Criteria andIndustrysIsNotNull() {
            addCriterion("industrys is not null");
            return (Criteria) this;
        }

        public Criteria andIndustrysEqualTo(String value) {
            addCriterion("industrys =", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysNotEqualTo(String value) {
            addCriterion("industrys <>", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysGreaterThan(String value) {
            addCriterion("industrys >", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysGreaterThanOrEqualTo(String value) {
            addCriterion("industrys >=", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysLessThan(String value) {
            addCriterion("industrys <", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysLessThanOrEqualTo(String value) {
            addCriterion("industrys <=", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysLike(String value) {
            addCriterion("industrys like", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysNotLike(String value) {
            addCriterion("industrys not like", value, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysIn(List<String> values) {
            addCriterion("industrys in", values, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysNotIn(List<String> values) {
            addCriterion("industrys not in", values, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysBetween(String value1, String value2) {
            addCriterion("industrys between", value1, value2, "industrys");
            return (Criteria) this;
        }

        public Criteria andIndustrysNotBetween(String value1, String value2) {
            addCriterion("industrys not between", value1, value2, "industrys");
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

        public Criteria andAreasIsNull() {
            addCriterion("areas is null");
            return (Criteria) this;
        }

        public Criteria andAreasIsNotNull() {
            addCriterion("areas is not null");
            return (Criteria) this;
        }

        public Criteria andAreasEqualTo(String value) {
            addCriterion("areas =", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasNotEqualTo(String value) {
            addCriterion("areas <>", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasGreaterThan(String value) {
            addCriterion("areas >", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasGreaterThanOrEqualTo(String value) {
            addCriterion("areas >=", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasLessThan(String value) {
            addCriterion("areas <", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasLessThanOrEqualTo(String value) {
            addCriterion("areas <=", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasLike(String value) {
            addCriterion("areas like", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasNotLike(String value) {
            addCriterion("areas not like", value, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasIn(List<String> values) {
            addCriterion("areas in", values, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasNotIn(List<String> values) {
            addCriterion("areas not in", values, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasBetween(String value1, String value2) {
            addCriterion("areas between", value1, value2, "areas");
            return (Criteria) this;
        }

        public Criteria andAreasNotBetween(String value1, String value2) {
            addCriterion("areas not between", value1, value2, "areas");
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

        public Criteria andBelongOrgCodeIsNull() {
            addCriterion("belong_org_code is null");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeIsNotNull() {
            addCriterion("belong_org_code is not null");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeEqualTo(String value) {
            addCriterion("belong_org_code =", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeNotEqualTo(String value) {
            addCriterion("belong_org_code <>", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeGreaterThan(String value) {
            addCriterion("belong_org_code >", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("belong_org_code >=", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeLessThan(String value) {
            addCriterion("belong_org_code <", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("belong_org_code <=", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeLike(String value) {
            addCriterion("belong_org_code like", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeNotLike(String value) {
            addCriterion("belong_org_code not like", value, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeIn(List<String> values) {
            addCriterion("belong_org_code in", values, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeNotIn(List<String> values) {
            addCriterion("belong_org_code not in", values, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeBetween(String value1, String value2) {
            addCriterion("belong_org_code between", value1, value2, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andBelongOrgCodeNotBetween(String value1, String value2) {
            addCriterion("belong_org_code not between", value1, value2, "belongOrgCode");
            return (Criteria) this;
        }

        public Criteria andExceptAreasIsNull() {
            addCriterion("except_areas is null");
            return (Criteria) this;
        }

        public Criteria andExceptAreasIsNotNull() {
            addCriterion("except_areas is not null");
            return (Criteria) this;
        }

        public Criteria andExceptAreasEqualTo(String value) {
            addCriterion("except_areas =", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasNotEqualTo(String value) {
            addCriterion("except_areas <>", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasGreaterThan(String value) {
            addCriterion("except_areas >", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasGreaterThanOrEqualTo(String value) {
            addCriterion("except_areas >=", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasLessThan(String value) {
            addCriterion("except_areas <", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasLessThanOrEqualTo(String value) {
            addCriterion("except_areas <=", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasLike(String value) {
            addCriterion("except_areas like", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasNotLike(String value) {
            addCriterion("except_areas not like", value, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasIn(List<String> values) {
            addCriterion("except_areas in", values, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasNotIn(List<String> values) {
            addCriterion("except_areas not in", values, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasBetween(String value1, String value2) {
            addCriterion("except_areas between", value1, value2, "exceptAreas");
            return (Criteria) this;
        }

        public Criteria andExceptAreasNotBetween(String value1, String value2) {
            addCriterion("except_areas not between", value1, value2, "exceptAreas");
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