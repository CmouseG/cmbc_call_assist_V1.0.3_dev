package com.guiji.robot.web.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.AiPoolInfo;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: RobotController 
* @Description: 机器人能力中心服务(前端使用)
* @date 2018年11月23日 上午9:17:35 
* @version V1.0  
*/
@RestController
public class RobotController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	@Autowired
	AiCacheService aiCacheService;
	
	
	/**
	 * 查询用户已分配的机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserInUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserInUseAiList(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId
			){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		if(StrUtils.isEmpty(qUserId)) {
			//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
			qUserId = userId.toString();
		}
		List<AiInuseCache> aiList = iAiResourceManagerService.queryUserInUseAiList(qUserId);
		if(ListUtil.isNotEmpty(aiList)) {
			//按机器人名称正序
			aiList.sort(Comparator.comparing(AiInuseCache::getSortId).thenComparing(AiInuseCache::getSortId));
		}
		return Result.ok(aiList);
	}
	
	
	/**
	 * 查询用户现在在忙的机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserBusyUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserBusyUseAiList(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId
			){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		if(StrUtils.isEmpty(qUserId)) {
			//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
			qUserId = userId.toString();
		}
		List<AiInuseCache> aiList = iAiResourceManagerService.queryUserBusyUseAiList(qUserId);
		if(ListUtil.isNotEmpty(aiList)) {
			//按机器人名称正序
			aiList.sort(Comparator.comparing(AiInuseCache::getSortId).thenComparing(AiInuseCache::getSortId));
		}
		return Result.ok(aiList);
	}
	
	
	/**
	 * 查询用户休息的（空闲/暂停不可用）机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserSleepUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserSleepUseAiList(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId
			){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		if(StrUtils.isEmpty(qUserId)) {
			//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
			qUserId = userId.toString();
		}
		List<AiInuseCache> aiList = iAiResourceManagerService.queryUserSleepUseAiList(qUserId);
		if(ListUtil.isNotEmpty(aiList)) {
			//按机器人名称正序
			aiList.sort(Comparator.comparing(AiInuseCache::getSortId).thenComparing(AiInuseCache::getSortId));
		}
		return Result.ok(aiList);
	}
	
	
	/**
	 * 手工释放机器人资源
	 * 本接口为预留接口，正常的资源释放是在每天晚上8:30自动释放所有空闲的机器人的，但是某些情况还是需要手工释放资源，所以开出个口子
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/aiResourRelease", method = RequestMethod.POST)
	public Result.ReturnData aiResourRelease(@RequestParam(value="userId",required=false)String userId){
		List<AiInuseCache> aiList = new ArrayList<AiInuseCache>();
		if(StrUtils.isEmpty(userId)) {
			//如果参数userId是空，那么释放所有空闲机器人
			Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
			if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
				for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
					userId = allUserAiInuseEntry.getKey();	//用户ID
					List<AiInuseCache> userAiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
					aiList.addAll(userAiList);
					logger.info("开始释放用户{}[{}]个机器人...",userId,userAiList==null?0:userAiList.size());
					//释放机器人资源
					aiCacheService.delUserAis(userId);
					logger.info("释放用户{}[{}]个机器人...完成",userId,userAiList==null?0:aiList.size());
				}
			}
		}else {
			//不为空,释放该用户空闲机器人资源
			aiList = aiCacheService.queryUserAiInUseList(userId);
			if(ListUtil.isNotEmpty(aiList)) {
				logger.info("开始释放用户{}[{}]个机器人...",userId,aiList==null?0:aiList.size());
				//释放机器人资源
				aiCacheService.delUserAis(userId);
				logger.info("释放用户{}[{}]个机器人...完成",userId,aiList==null?0:aiList.size());
			}
		}
		//将机器人池中的机器人都改为空闲
		if(ListUtil.isNotEmpty(aiList)) {
			for(AiInuseCache ai:aiList) {
				ai.setAiStatus(RobotConstants.AI_STATUS_F);
				ai.setCallingPhone(null);
				ai.setCallingTime(null);
				aiCacheService.changeAiPool(ai);
			}
		}
		return Result.ok();
	}
	
	
	/**
	 * 查询机器人资源池概况
	 * 查询资源池中总共多少机器人、在忙的有多少，每个用户分配了多少
	 * @return
	 */
	@RequestMapping(value = "/queryAiPoolInfo")
	public Result.ReturnData<AiPoolInfo> queryAiPoolInfo(){
		AiPoolInfo aiPoolInfo = iAiResourceManagerService.queryAiPoolInfo();
		return Result.ok(aiPoolInfo);
	}
}
