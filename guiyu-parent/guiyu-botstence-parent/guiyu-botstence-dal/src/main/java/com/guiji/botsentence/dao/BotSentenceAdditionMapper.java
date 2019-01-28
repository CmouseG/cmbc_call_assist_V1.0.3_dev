package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceAddition;
import com.guiji.botsentence.dao.entity.BotSentenceAdditionExample;

public interface BotSentenceAdditionMapper {
    int countByExample(BotSentenceAdditionExample example);

    int deleteByExample(BotSentenceAdditionExample example);

    int deleteByPrimaryKey(String processId);

    int insert(BotSentenceAddition record);

    int insertSelective(BotSentenceAddition record);

    List<BotSentenceAddition> selectByExampleWithBLOBs(BotSentenceAdditionExample example);

    List<BotSentenceAddition> selectByExample(BotSentenceAdditionExample example);

    BotSentenceAddition selectByPrimaryKey(String processId);

    int updateByExampleSelective(@Param("record") BotSentenceAddition record, @Param("example") BotSentenceAdditionExample example);

    int updateByExampleWithBLOBs(@Param("record") BotSentenceAddition record, @Param("example") BotSentenceAdditionExample example);

    int updateByExample(@Param("record") BotSentenceAddition record, @Param("example") BotSentenceAdditionExample example);

    int updateByPrimaryKeySelective(BotSentenceAddition record);

    int updateByPrimaryKeyWithBLOBs(BotSentenceAddition record);

    int updateByPrimaryKey(BotSentenceAddition record);
}