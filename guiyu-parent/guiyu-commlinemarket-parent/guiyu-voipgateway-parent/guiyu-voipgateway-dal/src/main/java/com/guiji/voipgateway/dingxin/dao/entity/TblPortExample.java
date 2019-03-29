package com.guiji.voipgateway.dingxin.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class TblPortExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TblPortExample() {
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

        public Criteria andShelfNoIsNull() {
            addCriterion("shelf_no is null");
            return (Criteria) this;
        }

        public Criteria andShelfNoIsNotNull() {
            addCriterion("shelf_no is not null");
            return (Criteria) this;
        }

        public Criteria andShelfNoEqualTo(Integer value) {
            addCriterion("shelf_no =", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotEqualTo(Integer value) {
            addCriterion("shelf_no <>", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoGreaterThan(Integer value) {
            addCriterion("shelf_no >", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("shelf_no >=", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoLessThan(Integer value) {
            addCriterion("shelf_no <", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoLessThanOrEqualTo(Integer value) {
            addCriterion("shelf_no <=", value, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoIn(List<Integer> values) {
            addCriterion("shelf_no in", values, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotIn(List<Integer> values) {
            addCriterion("shelf_no not in", values, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoBetween(Integer value1, Integer value2) {
            addCriterion("shelf_no between", value1, value2, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andShelfNoNotBetween(Integer value1, Integer value2) {
            addCriterion("shelf_no not between", value1, value2, "shelfNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoIsNull() {
            addCriterion("slot_no is null");
            return (Criteria) this;
        }

        public Criteria andSlotNoIsNotNull() {
            addCriterion("slot_no is not null");
            return (Criteria) this;
        }

        public Criteria andSlotNoEqualTo(Integer value) {
            addCriterion("slot_no =", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoNotEqualTo(Integer value) {
            addCriterion("slot_no <>", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoGreaterThan(Integer value) {
            addCriterion("slot_no >", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("slot_no >=", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoLessThan(Integer value) {
            addCriterion("slot_no <", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoLessThanOrEqualTo(Integer value) {
            addCriterion("slot_no <=", value, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoIn(List<Integer> values) {
            addCriterion("slot_no in", values, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoNotIn(List<Integer> values) {
            addCriterion("slot_no not in", values, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoBetween(Integer value1, Integer value2) {
            addCriterion("slot_no between", value1, value2, "slotNo");
            return (Criteria) this;
        }

        public Criteria andSlotNoNotBetween(Integer value1, Integer value2) {
            addCriterion("slot_no not between", value1, value2, "slotNo");
            return (Criteria) this;
        }

        public Criteria andPortNoIsNull() {
            addCriterion("port_no is null");
            return (Criteria) this;
        }

        public Criteria andPortNoIsNotNull() {
            addCriterion("port_no is not null");
            return (Criteria) this;
        }

        public Criteria andPortNoEqualTo(Integer value) {
            addCriterion("port_no =", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotEqualTo(Integer value) {
            addCriterion("port_no <>", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoGreaterThan(Integer value) {
            addCriterion("port_no >", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_no >=", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoLessThan(Integer value) {
            addCriterion("port_no <", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoLessThanOrEqualTo(Integer value) {
            addCriterion("port_no <=", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoIn(List<Integer> values) {
            addCriterion("port_no in", values, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotIn(List<Integer> values) {
            addCriterion("port_no not in", values, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoBetween(Integer value1, Integer value2) {
            addCriterion("port_no between", value1, value2, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotBetween(Integer value1, Integer value2) {
            addCriterion("port_no not between", value1, value2, "portNo");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
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

        public Criteria andPortPolicyUuidIsNull() {
            addCriterion("port_policy_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidIsNotNull() {
            addCriterion("port_policy_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidEqualTo(Integer value) {
            addCriterion("port_policy_uuid =", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidNotEqualTo(Integer value) {
            addCriterion("port_policy_uuid <>", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidGreaterThan(Integer value) {
            addCriterion("port_policy_uuid >", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_policy_uuid >=", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidLessThan(Integer value) {
            addCriterion("port_policy_uuid <", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidLessThanOrEqualTo(Integer value) {
            addCriterion("port_policy_uuid <=", value, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidIn(List<Integer> values) {
            addCriterion("port_policy_uuid in", values, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidNotIn(List<Integer> values) {
            addCriterion("port_policy_uuid not in", values, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidBetween(Integer value1, Integer value2) {
            addCriterion("port_policy_uuid between", value1, value2, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortPolicyUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("port_policy_uuid not between", value1, value2, "portPolicyUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidIsNull() {
            addCriterion("port_grp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidIsNotNull() {
            addCriterion("port_grp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidEqualTo(Integer value) {
            addCriterion("port_grp_uuid =", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidNotEqualTo(Integer value) {
            addCriterion("port_grp_uuid <>", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidGreaterThan(Integer value) {
            addCriterion("port_grp_uuid >", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_grp_uuid >=", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidLessThan(Integer value) {
            addCriterion("port_grp_uuid <", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("port_grp_uuid <=", value, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidIn(List<Integer> values) {
            addCriterion("port_grp_uuid in", values, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidNotIn(List<Integer> values) {
            addCriterion("port_grp_uuid not in", values, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidBetween(Integer value1, Integer value2) {
            addCriterion("port_grp_uuid between", value1, value2, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andPortGrpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("port_grp_uuid not between", value1, value2, "portGrpUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidIsNull() {
            addCriterion("lock_port_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidIsNotNull() {
            addCriterion("lock_port_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidEqualTo(Integer value) {
            addCriterion("lock_port_uuid =", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidNotEqualTo(Integer value) {
            addCriterion("lock_port_uuid <>", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidGreaterThan(Integer value) {
            addCriterion("lock_port_uuid >", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_port_uuid >=", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidLessThan(Integer value) {
            addCriterion("lock_port_uuid <", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidLessThanOrEqualTo(Integer value) {
            addCriterion("lock_port_uuid <=", value, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidIn(List<Integer> values) {
            addCriterion("lock_port_uuid in", values, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidNotIn(List<Integer> values) {
            addCriterion("lock_port_uuid not in", values, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidBetween(Integer value1, Integer value2) {
            addCriterion("lock_port_uuid between", value1, value2, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockPortUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_port_uuid not between", value1, value2, "lockPortUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidIsNull() {
            addCriterion("lock_sim_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidIsNotNull() {
            addCriterion("lock_sim_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidEqualTo(Integer value) {
            addCriterion("lock_sim_uuid =", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidNotEqualTo(Integer value) {
            addCriterion("lock_sim_uuid <>", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidGreaterThan(Integer value) {
            addCriterion("lock_sim_uuid >", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_sim_uuid >=", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidLessThan(Integer value) {
            addCriterion("lock_sim_uuid <", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidLessThanOrEqualTo(Integer value) {
            addCriterion("lock_sim_uuid <=", value, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidIn(List<Integer> values) {
            addCriterion("lock_sim_uuid in", values, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidNotIn(List<Integer> values) {
            addCriterion("lock_sim_uuid not in", values, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidBetween(Integer value1, Integer value2) {
            addCriterion("lock_sim_uuid between", value1, value2, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockSimUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_sim_uuid not between", value1, value2, "lockSimUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidIsNull() {
            addCriterion("lock_bkp_uuid is null");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidIsNotNull() {
            addCriterion("lock_bkp_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidEqualTo(Integer value) {
            addCriterion("lock_bkp_uuid =", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidNotEqualTo(Integer value) {
            addCriterion("lock_bkp_uuid <>", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidGreaterThan(Integer value) {
            addCriterion("lock_bkp_uuid >", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_bkp_uuid >=", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidLessThan(Integer value) {
            addCriterion("lock_bkp_uuid <", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidLessThanOrEqualTo(Integer value) {
            addCriterion("lock_bkp_uuid <=", value, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidIn(List<Integer> values) {
            addCriterion("lock_bkp_uuid in", values, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidNotIn(List<Integer> values) {
            addCriterion("lock_bkp_uuid not in", values, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidBetween(Integer value1, Integer value2) {
            addCriterion("lock_bkp_uuid between", value1, value2, "lockBkpUuid");
            return (Criteria) this;
        }

        public Criteria andLockBkpUuidNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_bkp_uuid not between", value1, value2, "lockBkpUuid");
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