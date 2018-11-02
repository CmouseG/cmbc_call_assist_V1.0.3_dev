package com.guiji.dispatch.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DispatchPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public DispatchPlanExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPlanUuidIsNull() {
            addCriterion("plan_uuid is null");
            return (Criteria) this;
        }

        public Criteria andPlanUuidIsNotNull() {
            addCriterion("plan_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andPlanUuidEqualTo(String value) {
            addCriterion("plan_uuid =", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidNotEqualTo(String value) {
            addCriterion("plan_uuid <>", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidGreaterThan(String value) {
            addCriterion("plan_uuid >", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidGreaterThanOrEqualTo(String value) {
            addCriterion("plan_uuid >=", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidLessThan(String value) {
            addCriterion("plan_uuid <", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidLessThanOrEqualTo(String value) {
            addCriterion("plan_uuid <=", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidLike(String value) {
            addCriterion("plan_uuid like", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidNotLike(String value) {
            addCriterion("plan_uuid not like", value, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidIn(List<String> values) {
            addCriterion("plan_uuid in", values, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidNotIn(List<String> values) {
            addCriterion("plan_uuid not in", values, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidBetween(String value1, String value2) {
            addCriterion("plan_uuid between", value1, value2, "planUuid");
            return (Criteria) this;
        }

        public Criteria andPlanUuidNotBetween(String value1, String value2) {
            addCriterion("plan_uuid not between", value1, value2, "planUuid");
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

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andBatchIdIsNull() {
            addCriterion("batch_id is null");
            return (Criteria) this;
        }

        public Criteria andBatchIdIsNotNull() {
            addCriterion("batch_id is not null");
            return (Criteria) this;
        }

        public Criteria andBatchIdEqualTo(Integer value) {
            addCriterion("batch_id =", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotEqualTo(Integer value) {
            addCriterion("batch_id <>", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdGreaterThan(Integer value) {
            addCriterion("batch_id >", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("batch_id >=", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdLessThan(Integer value) {
            addCriterion("batch_id <", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdLessThanOrEqualTo(Integer value) {
            addCriterion("batch_id <=", value, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdIn(List<Integer> values) {
            addCriterion("batch_id in", values, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotIn(List<Integer> values) {
            addCriterion("batch_id not in", values, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdBetween(Integer value1, Integer value2) {
            addCriterion("batch_id between", value1, value2, "batchId");
            return (Criteria) this;
        }

        public Criteria andBatchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("batch_id not between", value1, value2, "batchId");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAttachIsNull() {
            addCriterion("attach is null");
            return (Criteria) this;
        }

        public Criteria andAttachIsNotNull() {
            addCriterion("attach is not null");
            return (Criteria) this;
        }

        public Criteria andAttachEqualTo(String value) {
            addCriterion("attach =", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachNotEqualTo(String value) {
            addCriterion("attach <>", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachGreaterThan(String value) {
            addCriterion("attach >", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachGreaterThanOrEqualTo(String value) {
            addCriterion("attach >=", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachLessThan(String value) {
            addCriterion("attach <", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachLessThanOrEqualTo(String value) {
            addCriterion("attach <=", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachLike(String value) {
            addCriterion("attach like", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachNotLike(String value) {
            addCriterion("attach not like", value, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachIn(List<String> values) {
            addCriterion("attach in", values, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachNotIn(List<String> values) {
            addCriterion("attach not in", values, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachBetween(String value1, String value2) {
            addCriterion("attach between", value1, value2, "attach");
            return (Criteria) this;
        }

        public Criteria andAttachNotBetween(String value1, String value2) {
            addCriterion("attach not between", value1, value2, "attach");
            return (Criteria) this;
        }

        public Criteria andParamsIsNull() {
            addCriterion("params is null");
            return (Criteria) this;
        }

        public Criteria andParamsIsNotNull() {
            addCriterion("params is not null");
            return (Criteria) this;
        }

        public Criteria andParamsEqualTo(String value) {
            addCriterion("params =", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotEqualTo(String value) {
            addCriterion("params <>", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThan(String value) {
            addCriterion("params >", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThanOrEqualTo(String value) {
            addCriterion("params >=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThan(String value) {
            addCriterion("params <", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThanOrEqualTo(String value) {
            addCriterion("params <=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLike(String value) {
            addCriterion("params like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotLike(String value) {
            addCriterion("params not like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsIn(List<String> values) {
            addCriterion("params in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotIn(List<String> values) {
            addCriterion("params not in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsBetween(String value1, String value2) {
            addCriterion("params between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotBetween(String value1, String value2) {
            addCriterion("params not between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andStatusPlanIsNull() {
            addCriterion("status_plan is null");
            return (Criteria) this;
        }

        public Criteria andStatusPlanIsNotNull() {
            addCriterion("status_plan is not null");
            return (Criteria) this;
        }

        public Criteria andStatusPlanEqualTo(Integer value) {
            addCriterion("status_plan =", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanNotEqualTo(Integer value) {
            addCriterion("status_plan <>", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanGreaterThan(Integer value) {
            addCriterion("status_plan >", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_plan >=", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanLessThan(Integer value) {
            addCriterion("status_plan <", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanLessThanOrEqualTo(Integer value) {
            addCriterion("status_plan <=", value, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanIn(List<Integer> values) {
            addCriterion("status_plan in", values, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanNotIn(List<Integer> values) {
            addCriterion("status_plan not in", values, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanBetween(Integer value1, Integer value2) {
            addCriterion("status_plan between", value1, value2, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusPlanNotBetween(Integer value1, Integer value2) {
            addCriterion("status_plan not between", value1, value2, "statusPlan");
            return (Criteria) this;
        }

        public Criteria andStatusSyncIsNull() {
            addCriterion("status_sync is null");
            return (Criteria) this;
        }

        public Criteria andStatusSyncIsNotNull() {
            addCriterion("status_sync is not null");
            return (Criteria) this;
        }

        public Criteria andStatusSyncEqualTo(Integer value) {
            addCriterion("status_sync =", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncNotEqualTo(Integer value) {
            addCriterion("status_sync <>", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncGreaterThan(Integer value) {
            addCriterion("status_sync >", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_sync >=", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncLessThan(Integer value) {
            addCriterion("status_sync <", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncLessThanOrEqualTo(Integer value) {
            addCriterion("status_sync <=", value, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncIn(List<Integer> values) {
            addCriterion("status_sync in", values, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncNotIn(List<Integer> values) {
            addCriterion("status_sync not in", values, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncBetween(Integer value1, Integer value2) {
            addCriterion("status_sync between", value1, value2, "statusSync");
            return (Criteria) this;
        }

        public Criteria andStatusSyncNotBetween(Integer value1, Integer value2) {
            addCriterion("status_sync not between", value1, value2, "statusSync");
            return (Criteria) this;
        }

        public Criteria andRecallIsNull() {
            addCriterion("recall is null");
            return (Criteria) this;
        }

        public Criteria andRecallIsNotNull() {
            addCriterion("recall is not null");
            return (Criteria) this;
        }

        public Criteria andRecallEqualTo(Integer value) {
            addCriterion("recall =", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotEqualTo(Integer value) {
            addCriterion("recall <>", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallGreaterThan(Integer value) {
            addCriterion("recall >", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallGreaterThanOrEqualTo(Integer value) {
            addCriterion("recall >=", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallLessThan(Integer value) {
            addCriterion("recall <", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallLessThanOrEqualTo(Integer value) {
            addCriterion("recall <=", value, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallIn(List<Integer> values) {
            addCriterion("recall in", values, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotIn(List<Integer> values) {
            addCriterion("recall not in", values, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallBetween(Integer value1, Integer value2) {
            addCriterion("recall between", value1, value2, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallNotBetween(Integer value1, Integer value2) {
            addCriterion("recall not between", value1, value2, "recall");
            return (Criteria) this;
        }

        public Criteria andRecallParamsIsNull() {
            addCriterion("recall_params is null");
            return (Criteria) this;
        }

        public Criteria andRecallParamsIsNotNull() {
            addCriterion("recall_params is not null");
            return (Criteria) this;
        }

        public Criteria andRecallParamsEqualTo(String value) {
            addCriterion("recall_params =", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsNotEqualTo(String value) {
            addCriterion("recall_params <>", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsGreaterThan(String value) {
            addCriterion("recall_params >", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsGreaterThanOrEqualTo(String value) {
            addCriterion("recall_params >=", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsLessThan(String value) {
            addCriterion("recall_params <", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsLessThanOrEqualTo(String value) {
            addCriterion("recall_params <=", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsLike(String value) {
            addCriterion("recall_params like", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsNotLike(String value) {
            addCriterion("recall_params not like", value, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsIn(List<String> values) {
            addCriterion("recall_params in", values, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsNotIn(List<String> values) {
            addCriterion("recall_params not in", values, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsBetween(String value1, String value2) {
            addCriterion("recall_params between", value1, value2, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRecallParamsNotBetween(String value1, String value2) {
            addCriterion("recall_params not between", value1, value2, "recallParams");
            return (Criteria) this;
        }

        public Criteria andRobotIsNull() {
            addCriterion("robot is null");
            return (Criteria) this;
        }

        public Criteria andRobotIsNotNull() {
            addCriterion("robot is not null");
            return (Criteria) this;
        }

        public Criteria andRobotEqualTo(String value) {
            addCriterion("robot =", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotNotEqualTo(String value) {
            addCriterion("robot <>", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotGreaterThan(String value) {
            addCriterion("robot >", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotGreaterThanOrEqualTo(String value) {
            addCriterion("robot >=", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotLessThan(String value) {
            addCriterion("robot <", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotLessThanOrEqualTo(String value) {
            addCriterion("robot <=", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotLike(String value) {
            addCriterion("robot like", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotNotLike(String value) {
            addCriterion("robot not like", value, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotIn(List<String> values) {
            addCriterion("robot in", values, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotNotIn(List<String> values) {
            addCriterion("robot not in", values, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotBetween(String value1, String value2) {
            addCriterion("robot between", value1, value2, "robot");
            return (Criteria) this;
        }

        public Criteria andRobotNotBetween(String value1, String value2) {
            addCriterion("robot not between", value1, value2, "robot");
            return (Criteria) this;
        }

        public Criteria andLineIsNull() {
            addCriterion("line is null");
            return (Criteria) this;
        }

        public Criteria andLineIsNotNull() {
            addCriterion("line is not null");
            return (Criteria) this;
        }

        public Criteria andLineEqualTo(Integer value) {
            addCriterion("line =", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotEqualTo(Integer value) {
            addCriterion("line <>", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThan(Integer value) {
            addCriterion("line >", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineGreaterThanOrEqualTo(Integer value) {
            addCriterion("line >=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThan(Integer value) {
            addCriterion("line <", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineLessThanOrEqualTo(Integer value) {
            addCriterion("line <=", value, "line");
            return (Criteria) this;
        }

        public Criteria andLineIn(List<Integer> values) {
            addCriterion("line in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotIn(List<Integer> values) {
            addCriterion("line not in", values, "line");
            return (Criteria) this;
        }

        public Criteria andLineBetween(Integer value1, Integer value2) {
            addCriterion("line between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andLineNotBetween(Integer value1, Integer value2) {
            addCriterion("line not between", value1, value2, "line");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("`result` is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("`result` is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("`result` =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("`result` <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("`result` >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("`result` >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("`result` <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("`result` <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("`result` like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("`result` not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("`result` in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("`result` not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("`result` between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("`result` not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andCallAgentIsNull() {
            addCriterion("call_agent is null");
            return (Criteria) this;
        }

        public Criteria andCallAgentIsNotNull() {
            addCriterion("call_agent is not null");
            return (Criteria) this;
        }

        public Criteria andCallAgentEqualTo(String value) {
            addCriterion("call_agent =", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentNotEqualTo(String value) {
            addCriterion("call_agent <>", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentGreaterThan(String value) {
            addCriterion("call_agent >", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentGreaterThanOrEqualTo(String value) {
            addCriterion("call_agent >=", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentLessThan(String value) {
            addCriterion("call_agent <", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentLessThanOrEqualTo(String value) {
            addCriterion("call_agent <=", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentLike(String value) {
            addCriterion("call_agent like", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentNotLike(String value) {
            addCriterion("call_agent not like", value, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentIn(List<String> values) {
            addCriterion("call_agent in", values, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentNotIn(List<String> values) {
            addCriterion("call_agent not in", values, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentBetween(String value1, String value2) {
            addCriterion("call_agent between", value1, value2, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCallAgentNotBetween(String value1, String value2) {
            addCriterion("call_agent not between", value1, value2, "callAgent");
            return (Criteria) this;
        }

        public Criteria andCleanIsNull() {
            addCriterion("clean is null");
            return (Criteria) this;
        }

        public Criteria andCleanIsNotNull() {
            addCriterion("clean is not null");
            return (Criteria) this;
        }

        public Criteria andCleanEqualTo(Integer value) {
            addCriterion("clean =", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotEqualTo(Integer value) {
            addCriterion("clean <>", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanGreaterThan(Integer value) {
            addCriterion("clean >", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanGreaterThanOrEqualTo(Integer value) {
            addCriterion("clean >=", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanLessThan(Integer value) {
            addCriterion("clean <", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanLessThanOrEqualTo(Integer value) {
            addCriterion("clean <=", value, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanIn(List<Integer> values) {
            addCriterion("clean in", values, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotIn(List<Integer> values) {
            addCriterion("clean not in", values, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanBetween(Integer value1, Integer value2) {
            addCriterion("clean between", value1, value2, "clean");
            return (Criteria) this;
        }

        public Criteria andCleanNotBetween(Integer value1, Integer value2) {
            addCriterion("clean not between", value1, value2, "clean");
            return (Criteria) this;
        }

        public Criteria andCallDataIsNull() {
            addCriterion("call_data is null");
            return (Criteria) this;
        }

        public Criteria andCallDataIsNotNull() {
            addCriterion("call_data is not null");
            return (Criteria) this;
        }

        public Criteria andCallDataEqualTo(Integer value) {
            addCriterion("call_data =", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataNotEqualTo(Integer value) {
            addCriterion("call_data <>", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataGreaterThan(Integer value) {
            addCriterion("call_data >", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataGreaterThanOrEqualTo(Integer value) {
            addCriterion("call_data >=", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataLessThan(Integer value) {
            addCriterion("call_data <", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataLessThanOrEqualTo(Integer value) {
            addCriterion("call_data <=", value, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataIn(List<Integer> values) {
            addCriterion("call_data in", values, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataNotIn(List<Integer> values) {
            addCriterion("call_data not in", values, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataBetween(Integer value1, Integer value2) {
            addCriterion("call_data between", value1, value2, "callData");
            return (Criteria) this;
        }

        public Criteria andCallDataNotBetween(Integer value1, Integer value2) {
            addCriterion("call_data not between", value1, value2, "callData");
            return (Criteria) this;
        }

        public Criteria andCallHourIsNull() {
            addCriterion("call_hour is null");
            return (Criteria) this;
        }

        public Criteria andCallHourIsNotNull() {
            addCriterion("call_hour is not null");
            return (Criteria) this;
        }

        public Criteria andCallHourEqualTo(String value) {
            addCriterion("call_hour =", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourNotEqualTo(String value) {
            addCriterion("call_hour <>", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourGreaterThan(String value) {
            addCriterion("call_hour >", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourGreaterThanOrEqualTo(String value) {
            addCriterion("call_hour >=", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourLessThan(String value) {
            addCriterion("call_hour <", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourLessThanOrEqualTo(String value) {
            addCriterion("call_hour <=", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourLike(String value) {
            addCriterion("call_hour like", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourNotLike(String value) {
            addCriterion("call_hour not like", value, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourIn(List<String> values) {
            addCriterion("call_hour in", values, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourNotIn(List<String> values) {
            addCriterion("call_hour not in", values, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourBetween(String value1, String value2) {
            addCriterion("call_hour between", value1, value2, "callHour");
            return (Criteria) this;
        }

        public Criteria andCallHourNotBetween(String value1, String value2) {
            addCriterion("call_hour not between", value1, value2, "callHour");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andIsTtsIsNull() {
            addCriterion("is_tts is null");
            return (Criteria) this;
        }

        public Criteria andIsTtsIsNotNull() {
            addCriterion("is_tts is not null");
            return (Criteria) this;
        }

        public Criteria andIsTtsEqualTo(Byte value) {
            addCriterion("is_tts =", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsNotEqualTo(Byte value) {
            addCriterion("is_tts <>", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsGreaterThan(Byte value) {
            addCriterion("is_tts >", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_tts >=", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsLessThan(Byte value) {
            addCriterion("is_tts <", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsLessThanOrEqualTo(Byte value) {
            addCriterion("is_tts <=", value, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsIn(List<Byte> values) {
            addCriterion("is_tts in", values, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsNotIn(List<Byte> values) {
            addCriterion("is_tts not in", values, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsBetween(Byte value1, Byte value2) {
            addCriterion("is_tts between", value1, value2, "isTts");
            return (Criteria) this;
        }

        public Criteria andIsTtsNotBetween(Byte value1, Byte value2) {
            addCriterion("is_tts not between", value1, value2, "isTts");
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