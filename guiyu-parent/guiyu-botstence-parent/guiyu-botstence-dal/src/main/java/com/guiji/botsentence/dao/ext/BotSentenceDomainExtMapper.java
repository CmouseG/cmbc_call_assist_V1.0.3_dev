package com.guiji.botsentence.dao.ext;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceDomain;

public interface BotSentenceDomainExtMapper {

    int batchInsert(List<BotSentenceDomain> list);

    int batchUpdateComDomain(String processId);
    
    int batchUpdateIgnoreButNegative(String processId);
    
    int batchUpdateIsSpecialLimitFree(String processId);
    
    int batchUpdateIgnoreButSentence(String processId);
}