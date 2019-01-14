package com.guiji.da.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RobotCallDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public RobotCallDetailExample() {
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

        public Criteria andSeqIdIsNull() {
            addCriterion("seq_id is null");
            return (Criteria) this;
        }

        public Criteria andSeqIdIsNotNull() {
            addCriterion("seq_id is not null");
            return (Criteria) this;
        }

        public Criteria andSeqIdEqualTo(String value) {
            addCriterion("seq_id =", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotEqualTo(String value) {
            addCriterion("seq_id <>", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThan(String value) {
            addCriterion("seq_id >", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThanOrEqualTo(String value) {
            addCriterion("seq_id >=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThan(String value) {
            addCriterion("seq_id <", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThanOrEqualTo(String value) {
            addCriterion("seq_id <=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLike(String value) {
            addCriterion("seq_id like", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotLike(String value) {
            addCriterion("seq_id not like", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdIn(List<String> values) {
            addCriterion("seq_id in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotIn(List<String> values) {
            addCriterion("seq_id not in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdBetween(String value1, String value2) {
            addCriterion("seq_id between", value1, value2, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotBetween(String value1, String value2) {
            addCriterion("seq_id not between", value1, value2, "seqId");
            return (Criteria) this;
        }

        public Criteria andSceneIsNull() {
            addCriterion("scene is null");
            return (Criteria) this;
        }

        public Criteria andSceneIsNotNull() {
            addCriterion("scene is not null");
            return (Criteria) this;
        }

        public Criteria andSceneEqualTo(String value) {
            addCriterion("scene =", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneNotEqualTo(String value) {
            addCriterion("scene <>", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneGreaterThan(String value) {
            addCriterion("scene >", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneGreaterThanOrEqualTo(String value) {
            addCriterion("scene >=", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneLessThan(String value) {
            addCriterion("scene <", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneLessThanOrEqualTo(String value) {
            addCriterion("scene <=", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneLike(String value) {
            addCriterion("scene like", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneNotLike(String value) {
            addCriterion("scene not like", value, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneIn(List<String> values) {
            addCriterion("scene in", values, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneNotIn(List<String> values) {
            addCriterion("scene not in", values, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneBetween(String value1, String value2) {
            addCriterion("scene between", value1, value2, "scene");
            return (Criteria) this;
        }

        public Criteria andSceneNotBetween(String value1, String value2) {
            addCriterion("scene not between", value1, value2, "scene");
            return (Criteria) this;
        }

        public Criteria andHumanWavIsNull() {
            addCriterion("human_wav is null");
            return (Criteria) this;
        }

        public Criteria andHumanWavIsNotNull() {
            addCriterion("human_wav is not null");
            return (Criteria) this;
        }

        public Criteria andHumanWavEqualTo(String value) {
            addCriterion("human_wav =", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavNotEqualTo(String value) {
            addCriterion("human_wav <>", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavGreaterThan(String value) {
            addCriterion("human_wav >", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavGreaterThanOrEqualTo(String value) {
            addCriterion("human_wav >=", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavLessThan(String value) {
            addCriterion("human_wav <", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavLessThanOrEqualTo(String value) {
            addCriterion("human_wav <=", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavLike(String value) {
            addCriterion("human_wav like", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavNotLike(String value) {
            addCriterion("human_wav not like", value, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavIn(List<String> values) {
            addCriterion("human_wav in", values, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavNotIn(List<String> values) {
            addCriterion("human_wav not in", values, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavBetween(String value1, String value2) {
            addCriterion("human_wav between", value1, value2, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanWavNotBetween(String value1, String value2) {
            addCriterion("human_wav not between", value1, value2, "humanWav");
            return (Criteria) this;
        }

        public Criteria andHumanSayIsNull() {
            addCriterion("human_say is null");
            return (Criteria) this;
        }

        public Criteria andHumanSayIsNotNull() {
            addCriterion("human_say is not null");
            return (Criteria) this;
        }

        public Criteria andHumanSayEqualTo(String value) {
            addCriterion("human_say =", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayNotEqualTo(String value) {
            addCriterion("human_say <>", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayGreaterThan(String value) {
            addCriterion("human_say >", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayGreaterThanOrEqualTo(String value) {
            addCriterion("human_say >=", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayLessThan(String value) {
            addCriterion("human_say <", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayLessThanOrEqualTo(String value) {
            addCriterion("human_say <=", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayLike(String value) {
            addCriterion("human_say like", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayNotLike(String value) {
            addCriterion("human_say not like", value, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayIn(List<String> values) {
            addCriterion("human_say in", values, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayNotIn(List<String> values) {
            addCriterion("human_say not in", values, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayBetween(String value1, String value2) {
            addCriterion("human_say between", value1, value2, "humanSay");
            return (Criteria) this;
        }

        public Criteria andHumanSayNotBetween(String value1, String value2) {
            addCriterion("human_say not between", value1, value2, "humanSay");
            return (Criteria) this;
        }

        public Criteria andSayTimeIsNull() {
            addCriterion("say_time is null");
            return (Criteria) this;
        }

        public Criteria andSayTimeIsNotNull() {
            addCriterion("say_time is not null");
            return (Criteria) this;
        }

        public Criteria andSayTimeEqualTo(String value) {
            addCriterion("say_time =", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeNotEqualTo(String value) {
            addCriterion("say_time <>", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeGreaterThan(String value) {
            addCriterion("say_time >", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("say_time >=", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeLessThan(String value) {
            addCriterion("say_time <", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeLessThanOrEqualTo(String value) {
            addCriterion("say_time <=", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeLike(String value) {
            addCriterion("say_time like", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeNotLike(String value) {
            addCriterion("say_time not like", value, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeIn(List<String> values) {
            addCriterion("say_time in", values, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeNotIn(List<String> values) {
            addCriterion("say_time not in", values, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeBetween(String value1, String value2) {
            addCriterion("say_time between", value1, value2, "sayTime");
            return (Criteria) this;
        }

        public Criteria andSayTimeNotBetween(String value1, String value2) {
            addCriterion("say_time not between", value1, value2, "sayTime");
            return (Criteria) this;
        }

        public Criteria andRobotSayIsNull() {
            addCriterion("robot_say is null");
            return (Criteria) this;
        }

        public Criteria andRobotSayIsNotNull() {
            addCriterion("robot_say is not null");
            return (Criteria) this;
        }

        public Criteria andRobotSayEqualTo(String value) {
            addCriterion("robot_say =", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayNotEqualTo(String value) {
            addCriterion("robot_say <>", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayGreaterThan(String value) {
            addCriterion("robot_say >", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayGreaterThanOrEqualTo(String value) {
            addCriterion("robot_say >=", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayLessThan(String value) {
            addCriterion("robot_say <", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayLessThanOrEqualTo(String value) {
            addCriterion("robot_say <=", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayLike(String value) {
            addCriterion("robot_say like", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayNotLike(String value) {
            addCriterion("robot_say not like", value, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayIn(List<String> values) {
            addCriterion("robot_say in", values, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayNotIn(List<String> values) {
            addCriterion("robot_say not in", values, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayBetween(String value1, String value2) {
            addCriterion("robot_say between", value1, value2, "robotSay");
            return (Criteria) this;
        }

        public Criteria andRobotSayNotBetween(String value1, String value2) {
            addCriterion("robot_say not between", value1, value2, "robotSay");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("`type` like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("`type` not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRobotWavIsNull() {
            addCriterion("robot_wav is null");
            return (Criteria) this;
        }

        public Criteria andRobotWavIsNotNull() {
            addCriterion("robot_wav is not null");
            return (Criteria) this;
        }

        public Criteria andRobotWavEqualTo(String value) {
            addCriterion("robot_wav =", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavNotEqualTo(String value) {
            addCriterion("robot_wav <>", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavGreaterThan(String value) {
            addCriterion("robot_wav >", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavGreaterThanOrEqualTo(String value) {
            addCriterion("robot_wav >=", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavLessThan(String value) {
            addCriterion("robot_wav <", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavLessThanOrEqualTo(String value) {
            addCriterion("robot_wav <=", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavLike(String value) {
            addCriterion("robot_wav like", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavNotLike(String value) {
            addCriterion("robot_wav not like", value, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavIn(List<String> values) {
            addCriterion("robot_wav in", values, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavNotIn(List<String> values) {
            addCriterion("robot_wav not in", values, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavBetween(String value1, String value2) {
            addCriterion("robot_wav between", value1, value2, "robotWav");
            return (Criteria) this;
        }

        public Criteria andRobotWavNotBetween(String value1, String value2) {
            addCriterion("robot_wav not between", value1, value2, "robotWav");
            return (Criteria) this;
        }

        public Criteria andAiSceneIsNull() {
            addCriterion("ai_scene is null");
            return (Criteria) this;
        }

        public Criteria andAiSceneIsNotNull() {
            addCriterion("ai_scene is not null");
            return (Criteria) this;
        }

        public Criteria andAiSceneEqualTo(String value) {
            addCriterion("ai_scene =", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneNotEqualTo(String value) {
            addCriterion("ai_scene <>", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneGreaterThan(String value) {
            addCriterion("ai_scene >", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneGreaterThanOrEqualTo(String value) {
            addCriterion("ai_scene >=", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneLessThan(String value) {
            addCriterion("ai_scene <", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneLessThanOrEqualTo(String value) {
            addCriterion("ai_scene <=", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneLike(String value) {
            addCriterion("ai_scene like", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneNotLike(String value) {
            addCriterion("ai_scene not like", value, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneIn(List<String> values) {
            addCriterion("ai_scene in", values, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneNotIn(List<String> values) {
            addCriterion("ai_scene not in", values, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneBetween(String value1, String value2) {
            addCriterion("ai_scene between", value1, value2, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiSceneNotBetween(String value1, String value2) {
            addCriterion("ai_scene not between", value1, value2, "aiScene");
            return (Criteria) this;
        }

        public Criteria andAiIntentIsNull() {
            addCriterion("ai_intent is null");
            return (Criteria) this;
        }

        public Criteria andAiIntentIsNotNull() {
            addCriterion("ai_intent is not null");
            return (Criteria) this;
        }

        public Criteria andAiIntentEqualTo(String value) {
            addCriterion("ai_intent =", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentNotEqualTo(String value) {
            addCriterion("ai_intent <>", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentGreaterThan(String value) {
            addCriterion("ai_intent >", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentGreaterThanOrEqualTo(String value) {
            addCriterion("ai_intent >=", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentLessThan(String value) {
            addCriterion("ai_intent <", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentLessThanOrEqualTo(String value) {
            addCriterion("ai_intent <=", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentLike(String value) {
            addCriterion("ai_intent like", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentNotLike(String value) {
            addCriterion("ai_intent not like", value, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentIn(List<String> values) {
            addCriterion("ai_intent in", values, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentNotIn(List<String> values) {
            addCriterion("ai_intent not in", values, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentBetween(String value1, String value2) {
            addCriterion("ai_intent between", value1, value2, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andAiIntentNotBetween(String value1, String value2) {
            addCriterion("ai_intent not between", value1, value2, "aiIntent");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNull() {
            addCriterion("domain_type is null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIsNotNull() {
            addCriterion("domain_type is not null");
            return (Criteria) this;
        }

        public Criteria andDomainTypeEqualTo(Integer value) {
            addCriterion("domain_type =", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotEqualTo(Integer value) {
            addCriterion("domain_type <>", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThan(Integer value) {
            addCriterion("domain_type >", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("domain_type >=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThan(Integer value) {
            addCriterion("domain_type <", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeLessThanOrEqualTo(Integer value) {
            addCriterion("domain_type <=", value, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeIn(List<Integer> values) {
            addCriterion("domain_type in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotIn(List<Integer> values) {
            addCriterion("domain_type not in", values, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeBetween(Integer value1, Integer value2) {
            addCriterion("domain_type between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andDomainTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("domain_type not between", value1, value2, "domainType");
            return (Criteria) this;
        }

        public Criteria andIsRefusedIsNull() {
            addCriterion("is_refused is null");
            return (Criteria) this;
        }

        public Criteria andIsRefusedIsNotNull() {
            addCriterion("is_refused is not null");
            return (Criteria) this;
        }

        public Criteria andIsRefusedEqualTo(Integer value) {
            addCriterion("is_refused =", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedNotEqualTo(Integer value) {
            addCriterion("is_refused <>", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedGreaterThan(Integer value) {
            addCriterion("is_refused >", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_refused >=", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedLessThan(Integer value) {
            addCriterion("is_refused <", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedLessThanOrEqualTo(Integer value) {
            addCriterion("is_refused <=", value, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedIn(List<Integer> values) {
            addCriterion("is_refused in", values, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedNotIn(List<Integer> values) {
            addCriterion("is_refused not in", values, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedBetween(Integer value1, Integer value2) {
            addCriterion("is_refused between", value1, value2, "isRefused");
            return (Criteria) this;
        }

        public Criteria andIsRefusedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_refused not between", value1, value2, "isRefused");
            return (Criteria) this;
        }

        public Criteria andHangupTypeIsNull() {
            addCriterion("hangup_type is null");
            return (Criteria) this;
        }

        public Criteria andHangupTypeIsNotNull() {
            addCriterion("hangup_type is not null");
            return (Criteria) this;
        }

        public Criteria andHangupTypeEqualTo(Integer value) {
            addCriterion("hangup_type =", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeNotEqualTo(Integer value) {
            addCriterion("hangup_type <>", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeGreaterThan(Integer value) {
            addCriterion("hangup_type >", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("hangup_type >=", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeLessThan(Integer value) {
            addCriterion("hangup_type <", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeLessThanOrEqualTo(Integer value) {
            addCriterion("hangup_type <=", value, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeIn(List<Integer> values) {
            addCriterion("hangup_type in", values, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeNotIn(List<Integer> values) {
            addCriterion("hangup_type not in", values, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeBetween(Integer value1, Integer value2) {
            addCriterion("hangup_type between", value1, value2, "hangupType");
            return (Criteria) this;
        }

        public Criteria andHangupTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("hangup_type not between", value1, value2, "hangupType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeIsNull() {
            addCriterion("match_type is null");
            return (Criteria) this;
        }

        public Criteria andMatchTypeIsNotNull() {
            addCriterion("match_type is not null");
            return (Criteria) this;
        }

        public Criteria andMatchTypeEqualTo(Integer value) {
            addCriterion("match_type =", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeNotEqualTo(Integer value) {
            addCriterion("match_type <>", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeGreaterThan(Integer value) {
            addCriterion("match_type >", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("match_type >=", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeLessThan(Integer value) {
            addCriterion("match_type <", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeLessThanOrEqualTo(Integer value) {
            addCriterion("match_type <=", value, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeIn(List<Integer> values) {
            addCriterion("match_type in", values, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeNotIn(List<Integer> values) {
            addCriterion("match_type not in", values, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeBetween(Integer value1, Integer value2) {
            addCriterion("match_type between", value1, value2, "matchType");
            return (Criteria) this;
        }

        public Criteria andMatchTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("match_type not between", value1, value2, "matchType");
            return (Criteria) this;
        }

        public Criteria andWavIdIsNull() {
            addCriterion("wav_id is null");
            return (Criteria) this;
        }

        public Criteria andWavIdIsNotNull() {
            addCriterion("wav_id is not null");
            return (Criteria) this;
        }

        public Criteria andWavIdEqualTo(String value) {
            addCriterion("wav_id =", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdNotEqualTo(String value) {
            addCriterion("wav_id <>", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdGreaterThan(String value) {
            addCriterion("wav_id >", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdGreaterThanOrEqualTo(String value) {
            addCriterion("wav_id >=", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdLessThan(String value) {
            addCriterion("wav_id <", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdLessThanOrEqualTo(String value) {
            addCriterion("wav_id <=", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdLike(String value) {
            addCriterion("wav_id like", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdNotLike(String value) {
            addCriterion("wav_id not like", value, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdIn(List<String> values) {
            addCriterion("wav_id in", values, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdNotIn(List<String> values) {
            addCriterion("wav_id not in", values, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdBetween(String value1, String value2) {
            addCriterion("wav_id between", value1, value2, "wavId");
            return (Criteria) this;
        }

        public Criteria andWavIdNotBetween(String value1, String value2) {
            addCriterion("wav_id not between", value1, value2, "wavId");
            return (Criteria) this;
        }

        public Criteria andCrtDateIsNull() {
            addCriterion("crt_date is null");
            return (Criteria) this;
        }

        public Criteria andCrtDateIsNotNull() {
            addCriterion("crt_date is not null");
            return (Criteria) this;
        }

        public Criteria andCrtDateEqualTo(String value) {
            addCriterion("crt_date =", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateNotEqualTo(String value) {
            addCriterion("crt_date <>", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateGreaterThan(String value) {
            addCriterion("crt_date >", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateGreaterThanOrEqualTo(String value) {
            addCriterion("crt_date >=", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateLessThan(String value) {
            addCriterion("crt_date <", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateLessThanOrEqualTo(String value) {
            addCriterion("crt_date <=", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateLike(String value) {
            addCriterion("crt_date like", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateNotLike(String value) {
            addCriterion("crt_date not like", value, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateIn(List<String> values) {
            addCriterion("crt_date in", values, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateNotIn(List<String> values) {
            addCriterion("crt_date not in", values, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateBetween(String value1, String value2) {
            addCriterion("crt_date between", value1, value2, "crtDate");
            return (Criteria) this;
        }

        public Criteria andCrtDateNotBetween(String value1, String value2) {
            addCriterion("crt_date not between", value1, value2, "crtDate");
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