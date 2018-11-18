package ai.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BotSentenceNodeRelateionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceNodeRelateionExample() {
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

        public Criteria andNodeRelateionIdIsNull() {
            addCriterion("node_relateion_id is null");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdIsNotNull() {
            addCriterion("node_relateion_id is not null");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdEqualTo(String value) {
            addCriterion("node_relateion_id =", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdNotEqualTo(String value) {
            addCriterion("node_relateion_id <>", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdGreaterThan(String value) {
            addCriterion("node_relateion_id >", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdGreaterThanOrEqualTo(String value) {
            addCriterion("node_relateion_id >=", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdLessThan(String value) {
            addCriterion("node_relateion_id <", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdLessThanOrEqualTo(String value) {
            addCriterion("node_relateion_id <=", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdLike(String value) {
            addCriterion("node_relateion_id like", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdNotLike(String value) {
            addCriterion("node_relateion_id not like", value, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdIn(List<String> values) {
            addCriterion("node_relateion_id in", values, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdNotIn(List<String> values) {
            addCriterion("node_relateion_id not in", values, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdBetween(String value1, String value2) {
            addCriterion("node_relateion_id between", value1, value2, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeRelateionIdNotBetween(String value1, String value2) {
            addCriterion("node_relateion_id not between", value1, value2, "nodeRelateionId");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNull() {
            addCriterion("node_id is null");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNotNull() {
            addCriterion("node_id is not null");
            return (Criteria) this;
        }

        public Criteria andNodeIdEqualTo(String value) {
            addCriterion("node_id =", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotEqualTo(String value) {
            addCriterion("node_id <>", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThan(String value) {
            addCriterion("node_id >", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("node_id >=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThan(String value) {
            addCriterion("node_id <", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThanOrEqualTo(String value) {
            addCriterion("node_id <=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLike(String value) {
            addCriterion("node_id like", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotLike(String value) {
            addCriterion("node_id not like", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdIn(List<String> values) {
            addCriterion("node_id in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotIn(List<String> values) {
            addCriterion("node_id not in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdBetween(String value1, String value2) {
            addCriterion("node_id between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotBetween(String value1, String value2) {
            addCriterion("node_id not between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNull() {
            addCriterion("node_name is null");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNotNull() {
            addCriterion("node_name is not null");
            return (Criteria) this;
        }

        public Criteria andNodeNameEqualTo(String value) {
            addCriterion("node_name =", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotEqualTo(String value) {
            addCriterion("node_name <>", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThan(String value) {
            addCriterion("node_name >", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("node_name >=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThan(String value) {
            addCriterion("node_name <", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThanOrEqualTo(String value) {
            addCriterion("node_name <=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLike(String value) {
            addCriterion("node_name like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotLike(String value) {
            addCriterion("node_name not like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameIn(List<String> values) {
            addCriterion("node_name in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotIn(List<String> values) {
            addCriterion("node_name not in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameBetween(String value1, String value2) {
            addCriterion("node_name between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotBetween(String value1, String value2) {
            addCriterion("node_name not between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdIsNull() {
            addCriterion("child_node_id is null");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdIsNotNull() {
            addCriterion("child_node_id is not null");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdEqualTo(String value) {
            addCriterion("child_node_id =", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdNotEqualTo(String value) {
            addCriterion("child_node_id <>", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdGreaterThan(String value) {
            addCriterion("child_node_id >", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("child_node_id >=", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdLessThan(String value) {
            addCriterion("child_node_id <", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdLessThanOrEqualTo(String value) {
            addCriterion("child_node_id <=", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdLike(String value) {
            addCriterion("child_node_id like", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdNotLike(String value) {
            addCriterion("child_node_id not like", value, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdIn(List<String> values) {
            addCriterion("child_node_id in", values, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdNotIn(List<String> values) {
            addCriterion("child_node_id not in", values, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdBetween(String value1, String value2) {
            addCriterion("child_node_id between", value1, value2, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeIdNotBetween(String value1, String value2) {
            addCriterion("child_node_id not between", value1, value2, "childNodeId");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameIsNull() {
            addCriterion("child_node_name is null");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameIsNotNull() {
            addCriterion("child_node_name is not null");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameEqualTo(String value) {
            addCriterion("child_node_name =", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameNotEqualTo(String value) {
            addCriterion("child_node_name <>", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameGreaterThan(String value) {
            addCriterion("child_node_name >", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("child_node_name >=", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameLessThan(String value) {
            addCriterion("child_node_name <", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameLessThanOrEqualTo(String value) {
            addCriterion("child_node_name <=", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameLike(String value) {
            addCriterion("child_node_name like", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameNotLike(String value) {
            addCriterion("child_node_name not like", value, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameIn(List<String> values) {
            addCriterion("child_node_name in", values, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameNotIn(List<String> values) {
            addCriterion("child_node_name not in", values, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameBetween(String value1, String value2) {
            addCriterion("child_node_name between", value1, value2, "childNodeName");
            return (Criteria) this;
        }

        public Criteria andChildNodeNameNotBetween(String value1, String value2) {
            addCriterion("child_node_name not between", value1, value2, "childNodeName");
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