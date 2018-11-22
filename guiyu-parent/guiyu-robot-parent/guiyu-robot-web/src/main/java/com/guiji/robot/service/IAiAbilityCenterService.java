package com.guiji.robot.service;

import java.util.List;

import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;

/** 
* @ClassName: IAiAbilityCenterService 
* @Description: 机器人能力中心服务 
* @date 2018年11月16日 上午9:21:06 
* @version V1.0  
*/
public interface IAiAbilityCenterService {
	
	
	/**
	 * 导入任务时话术参数检查以及准备
	 * @param checkParams
	 * @return
	 */
	List<CheckResult> checkParams(List<HsParam> checkers);
	
	
	/**
	 * TTS语音合成以及返回下载地址url
	 * @param ttsVoice
	 * @return
	 */
	List<TtsVoice> fetchTtsUrls(TtsVoiceReq ttsVoiceReq);
	
	
	/**
	 * 拨打AI电话
	 * @param aiCallStartReq
	 * @return
	 */
	AiCallNext aiCallStart(AiCallStartReq aiCallStartReq);
	
	
	/**
	 * sellbot关键字匹配，预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @return
	 */
	AiCallNext aiLngKeyMatch(AiCallLngKeyMatchReq aiCallLngKeyMatchReq);
	
	
	/**
	 * 用户语音AI响应
	 * @param aiCallNextReq
	 * @return
	 */
	AiCallNext aiCallNext(AiCallNextReq aiCallNextReq);
	
	
	/**
	 * 电话挂断通知AI释放资源
	 * @param aiHangupReq
	 */
	void aiHangup(AiHangupReq aiHangupReq);
	
	
}
