package ai.guiji.botsentence.crm.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TabCustomerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public TabCustomerExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
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

        public Criteria andAdddateIsNull() {
            addCriterion("adddate is null");
            return (Criteria) this;
        }

        public Criteria andAdddateIsNotNull() {
            addCriterion("adddate is not null");
            return (Criteria) this;
        }

        public Criteria andAdddateEqualTo(Date value) {
            addCriterionForJDBCDate("adddate =", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("adddate <>", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateGreaterThan(Date value) {
            addCriterionForJDBCDate("adddate >", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("adddate >=", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateLessThan(Date value) {
            addCriterionForJDBCDate("adddate <", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("adddate <=", value, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateIn(List<Date> values) {
            addCriterionForJDBCDate("adddate in", values, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("adddate not in", values, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("adddate between", value1, value2, "adddate");
            return (Criteria) this;
        }

        public Criteria andAdddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("adddate not between", value1, value2, "adddate");
            return (Criteria) this;
        }

        public Criteria andTermdateIsNull() {
            addCriterion("termdate is null");
            return (Criteria) this;
        }

        public Criteria andTermdateIsNotNull() {
            addCriterion("termdate is not null");
            return (Criteria) this;
        }

        public Criteria andTermdateEqualTo(Date value) {
            addCriterionForJDBCDate("termdate =", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("termdate <>", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateGreaterThan(Date value) {
            addCriterionForJDBCDate("termdate >", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("termdate >=", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateLessThan(Date value) {
            addCriterionForJDBCDate("termdate <", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("termdate <=", value, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateIn(List<Date> values) {
            addCriterionForJDBCDate("termdate in", values, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("termdate not in", values, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("termdate between", value1, value2, "termdate");
            return (Criteria) this;
        }

        public Criteria andTermdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("termdate not between", value1, value2, "termdate");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
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

        public Criteria andSerialNumberIsNull() {
            addCriterion("serial_number is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(String value) {
            addCriterion("serial_number =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(String value) {
            addCriterion("serial_number <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(String value) {
            addCriterion("serial_number >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("serial_number >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(String value) {
            addCriterion("serial_number <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(String value) {
            addCriterion("serial_number <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLike(String value) {
            addCriterion("serial_number like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotLike(String value) {
            addCriterion("serial_number not like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<String> values) {
            addCriterion("serial_number in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<String> values) {
            addCriterion("serial_number not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(String value1, String value2) {
            addCriterion("serial_number between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(String value1, String value2) {
            addCriterion("serial_number not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andMachineCodeIsNull() {
            addCriterion("machine_code is null");
            return (Criteria) this;
        }

        public Criteria andMachineCodeIsNotNull() {
            addCriterion("machine_code is not null");
            return (Criteria) this;
        }

        public Criteria andMachineCodeEqualTo(String value) {
            addCriterion("machine_code =", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeNotEqualTo(String value) {
            addCriterion("machine_code <>", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeGreaterThan(String value) {
            addCriterion("machine_code >", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeGreaterThanOrEqualTo(String value) {
            addCriterion("machine_code >=", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeLessThan(String value) {
            addCriterion("machine_code <", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeLessThanOrEqualTo(String value) {
            addCriterion("machine_code <=", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeLike(String value) {
            addCriterion("machine_code like", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeNotLike(String value) {
            addCriterion("machine_code not like", value, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeIn(List<String> values) {
            addCriterion("machine_code in", values, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeNotIn(List<String> values) {
            addCriterion("machine_code not in", values, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeBetween(String value1, String value2) {
            addCriterion("machine_code between", value1, value2, "machineCode");
            return (Criteria) this;
        }

        public Criteria andMachineCodeNotBetween(String value1, String value2) {
            addCriterion("machine_code not between", value1, value2, "machineCode");
            return (Criteria) this;
        }

        public Criteria andPhonePortIsNull() {
            addCriterion("phone_port is null");
            return (Criteria) this;
        }

        public Criteria andPhonePortIsNotNull() {
            addCriterion("phone_port is not null");
            return (Criteria) this;
        }

        public Criteria andPhonePortEqualTo(String value) {
            addCriterion("phone_port =", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortNotEqualTo(String value) {
            addCriterion("phone_port <>", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortGreaterThan(String value) {
            addCriterion("phone_port >", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortGreaterThanOrEqualTo(String value) {
            addCriterion("phone_port >=", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortLessThan(String value) {
            addCriterion("phone_port <", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortLessThanOrEqualTo(String value) {
            addCriterion("phone_port <=", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortLike(String value) {
            addCriterion("phone_port like", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortNotLike(String value) {
            addCriterion("phone_port not like", value, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortIn(List<String> values) {
            addCriterion("phone_port in", values, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortNotIn(List<String> values) {
            addCriterion("phone_port not in", values, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortBetween(String value1, String value2) {
            addCriterion("phone_port between", value1, value2, "phonePort");
            return (Criteria) this;
        }

        public Criteria andPhonePortNotBetween(String value1, String value2) {
            addCriterion("phone_port not between", value1, value2, "phonePort");
            return (Criteria) this;
        }

        public Criteria andIslimitIsNull() {
            addCriterion("islimit is null");
            return (Criteria) this;
        }

        public Criteria andIslimitIsNotNull() {
            addCriterion("islimit is not null");
            return (Criteria) this;
        }

        public Criteria andIslimitEqualTo(Boolean value) {
            addCriterion("islimit =", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotEqualTo(Boolean value) {
            addCriterion("islimit <>", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitGreaterThan(Boolean value) {
            addCriterion("islimit >", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitGreaterThanOrEqualTo(Boolean value) {
            addCriterion("islimit >=", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitLessThan(Boolean value) {
            addCriterion("islimit <", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitLessThanOrEqualTo(Boolean value) {
            addCriterion("islimit <=", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitIn(List<Boolean> values) {
            addCriterion("islimit in", values, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotIn(List<Boolean> values) {
            addCriterion("islimit not in", values, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitBetween(Boolean value1, Boolean value2) {
            addCriterion("islimit between", value1, value2, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotBetween(Boolean value1, Boolean value2) {
            addCriterion("islimit not between", value1, value2, "islimit");
            return (Criteria) this;
        }

        public Criteria andLimitNumIsNull() {
            addCriterion("limit_num is null");
            return (Criteria) this;
        }

        public Criteria andLimitNumIsNotNull() {
            addCriterion("limit_num is not null");
            return (Criteria) this;
        }

        public Criteria andLimitNumEqualTo(Byte value) {
            addCriterion("limit_num =", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumNotEqualTo(Byte value) {
            addCriterion("limit_num <>", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumGreaterThan(Byte value) {
            addCriterion("limit_num >", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumGreaterThanOrEqualTo(Byte value) {
            addCriterion("limit_num >=", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumLessThan(Byte value) {
            addCriterion("limit_num <", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumLessThanOrEqualTo(Byte value) {
            addCriterion("limit_num <=", value, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumIn(List<Byte> values) {
            addCriterion("limit_num in", values, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumNotIn(List<Byte> values) {
            addCriterion("limit_num not in", values, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumBetween(Byte value1, Byte value2) {
            addCriterion("limit_num between", value1, value2, "limitNum");
            return (Criteria) this;
        }

        public Criteria andLimitNumNotBetween(Byte value1, Byte value2) {
            addCriterion("limit_num not between", value1, value2, "limitNum");
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