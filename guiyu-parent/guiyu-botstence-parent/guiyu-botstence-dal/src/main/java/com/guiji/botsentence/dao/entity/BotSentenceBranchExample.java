package com.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotSentenceBranchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceBranchExample() {
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

        public Criteria andBranchIdIsNull() {
            addCriterion("branch_id is null");
            return (Criteria) this;
        }

        public Criteria andBranchIdIsNotNull() {
            addCriterion("branch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBranchIdEqualTo(String value) {
            addCriterion("branch_id =", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotEqualTo(String value) {
            addCriterion("branch_id <>", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThan(String value) {
            addCriterion("branch_id >", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdGreaterThanOrEqualTo(String value) {
            addCriterion("branch_id >=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThan(String value) {
            addCriterion("branch_id <", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLessThanOrEqualTo(String value) {
            addCriterion("branch_id <=", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdLike(String value) {
            addCriterion("branch_id like", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotLike(String value) {
            addCriterion("branch_id not like", value, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdIn(List<String> values) {
            addCriterion("branch_id in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotIn(List<String> values) {
            addCriterion("branch_id not in", values, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdBetween(String value1, String value2) {
            addCriterion("branch_id between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchIdNotBetween(String value1, String value2) {
            addCriterion("branch_id not between", value1, value2, "branchId");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNull() {
            addCriterion("branch_name is null");
            return (Criteria) this;
        }

        public Criteria andBranchNameIsNotNull() {
            addCriterion("branch_name is not null");
            return (Criteria) this;
        }

        public Criteria andBranchNameEqualTo(String value) {
            addCriterion("branch_name =", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotEqualTo(String value) {
            addCriterion("branch_name <>", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThan(String value) {
            addCriterion("branch_name >", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("branch_name >=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThan(String value) {
            addCriterion("branch_name <", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLessThanOrEqualTo(String value) {
            addCriterion("branch_name <=", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameLike(String value) {
            addCriterion("branch_name like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotLike(String value) {
            addCriterion("branch_name not like", value, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameIn(List<String> values) {
            addCriterion("branch_name in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotIn(List<String> values) {
            addCriterion("branch_name not in", values, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameBetween(String value1, String value2) {
            addCriterion("branch_name between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andBranchNameNotBetween(String value1, String value2) {
            addCriterion("branch_name not between", value1, value2, "branchName");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Long value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Long value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Long value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Long value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Long value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Long value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Long> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Long> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Long value1, Long value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Long value1, Long value2) {
            addCriterion("seq not between", value1, value2, "seq");
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

        public Criteria andResponseIsNull() {
            addCriterion("response is null");
            return (Criteria) this;
        }

        public Criteria andResponseIsNotNull() {
            addCriterion("response is not null");
            return (Criteria) this;
        }

        public Criteria andResponseEqualTo(String value) {
            addCriterion("response =", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotEqualTo(String value) {
            addCriterion("response <>", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThan(String value) {
            addCriterion("response >", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseGreaterThanOrEqualTo(String value) {
            addCriterion("response >=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThan(String value) {
            addCriterion("response <", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLessThanOrEqualTo(String value) {
            addCriterion("response <=", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseLike(String value) {
            addCriterion("response like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotLike(String value) {
            addCriterion("response not like", value, "response");
            return (Criteria) this;
        }

        public Criteria andResponseIn(List<String> values) {
            addCriterion("response in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotIn(List<String> values) {
            addCriterion("response not in", values, "response");
            return (Criteria) this;
        }

        public Criteria andResponseBetween(String value1, String value2) {
            addCriterion("response between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andResponseNotBetween(String value1, String value2) {
            addCriterion("response not between", value1, value2, "response");
            return (Criteria) this;
        }

        public Criteria andNextIsNull() {
            addCriterion("`NEXT` is null");
            return (Criteria) this;
        }

        public Criteria andNextIsNotNull() {
            addCriterion("`NEXT` is not null");
            return (Criteria) this;
        }

        public Criteria andNextEqualTo(String value) {
            addCriterion("`NEXT` =", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextNotEqualTo(String value) {
            addCriterion("`NEXT` <>", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextGreaterThan(String value) {
            addCriterion("`NEXT` >", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextGreaterThanOrEqualTo(String value) {
            addCriterion("`NEXT` >=", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextLessThan(String value) {
            addCriterion("`NEXT` <", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextLessThanOrEqualTo(String value) {
            addCriterion("`NEXT` <=", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextLike(String value) {
            addCriterion("`NEXT` like", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextNotLike(String value) {
            addCriterion("`NEXT` not like", value, "next");
            return (Criteria) this;
        }

        public Criteria andNextIn(List<String> values) {
            addCriterion("`NEXT` in", values, "next");
            return (Criteria) this;
        }

        public Criteria andNextNotIn(List<String> values) {
            addCriterion("`NEXT` not in", values, "next");
            return (Criteria) this;
        }

        public Criteria andNextBetween(String value1, String value2) {
            addCriterion("`NEXT` between", value1, value2, "next");
            return (Criteria) this;
        }

        public Criteria andNextNotBetween(String value1, String value2) {
            addCriterion("`NEXT` not between", value1, value2, "next");
            return (Criteria) this;
        }

        public Criteria andIntentsIsNull() {
            addCriterion("intents is null");
            return (Criteria) this;
        }

        public Criteria andIntentsIsNotNull() {
            addCriterion("intents is not null");
            return (Criteria) this;
        }

        public Criteria andIntentsEqualTo(String value) {
            addCriterion("intents =", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsNotEqualTo(String value) {
            addCriterion("intents <>", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsGreaterThan(String value) {
            addCriterion("intents >", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsGreaterThanOrEqualTo(String value) {
            addCriterion("intents >=", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsLessThan(String value) {
            addCriterion("intents <", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsLessThanOrEqualTo(String value) {
            addCriterion("intents <=", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsLike(String value) {
            addCriterion("intents like", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsNotLike(String value) {
            addCriterion("intents not like", value, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsIn(List<String> values) {
            addCriterion("intents in", values, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsNotIn(List<String> values) {
            addCriterion("intents not in", values, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsBetween(String value1, String value2) {
            addCriterion("intents between", value1, value2, "intents");
            return (Criteria) this;
        }

        public Criteria andIntentsNotBetween(String value1, String value2) {
            addCriterion("intents not between", value1, value2, "intents");
            return (Criteria) this;
        }

        public Criteria andEndIsNull() {
            addCriterion("`END` is null");
            return (Criteria) this;
        }

        public Criteria andEndIsNotNull() {
            addCriterion("`END` is not null");
            return (Criteria) this;
        }

        public Criteria andEndEqualTo(String value) {
            addCriterion("`END` =", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotEqualTo(String value) {
            addCriterion("`END` <>", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThan(String value) {
            addCriterion("`END` >", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThanOrEqualTo(String value) {
            addCriterion("`END` >=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThan(String value) {
            addCriterion("`END` <", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThanOrEqualTo(String value) {
            addCriterion("`END` <=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLike(String value) {
            addCriterion("`END` like", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotLike(String value) {
            addCriterion("`END` not like", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndIn(List<String> values) {
            addCriterion("`END` in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotIn(List<String> values) {
            addCriterion("`END` not in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndBetween(String value1, String value2) {
            addCriterion("`END` between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotBetween(String value1, String value2) {
            addCriterion("`END` not between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andDomainIsNull() {
            addCriterion("`domain` is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("`domain` is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("`domain` =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("`domain` <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("`domain` >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("`domain` >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("`domain` <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("`domain` <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("`domain` like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("`domain` not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("`domain` in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("`domain` not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("`domain` between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("`domain` not between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeIsNull() {
            addCriterion("is_special_limit_free is null");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeIsNotNull() {
            addCriterion("is_special_limit_free is not null");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeEqualTo(String value) {
            addCriterion("is_special_limit_free =", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeNotEqualTo(String value) {
            addCriterion("is_special_limit_free <>", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeGreaterThan(String value) {
            addCriterion("is_special_limit_free >", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeGreaterThanOrEqualTo(String value) {
            addCriterion("is_special_limit_free >=", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeLessThan(String value) {
            addCriterion("is_special_limit_free <", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeLessThanOrEqualTo(String value) {
            addCriterion("is_special_limit_free <=", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeLike(String value) {
            addCriterion("is_special_limit_free like", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeNotLike(String value) {
            addCriterion("is_special_limit_free not like", value, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeIn(List<String> values) {
            addCriterion("is_special_limit_free in", values, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeNotIn(List<String> values) {
            addCriterion("is_special_limit_free not in", values, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeBetween(String value1, String value2) {
            addCriterion("is_special_limit_free between", value1, value2, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLimitFreeNotBetween(String value1, String value2) {
            addCriterion("is_special_limit_free not between", value1, value2, "isSpecialLimitFree");
            return (Criteria) this;
        }

        public Criteria andUserAskIsNull() {
            addCriterion("user_ask is null");
            return (Criteria) this;
        }

        public Criteria andUserAskIsNotNull() {
            addCriterion("user_ask is not null");
            return (Criteria) this;
        }

        public Criteria andUserAskEqualTo(String value) {
            addCriterion("user_ask =", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskNotEqualTo(String value) {
            addCriterion("user_ask <>", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskGreaterThan(String value) {
            addCriterion("user_ask >", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskGreaterThanOrEqualTo(String value) {
            addCriterion("user_ask >=", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskLessThan(String value) {
            addCriterion("user_ask <", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskLessThanOrEqualTo(String value) {
            addCriterion("user_ask <=", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskLike(String value) {
            addCriterion("user_ask like", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskNotLike(String value) {
            addCriterion("user_ask not like", value, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskIn(List<String> values) {
            addCriterion("user_ask in", values, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskNotIn(List<String> values) {
            addCriterion("user_ask not in", values, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskBetween(String value1, String value2) {
            addCriterion("user_ask between", value1, value2, "userAsk");
            return (Criteria) this;
        }

        public Criteria andUserAskNotBetween(String value1, String value2) {
            addCriterion("user_ask not between", value1, value2, "userAsk");
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

        public Criteria andLineNameIsNull() {
            addCriterion("line_name is null");
            return (Criteria) this;
        }

        public Criteria andLineNameIsNotNull() {
            addCriterion("line_name is not null");
            return (Criteria) this;
        }

        public Criteria andLineNameEqualTo(String value) {
            addCriterion("line_name =", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotEqualTo(String value) {
            addCriterion("line_name <>", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThan(String value) {
            addCriterion("line_name >", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameGreaterThanOrEqualTo(String value) {
            addCriterion("line_name >=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThan(String value) {
            addCriterion("line_name <", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLessThanOrEqualTo(String value) {
            addCriterion("line_name <=", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameLike(String value) {
            addCriterion("line_name like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotLike(String value) {
            addCriterion("line_name not like", value, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameIn(List<String> values) {
            addCriterion("line_name in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotIn(List<String> values) {
            addCriterion("line_name not in", values, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameBetween(String value1, String value2) {
            addCriterion("line_name between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andLineNameNotBetween(String value1, String value2) {
            addCriterion("line_name not between", value1, value2, "lineName");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(String value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(String value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(String value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(String value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(String value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(String value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLike(String value) {
            addCriterion("is_show like", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotLike(String value) {
            addCriterion("is_show not like", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<String> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<String> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(String value1, String value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(String value1, String value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andRespnameIsNull() {
            addCriterion("respname is null");
            return (Criteria) this;
        }

        public Criteria andRespnameIsNotNull() {
            addCriterion("respname is not null");
            return (Criteria) this;
        }

        public Criteria andRespnameEqualTo(String value) {
            addCriterion("respname =", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameNotEqualTo(String value) {
            addCriterion("respname <>", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameGreaterThan(String value) {
            addCriterion("respname >", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameGreaterThanOrEqualTo(String value) {
            addCriterion("respname >=", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameLessThan(String value) {
            addCriterion("respname <", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameLessThanOrEqualTo(String value) {
            addCriterion("respname <=", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameLike(String value) {
            addCriterion("respname like", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameNotLike(String value) {
            addCriterion("respname not like", value, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameIn(List<String> values) {
            addCriterion("respname in", values, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameNotIn(List<String> values) {
            addCriterion("respname not in", values, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameBetween(String value1, String value2) {
            addCriterion("respname between", value1, value2, "respname");
            return (Criteria) this;
        }

        public Criteria andRespnameNotBetween(String value1, String value2) {
            addCriterion("respname not between", value1, value2, "respname");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andAgentIntentIsNull() {
            addCriterion("agent_intent is null");
            return (Criteria) this;
        }

        public Criteria andAgentIntentIsNotNull() {
            addCriterion("agent_intent is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIntentEqualTo(String value) {
            addCriterion("agent_intent =", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentNotEqualTo(String value) {
            addCriterion("agent_intent <>", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentGreaterThan(String value) {
            addCriterion("agent_intent >", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentGreaterThanOrEqualTo(String value) {
            addCriterion("agent_intent >=", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentLessThan(String value) {
            addCriterion("agent_intent <", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentLessThanOrEqualTo(String value) {
            addCriterion("agent_intent <=", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentLike(String value) {
            addCriterion("agent_intent like", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentNotLike(String value) {
            addCriterion("agent_intent not like", value, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentIn(List<String> values) {
            addCriterion("agent_intent in", values, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentNotIn(List<String> values) {
            addCriterion("agent_intent not in", values, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentBetween(String value1, String value2) {
            addCriterion("agent_intent between", value1, value2, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andAgentIntentNotBetween(String value1, String value2) {
            addCriterion("agent_intent not between", value1, value2, "agentIntent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentIsNull() {
            addCriterion("need_agent is null");
            return (Criteria) this;
        }

        public Criteria andNeedAgentIsNotNull() {
            addCriterion("need_agent is not null");
            return (Criteria) this;
        }

        public Criteria andNeedAgentEqualTo(String value) {
            addCriterion("need_agent =", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentNotEqualTo(String value) {
            addCriterion("need_agent <>", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentGreaterThan(String value) {
            addCriterion("need_agent >", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentGreaterThanOrEqualTo(String value) {
            addCriterion("need_agent >=", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentLessThan(String value) {
            addCriterion("need_agent <", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentLessThanOrEqualTo(String value) {
            addCriterion("need_agent <=", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentLike(String value) {
            addCriterion("need_agent like", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentNotLike(String value) {
            addCriterion("need_agent not like", value, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentIn(List<String> values) {
            addCriterion("need_agent in", values, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentNotIn(List<String> values) {
            addCriterion("need_agent not in", values, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentBetween(String value1, String value2) {
            addCriterion("need_agent between", value1, value2, "needAgent");
            return (Criteria) this;
        }

        public Criteria andNeedAgentNotBetween(String value1, String value2) {
            addCriterion("need_agent not between", value1, value2, "needAgent");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(String value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(String value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(String value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(String value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(String value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(String value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLike(String value) {
            addCriterion("weight like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotLike(String value) {
            addCriterion("weight not like", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<String> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<String> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(String value1, String value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(String value1, String value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andRuleIsNull() {
            addCriterion("`rule` is null");
            return (Criteria) this;
        }

        public Criteria andRuleIsNotNull() {
            addCriterion("`rule` is not null");
            return (Criteria) this;
        }

        public Criteria andRuleEqualTo(String value) {
            addCriterion("`rule` =", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleNotEqualTo(String value) {
            addCriterion("`rule` <>", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleGreaterThan(String value) {
            addCriterion("`rule` >", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleGreaterThanOrEqualTo(String value) {
            addCriterion("`rule` >=", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleLessThan(String value) {
            addCriterion("`rule` <", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleLessThanOrEqualTo(String value) {
            addCriterion("`rule` <=", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleLike(String value) {
            addCriterion("`rule` like", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleNotLike(String value) {
            addCriterion("`rule` not like", value, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleIn(List<String> values) {
            addCriterion("`rule` in", values, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleNotIn(List<String> values) {
            addCriterion("`rule` not in", values, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleBetween(String value1, String value2) {
            addCriterion("`rule` between", value1, value2, "rule");
            return (Criteria) this;
        }

        public Criteria andRuleNotBetween(String value1, String value2) {
            addCriterion("`rule` not between", value1, value2, "rule");
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