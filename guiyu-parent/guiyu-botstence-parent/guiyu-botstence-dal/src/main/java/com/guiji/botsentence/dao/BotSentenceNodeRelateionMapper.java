package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceNodeRelateion;
import com.guiji.botsentence.dao.entity.BotSentenceNodeRelateionExample;

public interface BotSentenceNodeRelateionMapper {
    int countByExample(BotSentenceNodeRelateionExample example);

    int deleteByExample(BotSentenceNodeRelateionExample example);

    int deleteByPrimaryKey(String nodeRelateionId);

    int insert(BotSentenceNodeRelateion record);

    int insertSelective(BotSentenceNodeRelateion record);

    List<BotSentenceNodeRelateion> selectByExample(BotSentenceNodeRelateionExample example);

    BotSentenceNodeRelateion selectByPrimaryKey(String nodeRelateionId);

    int updateByExampleSelective(@Param("record") BotSentenceNodeRelateion record, @Param("example") BotSentenceNodeRelateionExample example);

    int updateByExample(@Param("record") BotSentenceNodeRelateion record, @Param("example") BotSentenceNodeRelateionExample example);

    int updateByPrimaryKeySelective(BotSentenceNodeRelateion record);

    int updateByPrimaryKey(BotSentenceNodeRelateion record);
}