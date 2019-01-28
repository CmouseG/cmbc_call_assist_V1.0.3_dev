package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntent;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntentExample;

public interface BotSentenceSurveyIntentMapper {
    int countByExample(BotSentenceSurveyIntentExample example);

    int deleteByExample(BotSentenceSurveyIntentExample example);

    int deleteByPrimaryKey(String surveyIntentId);

    int insert(BotSentenceSurveyIntent record);

    int insertSelective(BotSentenceSurveyIntent record);

    List<BotSentenceSurveyIntent> selectByExample(BotSentenceSurveyIntentExample example);

    BotSentenceSurveyIntent selectByPrimaryKey(String surveyIntentId);

    int updateByExampleSelective(@Param("record") BotSentenceSurveyIntent record, @Param("example") BotSentenceSurveyIntentExample example);

    int updateByExample(@Param("record") BotSentenceSurveyIntent record, @Param("example") BotSentenceSurveyIntentExample example);

    int updateByPrimaryKeySelective(BotSentenceSurveyIntent record);

    int updateByPrimaryKey(BotSentenceSurveyIntent record);
}