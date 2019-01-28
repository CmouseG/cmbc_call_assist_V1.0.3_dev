package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;

public interface BotSentenceTradeMapper {
    int countByExample(BotSentenceTradeExample example);

    int deleteByExample(BotSentenceTradeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BotSentenceTrade record);

    int insertSelective(BotSentenceTrade record);

    List<BotSentenceTrade> selectByExample(BotSentenceTradeExample example);

    BotSentenceTrade selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BotSentenceTrade record, @Param("example") BotSentenceTradeExample example);

    int updateByExample(@Param("record") BotSentenceTrade record, @Param("example") BotSentenceTradeExample example);

    int updateByPrimaryKeySelective(BotSentenceTrade record);

    int updateByPrimaryKey(BotSentenceTrade record);
}