package com.guiji.botsentence.service;

import com.guiji.botsentence.vo.BotSentenceIntentVO;

import java.util.List;

public interface IKeywordsVerifyService {

    /**
     * 主流程关键词校验
     * 1、当前分支所有意图的关键词不能重复
     * 2、同级分支所有关键词不能重复
     */
    void verifyMainProcessBranch(List<BotSentenceIntentVO> intentVOList, String processId, String domainName, String currentBranchId);

    /**
     * 通用对话关键词校验
     * 1、当前通用对话所有意图的关键词不能重复
     * 2、与限定的通用对话所有关键词不能重复
     * 3、与所有业务问答的关键词不能重复
     */
    void verifyCommonDialogBranch(List<BotSentenceIntentVO> intentVOList, String processId, String currentBranchId);


    /**
     * 通用对话关键词校验
     * 1、当前业务问答所有意图的关键词不能重复
     * 2、与其他业务问答的关键词不能重复
     * 3、与限定的通用对话所有关键词不能重复
     */
    void verifyBusinessAnswerBranch(List<BotSentenceIntentVO> intentVOList, String processId, String currentBranchId);
}
