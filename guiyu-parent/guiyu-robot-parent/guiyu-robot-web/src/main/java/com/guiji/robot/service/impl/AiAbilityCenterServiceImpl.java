package com.guiji.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.utils.SystemUtil;

/** 
* @ClassName: AiAbilityCenterServiceImpl 
* @Description: 机器人能力中心服务 
* @date 2018年11月16日 下午2:17:37 
* @version V1.0  
*/
@Service
public class AiAbilityCenterServiceImpl implements IAiAbilityCenterService{
	@Autowired
	ISellbotService iSellbotService;
	
	/**
	 * 导入任务时话术参数检查以及准备
	 * @param checkParams
	 * @return
	 */
	@Override
	public CheckResult checkParams(CheckParams checkParams) {
		CheckResult result = new CheckResult();
		result.setCheckMsg(true);
		return result;
	}
	
	
	/**
	 * 拨打电话前AI资源准备校验，确认是否可以发起拨打电话
	 * @param checkAiReady
	 * @return
	 */
	@Override
	public CheckResult checkAiResourceReady(CheckAiReady checkAiReady) {
		CheckResult result = new CheckResult();
		result.setCheckMsg(true);
		return result;
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
		SellbotRestoreReq sellbotRestoreReq = new SellbotRestoreReq();
		sellbotRestoreReq.setCfg(aiCallStartReq.getTemplateId());
		sellbotRestoreReq.setPhonenum(aiCallStartReq.getPhoneNo());
		sellbotRestoreReq.setSeqid(aiCallStartReq.getSeqid());
		String sellbotRsp = iSellbotService.restore(sellbotRestoreReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(SystemUtil.getBusiSerialNo(null, 32));
		aiNext.setSellbotJson(sellbotRsp);
		return null;
	}
	
	
	/**
	 * sellbot关键字匹配，预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @return
	 */
	@Override
	public AiCallNext aiLngKeyMatch(AiCallLngKeyMatchReq aiCallLngKeyMatchReq) {
		SellbotMatchReq sellbotMatchReq = new SellbotMatchReq();
		sellbotMatchReq.setSentence(aiCallLngKeyMatchReq.getSentence());
		//关键字匹配
		String sellbotRsp = iSellbotService.match(sellbotMatchReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(aiCallLngKeyMatchReq.getAiNo());
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * 用户语音AI响应
	 * @param aiCallNextReq
	 * @return
	 */
	@Override
	public AiCallNext aiCallNext(AiCallNextReq aiCallNextReq) {
		SellbotSayhelloReq sellbotSayhelloReq = new SellbotSayhelloReq();
		sellbotSayhelloReq.setSentence(aiCallNextReq.getSentence());
		String sellbotRsp = iSellbotService.sayhello(sellbotSayhelloReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(aiCallNextReq.getAiNo());
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * 电话挂断通知AI释放资源
	 * @param aiHangupReq
	 */
	@Override
	public void aiHangup(AiHangupReq aiHangupReq) {
		
	}
}
