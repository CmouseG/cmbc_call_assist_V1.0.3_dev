package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiResourceApply;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;
import com.guiji.utils.SystemUtil;

import lombok.Synchronized;

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
	
	/**
	 * 机器人资源分配
	 * 增加同步锁-简单点可以先加代码锁，后续改为分布锁，以防止再申请资源时资源冲突
	 * @param checkAiReady
	 * @return
	 */
	@Override
	@Synchronized
	public List<AiInuseCache> aiAssign(AiResourceApply checkAiReady){
		if(checkAiReady != null) {
			//1、检查每个话术模板需要启动几个机器人
			Map<String,Integer> addTempAiMap = this.queryAddTemplateAi(checkAiReady.getUserId());
			int addAiNum = 0;
			for (Map.Entry<String,Integer> addEntry : addTempAiMap.entrySet()) { 
				addAiNum = addAiNum + addEntry.getValue();
			}
			if(addAiNum!=checkAiReady.getAiNum()) {
				//检查出来的机器人新增数量和本次要申请的数量不匹配
				throw new RobotException(AiErrorEnum.AI00060005.getErrorCode(),AiErrorEnum.AI00060005.getErrorMsg());
			}
			//调用进程管理服务申请sellbot机器人资源
			ReturnData<List<ProcessInstanceVO>> processInstanceListData = iProcessSchedule.getSellbot(addAiNum);
			if(processInstanceListData == null || !RobotConstants.RSP_CODE_SUCCESS.equals(processInstanceListData.getCode())){
				logger.error("调用进程管理申请{}个机器人资源异常...",addAiNum);
				throw new RobotException(processInstanceListData.getCode(),processInstanceListData.getMsg());
			}
			List<ProcessInstanceVO> instanceList = processInstanceListData.getBody();
			if(instanceList == null || instanceList.isEmpty()) {
				logger.error("调用进程管理申请{}个机器人异常，无空余可用机器人...");
				throw new RobotException(AiErrorEnum.AI00060008.getErrorCode(),AiErrorEnum.AI00060008.getErrorMsg());
			}
			List<AiInuseCache> list = new ArrayList<AiInuseCache>();
			int idx = 0;
			for (Map.Entry<String,Integer> addEntry : addTempAiMap.entrySet()) {
				for(int i=0;i<addEntry.getValue();i++) {
					if(idx+1>instanceList.size()) {
						//需要分配的机器人>申请到的机器人; 能分配几个是几个
						logger.error("机器人申请到的资源不够，需要分配机器人数量{}，申请到的机器人资源数量{}",addAiNum,instanceList.size());
						break;
					}
					AiInuseCache aiInuse = new AiInuseCache();
					aiInuse.setAiNo(this.genAiNo(instanceList.get(idx).getIp(), String.valueOf(instanceList.get(idx).getPort()))); //机器人临时编号
					aiInuse.setAiStatus(RobotConstants.AI_STATUS_F); //新申请机器人默认空闲状态
					aiInuse.setUserId(checkAiReady.getUserId()); //用户ID
					aiInuse.setInitDate(DateUtil.getCurrentymd()); //分配日期
					aiInuse.setInitTime(DateUtil.getCurrentTime()); //分配时间
					aiInuse.setIp(instanceList.get(idx).getIp());	//机器IP
					aiInuse.setPort(String.valueOf(instanceList.get(idx).getPort()));	//机器人port
					list.add(aiInuse);
					idx++;
				}
			}
			//2、更新到缓存
			aiCacheService.cacheUserAiAssign(checkAiReady.getUserId(), list);
			//3、异步记录历史
			aiAsynDealService.initAiCycleHis(list);
			return list;
		}
		return null;
	}
	
	
	/**
	 * 匹配现在客户最新配置以及现在缓存中的配置
	 * 确认本次新增，每类模板需要增加多少个机器人
	 * 只支持数量增加的场景，如果是减少或者变更模板，都不能再运行中新增，而是必须清空后重新获取
	 * @param userId
	 * @return
	 */
	private Map<String,Integer> queryAddTemplateAi(String userId){
		Map<String,Integer> addTempAiMap = new HashMap<String,Integer>();
		Map<String,Integer> cacheMap = new HashMap<String,Integer>();
		List<AiInuseCache> userAiInuseList = aiCacheService.queryUserAiInUseList(userId);
		//现在用户机器人配置
		Map<String,Integer> userAiCfgMap = new HashMap<String,Integer>();
		List<UserAiCfgInfo> userAiCfgList = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
		for(UserAiCfgInfo cfg:userAiCfgList) {
			if(RobotConstants.USER_CFG_STATUS_S.equals(cfg.getStatus())) {
				userAiCfgMap.put(cfg.getTemplateIds(), userAiCfgMap.get(cfg.getTemplateIds())==null?1:userAiCfgMap.get(cfg.getTemplateIds())+1);
			}
		}
		if(ListUtil.isNotEmpty(userAiInuseList)) {
			for(AiInuseCache aiCache:userAiInuseList) {
				cacheMap.put(aiCache.getTemplateIds(), cacheMap.get(aiCache.getTemplateIds())==null?1:cacheMap.get(aiCache.getTemplateIds())+1);
			}
		}
		if(userAiCfgMap!=null && !userAiCfgMap.isEmpty()) {
			for (Map.Entry<String,Integer> cfgEntry : userAiCfgMap.entrySet()) { 
				int addNum = cfgEntry.getValue()==null?0:cfgEntry.getValue() - cacheMap.get(cfgEntry.getKey()==null?0:cacheMap.get(cfgEntry.getKey()));
				if(addNum>0) {
					addTempAiMap.put(cfgEntry.getKey(), addNum);
				}
			}
		}
		return addTempAiMap;
	}
	
	
	/**
	 * 机器人资源释放还回进程资源池
	 * @param aiBaseInfo
	 */
	@Override
	public void aiRelease(AiInuseCache aiInuse) {
		//调用进程管理，释放机器人资源
		if(aiInuse != null) {
			List<ProcessInstanceVO> processList = new ArrayList<ProcessInstanceVO>();
			ProcessInstanceVO vo = new ProcessInstanceVO();
			vo.setIp(aiInuse.getIp());
			vo.setPort(Integer.valueOf(aiInuse.getPort()));
			processList.add(vo);
			//调用进程管理释放资源
			iProcessSchedule.release(processList);
			//异步记录日志
			aiAsynDealService.releaseAiCycleHis(new ArrayList<AiInuseCache>(){{add(aiInuse);}});
		}
		
	}
	
	
	/**
	 * 机器人资源批量释放还回进程资源池
	 * @param aiList
	 */
	@Override
	public void aiBatchRelease(List<AiInuseCache> aiList) {
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
			iProcessSchedule.release(processList);
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
					if(RobotConstants.AI_STATUS_B.equals(ai.getAiStatus())) {
						it.remove();
					}
				}
			}
			return list;
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
	 * @param ip
	 * @param port
	 * @return
	 */
	private String genAiNo(String ip,String port) {
		String aiNo = ip+port;
		aiNo = aiNo.replaceAll("\\.","");
		return SystemUtil.getBusiSerialNo(aiNo);
	}
}
