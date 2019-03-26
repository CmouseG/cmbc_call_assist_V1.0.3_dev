package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportCallHourExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ReportCallHourExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCallTimeIsNull() {
            addCriterion("call_time is null");
            return (Criteria) this;
        }

        public Criteria andCallTimeIsNotNull() {
            addCriterion("call_time is not null");
            return (Criteria) this;
        }

        public Criteria andCallTimeEqualTo(Date value) {
            addCriterion("call_time =", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeNotEqualTo(Date value) {
            addCriterion("call_time <>", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeGreaterThan(Date value) {
            addCriterion("call_time >", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("call_time >=", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeLessThan(Date value) {
            addCriterion("call_time <", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeLessThanOrEqualTo(Date value) {
            addCriterion("call_time <=", value, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeIn(List<Date> values) {
            addCriterion("call_time in", values, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeNotIn(List<Date> values) {
            addCriterion("call_time not in", values, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeBetween(Date value1, Date value2) {
            addCriterion("call_time between", value1, value2, "callTime");
            return (Criteria) this;
        }

        public Criteria andCallTimeNotBetween(Date value1, Date value2) {
            addCriterion("call_time not between", value1, value2, "callTime");
            return (Criteria) this;
        }

        public Criteria andOutCountIsNull() {
            addCriterion("out_count is null");
            return (Criteria) this;
        }

        public Criteria andOutCountIsNotNull() {
            addCriterion("out_count is not null");
            return (Criteria) this;
        }

        public Criteria andOutCountEqualTo(Integer value) {
            addCriterion("out_count =", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotEqualTo(Integer value) {
            addCriterion("out_count <>", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountGreaterThan(Integer value) {
            addCriterion("out_count >", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_count >=", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountLessThan(Integer value) {
            addCriterion("out_count <", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountLessThanOrEqualTo(Integer value) {
            addCriterion("out_count <=", value, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountIn(List<Integer> values) {
            addCriterion("out_count in", values, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotIn(List<Integer> values) {
            addCriterion("out_count not in", values, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountBetween(Integer value1, Integer value2) {
            addCriterion("out_count between", value1, value2, "outCount");
            return (Criteria) this;
        }

        public Criteria andOutCountNotBetween(Integer value1, Integer value2) {
            addCriterion("out_count not between", value1, value2, "outCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountIsNull() {
            addCriterion("connect_count is null");
            return (Criteria) this;
        }

        public Criteria andConnectCountIsNotNull() {
            addCriterion("connect_count is not null");
            return (Criteria) this;
        }

        public Criteria andConnectCountEqualTo(Integer value) {
            addCriterion("connect_count =", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountNotEqualTo(Integer value) {
            addCriterion("connect_count <>", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountGreaterThan(Integer value) {
            addCriterion("connect_count >", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("connect_count >=", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountLessThan(Integer value) {
            addCriterion("connect_count <", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountLessThanOrEqualTo(Integer value) {
            addCriterion("connect_count <=", value, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountIn(List<Integer> values) {
            addCriterion("connect_count in", values, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountNotIn(List<Integer> values) {
            addCriterion("connect_count not in", values, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountBetween(Integer value1, Integer value2) {
            addCriterion("connect_count between", value1, value2, "connectCount");
            return (Criteria) this;
        }

        public Criteria andConnectCountNotBetween(Integer value1, Integer value2) {
            addCriterion("connect_count not between", value1, value2, "connectCount");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Long value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Long value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Long value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Long value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Long value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Long value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Long> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Long> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Long value1, Long value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Long value1, Long value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andTempidIsNull() {
            addCriterion("tempid is null");
            return (Criteria) this;
        }

        public Criteria andTempidIsNotNull() {
            addCriterion("tempid is not null");
            return (Criteria) this;
        }

        public Criteria andTempidEqualTo(String value) {
            addCriterion("tempid =", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotEqualTo(String value) {
            addCriterion("tempid <>", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidGreaterThan(String value) {
            addCriterion("tempid >", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidGreaterThanOrEqualTo(String value) {
            addCriterion("tempid >=", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLessThan(String value) {
            addCriterion("tempid <", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLessThanOrEqualTo(String value) {
            addCriterion("tempid <=", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLike(String value) {
            addCriterion("tempid like", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotLike(String value) {
            addCriterion("tempid not like", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidIn(List<String> values) {
            addCriterion("tempid in", values, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotIn(List<String> values) {
            addCriterion("tempid not in", values, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidBetween(String value1, String value2) {
            addCriterion("tempid between", value1, value2, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotBetween(String value1, String value2) {
            addCriterion("tempid not between", value1, value2, "tempid");
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
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