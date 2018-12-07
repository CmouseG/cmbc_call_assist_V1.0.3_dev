package com.guiji.da.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RobotCallProcessStatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public RobotCallProcessStatExample() {
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

        public Criteria andStatDateIsNull() {
            addCriterion("stat_date is null");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNotNull() {
            addCriterion("stat_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatDateEqualTo(String value) {
            addCriterion("stat_date =", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotEqualTo(String value) {
            addCriterion("stat_date <>", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThan(String value) {
            addCriterion("stat_date >", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThanOrEqualTo(String value) {
            addCriterion("stat_date >=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThan(String value) {
            addCriterion("stat_date <", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThanOrEqualTo(String value) {
            addCriterion("stat_date <=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLike(String value) {
            addCriterion("stat_date like", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotLike(String value) {
            addCriterion("stat_date not like", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateIn(List<String> values) {
            addCriterion("stat_date in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotIn(List<String> values) {
            addCriterion("stat_date not in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateBetween(String value1, String value2) {
            addCriterion("stat_date between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotBetween(String value1, String value2) {
            addCriterion("stat_date not between", value1, value2, "statDate");
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

        public Criteria andAiAnswerIsNull() {
            addCriterion("ai_answer is null");
            return (Criteria) this;
        }

        public Criteria andAiAnswerIsNotNull() {
            addCriterion("ai_answer is not null");
            return (Criteria) this;
        }

        public Criteria andAiAnswerEqualTo(String value) {
            addCriterion("ai_answer =", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerNotEqualTo(String value) {
            addCriterion("ai_answer <>", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerGreaterThan(String value) {
            addCriterion("ai_answer >", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerGreaterThanOrEqualTo(String value) {
            addCriterion("ai_answer >=", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerLessThan(String value) {
            addCriterion("ai_answer <", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerLessThanOrEqualTo(String value) {
            addCriterion("ai_answer <=", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerLike(String value) {
            addCriterion("ai_answer like", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerNotLike(String value) {
            addCriterion("ai_answer not like", value, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerIn(List<String> values) {
            addCriterion("ai_answer in", values, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerNotIn(List<String> values) {
            addCriterion("ai_answer not in", values, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerBetween(String value1, String value2) {
            addCriterion("ai_answer between", value1, value2, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andAiAnswerNotBetween(String value1, String value2) {
            addCriterion("ai_answer not between", value1, value2, "aiAnswer");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainIsNull() {
            addCriterion("current_domain is null");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainIsNotNull() {
            addCriterion("current_domain is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainEqualTo(String value) {
            addCriterion("current_domain =", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainNotEqualTo(String value) {
            addCriterion("current_domain <>", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainGreaterThan(String value) {
            addCriterion("current_domain >", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainGreaterThanOrEqualTo(String value) {
            addCriterion("current_domain >=", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainLessThan(String value) {
            addCriterion("current_domain <", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainLessThanOrEqualTo(String value) {
            addCriterion("current_domain <=", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainLike(String value) {
            addCriterion("current_domain like", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainNotLike(String value) {
            addCriterion("current_domain not like", value, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainIn(List<String> values) {
            addCriterion("current_domain in", values, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainNotIn(List<String> values) {
            addCriterion("current_domain not in", values, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainBetween(String value1, String value2) {
            addCriterion("current_domain between", value1, value2, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andCurrentDomainNotBetween(String value1, String value2) {
            addCriterion("current_domain not between", value1, value2, "currentDomain");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNull() {
            addCriterion("domain_type is null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNotNull() {
            addCriterion("domain_type is not null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeEqualTo(String value) {
            addCriterion("domain_type =", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotEqualTo(String value) {
            addCriterion("domain_type <>", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThan(String value) {
            addCriterion("domain_type >", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThanOrEqualTo(String value) {
            addCriterion("domain_type >=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThan(String value) {
            addCriterion("domain_type <", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThanOrEqualTo(String value) {
            addCriterion("domain_type <=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLike(String value) {
            addCriterion("domain_type like", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotLike(String value) {
            addCriterion("domain_type not like", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIn(List<String> values) {
            addCriterion("domain_type in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotIn(List<String> values) {
            addCriterion("domain_type not in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeBetween(String value1, String value2) {
            addCriterion("domain_type between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotBetween(String value1, String value2) {
            addCriterion("domain_type not between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andTotalStatIsNull() {
            addCriterion("total_stat is null");
            return (Criteria) this;
        }

        public Criteria andTotalStatIsNotNull() {
            addCriterion("total_stat is not null");
            return (Criteria) this;
        }

        public Criteria andTotalStatEqualTo(Integer value) {
            addCriterion("total_stat =", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatNotEqualTo(Integer value) {
            addCriterion("total_stat <>", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatGreaterThan(Integer value) {
            addCriterion("total_stat >", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_stat >=", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatLessThan(Integer value) {
            addCriterion("total_stat <", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatLessThanOrEqualTo(Integer value) {
            addCriterion("total_stat <=", value, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatIn(List<Integer> values) {
            addCriterion("total_stat in", values, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatNotIn(List<Integer> values) {
            addCriterion("total_stat not in", values, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatBetween(Integer value1, Integer value2) {
            addCriterion("total_stat between", value1, value2, "totalStat");
            return (Criteria) this;
        }

        public Criteria andTotalStatNotBetween(Integer value1, Integer value2) {
            addCriterion("total_stat not between", value1, value2, "totalStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatIsNull() {
            addCriterion("refused_stat is null");
            return (Criteria) this;
        }

        public Criteria andRefusedStatIsNotNull() {
            addCriterion("refused_stat is not null");
            return (Criteria) this;
        }

        public Criteria andRefusedStatEqualTo(String value) {
            addCriterion("refused_stat =", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatNotEqualTo(String value) {
            addCriterion("refused_stat <>", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatGreaterThan(String value) {
            addCriterion("refused_stat >", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatGreaterThanOrEqualTo(String value) {
            addCriterion("refused_stat >=", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatLessThan(String value) {
            addCriterion("refused_stat <", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatLessThanOrEqualTo(String value) {
            addCriterion("refused_stat <=", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatLike(String value) {
            addCriterion("refused_stat like", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatNotLike(String value) {
            addCriterion("refused_stat not like", value, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatIn(List<String> values) {
            addCriterion("refused_stat in", values, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatNotIn(List<String> values) {
            addCriterion("refused_stat not in", values, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatBetween(String value1, String value2) {
            addCriterion("refused_stat between", value1, value2, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andRefusedStatNotBetween(String value1, String value2) {
            addCriterion("refused_stat not between", value1, value2, "refusedStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatIsNull() {
            addCriterion("hangup_stat is null");
            return (Criteria) this;
        }

        public Criteria andHangupStatIsNotNull() {
            addCriterion("hangup_stat is not null");
            return (Criteria) this;
        }

        public Criteria andHangupStatEqualTo(String value) {
            addCriterion("hangup_stat =", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatNotEqualTo(String value) {
            addCriterion("hangup_stat <>", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatGreaterThan(String value) {
            addCriterion("hangup_stat >", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatGreaterThanOrEqualTo(String value) {
            addCriterion("hangup_stat >=", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatLessThan(String value) {
            addCriterion("hangup_stat <", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatLessThanOrEqualTo(String value) {
            addCriterion("hangup_stat <=", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatLike(String value) {
            addCriterion("hangup_stat like", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatNotLike(String value) {
            addCriterion("hangup_stat not like", value, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatIn(List<String> values) {
            addCriterion("hangup_stat in", values, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatNotIn(List<String> values) {
            addCriterion("hangup_stat not in", values, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatBetween(String value1, String value2) {
            addCriterion("hangup_stat between", value1, value2, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andHangupStatNotBetween(String value1, String value2) {
            addCriterion("hangup_stat not between", value1, value2, "hangupStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatIsNull() {
            addCriterion("match_stat is null");
            return (Criteria) this;
        }

        public Criteria andMatchStatIsNotNull() {
            addCriterion("match_stat is not null");
            return (Criteria) this;
        }

        public Criteria andMatchStatEqualTo(String value) {
            addCriterion("match_stat =", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatNotEqualTo(String value) {
            addCriterion("match_stat <>", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatGreaterThan(String value) {
            addCriterion("match_stat >", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatGreaterThanOrEqualTo(String value) {
            addCriterion("match_stat >=", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatLessThan(String value) {
            addCriterion("match_stat <", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatLessThanOrEqualTo(String value) {
            addCriterion("match_stat <=", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatLike(String value) {
            addCriterion("match_stat like", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatNotLike(String value) {
            addCriterion("match_stat not like", value, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatIn(List<String> values) {
            addCriterion("match_stat in", values, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatNotIn(List<String> values) {
            addCriterion("match_stat not in", values, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatBetween(String value1, String value2) {
            addCriterion("match_stat between", value1, value2, "matchStat");
            return (Criteria) this;
        }

        public Criteria andMatchStatNotBetween(String value1, String value2) {
            addCriterion("match_stat not between", value1, value2, "matchStat");
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