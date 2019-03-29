package com.guiji.voipgateway.dingxin.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class TblGwExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TblGwExample() {
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

        public Criteria andNeUuidIsNull() {
            addCriterion("ne_uuid is null");
            return (Criteria) this;
        }

        public Criteria andNeUuidIsNotNull() {
            addCriterion("ne_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andNeUuidEqualTo(Integer value) {
            addCriterion("ne_uuid =", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidNotEqualTo(Integer value) {
            addCriterion("ne_uuid <>", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidGreaterThan(Integer value) {
            addCriterion("ne_uuid >", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("ne_uuid >=", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidLessThan(Integer value) {
            addCriterion("ne_uuid <", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidLessThanOrEqualTo(Integer value) {
            addCriterion("ne_uuid <=", value, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidIn(List<Integer> values) {
            addCriterion("ne_uuid in", values, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidNotIn(List<Integer> values) {
            addCriterion("ne_uuid not in", values, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidBetween(Integer value1, Integer value2) {
            addCriterion("ne_uuid between", value1, value2, "neUuid");
            return (Criteria) this;
        }

        public Criteria andNeUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("ne_uuid not between", value1, value2, "neUuid");
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

        public Criteria andDefaultGrpUuidIsNull() {
            addCriterion("default_grp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidIsNotNull() {
            addCriterion("default_grp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidEqualTo(Integer value) {
            addCriterion("default_grp_uuid =", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidNotEqualTo(Integer value) {
            addCriterion("default_grp_uuid <>", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidGreaterThan(Integer value) {
            addCriterion("default_grp_uuid >", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("default_grp_uuid >=", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidLessThan(Integer value) {
            addCriterion("default_grp_uuid <", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("default_grp_uuid <=", value, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidIn(List<Integer> values) {
            addCriterion("default_grp_uuid in", values, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidNotIn(List<Integer> values) {
            addCriterion("default_grp_uuid not in", values, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidBetween(Integer value1, Integer value2) {
            addCriterion("default_grp_uuid between", value1, value2, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andDefaultGrpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("default_grp_uuid not between", value1, value2, "defaultGrpUuid");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagIsNull() {
            addCriterion("virtual_sim_flag is null");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagIsNotNull() {
            addCriterion("virtual_sim_flag is not null");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagEqualTo(Integer value) {
            addCriterion("virtual_sim_flag =", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagNotEqualTo(Integer value) {
            addCriterion("virtual_sim_flag <>", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagGreaterThan(Integer value) {
            addCriterion("virtual_sim_flag >", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("virtual_sim_flag >=", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagLessThan(Integer value) {
            addCriterion("virtual_sim_flag <", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagLessThanOrEqualTo(Integer value) {
            addCriterion("virtual_sim_flag <=", value, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagIn(List<Integer> values) {
            addCriterion("virtual_sim_flag in", values, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagNotIn(List<Integer> values) {
            addCriterion("virtual_sim_flag not in", values, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagBetween(Integer value1, Integer value2) {
            addCriterion("virtual_sim_flag between", value1, value2, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andVirtualSimFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("virtual_sim_flag not between", value1, value2, "virtualSimFlag");
            return (Criteria) this;
        }

        public Criteria andGwpNumIsNull() {
            addCriterion("gwp_num is null");
            return (Criteria) this;
        }

        public Criteria andGwpNumIsNotNull() {
            addCriterion("gwp_num is not null");
            return (Criteria) this;
        }

        public Criteria andGwpNumEqualTo(Integer value) {
            addCriterion("gwp_num =", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumNotEqualTo(Integer value) {
            addCriterion("gwp_num <>", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumGreaterThan(Integer value) {
            addCriterion("gwp_num >", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("gwp_num >=", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumLessThan(Integer value) {
            addCriterion("gwp_num <", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumLessThanOrEqualTo(Integer value) {
            addCriterion("gwp_num <=", value, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumIn(List<Integer> values) {
            addCriterion("gwp_num in", values, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumNotIn(List<Integer> values) {
            addCriterion("gwp_num not in", values, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumBetween(Integer value1, Integer value2) {
            addCriterion("gwp_num between", value1, value2, "gwpNum");
            return (Criteria) this;
        }

        public Criteria andGwpNumNotBetween(Integer value1, Integer value2) {
            addCriterion("gwp_num not between", value1, value2, "gwpNum");
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