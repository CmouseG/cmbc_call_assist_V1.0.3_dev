package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import java.util.List;

public interface BotSentenceLabelExtMapper {

    int batchInsert(List<BotSentenceLabel> list);

   
}