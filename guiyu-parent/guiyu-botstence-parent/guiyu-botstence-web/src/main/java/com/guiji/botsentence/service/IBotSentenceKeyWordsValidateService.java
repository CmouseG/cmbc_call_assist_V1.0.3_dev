package com.guiji.botsentence.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.guiji.botsentence.dao.entity.BotSentenceAddition;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
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
public interface IBotSentenceKeyWordsValidateService {

	
	public void validateBusinessAskKeywords(List<BotSentenceIntentVO> list, String processId, List<Long> ignoreIntentIds);
	
	public void validateIntents(String processId, Long intentId);
	
	public void validateBusinessAskKeywords2(List<BotSentenceIntentVO> list, String processId, List<Long> ignoreIntentIds);
}
