package com.guiji.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.UserService;
import com.guiji.auth.util.AuthUtil;
import com.guiji.common.model.Page;
import com.guiji.user.dao.entity.SysUser;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
@RequestMapping("user")
public class UserController{
	
	@Autowired
	private UserService service;
	
	@RequestMapping("regist")
	public void insert(SysUser user,Long roleId) throws Exception{
		if(service.existUserName(user)){
			throw new CheckConditionException("00010005");
		}
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		service.insert(user,roleId);
	}
	
	@RequestMapping("update")
	public void update(SysUser user,String[] roleId) throws CheckConditionException{
		if(service.existUserName(user)){
			throw new CheckConditionException("00010005");
		}
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		service.update(user,roleId);
	}
	
	@RequestMapping("delete")
	public void delete(Long id){
		service.delete(id);
	}
	
	@RequestMapping("getUserId")
	public Long getUserId(){
		return (Long) SecurityUtils.getSubject().getSession().getAttribute("userId");
	}
	
	@RequestMapping("getUserByPage")
	public Page<SysUser> getUserByPage(Page<SysUser> page){
		service.getUserByPage(page);
		return page;
	}
	
}
