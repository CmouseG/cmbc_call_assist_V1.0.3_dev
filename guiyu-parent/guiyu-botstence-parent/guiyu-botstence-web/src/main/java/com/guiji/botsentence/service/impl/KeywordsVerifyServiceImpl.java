package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BusinessAnswerTaskExtMapper;
import com.guiji.botsentence.service.IKeywordsVerifyService;
import com.guiji.botsentence.util.KeywordsUtil;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KeywordsVerifyServiceImpl implements IKeywordsVerifyService {

    private static String INTENT_ERROR_FORMAT = "意图'%'与'%'的关键词【%】重复<br/>";

    private static String BRANCH_ERROR_FORMAT = "意图'%'与【分支'%'-'%'】的关键词【%】重复<br/>";

    private static String COMMON_DIALOG_ERROR_FORMAT = "意图'%'与【通用对话'%'-'%'】的关键词【%】重复<br/>";

    private static String BUSINESS_ANSWER_ERROR_FORMAT = "意图'%'与【业务问答'%'-'%'】的关键词【%】重复<br/>";

    @Resource
    private BotSentenceBranchMapper botSentenceBranchMapper;

    @Resource
    private BotSentenceIntentMapper botSentenceIntentMapper;

    @Resource
    private BusinessAnswerTaskExtMapper businessAnswerTaskExtMapper;

    @Override
    public void verifyMainProcessBranch(List<BotSentenceIntentVO> intentVOList, String processId, String domainName, String currentBranchId) {

        if(CollectionUtils.isEmpty(intentVOList)){
            return;
        }

        StringBuilder errorSb = new StringBuilder();

        // 1、当前分支所有意图的关键词不能重复
        Map<String, String> keywordToIntentNameMap = Maps.newHashMap();
        verifyCurrentBranchIntents(intentVOList, keywordToIntentNameMap, errorSb);

//        2、同级分支所有关键词不能重复
        BotSentenceBranchExample example = new BotSentenceBranchExample();
        example.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domainName);
        List<BotSentenceBranch> branches = botSentenceBranchMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(branches)){
            return;
        }

        branches.forEach(branch -> {
            if(branch.getBranchId().equals(currentBranchId)){
                return;
            }
            if(StringUtils.isBlank(branch.getIntents())){
                return;
            }
            List<BotSentenceIntent> botSentenceIntents = getIntentsByIntentIds(branch.getIntents());
            botSentenceIntents.forEach(botSentenceIntent -> {
                List<String> keywords = JSON.parseArray(botSentenceIntent.getKeywords(), String.class);
                keywords.forEach(originKeyword -> {
                    if(keywordToIntentNameMap.containsKey(originKeyword)){
                        KeywordsUtil.Keyword keyword = KeywordsUtil.buildKeywordFromOrigin(originKeyword);
                        errorSb.append(String.format(BRANCH_ERROR_FORMAT, keywordToIntentNameMap.get(originKeyword), branch.getBranchName(), botSentenceIntent.getName(), keyword.getDisplayKeyword()));
                    }
                });
            });
        });

        String errorMessage = errorSb.toString();
        if(StringUtils.isNotBlank(errorMessage)){
            throw new CommonException(errorMessage);
        }
    }

    @Override
    public void verifyCommonDialogBranch(List<BotSentenceIntentVO> intentVOList, String processId, String currentBranchId) {
        if(CollectionUtils.isEmpty(intentVOList)){
            return;
        }
        StringBuilder errorSb = new StringBuilder();

        // 1、当前分支所有意图的关键词不能重复
        Map<String, String> keywordToIntentNameMap = Maps.newHashMap();
        verifyCurrentBranchIntents(intentVOList, keywordToIntentNameMap, errorSb);

        // 2、与限定的通用对话所有关键词不能重复
        List<BotSentenceBranch> specialCommonDialogBranches = Lists.newArrayList();

        BotSentenceBranchExample negativeBranchExample = new BotSentenceBranchExample();
        negativeBranchExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andBranchIdNotEqualTo(currentBranchId)
                .andIntentsIsNotNull()
                .andDomainIn(Arrays.asList("在忙","投诉","拒绝","用户不清楚"))
                .andBranchNameEqualTo("negative");

        specialCommonDialogBranches.addAll(botSentenceBranchMapper.selectByExample(negativeBranchExample));


        BotSentenceBranchExample specialBranchExample = new BotSentenceBranchExample();
        specialBranchExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andBranchIdNotEqualTo(currentBranchId)
                .andIntentsIsNotNull()
                .andDomainEqualTo("号码过滤")
                .andBranchNameEqualTo("special");

        specialCommonDialogBranches.addAll(botSentenceBranchMapper.selectByExample(specialBranchExample));

        specialCommonDialogBranches.forEach(branch -> {
            String intentIds = branch.getIntents();
            if(StringUtils.isBlank(intentIds)){
                return;
            }
            List<BotSentenceIntent> botSentenceIntents = getIntentsByIntentIds(intentIds);
            botSentenceIntents.forEach(intent ->{
                List<String> keywords = JSON.parseArray(intent.getKeywords(), String.class);
                keywords.forEach(originKeyword -> {
                    if(keywordToIntentNameMap.containsKey(originKeyword)){
                        KeywordsUtil.Keyword keyword = KeywordsUtil.buildKeywordFromOrigin(originKeyword);
                        errorSb.append(String.format(COMMON_DIALOG_ERROR_FORMAT, keywordToIntentNameMap.get(originKeyword), branch.getDomain(), intent.getName(), keyword.getDisplayKeyword()));
                    }
                });
            });
        });

//      3、与所有业务问答的关键词不能重复
        List<BusinessAnswerTaskExt> businessAnswerBranches = businessAnswerTaskExtMapper.queryBusinessAnswerTaskExtById(processId);
        Integer index = 1;
        for (BusinessAnswerTaskExt branch: businessAnswerBranches) {
            String intentIds = branch.getIntentId();
            if(StringUtils.isBlank(intentIds)){
                index++;
                continue;
            }
            List<BotSentenceIntent> botSentenceIntents = getIntentsByIntentIds(intentIds);
            for (BotSentenceIntent intent: botSentenceIntents) {
                List<String> keywords = JSON.parseArray(intent.getKeywords(), String.class);
                for (String originKeyword: keywords) {
                    if(keywordToIntentNameMap.containsKey(originKeyword)){
                        KeywordsUtil.Keyword keyword = KeywordsUtil.buildKeywordFromOrigin(originKeyword);
                        errorSb.append(String.format(BUSINESS_ANSWER_ERROR_FORMAT, keywordToIntentNameMap.get(originKeyword), String.valueOf(index), intent.getName(), keyword.getDisplayKeyword()));
                    }
                }
            }
            index++;
        }

        String errorMessage = errorSb.toString();
        if(StringUtils.isNotBlank(errorMessage)){
            throw new CommonException(errorMessage);
        }
    }

    /**
     * 当前分支所有意图的关键词不能重复
     * @param intentVOList 当前分支所有意图
     * @param keywordToIntentNameMap
     * @param errorSb
     */
    private void verifyCurrentBranchIntents(List<BotSentenceIntentVO> intentVOList, Map<String, String> keywordToIntentNameMap, StringBuilder errorSb){
        intentVOList.forEach(intentVO -> {
            List<String> keywords = KeywordsUtil.getAllKeywordsFromIntentVO(intentVO);
            keywords.forEach(originKeyword -> {
                if(keywordToIntentNameMap.containsKey(originKeyword)){
                    KeywordsUtil.Keyword keyword = KeywordsUtil.buildKeywordFromOrigin(originKeyword);
                    errorSb.append(String.format(INTENT_ERROR_FORMAT, intentVO.getName(), keywordToIntentNameMap.get(originKeyword), keyword.getDisplayKeyword()));
                }else {
                    keywordToIntentNameMap.put(originKeyword, intentVO.getName());
                }
            });
        });
    }

    private List<BotSentenceIntent> getIntentsByIntentIds(String intentIds){

        String[] intentIdArray = intentIds.split(",");
        List<Long> intentIdList = Lists.newArrayList();
        for (String intentId: intentIdArray) {
            intentIdList.add(Long.parseLong(intentId));
        }
        BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
        intentExample.createCriteria().andIdIn(intentIdList);
        return botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
    }
}
