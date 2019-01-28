package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceLog;
import com.guiji.botsentence.dao.entity.BotSentenceLogExample;

public interface BotSentenceLogMapper {
    int countByExample(BotSentenceLogExample example);

    int deleteByExample(BotSentenceLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceLog record);

    int insertSelective(BotSentenceLog record);

    List<BotSentenceLog> selectByExample(BotSentenceLogExample example);

    BotSentenceLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceLog record, @Param("example") BotSentenceLogExample example);

    int updateByExample(@Param("record") BotSentenceLog record, @Param("example") BotSentenceLogExample example);

    int updateByPrimaryKeySelective(BotSentenceLog record);

    int updateByPrimaryKey(BotSentenceLog record);
}