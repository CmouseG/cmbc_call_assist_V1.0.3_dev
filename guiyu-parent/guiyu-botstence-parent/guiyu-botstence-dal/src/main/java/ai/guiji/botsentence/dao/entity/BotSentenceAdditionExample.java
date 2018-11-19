package ai.guiji.botsentence.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class BotSentenceAdditionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BotSentenceAdditionExample() {
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

        public Criteria andTemplateJsonIsNull() {
            addCriterion("template_json is null");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonIsNotNull() {
            addCriterion("template_json is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonEqualTo(String value) {
            addCriterion("template_json =", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonNotEqualTo(String value) {
            addCriterion("template_json <>", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonGreaterThan(String value) {
            addCriterion("template_json >", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonGreaterThanOrEqualTo(String value) {
            addCriterion("template_json >=", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonLessThan(String value) {
            addCriterion("template_json <", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonLessThanOrEqualTo(String value) {
            addCriterion("template_json <=", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonLike(String value) {
            addCriterion("template_json like", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonNotLike(String value) {
            addCriterion("template_json not like", value, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonIn(List<String> values) {
            addCriterion("template_json in", values, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonNotIn(List<String> values) {
            addCriterion("template_json not in", values, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonBetween(String value1, String value2) {
            addCriterion("template_json between", value1, value2, "templateJson");
            return (Criteria) this;
        }

        public Criteria andTemplateJsonNotBetween(String value1, String value2) {
            addCriterion("template_json not between", value1, value2, "templateJson");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtIsNull() {
            addCriterion("weights_txt is null");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtIsNotNull() {
            addCriterion("weights_txt is not null");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtEqualTo(String value) {
            addCriterion("weights_txt =", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtNotEqualTo(String value) {
            addCriterion("weights_txt <>", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtGreaterThan(String value) {
            addCriterion("weights_txt >", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtGreaterThanOrEqualTo(String value) {
            addCriterion("weights_txt >=", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtLessThan(String value) {
            addCriterion("weights_txt <", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtLessThanOrEqualTo(String value) {
            addCriterion("weights_txt <=", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtLike(String value) {
            addCriterion("weights_txt like", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtNotLike(String value) {
            addCriterion("weights_txt not like", value, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtIn(List<String> values) {
            addCriterion("weights_txt in", values, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtNotIn(List<String> values) {
            addCriterion("weights_txt not in", values, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtBetween(String value1, String value2) {
            addCriterion("weights_txt between", value1, value2, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andWeightsTxtNotBetween(String value1, String value2) {
            addCriterion("weights_txt not between", value1, value2, "weightsTxt");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonIsNull() {
            addCriterion("options_json is null");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonIsNotNull() {
            addCriterion("options_json is not null");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonEqualTo(String value) {
            addCriterion("options_json =", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonNotEqualTo(String value) {
            addCriterion("options_json <>", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonGreaterThan(String value) {
            addCriterion("options_json >", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonGreaterThanOrEqualTo(String value) {
            addCriterion("options_json >=", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonLessThan(String value) {
            addCriterion("options_json <", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonLessThanOrEqualTo(String value) {
            addCriterion("options_json <=", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonLike(String value) {
            addCriterion("options_json like", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonNotLike(String value) {
            addCriterion("options_json not like", value, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonIn(List<String> values) {
            addCriterion("options_json in", values, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonNotIn(List<String> values) {
            addCriterion("options_json not in", values, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonBetween(String value1, String value2) {
            addCriterion("options_json between", value1, value2, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andOptionsJsonNotBetween(String value1, String value2) {
            addCriterion("options_json not between", value1, value2, "optionsJson");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtIsNull() {
            addCriterion("stopwords_txt is null");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtIsNotNull() {
            addCriterion("stopwords_txt is not null");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtEqualTo(String value) {
            addCriterion("stopwords_txt =", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtNotEqualTo(String value) {
            addCriterion("stopwords_txt <>", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtGreaterThan(String value) {
            addCriterion("stopwords_txt >", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtGreaterThanOrEqualTo(String value) {
            addCriterion("stopwords_txt >=", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtLessThan(String value) {
            addCriterion("stopwords_txt <", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtLessThanOrEqualTo(String value) {
            addCriterion("stopwords_txt <=", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtLike(String value) {
            addCriterion("stopwords_txt like", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtNotLike(String value) {
            addCriterion("stopwords_txt not like", value, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtIn(List<String> values) {
            addCriterion("stopwords_txt in", values, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtNotIn(List<String> values) {
            addCriterion("stopwords_txt not in", values, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtBetween(String value1, String value2) {
            addCriterion("stopwords_txt between", value1, value2, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andStopwordsTxtNotBetween(String value1, String value2) {
            addCriterion("stopwords_txt not between", value1, value2, "stopwordsTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtIsNull() {
            addCriterion("userdict_txt is null");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtIsNotNull() {
            addCriterion("userdict_txt is not null");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtEqualTo(String value) {
            addCriterion("userdict_txt =", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtNotEqualTo(String value) {
            addCriterion("userdict_txt <>", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtGreaterThan(String value) {
            addCriterion("userdict_txt >", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtGreaterThanOrEqualTo(String value) {
            addCriterion("userdict_txt >=", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtLessThan(String value) {
            addCriterion("userdict_txt <", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtLessThanOrEqualTo(String value) {
            addCriterion("userdict_txt <=", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtLike(String value) {
            addCriterion("userdict_txt like", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtNotLike(String value) {
            addCriterion("userdict_txt not like", value, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtIn(List<String> values) {
            addCriterion("userdict_txt in", values, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtNotIn(List<String> values) {
            addCriterion("userdict_txt not in", values, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtBetween(String value1, String value2) {
            addCriterion("userdict_txt between", value1, value2, "userdictTxt");
            return (Criteria) this;
        }

        public Criteria andUserdictTxtNotBetween(String value1, String value2) {
            addCriterion("userdict_txt not between", value1, value2, "userdictTxt");
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