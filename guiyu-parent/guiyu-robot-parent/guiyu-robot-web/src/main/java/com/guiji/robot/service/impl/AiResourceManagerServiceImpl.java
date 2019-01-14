package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.model.ProcessReleaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiPoolInfo;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiResourceManagerServiceImpl 
* @Description: 机器人资源管理
* @date 2018年11月16日 下午2:19:33 
* @version V1.0  
*/
@Service
public class AiResourceManagerServiceImpl implements IAiResourceManagerService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	AiAsynDealService aiAsynDealService;
	@Autowired
	AiCacheService aiCacheService; 
	@Autowired
	IUserAiCfgService iUserAiCfgService;
	@Autowired
	IProcessSchedule iProcessSchedule;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	
	
	/**
	 * 启动时，先初始化次
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initAiPool() {
		if(!redisUtil.hasKey(RobotConstants.ROBOT_POOL_AI)) {
			//如果缓存中不存在，初始化下
			this.aiPoolInit();
		}
	}
	
	/**
	 * 初始化机器人资源池
	 * 将机器人从进程管理中初始化到缓存机器人池中
	 */
	public List<AiInuseCache> aiPoolInit() {
		Lock lock = new Lock(RobotConstants.LOCK_ROBOT_AIPOOL, RobotConstants.LOCK_ROBOT_AIPOOL);
		if (distributedLockHandler.tryLock(lock)) {
			try {
				//调用进程管理服务申请sellbot机器人资源
				ReturnData<List<ProcessInstanceVO>> processInstanceListData = iProcessSchedule.getSellbot(10000);	//这里需要获取所有机器人资源，后续需要单独提供接口，现在先用10000来获取全部
				if(processInstanceListData == null) {
					logger.error("调用进程管理申请资源，返回数据为空..");
					throw new RobotException(AiErrorEnum.AI00060007.getErrorCode(),AiErrorEnum.AI00060007.getErrorMsg());
				}else if(!RobotConstants.RSP_CODE_SUCCESS.equals(processInstanceListData.getCode())) {
					logger.error("调用进程管理申请全部机器人资源异常...");
					throw new RobotException(processInstanceListData.getCode(),processInstanceListData.getMsg());
				}
				List<ProcessInstanceVO> instanceList = processInstanceListData.getBody();
				if(instanceList == null || instanceList.isEmpty() || instanceList.size()==0) {
					logger.error("调用进程管理申请全部机器人异常，无空余可用机器人...");
					throw new RobotException(AiErrorEnum.AI00060008.getErrorCode(),AiErrorEnum.AI00060008.getErrorMsg());
				}else {
					logger.info("初始化机器人资源池,申请到{}个机器人",instanceList.size());
				}
				List<AiInuseCache> aiPoolList = new ArrayList<AiInuseCache>();
				for(int i=0;i<instanceList.size();i++) {
					AiInuseCache aiInuse = new AiInuseCache();
					aiInuse.setAiNo(this.genAiNo(instanceList.get(i).getIp(), String.valueOf(instanceList.get(i).getPort()))); //机器人临时编号
					aiInuse.setAiStatus(RobotConstants.AI_STATUS_F); //新申请机器人默认空闲状态
					aiInuse.setInitDate(DateUtil.getCurrentymd()); //初始化日期
					aiInuse.setInitTime(DateUtil.getCurrentTime()); //初始化时间
					aiInuse.setIp(instanceList.get(i).getIp());	//机器IP
					aiInuse.setPort(String.valueOf(instanceList.get(i).getPort()));	//机器人port
					aiPoolList.add(aiInuse);
				}
				//放入redis缓存
				aiCacheService.initAiPool(aiPoolList);
				//3、异步记录历史
				aiAsynDealService.initAiCycleHis(aiPoolList);
				return aiPoolList;
			} catch (RobotException e) {
				throw e; 
			} catch (Exception e1) {
				logger.error("机器人资源池初始化异常！",e1);
				throw new RobotException(AiErrorEnum.AI00060033.getErrorCode(),AiErrorEnum.AI00060033.getErrorMsg());
			}finally {
				//释放锁
				distributedLockHandler.releaseLock(lock);
			}
		}else {
			logger.warn("用户机器人资源池未能获取资源锁！！！");
			throw new RobotException(AiErrorEnum.AI00060034.getErrorCode(),AiErrorEnum.AI00060034.getErrorMsg());
		}
	}
	
	/**
	 * 重新加载sellbot资源
	 */
	@Override
	public void reloadSellbotResource() {
		//删除机器人资源
		aiCacheService.delAiPools();
		//重新加载
		this.aiPoolInit();
	}
	
	/**
	 * 从AI资源池中获取所有机器人
	 * @return
	 */
	@Override
	public List<AiInuseCache> queryAiPoolList(){
		return aiCacheService.queryAiPools();
	}
	
	
	/**
	 * 查询机器人资源池概况
	 * 查询资源池中总共多少机器人、在忙的有多少，每个用户分配了多少
	 * @return
	 */
	@Override
	public AiPoolInfo queryAiPoolInfo() {
		AiPoolInfo aiPoolInfo = new AiPoolInfo();
		List<AiInuseCache> allAiList = this.queryAiPoolList();
		if(ListUtil.isNotEmpty(allAiList)) {
			aiPoolInfo.setTotalNum(allAiList.size()); //资源池中机器人总数
			int aiBusiNum = 0;
			for(AiInuseCache ai:allAiList) {
				if(RobotConstants.AI_STATUS_B.equals(ai.getAiStatus())) {
					aiBusiNum++;
				}
			}
			aiPoolInfo.setBusiNum(aiBusiNum);
		}
		//查询所有用户已分配的机器人列表
		Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
		if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
			Map<String,Integer> userAiMap = new HashMap<String,Integer>();
			for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
				String userId = allUserAiInuseEntry.getKey();	//用户ID
				List<AiInuseCache> userAiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
				if(ListUtil.isNotEmpty(userAiList)) {
					userAiMap.put(userId, userAiList.size());
				}
			}
			aiPoolInfo.setUserAssignMap(userAiMap);
		}
		return aiPoolInfo;
	}
	
	
	/**
	 * 从机器人资源池中分配一个机器人给本次申请
	 * @param aiCallApplyReq
	 * @param userResourceCache
	 * @return
	 */
	@Override
	public AiInuseCache aiAssign(AiCallApplyReq aiCallApplyReq) {
		//获取AI机器人资源池
		List<AiInuseCache> aiPoolList = this.queryAiPoolList();
		if(aiPoolList == null || aiPoolList.isEmpty()) {
			//如果缓存中没有数据，初始化下
			aiPoolList = this.aiPoolInit();
		}
		AiInuseCache assignAi = null;
		if(aiPoolList != null && !aiPoolList.isEmpty()) {
			for(AiInuseCache aiInuseCache : aiPoolList) {
				if(RobotConstants.AI_STATUS_F.equals(aiInuseCache.getAiStatus())) {
					//找到1个空闲的机器人
					assignAi = aiInuseCache;
					break;
				}
			}
		}
		if(assignAi==null) {
			//没有空闲机器人
			logger.error("本地机器人资源池机器人总数{},没有空闲机器人",aiPoolList.size());
			throw new RobotException(AiErrorEnum.AI00060002.getErrorCode(),AiErrorEnum.AI00060002.getErrorMsg());
		}
		//设置分配后的部分信息
		assignAi.setUserId(aiCallApplyReq.getUserId());	//用户号
		assignAi.setTemplateIds(aiCallApplyReq.getTemplateId());	//模板号
		assignAi.setAiStatus(RobotConstants.AI_STATUS_B); //忙
		assignAi.setCallingPhone(aiCallApplyReq.getPhoneNo()); //正在拨打的电话
		assignAi.setCallingTime(DateUtil.getCurrentTime()); //开始拨打时间
		assignAi.setSeqId(aiCallApplyReq.getSeqId());	//会话id
		assignAi.setCallNum(assignAi.getCallNum()+1); //拨打数量
		//将这个机器人分配给用户
		aiCacheService.changeAiInUse(assignAi);
		//将资源池中机器人状态更新为忙
		aiCacheService.changeAiPool(assignAi);
		return assignAi;
	}
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * 1、将用户inuse缓存清理掉
	 * 2、将机器人资源池中的机器人状态置为闲
	 * @param aiInuse
	 */
	@Override
	public void aiRelease(AiInuseCache aiInuse) {
		if(aiInuse != null) {
			//从用户缓存中也清理掉该用户的找个机器人
			aiCacheService.delUserAi(aiInuse.getUserId(), aiInuse.getAiNo());
			//将机器人资源池中的机器人状态置为闲
			aiInuse.setAiStatus(RobotConstants.AI_STATUS_F);
			aiInuse.setCallingPhone(null);
			aiInuse.setCallingTime(null);
			aiCacheService.changeAiPool(aiInuse);
		}
	}
	
	
	/**
	 * 机器人资源批量释放还回进程资源池
	 * @param aiList
	 */
	@Override
	public void aiBatchRtnProcess(List<AiInuseCache> aiList) {
		//调用进程管理，释放机器人资源
		if(ListUtil.isNotEmpty(aiList)) {
			List<ProcessInstanceVO> processList = new ArrayList<ProcessInstanceVO>();
			for(AiInuseCache ai : aiList) {
				ProcessInstanceVO vo = new ProcessInstanceVO();
				vo.setIp(ai.getIp());
				vo.setPort(Integer.valueOf(ai.getPort()));
				processList.add(vo);
			}
			//调用进程管理释放资源
			ProcessReleaseVO processReleaseVO = new ProcessReleaseVO();
			processReleaseVO.setProcessInstanceVOS(processList);
			iProcessSchedule.release(processReleaseVO);
			//异步记录日志
			aiAsynDealService.releaseAiCycleHis(aiList);
		}
	}
	

	/**
	 * 设置机器人忙-正在打电话
	 * @param aiInuseCache
	 */
	@Override
	public void aiBusy(AiInuseCache aiInuseCache) {
		if(aiInuseCache != null) {
			aiInuseCache.setAiStatus(RobotConstants.AI_STATUS_B);
			aiCacheService.changeAiInUse(aiInuseCache);
		}
	}
	
	
	/**
	 * 设置机器人空闲
	 * @param aiInuseCache
	 */
	@Override
	public void aiFree(AiInuseCache aiInuseCache) {
		if(aiInuseCache != null) {
			aiInuseCache.setAiStatus(RobotConstants.AI_STATUS_F);
			aiCacheService.changeAiInUse(aiInuseCache);
		}
	}
	
	
	/**
	 * 设置机器人暂停不可用
	 * @param aiInuseCache
	 */
	@Override
	public void aiPause(AiInuseCache aiInuseCache) {
		if(aiInuseCache != null) {
			aiInuseCache.setAiStatus(RobotConstants.AI_STATUS_P);
			aiCacheService.changeAiInUse(aiInuseCache);
		}
	}
	

	/**
	 * 查询用户已分配的AI列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AiInuseCache> queryUserInUseAiList(String userId){
		if(StrUtils.isNotEmpty(userId)) {
			return aiCacheService.queryUserAiInUseList(userId);
		}
		return null;
	}
	
	
	/**
	 * 查询用户正在忙的AI列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AiInuseCache> queryUserBusyUseAiList(String userId){
		if(StrUtils.isNotEmpty(userId)) {
			List<AiInuseCache> list = aiCacheService.queryUserAiInUseList(userId);
			if(ListUtil.isNotEmpty(list)) {
				Iterator<AiInuseCache> it = list.iterator();
				while(it.hasNext()){
					AiInuseCache ai = it.next();
					if(!RobotConstants.AI_STATUS_B.equals(ai.getAiStatus())) {
						it.remove();
					}
				}
			}
			return list;
		}
		return null;
	}
	
	
	/**
	 * 查询用户在休息的AI列表（空闲/暂停不可以用）
	 * @param userId
	 * @return
	 */
	public List<AiInuseCache> queryUserSleepUseAiList(String userId){
		if(StrUtils.isNotEmpty(userId)) {
			List<AiInuseCache> sleepList = new ArrayList<AiInuseCache>();
			UserResourceCache userResourceCache = aiCacheService.getUserResource(userId);
			int aiNum = 0;	//用户总机器人
			int aiInuseNum = 0;	//用户在忙的机器人
			if(userResourceCache!=null) {
				aiNum = userResourceCache.getAiNum();	//用户总机器人
			}
			List<AiInuseCache> aiInuseList = aiCacheService.queryUserAiInUseList(userId);	
			if(ListUtil.isNotEmpty(aiInuseList)) {
				aiInuseNum = aiInuseList.size();
			}
			if(aiNum-aiInuseNum>0) {
				//总机器人-在忙的机器人 = 空闲的机器人
				for(int i=0;i<(aiNum-aiInuseNum);i++) {
					//拼装用户空闲的机器人
					AiInuseCache aiInuseCache = new AiInuseCache();
					aiInuseCache.setAiNo("AI"+i); //机器人编号
					aiInuseCache.setAiName("硅语"+(i+1)+"号"); //机器人名字
					aiInuseCache.setSortId(i); //排序ID
					aiInuseCache.setAiStatus(RobotConstants.AI_STATUS_F); //空闲
					aiInuseCache.setCallNum(0);
					aiInuseCache.setUserId(userId);
					sleepList.add(aiInuseCache);
				}
			}
			return sleepList;
		}
		return null;
	}
	
	
	/**
	 * 查询用户某个机器人
	 * @param userId 用户id
	 * @param aiNo 机器人编号
	 * @return
	 */
	@Override
	public AiInuseCache queryUserAi(String userId,String aiNo){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(aiNo)) {
			return aiCacheService.queryAiInuse(userId, aiNo);
		}
		return null;
	}
	
	/**
	 * ip/port生成机器人
	 * ipportyyyyMMdd
	 * @param ip
	 * @param port
	 * @return
	 */
	private String genAiNo(String ip,String port) {
		String aiNo = ip+port;
		aiNo = aiNo.replaceAll("\\.","");
		return DateUtil.getStringDate(new Date())+aiNo;
	}
}
