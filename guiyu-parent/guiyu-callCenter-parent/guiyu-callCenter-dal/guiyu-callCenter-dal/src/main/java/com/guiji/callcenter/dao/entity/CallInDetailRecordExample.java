package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class CallInDetailRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CallInDetailRecordExample() {
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

        public Criteria andCallDetailIdIsNull() {
            addCriterion("call_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdIsNotNull() {
            addCriterion("call_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdEqualTo(Long value) {
            addCriterion("call_detail_id =", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdNotEqualTo(Long value) {
            addCriterion("call_detail_id <>", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdGreaterThan(Long value) {
            addCriterion("call_detail_id >", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("call_detail_id >=", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdLessThan(Long value) {
            addCriterion("call_detail_id <", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("call_detail_id <=", value, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdIn(List<Long> values) {
            addCriterion("call_detail_id in", values, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdNotIn(List<Long> values) {
            addCriterion("call_detail_id not in", values, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdBetween(Long value1, Long value2) {
            addCriterion("call_detail_id between", value1, value2, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("call_detail_id not between", value1, value2, "callDetailId");
            return (Criteria) this;
        }

        public Criteria andCallIdIsNull() {
            addCriterion("call_id is null");
            return (Criteria) this;
        }

        public Criteria andCallIdIsNotNull() {
            addCriterion("call_id is not null");
            return (Criteria) this;
        }

        public Criteria andCallIdEqualTo(Long value) {
            addCriterion("call_id =", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotEqualTo(Long value) {
            addCriterion("call_id <>", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdGreaterThan(Long value) {
            addCriterion("call_id >", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdGreaterThanOrEqualTo(Long value) {
            addCriterion("call_id >=", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdLessThan(Long value) {
            addCriterion("call_id <", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdLessThanOrEqualTo(Long value) {
            addCriterion("call_id <=", value, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdIn(List<Long> values) {
            addCriterion("call_id in", values, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotIn(List<Long> values) {
            addCriterion("call_id not in", values, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdBetween(Long value1, Long value2) {
            addCriterion("call_id between", value1, value2, "callId");
            return (Criteria) this;
        }

        public Criteria andCallIdNotBetween(Long value1, Long value2) {
            addCriterion("call_id not between", value1, value2, "callId");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileIsNull() {
            addCriterion("agent_record_file is null");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileIsNotNull() {
            addCriterion("agent_record_file is not null");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileEqualTo(String value) {
            addCriterion("agent_record_file =", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileNotEqualTo(String value) {
            addCriterion("agent_record_file <>", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileGreaterThan(String value) {
            addCriterion("agent_record_file >", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileGreaterThanOrEqualTo(String value) {
            addCriterion("agent_record_file >=", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileLessThan(String value) {
            addCriterion("agent_record_file <", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileLessThanOrEqualTo(String value) {
            addCriterion("agent_record_file <=", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileLike(String value) {
            addCriterion("agent_record_file like", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileNotLike(String value) {
            addCriterion("agent_record_file not like", value, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileIn(List<String> values) {
            addCriterion("agent_record_file in", values, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileNotIn(List<String> values) {
            addCriterion("agent_record_file not in", values, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileBetween(String value1, String value2) {
            addCriterion("agent_record_file between", value1, value2, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordFileNotBetween(String value1, String value2) {
            addCriterion("agent_record_file not between", value1, value2, "agentRecordFile");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlIsNull() {
            addCriterion("agent_record_url is null");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlIsNotNull() {
            addCriterion("agent_record_url is not null");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlEqualTo(String value) {
            addCriterion("agent_record_url =", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlNotEqualTo(String value) {
            addCriterion("agent_record_url <>", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlGreaterThan(String value) {
            addCriterion("agent_record_url >", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlGreaterThanOrEqualTo(String value) {
            addCriterion("agent_record_url >=", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlLessThan(String value) {
            addCriterion("agent_record_url <", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlLessThanOrEqualTo(String value) {
            addCriterion("agent_record_url <=", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlLike(String value) {
            addCriterion("agent_record_url like", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlNotLike(String value) {
            addCriterion("agent_record_url not like", value, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlIn(List<String> values) {
            addCriterion("agent_record_url in", values, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlNotIn(List<String> values) {
            addCriterion("agent_record_url not in", values, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlBetween(String value1, String value2) {
            addCriterion("agent_record_url between", value1, value2, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andAgentRecordUrlNotBetween(String value1, String value2) {
            addCriterion("agent_record_url not between", value1, value2, "agentRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileIsNull() {
            addCriterion("bot_record_file is null");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileIsNotNull() {
            addCriterion("bot_record_file is not null");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileEqualTo(String value) {
            addCriterion("bot_record_file =", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileNotEqualTo(String value) {
            addCriterion("bot_record_file <>", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileGreaterThan(String value) {
            addCriterion("bot_record_file >", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileGreaterThanOrEqualTo(String value) {
            addCriterion("bot_record_file >=", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileLessThan(String value) {
            addCriterion("bot_record_file <", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileLessThanOrEqualTo(String value) {
            addCriterion("bot_record_file <=", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileLike(String value) {
            addCriterion("bot_record_file like", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileNotLike(String value) {
            addCriterion("bot_record_file not like", value, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileIn(List<String> values) {
            addCriterion("bot_record_file in", values, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileNotIn(List<String> values) {
            addCriterion("bot_record_file not in", values, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileBetween(String value1, String value2) {
            addCriterion("bot_record_file between", value1, value2, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordFileNotBetween(String value1, String value2) {
            addCriterion("bot_record_file not between", value1, value2, "botRecordFile");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlIsNull() {
            addCriterion("bot_record_url is null");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlIsNotNull() {
            addCriterion("bot_record_url is not null");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlEqualTo(String value) {
            addCriterion("bot_record_url =", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlNotEqualTo(String value) {
            addCriterion("bot_record_url <>", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlGreaterThan(String value) {
            addCriterion("bot_record_url >", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlGreaterThanOrEqualTo(String value) {
            addCriterion("bot_record_url >=", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlLessThan(String value) {
            addCriterion("bot_record_url <", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlLessThanOrEqualTo(String value) {
            addCriterion("bot_record_url <=", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlLike(String value) {
            addCriterion("bot_record_url like", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlNotLike(String value) {
            addCriterion("bot_record_url not like", value, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlIn(List<String> values) {
            addCriterion("bot_record_url in", values, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlNotIn(List<String> values) {
            addCriterion("bot_record_url not in", values, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlBetween(String value1, String value2) {
            addCriterion("bot_record_url between", value1, value2, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andBotRecordUrlNotBetween(String value1, String value2) {
            addCriterion("bot_record_url not between", value1, value2, "botRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileIsNull() {
            addCriterion("customer_record_file is null");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileIsNotNull() {
            addCriterion("customer_record_file is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileEqualTo(String value) {
            addCriterion("customer_record_file =", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileNotEqualTo(String value) {
            addCriterion("customer_record_file <>", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileGreaterThan(String value) {
            addCriterion("customer_record_file >", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileGreaterThanOrEqualTo(String value) {
            addCriterion("customer_record_file >=", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileLessThan(String value) {
            addCriterion("customer_record_file <", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileLessThanOrEqualTo(String value) {
            addCriterion("customer_record_file <=", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileLike(String value) {
            addCriterion("customer_record_file like", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileNotLike(String value) {
            addCriterion("customer_record_file not like", value, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileIn(List<String> values) {
            addCriterion("customer_record_file in", values, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileNotIn(List<String> values) {
            addCriterion("customer_record_file not in", values, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileBetween(String value1, String value2) {
            addCriterion("customer_record_file between", value1, value2, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordFileNotBetween(String value1, String value2) {
            addCriterion("customer_record_file not between", value1, value2, "customerRecordFile");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlIsNull() {
            addCriterion("customer_record_url is null");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlIsNotNull() {
            addCriterion("customer_record_url is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlEqualTo(String value) {
            addCriterion("customer_record_url =", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlNotEqualTo(String value) {
            addCriterion("customer_record_url <>", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlGreaterThan(String value) {
            addCriterion("customer_record_url >", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlGreaterThanOrEqualTo(String value) {
            addCriterion("customer_record_url >=", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlLessThan(String value) {
            addCriterion("customer_record_url <", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlLessThanOrEqualTo(String value) {
            addCriterion("customer_record_url <=", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlLike(String value) {
            addCriterion("customer_record_url like", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlNotLike(String value) {
            addCriterion("customer_record_url not like", value, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlIn(List<String> values) {
            addCriterion("customer_record_url in", values, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlNotIn(List<String> values) {
            addCriterion("customer_record_url not in", values, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlBetween(String value1, String value2) {
            addCriterion("customer_record_url between", value1, value2, "customerRecordUrl");
            return (Criteria) this;
        }

        public Criteria andCustomerRecordUrlNotBetween(String value1, String value2) {
            addCriterion("customer_record_url not between", value1, value2, "customerRecordUrl");
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