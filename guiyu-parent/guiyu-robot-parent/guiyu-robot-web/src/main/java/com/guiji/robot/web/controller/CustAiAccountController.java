package com.guiji.robot.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.dao.entity.ext.UserAiCfgQuery;
import com.guiji.robot.dao.ext.UserAiCfgInfoMapperExt;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.UserAiCfgDetailVO;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiTemplateVO;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.UserAiCfgBaseInfoVO;
import com.guiji.robot.service.vo.UserAiCfgQueryCondition;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ControllerUtil;
import com.guiji.robot.util.ListUtil;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;

import ai.guiji.botsentence.api.IBotSentenceProcess;
import ai.guiji.botsentence.api.entity.BotSentenceProcess;
import ai.guiji.botsentence.api.entity.ServerResult;

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
	@Autowired
	ControllerUtil controllerUtil;
	@Autowired
	IBotSentenceProcess iBotSentenceProcess;
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	UserAiCfgInfoMapperExt userAiCfgInfoMapperExt;
	
	/**
	 * 新增或者修改用户机器人配置信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfgBaseInfo", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgBaseInfo> saveUserAiCfgBaseInfo(
			@RequestBody UserAiCfgBaseInfo userAiCfgBaseInfo,
			@RequestHeader Long userId){
		if(userAiCfgBaseInfo == null
				|| StrUtils.isEmpty(userAiCfgBaseInfo.getUserId())
				|| userAiCfgBaseInfo.getAiTotalNum() == null
				|| userAiCfgBaseInfo.getAiTotalNum() < 0
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		if(userId != null) {
			//当前登录用户
			userAiCfgBaseInfo.setCrtUser(String.valueOf(userId));
			userAiCfgBaseInfo.setUpdateUser(String.valueOf(userId));
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
	public Result.ReturnData<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoByUserId(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId, 
			@RequestHeader Boolean isSuperAdmin){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		UserAiCfgBaseInfo userAiCfgBaseInfo = null;
		if(StrUtils.isEmpty(qUserId) && isSuperAdmin) {
			//如果请求参数user是空，且是管理员，才统计全部
			qUserId = null;
			//是超级管理员，显示已经配置的所有用户的总共机器人数量
			Integer totalAiNumInt = userAiCfgInfoMapperExt.countUserAi(new UserAiCfgQuery());
			int totalAiNum = (totalAiNumInt==null)?0:totalAiNumInt;
			userAiCfgBaseInfo = new UserAiCfgBaseInfo();
			userAiCfgBaseInfo.setAiTotalNum(totalAiNum);
		}else {
			if(StrUtils.isEmpty(qUserId)) {
				//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
				qUserId = userId.toString();
			}
			//不是超级管理员，返回当前用户的机器人账户基本信息配置
			userAiCfgBaseInfo = iUserAiCfgService.queryUserAiCfgBaseInfoByUserId(qUserId);
		}
		return Result.ok(userAiCfgBaseInfo);
	}
	
	/**
	 * 查询用户机器人账户基本信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryUserAiCfgBaseInfoByOrgCode", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgBaseInfo> queryUserAiCfgBaseInfoByOrgCode(
			@RequestParam(value="orgCode",required=false)String qOrgCode,
			@RequestHeader String orgCode, 
			@RequestHeader Boolean isSuperAdmin){
		//1、先按是否有传参   2、是否超管  3、使用登录orgcode
		UserAiCfgBaseInfo userAiCfgBaseInfo = new UserAiCfgBaseInfo();
		if(StrUtils.isNotEmpty(qOrgCode)) {
			//如果参数有查某个机构，那么不按默认查
			orgCode = qOrgCode;
		}else if(isSuperAdmin) {
			//如果请求参数user是空，且是管理员，才统计全部
			//是超级管理员，显示已经配置的所有用户的总共机器人数量
			Integer totalAiNumInt = userAiCfgInfoMapperExt.countUserAi(new UserAiCfgQuery());
			int totalAiNum = (totalAiNumInt==null)?0:totalAiNumInt;
			userAiCfgBaseInfo.setAiTotalNum(totalAiNum);
			return Result.ok(userAiCfgBaseInfo);
		}
		List<UserAiCfgBaseInfo> list = iUserAiCfgService.queryUserAiCfgBaseInfoByOrgCode(orgCode);
		int aiTotalNum = 0;
		if(ListUtil.isNotEmpty(list)) {
			for(UserAiCfgBaseInfo cfg:list) {
				aiTotalNum = aiTotalNum + (cfg.getAiTotalNum()==null?0:cfg.getAiTotalNum());
			}
		}
		userAiCfgBaseInfo.setAiTotalNum(aiTotalNum);
		return Result.ok(userAiCfgBaseInfo);
	}
	
	
	/**
	 * 查询用户所属企业下还可以使用的机器人数量
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryOrgAvailableAiNum", method = RequestMethod.POST)
	public Result.ReturnData<Integer> queryOrgAvailableAiNum(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId){
		if(StrUtils.isEmpty(qUserId)) {
			//如果不传查询参数，那么默认查询当前用户
			qUserId = userId.toString();
		}
		int availableAiNum = iUserAiCfgService.queryOrgAvailableAiNum(qUserId);
		return Result.ok(availableAiNum);
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
		List<UserAiCfgBaseInfo> list = page.getRecords();
		if(ListUtil.isNotEmpty(list)) {
			Map<String,String> userMap = new HashMap<String,String>();
			for(UserAiCfgBaseInfo base:list) {
				UserAiCfgBaseInfoVO vo = new UserAiCfgBaseInfoVO();
				BeanUtil.copyProperties(base, vo);
				String uId = base.getUserId();
				//设置用户名称
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
				//设置是否分配了机器人
				UserResourceCache userResourceCache = aiCacheService.getUserResource(uId);
				if(userResourceCache != null && userResourceCache.getAiNum() > 0) {
					//已分配机器人数量
					vo.setAssignNum(userResourceCache.getAiNum());
				}else {
					//默认0
					vo.setAssignNum(0);
				}
				rtnList.add(vo);
			}
		}
		Page<UserAiCfgBaseInfoVO> rtnPage = new Page<UserAiCfgBaseInfoVO>(pageNo,page.getTotalRecord(),rtnList);
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
	@RequestMapping(value = "/queryCustRobotTemplateList", method = RequestMethod.POST)
	public Result.ReturnData<List<AiTemplateVO>> queryCustRobotTemplateList(
			@RequestParam(value="userId",required=false)String qUserId,
			@RequestHeader Long userId){
		if(StrUtils.isEmpty(qUserId) && userId==null) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		} 
		if(StrUtils.isEmpty(qUserId)) {
			//如果查询用户为空，那么查询系统登陆用户，否则查询该用户
			qUserId = userId.toString();
		}
		List<AiTemplateVO> rtnList = new ArrayList<AiTemplateVO>();
		List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(qUserId);
		if(ListUtil.isNotEmpty(list)) {
			//开始获取用户配置的机器人列表
			Set<String> templateSet = new HashSet<String>();
			for(UserAiCfgInfo cfg : list) {
				if(RobotConstants.USER_CFG_STATUS_S == cfg.getStatus()) {
					//只要正常状态数据
					String templateIds = cfg.getTemplateIds();
					if(StrUtils.isNotEmpty(templateIds)) {
						if(StrUtils.isNotEmpty(templateIds)) {
							String[] templateArray = templateIds.split(",");	//多模板逗号分隔
							for(String templateId : templateArray) {
								//放入set,防重
								templateSet.add(templateId);
							}
						}
					}
				}
			}
			//开始设置返回对象list
			if(templateSet != null && !templateSet.isEmpty()) {
				for(String templateId : templateSet) {
					//缓存中没有，重新查询
					ServerResult<List<BotSentenceProcess>> templateData = iBotSentenceProcess.getTemplateById(templateId);
					if(templateData != null && ListUtil.isNotEmpty(templateData.getData())) {
						BotSentenceProcess botSentenceProcess = templateData.getData().get(0);
						if(botSentenceProcess != null) {
							AiTemplateVO aiTemplateVO = new AiTemplateVO();
							aiTemplateVO.setTemplateId(templateId);
							aiTemplateVO.setTemplateName(botSentenceProcess.getTemplateName());
							rtnList.add(aiTemplateVO);
						}else {
							logger.error("查不到对应的话术模板{}",templateId);
						}
					}
				}
				//开始设置tts标志
				if(rtnList != null && !rtnList.isEmpty()) {
					for(AiTemplateVO vo : rtnList) {
						String templateId = vo.getTemplateId();
						HsReplace hsReplace = aiCacheService.queyHsReplace(templateId);
						if(hsReplace!=null && hsReplace.isTemplate_tts_flag()) {
							vo.setTtsFlag(true);
						}else {
							vo.setTtsFlag(false);
						}
					}
				}
			}
		}
		return Result.ok(rtnList);
	}
	
	
	/**
	 * 查询用户正在使用的机器人开户账号明细
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/queryCustAccountForPage/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public Result.ReturnData<Page<UserAiCfgDetailVO>> queryCustAccountForPage(
			 @PathVariable(value="pageNo",required=true)int pageNo,
			 @PathVariable(value="pageSize",required=true)int pageSize,
			 @RequestBody UserAiCfgQueryCondition condition){
		//后端分页查询
		Page<UserAiCfgInfo> page = iUserAiCfgService.queryCustAccountForPage(pageNo, pageSize, condition);
		//处理转为前端需要的VO后返回
		Page<UserAiCfgDetailVO> rtnPage = new Page<UserAiCfgDetailVO>(pageNo,page.getTotalRecord(),controllerUtil.changeUserAiCfg2VO(page.getRecords()));
		return Result.ok(rtnPage);
	}
	
	
	
	/**
	 * 新增或者修改用户机器人配置线路拆分信息明细
	 * @param userAiCfgInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserAiCfg", method = RequestMethod.POST)
	public Result.ReturnData<UserAiCfgInfo> saveUserAiCfg(
			@RequestBody UserAiCfgInfo userAiCfgInfo,
			@RequestHeader Long userId){
		if(userAiCfgInfo == null
				|| StrUtils.isEmpty(userAiCfgInfo.getUserId())
				|| StrUtils.isEmpty(userAiCfgInfo.getTemplateIds())
				|| userAiCfgInfo.getAiNum() == null
				) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		if(userAiCfgInfo.getAiNum()<=0) {
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),"机器人分配数量必须大于0");
		}
		if(userId != null) {
			//当前登录用户
			userAiCfgInfo.setCrtUser(String.valueOf(userId));
			userAiCfgInfo.setUpdateUser(String.valueOf(userId));
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
	public Result.ReturnData delUserCfg(@RequestParam(value="userId",required=true)String userId,@RequestParam(value="id",required=true)int id){
		if(StrUtils.isEmpty(userId) && StrUtils.isEmpty(id)) {
			//必输校验
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		iUserAiCfgService.delUserCfg(userId, id);
		return Result.ok();
	}
	
}
