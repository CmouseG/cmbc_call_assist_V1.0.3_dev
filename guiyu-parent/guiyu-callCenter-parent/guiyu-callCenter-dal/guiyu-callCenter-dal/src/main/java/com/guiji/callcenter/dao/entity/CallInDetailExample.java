package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallInDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public CallInDetailExample() {
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

        public Criteria andAccurateIntentIsNull() {
            addCriterion("accurate_intent is null");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentIsNotNull() {
            addCriterion("accurate_intent is not null");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentEqualTo(String value) {
            addCriterion("accurate_intent =", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotEqualTo(String value) {
            addCriterion("accurate_intent <>", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentGreaterThan(String value) {
            addCriterion("accurate_intent >", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentGreaterThanOrEqualTo(String value) {
            addCriterion("accurate_intent >=", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLessThan(String value) {
            addCriterion("accurate_intent <", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLessThanOrEqualTo(String value) {
            addCriterion("accurate_intent <=", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLike(String value) {
            addCriterion("accurate_intent like", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotLike(String value) {
            addCriterion("accurate_intent not like", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentIn(List<String> values) {
            addCriterion("accurate_intent in", values, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotIn(List<String> values) {
            addCriterion("accurate_intent not in", values, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentBetween(String value1, String value2) {
            addCriterion("accurate_intent between", value1, value2, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotBetween(String value1, String value2) {
            addCriterion("accurate_intent not between", value1, value2, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextIsNull() {
            addCriterion("agent_answer_text is null");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextIsNotNull() {
            addCriterion("agent_answer_text is not null");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextEqualTo(String value) {
            addCriterion("agent_answer_text =", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextNotEqualTo(String value) {
            addCriterion("agent_answer_text <>", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextGreaterThan(String value) {
            addCriterion("agent_answer_text >", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextGreaterThanOrEqualTo(String value) {
            addCriterion("agent_answer_text >=", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextLessThan(String value) {
            addCriterion("agent_answer_text <", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextLessThanOrEqualTo(String value) {
            addCriterion("agent_answer_text <=", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextLike(String value) {
            addCriterion("agent_answer_text like", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextNotLike(String value) {
            addCriterion("agent_answer_text not like", value, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextIn(List<String> values) {
            addCriterion("agent_answer_text in", values, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextNotIn(List<String> values) {
            addCriterion("agent_answer_text not in", values, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextBetween(String value1, String value2) {
            addCriterion("agent_answer_text between", value1, value2, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTextNotBetween(String value1, String value2) {
            addCriterion("agent_answer_text not between", value1, value2, "agentAnswerText");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeIsNull() {
            addCriterion("agent_answer_time is null");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeIsNotNull() {
            addCriterion("agent_answer_time is not null");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeEqualTo(Date value) {
            addCriterion("agent_answer_time =", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeNotEqualTo(Date value) {
            addCriterion("agent_answer_time <>", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeGreaterThan(Date value) {
            addCriterion("agent_answer_time >", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("agent_answer_time >=", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeLessThan(Date value) {
            addCriterion("agent_answer_time <", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeLessThanOrEqualTo(Date value) {
            addCriterion("agent_answer_time <=", value, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeIn(List<Date> values) {
            addCriterion("agent_answer_time in", values, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeNotIn(List<Date> values) {
            addCriterion("agent_answer_time not in", values, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeBetween(Date value1, Date value2) {
            addCriterion("agent_answer_time between", value1, value2, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAgentAnswerTimeNotBetween(Date value1, Date value2) {
            addCriterion("agent_answer_time not between", value1, value2, "agentAnswerTime");
            return (Criteria) this;
        }

        public Criteria andAiDurationIsNull() {
            addCriterion("ai_duration is null");
            return (Criteria) this;
        }

        public Criteria andAiDurationIsNotNull() {
            addCriterion("ai_duration is not null");
            return (Criteria) this;
        }

        public Criteria andAiDurationEqualTo(Integer value) {
            addCriterion("ai_duration =", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationNotEqualTo(Integer value) {
            addCriterion("ai_duration <>", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationGreaterThan(Integer value) {
            addCriterion("ai_duration >", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("ai_duration >=", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationLessThan(Integer value) {
            addCriterion("ai_duration <", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationLessThanOrEqualTo(Integer value) {
            addCriterion("ai_duration <=", value, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationIn(List<Integer> values) {
            addCriterion("ai_duration in", values, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationNotIn(List<Integer> values) {
            addCriterion("ai_duration not in", values, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationBetween(Integer value1, Integer value2) {
            addCriterion("ai_duration between", value1, value2, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAiDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("ai_duration not between", value1, value2, "aiDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationIsNull() {
            addCriterion("asr_duration is null");
            return (Criteria) this;
        }

        public Criteria andAsrDurationIsNotNull() {
            addCriterion("asr_duration is not null");
            return (Criteria) this;
        }

        public Criteria andAsrDurationEqualTo(Integer value) {
            addCriterion("asr_duration =", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationNotEqualTo(Integer value) {
            addCriterion("asr_duration <>", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationGreaterThan(Integer value) {
            addCriterion("asr_duration >", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("asr_duration >=", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationLessThan(Integer value) {
            addCriterion("asr_duration <", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationLessThanOrEqualTo(Integer value) {
            addCriterion("asr_duration <=", value, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationIn(List<Integer> values) {
            addCriterion("asr_duration in", values, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationNotIn(List<Integer> values) {
            addCriterion("asr_duration not in", values, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationBetween(Integer value1, Integer value2) {
            addCriterion("asr_duration between", value1, value2, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andAsrDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("asr_duration not between", value1, value2, "asrDuration");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextIsNull() {
            addCriterion("bot_answer_text is null");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextIsNotNull() {
            addCriterion("bot_answer_text is not null");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextEqualTo(String value) {
            addCriterion("bot_answer_text =", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextNotEqualTo(String value) {
            addCriterion("bot_answer_text <>", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextGreaterThan(String value) {
            addCriterion("bot_answer_text >", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextGreaterThanOrEqualTo(String value) {
            addCriterion("bot_answer_text >=", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextLessThan(String value) {
            addCriterion("bot_answer_text <", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextLessThanOrEqualTo(String value) {
            addCriterion("bot_answer_text <=", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextLike(String value) {
            addCriterion("bot_answer_text like", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextNotLike(String value) {
            addCriterion("bot_answer_text not like", value, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextIn(List<String> values) {
            addCriterion("bot_answer_text in", values, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextNotIn(List<String> values) {
            addCriterion("bot_answer_text not in", values, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextBetween(String value1, String value2) {
            addCriterion("bot_answer_text between", value1, value2, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTextNotBetween(String value1, String value2) {
            addCriterion("bot_answer_text not between", value1, value2, "botAnswerText");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeIsNull() {
            addCriterion("bot_answer_time is null");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeIsNotNull() {
            addCriterion("bot_answer_time is not null");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeEqualTo(Date value) {
            addCriterion("bot_answer_time =", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeNotEqualTo(Date value) {
            addCriterion("bot_answer_time <>", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeGreaterThan(Date value) {
            addCriterion("bot_answer_time >", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("bot_answer_time >=", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeLessThan(Date value) {
            addCriterion("bot_answer_time <", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeLessThanOrEqualTo(Date value) {
            addCriterion("bot_answer_time <=", value, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeIn(List<Date> values) {
            addCriterion("bot_answer_time in", values, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeNotIn(List<Date> values) {
            addCriterion("bot_answer_time not in", values, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeBetween(Date value1, Date value2) {
            addCriterion("bot_answer_time between", value1, value2, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andBotAnswerTimeNotBetween(Date value1, Date value2) {
            addCriterion("bot_answer_time not between", value1, value2, "botAnswerTime");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeIsNull() {
            addCriterion("call_detail_type is null");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeIsNotNull() {
            addCriterion("call_detail_type is not null");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeEqualTo(Integer value) {
            addCriterion("call_detail_type =", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeNotEqualTo(Integer value) {
            addCriterion("call_detail_type <>", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeGreaterThan(Integer value) {
            addCriterion("call_detail_type >", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_detail_type >=", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeLessThan(Integer value) {
            addCriterion("call_detail_type <", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeLessThanOrEqualTo(Integer value) {
            addCriterion("call_detail_type <=", value, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeIn(List<Integer> values) {
            addCriterion("call_detail_type in", values, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeNotIn(List<Integer> values) {
            addCriterion("call_detail_type not in", values, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeBetween(Integer value1, Integer value2) {
            addCriterion("call_detail_type between", value1, value2, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCallDetailTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("call_detail_type not between", value1, value2, "callDetailType");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextIsNull() {
            addCriterion("customer_say_text is null");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextIsNotNull() {
            addCriterion("customer_say_text is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextEqualTo(String value) {
            addCriterion("customer_say_text =", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextNotEqualTo(String value) {
            addCriterion("customer_say_text <>", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextGreaterThan(String value) {
            addCriterion("customer_say_text >", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextGreaterThanOrEqualTo(String value) {
            addCriterion("customer_say_text >=", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextLessThan(String value) {
            addCriterion("customer_say_text <", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextLessThanOrEqualTo(String value) {
            addCriterion("customer_say_text <=", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextLike(String value) {
            addCriterion("customer_say_text like", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextNotLike(String value) {
            addCriterion("customer_say_text not like", value, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextIn(List<String> values) {
            addCriterion("customer_say_text in", values, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextNotIn(List<String> values) {
            addCriterion("customer_say_text not in", values, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextBetween(String value1, String value2) {
            addCriterion("customer_say_text between", value1, value2, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTextNotBetween(String value1, String value2) {
            addCriterion("customer_say_text not between", value1, value2, "customerSayText");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeIsNull() {
            addCriterion("customer_say_time is null");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeIsNotNull() {
            addCriterion("customer_say_time is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeEqualTo(Date value) {
            addCriterion("customer_say_time =", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeNotEqualTo(Date value) {
            addCriterion("customer_say_time <>", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeGreaterThan(Date value) {
            addCriterion("customer_say_time >", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("customer_say_time >=", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeLessThan(Date value) {
            addCriterion("customer_say_time <", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeLessThanOrEqualTo(Date value) {
            addCriterion("customer_say_time <=", value, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeIn(List<Date> values) {
            addCriterion("customer_say_time in", values, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeNotIn(List<Date> values) {
            addCriterion("customer_say_time not in", values, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeBetween(Date value1, Date value2) {
            addCriterion("customer_say_time between", value1, value2, "customerSayTime");
            return (Criteria) this;
        }

        public Criteria andCustomerSayTimeNotBetween(Date value1, Date value2) {
            addCriterion("customer_say_time not between", value1, value2, "customerSayTime");
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

        public Criteria andTotalDurationIsNull() {
            addCriterion("total_duration is null");
            return (Criteria) this;
        }

        public Criteria andTotalDurationIsNotNull() {
            addCriterion("total_duration is not null");
            return (Criteria) this;
        }

        public Criteria andTotalDurationEqualTo(Integer value) {
            addCriterion("total_duration =", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationNotEqualTo(Integer value) {
            addCriterion("total_duration <>", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationGreaterThan(Integer value) {
            addCriterion("total_duration >", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_duration >=", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationLessThan(Integer value) {
            addCriterion("total_duration <", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationLessThanOrEqualTo(Integer value) {
            addCriterion("total_duration <=", value, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationIn(List<Integer> values) {
            addCriterion("total_duration in", values, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationNotIn(List<Integer> values) {
            addCriterion("total_duration not in", values, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationBetween(Integer value1, Integer value2) {
            addCriterion("total_duration between", value1, value2, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andTotalDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("total_duration not between", value1, value2, "totalDuration");
            return (Criteria) this;
        }

        public Criteria andShardingValueIsNull() {
            addCriterion("sharding_value is null");
            return (Criteria) this;
        }

        public Criteria andShardingValueIsNotNull() {
            addCriterion("sharding_value is not null");
            return (Criteria) this;
        }

        public Criteria andShardingValueEqualTo(Integer value) {
            addCriterion("sharding_value =", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueNotEqualTo(Integer value) {
            addCriterion("sharding_value <>", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueGreaterThan(Integer value) {
            addCriterion("sharding_value >", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("sharding_value >=", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueLessThan(Integer value) {
            addCriterion("sharding_value <", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueLessThanOrEqualTo(Integer value) {
            addCriterion("sharding_value <=", value, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueIn(List<Integer> values) {
            addCriterion("sharding_value in", values, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueNotIn(List<Integer> values) {
            addCriterion("sharding_value not in", values, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueBetween(Integer value1, Integer value2) {
            addCriterion("sharding_value between", value1, value2, "shardingValue");
            return (Criteria) this;
        }

        public Criteria andShardingValueNotBetween(Integer value1, Integer value2) {
            addCriterion("sharding_value not between", value1, value2, "shardingValue");
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