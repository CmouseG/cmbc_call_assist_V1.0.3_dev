package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class ReportCallDayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ReportCallDayExample() {
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

        public Criteria andCallDateIsNull() {
            addCriterion("call_date is null");
            return (Criteria) this;
        }

        public Criteria andCallDateIsNotNull() {
            addCriterion("call_date is not null");
            return (Criteria) this;
        }

        public Criteria andCallDateEqualTo(String value) {
            addCriterion("call_date =", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotEqualTo(String value) {
            addCriterion("call_date <>", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateGreaterThan(String value) {
            addCriterion("call_date >", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateGreaterThanOrEqualTo(String value) {
            addCriterion("call_date >=", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLessThan(String value) {
            addCriterion("call_date <", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLessThanOrEqualTo(String value) {
            addCriterion("call_date <=", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLike(String value) {
            addCriterion("call_date like", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotLike(String value) {
            addCriterion("call_date not like", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateIn(List<String> values) {
            addCriterion("call_date in", values, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotIn(List<String> values) {
            addCriterion("call_date not in", values, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateBetween(String value1, String value2) {
            addCriterion("call_date between", value1, value2, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotBetween(String value1, String value2) {
            addCriterion("call_date not between", value1, value2, "callDate");
            return (Criteria) this;
        }

        public Criteria andDurationTypeIsNull() {
            addCriterion("duration_type is null");
            return (Criteria) this;
        }

        public Criteria andDurationTypeIsNotNull() {
            addCriterion("duration_type is not null");
            return (Criteria) this;
        }

        public Criteria andDurationTypeEqualTo(Integer value) {
            addCriterion("duration_type =", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeNotEqualTo(Integer value) {
            addCriterion("duration_type <>", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeGreaterThan(Integer value) {
            addCriterion("duration_type >", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("duration_type >=", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeLessThan(Integer value) {
            addCriterion("duration_type <", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("duration_type <=", value, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeIn(List<Integer> values) {
            addCriterion("duration_type in", values, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeNotIn(List<Integer> values) {
            addCriterion("duration_type not in", values, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeBetween(Integer value1, Integer value2) {
            addCriterion("duration_type between", value1, value2, "durationType");
            return (Criteria) this;
        }

        public Criteria andDurationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("duration_type not between", value1, value2, "durationType");
            return (Criteria) this;
        }

        public Criteria andIntentIsNull() {
            addCriterion("intent is null");
            return (Criteria) this;
        }

        public Criteria andIntentIsNotNull() {
            addCriterion("intent is not null");
            return (Criteria) this;
        }

        public Criteria andIntentEqualTo(String value) {
            addCriterion("intent =", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentNotEqualTo(String value) {
            addCriterion("intent <>", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentGreaterThan(String value) {
            addCriterion("intent >", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentGreaterThanOrEqualTo(String value) {
            addCriterion("intent >=", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentLessThan(String value) {
            addCriterion("intent <", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentLessThanOrEqualTo(String value) {
            addCriterion("intent <=", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentLike(String value) {
            addCriterion("intent like", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentNotLike(String value) {
            addCriterion("intent not like", value, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentIn(List<String> values) {
            addCriterion("intent in", values, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentNotIn(List<String> values) {
            addCriterion("intent not in", values, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentBetween(String value1, String value2) {
            addCriterion("intent between", value1, value2, "intent");
            return (Criteria) this;
        }

        public Criteria andIntentNotBetween(String value1, String value2) {
            addCriterion("intent not between", value1, value2, "intent");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andCallCountIsNull() {
            addCriterion("call_count is null");
            return (Criteria) this;
        }

        public Criteria andCallCountIsNotNull() {
            addCriterion("call_count is not null");
            return (Criteria) this;
        }

        public Criteria andCallCountEqualTo(Integer value) {
            addCriterion("call_count =", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountNotEqualTo(Integer value) {
            addCriterion("call_count <>", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountGreaterThan(Integer value) {
            addCriterion("call_count >", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_count >=", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountLessThan(Integer value) {
            addCriterion("call_count <", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountLessThanOrEqualTo(Integer value) {
            addCriterion("call_count <=", value, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountIn(List<Integer> values) {
            addCriterion("call_count in", values, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountNotIn(List<Integer> values) {
            addCriterion("call_count not in", values, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountBetween(Integer value1, Integer value2) {
            addCriterion("call_count between", value1, value2, "callCount");
            return (Criteria) this;
        }

        public Criteria andCallCountNotBetween(Integer value1, Integer value2) {
            addCriterion("call_count not between", value1, value2, "callCount");
            return (Criteria) this;
        }

        public Criteria andDurationAllIsNull() {
            addCriterion("duration_all is null");
            return (Criteria) this;
        }

        public Criteria andDurationAllIsNotNull() {
            addCriterion("duration_all is not null");
            return (Criteria) this;
        }

        public Criteria andDurationAllEqualTo(Long value) {
            addCriterion("duration_all =", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllNotEqualTo(Long value) {
            addCriterion("duration_all <>", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllGreaterThan(Long value) {
            addCriterion("duration_all >", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllGreaterThanOrEqualTo(Long value) {
            addCriterion("duration_all >=", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllLessThan(Long value) {
            addCriterion("duration_all <", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllLessThanOrEqualTo(Long value) {
            addCriterion("duration_all <=", value, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllIn(List<Long> values) {
            addCriterion("duration_all in", values, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllNotIn(List<Long> values) {
            addCriterion("duration_all not in", values, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllBetween(Long value1, Long value2) {
            addCriterion("duration_all between", value1, value2, "durationAll");
            return (Criteria) this;
        }

        public Criteria andDurationAllNotBetween(Long value1, Long value2) {
            addCriterion("duration_all not between", value1, value2, "durationAll");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
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