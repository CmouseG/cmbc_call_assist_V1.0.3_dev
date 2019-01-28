package com.guiji.botsentence.dao.ext;

import java.util.List;

import com.guiji.botsentence.dao.entity.ext.IntentVO;

public interface BotSentenceIntentExtMapper {
    

    int batchInsert(List<IntentVO> list);

}