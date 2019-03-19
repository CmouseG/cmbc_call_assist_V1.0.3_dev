package com.guiji.botsentence.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceDomain;

public interface BotSentenceDomainExtMapper {

    int batchInsert(List<BotSentenceDomain> list);

    int batchUpdateComDomain(String processId);
    
    int batchUpdateIgnoreButNegative(String processId);
    
    int batchUpdateIsSpecialLimitFree(String processId);
    
    int batchUpdateIgnoreButSentence(String processId);
    
    int updateIgnoreButSentenceByDomain(@Param("processId")String processId, @Param("domainName")String domainName);
    
    int updateIgnoreButNegativeByDomain(@Param("processId")String processId, @Param("domainName")String domainName);
    
    int updateNotMatchLess4ToByDomain(@Param("processId")String processId, @Param("domainName")String domainName);
    
    int updateNotMatchToByDomain(@Param("processId")String processId, @Param("domainName")String domainName);
    
    int updateNotWordsToByDomain(@Param("processId")String processId, @Param("domainName")String domainName);
    
}