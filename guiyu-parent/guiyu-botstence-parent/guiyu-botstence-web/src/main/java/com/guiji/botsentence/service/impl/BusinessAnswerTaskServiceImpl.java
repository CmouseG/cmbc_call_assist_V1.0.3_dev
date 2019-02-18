package com.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceAdditionMapper;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.ext.BusinessAnswerTaskExtMapper;
import com.guiji.botsentence.service.BusinessAnswerTaskService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.botsentence.vo.BusinessAnswerVo;
import com.guiji.common.exception.CommonException;

@Service
public class BusinessAnswerTaskServiceImpl implements  BusinessAnswerTaskService{

	@Autowired
	private BusinessAnswerTaskExtMapper mapper;

	@Autowired
	private VoliceInfoMapper voliceInfoMapper;

	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	

	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Autowired
	private VoliceServiceImpl voliceServiceImpl;
	
	@Autowired
	private BotSentenceKeyWordsServiceImpl botSentenceKeyWordsService;
	
	@Autowired
	private BotSentenceKeyWordsValidateServiceImpl botSentenceKeyWordsValidateService;

	private static final String DOMAIN="一般问题";
	
	private static final String SPECIAL ="special_";
	
	private static final String AGENT ="agent";
	
	private Logger logger = LoggerFactory.getLogger(BusinessAnswerTaskServiceImpl.class);
	
	/**
	 * 分页查询
	 */
	@Override
	public List<BusinessAnswerTaskExt> queryBusinessAnswerListByPage(String processId) {
		List<BusinessAnswerTaskExt>  list=mapper.queryBusinessAnswerTaskExtById(processId);
		int index = 1;
		for(BusinessAnswerTaskExt item:list){
			item.setIndex(index);
			item.setProcessId(processId);
			
			//查询关键词库列表
			List<BotSentenceIntentVO> intentList = botSentenceKeyWordsService.getIntent(item.getBranchId());
			item.setIntentList(intentList);
			
			

			String voliceIds= item.getVoliceId();
			if(org.apache.commons.lang.StringUtils.isNotBlank(voliceIds)){
				String voliceId=splitId(voliceIds);
				if(org.apache.commons.lang.StringUtils.isNotBlank(voliceId)) {
					item.setVoliceId(voliceId);
					Map<String,String> map=mapper.queryVoliceUrlByIntentId(voliceId);
					if(null != map) {
						item.setVoliceUrl(map.get("volice_url"));
						item.setContent(map.get("content"));
					}
				}else {
					item.setVoliceId(null);
				}
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
		
		//校验关键字
		//获取所有关键词库对应关键词集合
		if(null != record.getIntentList() && record.getIntentList().size() > 0) {
			botSentenceKeyWordsValidateService.validateBusinessAskKeywords(record.getIntentList(), record.getProcessId(), null);
			
		}else {
			throw new CommonException("请选择意图!");
		}
		
		
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(record.getProcessId());
		
		//获取branchName
		int branchNo =mapper.getLastbranchNo(record.getProcessId());
		String branchName = SPECIAL+branchNo;
		

		BotSentenceBranch branch=new BotSentenceBranch();
		branch.setProcessId(record.getProcessId());
		
		if(null != record.getIntentList() && record.getIntentList().size() > 0) {
			String intents = botSentenceKeyWordsService.saveIntent(DOMAIN, process.getProcessId(), process.getTemplateId(), record.getIntentList(), "02", null, userId);
			if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
				branch.setIntents(intents);
			}
		}
		
		branch.setUserAsk(record.getUserAsk().replace("\n", "").trim().replace(",", "，"));
		branch.setDomain(DOMAIN);
		branch.setNext(DOMAIN);
		branch.setEnd("邀约");
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
			if(StringUtils.isEmpty(record.getContent())) {
				throw new CommonException("请先保存文案内容!");
			}
			//插入音频信息
			VoliceInfo voliceInfo=new VoliceInfo();
			voliceInfo.setContent(record.getContent().replace("\n", "").trim());
			voliceInfo.setProcessId(record.getProcessId());
			voliceInfo.setTemplateId(process.getTemplateId());
			voliceInfo.setType("00");
			voliceInfo.setDomainName(DOMAIN);
			voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
			voliceInfo.setCrtUser(userId);
			voliceInfo.setFlag("【新增】");
			voliceServiceImpl.saveVoliceInfo(voliceInfo, userId);
			branch.setResponse("["+voliceInfo.getVoliceId()+"]");
		}
		branch.setCrtTime(new Date(System.currentTimeMillis()));
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
	
	/**
	 * 
	 * @param addKeyword   不带双引号的关键字，例如：位置，在哪里，公司地址 
	 * @param forselect_keywords  select.json的一般问答内容,带双引号
	 * @return
	 */
	private String addKeywords(String addKeyword, String forselect_keywords) {
		logger.info("需要新增的关键字: " + addKeyword);
		String[] keys = addKeyword.split(",");
		
		List<String> selectKeywordList = BotSentenceUtil.getKeywords(forselect_keywords);
		String leftSelectKeyWord = selectKeywordList.get(0);
		
		String[] select_keyword_array = leftSelectKeyWord.split(",");
		List<String> select_keyword_list = Arrays.asList(select_keyword_array);
		
		
		for(int i = 0 ; i < keys.length ; i++) {
			if(!select_keyword_list.contains("\"" + keys[i] + "\"")) {
				forselect_keywords = forselect_keywords.substring(0, forselect_keywords.length() -1 ) + ",\"" + keys[i] + "\"]";
			}
		}
		
		
		/*if(forselect_keywords.indexOf("[\"" + keyword + "\"") > 0) {
			logger.info("新增select关键词: " + keyword);
			return forselect_keywords;
		}
		
		if(forselect_keywords.indexOf(",\"" + keyword + "\",") > 0) {
			logger.info("新增select关键词: " + keyword);
			return forselect_keywords;
		}
		
		if(forselect_keywords.indexOf(",\"" + keyword + "\"]") > 0) {
			logger.info("新增select关键词: " + keyword);
			return forselect_keywords;
		}
		
		forselect_keywords = forselect_keywords.substring(0, forselect_keywords.length() -1 ) + ",\"" + keyword + "\"]";
		*/
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
		String resp = branch.getResponse();
		
		String[] resp_array = BotSentenceUtil.getResponses(resp);
		if(null != resp_array && resp_array.length > 0) {
			for(int i = 0 ; i < resp_array.length ; i++) {
				logger.info("删除录音信息: " + resp_array[i]);
				voliceInfoMapper.deleteByPrimaryKey(new Long(resp_array[i]));
				
				//删除TTS任务信息
				BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
				ttsExample.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andBusiIdEqualTo(resp_array[i]);
				botSentenceTtsTaskMapper.deleteByExample(ttsExample);
				logger.info("删除TTS任务信息");
				
				//删除备用话术信息
				BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
				backExample.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andVoliceIdEqualTo(new Long(resp_array[i]));
				botSentenceTtsBackupMapper.deleteByExample(backExample);
				logger.info("删除备用话术信息");
			}
		}
		
		//删除select.json关键字
		BotSentenceIntentExample example = new BotSentenceIntentExample();
		example.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andDomainNameEqualTo(DOMAIN).andForSelectEqualTo(1);
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
		String oldNext = oldbranch.getNext();
		logger.info("当前转人工意图: " + oldAgentIntent);
		
		
		//校验关键字
		List<Long> intentIds = new ArrayList<>();
		
		//String[] keys = new String[] {};
		
		//获取所有关键词库对应关键词集合
		if(null != record.getIntentList() && record.getIntentList().size() > 0) {
			//String allKeywords = "";
			for(BotSentenceIntentVO temp : record.getIntentList()) {
				if(null != temp.getId()) {
					intentIds.add(new Long(temp.getId()));
				}
			}
			botSentenceKeyWordsValidateService.validateBusinessAskKeywords(record.getIntentList(), record.getProcessId(), intentIds);
		}else {
			throw new CommonException("请选择意图!");
		}
		
		//插入流程信息
		BotSentenceBranch branch= botSentenceBranchMapper.selectByPrimaryKey(record.getBranchId());
		branch.setUserAsk(record.getUserAsk().replace("\n", "").trim().replace(",", "，"));
		branch.setNeedAgent(null);//默认设置不需要转人工
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getWeight())) {
			branch.setWeight(record.getWeight());
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(record.getRule()) && Constant.BRANCH_RULE_02.equals(record.getRule())) {
			branch.setRule(record.getRule());
			if(StringUtils.isEmpty(record.getEnd())){
				throw new CommonException("请选择跳转逻辑!");
			}
			
			//删除原先维护的文案
			String responses = branch.getResponse();
			if(org.apache.commons.lang.StringUtils.isNotBlank(responses) && responses.length() > 2) {
				responses = responses.substring(1, responses.length() - 1);
				String array[] = responses.split(",");
				for(String temp : array) {
					voliceInfoMapper.deleteByPrimaryKey(new Long(temp));

					//删除TTS任务信息
					BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
					ttsExample.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andBusiIdEqualTo(temp);
					botSentenceTtsTaskMapper.deleteByExample(ttsExample);
					logger.info("删除TTS任务信息");
					
					//删除备用话术信息
					BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
					backExample.createCriteria().andProcessIdEqualTo(branch.getProcessId()).andVoliceIdEqualTo(new Long(temp));
					botSentenceTtsBackupMapper.deleteByExample(backExample);
					logger.info("删除备用话术信息");
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
			if(StringUtils.isEmpty(record.getContent())) {
				throw new CommonException("请先保存文案内容!");
			}
			branch.setEnd("邀约");
			branch.setRule(null);
			VoliceInfo voliceInfo=new VoliceInfo();
			
			if(org.apache.commons.lang.StringUtils.isNotBlank(record.getVoliceId())) {
				//更新音频信息
				voliceInfo=voliceInfoMapper.selectByPrimaryKey(new Long(record.getVoliceId()));
				//if(!voliceInfo.getContent().equals(record.getContent()) && !"【新增】".equals(voliceInfo.getFlag())) {
				if(!"【新增】".equals(voliceInfo.getFlag())) {
					voliceInfo.setFlag("【修改】");
				}
				voliceInfo.setContent(record.getContent().replace("\n", "").trim());
				voliceInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
				voliceInfo.setLstUpdateUser(userId);
				logger.info("当前flag: " + voliceInfo.getFlag());
				voliceServiceImpl.saveVoliceInfo(voliceInfo, userId);
			}else {
				//插入音频信息
				voliceInfo.setContent(record.getContent().replace("\n", "").trim());
				voliceInfo.setProcessId(record.getProcessId());
				voliceInfo.setTemplateId(oldbranch.getTemplateId());
				voliceInfo.setType("00");
				voliceInfo.setDomainName(DOMAIN);
				voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
				voliceInfo.setCrtUser(userId);
				voliceInfo.setFlag("【新增】");
				voliceServiceImpl.saveVoliceInfo(voliceInfo, userId);
				branch.setResponse("["+voliceInfo.getVoliceId()+"]");
			}
			
			branch.setResponse("["+voliceInfo.getVoliceId()+"]");
			
		}
		branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
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

	private String joinString(String[] array){
		StringBuffer buffer=new StringBuffer();
		for(String item:array){
			buffer.append(item);
			buffer.append(",");
		}
		String resultString=buffer.toString();
		return resultString.substring(0, resultString.length()-1);
	}

	public static void main(String[] args) {
		System.out.println("zpdcsmb_40575_en".substring(0, "zpdcsmb_40575_en".length()-3));
	}

	@Override
	public List<BusinessAnswerTaskExt> queryBusinessAnswerList(String processId) {
		List<BusinessAnswerTaskExt> list = mapper.queryBusinessAnswerTaskExtById(processId);
		return list;
	}
}
