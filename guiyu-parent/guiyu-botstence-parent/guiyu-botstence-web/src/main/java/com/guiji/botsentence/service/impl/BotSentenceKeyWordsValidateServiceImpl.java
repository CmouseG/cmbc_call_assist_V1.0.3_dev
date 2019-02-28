package com.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.botsentence.dao.BotSentenceAdditionMapper;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceIntentMapper;
import com.guiji.botsentence.dao.entity.BotSentenceAddition;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceIntent;
import com.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import com.guiji.botsentence.service.IBotSentenceKeyWordsValidateService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceIntentVO;
import com.guiji.common.exception.CommonException;

/**
 * 
* @ClassName: IBotSentenceKeyWordsValidateService
* @Description: 关键词校验相关服务类
* @author: 张朋
* @date 2019年1月11日 下午4:36:02 
* @version V1.0
 */
@Service
public class BotSentenceKeyWordsValidateServiceImpl implements IBotSentenceKeyWordsValidateService {
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	/**
	 * 根据前台保存的关键词，校验业务问答 、通用对话的关键词是否重复
	 * 校验规则如下：
	 * 1、与主流程之外的所有关键词校验是否重复
	 * 2、根据关键词查询相似词库里面的关键词列表，再用获取到相似词库的每个关键词去和其它所有关键词校验 
	 */
	@Override
	public void validateBusinessAskKeywords(List<BotSentenceIntentVO> list, String processId, List<Long> ignoreIntentIds) {
		BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(processId);
		
		//根据意图列表获取所有关键词
		String[] keys = new String[] {};
		
		//获取所有关键词库对应关键词集合
		if(null != list && list.size() > 0) {
			String allKeywords = "";
			for(BotSentenceIntentVO temp : list) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(temp.getKeywords())) {
					String replaceKeyWords = temp.getKeywords().replaceAll("，", ",");
					replaceKeyWords = replaceKeyWords.replace("\n", "");
					
					allKeywords = allKeywords + replaceKeyWords + ",";
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(allKeywords)) {
				allKeywords = allKeywords.substring(0, allKeywords.length()-1);
				keys=allKeywords.split(",");
			}
		}
		
		
		//与除主流程之外的所有关键字去重
		List<Long> intentIds = new ArrayList<>();
		
		if(null != ignoreIntentIds) {
			intentIds.addAll(ignoreIntentIds);
		}
		
		//增加号码过滤不去重
		List<String> ignoredomain = new ArrayList<>();
		ignoredomain.add("号码过滤");
		ignoredomain.add("不清楚");
		ignoredomain.add("不知道");
		ignoredomain.add("未匹配");
		
		
		BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
		intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(ignoredomain);
		List<BotSentenceIntent> phoneIgnoreList = botSentenceIntentMapper.selectByExample(intentExample);
		if(null != phoneIgnoreList && phoneIgnoreList.size() > 0) {
			for(BotSentenceIntent intent : phoneIgnoreList) {
				intentIds.add(intent.getId());
			}
		}
		
		BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
		branchexample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1");
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
		/*BotSentenceBranch explainBranch = botSentenceProcessService.getStartExplainBranch(processId);
		if(null != explainBranch) {
			String intents = explainBranch.getIntents();
			if(org.apache.commons.lang.StringUtils.isNotBlank(intents)) {
				String [] array = intents.split(",");
				for(int i = 0 ; i < array.length ; i++) {
					intentIds.add(new Long(array[i]));
				}
			}
		}*/
		
		Map<String, String> keywords = botSentenceProcessService.getAllMainFlowKeywords(processId, intentIds);
		
		List<List<String>> simList = BotSentenceUtil.getSimtxtKeywordsList(addition.getSimTxt());
		
		Map<String, String> simLineMap = BotSentenceUtil.getSimtxtKeywordsByKeyword(simList, keys);//匹配到相似词库列表
		
		String message = "";
		
		//校验意图之间关键词是否重复
		if(null != list && list.size() > 0) {
			for(int i = 0 ; i < list.size() - 1 ; i++) {
				//获取关键词
				BotSentenceIntentVO vo1 = list.get(i);
				String keywords1 = vo1.getKeywords();
				if(StringUtils.isBlank(keywords1)) {
					continue;
				}
				List<String> keywordList1 = BotSentenceUtil.StringToList(keywords1);
				
				for(int j = i+1; j < list.size() ; j++) {
					//获取关键词
					BotSentenceIntentVO vo2 = list.get(j);
					String keywords2 = vo2.getKeywords();
					if(StringUtils.isBlank(keywords2)) {
						continue;
					}
					List<String> keywordList2 = BotSentenceUtil.StringToList(keywords2);
					for(String temp : keywordList2) {
						if(keywordList1.contains(temp)) {
							String repeat = "意图'"+vo1.getName()+"'与'"+vo2.getName()+"'的关键词【"+temp+"】重复";
							message = message + repeat + "<br/>";
						}
					}
				}
			}
		}
		
		
		for(int j = 0 ; j < keys.length ; j++) {
			//校验关键字是否重复
			if(keywords.containsKey(keys[j])) {
				String repeat = "【" + keys[j] + "】 与 【" + keywords.get(keys[j]) + "】的关键字重复了";
				message = message + repeat + "<br/>";
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
	}

	
	/**
	 * 校验多个意图之间的关键词重复
	 * @param list
	 */
	public String validateIntentKeywords(List<BotSentenceIntentVO> list) {
		String message = "";
		//校验意图之间关键词是否重复
		if(null != list && list.size() > 0) {
			for(int i = 0 ; i < list.size() - 1 ; i++) {
				//获取关键词
				BotSentenceIntentVO vo1 = list.get(i);
				String keywords1 = vo1.getKeywords();
				if(StringUtils.isBlank(keywords1)) {
					continue;
				}
				List<String> keywordList1 = BotSentenceUtil.StringToList(keywords1);
				
				for(int j = i+1; j < list.size() ; j++) {
					//获取关键词
					BotSentenceIntentVO vo2 = list.get(j);
					String keywords2 = vo2.getKeywords();
					if(StringUtils.isBlank(keywords2)) {
						continue;
					}
					List<String> keywordList2 = BotSentenceUtil.StringToList(keywords2);
					for(String temp : keywordList2) {
						if(keywordList1.contains(temp)) {
							String repeat = "意图'"+vo1.getName()+"'与'"+vo2.getName()+"'的关键词【"+temp+"】重复";
							message = message + repeat + "<br/>";
						}
					}
				}
			}
		}
		return message;
		
	}


	@Override
	public void validateIntents(String processId, Long intentId) {
		String message = "";
		if(null != intentId) {
				BotSentenceIntentExample example = new BotSentenceIntentExample();
				example.createCriteria().andProcessIdEqualTo(processId).andRefrenceIdEqualTo(intentId);
				List<BotSentenceIntent> exist = botSentenceIntentMapper.selectByExample(example);
				if(null != exist && exist.size() > 0) {
					message = "意图【"+exist.get(0).getName()+"】已在【"+exist.get(0).getDomainName()+"】添加";
				}
		}else {
			message = "请求参数不完整!";
		}
		if(StringUtils.isNotBlank(message)) {
			throw new CommonException(message);
		}
	}
	
}
