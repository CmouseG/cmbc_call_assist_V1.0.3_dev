package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class LineCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public LineCountExample() {
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

        public Criteria andCalloutserverIdIsNull() {
            addCriterion("calloutserver_id is null");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdIsNotNull() {
            addCriterion("calloutserver_id is not null");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdEqualTo(String value) {
            addCriterion("calloutserver_id =", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdNotEqualTo(String value) {
            addCriterion("calloutserver_id <>", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdGreaterThan(String value) {
            addCriterion("calloutserver_id >", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdGreaterThanOrEqualTo(String value) {
            addCriterion("calloutserver_id >=", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdLessThan(String value) {
            addCriterion("calloutserver_id <", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdLessThanOrEqualTo(String value) {
            addCriterion("calloutserver_id <=", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdLike(String value) {
            addCriterion("calloutserver_id like", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdNotLike(String value) {
            addCriterion("calloutserver_id not like", value, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdIn(List<String> values) {
            addCriterion("calloutserver_id in", values, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdNotIn(List<String> values) {
            addCriterion("calloutserver_id not in", values, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdBetween(String value1, String value2) {
            addCriterion("calloutserver_id between", value1, value2, "calloutserverId");
            return (Criteria) this;
        }

        public Criteria andCalloutserverIdNotBetween(String value1, String value2) {
            addCriterion("calloutserver_id not between", value1, value2, "calloutserverId");
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

        public Criteria andUsedConcurrentCallsIsNull() {
            addCriterion("used_concurrent_calls is null");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsIsNotNull() {
            addCriterion("used_concurrent_calls is not null");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsEqualTo(Integer value) {
            addCriterion("used_concurrent_calls =", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsNotEqualTo(Integer value) {
            addCriterion("used_concurrent_calls <>", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsGreaterThan(Integer value) {
            addCriterion("used_concurrent_calls >", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsGreaterThanOrEqualTo(Integer value) {
            addCriterion("used_concurrent_calls >=", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsLessThan(Integer value) {
            addCriterion("used_concurrent_calls <", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsLessThanOrEqualTo(Integer value) {
            addCriterion("used_concurrent_calls <=", value, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsIn(List<Integer> values) {
            addCriterion("used_concurrent_calls in", values, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsNotIn(List<Integer> values) {
            addCriterion("used_concurrent_calls not in", values, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsBetween(Integer value1, Integer value2) {
            addCriterion("used_concurrent_calls between", value1, value2, "usedConcurrentCalls");
            return (Criteria) this;
        }

        public Criteria andUsedConcurrentCallsNotBetween(Integer value1, Integer value2) {
            addCriterion("used_concurrent_calls not between", value1, value2, "usedConcurrentCalls");
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