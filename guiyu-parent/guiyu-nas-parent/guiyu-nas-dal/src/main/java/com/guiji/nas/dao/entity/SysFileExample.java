package com.guiji.nas.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysFileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public SysFileExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andBusiIdIsNull() {
            addCriterion("BUSI_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusiIdIsNotNull() {
            addCriterion("BUSI_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusiIdEqualTo(String value) {
            addCriterion("BUSI_ID =", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdNotEqualTo(String value) {
            addCriterion("BUSI_ID <>", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdGreaterThan(String value) {
            addCriterion("BUSI_ID >", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_ID >=", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdLessThan(String value) {
            addCriterion("BUSI_ID <", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdLessThanOrEqualTo(String value) {
            addCriterion("BUSI_ID <=", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdLike(String value) {
            addCriterion("BUSI_ID like", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdNotLike(String value) {
            addCriterion("BUSI_ID not like", value, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdIn(List<String> values) {
            addCriterion("BUSI_ID in", values, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdNotIn(List<String> values) {
            addCriterion("BUSI_ID not in", values, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdBetween(String value1, String value2) {
            addCriterion("BUSI_ID between", value1, value2, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiIdNotBetween(String value1, String value2) {
            addCriterion("BUSI_ID not between", value1, value2, "busiId");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIsNull() {
            addCriterion("BUSI_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIsNotNull() {
            addCriterion("BUSI_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusiTypeEqualTo(String value) {
            addCriterion("BUSI_TYPE =", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotEqualTo(String value) {
            addCriterion("BUSI_TYPE <>", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeGreaterThan(String value) {
            addCriterion("BUSI_TYPE >", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_TYPE >=", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLessThan(String value) {
            addCriterion("BUSI_TYPE <", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLessThanOrEqualTo(String value) {
            addCriterion("BUSI_TYPE <=", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeLike(String value) {
            addCriterion("BUSI_TYPE like", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotLike(String value) {
            addCriterion("BUSI_TYPE not like", value, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeIn(List<String> values) {
            addCriterion("BUSI_TYPE in", values, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotIn(List<String> values) {
            addCriterion("BUSI_TYPE not in", values, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeBetween(String value1, String value2) {
            addCriterion("BUSI_TYPE between", value1, value2, "busiType");
            return (Criteria) this;
        }

        public Criteria andBusiTypeNotBetween(String value1, String value2) {
            addCriterion("BUSI_TYPE not between", value1, value2, "busiType");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNull() {
            addCriterion("FILE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNotNull() {
            addCriterion("FILE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFileTypeEqualTo(String value) {
            addCriterion("FILE_TYPE =", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotEqualTo(String value) {
            addCriterion("FILE_TYPE <>", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThan(String value) {
            addCriterion("FILE_TYPE >", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_TYPE >=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThan(String value) {
            addCriterion("FILE_TYPE <", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThanOrEqualTo(String value) {
            addCriterion("FILE_TYPE <=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLike(String value) {
            addCriterion("FILE_TYPE like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotLike(String value) {
            addCriterion("FILE_TYPE not like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeIn(List<String> values) {
            addCriterion("FILE_TYPE in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotIn(List<String> values) {
            addCriterion("FILE_TYPE not in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeBetween(String value1, String value2) {
            addCriterion("FILE_TYPE between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotBetween(String value1, String value2) {
            addCriterion("FILE_TYPE not between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNull() {
            addCriterion("FILE_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNotNull() {
            addCriterion("FILE_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andFileSizeEqualTo(Double value) {
            addCriterion("FILE_SIZE =", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotEqualTo(Double value) {
            addCriterion("FILE_SIZE <>", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThan(Double value) {
            addCriterion("FILE_SIZE >", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThanOrEqualTo(Double value) {
            addCriterion("FILE_SIZE >=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThan(Double value) {
            addCriterion("FILE_SIZE <", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThanOrEqualTo(Double value) {
            addCriterion("FILE_SIZE <=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeIn(List<Double> values) {
            addCriterion("FILE_SIZE in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotIn(List<Double> values) {
            addCriterion("FILE_SIZE not in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeBetween(Double value1, Double value2) {
            addCriterion("FILE_SIZE between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotBetween(Double value1, Double value2) {
            addCriterion("FILE_SIZE not between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andSkUrlIsNull() {
            addCriterion("SK_URL is null");
            return (Criteria) this;
        }

        public Criteria andSkUrlIsNotNull() {
            addCriterion("SK_URL is not null");
            return (Criteria) this;
        }

        public Criteria andSkUrlEqualTo(String value) {
            addCriterion("SK_URL =", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlNotEqualTo(String value) {
            addCriterion("SK_URL <>", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlGreaterThan(String value) {
            addCriterion("SK_URL >", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlGreaterThanOrEqualTo(String value) {
            addCriterion("SK_URL >=", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlLessThan(String value) {
            addCriterion("SK_URL <", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlLessThanOrEqualTo(String value) {
            addCriterion("SK_URL <=", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlLike(String value) {
            addCriterion("SK_URL like", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlNotLike(String value) {
            addCriterion("SK_URL not like", value, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlIn(List<String> values) {
            addCriterion("SK_URL in", values, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlNotIn(List<String> values) {
            addCriterion("SK_URL not in", values, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlBetween(String value1, String value2) {
            addCriterion("SK_URL between", value1, value2, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkUrlNotBetween(String value1, String value2) {
            addCriterion("SK_URL not between", value1, value2, "skUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlIsNull() {
            addCriterion("SK_THUMB_IMAGE_URL is null");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlIsNotNull() {
            addCriterion("SK_THUMB_IMAGE_URL is not null");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlEqualTo(String value) {
            addCriterion("SK_THUMB_IMAGE_URL =", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlNotEqualTo(String value) {
            addCriterion("SK_THUMB_IMAGE_URL <>", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlGreaterThan(String value) {
            addCriterion("SK_THUMB_IMAGE_URL >", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("SK_THUMB_IMAGE_URL >=", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlLessThan(String value) {
            addCriterion("SK_THUMB_IMAGE_URL <", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlLessThanOrEqualTo(String value) {
            addCriterion("SK_THUMB_IMAGE_URL <=", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlLike(String value) {
            addCriterion("SK_THUMB_IMAGE_URL like", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlNotLike(String value) {
            addCriterion("SK_THUMB_IMAGE_URL not like", value, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlIn(List<String> values) {
            addCriterion("SK_THUMB_IMAGE_URL in", values, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlNotIn(List<String> values) {
            addCriterion("SK_THUMB_IMAGE_URL not in", values, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlBetween(String value1, String value2) {
            addCriterion("SK_THUMB_IMAGE_URL between", value1, value2, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSkThumbImageUrlNotBetween(String value1, String value2) {
            addCriterion("SK_THUMB_IMAGE_URL not between", value1, value2, "skThumbImageUrl");
            return (Criteria) this;
        }

        public Criteria andSysCodeIsNull() {
            addCriterion("SYS_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSysCodeIsNotNull() {
            addCriterion("SYS_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSysCodeEqualTo(String value) {
            addCriterion("SYS_CODE =", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotEqualTo(String value) {
            addCriterion("SYS_CODE <>", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeGreaterThan(String value) {
            addCriterion("SYS_CODE >", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_CODE >=", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLessThan(String value) {
            addCriterion("SYS_CODE <", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLessThanOrEqualTo(String value) {
            addCriterion("SYS_CODE <=", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeLike(String value) {
            addCriterion("SYS_CODE like", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotLike(String value) {
            addCriterion("SYS_CODE not like", value, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeIn(List<String> values) {
            addCriterion("SYS_CODE in", values, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotIn(List<String> values) {
            addCriterion("SYS_CODE not in", values, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeBetween(String value1, String value2) {
            addCriterion("SYS_CODE between", value1, value2, "sysCode");
            return (Criteria) this;
        }

        public Criteria andSysCodeNotBetween(String value1, String value2) {
            addCriterion("SYS_CODE not between", value1, value2, "sysCode");
            return (Criteria) this;
        }

        public Criteria andCrtUserIsNull() {
            addCriterion("CRT_USER is null");
            return (Criteria) this;
        }

        public Criteria andCrtUserIsNotNull() {
            addCriterion("CRT_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCrtUserEqualTo(String value) {
            addCriterion("CRT_USER =", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotEqualTo(String value) {
            addCriterion("CRT_USER <>", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThan(String value) {
            addCriterion("CRT_USER >", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserGreaterThanOrEqualTo(String value) {
            addCriterion("CRT_USER >=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThan(String value) {
            addCriterion("CRT_USER <", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLessThanOrEqualTo(String value) {
            addCriterion("CRT_USER <=", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserLike(String value) {
            addCriterion("CRT_USER like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotLike(String value) {
            addCriterion("CRT_USER not like", value, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserIn(List<String> values) {
            addCriterion("CRT_USER in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotIn(List<String> values) {
            addCriterion("CRT_USER not in", values, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserBetween(String value1, String value2) {
            addCriterion("CRT_USER between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtUserNotBetween(String value1, String value2) {
            addCriterion("CRT_USER not between", value1, value2, "crtUser");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIsNull() {
            addCriterion("CRT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIsNotNull() {
            addCriterion("CRT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCrtTimeEqualTo(Date value) {
            addCriterion("CRT_TIME =", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotEqualTo(Date value) {
            addCriterion("CRT_TIME <>", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeGreaterThan(Date value) {
            addCriterion("CRT_TIME >", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CRT_TIME >=", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeLessThan(Date value) {
            addCriterion("CRT_TIME <", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeLessThanOrEqualTo(Date value) {
            addCriterion("CRT_TIME <=", value, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeIn(List<Date> values) {
            addCriterion("CRT_TIME in", values, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotIn(List<Date> values) {
            addCriterion("CRT_TIME not in", values, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeBetween(Date value1, Date value2) {
            addCriterion("CRT_TIME between", value1, value2, "crtTime");
            return (Criteria) this;
        }

        public Criteria andCrtTimeNotBetween(Date value1, Date value2) {
            addCriterion("CRT_TIME not between", value1, value2, "crtTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNull() {
            addCriterion("LST_UPDATE_USER is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIsNotNull() {
            addCriterion("LST_UPDATE_USER is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserEqualTo(String value) {
            addCriterion("LST_UPDATE_USER =", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotEqualTo(String value) {
            addCriterion("LST_UPDATE_USER <>", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThan(String value) {
            addCriterion("LST_UPDATE_USER >", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("LST_UPDATE_USER >=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThan(String value) {
            addCriterion("LST_UPDATE_USER <", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("LST_UPDATE_USER <=", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserLike(String value) {
            addCriterion("LST_UPDATE_USER like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotLike(String value) {
            addCriterion("LST_UPDATE_USER not like", value, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserIn(List<String> values) {
            addCriterion("LST_UPDATE_USER in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotIn(List<String> values) {
            addCriterion("LST_UPDATE_USER not in", values, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserBetween(String value1, String value2) {
            addCriterion("LST_UPDATE_USER between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateUserNotBetween(String value1, String value2) {
            addCriterion("LST_UPDATE_USER not between", value1, value2, "lstUpdateUser");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNull() {
            addCriterion("LST_UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIsNotNull() {
            addCriterion("LST_UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeEqualTo(Date value) {
            addCriterion("LST_UPDATE_TIME =", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotEqualTo(Date value) {
            addCriterion("LST_UPDATE_TIME <>", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThan(Date value) {
            addCriterion("LST_UPDATE_TIME >", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LST_UPDATE_TIME >=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThan(Date value) {
            addCriterion("LST_UPDATE_TIME <", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("LST_UPDATE_TIME <=", value, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeIn(List<Date> values) {
            addCriterion("LST_UPDATE_TIME in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotIn(List<Date> values) {
            addCriterion("LST_UPDATE_TIME not in", values, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("LST_UPDATE_TIME between", value1, value2, "lstUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLstUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("LST_UPDATE_TIME not between", value1, value2, "lstUpdateTime");
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