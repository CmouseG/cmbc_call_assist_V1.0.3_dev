package com.guiji.botsentence.dao;

import com.guiji.botsentence.dao.entity.BotSentenceShareAuth;
import com.guiji.botsentence.dao.entity.BotSentenceShareAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceShareAuthMapper {
    int countByExample(BotSentenceShareAuthExample example);

    int deleteByExample(BotSentenceShareAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceShareAuth record);

    int insertSelective(BotSentenceShareAuth record);

    List<BotSentenceShareAuth> selectByExample(BotSentenceShareAuthExample example);

    BotSentenceShareAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceShareAuth record, @Param("example") BotSentenceShareAuthExample example);

    int updateByExample(@Param("record") BotSentenceShareAuth record, @Param("example") BotSentenceShareAuthExample example);

    int updateByPrimaryKeySelective(BotSentenceShareAuth record);

    int updateByPrimaryKey(BotSentenceShareAuth record);
}