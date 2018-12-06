package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class ReportCallCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ReportCallCountExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCallDateIsNull() {
            addCriterion("call_date is null");
            return (Criteria) this;
        }

        public Criteria andCallDateIsNotNull() {
            addCriterion("call_date is not null");
            return (Criteria) this;
        }

        public Criteria andCallDateEqualTo(String value) {
            addCriterion("call_date =", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotEqualTo(String value) {
            addCriterion("call_date <>", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateGreaterThan(String value) {
            addCriterion("call_date >", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateGreaterThanOrEqualTo(String value) {
            addCriterion("call_date >=", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLessThan(String value) {
            addCriterion("call_date <", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLessThanOrEqualTo(String value) {
            addCriterion("call_date <=", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateLike(String value) {
            addCriterion("call_date like", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotLike(String value) {
            addCriterion("call_date not like", value, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateIn(List<String> values) {
            addCriterion("call_date in", values, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotIn(List<String> values) {
            addCriterion("call_date not in", values, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateBetween(String value1, String value2) {
            addCriterion("call_date between", value1, value2, "callDate");
            return (Criteria) this;
        }

        public Criteria andCallDateNotBetween(String value1, String value2) {
            addCriterion("call_date not between", value1, value2, "callDate");
            return (Criteria) this;
        }

        public Criteria andCountAIsNull() {
            addCriterion("count_a is null");
            return (Criteria) this;
        }

        public Criteria andCountAIsNotNull() {
            addCriterion("count_a is not null");
            return (Criteria) this;
        }

        public Criteria andCountAEqualTo(Integer value) {
            addCriterion("count_a =", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountANotEqualTo(Integer value) {
            addCriterion("count_a <>", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountAGreaterThan(Integer value) {
            addCriterion("count_a >", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountAGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_a >=", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountALessThan(Integer value) {
            addCriterion("count_a <", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountALessThanOrEqualTo(Integer value) {
            addCriterion("count_a <=", value, "countA");
            return (Criteria) this;
        }

        public Criteria andCountAIn(List<Integer> values) {
            addCriterion("count_a in", values, "countA");
            return (Criteria) this;
        }

        public Criteria andCountANotIn(List<Integer> values) {
            addCriterion("count_a not in", values, "countA");
            return (Criteria) this;
        }

        public Criteria andCountABetween(Integer value1, Integer value2) {
            addCriterion("count_a between", value1, value2, "countA");
            return (Criteria) this;
        }

        public Criteria andCountANotBetween(Integer value1, Integer value2) {
            addCriterion("count_a not between", value1, value2, "countA");
            return (Criteria) this;
        }

        public Criteria andCountBIsNull() {
            addCriterion("count_b is null");
            return (Criteria) this;
        }

        public Criteria andCountBIsNotNull() {
            addCriterion("count_b is not null");
            return (Criteria) this;
        }

        public Criteria andCountBEqualTo(Integer value) {
            addCriterion("count_b =", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBNotEqualTo(Integer value) {
            addCriterion("count_b <>", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBGreaterThan(Integer value) {
            addCriterion("count_b >", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_b >=", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBLessThan(Integer value) {
            addCriterion("count_b <", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBLessThanOrEqualTo(Integer value) {
            addCriterion("count_b <=", value, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBIn(List<Integer> values) {
            addCriterion("count_b in", values, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBNotIn(List<Integer> values) {
            addCriterion("count_b not in", values, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBBetween(Integer value1, Integer value2) {
            addCriterion("count_b between", value1, value2, "countB");
            return (Criteria) this;
        }

        public Criteria andCountBNotBetween(Integer value1, Integer value2) {
            addCriterion("count_b not between", value1, value2, "countB");
            return (Criteria) this;
        }

        public Criteria andCountCIsNull() {
            addCriterion("count_c is null");
            return (Criteria) this;
        }

        public Criteria andCountCIsNotNull() {
            addCriterion("count_c is not null");
            return (Criteria) this;
        }

        public Criteria andCountCEqualTo(Integer value) {
            addCriterion("count_c =", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCNotEqualTo(Integer value) {
            addCriterion("count_c <>", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCGreaterThan(Integer value) {
            addCriterion("count_c >", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_c >=", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCLessThan(Integer value) {
            addCriterion("count_c <", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCLessThanOrEqualTo(Integer value) {
            addCriterion("count_c <=", value, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCIn(List<Integer> values) {
            addCriterion("count_c in", values, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCNotIn(List<Integer> values) {
            addCriterion("count_c not in", values, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCBetween(Integer value1, Integer value2) {
            addCriterion("count_c between", value1, value2, "countC");
            return (Criteria) this;
        }

        public Criteria andCountCNotBetween(Integer value1, Integer value2) {
            addCriterion("count_c not between", value1, value2, "countC");
            return (Criteria) this;
        }

        public Criteria andCountDIsNull() {
            addCriterion("count_d is null");
            return (Criteria) this;
        }

        public Criteria andCountDIsNotNull() {
            addCriterion("count_d is not null");
            return (Criteria) this;
        }

        public Criteria andCountDEqualTo(Integer value) {
            addCriterion("count_d =", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDNotEqualTo(Integer value) {
            addCriterion("count_d <>", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDGreaterThan(Integer value) {
            addCriterion("count_d >", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_d >=", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDLessThan(Integer value) {
            addCriterion("count_d <", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDLessThanOrEqualTo(Integer value) {
            addCriterion("count_d <=", value, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDIn(List<Integer> values) {
            addCriterion("count_d in", values, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDNotIn(List<Integer> values) {
            addCriterion("count_d not in", values, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDBetween(Integer value1, Integer value2) {
            addCriterion("count_d between", value1, value2, "countD");
            return (Criteria) this;
        }

        public Criteria andCountDNotBetween(Integer value1, Integer value2) {
            addCriterion("count_d not between", value1, value2, "countD");
            return (Criteria) this;
        }

        public Criteria andCountEIsNull() {
            addCriterion("count_e is null");
            return (Criteria) this;
        }

        public Criteria andCountEIsNotNull() {
            addCriterion("count_e is not null");
            return (Criteria) this;
        }

        public Criteria andCountEEqualTo(Integer value) {
            addCriterion("count_e =", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountENotEqualTo(Integer value) {
            addCriterion("count_e <>", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountEGreaterThan(Integer value) {
            addCriterion("count_e >", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountEGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_e >=", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountELessThan(Integer value) {
            addCriterion("count_e <", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountELessThanOrEqualTo(Integer value) {
            addCriterion("count_e <=", value, "countE");
            return (Criteria) this;
        }

        public Criteria andCountEIn(List<Integer> values) {
            addCriterion("count_e in", values, "countE");
            return (Criteria) this;
        }

        public Criteria andCountENotIn(List<Integer> values) {
            addCriterion("count_e not in", values, "countE");
            return (Criteria) this;
        }

        public Criteria andCountEBetween(Integer value1, Integer value2) {
            addCriterion("count_e between", value1, value2, "countE");
            return (Criteria) this;
        }

        public Criteria andCountENotBetween(Integer value1, Integer value2) {
            addCriterion("count_e not between", value1, value2, "countE");
            return (Criteria) this;
        }

        public Criteria andCountFIsNull() {
            addCriterion("count_f is null");
            return (Criteria) this;
        }

        public Criteria andCountFIsNotNull() {
            addCriterion("count_f is not null");
            return (Criteria) this;
        }

        public Criteria andCountFEqualTo(Integer value) {
            addCriterion("count_f =", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFNotEqualTo(Integer value) {
            addCriterion("count_f <>", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFGreaterThan(Integer value) {
            addCriterion("count_f >", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_f >=", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFLessThan(Integer value) {
            addCriterion("count_f <", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFLessThanOrEqualTo(Integer value) {
            addCriterion("count_f <=", value, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFIn(List<Integer> values) {
            addCriterion("count_f in", values, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFNotIn(List<Integer> values) {
            addCriterion("count_f not in", values, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFBetween(Integer value1, Integer value2) {
            addCriterion("count_f between", value1, value2, "countF");
            return (Criteria) this;
        }

        public Criteria andCountFNotBetween(Integer value1, Integer value2) {
            addCriterion("count_f not between", value1, value2, "countF");
            return (Criteria) this;
        }

        public Criteria andCountUIsNull() {
            addCriterion("count_u is null");
            return (Criteria) this;
        }

        public Criteria andCountUIsNotNull() {
            addCriterion("count_u is not null");
            return (Criteria) this;
        }

        public Criteria andCountUEqualTo(Integer value) {
            addCriterion("count_u =", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUNotEqualTo(Integer value) {
            addCriterion("count_u <>", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUGreaterThan(Integer value) {
            addCriterion("count_u >", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_u >=", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountULessThan(Integer value) {
            addCriterion("count_u <", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountULessThanOrEqualTo(Integer value) {
            addCriterion("count_u <=", value, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUIn(List<Integer> values) {
            addCriterion("count_u in", values, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUNotIn(List<Integer> values) {
            addCriterion("count_u not in", values, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUBetween(Integer value1, Integer value2) {
            addCriterion("count_u between", value1, value2, "countU");
            return (Criteria) this;
        }

        public Criteria andCountUNotBetween(Integer value1, Integer value2) {
            addCriterion("count_u not between", value1, value2, "countU");
            return (Criteria) this;
        }

        public Criteria andCountVIsNull() {
            addCriterion("count_v is null");
            return (Criteria) this;
        }

        public Criteria andCountVIsNotNull() {
            addCriterion("count_v is not null");
            return (Criteria) this;
        }

        public Criteria andCountVEqualTo(Integer value) {
            addCriterion("count_v =", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVNotEqualTo(Integer value) {
            addCriterion("count_v <>", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVGreaterThan(Integer value) {
            addCriterion("count_v >", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_v >=", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVLessThan(Integer value) {
            addCriterion("count_v <", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVLessThanOrEqualTo(Integer value) {
            addCriterion("count_v <=", value, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVIn(List<Integer> values) {
            addCriterion("count_v in", values, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVNotIn(List<Integer> values) {
            addCriterion("count_v not in", values, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVBetween(Integer value1, Integer value2) {
            addCriterion("count_v between", value1, value2, "countV");
            return (Criteria) this;
        }

        public Criteria andCountVNotBetween(Integer value1, Integer value2) {
            addCriterion("count_v not between", value1, value2, "countV");
            return (Criteria) this;
        }

        public Criteria andCountWIsNull() {
            addCriterion("count_w is null");
            return (Criteria) this;
        }

        public Criteria andCountWIsNotNull() {
            addCriterion("count_w is not null");
            return (Criteria) this;
        }

        public Criteria andCountWEqualTo(Integer value) {
            addCriterion("count_w =", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWNotEqualTo(Integer value) {
            addCriterion("count_w <>", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWGreaterThan(Integer value) {
            addCriterion("count_w >", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_w >=", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWLessThan(Integer value) {
            addCriterion("count_w <", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWLessThanOrEqualTo(Integer value) {
            addCriterion("count_w <=", value, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWIn(List<Integer> values) {
            addCriterion("count_w in", values, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWNotIn(List<Integer> values) {
            addCriterion("count_w not in", values, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWBetween(Integer value1, Integer value2) {
            addCriterion("count_w between", value1, value2, "countW");
            return (Criteria) this;
        }

        public Criteria andCountWNotBetween(Integer value1, Integer value2) {
            addCriterion("count_w not between", value1, value2, "countW");
            return (Criteria) this;
        }

        public Criteria andCountAllIsNull() {
            addCriterion("count_all is null");
            return (Criteria) this;
        }

        public Criteria andCountAllIsNotNull() {
            addCriterion("count_all is not null");
            return (Criteria) this;
        }

        public Criteria andCountAllEqualTo(Integer value) {
            addCriterion("count_all =", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllNotEqualTo(Integer value) {
            addCriterion("count_all <>", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllGreaterThan(Integer value) {
            addCriterion("count_all >", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllGreaterThanOrEqualTo(Integer value) {
            addCriterion("count_all >=", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllLessThan(Integer value) {
            addCriterion("count_all <", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllLessThanOrEqualTo(Integer value) {
            addCriterion("count_all <=", value, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllIn(List<Integer> values) {
            addCriterion("count_all in", values, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllNotIn(List<Integer> values) {
            addCriterion("count_all not in", values, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllBetween(Integer value1, Integer value2) {
            addCriterion("count_all between", value1, value2, "countAll");
            return (Criteria) this;
        }

        public Criteria andCountAllNotBetween(Integer value1, Integer value2) {
            addCriterion("count_all not between", value1, value2, "countAll");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentIsNull() {
            addCriterion("accurate_intent is null");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentIsNotNull() {
            addCriterion("accurate_intent is not null");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentEqualTo(String value) {
            addCriterion("accurate_intent =", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotEqualTo(String value) {
            addCriterion("accurate_intent <>", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentGreaterThan(String value) {
            addCriterion("accurate_intent >", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentGreaterThanOrEqualTo(String value) {
            addCriterion("accurate_intent >=", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLessThan(String value) {
            addCriterion("accurate_intent <", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLessThanOrEqualTo(String value) {
            addCriterion("accurate_intent <=", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentLike(String value) {
            addCriterion("accurate_intent like", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotLike(String value) {
            addCriterion("accurate_intent not like", value, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentIn(List<String> values) {
            addCriterion("accurate_intent in", values, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotIn(List<String> values) {
            addCriterion("accurate_intent not in", values, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentBetween(String value1, String value2) {
            addCriterion("accurate_intent between", value1, value2, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andAccurateIntentNotBetween(String value1, String value2) {
            addCriterion("accurate_intent not between", value1, value2, "accurateIntent");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("customer_id like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("customer_id not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
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