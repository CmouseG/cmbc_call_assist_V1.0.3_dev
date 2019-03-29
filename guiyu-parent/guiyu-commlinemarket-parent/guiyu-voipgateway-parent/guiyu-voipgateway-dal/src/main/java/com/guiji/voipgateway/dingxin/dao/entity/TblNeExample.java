package com.guiji.voipgateway.dingxin.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblNeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TblNeExample() {
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

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(Integer value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(Integer value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(Integer value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(Integer value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(Integer value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<Integer> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<Integer> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(Integer value1, Integer value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andRecStatusIsNull() {
            addCriterion("rec_status is null");
            return (Criteria) this;
        }

        public Criteria andRecStatusIsNotNull() {
            addCriterion("rec_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecStatusEqualTo(Integer value) {
            addCriterion("rec_status =", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusNotEqualTo(Integer value) {
            addCriterion("rec_status <>", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusGreaterThan(Integer value) {
            addCriterion("rec_status >", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("rec_status >=", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusLessThan(Integer value) {
            addCriterion("rec_status <", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusLessThanOrEqualTo(Integer value) {
            addCriterion("rec_status <=", value, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusIn(List<Integer> values) {
            addCriterion("rec_status in", values, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusNotIn(List<Integer> values) {
            addCriterion("rec_status not in", values, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusBetween(Integer value1, Integer value2) {
            addCriterion("rec_status between", value1, value2, "recStatus");
            return (Criteria) this;
        }

        public Criteria andRecStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("rec_status not between", value1, value2, "recStatus");
            return (Criteria) this;
        }

        public Criteria andAliasIsNull() {
            addCriterion("`alias` is null");
            return (Criteria) this;
        }

        public Criteria andAliasIsNotNull() {
            addCriterion("`alias` is not null");
            return (Criteria) this;
        }

        public Criteria andAliasEqualTo(String value) {
            addCriterion("`alias` =", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotEqualTo(String value) {
            addCriterion("`alias` <>", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasGreaterThan(String value) {
            addCriterion("`alias` >", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasGreaterThanOrEqualTo(String value) {
            addCriterion("`alias` >=", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLessThan(String value) {
            addCriterion("`alias` <", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLessThanOrEqualTo(String value) {
            addCriterion("`alias` <=", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasLike(String value) {
            addCriterion("`alias` like", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotLike(String value) {
            addCriterion("`alias` not like", value, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasIn(List<String> values) {
            addCriterion("`alias` in", values, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotIn(List<String> values) {
            addCriterion("`alias` not in", values, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasBetween(String value1, String value2) {
            addCriterion("`alias` between", value1, value2, "alias");
            return (Criteria) this;
        }

        public Criteria andAliasNotBetween(String value1, String value2) {
            addCriterion("`alias` not between", value1, value2, "alias");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNull() {
            addCriterion("admin_status is null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIsNotNull() {
            addCriterion("admin_status is not null");
            return (Criteria) this;
        }

        public Criteria andAdminStatusEqualTo(Integer value) {
            addCriterion("admin_status =", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotEqualTo(Integer value) {
            addCriterion("admin_status <>", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThan(Integer value) {
            addCriterion("admin_status >", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("admin_status >=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThan(Integer value) {
            addCriterion("admin_status <", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusLessThanOrEqualTo(Integer value) {
            addCriterion("admin_status <=", value, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusIn(List<Integer> values) {
            addCriterion("admin_status in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotIn(List<Integer> values) {
            addCriterion("admin_status not in", values, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusBetween(Integer value1, Integer value2) {
            addCriterion("admin_status between", value1, value2, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andAdminStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("admin_status not between", value1, value2, "adminStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusIsNull() {
            addCriterion("opr_status is null");
            return (Criteria) this;
        }

        public Criteria andOprStatusIsNotNull() {
            addCriterion("opr_status is not null");
            return (Criteria) this;
        }

        public Criteria andOprStatusEqualTo(Integer value) {
            addCriterion("opr_status =", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusNotEqualTo(Integer value) {
            addCriterion("opr_status <>", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusGreaterThan(Integer value) {
            addCriterion("opr_status >", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("opr_status >=", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusLessThan(Integer value) {
            addCriterion("opr_status <", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusLessThanOrEqualTo(Integer value) {
            addCriterion("opr_status <=", value, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusIn(List<Integer> values) {
            addCriterion("opr_status in", values, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusNotIn(List<Integer> values) {
            addCriterion("opr_status not in", values, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusBetween(Integer value1, Integer value2) {
            addCriterion("opr_status between", value1, value2, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andOprStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("opr_status not between", value1, value2, "oprStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNull() {
            addCriterion("run_status is null");
            return (Criteria) this;
        }

        public Criteria andRunStatusIsNotNull() {
            addCriterion("run_status is not null");
            return (Criteria) this;
        }

        public Criteria andRunStatusEqualTo(Integer value) {
            addCriterion("run_status =", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotEqualTo(Integer value) {
            addCriterion("run_status <>", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThan(Integer value) {
            addCriterion("run_status >", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("run_status >=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThan(Integer value) {
            addCriterion("run_status <", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusLessThanOrEqualTo(Integer value) {
            addCriterion("run_status <=", value, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusIn(List<Integer> values) {
            addCriterion("run_status in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotIn(List<Integer> values) {
            addCriterion("run_status not in", values, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusBetween(Integer value1, Integer value2) {
            addCriterion("run_status between", value1, value2, "runStatus");
            return (Criteria) this;
        }

        public Criteria andRunStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("run_status not between", value1, value2, "runStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusIsNull() {
            addCriterion("action_status is null");
            return (Criteria) this;
        }

        public Criteria andActionStatusIsNotNull() {
            addCriterion("action_status is not null");
            return (Criteria) this;
        }

        public Criteria andActionStatusEqualTo(Integer value) {
            addCriterion("action_status =", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusNotEqualTo(Integer value) {
            addCriterion("action_status <>", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusGreaterThan(Integer value) {
            addCriterion("action_status >", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("action_status >=", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusLessThan(Integer value) {
            addCriterion("action_status <", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusLessThanOrEqualTo(Integer value) {
            addCriterion("action_status <=", value, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusIn(List<Integer> values) {
            addCriterion("action_status in", values, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusNotIn(List<Integer> values) {
            addCriterion("action_status not in", values, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusBetween(Integer value1, Integer value2) {
            addCriterion("action_status between", value1, value2, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("action_status not between", value1, value2, "actionStatus");
            return (Criteria) this;
        }

        public Criteria andActionResultIsNull() {
            addCriterion("action_result is null");
            return (Criteria) this;
        }

        public Criteria andActionResultIsNotNull() {
            addCriterion("action_result is not null");
            return (Criteria) this;
        }

        public Criteria andActionResultEqualTo(Integer value) {
            addCriterion("action_result =", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultNotEqualTo(Integer value) {
            addCriterion("action_result <>", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultGreaterThan(Integer value) {
            addCriterion("action_result >", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("action_result >=", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultLessThan(Integer value) {
            addCriterion("action_result <", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultLessThanOrEqualTo(Integer value) {
            addCriterion("action_result <=", value, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultIn(List<Integer> values) {
            addCriterion("action_result in", values, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultNotIn(List<Integer> values) {
            addCriterion("action_result not in", values, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultBetween(Integer value1, Integer value2) {
            addCriterion("action_result between", value1, value2, "actionResult");
            return (Criteria) this;
        }

        public Criteria andActionResultNotBetween(Integer value1, Integer value2) {
            addCriterion("action_result not between", value1, value2, "actionResult");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsIsNull() {
            addCriterion("alm_status_bits is null");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsIsNotNull() {
            addCriterion("alm_status_bits is not null");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsEqualTo(Integer value) {
            addCriterion("alm_status_bits =", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsNotEqualTo(Integer value) {
            addCriterion("alm_status_bits <>", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsGreaterThan(Integer value) {
            addCriterion("alm_status_bits >", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsGreaterThanOrEqualTo(Integer value) {
            addCriterion("alm_status_bits >=", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsLessThan(Integer value) {
            addCriterion("alm_status_bits <", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsLessThanOrEqualTo(Integer value) {
            addCriterion("alm_status_bits <=", value, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsIn(List<Integer> values) {
            addCriterion("alm_status_bits in", values, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsNotIn(List<Integer> values) {
            addCriterion("alm_status_bits not in", values, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsBetween(Integer value1, Integer value2) {
            addCriterion("alm_status_bits between", value1, value2, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andAlmStatusBitsNotBetween(Integer value1, Integer value2) {
            addCriterion("alm_status_bits not between", value1, value2, "almStatusBits");
            return (Criteria) this;
        }

        public Criteria andDomainUuidIsNull() {
            addCriterion("domain_uuid is null");
            return (Criteria) this;
        }

        public Criteria andDomainUuidIsNotNull() {
            addCriterion("domain_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andDomainUuidEqualTo(Integer value) {
            addCriterion("domain_uuid =", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidNotEqualTo(Integer value) {
            addCriterion("domain_uuid <>", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidGreaterThan(Integer value) {
            addCriterion("domain_uuid >", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("domain_uuid >=", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidLessThan(Integer value) {
            addCriterion("domain_uuid <", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidLessThanOrEqualTo(Integer value) {
            addCriterion("domain_uuid <=", value, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidIn(List<Integer> values) {
            addCriterion("domain_uuid in", values, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidNotIn(List<Integer> values) {
            addCriterion("domain_uuid not in", values, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidBetween(Integer value1, Integer value2) {
            addCriterion("domain_uuid between", value1, value2, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andDomainUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("domain_uuid not between", value1, value2, "domainUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidIsNull() {
            addCriterion("site_uuid is null");
            return (Criteria) this;
        }

        public Criteria andSiteUuidIsNotNull() {
            addCriterion("site_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andSiteUuidEqualTo(Integer value) {
            addCriterion("site_uuid =", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidNotEqualTo(Integer value) {
            addCriterion("site_uuid <>", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidGreaterThan(Integer value) {
            addCriterion("site_uuid >", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("site_uuid >=", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidLessThan(Integer value) {
            addCriterion("site_uuid <", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidLessThanOrEqualTo(Integer value) {
            addCriterion("site_uuid <=", value, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidIn(List<Integer> values) {
            addCriterion("site_uuid in", values, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidNotIn(List<Integer> values) {
            addCriterion("site_uuid not in", values, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidBetween(Integer value1, Integer value2) {
            addCriterion("site_uuid between", value1, value2, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andSiteUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("site_uuid not between", value1, value2, "siteUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidIsNull() {
            addCriterion("policy_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidIsNotNull() {
            addCriterion("policy_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidEqualTo(Integer value) {
            addCriterion("policy_uuid =", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidNotEqualTo(Integer value) {
            addCriterion("policy_uuid <>", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidGreaterThan(Integer value) {
            addCriterion("policy_uuid >", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("policy_uuid >=", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidLessThan(Integer value) {
            addCriterion("policy_uuid <", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidLessThanOrEqualTo(Integer value) {
            addCriterion("policy_uuid <=", value, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidIn(List<Integer> values) {
            addCriterion("policy_uuid in", values, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidNotIn(List<Integer> values) {
            addCriterion("policy_uuid not in", values, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidBetween(Integer value1, Integer value2) {
            addCriterion("policy_uuid between", value1, value2, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andPolicyUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("policy_uuid not between", value1, value2, "policyUuid");
            return (Criteria) this;
        }

        public Criteria andVendorIdIsNull() {
            addCriterion("vendor_id is null");
            return (Criteria) this;
        }

        public Criteria andVendorIdIsNotNull() {
            addCriterion("vendor_id is not null");
            return (Criteria) this;
        }

        public Criteria andVendorIdEqualTo(Integer value) {
            addCriterion("vendor_id =", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdNotEqualTo(Integer value) {
            addCriterion("vendor_id <>", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdGreaterThan(Integer value) {
            addCriterion("vendor_id >", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("vendor_id >=", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdLessThan(Integer value) {
            addCriterion("vendor_id <", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdLessThanOrEqualTo(Integer value) {
            addCriterion("vendor_id <=", value, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdIn(List<Integer> values) {
            addCriterion("vendor_id in", values, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdNotIn(List<Integer> values) {
            addCriterion("vendor_id not in", values, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdBetween(Integer value1, Integer value2) {
            addCriterion("vendor_id between", value1, value2, "vendorId");
            return (Criteria) this;
        }

        public Criteria andVendorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("vendor_id not between", value1, value2, "vendorId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Integer value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Integer value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Integer value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Integer value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Integer value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Integer> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Integer> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Integer value1, Integer value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Integer value1, Integer value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andPackageVersionIsNull() {
            addCriterion("package_version is null");
            return (Criteria) this;
        }

        public Criteria andPackageVersionIsNotNull() {
            addCriterion("package_version is not null");
            return (Criteria) this;
        }

        public Criteria andPackageVersionEqualTo(String value) {
            addCriterion("package_version =", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionNotEqualTo(String value) {
            addCriterion("package_version <>", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionGreaterThan(String value) {
            addCriterion("package_version >", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionGreaterThanOrEqualTo(String value) {
            addCriterion("package_version >=", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionLessThan(String value) {
            addCriterion("package_version <", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionLessThanOrEqualTo(String value) {
            addCriterion("package_version <=", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionLike(String value) {
            addCriterion("package_version like", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionNotLike(String value) {
            addCriterion("package_version not like", value, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionIn(List<String> values) {
            addCriterion("package_version in", values, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionNotIn(List<String> values) {
            addCriterion("package_version not in", values, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionBetween(String value1, String value2) {
            addCriterion("package_version between", value1, value2, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageVersionNotBetween(String value1, String value2) {
            addCriterion("package_version not between", value1, value2, "packageVersion");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeIsNull() {
            addCriterion("package_build_time is null");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeIsNotNull() {
            addCriterion("package_build_time is not null");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeEqualTo(Date value) {
            addCriterion("package_build_time =", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeNotEqualTo(Date value) {
            addCriterion("package_build_time <>", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeGreaterThan(Date value) {
            addCriterion("package_build_time >", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("package_build_time >=", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeLessThan(Date value) {
            addCriterion("package_build_time <", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeLessThanOrEqualTo(Date value) {
            addCriterion("package_build_time <=", value, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeIn(List<Date> values) {
            addCriterion("package_build_time in", values, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeNotIn(List<Date> values) {
            addCriterion("package_build_time not in", values, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeBetween(Date value1, Date value2) {
            addCriterion("package_build_time between", value1, value2, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andPackageBuildTimeNotBetween(Date value1, Date value2) {
            addCriterion("package_build_time not between", value1, value2, "packageBuildTime");
            return (Criteria) this;
        }

        public Criteria andDetailVerIsNull() {
            addCriterion("detail_ver is null");
            return (Criteria) this;
        }

        public Criteria andDetailVerIsNotNull() {
            addCriterion("detail_ver is not null");
            return (Criteria) this;
        }

        public Criteria andDetailVerEqualTo(String value) {
            addCriterion("detail_ver =", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerNotEqualTo(String value) {
            addCriterion("detail_ver <>", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerGreaterThan(String value) {
            addCriterion("detail_ver >", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerGreaterThanOrEqualTo(String value) {
            addCriterion("detail_ver >=", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerLessThan(String value) {
            addCriterion("detail_ver <", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerLessThanOrEqualTo(String value) {
            addCriterion("detail_ver <=", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerLike(String value) {
            addCriterion("detail_ver like", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerNotLike(String value) {
            addCriterion("detail_ver not like", value, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerIn(List<String> values) {
            addCriterion("detail_ver in", values, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerNotIn(List<String> values) {
            addCriterion("detail_ver not in", values, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerBetween(String value1, String value2) {
            addCriterion("detail_ver between", value1, value2, "detailVer");
            return (Criteria) this;
        }

        public Criteria andDetailVerNotBetween(String value1, String value2) {
            addCriterion("detail_ver not between", value1, value2, "detailVer");
            return (Criteria) this;
        }

        public Criteria andSipAgentIsNull() {
            addCriterion("sip_agent is null");
            return (Criteria) this;
        }

        public Criteria andSipAgentIsNotNull() {
            addCriterion("sip_agent is not null");
            return (Criteria) this;
        }

        public Criteria andSipAgentEqualTo(String value) {
            addCriterion("sip_agent =", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentNotEqualTo(String value) {
            addCriterion("sip_agent <>", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentGreaterThan(String value) {
            addCriterion("sip_agent >", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentGreaterThanOrEqualTo(String value) {
            addCriterion("sip_agent >=", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentLessThan(String value) {
            addCriterion("sip_agent <", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentLessThanOrEqualTo(String value) {
            addCriterion("sip_agent <=", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentLike(String value) {
            addCriterion("sip_agent like", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentNotLike(String value) {
            addCriterion("sip_agent not like", value, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentIn(List<String> values) {
            addCriterion("sip_agent in", values, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentNotIn(List<String> values) {
            addCriterion("sip_agent not in", values, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentBetween(String value1, String value2) {
            addCriterion("sip_agent between", value1, value2, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipAgentNotBetween(String value1, String value2) {
            addCriterion("sip_agent not between", value1, value2, "sipAgent");
            return (Criteria) this;
        }

        public Criteria andSipOwnerIsNull() {
            addCriterion("sip_owner is null");
            return (Criteria) this;
        }

        public Criteria andSipOwnerIsNotNull() {
            addCriterion("sip_owner is not null");
            return (Criteria) this;
        }

        public Criteria andSipOwnerEqualTo(String value) {
            addCriterion("sip_owner =", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerNotEqualTo(String value) {
            addCriterion("sip_owner <>", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerGreaterThan(String value) {
            addCriterion("sip_owner >", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("sip_owner >=", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerLessThan(String value) {
            addCriterion("sip_owner <", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerLessThanOrEqualTo(String value) {
            addCriterion("sip_owner <=", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerLike(String value) {
            addCriterion("sip_owner like", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerNotLike(String value) {
            addCriterion("sip_owner not like", value, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerIn(List<String> values) {
            addCriterion("sip_owner in", values, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerNotIn(List<String> values) {
            addCriterion("sip_owner not in", values, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerBetween(String value1, String value2) {
            addCriterion("sip_owner between", value1, value2, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andSipOwnerNotBetween(String value1, String value2) {
            addCriterion("sip_owner not between", value1, value2, "sipOwner");
            return (Criteria) this;
        }

        public Criteria andCliPromptIsNull() {
            addCriterion("cli_prompt is null");
            return (Criteria) this;
        }

        public Criteria andCliPromptIsNotNull() {
            addCriterion("cli_prompt is not null");
            return (Criteria) this;
        }

        public Criteria andCliPromptEqualTo(String value) {
            addCriterion("cli_prompt =", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptNotEqualTo(String value) {
            addCriterion("cli_prompt <>", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptGreaterThan(String value) {
            addCriterion("cli_prompt >", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptGreaterThanOrEqualTo(String value) {
            addCriterion("cli_prompt >=", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptLessThan(String value) {
            addCriterion("cli_prompt <", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptLessThanOrEqualTo(String value) {
            addCriterion("cli_prompt <=", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptLike(String value) {
            addCriterion("cli_prompt like", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptNotLike(String value) {
            addCriterion("cli_prompt not like", value, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptIn(List<String> values) {
            addCriterion("cli_prompt in", values, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptNotIn(List<String> values) {
            addCriterion("cli_prompt not in", values, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptBetween(String value1, String value2) {
            addCriterion("cli_prompt between", value1, value2, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andCliPromptNotBetween(String value1, String value2) {
            addCriterion("cli_prompt not between", value1, value2, "cliPrompt");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultIsNull() {
            addCriterion("dhcp_default is null");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultIsNotNull() {
            addCriterion("dhcp_default is not null");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultEqualTo(Integer value) {
            addCriterion("dhcp_default =", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultNotEqualTo(Integer value) {
            addCriterion("dhcp_default <>", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultGreaterThan(Integer value) {
            addCriterion("dhcp_default >", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultGreaterThanOrEqualTo(Integer value) {
            addCriterion("dhcp_default >=", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultLessThan(Integer value) {
            addCriterion("dhcp_default <", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultLessThanOrEqualTo(Integer value) {
            addCriterion("dhcp_default <=", value, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultIn(List<Integer> values) {
            addCriterion("dhcp_default in", values, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultNotIn(List<Integer> values) {
            addCriterion("dhcp_default not in", values, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultBetween(Integer value1, Integer value2) {
            addCriterion("dhcp_default between", value1, value2, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andDhcpDefaultNotBetween(Integer value1, Integer value2) {
            addCriterion("dhcp_default not between", value1, value2, "dhcpDefault");
            return (Criteria) this;
        }

        public Criteria andIpTypeIsNull() {
            addCriterion("ip_type is null");
            return (Criteria) this;
        }

        public Criteria andIpTypeIsNotNull() {
            addCriterion("ip_type is not null");
            return (Criteria) this;
        }

        public Criteria andIpTypeEqualTo(Integer value) {
            addCriterion("ip_type =", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeNotEqualTo(Integer value) {
            addCriterion("ip_type <>", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeGreaterThan(Integer value) {
            addCriterion("ip_type >", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_type >=", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeLessThan(Integer value) {
            addCriterion("ip_type <", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ip_type <=", value, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeIn(List<Integer> values) {
            addCriterion("ip_type in", values, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeNotIn(List<Integer> values) {
            addCriterion("ip_type not in", values, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeBetween(Integer value1, Integer value2) {
            addCriterion("ip_type between", value1, value2, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_type not between", value1, value2, "ipType");
            return (Criteria) this;
        }

        public Criteria andIpAddrIsNull() {
            addCriterion("ip_addr is null");
            return (Criteria) this;
        }

        public Criteria andIpAddrIsNotNull() {
            addCriterion("ip_addr is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddrEqualTo(String value) {
            addCriterion("ip_addr =", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrNotEqualTo(String value) {
            addCriterion("ip_addr <>", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrGreaterThan(String value) {
            addCriterion("ip_addr >", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrGreaterThanOrEqualTo(String value) {
            addCriterion("ip_addr >=", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrLessThan(String value) {
            addCriterion("ip_addr <", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrLessThanOrEqualTo(String value) {
            addCriterion("ip_addr <=", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrLike(String value) {
            addCriterion("ip_addr like", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrNotLike(String value) {
            addCriterion("ip_addr not like", value, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrIn(List<String> values) {
            addCriterion("ip_addr in", values, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrNotIn(List<String> values) {
            addCriterion("ip_addr not in", values, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrBetween(String value1, String value2) {
            addCriterion("ip_addr between", value1, value2, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andIpAddrNotBetween(String value1, String value2) {
            addCriterion("ip_addr not between", value1, value2, "ipAddr");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumIsNull() {
            addCriterion("mac_addr_num is null");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumIsNotNull() {
            addCriterion("mac_addr_num is not null");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumEqualTo(Integer value) {
            addCriterion("mac_addr_num =", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumNotEqualTo(Integer value) {
            addCriterion("mac_addr_num <>", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumGreaterThan(Integer value) {
            addCriterion("mac_addr_num >", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("mac_addr_num >=", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumLessThan(Integer value) {
            addCriterion("mac_addr_num <", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumLessThanOrEqualTo(Integer value) {
            addCriterion("mac_addr_num <=", value, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumIn(List<Integer> values) {
            addCriterion("mac_addr_num in", values, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumNotIn(List<Integer> values) {
            addCriterion("mac_addr_num not in", values, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumBetween(Integer value1, Integer value2) {
            addCriterion("mac_addr_num between", value1, value2, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMacAddrNumNotBetween(Integer value1, Integer value2) {
            addCriterion("mac_addr_num not between", value1, value2, "macAddrNum");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryIsNull() {
            addCriterion("made_factory is null");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryIsNotNull() {
            addCriterion("made_factory is not null");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryEqualTo(String value) {
            addCriterion("made_factory =", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryNotEqualTo(String value) {
            addCriterion("made_factory <>", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryGreaterThan(String value) {
            addCriterion("made_factory >", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryGreaterThanOrEqualTo(String value) {
            addCriterion("made_factory >=", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryLessThan(String value) {
            addCriterion("made_factory <", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryLessThanOrEqualTo(String value) {
            addCriterion("made_factory <=", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryLike(String value) {
            addCriterion("made_factory like", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryNotLike(String value) {
            addCriterion("made_factory not like", value, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryIn(List<String> values) {
            addCriterion("made_factory in", values, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryNotIn(List<String> values) {
            addCriterion("made_factory not in", values, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryBetween(String value1, String value2) {
            addCriterion("made_factory between", value1, value2, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeFactoryNotBetween(String value1, String value2) {
            addCriterion("made_factory not between", value1, value2, "madeFactory");
            return (Criteria) this;
        }

        public Criteria andMadeSiteIsNull() {
            addCriterion("made_site is null");
            return (Criteria) this;
        }

        public Criteria andMadeSiteIsNotNull() {
            addCriterion("made_site is not null");
            return (Criteria) this;
        }

        public Criteria andMadeSiteEqualTo(String value) {
            addCriterion("made_site =", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteNotEqualTo(String value) {
            addCriterion("made_site <>", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteGreaterThan(String value) {
            addCriterion("made_site >", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteGreaterThanOrEqualTo(String value) {
            addCriterion("made_site >=", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteLessThan(String value) {
            addCriterion("made_site <", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteLessThanOrEqualTo(String value) {
            addCriterion("made_site <=", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteLike(String value) {
            addCriterion("made_site like", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteNotLike(String value) {
            addCriterion("made_site not like", value, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteIn(List<String> values) {
            addCriterion("made_site in", values, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteNotIn(List<String> values) {
            addCriterion("made_site not in", values, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteBetween(String value1, String value2) {
            addCriterion("made_site between", value1, value2, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeSiteNotBetween(String value1, String value2) {
            addCriterion("made_site not between", value1, value2, "madeSite");
            return (Criteria) this;
        }

        public Criteria andMadeDateIsNull() {
            addCriterion("made_date is null");
            return (Criteria) this;
        }

        public Criteria andMadeDateIsNotNull() {
            addCriterion("made_date is not null");
            return (Criteria) this;
        }

        public Criteria andMadeDateEqualTo(String value) {
            addCriterion("made_date =", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateNotEqualTo(String value) {
            addCriterion("made_date <>", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateGreaterThan(String value) {
            addCriterion("made_date >", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateGreaterThanOrEqualTo(String value) {
            addCriterion("made_date >=", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateLessThan(String value) {
            addCriterion("made_date <", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateLessThanOrEqualTo(String value) {
            addCriterion("made_date <=", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateLike(String value) {
            addCriterion("made_date like", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateNotLike(String value) {
            addCriterion("made_date not like", value, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateIn(List<String> values) {
            addCriterion("made_date in", values, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateNotIn(List<String> values) {
            addCriterion("made_date not in", values, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateBetween(String value1, String value2) {
            addCriterion("made_date between", value1, value2, "madeDate");
            return (Criteria) this;
        }

        public Criteria andMadeDateNotBetween(String value1, String value2) {
            addCriterion("made_date not between", value1, value2, "madeDate");
            return (Criteria) this;
        }

        public Criteria andTestSiteIsNull() {
            addCriterion("test_site is null");
            return (Criteria) this;
        }

        public Criteria andTestSiteIsNotNull() {
            addCriterion("test_site is not null");
            return (Criteria) this;
        }

        public Criteria andTestSiteEqualTo(String value) {
            addCriterion("test_site =", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteNotEqualTo(String value) {
            addCriterion("test_site <>", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteGreaterThan(String value) {
            addCriterion("test_site >", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteGreaterThanOrEqualTo(String value) {
            addCriterion("test_site >=", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteLessThan(String value) {
            addCriterion("test_site <", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteLessThanOrEqualTo(String value) {
            addCriterion("test_site <=", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteLike(String value) {
            addCriterion("test_site like", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteNotLike(String value) {
            addCriterion("test_site not like", value, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteIn(List<String> values) {
            addCriterion("test_site in", values, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteNotIn(List<String> values) {
            addCriterion("test_site not in", values, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteBetween(String value1, String value2) {
            addCriterion("test_site between", value1, value2, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestSiteNotBetween(String value1, String value2) {
            addCriterion("test_site not between", value1, value2, "testSite");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNull() {
            addCriterion("test_date is null");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNotNull() {
            addCriterion("test_date is not null");
            return (Criteria) this;
        }

        public Criteria andTestDateEqualTo(String value) {
            addCriterion("test_date =", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotEqualTo(String value) {
            addCriterion("test_date <>", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThan(String value) {
            addCriterion("test_date >", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThanOrEqualTo(String value) {
            addCriterion("test_date >=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThan(String value) {
            addCriterion("test_date <", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThanOrEqualTo(String value) {
            addCriterion("test_date <=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLike(String value) {
            addCriterion("test_date like", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotLike(String value) {
            addCriterion("test_date not like", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateIn(List<String> values) {
            addCriterion("test_date in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotIn(List<String> values) {
            addCriterion("test_date not in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateBetween(String value1, String value2) {
            addCriterion("test_date between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotBetween(String value1, String value2) {
            addCriterion("test_date not between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("`password` is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("`password` is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("`password` =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("`password` <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("`password` >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("`password` >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("`password` <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("`password` <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("`password` like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("`password` not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("`password` in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("`password` not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("`password` between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("`password` not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeIsNull() {
            addCriterion("encrypt_type is null");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeIsNotNull() {
            addCriterion("encrypt_type is not null");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeEqualTo(Integer value) {
            addCriterion("encrypt_type =", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeNotEqualTo(Integer value) {
            addCriterion("encrypt_type <>", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeGreaterThan(Integer value) {
            addCriterion("encrypt_type >", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("encrypt_type >=", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeLessThan(Integer value) {
            addCriterion("encrypt_type <", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeLessThanOrEqualTo(Integer value) {
            addCriterion("encrypt_type <=", value, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeIn(List<Integer> values) {
            addCriterion("encrypt_type in", values, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeNotIn(List<Integer> values) {
            addCriterion("encrypt_type not in", values, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeBetween(Integer value1, Integer value2) {
            addCriterion("encrypt_type between", value1, value2, "encryptType");
            return (Criteria) this;
        }

        public Criteria andEncryptTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("encrypt_type not between", value1, value2, "encryptType");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrIsNull() {
            addCriterion("outer_ip_addr is null");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrIsNotNull() {
            addCriterion("outer_ip_addr is not null");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrEqualTo(String value) {
            addCriterion("outer_ip_addr =", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrNotEqualTo(String value) {
            addCriterion("outer_ip_addr <>", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrGreaterThan(String value) {
            addCriterion("outer_ip_addr >", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrGreaterThanOrEqualTo(String value) {
            addCriterion("outer_ip_addr >=", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrLessThan(String value) {
            addCriterion("outer_ip_addr <", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrLessThanOrEqualTo(String value) {
            addCriterion("outer_ip_addr <=", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrLike(String value) {
            addCriterion("outer_ip_addr like", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrNotLike(String value) {
            addCriterion("outer_ip_addr not like", value, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrIn(List<String> values) {
            addCriterion("outer_ip_addr in", values, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrNotIn(List<String> values) {
            addCriterion("outer_ip_addr not in", values, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrBetween(String value1, String value2) {
            addCriterion("outer_ip_addr between", value1, value2, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andOuterIpAddrNotBetween(String value1, String value2) {
            addCriterion("outer_ip_addr not between", value1, value2, "outerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrIsNull() {
            addCriterion("inner_ip_addr is null");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrIsNotNull() {
            addCriterion("inner_ip_addr is not null");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrEqualTo(String value) {
            addCriterion("inner_ip_addr =", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrNotEqualTo(String value) {
            addCriterion("inner_ip_addr <>", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrGreaterThan(String value) {
            addCriterion("inner_ip_addr >", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrGreaterThanOrEqualTo(String value) {
            addCriterion("inner_ip_addr >=", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrLessThan(String value) {
            addCriterion("inner_ip_addr <", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrLessThanOrEqualTo(String value) {
            addCriterion("inner_ip_addr <=", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrLike(String value) {
            addCriterion("inner_ip_addr like", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrNotLike(String value) {
            addCriterion("inner_ip_addr not like", value, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrIn(List<String> values) {
            addCriterion("inner_ip_addr in", values, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrNotIn(List<String> values) {
            addCriterion("inner_ip_addr not in", values, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrBetween(String value1, String value2) {
            addCriterion("inner_ip_addr between", value1, value2, "innerIpAddr");
            return (Criteria) this;
        }

        public Criteria andInnerIpAddrNotBetween(String value1, String value2) {
            addCriterion("inner_ip_addr not between", value1, value2, "innerIpAddr");
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

        public Criteria andLastRegTimeIsNull() {
            addCriterion("last_reg_time is null");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeIsNotNull() {
            addCriterion("last_reg_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeEqualTo(Date value) {
            addCriterion("last_reg_time =", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeNotEqualTo(Date value) {
            addCriterion("last_reg_time <>", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeGreaterThan(Date value) {
            addCriterion("last_reg_time >", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_reg_time >=", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeLessThan(Date value) {
            addCriterion("last_reg_time <", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_reg_time <=", value, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeIn(List<Date> values) {
            addCriterion("last_reg_time in", values, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeNotIn(List<Date> values) {
            addCriterion("last_reg_time not in", values, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeBetween(Date value1, Date value2) {
            addCriterion("last_reg_time between", value1, value2, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andLastRegTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_reg_time not between", value1, value2, "lastRegTime");
            return (Criteria) this;
        }

        public Criteria andRegFailCountIsNull() {
            addCriterion("reg_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andRegFailCountIsNotNull() {
            addCriterion("reg_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andRegFailCountEqualTo(Integer value) {
            addCriterion("reg_fail_count =", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountNotEqualTo(Integer value) {
            addCriterion("reg_fail_count <>", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountGreaterThan(Integer value) {
            addCriterion("reg_fail_count >", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_fail_count >=", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountLessThan(Integer value) {
            addCriterion("reg_fail_count <", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("reg_fail_count <=", value, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountIn(List<Integer> values) {
            addCriterion("reg_fail_count in", values, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountNotIn(List<Integer> values) {
            addCriterion("reg_fail_count not in", values, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountBetween(Integer value1, Integer value2) {
            addCriterion("reg_fail_count between", value1, value2, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andRegFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("reg_fail_count not between", value1, value2, "regFailCount");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIsNull() {
            addCriterion("upgrade_type is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIsNotNull() {
            addCriterion("upgrade_type is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeEqualTo(Integer value) {
            addCriterion("upgrade_type =", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotEqualTo(Integer value) {
            addCriterion("upgrade_type <>", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeGreaterThan(Integer value) {
            addCriterion("upgrade_type >", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_type >=", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeLessThan(Integer value) {
            addCriterion("upgrade_type <", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_type <=", value, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeIn(List<Integer> values) {
            addCriterion("upgrade_type in", values, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotIn(List<Integer> values) {
            addCriterion("upgrade_type not in", values, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_type between", value1, value2, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_type not between", value1, value2, "upgradeType");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagIsNull() {
            addCriterion("upgrade_force_flag is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagIsNotNull() {
            addCriterion("upgrade_force_flag is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagEqualTo(Integer value) {
            addCriterion("upgrade_force_flag =", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagNotEqualTo(Integer value) {
            addCriterion("upgrade_force_flag <>", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagGreaterThan(Integer value) {
            addCriterion("upgrade_force_flag >", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_force_flag >=", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagLessThan(Integer value) {
            addCriterion("upgrade_force_flag <", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_force_flag <=", value, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagIn(List<Integer> values) {
            addCriterion("upgrade_force_flag in", values, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagNotIn(List<Integer> values) {
            addCriterion("upgrade_force_flag not in", values, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_force_flag between", value1, value2, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeForceFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_force_flag not between", value1, value2, "upgradeForceFlag");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerIsNull() {
            addCriterion("target_software_ver is null");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerIsNotNull() {
            addCriterion("target_software_ver is not null");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerEqualTo(String value) {
            addCriterion("target_software_ver =", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerNotEqualTo(String value) {
            addCriterion("target_software_ver <>", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerGreaterThan(String value) {
            addCriterion("target_software_ver >", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerGreaterThanOrEqualTo(String value) {
            addCriterion("target_software_ver >=", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerLessThan(String value) {
            addCriterion("target_software_ver <", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerLessThanOrEqualTo(String value) {
            addCriterion("target_software_ver <=", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerLike(String value) {
            addCriterion("target_software_ver like", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerNotLike(String value) {
            addCriterion("target_software_ver not like", value, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerIn(List<String> values) {
            addCriterion("target_software_ver in", values, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerNotIn(List<String> values) {
            addCriterion("target_software_ver not in", values, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerBetween(String value1, String value2) {
            addCriterion("target_software_ver between", value1, value2, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andTargetSoftwareVerNotBetween(String value1, String value2) {
            addCriterion("target_software_ver not between", value1, value2, "targetSoftwareVer");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIsNull() {
            addCriterion("upgrade_status is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIsNotNull() {
            addCriterion("upgrade_status is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusEqualTo(Integer value) {
            addCriterion("upgrade_status =", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotEqualTo(Integer value) {
            addCriterion("upgrade_status <>", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusGreaterThan(Integer value) {
            addCriterion("upgrade_status >", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_status >=", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusLessThan(Integer value) {
            addCriterion("upgrade_status <", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_status <=", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIn(List<Integer> values) {
            addCriterion("upgrade_status in", values, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotIn(List<Integer> values) {
            addCriterion("upgrade_status not in", values, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_status between", value1, value2, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_status not between", value1, value2, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultIsNull() {
            addCriterion("last_upgrade_result is null");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultIsNotNull() {
            addCriterion("last_upgrade_result is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultEqualTo(Integer value) {
            addCriterion("last_upgrade_result =", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultNotEqualTo(Integer value) {
            addCriterion("last_upgrade_result <>", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultGreaterThan(Integer value) {
            addCriterion("last_upgrade_result >", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_upgrade_result >=", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultLessThan(Integer value) {
            addCriterion("last_upgrade_result <", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultLessThanOrEqualTo(Integer value) {
            addCriterion("last_upgrade_result <=", value, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultIn(List<Integer> values) {
            addCriterion("last_upgrade_result in", values, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultNotIn(List<Integer> values) {
            addCriterion("last_upgrade_result not in", values, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultBetween(Integer value1, Integer value2) {
            addCriterion("last_upgrade_result between", value1, value2, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeResultNotBetween(Integer value1, Integer value2) {
            addCriterion("last_upgrade_result not between", value1, value2, "lastUpgradeResult");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeIsNull() {
            addCriterion("last_upgrade_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeIsNotNull() {
            addCriterion("last_upgrade_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeEqualTo(Date value) {
            addCriterion("last_upgrade_time =", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeNotEqualTo(Date value) {
            addCriterion("last_upgrade_time <>", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeGreaterThan(Date value) {
            addCriterion("last_upgrade_time >", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_upgrade_time >=", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeLessThan(Date value) {
            addCriterion("last_upgrade_time <", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_upgrade_time <=", value, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeIn(List<Date> values) {
            addCriterion("last_upgrade_time in", values, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeNotIn(List<Date> values) {
            addCriterion("last_upgrade_time not in", values, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeBetween(Date value1, Date value2) {
            addCriterion("last_upgrade_time between", value1, value2, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andLastUpgradeTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_upgrade_time not between", value1, value2, "lastUpgradeTime");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountIsNull() {
            addCriterion("port_total_count is null");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountIsNotNull() {
            addCriterion("port_total_count is not null");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountEqualTo(Integer value) {
            addCriterion("port_total_count =", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountNotEqualTo(Integer value) {
            addCriterion("port_total_count <>", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountGreaterThan(Integer value) {
            addCriterion("port_total_count >", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_total_count >=", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountLessThan(Integer value) {
            addCriterion("port_total_count <", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountLessThanOrEqualTo(Integer value) {
            addCriterion("port_total_count <=", value, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountIn(List<Integer> values) {
            addCriterion("port_total_count in", values, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountNotIn(List<Integer> values) {
            addCriterion("port_total_count not in", values, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountBetween(Integer value1, Integer value2) {
            addCriterion("port_total_count between", value1, value2, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortTotalCountNotBetween(Integer value1, Integer value2) {
            addCriterion("port_total_count not between", value1, value2, "portTotalCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountIsNull() {
            addCriterion("port_work_count is null");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountIsNotNull() {
            addCriterion("port_work_count is not null");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountEqualTo(Integer value) {
            addCriterion("port_work_count =", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountNotEqualTo(Integer value) {
            addCriterion("port_work_count <>", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountGreaterThan(Integer value) {
            addCriterion("port_work_count >", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_work_count >=", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountLessThan(Integer value) {
            addCriterion("port_work_count <", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountLessThanOrEqualTo(Integer value) {
            addCriterion("port_work_count <=", value, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountIn(List<Integer> values) {
            addCriterion("port_work_count in", values, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountNotIn(List<Integer> values) {
            addCriterion("port_work_count not in", values, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountBetween(Integer value1, Integer value2) {
            addCriterion("port_work_count between", value1, value2, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andPortWorkCountNotBetween(Integer value1, Integer value2) {
            addCriterion("port_work_count not between", value1, value2, "portWorkCount");
            return (Criteria) this;
        }

        public Criteria andDetailDescIsNull() {
            addCriterion("detail_desc is null");
            return (Criteria) this;
        }

        public Criteria andDetailDescIsNotNull() {
            addCriterion("detail_desc is not null");
            return (Criteria) this;
        }

        public Criteria andDetailDescEqualTo(String value) {
            addCriterion("detail_desc =", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescNotEqualTo(String value) {
            addCriterion("detail_desc <>", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescGreaterThan(String value) {
            addCriterion("detail_desc >", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescGreaterThanOrEqualTo(String value) {
            addCriterion("detail_desc >=", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescLessThan(String value) {
            addCriterion("detail_desc <", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescLessThanOrEqualTo(String value) {
            addCriterion("detail_desc <=", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescLike(String value) {
            addCriterion("detail_desc like", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescNotLike(String value) {
            addCriterion("detail_desc not like", value, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescIn(List<String> values) {
            addCriterion("detail_desc in", values, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescNotIn(List<String> values) {
            addCriterion("detail_desc not in", values, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescBetween(String value1, String value2) {
            addCriterion("detail_desc between", value1, value2, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andDetailDescNotBetween(String value1, String value2) {
            addCriterion("detail_desc not between", value1, value2, "detailDesc");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnIsNull() {
            addCriterion("next_ne_alarm_sn is null");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnIsNotNull() {
            addCriterion("next_ne_alarm_sn is not null");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnEqualTo(Integer value) {
            addCriterion("next_ne_alarm_sn =", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnNotEqualTo(Integer value) {
            addCriterion("next_ne_alarm_sn <>", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnGreaterThan(Integer value) {
            addCriterion("next_ne_alarm_sn >", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnGreaterThanOrEqualTo(Integer value) {
            addCriterion("next_ne_alarm_sn >=", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnLessThan(Integer value) {
            addCriterion("next_ne_alarm_sn <", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnLessThanOrEqualTo(Integer value) {
            addCriterion("next_ne_alarm_sn <=", value, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnIn(List<Integer> values) {
            addCriterion("next_ne_alarm_sn in", values, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnNotIn(List<Integer> values) {
            addCriterion("next_ne_alarm_sn not in", values, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnBetween(Integer value1, Integer value2) {
            addCriterion("next_ne_alarm_sn between", value1, value2, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andNextNeAlarmSnNotBetween(Integer value1, Integer value2) {
            addCriterion("next_ne_alarm_sn not between", value1, value2, "nextNeAlarmSn");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusIsNull() {
            addCriterion("syslog_status is null");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusIsNotNull() {
            addCriterion("syslog_status is not null");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusEqualTo(Integer value) {
            addCriterion("syslog_status =", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusNotEqualTo(Integer value) {
            addCriterion("syslog_status <>", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusGreaterThan(Integer value) {
            addCriterion("syslog_status >", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("syslog_status >=", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusLessThan(Integer value) {
            addCriterion("syslog_status <", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusLessThanOrEqualTo(Integer value) {
            addCriterion("syslog_status <=", value, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusIn(List<Integer> values) {
            addCriterion("syslog_status in", values, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusNotIn(List<Integer> values) {
            addCriterion("syslog_status not in", values, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusBetween(Integer value1, Integer value2) {
            addCriterion("syslog_status between", value1, value2, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andSyslogStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("syslog_status not between", value1, value2, "syslogStatus");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidIsNull() {
            addCriterion("log_sys_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidIsNotNull() {
            addCriterion("log_sys_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidEqualTo(Integer value) {
            addCriterion("log_sys_uuid =", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidNotEqualTo(Integer value) {
            addCriterion("log_sys_uuid <>", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidGreaterThan(Integer value) {
            addCriterion("log_sys_uuid >", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("log_sys_uuid >=", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidLessThan(Integer value) {
            addCriterion("log_sys_uuid <", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidLessThanOrEqualTo(Integer value) {
            addCriterion("log_sys_uuid <=", value, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidIn(List<Integer> values) {
            addCriterion("log_sys_uuid in", values, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidNotIn(List<Integer> values) {
            addCriterion("log_sys_uuid not in", values, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidBetween(Integer value1, Integer value2) {
            addCriterion("log_sys_uuid between", value1, value2, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andLogSysUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("log_sys_uuid not between", value1, value2, "logSysUuid");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateIsNull() {
            addCriterion("syslog_begin_date is null");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateIsNotNull() {
            addCriterion("syslog_begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateEqualTo(Date value) {
            addCriterion("syslog_begin_date =", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateNotEqualTo(Date value) {
            addCriterion("syslog_begin_date <>", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateGreaterThan(Date value) {
            addCriterion("syslog_begin_date >", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterion("syslog_begin_date >=", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateLessThan(Date value) {
            addCriterion("syslog_begin_date <", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateLessThanOrEqualTo(Date value) {
            addCriterion("syslog_begin_date <=", value, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateIn(List<Date> values) {
            addCriterion("syslog_begin_date in", values, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateNotIn(List<Date> values) {
            addCriterion("syslog_begin_date not in", values, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateBetween(Date value1, Date value2) {
            addCriterion("syslog_begin_date between", value1, value2, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogBeginDateNotBetween(Date value1, Date value2) {
            addCriterion("syslog_begin_date not between", value1, value2, "syslogBeginDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateIsNull() {
            addCriterion("syslog_end_date is null");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateIsNotNull() {
            addCriterion("syslog_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateEqualTo(Date value) {
            addCriterion("syslog_end_date =", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateNotEqualTo(Date value) {
            addCriterion("syslog_end_date <>", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateGreaterThan(Date value) {
            addCriterion("syslog_end_date >", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("syslog_end_date >=", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateLessThan(Date value) {
            addCriterion("syslog_end_date <", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateLessThanOrEqualTo(Date value) {
            addCriterion("syslog_end_date <=", value, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateIn(List<Date> values) {
            addCriterion("syslog_end_date in", values, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateNotIn(List<Date> values) {
            addCriterion("syslog_end_date not in", values, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateBetween(Date value1, Date value2) {
            addCriterion("syslog_end_date between", value1, value2, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogEndDateNotBetween(Date value1, Date value2) {
            addCriterion("syslog_end_date not between", value1, value2, "syslogEndDate");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelIsNull() {
            addCriterion("syslog_debug_level is null");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelIsNotNull() {
            addCriterion("syslog_debug_level is not null");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelEqualTo(Integer value) {
            addCriterion("syslog_debug_level =", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelNotEqualTo(Integer value) {
            addCriterion("syslog_debug_level <>", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelGreaterThan(Integer value) {
            addCriterion("syslog_debug_level >", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("syslog_debug_level >=", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelLessThan(Integer value) {
            addCriterion("syslog_debug_level <", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelLessThanOrEqualTo(Integer value) {
            addCriterion("syslog_debug_level <=", value, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelIn(List<Integer> values) {
            addCriterion("syslog_debug_level in", values, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelNotIn(List<Integer> values) {
            addCriterion("syslog_debug_level not in", values, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelBetween(Integer value1, Integer value2) {
            addCriterion("syslog_debug_level between", value1, value2, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andSyslogDebugLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("syslog_debug_level not between", value1, value2, "syslogDebugLevel");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagIsNull() {
            addCriterion("cdr_log_flag is null");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagIsNotNull() {
            addCriterion("cdr_log_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagEqualTo(Integer value) {
            addCriterion("cdr_log_flag =", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagNotEqualTo(Integer value) {
            addCriterion("cdr_log_flag <>", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagGreaterThan(Integer value) {
            addCriterion("cdr_log_flag >", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("cdr_log_flag >=", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagLessThan(Integer value) {
            addCriterion("cdr_log_flag <", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagLessThanOrEqualTo(Integer value) {
            addCriterion("cdr_log_flag <=", value, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagIn(List<Integer> values) {
            addCriterion("cdr_log_flag in", values, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagNotIn(List<Integer> values) {
            addCriterion("cdr_log_flag not in", values, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagBetween(Integer value1, Integer value2) {
            addCriterion("cdr_log_flag between", value1, value2, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("cdr_log_flag not between", value1, value2, "cdrLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagIsNull() {
            addCriterion("signal_log_flag is null");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagIsNotNull() {
            addCriterion("signal_log_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagEqualTo(Integer value) {
            addCriterion("signal_log_flag =", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagNotEqualTo(Integer value) {
            addCriterion("signal_log_flag <>", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagGreaterThan(Integer value) {
            addCriterion("signal_log_flag >", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("signal_log_flag >=", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagLessThan(Integer value) {
            addCriterion("signal_log_flag <", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagLessThanOrEqualTo(Integer value) {
            addCriterion("signal_log_flag <=", value, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagIn(List<Integer> values) {
            addCriterion("signal_log_flag in", values, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagNotIn(List<Integer> values) {
            addCriterion("signal_log_flag not in", values, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagBetween(Integer value1, Integer value2) {
            addCriterion("signal_log_flag between", value1, value2, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andSignalLogFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("signal_log_flag not between", value1, value2, "signalLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagIsNull() {
            addCriterion("media_log_flag is null");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagIsNotNull() {
            addCriterion("media_log_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagEqualTo(Integer value) {
            addCriterion("media_log_flag =", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagNotEqualTo(Integer value) {
            addCriterion("media_log_flag <>", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagGreaterThan(Integer value) {
            addCriterion("media_log_flag >", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("media_log_flag >=", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagLessThan(Integer value) {
            addCriterion("media_log_flag <", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagLessThanOrEqualTo(Integer value) {
            addCriterion("media_log_flag <=", value, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagIn(List<Integer> values) {
            addCriterion("media_log_flag in", values, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagNotIn(List<Integer> values) {
            addCriterion("media_log_flag not in", values, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagBetween(Integer value1, Integer value2) {
            addCriterion("media_log_flag between", value1, value2, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andMediaLogFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("media_log_flag not between", value1, value2, "mediaLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagIsNull() {
            addCriterion("system_log_flag is null");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagIsNotNull() {
            addCriterion("system_log_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagEqualTo(Integer value) {
            addCriterion("system_log_flag =", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagNotEqualTo(Integer value) {
            addCriterion("system_log_flag <>", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagGreaterThan(Integer value) {
            addCriterion("system_log_flag >", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_log_flag >=", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagLessThan(Integer value) {
            addCriterion("system_log_flag <", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagLessThanOrEqualTo(Integer value) {
            addCriterion("system_log_flag <=", value, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagIn(List<Integer> values) {
            addCriterion("system_log_flag in", values, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagNotIn(List<Integer> values) {
            addCriterion("system_log_flag not in", values, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagBetween(Integer value1, Integer value2) {
            addCriterion("system_log_flag between", value1, value2, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andSystemLogFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("system_log_flag not between", value1, value2, "systemLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagIsNull() {
            addCriterion("mng_log_flag is null");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagIsNotNull() {
            addCriterion("mng_log_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagEqualTo(Integer value) {
            addCriterion("mng_log_flag =", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagNotEqualTo(Integer value) {
            addCriterion("mng_log_flag <>", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagGreaterThan(Integer value) {
            addCriterion("mng_log_flag >", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("mng_log_flag >=", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagLessThan(Integer value) {
            addCriterion("mng_log_flag <", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagLessThanOrEqualTo(Integer value) {
            addCriterion("mng_log_flag <=", value, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagIn(List<Integer> values) {
            addCriterion("mng_log_flag in", values, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagNotIn(List<Integer> values) {
            addCriterion("mng_log_flag not in", values, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagBetween(Integer value1, Integer value2) {
            addCriterion("mng_log_flag between", value1, value2, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andMngLogFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("mng_log_flag not between", value1, value2, "mngLogFlag");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgIsNull() {
            addCriterion("cdr_log_falg is null");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgIsNotNull() {
            addCriterion("cdr_log_falg is not null");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgEqualTo(Integer value) {
            addCriterion("cdr_log_falg =", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgNotEqualTo(Integer value) {
            addCriterion("cdr_log_falg <>", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgGreaterThan(Integer value) {
            addCriterion("cdr_log_falg >", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgGreaterThanOrEqualTo(Integer value) {
            addCriterion("cdr_log_falg >=", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgLessThan(Integer value) {
            addCriterion("cdr_log_falg <", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgLessThanOrEqualTo(Integer value) {
            addCriterion("cdr_log_falg <=", value, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgIn(List<Integer> values) {
            addCriterion("cdr_log_falg in", values, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgNotIn(List<Integer> values) {
            addCriterion("cdr_log_falg not in", values, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgBetween(Integer value1, Integer value2) {
            addCriterion("cdr_log_falg between", value1, value2, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andCdrLogFalgNotBetween(Integer value1, Integer value2) {
            addCriterion("cdr_log_falg not between", value1, value2, "cdrLogFalg");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagIsNull() {
            addCriterion("sipsrv_lock_flag is null");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagIsNotNull() {
            addCriterion("sipsrv_lock_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagEqualTo(Integer value) {
            addCriterion("sipsrv_lock_flag =", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagNotEqualTo(Integer value) {
            addCriterion("sipsrv_lock_flag <>", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagGreaterThan(Integer value) {
            addCriterion("sipsrv_lock_flag >", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sipsrv_lock_flag >=", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagLessThan(Integer value) {
            addCriterion("sipsrv_lock_flag <", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sipsrv_lock_flag <=", value, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagIn(List<Integer> values) {
            addCriterion("sipsrv_lock_flag in", values, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagNotIn(List<Integer> values) {
            addCriterion("sipsrv_lock_flag not in", values, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagBetween(Integer value1, Integer value2) {
            addCriterion("sipsrv_lock_flag between", value1, value2, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andSipsrvLockFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sipsrv_lock_flag not between", value1, value2, "sipsrvLockFlag");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerIsNull() {
            addCriterion("primary_sip_server is null");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerIsNotNull() {
            addCriterion("primary_sip_server is not null");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerEqualTo(String value) {
            addCriterion("primary_sip_server =", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerNotEqualTo(String value) {
            addCriterion("primary_sip_server <>", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerGreaterThan(String value) {
            addCriterion("primary_sip_server >", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerGreaterThanOrEqualTo(String value) {
            addCriterion("primary_sip_server >=", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerLessThan(String value) {
            addCriterion("primary_sip_server <", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerLessThanOrEqualTo(String value) {
            addCriterion("primary_sip_server <=", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerLike(String value) {
            addCriterion("primary_sip_server like", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerNotLike(String value) {
            addCriterion("primary_sip_server not like", value, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerIn(List<String> values) {
            addCriterion("primary_sip_server in", values, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerNotIn(List<String> values) {
            addCriterion("primary_sip_server not in", values, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerBetween(String value1, String value2) {
            addCriterion("primary_sip_server between", value1, value2, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipServerNotBetween(String value1, String value2) {
            addCriterion("primary_sip_server not between", value1, value2, "primarySipServer");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortIsNull() {
            addCriterion("primary_sipsrv_port is null");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortIsNotNull() {
            addCriterion("primary_sipsrv_port is not null");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortEqualTo(Integer value) {
            addCriterion("primary_sipsrv_port =", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortNotEqualTo(Integer value) {
            addCriterion("primary_sipsrv_port <>", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortGreaterThan(Integer value) {
            addCriterion("primary_sipsrv_port >", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("primary_sipsrv_port >=", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortLessThan(Integer value) {
            addCriterion("primary_sipsrv_port <", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortLessThanOrEqualTo(Integer value) {
            addCriterion("primary_sipsrv_port <=", value, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortIn(List<Integer> values) {
            addCriterion("primary_sipsrv_port in", values, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortNotIn(List<Integer> values) {
            addCriterion("primary_sipsrv_port not in", values, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortBetween(Integer value1, Integer value2) {
            addCriterion("primary_sipsrv_port between", value1, value2, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andPrimarySipsrvPortNotBetween(Integer value1, Integer value2) {
            addCriterion("primary_sipsrv_port not between", value1, value2, "primarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerIsNull() {
            addCriterion("secondary_sip_server is null");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerIsNotNull() {
            addCriterion("secondary_sip_server is not null");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerEqualTo(String value) {
            addCriterion("secondary_sip_server =", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerNotEqualTo(String value) {
            addCriterion("secondary_sip_server <>", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerGreaterThan(String value) {
            addCriterion("secondary_sip_server >", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerGreaterThanOrEqualTo(String value) {
            addCriterion("secondary_sip_server >=", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerLessThan(String value) {
            addCriterion("secondary_sip_server <", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerLessThanOrEqualTo(String value) {
            addCriterion("secondary_sip_server <=", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerLike(String value) {
            addCriterion("secondary_sip_server like", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerNotLike(String value) {
            addCriterion("secondary_sip_server not like", value, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerIn(List<String> values) {
            addCriterion("secondary_sip_server in", values, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerNotIn(List<String> values) {
            addCriterion("secondary_sip_server not in", values, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerBetween(String value1, String value2) {
            addCriterion("secondary_sip_server between", value1, value2, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipServerNotBetween(String value1, String value2) {
            addCriterion("secondary_sip_server not between", value1, value2, "secondarySipServer");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortIsNull() {
            addCriterion("secondary_sipsrv_port is null");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortIsNotNull() {
            addCriterion("secondary_sipsrv_port is not null");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortEqualTo(Integer value) {
            addCriterion("secondary_sipsrv_port =", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortNotEqualTo(Integer value) {
            addCriterion("secondary_sipsrv_port <>", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortGreaterThan(Integer value) {
            addCriterion("secondary_sipsrv_port >", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("secondary_sipsrv_port >=", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortLessThan(Integer value) {
            addCriterion("secondary_sipsrv_port <", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortLessThanOrEqualTo(Integer value) {
            addCriterion("secondary_sipsrv_port <=", value, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortIn(List<Integer> values) {
            addCriterion("secondary_sipsrv_port in", values, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortNotIn(List<Integer> values) {
            addCriterion("secondary_sipsrv_port not in", values, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortBetween(Integer value1, Integer value2) {
            addCriterion("secondary_sipsrv_port between", value1, value2, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andSecondarySipsrvPortNotBetween(Integer value1, Integer value2) {
            addCriterion("secondary_sipsrv_port not between", value1, value2, "secondarySipsrvPort");
            return (Criteria) this;
        }

        public Criteria andNtpStatusIsNull() {
            addCriterion("ntp_status is null");
            return (Criteria) this;
        }

        public Criteria andNtpStatusIsNotNull() {
            addCriterion("ntp_status is not null");
            return (Criteria) this;
        }

        public Criteria andNtpStatusEqualTo(Integer value) {
            addCriterion("ntp_status =", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusNotEqualTo(Integer value) {
            addCriterion("ntp_status <>", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusGreaterThan(Integer value) {
            addCriterion("ntp_status >", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("ntp_status >=", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusLessThan(Integer value) {
            addCriterion("ntp_status <", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusLessThanOrEqualTo(Integer value) {
            addCriterion("ntp_status <=", value, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusIn(List<Integer> values) {
            addCriterion("ntp_status in", values, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusNotIn(List<Integer> values) {
            addCriterion("ntp_status not in", values, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusBetween(Integer value1, Integer value2) {
            addCriterion("ntp_status between", value1, value2, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andNtpStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("ntp_status not between", value1, value2, "ntpStatus");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagIsNull() {
            addCriterion("auto_reboot_flag is null");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagIsNotNull() {
            addCriterion("auto_reboot_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagEqualTo(Integer value) {
            addCriterion("auto_reboot_flag =", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagNotEqualTo(Integer value) {
            addCriterion("auto_reboot_flag <>", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagGreaterThan(Integer value) {
            addCriterion("auto_reboot_flag >", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("auto_reboot_flag >=", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagLessThan(Integer value) {
            addCriterion("auto_reboot_flag <", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagLessThanOrEqualTo(Integer value) {
            addCriterion("auto_reboot_flag <=", value, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagIn(List<Integer> values) {
            addCriterion("auto_reboot_flag in", values, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagNotIn(List<Integer> values) {
            addCriterion("auto_reboot_flag not in", values, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagBetween(Integer value1, Integer value2) {
            addCriterion("auto_reboot_flag between", value1, value2, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andAutoRebootFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("auto_reboot_flag not between", value1, value2, "autoRebootFlag");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusIsNull() {
            addCriterion("switch_chip_status is null");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusIsNotNull() {
            addCriterion("switch_chip_status is not null");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusEqualTo(Integer value) {
            addCriterion("switch_chip_status =", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusNotEqualTo(Integer value) {
            addCriterion("switch_chip_status <>", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusGreaterThan(Integer value) {
            addCriterion("switch_chip_status >", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("switch_chip_status >=", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusLessThan(Integer value) {
            addCriterion("switch_chip_status <", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusLessThanOrEqualTo(Integer value) {
            addCriterion("switch_chip_status <=", value, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusIn(List<Integer> values) {
            addCriterion("switch_chip_status in", values, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusNotIn(List<Integer> values) {
            addCriterion("switch_chip_status not in", values, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusBetween(Integer value1, Integer value2) {
            addCriterion("switch_chip_status between", value1, value2, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andSwitchChipStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("switch_chip_status not between", value1, value2, "switchChipStatus");
            return (Criteria) this;
        }

        public Criteria andCurCpuIsNull() {
            addCriterion("cur_cpu is null");
            return (Criteria) this;
        }

        public Criteria andCurCpuIsNotNull() {
            addCriterion("cur_cpu is not null");
            return (Criteria) this;
        }

        public Criteria andCurCpuEqualTo(Integer value) {
            addCriterion("cur_cpu =", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuNotEqualTo(Integer value) {
            addCriterion("cur_cpu <>", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuGreaterThan(Integer value) {
            addCriterion("cur_cpu >", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_cpu >=", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuLessThan(Integer value) {
            addCriterion("cur_cpu <", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuLessThanOrEqualTo(Integer value) {
            addCriterion("cur_cpu <=", value, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuIn(List<Integer> values) {
            addCriterion("cur_cpu in", values, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuNotIn(List<Integer> values) {
            addCriterion("cur_cpu not in", values, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuBetween(Integer value1, Integer value2) {
            addCriterion("cur_cpu between", value1, value2, "curCpu");
            return (Criteria) this;
        }

        public Criteria andCurCpuNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_cpu not between", value1, value2, "curCpu");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5IsNull() {
            addCriterion("avg_cpu_5 is null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5IsNotNull() {
            addCriterion("avg_cpu_5 is not null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5EqualTo(Integer value) {
            addCriterion("avg_cpu_5 =", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5NotEqualTo(Integer value) {
            addCriterion("avg_cpu_5 <>", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5GreaterThan(Integer value) {
            addCriterion("avg_cpu_5 >", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5GreaterThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_5 >=", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5LessThan(Integer value) {
            addCriterion("avg_cpu_5 <", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5LessThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_5 <=", value, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5In(List<Integer> values) {
            addCriterion("avg_cpu_5 in", values, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5NotIn(List<Integer> values) {
            addCriterion("avg_cpu_5 not in", values, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5Between(Integer value1, Integer value2) {
            addCriterion("avg_cpu_5 between", value1, value2, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu5NotBetween(Integer value1, Integer value2) {
            addCriterion("avg_cpu_5 not between", value1, value2, "avgCpu5");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60IsNull() {
            addCriterion("avg_cpu_60 is null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60IsNotNull() {
            addCriterion("avg_cpu_60 is not null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60EqualTo(Integer value) {
            addCriterion("avg_cpu_60 =", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60NotEqualTo(Integer value) {
            addCriterion("avg_cpu_60 <>", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60GreaterThan(Integer value) {
            addCriterion("avg_cpu_60 >", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60GreaterThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_60 >=", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60LessThan(Integer value) {
            addCriterion("avg_cpu_60 <", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60LessThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_60 <=", value, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60In(List<Integer> values) {
            addCriterion("avg_cpu_60 in", values, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60NotIn(List<Integer> values) {
            addCriterion("avg_cpu_60 not in", values, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60Between(Integer value1, Integer value2) {
            addCriterion("avg_cpu_60 between", value1, value2, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu60NotBetween(Integer value1, Integer value2) {
            addCriterion("avg_cpu_60 not between", value1, value2, "avgCpu60");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600IsNull() {
            addCriterion("avg_cpu_600 is null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600IsNotNull() {
            addCriterion("avg_cpu_600 is not null");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600EqualTo(Integer value) {
            addCriterion("avg_cpu_600 =", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600NotEqualTo(Integer value) {
            addCriterion("avg_cpu_600 <>", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600GreaterThan(Integer value) {
            addCriterion("avg_cpu_600 >", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600GreaterThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_600 >=", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600LessThan(Integer value) {
            addCriterion("avg_cpu_600 <", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600LessThanOrEqualTo(Integer value) {
            addCriterion("avg_cpu_600 <=", value, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600In(List<Integer> values) {
            addCriterion("avg_cpu_600 in", values, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600NotIn(List<Integer> values) {
            addCriterion("avg_cpu_600 not in", values, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600Between(Integer value1, Integer value2) {
            addCriterion("avg_cpu_600 between", value1, value2, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andAvgCpu600NotBetween(Integer value1, Integer value2) {
            addCriterion("avg_cpu_600 not between", value1, value2, "avgCpu600");
            return (Criteria) this;
        }

        public Criteria andFreeMemIsNull() {
            addCriterion("free_mem is null");
            return (Criteria) this;
        }

        public Criteria andFreeMemIsNotNull() {
            addCriterion("free_mem is not null");
            return (Criteria) this;
        }

        public Criteria andFreeMemEqualTo(Integer value) {
            addCriterion("free_mem =", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemNotEqualTo(Integer value) {
            addCriterion("free_mem <>", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemGreaterThan(Integer value) {
            addCriterion("free_mem >", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemGreaterThanOrEqualTo(Integer value) {
            addCriterion("free_mem >=", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemLessThan(Integer value) {
            addCriterion("free_mem <", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemLessThanOrEqualTo(Integer value) {
            addCriterion("free_mem <=", value, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemIn(List<Integer> values) {
            addCriterion("free_mem in", values, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemNotIn(List<Integer> values) {
            addCriterion("free_mem not in", values, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemBetween(Integer value1, Integer value2) {
            addCriterion("free_mem between", value1, value2, "freeMem");
            return (Criteria) this;
        }

        public Criteria andFreeMemNotBetween(Integer value1, Integer value2) {
            addCriterion("free_mem not between", value1, value2, "freeMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemIsNull() {
            addCriterion("total_mem is null");
            return (Criteria) this;
        }

        public Criteria andTotalMemIsNotNull() {
            addCriterion("total_mem is not null");
            return (Criteria) this;
        }

        public Criteria andTotalMemEqualTo(Integer value) {
            addCriterion("total_mem =", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotEqualTo(Integer value) {
            addCriterion("total_mem <>", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemGreaterThan(Integer value) {
            addCriterion("total_mem >", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_mem >=", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemLessThan(Integer value) {
            addCriterion("total_mem <", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemLessThanOrEqualTo(Integer value) {
            addCriterion("total_mem <=", value, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemIn(List<Integer> values) {
            addCriterion("total_mem in", values, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotIn(List<Integer> values) {
            addCriterion("total_mem not in", values, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemBetween(Integer value1, Integer value2) {
            addCriterion("total_mem between", value1, value2, "totalMem");
            return (Criteria) this;
        }

        public Criteria andTotalMemNotBetween(Integer value1, Integer value2) {
            addCriterion("total_mem not between", value1, value2, "totalMem");
            return (Criteria) this;
        }

        public Criteria andMemUsageIsNull() {
            addCriterion("mem_usage is null");
            return (Criteria) this;
        }

        public Criteria andMemUsageIsNotNull() {
            addCriterion("mem_usage is not null");
            return (Criteria) this;
        }

        public Criteria andMemUsageEqualTo(Integer value) {
            addCriterion("mem_usage =", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageNotEqualTo(Integer value) {
            addCriterion("mem_usage <>", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageGreaterThan(Integer value) {
            addCriterion("mem_usage >", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageGreaterThanOrEqualTo(Integer value) {
            addCriterion("mem_usage >=", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageLessThan(Integer value) {
            addCriterion("mem_usage <", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageLessThanOrEqualTo(Integer value) {
            addCriterion("mem_usage <=", value, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageIn(List<Integer> values) {
            addCriterion("mem_usage in", values, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageNotIn(List<Integer> values) {
            addCriterion("mem_usage not in", values, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageBetween(Integer value1, Integer value2) {
            addCriterion("mem_usage between", value1, value2, "memUsage");
            return (Criteria) this;
        }

        public Criteria andMemUsageNotBetween(Integer value1, Integer value2) {
            addCriterion("mem_usage not between", value1, value2, "memUsage");
            return (Criteria) this;
        }

        public Criteria andDevTimeIsNull() {
            addCriterion("dev_time is null");
            return (Criteria) this;
        }

        public Criteria andDevTimeIsNotNull() {
            addCriterion("dev_time is not null");
            return (Criteria) this;
        }

        public Criteria andDevTimeEqualTo(Date value) {
            addCriterion("dev_time =", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeNotEqualTo(Date value) {
            addCriterion("dev_time <>", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeGreaterThan(Date value) {
            addCriterion("dev_time >", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("dev_time >=", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeLessThan(Date value) {
            addCriterion("dev_time <", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeLessThanOrEqualTo(Date value) {
            addCriterion("dev_time <=", value, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeIn(List<Date> values) {
            addCriterion("dev_time in", values, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeNotIn(List<Date> values) {
            addCriterion("dev_time not in", values, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeBetween(Date value1, Date value2) {
            addCriterion("dev_time between", value1, value2, "devTime");
            return (Criteria) this;
        }

        public Criteria andDevTimeNotBetween(Date value1, Date value2) {
            addCriterion("dev_time not between", value1, value2, "devTime");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagIsNull() {
            addCriterion("alarm_flag is null");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagIsNotNull() {
            addCriterion("alarm_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagEqualTo(Integer value) {
            addCriterion("alarm_flag =", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagNotEqualTo(Integer value) {
            addCriterion("alarm_flag <>", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagGreaterThan(Integer value) {
            addCriterion("alarm_flag >", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("alarm_flag >=", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagLessThan(Integer value) {
            addCriterion("alarm_flag <", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagLessThanOrEqualTo(Integer value) {
            addCriterion("alarm_flag <=", value, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagIn(List<Integer> values) {
            addCriterion("alarm_flag in", values, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagNotIn(List<Integer> values) {
            addCriterion("alarm_flag not in", values, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagBetween(Integer value1, Integer value2) {
            addCriterion("alarm_flag between", value1, value2, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andAlarmFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("alarm_flag not between", value1, value2, "alarmFlag");
            return (Criteria) this;
        }

        public Criteria andLocMccIsNull() {
            addCriterion("loc_mcc is null");
            return (Criteria) this;
        }

        public Criteria andLocMccIsNotNull() {
            addCriterion("loc_mcc is not null");
            return (Criteria) this;
        }

        public Criteria andLocMccEqualTo(Integer value) {
            addCriterion("loc_mcc =", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccNotEqualTo(Integer value) {
            addCriterion("loc_mcc <>", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccGreaterThan(Integer value) {
            addCriterion("loc_mcc >", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccGreaterThanOrEqualTo(Integer value) {
            addCriterion("loc_mcc >=", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccLessThan(Integer value) {
            addCriterion("loc_mcc <", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccLessThanOrEqualTo(Integer value) {
            addCriterion("loc_mcc <=", value, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccIn(List<Integer> values) {
            addCriterion("loc_mcc in", values, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccNotIn(List<Integer> values) {
            addCriterion("loc_mcc not in", values, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccBetween(Integer value1, Integer value2) {
            addCriterion("loc_mcc between", value1, value2, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMccNotBetween(Integer value1, Integer value2) {
            addCriterion("loc_mcc not between", value1, value2, "locMcc");
            return (Criteria) this;
        }

        public Criteria andLocMncIsNull() {
            addCriterion("loc_mnc is null");
            return (Criteria) this;
        }

        public Criteria andLocMncIsNotNull() {
            addCriterion("loc_mnc is not null");
            return (Criteria) this;
        }

        public Criteria andLocMncEqualTo(Integer value) {
            addCriterion("loc_mnc =", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncNotEqualTo(Integer value) {
            addCriterion("loc_mnc <>", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncGreaterThan(Integer value) {
            addCriterion("loc_mnc >", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncGreaterThanOrEqualTo(Integer value) {
            addCriterion("loc_mnc >=", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncLessThan(Integer value) {
            addCriterion("loc_mnc <", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncLessThanOrEqualTo(Integer value) {
            addCriterion("loc_mnc <=", value, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncIn(List<Integer> values) {
            addCriterion("loc_mnc in", values, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncNotIn(List<Integer> values) {
            addCriterion("loc_mnc not in", values, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncBetween(Integer value1, Integer value2) {
            addCriterion("loc_mnc between", value1, value2, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocMncNotBetween(Integer value1, Integer value2) {
            addCriterion("loc_mnc not between", value1, value2, "locMnc");
            return (Criteria) this;
        }

        public Criteria andLocLacIsNull() {
            addCriterion("loc_lac is null");
            return (Criteria) this;
        }

        public Criteria andLocLacIsNotNull() {
            addCriterion("loc_lac is not null");
            return (Criteria) this;
        }

        public Criteria andLocLacEqualTo(Integer value) {
            addCriterion("loc_lac =", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacNotEqualTo(Integer value) {
            addCriterion("loc_lac <>", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacGreaterThan(Integer value) {
            addCriterion("loc_lac >", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacGreaterThanOrEqualTo(Integer value) {
            addCriterion("loc_lac >=", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacLessThan(Integer value) {
            addCriterion("loc_lac <", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacLessThanOrEqualTo(Integer value) {
            addCriterion("loc_lac <=", value, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacIn(List<Integer> values) {
            addCriterion("loc_lac in", values, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacNotIn(List<Integer> values) {
            addCriterion("loc_lac not in", values, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacBetween(Integer value1, Integer value2) {
            addCriterion("loc_lac between", value1, value2, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocLacNotBetween(Integer value1, Integer value2) {
            addCriterion("loc_lac not between", value1, value2, "locLac");
            return (Criteria) this;
        }

        public Criteria andLocCellIdIsNull() {
            addCriterion("loc_cell_id is null");
            return (Criteria) this;
        }

        public Criteria andLocCellIdIsNotNull() {
            addCriterion("loc_cell_id is not null");
            return (Criteria) this;
        }

        public Criteria andLocCellIdEqualTo(Integer value) {
            addCriterion("loc_cell_id =", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdNotEqualTo(Integer value) {
            addCriterion("loc_cell_id <>", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdGreaterThan(Integer value) {
            addCriterion("loc_cell_id >", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("loc_cell_id >=", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdLessThan(Integer value) {
            addCriterion("loc_cell_id <", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdLessThanOrEqualTo(Integer value) {
            addCriterion("loc_cell_id <=", value, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdIn(List<Integer> values) {
            addCriterion("loc_cell_id in", values, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdNotIn(List<Integer> values) {
            addCriterion("loc_cell_id not in", values, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdBetween(Integer value1, Integer value2) {
            addCriterion("loc_cell_id between", value1, value2, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocCellIdNotBetween(Integer value1, Integer value2) {
            addCriterion("loc_cell_id not between", value1, value2, "locCellId");
            return (Criteria) this;
        }

        public Criteria andLocStatusIsNull() {
            addCriterion("loc_status is null");
            return (Criteria) this;
        }

        public Criteria andLocStatusIsNotNull() {
            addCriterion("loc_status is not null");
            return (Criteria) this;
        }

        public Criteria andLocStatusEqualTo(Integer value) {
            addCriterion("loc_status =", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusNotEqualTo(Integer value) {
            addCriterion("loc_status <>", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusGreaterThan(Integer value) {
            addCriterion("loc_status >", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("loc_status >=", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusLessThan(Integer value) {
            addCriterion("loc_status <", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusLessThanOrEqualTo(Integer value) {
            addCriterion("loc_status <=", value, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusIn(List<Integer> values) {
            addCriterion("loc_status in", values, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusNotIn(List<Integer> values) {
            addCriterion("loc_status not in", values, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusBetween(Integer value1, Integer value2) {
            addCriterion("loc_status between", value1, value2, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("loc_status not between", value1, value2, "locStatus");
            return (Criteria) this;
        }

        public Criteria andLocLatIsNull() {
            addCriterion("loc_lat is null");
            return (Criteria) this;
        }

        public Criteria andLocLatIsNotNull() {
            addCriterion("loc_lat is not null");
            return (Criteria) this;
        }

        public Criteria andLocLatEqualTo(Float value) {
            addCriterion("loc_lat =", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatNotEqualTo(Float value) {
            addCriterion("loc_lat <>", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatGreaterThan(Float value) {
            addCriterion("loc_lat >", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatGreaterThanOrEqualTo(Float value) {
            addCriterion("loc_lat >=", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatLessThan(Float value) {
            addCriterion("loc_lat <", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatLessThanOrEqualTo(Float value) {
            addCriterion("loc_lat <=", value, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatIn(List<Float> values) {
            addCriterion("loc_lat in", values, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatNotIn(List<Float> values) {
            addCriterion("loc_lat not in", values, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatBetween(Float value1, Float value2) {
            addCriterion("loc_lat between", value1, value2, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLatNotBetween(Float value1, Float value2) {
            addCriterion("loc_lat not between", value1, value2, "locLat");
            return (Criteria) this;
        }

        public Criteria andLocLngIsNull() {
            addCriterion("loc_lng is null");
            return (Criteria) this;
        }

        public Criteria andLocLngIsNotNull() {
            addCriterion("loc_lng is not null");
            return (Criteria) this;
        }

        public Criteria andLocLngEqualTo(Float value) {
            addCriterion("loc_lng =", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngNotEqualTo(Float value) {
            addCriterion("loc_lng <>", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngGreaterThan(Float value) {
            addCriterion("loc_lng >", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngGreaterThanOrEqualTo(Float value) {
            addCriterion("loc_lng >=", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngLessThan(Float value) {
            addCriterion("loc_lng <", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngLessThanOrEqualTo(Float value) {
            addCriterion("loc_lng <=", value, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngIn(List<Float> values) {
            addCriterion("loc_lng in", values, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngNotIn(List<Float> values) {
            addCriterion("loc_lng not in", values, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngBetween(Float value1, Float value2) {
            addCriterion("loc_lng between", value1, value2, "locLng");
            return (Criteria) this;
        }

        public Criteria andLocLngNotBetween(Float value1, Float value2) {
            addCriterion("loc_lng not between", value1, value2, "locLng");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagIsNull() {
            addCriterion("upgrade_flag is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagIsNotNull() {
            addCriterion("upgrade_flag is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagEqualTo(Integer value) {
            addCriterion("upgrade_flag =", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagNotEqualTo(Integer value) {
            addCriterion("upgrade_flag <>", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagGreaterThan(Integer value) {
            addCriterion("upgrade_flag >", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_flag >=", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagLessThan(Integer value) {
            addCriterion("upgrade_flag <", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_flag <=", value, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagIn(List<Integer> values) {
            addCriterion("upgrade_flag in", values, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagNotIn(List<Integer> values) {
            addCriterion("upgrade_flag not in", values, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_flag between", value1, value2, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andUpgradeFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_flag not between", value1, value2, "upgradeFlag");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusIsNull() {
            addCriterion("time_chip_status is null");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusIsNotNull() {
            addCriterion("time_chip_status is not null");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusEqualTo(Integer value) {
            addCriterion("time_chip_status =", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusNotEqualTo(Integer value) {
            addCriterion("time_chip_status <>", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusGreaterThan(Integer value) {
            addCriterion("time_chip_status >", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_chip_status >=", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusLessThan(Integer value) {
            addCriterion("time_chip_status <", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusLessThanOrEqualTo(Integer value) {
            addCriterion("time_chip_status <=", value, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusIn(List<Integer> values) {
            addCriterion("time_chip_status in", values, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusNotIn(List<Integer> values) {
            addCriterion("time_chip_status not in", values, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusBetween(Integer value1, Integer value2) {
            addCriterion("time_chip_status between", value1, value2, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andTimeChipStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("time_chip_status not between", value1, value2, "timeChipStatus");
            return (Criteria) this;
        }

        public Criteria andAllocMemIsNull() {
            addCriterion("alloc_mem is null");
            return (Criteria) this;
        }

        public Criteria andAllocMemIsNotNull() {
            addCriterion("alloc_mem is not null");
            return (Criteria) this;
        }

        public Criteria andAllocMemEqualTo(Integer value) {
            addCriterion("alloc_mem =", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemNotEqualTo(Integer value) {
            addCriterion("alloc_mem <>", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemGreaterThan(Integer value) {
            addCriterion("alloc_mem >", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemGreaterThanOrEqualTo(Integer value) {
            addCriterion("alloc_mem >=", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemLessThan(Integer value) {
            addCriterion("alloc_mem <", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemLessThanOrEqualTo(Integer value) {
            addCriterion("alloc_mem <=", value, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemIn(List<Integer> values) {
            addCriterion("alloc_mem in", values, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemNotIn(List<Integer> values) {
            addCriterion("alloc_mem not in", values, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemBetween(Integer value1, Integer value2) {
            addCriterion("alloc_mem between", value1, value2, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAllocMemNotBetween(Integer value1, Integer value2) {
            addCriterion("alloc_mem not between", value1, value2, "allocMem");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodIsNull() {
            addCriterion("alarm_short_period is null");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodIsNotNull() {
            addCriterion("alarm_short_period is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodEqualTo(Integer value) {
            addCriterion("alarm_short_period =", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodNotEqualTo(Integer value) {
            addCriterion("alarm_short_period <>", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodGreaterThan(Integer value) {
            addCriterion("alarm_short_period >", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("alarm_short_period >=", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodLessThan(Integer value) {
            addCriterion("alarm_short_period <", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("alarm_short_period <=", value, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodIn(List<Integer> values) {
            addCriterion("alarm_short_period in", values, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodNotIn(List<Integer> values) {
            addCriterion("alarm_short_period not in", values, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodBetween(Integer value1, Integer value2) {
            addCriterion("alarm_short_period between", value1, value2, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmShortPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("alarm_short_period not between", value1, value2, "alarmShortPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodIsNull() {
            addCriterion("alarm_long_period is null");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodIsNotNull() {
            addCriterion("alarm_long_period is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodEqualTo(Integer value) {
            addCriterion("alarm_long_period =", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodNotEqualTo(Integer value) {
            addCriterion("alarm_long_period <>", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodGreaterThan(Integer value) {
            addCriterion("alarm_long_period >", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("alarm_long_period >=", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodLessThan(Integer value) {
            addCriterion("alarm_long_period <", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("alarm_long_period <=", value, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodIn(List<Integer> values) {
            addCriterion("alarm_long_period in", values, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodNotIn(List<Integer> values) {
            addCriterion("alarm_long_period not in", values, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodBetween(Integer value1, Integer value2) {
            addCriterion("alarm_long_period between", value1, value2, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andAlarmLongPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("alarm_long_period not between", value1, value2, "alarmLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodIsNull() {
            addCriterion("status_short_period is null");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodIsNotNull() {
            addCriterion("status_short_period is not null");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodEqualTo(Integer value) {
            addCriterion("status_short_period =", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodNotEqualTo(Integer value) {
            addCriterion("status_short_period <>", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodGreaterThan(Integer value) {
            addCriterion("status_short_period >", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_short_period >=", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodLessThan(Integer value) {
            addCriterion("status_short_period <", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("status_short_period <=", value, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodIn(List<Integer> values) {
            addCriterion("status_short_period in", values, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodNotIn(List<Integer> values) {
            addCriterion("status_short_period not in", values, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodBetween(Integer value1, Integer value2) {
            addCriterion("status_short_period between", value1, value2, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusShortPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("status_short_period not between", value1, value2, "statusShortPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodIsNull() {
            addCriterion("status_long_period is null");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodIsNotNull() {
            addCriterion("status_long_period is not null");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodEqualTo(Integer value) {
            addCriterion("status_long_period =", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodNotEqualTo(Integer value) {
            addCriterion("status_long_period <>", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodGreaterThan(Integer value) {
            addCriterion("status_long_period >", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_long_period >=", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodLessThan(Integer value) {
            addCriterion("status_long_period <", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("status_long_period <=", value, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodIn(List<Integer> values) {
            addCriterion("status_long_period in", values, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodNotIn(List<Integer> values) {
            addCriterion("status_long_period not in", values, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodBetween(Integer value1, Integer value2) {
            addCriterion("status_long_period between", value1, value2, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStatusLongPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("status_long_period not between", value1, value2, "statusLongPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodIsNull() {
            addCriterion("statics_period is null");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodIsNotNull() {
            addCriterion("statics_period is not null");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodEqualTo(Integer value) {
            addCriterion("statics_period =", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodNotEqualTo(Integer value) {
            addCriterion("statics_period <>", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodGreaterThan(Integer value) {
            addCriterion("statics_period >", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("statics_period >=", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodLessThan(Integer value) {
            addCriterion("statics_period <", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("statics_period <=", value, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodIn(List<Integer> values) {
            addCriterion("statics_period in", values, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodNotIn(List<Integer> values) {
            addCriterion("statics_period not in", values, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodBetween(Integer value1, Integer value2) {
            addCriterion("statics_period between", value1, value2, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andStaticsPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("statics_period not between", value1, value2, "staticsPeriod");
            return (Criteria) this;
        }

        public Criteria andLockStatusIsNull() {
            addCriterion("lock_status is null");
            return (Criteria) this;
        }

        public Criteria andLockStatusIsNotNull() {
            addCriterion("lock_status is not null");
            return (Criteria) this;
        }

        public Criteria andLockStatusEqualTo(Integer value) {
            addCriterion("lock_status =", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusNotEqualTo(Integer value) {
            addCriterion("lock_status <>", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusGreaterThan(Integer value) {
            addCriterion("lock_status >", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_status >=", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusLessThan(Integer value) {
            addCriterion("lock_status <", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusLessThanOrEqualTo(Integer value) {
            addCriterion("lock_status <=", value, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusIn(List<Integer> values) {
            addCriterion("lock_status in", values, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusNotIn(List<Integer> values) {
            addCriterion("lock_status not in", values, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusBetween(Integer value1, Integer value2) {
            addCriterion("lock_status between", value1, value2, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andLockStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_status not between", value1, value2, "lockStatus");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageIsNull() {
            addCriterion("mem_aos_usage is null");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageIsNotNull() {
            addCriterion("mem_aos_usage is not null");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageEqualTo(Integer value) {
            addCriterion("mem_aos_usage =", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageNotEqualTo(Integer value) {
            addCriterion("mem_aos_usage <>", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageGreaterThan(Integer value) {
            addCriterion("mem_aos_usage >", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageGreaterThanOrEqualTo(Integer value) {
            addCriterion("mem_aos_usage >=", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageLessThan(Integer value) {
            addCriterion("mem_aos_usage <", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageLessThanOrEqualTo(Integer value) {
            addCriterion("mem_aos_usage <=", value, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageIn(List<Integer> values) {
            addCriterion("mem_aos_usage in", values, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageNotIn(List<Integer> values) {
            addCriterion("mem_aos_usage not in", values, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageBetween(Integer value1, Integer value2) {
            addCriterion("mem_aos_usage between", value1, value2, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemAosUsageNotBetween(Integer value1, Integer value2) {
            addCriterion("mem_aos_usage not between", value1, value2, "memAosUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageIsNull() {
            addCriterion("mem_linux_usage is null");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageIsNotNull() {
            addCriterion("mem_linux_usage is not null");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageEqualTo(Integer value) {
            addCriterion("mem_linux_usage =", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageNotEqualTo(Integer value) {
            addCriterion("mem_linux_usage <>", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageGreaterThan(Integer value) {
            addCriterion("mem_linux_usage >", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageGreaterThanOrEqualTo(Integer value) {
            addCriterion("mem_linux_usage >=", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageLessThan(Integer value) {
            addCriterion("mem_linux_usage <", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageLessThanOrEqualTo(Integer value) {
            addCriterion("mem_linux_usage <=", value, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageIn(List<Integer> values) {
            addCriterion("mem_linux_usage in", values, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageNotIn(List<Integer> values) {
            addCriterion("mem_linux_usage not in", values, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageBetween(Integer value1, Integer value2) {
            addCriterion("mem_linux_usage between", value1, value2, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andMemLinuxUsageNotBetween(Integer value1, Integer value2) {
            addCriterion("mem_linux_usage not between", value1, value2, "memLinuxUsage");
            return (Criteria) this;
        }

        public Criteria andNatModeIsNull() {
            addCriterion("nat_mode is null");
            return (Criteria) this;
        }

        public Criteria andNatModeIsNotNull() {
            addCriterion("nat_mode is not null");
            return (Criteria) this;
        }

        public Criteria andNatModeEqualTo(Integer value) {
            addCriterion("nat_mode =", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeNotEqualTo(Integer value) {
            addCriterion("nat_mode <>", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeGreaterThan(Integer value) {
            addCriterion("nat_mode >", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("nat_mode >=", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeLessThan(Integer value) {
            addCriterion("nat_mode <", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeLessThanOrEqualTo(Integer value) {
            addCriterion("nat_mode <=", value, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeIn(List<Integer> values) {
            addCriterion("nat_mode in", values, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeNotIn(List<Integer> values) {
            addCriterion("nat_mode not in", values, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeBetween(Integer value1, Integer value2) {
            addCriterion("nat_mode between", value1, value2, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatModeNotBetween(Integer value1, Integer value2) {
            addCriterion("nat_mode not between", value1, value2, "natMode");
            return (Criteria) this;
        }

        public Criteria andNatStatusIsNull() {
            addCriterion("nat_status is null");
            return (Criteria) this;
        }

        public Criteria andNatStatusIsNotNull() {
            addCriterion("nat_status is not null");
            return (Criteria) this;
        }

        public Criteria andNatStatusEqualTo(Integer value) {
            addCriterion("nat_status =", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusNotEqualTo(Integer value) {
            addCriterion("nat_status <>", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusGreaterThan(Integer value) {
            addCriterion("nat_status >", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("nat_status >=", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusLessThan(Integer value) {
            addCriterion("nat_status <", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusLessThanOrEqualTo(Integer value) {
            addCriterion("nat_status <=", value, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusIn(List<Integer> values) {
            addCriterion("nat_status in", values, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusNotIn(List<Integer> values) {
            addCriterion("nat_status not in", values, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusBetween(Integer value1, Integer value2) {
            addCriterion("nat_status between", value1, value2, "natStatus");
            return (Criteria) this;
        }

        public Criteria andNatStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("nat_status not between", value1, value2, "natStatus");
            return (Criteria) this;
        }

        public Criteria andStoreUsageIsNull() {
            addCriterion("store_usage is null");
            return (Criteria) this;
        }

        public Criteria andStoreUsageIsNotNull() {
            addCriterion("store_usage is not null");
            return (Criteria) this;
        }

        public Criteria andStoreUsageEqualTo(Integer value) {
            addCriterion("store_usage =", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageNotEqualTo(Integer value) {
            addCriterion("store_usage <>", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageGreaterThan(Integer value) {
            addCriterion("store_usage >", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_usage >=", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageLessThan(Integer value) {
            addCriterion("store_usage <", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageLessThanOrEqualTo(Integer value) {
            addCriterion("store_usage <=", value, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageIn(List<Integer> values) {
            addCriterion("store_usage in", values, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageNotIn(List<Integer> values) {
            addCriterion("store_usage not in", values, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageBetween(Integer value1, Integer value2) {
            addCriterion("store_usage between", value1, value2, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andStoreUsageNotBetween(Integer value1, Integer value2) {
            addCriterion("store_usage not between", value1, value2, "storeUsage");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeIsNull() {
            addCriterion("eth_link_mode is null");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeIsNotNull() {
            addCriterion("eth_link_mode is not null");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeEqualTo(Integer value) {
            addCriterion("eth_link_mode =", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeNotEqualTo(Integer value) {
            addCriterion("eth_link_mode <>", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeGreaterThan(Integer value) {
            addCriterion("eth_link_mode >", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("eth_link_mode >=", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeLessThan(Integer value) {
            addCriterion("eth_link_mode <", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeLessThanOrEqualTo(Integer value) {
            addCriterion("eth_link_mode <=", value, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeIn(List<Integer> values) {
            addCriterion("eth_link_mode in", values, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeNotIn(List<Integer> values) {
            addCriterion("eth_link_mode not in", values, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeBetween(Integer value1, Integer value2) {
            addCriterion("eth_link_mode between", value1, value2, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andEthLinkModeNotBetween(Integer value1, Integer value2) {
            addCriterion("eth_link_mode not between", value1, value2, "ethLinkMode");
            return (Criteria) this;
        }

        public Criteria andPortType1IsNull() {
            addCriterion("port_type1 is null");
            return (Criteria) this;
        }

        public Criteria andPortType1IsNotNull() {
            addCriterion("port_type1 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType1EqualTo(Integer value) {
            addCriterion("port_type1 =", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1NotEqualTo(Integer value) {
            addCriterion("port_type1 <>", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1GreaterThan(Integer value) {
            addCriterion("port_type1 >", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type1 >=", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1LessThan(Integer value) {
            addCriterion("port_type1 <", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1LessThanOrEqualTo(Integer value) {
            addCriterion("port_type1 <=", value, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1In(List<Integer> values) {
            addCriterion("port_type1 in", values, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1NotIn(List<Integer> values) {
            addCriterion("port_type1 not in", values, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1Between(Integer value1, Integer value2) {
            addCriterion("port_type1 between", value1, value2, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortType1NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type1 not between", value1, value2, "portType1");
            return (Criteria) this;
        }

        public Criteria andPortCount1IsNull() {
            addCriterion("port_count1 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount1IsNotNull() {
            addCriterion("port_count1 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount1EqualTo(Integer value) {
            addCriterion("port_count1 =", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1NotEqualTo(Integer value) {
            addCriterion("port_count1 <>", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1GreaterThan(Integer value) {
            addCriterion("port_count1 >", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count1 >=", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1LessThan(Integer value) {
            addCriterion("port_count1 <", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1LessThanOrEqualTo(Integer value) {
            addCriterion("port_count1 <=", value, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1In(List<Integer> values) {
            addCriterion("port_count1 in", values, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1NotIn(List<Integer> values) {
            addCriterion("port_count1 not in", values, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1Between(Integer value1, Integer value2) {
            addCriterion("port_count1 between", value1, value2, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortCount1NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count1 not between", value1, value2, "portCount1");
            return (Criteria) this;
        }

        public Criteria andPortType2IsNull() {
            addCriterion("port_type2 is null");
            return (Criteria) this;
        }

        public Criteria andPortType2IsNotNull() {
            addCriterion("port_type2 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType2EqualTo(Integer value) {
            addCriterion("port_type2 =", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2NotEqualTo(Integer value) {
            addCriterion("port_type2 <>", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2GreaterThan(Integer value) {
            addCriterion("port_type2 >", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type2 >=", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2LessThan(Integer value) {
            addCriterion("port_type2 <", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2LessThanOrEqualTo(Integer value) {
            addCriterion("port_type2 <=", value, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2In(List<Integer> values) {
            addCriterion("port_type2 in", values, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2NotIn(List<Integer> values) {
            addCriterion("port_type2 not in", values, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2Between(Integer value1, Integer value2) {
            addCriterion("port_type2 between", value1, value2, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortType2NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type2 not between", value1, value2, "portType2");
            return (Criteria) this;
        }

        public Criteria andPortCount2IsNull() {
            addCriterion("port_count2 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount2IsNotNull() {
            addCriterion("port_count2 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount2EqualTo(Integer value) {
            addCriterion("port_count2 =", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2NotEqualTo(Integer value) {
            addCriterion("port_count2 <>", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2GreaterThan(Integer value) {
            addCriterion("port_count2 >", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count2 >=", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2LessThan(Integer value) {
            addCriterion("port_count2 <", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2LessThanOrEqualTo(Integer value) {
            addCriterion("port_count2 <=", value, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2In(List<Integer> values) {
            addCriterion("port_count2 in", values, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2NotIn(List<Integer> values) {
            addCriterion("port_count2 not in", values, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2Between(Integer value1, Integer value2) {
            addCriterion("port_count2 between", value1, value2, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortCount2NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count2 not between", value1, value2, "portCount2");
            return (Criteria) this;
        }

        public Criteria andPortType3IsNull() {
            addCriterion("port_type3 is null");
            return (Criteria) this;
        }

        public Criteria andPortType3IsNotNull() {
            addCriterion("port_type3 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType3EqualTo(Integer value) {
            addCriterion("port_type3 =", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3NotEqualTo(Integer value) {
            addCriterion("port_type3 <>", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3GreaterThan(Integer value) {
            addCriterion("port_type3 >", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type3 >=", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3LessThan(Integer value) {
            addCriterion("port_type3 <", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3LessThanOrEqualTo(Integer value) {
            addCriterion("port_type3 <=", value, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3In(List<Integer> values) {
            addCriterion("port_type3 in", values, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3NotIn(List<Integer> values) {
            addCriterion("port_type3 not in", values, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3Between(Integer value1, Integer value2) {
            addCriterion("port_type3 between", value1, value2, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortType3NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type3 not between", value1, value2, "portType3");
            return (Criteria) this;
        }

        public Criteria andPortCount3IsNull() {
            addCriterion("port_count3 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount3IsNotNull() {
            addCriterion("port_count3 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount3EqualTo(Integer value) {
            addCriterion("port_count3 =", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3NotEqualTo(Integer value) {
            addCriterion("port_count3 <>", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3GreaterThan(Integer value) {
            addCriterion("port_count3 >", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count3 >=", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3LessThan(Integer value) {
            addCriterion("port_count3 <", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3LessThanOrEqualTo(Integer value) {
            addCriterion("port_count3 <=", value, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3In(List<Integer> values) {
            addCriterion("port_count3 in", values, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3NotIn(List<Integer> values) {
            addCriterion("port_count3 not in", values, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3Between(Integer value1, Integer value2) {
            addCriterion("port_count3 between", value1, value2, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortCount3NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count3 not between", value1, value2, "portCount3");
            return (Criteria) this;
        }

        public Criteria andPortType4IsNull() {
            addCriterion("port_type4 is null");
            return (Criteria) this;
        }

        public Criteria andPortType4IsNotNull() {
            addCriterion("port_type4 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType4EqualTo(Integer value) {
            addCriterion("port_type4 =", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4NotEqualTo(Integer value) {
            addCriterion("port_type4 <>", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4GreaterThan(Integer value) {
            addCriterion("port_type4 >", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type4 >=", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4LessThan(Integer value) {
            addCriterion("port_type4 <", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4LessThanOrEqualTo(Integer value) {
            addCriterion("port_type4 <=", value, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4In(List<Integer> values) {
            addCriterion("port_type4 in", values, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4NotIn(List<Integer> values) {
            addCriterion("port_type4 not in", values, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4Between(Integer value1, Integer value2) {
            addCriterion("port_type4 between", value1, value2, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortType4NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type4 not between", value1, value2, "portType4");
            return (Criteria) this;
        }

        public Criteria andPortCount4IsNull() {
            addCriterion("port_count4 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount4IsNotNull() {
            addCriterion("port_count4 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount4EqualTo(Integer value) {
            addCriterion("port_count4 =", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4NotEqualTo(Integer value) {
            addCriterion("port_count4 <>", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4GreaterThan(Integer value) {
            addCriterion("port_count4 >", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count4 >=", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4LessThan(Integer value) {
            addCriterion("port_count4 <", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4LessThanOrEqualTo(Integer value) {
            addCriterion("port_count4 <=", value, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4In(List<Integer> values) {
            addCriterion("port_count4 in", values, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4NotIn(List<Integer> values) {
            addCriterion("port_count4 not in", values, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4Between(Integer value1, Integer value2) {
            addCriterion("port_count4 between", value1, value2, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortCount4NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count4 not between", value1, value2, "portCount4");
            return (Criteria) this;
        }

        public Criteria andPortType5IsNull() {
            addCriterion("port_type5 is null");
            return (Criteria) this;
        }

        public Criteria andPortType5IsNotNull() {
            addCriterion("port_type5 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType5EqualTo(Integer value) {
            addCriterion("port_type5 =", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5NotEqualTo(Integer value) {
            addCriterion("port_type5 <>", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5GreaterThan(Integer value) {
            addCriterion("port_type5 >", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type5 >=", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5LessThan(Integer value) {
            addCriterion("port_type5 <", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5LessThanOrEqualTo(Integer value) {
            addCriterion("port_type5 <=", value, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5In(List<Integer> values) {
            addCriterion("port_type5 in", values, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5NotIn(List<Integer> values) {
            addCriterion("port_type5 not in", values, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5Between(Integer value1, Integer value2) {
            addCriterion("port_type5 between", value1, value2, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortType5NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type5 not between", value1, value2, "portType5");
            return (Criteria) this;
        }

        public Criteria andPortCount5IsNull() {
            addCriterion("port_count5 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount5IsNotNull() {
            addCriterion("port_count5 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount5EqualTo(Integer value) {
            addCriterion("port_count5 =", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5NotEqualTo(Integer value) {
            addCriterion("port_count5 <>", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5GreaterThan(Integer value) {
            addCriterion("port_count5 >", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count5 >=", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5LessThan(Integer value) {
            addCriterion("port_count5 <", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5LessThanOrEqualTo(Integer value) {
            addCriterion("port_count5 <=", value, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5In(List<Integer> values) {
            addCriterion("port_count5 in", values, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5NotIn(List<Integer> values) {
            addCriterion("port_count5 not in", values, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5Between(Integer value1, Integer value2) {
            addCriterion("port_count5 between", value1, value2, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortCount5NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count5 not between", value1, value2, "portCount5");
            return (Criteria) this;
        }

        public Criteria andPortType6IsNull() {
            addCriterion("port_type6 is null");
            return (Criteria) this;
        }

        public Criteria andPortType6IsNotNull() {
            addCriterion("port_type6 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType6EqualTo(Integer value) {
            addCriterion("port_type6 =", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6NotEqualTo(Integer value) {
            addCriterion("port_type6 <>", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6GreaterThan(Integer value) {
            addCriterion("port_type6 >", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type6 >=", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6LessThan(Integer value) {
            addCriterion("port_type6 <", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6LessThanOrEqualTo(Integer value) {
            addCriterion("port_type6 <=", value, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6In(List<Integer> values) {
            addCriterion("port_type6 in", values, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6NotIn(List<Integer> values) {
            addCriterion("port_type6 not in", values, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6Between(Integer value1, Integer value2) {
            addCriterion("port_type6 between", value1, value2, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortType6NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type6 not between", value1, value2, "portType6");
            return (Criteria) this;
        }

        public Criteria andPortCount6IsNull() {
            addCriterion("port_count6 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount6IsNotNull() {
            addCriterion("port_count6 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount6EqualTo(Integer value) {
            addCriterion("port_count6 =", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6NotEqualTo(Integer value) {
            addCriterion("port_count6 <>", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6GreaterThan(Integer value) {
            addCriterion("port_count6 >", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count6 >=", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6LessThan(Integer value) {
            addCriterion("port_count6 <", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6LessThanOrEqualTo(Integer value) {
            addCriterion("port_count6 <=", value, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6In(List<Integer> values) {
            addCriterion("port_count6 in", values, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6NotIn(List<Integer> values) {
            addCriterion("port_count6 not in", values, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6Between(Integer value1, Integer value2) {
            addCriterion("port_count6 between", value1, value2, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortCount6NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count6 not between", value1, value2, "portCount6");
            return (Criteria) this;
        }

        public Criteria andPortType7IsNull() {
            addCriterion("port_type7 is null");
            return (Criteria) this;
        }

        public Criteria andPortType7IsNotNull() {
            addCriterion("port_type7 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType7EqualTo(Integer value) {
            addCriterion("port_type7 =", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7NotEqualTo(Integer value) {
            addCriterion("port_type7 <>", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7GreaterThan(Integer value) {
            addCriterion("port_type7 >", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type7 >=", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7LessThan(Integer value) {
            addCriterion("port_type7 <", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7LessThanOrEqualTo(Integer value) {
            addCriterion("port_type7 <=", value, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7In(List<Integer> values) {
            addCriterion("port_type7 in", values, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7NotIn(List<Integer> values) {
            addCriterion("port_type7 not in", values, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7Between(Integer value1, Integer value2) {
            addCriterion("port_type7 between", value1, value2, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortType7NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type7 not between", value1, value2, "portType7");
            return (Criteria) this;
        }

        public Criteria andPortCount7IsNull() {
            addCriterion("port_count7 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount7IsNotNull() {
            addCriterion("port_count7 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount7EqualTo(Integer value) {
            addCriterion("port_count7 =", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7NotEqualTo(Integer value) {
            addCriterion("port_count7 <>", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7GreaterThan(Integer value) {
            addCriterion("port_count7 >", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count7 >=", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7LessThan(Integer value) {
            addCriterion("port_count7 <", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7LessThanOrEqualTo(Integer value) {
            addCriterion("port_count7 <=", value, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7In(List<Integer> values) {
            addCriterion("port_count7 in", values, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7NotIn(List<Integer> values) {
            addCriterion("port_count7 not in", values, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7Between(Integer value1, Integer value2) {
            addCriterion("port_count7 between", value1, value2, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortCount7NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count7 not between", value1, value2, "portCount7");
            return (Criteria) this;
        }

        public Criteria andPortType8IsNull() {
            addCriterion("port_type8 is null");
            return (Criteria) this;
        }

        public Criteria andPortType8IsNotNull() {
            addCriterion("port_type8 is not null");
            return (Criteria) this;
        }

        public Criteria andPortType8EqualTo(Integer value) {
            addCriterion("port_type8 =", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8NotEqualTo(Integer value) {
            addCriterion("port_type8 <>", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8GreaterThan(Integer value) {
            addCriterion("port_type8 >", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type8 >=", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8LessThan(Integer value) {
            addCriterion("port_type8 <", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8LessThanOrEqualTo(Integer value) {
            addCriterion("port_type8 <=", value, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8In(List<Integer> values) {
            addCriterion("port_type8 in", values, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8NotIn(List<Integer> values) {
            addCriterion("port_type8 not in", values, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8Between(Integer value1, Integer value2) {
            addCriterion("port_type8 between", value1, value2, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortType8NotBetween(Integer value1, Integer value2) {
            addCriterion("port_type8 not between", value1, value2, "portType8");
            return (Criteria) this;
        }

        public Criteria andPortCount8IsNull() {
            addCriterion("port_count8 is null");
            return (Criteria) this;
        }

        public Criteria andPortCount8IsNotNull() {
            addCriterion("port_count8 is not null");
            return (Criteria) this;
        }

        public Criteria andPortCount8EqualTo(Integer value) {
            addCriterion("port_count8 =", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8NotEqualTo(Integer value) {
            addCriterion("port_count8 <>", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8GreaterThan(Integer value) {
            addCriterion("port_count8 >", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8GreaterThanOrEqualTo(Integer value) {
            addCriterion("port_count8 >=", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8LessThan(Integer value) {
            addCriterion("port_count8 <", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8LessThanOrEqualTo(Integer value) {
            addCriterion("port_count8 <=", value, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8In(List<Integer> values) {
            addCriterion("port_count8 in", values, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8NotIn(List<Integer> values) {
            addCriterion("port_count8 not in", values, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8Between(Integer value1, Integer value2) {
            addCriterion("port_count8 between", value1, value2, "portCount8");
            return (Criteria) this;
        }

        public Criteria andPortCount8NotBetween(Integer value1, Integer value2) {
            addCriterion("port_count8 not between", value1, value2, "portCount8");
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