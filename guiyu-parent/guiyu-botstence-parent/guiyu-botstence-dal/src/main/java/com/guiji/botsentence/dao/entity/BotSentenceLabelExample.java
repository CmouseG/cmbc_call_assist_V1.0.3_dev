package com.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotSentenceLabelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceLabelExample() {
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

        public Criteria andLabelIdIsNull() {
            addCriterion("label_id is null");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNotNull() {
            addCriterion("label_id is not null");
            return (Criteria) this;
        }

        public Criteria andLabelIdEqualTo(String value) {
            addCriterion("label_id =", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotEqualTo(String value) {
            addCriterion("label_id <>", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThan(String value) {
            addCriterion("label_id >", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThanOrEqualTo(String value) {
            addCriterion("label_id >=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThan(String value) {
            addCriterion("label_id <", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThanOrEqualTo(String value) {
            addCriterion("label_id <=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLike(String value) {
            addCriterion("label_id like", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotLike(String value) {
            addCriterion("label_id not like", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdIn(List<String> values) {
            addCriterion("label_id in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotIn(List<String> values) {
            addCriterion("label_id not in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdBetween(String value1, String value2) {
            addCriterion("label_id between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotBetween(String value1, String value2) {
            addCriterion("label_id not between", value1, value2, "labelId");
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

        public Criteria andProcessIdEqualTo(String value) {
            addCriterion("process_id =", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotEqualTo(String value) {
            addCriterion("process_id <>", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThan(String value) {
            addCriterion("process_id >", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThanOrEqualTo(String value) {
            addCriterion("process_id >=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThan(String value) {
            addCriterion("process_id <", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThanOrEqualTo(String value) {
            addCriterion("process_id <=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLike(String value) {
            addCriterion("process_id like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotLike(String value) {
            addCriterion("process_id not like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdIn(List<String> values) {
            addCriterion("process_id in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotIn(List<String> values) {
            addCriterion("process_id not in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdBetween(String value1, String value2) {
            addCriterion("process_id between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotBetween(String value1, String value2) {
            addCriterion("process_id not between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNull() {
            addCriterion("label_name is null");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNotNull() {
            addCriterion("label_name is not null");
            return (Criteria) this;
        }

        public Criteria andLabelNameEqualTo(String value) {
            addCriterion("label_name =", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotEqualTo(String value) {
            addCriterion("label_name <>", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThan(String value) {
            addCriterion("label_name >", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThanOrEqualTo(String value) {
            addCriterion("label_name >=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThan(String value) {
            addCriterion("label_name <", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThanOrEqualTo(String value) {
            addCriterion("label_name <=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLike(String value) {
            addCriterion("label_name like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotLike(String value) {
            addCriterion("label_name not like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameIn(List<String> values) {
            addCriterion("label_name in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotIn(List<String> values) {
            addCriterion("label_name not in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameBetween(String value1, String value2) {
            addCriterion("label_name between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotBetween(String value1, String value2) {
            addCriterion("label_name not between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andConversationCountIsNull() {
            addCriterion("conversation_count is null");
            return (Criteria) this;
        }

        public Criteria andConversationCountIsNotNull() {
            addCriterion("conversation_count is not null");
            return (Criteria) this;
        }

        public Criteria andConversationCountEqualTo(Integer value) {
            addCriterion("conversation_count =", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountNotEqualTo(Integer value) {
            addCriterion("conversation_count <>", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountGreaterThan(Integer value) {
            addCriterion("conversation_count >", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("conversation_count >=", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountLessThan(Integer value) {
            addCriterion("conversation_count <", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountLessThanOrEqualTo(Integer value) {
            addCriterion("conversation_count <=", value, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountIn(List<Integer> values) {
            addCriterion("conversation_count in", values, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountNotIn(List<Integer> values) {
            addCriterion("conversation_count not in", values, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountBetween(Integer value1, Integer value2) {
            addCriterion("conversation_count between", value1, value2, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andConversationCountNotBetween(Integer value1, Integer value2) {
            addCriterion("conversation_count not between", value1, value2, "conversationCount");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsIsNull() {
            addCriterion("display_keywords is null");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsIsNotNull() {
            addCriterion("display_keywords is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsEqualTo(String value) {
            addCriterion("display_keywords =", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsNotEqualTo(String value) {
            addCriterion("display_keywords <>", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsGreaterThan(String value) {
            addCriterion("display_keywords >", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("display_keywords >=", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsLessThan(String value) {
            addCriterion("display_keywords <", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsLessThanOrEqualTo(String value) {
            addCriterion("display_keywords <=", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsLike(String value) {
            addCriterion("display_keywords like", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsNotLike(String value) {
            addCriterion("display_keywords not like", value, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsIn(List<String> values) {
            addCriterion("display_keywords in", values, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsNotIn(List<String> values) {
            addCriterion("display_keywords not in", values, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBetween(String value1, String value2) {
            addCriterion("display_keywords between", value1, value2, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsNotBetween(String value1, String value2) {
            addCriterion("display_keywords not between", value1, value2, "displayKeywords");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeIsNull() {
            addCriterion("display_keywords_before is null");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeIsNotNull() {
            addCriterion("display_keywords_before is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeEqualTo(String value) {
            addCriterion("display_keywords_before =", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeNotEqualTo(String value) {
            addCriterion("display_keywords_before <>", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeGreaterThan(String value) {
            addCriterion("display_keywords_before >", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeGreaterThanOrEqualTo(String value) {
            addCriterion("display_keywords_before >=", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeLessThan(String value) {
            addCriterion("display_keywords_before <", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeLessThanOrEqualTo(String value) {
            addCriterion("display_keywords_before <=", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeLike(String value) {
            addCriterion("display_keywords_before like", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeNotLike(String value) {
            addCriterion("display_keywords_before not like", value, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeIn(List<String> values) {
            addCriterion("display_keywords_before in", values, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeNotIn(List<String> values) {
            addCriterion("display_keywords_before not in", values, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeBetween(String value1, String value2) {
            addCriterion("display_keywords_before between", value1, value2, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andDisplayKeywordsBeforeNotBetween(String value1, String value2) {
            addCriterion("display_keywords_before not between", value1, value2, "displayKeywordsBefore");
            return (Criteria) this;
        }

        public Criteria andSpecialCountIsNull() {
            addCriterion("special_count is null");
            return (Criteria) this;
        }

        public Criteria andSpecialCountIsNotNull() {
            addCriterion("special_count is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialCountEqualTo(Integer value) {
            addCriterion("special_count =", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountNotEqualTo(Integer value) {
            addCriterion("special_count <>", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountGreaterThan(Integer value) {
            addCriterion("special_count >", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("special_count >=", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountLessThan(Integer value) {
            addCriterion("special_count <", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountLessThanOrEqualTo(Integer value) {
            addCriterion("special_count <=", value, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountIn(List<Integer> values) {
            addCriterion("special_count in", values, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountNotIn(List<Integer> values) {
            addCriterion("special_count not in", values, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountBetween(Integer value1, Integer value2) {
            addCriterion("special_count between", value1, value2, "specialCount");
            return (Criteria) this;
        }

        public Criteria andSpecialCountNotBetween(Integer value1, Integer value2) {
            addCriterion("special_count not between", value1, value2, "specialCount");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSIsNull() {
            addCriterion("used_time_s is null");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSIsNotNull() {
            addCriterion("used_time_s is not null");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSEqualTo(Integer value) {
            addCriterion("used_time_s =", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSNotEqualTo(Integer value) {
            addCriterion("used_time_s <>", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSGreaterThan(Integer value) {
            addCriterion("used_time_s >", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSGreaterThanOrEqualTo(Integer value) {
            addCriterion("used_time_s >=", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSLessThan(Integer value) {
            addCriterion("used_time_s <", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSLessThanOrEqualTo(Integer value) {
            addCriterion("used_time_s <=", value, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSIn(List<Integer> values) {
            addCriterion("used_time_s in", values, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSNotIn(List<Integer> values) {
            addCriterion("used_time_s not in", values, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSBetween(Integer value1, Integer value2) {
            addCriterion("used_time_s between", value1, value2, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andUsedTimeSNotBetween(Integer value1, Integer value2) {
            addCriterion("used_time_s not between", value1, value2, "usedTimeS");
            return (Criteria) this;
        }

        public Criteria andDenyCountIsNull() {
            addCriterion("deny_count is null");
            return (Criteria) this;
        }

        public Criteria andDenyCountIsNotNull() {
            addCriterion("deny_count is not null");
            return (Criteria) this;
        }

        public Criteria andDenyCountEqualTo(Integer value) {
            addCriterion("deny_count =", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountNotEqualTo(Integer value) {
            addCriterion("deny_count <>", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountGreaterThan(Integer value) {
            addCriterion("deny_count >", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("deny_count >=", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountLessThan(Integer value) {
            addCriterion("deny_count <", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountLessThanOrEqualTo(Integer value) {
            addCriterion("deny_count <=", value, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountIn(List<Integer> values) {
            addCriterion("deny_count in", values, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountNotIn(List<Integer> values) {
            addCriterion("deny_count not in", values, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountBetween(Integer value1, Integer value2) {
            addCriterion("deny_count between", value1, value2, "denyCount");
            return (Criteria) this;
        }

        public Criteria andDenyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("deny_count not between", value1, value2, "denyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountIsNull() {
            addCriterion("busy_count is null");
            return (Criteria) this;
        }

        public Criteria andBusyCountIsNotNull() {
            addCriterion("busy_count is not null");
            return (Criteria) this;
        }

        public Criteria andBusyCountEqualTo(Integer value) {
            addCriterion("busy_count =", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountNotEqualTo(Integer value) {
            addCriterion("busy_count <>", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountGreaterThan(Integer value) {
            addCriterion("busy_count >", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("busy_count >=", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountLessThan(Integer value) {
            addCriterion("busy_count <", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountLessThanOrEqualTo(Integer value) {
            addCriterion("busy_count <=", value, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountIn(List<Integer> values) {
            addCriterion("busy_count in", values, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountNotIn(List<Integer> values) {
            addCriterion("busy_count not in", values, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountBetween(Integer value1, Integer value2) {
            addCriterion("busy_count between", value1, value2, "busyCount");
            return (Criteria) this;
        }

        public Criteria andBusyCountNotBetween(Integer value1, Integer value2) {
            addCriterion("busy_count not between", value1, value2, "busyCount");
            return (Criteria) this;
        }

        public Criteria andScoreUpIsNull() {
            addCriterion("score_up is null");
            return (Criteria) this;
        }

        public Criteria andScoreUpIsNotNull() {
            addCriterion("score_up is not null");
            return (Criteria) this;
        }

        public Criteria andScoreUpEqualTo(Double value) {
            addCriterion("score_up =", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpNotEqualTo(Double value) {
            addCriterion("score_up <>", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpGreaterThan(Double value) {
            addCriterion("score_up >", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpGreaterThanOrEqualTo(Double value) {
            addCriterion("score_up >=", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpLessThan(Double value) {
            addCriterion("score_up <", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpLessThanOrEqualTo(Double value) {
            addCriterion("score_up <=", value, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpIn(List<Double> values) {
            addCriterion("score_up in", values, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpNotIn(List<Double> values) {
            addCriterion("score_up not in", values, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpBetween(Double value1, Double value2) {
            addCriterion("score_up between", value1, value2, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreUpNotBetween(Double value1, Double value2) {
            addCriterion("score_up not between", value1, value2, "scoreUp");
            return (Criteria) this;
        }

        public Criteria andScoreLowIsNull() {
            addCriterion("score_low is null");
            return (Criteria) this;
        }

        public Criteria andScoreLowIsNotNull() {
            addCriterion("score_low is not null");
            return (Criteria) this;
        }

        public Criteria andScoreLowEqualTo(Double value) {
            addCriterion("score_low =", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowNotEqualTo(Double value) {
            addCriterion("score_low <>", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowGreaterThan(Double value) {
            addCriterion("score_low >", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowGreaterThanOrEqualTo(Double value) {
            addCriterion("score_low >=", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowLessThan(Double value) {
            addCriterion("score_low <", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowLessThanOrEqualTo(Double value) {
            addCriterion("score_low <=", value, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowIn(List<Double> values) {
            addCriterion("score_low in", values, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowNotIn(List<Double> values) {
            addCriterion("score_low not in", values, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowBetween(Double value1, Double value2) {
            addCriterion("score_low between", value1, value2, "scoreLow");
            return (Criteria) this;
        }

        public Criteria andScoreLowNotBetween(Double value1, Double value2) {
            addCriterion("score_low not between", value1, value2, "scoreLow");
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

        public Criteria andCrtUserIsNull() {
            addCriterion("crt_user is null");
            return (Criteria) this;
        }

        public Criteria andCrtUserIsNotNull() {
            addCriterion("crt_user is not null");
            return (Criteria) this;
        }

        public Criteria andCrtUserEqualTo(String value) {
            addCriterion("crt_user =", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotEqualTo(String value) {
            addCriterion("crt_user <>", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThan(String value) {
            addCriterion("crt_user >", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThanOrEqualTo(String value) {
            addCriterion("crt_user >=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThan(String value) {
            addCriterion("crt_user <", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThanOrEqualTo(String value) {
            addCriterion("crt_user <=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLike(String value) {
            addCriterion("crt_user like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotLike(String value) {
            addCriterion("crt_user not like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserIn(List<String> values) {
            addCriterion("crt_user in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotIn(List<String> values) {
            addCriterion("crt_user not in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserBetween(String value1, String value2) {
            addCriterion("crt_user between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotBetween(String value1, String value2) {
            addCriterion("crt_user not between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNull() {
            addCriterion("lst_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNotNull() {
            addCriterion("lst_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeEqualTo(Date value) {
            addCriterion("lst_update_time =", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotEqualTo(Date value) {
            addCriterion("lst_update_time <>", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThan(Date value) {
            addCriterion("lst_update_time >", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lst_update_time >=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThan(Date value) {
            addCriterion("lst_update_time <", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("lst_update_time <=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIn(List<Date> values) {
            addCriterion("lst_update_time in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotIn(List<Date> values) {
            addCriterion("lst_update_time not in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("lst_update_time between", value1, value2, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("lst_update_time not between", value1, value2, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNull() {
            addCriterion("lst_update_user is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNotNull() {
            addCriterion("lst_update_user is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserEqualTo(String value) {
            addCriterion("lst_update_user =", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotEqualTo(String value) {
            addCriterion("lst_update_user <>", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThan(String value) {
            addCriterion("lst_update_user >", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("lst_update_user >=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThan(String value) {
            addCriterion("lst_update_user <", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("lst_update_user <=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLike(String value) {
            addCriterion("lst_update_user like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotLike(String value) {
            addCriterion("lst_update_user not like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIn(List<String> values) {
            addCriterion("lst_update_user in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotIn(List<String> values) {
            addCriterion("lst_update_user not in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserBetween(String value1, String value2) {
            addCriterion("lst_update_user between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotBetween(String value1, String value2) {
            addCriterion("lst_update_user not between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andShowNameIsNull() {
            addCriterion("show_name is null");
            return (Criteria) this;
        }

        public Criteria andShowNameIsNotNull() {
            addCriterion("show_name is not null");
            return (Criteria) this;
        }

        public Criteria andShowNameEqualTo(String value) {
            addCriterion("show_name =", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotEqualTo(String value) {
            addCriterion("show_name <>", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameGreaterThan(String value) {
            addCriterion("show_name >", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameGreaterThanOrEqualTo(String value) {
            addCriterion("show_name >=", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLessThan(String value) {
            addCriterion("show_name <", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLessThanOrEqualTo(String value) {
            addCriterion("show_name <=", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLike(String value) {
            addCriterion("show_name like", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotLike(String value) {
            addCriterion("show_name not like", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameIn(List<String> values) {
            addCriterion("show_name in", values, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotIn(List<String> values) {
            addCriterion("show_name not in", values, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameBetween(String value1, String value2) {
            addCriterion("show_name between", value1, value2, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotBetween(String value1, String value2) {
            addCriterion("show_name not between", value1, value2, "showName");
            return (Criteria) this;
        }

        public Criteria andHelpDetailIsNull() {
            addCriterion("help_detail is null");
            return (Criteria) this;
        }

        public Criteria andHelpDetailIsNotNull() {
            addCriterion("help_detail is not null");
            return (Criteria) this;
        }

        public Criteria andHelpDetailEqualTo(String value) {
            addCriterion("help_detail =", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailNotEqualTo(String value) {
            addCriterion("help_detail <>", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailGreaterThan(String value) {
            addCriterion("help_detail >", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailGreaterThanOrEqualTo(String value) {
            addCriterion("help_detail >=", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailLessThan(String value) {
            addCriterion("help_detail <", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailLessThanOrEqualTo(String value) {
            addCriterion("help_detail <=", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailLike(String value) {
            addCriterion("help_detail like", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailNotLike(String value) {
            addCriterion("help_detail not like", value, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailIn(List<String> values) {
            addCriterion("help_detail in", values, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailNotIn(List<String> values) {
            addCriterion("help_detail not in", values, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailBetween(String value1, String value2) {
            addCriterion("help_detail between", value1, value2, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andHelpDetailNotBetween(String value1, String value2) {
            addCriterion("help_detail not between", value1, value2, "helpDetail");
            return (Criteria) this;
        }

        public Criteria andAnnotationIsNull() {
            addCriterion("annotation is null");
            return (Criteria) this;
        }

        public Criteria andAnnotationIsNotNull() {
            addCriterion("annotation is not null");
            return (Criteria) this;
        }

        public Criteria andAnnotationEqualTo(String value) {
            addCriterion("annotation =", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationNotEqualTo(String value) {
            addCriterion("annotation <>", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationGreaterThan(String value) {
            addCriterion("annotation >", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationGreaterThanOrEqualTo(String value) {
            addCriterion("annotation >=", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationLessThan(String value) {
            addCriterion("annotation <", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationLessThanOrEqualTo(String value) {
            addCriterion("annotation <=", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationLike(String value) {
            addCriterion("annotation like", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationNotLike(String value) {
            addCriterion("annotation not like", value, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationIn(List<String> values) {
            addCriterion("annotation in", values, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationNotIn(List<String> values) {
            addCriterion("annotation not in", values, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationBetween(String value1, String value2) {
            addCriterion("annotation between", value1, value2, "annotation");
            return (Criteria) this;
        }

        public Criteria andAnnotationNotBetween(String value1, String value2) {
            addCriterion("annotation not between", value1, value2, "annotation");
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