package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceNodeEffect;
import com.guiji.botsentence.dao.entity.BotSentenceNodeEffectExample;

public interface BotSentenceNodeEffectMapper {
    int countByExample(BotSentenceNodeEffectExample example);

    int deleteByExample(BotSentenceNodeEffectExample example);

    int deleteByPrimaryKey(String nodeId);

    int insert(BotSentenceNodeEffect record);

    int insertSelective(BotSentenceNodeEffect record);

    List<BotSentenceNodeEffect> selectByExample(BotSentenceNodeEffectExample example);

    BotSentenceNodeEffect selectByPrimaryKey(String nodeId);

    int updateByExampleSelective(@Param("record") BotSentenceNodeEffect record, @Param("example") BotSentenceNodeEffectExample example);

    int updateByExample(@Param("record") BotSentenceNodeEffect record, @Param("example") BotSentenceNodeEffectExample example);

    int updateByPrimaryKeySelective(BotSentenceNodeEffect record);

    int updateByPrimaryKey(BotSentenceNodeEffect record);
}