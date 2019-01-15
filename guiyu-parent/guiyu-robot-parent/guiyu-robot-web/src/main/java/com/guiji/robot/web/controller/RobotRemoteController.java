package com.guiji.robot.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.guiji.component.result.Result;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.model.AiCallLngKeyMatchReq;
import com.guiji.robot.model.AiCallNext;
import com.guiji.robot.model.AiCallNextReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiFlowMsgPushReq;
import com.guiji.robot.model.AiHangupReq;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.robot.model.UserAiCfgBaseInfoVO;
import com.guiji.robot.model.UserAiCfgDetailVO;
import com.guiji.robot.model.UserAiCfgVO;
import com.guiji.robot.model.UserResourceCache;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.robot.util.ControllerUtil;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;

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
	IAiResourceManagerService iAiResourceManagerService;
	@Autowired
	IAiAbilityCenterService iAiAbilityCenterService;
	@Autowired
	IUserAiCfgService iUserAiCfgService;
	@Autowired
	AiNewTransService aiNewTransService;
	@Autowired
	ITtsWavService iTtsWavService;
	@Autowired
	ControllerUtil controllerUtil;
	@Autowired
	IProcessSchedule iProcessSchedule; 
	@Autowired
	AiCacheService aiCacheService;
	
	/************************1、资源服务************************/
	
	/**
	 * 拨打参数完整性校验
	 * @param checkParams
	 * @return
	 */
	public Result.ReturnData<List<CheckResult>> checkParams(@RequestBody CheckParamsReq checkParamsReq){
		List<CheckResult> checkResultList = iAiAbilityCenterService.checkParams(checkParamsReq);
		return Result.ok(checkResultList);
	}
	
	
	
	/************************2、能力服务************************/
	/**
	 * TTS语音下载
	 * @param ttsVoice
	 * @return
	 */
	public Result.ReturnData<List<TtsComposeCheckRsp>> ttsComposeCheck(@RequestBody List<TtsVoiceReq> ttsVoiceReqList){
		List<TtsComposeCheckRsp> rspList = iAiAbilityCenterService.ttsComposeCheck(ttsVoiceReqList);
		return Result.ok(rspList);
	}
	
	
	/**
	 * TTS语音下载
	 * @param ttsVoice
	 * @return
	 */
	public Result.ReturnData<List<TtsVoice>> ttsCompose(@RequestBody TtsVoiceReq ttsVoiceReq){
		TtsComposeCheckRsp rsp = iAiAbilityCenterService.fetchTtsUrls(ttsVoiceReq);
		List<TtsVoice> list = rsp.getTtsVoiceList();
		return Result.ok(list);
	}
	
	/**
	 * 机器人资源申请（准备拨打电话）
	 * @param aiCallStartReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallApply(@RequestBody AiCallApplyReq aiCallApplyReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallApply(aiCallApplyReq);
		return Result.ok(aiCallNext);
	}
	
	
	@PostMapping(value = "/remote/flowMsgPush")
	public Result.ReturnData flowMsgPush(@RequestBody AiFlowMsgPushReq aiFlowMsgPushReq){
		iAiAbilityCenterService.flowMsgPush(aiFlowMsgPushReq);
		return Result.ok();
	}
	
	
	/**
	 * 拨打AI电话
	 * @param aiCallStartReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallStart(@RequestBody AiCallStartReq aiCallStartReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallStart(aiCallStartReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * sellbot关键字匹配
	 * 预校验下是否命中了关键字，命中后调用方再调aiCallNext，减轻主流程压力
	 * @param aiCallLngKeyMatchReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiLngKeyMatch(@RequestBody AiCallLngKeyMatchReq aiCallLngKeyMatchReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiLngKeyMatch(aiCallLngKeyMatchReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * 用户语音AI响应
	 * @param aiCallNextReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallNext(@RequestBody AiCallNextReq aiCallNextReq){
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallNext(aiCallNextReq);
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * 挂断电话释放资源
	 * @param aiHangupReq
	 * @return
	 */
	public Result.ReturnData aiHangup(@RequestBody AiHangupReq aiHangupReq){
		iAiAbilityCenterService.aiHangup(aiHangupReq);
		return Result.ok();
	}
	
	
	/**
	 * 查询用户机器人配置基本信息
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public Result.ReturnData<UserAiCfgBaseInfoVO> queryCustBaseAccount(@RequestParam(value="userId",required=true)String userId){
		if(StrUtils.isEmpty(userId)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		//查询用户机器人配置基本信息
		UserAiCfgBaseInfo userAiCfgBaseInfo = iUserAiCfgService.queryUserAiCfgBaseInfoByUserId(userId);
		if(userAiCfgBaseInfo != null) {
			UserAiCfgBaseInfoVO vo = new UserAiCfgBaseInfoVO();
			BeanUtil.copyProperties(userAiCfgBaseInfo, vo);
			return Result.ok(vo);
		}
		return Result.ok();
	}
	
	
	/**
	 * 查询用户机器人拆分详情
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public Result.ReturnData<UserAiCfgVO> queryCustAccount(@RequestParam(value="userId",required=true)String userId){
		if(StrUtils.isEmpty(userId)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		UserAiCfgVO userAiCfgVO = new UserAiCfgVO();
		//查询用户机器人配置基本信息
		UserAiCfgBaseInfo userAiCfgBaseInfo = iUserAiCfgService.queryUserAiCfgBaseInfoByUserId(userId);
		//查询用户机器人配置分配详情
		List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
		if(userAiCfgBaseInfo != null) {
			userAiCfgVO.setUserId(userId);
			userAiCfgVO.setAiTotalNum(userAiCfgBaseInfo.getAiTotalNum());
		}
		if(ListUtil.isNotEmpty(list)) {
			List<UserAiCfgDetailVO> userDetailList = controllerUtil.changeUserAiCfg2VO(list);
			userAiCfgVO.setUserAiCfgDetailList(userDetailList);
		}
		return Result.ok(userAiCfgVO);
	}
	
	/**
	 * TTS合成后的回调服务
	 * @param ttsCallbackList
	 * @return
	 */
	public Result.ReturnData ttsCallback(@RequestBody List<TtsCallback> ttsCallbackList){
		if(ListUtil.isNotEmpty(ttsCallbackList)) {
			logger.info("接收TTS回调，共计:{}条数据",ttsCallbackList.size());
			for(TtsCallback ttsCallback : ttsCallbackList) {
				TtsCallbackHis ttsCallbackHis = new TtsCallbackHis();
				//拷贝基本属性
				BeanUtil.copyProperties(ttsCallback, ttsCallbackHis);
				if(ttsCallback.getAudios()!=null) {
					//将消息转未JSON报文
					String jsonData = JSON.toJSONString(ttsCallback.getAudios());
					ttsCallbackHis.setTtsJsonData(jsonData);
				}
				//新开事务保存
				aiNewTransService.recordTtsCallback(ttsCallbackHis);
			}
			logger.info("接收TTS回调，数据落地完成..");
			//异步处理TTS数据
			iTtsWavService.asynTtsCallback(ttsCallbackList);
		}
		return Result.ok();
	}

	/**
	 * 查询机器人资源数量（sellbot）
	 */
	@Override
	public Result.ReturnData<Integer> queryRobotResNum(){
		return iProcessSchedule.sellbotCount();
	}
	
	
	/**
	 * 重新加载机器人资源
	 */
	@Override
	public Result.ReturnData<Integer> reloadSellbot(){
		iAiResourceManagerService.reloadSellbotResource();
		return Result.ok();
	}
	
	
	/**
	 * 查询用户机器人配置信息
	 */
	@Override
	public Result.ReturnData<UserResourceCache> queryUserResourceCache(@RequestParam(value="userId",required=true) String userId){
		if(StrUtils.isNotEmpty(userId)) {
			UserResourceCache userResourceCache = aiCacheService.getUserResource(userId);
			return Result.ok(userResourceCache);
		}
		return Result.ok();
	}
}
