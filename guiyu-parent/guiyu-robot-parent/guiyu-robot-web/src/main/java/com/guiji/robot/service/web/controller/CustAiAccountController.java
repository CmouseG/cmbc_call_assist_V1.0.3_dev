package com.guiji.robot.service.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: CustAiCfgAccountController 
* @Description: 用户机器人服务
* @date 2018年11月16日 下午3:40:47 
* @version V1.0  
*/
@RequestMapping(value = "/account")
@RestController
public class CustAiAccountController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUserAiCfgService iUserAiCfgService;
	
	
	
	/**
	 * 新增或者修改用户机器人配置信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfgBaseInfo", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgBaseInfo> saveUserAiCfgBaseInfo(@RequestBody UserAiCfgBaseInfo userAiCfgBaseInfo){
		logger.info("新增/修改用户机器人配置基本信息");
		if(userAiCfgBaseInfo == null
				|| StrUtils.isEmpty(userAiCfgBaseInfo.getUserId())
				|| StrUtils.isEmpty(userAiCfgBaseInfo.getTemplateIds())
				|| userAiCfgBaseInfo.getAiTotalNum() == null
				|| userAiCfgBaseInfo.getAiTotalNum() < 0
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		userAiCfgBaseInfo = iUserAiCfgService.saveOrUpdate(userAiCfgBaseInfo);
		return Result.ok(userAiCfgBaseInfo);
	}
	
	
	/**
	 * 查询用户机器人账户基本信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserAiCfgBaseInfoByUserId", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoByUserId(@RequestParam(value="userId",required=true)String userId){
		if(StrUtils.isEmpty(userId)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		UserAiCfgBaseInfo userAiCfgBaseInfo = iUserAiCfgService.queryUserAiCfgBaseInfoByUserId(userId);
		return Result.ok(userAiCfgBaseInfo);
	}
	
	
	/**
	 * 查询用户正在使用的机器人开户账号明细
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryCustAccount", method = RequestMethod.POST)
	public Result.ReturnData<List<UserAiCfgInfo>> queryCustAccount(@RequestParam(value="userId",required=true)String userId){
		if(StrUtils.isEmpty(userId)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
		return Result.ok(list);
	}
	
	
	
	/**
	 * 新增或者修改用户机器人配置线路拆分信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfg", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgInfo> saveUserAiCfg(@RequestBody UserAiCfgInfo userAiCfgInfo){
		logger.info("新增/修改用户机器人配置线路拆分信息");
		if(userAiCfgInfo == null
				|| StrUtils.isEmpty(userAiCfgInfo.getUserId())
				|| StrUtils.isEmpty(userAiCfgInfo.getTemplateIds())
				|| userAiCfgInfo.getAiNum() == null
				|| userAiCfgInfo.getAiNum() <=0
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		UserAiCfgInfo cfgInfo = iUserAiCfgService.userAiCfgChange(userAiCfgInfo);
		return Result.ok(cfgInfo);
	}
	
	
	/**
	 * 新增或者修改用户机器人配置线路拆分信息
	 * @param userId 要删除的用户编号
	 * @param id 要删除的数据id
	 * @return
	 */
	@RequestMapping(value = "/delUserCfg", method = RequestMethod.POST)
	public Result.ReturnData delUserCfg(@RequestParam(value="userId",required=true)String userId,@RequestParam(value="id",required=true)String id){
		logger.info("新增/修改用户机器人配置信息");
		if(StrUtils.isEmpty(userId) && StrUtils.isEmpty(id)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		iUserAiCfgService.delUserCfg(userId, id);
		return Result.ok();
	}
	
}
