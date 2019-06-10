package com.guiji.robot.web.controller;

import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.cfg.RobotMqConfig;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.*;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.robot.util.ControllerUtil;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	@Autowired
	QueueSender queueSender;
	
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
	public Result.ReturnData<TtsComposeCheckRsp> ttsCompose(@RequestBody TtsVoiceReq ttsVoiceReq){
		TtsComposeCheckRsp rsp = iAiAbilityCenterService.fetchTtsUrls(ttsVoiceReq);
		return Result.ok(rsp);
	}
	
	/**
	 * 机器人资源申请（准备拨打电话）
	 * @param aiCallStartReq
	 * @return
	 */
	public Result.ReturnData<AiCallNext> aiCallApply(@RequestBody AiCallApplyReq aiCallApplyReq){
		logger.warn("[{},{},{},{},{}]", aiCallApplyReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallApply-in", JsonUtils.bean2Json(aiCallApplyReq));
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallApply(aiCallApplyReq);
		logger.warn("[{},{},{},{},{}]", aiCallApplyReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallApply-out", JsonUtils.bean2Json(aiCallNext));
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

		logger.warn("[{},{},{},{},{}]", aiCallStartReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallStart-in", JsonUtils.bean2Json(aiCallStartReq));
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallStart(aiCallStartReq);
		logger.warn("[{},{},{},{},{}]", aiCallStartReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallStart-out", JsonUtils.bean2Json(aiCallNext));
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
		logger.warn("[{},{},{},{},{}]", aiCallNextReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallNext-in", JsonUtils.bean2Json(aiCallNextReq));
		AiCallNext aiCallNext = iAiAbilityCenterService.aiCallNext(aiCallNextReq);
		logger.warn("[{},{},{},{},{}]", aiCallNextReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiCallNext-out", JsonUtils.bean2Json(aiCallNext));
		return Result.ok(aiCallNext);
	}
	
	
	/**
	 * 挂断电话释放资源
	 * @param aiHangupReq
	 * @return
	 */
	public Result.ReturnData<HangupRes> aiHangup(@RequestBody AiHangupReq aiHangupReq){
		logger.warn("[{},{},{},{},{}]", aiHangupReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiHangup-in", JsonUtils.bean2Json(aiHangupReq));
        HangupRes hangupRes = iAiAbilityCenterService.aiHangup(aiHangupReq);
        logger.warn("[{},{},{},{},{}]", aiHangupReq.getPhoneNo(), DateUtil.formatDatetime(new Date()), "robot", "aiHangup-out", JsonUtils.bean2Json(hangupRes));
		return Result.ok(hangupRes);
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
			ttsCallbackList.forEach(obj -> queueSender.send(RobotMqConfig.TTS_CALLBACK_QUEUE, JsonUtils.bean2Json(obj)));
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

	/**
	 * 查询用户机器人配置信息
	 */
	@Override
	public Result.ReturnData<UserResourceCacheWithVersion> queryUserResourceCacheWithVersion(@RequestParam(value="userId",required=true) String userId){
		if(StrUtils.isNotEmpty(userId)) {
			UserResourceCacheWithVersion userResourceCache = aiCacheService.getUserResourceWithVersion(userId);
			return Result.ok(userResourceCache);
		}
		return Result.ok();
	}
	
	
	/**
	 * 查询所有用户机器人配置信息
	 */
	@Override
	public Result.ReturnData<Map<String,UserResourceCache>> queryAllUserResourceCache(){
		Map<String,UserResourceCache> uerResourceMap = aiCacheService.getAllUserResources();
		return Result.ok(uerResourceMap);
	}
}
