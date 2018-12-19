package com.guiji.cloud.zuul.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.cloud.zuul.config.AuthUtil;
import com.guiji.cloud.zuul.service.ZuulService;
import com.guiji.cloud.zuul.token.ApiKeyToken;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;

@RestController	
@RequestMapping
public class LoginController {
	private final static int SUPER_ADMIN = 0;
	
	@Autowired
	private ZuulService zuulService;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@RequestMapping("login")
	public Map<String,Object> login(String username,String password){
		boolean isSuperAdmin = false;
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Session session=subject.getSession();
		Long userId=zuulService.getUserId(username, AuthUtil.encrypt(password));
		List<SysRole> sysRoles=zuulService.getRoleByUserId(userId);
		if (sysRoles != null) {
			for (SysRole sysRole:sysRoles) {
				if (SUPER_ADMIN == sysRole.getSuperAdmin()) {
					isSuperAdmin = true;
					break;
				}
			}
		}
		SysUser sysUser=sysUserMapper.getUserById(userId);
		session.setAttribute("userId", userId);
		session.setAttribute("orgCode", sysUser.getOrgCode());
		session.setAttribute("isSuperAdmin", isSuperAdmin);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("isSuperAdmin",isSuperAdmin);
		map.put("roleId",sysRoles.get(0).getId());
		return map;
	}
	
	
	@RequestMapping("apiLogin")
	public Serializable apiLogin(String accessKey,String secretKey){
		ApiKeyToken token=new ApiKeyToken(accessKey,secretKey);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Session session=subject.getSession();
		Long userId=zuulService.getUserId(accessKey, AuthUtil.encrypt(secretKey));
		session.setAttribute("userId", userId);
		return session.getId();
	}
	
	@RequestMapping("loginOut")
	public void loginOut(){
		Subject subject =SecurityUtils.getSubject();
		subject.logout();
	}


}
