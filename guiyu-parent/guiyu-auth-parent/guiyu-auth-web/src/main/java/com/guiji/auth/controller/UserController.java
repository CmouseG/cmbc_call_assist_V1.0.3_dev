package com.guiji.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public void insert(SysUser user,Long roleId){
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		service.insert(user,roleId);
	}
	
	@RequestMapping("changePassword")
	public void changePassword(SysUser user){
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		service.changePassword(user);
	}
	
	@RequestMapping("delete")
	public void delete(Long id){
		service.delete(id);
	}
	
	@RequestMapping("changeStatus")
	public void changeStatus(SysUser user){
		service.changeStatus(user);
	}
	
	@RequestMapping("addRoles")
	public void addRole(String userId,String[] roleIds){
		service.addRole(userId,roleIds);
	}

	@RequestMapping("getUserByPage")
	public Page<SysUser> getUserByPage(Page<SysUser> page){
		service.getUserByPage(page);
		return page;
	}
	
}
