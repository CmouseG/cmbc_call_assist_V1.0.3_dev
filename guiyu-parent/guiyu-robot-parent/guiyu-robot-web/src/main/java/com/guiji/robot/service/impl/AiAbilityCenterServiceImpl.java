package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.model.Constant;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.RobotCallHis;
import com.guiji.robot.dao.entity.TtsWavHis;
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
import com.guiji.robot.model.HsParam;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.robot.service.IAiAbilityCenterService;
import com.guiji.robot.service.IAiCycleHisService;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.AiFlowSentenceCache;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiResourceApply;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
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
	@Autowired
	ITtsWavService iTtsWavService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	AiNewTransService robotNewTransService;
	@Autowired
	IAiCycleHisService iAiCycleHisService;
	@Autowired
	AiAsynDealService aiAsynDealService;
	
	/**
	 * 导入任务时话术参数检查以及准备TTS合成
	 * @param checkParamsReq
	 * @return
	 */
	@Override
	public List<CheckResult> checkParams(CheckParamsReq checkParamsReq) {
		List<CheckResult> list = new ArrayList<CheckResult>();
		boolean isNeedResourceInit = checkParamsReq.isNeedResourceInit();	//是否需要同步准备资源
		List<HsParam> checkers = checkParamsReq.getCheckers();
		if(ListUtil.isNotEmpty(checkers)) {
			//本地异步处理的需要合成TTS的数据
			List<HsParam> localTtsDealList = new ArrayList<HsParam>();
			//列表不为空
			for(HsParam hsChecker : checkers) {
				CheckResult result = new CheckResult();
				result.setSeqid(hsChecker.getSeqid());
				result.setPass(true); //默认校验通过
				//校验下必输
				if(StrUtils.isEmpty(hsChecker.getSeqid())
						|| StrUtils.isEmpty(hsChecker.getTemplateId())) {
					result.setCheckMsg("必输项校验未通过");
					result.setPass(false); //默认不通过，参数不存在
					list.add(result);
					continue;
				}
				//逐个检查
				HsReplace hsReplace = aiCacheService.queyHsReplace(hsChecker.getTemplateId());
				if(hsReplace.isTemplate_tts_flag()) {
					if(StrUtils.isEmpty(hsChecker.getParams())) {
						//如果需要TTS，但是又没用参数，直接返回报错
						result.setCheckMsg("参数校验不通过,参数不能为空");
						result.setPass(false); //默认不通过，参数不存在
						list.add(result);
						continue;
					}
					//当前话术模板需要的参数
					String[] needParams = hsReplace.getReplace_variables_flag();
					if(needParams != null && needParams.length>0) {
						//参数个数校验以及参数赋值
						String[] params = hsChecker.getParams().split("\\|");
						if(params.length<needParams.length) {
							logger.error("会话:{}参数校验不通过,缺失参数",hsChecker.getSeqid());
							result.setCheckMsg("参数校验不通过,缺失参数");
							result.setPass(false); //默认不通过，参数不存在
							list.add(result);
							continue;
						}
						Map<String,String> paramMap = new HashMap<String,String>();
						for(int i=0;i<needParams.length;i++) {
							String key = needParams[i];	//模板中参数key
							String value = params[i];	//传过来的参数value
							paramMap.put(key, value);
						}
						hsChecker.setParamMap(paramMap);
					}
					if(isNeedResourceInit && result.isPass() && hsReplace.isTemplate_tts_flag()) {
						//1、需要初始化资源  2、参数校验通过 3、需要TTS合成
						localTtsDealList.add(hsChecker);
					}
				}else {
					logger.error("会话id：{},模板:{},不需要TTS合成，返回null",hsChecker.getSeqid(),hsChecker.getTemplateId());
					result.setCheckMsg("不需要TTS合成");
					result.setPass(true); //默认通过，调用方可以认为数据可以正常入库，做后续操作
					list.add(result);
					continue;
				}
				list.add(result);
			}
			if(ListUtil.isNotEmpty(localTtsDealList)) {
				logger.info("异步发起TTS合成,共计:{}条数据",localTtsDealList.size());
				iTtsWavService.asynTtsCompose(localTtsDealList);
			}
		}else {
			return list;
		}
		return list;
	}
	
	
	/**
	 * TTS语音合成以及返回下载地址url
	 * @param ttsVoice
	 * @return
	 */
	@Override
	public TtsComposeCheckRsp fetchTtsUrls(TtsVoiceReq ttsVoiceReq){
		if(ttsVoiceReq == null
				|| StrUtils.isEmpty(ttsVoiceReq.getTemplateId())
				|| StrUtils.isEmpty(ttsVoiceReq.getSeqid())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		TtsComposeCheckRsp rsp = new TtsComposeCheckRsp();
		rsp.setSeqId(ttsVoiceReq.getSeqid());
		//根据会话ID查找语音的TTS合成结果
		TtsWavHis ttsWavHis = iTtsWavService.queryTtsWavBySeqId(ttsVoiceReq.getSeqid());
		if(ttsWavHis == null) {
			logger.info("会话ID:{}TTS查不到数据",ttsVoiceReq.getSeqid());
			//逐个检查
			HsReplace hsReplace = aiCacheService.queyHsReplace(ttsVoiceReq.getTemplateId());
			if(!hsReplace.isTemplate_tts_flag()) {
				logger.info("会话ID：{}不需要TTS合成...",ttsVoiceReq.getSeqid());
				rsp.setStatus(RobotConstants.TTS_STATUS_S);
			}else {
				rsp.setStatus(RobotConstants.TTS_STATUS_N);
			}
		}else {
			rsp.setStatus(ttsWavHis.getStatus());
			if(RobotConstants.TTS_STATUS_P.equals(ttsWavHis.getStatus())) {
				logger.error("会话ID:{}TTS数据合成中...",ttsVoiceReq.getSeqid());
			}else if(RobotConstants.TTS_STATUS_F.equals(ttsWavHis.getStatus())) {
//				logger.error("会话ID:{}TTS数据合成失败!",ttsVoiceReq.getSeqid());
			}else {
				//查询出合成后的数据JSON
				String ttsJsonData = ttsWavHis.getTtsJsonData();
				List<TtsVoice> list = JSON.parseArray(ttsJsonData, TtsVoice.class);
				rsp.setTtsVoiceList(list);
			}
		}
		return rsp;
	}
	
	
	/**
	 * 批量TTS合成下载
	 * @param seqIdList
	 * @return
	 */
	public List<TtsComposeCheckRsp> ttsComposeCheck(List<TtsVoiceReq> ttsVoiceReqList){
		if(ListUtil.isNotEmpty(ttsVoiceReqList)) {
			List<TtsComposeCheckRsp> list = new ArrayList<TtsComposeCheckRsp>();
			for(TtsVoiceReq ttsVoiceReq : ttsVoiceReqList) {
				TtsComposeCheckRsp rsp = this.fetchTtsUrls(ttsVoiceReq);
				list.add(rsp);
			}
			return list;
		}
		return null;
	}
	
	
	/**
	 * 机器人资源申请（准备拨打电话）
	 * 每个用户资源分配高并发时会冲突，增加并发锁
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
	@Override
	@Transactional
	public AiCallNext aiCallApply(AiCallApplyReq aiCallApplyReq){
		/**1、请求参数校验 **/
		if(aiCallApplyReq == null
				|| StrUtils.isEmpty(aiCallApplyReq.getSeqId())
				|| StrUtils.isEmpty(aiCallApplyReq.getPhoneNo())
				|| StrUtils.isEmpty(aiCallApplyReq.getTemplateId())
				|| StrUtils.isEmpty(aiCallApplyReq.getUserId())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		Lock lock = new Lock(RobotConstants.LOCK_NAME_ASSIGN+aiCallApplyReq.getUserId(), RobotConstants.LOCK_NAME_ASSIGN+aiCallApplyReq.getUserId());
		if (distributedLockHandler.tryLock(lock)) {
			try {
				String userId = aiCallApplyReq.getUserId();
				/**2、资源校验以及准备**/
				UserResourceCache userResourceCache = aiCacheService.getUserResource(userId);
				if(userResourceCache == null) {
					//用户无机器人资源
					throw new RobotException(AiErrorEnum.AI00060003.getErrorCode(),AiErrorEnum.AI00060003.getErrorMsg());
				}
				//查询用户已分配的机器人
				List<AiInuseCache> userAiInuseList = aiCacheService.queryUserAiInUseList(userId);
				if(StrUtils.isNotEmpty(userResourceCache.getChgStatus())){
					if(RobotConstants.USER_CHG_STATUS_A.equals(userResourceCache.getChgStatus())) {
						//用户资源增加
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
						//新增完成后，将用户资源状态设置为正常
						userResourceCache.setChgStatus(null);
						aiCacheService.putUserResource(userResourceCache);
					}else if(RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())){
						//用户资源减少，先将现在空闲的机器人释放掉
						this.releaseFreeAi(userId,userAiInuseList);
						throw new RobotException(AiErrorEnum.AI00060004.getErrorCode(),AiErrorEnum.AI00060004.getErrorMsg());
					}
				}else {
					//用户资源没有变化
					if(userAiInuseList == null || userAiInuseList.isEmpty()) {
						//用户资源没有变化，但是如果用户现在还没有分配资源，那么重新分配机器人
						//本次需要申请的机器人数量
						int addAiNum = userResourceCache.getAiNum();
						logger.info("用户{}现在还没有初始化可用资源，重新分配{}个机器人",userResourceCache.getUserId(),addAiNum);
						if(addAiNum > 0) {
							AiResourceApply aiResourceApply = new AiResourceApply();
							aiResourceApply.setUserId(userId);
							aiResourceApply.setAiNum(addAiNum);
							iAiResourceManagerService.aiAssign(aiResourceApply);
						}
					}
				}
				/**3、机器人分配 **/
				AiInuseCache nowAi = null;  //本次要分配的机器人
				//重新查询机器人
				userAiInuseList = aiCacheService.queryUserAiInUseList(userId);
				if(ListUtil.isNotEmpty(userAiInuseList)) {
					for(AiInuseCache aiInuseCache : userAiInuseList){
						//检查要是空闲的机器人
						if(RobotConstants.AI_STATUS_F.equals(aiInuseCache.getAiStatus())) {
							//检查要模板匹配
							if(aiInuseCache.getTemplateIds().contains(aiCallApplyReq.getTemplateId())) {
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
				nowAi.setCallingPhone(aiCallApplyReq.getPhoneNo()); //正在拨打的电话
				nowAi.setCallingTime(DateUtil.getCurrentTime()); //开始拨打时间
				nowAi.setSeqId(aiCallApplyReq.getSeqId());
				nowAi.setCallNum(nowAi.getCallNum()+1); //拨打数量
				iAiResourceManagerService.aiBusy(nowAi);
				/**5、记录调用中心log**/
				aiAsynDealService.recordCallLog(aiCallApplyReq.getSeqId(), aiCallApplyReq.getPhoneNo(), Constant.MODULAR_STATUS_END, "机器人申请成功,机器人编号:"+nowAi.getAiNo());
				/**6、返回**/
				AiCallNext aiCallNext = new AiCallNext();
				aiCallNext.setAiNo(nowAi.getAiNo());
				return aiCallNext;
			} catch (RobotException e) {
				//记录log
				aiAsynDealService.recordCallLog(aiCallApplyReq.getSeqId(), aiCallApplyReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "机器人申请失败,"+e.getErrorMessage());
				throw e; 
			} catch (Exception e1) {
				//记录log
				aiAsynDealService.recordCallLog(aiCallApplyReq.getSeqId(), aiCallApplyReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "机器人申请失败,未知异常");
				logger.error("机器人分配异常！",e1);
				throw new RobotException(AiErrorEnum.AI00060028.getErrorCode(),AiErrorEnum.AI00060028.getErrorMsg());
			}finally {
				//释放锁
				distributedLockHandler.releaseLock(lock);
			}
		}else {
			logger.warn("用户机器人配置变更未能获取锁！！！");
			throw new RobotException(AiErrorEnum.AI00060027.getErrorCode(),AiErrorEnum.AI00060027.getErrorMsg());
		}
	}
	
	
	/**
	 * 拨打AI电话
	 * 1、请求基本信息校验
	 * 2、机器人基本信息检查
	 * 3、调用sellbot拨打电话  
	 *    
	 * @param aiCallStartReq
	 * @return
	 */
	@Override
	@Transactional
	public AiCallNext aiCallStart(AiCallStartReq aiCallStartReq) {
		/**1、请求参数校验 **/
		if(aiCallStartReq == null
				|| StrUtils.isEmpty(aiCallStartReq.getSeqId())
				|| StrUtils.isEmpty(aiCallStartReq.getAiNo())
				|| StrUtils.isEmpty(aiCallStartReq.getPhoneNo())
				|| StrUtils.isEmpty(aiCallStartReq.getTemplateId())
				|| StrUtils.isEmpty(aiCallStartReq.getUserId())) {
			//记录log
			aiAsynDealService.recordCallLog(aiCallStartReq.getSeqId(), aiCallStartReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "开始拨打电话,必输参数校验失败!");
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		String userId = aiCallStartReq.getUserId();
		//根据参数查询本次要调用的机器人
		AiInuseCache nowAi = aiCacheService.queryAiInuse(userId, aiCallStartReq.getAiNo());
		if(nowAi == null) {
			//记录log
			aiAsynDealService.recordCallLog(aiCallStartReq.getSeqId(), aiCallStartReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "开始拨打电话,用户无该模板空闲的机器人!");
			//无空闲的机器人
			throw new RobotException(AiErrorEnum.AI00060002.getErrorCode(),AiErrorEnum.AI00060002.getErrorMsg());
		}
		if(!aiCallStartReq.getPhoneNo().equals(nowAi.getCallingPhone())) {
			//机器人已经分配给其他号码
			logger.error("机器人{}已经分配给{}，{}不可拨打，详细信息：{}!",nowAi.getAiNo(),nowAi.getCallingPhone(),aiCallStartReq.getPhoneNo(),nowAi);
			throw new RobotException(AiErrorEnum.AI00060025.getErrorCode(),AiErrorEnum.AI00060025.getErrorMsg());
		}
		try {
			/**5、调用sellbot打电话 **/
			SellbotRestoreReq sellbotRestoreReq = new SellbotRestoreReq();
			//获取话术模板配置文件
			HsReplace hsReplace = aiCacheService.queyHsReplace(aiCallStartReq.getTemplateId());
			if(hsReplace !=null && hsReplace.isTemplate_tts_flag()) {
				//需要TTS合成，将参数信息也发给sellbot，用来交互过程中返回完成sentence信息
				TtsWavHis ttsWavHis = iTtsWavService.queryTtsWavBySeqId(aiCallStartReq.getSeqId());
				if(ttsWavHis != null) {
					String[] replace_variables_flag = hsReplace.getReplace_variables_flag();
					//将参数设置为|分隔
					if(replace_variables_flag != null && replace_variables_flag.length>0) {
						String keyStr = "";
						for(String key:replace_variables_flag) {
							keyStr = keyStr+"|"+key;
						}
						//去掉第一个|
						keyStr = keyStr.substring(1);
						sellbotRestoreReq.setKey(keyStr);	//参数key
					}
					sellbotRestoreReq.setVal(ttsWavHis.getReqParams());	//	参数值
				}
			}
			sellbotRestoreReq.setCfg(aiCallStartReq.getTemplateId()); //话术模板
			sellbotRestoreReq.setPhonenum(aiCallStartReq.getPhoneNo());	//手机号
			sellbotRestoreReq.setSeqid(aiCallStartReq.getSeqId());	//会话ID
			String sellbotRsp = iSellbotService.restore(new AiBaseInfo(nowAi.getAiNo(),nowAi.getIp(),nowAi.getPort()),sellbotRestoreReq);
			AiCallNext aiNext = new AiCallNext();
			aiNext.setAiNo(nowAi.getAiNo());
			aiNext.setSellbotJson(sellbotRsp);
			/**6、保存一通电话记录(放最后) **/
			RobotCallHis robotCallHis = new RobotCallHis();
			robotCallHis.setUserId(userId);
			robotCallHis.setAiNo(aiCallStartReq.getAiNo());
			robotCallHis.setSeqId(aiCallStartReq.getSeqId());
			robotCallHis.setTemplateId(aiCallStartReq.getTemplateId());
			robotCallHis.setAssignTime(new Date());
			robotCallHis.setCallStatus(RobotConstants.CALLINT_STATUS_I);	//通话中
			robotNewTransService.recordRobotCallHis(robotCallHis);
			/**7、记录调用中心日志**/
			aiAsynDealService.recordCallLog(aiCallStartReq.getSeqId(), aiCallStartReq.getPhoneNo(), Constant.MODULAR_STATUS_END, "开始拨打电话,成功!");
			return aiNext;
		} catch (Exception e) {
			aiAsynDealService.recordCallLog(aiCallStartReq.getSeqId(), aiCallStartReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "开始拨打电话,发生未知异常!");
			logger.error("{}的机器人{}在拨打电话{}时调用SELLBOT接口发生异常!",aiCallStartReq.getUserId(),nowAi.getAiNo(),aiCallStartReq.getPhoneNo(),e);
			throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
		}
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
		String sellbotRsp = iSellbotService.match(new AiBaseInfo(nowAi.getAiNo(),nowAi.getIp(),nowAi.getPort()),sellbotMatchReq);
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(aiCallLngKeyMatchReq.getAiNo());
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * 软电话通讯过程中消息推送
	 * 异步处理
	 * @param aiFlowMsgPushReq
	 */
	@Async
	public void flowMsgPush(AiFlowMsgPushReq aiFlowMsgPushReq){
		if(aiFlowMsgPushReq == null
				|| StrUtils.isEmpty(aiFlowMsgPushReq.getUserId())
				|| StrUtils.isEmpty(aiFlowMsgPushReq.getAiNo())
				|| StrUtils.isEmpty(aiFlowMsgPushReq.getSeqId())
				|| StrUtils.isEmpty(aiFlowMsgPushReq.getSentence())
				|| aiFlowMsgPushReq.getTimestamp()<=0) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		//客户一句话
		String sentence = aiFlowMsgPushReq.getSentence();
		//长度为2的情况去除标点符号
		if(sentence.length()==2) {
			sentence = this.cnText(sentence);
			aiFlowMsgPushReq.setSentence(sentence);
		}
		if(StrUtils.isEmpty(aiFlowMsgPushReq.getSentence())) {
			return;
		}
		//判断是否需要噪音检测
		boolean isNeedVoiceCheckFlag = this.isNeedVoiceCheck(aiFlowMsgPushReq);
		if(isNeedVoiceCheckFlag) {
			//需要噪音检测
			//TODO
//			if("噪音") return;
		}
		//放入redis
		AiFlowSentenceCache aiFlowSentenceCache = new AiFlowSentenceCache();
		BeanUtil.copyProperties(aiFlowMsgPushReq, aiFlowSentenceCache);
		aiCacheService.putFlowSentence(aiFlowSentenceCache);
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
				|| StrUtils.isEmpty(aiCallNextReq.getStatus())) {
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		AiCallNext aiNext = new AiCallNext();
		aiNext.setAiNo(aiCallNextReq.getAiNo());
		SellbotSayhelloReq sellbotSayhelloReq = new SellbotSayhelloReq();
		AiInuseCache nowAi = iAiResourceManagerService.queryUserAi(aiCallNextReq.getUserId(), aiCallNextReq.getAiNo());
		if(nowAi == null) {
			//机器人不存在
			throw new RobotException(AiErrorEnum.AI00060006.getErrorCode(),AiErrorEnum.AI00060006.getErrorMsg());
		}
		//从缓存中获取一条长度最长的sentence同ai交互
		AiFlowSentenceCache sentenceCache = this.getMsgFlowSentence(aiCallNextReq);
		if(sentenceCache != null) {
			//先过滤掉wait的数据操作
			String waitFlag = this.flowMsgWaitDeal(sentenceCache, aiCallNextReq);
			//不管处理结果如何,从缓存中清掉这个已经用掉的数据
			aiCacheService.delSentence(sentenceCache.getSeqId(), sentenceCache.getTimestamp());
			if(RobotConstants.HELLO_STATUS_WAIT.equals(waitFlag)) {
				//如果结果是wait操作
				aiNext.setHelloStatus(RobotConstants.HELLO_STATUS_WAIT);
				return aiNext;
			}
		}else {
			logger.info("会话ID:{}没有获取到有效的流消息",aiCallNextReq.getSeqId());
			//流消息未发送,客户8S没说话,机器人要回一句
			//默认打断次数配置，后续根据 话术模板 取个性化配置
			int break_time = 8;
			if(aiCallNextReq.getWaitCnt() < break_time){
				logger.info("会话ID:{},,等待时间{},小于限制时间:{}...return wait",aiCallNextReq.getSeqId(),aiCallNextReq.getWaitCnt(),break_time);
				aiNext.setHelloStatus(RobotConstants.HELLO_STATUS_WAIT);
				return aiNext;
			}else {
				logger.info("会话ID：{},等待时间{},超过限制时间:{}",aiCallNextReq.getSeqId(),aiCallNextReq.getWaitCnt(),break_time);
				//静音超时事件
				sellbotSayhelloReq.setSilence_exceed(true);
			}
		}
		aiNext.setHelloStatus(RobotConstants.HELLO_STATUS_PLAY);	//播音
		sellbotSayhelloReq.setSentence(sentenceCache==null?null:sentenceCache.getSentence());
		String sellbotRsp = iSellbotService.sayhello(new AiBaseInfo(nowAi.getAiNo(),nowAi.getIp(),nowAi.getPort()),sellbotSayhelloReq);
		aiNext.setSellbotJson(sellbotRsp);
		return aiNext;
	}
	
	
	/**
	 * 电话挂断通知AI释放资源
	 * 1、挂断后将通话历史设置为完成
	 * 2、检查用户资源变更锁，如果减少，那么将该机器人资源释放掉
	 * 3、正常情况下，将机器人状态变更为空闲，可以接收其他电话请求
	 * 4、清空消息流缓存
	 * @param aiHangupReq
	 */
	@Override
	public void aiHangup(AiHangupReq aiHangupReq) {
		/**1、请求参数校验 **/
		if(aiHangupReq == null
				|| StrUtils.isEmpty(aiHangupReq.getSeqId())
				|| StrUtils.isEmpty(aiHangupReq.getAiNo())
				|| StrUtils.isEmpty(aiHangupReq.getPhoneNo())
				|| StrUtils.isEmpty(aiHangupReq.getUserId())) {
			//记录log
			aiAsynDealService.recordCallLog(aiHangupReq.getSeqId(), aiHangupReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "挂断电话,请求必输参数校验失败!");
			//必输校验不通过
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		/**2、将通话历史设置为完成 **/
		RobotCallHis robotCallHis = iAiCycleHisService.queryRobotCallhisBySeqId(aiHangupReq.getSeqId());
		if(robotCallHis != null) {
			robotCallHis.setCallStatus(RobotConstants.CALLING_STATUS_S); //通话完成
		}
		robotNewTransService.recordRobotCallHis(robotCallHis);
		
		/**3、资源校验以及准备**/
		AiInuseCache nowAi = iAiResourceManagerService.queryUserAi(aiHangupReq.getUserId(), aiHangupReq.getAiNo());
		if(nowAi == null) {
			//记录log
			aiAsynDealService.recordCallLog(aiHangupReq.getSeqId(), aiHangupReq.getPhoneNo(), Constant.MODULAR_STATUS_ERROR, "挂断电话,机器人"+aiHangupReq.getAiNo()+"不存在");
			//机器人不存在
			throw new RobotException(AiErrorEnum.AI00060006.getErrorCode(),AiErrorEnum.AI00060006.getErrorMsg());
		}
		UserResourceCache userResourceCache = aiCacheService.getUserResource(aiHangupReq.getUserId());
		if(userResourceCache !=null && StrUtils.isNotEmpty(userResourceCache.getChgStatus()) && RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())){
			//如果是用户资源减少，那么直接释放机器人
			iAiResourceManagerService.aiRelease(nowAi);
			//释放后再查下用户还有没有机器人了，如果没有了，就把用户机器人清空，并将用户资源状态重置为正常
			List<AiInuseCache> userAiInuseList = aiCacheService.queryUserAiInUseList(aiHangupReq.getUserId());
			if(userAiInuseList == null || userAiInuseList.isEmpty()) {
				//用户资源变更状态，设置为正常(本次变更影响已处理完)
				userResourceCache.setChgStatus(null);
				aiCacheService.putUserResource(userResourceCache);
				//将用户机器人清空
				aiCacheService.delUserAis(aiHangupReq.getUserId());
			}
		}else {
			//正常情况下，只需要把机器人设置为空闲即可。
			iAiResourceManagerService.aiFree(nowAi);
		}
		/**4、清空消息流缓存**/
		aiCacheService.delAllSentenceBySeqId(aiHangupReq.getSeqId());
		/**5、记录log **/
		aiAsynDealService.recordCallLog(aiHangupReq.getSeqId(), aiHangupReq.getPhoneNo(), Constant.MODULAR_STATUS_END, "挂断电话完成!");
	}
	
	
	/**
	 * 将用户现在是空闲的机器人释放掉
	 * @param userAiInuseList
	 */
	private void releaseFreeAi(String userId,List<AiInuseCache> userAiInuseList) {
		if(ListUtil.isNotEmpty(userAiInuseList)) {
			for(AiInuseCache ai : userAiInuseList) {
				if(RobotConstants.AI_STATUS_F.equals(ai.getAiStatus())) {
					//如果是用户资源减少，那么直接释放机器人
					iAiResourceManagerService.aiRelease(ai);
				}
			}
			//释放后再查下用户还有没有机器人了，如果没有了，就把用户机器人清空，并将用户资源状态重置为正常
			List<AiInuseCache> newUserAiInuseList = aiCacheService.queryUserAiInUseList(userId);
			if(newUserAiInuseList == null || newUserAiInuseList.isEmpty()) {
				UserResourceCache userResourceCache = aiCacheService.getUserResource(userId);
				if(userResourceCache != null) {
					//用户资源变更状态，设置为正常(本次变更影响已处理完)
					userResourceCache.setChgStatus(null);
					aiCacheService.putUserResource(userResourceCache);
				}
				//将用户机器人清空
				aiCacheService.delUserAis(userId);
			}
		}
		logger.info("用户资源变更，释放用户{}空闲机器人",userId);
	}
	
	
	/**
	 * 去除标点符号，保留中文
	 * @param str
	 * @return
	 */
	private String cnText(String str) {
		if(StrUtils.isNotEmpty(str)) {
			Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
	        //[\u4E00-\u9FA5]是unicode2的中文区间
	        Matcher matcher = pattern.matcher(str);
	        str = matcher.replaceAll("");
		}
		return str;
	}
	
	/**
	 * 是否需要走噪音检测
	 * @param sentence
	 * @return
	 */
	private boolean isNeedVoiceCheck(AiFlowMsgPushReq aiFlowMsgPushReq) {
		//长度>4不需要走噪音检测
		if(aiFlowMsgPushReq.getSentence().length()>4) {
			return false;
		}
		//白名单不走噪音检测
		if(this.constants(RobotConstants.white_list, aiFlowMsgPushReq.getSentence())) {
			return false;
		}
		//关键字匹配了，不需要走噪音检测
		AiCallLngKeyMatchReq aiCallLngKeyMatchReq = new AiCallLngKeyMatchReq();
		BeanUtil.copyProperties(aiFlowMsgPushReq, aiCallLngKeyMatchReq);
		boolean isMatched = this.isMatched(aiCallLngKeyMatchReq);
		if(isMatched) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 是否匹配了关键字
	 * @param aiCallLngKeyMatchReq
	 * @return
	 */
	private boolean isMatched(AiCallLngKeyMatchReq aiCallLngKeyMatchReq) {
		if(aiCallLngKeyMatchReq != null) {
			AiCallNext aiCallNext = this.aiLngKeyMatch(aiCallLngKeyMatchReq);
			if(aiCallNext != null && StrUtils.isNotEmpty(aiCallNext.getSellbotJson())) {
				JSONObject jsonObject = JSON.parseObject(aiCallNext.getSellbotJson());
				String matched = jsonObject.getString("matched");
				if("1".equals(matched)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 从消息缓存流中获取一条消息同AI交互
	 * @return
	 */
	private AiFlowSentenceCache getMsgFlowSentence(AiCallNextReq aiCallNextReq) {
		//播音状态时(status=1)，time_stamp表示播音开始的时间，空间状态时(status=0),表示播音结束的时间
		long beginVoiceTime = aiCallNextReq.getTimestamp();
		if(RobotConstants.CALL_STATUS_OVER.equals(aiCallNextReq.getStatus())) {
			//如果现在状态是语音播放结束,那么时间-800ms
			beginVoiceTime = beginVoiceTime - 800;
		}
		String seqId = aiCallNextReq.getSeqId();
		//从缓存中读取
		Map<Long,AiFlowSentenceCache> sentenceMap = aiCacheService.queryFlowSentence(seqId);
		if(sentenceMap!=null && !sentenceMap.isEmpty()) {
			//sentence最长的消息
			AiFlowSentenceCache maxLengthSentence = null;
			int maxLength = 0;
			for (Map.Entry<Long,AiFlowSentenceCache> sentenceEntry : sentenceMap.entrySet()) {
				//缓存数据时间>播音开始时间
				if(sentenceEntry.getKey().longValue()>beginVoiceTime) {
					if(maxLengthSentence == null && sentenceEntry.getValue().getSentence().length()>maxLength) {
						maxLengthSentence = sentenceEntry.getValue();
						maxLength = sentenceEntry.getValue().getSentence().length();
					}
				}
			}
			return maxLengthSentence;
		}
		return null;
	}
	
	
	/**
	 * 消息流开始处理-wait处理
	 * 1、status=999(开场白)
	 * 	  是否命中关键字,未命中,wait
	 * 2、status=1(播音中)	
	 *   2.1、命中黑名单-wait
	 *   2.2、流消息语音识别的内容长度小于3且没有匹配关键字-wait
	 * @param sentenceCache
	 * @param aiCallNextReq
	 */
	private String flowMsgWaitDeal(AiFlowSentenceCache sentenceCache,AiCallNextReq aiCallNextReq) {
		//播音状态
		String status = aiCallNextReq.getStatus();
		if(RobotConstants.CALL_STATUS_BEGIN.equals(status)) {
			//开场白
			AiCallLngKeyMatchReq aiCallLngKeyMatchReq = new AiCallLngKeyMatchReq();
			BeanUtil.copyProperties(aiCallNextReq, aiCallLngKeyMatchReq);
			//设置sentence
			aiCallLngKeyMatchReq.setSentence(sentenceCache.getSentence());
			boolean isMatched = this.isMatched(aiCallLngKeyMatchReq);
			if(!isMatched) {
				logger.info("会话ID:{},sentence:{},匹配关键字...return wait",aiCallNextReq.getSeqId(),sentenceCache.getSentence());
				//未匹配关键字
				return RobotConstants.HELLO_STATUS_WAIT;
			}
		}else if(RobotConstants.CALL_STATUS_ING.equals(status)) {
			//播音中
			//匹配黑名单
			if(this.constants(RobotConstants.black_list,sentenceCache.getSentence())) {
				logger.info("会话ID:{},sentence:{},匹配黑名单...return wait",aiCallNextReq.getSeqId(),sentenceCache.getSentence());
				return RobotConstants.HELLO_STATUS_WAIT;
			}
			if(sentenceCache.getSentence().length()<3) {
				//长度<3且没有命中关键字-wait
				AiCallLngKeyMatchReq aiCallLngKeyMatchReq = new AiCallLngKeyMatchReq();
				BeanUtil.copyProperties(aiCallNextReq, aiCallLngKeyMatchReq);
				aiCallLngKeyMatchReq.setSentence(sentenceCache.getSentence());
				boolean isMatched = this.isMatched(aiCallLngKeyMatchReq);
				if(!isMatched) {
					//未匹配关键字
					logger.info("会话ID:{},sentence:{},长度<3且未匹配关键字...return wait",aiCallNextReq.getSeqId(),sentenceCache.getSentence());
					return RobotConstants.HELLO_STATUS_WAIT;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 检查txt文本中是否包含了数组中的字符串
	 * @param array
	 * @param txt
	 * @return
	 */
	private boolean constants(List<String> array,String txt) {
		if(ListUtil.isNotEmpty(array) && StrUtils.isNotEmpty(txt)) {
			for(String str:array) {
				if(txt.indexOf(str)>=0) {
					return true;
				}
			}
		}
		return false;
	}
}
