package com.guiji.botsentence.dao.ext;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceProcess;

public interface BotSentenceProcessExtMapper {
 
    int batchInsert(List<BotSentenceProcess> list);

    public List<BotSentenceProcess> getTemplateBySelf(String accountNo);

    public List<BotSentenceProcess> getTemplateById(String templateId);
    
    public List<Object> getAvailableTemplateBySelf(String accountNo);
}