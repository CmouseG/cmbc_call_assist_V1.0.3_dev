package com.guiji.botsentence.dao;

import com.guiji.botsentence.dao.entity.BotSentenceShareHistory;
import com.guiji.botsentence.dao.entity.BotSentenceShareHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceShareHistoryMapper {
    int countByExample(BotSentenceShareHistoryExample example);

    int deleteByExample(BotSentenceShareHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceShareHistory record);

    int insertSelective(BotSentenceShareHistory record);

    List<BotSentenceShareHistory> selectByExample(BotSentenceShareHistoryExample example);

    BotSentenceShareHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceShareHistory record, @Param("example") BotSentenceShareHistoryExample example);

    int updateByExample(@Param("record") BotSentenceShareHistory record, @Param("example") BotSentenceShareHistoryExample example);

    int updateByPrimaryKeySelective(BotSentenceShareHistory record);

    int updateByPrimaryKey(BotSentenceShareHistory record);
}