package com.guiji.botsentence.dao.ext;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceBranch;

public interface BotSentenceBranchExtMapper {
   
    int batchInsert(List<BotSentenceBranch> list);
    
    public List<String> querySpecialBranchoList(String processId);

}