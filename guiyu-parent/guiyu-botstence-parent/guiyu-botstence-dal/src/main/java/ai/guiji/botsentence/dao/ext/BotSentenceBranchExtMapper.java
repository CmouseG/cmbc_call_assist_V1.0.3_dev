package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import java.util.List;

public interface BotSentenceBranchExtMapper {
   
    int batchInsert(List<BotSentenceBranch> list);
    
    public List<String> querySpecialBranchoList(String processId);

}