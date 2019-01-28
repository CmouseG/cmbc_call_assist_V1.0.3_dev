package com.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.controller.server.vo.DomainParamVO;
import com.guiji.botsentence.controller.server.vo.OptionsVO;
import com.guiji.botsentence.dao.entity.BotSentenceOptions;
import com.guiji.botsentence.dao.entity.BotSentenceSurvey;
import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntent;
import com.guiji.botsentence.service.impl.BotsentenceVariableServiceImpl;
import com.guiji.botsentence.vo.BotSentenceSurveyVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.result.ServerResult;

/**
 * 
* @ClassName: BotsentenceVariableController
* @Description: 话术流程变量设置后台逻辑处理类
* @author: 张朋
* @date 2018年12月11日 上午12:00:02 
* @version V1.0
 */
@RestController
@RequestMapping(value="variable")
public class BotsentenceVariableController {

	@Autowired
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@RequestMapping(value="saveVariable")
	public ServerResult saveVariable(@JsonParam OptionsVO requestParam, @RequestHeader String userId) {
		if(null != requestParam && StringUtils.isNotBlank(requestParam.getProcessId())) {
			botsentenceVariableService.saveOptions(requestParam, userId);
			return ServerResult.createBySuccess();
		}else {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
	}
	
	@RequestMapping(value="saveSurvey")
	public ServerResult saveSurvey(@JsonParam String processId, @JsonParam List<JSONObject> requestList, @RequestHeader String userId) {
		if(StringUtils.isNotBlank(processId) && null != requestList && requestList.size() > 0) {
			
			List<BotSentenceSurvey> list = new ArrayList<>();
			for(JSONObject object : requestList) {
				BotSentenceSurvey botSentenceSurvey = JSON.toJavaObject(object, BotSentenceSurvey.class);
				list.add(botSentenceSurvey);
			}
			
			botsentenceVariableService.saveSurvey(processId, list, userId);
			return ServerResult.createBySuccess();
		}else {
			return ServerResult.createByErrorMessage("请求参数为空!");
		}
	}
	
	
	@RequestMapping(value="saveSurveyIntent")
	public ServerResult saveSurveyIntent(@JsonParam BotSentenceSurveyIntent botSentenceSurveyIntent, @RequestHeader String userId) {
		if(null != botSentenceSurveyIntent && StringUtils.isNotBlank(botSentenceSurveyIntent.getProcessId())) {
			botsentenceVariableService.saveSurveyIntent(botSentenceSurveyIntent, userId);
			return ServerResult.createBySuccess();
		}else {
			return ServerResult.createByErrorMessage("请求参数为空!");
		}
	}
	
	@RequestMapping(value="querySurveyByProcessId")
	public ServerResult<List<BotSentenceSurveyVO>> querySurveyByProcessId(@JsonParam String processId) {
		if(StringUtils.isNotBlank(processId)) {
			List<BotSentenceSurveyVO> result = botsentenceVariableService.querySurveyByProcessId(processId);
			return ServerResult.createBySuccess(result);
		}else {
			return ServerResult.createByErrorMessage("请求参数为空!");
		}
	}
	
	@RequestMapping(value="queryDomainParamList")
	public ServerResult<List<DomainParamVO>> queryDomainParamList(@JsonParam String processId, @JsonParam String paramType) {
		List<DomainParamVO> list = botsentenceVariableService.queryDomainParamList(processId, paramType);
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="queryOptionsByProcessId")
	public ServerResult<BotSentenceOptions> queryOptionsByProcessId(@JsonParam String processId) {
		BotSentenceOptions botSentenceOptions = botsentenceVariableService.queryOptionsByProcessId(processId);
		return ServerResult.createBySuccess(botSentenceOptions);
	}
	
	@RequestMapping(value="deleteSilenceVolice")
	public ServerResult deleteSilenceVolice(@JsonParam String processId, @JsonParam String voliceId, @RequestHeader String userId) {
		if(StringUtils.isNotBlank(processId) && StringUtils.isNotBlank(voliceId)) {
			botsentenceVariableService.deleteSilenceVolice(processId, voliceId, userId);
			return ServerResult.createBySuccess();
		}else {
			return ServerResult.createByErrorMessage("请求参数为空!");
		}
	}
	
	@RequestMapping(value="deleteSurveyIntent")
	public ServerResult deleteSurveyIntent(@JsonParam String processId, @JsonParam String surveyIntentId) {
		botsentenceVariableService.deleteSurveyIntent(processId, surveyIntentId);
		return ServerResult.createBySuccess(); 
	}
	
	
}
