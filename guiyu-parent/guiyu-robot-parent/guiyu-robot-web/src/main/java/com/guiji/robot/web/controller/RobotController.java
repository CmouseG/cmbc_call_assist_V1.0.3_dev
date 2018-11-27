package com.guiji.robot.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.vo.AiInuseCache;
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
	
	
	/**
	 * 查询用户已分配的机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserInUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserInUseAiList(
			@RequestParam(value="userId",required=true)String qUserId,
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
		return Result.ok(aiList);
	}
	
	
	/**
	 * 查询用户现在在忙的机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserBusyUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserBusyUseAiList(
			@RequestParam(value="userId",required=true)String qUserId,
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
		return Result.ok(aiList);
	}
	
	
	/**
	 * 查询用户休息的（空闲/暂停不可用）机器人列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserSleepUseAiList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiInuseCache>> queryUserSleepUseAiList(
			@RequestParam(value="userId",required=true)String qUserId,
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
		return Result.ok(aiList);
	}
}
