package com.guiji.cloud.zuul.controller;

import java.io.Serializable;

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
	
	@Autowired
	private ZuulService zuulService;
	
	@RequestMapping("login")
	public Serializable login(String username,String password){
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Session session=subject.getSession();
		Long userId=zuulService.getUserId(username, AuthUtil.encrypt(password));
		session.setAttribute("userId", userId);
		return session.getId();
	}
	
	@RequestMapping("loginOut")
	public void loginOut(){
		Subject subject =SecurityUtils.getSubject();
		subject.logout();
	}


}
