package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.api.entity.*;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BotSentenceKeywordAuditItemExtMapper;
import com.guiji.botsentence.service.IKeywordsAuditService;
import com.guiji.botsentence.service.ITradeService;
import com.guiji.botsentence.util.enums.KeywordAuditStatus;
import com.guiji.common.model.Page;
import com.guiji.component.result.ServerResult;
import com.guiji.user.dao.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class KeywordsAuditServiceImpl implements IKeywordsAuditService {

    private Logger logger = LoggerFactory.getLogger(KeywordsAuditServiceImpl.class);

    private static final String CUSTOMIZE_INTENT_NAME = "自定义";

    private static final String ASSEMBLE_KEYWORD_REGEX = "[";

    @Resource
    private IAuth iAuth;

    @Resource
    private BotSentenceProcessMapper botSentenceProcessMapper;

    @Resource
    private BotSentenceIntentMapper botSentenceIntentMapper;

    @Resource
    private BotSentenceKeywordTemplateMapper botSentenceKeywordTemplateMapper;

    @Resource
    private BotSentenceKeywordAuditMapper botSentenceKeywordAuditMapper;

    @Resource
    private BotSentenceKeywordAuditItemMapper botSentenceKeywordAuditItemMapper;

    @Resource
    private BotSentenceKeywordAuditItemExtMapper botSentenceKeywordAuditItemExtMapper;

    @Resource
    private ITradeService iTradeService;

    @Override
    @Transactional
    public void initiateKeywordsAudit(String processId, String userId) {

        BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
        logger.info("botSentenceProcess:{}", JSON.toJSONString(botSentenceProcess));

        if(null == botSentenceProcess || StringUtils.isBlank(botSentenceProcess.getIndustryId())){
            throw new RuntimeException("invalid process!");
        }

        BotSentenceIntentExample botSentenceIntentExample = new BotSentenceIntentExample();
        botSentenceIntentExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andNameEqualTo(CUSTOMIZE_INTENT_NAME);

        List<BotSentenceIntent> botSentenceIntents = botSentenceIntentMapper.selectByExampleWithBLOBs(botSentenceIntentExample);

        if(CollectionUtils.isEmpty(botSentenceIntents)){
            logger.warn("can not find customize intent with processId:{}", processId);
            return;
        }

        //获取行业所有模板关键词
        List<String> allTemplateKeywords = getKeywordsByIndustryId(botSentenceProcess.getIndustryId());
        //获取所有对应intent的关键词
        Map<Long,List<String>> oldIntentIdToKeyWordsMap = getOldIntentIdToKeyWordsMap(botSentenceIntents);

        Date nowDate = new Date();
        for (BotSentenceIntent intent: botSentenceIntents) {

            List<String> customizeKeyWords = JSON.parseArray(intent.getKeywords(), String.class);
            if(CollectionUtils.isEmpty(customizeKeyWords)){
                continue;
            }

            Long oldIntentId = intent.getOldId();
            if(null != oldIntentId && !CollectionUtils.isEmpty(oldIntentIdToKeyWordsMap.get(oldIntentId))){
                //去除所有对应intent的关键词
                customizeKeyWords.removeAll(oldIntentIdToKeyWordsMap.get(oldIntentId));
            }
            customizeKeyWords.removeAll(allTemplateKeywords);//去除行业模板已有的关键字
            //去除所有组合关键字
            customizeKeyWords.removeIf(keyword -> keyword.contains(ASSEMBLE_KEYWORD_REGEX));
            if(CollectionUtils.isEmpty(customizeKeyWords)){
                continue;
            }

            BotSentenceKeywordAudit keywordAudit = new BotSentenceKeywordAudit();
            keywordAudit.setProcessId(processId);
            keywordAudit.setIntentId(intent.getId().intValue());
            keywordAudit.setUserId(Integer.parseInt(userId));
            keywordAudit.setKeywordsCount(customizeKeyWords.size());
            keywordAudit.setCreateTime(nowDate);
            botSentenceKeywordAuditMapper.insert(keywordAudit);

            Integer keywordAuditId = keywordAudit.getId();

            List<BotSentenceKeywordAuditItem> keywordAuditItems = Lists.newArrayList();
            for (String keyword: customizeKeyWords) {
                BotSentenceKeywordAuditItem keywordAuditItem = new BotSentenceKeywordAuditItem();
                keywordAuditItem.setKeywordAuditId(keywordAuditId);
                keywordAuditItem.setKeyword(keyword);
                keywordAuditItem.setAuditStatus(KeywordAuditStatus.WAIT_AUDIT.getKey());
                keywordAuditItem.setCreateTime(nowDate);
                keywordAuditItem.setLastUpdateTime(nowDate);
                keywordAuditItems.add(keywordAuditItem);
            }

            botSentenceKeywordAuditItemExtMapper.batchInsert(keywordAuditItems);
        }
        logger.info("initiate keywords audit success");
    }

    @Override
    public ServerResult<Page<KeywordsAuditRpsVO>> getKeywordAuditList(KeywordsAuditListReqVO keywordsAuditListReqVO) {

        Page<KeywordsAuditRpsVO> page = new Page<>();
        page.setPageSize(keywordsAuditListReqVO.getPageSize());
        page.setPageNo(keywordsAuditListReqVO.getPageNum());

        BotSentenceKeywordAuditExample keywordAuditExample = new BotSentenceKeywordAuditExample();
        keywordAuditExample.setOffset(keywordsAuditListReqVO.getOffset());
        keywordAuditExample.setLimit(keywordsAuditListReqVO.getPageSize());
        keywordAuditExample.setOrderByClause("create_time desc");
        BotSentenceKeywordAuditExample.Criteria keywordAuditCriteria = keywordAuditExample.createCriteria();

        if(0 == keywordsAuditListReqVO.getKeywordAuditStatus()){
            keywordAuditCriteria.andKeywordsCountGreaterThan(0);
        }else if(1 == keywordsAuditListReqVO.getKeywordAuditStatus()){
            keywordAuditCriteria.andKeywordsCountLessThanOrEqualTo(0);
        }

        Date startDate = keywordsAuditListReqVO.getStartDate();
        if(null != startDate){
            Calendar startCalendar = new GregorianCalendar();
            startCalendar.setTime(startDate);
            startCalendar.set(Calendar.HOUR, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            keywordAuditCriteria.andCreateTimeGreaterThanOrEqualTo(startCalendar.getTime());
        }

        Date endDate = keywordsAuditListReqVO.getEndDate();
        if(null != endDate){
            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(endDate);
            endCalendar.set(Calendar.HOUR, 23);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.SECOND, 59);
            keywordAuditCriteria.andCreateTimeLessThanOrEqualTo(endCalendar.getTime());
        }

        Map<String, BotSentenceProcess> processIdToProcessMap = Maps.newHashMap();
        String speechName = keywordsAuditListReqVO.getSpeechName();
        String reqIndustryId = keywordsAuditListReqVO.getIndustryId();
        if(StringUtils.isNotBlank(speechName) || StringUtils.isNotBlank(reqIndustryId)){

            BotSentenceProcessExample processExample = new BotSentenceProcessExample();
            BotSentenceProcessExample.Criteria processCriteria = processExample.createCriteria();

            if(StringUtils.isNotBlank(speechName)){
                processCriteria.andTemplateNameLike("%"+speechName+"%");
            }
            if(StringUtils.isNotBlank(reqIndustryId)){
                processCriteria.andIndustryIdLike(reqIndustryId + "%");
            }

            List<BotSentenceProcess> processes = botSentenceProcessMapper.selectByExample(processExample);

            if(CollectionUtils.isEmpty(processes)){
                return ServerResult.createBySuccess(page);
            }

            processIdToProcessMap = processes.stream().collect(Collectors.toMap(BotSentenceProcess::getProcessId, Function.identity()));

            keywordAuditCriteria.andProcessIdIn(Lists.newArrayList(processIdToProcessMap.keySet()));
        }

        int totalRecord = (int) botSentenceKeywordAuditMapper.countByExample(keywordAuditExample);
        page.setTotalRecord(totalRecord);
        if(0 == totalRecord){
            return ServerResult.createBySuccess(page);
        }

        List<BotSentenceKeywordAudit> keywordAuditList = botSentenceKeywordAuditMapper.selectByExample(keywordAuditExample);
        if(CollectionUtils.isEmpty(keywordAuditList)){
            return ServerResult.createBySuccess(page);
        }

        Map<Integer, List<KeywordAuditItemRpsVO>> auditIdToItemListMap = Maps.newHashMap();
        Set<String> processIdSet = Sets.newHashSet();
        keywordAuditList.forEach(keywordAudit -> {
            auditIdToItemListMap.put(keywordAudit.getId(), Lists.newArrayList());
            processIdSet.add(keywordAudit.getProcessId());
        });

        if(CollectionUtils.isEmpty(processIdToProcessMap)){
            BotSentenceProcessExample processExample = new BotSentenceProcessExample();
            processExample.createCriteria().andProcessIdIn(Lists.newArrayList(processIdSet));

            List<BotSentenceProcess> processes = botSentenceProcessMapper.selectByExample(processExample);
            processIdToProcessMap = processes.stream().collect(Collectors.toMap(BotSentenceProcess::getProcessId, Function.identity()));
        }

        Set<String> industryIds = Sets.newHashSet();
        processIdToProcessMap.values().forEach(process -> {
            if(processIdSet.contains(process.getProcessId())){
                industryIds.add(process.getIndustryId());
            }
        });
        Map<String, String> industryIdToFullNameMap = iTradeService.getIndustryIdToFullNameMap(industryIds);

        KeywordAuditStatus auditStatus = KeywordAuditStatus.getStatusByKey(keywordsAuditListReqVO.getKeywordAuditStatus());
        BotSentenceKeywordAuditItemExample auditItemExample = new BotSentenceKeywordAuditItemExample();
        BotSentenceKeywordAuditItemExample.Criteria auditCriteria = auditItemExample.createCriteria();
        auditCriteria.andKeywordAuditIdIn(Lists.newArrayList(auditIdToItemListMap.keySet()));
        if(KeywordAuditStatus.WAIT_AUDIT == auditStatus){
            auditCriteria.andAuditStatusEqualTo(KeywordAuditStatus.WAIT_AUDIT.getKey());
        }else {
            auditCriteria.andAuditStatusNotEqualTo(KeywordAuditStatus.WAIT_AUDIT.getKey());
        }

        List<BotSentenceKeywordAuditItem> auditItems = botSentenceKeywordAuditItemMapper.selectByExample(auditItemExample);

        auditItems.forEach(auditItem -> {
            KeywordAuditItemRpsVO itemRpsVO = new KeywordAuditItemRpsVO();
            BeanUtils.copyProperties(auditItem, itemRpsVO);
            auditIdToItemListMap.get(auditItem.getKeywordAuditId()).add(itemRpsVO);
        });

        List<KeywordsAuditRpsVO> auditRpsVOS = Lists.newArrayList();
        for (BotSentenceKeywordAudit keywordAudit: keywordAuditList) {
            KeywordsAuditRpsVO auditRpsVO = new KeywordsAuditRpsVO();
            BeanUtils.copyProperties(keywordAudit, auditRpsVO);
            auditRpsVO.setKeywordAuditId(keywordAudit.getId());

            BotSentenceProcess process = processIdToProcessMap.get(keywordAudit.getProcessId());
            auditRpsVO.setAuditItems(auditIdToItemListMap.get(keywordAudit.getId()));

            String industryId = process.getIndustryId();
            auditRpsVO.setIndustryId(industryId);
            auditRpsVO.setIndustry(industryIdToFullNameMap.get(industryId));
            auditRpsVO.setTemplateName(process.getTemplateName());

            auditRpsVO.setProduceUserId(keywordAudit.getUserId());
            SysUser sysUser = iAuth.getUserById((long)keywordAudit.getUserId()).getBody();
            auditRpsVO.setProduceUserName(sysUser.getUsername());

            auditRpsVOS.add(auditRpsVO);
        }
        page.setRecords(auditRpsVOS);
        return ServerResult.createBySuccess(page);
    }


    @Override
    public ServerResult<List<KeywordAuditItemRpsVO>> getItemListByStatus(KeywordAuditItemReqVO keywordAuditItemReqVO) {

        List<KeywordAuditItemRpsVO> itemRpsVOS = Lists.newArrayList();

        BotSentenceKeywordAuditItemExample auditItemExample = new BotSentenceKeywordAuditItemExample();
        auditItemExample.createCriteria()
                .andAuditStatusEqualTo(keywordAuditItemReqVO.getAuditStatus())
                .andKeywordAuditIdEqualTo(keywordAuditItemReqVO.getKeywordAuditId());

        List<BotSentenceKeywordAuditItem> auditItems = botSentenceKeywordAuditItemMapper.selectByExample(auditItemExample);
        auditItems.forEach(item ->{
            KeywordAuditItemRpsVO itemRpsVO = new KeywordAuditItemRpsVO();
            BeanUtils.copyProperties(item, itemRpsVO);
            itemRpsVOS.add(itemRpsVO);
        });

        return ServerResult.createBySuccess(itemRpsVOS);
    }

    @Override
    @Transactional
    public void batchAuditKeywordItem(KeywordBatchAuditItemReqVO keywordBatchAuditItemReqVO) {

        KeywordAuditStatus status = KeywordAuditStatus.getStatusByKey(keywordBatchAuditItemReqVO.getAuditStatus());
        if(KeywordAuditStatus.WAIT_AUDIT == status){
            throw new RuntimeException("illegal audit status");
        }

        BotSentenceKeywordAudit keywordAudit = botSentenceKeywordAuditMapper.selectByPrimaryKey(keywordBatchAuditItemReqVO.getKeywordAuditId());
        if(null == keywordAudit){
            throw new RuntimeException("can not find keyword audit");
        }

        BotSentenceKeywordAuditItemExample auditItemExample = new BotSentenceKeywordAuditItemExample();
        auditItemExample.createCriteria()
                .andIdIn(keywordBatchAuditItemReqVO.getKeywordAuditItemIds())
                .andAuditStatusEqualTo(KeywordAuditStatus.WAIT_AUDIT.getKey());
        List<BotSentenceKeywordAuditItem> auditItems = botSentenceKeywordAuditItemMapper.selectByExample(auditItemExample);
        if(CollectionUtils.isEmpty(auditItems)){
            return;
        }

        List<String> keywords = Lists.newArrayList();
        auditItems.forEach(item -> {
            item.setAuditStatus(status.getKey());
            item.setLastUpdateTime(new Date());
            item.setAuditUserId(keywordBatchAuditItemReqVO.getAuditUserId());
            item.setTemplateId(keywordBatchAuditItemReqVO.getKeywordTemplateId());
            keywords.add(item.getKeyword());
        });

        int waitAuditKeywordCount = keywordAudit.getKeywordsCount()- auditItems.size();
        keywordAudit.setKeywordsCount(waitAuditKeywordCount);
        botSentenceKeywordAuditMapper.updateByPrimaryKeySelective(keywordAudit);
        botSentenceKeywordAuditItemExtMapper.batchAudit(auditItems);

        if(KeywordAuditStatus.JOINED == status) {
            BotSentenceKeywordTemplate keywordTemplate = botSentenceKeywordTemplateMapper.selectByPrimaryKey(keywordBatchAuditItemReqVO.getKeywordTemplateId());
            if(null == keywordTemplate){
                throw new RuntimeException("can not find keyword template");
            }
            List<String> templateKeywords = JSON.parseArray(keywordTemplate.getKeywords(),String.class);
            if(CollectionUtils.isEmpty(templateKeywords)){
                templateKeywords = Lists.newArrayList();
            }
            templateKeywords.addAll(keywords);
            keywordTemplate.setKeywords(JSON.toJSONString(Sets.newHashSet(templateKeywords)));
            botSentenceKeywordTemplateMapper.updateByPrimaryKeySelective(keywordTemplate);
        }
    }

    private List<String> getKeywordsByIndustryId(String industryId){

        List<String> allKeywords = Lists.newArrayList();

        BotSentenceKeywordTemplateExample example = new BotSentenceKeywordTemplateExample();
        example.createCriteria().andIndustryIdEqualTo(industryId);

        List<BotSentenceKeywordTemplate> keywordTemplates = botSentenceKeywordTemplateMapper.selectByExampleWithBLOBs(example);

        keywordTemplates.forEach(template -> allKeywords.addAll(JSON.parseArray(template.getKeywords(), String.class)));

        return allKeywords;
    }

    private Map<Long,List<String>> getOldIntentIdToKeyWordsMap(List<BotSentenceIntent> botSentenceIntents){
        Map<Long,List<String>> oldIntentIdToKeyWordsMap = Maps.newHashMap();

        List<Long> oldIntentIds = Lists.newArrayList();
        botSentenceIntents.forEach(intent -> oldIntentIds.add(intent.getOldId()));

        oldIntentIds.removeIf(Predicate.isEqual(null));
        if(CollectionUtils.isEmpty(oldIntentIds)){
            return oldIntentIdToKeyWordsMap;
        }

        BotSentenceIntentExample botSentenceIntentExample = new BotSentenceIntentExample();
        botSentenceIntentExample.createCriteria().andIdIn(oldIntentIds);
        List<BotSentenceIntent> oldIntents = botSentenceIntentMapper.selectByExampleWithBLOBs(botSentenceIntentExample);

        oldIntents.forEach(intent -> oldIntentIdToKeyWordsMap.put(intent.getId(), JSON.parseArray(intent.getKeywords(), String.class)));

        return oldIntentIdToKeyWordsMap;
    }
}
