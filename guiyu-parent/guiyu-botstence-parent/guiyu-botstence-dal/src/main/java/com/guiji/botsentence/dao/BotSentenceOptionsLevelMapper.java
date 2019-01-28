package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceOptionsLevel;
import com.guiji.botsentence.dao.entity.BotSentenceOptionsLevelExample;

public interface BotSentenceOptionsLevelMapper {
    int countByExample(BotSentenceOptionsLevelExample example);

    int deleteByExample(BotSentenceOptionsLevelExample example);

    int insert(BotSentenceOptionsLevel record);

    int insertSelective(BotSentenceOptionsLevel record);

    List<BotSentenceOptionsLevel> selectByExample(BotSentenceOptionsLevelExample example);

    int updateByExampleSelective(@Param("record") BotSentenceOptionsLevel record, @Param("example") BotSentenceOptionsLevelExample example);

    int updateByExample(@Param("record") BotSentenceOptionsLevel record, @Param("example") BotSentenceOptionsLevelExample example);
}