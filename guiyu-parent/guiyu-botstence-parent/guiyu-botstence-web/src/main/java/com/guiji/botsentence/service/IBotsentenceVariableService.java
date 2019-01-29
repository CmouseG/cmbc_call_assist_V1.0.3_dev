package com.guiji.botsentence.service;


import java.util.List;

import com.guiji.botsentence.controller.server.vo.DomainParamVO;
import com.guiji.botsentence.controller.server.vo.OptionsVO;
import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.BotSentenceOptionsLevel;
import com.guiji.botsentence.dao.entity.BotSentenceSurvey;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntent;
import com.guiji.botsentence.vo.BotSentenceSurveyVO;

public interface IBotsentenceVariableService {

	public void saveOptions(OptionsVO options, String userId);
	
	public BotSentenceOptions queryOptionsByProcessId(String processId);
	
	public void saveStart(String processId, String flag, boolean type, String userId);
	
	public List<BotSentenceOptionsLevel> queryUserDefineMatchOrder(String processId);
	
	public void saveSurvey(String processId, List<BotSentenceSurvey> requestSurvey, String userId);
	
	public void saveSurveyIntent(BotSentenceSurveyIntent surveyIntent, String userId);
	
	public BotSentenceSurveyIntent getSurveyIntentByName(String processId, String domain, String intentName);
	
	public List<BotSentenceSurveyVO> querySurveyByProcessId(String processId);
	
	public List<DomainParamVO> queryDomainParamList(String processId, String paramType);
	
	public void deleteSilenceVolice(String processId, String voliceId, String userId);
	
	public void deleteSurveyIntent(String processId, String surveyIntentId);

	public String generateWeightJson(String processId);
	
	public String generateStatJson(String processId);
	
	public String generateSelectJson(String processId, List<String> agentKeywordList);
	
	public String generateZaiMangJson(String processId);
	
	public String generateTouSuJson(String processId);
	
	public String generateJuJueJson(String processId);
	
	public String generateCommonJson(String processId);
}
