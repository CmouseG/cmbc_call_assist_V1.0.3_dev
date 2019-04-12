package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.guiji.botsentence.api.entity.KeywordTemplateReqVO;
import com.guiji.botsentence.dao.BotSentenceKeywordTemplateMapper;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;
import com.guiji.botsentence.service.IKeywordTemplateService;
import com.guiji.botsentence.util.enums.IntentType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class KeywordTemplateServiceImpl implements IKeywordTemplateService {

    private static final String EMPTY_KEYWORDS = "[]";

    @Resource
    private BotSentenceTradeMapper botSentenceTradeMapper;

    @Resource
    private BotSentenceKeywordTemplateMapper botSentenceKeywordTemplateMapper;

    @Override
    public void addKeywordTemplate(KeywordTemplateReqVO keywordTemplateReqVO) {
        String intentName = keywordTemplateReqVO.getIntentName();

        if(StringUtils.isBlank(intentName)){
            throw new RuntimeException("invalid parameters:" + JSON.toJSONString(keywordTemplateReqVO));
        }

        IntentType intentType = IntentType.getTypeByKey(keywordTemplateReqVO.getTemplateType());
        if(null == intentType){
            throw new RuntimeException("invalid keyword type:" + keywordTemplateReqVO.getTemplateType());
        }

        BotSentenceKeywordTemplate keywordTemplate = new BotSentenceKeywordTemplate();

        switch (intentType){
            case COMMON:
                keywordTemplate.setIndustryId(IntentType.COMMON_INDUSTRY_ID);
                keywordTemplate.setIndustryName(IntentType.COMMON_INDUSTRY_NAME);
                break;
            case INDUSTRY:
                String industryId = keywordTemplateReqVO.getIndustryId();
                BotSentenceTradeExample tradeExample = new BotSentenceTradeExample();
                tradeExample.createCriteria().andIndustryIdEqualTo(industryId);
                List<BotSentenceTrade> tradeList = botSentenceTradeMapper.selectByExample(tradeExample);
                if(CollectionUtils.isEmpty(tradeList)){
                    throw new RuntimeException("can not find industry, id:" + industryId);
                }
                BotSentenceTrade trade = tradeList.get(0);
                keywordTemplate.setIndustryId(trade.getIndustryId());
                keywordTemplate.setIndustryName(trade.getIndustryName());
                break;
        }
        keywordTemplate.setType(intentType.getKey());
        keywordTemplate.setIntentName(intentName);
        keywordTemplate.setKeywords(EMPTY_KEYWORDS);
        keywordTemplate.setCrtTime(new Date());
        keywordTemplate.setCrtUser(keywordTemplateReqVO.getUserId());

        botSentenceKeywordTemplateMapper.insert(keywordTemplate);
    }
}
