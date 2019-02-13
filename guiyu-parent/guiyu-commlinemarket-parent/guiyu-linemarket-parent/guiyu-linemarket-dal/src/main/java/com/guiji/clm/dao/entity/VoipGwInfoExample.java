package com.guiji.clm.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoipGwInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public VoipGwInfoExample() {
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

        public Criteria andGwNameIsNull() {
            addCriterion("gw_name is null");
            return (Criteria) this;
        }

        public Criteria andGwNameIsNotNull() {
            addCriterion("gw_name is not null");
            return (Criteria) this;
        }

        public Criteria andGwNameEqualTo(String value) {
            addCriterion("gw_name =", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameNotEqualTo(String value) {
            addCriterion("gw_name <>", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameGreaterThan(String value) {
            addCriterion("gw_name >", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameGreaterThanOrEqualTo(String value) {
            addCriterion("gw_name >=", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameLessThan(String value) {
            addCriterion("gw_name <", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameLessThanOrEqualTo(String value) {
            addCriterion("gw_name <=", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameLike(String value) {
            addCriterion("gw_name like", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameNotLike(String value) {
            addCriterion("gw_name not like", value, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameIn(List<String> values) {
            addCriterion("gw_name in", values, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameNotIn(List<String> values) {
            addCriterion("gw_name not in", values, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameBetween(String value1, String value2) {
            addCriterion("gw_name between", value1, value2, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwNameNotBetween(String value1, String value2) {
            addCriterion("gw_name not between", value1, value2, "gwName");
            return (Criteria) this;
        }

        public Criteria andGwBrandIsNull() {
            addCriterion("gw_brand is null");
            return (Criteria) this;
        }

        public Criteria andGwBrandIsNotNull() {
            addCriterion("gw_brand is not null");
            return (Criteria) this;
        }

        public Criteria andGwBrandEqualTo(String value) {
            addCriterion("gw_brand =", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandNotEqualTo(String value) {
            addCriterion("gw_brand <>", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandGreaterThan(String value) {
            addCriterion("gw_brand >", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandGreaterThanOrEqualTo(String value) {
            addCriterion("gw_brand >=", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandLessThan(String value) {
            addCriterion("gw_brand <", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandLessThanOrEqualTo(String value) {
            addCriterion("gw_brand <=", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandLike(String value) {
            addCriterion("gw_brand like", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandNotLike(String value) {
            addCriterion("gw_brand not like", value, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandIn(List<String> values) {
            addCriterion("gw_brand in", values, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandNotIn(List<String> values) {
            addCriterion("gw_brand not in", values, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandBetween(String value1, String value2) {
            addCriterion("gw_brand between", value1, value2, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwBrandNotBetween(String value1, String value2) {
            addCriterion("gw_brand not between", value1, value2, "gwBrand");
            return (Criteria) this;
        }

        public Criteria andGwVersionIsNull() {
            addCriterion("gw_version is null");
            return (Criteria) this;
        }

        public Criteria andGwVersionIsNotNull() {
            addCriterion("gw_version is not null");
            return (Criteria) this;
        }

        public Criteria andGwVersionEqualTo(String value) {
            addCriterion("gw_version =", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionNotEqualTo(String value) {
            addCriterion("gw_version <>", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionGreaterThan(String value) {
            addCriterion("gw_version >", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionGreaterThanOrEqualTo(String value) {
            addCriterion("gw_version >=", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionLessThan(String value) {
            addCriterion("gw_version <", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionLessThanOrEqualTo(String value) {
            addCriterion("gw_version <=", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionLike(String value) {
            addCriterion("gw_version like", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionNotLike(String value) {
            addCriterion("gw_version not like", value, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionIn(List<String> values) {
            addCriterion("gw_version in", values, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionNotIn(List<String> values) {
            addCriterion("gw_version not in", values, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionBetween(String value1, String value2) {
            addCriterion("gw_version between", value1, value2, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andGwVersionNotBetween(String value1, String value2) {
            addCriterion("gw_version not between", value1, value2, "gwVersion");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_Id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_Id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Integer value) {
            addCriterion("company_Id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Integer value) {
            addCriterion("company_Id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Integer value) {
            addCriterion("company_Id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_Id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Integer value) {
            addCriterion("company_Id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
            addCriterion("company_Id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Integer> values) {
            addCriterion("company_Id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Integer> values) {
            addCriterion("company_Id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
            addCriterion("company_Id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("company_Id not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andDevIdIsNull() {
            addCriterion("dev_id is null");
            return (Criteria) this;
        }

        public Criteria andDevIdIsNotNull() {
            addCriterion("dev_id is not null");
            return (Criteria) this;
        }

        public Criteria andDevIdEqualTo(Integer value) {
            addCriterion("dev_id =", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotEqualTo(Integer value) {
            addCriterion("dev_id <>", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdGreaterThan(Integer value) {
            addCriterion("dev_id >", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dev_id >=", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdLessThan(Integer value) {
            addCriterion("dev_id <", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdLessThanOrEqualTo(Integer value) {
            addCriterion("dev_id <=", value, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdIn(List<Integer> values) {
            addCriterion("dev_id in", values, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotIn(List<Integer> values) {
            addCriterion("dev_id not in", values, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdBetween(Integer value1, Integer value2) {
            addCriterion("dev_id between", value1, value2, "devId");
            return (Criteria) this;
        }

        public Criteria andDevIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dev_id not between", value1, value2, "devId");
            return (Criteria) this;
        }

        public Criteria andPortNumIsNull() {
            addCriterion("port_num is null");
            return (Criteria) this;
        }

        public Criteria andPortNumIsNotNull() {
            addCriterion("port_num is not null");
            return (Criteria) this;
        }

        public Criteria andPortNumEqualTo(Integer value) {
            addCriterion("port_num =", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumNotEqualTo(Integer value) {
            addCriterion("port_num <>", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumGreaterThan(Integer value) {
            addCriterion("port_num >", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_num >=", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumLessThan(Integer value) {
            addCriterion("port_num <", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumLessThanOrEqualTo(Integer value) {
            addCriterion("port_num <=", value, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumIn(List<Integer> values) {
            addCriterion("port_num in", values, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumNotIn(List<Integer> values) {
            addCriterion("port_num not in", values, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumBetween(Integer value1, Integer value2) {
            addCriterion("port_num between", value1, value2, "portNum");
            return (Criteria) this;
        }

        public Criteria andPortNumNotBetween(Integer value1, Integer value2) {
            addCriterion("port_num not between", value1, value2, "portNum");
            return (Criteria) this;
        }

        public Criteria andSipIpIsNull() {
            addCriterion("sip_ip is null");
            return (Criteria) this;
        }

        public Criteria andSipIpIsNotNull() {
            addCriterion("sip_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSipIpEqualTo(String value) {
            addCriterion("sip_ip =", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpNotEqualTo(String value) {
            addCriterion("sip_ip <>", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpGreaterThan(String value) {
            addCriterion("sip_ip >", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpGreaterThanOrEqualTo(String value) {
            addCriterion("sip_ip >=", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpLessThan(String value) {
            addCriterion("sip_ip <", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpLessThanOrEqualTo(String value) {
            addCriterion("sip_ip <=", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpLike(String value) {
            addCriterion("sip_ip like", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpNotLike(String value) {
            addCriterion("sip_ip not like", value, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpIn(List<String> values) {
            addCriterion("sip_ip in", values, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpNotIn(List<String> values) {
            addCriterion("sip_ip not in", values, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpBetween(String value1, String value2) {
            addCriterion("sip_ip between", value1, value2, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipIpNotBetween(String value1, String value2) {
            addCriterion("sip_ip not between", value1, value2, "sipIp");
            return (Criteria) this;
        }

        public Criteria andSipPortIsNull() {
            addCriterion("sip_port is null");
            return (Criteria) this;
        }

        public Criteria andSipPortIsNotNull() {
            addCriterion("sip_port is not null");
            return (Criteria) this;
        }

        public Criteria andSipPortEqualTo(Integer value) {
            addCriterion("sip_port =", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortNotEqualTo(Integer value) {
            addCriterion("sip_port <>", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortGreaterThan(Integer value) {
            addCriterion("sip_port >", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sip_port >=", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortLessThan(Integer value) {
            addCriterion("sip_port <", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortLessThanOrEqualTo(Integer value) {
            addCriterion("sip_port <=", value, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortIn(List<Integer> values) {
            addCriterion("sip_port in", values, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortNotIn(List<Integer> values) {
            addCriterion("sip_port not in", values, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortBetween(Integer value1, Integer value2) {
            addCriterion("sip_port between", value1, value2, "sipPort");
            return (Criteria) this;
        }

        public Criteria andSipPortNotBetween(Integer value1, Integer value2) {
            addCriterion("sip_port not between", value1, value2, "sipPort");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountIsNull() {
            addCriterion("start_sip_account is null");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountIsNotNull() {
            addCriterion("start_sip_account is not null");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountEqualTo(Integer value) {
            addCriterion("start_sip_account =", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountNotEqualTo(Integer value) {
            addCriterion("start_sip_account <>", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountGreaterThan(Integer value) {
            addCriterion("start_sip_account >", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_sip_account >=", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountLessThan(Integer value) {
            addCriterion("start_sip_account <", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountLessThanOrEqualTo(Integer value) {
            addCriterion("start_sip_account <=", value, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountIn(List<Integer> values) {
            addCriterion("start_sip_account in", values, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountNotIn(List<Integer> values) {
            addCriterion("start_sip_account not in", values, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountBetween(Integer value1, Integer value2) {
            addCriterion("start_sip_account between", value1, value2, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("start_sip_account not between", value1, value2, "startSipAccount");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdIsNull() {
            addCriterion("start_sip_pwd is null");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdIsNotNull() {
            addCriterion("start_sip_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdEqualTo(Integer value) {
            addCriterion("start_sip_pwd =", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdNotEqualTo(Integer value) {
            addCriterion("start_sip_pwd <>", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdGreaterThan(Integer value) {
            addCriterion("start_sip_pwd >", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_sip_pwd >=", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdLessThan(Integer value) {
            addCriterion("start_sip_pwd <", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdLessThanOrEqualTo(Integer value) {
            addCriterion("start_sip_pwd <=", value, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdIn(List<Integer> values) {
            addCriterion("start_sip_pwd in", values, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdNotIn(List<Integer> values) {
            addCriterion("start_sip_pwd not in", values, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdBetween(Integer value1, Integer value2) {
            addCriterion("start_sip_pwd between", value1, value2, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andStartSipPwdNotBetween(Integer value1, Integer value2) {
            addCriterion("start_sip_pwd not between", value1, value2, "startSipPwd");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepIsNull() {
            addCriterion("sip_account_step is null");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepIsNotNull() {
            addCriterion("sip_account_step is not null");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepEqualTo(Integer value) {
            addCriterion("sip_account_step =", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepNotEqualTo(Integer value) {
            addCriterion("sip_account_step <>", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepGreaterThan(Integer value) {
            addCriterion("sip_account_step >", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("sip_account_step >=", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepLessThan(Integer value) {
            addCriterion("sip_account_step <", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepLessThanOrEqualTo(Integer value) {
            addCriterion("sip_account_step <=", value, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepIn(List<Integer> values) {
            addCriterion("sip_account_step in", values, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepNotIn(List<Integer> values) {
            addCriterion("sip_account_step not in", values, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepBetween(Integer value1, Integer value2) {
            addCriterion("sip_account_step between", value1, value2, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipAccountStepNotBetween(Integer value1, Integer value2) {
            addCriterion("sip_account_step not between", value1, value2, "sipAccountStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepIsNull() {
            addCriterion("sip_pwd_step is null");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepIsNotNull() {
            addCriterion("sip_pwd_step is not null");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepEqualTo(Integer value) {
            addCriterion("sip_pwd_step =", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepNotEqualTo(Integer value) {
            addCriterion("sip_pwd_step <>", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepGreaterThan(Integer value) {
            addCriterion("sip_pwd_step >", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepGreaterThanOrEqualTo(Integer value) {
            addCriterion("sip_pwd_step >=", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepLessThan(Integer value) {
            addCriterion("sip_pwd_step <", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepLessThanOrEqualTo(Integer value) {
            addCriterion("sip_pwd_step <=", value, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepIn(List<Integer> values) {
            addCriterion("sip_pwd_step in", values, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepNotIn(List<Integer> values) {
            addCriterion("sip_pwd_step not in", values, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepBetween(Integer value1, Integer value2) {
            addCriterion("sip_pwd_step between", value1, value2, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andSipPwdStepNotBetween(Integer value1, Integer value2) {
            addCriterion("sip_pwd_step not between", value1, value2, "sipPwdStep");
            return (Criteria) this;
        }

        public Criteria andUnivalentIsNull() {
            addCriterion("univalent is null");
            return (Criteria) this;
        }

        public Criteria andUnivalentIsNotNull() {
            addCriterion("univalent is not null");
            return (Criteria) this;
        }

        public Criteria andUnivalentEqualTo(BigDecimal value) {
            addCriterion("univalent =", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotEqualTo(BigDecimal value) {
            addCriterion("univalent <>", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentGreaterThan(BigDecimal value) {
            addCriterion("univalent >", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("univalent >=", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentLessThan(BigDecimal value) {
            addCriterion("univalent <", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("univalent <=", value, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentIn(List<BigDecimal> values) {
            addCriterion("univalent in", values, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotIn(List<BigDecimal> values) {
            addCriterion("univalent not in", values, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("univalent between", value1, value2, "univalent");
            return (Criteria) this;
        }

        public Criteria andUnivalentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("univalent not between", value1, value2, "univalent");
            return (Criteria) this;
        }

        public Criteria andRegTypeIsNull() {
            addCriterion("reg_type is null");
            return (Criteria) this;
        }

        public Criteria andRegTypeIsNotNull() {
            addCriterion("reg_type is not null");
            return (Criteria) this;
        }

        public Criteria andRegTypeEqualTo(Integer value) {
            addCriterion("reg_type =", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeNotEqualTo(Integer value) {
            addCriterion("reg_type <>", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeGreaterThan(Integer value) {
            addCriterion("reg_type >", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_type >=", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeLessThan(Integer value) {
            addCriterion("reg_type <", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeLessThanOrEqualTo(Integer value) {
            addCriterion("reg_type <=", value, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeIn(List<Integer> values) {
            addCriterion("reg_type in", values, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeNotIn(List<Integer> values) {
            addCriterion("reg_type not in", values, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeBetween(Integer value1, Integer value2) {
            addCriterion("reg_type between", value1, value2, "regType");
            return (Criteria) this;
        }

        public Criteria andRegTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("reg_type not between", value1, value2, "regType");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusIsNull() {
            addCriterion("gw_reg_status is null");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusIsNotNull() {
            addCriterion("gw_reg_status is not null");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusEqualTo(Integer value) {
            addCriterion("gw_reg_status =", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusNotEqualTo(Integer value) {
            addCriterion("gw_reg_status <>", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusGreaterThan(Integer value) {
            addCriterion("gw_reg_status >", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("gw_reg_status >=", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusLessThan(Integer value) {
            addCriterion("gw_reg_status <", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusLessThanOrEqualTo(Integer value) {
            addCriterion("gw_reg_status <=", value, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusIn(List<Integer> values) {
            addCriterion("gw_reg_status in", values, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusNotIn(List<Integer> values) {
            addCriterion("gw_reg_status not in", values, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusBetween(Integer value1, Integer value2) {
            addCriterion("gw_reg_status between", value1, value2, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwRegStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("gw_reg_status not between", value1, value2, "gwRegStatus");
            return (Criteria) this;
        }

        public Criteria andGwIpIsNull() {
            addCriterion("gw_ip is null");
            return (Criteria) this;
        }

        public Criteria andGwIpIsNotNull() {
            addCriterion("gw_ip is not null");
            return (Criteria) this;
        }

        public Criteria andGwIpEqualTo(String value) {
            addCriterion("gw_ip =", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpNotEqualTo(String value) {
            addCriterion("gw_ip <>", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpGreaterThan(String value) {
            addCriterion("gw_ip >", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpGreaterThanOrEqualTo(String value) {
            addCriterion("gw_ip >=", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpLessThan(String value) {
            addCriterion("gw_ip <", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpLessThanOrEqualTo(String value) {
            addCriterion("gw_ip <=", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpLike(String value) {
            addCriterion("gw_ip like", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpNotLike(String value) {
            addCriterion("gw_ip not like", value, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpIn(List<String> values) {
            addCriterion("gw_ip in", values, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpNotIn(List<String> values) {
            addCriterion("gw_ip not in", values, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpBetween(String value1, String value2) {
            addCriterion("gw_ip between", value1, value2, "gwIp");
            return (Criteria) this;
        }

        public Criteria andGwIpNotBetween(String value1, String value2) {
            addCriterion("gw_ip not between", value1, value2, "gwIp");
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

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNull() {
            addCriterion("org_code is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("org_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("org_code =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("org_code <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("org_code >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("org_code >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("org_code <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("org_code <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("org_code like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("org_code not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("org_code in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("org_code not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("org_code between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("org_code not between", value1, value2, "orgCode");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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