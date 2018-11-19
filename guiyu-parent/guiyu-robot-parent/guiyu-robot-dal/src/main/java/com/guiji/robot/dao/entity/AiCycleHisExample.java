package com.guiji.robot.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AiCycleHisExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AiCycleHisExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAssignIdIsNull() {
            addCriterion("assign_id is null");
            return (Criteria) this;
        }

        public Criteria andAssignIdIsNotNull() {
            addCriterion("assign_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssignIdEqualTo(String value) {
            addCriterion("assign_id =", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotEqualTo(String value) {
            addCriterion("assign_id <>", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdGreaterThan(String value) {
            addCriterion("assign_id >", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdGreaterThanOrEqualTo(String value) {
            addCriterion("assign_id >=", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdLessThan(String value) {
            addCriterion("assign_id <", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdLessThanOrEqualTo(String value) {
            addCriterion("assign_id <=", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdLike(String value) {
            addCriterion("assign_id like", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotLike(String value) {
            addCriterion("assign_id not like", value, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdIn(List<String> values) {
            addCriterion("assign_id in", values, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotIn(List<String> values) {
            addCriterion("assign_id not in", values, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdBetween(String value1, String value2) {
            addCriterion("assign_id between", value1, value2, "assignId");
            return (Criteria) this;
        }

        public Criteria andAssignIdNotBetween(String value1, String value2) {
            addCriterion("assign_id not between", value1, value2, "assignId");
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

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAiNoIsNull() {
            addCriterion("ai_no is null");
            return (Criteria) this;
        }

        public Criteria andAiNoIsNotNull() {
            addCriterion("ai_no is not null");
            return (Criteria) this;
        }

        public Criteria andAiNoEqualTo(String value) {
            addCriterion("ai_no =", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoNotEqualTo(String value) {
            addCriterion("ai_no <>", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoGreaterThan(String value) {
            addCriterion("ai_no >", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoGreaterThanOrEqualTo(String value) {
            addCriterion("ai_no >=", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoLessThan(String value) {
            addCriterion("ai_no <", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoLessThanOrEqualTo(String value) {
            addCriterion("ai_no <=", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoLike(String value) {
            addCriterion("ai_no like", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoNotLike(String value) {
            addCriterion("ai_no not like", value, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoIn(List<String> values) {
            addCriterion("ai_no in", values, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoNotIn(List<String> values) {
            addCriterion("ai_no not in", values, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoBetween(String value1, String value2) {
            addCriterion("ai_no between", value1, value2, "aiNo");
            return (Criteria) this;
        }

        public Criteria andAiNoNotBetween(String value1, String value2) {
            addCriterion("ai_no not between", value1, value2, "aiNo");
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

        public Criteria andAssignDateIsNull() {
            addCriterion("assign_date is null");
            return (Criteria) this;
        }

        public Criteria andAssignDateIsNotNull() {
            addCriterion("assign_date is not null");
            return (Criteria) this;
        }

        public Criteria andAssignDateEqualTo(String value) {
            addCriterion("assign_date =", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotEqualTo(String value) {
            addCriterion("assign_date <>", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateGreaterThan(String value) {
            addCriterion("assign_date >", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateGreaterThanOrEqualTo(String value) {
            addCriterion("assign_date >=", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateLessThan(String value) {
            addCriterion("assign_date <", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateLessThanOrEqualTo(String value) {
            addCriterion("assign_date <=", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateLike(String value) {
            addCriterion("assign_date like", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotLike(String value) {
            addCriterion("assign_date not like", value, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateIn(List<String> values) {
            addCriterion("assign_date in", values, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotIn(List<String> values) {
            addCriterion("assign_date not in", values, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateBetween(String value1, String value2) {
            addCriterion("assign_date between", value1, value2, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignDateNotBetween(String value1, String value2) {
            addCriterion("assign_date not between", value1, value2, "assignDate");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNull() {
            addCriterion("assign_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNotNull() {
            addCriterion("assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeEqualTo(String value) {
            addCriterion("assign_time =", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotEqualTo(String value) {
            addCriterion("assign_time <>", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThan(String value) {
            addCriterion("assign_time >", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThanOrEqualTo(String value) {
            addCriterion("assign_time >=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThan(String value) {
            addCriterion("assign_time <", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThanOrEqualTo(String value) {
            addCriterion("assign_time <=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLike(String value) {
            addCriterion("assign_time like", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotLike(String value) {
            addCriterion("assign_time not like", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIn(List<String> values) {
            addCriterion("assign_time in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotIn(List<String> values) {
            addCriterion("assign_time not in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeBetween(String value1, String value2) {
            addCriterion("assign_time between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotBetween(String value1, String value2) {
            addCriterion("assign_time not between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateIsNull() {
            addCriterion("taskback_date is null");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateIsNotNull() {
            addCriterion("taskback_date is not null");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateEqualTo(String value) {
            addCriterion("taskback_date =", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateNotEqualTo(String value) {
            addCriterion("taskback_date <>", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateGreaterThan(String value) {
            addCriterion("taskback_date >", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateGreaterThanOrEqualTo(String value) {
            addCriterion("taskback_date >=", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateLessThan(String value) {
            addCriterion("taskback_date <", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateLessThanOrEqualTo(String value) {
            addCriterion("taskback_date <=", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateLike(String value) {
            addCriterion("taskback_date like", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateNotLike(String value) {
            addCriterion("taskback_date not like", value, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateIn(List<String> values) {
            addCriterion("taskback_date in", values, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateNotIn(List<String> values) {
            addCriterion("taskback_date not in", values, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateBetween(String value1, String value2) {
            addCriterion("taskback_date between", value1, value2, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackDateNotBetween(String value1, String value2) {
            addCriterion("taskback_date not between", value1, value2, "taskbackDate");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeIsNull() {
            addCriterion("taskback_time is null");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeIsNotNull() {
            addCriterion("taskback_time is not null");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeEqualTo(String value) {
            addCriterion("taskback_time =", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeNotEqualTo(String value) {
            addCriterion("taskback_time <>", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeGreaterThan(String value) {
            addCriterion("taskback_time >", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeGreaterThanOrEqualTo(String value) {
            addCriterion("taskback_time >=", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeLessThan(String value) {
            addCriterion("taskback_time <", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeLessThanOrEqualTo(String value) {
            addCriterion("taskback_time <=", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeLike(String value) {
            addCriterion("taskback_time like", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeNotLike(String value) {
            addCriterion("taskback_time not like", value, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeIn(List<String> values) {
            addCriterion("taskback_time in", values, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeNotIn(List<String> values) {
            addCriterion("taskback_time not in", values, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeBetween(String value1, String value2) {
            addCriterion("taskback_time between", value1, value2, "taskbackTime");
            return (Criteria) this;
        }

        public Criteria andTaskbackTimeNotBetween(String value1, String value2) {
            addCriterion("taskback_time not between", value1, value2, "taskbackTime");
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