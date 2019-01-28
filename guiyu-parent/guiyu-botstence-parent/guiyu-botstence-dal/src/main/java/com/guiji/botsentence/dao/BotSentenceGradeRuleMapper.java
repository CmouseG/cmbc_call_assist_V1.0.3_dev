package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceGradeRule;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRuleExample;

public interface BotSentenceGradeRuleMapper {
    int countByExample(BotSentenceGradeRuleExample example);

    int deleteByExample(BotSentenceGradeRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceGradeRule record);

    int insertSelective(BotSentenceGradeRule record);

    List<BotSentenceGradeRule> selectByExample(BotSentenceGradeRuleExample example);

    BotSentenceGradeRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceGradeRule record, @Param("example") BotSentenceGradeRuleExample example);

    int updateByExample(@Param("record") BotSentenceGradeRule record, @Param("example") BotSentenceGradeRuleExample example);

    int updateByPrimaryKeySelective(BotSentenceGradeRule record);

    int updateByPrimaryKey(BotSentenceGradeRule record);
}