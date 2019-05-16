package com.guiji.botsentence.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;
import com.guiji.botsentence.service.ITradeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements ITradeService {

    @Resource
    private BotSentenceTradeMapper botSentenceTradeMapper;


    @Override
    public String getIndustryFullName(String industryId) {
        return getIndustryIdToFullNameMap(Collections.singleton(industryId)).get(industryId);
    }

    @Override
    public Map<String, String> getIndustryIdToFullNameMap(Set<String> industryIds) {

        Map<String, String> industryIdToFullNameMap = Maps.newHashMap();
        if(CollectionUtils.isEmpty(industryIds)){
            return industryIdToFullNameMap;
        }

        Set<String> fullIndustryIds = Sets.newHashSet();
        industryIds.forEach(industryId -> {
            if(StringUtils.isBlank(industryId)){
                return;
            }
            fullIndustryIds.add(industryId);
            int endIndex = industryId.length() - 2;
            while (endIndex >= 2){
                fullIndustryIds.add(industryId.substring(0, endIndex));
                endIndex = endIndex - 2;
            }
        });

        BotSentenceTradeExample tradeExample = new BotSentenceTradeExample();
        tradeExample.createCriteria().andIndustryIdIn(Lists.newArrayList(fullIndustryIds));

        List<BotSentenceTrade> tradeList = botSentenceTradeMapper.selectByExample(tradeExample);

        if(CollectionUtils.isEmpty(tradeList)){
            return industryIdToFullNameMap;
        }

        Map<String, BotSentenceTrade> industryIdToTradeMap = Maps.newHashMap();
        tradeList.forEach(trade -> industryIdToTradeMap.put(trade.getIndustryId(), trade));

        industryIds.forEach(industryId -> {
            BotSentenceTrade currentTrade = industryIdToTradeMap.get(industryId);
            if(null == currentTrade){
                return;
            }
            String parentIndustryId = currentTrade.getParentId();
            StringBuilder fullNameBuilder = new StringBuilder(currentTrade.getIndustryName());

            while (StringUtils.isNotBlank(parentIndustryId)){
                currentTrade = industryIdToTradeMap.get(parentIndustryId);
                if(null == currentTrade){
                    break;
                }
                parentIndustryId = currentTrade.getParentId();
                fullNameBuilder.insert(0, currentTrade.getIndustryName() + "/");
            }

            industryIdToFullNameMap.put(industryId, fullNameBuilder.toString());
        });

        return industryIdToFullNameMap;
    }
}
