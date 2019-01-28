package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceGrade;
import com.guiji.botsentence.dao.entity.BotSentenceGradeExample;

public interface BotSentenceGradeMapper {
    int countByExample(BotSentenceGradeExample example);

    int deleteByExample(BotSentenceGradeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceGrade record);

    int insertSelective(BotSentenceGrade record);

    List<BotSentenceGrade> selectByExample(BotSentenceGradeExample example);

    BotSentenceGrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceGrade record, @Param("example") BotSentenceGradeExample example);

    int updateByExample(@Param("record") BotSentenceGrade record, @Param("example") BotSentenceGradeExample example);

    int updateByPrimaryKeySelective(BotSentenceGrade record);

    int updateByPrimaryKey(BotSentenceGrade record);
}