package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceTemplateExample;

public interface BotSentenceTemplateMapper {
    int countByExample(BotSentenceTemplateExample example);

    int deleteByExample(BotSentenceTemplateExample example);

    int deleteByPrimaryKey(String processId);

    int insert(BotSentenceTemplate record);

    int insertSelective(BotSentenceTemplate record);

    List<BotSentenceTemplate> selectByExample(BotSentenceTemplateExample example);

    BotSentenceTemplate selectByPrimaryKey(String processId);

    int updateByExampleSelective(@Param("record") BotSentenceTemplate record, @Param("example") BotSentenceTemplateExample example);

    int updateByExample(@Param("record") BotSentenceTemplate record, @Param("example") BotSentenceTemplateExample example);

    int updateByPrimaryKeySelective(BotSentenceTemplate record);

    int updateByPrimaryKey(BotSentenceTemplate record);
}