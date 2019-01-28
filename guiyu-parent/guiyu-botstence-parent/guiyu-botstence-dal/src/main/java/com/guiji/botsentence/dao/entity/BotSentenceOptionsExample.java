package com.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotSentenceOptionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceOptionsExample() {
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

        public Criteria andOptionsIdIsNull() {
            addCriterion("options_id is null");
            return (Criteria) this;
        }

        public Criteria andOptionsIdIsNotNull() {
            addCriterion("options_id is not null");
            return (Criteria) this;
        }

        public Criteria andOptionsIdEqualTo(String value) {
            addCriterion("options_id =", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdNotEqualTo(String value) {
            addCriterion("options_id <>", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdGreaterThan(String value) {
            addCriterion("options_id >", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdGreaterThanOrEqualTo(String value) {
            addCriterion("options_id >=", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdLessThan(String value) {
            addCriterion("options_id <", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdLessThanOrEqualTo(String value) {
            addCriterion("options_id <=", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdLike(String value) {
            addCriterion("options_id like", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdNotLike(String value) {
            addCriterion("options_id not like", value, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdIn(List<String> values) {
            addCriterion("options_id in", values, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdNotIn(List<String> values) {
            addCriterion("options_id not in", values, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdBetween(String value1, String value2) {
            addCriterion("options_id between", value1, value2, "optionsId");
            return (Criteria) this;
        }

        public Criteria andOptionsIdNotBetween(String value1, String value2) {
            addCriterion("options_id not between", value1, value2, "optionsId");
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

        public Criteria andCheckSimIsNull() {
            addCriterion("check_sim is null");
            return (Criteria) this;
        }

        public Criteria andCheckSimIsNotNull() {
            addCriterion("check_sim is not null");
            return (Criteria) this;
        }

        public Criteria andCheckSimEqualTo(Boolean value) {
            addCriterion("check_sim =", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimNotEqualTo(Boolean value) {
            addCriterion("check_sim <>", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimGreaterThan(Boolean value) {
            addCriterion("check_sim >", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimGreaterThanOrEqualTo(Boolean value) {
            addCriterion("check_sim >=", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimLessThan(Boolean value) {
            addCriterion("check_sim <", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimLessThanOrEqualTo(Boolean value) {
            addCriterion("check_sim <=", value, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimIn(List<Boolean> values) {
            addCriterion("check_sim in", values, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimNotIn(List<Boolean> values) {
            addCriterion("check_sim not in", values, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimBetween(Boolean value1, Boolean value2) {
            addCriterion("check_sim between", value1, value2, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCheckSimNotBetween(Boolean value1, Boolean value2) {
            addCriterion("check_sim not between", value1, value2, "checkSim");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorIsNull() {
            addCriterion("cur_domain_prior is null");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorIsNotNull() {
            addCriterion("cur_domain_prior is not null");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorEqualTo(Boolean value) {
            addCriterion("cur_domain_prior =", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorNotEqualTo(Boolean value) {
            addCriterion("cur_domain_prior <>", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorGreaterThan(Boolean value) {
            addCriterion("cur_domain_prior >", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorGreaterThanOrEqualTo(Boolean value) {
            addCriterion("cur_domain_prior >=", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorLessThan(Boolean value) {
            addCriterion("cur_domain_prior <", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorLessThanOrEqualTo(Boolean value) {
            addCriterion("cur_domain_prior <=", value, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorIn(List<Boolean> values) {
            addCriterion("cur_domain_prior in", values, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorNotIn(List<Boolean> values) {
            addCriterion("cur_domain_prior not in", values, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorBetween(Boolean value1, Boolean value2) {
            addCriterion("cur_domain_prior between", value1, value2, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andCurDomainPriorNotBetween(Boolean value1, Boolean value2) {
            addCriterion("cur_domain_prior not between", value1, value2, "curDomainPrior");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicIsNull() {
            addCriterion("use_not_match_logic is null");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicIsNotNull() {
            addCriterion("use_not_match_logic is not null");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicEqualTo(Boolean value) {
            addCriterion("use_not_match_logic =", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicNotEqualTo(Boolean value) {
            addCriterion("use_not_match_logic <>", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicGreaterThan(Boolean value) {
            addCriterion("use_not_match_logic >", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicGreaterThanOrEqualTo(Boolean value) {
            addCriterion("use_not_match_logic >=", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicLessThan(Boolean value) {
            addCriterion("use_not_match_logic <", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicLessThanOrEqualTo(Boolean value) {
            addCriterion("use_not_match_logic <=", value, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicIn(List<Boolean> values) {
            addCriterion("use_not_match_logic in", values, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicNotIn(List<Boolean> values) {
            addCriterion("use_not_match_logic not in", values, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicBetween(Boolean value1, Boolean value2) {
            addCriterion("use_not_match_logic between", value1, value2, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andUseNotMatchLogicNotBetween(Boolean value1, Boolean value2) {
            addCriterion("use_not_match_logic not between", value1, value2, "useNotMatchLogic");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionIsNull() {
            addCriterion("not_match_solution is null");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionIsNotNull() {
            addCriterion("not_match_solution is not null");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionEqualTo(String value) {
            addCriterion("not_match_solution =", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionNotEqualTo(String value) {
            addCriterion("not_match_solution <>", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionGreaterThan(String value) {
            addCriterion("not_match_solution >", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionGreaterThanOrEqualTo(String value) {
            addCriterion("not_match_solution >=", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionLessThan(String value) {
            addCriterion("not_match_solution <", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionLessThanOrEqualTo(String value) {
            addCriterion("not_match_solution <=", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionLike(String value) {
            addCriterion("not_match_solution like", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionNotLike(String value) {
            addCriterion("not_match_solution not like", value, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionIn(List<String> values) {
            addCriterion("not_match_solution in", values, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionNotIn(List<String> values) {
            addCriterion("not_match_solution not in", values, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionBetween(String value1, String value2) {
            addCriterion("not_match_solution between", value1, value2, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andNotMatchSolutionNotBetween(String value1, String value2) {
            addCriterion("not_match_solution not between", value1, value2, "notMatchSolution");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListIsNull() {
            addCriterion("use_endfiles_list is null");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListIsNotNull() {
            addCriterion("use_endfiles_list is not null");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListEqualTo(Boolean value) {
            addCriterion("use_endfiles_list =", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListNotEqualTo(Boolean value) {
            addCriterion("use_endfiles_list <>", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListGreaterThan(Boolean value) {
            addCriterion("use_endfiles_list >", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListGreaterThanOrEqualTo(Boolean value) {
            addCriterion("use_endfiles_list >=", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListLessThan(Boolean value) {
            addCriterion("use_endfiles_list <", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListLessThanOrEqualTo(Boolean value) {
            addCriterion("use_endfiles_list <=", value, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListIn(List<Boolean> values) {
            addCriterion("use_endfiles_list in", values, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListNotIn(List<Boolean> values) {
            addCriterion("use_endfiles_list not in", values, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListBetween(Boolean value1, Boolean value2) {
            addCriterion("use_endfiles_list between", value1, value2, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andUseEndfilesListNotBetween(Boolean value1, Boolean value2) {
            addCriterion("use_endfiles_list not between", value1, value2, "useEndfilesList");
            return (Criteria) this;
        }

        public Criteria andTradeIsNull() {
            addCriterion("trade is null");
            return (Criteria) this;
        }

        public Criteria andTradeIsNotNull() {
            addCriterion("trade is not null");
            return (Criteria) this;
        }

        public Criteria andTradeEqualTo(String value) {
            addCriterion("trade =", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotEqualTo(String value) {
            addCriterion("trade <>", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThan(String value) {
            addCriterion("trade >", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeGreaterThanOrEqualTo(String value) {
            addCriterion("trade >=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThan(String value) {
            addCriterion("trade <", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLessThanOrEqualTo(String value) {
            addCriterion("trade <=", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeLike(String value) {
            addCriterion("trade like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotLike(String value) {
            addCriterion("trade not like", value, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeIn(List<String> values) {
            addCriterion("trade in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotIn(List<String> values) {
            addCriterion("trade not in", values, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeBetween(String value1, String value2) {
            addCriterion("trade between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTradeNotBetween(String value1, String value2) {
            addCriterion("trade not between", value1, value2, "trade");
            return (Criteria) this;
        }

        public Criteria andTempnameIsNull() {
            addCriterion("tempname is null");
            return (Criteria) this;
        }

        public Criteria andTempnameIsNotNull() {
            addCriterion("tempname is not null");
            return (Criteria) this;
        }

        public Criteria andTempnameEqualTo(String value) {
            addCriterion("tempname =", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotEqualTo(String value) {
            addCriterion("tempname <>", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameGreaterThan(String value) {
            addCriterion("tempname >", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameGreaterThanOrEqualTo(String value) {
            addCriterion("tempname >=", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLessThan(String value) {
            addCriterion("tempname <", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLessThanOrEqualTo(String value) {
            addCriterion("tempname <=", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameLike(String value) {
            addCriterion("tempname like", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotLike(String value) {
            addCriterion("tempname not like", value, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameIn(List<String> values) {
            addCriterion("tempname in", values, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotIn(List<String> values) {
            addCriterion("tempname not in", values, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameBetween(String value1, String value2) {
            addCriterion("tempname between", value1, value2, "tempname");
            return (Criteria) this;
        }

        public Criteria andTempnameNotBetween(String value1, String value2) {
            addCriterion("tempname not between", value1, value2, "tempname");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartIsNull() {
            addCriterion("non_interruptable_start is null");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartIsNotNull() {
            addCriterion("non_interruptable_start is not null");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartEqualTo(Boolean value) {
            addCriterion("non_interruptable_start =", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartNotEqualTo(Boolean value) {
            addCriterion("non_interruptable_start <>", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartGreaterThan(Boolean value) {
            addCriterion("non_interruptable_start >", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("non_interruptable_start >=", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartLessThan(Boolean value) {
            addCriterion("non_interruptable_start <", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartLessThanOrEqualTo(Boolean value) {
            addCriterion("non_interruptable_start <=", value, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartIn(List<Boolean> values) {
            addCriterion("non_interruptable_start in", values, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartNotIn(List<Boolean> values) {
            addCriterion("non_interruptable_start not in", values, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartBetween(Boolean value1, Boolean value2) {
            addCriterion("non_interruptable_start between", value1, value2, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("non_interruptable_start not between", value1, value2, "nonInterruptableStart");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableIsNull() {
            addCriterion("non_interruptable is null");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableIsNotNull() {
            addCriterion("non_interruptable is not null");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableEqualTo(String value) {
            addCriterion("non_interruptable =", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableNotEqualTo(String value) {
            addCriterion("non_interruptable <>", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableGreaterThan(String value) {
            addCriterion("non_interruptable >", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableGreaterThanOrEqualTo(String value) {
            addCriterion("non_interruptable >=", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableLessThan(String value) {
            addCriterion("non_interruptable <", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableLessThanOrEqualTo(String value) {
            addCriterion("non_interruptable <=", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableLike(String value) {
            addCriterion("non_interruptable like", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableNotLike(String value) {
            addCriterion("non_interruptable not like", value, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableIn(List<String> values) {
            addCriterion("non_interruptable in", values, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableNotIn(List<String> values) {
            addCriterion("non_interruptable not in", values, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableBetween(String value1, String value2) {
            addCriterion("non_interruptable between", value1, value2, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andNonInterruptableNotBetween(String value1, String value2) {
            addCriterion("non_interruptable not between", value1, value2, "nonInterruptable");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartIsNull() {
            addCriterion("silence_wait_start is null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartIsNotNull() {
            addCriterion("silence_wait_start is not null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartEqualTo(Boolean value) {
            addCriterion("silence_wait_start =", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartNotEqualTo(Boolean value) {
            addCriterion("silence_wait_start <>", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartGreaterThan(Boolean value) {
            addCriterion("silence_wait_start >", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("silence_wait_start >=", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartLessThan(Boolean value) {
            addCriterion("silence_wait_start <", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartLessThanOrEqualTo(Boolean value) {
            addCriterion("silence_wait_start <=", value, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartIn(List<Boolean> values) {
            addCriterion("silence_wait_start in", values, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartNotIn(List<Boolean> values) {
            addCriterion("silence_wait_start not in", values, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartBetween(Boolean value1, Boolean value2) {
            addCriterion("silence_wait_start between", value1, value2, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("silence_wait_start not between", value1, value2, "silenceWaitStart");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsIsNull() {
            addCriterion("silence_wait_secs is null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsIsNotNull() {
            addCriterion("silence_wait_secs is not null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsEqualTo(Integer value) {
            addCriterion("silence_wait_secs =", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsNotEqualTo(Integer value) {
            addCriterion("silence_wait_secs <>", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsGreaterThan(Integer value) {
            addCriterion("silence_wait_secs >", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsGreaterThanOrEqualTo(Integer value) {
            addCriterion("silence_wait_secs >=", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsLessThan(Integer value) {
            addCriterion("silence_wait_secs <", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsLessThanOrEqualTo(Integer value) {
            addCriterion("silence_wait_secs <=", value, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsIn(List<Integer> values) {
            addCriterion("silence_wait_secs in", values, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsNotIn(List<Integer> values) {
            addCriterion("silence_wait_secs not in", values, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsBetween(Integer value1, Integer value2) {
            addCriterion("silence_wait_secs between", value1, value2, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitSecsNotBetween(Integer value1, Integer value2) {
            addCriterion("silence_wait_secs not between", value1, value2, "silenceWaitSecs");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeIsNull() {
            addCriterion("silence_wait_time is null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeIsNotNull() {
            addCriterion("silence_wait_time is not null");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeEqualTo(Integer value) {
            addCriterion("silence_wait_time =", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeNotEqualTo(Integer value) {
            addCriterion("silence_wait_time <>", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeGreaterThan(Integer value) {
            addCriterion("silence_wait_time >", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("silence_wait_time >=", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeLessThan(Integer value) {
            addCriterion("silence_wait_time <", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeLessThanOrEqualTo(Integer value) {
            addCriterion("silence_wait_time <=", value, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeIn(List<Integer> values) {
            addCriterion("silence_wait_time in", values, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeNotIn(List<Integer> values) {
            addCriterion("silence_wait_time not in", values, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeBetween(Integer value1, Integer value2) {
            addCriterion("silence_wait_time between", value1, value2, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceWaitTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("silence_wait_time not between", value1, value2, "silenceWaitTime");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyIsNull() {
            addCriterion("silence_as_empty is null");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyIsNotNull() {
            addCriterion("silence_as_empty is not null");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyEqualTo(Boolean value) {
            addCriterion("silence_as_empty =", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyNotEqualTo(Boolean value) {
            addCriterion("silence_as_empty <>", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyGreaterThan(Boolean value) {
            addCriterion("silence_as_empty >", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("silence_as_empty >=", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyLessThan(Boolean value) {
            addCriterion("silence_as_empty <", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyLessThanOrEqualTo(Boolean value) {
            addCriterion("silence_as_empty <=", value, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyIn(List<Boolean> values) {
            addCriterion("silence_as_empty in", values, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyNotIn(List<Boolean> values) {
            addCriterion("silence_as_empty not in", values, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyBetween(Boolean value1, Boolean value2) {
            addCriterion("silence_as_empty between", value1, value2, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andSilenceAsEmptyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("silence_as_empty not between", value1, value2, "silenceAsEmpty");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderIsNull() {
            addCriterion("user_define_match_order is null");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderIsNotNull() {
            addCriterion("user_define_match_order is not null");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderEqualTo(Boolean value) {
            addCriterion("user_define_match_order =", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderNotEqualTo(Boolean value) {
            addCriterion("user_define_match_order <>", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderGreaterThan(Boolean value) {
            addCriterion("user_define_match_order >", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderGreaterThanOrEqualTo(Boolean value) {
            addCriterion("user_define_match_order >=", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderLessThan(Boolean value) {
            addCriterion("user_define_match_order <", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderLessThanOrEqualTo(Boolean value) {
            addCriterion("user_define_match_order <=", value, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderIn(List<Boolean> values) {
            addCriterion("user_define_match_order in", values, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderNotIn(List<Boolean> values) {
            addCriterion("user_define_match_order not in", values, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderBetween(Boolean value1, Boolean value2) {
            addCriterion("user_define_match_order between", value1, value2, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andUserDefineMatchOrderNotBetween(Boolean value1, Boolean value2) {
            addCriterion("user_define_match_order not between", value1, value2, "userDefineMatchOrder");
            return (Criteria) this;
        }

        public Criteria andGrubStartIsNull() {
            addCriterion("grub_start is null");
            return (Criteria) this;
        }

        public Criteria andGrubStartIsNotNull() {
            addCriterion("grub_start is not null");
            return (Criteria) this;
        }

        public Criteria andGrubStartEqualTo(Boolean value) {
            addCriterion("grub_start =", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartNotEqualTo(Boolean value) {
            addCriterion("grub_start <>", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartGreaterThan(Boolean value) {
            addCriterion("grub_start >", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("grub_start >=", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartLessThan(Boolean value) {
            addCriterion("grub_start <", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartLessThanOrEqualTo(Boolean value) {
            addCriterion("grub_start <=", value, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartIn(List<Boolean> values) {
            addCriterion("grub_start in", values, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartNotIn(List<Boolean> values) {
            addCriterion("grub_start not in", values, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartBetween(Boolean value1, Boolean value2) {
            addCriterion("grub_start between", value1, value2, "grubStart");
            return (Criteria) this;
        }

        public Criteria andGrubStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("grub_start not between", value1, value2, "grubStart");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableIsNull() {
            addCriterion("positive_interruptable is null");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableIsNotNull() {
            addCriterion("positive_interruptable is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableEqualTo(Boolean value) {
            addCriterion("positive_interruptable =", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableNotEqualTo(Boolean value) {
            addCriterion("positive_interruptable <>", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableGreaterThan(Boolean value) {
            addCriterion("positive_interruptable >", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("positive_interruptable >=", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableLessThan(Boolean value) {
            addCriterion("positive_interruptable <", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableLessThanOrEqualTo(Boolean value) {
            addCriterion("positive_interruptable <=", value, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableIn(List<Boolean> values) {
            addCriterion("positive_interruptable in", values, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableNotIn(List<Boolean> values) {
            addCriterion("positive_interruptable not in", values, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableBetween(Boolean value1, Boolean value2) {
            addCriterion("positive_interruptable between", value1, value2, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andPositiveInterruptableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("positive_interruptable not between", value1, value2, "positiveInterruptable");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartIsNull() {
            addCriterion("interruptable_domain_start is null");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartIsNotNull() {
            addCriterion("interruptable_domain_start is not null");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartEqualTo(Boolean value) {
            addCriterion("interruptable_domain_start =", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartNotEqualTo(Boolean value) {
            addCriterion("interruptable_domain_start <>", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartGreaterThan(Boolean value) {
            addCriterion("interruptable_domain_start >", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("interruptable_domain_start >=", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartLessThan(Boolean value) {
            addCriterion("interruptable_domain_start <", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartLessThanOrEqualTo(Boolean value) {
            addCriterion("interruptable_domain_start <=", value, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartIn(List<Boolean> values) {
            addCriterion("interruptable_domain_start in", values, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartNotIn(List<Boolean> values) {
            addCriterion("interruptable_domain_start not in", values, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartBetween(Boolean value1, Boolean value2) {
            addCriterion("interruptable_domain_start between", value1, value2, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andInterruptableDomainStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("interruptable_domain_start not between", value1, value2, "interruptableDomainStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartIsNull() {
            addCriterion("global_interruptable_domains_start is null");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartIsNotNull() {
            addCriterion("global_interruptable_domains_start is not null");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartEqualTo(Boolean value) {
            addCriterion("global_interruptable_domains_start =", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartNotEqualTo(Boolean value) {
            addCriterion("global_interruptable_domains_start <>", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartGreaterThan(Boolean value) {
            addCriterion("global_interruptable_domains_start >", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("global_interruptable_domains_start >=", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartLessThan(Boolean value) {
            addCriterion("global_interruptable_domains_start <", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartLessThanOrEqualTo(Boolean value) {
            addCriterion("global_interruptable_domains_start <=", value, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartIn(List<Boolean> values) {
            addCriterion("global_interruptable_domains_start in", values, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartNotIn(List<Boolean> values) {
            addCriterion("global_interruptable_domains_start not in", values, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartBetween(Boolean value1, Boolean value2) {
            addCriterion("global_interruptable_domains_start between", value1, value2, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("global_interruptable_domains_start not between", value1, value2, "globalInterruptableDomainsStart");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsIsNull() {
            addCriterion("global_interruptable_domains is null");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsIsNotNull() {
            addCriterion("global_interruptable_domains is not null");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsEqualTo(String value) {
            addCriterion("global_interruptable_domains =", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsNotEqualTo(String value) {
            addCriterion("global_interruptable_domains <>", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsGreaterThan(String value) {
            addCriterion("global_interruptable_domains >", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsGreaterThanOrEqualTo(String value) {
            addCriterion("global_interruptable_domains >=", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsLessThan(String value) {
            addCriterion("global_interruptable_domains <", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsLessThanOrEqualTo(String value) {
            addCriterion("global_interruptable_domains <=", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsLike(String value) {
            addCriterion("global_interruptable_domains like", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsNotLike(String value) {
            addCriterion("global_interruptable_domains not like", value, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsIn(List<String> values) {
            addCriterion("global_interruptable_domains in", values, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsNotIn(List<String> values) {
            addCriterion("global_interruptable_domains not in", values, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsBetween(String value1, String value2) {
            addCriterion("global_interruptable_domains between", value1, value2, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andGlobalInterruptableDomainsNotBetween(String value1, String value2) {
            addCriterion("global_interruptable_domains not between", value1, value2, "globalInterruptableDomains");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartIsNull() {
            addCriterion("ignore_but_domains_start is null");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartIsNotNull() {
            addCriterion("ignore_but_domains_start is not null");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartEqualTo(Boolean value) {
            addCriterion("ignore_but_domains_start =", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartNotEqualTo(Boolean value) {
            addCriterion("ignore_but_domains_start <>", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartGreaterThan(Boolean value) {
            addCriterion("ignore_but_domains_start >", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ignore_but_domains_start >=", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartLessThan(Boolean value) {
            addCriterion("ignore_but_domains_start <", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartLessThanOrEqualTo(Boolean value) {
            addCriterion("ignore_but_domains_start <=", value, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartIn(List<Boolean> values) {
            addCriterion("ignore_but_domains_start in", values, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartNotIn(List<Boolean> values) {
            addCriterion("ignore_but_domains_start not in", values, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_but_domains_start between", value1, value2, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButDomainsStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_but_domains_start not between", value1, value2, "ignoreButDomainsStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartIsNull() {
            addCriterion("ignore_user_sentence_start is null");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartIsNotNull() {
            addCriterion("ignore_user_sentence_start is not null");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartEqualTo(Boolean value) {
            addCriterion("ignore_user_sentence_start =", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartNotEqualTo(Boolean value) {
            addCriterion("ignore_user_sentence_start <>", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartGreaterThan(Boolean value) {
            addCriterion("ignore_user_sentence_start >", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ignore_user_sentence_start >=", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartLessThan(Boolean value) {
            addCriterion("ignore_user_sentence_start <", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartLessThanOrEqualTo(Boolean value) {
            addCriterion("ignore_user_sentence_start <=", value, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartIn(List<Boolean> values) {
            addCriterion("ignore_user_sentence_start in", values, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartNotIn(List<Boolean> values) {
            addCriterion("ignore_user_sentence_start not in", values, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_user_sentence_start between", value1, value2, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreUserSentenceStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_user_sentence_start not between", value1, value2, "ignoreUserSentenceStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartIsNull() {
            addCriterion("ignore_but_negative_start is null");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartIsNotNull() {
            addCriterion("ignore_but_negative_start is not null");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartEqualTo(Boolean value) {
            addCriterion("ignore_but_negative_start =", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartNotEqualTo(Boolean value) {
            addCriterion("ignore_but_negative_start <>", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartGreaterThan(Boolean value) {
            addCriterion("ignore_but_negative_start >", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ignore_but_negative_start >=", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartLessThan(Boolean value) {
            addCriterion("ignore_but_negative_start <", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartLessThanOrEqualTo(Boolean value) {
            addCriterion("ignore_but_negative_start <=", value, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartIn(List<Boolean> values) {
            addCriterion("ignore_but_negative_start in", values, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartNotIn(List<Boolean> values) {
            addCriterion("ignore_but_negative_start not in", values, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_but_negative_start between", value1, value2, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andIgnoreButNegativeStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ignore_but_negative_start not between", value1, value2, "ignoreButNegativeStart");
            return (Criteria) this;
        }

        public Criteria andAsrEngineIsNull() {
            addCriterion("asr_engine is null");
            return (Criteria) this;
        }

        public Criteria andAsrEngineIsNotNull() {
            addCriterion("asr_engine is not null");
            return (Criteria) this;
        }

        public Criteria andAsrEngineEqualTo(String value) {
            addCriterion("asr_engine =", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineNotEqualTo(String value) {
            addCriterion("asr_engine <>", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineGreaterThan(String value) {
            addCriterion("asr_engine >", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineGreaterThanOrEqualTo(String value) {
            addCriterion("asr_engine >=", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineLessThan(String value) {
            addCriterion("asr_engine <", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineLessThanOrEqualTo(String value) {
            addCriterion("asr_engine <=", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineLike(String value) {
            addCriterion("asr_engine like", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineNotLike(String value) {
            addCriterion("asr_engine not like", value, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineIn(List<String> values) {
            addCriterion("asr_engine in", values, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineNotIn(List<String> values) {
            addCriterion("asr_engine not in", values, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineBetween(String value1, String value2) {
            addCriterion("asr_engine between", value1, value2, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andAsrEngineNotBetween(String value1, String value2) {
            addCriterion("asr_engine not between", value1, value2, "asrEngine");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllIsNull() {
            addCriterion("multi_keyword_all is null");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllIsNotNull() {
            addCriterion("multi_keyword_all is not null");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllEqualTo(Boolean value) {
            addCriterion("multi_keyword_all =", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllNotEqualTo(Boolean value) {
            addCriterion("multi_keyword_all <>", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllGreaterThan(Boolean value) {
            addCriterion("multi_keyword_all >", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllGreaterThanOrEqualTo(Boolean value) {
            addCriterion("multi_keyword_all >=", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllLessThan(Boolean value) {
            addCriterion("multi_keyword_all <", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllLessThanOrEqualTo(Boolean value) {
            addCriterion("multi_keyword_all <=", value, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllIn(List<Boolean> values) {
            addCriterion("multi_keyword_all in", values, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllNotIn(List<Boolean> values) {
            addCriterion("multi_keyword_all not in", values, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllBetween(Boolean value1, Boolean value2) {
            addCriterion("multi_keyword_all between", value1, value2, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andMultiKeywordAllNotBetween(Boolean value1, Boolean value2) {
            addCriterion("multi_keyword_all not between", value1, value2, "multiKeywordAll");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartIsNull() {
            addCriterion("not_match_less4_to_start is null");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartIsNotNull() {
            addCriterion("not_match_less4_to_start is not null");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartEqualTo(Boolean value) {
            addCriterion("not_match_less4_to_start =", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartNotEqualTo(Boolean value) {
            addCriterion("not_match_less4_to_start <>", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartGreaterThan(Boolean value) {
            addCriterion("not_match_less4_to_start >", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("not_match_less4_to_start >=", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartLessThan(Boolean value) {
            addCriterion("not_match_less4_to_start <", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartLessThanOrEqualTo(Boolean value) {
            addCriterion("not_match_less4_to_start <=", value, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartIn(List<Boolean> values) {
            addCriterion("not_match_less4_to_start in", values, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartNotIn(List<Boolean> values) {
            addCriterion("not_match_less4_to_start not in", values, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartBetween(Boolean value1, Boolean value2) {
            addCriterion("not_match_less4_to_start between", value1, value2, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLess4ToStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("not_match_less4_to_start not between", value1, value2, "notMatchLess4ToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumIsNull() {
            addCriterion("not_match_less_num is null");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumIsNotNull() {
            addCriterion("not_match_less_num is not null");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumEqualTo(Integer value) {
            addCriterion("not_match_less_num =", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumNotEqualTo(Integer value) {
            addCriterion("not_match_less_num <>", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumGreaterThan(Integer value) {
            addCriterion("not_match_less_num >", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("not_match_less_num >=", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumLessThan(Integer value) {
            addCriterion("not_match_less_num <", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumLessThanOrEqualTo(Integer value) {
            addCriterion("not_match_less_num <=", value, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumIn(List<Integer> values) {
            addCriterion("not_match_less_num in", values, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumNotIn(List<Integer> values) {
            addCriterion("not_match_less_num not in", values, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumBetween(Integer value1, Integer value2) {
            addCriterion("not_match_less_num between", value1, value2, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchLessNumNotBetween(Integer value1, Integer value2) {
            addCriterion("not_match_less_num not between", value1, value2, "notMatchLessNum");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartIsNull() {
            addCriterion("not_match_to_start is null");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartIsNotNull() {
            addCriterion("not_match_to_start is not null");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartEqualTo(Boolean value) {
            addCriterion("not_match_to_start =", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartNotEqualTo(Boolean value) {
            addCriterion("not_match_to_start <>", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartGreaterThan(Boolean value) {
            addCriterion("not_match_to_start >", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("not_match_to_start >=", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartLessThan(Boolean value) {
            addCriterion("not_match_to_start <", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartLessThanOrEqualTo(Boolean value) {
            addCriterion("not_match_to_start <=", value, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartIn(List<Boolean> values) {
            addCriterion("not_match_to_start in", values, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartNotIn(List<Boolean> values) {
            addCriterion("not_match_to_start not in", values, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartBetween(Boolean value1, Boolean value2) {
            addCriterion("not_match_to_start between", value1, value2, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNotMatchToStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("not_match_to_start not between", value1, value2, "notMatchToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartIsNull() {
            addCriterion("no_words_to_start is null");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartIsNotNull() {
            addCriterion("no_words_to_start is not null");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartEqualTo(Boolean value) {
            addCriterion("no_words_to_start =", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartNotEqualTo(Boolean value) {
            addCriterion("no_words_to_start <>", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartGreaterThan(Boolean value) {
            addCriterion("no_words_to_start >", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("no_words_to_start >=", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartLessThan(Boolean value) {
            addCriterion("no_words_to_start <", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartLessThanOrEqualTo(Boolean value) {
            addCriterion("no_words_to_start <=", value, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartIn(List<Boolean> values) {
            addCriterion("no_words_to_start in", values, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartNotIn(List<Boolean> values) {
            addCriterion("no_words_to_start not in", values, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartBetween(Boolean value1, Boolean value2) {
            addCriterion("no_words_to_start between", value1, value2, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andNoWordsToStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("no_words_to_start not between", value1, value2, "noWordsToStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartIsNull() {
            addCriterion("interruption_config_start is null");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartIsNotNull() {
            addCriterion("interruption_config_start is not null");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartEqualTo(Boolean value) {
            addCriterion("interruption_config_start =", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartNotEqualTo(Boolean value) {
            addCriterion("interruption_config_start <>", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartGreaterThan(Boolean value) {
            addCriterion("interruption_config_start >", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("interruption_config_start >=", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartLessThan(Boolean value) {
            addCriterion("interruption_config_start <", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartLessThanOrEqualTo(Boolean value) {
            addCriterion("interruption_config_start <=", value, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartIn(List<Boolean> values) {
            addCriterion("interruption_config_start in", values, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartNotIn(List<Boolean> values) {
            addCriterion("interruption_config_start not in", values, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartBetween(Boolean value1, Boolean value2) {
            addCriterion("interruption_config_start between", value1, value2, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptionConfigStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("interruption_config_start not between", value1, value2, "interruptionConfigStart");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumIsNull() {
            addCriterion("interrupt_words_num is null");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumIsNotNull() {
            addCriterion("interrupt_words_num is not null");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumEqualTo(Integer value) {
            addCriterion("interrupt_words_num =", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumNotEqualTo(Integer value) {
            addCriterion("interrupt_words_num <>", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumGreaterThan(Integer value) {
            addCriterion("interrupt_words_num >", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("interrupt_words_num >=", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumLessThan(Integer value) {
            addCriterion("interrupt_words_num <", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumLessThanOrEqualTo(Integer value) {
            addCriterion("interrupt_words_num <=", value, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumIn(List<Integer> values) {
            addCriterion("interrupt_words_num in", values, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumNotIn(List<Integer> values) {
            addCriterion("interrupt_words_num not in", values, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumBetween(Integer value1, Integer value2) {
            addCriterion("interrupt_words_num between", value1, value2, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptWordsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("interrupt_words_num not between", value1, value2, "interruptWordsNum");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalIsNull() {
            addCriterion("interrupt_min_interval is null");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalIsNotNull() {
            addCriterion("interrupt_min_interval is not null");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalEqualTo(Integer value) {
            addCriterion("interrupt_min_interval =", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalNotEqualTo(Integer value) {
            addCriterion("interrupt_min_interval <>", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalGreaterThan(Integer value) {
            addCriterion("interrupt_min_interval >", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalGreaterThanOrEqualTo(Integer value) {
            addCriterion("interrupt_min_interval >=", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalLessThan(Integer value) {
            addCriterion("interrupt_min_interval <", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalLessThanOrEqualTo(Integer value) {
            addCriterion("interrupt_min_interval <=", value, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalIn(List<Integer> values) {
            addCriterion("interrupt_min_interval in", values, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalNotIn(List<Integer> values) {
            addCriterion("interrupt_min_interval not in", values, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalBetween(Integer value1, Integer value2) {
            addCriterion("interrupt_min_interval between", value1, value2, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andInterruptMinIntervalNotBetween(Integer value1, Integer value2) {
            addCriterion("interrupt_min_interval not between", value1, value2, "interruptMinInterval");
            return (Criteria) this;
        }

        public Criteria andVoiceIsNull() {
            addCriterion("voice is null");
            return (Criteria) this;
        }

        public Criteria andVoiceIsNotNull() {
            addCriterion("voice is not null");
            return (Criteria) this;
        }

        public Criteria andVoiceEqualTo(String value) {
            addCriterion("voice =", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceNotEqualTo(String value) {
            addCriterion("voice <>", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceGreaterThan(String value) {
            addCriterion("voice >", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceGreaterThanOrEqualTo(String value) {
            addCriterion("voice >=", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceLessThan(String value) {
            addCriterion("voice <", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceLessThanOrEqualTo(String value) {
            addCriterion("voice <=", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceLike(String value) {
            addCriterion("voice like", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceNotLike(String value) {
            addCriterion("voice not like", value, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceIn(List<String> values) {
            addCriterion("voice in", values, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceNotIn(List<String> values) {
            addCriterion("voice not in", values, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceBetween(String value1, String value2) {
            addCriterion("voice between", value1, value2, "voice");
            return (Criteria) this;
        }

        public Criteria andVoiceNotBetween(String value1, String value2) {
            addCriterion("voice not between", value1, value2, "voice");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartIsNull() {
            addCriterion("special_limit_start is null");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartIsNotNull() {
            addCriterion("special_limit_start is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartEqualTo(Boolean value) {
            addCriterion("special_limit_start =", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartNotEqualTo(Boolean value) {
            addCriterion("special_limit_start <>", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartGreaterThan(Boolean value) {
            addCriterion("special_limit_start >", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("special_limit_start >=", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartLessThan(Boolean value) {
            addCriterion("special_limit_start <", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartLessThanOrEqualTo(Boolean value) {
            addCriterion("special_limit_start <=", value, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartIn(List<Boolean> values) {
            addCriterion("special_limit_start in", values, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartNotIn(List<Boolean> values) {
            addCriterion("special_limit_start not in", values, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartBetween(Boolean value1, Boolean value2) {
            addCriterion("special_limit_start between", value1, value2, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("special_limit_start not between", value1, value2, "specialLimitStart");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitIsNull() {
            addCriterion("special_limit is null");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitIsNotNull() {
            addCriterion("special_limit is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitEqualTo(Integer value) {
            addCriterion("special_limit =", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitNotEqualTo(Integer value) {
            addCriterion("special_limit <>", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitGreaterThan(Integer value) {
            addCriterion("special_limit >", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("special_limit >=", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitLessThan(Integer value) {
            addCriterion("special_limit <", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitLessThanOrEqualTo(Integer value) {
            addCriterion("special_limit <=", value, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitIn(List<Integer> values) {
            addCriterion("special_limit in", values, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitNotIn(List<Integer> values) {
            addCriterion("special_limit not in", values, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitBetween(Integer value1, Integer value2) {
            addCriterion("special_limit between", value1, value2, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSpecialLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("special_limit not between", value1, value2, "specialLimit");
            return (Criteria) this;
        }

        public Criteria andSurveyStartIsNull() {
            addCriterion("survey_start is null");
            return (Criteria) this;
        }

        public Criteria andSurveyStartIsNotNull() {
            addCriterion("survey_start is not null");
            return (Criteria) this;
        }

        public Criteria andSurveyStartEqualTo(Boolean value) {
            addCriterion("survey_start =", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartNotEqualTo(Boolean value) {
            addCriterion("survey_start <>", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartGreaterThan(Boolean value) {
            addCriterion("survey_start >", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartGreaterThanOrEqualTo(Boolean value) {
            addCriterion("survey_start >=", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartLessThan(Boolean value) {
            addCriterion("survey_start <", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartLessThanOrEqualTo(Boolean value) {
            addCriterion("survey_start <=", value, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartIn(List<Boolean> values) {
            addCriterion("survey_start in", values, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartNotIn(List<Boolean> values) {
            addCriterion("survey_start not in", values, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartBetween(Boolean value1, Boolean value2) {
            addCriterion("survey_start between", value1, value2, "surveyStart");
            return (Criteria) this;
        }

        public Criteria andSurveyStartNotBetween(Boolean value1, Boolean value2) {
            addCriterion("survey_start not between", value1, value2, "surveyStart");
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