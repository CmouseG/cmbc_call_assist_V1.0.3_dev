package com.guiji.process.server.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysProcessLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SysProcessLogExample() {
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

        public Criteria andProcessIdIsNull() {
            addCriterion("process_id is null");
            return (Criteria) this;
        }

        public Criteria andProcessIdIsNotNull() {
            addCriterion("process_id is not null");
            return (Criteria) this;
        }

        public Criteria andProcessIdEqualTo(Long value) {
            addCriterion("process_id =", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotEqualTo(Long value) {
            addCriterion("process_id <>", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThan(Long value) {
            addCriterion("process_id >", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThanOrEqualTo(Long value) {
            addCriterion("process_id >=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThan(Long value) {
            addCriterion("process_id <", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThanOrEqualTo(Long value) {
            addCriterion("process_id <=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdIn(List<Long> values) {
            addCriterion("process_id in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotIn(List<Long> values) {
            addCriterion("process_id not in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdBetween(Long value1, Long value2) {
            addCriterion("process_id between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotBetween(Long value1, Long value2) {
            addCriterion("process_id not between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andCmdTypeIsNull() {
            addCriterion("cmd_type is null");
            return (Criteria) this;
        }

        public Criteria andCmdTypeIsNotNull() {
            addCriterion("cmd_type is not null");
            return (Criteria) this;
        }

        public Criteria andCmdTypeEqualTo(String value) {
            addCriterion("cmd_type =", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeNotEqualTo(String value) {
            addCriterion("cmd_type <>", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeGreaterThan(String value) {
            addCriterion("cmd_type >", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cmd_type >=", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeLessThan(String value) {
            addCriterion("cmd_type <", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeLessThanOrEqualTo(String value) {
            addCriterion("cmd_type <=", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeLike(String value) {
            addCriterion("cmd_type like", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeNotLike(String value) {
            addCriterion("cmd_type not like", value, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeIn(List<String> values) {
            addCriterion("cmd_type in", values, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeNotIn(List<String> values) {
            addCriterion("cmd_type not in", values, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeBetween(String value1, String value2) {
            addCriterion("cmd_type between", value1, value2, "cmdType");
            return (Criteria) this;
        }

        public Criteria andCmdTypeNotBetween(String value1, String value2) {
            addCriterion("cmd_type not between", value1, value2, "cmdType");
            return (Criteria) this;
        }

        public Criteria andProcessKeyIsNull() {
            addCriterion("process_key is null");
            return (Criteria) this;
        }

        public Criteria andProcessKeyIsNotNull() {
            addCriterion("process_key is not null");
            return (Criteria) this;
        }

        public Criteria andProcessKeyEqualTo(String value) {
            addCriterion("process_key =", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyNotEqualTo(String value) {
            addCriterion("process_key <>", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyGreaterThan(String value) {
            addCriterion("process_key >", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyGreaterThanOrEqualTo(String value) {
            addCriterion("process_key >=", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyLessThan(String value) {
            addCriterion("process_key <", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyLessThanOrEqualTo(String value) {
            addCriterion("process_key <=", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyLike(String value) {
            addCriterion("process_key like", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyNotLike(String value) {
            addCriterion("process_key not like", value, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyIn(List<String> values) {
            addCriterion("process_key in", values, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyNotIn(List<String> values) {
            addCriterion("process_key not in", values, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyBetween(String value1, String value2) {
            addCriterion("process_key between", value1, value2, "processKey");
            return (Criteria) this;
        }

        public Criteria andProcessKeyNotBetween(String value1, String value2) {
            addCriterion("process_key not between", value1, value2, "processKey");
            return (Criteria) this;
        }

        public Criteria andParametersIsNull() {
            addCriterion("`parameters` is null");
            return (Criteria) this;
        }

        public Criteria andParametersIsNotNull() {
            addCriterion("`parameters` is not null");
            return (Criteria) this;
        }

        public Criteria andParametersEqualTo(String value) {
            addCriterion("`parameters` =", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersNotEqualTo(String value) {
            addCriterion("`parameters` <>", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersGreaterThan(String value) {
            addCriterion("`parameters` >", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersGreaterThanOrEqualTo(String value) {
            addCriterion("`parameters` >=", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersLessThan(String value) {
            addCriterion("`parameters` <", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersLessThanOrEqualTo(String value) {
            addCriterion("`parameters` <=", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersLike(String value) {
            addCriterion("`parameters` like", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersNotLike(String value) {
            addCriterion("`parameters` not like", value, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersIn(List<String> values) {
            addCriterion("`parameters` in", values, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersNotIn(List<String> values) {
            addCriterion("`parameters` not in", values, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersBetween(String value1, String value2) {
            addCriterion("`parameters` between", value1, value2, "parameters");
            return (Criteria) this;
        }

        public Criteria andParametersNotBetween(String value1, String value2) {
            addCriterion("`parameters` not between", value1, value2, "parameters");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("`result` is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("`result` is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("`result` =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("`result` <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("`result` >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("`result` >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("`result` <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("`result` <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("`result` like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("`result` not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("`result` in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("`result` not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("`result` between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("`result` not between", value1, value2, "result");
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