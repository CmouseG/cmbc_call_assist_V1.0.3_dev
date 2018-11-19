package com.guiji.robot.service.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckAiReady;
import com.guiji.robot.model.CheckParams;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.service.IAiAbilityCenterService;

/** 
* @ClassName: RobotRemoteController 
* @Description: 机器人能力中心服务
* @date 2018年11月19日 上午10:13:49 
* @version V1.0  
*/
@RestController
public class RobotRemoteController implements IRobotRemote{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IAiAbilityCenterService iAiAbilityCenterService;
	
	/************************1、资源服务************************/
	
	/**
	 * 拨打参数完整性校验
	 * @param checkParams
	 * @return
	 */
	public Result.ReturnData<List<CheckResult>> checkParams(CheckParams checkParams){
		List<CheckResult> checkResultList = iAiAbilityCenterService.checkParams(checkParams);
		return Result.ok(checkResultList);
	}
	
	
	
	/************************2、能力服务************************/
	/**
	 * TTS语音下载
	 * @param ttsVoice
	 * @return
	 */
	public Result.ReturnData<List<TtsVoice>> ttsCompose(TtsVoice ttsVoice){
		List<TtsVoice> list = iAiAbilityCenterService.ttsCompose(ttsVoice);
		return Result.ok(list);
	}
	
	
	/**
	 * 拨打AI电话
	 * @param aiCallStartReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallStart(AiCallStartReq aiCallStartReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallStart(aiCallStartReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * sellbot关键字匹配
	 * 预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @param aiCallLngKeyMatchReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiLngKeyMatch(AiCallLngKeyMatchReq aiCallLngKeyMatchReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiLngKeyMatch(aiCallLngKeyMatchReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * 用户语音AI响应
	 * @param aiCallNextReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallNext(AiCallNextReq aiCallNextReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallNext(aiCallNextReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * 挂断电话释放资源
	 * @param aiHangupReq
	 * @return
	 */
	public Result.ReturnData aiHangup(AiHangupReq aiHangupReq){
		iAiAbilityCenterService.aiHangup(aiHangupReq);
		return Result.ok();
	}
}
