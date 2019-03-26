package com.guiji.auth.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.model.UserAuth;
import com.guiji.auth.service.OrganizationService;
import com.guiji.auth.service.UserService;
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
	UserService userService;
	@Autowired
	OrganizationService organizationService;
	
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
				SysOrganization organization = organizationService.getOrgByCode(orgCode);
				if(organization != null) {
					//内存10分支有效
					LocalCacheUtil.set("KEY_ORG"+orgCode, organization, LocalCacheUtil.TEN_MIN);
					return organization;
				}else {
					log.error("企业CODE:{},查询不到企业信息，返回：{}",orgCode,organization);
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
	public SysUser queryUser(Integer userId) {
		if(userId!=null) {
			//先从缓存中取
			SysUser sysUser = LocalCacheUtil.getT("KEY_USER"+userId);
			if(sysUser!=null) {
				//缓存中有，直接取
				return sysUser;
			}else{
				//缓存中没有,重新查，并放入内存
				sysUser = userService.getUserById(Long.valueOf(userId));
				if(sysUser!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER"+userId, sysUser, LocalCacheUtil.TEN_MIN);
					return sysUser;
				}else {
					log.error("用户ID:{},查询不到用户信息，返回：{}",userId,sysUser);
				}
			}
		}
		return null;
	}
	
	/**
	 * 查询用户数据查询权限
	 * @param userId
	 * @return
	 */
	public UserAuth queryUserAuth(Integer userId) {
		if(userId!=null) {
			//先从缓存中取
			UserAuth userAuth = LocalCacheUtil.getT("KEY_USER_AUTH"+userId);
			if(userAuth!=null) {
				//缓存中有，直接取
				return userAuth;
			}else{
				//缓存中没有,重新查，并放入内存
				userAuth = new UserAuth(); 
				SysUser sysUser = this.queryUser(userId);
				userAuth.setUserId(userId);
				userAuth.setOrgCode(sysUser.getOrgCode()); //组织代码
				List<SysRole> roleList = userService.getRoleByUserId(Long.valueOf(userId));
				if(roleList!=null && !roleList.isEmpty()) {
					//现在一个用户只有1个角色
					Integer authLevel = roleList.get(0).getDataAuthLevel();
					if(authLevel==null) {
						userAuth.setAuthLevel(1); //默认1-查询个人
					}else {
						userAuth.setAuthLevel(authLevel);
					}
				}else {
					userAuth.setAuthLevel(1); //默认1-查询个人
				}
				if(userAuth!=null) {
					//内存10分钟有效
					LocalCacheUtil.set("KEY_USER_AUTH"+userId, userAuth, LocalCacheUtil.TEN_MIN);
					return userAuth;
				}else {
					log.error("用户ID:{},查询不到用户信息，返回：{}",userId,userAuth);
				}
			}
		}
		return null;
	}
	
	
	
}
