package com.guiji.botsentence.dao;

import com.guiji.botsentence.dao.entity.BotSentenceKeywordAudit;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordAuditExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BotSentenceKeywordAuditMapper {
    long countByExample(BotSentenceKeywordAuditExample example);

    int deleteByExample(BotSentenceKeywordAuditExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotSentenceKeywordAudit record);

    int insertSelective(BotSentenceKeywordAudit record);

    List<BotSentenceKeywordAudit> selectByExample(BotSentenceKeywordAuditExample example);

    BotSentenceKeywordAudit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotSentenceKeywordAudit record, @Param("example") BotSentenceKeywordAuditExample example);

    int updateByExample(@Param("record") BotSentenceKeywordAudit record, @Param("example") BotSentenceKeywordAuditExample example);

    int updateByPrimaryKeySelective(BotSentenceKeywordAudit record);

    int updateByPrimaryKey(BotSentenceKeywordAudit record);
}