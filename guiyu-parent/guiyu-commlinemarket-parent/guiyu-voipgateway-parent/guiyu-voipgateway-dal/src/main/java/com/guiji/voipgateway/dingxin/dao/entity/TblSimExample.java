package com.guiji.voipgateway.dingxin.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblSimExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TblSimExample() {
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

        public Criteria andImsiIsNull() {
            addCriterion("imsi is null");
            return (Criteria) this;
        }

        public Criteria andImsiIsNotNull() {
            addCriterion("imsi is not null");
            return (Criteria) this;
        }

        public Criteria andImsiEqualTo(String value) {
            addCriterion("imsi =", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotEqualTo(String value) {
            addCriterion("imsi <>", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThan(String value) {
            addCriterion("imsi >", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThanOrEqualTo(String value) {
            addCriterion("imsi >=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThan(String value) {
            addCriterion("imsi <", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThanOrEqualTo(String value) {
            addCriterion("imsi <=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLike(String value) {
            addCriterion("imsi like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotLike(String value) {
            addCriterion("imsi not like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiIn(List<String> values) {
            addCriterion("imsi in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotIn(List<String> values) {
            addCriterion("imsi not in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiBetween(String value1, String value2) {
            addCriterion("imsi between", value1, value2, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotBetween(String value1, String value2) {
            addCriterion("imsi not between", value1, value2, "imsi");
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

        public Criteria andGrpUuidIsNull() {
            addCriterion("grp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andGrpUuidIsNotNull() {
            addCriterion("grp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andGrpUuidEqualTo(Integer value) {
            addCriterion("grp_uuid =", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidNotEqualTo(Integer value) {
            addCriterion("grp_uuid <>", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidGreaterThan(Integer value) {
            addCriterion("grp_uuid >", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("grp_uuid >=", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidLessThan(Integer value) {
            addCriterion("grp_uuid <", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("grp_uuid <=", value, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidIn(List<Integer> values) {
            addCriterion("grp_uuid in", values, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidNotIn(List<Integer> values) {
            addCriterion("grp_uuid not in", values, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidBetween(Integer value1, Integer value2) {
            addCriterion("grp_uuid between", value1, value2, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andGrpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("grp_uuid not between", value1, value2, "grpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidIsNull() {
            addCriterion("promotion_grp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidIsNotNull() {
            addCriterion("promotion_grp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidEqualTo(Integer value) {
            addCriterion("promotion_grp_uuid =", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidNotEqualTo(Integer value) {
            addCriterion("promotion_grp_uuid <>", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidGreaterThan(Integer value) {
            addCriterion("promotion_grp_uuid >", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("promotion_grp_uuid >=", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidLessThan(Integer value) {
            addCriterion("promotion_grp_uuid <", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("promotion_grp_uuid <=", value, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidIn(List<Integer> values) {
            addCriterion("promotion_grp_uuid in", values, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidNotIn(List<Integer> values) {
            addCriterion("promotion_grp_uuid not in", values, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidBetween(Integer value1, Integer value2) {
            addCriterion("promotion_grp_uuid between", value1, value2, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPromotionGrpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("promotion_grp_uuid not between", value1, value2, "promotionGrpUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidIsNull() {
            addCriterion("orig_zone_uuid is null");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidIsNotNull() {
            addCriterion("orig_zone_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidEqualTo(Integer value) {
            addCriterion("orig_zone_uuid =", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidNotEqualTo(Integer value) {
            addCriterion("orig_zone_uuid <>", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidGreaterThan(Integer value) {
            addCriterion("orig_zone_uuid >", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("orig_zone_uuid >=", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidLessThan(Integer value) {
            addCriterion("orig_zone_uuid <", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidLessThanOrEqualTo(Integer value) {
            addCriterion("orig_zone_uuid <=", value, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidIn(List<Integer> values) {
            addCriterion("orig_zone_uuid in", values, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidNotIn(List<Integer> values) {
            addCriterion("orig_zone_uuid not in", values, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidBetween(Integer value1, Integer value2) {
            addCriterion("orig_zone_uuid between", value1, value2, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andOrigZoneUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("orig_zone_uuid not between", value1, value2, "origZoneUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidIsNull() {
            addCriterion("last_site_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidIsNotNull() {
            addCriterion("last_site_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidEqualTo(Integer value) {
            addCriterion("last_site_uuid =", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidNotEqualTo(Integer value) {
            addCriterion("last_site_uuid <>", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidGreaterThan(Integer value) {
            addCriterion("last_site_uuid >", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_site_uuid >=", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidLessThan(Integer value) {
            addCriterion("last_site_uuid <", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidLessThanOrEqualTo(Integer value) {
            addCriterion("last_site_uuid <=", value, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidIn(List<Integer> values) {
            addCriterion("last_site_uuid in", values, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidNotIn(List<Integer> values) {
            addCriterion("last_site_uuid not in", values, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidBetween(Integer value1, Integer value2) {
            addCriterion("last_site_uuid between", value1, value2, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andLastSiteUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("last_site_uuid not between", value1, value2, "lastSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidIsNull() {
            addCriterion("next_site_uuid is null");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidIsNotNull() {
            addCriterion("next_site_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidEqualTo(Integer value) {
            addCriterion("next_site_uuid =", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidNotEqualTo(Integer value) {
            addCriterion("next_site_uuid <>", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidGreaterThan(Integer value) {
            addCriterion("next_site_uuid >", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("next_site_uuid >=", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidLessThan(Integer value) {
            addCriterion("next_site_uuid <", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidLessThanOrEqualTo(Integer value) {
            addCriterion("next_site_uuid <=", value, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidIn(List<Integer> values) {
            addCriterion("next_site_uuid in", values, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidNotIn(List<Integer> values) {
            addCriterion("next_site_uuid not in", values, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidBetween(Integer value1, Integer value2) {
            addCriterion("next_site_uuid between", value1, value2, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andNextSiteUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("next_site_uuid not between", value1, value2, "nextSiteUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidIsNull() {
            addCriterion("bkp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andBkpUuidIsNotNull() {
            addCriterion("bkp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andBkpUuidEqualTo(Integer value) {
            addCriterion("bkp_uuid =", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidNotEqualTo(Integer value) {
            addCriterion("bkp_uuid <>", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidGreaterThan(Integer value) {
            addCriterion("bkp_uuid >", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("bkp_uuid >=", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidLessThan(Integer value) {
            addCriterion("bkp_uuid <", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("bkp_uuid <=", value, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidIn(List<Integer> values) {
            addCriterion("bkp_uuid in", values, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidNotIn(List<Integer> values) {
            addCriterion("bkp_uuid not in", values, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidBetween(Integer value1, Integer value2) {
            addCriterion("bkp_uuid between", value1, value2, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andBkpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("bkp_uuid not between", value1, value2, "bkpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidIsNull() {
            addCriterion("local_gwp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidIsNotNull() {
            addCriterion("local_gwp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidEqualTo(Integer value) {
            addCriterion("local_gwp_uuid =", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidNotEqualTo(Integer value) {
            addCriterion("local_gwp_uuid <>", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidGreaterThan(Integer value) {
            addCriterion("local_gwp_uuid >", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("local_gwp_uuid >=", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidLessThan(Integer value) {
            addCriterion("local_gwp_uuid <", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("local_gwp_uuid <=", value, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidIn(List<Integer> values) {
            addCriterion("local_gwp_uuid in", values, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidNotIn(List<Integer> values) {
            addCriterion("local_gwp_uuid not in", values, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidBetween(Integer value1, Integer value2) {
            addCriterion("local_gwp_uuid between", value1, value2, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLocalGwpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("local_gwp_uuid not between", value1, value2, "localGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidIsNull() {
            addCriterion("lock_gwp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidIsNotNull() {
            addCriterion("lock_gwp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidEqualTo(Integer value) {
            addCriterion("lock_gwp_uuid =", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidNotEqualTo(Integer value) {
            addCriterion("lock_gwp_uuid <>", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidGreaterThan(Integer value) {
            addCriterion("lock_gwp_uuid >", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_gwp_uuid >=", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidLessThan(Integer value) {
            addCriterion("lock_gwp_uuid <", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("lock_gwp_uuid <=", value, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidIn(List<Integer> values) {
            addCriterion("lock_gwp_uuid in", values, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidNotIn(List<Integer> values) {
            addCriterion("lock_gwp_uuid not in", values, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidBetween(Integer value1, Integer value2) {
            addCriterion("lock_gwp_uuid between", value1, value2, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andLockGwpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_gwp_uuid not between", value1, value2, "lockGwpUuid");
            return (Criteria) this;
        }

        public Criteria andIccIdIsNull() {
            addCriterion("icc_id is null");
            return (Criteria) this;
        }

        public Criteria andIccIdIsNotNull() {
            addCriterion("icc_id is not null");
            return (Criteria) this;
        }

        public Criteria andIccIdEqualTo(String value) {
            addCriterion("icc_id =", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdNotEqualTo(String value) {
            addCriterion("icc_id <>", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdGreaterThan(String value) {
            addCriterion("icc_id >", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdGreaterThanOrEqualTo(String value) {
            addCriterion("icc_id >=", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdLessThan(String value) {
            addCriterion("icc_id <", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdLessThanOrEqualTo(String value) {
            addCriterion("icc_id <=", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdLike(String value) {
            addCriterion("icc_id like", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdNotLike(String value) {
            addCriterion("icc_id not like", value, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdIn(List<String> values) {
            addCriterion("icc_id in", values, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdNotIn(List<String> values) {
            addCriterion("icc_id not in", values, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdBetween(String value1, String value2) {
            addCriterion("icc_id between", value1, value2, "iccId");
            return (Criteria) this;
        }

        public Criteria andIccIdNotBetween(String value1, String value2) {
            addCriterion("icc_id not between", value1, value2, "iccId");
            return (Criteria) this;
        }

        public Criteria andBindImeiIsNull() {
            addCriterion("bind_imei is null");
            return (Criteria) this;
        }

        public Criteria andBindImeiIsNotNull() {
            addCriterion("bind_imei is not null");
            return (Criteria) this;
        }

        public Criteria andBindImeiEqualTo(String value) {
            addCriterion("bind_imei =", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiNotEqualTo(String value) {
            addCriterion("bind_imei <>", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiGreaterThan(String value) {
            addCriterion("bind_imei >", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiGreaterThanOrEqualTo(String value) {
            addCriterion("bind_imei >=", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiLessThan(String value) {
            addCriterion("bind_imei <", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiLessThanOrEqualTo(String value) {
            addCriterion("bind_imei <=", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiLike(String value) {
            addCriterion("bind_imei like", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiNotLike(String value) {
            addCriterion("bind_imei not like", value, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiIn(List<String> values) {
            addCriterion("bind_imei in", values, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiNotIn(List<String> values) {
            addCriterion("bind_imei not in", values, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiBetween(String value1, String value2) {
            addCriterion("bind_imei between", value1, value2, "bindImei");
            return (Criteria) this;
        }

        public Criteria andBindImeiNotBetween(String value1, String value2) {
            addCriterion("bind_imei not between", value1, value2, "bindImei");
            return (Criteria) this;
        }

        public Criteria andPin1CodeIsNull() {
            addCriterion("pin1_code is null");
            return (Criteria) this;
        }

        public Criteria andPin1CodeIsNotNull() {
            addCriterion("pin1_code is not null");
            return (Criteria) this;
        }

        public Criteria andPin1CodeEqualTo(String value) {
            addCriterion("pin1_code =", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeNotEqualTo(String value) {
            addCriterion("pin1_code <>", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeGreaterThan(String value) {
            addCriterion("pin1_code >", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeGreaterThanOrEqualTo(String value) {
            addCriterion("pin1_code >=", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeLessThan(String value) {
            addCriterion("pin1_code <", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeLessThanOrEqualTo(String value) {
            addCriterion("pin1_code <=", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeLike(String value) {
            addCriterion("pin1_code like", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeNotLike(String value) {
            addCriterion("pin1_code not like", value, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeIn(List<String> values) {
            addCriterion("pin1_code in", values, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeNotIn(List<String> values) {
            addCriterion("pin1_code not in", values, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeBetween(String value1, String value2) {
            addCriterion("pin1_code between", value1, value2, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin1CodeNotBetween(String value1, String value2) {
            addCriterion("pin1_code not between", value1, value2, "pin1Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeIsNull() {
            addCriterion("pin2_code is null");
            return (Criteria) this;
        }

        public Criteria andPin2CodeIsNotNull() {
            addCriterion("pin2_code is not null");
            return (Criteria) this;
        }

        public Criteria andPin2CodeEqualTo(String value) {
            addCriterion("pin2_code =", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeNotEqualTo(String value) {
            addCriterion("pin2_code <>", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeGreaterThan(String value) {
            addCriterion("pin2_code >", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeGreaterThanOrEqualTo(String value) {
            addCriterion("pin2_code >=", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeLessThan(String value) {
            addCriterion("pin2_code <", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeLessThanOrEqualTo(String value) {
            addCriterion("pin2_code <=", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeLike(String value) {
            addCriterion("pin2_code like", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeNotLike(String value) {
            addCriterion("pin2_code not like", value, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeIn(List<String> values) {
            addCriterion("pin2_code in", values, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeNotIn(List<String> values) {
            addCriterion("pin2_code not in", values, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeBetween(String value1, String value2) {
            addCriterion("pin2_code between", value1, value2, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPin2CodeNotBetween(String value1, String value2) {
            addCriterion("pin2_code not between", value1, value2, "pin2Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeIsNull() {
            addCriterion("puk1_code is null");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeIsNotNull() {
            addCriterion("puk1_code is not null");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeEqualTo(String value) {
            addCriterion("puk1_code =", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeNotEqualTo(String value) {
            addCriterion("puk1_code <>", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeGreaterThan(String value) {
            addCriterion("puk1_code >", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeGreaterThanOrEqualTo(String value) {
            addCriterion("puk1_code >=", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeLessThan(String value) {
            addCriterion("puk1_code <", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeLessThanOrEqualTo(String value) {
            addCriterion("puk1_code <=", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeLike(String value) {
            addCriterion("puk1_code like", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeNotLike(String value) {
            addCriterion("puk1_code not like", value, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeIn(List<String> values) {
            addCriterion("puk1_code in", values, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeNotIn(List<String> values) {
            addCriterion("puk1_code not in", values, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeBetween(String value1, String value2) {
            addCriterion("puk1_code between", value1, value2, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk1CodeNotBetween(String value1, String value2) {
            addCriterion("puk1_code not between", value1, value2, "puk1Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeIsNull() {
            addCriterion("puk2_code is null");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeIsNotNull() {
            addCriterion("puk2_code is not null");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeEqualTo(String value) {
            addCriterion("puk2_code =", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeNotEqualTo(String value) {
            addCriterion("puk2_code <>", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeGreaterThan(String value) {
            addCriterion("puk2_code >", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeGreaterThanOrEqualTo(String value) {
            addCriterion("puk2_code >=", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeLessThan(String value) {
            addCriterion("puk2_code <", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeLessThanOrEqualTo(String value) {
            addCriterion("puk2_code <=", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeLike(String value) {
            addCriterion("puk2_code like", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeNotLike(String value) {
            addCriterion("puk2_code not like", value, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeIn(List<String> values) {
            addCriterion("puk2_code in", values, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeNotIn(List<String> values) {
            addCriterion("puk2_code not in", values, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeBetween(String value1, String value2) {
            addCriterion("puk2_code between", value1, value2, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andPuk2CodeNotBetween(String value1, String value2) {
            addCriterion("puk2_code not between", value1, value2, "puk2Code");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("`operator` is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("`operator` is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("`operator` =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("`operator` <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("`operator` >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("`operator` >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("`operator` <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("`operator` <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("`operator` like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("`operator` not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("`operator` in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("`operator` not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("`operator` between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("`operator` not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andSimNumberIsNull() {
            addCriterion("sim_number is null");
            return (Criteria) this;
        }

        public Criteria andSimNumberIsNotNull() {
            addCriterion("sim_number is not null");
            return (Criteria) this;
        }

        public Criteria andSimNumberEqualTo(String value) {
            addCriterion("sim_number =", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberNotEqualTo(String value) {
            addCriterion("sim_number <>", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberGreaterThan(String value) {
            addCriterion("sim_number >", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberGreaterThanOrEqualTo(String value) {
            addCriterion("sim_number >=", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberLessThan(String value) {
            addCriterion("sim_number <", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberLessThanOrEqualTo(String value) {
            addCriterion("sim_number <=", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberLike(String value) {
            addCriterion("sim_number like", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberNotLike(String value) {
            addCriterion("sim_number not like", value, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberIn(List<String> values) {
            addCriterion("sim_number in", values, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberNotIn(List<String> values) {
            addCriterion("sim_number not in", values, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberBetween(String value1, String value2) {
            addCriterion("sim_number between", value1, value2, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSimNumberNotBetween(String value1, String value2) {
            addCriterion("sim_number not between", value1, value2, "simNumber");
            return (Criteria) this;
        }

        public Criteria andSmscIsNull() {
            addCriterion("smsc is null");
            return (Criteria) this;
        }

        public Criteria andSmscIsNotNull() {
            addCriterion("smsc is not null");
            return (Criteria) this;
        }

        public Criteria andSmscEqualTo(String value) {
            addCriterion("smsc =", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscNotEqualTo(String value) {
            addCriterion("smsc <>", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscGreaterThan(String value) {
            addCriterion("smsc >", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscGreaterThanOrEqualTo(String value) {
            addCriterion("smsc >=", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscLessThan(String value) {
            addCriterion("smsc <", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscLessThanOrEqualTo(String value) {
            addCriterion("smsc <=", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscLike(String value) {
            addCriterion("smsc like", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscNotLike(String value) {
            addCriterion("smsc not like", value, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscIn(List<String> values) {
            addCriterion("smsc in", values, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscNotIn(List<String> values) {
            addCriterion("smsc not in", values, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscBetween(String value1, String value2) {
            addCriterion("smsc between", value1, value2, "smsc");
            return (Criteria) this;
        }

        public Criteria andSmscNotBetween(String value1, String value2) {
            addCriterion("smsc not between", value1, value2, "smsc");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeIsNull() {
            addCriterion("money_type is null");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeIsNotNull() {
            addCriterion("money_type is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeEqualTo(Integer value) {
            addCriterion("money_type =", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeNotEqualTo(Integer value) {
            addCriterion("money_type <>", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeGreaterThan(Integer value) {
            addCriterion("money_type >", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("money_type >=", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeLessThan(Integer value) {
            addCriterion("money_type <", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("money_type <=", value, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeIn(List<Integer> values) {
            addCriterion("money_type in", values, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeNotIn(List<Integer> values) {
            addCriterion("money_type not in", values, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeBetween(Integer value1, Integer value2) {
            addCriterion("money_type between", value1, value2, "moneyType");
            return (Criteria) this;
        }

        public Criteria andMoneyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("money_type not between", value1, value2, "moneyType");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeIsNull() {
            addCriterion("prepaid_fee is null");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeIsNotNull() {
            addCriterion("prepaid_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeEqualTo(Long value) {
            addCriterion("prepaid_fee =", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeNotEqualTo(Long value) {
            addCriterion("prepaid_fee <>", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeGreaterThan(Long value) {
            addCriterion("prepaid_fee >", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeGreaterThanOrEqualTo(Long value) {
            addCriterion("prepaid_fee >=", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeLessThan(Long value) {
            addCriterion("prepaid_fee <", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeLessThanOrEqualTo(Long value) {
            addCriterion("prepaid_fee <=", value, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeIn(List<Long> values) {
            addCriterion("prepaid_fee in", values, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeNotIn(List<Long> values) {
            addCriterion("prepaid_fee not in", values, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeBetween(Long value1, Long value2) {
            addCriterion("prepaid_fee between", value1, value2, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andPrepaidFeeNotBetween(Long value1, Long value2) {
            addCriterion("prepaid_fee not between", value1, value2, "prepaidFee");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNull() {
            addCriterion("total_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNotNull() {
            addCriterion("total_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCostEqualTo(Long value) {
            addCriterion("total_cost =", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotEqualTo(Long value) {
            addCriterion("total_cost <>", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThan(Long value) {
            addCriterion("total_cost >", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThanOrEqualTo(Long value) {
            addCriterion("total_cost >=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThan(Long value) {
            addCriterion("total_cost <", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThanOrEqualTo(Long value) {
            addCriterion("total_cost <=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostIn(List<Long> values) {
            addCriterion("total_cost in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotIn(List<Long> values) {
            addCriterion("total_cost not in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostBetween(Long value1, Long value2) {
            addCriterion("total_cost between", value1, value2, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotBetween(Long value1, Long value2) {
            addCriterion("total_cost not between", value1, value2, "totalCost");
            return (Criteria) this;
        }

        public Criteria andLastBalanceIsNull() {
            addCriterion("last_balance is null");
            return (Criteria) this;
        }

        public Criteria andLastBalanceIsNotNull() {
            addCriterion("last_balance is not null");
            return (Criteria) this;
        }

        public Criteria andLastBalanceEqualTo(Float value) {
            addCriterion("last_balance =", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceNotEqualTo(Float value) {
            addCriterion("last_balance <>", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceGreaterThan(Float value) {
            addCriterion("last_balance >", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceGreaterThanOrEqualTo(Float value) {
            addCriterion("last_balance >=", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceLessThan(Float value) {
            addCriterion("last_balance <", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceLessThanOrEqualTo(Float value) {
            addCriterion("last_balance <=", value, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceIn(List<Float> values) {
            addCriterion("last_balance in", values, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceNotIn(List<Float> values) {
            addCriterion("last_balance not in", values, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceBetween(Float value1, Float value2) {
            addCriterion("last_balance between", value1, value2, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andLastBalanceNotBetween(Float value1, Float value2) {
            addCriterion("last_balance not between", value1, value2, "lastBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceIsNull() {
            addCriterion("cur_balance is null");
            return (Criteria) this;
        }

        public Criteria andCurBalanceIsNotNull() {
            addCriterion("cur_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCurBalanceEqualTo(Float value) {
            addCriterion("cur_balance =", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceNotEqualTo(Float value) {
            addCriterion("cur_balance <>", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceGreaterThan(Float value) {
            addCriterion("cur_balance >", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceGreaterThanOrEqualTo(Float value) {
            addCriterion("cur_balance >=", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceLessThan(Float value) {
            addCriterion("cur_balance <", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceLessThanOrEqualTo(Float value) {
            addCriterion("cur_balance <=", value, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceIn(List<Float> values) {
            addCriterion("cur_balance in", values, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceNotIn(List<Float> values) {
            addCriterion("cur_balance not in", values, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceBetween(Float value1, Float value2) {
            addCriterion("cur_balance between", value1, value2, "curBalance");
            return (Criteria) this;
        }

        public Criteria andCurBalanceNotBetween(Float value1, Float value2) {
            addCriterion("cur_balance not between", value1, value2, "curBalance");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeIsNull() {
            addCriterion("left_call_time is null");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeIsNotNull() {
            addCriterion("left_call_time is not null");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeEqualTo(Integer value) {
            addCriterion("left_call_time =", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeNotEqualTo(Integer value) {
            addCriterion("left_call_time <>", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeGreaterThan(Integer value) {
            addCriterion("left_call_time >", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("left_call_time >=", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeLessThan(Integer value) {
            addCriterion("left_call_time <", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeLessThanOrEqualTo(Integer value) {
            addCriterion("left_call_time <=", value, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeIn(List<Integer> values) {
            addCriterion("left_call_time in", values, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeNotIn(List<Integer> values) {
            addCriterion("left_call_time not in", values, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeBetween(Integer value1, Integer value2) {
            addCriterion("left_call_time between", value1, value2, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andLeftCallTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("left_call_time not between", value1, value2, "leftCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeIsNull() {
            addCriterion("prom_call_time is null");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeIsNotNull() {
            addCriterion("prom_call_time is not null");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeEqualTo(Integer value) {
            addCriterion("prom_call_time =", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeNotEqualTo(Integer value) {
            addCriterion("prom_call_time <>", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeGreaterThan(Integer value) {
            addCriterion("prom_call_time >", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prom_call_time >=", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeLessThan(Integer value) {
            addCriterion("prom_call_time <", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeLessThanOrEqualTo(Integer value) {
            addCriterion("prom_call_time <=", value, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeIn(List<Integer> values) {
            addCriterion("prom_call_time in", values, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeNotIn(List<Integer> values) {
            addCriterion("prom_call_time not in", values, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeBetween(Integer value1, Integer value2) {
            addCriterion("prom_call_time between", value1, value2, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andPromCallTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("prom_call_time not between", value1, value2, "promCallTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeIsNull() {
            addCriterion("last_group_time is null");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeIsNotNull() {
            addCriterion("last_group_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeEqualTo(Date value) {
            addCriterion("last_group_time =", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeNotEqualTo(Date value) {
            addCriterion("last_group_time <>", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeGreaterThan(Date value) {
            addCriterion("last_group_time >", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_group_time >=", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeLessThan(Date value) {
            addCriterion("last_group_time <", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_group_time <=", value, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeIn(List<Date> values) {
            addCriterion("last_group_time in", values, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeNotIn(List<Date> values) {
            addCriterion("last_group_time not in", values, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeBetween(Date value1, Date value2) {
            addCriterion("last_group_time between", value1, value2, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastGroupTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_group_time not between", value1, value2, "lastGroupTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeIsNull() {
            addCriterion("last_load_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeIsNotNull() {
            addCriterion("last_load_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeEqualTo(Date value) {
            addCriterion("last_load_time =", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeNotEqualTo(Date value) {
            addCriterion("last_load_time <>", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeGreaterThan(Date value) {
            addCriterion("last_load_time >", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_load_time >=", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeLessThan(Date value) {
            addCriterion("last_load_time <", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_load_time <=", value, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeIn(List<Date> values) {
            addCriterion("last_load_time in", values, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeNotIn(List<Date> values) {
            addCriterion("last_load_time not in", values, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeBetween(Date value1, Date value2) {
            addCriterion("last_load_time between", value1, value2, "lastLoadTime");
            return (Criteria) this;
        }

        public Criteria andLastLoadTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_load_time not between", value1, value2, "lastLoadTime");
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

        public Criteria andLastBindTimeIsNull() {
            addCriterion("last_bind_time is null");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeIsNotNull() {
            addCriterion("last_bind_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeEqualTo(Date value) {
            addCriterion("last_bind_time =", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeNotEqualTo(Date value) {
            addCriterion("last_bind_time <>", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeGreaterThan(Date value) {
            addCriterion("last_bind_time >", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_bind_time >=", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeLessThan(Date value) {
            addCriterion("last_bind_time <", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_bind_time <=", value, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeIn(List<Date> values) {
            addCriterion("last_bind_time in", values, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeNotIn(List<Date> values) {
            addCriterion("last_bind_time not in", values, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeBetween(Date value1, Date value2) {
            addCriterion("last_bind_time between", value1, value2, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastBindTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_bind_time not between", value1, value2, "lastBindTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeIsNull() {
            addCriterion("last_used_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeIsNotNull() {
            addCriterion("last_used_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeEqualTo(Date value) {
            addCriterion("last_used_time =", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotEqualTo(Date value) {
            addCriterion("last_used_time <>", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeGreaterThan(Date value) {
            addCriterion("last_used_time >", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_used_time >=", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeLessThan(Date value) {
            addCriterion("last_used_time <", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_used_time <=", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeIn(List<Date> values) {
            addCriterion("last_used_time in", values, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotIn(List<Date> values) {
            addCriterion("last_used_time not in", values, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeBetween(Date value1, Date value2) {
            addCriterion("last_used_time between", value1, value2, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_used_time not between", value1, value2, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeIsNull() {
            addCriterion("last_prom_time is null");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeIsNotNull() {
            addCriterion("last_prom_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeEqualTo(Date value) {
            addCriterion("last_prom_time =", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeNotEqualTo(Date value) {
            addCriterion("last_prom_time <>", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeGreaterThan(Date value) {
            addCriterion("last_prom_time >", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_prom_time >=", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeLessThan(Date value) {
            addCriterion("last_prom_time <", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_prom_time <=", value, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeIn(List<Date> values) {
            addCriterion("last_prom_time in", values, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeNotIn(List<Date> values) {
            addCriterion("last_prom_time not in", values, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeBetween(Date value1, Date value2) {
            addCriterion("last_prom_time between", value1, value2, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastPromTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_prom_time not between", value1, value2, "lastPromTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeIsNull() {
            addCriterion("last_balance_time is null");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeIsNotNull() {
            addCriterion("last_balance_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeEqualTo(Date value) {
            addCriterion("last_balance_time =", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeNotEqualTo(Date value) {
            addCriterion("last_balance_time <>", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeGreaterThan(Date value) {
            addCriterion("last_balance_time >", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_balance_time >=", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeLessThan(Date value) {
            addCriterion("last_balance_time <", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_balance_time <=", value, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeIn(List<Date> values) {
            addCriterion("last_balance_time in", values, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeNotIn(List<Date> values) {
            addCriterion("last_balance_time not in", values, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeBetween(Date value1, Date value2) {
            addCriterion("last_balance_time between", value1, value2, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andLastBalanceTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_balance_time not between", value1, value2, "lastBalanceTime");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonIsNull() {
            addCriterion("deactive_reason is null");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonIsNotNull() {
            addCriterion("deactive_reason is not null");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonEqualTo(Integer value) {
            addCriterion("deactive_reason =", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonNotEqualTo(Integer value) {
            addCriterion("deactive_reason <>", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonGreaterThan(Integer value) {
            addCriterion("deactive_reason >", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("deactive_reason >=", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonLessThan(Integer value) {
            addCriterion("deactive_reason <", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonLessThanOrEqualTo(Integer value) {
            addCriterion("deactive_reason <=", value, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonIn(List<Integer> values) {
            addCriterion("deactive_reason in", values, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonNotIn(List<Integer> values) {
            addCriterion("deactive_reason not in", values, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonBetween(Integer value1, Integer value2) {
            addCriterion("deactive_reason between", value1, value2, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andDeactiveReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("deactive_reason not between", value1, value2, "deactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonIsNull() {
            addCriterion("last_deactive_reason is null");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonIsNotNull() {
            addCriterion("last_deactive_reason is not null");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonEqualTo(Integer value) {
            addCriterion("last_deactive_reason =", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonNotEqualTo(Integer value) {
            addCriterion("last_deactive_reason <>", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonGreaterThan(Integer value) {
            addCriterion("last_deactive_reason >", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_deactive_reason >=", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonLessThan(Integer value) {
            addCriterion("last_deactive_reason <", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonLessThanOrEqualTo(Integer value) {
            addCriterion("last_deactive_reason <=", value, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonIn(List<Integer> values) {
            addCriterion("last_deactive_reason in", values, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonNotIn(List<Integer> values) {
            addCriterion("last_deactive_reason not in", values, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonBetween(Integer value1, Integer value2) {
            addCriterion("last_deactive_reason between", value1, value2, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andLastDeactiveReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("last_deactive_reason not between", value1, value2, "lastDeactiveReason");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagIsNull() {
            addCriterion("blocked_flag is null");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagIsNotNull() {
            addCriterion("blocked_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagEqualTo(Integer value) {
            addCriterion("blocked_flag =", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagNotEqualTo(Integer value) {
            addCriterion("blocked_flag <>", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagGreaterThan(Integer value) {
            addCriterion("blocked_flag >", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("blocked_flag >=", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagLessThan(Integer value) {
            addCriterion("blocked_flag <", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagLessThanOrEqualTo(Integer value) {
            addCriterion("blocked_flag <=", value, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagIn(List<Integer> values) {
            addCriterion("blocked_flag in", values, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagNotIn(List<Integer> values) {
            addCriterion("blocked_flag not in", values, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagBetween(Integer value1, Integer value2) {
            addCriterion("blocked_flag between", value1, value2, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andBlockedFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("blocked_flag not between", value1, value2, "blockedFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagIsNull() {
            addCriterion("low_balance_flag is null");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagIsNotNull() {
            addCriterion("low_balance_flag is not null");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagEqualTo(Integer value) {
            addCriterion("low_balance_flag =", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagNotEqualTo(Integer value) {
            addCriterion("low_balance_flag <>", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagGreaterThan(Integer value) {
            addCriterion("low_balance_flag >", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("low_balance_flag >=", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagLessThan(Integer value) {
            addCriterion("low_balance_flag <", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagLessThanOrEqualTo(Integer value) {
            addCriterion("low_balance_flag <=", value, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagIn(List<Integer> values) {
            addCriterion("low_balance_flag in", values, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagNotIn(List<Integer> values) {
            addCriterion("low_balance_flag not in", values, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagBetween(Integer value1, Integer value2) {
            addCriterion("low_balance_flag between", value1, value2, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andLowBalanceFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("low_balance_flag not between", value1, value2, "lowBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagIsNull() {
            addCriterion("no_balance_flag is null");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagIsNotNull() {
            addCriterion("no_balance_flag is not null");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagEqualTo(Integer value) {
            addCriterion("no_balance_flag =", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagNotEqualTo(Integer value) {
            addCriterion("no_balance_flag <>", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagGreaterThan(Integer value) {
            addCriterion("no_balance_flag >", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("no_balance_flag >=", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagLessThan(Integer value) {
            addCriterion("no_balance_flag <", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagLessThanOrEqualTo(Integer value) {
            addCriterion("no_balance_flag <=", value, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagIn(List<Integer> values) {
            addCriterion("no_balance_flag in", values, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagNotIn(List<Integer> values) {
            addCriterion("no_balance_flag not in", values, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagBetween(Integer value1, Integer value2) {
            addCriterion("no_balance_flag between", value1, value2, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andNoBalanceFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("no_balance_flag not between", value1, value2, "noBalanceFlag");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusIsNull() {
            addCriterion("promotion_status is null");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusIsNotNull() {
            addCriterion("promotion_status is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusEqualTo(Integer value) {
            addCriterion("promotion_status =", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusNotEqualTo(Integer value) {
            addCriterion("promotion_status <>", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusGreaterThan(Integer value) {
            addCriterion("promotion_status >", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("promotion_status >=", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusLessThan(Integer value) {
            addCriterion("promotion_status <", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusLessThanOrEqualTo(Integer value) {
            addCriterion("promotion_status <=", value, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusIn(List<Integer> values) {
            addCriterion("promotion_status in", values, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusNotIn(List<Integer> values) {
            addCriterion("promotion_status not in", values, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusBetween(Integer value1, Integer value2) {
            addCriterion("promotion_status between", value1, value2, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("promotion_status not between", value1, value2, "promotionStatus");
            return (Criteria) this;
        }

        public Criteria andPromotionCountIsNull() {
            addCriterion("promotion_count is null");
            return (Criteria) this;
        }

        public Criteria andPromotionCountIsNotNull() {
            addCriterion("promotion_count is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionCountEqualTo(Integer value) {
            addCriterion("promotion_count =", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountNotEqualTo(Integer value) {
            addCriterion("promotion_count <>", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountGreaterThan(Integer value) {
            addCriterion("promotion_count >", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("promotion_count >=", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountLessThan(Integer value) {
            addCriterion("promotion_count <", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountLessThanOrEqualTo(Integer value) {
            addCriterion("promotion_count <=", value, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountIn(List<Integer> values) {
            addCriterion("promotion_count in", values, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountNotIn(List<Integer> values) {
            addCriterion("promotion_count not in", values, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountBetween(Integer value1, Integer value2) {
            addCriterion("promotion_count between", value1, value2, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionCountNotBetween(Integer value1, Integer value2) {
            addCriterion("promotion_count not between", value1, value2, "promotionCount");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeIsNull() {
            addCriterion("promotion_time is null");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeIsNotNull() {
            addCriterion("promotion_time is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeEqualTo(Date value) {
            addCriterion("promotion_time =", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeNotEqualTo(Date value) {
            addCriterion("promotion_time <>", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeGreaterThan(Date value) {
            addCriterion("promotion_time >", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("promotion_time >=", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeLessThan(Date value) {
            addCriterion("promotion_time <", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeLessThanOrEqualTo(Date value) {
            addCriterion("promotion_time <=", value, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeIn(List<Date> values) {
            addCriterion("promotion_time in", values, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeNotIn(List<Date> values) {
            addCriterion("promotion_time not in", values, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeBetween(Date value1, Date value2) {
            addCriterion("promotion_time between", value1, value2, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionTimeNotBetween(Date value1, Date value2) {
            addCriterion("promotion_time not between", value1, value2, "promotionTime");
            return (Criteria) this;
        }

        public Criteria andPromotionReportIsNull() {
            addCriterion("promotion_report is null");
            return (Criteria) this;
        }

        public Criteria andPromotionReportIsNotNull() {
            addCriterion("promotion_report is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionReportEqualTo(String value) {
            addCriterion("promotion_report =", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportNotEqualTo(String value) {
            addCriterion("promotion_report <>", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportGreaterThan(String value) {
            addCriterion("promotion_report >", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportGreaterThanOrEqualTo(String value) {
            addCriterion("promotion_report >=", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportLessThan(String value) {
            addCriterion("promotion_report <", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportLessThanOrEqualTo(String value) {
            addCriterion("promotion_report <=", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportLike(String value) {
            addCriterion("promotion_report like", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportNotLike(String value) {
            addCriterion("promotion_report not like", value, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportIn(List<String> values) {
            addCriterion("promotion_report in", values, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportNotIn(List<String> values) {
            addCriterion("promotion_report not in", values, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportBetween(String value1, String value2) {
            addCriterion("promotion_report between", value1, value2, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andPromotionReportNotBetween(String value1, String value2) {
            addCriterion("promotion_report not between", value1, value2, "promotionReport");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountIsNull() {
            addCriterion("hbm_acd_short_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountIsNotNull() {
            addCriterion("hbm_acd_short_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountEqualTo(Integer value) {
            addCriterion("hbm_acd_short_count =", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountNotEqualTo(Integer value) {
            addCriterion("hbm_acd_short_count <>", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountGreaterThan(Integer value) {
            addCriterion("hbm_acd_short_count >", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_short_count >=", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountLessThan(Integer value) {
            addCriterion("hbm_acd_short_count <", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_short_count <=", value, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountIn(List<Integer> values) {
            addCriterion("hbm_acd_short_count in", values, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountNotIn(List<Integer> values) {
            addCriterion("hbm_acd_short_count not in", values, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_short_count between", value1, value2, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdShortCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_short_count not between", value1, value2, "hbmAcdShortCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountIsNull() {
            addCriterion("hbm_acd_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountIsNotNull() {
            addCriterion("hbm_acd_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountEqualTo(Integer value) {
            addCriterion("hbm_acd_fail_count =", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountNotEqualTo(Integer value) {
            addCriterion("hbm_acd_fail_count <>", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountGreaterThan(Integer value) {
            addCriterion("hbm_acd_fail_count >", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_fail_count >=", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountLessThan(Integer value) {
            addCriterion("hbm_acd_fail_count <", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_fail_count <=", value, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountIn(List<Integer> values) {
            addCriterion("hbm_acd_fail_count in", values, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountNotIn(List<Integer> values) {
            addCriterion("hbm_acd_fail_count not in", values, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_fail_count between", value1, value2, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_fail_count not between", value1, value2, "hbmAcdFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountIsNull() {
            addCriterion("hbm_acd_sms_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountIsNotNull() {
            addCriterion("hbm_acd_sms_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountEqualTo(Integer value) {
            addCriterion("hbm_acd_sms_count =", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountNotEqualTo(Integer value) {
            addCriterion("hbm_acd_sms_count <>", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountGreaterThan(Integer value) {
            addCriterion("hbm_acd_sms_count >", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_sms_count >=", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountLessThan(Integer value) {
            addCriterion("hbm_acd_sms_count <", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_acd_sms_count <=", value, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountIn(List<Integer> values) {
            addCriterion("hbm_acd_sms_count in", values, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountNotIn(List<Integer> values) {
            addCriterion("hbm_acd_sms_count not in", values, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_sms_count between", value1, value2, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmAcdSmsCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_acd_sms_count not between", value1, value2, "hbmAcdSmsCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountIsNull() {
            addCriterion("hbm_sms_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountIsNotNull() {
            addCriterion("hbm_sms_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountEqualTo(Integer value) {
            addCriterion("hbm_sms_fail_count =", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountNotEqualTo(Integer value) {
            addCriterion("hbm_sms_fail_count <>", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountGreaterThan(Integer value) {
            addCriterion("hbm_sms_fail_count >", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_sms_fail_count >=", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountLessThan(Integer value) {
            addCriterion("hbm_sms_fail_count <", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_sms_fail_count <=", value, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountIn(List<Integer> values) {
            addCriterion("hbm_sms_fail_count in", values, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountNotIn(List<Integer> values) {
            addCriterion("hbm_sms_fail_count not in", values, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_sms_fail_count between", value1, value2, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmSmsFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_sms_fail_count not between", value1, value2, "hbmSmsFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountIsNull() {
            addCriterion("hbm_call_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountIsNotNull() {
            addCriterion("hbm_call_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountEqualTo(Integer value) {
            addCriterion("hbm_call_fail_count =", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountNotEqualTo(Integer value) {
            addCriterion("hbm_call_fail_count <>", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountGreaterThan(Integer value) {
            addCriterion("hbm_call_fail_count >", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_call_fail_count >=", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountLessThan(Integer value) {
            addCriterion("hbm_call_fail_count <", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_call_fail_count <=", value, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountIn(List<Integer> values) {
            addCriterion("hbm_call_fail_count in", values, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountNotIn(List<Integer> values) {
            addCriterion("hbm_call_fail_count not in", values, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_call_fail_count between", value1, value2, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmCallFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_call_fail_count not between", value1, value2, "hbmCallFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountIsNull() {
            addCriterion("hbm_dtmf_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountIsNotNull() {
            addCriterion("hbm_dtmf_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountEqualTo(Integer value) {
            addCriterion("hbm_dtmf_fail_count =", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountNotEqualTo(Integer value) {
            addCriterion("hbm_dtmf_fail_count <>", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountGreaterThan(Integer value) {
            addCriterion("hbm_dtmf_fail_count >", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_dtmf_fail_count >=", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountLessThan(Integer value) {
            addCriterion("hbm_dtmf_fail_count <", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_dtmf_fail_count <=", value, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountIn(List<Integer> values) {
            addCriterion("hbm_dtmf_fail_count in", values, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountNotIn(List<Integer> values) {
            addCriterion("hbm_dtmf_fail_count not in", values, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_dtmf_fail_count between", value1, value2, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmDtmfFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_dtmf_fail_count not between", value1, value2, "hbmDtmfFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountIsNull() {
            addCriterion("hbm_reg_fail_count is null");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountIsNotNull() {
            addCriterion("hbm_reg_fail_count is not null");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountEqualTo(Integer value) {
            addCriterion("hbm_reg_fail_count =", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountNotEqualTo(Integer value) {
            addCriterion("hbm_reg_fail_count <>", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountGreaterThan(Integer value) {
            addCriterion("hbm_reg_fail_count >", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("hbm_reg_fail_count >=", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountLessThan(Integer value) {
            addCriterion("hbm_reg_fail_count <", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountLessThanOrEqualTo(Integer value) {
            addCriterion("hbm_reg_fail_count <=", value, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountIn(List<Integer> values) {
            addCriterion("hbm_reg_fail_count in", values, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountNotIn(List<Integer> values) {
            addCriterion("hbm_reg_fail_count not in", values, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountBetween(Integer value1, Integer value2) {
            addCriterion("hbm_reg_fail_count between", value1, value2, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andHbmRegFailCountNotBetween(Integer value1, Integer value2) {
            addCriterion("hbm_reg_fail_count not between", value1, value2, "hbmRegFailCount");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagIsNull() {
            addCriterion("sim_recharged_flag is null");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagIsNotNull() {
            addCriterion("sim_recharged_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagEqualTo(Integer value) {
            addCriterion("sim_recharged_flag =", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagNotEqualTo(Integer value) {
            addCriterion("sim_recharged_flag <>", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagGreaterThan(Integer value) {
            addCriterion("sim_recharged_flag >", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sim_recharged_flag >=", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagLessThan(Integer value) {
            addCriterion("sim_recharged_flag <", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sim_recharged_flag <=", value, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagIn(List<Integer> values) {
            addCriterion("sim_recharged_flag in", values, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagNotIn(List<Integer> values) {
            addCriterion("sim_recharged_flag not in", values, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagBetween(Integer value1, Integer value2) {
            addCriterion("sim_recharged_flag between", value1, value2, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andSimRechargedFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sim_recharged_flag not between", value1, value2, "simRechargedFlag");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidIsNull() {
            addCriterion("paid_list_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidIsNotNull() {
            addCriterion("paid_list_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidEqualTo(Integer value) {
            addCriterion("paid_list_uuid =", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidNotEqualTo(Integer value) {
            addCriterion("paid_list_uuid <>", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidGreaterThan(Integer value) {
            addCriterion("paid_list_uuid >", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("paid_list_uuid >=", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidLessThan(Integer value) {
            addCriterion("paid_list_uuid <", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidLessThanOrEqualTo(Integer value) {
            addCriterion("paid_list_uuid <=", value, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidIn(List<Integer> values) {
            addCriterion("paid_list_uuid in", values, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidNotIn(List<Integer> values) {
            addCriterion("paid_list_uuid not in", values, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidBetween(Integer value1, Integer value2) {
            addCriterion("paid_list_uuid between", value1, value2, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andPaidListUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("paid_list_uuid not between", value1, value2, "paidListUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagIsNull() {
            addCriterion("local_sim_flag is null");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagIsNotNull() {
            addCriterion("local_sim_flag is not null");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagEqualTo(Integer value) {
            addCriterion("local_sim_flag =", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagNotEqualTo(Integer value) {
            addCriterion("local_sim_flag <>", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagGreaterThan(Integer value) {
            addCriterion("local_sim_flag >", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("local_sim_flag >=", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagLessThan(Integer value) {
            addCriterion("local_sim_flag <", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagLessThanOrEqualTo(Integer value) {
            addCriterion("local_sim_flag <=", value, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagIn(List<Integer> values) {
            addCriterion("local_sim_flag in", values, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagNotIn(List<Integer> values) {
            addCriterion("local_sim_flag not in", values, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagBetween(Integer value1, Integer value2) {
            addCriterion("local_sim_flag between", value1, value2, "localSimFlag");
            return (Criteria) this;
        }

        public Criteria andLocalSimFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("local_sim_flag not between", value1, value2, "localSimFlag");
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

        public Criteria andSetCurBalanceIsNull() {
            addCriterion("set_cur_balance is null");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceIsNotNull() {
            addCriterion("set_cur_balance is not null");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceEqualTo(Float value) {
            addCriterion("set_cur_balance =", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceNotEqualTo(Float value) {
            addCriterion("set_cur_balance <>", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceGreaterThan(Float value) {
            addCriterion("set_cur_balance >", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceGreaterThanOrEqualTo(Float value) {
            addCriterion("set_cur_balance >=", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceLessThan(Float value) {
            addCriterion("set_cur_balance <", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceLessThanOrEqualTo(Float value) {
            addCriterion("set_cur_balance <=", value, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceIn(List<Float> values) {
            addCriterion("set_cur_balance in", values, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceNotIn(List<Float> values) {
            addCriterion("set_cur_balance not in", values, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceBetween(Float value1, Float value2) {
            addCriterion("set_cur_balance between", value1, value2, "setCurBalance");
            return (Criteria) this;
        }

        public Criteria andSetCurBalanceNotBetween(Float value1, Float value2) {
            addCriterion("set_cur_balance not between", value1, value2, "setCurBalance");
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