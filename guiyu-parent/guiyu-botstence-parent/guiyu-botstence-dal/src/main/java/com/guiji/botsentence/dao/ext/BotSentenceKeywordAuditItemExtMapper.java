package com.guiji.botsentence.dao.ext;

import com.guiji.botsentence.dao.entity.BotSentenceKeywordAuditItem;

import java.util.List;

public interface BotSentenceKeywordAuditItemExtMapper {
    void batchInsert(List<BotSentenceKeywordAuditItem> list);

    void batchAudit(List<BotSentenceKeywordAuditItem> list);
}