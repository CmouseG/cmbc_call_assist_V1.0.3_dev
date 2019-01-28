package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceSurvey;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyExample;

public interface BotSentenceSurveyMapper {
    int countByExample(BotSentenceSurveyExample example);

    int deleteByExample(BotSentenceSurveyExample example);

    int deleteByPrimaryKey(String surveyId);

    int insert(BotSentenceSurvey record);

    int insertSelective(BotSentenceSurvey record);

    List<BotSentenceSurvey> selectByExample(BotSentenceSurveyExample example);

    BotSentenceSurvey selectByPrimaryKey(String surveyId);

    int updateByExampleSelective(@Param("record") BotSentenceSurvey record, @Param("example") BotSentenceSurveyExample example);

    int updateByExample(@Param("record") BotSentenceSurvey record, @Param("example") BotSentenceSurveyExample example);

    int updateByPrimaryKeySelective(BotSentenceSurvey record);

    int updateByPrimaryKey(BotSentenceSurvey record);
}