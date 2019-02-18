package com.guiji.botsentence.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceBranch;

public interface BotSentenceBranchExtMapper {
   
    int batchInsert(List<BotSentenceBranch> list);
    
    public List<String> querySpecialBranchoList(String processId);
    
    public void updateEndWhenDeleteDomain(@Param("processId")String processId,@Param("domain")String domain);

}