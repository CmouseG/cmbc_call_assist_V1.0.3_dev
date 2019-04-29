package com.guiji.sms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.sms.common.Constants;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;

@Component
public class AuthUtil
{
	@Autowired
	IAuth auth;
	@Autowired
	IOrg org;
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 根据组织代码获取组织名称
	 */
	public String getOrgNameByOrgCode(String orgCode)
	{
		String orgName = redisUtil.getT(Constants.SMS_ORGNAME+orgCode);
		if(orgName != null) {return orgName;}
		ReturnData<SysOrganization> organization = org.getOrgByCode(orgCode);
		orgName = organization.getBody().getName();
		redisUtil.set(Constants.SMS_ORGNAME+orgCode, orgName, 30*60*1L);
		return orgName;
	}
	
	/**
	 * 根据用户id获取用户名
	 */
	public String getUserNameByUserId(Long userId)
	{
		String userName = redisUtil.getT(Constants.SMS_USERNAME+userId);
		if(userName != null) {return userName;}
		ReturnData<SysUser> user = auth.getUserById(userId);
		userName = user.getBody().getUsername();
		redisUtil.set(Constants.SMS_USERNAME+userId, userName, 30*60*1L);
		return userName;
	}
}
