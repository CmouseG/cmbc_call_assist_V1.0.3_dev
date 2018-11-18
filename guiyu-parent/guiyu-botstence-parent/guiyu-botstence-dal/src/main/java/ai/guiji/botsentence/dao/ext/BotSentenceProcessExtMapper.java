package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import java.util.List;

public interface BotSentenceProcessExtMapper {
 
    int batchInsert(List<BotSentenceProcess> list);


}