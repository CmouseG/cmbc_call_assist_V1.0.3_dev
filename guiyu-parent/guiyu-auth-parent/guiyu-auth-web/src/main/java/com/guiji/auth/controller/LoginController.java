package com.guiji.auth.controller;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;

@RestController	
@RequestMapping
public class LoginController {
	
	@RequestMapping("login")
	public Serializable login(String username,String password){
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		Session session=subject.getSession();
		return session.getId();
	}
	
	@RequestMapping("loginOut")
	public void loginOut(){
		Subject subject =SecurityUtils.getSubject();
		subject.logout();
	}
	
	@RequestMapping("noLogin")
	public ReturnData<?> noLogin(){
		return Result.error("0001001");
	}
	
	@RequestMapping("notAuth")
	public ReturnData<?> notAuth(){
		return Result.error("0001002");
	}

}
