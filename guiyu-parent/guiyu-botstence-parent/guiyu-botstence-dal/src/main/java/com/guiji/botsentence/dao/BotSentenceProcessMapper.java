package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;

public interface BotSentenceProcessMapper {
    int countByExample(BotSentenceProcessExample example);

    int deleteByExample(BotSentenceProcessExample example);

    int deleteByPrimaryKey(String processId);

    int insert(BotSentenceProcess record);

    int insertSelective(BotSentenceProcess record);

    List<BotSentenceProcess> selectByExample(BotSentenceProcessExample example);

    BotSentenceProcess selectByPrimaryKey(String processId);

    int updateByExampleSelective(@Param("record") BotSentenceProcess record, @Param("example") BotSentenceProcessExample example);

    int updateByExample(@Param("record") BotSentenceProcess record, @Param("example") BotSentenceProcessExample example);

    int updateByPrimaryKeySelective(BotSentenceProcess record);

    int updateByPrimaryKey(BotSentenceProcess record);
}