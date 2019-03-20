package com.guiji.callcenter.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class FsBindExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FsBindExample() {
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

        public Criteria andServiceIdIsNull() {
            addCriterion("service_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("service_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(String value) {
            addCriterion("service_id =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(String value) {
            addCriterion("service_id <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(String value) {
            addCriterion("service_id >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("service_id >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(String value) {
            addCriterion("service_id <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(String value) {
            addCriterion("service_id <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLike(String value) {
            addCriterion("service_id like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotLike(String value) {
            addCriterion("service_id not like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<String> values) {
            addCriterion("service_id in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<String> values) {
            addCriterion("service_id not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(String value1, String value2) {
            addCriterion("service_id between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(String value1, String value2) {
            addCriterion("service_id not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdIsNull() {
            addCriterion("fs_agent_id is null");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdIsNotNull() {
            addCriterion("fs_agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdEqualTo(String value) {
            addCriterion("fs_agent_id =", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdNotEqualTo(String value) {
            addCriterion("fs_agent_id <>", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdGreaterThan(String value) {
            addCriterion("fs_agent_id >", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("fs_agent_id >=", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdLessThan(String value) {
            addCriterion("fs_agent_id <", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdLessThanOrEqualTo(String value) {
            addCriterion("fs_agent_id <=", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdLike(String value) {
            addCriterion("fs_agent_id like", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdNotLike(String value) {
            addCriterion("fs_agent_id not like", value, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdIn(List<String> values) {
            addCriterion("fs_agent_id in", values, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdNotIn(List<String> values) {
            addCriterion("fs_agent_id not in", values, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdBetween(String value1, String value2) {
            addCriterion("fs_agent_id between", value1, value2, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentIdNotBetween(String value1, String value2) {
            addCriterion("fs_agent_id not between", value1, value2, "fsAgentId");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrIsNull() {
            addCriterion("fs_agent_addr is null");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrIsNotNull() {
            addCriterion("fs_agent_addr is not null");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrEqualTo(String value) {
            addCriterion("fs_agent_addr =", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrNotEqualTo(String value) {
            addCriterion("fs_agent_addr <>", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrGreaterThan(String value) {
            addCriterion("fs_agent_addr >", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrGreaterThanOrEqualTo(String value) {
            addCriterion("fs_agent_addr >=", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrLessThan(String value) {
            addCriterion("fs_agent_addr <", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrLessThanOrEqualTo(String value) {
            addCriterion("fs_agent_addr <=", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrLike(String value) {
            addCriterion("fs_agent_addr like", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrNotLike(String value) {
            addCriterion("fs_agent_addr not like", value, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrIn(List<String> values) {
            addCriterion("fs_agent_addr in", values, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrNotIn(List<String> values) {
            addCriterion("fs_agent_addr not in", values, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrBetween(String value1, String value2) {
            addCriterion("fs_agent_addr between", value1, value2, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsAgentAddrNotBetween(String value1, String value2) {
            addCriterion("fs_agent_addr not between", value1, value2, "fsAgentAddr");
            return (Criteria) this;
        }

        public Criteria andFsEslPortIsNull() {
            addCriterion("fs_esl_port is null");
            return (Criteria) this;
        }

        public Criteria andFsEslPortIsNotNull() {
            addCriterion("fs_esl_port is not null");
            return (Criteria) this;
        }

        public Criteria andFsEslPortEqualTo(String value) {
            addCriterion("fs_esl_port =", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortNotEqualTo(String value) {
            addCriterion("fs_esl_port <>", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortGreaterThan(String value) {
            addCriterion("fs_esl_port >", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortGreaterThanOrEqualTo(String value) {
            addCriterion("fs_esl_port >=", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortLessThan(String value) {
            addCriterion("fs_esl_port <", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortLessThanOrEqualTo(String value) {
            addCriterion("fs_esl_port <=", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortLike(String value) {
            addCriterion("fs_esl_port like", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortNotLike(String value) {
            addCriterion("fs_esl_port not like", value, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortIn(List<String> values) {
            addCriterion("fs_esl_port in", values, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortNotIn(List<String> values) {
            addCriterion("fs_esl_port not in", values, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortBetween(String value1, String value2) {
            addCriterion("fs_esl_port between", value1, value2, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPortNotBetween(String value1, String value2) {
            addCriterion("fs_esl_port not between", value1, value2, "fsEslPort");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdIsNull() {
            addCriterion("fs_esl_pwd is null");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdIsNotNull() {
            addCriterion("fs_esl_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdEqualTo(String value) {
            addCriterion("fs_esl_pwd =", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdNotEqualTo(String value) {
            addCriterion("fs_esl_pwd <>", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdGreaterThan(String value) {
            addCriterion("fs_esl_pwd >", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdGreaterThanOrEqualTo(String value) {
            addCriterion("fs_esl_pwd >=", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdLessThan(String value) {
            addCriterion("fs_esl_pwd <", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdLessThanOrEqualTo(String value) {
            addCriterion("fs_esl_pwd <=", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdLike(String value) {
            addCriterion("fs_esl_pwd like", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdNotLike(String value) {
            addCriterion("fs_esl_pwd not like", value, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdIn(List<String> values) {
            addCriterion("fs_esl_pwd in", values, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdNotIn(List<String> values) {
            addCriterion("fs_esl_pwd not in", values, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdBetween(String value1, String value2) {
            addCriterion("fs_esl_pwd between", value1, value2, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsEslPwdNotBetween(String value1, String value2) {
            addCriterion("fs_esl_pwd not between", value1, value2, "fsEslPwd");
            return (Criteria) this;
        }

        public Criteria andFsInPortIsNull() {
            addCriterion("fs_in_port is null");
            return (Criteria) this;
        }

        public Criteria andFsInPortIsNotNull() {
            addCriterion("fs_in_port is not null");
            return (Criteria) this;
        }

        public Criteria andFsInPortEqualTo(String value) {
            addCriterion("fs_in_port =", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortNotEqualTo(String value) {
            addCriterion("fs_in_port <>", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortGreaterThan(String value) {
            addCriterion("fs_in_port >", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortGreaterThanOrEqualTo(String value) {
            addCriterion("fs_in_port >=", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortLessThan(String value) {
            addCriterion("fs_in_port <", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortLessThanOrEqualTo(String value) {
            addCriterion("fs_in_port <=", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortLike(String value) {
            addCriterion("fs_in_port like", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortNotLike(String value) {
            addCriterion("fs_in_port not like", value, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortIn(List<String> values) {
            addCriterion("fs_in_port in", values, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortNotIn(List<String> values) {
            addCriterion("fs_in_port not in", values, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortBetween(String value1, String value2) {
            addCriterion("fs_in_port between", value1, value2, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsInPortNotBetween(String value1, String value2) {
            addCriterion("fs_in_port not between", value1, value2, "fsInPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortIsNull() {
            addCriterion("fs_out_port is null");
            return (Criteria) this;
        }

        public Criteria andFsOutPortIsNotNull() {
            addCriterion("fs_out_port is not null");
            return (Criteria) this;
        }

        public Criteria andFsOutPortEqualTo(String value) {
            addCriterion("fs_out_port =", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortNotEqualTo(String value) {
            addCriterion("fs_out_port <>", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortGreaterThan(String value) {
            addCriterion("fs_out_port >", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortGreaterThanOrEqualTo(String value) {
            addCriterion("fs_out_port >=", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortLessThan(String value) {
            addCriterion("fs_out_port <", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortLessThanOrEqualTo(String value) {
            addCriterion("fs_out_port <=", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortLike(String value) {
            addCriterion("fs_out_port like", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortNotLike(String value) {
            addCriterion("fs_out_port not like", value, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortIn(List<String> values) {
            addCriterion("fs_out_port in", values, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortNotIn(List<String> values) {
            addCriterion("fs_out_port not in", values, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortBetween(String value1, String value2) {
            addCriterion("fs_out_port between", value1, value2, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andFsOutPortNotBetween(String value1, String value2) {
            addCriterion("fs_out_port not between", value1, value2, "fsOutPort");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("create_date like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("create_date not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
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