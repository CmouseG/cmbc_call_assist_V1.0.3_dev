package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceGradeDetail;
import com.guiji.botsentence.dao.entity.BotSentenceGradeDetailExample;

public interface BotSentenceGradeDetailMapper {
    int countByExample(BotSentenceGradeDetailExample example);

    int deleteByExample(BotSentenceGradeDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceGradeDetail record);

    int insertSelective(BotSentenceGradeDetail record);

    List<BotSentenceGradeDetail> selectByExample(BotSentenceGradeDetailExample example);

    BotSentenceGradeDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceGradeDetail record, @Param("example") BotSentenceGradeDetailExample example);

    int updateByExample(@Param("record") BotSentenceGradeDetail record, @Param("example") BotSentenceGradeDetailExample example);

    int updateByPrimaryKeySelective(BotSentenceGradeDetail record);

    int updateByPrimaryKey(BotSentenceGradeDetail record);
}