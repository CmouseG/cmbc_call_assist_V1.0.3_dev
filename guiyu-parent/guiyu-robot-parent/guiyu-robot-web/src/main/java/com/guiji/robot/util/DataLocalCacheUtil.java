package com.guiji.robot.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.auth.model.UserAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: DataLocalCacheUtil 
* @Description: 本地业务数据缓存工具服务
* @date 2018年12月4日 上午10:34:04 
* @version V1.0  
*/
@Slf4j
@Service
public class DataLocalCacheUtil {
	@Autowired
	IAuth iAuth;
	@Autowired
	IOrg iOrg;
	@Autowired
	AiCacheService aiCacheService;
	
	
	/**
	 * 查询用户数据查询权限
	 * @param userId
	 * @return
	 */
	public UserAuth queryUserAuth(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			//先从缓存中取
			UserAuth ua = LocalCacheUtil.getT("KEY_USER_AUTH"+userId);
			if(ua!=null) {
				//缓存中有，直接取
				return ua;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<UserAuth> userAuthData = iAuth.queryUserDataAuth(Long.valueOf(userId));
				if(userAuthData != null && userAuthData.getBody()!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER_AUTH"+userId, userAuthData.getBody(), LocalCacheUtil.TEN_MIN);
					return userAuthData.getBody();
				}else {
					log.error("用户ID:{},查询不到用户数据查询权限，默认查询本人",userId);
					ua = new UserAuth();
					ua.setAuthLevel(RobotConstants.USER_DATA_AUTH_ME);
					return ua;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 查询企业编号查询企业信息
	 * @param userId
	 * @return
	 */
	public SysOrganization queryOrgByCode(String orgCode) {
		if(StrUtils.isNotEmpty(orgCode)) {
			//先从缓存中取
			SysOrganization org = LocalCacheUtil.getT("KEY_ORG"+orgCode);
			if(org!=null) {
				//缓存中有，直接取
				return org;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<SysOrganization> orgData = iOrg.getOrgByCode(orgCode);
				if(orgData != null && orgData.getBody()!=null) {
					//内存1个小时有效
					LocalCacheUtil.set("KEY_ORG"+orgCode, orgData.getBody(), LocalCacheUtil.TEN_MIN);
					return orgData.getBody();
				}else {
					log.error("企业CODE:{},查询不到企业信息，返回：{}",orgCode,orgData);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public SysUser queryUser(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			//先从缓存中取
			SysUser sysUser = LocalCacheUtil.getT("KEY_USER"+userId);
			if(sysUser!=null) {
				//缓存中有，直接取
				return sysUser;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<SysUser> userData = iAuth.getUserById(Long.valueOf(userId));
				if(userData != null && userData.getBody()!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER"+userId, userData.getBody(), LocalCacheUtil.TEN_MIN);
					return userData.getBody();
				}else {
					log.error("用户ID:{},查询不到用户信息，返回：{}",userId,userData);
				}
			}
		}
		return null;
	}
	
	/**
	 * 查询用户所有角色信息
	 * @param userId
	 * @return
	 */
	public List<SysRole> queryUserRole(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			//先从缓存中取
			List<SysRole> roleList = LocalCacheUtil.getT("KEY_USER_ROLE"+userId);
			if(roleList!=null) {
				//缓存中有，直接取
				return roleList;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<List<SysRole>> userRoleData = iAuth.getRoleByUserId(Long.valueOf(userId));
				if(userRoleData != null && userRoleData.getBody()!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER_ROLE"+userId, userRoleData.getBody(), LocalCacheUtil.TEN_MIN);
					return userRoleData.getBody();
				}else {
					log.error("用户ID:{},查询不到用户角色信息，返回：{}",userId,userRoleData);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 查询模板
	 * @param userId
	 * @return
	 */
	public HsReplace queryTemplate(String templateId) {
		if(StrUtils.isNotEmpty(templateId)) {
			//先从缓存中取
			HsReplace hsReplace = LocalCacheUtil.getT("KEY_TEMPLATE"+templateId);
			if(hsReplace!=null) {
				//缓存中有，直接取
				return hsReplace;
			}else{
				//缓存中没有,重新查，并放入内存
				hsReplace = aiCacheService.queyHsReplace(templateId);
				if(hsReplace != null) {
					//缓存1小时有效
					LocalCacheUtil.set("KEY_TEMPLATE"+templateId, hsReplace, LocalCacheUtil.TEN_MIN);
					return hsReplace;
				}else {
					log.error("话术模板ID:{},查询不到模板信息",templateId);
				}
			}
		}
		return null;
	}
}
