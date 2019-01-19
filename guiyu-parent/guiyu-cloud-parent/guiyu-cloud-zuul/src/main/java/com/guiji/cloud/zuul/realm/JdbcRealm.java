package com.guiji.cloud.zuul.realm;

import com.guiji.cloud.zuul.entity.JwtToken;
import com.guiji.user.dao.SysUserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Set;


@Component
public class JdbcRealm extends AuthorizingRealm{

	@Autowired
	private SysUserMapper userMapper;
	

	@PostConstruct
	public void initConstruct(){
	    HashedCredentialsMatcher matcher=new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
	    setCredentialsMatcher(matcher);
	}
	/**
	 * 注意坑点 : 必须重写此方法，不然Shiro会报错
	 * 因为创建了 JWTToken 用于替换Shiro原生 token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}
	
	/**
	 * 权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//null usernames are invalid
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		String username = (String) getAvailablePrincipal(principals);

		Set<String> permissions = userMapper.getPermissions(username);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		return info;
	}

	/**
	 * 凭证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}
		String password=userMapper.getPassword(username);
		
		if(StringUtils.isEmpty(password)){
			throw new AuthenticationException("no hava this user");
		}
		
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
		return info;

	}

}
