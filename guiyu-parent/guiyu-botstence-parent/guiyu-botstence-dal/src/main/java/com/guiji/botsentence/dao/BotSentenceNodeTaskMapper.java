package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceNodeTask;
import com.guiji.botsentence.dao.entity.BotSentenceNodeTaskExample;

public interface BotSentenceNodeTaskMapper {
    int countByExample(BotSentenceNodeTaskExample example);

    int deleteByExample(BotSentenceNodeTaskExample example);

    int deleteByPrimaryKey(String nodeId);

    int insert(BotSentenceNodeTask record);

    int insertSelective(BotSentenceNodeTask record);

    List<BotSentenceNodeTask> selectByExample(BotSentenceNodeTaskExample example);

    BotSentenceNodeTask selectByPrimaryKey(String nodeId);

    int updateByExampleSelective(@Param("record") BotSentenceNodeTask record, @Param("example") BotSentenceNodeTaskExample example);

    int updateByExample(@Param("record") BotSentenceNodeTask record, @Param("example") BotSentenceNodeTaskExample example);

    int updateByPrimaryKeySelective(BotSentenceNodeTask record);

    int updateByPrimaryKey(BotSentenceNodeTask record);
}