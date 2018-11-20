package com.guiji.cloud.zuul.controller;

import java.io.Serializable;
import java.util.List;

import com.guiji.user.dao.entity.SysRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.cloud.zuul.config.AuthUtil;
import com.guiji.cloud.zuul.service.ZuulService;

@RestController	
@RequestMapping
public class LoginController {
	private final static int SUPER_ADMIN = 0;
	
	@Autowired
	private ZuulService zuulService;
	
	@RequestMapping("login")
	public Serializable login(String username,String password){
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
		session.setAttribute("userId", userId);
		session.setAttribute("isSuperAdmin", isSuperAdmin);
		return session.getId();
	}
	
	@RequestMapping("loginOut")
	public void loginOut(){
		Subject subject =SecurityUtils.getSubject();
		subject.logout();
	}


}
