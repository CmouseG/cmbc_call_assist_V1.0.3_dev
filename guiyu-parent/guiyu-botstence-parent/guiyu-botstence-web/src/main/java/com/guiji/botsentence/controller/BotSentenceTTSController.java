package com.guiji.botsentence.controller;

import com.google.common.collect.Lists;
import com.guiji.botsentence.dao.entity.BotSentenceTtsParam;
import com.guiji.botsentence.service.IBotSentenceTtsService;
import com.guiji.botsentence.service.ITtsService;
import com.guiji.botsentence.vo.*;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.result.ServerResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @Description:tts制作相关类
 * @author zhangpeng
 * @date 2018年10月30日 
 *
 */
@RestController
@RequestMapping(value="tts")
public class BotSentenceTTSController {

	@Resource
	private IBotSentenceTtsService botSentenceTtsService;

	@Resource
	private ITtsService iTtsService;
	
	@RequestMapping(value="saveTtsParam")
	public ServerResult saveTtsParam(@JsonParam TtsParamVO param, @RequestHeader String userId) {
		
		if(null == param) {
			return ServerResult.createByErrorMessage("参数为空");
		}
		
		if(StringUtils.isBlank(param.getProcessId())) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		if(null == param.getParams() || param.getParams().size() < 1) {
			return ServerResult.createByErrorMessage("参数列表为空");
		}
		
		botSentenceTtsService.saveTtsParam(param, userId);
		
		return ServerResult.createBySuccess();
	}
	
	
	@RequestMapping(value="saveTtsBackup")
	public ServerResult saveTtsBackup(@JsonParam TtsBackupVO param, @RequestHeader String userId) {
		
		if(null == param) {
			return ServerResult.createByErrorMessage("参数为空");
		}
		
		if(StringUtils.isBlank(param.getProcessId())) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		if(null == param.getBackups() || param.getBackups().size() < 1) {
			return ServerResult.createByErrorMessage("参数列表为空");
		}
		
		botSentenceTtsService.saveTtsBackup(param, userId);
		
		return ServerResult.createBySuccess();
	}

	@RequestMapping(value="saveSingleTtsBackup")
	public ServerResult saveSingleTtsBackup(@RequestBody TtsBackupReqVO ttsBackupReqVO, @RequestHeader String userId) {

		if(null == ttsBackupReqVO || StringUtils.isBlank(ttsBackupReqVO.getContent())) {
			return ServerResult.createByErrorMessage("参数为空");
		}

		if(StringUtils.isBlank(ttsBackupReqVO.getProcessId())) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}

		botSentenceTtsService.saveSingleTtsBackup(ttsBackupReqVO, userId);

		return ServerResult.createBySuccess();
	}
	
	@RequestMapping(value="queryTtsParamList")
	public ServerResult<List<TtsParam>> queryTtsParamList(@JsonParam String processId) {
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		List<TtsParam> ttsParams = Lists.newArrayList();
		List<BotSentenceTtsParam> ttsParamList = iTtsService.getSortedParams(processId);
		ttsParamList.forEach(ttsParam -> {
			TtsParam param = new TtsParam();
			param.setParamKey(ttsParam.getParamKey());
			param.setParamType(ttsParam.getParamType());
			ttsParams.add(param);
		});
		return ServerResult.createBySuccess(ttsParams);
	}
	
	@RequestMapping(value="queryTtsBackupList")
	public ServerResult<List<TtsBackup>> queryTtsBackupList(@JsonParam String processId) {
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		List<TtsBackup> list = botSentenceTtsService.queryTtsBackupList(processId);
		
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 生成TTS合成录音
	 * @param flow
	 * @return
	 */
	@RequestMapping(value="generateTTS")
	public ServerResult<FlowInfoVO> generateTTS(@JsonParam String processId, @RequestHeader String userId){
		botSentenceTtsService.generateTTS(processId, userId);
		return ServerResult.createBySuccess();
	}
	
}
