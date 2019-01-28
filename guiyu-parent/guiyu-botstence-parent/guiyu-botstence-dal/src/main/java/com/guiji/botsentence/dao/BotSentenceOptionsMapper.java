package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.BotSentenceOptionsExample;

public interface BotSentenceOptionsMapper {
    int countByExample(BotSentenceOptionsExample example);

    int deleteByExample(BotSentenceOptionsExample example);

    int deleteByPrimaryKey(String optionsId);

    int insert(BotSentenceOptions record);

    int insertSelective(BotSentenceOptions record);

    List<BotSentenceOptions> selectByExample(BotSentenceOptionsExample example);

    BotSentenceOptions selectByPrimaryKey(String optionsId);

    int updateByExampleSelective(@Param("record") BotSentenceOptions record, @Param("example") BotSentenceOptionsExample example);

    int updateByExample(@Param("record") BotSentenceOptions record, @Param("example") BotSentenceOptionsExample example);

    int updateByPrimaryKeySelective(BotSentenceOptions record);

    int updateByPrimaryKey(BotSentenceOptions record);
}