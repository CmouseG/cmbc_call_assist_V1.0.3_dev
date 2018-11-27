package com.guiji.robot.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.api.IAuth;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.UserAiCfgBaseInfoVO;
import com.guiji.robot.service.vo.UserAiCfgQueryCondition;
import com.guiji.robot.util.ListUtil;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
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
	@Autowired
	IAuth iAuth;
	
	
	/**
	 * 新增或者修改用户机器人配置信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfgBaseInfo", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgBaseInfo> saveUserAiCfgBaseInfo(@RequestBody UserAiCfgBaseInfo userAiCfgBaseInfo){
		if(userAiCfgBaseInfo == null
				|| StrUtils.isEmpty(userAiCfgBaseInfo.getUserId())
				|| userAiCfgBaseInfo.getAiTotalNum() == null
				|| userAiCfgBaseInfo.getAiTotalNum() < 0
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		userAiCfgBaseInfo = iUserAiCfgService.putupUserCfgBase(userAiCfgBaseInfo);
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
	 * 查询用户机器人账户基本信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserAiCfgBaseFroPageByUserId", method = RequestMethod.POST)
	public Result.ReturnData<Page<UserAiCfgBaseInfoVO>> queryUserAiCfgBaseInfoByUserId(
			@RequestParam(value="pageNo",required=true)int pageNo,
			@RequestParam(value="pageSize",required=true)int pageSize,
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId, 
			@RequestHeader Boolean isSuperAdmin){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		if(StrUtils.isEmpty(qUserId)) {
			//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
			qUserId = userId.toString();
		}
		if(isSuperAdmin) {
			qUserId = null;
		}
		List<UserAiCfgBaseInfoVO> rtnList = new ArrayList<UserAiCfgBaseInfoVO>();
		Page<UserAiCfgBaseInfo> page = iUserAiCfgService.queryUserAiCfgBaseInfoFroPageByUserId(pageNo,pageSize,qUserId);
		List<UserAiCfgBaseInfo> list = new ArrayList<UserAiCfgBaseInfo>();
		if(ListUtil.isNotEmpty(list)) {
			Map<String,String> userMap = new HashMap<String,String>();
			for(UserAiCfgBaseInfo base:list) {
				UserAiCfgBaseInfoVO vo = new UserAiCfgBaseInfoVO();
				BeanUtil.copyProperties(base, vo);
				String uId = base.getUserId();
				if(userMap.get(uId)!=null) {
					vo.setUserName(userMap.get(uId));
				}else {
					//缓存中没有，重新查
					ReturnData<SysUser> userData = iAuth.getUserById(Long.parseLong(uId));
					if(userData != null && userData.getBody()!=null) {
						String userName = userData.getBody().getUsername();
						vo.setUserName(userName);
						userMap.put(uId, userName);
					}
				}
				rtnList.add(vo);
			}
		}
		Page<UserAiCfgBaseInfoVO> rtnPage = new Page<UserAiCfgBaseInfoVO>(pageSize,page.getTotalRecord(),rtnList);
		return Result.ok(rtnPage);
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
	 * 查询用户正在使用的机器人开户账号明细
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryCustAccountForPage/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public Result.ReturnData<Page<UserAiCfgInfo>> queryCustAccountForPage(
			 @PathVariable(value="pageNo",required=true)int pageNo,
			 @PathVariable(value="pageSize",required=true)int pageSize,
			 @RequestBody UserAiCfgQueryCondition condition){
		Page<UserAiCfgInfo> page = iUserAiCfgService.queryCustAccountForPage(pageNo, pageSize, condition);
		return Result.ok(page);
	}
	
	
	
	/**
	 * 新增或者修改用户机器人配置线路拆分信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfg", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgInfo> saveUserAiCfg(@RequestBody UserAiCfgInfo userAiCfgInfo){
		if(userAiCfgInfo == null
				|| StrUtils.isEmpty(userAiCfgInfo.getUserId())
				|| StrUtils.isEmpty(userAiCfgInfo.getTemplateIds())
				|| userAiCfgInfo.getAiNum() == null
				|| userAiCfgInfo.getAiNum() <=0
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		UserAiCfgInfo cfgInfo = iUserAiCfgService.userAiCfgChange(null,userAiCfgInfo);
		return Result.ok(cfgInfo);
	}
	
	
	/**
	 * 删除用户机器人配置线路拆分信息
	 * @param userId 要删除的用户编号
	 * @param id 要删除的数据id
	 * @return
	 */
	@RequestMapping(value = "/delUserCfg", method = RequestMethod.POST)
	public Result.ReturnData delUserCfg(@RequestParam(value="userId",required=true)String userId,@RequestParam(value="id",required=true)String id){
		if(StrUtils.isEmpty(userId) && StrUtils.isEmpty(id)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		iUserAiCfgService.delUserCfg(userId, id);
		return Result.ok();
	}
	
}
