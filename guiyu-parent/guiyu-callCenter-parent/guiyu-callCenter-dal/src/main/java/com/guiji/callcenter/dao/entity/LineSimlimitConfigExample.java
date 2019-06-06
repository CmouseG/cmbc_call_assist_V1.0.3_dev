package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LineSimlimitConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public LineSimlimitConfigExample() {
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

        public Criteria andCallCountTopIsNull() {
            addCriterion("call_count_top is null");
            return (Criteria) this;
        }

        public Criteria andCallCountTopIsNotNull() {
            addCriterion("call_count_top is not null");
            return (Criteria) this;
        }

        public Criteria andCallCountTopEqualTo(Integer value) {
            addCriterion("call_count_top =", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopNotEqualTo(Integer value) {
            addCriterion("call_count_top <>", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopGreaterThan(Integer value) {
            addCriterion("call_count_top >", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_count_top >=", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopLessThan(Integer value) {
            addCriterion("call_count_top <", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopLessThanOrEqualTo(Integer value) {
            addCriterion("call_count_top <=", value, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopIn(List<Integer> values) {
            addCriterion("call_count_top in", values, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopNotIn(List<Integer> values) {
            addCriterion("call_count_top not in", values, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopBetween(Integer value1, Integer value2) {
            addCriterion("call_count_top between", value1, value2, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountTopNotBetween(Integer value1, Integer value2) {
            addCriterion("call_count_top not between", value1, value2, "callCountTop");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodIsNull() {
            addCriterion("call_count_period is null");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodIsNotNull() {
            addCriterion("call_count_period is not null");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodEqualTo(Integer value) {
            addCriterion("call_count_period =", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodNotEqualTo(Integer value) {
            addCriterion("call_count_period <>", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodGreaterThan(Integer value) {
            addCriterion("call_count_period >", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_count_period >=", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodLessThan(Integer value) {
            addCriterion("call_count_period <", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("call_count_period <=", value, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodIn(List<Integer> values) {
            addCriterion("call_count_period in", values, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodNotIn(List<Integer> values) {
            addCriterion("call_count_period not in", values, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodBetween(Integer value1, Integer value2) {
            addCriterion("call_count_period between", value1, value2, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andCallCountPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("call_count_period not between", value1, value2, "callCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopIsNull() {
            addCriterion("connect_count_top is null");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopIsNotNull() {
            addCriterion("connect_count_top is not null");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopEqualTo(Integer value) {
            addCriterion("connect_count_top =", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopNotEqualTo(Integer value) {
            addCriterion("connect_count_top <>", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopGreaterThan(Integer value) {
            addCriterion("connect_count_top >", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopGreaterThanOrEqualTo(Integer value) {
            addCriterion("connect_count_top >=", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopLessThan(Integer value) {
            addCriterion("connect_count_top <", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopLessThanOrEqualTo(Integer value) {
            addCriterion("connect_count_top <=", value, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopIn(List<Integer> values) {
            addCriterion("connect_count_top in", values, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopNotIn(List<Integer> values) {
            addCriterion("connect_count_top not in", values, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopBetween(Integer value1, Integer value2) {
            addCriterion("connect_count_top between", value1, value2, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountTopNotBetween(Integer value1, Integer value2) {
            addCriterion("connect_count_top not between", value1, value2, "connectCountTop");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodIsNull() {
            addCriterion("connect_count_period is null");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodIsNotNull() {
            addCriterion("connect_count_period is not null");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodEqualTo(Integer value) {
            addCriterion("connect_count_period =", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodNotEqualTo(Integer value) {
            addCriterion("connect_count_period <>", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodGreaterThan(Integer value) {
            addCriterion("connect_count_period >", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("connect_count_period >=", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodLessThan(Integer value) {
            addCriterion("connect_count_period <", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("connect_count_period <=", value, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodIn(List<Integer> values) {
            addCriterion("connect_count_period in", values, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodNotIn(List<Integer> values) {
            addCriterion("connect_count_period not in", values, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodBetween(Integer value1, Integer value2) {
            addCriterion("connect_count_period between", value1, value2, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectCountPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("connect_count_period not between", value1, value2, "connectCountPeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopIsNull() {
            addCriterion("connect_time_top is null");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopIsNotNull() {
            addCriterion("connect_time_top is not null");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopEqualTo(Integer value) {
            addCriterion("connect_time_top =", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopNotEqualTo(Integer value) {
            addCriterion("connect_time_top <>", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopGreaterThan(Integer value) {
            addCriterion("connect_time_top >", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopGreaterThanOrEqualTo(Integer value) {
            addCriterion("connect_time_top >=", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopLessThan(Integer value) {
            addCriterion("connect_time_top <", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopLessThanOrEqualTo(Integer value) {
            addCriterion("connect_time_top <=", value, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopIn(List<Integer> values) {
            addCriterion("connect_time_top in", values, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopNotIn(List<Integer> values) {
            addCriterion("connect_time_top not in", values, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopBetween(Integer value1, Integer value2) {
            addCriterion("connect_time_top between", value1, value2, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimeTopNotBetween(Integer value1, Integer value2) {
            addCriterion("connect_time_top not between", value1, value2, "connectTimeTop");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodIsNull() {
            addCriterion("connect_time_period is null");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodIsNotNull() {
            addCriterion("connect_time_period is not null");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodEqualTo(Integer value) {
            addCriterion("connect_time_period =", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodNotEqualTo(Integer value) {
            addCriterion("connect_time_period <>", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodGreaterThan(Integer value) {
            addCriterion("connect_time_period >", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("connect_time_period >=", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodLessThan(Integer value) {
            addCriterion("connect_time_period <", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodLessThanOrEqualTo(Integer value) {
            addCriterion("connect_time_period <=", value, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodIn(List<Integer> values) {
            addCriterion("connect_time_period in", values, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodNotIn(List<Integer> values) {
            addCriterion("connect_time_period not in", values, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodBetween(Integer value1, Integer value2) {
            addCriterion("connect_time_period between", value1, value2, "connectTimePeriod");
            return (Criteria) this;
        }

        public Criteria andConnectTimePeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("connect_time_period not between", value1, value2, "connectTimePeriod");
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