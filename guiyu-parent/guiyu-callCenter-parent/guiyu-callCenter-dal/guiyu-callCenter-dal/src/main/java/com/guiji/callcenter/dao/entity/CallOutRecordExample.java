package com.guiji.callcenter.dao.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CallOutRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CallOutRecordExample() {
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

        public Criteria andCallIdIsNull() {
            addCriterion("call_id is null");
            return (Criteria) this;
        }

        public Criteria andCallIdIsNotNull() {
            addCriterion("call_id is not null");
            return (Criteria) this;
        }

        public Criteria andCallIdEqualTo(BigInteger value) {
            addCriterion("call_id =", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotEqualTo(BigInteger value) {
            addCriterion("call_id <>", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdGreaterThan(BigInteger value) {
            addCriterion("call_id >", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdGreaterThanOrEqualTo(BigInteger value) {
            addCriterion("call_id >=", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdLessThan(BigInteger value) {
            addCriterion("call_id <", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdLessThanOrEqualTo(BigInteger value) {
            addCriterion("call_id <=", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdIn(List<BigInteger> values) {
            addCriterion("call_id in", values, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotIn(List<BigInteger> values) {
            addCriterion("call_id not in", values, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdBetween(BigInteger value1, BigInteger value2) {
            addCriterion("call_id between", value1, value2, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotBetween(BigInteger value1, BigInteger value2) {
            addCriterion("call_id not between", value1, value2, "callId");
            return (Criteria) this;
        }

        public Criteria andRecordFileIsNull() {
            addCriterion("record_file is null");
            return (Criteria) this;
        }

        public Criteria andRecordFileIsNotNull() {
            addCriterion("record_file is not null");
            return (Criteria) this;
        }

        public Criteria andRecordFileEqualTo(String value) {
            addCriterion("record_file =", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotEqualTo(String value) {
            addCriterion("record_file <>", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileGreaterThan(String value) {
            addCriterion("record_file >", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileGreaterThanOrEqualTo(String value) {
            addCriterion("record_file >=", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLessThan(String value) {
            addCriterion("record_file <", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLessThanOrEqualTo(String value) {
            addCriterion("record_file <=", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileLike(String value) {
            addCriterion("record_file like", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotLike(String value) {
            addCriterion("record_file not like", value, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileIn(List<String> values) {
            addCriterion("record_file in", values, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotIn(List<String> values) {
            addCriterion("record_file not in", values, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileBetween(String value1, String value2) {
            addCriterion("record_file between", value1, value2, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordFileNotBetween(String value1, String value2) {
            addCriterion("record_file not between", value1, value2, "recordFile");
            return (Criteria) this;
        }

        public Criteria andRecordUrlIsNull() {
            addCriterion("record_url is null");
            return (Criteria) this;
        }

        public Criteria andRecordUrlIsNotNull() {
            addCriterion("record_url is not null");
            return (Criteria) this;
        }

        public Criteria andRecordUrlEqualTo(String value) {
            addCriterion("record_url =", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlNotEqualTo(String value) {
            addCriterion("record_url <>", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlGreaterThan(String value) {
            addCriterion("record_url >", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlGreaterThanOrEqualTo(String value) {
            addCriterion("record_url >=", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlLessThan(String value) {
            addCriterion("record_url <", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlLessThanOrEqualTo(String value) {
            addCriterion("record_url <=", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlLike(String value) {
            addCriterion("record_url like", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlNotLike(String value) {
            addCriterion("record_url not like", value, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlIn(List<String> values) {
            addCriterion("record_url in", values, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlNotIn(List<String> values) {
            addCriterion("record_url not in", values, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlBetween(String value1, String value2) {
            addCriterion("record_url between", value1, value2, "recordUrl");
            return (Criteria) this;
        }

        public Criteria andRecordUrlNotBetween(String value1, String value2) {
            addCriterion("record_url not between", value1, value2, "recordUrl");
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