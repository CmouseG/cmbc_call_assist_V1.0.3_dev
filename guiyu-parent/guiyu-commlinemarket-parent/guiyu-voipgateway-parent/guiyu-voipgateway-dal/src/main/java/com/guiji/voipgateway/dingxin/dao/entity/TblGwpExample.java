package com.guiji.voipgateway.dingxin.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TblGwpExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TblGwpExample() {
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

        public Criteria andPortUuidIsNull() {
            addCriterion("port_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPortUuidIsNotNull() {
            addCriterion("port_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPortUuidEqualTo(Integer value) {
            addCriterion("port_uuid =", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidNotEqualTo(Integer value) {
            addCriterion("port_uuid <>", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidGreaterThan(Integer value) {
            addCriterion("port_uuid >", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_uuid >=", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidLessThan(Integer value) {
            addCriterion("port_uuid <", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidLessThanOrEqualTo(Integer value) {
            addCriterion("port_uuid <=", value, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidIn(List<Integer> values) {
            addCriterion("port_uuid in", values, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidNotIn(List<Integer> values) {
            addCriterion("port_uuid not in", values, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidBetween(Integer value1, Integer value2) {
            addCriterion("port_uuid between", value1, value2, "portUuid");
            return (Criteria) this;
        }

        public Criteria andPortUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("port_uuid not between", value1, value2, "portUuid");
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

        public Criteria andLocalSimUuidIsNull() {
            addCriterion("local_sim_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidIsNotNull() {
            addCriterion("local_sim_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidEqualTo(Integer value) {
            addCriterion("local_sim_uuid =", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidNotEqualTo(Integer value) {
            addCriterion("local_sim_uuid <>", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidGreaterThan(Integer value) {
            addCriterion("local_sim_uuid >", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("local_sim_uuid >=", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidLessThan(Integer value) {
            addCriterion("local_sim_uuid <", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidLessThanOrEqualTo(Integer value) {
            addCriterion("local_sim_uuid <=", value, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidIn(List<Integer> values) {
            addCriterion("local_sim_uuid in", values, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidNotIn(List<Integer> values) {
            addCriterion("local_sim_uuid not in", values, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidBetween(Integer value1, Integer value2) {
            addCriterion("local_sim_uuid between", value1, value2, "localSimUuid");
            return (Criteria) this;
        }

        public Criteria andLocalSimUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("local_sim_uuid not between", value1, value2, "localSimUuid");
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

        public Criteria andLocalImeiIsNull() {
            addCriterion("local_imei is null");
            return (Criteria) this;
        }

        public Criteria andLocalImeiIsNotNull() {
            addCriterion("local_imei is not null");
            return (Criteria) this;
        }

        public Criteria andLocalImeiEqualTo(String value) {
            addCriterion("local_imei =", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiNotEqualTo(String value) {
            addCriterion("local_imei <>", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiGreaterThan(String value) {
            addCriterion("local_imei >", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiGreaterThanOrEqualTo(String value) {
            addCriterion("local_imei >=", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiLessThan(String value) {
            addCriterion("local_imei <", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiLessThanOrEqualTo(String value) {
            addCriterion("local_imei <=", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiLike(String value) {
            addCriterion("local_imei like", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiNotLike(String value) {
            addCriterion("local_imei not like", value, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiIn(List<String> values) {
            addCriterion("local_imei in", values, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiNotIn(List<String> values) {
            addCriterion("local_imei not in", values, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiBetween(String value1, String value2) {
            addCriterion("local_imei between", value1, value2, "localImei");
            return (Criteria) this;
        }

        public Criteria andLocalImeiNotBetween(String value1, String value2) {
            addCriterion("local_imei not between", value1, value2, "localImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiIsNull() {
            addCriterion("current_imei is null");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiIsNotNull() {
            addCriterion("current_imei is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiEqualTo(String value) {
            addCriterion("current_imei =", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiNotEqualTo(String value) {
            addCriterion("current_imei <>", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiGreaterThan(String value) {
            addCriterion("current_imei >", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiGreaterThanOrEqualTo(String value) {
            addCriterion("current_imei >=", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiLessThan(String value) {
            addCriterion("current_imei <", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiLessThanOrEqualTo(String value) {
            addCriterion("current_imei <=", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiLike(String value) {
            addCriterion("current_imei like", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiNotLike(String value) {
            addCriterion("current_imei not like", value, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiIn(List<String> values) {
            addCriterion("current_imei in", values, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiNotIn(List<String> values) {
            addCriterion("current_imei not in", values, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiBetween(String value1, String value2) {
            addCriterion("current_imei between", value1, value2, "currentImei");
            return (Criteria) this;
        }

        public Criteria andCurrentImeiNotBetween(String value1, String value2) {
            addCriterion("current_imei not between", value1, value2, "currentImei");
            return (Criteria) this;
        }

        public Criteria andLocalImsiIsNull() {
            addCriterion("local_imsi is null");
            return (Criteria) this;
        }

        public Criteria andLocalImsiIsNotNull() {
            addCriterion("local_imsi is not null");
            return (Criteria) this;
        }

        public Criteria andLocalImsiEqualTo(String value) {
            addCriterion("local_imsi =", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiNotEqualTo(String value) {
            addCriterion("local_imsi <>", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiGreaterThan(String value) {
            addCriterion("local_imsi >", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiGreaterThanOrEqualTo(String value) {
            addCriterion("local_imsi >=", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiLessThan(String value) {
            addCriterion("local_imsi <", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiLessThanOrEqualTo(String value) {
            addCriterion("local_imsi <=", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiLike(String value) {
            addCriterion("local_imsi like", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiNotLike(String value) {
            addCriterion("local_imsi not like", value, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiIn(List<String> values) {
            addCriterion("local_imsi in", values, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiNotIn(List<String> values) {
            addCriterion("local_imsi not in", values, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiBetween(String value1, String value2) {
            addCriterion("local_imsi between", value1, value2, "localImsi");
            return (Criteria) this;
        }

        public Criteria andLocalImsiNotBetween(String value1, String value2) {
            addCriterion("local_imsi not between", value1, value2, "localImsi");
            return (Criteria) this;
        }

        public Criteria andWorkModeIsNull() {
            addCriterion("work_mode is null");
            return (Criteria) this;
        }

        public Criteria andWorkModeIsNotNull() {
            addCriterion("work_mode is not null");
            return (Criteria) this;
        }

        public Criteria andWorkModeEqualTo(Integer value) {
            addCriterion("work_mode =", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeNotEqualTo(Integer value) {
            addCriterion("work_mode <>", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeGreaterThan(Integer value) {
            addCriterion("work_mode >", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("work_mode >=", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeLessThan(Integer value) {
            addCriterion("work_mode <", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeLessThanOrEqualTo(Integer value) {
            addCriterion("work_mode <=", value, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeIn(List<Integer> values) {
            addCriterion("work_mode in", values, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeNotIn(List<Integer> values) {
            addCriterion("work_mode not in", values, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeBetween(Integer value1, Integer value2) {
            addCriterion("work_mode between", value1, value2, "workMode");
            return (Criteria) this;
        }

        public Criteria andWorkModeNotBetween(Integer value1, Integer value2) {
            addCriterion("work_mode not between", value1, value2, "workMode");
            return (Criteria) this;
        }

        public Criteria andModTypeIsNull() {
            addCriterion("mod_type is null");
            return (Criteria) this;
        }

        public Criteria andModTypeIsNotNull() {
            addCriterion("mod_type is not null");
            return (Criteria) this;
        }

        public Criteria andModTypeEqualTo(Integer value) {
            addCriterion("mod_type =", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeNotEqualTo(Integer value) {
            addCriterion("mod_type <>", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeGreaterThan(Integer value) {
            addCriterion("mod_type >", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_type >=", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeLessThan(Integer value) {
            addCriterion("mod_type <", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeLessThanOrEqualTo(Integer value) {
            addCriterion("mod_type <=", value, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeIn(List<Integer> values) {
            addCriterion("mod_type in", values, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeNotIn(List<Integer> values) {
            addCriterion("mod_type not in", values, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeBetween(Integer value1, Integer value2) {
            addCriterion("mod_type between", value1, value2, "modType");
            return (Criteria) this;
        }

        public Criteria andModTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_type not between", value1, value2, "modType");
            return (Criteria) this;
        }

        public Criteria andModStatusIsNull() {
            addCriterion("mod_status is null");
            return (Criteria) this;
        }

        public Criteria andModStatusIsNotNull() {
            addCriterion("mod_status is not null");
            return (Criteria) this;
        }

        public Criteria andModStatusEqualTo(Integer value) {
            addCriterion("mod_status =", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusNotEqualTo(Integer value) {
            addCriterion("mod_status <>", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusGreaterThan(Integer value) {
            addCriterion("mod_status >", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_status >=", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusLessThan(Integer value) {
            addCriterion("mod_status <", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusLessThanOrEqualTo(Integer value) {
            addCriterion("mod_status <=", value, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusIn(List<Integer> values) {
            addCriterion("mod_status in", values, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusNotIn(List<Integer> values) {
            addCriterion("mod_status not in", values, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusBetween(Integer value1, Integer value2) {
            addCriterion("mod_status between", value1, value2, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_status not between", value1, value2, "modStatus");
            return (Criteria) this;
        }

        public Criteria andModSignalValIsNull() {
            addCriterion("mod_signal_val is null");
            return (Criteria) this;
        }

        public Criteria andModSignalValIsNotNull() {
            addCriterion("mod_signal_val is not null");
            return (Criteria) this;
        }

        public Criteria andModSignalValEqualTo(Integer value) {
            addCriterion("mod_signal_val =", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValNotEqualTo(Integer value) {
            addCriterion("mod_signal_val <>", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValGreaterThan(Integer value) {
            addCriterion("mod_signal_val >", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_signal_val >=", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValLessThan(Integer value) {
            addCriterion("mod_signal_val <", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValLessThanOrEqualTo(Integer value) {
            addCriterion("mod_signal_val <=", value, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValIn(List<Integer> values) {
            addCriterion("mod_signal_val in", values, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValNotIn(List<Integer> values) {
            addCriterion("mod_signal_val not in", values, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValBetween(Integer value1, Integer value2) {
            addCriterion("mod_signal_val between", value1, value2, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalValNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_signal_val not between", value1, value2, "modSignalVal");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelIsNull() {
            addCriterion("mod_signal_level is null");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelIsNotNull() {
            addCriterion("mod_signal_level is not null");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelEqualTo(Integer value) {
            addCriterion("mod_signal_level =", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelNotEqualTo(Integer value) {
            addCriterion("mod_signal_level <>", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelGreaterThan(Integer value) {
            addCriterion("mod_signal_level >", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_signal_level >=", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelLessThan(Integer value) {
            addCriterion("mod_signal_level <", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelLessThanOrEqualTo(Integer value) {
            addCriterion("mod_signal_level <=", value, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelIn(List<Integer> values) {
            addCriterion("mod_signal_level in", values, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelNotIn(List<Integer> values) {
            addCriterion("mod_signal_level not in", values, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelBetween(Integer value1, Integer value2) {
            addCriterion("mod_signal_level between", value1, value2, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModSignalLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_signal_level not between", value1, value2, "modSignalLevel");
            return (Criteria) this;
        }

        public Criteria andModBerValIsNull() {
            addCriterion("mod_ber_val is null");
            return (Criteria) this;
        }

        public Criteria andModBerValIsNotNull() {
            addCriterion("mod_ber_val is not null");
            return (Criteria) this;
        }

        public Criteria andModBerValEqualTo(Integer value) {
            addCriterion("mod_ber_val =", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValNotEqualTo(Integer value) {
            addCriterion("mod_ber_val <>", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValGreaterThan(Integer value) {
            addCriterion("mod_ber_val >", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_ber_val >=", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValLessThan(Integer value) {
            addCriterion("mod_ber_val <", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValLessThanOrEqualTo(Integer value) {
            addCriterion("mod_ber_val <=", value, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValIn(List<Integer> values) {
            addCriterion("mod_ber_val in", values, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValNotIn(List<Integer> values) {
            addCriterion("mod_ber_val not in", values, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValBetween(Integer value1, Integer value2) {
            addCriterion("mod_ber_val between", value1, value2, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModBerValNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_ber_val not between", value1, value2, "modBerVal");
            return (Criteria) this;
        }

        public Criteria andModErrorCountIsNull() {
            addCriterion("mod_error_count is null");
            return (Criteria) this;
        }

        public Criteria andModErrorCountIsNotNull() {
            addCriterion("mod_error_count is not null");
            return (Criteria) this;
        }

        public Criteria andModErrorCountEqualTo(Integer value) {
            addCriterion("mod_error_count =", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountNotEqualTo(Integer value) {
            addCriterion("mod_error_count <>", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountGreaterThan(Integer value) {
            addCriterion("mod_error_count >", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("mod_error_count >=", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountLessThan(Integer value) {
            addCriterion("mod_error_count <", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountLessThanOrEqualTo(Integer value) {
            addCriterion("mod_error_count <=", value, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountIn(List<Integer> values) {
            addCriterion("mod_error_count in", values, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountNotIn(List<Integer> values) {
            addCriterion("mod_error_count not in", values, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountBetween(Integer value1, Integer value2) {
            addCriterion("mod_error_count between", value1, value2, "modErrorCount");
            return (Criteria) this;
        }

        public Criteria andModErrorCountNotBetween(Integer value1, Integer value2) {
            addCriterion("mod_error_count not between", value1, value2, "modErrorCount");
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

        public Criteria andCurCallStatusIsNull() {
            addCriterion("cur_call_status is null");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusIsNotNull() {
            addCriterion("cur_call_status is not null");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusEqualTo(Integer value) {
            addCriterion("cur_call_status =", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusNotEqualTo(Integer value) {
            addCriterion("cur_call_status <>", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusGreaterThan(Integer value) {
            addCriterion("cur_call_status >", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_call_status >=", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusLessThan(Integer value) {
            addCriterion("cur_call_status <", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cur_call_status <=", value, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusIn(List<Integer> values) {
            addCriterion("cur_call_status in", values, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusNotIn(List<Integer> values) {
            addCriterion("cur_call_status not in", values, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusBetween(Integer value1, Integer value2) {
            addCriterion("cur_call_status between", value1, value2, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_call_status not between", value1, value2, "curCallStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusIsNull() {
            addCriterion("cur_sms_status is null");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusIsNotNull() {
            addCriterion("cur_sms_status is not null");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusEqualTo(Integer value) {
            addCriterion("cur_sms_status =", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusNotEqualTo(Integer value) {
            addCriterion("cur_sms_status <>", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusGreaterThan(Integer value) {
            addCriterion("cur_sms_status >", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_sms_status >=", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusLessThan(Integer value) {
            addCriterion("cur_sms_status <", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cur_sms_status <=", value, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusIn(List<Integer> values) {
            addCriterion("cur_sms_status in", values, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusNotIn(List<Integer> values) {
            addCriterion("cur_sms_status not in", values, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusBetween(Integer value1, Integer value2) {
            addCriterion("cur_sms_status between", value1, value2, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurSmsStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_sms_status not between", value1, value2, "curSmsStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusIsNull() {
            addCriterion("cur_ussd_status is null");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusIsNotNull() {
            addCriterion("cur_ussd_status is not null");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusEqualTo(Integer value) {
            addCriterion("cur_ussd_status =", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusNotEqualTo(Integer value) {
            addCriterion("cur_ussd_status <>", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusGreaterThan(Integer value) {
            addCriterion("cur_ussd_status >", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_ussd_status >=", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusLessThan(Integer value) {
            addCriterion("cur_ussd_status <", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cur_ussd_status <=", value, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusIn(List<Integer> values) {
            addCriterion("cur_ussd_status in", values, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusNotIn(List<Integer> values) {
            addCriterion("cur_ussd_status not in", values, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusBetween(Integer value1, Integer value2) {
            addCriterion("cur_ussd_status between", value1, value2, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurUssdStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_ussd_status not between", value1, value2, "curUssdStatus");
            return (Criteria) this;
        }

        public Criteria andCurCallSnIsNull() {
            addCriterion("cur_call_sn is null");
            return (Criteria) this;
        }

        public Criteria andCurCallSnIsNotNull() {
            addCriterion("cur_call_sn is not null");
            return (Criteria) this;
        }

        public Criteria andCurCallSnEqualTo(Integer value) {
            addCriterion("cur_call_sn =", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnNotEqualTo(Integer value) {
            addCriterion("cur_call_sn <>", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnGreaterThan(Integer value) {
            addCriterion("cur_call_sn >", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_call_sn >=", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnLessThan(Integer value) {
            addCriterion("cur_call_sn <", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnLessThanOrEqualTo(Integer value) {
            addCriterion("cur_call_sn <=", value, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnIn(List<Integer> values) {
            addCriterion("cur_call_sn in", values, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnNotIn(List<Integer> values) {
            addCriterion("cur_call_sn not in", values, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnBetween(Integer value1, Integer value2) {
            addCriterion("cur_call_sn between", value1, value2, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurCallSnNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_call_sn not between", value1, value2, "curCallSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnIsNull() {
            addCriterion("cur_sms_sn is null");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnIsNotNull() {
            addCriterion("cur_sms_sn is not null");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnEqualTo(Integer value) {
            addCriterion("cur_sms_sn =", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnNotEqualTo(Integer value) {
            addCriterion("cur_sms_sn <>", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnGreaterThan(Integer value) {
            addCriterion("cur_sms_sn >", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_sms_sn >=", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnLessThan(Integer value) {
            addCriterion("cur_sms_sn <", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnLessThanOrEqualTo(Integer value) {
            addCriterion("cur_sms_sn <=", value, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnIn(List<Integer> values) {
            addCriterion("cur_sms_sn in", values, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnNotIn(List<Integer> values) {
            addCriterion("cur_sms_sn not in", values, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnBetween(Integer value1, Integer value2) {
            addCriterion("cur_sms_sn between", value1, value2, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurSmsSnNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_sms_sn not between", value1, value2, "curSmsSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnIsNull() {
            addCriterion("cur_ussd_sn is null");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnIsNotNull() {
            addCriterion("cur_ussd_sn is not null");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnEqualTo(Integer value) {
            addCriterion("cur_ussd_sn =", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnNotEqualTo(Integer value) {
            addCriterion("cur_ussd_sn <>", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnGreaterThan(Integer value) {
            addCriterion("cur_ussd_sn >", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnGreaterThanOrEqualTo(Integer value) {
            addCriterion("cur_ussd_sn >=", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnLessThan(Integer value) {
            addCriterion("cur_ussd_sn <", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnLessThanOrEqualTo(Integer value) {
            addCriterion("cur_ussd_sn <=", value, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnIn(List<Integer> values) {
            addCriterion("cur_ussd_sn in", values, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnNotIn(List<Integer> values) {
            addCriterion("cur_ussd_sn not in", values, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnBetween(Integer value1, Integer value2) {
            addCriterion("cur_ussd_sn between", value1, value2, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andCurUssdSnNotBetween(Integer value1, Integer value2) {
            addCriterion("cur_ussd_sn not between", value1, value2, "curUssdSn");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayIsNull() {
            addCriterion("round_trip_delay is null");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayIsNotNull() {
            addCriterion("round_trip_delay is not null");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayEqualTo(Integer value) {
            addCriterion("round_trip_delay =", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayNotEqualTo(Integer value) {
            addCriterion("round_trip_delay <>", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayGreaterThan(Integer value) {
            addCriterion("round_trip_delay >", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayGreaterThanOrEqualTo(Integer value) {
            addCriterion("round_trip_delay >=", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayLessThan(Integer value) {
            addCriterion("round_trip_delay <", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayLessThanOrEqualTo(Integer value) {
            addCriterion("round_trip_delay <=", value, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayIn(List<Integer> values) {
            addCriterion("round_trip_delay in", values, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayNotIn(List<Integer> values) {
            addCriterion("round_trip_delay not in", values, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayBetween(Integer value1, Integer value2) {
            addCriterion("round_trip_delay between", value1, value2, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andRoundTripDelayNotBetween(Integer value1, Integer value2) {
            addCriterion("round_trip_delay not between", value1, value2, "roundTripDelay");
            return (Criteria) this;
        }

        public Criteria andPacketAllIsNull() {
            addCriterion("packet_all is null");
            return (Criteria) this;
        }

        public Criteria andPacketAllIsNotNull() {
            addCriterion("packet_all is not null");
            return (Criteria) this;
        }

        public Criteria andPacketAllEqualTo(Integer value) {
            addCriterion("packet_all =", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllNotEqualTo(Integer value) {
            addCriterion("packet_all <>", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllGreaterThan(Integer value) {
            addCriterion("packet_all >", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllGreaterThanOrEqualTo(Integer value) {
            addCriterion("packet_all >=", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllLessThan(Integer value) {
            addCriterion("packet_all <", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllLessThanOrEqualTo(Integer value) {
            addCriterion("packet_all <=", value, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllIn(List<Integer> values) {
            addCriterion("packet_all in", values, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllNotIn(List<Integer> values) {
            addCriterion("packet_all not in", values, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllBetween(Integer value1, Integer value2) {
            addCriterion("packet_all between", value1, value2, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketAllNotBetween(Integer value1, Integer value2) {
            addCriterion("packet_all not between", value1, value2, "packetAll");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesIsNull() {
            addCriterion("packet_retries is null");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesIsNotNull() {
            addCriterion("packet_retries is not null");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesEqualTo(Integer value) {
            addCriterion("packet_retries =", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesNotEqualTo(Integer value) {
            addCriterion("packet_retries <>", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesGreaterThan(Integer value) {
            addCriterion("packet_retries >", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesGreaterThanOrEqualTo(Integer value) {
            addCriterion("packet_retries >=", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesLessThan(Integer value) {
            addCriterion("packet_retries <", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesLessThanOrEqualTo(Integer value) {
            addCriterion("packet_retries <=", value, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesIn(List<Integer> values) {
            addCriterion("packet_retries in", values, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesNotIn(List<Integer> values) {
            addCriterion("packet_retries not in", values, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesBetween(Integer value1, Integer value2) {
            addCriterion("packet_retries between", value1, value2, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketRetriesNotBetween(Integer value1, Integer value2) {
            addCriterion("packet_retries not between", value1, value2, "packetRetries");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutIsNull() {
            addCriterion("packet_timeout is null");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutIsNotNull() {
            addCriterion("packet_timeout is not null");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutEqualTo(Integer value) {
            addCriterion("packet_timeout =", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutNotEqualTo(Integer value) {
            addCriterion("packet_timeout <>", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutGreaterThan(Integer value) {
            addCriterion("packet_timeout >", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutGreaterThanOrEqualTo(Integer value) {
            addCriterion("packet_timeout >=", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutLessThan(Integer value) {
            addCriterion("packet_timeout <", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutLessThanOrEqualTo(Integer value) {
            addCriterion("packet_timeout <=", value, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutIn(List<Integer> values) {
            addCriterion("packet_timeout in", values, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutNotIn(List<Integer> values) {
            addCriterion("packet_timeout not in", values, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutBetween(Integer value1, Integer value2) {
            addCriterion("packet_timeout between", value1, value2, "packetTimeout");
            return (Criteria) this;
        }

        public Criteria andPacketTimeoutNotBetween(Integer value1, Integer value2) {
            addCriterion("packet_timeout not between", value1, value2, "packetTimeout");
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