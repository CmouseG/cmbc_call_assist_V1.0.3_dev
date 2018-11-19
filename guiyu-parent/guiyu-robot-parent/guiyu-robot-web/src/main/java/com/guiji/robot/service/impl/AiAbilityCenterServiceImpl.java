package com.guiji.robot.service.impl;

import java.util.List;

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
* @ClassName: AiAbilityCenterServiceImpl 
* @Description: 机器人能力中心服务 
* @date 2018年11月16日 下午2:17:37 
* @version V1.0  
*/
public class AiAbilityCenterServiceImpl implements IAiAbilityCenterService{

	
	/**
	 * 导入任务时话术参数检查以及准备
	 * @param checkParams
	 * @return
	 */
	@Override
	public CheckResult checkParams(CheckParams checkParams) {
		return null;
	}
	
	
	/**
	 * 拨打电话前AI资源准备校验，确认是否可以发起拨打电话
	 * @param checkAiReady
	 * @return
	 */
	@Override
	public CheckResult checkAiResourceReady(CheckAiReady checkAiReady) {
		return null;
	}
	
	
	/**
	 * TTS语音合成以及返回下载地址url
	 * @param ttsVoice
	 * @return
	 */
	@Override
	public List<TtsVoice> ttsCompose(TtsVoice ttsVoice){
		return null;
	}
	
	
	/**
	 * 拨打AI电话
	 * @param aiCallStartReq
	 * @return
	 */
	@Override
	public AiCallNext aiCallStart(AiCallStartReq aiCallStartReq) {
		return null;
	}
	
	
	/**
	 * sellbot关键字匹配，预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @return
	 */
	@Override
	public AiCallNext aiLngKeyMatch(AiCallLngKeyMatchReq aiCallLngKeyMatchReq) {
		return null;
	}
	
	
	/**
	 * 用户语音AI响应
	 * @param aiCallNextReq
	 * @return
	 */
	@Override
	public AiCallNext aiCallNext(AiCallNextReq aiCallNextReq) {
		return null;
	}
	
	
	/**
	 * 电话挂断通知AI释放资源
	 * @param aiHangupReq
	 */
	@Override
	public void aiHangup(AiHangupReq aiHangupReq) {

	}
}
