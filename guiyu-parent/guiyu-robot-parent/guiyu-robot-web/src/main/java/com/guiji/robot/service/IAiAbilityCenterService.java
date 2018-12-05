package com.guiji.robot.service;

import java.util.List;


import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsComposeCheckRsp;
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
	 * @param checkParamsReq
	 * @return
	 */
	List<CheckResult> checkParams(CheckParamsReq checkParamsReq);
	
	
	/**
	 * TTS语音合成以及返回下载地址url
	 * @param ttsVoice
	 * @return
	 */
	TtsComposeCheckRsp fetchTtsUrls(TtsVoiceReq ttsVoiceReq);
	
	
	/**
	 * 批量TTS合成下载
	 * @param ttsVoiceReqList
	 * @return
	 */
	public List<TtsComposeCheckRsp> ttsComposeCheck(List<TtsVoiceReq> ttsVoiceReqList);
	
	
	/**
	 * 机器人资源申请（准备拨打电话）
	 * 1、资源准备
	 * 	  1.1、检查用户资源变更锁，如果减少，那么返回准备失败结果，如果是资源增加了，那么确认下增加了多少，并重新获取并分配下资源。
	 *    1.2、检查用户是否满负荷在跑，如果没有满负荷，那么拉起机器人满负荷运行。
	 *    1.3、检查是否需要tts,且tts是否合成了（待定，应该呼叫中心检查语音资源，机器人能力中心检查机器人资源）
	 *    1.4、新申请的资源记录生命周期中
	 * 
	 * 2、机器人分配
	 *    从缓存拉取用户所有机器人，将状态是空闲的机器人分配给这个电话
	 *    
	 * @param aiCallStartReq
	 * @return
	 */
	public AiCallNext aiCallApply(AiCallApplyReq aiCallApplyReq);
	
	
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
