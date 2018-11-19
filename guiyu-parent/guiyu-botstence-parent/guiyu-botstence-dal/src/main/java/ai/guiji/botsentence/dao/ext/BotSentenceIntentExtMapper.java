package ai.guiji.botsentence.dao.ext;

import ai.guiji.botsentence.dao.entity.ext.IntentVO;

import java.util.List;

public interface BotSentenceIntentExtMapper {
    

    int batchInsert(List<IntentVO> list);

}