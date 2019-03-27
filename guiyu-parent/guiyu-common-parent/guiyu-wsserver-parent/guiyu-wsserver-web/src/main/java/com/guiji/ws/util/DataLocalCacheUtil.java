package com.guiji.ws.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.component.result.Result.ReturnData;
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
	
	/**
	 * 查询用户所属真实企业信息缓存 
	 * @param userId
	 * @return
	 */
	public SysOrganization queryUserRealOrg(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			//先从缓存中取
			SysOrganization org = LocalCacheUtil.getT("KEY_USER_ORG"+userId);
			if(org!=null) {
				//缓存中有，直接取
				return org;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<SysOrganization> orgData = iAuth.getOrgByUserId(Long.valueOf(userId));
				if(orgData != null && orgData.getBody()!=null) {
					//内存1个小时有效
					LocalCacheUtil.set("KEY_USER_ORG"+userId, orgData.getBody(), LocalCacheUtil.TEN_MIN);
					return orgData.getBody();
				}else {
					log.error("用户ID:{},查询不到用户所属企业信息，返回：{}",userId,orgData);
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
	 * 校验用户是否坐席
	 * @param userId
	 * @return
	 */
	public boolean isAgentUser(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			//先从缓存中取
			Boolean isAgent = LocalCacheUtil.getT("KEY_USER_AGENT"+userId);
			if(isAgent!=null) {
				//缓存中有，直接取
				return isAgent;
			}else{
				//缓存中没有,重新查，并放入内存
				ReturnData<Boolean> isAgentData = iAuth.isAgentUser(Integer.valueOf(userId));
				if(isAgentData != null && isAgentData.getBody()!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER_AGENT"+userId, isAgentData.getBody(), LocalCacheUtil.TEN_MIN);
					return isAgentData.getBody();
				}else {
					log.error("用户ID:{},查询不到用户是否坐席信息，返回：{}",userId,isAgentData);
				}
			}
		}
		return false;
	}
	
}
