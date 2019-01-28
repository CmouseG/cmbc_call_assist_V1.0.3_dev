package com.guiji.botsentence.dao.ext;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceLabel;

public interface BotSentenceLabelExtMapper {

    int batchInsert(List<BotSentenceLabel> list);

   
}