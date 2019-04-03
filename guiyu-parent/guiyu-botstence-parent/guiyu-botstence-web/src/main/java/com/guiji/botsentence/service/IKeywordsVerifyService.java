package com.guiji.botsentence.service;

import com.guiji.botsentence.vo.BotSentenceIntentVO;

import java.util.List;

public interface IKeywordsVerifyService {

    /**
     * 主流程关键词校验
     * 1、当前分支所有意图的关键词不能重复
     * 2、同级分支所有关键词不能重复
     */
    void verifyMainProcessBranch(String nowBranchId, List<BotSentenceIntentVO> intentVOList, String processId, String domainName);
}
