package com.guiji.robot.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TtsWavHisExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TtsWavHisExample() {
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

        public Criteria andTtsKeyIsNull() {
            addCriterion("tts_key is null");
            return (Criteria) this;
        }

        public Criteria andTtsKeyIsNotNull() {
            addCriterion("tts_key is not null");
            return (Criteria) this;
        }

        public Criteria andTtsKeyEqualTo(String value) {
            addCriterion("tts_key =", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyNotEqualTo(String value) {
            addCriterion("tts_key <>", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyGreaterThan(String value) {
            addCriterion("tts_key >", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyGreaterThanOrEqualTo(String value) {
            addCriterion("tts_key >=", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyLessThan(String value) {
            addCriterion("tts_key <", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyLessThanOrEqualTo(String value) {
            addCriterion("tts_key <=", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyLike(String value) {
            addCriterion("tts_key like", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyNotLike(String value) {
            addCriterion("tts_key not like", value, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyIn(List<String> values) {
            addCriterion("tts_key in", values, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyNotIn(List<String> values) {
            addCriterion("tts_key not in", values, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyBetween(String value1, String value2) {
            addCriterion("tts_key between", value1, value2, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsKeyNotBetween(String value1, String value2) {
            addCriterion("tts_key not between", value1, value2, "ttsKey");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysIsNull() {
            addCriterion("tts_param_keys is null");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysIsNotNull() {
            addCriterion("tts_param_keys is not null");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysEqualTo(String value) {
            addCriterion("tts_param_keys =", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysNotEqualTo(String value) {
            addCriterion("tts_param_keys <>", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysGreaterThan(String value) {
            addCriterion("tts_param_keys >", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysGreaterThanOrEqualTo(String value) {
            addCriterion("tts_param_keys >=", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysLessThan(String value) {
            addCriterion("tts_param_keys <", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysLessThanOrEqualTo(String value) {
            addCriterion("tts_param_keys <=", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysLike(String value) {
            addCriterion("tts_param_keys like", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysNotLike(String value) {
            addCriterion("tts_param_keys not like", value, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysIn(List<String> values) {
            addCriterion("tts_param_keys in", values, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysNotIn(List<String> values) {
            addCriterion("tts_param_keys not in", values, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysBetween(String value1, String value2) {
            addCriterion("tts_param_keys between", value1, value2, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamKeysNotBetween(String value1, String value2) {
            addCriterion("tts_param_keys not between", value1, value2, "ttsParamKeys");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesIsNull() {
            addCriterion("tts_param_values is null");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesIsNotNull() {
            addCriterion("tts_param_values is not null");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesEqualTo(String value) {
            addCriterion("tts_param_values =", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesNotEqualTo(String value) {
            addCriterion("tts_param_values <>", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesGreaterThan(String value) {
            addCriterion("tts_param_values >", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesGreaterThanOrEqualTo(String value) {
            addCriterion("tts_param_values >=", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesLessThan(String value) {
            addCriterion("tts_param_values <", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesLessThanOrEqualTo(String value) {
            addCriterion("tts_param_values <=", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesLike(String value) {
            addCriterion("tts_param_values like", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesNotLike(String value) {
            addCriterion("tts_param_values not like", value, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesIn(List<String> values) {
            addCriterion("tts_param_values in", values, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesNotIn(List<String> values) {
            addCriterion("tts_param_values not in", values, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesBetween(String value1, String value2) {
            addCriterion("tts_param_values between", value1, value2, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsParamValuesNotBetween(String value1, String value2) {
            addCriterion("tts_param_values not between", value1, value2, "ttsParamValues");
            return (Criteria) this;
        }

        public Criteria andTtsUrlIsNull() {
            addCriterion("tts_url is null");
            return (Criteria) this;
        }

        public Criteria andTtsUrlIsNotNull() {
            addCriterion("tts_url is not null");
            return (Criteria) this;
        }

        public Criteria andTtsUrlEqualTo(String value) {
            addCriterion("tts_url =", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlNotEqualTo(String value) {
            addCriterion("tts_url <>", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlGreaterThan(String value) {
            addCriterion("tts_url >", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlGreaterThanOrEqualTo(String value) {
            addCriterion("tts_url >=", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlLessThan(String value) {
            addCriterion("tts_url <", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlLessThanOrEqualTo(String value) {
            addCriterion("tts_url <=", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlLike(String value) {
            addCriterion("tts_url like", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlNotLike(String value) {
            addCriterion("tts_url not like", value, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlIn(List<String> values) {
            addCriterion("tts_url in", values, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlNotIn(List<String> values) {
            addCriterion("tts_url not in", values, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlBetween(String value1, String value2) {
            addCriterion("tts_url between", value1, value2, "ttsUrl");
            return (Criteria) this;
        }

        public Criteria andTtsUrlNotBetween(String value1, String value2) {
            addCriterion("tts_url not between", value1, value2, "ttsUrl");
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