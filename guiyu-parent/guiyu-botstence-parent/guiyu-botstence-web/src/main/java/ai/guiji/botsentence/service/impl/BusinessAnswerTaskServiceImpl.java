package ai.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.ext.BusinessAnswerTaskExtMapper;
import ai.guiji.botsentence.service.BusinessAnswerTaskService;
import ai.guiji.botsentence.util.BotSentenceUtil;
import ai.guiji.botsentence.vo.BusinessAnswerVo;
import ai.guiji.component.exception.CommonException;

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
	private VoliceServiceImpl voliceServiceImpl;

	private static final String DOMAIN="一般问题";
	
	private static final String SPECIAL ="special_";
	
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
			String intentIds=item.getIntentId();
			if(org.apache.commons.lang.StringUtils.isNotBlank(intentIds)) {
				String intentId=intentIds.split(",")[0].trim();
				if(intentId!=null){
					item.setIntentId(intentId);
					String keywords=mapper.queryKeywordsByIntentId(intentId);
					if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)) {
						
						List<String> keywordList = BotSentenceUtil.getKeywords(keywords);
						if(null != keywordList && keywordList.size() > 0) {
							item.setKeyWords(keywordList.get(0).replace("\"", ""));
						}
						/*JSONArray jsonArray = JSON.parseArray(keywords);
						String[] keyArray=new String[jsonArray.size()];
						jsonArray.toArray(keyArray);
						keywords=joinString(keyArray);
						item.setKeyWords(keywords);*/
						
						
					}
				}else {
					item.setIntentId(null);
				}
			}
			
			

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
	public void addBusinessAnswer(BusinessAnswerVo record,Long userId) {

		
		String replaceKeyWords = record.getKeyWords().replaceAll("，", ",");
		replaceKeyWords = replaceKeyWords.replace("\n", "");
		String[] keys=replaceKeyWords.split(",");
		
		
		BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(record.getProcessId());
		
		//与除主流程之外的所有关键字去重
		List<Long> intentIds = new ArrayList<>();
		
		BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
		branchexample.createCriteria().andProcessIdEqualTo(record.getProcessId()).andIsShowEqualTo("1");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchexample);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				String intents = branch.getIntents();
				if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(int i = 0 ; i < array.length ; i++) {
						intentIds.add(new Long(array[i]));
					}
				}
			}
		}
		
		//获取解释开场白的关键字
		BotSentenceBranch explainBranch = botSentenceProcessService.getStartExplainBranch(record.getProcessId());
		if(null != explainBranch) {
			String intents = explainBranch.getIntents();
			if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
				String [] array = intents.split(",");
				for(int i = 0 ; i < array.length ; i++) {
					intentIds.add(new Long(array[i]));
				}
			}
		}
		
		
		Map<String, String> keywords = botSentenceProcessService.getAllMainFlowKeywords(record.getProcessId(), intentIds);
		
		//获取select.json的关键词
		/*BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
		intentExample.createCriteria().andProcessIdEqualTo(record.getProcessId()).andForSelectEqualTo(1);
		List<BotSentenceIntent> intentList = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
		
		Map<String, String> selectKeywords = new HashMap<>();
		
		if(null != intentList && intentList.size() > 0) {
			for(BotSentenceIntent intent : intentList) {
				String[] select_keyword_array = BotSentenceUtil.getKeywords(intent.getKeywords()).get(0).replace("\"", "").split(",");
				for(int i = 0 ; i < select_keyword_array.length ; i++) {
					selectKeywords.put(select_keyword_array[i], intent.getDomainName());
				}
			}
		}*/
		
		List<List<String>> simList = BotSentenceUtil.getSimtxtKeywordsList(addition.getSimTxt());
		Map<String, String> simLineMap = BotSentenceUtil.getSimtxtKeywordsByKeyword(simList, keys);//匹配到相似词库列表
		
		String message = "";
		
		for(int j = 0 ; j < keys.length ; j++) {
			//校验关键字是否重复
			if(keywords.containsKey(keys[j])) {
				String repeat = "【" + keys[j] + "】 与 【" + keywords.get(keys[j]) + "】的关键字重复了";
				message = message + repeat + "<br/>";
			}
			
			
			//与相似词库去重
			/*List<String> simKeywords = BotSentenceUtil.getSimtxtKeywords(addition.getSimTxt());
			if(simKeywords.contains(keys[j])) {
				String repeat = "【" + keys[j] + "】 与相似词库的关键字重复了";
				message = message + repeat + "<br/>";
			}*/
		}
		
		//与相似词库校验
		Set<String> set = keywords.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String next = iterator.next();
			if(org.apache.commons.lang.StringUtils.isNotBlank(next.trim())) {
				if(simLineMap.containsKey(next)) {
					String repeat = "【" + simLineMap.get(next) + "】与 "+keywords.get(next)+"的关键词【"+next+"】的相似词重复了";
					message = message + repeat + "<br/>";
				}
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(message)) {
			throw new CommonException(message);
		}
		
		String keysString=JSONObject.toJSONString(keys);
		
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(record.getProcessId());
		
		//获取branchName
		int branchNo =mapper.getLastbranchNo(record.getProcessId());
		String branchName = SPECIAL+branchNo;

		//插入意图
		BotSentenceIntent intent=new BotSentenceIntent();
		intent.setKeywords(keysString);
		intent.setProcessId(record.getProcessId());
		intent.setTemplateId(process.getTemplateId());
		intent.setIndustry(process.getIndustry());
		intent.setForSelect(0);
		intent.setName("trade_" + process.getTemplateId().split("_")[0] + "_" + DOMAIN + "_" + branchName);
		intent.setCrtTime(new Date(System.currentTimeMillis()));
		intent.setCrtUser(userId.toString());
		botSentenceIntentMapper.insertSelective(intent);

		//插入音频信息
		VoliceInfo voliceInfo=new VoliceInfo();
		voliceInfo.setContent(record.getContent().replace("\n", "").trim());
		voliceInfo.setProcessId(record.getProcessId());
		voliceInfo.setTemplateId(process.getTemplateId());
		voliceInfo.setType("00");
		voliceInfo.setDomainName(DOMAIN);
		voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
		voliceInfo.setCrtUser(userId.toString());
		voliceInfo.setFlag("【新增】");
		//voliceInfoMapper.insertSelective(voliceInfo);
		voliceServiceImpl.saveVoliceInfo(voliceInfo,userId);

		//插入流程信息
		
		BotSentenceBranch branch=new BotSentenceBranch();
		branch.setProcessId(record.getProcessId());
		branch.setIntents(intent.getId().toString());
		branch.setResponse("["+voliceInfo.getVoliceId()+"]");
		branch.setUserAsk(record.getUserAsk());
		branch.setDomain(DOMAIN);
		branch.setNext(DOMAIN);
		branch.setEnd("邀约");
		branch.setTemplateId(process.getTemplateId());
		branch.setBranchName(branchName);
		branch.setCrtTime(new Date(System.currentTimeMillis()));
		branch.setCrtUser(userId.toString());
		botSentenceBranchMapper.insertSelective(branch);
		
		//更新意图表--一般问题forselect=1的数据
		BotSentenceIntentExample example = new BotSentenceIntentExample();
		example.createCriteria().andProcessIdEqualTo(record.getProcessId()).andDomainNameEqualTo(DOMAIN).andForSelectEqualTo(1);
		List<BotSentenceIntent> list = botSentenceIntentMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			BotSentenceIntent forselect_intent = list.get(0);
			String forselect_keywords = forselect_intent.getKeywords();
			
			//for(int i = 0 ; i < keys.length ; i++) {
				forselect_keywords = addKeywords(replaceKeyWords, forselect_keywords);
			//}
			
				forselect_keywords = forselect_keywords.replace("\n", "");//替换换行符
			forselect_intent.setKeywords(forselect_keywords);
			forselect_intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
			forselect_intent.setLstUpdateUser(userId.toString());
			botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(forselect_intent);
		}
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(record.getProcessId(),userId);
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
		
		
		//String left_select_keyword = new_select_keyword_list.toString().trim();
		if(org.apache.commons.lang.StringUtils.isNotBlank(yuliuSelectKeyWord)) {
			forselect_keywords = left_select_keyword + "," + yuliuSelectKeyWord;
		}else {
			forselect_keywords = left_select_keyword;
		}
		forselect_keywords = "[" + forselect_keywords + "]";
		
		/*if(forselect_keywords.indexOf("[\"" + keyword + "\"") > 0) {
			forselect_keywords = forselect_keywords.replace("[\"" + keyword + "\"", "[");
			logger.info("删除select关键词: " + keyword);
		}
		
		if(forselect_keywords.indexOf(",\"" + keyword + "\",") > 0) {
			forselect_keywords = forselect_keywords.replace(",\"" + keyword + "\",", ",");
			logger.info("删除select关键词: " + keyword);
		}
		
		if(forselect_keywords.indexOf(",\"" + keyword + "\"]") > 0) {
			forselect_keywords = forselect_keywords.replace(",\"" + keyword + "\"]", "]");
			logger.info("删除select关键词: " + keyword);
		}*/
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
	public void delBusinessAnswer(String branchId,Long userId) {
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
					forselect_intent.setLstUpdateUser(userId.toString());
					botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(forselect_intent);
				}
				
				//删除相似词库
				/*BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(branch.getProcessId());
				List<String> simKeywords = BotSentenceUtil.getSimtxtKeywords(addition.getSimTxt());
				List<String> deleteSimKeywords = new ArrayList<>();
				if(null != intent.getKeywords() && intent.getKeywords().length() > 2) {
					String intent_keywords = intent.getKeywords().replaceAll("\"", "");
					String []keywords_array = intent_keywords.substring(1,intent_keywords.length()-1).split(",");
					for(int j = 0 ; j < keywords_array.length ; j++) {
						if(simKeywords.contains(keywords_array[j])) {
							logger.info("当前关键字【" + keywords_array[j] + "】在相似词库存在，则需要删除相似词库当前行");
							deleteSimKeywords.add(keywords_array[j]);
						}
					}
				}
				String newSimTxt = BotSentenceUtil.deleteSimtxtKeywords(addition.getSimTxt(), deleteSimKeywords);
				addition.setSimTxt(newSimTxt);
				botSentenceAdditionMapper.updateByPrimaryKeyWithBLOBs(addition);*/
				
				
			}
		}
		logger.info("删除branch一般问题: " + branchId);
		botSentenceBranchMapper.deleteByPrimaryKey(branchId);
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(branch.getProcessId(),userId);
	}

	@Override
	@Transactional
	public void updateBusinessAnswer(BusinessAnswerVo record,Long userId){
		if(org.apache.commons.lang.StringUtils.isBlank(record.getProcessId()) || 
				org.apache.commons.lang.StringUtils.isBlank(record.getBranchId()) ||
				org.apache.commons.lang.StringUtils.isBlank(record.getVoliceId()) ||
				org.apache.commons.lang.StringUtils.isBlank(record.getIntentId()) ||
				org.apache.commons.lang.StringUtils.isBlank(record.getKeyWords()) ||
				org.apache.commons.lang.StringUtils.isBlank(record.getContent())) {
			throw new CommonException("更新失败,请求参数不完整!");
		}
		
		String replaceKeyWords = record.getKeyWords().replaceAll("，", ",");
		replaceKeyWords = replaceKeyWords.replace("\n", "");
		replaceKeyWords = replaceKeyWords.trim();
		String[] keys=replaceKeyWords.split(",");
		String keysString=JSONObject.toJSONString(keys);
		keysString  = keysString.substring(1, keysString.length()-1);


		//更新意图
		BotSentenceIntent intent=botSentenceIntentMapper.selectByPrimaryKey(new Long(record.getIntentId()));
		String oldKeyWord = intent.getKeywords();
		
		
		//校验关键字
		List<Long> intentIds = new ArrayList<>();
		intentIds.add(new Long(intent.getId()));
		
		BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(record.getProcessId());
		
		//与除主流程之外的所有关键字去重
		BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
		branchexample.createCriteria().andProcessIdEqualTo(record.getProcessId()).andIsShowEqualTo("1");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchexample);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				String intents = branch.getIntents();
				if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
					String [] array = intents.split(",");
					for(int i = 0 ; i < array.length ; i++) {
						intentIds.add(new Long(array[i]));
					}
				}
			}
		}
		
		
		//获取解释开场白的关键字
		BotSentenceBranch explainBranch = botSentenceProcessService.getStartExplainBranch(record.getProcessId());
		if(null != explainBranch) {
			String intents = explainBranch.getIntents();
			if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
				String [] array = intents.split(",");
				for(int i = 0 ; i < array.length ; i++) {
					intentIds.add(new Long(array[i]));
				}
			}
		}
		
		
		Map<String, String>  keywords = botSentenceProcessService.getAllMainFlowKeywords(record.getProcessId(), intentIds);
		
		List<List<String>> simList = BotSentenceUtil.getSimtxtKeywordsList(addition.getSimTxt());
		Map<String, String> simLineMap = BotSentenceUtil.getSimtxtKeywordsByKeyword(simList, keys);//匹配到相似词库列表
		String message = "";
		
		for(int j = 0 ; j < keys.length ; j++) {
			//校验关键字是否重复
			if(keywords.containsKey(keys[j])) {
				String repeat = "【" + keys[j] + "】 与 【" + keywords.get(keys[j]) + "】的关键字重复了";
				message = message + repeat + "<br/>";
				//throw new CommonException("关键字【" + keywords_array[j] + "】重复");
			}
		}
		
		//与相似词库校验
		Set<String> set = keywords.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String next = iterator.next();
			if(org.apache.commons.lang.StringUtils.isNotBlank(next.trim())) {
				if(simLineMap.containsKey(next)) {
					String repeat = "【" + simLineMap.get(next) + "】与 "+keywords.get(next)+"的关键词【"+next+"】的相似词重复了";
					message = message + repeat + "<br/>";
				}
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(message)) {
			throw new CommonException(message);
		}
		
		List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
		if(null != keywordList && keywordList.size() > 0 && org.apache.commons.lang.StringUtils.isNotBlank(keywordList.get(1))) {
			intent.setKeywords("[" + keysString + "," + keywordList.get(1).replace("\n", "") + "]");
		}else {
			intent.setKeywords("[" + keysString + "]");
		}
		intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
		intent.setLstUpdateUser(userId.toString());
		botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);

		//更新音频信息
		VoliceInfo voliceInfo=voliceInfoMapper.selectByPrimaryKey(new Long(record.getVoliceId()));
		if(!voliceInfo.getContent().equals(record.getContent()) && !"【新增】".equals(voliceInfo.getFlag())) {
			voliceInfo.setFlag("【修改】");
		}
		voliceInfo.setContent(record.getContent().replace("\n", "").trim());
		voliceInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
		voliceInfo.setLstUpdateUser(userId.toString());
		logger.info("当前flag: " + voliceInfo.getFlag());
		
		//voliceInfoMapper.updateByPrimaryKey(voliceInfo);
		voliceServiceImpl.saveVoliceInfo(voliceInfo,userId);

		//插入流程信息
		BotSentenceBranch branch=new BotSentenceBranch();
		branch.setUserAsk(record.getUserAsk());
		branch.setBranchId(record.getBranchId());
		branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		branch.setLstUpdateUser(userId.toString());
		botSentenceBranchMapper.updateByPrimaryKeySelective(branch);
		
		//更新意图表--一般问题forselect=1的数据
		BotSentenceIntentExample example = new BotSentenceIntentExample();
		example.createCriteria().andProcessIdEqualTo(record.getProcessId()).andDomainNameEqualTo(DOMAIN).andForSelectEqualTo(1);
		List<BotSentenceIntent> list = botSentenceIntentMapper.selectByExampleWithBLOBs(example);
		if(null != list && list.size() > 0) {
			BotSentenceIntent forselect_intent = list.get(0);
			String forselect_keywords = forselect_intent.getKeywords();
			
			
			
			
			
			
			String newKeywords = deleteKeywords(oldKeyWord, forselect_keywords);
			newKeywords = addKeywords(replaceKeyWords, newKeywords);
			
			
			
			
			
			
			
			
			
			
			//先删除原来意图对应的关键词
			/*String[] old_keyword_array = BotSentenceUtil.getKeywords(oldKeyWord).get(0).replace("\"", "").split(",");
			for(int i = 0 ; i < old_keyword_array.length ; i++) {
				forselect_keywords = deleteKeywords(old_keyword_array[i], forselect_keywords);
			}*/
			
			
			//新增更新后的关键词
			/*for(int i = 0 ; i < keys.length ; i++) {
				forselect_keywords = addKeywords(keys[i], forselect_keywords);
			}*/
			
			forselect_intent.setKeywords(newKeywords);
			forselect_intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
			forselect_intent.setLstUpdateUser(userId.toString());
			botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(forselect_intent);
		}
		
		//更新话术流程状态
		botSentenceProcessService.updateProcessState(record.getProcessId(),userId);

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
}
