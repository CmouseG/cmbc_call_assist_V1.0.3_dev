package com.guiji.botsentence.dao;

import com.guiji.botsentence.dao.entity.BotSentenceDeploy;
import com.guiji.botsentence.dao.entity.BotSentenceDeployExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceDeployMapper {
    int countByExample(BotSentenceDeployExample example);

    int deleteByExample(BotSentenceDeployExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BotSentenceDeploy record);

    int insertSelective(BotSentenceDeploy record);

    List<BotSentenceDeploy> selectByExample(BotSentenceDeployExample example);

    BotSentenceDeploy selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BotSentenceDeploy record, @Param("example") BotSentenceDeployExample example);

    int updateByExample(@Param("record") BotSentenceDeploy record, @Param("example") BotSentenceDeployExample example);

    int updateByPrimaryKeySelective(BotSentenceDeploy record);

    int updateByPrimaryKey(BotSentenceDeploy record);
}