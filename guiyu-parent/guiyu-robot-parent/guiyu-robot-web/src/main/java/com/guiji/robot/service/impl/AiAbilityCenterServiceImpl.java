package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckParams;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiResourceApply;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiAbilityCenterServiceImpl 
* @Description: 机器人能力中心服务 
* @date 2018年11月16日 下午2:17:37 
* @version V1.0  
*/
@Service
public class AiAbilityCenterServiceImpl implements IAiAbilityCenterService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ISellbotService iSellbotService;
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	
	/**
	 * 导入任务时话术参数检查以及准备
	 * @param checkParams
	 * @return
	 */
	@Override
	public List<CheckResult> checkParams(CheckParams checkParams) {
		List<CheckResult> list = new ArrayList<CheckResult>();
		CheckResult result = new CheckResult();
		result.setCheckMsg(true);
		list.add(result);
		return list;
	}
	
	
	/**
	 * TTS语音合成以及返回下载地址url
	 * @param ttsVoice
	 * @return
	 */
	@Override
	public List<TtsVoice> ttsCompose(TtsVoiceReq ttsVoiceReq){
		return null;
	}
	
	
	/**
	 * 拨打AI电话
	 * 1、资源准备
	 * 	  1.1、检查用户资源变更锁，如果减少，那么返回准备失败结果，如果是资源增加了，那么确认下增加了多少，并重新获取并分配下资源。
	 *    1.2、检查用户是否满负荷在跑，如果没有满负荷，那么拉起机器人满负荷运行。
	 *    1.3、检查是否需要tts,且tts是否合成了（待定，应该呼叫中心检查语音资源，机器人能力中心检查机器人资源）
	 *    1.4、新申请的资源记录生命周期中
	 * 
	 * 2、机器人分配
	 *    从缓存拉取用户所有机器人，将状态是空闲的机器人分配给这个电话
	 * 3、调用sellbot拨打电话   
	 *    
	 * @param aiCallStartReq
	 * @return
	 */
	@Override
	public AiCallNext aiCallStart(AiCallStartReq aiCallStartReq) {
		/**1、请求参数校验 **/
		if(aiCallStartReq == null
				|| StrUtils.isEmpty(aiCallStartReq.getSeqid())
				|| StrUtils.isEmpty(aiCallStartReq.getPhoneNo())
				|| StrUtils.isEmpty(aiCallStartReq.getSeqid())
				|| StrUtils.isEmpty(aiCallStartReq.getTemplateId())
				|| StrUtils.isEmpty(aiCallStartReq.getUserId())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		String userId = aiCallStartReq.getUserId();
		/**2、资源校验以及准备**/
		UserResourceCache userResourceCache = aiCacheService.queryUserResource(userId);
		if(userResourceCache == null) {
			//用户无机器人资源
			throw new RobotException(AiErrorEnum.AI00060003.getErrorCode(),AiErrorEnum.AI00060003.getErrorMsg());
		}
		if(StrUtils.isNotEmpty(userResourceCache.getChgStatus())){
			if(RobotConstants.USER_CHG_STATUS_A.equals(userResourceCache.getChgStatus())) {
				//用户资源增加
				//查询用户已分配的机器人
				List<AiInuseCache> userAiInuseList = aiCacheService.queryUserAiInUseList(userId);
				//本次需要申请的机器人数量
				int addAiNum = 0;
				if(ListUtil.isNotEmpty(userAiInuseList)) {
					//目前用户总机器人数量-已分配的机器人数量
					addAiNum = userResourceCache.getAiNum() - userAiInuseList.size();
				}else {
					addAiNum = userResourceCache.getAiNum();
				}
				if(addAiNum >0) {
					//本次需要分配的机器人数量>0，调用进程管理重新分配机器人
					AiResourceApply aiResourceApply = new AiResourceApply();
					aiResourceApply.setUserId(userId);
					aiResourceApply.setAiNum(addAiNum);
					iAiResourceManagerService.aiAssign(aiResourceApply);
				}
			}else if(RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())){
				//用户资源减少
				throw new RobotException(AiErrorEnum.AI00060004.getErrorCode(),AiErrorEnum.AI00060004.getErrorMsg());
			}else {
				//用户资源没有变化
			}
		}
		/**3、机器人分配 **/
		AiInuseCache nowAi = null;  //本次要分配的机器人
		List<AiInuseCache> userAiInuseList = aiCacheService.queryUserAiInUseList(userId);
		if(ListUtil.isNotEmpty(userAiInuseList)) {
			for(AiInuseCache aiInuseCache : userAiInuseList){
				//检查要是空闲的机器人
				if(RobotConstants.AI_STATUS_F.equals(aiInuseCache.getAiStatus())) {
					//检查要模板匹配
					if(aiInuseCache.getTemplateIds().contains(aiCallStartReq.getTemplateId())) {
						//机器人空闲，且可以拨打该模板，那么分配开始拨打
						nowAi = aiInuseCache;
						break;
					}
				}else {
					continue;
				}
			}
		}else {
			throw new RobotException(AiErrorEnum.AI00060003.getErrorCode(),AiErrorEnum.AI00060003.getErrorMsg());
		}
		if(nowAi == null) {
			//无空闲的机器人
			throw new RobotException(AiErrorEnum.AI00060002.getErrorCode(),AiErrorEnum.AI00060002.getErrorMsg());
		}
		/**4、机器人设置为忙**/
		nowAi.setCallingPhone(aiCallStartReq.getPhoneNo()); //正在拨打的电话
		nowAi.setCallingTime(DateUtil.getCurrentTime()); //开始拨打时间
		nowAi.setCallNum(nowAi.getCallNum()+1); //拨打数量
		iAiResourceManagerService.aiBusy(nowAi);
		/**5、调用sellbot打电话 **/
		SellbotRestoreReq sellbotRestoreReq = new SellbotRestoreReq();
		sellbotRestoreReq.setCfg(aiCallStartReq.getTemplateId()); //话术模板
		sellbotRestoreReq.setPhonenum(aiCallStartReq.getPhoneNo());	//手机号
		sellbotRestoreReq.setSeqid(aiCallStartReq.getSeqid());	//会话ID
		String sellbotRsp = iSellbotService.restore(new AiBaseInfo(nowAi.getIp(),nowAi.getPort(),nowAi.getAiNo()),sellbotRestoreReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(nowAi.getAiNo());
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * sellbot关键字匹配，预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @return
	 */
	@Override
	public AiCallNext aiLngKeyMatch(AiCallLngKeyMatchReq aiCallLngKeyMatchReq) {
		if(aiCallLngKeyMatchReq == null
				|| StrUtils.isEmpty(aiCallLngKeyMatchReq.getUserId())
				|| StrUtils.isEmpty(aiCallLngKeyMatchReq.getAiNo())
				|| StrUtils.isEmpty(aiCallLngKeyMatchReq.getSentence())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		AiInuseCache nowAi = iAiResourceManagerService.queryUserAi(aiCallLngKeyMatchReq.getUserId(), aiCallLngKeyMatchReq.getAiNo());
		if(nowAi == null) {
			//机器人不存在
			throw new RobotException(AiErrorEnum.AI00060006.getErrorCode(),AiErrorEnum.AI00060006.getErrorMsg());
		}
		SellbotMatchReq sellbotMatchReq = new SellbotMatchReq();
		sellbotMatchReq.setSentence(aiCallLngKeyMatchReq.getSentence());
		//关键字匹配
		String sellbotRsp = iSellbotService.match(new AiBaseInfo(nowAi.getIp(),nowAi.getPort(),nowAi.getAiNo()),sellbotMatchReq);
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
		if(aiCallNextReq == null
				|| StrUtils.isEmpty(aiCallNextReq.getUserId())
				|| StrUtils.isEmpty(aiCallNextReq.getAiNo())
				|| StrUtils.isEmpty(aiCallNextReq.getSentence())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		AiInuseCache nowAi = iAiResourceManagerService.queryUserAi(aiCallNextReq.getUserId(), aiCallNextReq.getAiNo());
		if(nowAi == null) {
			//机器人不存在
			throw new RobotException(AiErrorEnum.AI00060006.getErrorCode(),AiErrorEnum.AI00060006.getErrorMsg());
		}
		SellbotSayhelloReq sellbotSayhelloReq = new SellbotSayhelloReq();
		sellbotSayhelloReq.setSentence(aiCallNextReq.getSentence());
		String sellbotRsp = iSellbotService.sayhello(new AiBaseInfo(nowAi.getIp(),nowAi.getPort(),nowAi.getAiNo()),sellbotSayhelloReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(aiCallNextReq.getAiNo());
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * 电话挂断通知AI释放资源
	 * 1、检查用户资源变更锁，如果减少，那么将该机器人资源释放掉
	 * 2、正常情况下，将机器人状态变更为空闲，可以接收其他电话请求
	 * @param aiHangupReq
	 */
	@Override
	public void aiHangup(AiHangupReq aiHangupReq) {
		/**1、请求参数校验 **/
		if(aiHangupReq == null
				|| StrUtils.isEmpty(aiHangupReq.getAiNo())
				|| StrUtils.isEmpty(aiHangupReq.getPhoneNo())
				|| StrUtils.isEmpty(aiHangupReq.getUserId())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		/**2、资源校验以及准备**/
		AiInuseCache nowAi = iAiResourceManagerService.queryUserAi(aiHangupReq.getUserId(), aiHangupReq.getAiNo());
		if(nowAi == null) {
			//机器人不存在
			throw new RobotException(AiErrorEnum.AI00060006.getErrorCode(),AiErrorEnum.AI00060006.getErrorMsg());
		}
		UserResourceCache userResourceCache = aiCacheService.queryUserResource(aiHangupReq.getUserId());
		if(userResourceCache !=null && StrUtils.isNotEmpty(userResourceCache.getChgStatus()) && RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())){
			//如果是用户资源减少，那么直接释放机器人
			iAiResourceManagerService.aiRelease(nowAi);
		}else {
			//正常情况下，只需要把机器人设置为空闲即可。
			iAiResourceManagerService.aiFree(nowAi);
		}
	}
	
}
