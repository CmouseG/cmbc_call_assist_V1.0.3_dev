package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import com.guiji.botsentence.service.IKeywordsVerifyService;
import com.guiji.botsentence.util.KeywordsUtil;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class KeywordsVerifyServiceImpl implements IKeywordsVerifyService {

    private static String INTENT_ERROR_FORMAT = "意图'%'与'%'的关键词【%】重复<br/>";

    private static String BRANCH_ERROR_FORMAT = "意图'%'与分支'%'-'%'的关键词【%】重复<br/>";

    @Resource
    private BotSentenceBranchMapper botSentenceBranchMapper;

    @Resource
    private BotSentenceIntentMapper botSentenceIntentMapper;

    @Override
    public void verifyMainProcessBranch(String nowBranchId, List<BotSentenceIntentVO> intentVOList, String processId, String domainName) {

        if(CollectionUtils.isEmpty(intentVOList)){
            return;
        }

        StringBuilder errorSb = new StringBuilder();
//      1、当前分支所有意图的关键词不能重复
        Map<String, String> keywordToIntentNameMap = Maps.newHashMap();
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

//        2、同级分支所有关键词不能重复
        BotSentenceBranchExample example = new BotSentenceBranchExample();
        example.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domainName);
        List<BotSentenceBranch> branches = botSentenceBranchMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(branches)){
            return;
        }

        branches.forEach(branch -> {
            if(branch.getBranchId().equals(nowBranchId)){
                return;
            }
            if(StringUtils.isBlank(branch.getIntents())){
                return;
            }
            String[] intentIds = branch.getIntents().split(",");
            List<Long> intentIdList = Lists.newArrayList();
            for (String intentId: intentIds) {
                intentIdList.add(Long.parseLong(intentId));
            }
            BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
            intentExample.createCriteria().andIdIn(intentIdList);
            List<BotSentenceIntent> botSentenceIntents = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
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
}
