package com.guiji.cloud.zuul.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.guiji.cloud.zuul.token.ApiKeyToken;
import com.guiji.user.dao.SysUserMapper;


@Component
public class ApiJdbcRealm extends AuthorizingRealm{

	@Autowired
	private SysUserMapper userMapper;
	
	public boolean supports(AuthenticationToken token){
		return (token instanceof ApiKeyToken);
	}
	
	/**
	 * 权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<Permission> set = new HashSet<Permission>();
		AllPermission permission=new AllPermission();
		set.add(permission);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setObjectPermissions(set);
		return info;
	}

	/**
	 * 凭证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String accessKey = upToken.getUsername();
		if (accessKey == null) {
			throw new AccountException("Null accessKey are not allowed by this realm.");
		}
		
		String secretKey =userMapper.getSecretKey(accessKey);
		
		if(StringUtils.isEmpty(secretKey)){
			throw new AuthenticationException("no hava this user");
		}
		
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(secretKey, secretKey.toCharArray(), getName());
		return info;
	}

}
