package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceNodePoolRelation;
import com.guiji.botsentence.dao.entity.BotSentenceNodePoolRelationExample;

public interface BotSentenceNodePoolRelationMapper {
    int countByExample(BotSentenceNodePoolRelationExample example);

    int deleteByExample(BotSentenceNodePoolRelationExample example);

    int deleteByPrimaryKey(String poolRelationId);

    int insert(BotSentenceNodePoolRelation record);

    int insertSelective(BotSentenceNodePoolRelation record);

    List<BotSentenceNodePoolRelation> selectByExample(BotSentenceNodePoolRelationExample example);

    BotSentenceNodePoolRelation selectByPrimaryKey(String poolRelationId);

    int updateByExampleSelective(@Param("record") BotSentenceNodePoolRelation record, @Param("example") BotSentenceNodePoolRelationExample example);

    int updateByExample(@Param("record") BotSentenceNodePoolRelation record, @Param("example") BotSentenceNodePoolRelationExample example);

    int updateByPrimaryKeySelective(BotSentenceNodePoolRelation record);

    int updateByPrimaryKey(BotSentenceNodePoolRelation record);
}