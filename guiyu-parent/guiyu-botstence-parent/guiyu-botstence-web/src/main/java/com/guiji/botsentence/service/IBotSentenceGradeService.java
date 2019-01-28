package com.guiji.botsentence.service;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceGrade;
import com.guiji.botsentence.dao.entity.BotSentenceGradeDetail;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRule;
import com.guiji.botsentence.vo.GradeEvaluateVO;

/**
 * 意向服务接口
 * @author 张朋
 *
 */
public interface IBotSentenceGradeService {

	public void saveBotSentenceGrade(BotSentenceGrade botSentenceGrade, String userId);
	
	public void saveBotSentenceGradeDetailList(List<BotSentenceGradeDetail> list, String userId);
	
	public List<BotSentenceGradeDetail> queryBotSentenceGradeDetailList(String processId);
	
	public BotSentenceGrade getBotSentenceGrade(String processId);
	
	public String generateEvaluate(BotSentenceGradeRule rule);
	
	public String generateGradeEvaluate(String processId, String gradeName);
	
	public String getIntentNameByRuleNo(String processId, String ruleNo);
	
}
