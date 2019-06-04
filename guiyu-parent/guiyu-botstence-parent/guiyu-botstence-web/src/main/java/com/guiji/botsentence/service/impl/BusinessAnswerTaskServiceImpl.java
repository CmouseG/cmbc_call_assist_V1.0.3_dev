package com.guiji.botsentence.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BusinessAnswerTaskExtMapper;
import com.guiji.botsentence.service.BusinessAnswerTaskService;
import com.guiji.botsentence.service.IGradeService;
import com.guiji.botsentence.service.IKeywordsVerifyService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.enums.DomainNameEnum;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.botsentence.vo.BusinessAnswerVo;
import com.guiji.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BusinessAnswerTaskServiceImpl implements  BusinessAnswerTaskService{

	@Resource
	private BusinessAnswerTaskExtMapper mapper;

	@Resource
	private VoliceInfoMapper voliceInfoMapper;

	@Resource
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Resource
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Resource
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Resource
	private VoliceServiceImpl voliceServiceImpl;
	
	@Resource
	private BotSentenceKeyWordsServiceImpl botSentenceKeyWordsService;

	@Resource
	private IKeywordsVerifyService iKeywordsVerifyService;
	
	@Resource
	private IGradeService iGradeService;

	private static final String SPECIAL ="special_";
	
	private Logger logger = LoggerFactory.getLogger(BusinessAnswerTaskServiceImpl.class);
	
	/**
	 * 分页查询
	 */
	@Override
	public List<BusinessAnswerTaskExt> queryBusinessAnswerListByPage(String processId) {
		List<BusinessAnswerTaskExt> list=mapper.queryBusinessAnswerTaskExtById(processId);
		int index = 1;
		for(BusinessAnswerTaskExt item:list){
			item.setIndex(index);
			item.setProcessId(processId);
			
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(item.getBranchId());
			item.setIntentList(intentList);

			List<Long> voiceIdList = JSON.parseArray(item.getVoliceId(), Long.class);
			if(!CollectionUtils.isEmpty(voiceIdList)){
				VoliceInfoExample voiceExample = new VoliceInfoExample();
				voiceExample.createCriteria().andVoliceIdIn(voiceIdList);
				item.setVoiceInfoList(voliceInfoMapper.selectByExample(voiceExample));
			}
			index++;
		}
		
		return list;
	}

	@Override
	public int countBusinessAnswerNum(String processId) {
		return mapper.queryBusinessAnswerTaskExtCount(processId);
	}

	@Override
	@Transactional
	public void addBusinessAnswer(BusinessAnswerVo record, String userId) {

		List<BotSentenceIntentVO> intentVOS = record.getIntentList();

		if(CollectionUtils.isEmpty(intentVOS)){
			throw new CommonException("请选择意图!");
		}

		iKeywordsVerifyService.verifyBusinessAnswerBranch(intentVOS, record.getProcessId(), null);

		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(record.getProcessId());
		
		//获取branchName
		int branchNo =mapper.getLastbranchNo(record.getProcessId());
		String branchName = SPECIAL+branchNo;
		

		BotSentenceBranch branch=new BotSentenceBranch();
		branch.setProcessId(record.getProcessId());
		
		if(null != record.getIntentList() && record.getIntentList().size() > 0) {
			String intents = botSentenceKeyWordsService.saveIntent(DomainNameEnum.NORMAL_QUESTION.getKey(), process.getProcessId(), process.getTemplateId(), record.getIntentList(), "02", null, userId);
			if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
				branch.setIntents(intents);
			}
		}
		
		branch.setUserAsk(record.getUserAsk().replace("\n", "").trim().replace(",", "，"));
		branch.setDomain(DomainNameEnum.NORMAL_QUESTION.getKey());
		branch.setNext(DomainNameEnum.NORMAL_QUESTION.getKey());
		branch.setEnd(DomainNameEnum.INVITATION.getKey());
		branch.setTemplateId(process.getTemplateId());
		branch.setBranchName(branchName);
		branch.setNeedAgent(null);//默认设置不需要转人工
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWeight())) {
			branch.setWeight(record.getWeight());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRule()) && Constant.BRANCH_RULE_02.equals(record.getRule())) {
			branch.setRule(record.getRule());
			if(StringUtils.isEmpty(record.getEnd())){
				throw new CommonException("请选择跳转逻辑!");
			}
			branch.setEnd(record.getEnd());
			
			if(Constant.DOMAIN_TYPE_AGENT.equals(record.getEnd())) {
				//校验是否存在agent的卡片
				BotSentenceDomain domain = botSentenceProcessService.getDomain(record.getProcessId(), "agent");
				if(null == domain) {
					throw new CommonException("请先添加转人工卡片!");
				}
				branch.setNeedAgent(Constant.NEED_AGENT_YES);
			}
			branch.setResponse("[]");
		}else {
			List<VoliceInfo> voiceInfoList = record.getVoiceInfoList();
			if(CollectionUtils.isEmpty(voiceInfoList)) {
				throw new CommonException("请先保存文案内容!");
			}

			List<Long> newVoiceIds = Lists.newArrayList();

			voiceInfoList.forEach(voiceInfo -> {
				voiceInfo.setContent(voiceInfo.getContent().replace("\n", "").trim());
				voiceInfo.setProcessId(record.getProcessId());
				voiceInfo.setTemplateId(branch.getTemplateId());
				voiceInfo.setType(Constant.VOICE_TYPE_NORMAL);
				voiceInfo.setDomainName(DomainNameEnum.NORMAL_QUESTION.getKey());
				voiceInfo.setCrtTime(new Date());
				voiceInfo.setCrtUser(userId);
				voiceInfo.setFlag(Constant.TEXT_ADD);
				newVoiceIds.add(voliceServiceImpl.saveVoliceInfo(voiceInfo, userId));
			});

			branch.setResponse(JSON.toJSONString(newVoiceIds));
		}
		branch.setCrtTime(new Date());
		branch.setCrtUser(userId);
		botSentenceBranchMapper.insertSelective(branch);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(record.getProcessId(), userId);
	}
	
	/**
	 * 
	 * @param deleteKeyword  带双引号的关键字，例如："位置","公司地址"
	 * @param forselect_keywords 带双引号的关键字
	 * @return
	 */
	private String deleteKeywords(String deleteKeyword, String forselect_keywords) {
		logger.info("需要删除的关键字: " + deleteKeyword);
		if(StringUtils.isEmpty(deleteKeyword)) {
			return forselect_keywords;
		}
		List<String> selectKeywordList = BotSentenceUtil.getKeywords(forselect_keywords.trim());
		String leftSelectKeyWord = selectKeywordList.get(0);
		String yuliuSelectKeyWord = null;
		if(selectKeywordList.size() > 1) {
			yuliuSelectKeyWord = selectKeywordList.get(1);
		}
		
		String[] select_keyword_array = leftSelectKeyWord.split(",");
		
		List<String> select_keyword_list = new ArrayList<>();
		
		for(String temp : select_keyword_array) {
			select_keyword_list.add(temp.trim());
		}
		
		List<String> new_select_keyword_list = new ArrayList<>();
		
		
		List<String> deleteKeywordList = BotSentenceUtil.getKeywords(deleteKeyword);
		
		String left = deleteKeywordList.get(0);
		String[] delete_keyword_array = left.split(",");
		List<String> delete_keyword_List = Arrays.asList(delete_keyword_array);
		
		for(String temp : select_keyword_list) {
			if(delete_keyword_List.contains(temp.trim())) {
				continue;
			}
			new_select_keyword_list.add(temp);
		}
		
		String left_select_keyword = "";
		for(int i = 0 ; i < new_select_keyword_list.size() ; i++) {
			if(i == 0) {
				left_select_keyword = new_select_keyword_list.get(i);
			}else {
				left_select_keyword = left_select_keyword + "," + new_select_keyword_list.get(i);
			}
		}
		
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(yuliuSelectKeyWord)) {
			if(org.apache.commons.lang.StringUtils.isNotBlank(left_select_keyword)) {
				forselect_keywords = left_select_keyword + "," + yuliuSelectKeyWord;
			}else {
				forselect_keywords = yuliuSelectKeyWord;
			}
		}else {
			forselect_keywords = left_select_keyword;
		}
		forselect_keywords = "[" + forselect_keywords + "]";
		
		
		return forselect_keywords;
	}
	

	@Override
	@Transactional
	public void delBusinessAnswer(String branchId, String userId) {
		if(org.apache.commons.lang.StringUtils.isBlank(branchId)) {
			throw new CommonException("删除失败,请求参数不完整!");
		}
		BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		if(null == branch) {
			throw new CommonException("删除数据不存在!");
		}

		iGradeService.deleteGradeForBindBranch(branch.getProcessId(), branch.getBranchName());

		String resp = branch.getResponse();
		
		String[] resp_array = BotSentenceUtil.getResponses(resp);
		if(null != resp_array && resp_array.length > 0) {
			for(int i = 0 ; i < resp_array.length ; i++) {
				voliceServiceImpl.deleteVolice(branch.getProcessId(), resp_array[i]);
			}
		}
		
		//删除select.json关键字
		BotSentenceIntentExample example = new BotSentenceIntentExample();
		example.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andDomainNameEqualTo(DomainNameEnum.NORMAL_QUESTION.getKey()).andForSelectEqualTo(1);
		List<BotSentenceIntent> list = botSentenceIntentMapper.selectByExampleWithBLOBs(example);
		String forselect_keywords = "";
		BotSentenceIntent forselect_intent = null;
		if(null != list && list.size() > 0) {
			forselect_intent = list.get(0);
			forselect_keywords = forselect_intent.getKeywords();
		}
		
		String intents = branch.getIntents();
		String[] intent_array = intents.split(",");
		if(null != intent_array && intent_array.length > 0) {
			for(int i = 0 ; i < intent_array.length ; i++) {
				logger.info("删除意图信息: " + intent_array[i]);
				BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent_array[i]));
				botSentenceIntentMapper.deleteByPrimaryKey(new Long(intent_array[i]));
				
				if(!StringUtils.isEmpty(forselect_keywords)) {
					//删除当前意图对应的关键词
					String newKeywords = deleteKeywords(intent.getKeywords(), forselect_keywords);
					
					newKeywords = newKeywords.replace("\n", "");//替换换行符
					forselect_intent.setKeywords(newKeywords);
					forselect_intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
					forselect_intent.setLstUpdateUser(userId);
					botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(forselect_intent);
				}
			}
		}
		logger.info("删除branch一般问题: " + branchId);
		botSentenceBranchMapper.deleteByPrimaryKey(branchId);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(branch.getProcessId(), userId);
	}

	@Override
	@Transactional
	public void updateBusinessAnswer(BusinessAnswerVo record, String userId){
		if(org.apache.commons.lang.StringUtils.isBlank(record.getProcessId()) || 
				org.apache.commons.lang.StringUtils.isBlank(record.getBranchId())) {
			throw new CommonException("更新失败,请求参数不完整!");
		}
		
		BotSentenceBranch oldbranch = botSentenceBranchMapper.selectByPrimaryKey(record.getBranchId());
		String oldAgentIntent = oldbranch.getAgentIntent();
		logger.info("当前转人工意图: " + oldAgentIntent);

		List<BotSentenceIntentVO> intentVOS = record.getIntentList();

		if(CollectionUtils.isEmpty(intentVOS)){
			throw new CommonException("请选择意图!");
		}

		iKeywordsVerifyService.verifyBusinessAnswerBranch(intentVOS, record.getProcessId(), record.getBranchId());

		//插入流程信息
		BotSentenceBranch branch= botSentenceBranchMapper.selectByPrimaryKey(record.getBranchId());
		branch.setUserAsk(record.getUserAsk().replace("\n", "").trim().replace(",", "，"));
		branch.setNeedAgent(null);//默认设置不需要转人工
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWeight())) {
			branch.setWeight(record.getWeight());
		}

		//判断是否跳转主流程
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRule())
				&& Constant.BRANCH_RULE_02.equals(record.getRule())) {
			branch.setRule(record.getRule());
			if(StringUtils.isEmpty(record.getEnd())){
				throw new CommonException("请选择跳转逻辑!");
			}
			
			//删除原先维护的文案
			String responses = branch.getResponse();
			if(org.apache.commons.lang.StringUtils.isNotBlank(responses) && responses.length() > 2) {
				responses = responses.substring(1, responses.length() - 1);
				String[] array = responses.split(",");
				for(String temp : array) {
					voliceServiceImpl.deleteVolice(branch.getProcessId(), temp);
				}
			}
			
			branch.setResponse("[]");
			branch.setEnd(record.getEnd());
			
			if(Constant.DOMAIN_TYPE_AGENT.equals(record.getEnd())) {
				//校验是否存在agent的卡片
				BotSentenceDomain domain = botSentenceProcessService.getDomain(record.getProcessId(), "agent");
				if(null == domain) {
					throw new CommonException("请先添加转人工卡片!");
				}
				branch.setNeedAgent(Constant.NEED_AGENT_YES);//设置需要转人工
			}
		}else {
			List<VoliceInfo> voiceInfoList = record.getVoiceInfoList();
			if(CollectionUtils.isEmpty(voiceInfoList)) {
				throw new CommonException("请先保存文案内容!");
			}

			branch.setEnd(DomainNameEnum.INVITATION.getKey());
			branch.setRule(null);

			List<Long> originVoiceIds = JSON.parseArray(branch.getResponse(), Long.class);
			List<Long> newVoiceIds = Lists.newArrayList();

			voiceInfoList.forEach(voiceInfo -> {
				if(null != voiceInfo.getVoliceId()){
					VoliceInfo originInfo = voliceInfoMapper.selectByPrimaryKey(voiceInfo.getVoliceId());
					if(null == originInfo){
						throw new CommonException(voiceInfo.getVoliceId() + "录音记录不存在!");
					}
					originInfo.setContent(voiceInfo.getContent().replace("\n", "").trim());
					originInfo.setFlag(Constant.TEXT_MODIFY);
					originInfo.setLstUpdateTime(new Date());
					originInfo.setLstUpdateUser(userId);
					voliceServiceImpl.saveVoliceInfo(originInfo, userId);
					newVoiceIds.add(voiceInfo.getVoliceId());
				}else {
					voiceInfo.setContent(voiceInfo.getContent().replace("\n", "").trim());
					voiceInfo.setProcessId(record.getProcessId());
					voiceInfo.setTemplateId(branch.getTemplateId());
					voiceInfo.setType(Constant.VOICE_TYPE_NORMAL);
					voiceInfo.setDomainName(DomainNameEnum.NORMAL_QUESTION.getKey());
					voiceInfo.setCrtTime(new Date());
					voiceInfo.setCrtUser(userId);
					voiceInfo.setFlag(Constant.TEXT_ADD);
					newVoiceIds.add(voliceServiceImpl.saveVoliceInfo(voiceInfo, userId));
				}
			});

			branch.setResponse(JSON.toJSONString(newVoiceIds));

			originVoiceIds.removeAll(newVoiceIds);
			originVoiceIds.forEach(voiceId -> {
				voliceServiceImpl.deleteVolice(branch.getProcessId(), String.valueOf(voiceId));
			});
		}
		branch.setLstUpdateTime(new Date());
		branch.setLstUpdateUser(userId);
		
		//保存关键词库信息
		if(null != record.getIntentList() && record.getIntentList().size() > 0) {
			String intentIds2 = botSentenceKeyWordsService.saveIntent(branch.getDomain(), branch.getProcessId(), branch.getTemplateId(), record.getIntentList(), "02", branch, userId);
			if(org.apache.commons.lang.StringUtils.isNotBlank(intentIds2)) {
				branch.setIntents(intentIds2);
			}
			else {
				branch.setIntents(null);
			}
		}else {
			branch.setIntents(null);
		}
		
		botSentenceBranchMapper.updateByPrimaryKey(branch);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(record.getProcessId(), userId);

	}

	private String splitId(String joinString) {
		String ids=joinString.substring(1, joinString.length()-1);
		if(!StringUtils.isEmpty(ids)){
			return ids.split(",")[0].trim();
		}
		return null;
	}

	@Override
	public List<BusinessAnswerTaskExt> queryBusinessAnswerList(String processId) {
		List<BusinessAnswerTaskExt> list = mapper.queryBusinessAnswerTaskExtById(processId);
		return list;
	}
}
