package com.guiji.botsentence.dao;

import com.guiji.botsentence.dao.entity.BotSentenceKeywordAuditItem;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordAuditItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BotSentenceKeywordAuditItemMapper {
    long countByExample(BotSentenceKeywordAuditItemExample example);

    int deleteByExample(BotSentenceKeywordAuditItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceKeywordAuditItem record);

    int insertSelective(BotSentenceKeywordAuditItem record);

    List<BotSentenceKeywordAuditItem> selectByExample(BotSentenceKeywordAuditItemExample example);

    BotSentenceKeywordAuditItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceKeywordAuditItem record, @Param("example") BotSentenceKeywordAuditItemExample example);

    int updateByExample(@Param("record") BotSentenceKeywordAuditItem record, @Param("example") BotSentenceKeywordAuditItemExample example);

    int updateByPrimaryKeySelective(BotSentenceKeywordAuditItem record);

    int updateByPrimaryKey(BotSentenceKeywordAuditItem record);
}