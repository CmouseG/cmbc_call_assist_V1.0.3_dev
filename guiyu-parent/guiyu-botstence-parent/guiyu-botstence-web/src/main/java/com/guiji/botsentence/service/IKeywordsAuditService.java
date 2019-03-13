package com.guiji.botsentence.service;

import com.guiji.botsentence.api.entity.*;
import com.guiji.common.model.Page;
import com.guiji.component.result.ServerResult;

import java.util.List;

public interface IKeywordsAuditService {

    void initiateKeywordsAudit(String processId, String userId);

    ServerResult<Page<KeywordsAuditRpsVO>> getKeywordAuditList(KeywordsAuditListReqVO keywordsAuditListReqVO);

    ServerResult<List<KeywordAuditItemRpsVO>> getItemListByStatus(KeywordAuditItemReqVO keywordAuditItemReqVO);

    void batchAuditKeywordItem(KeywordBatchAuditItemReqVO keywordBatchAuditItemReqVO);
}
