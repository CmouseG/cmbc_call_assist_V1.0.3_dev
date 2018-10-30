package com.guiji.auth.controller;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.UserService;
import com.guiji.user.dao.entity.User;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
@RequestMapping("user")
public class UserController{
	
	@Autowired
	private UserService service;
	
	@RequestMapping("regist")
	public void insert(User user){
		SimpleHash hash=new SimpleHash(Sha512Hash.ALGORITHM_NAME,user.getUsername());
		user.setPassword(hash.toString());
		service.insert(user);
	}
	
	@RequestMapping("changePassword")
	public void changePassword(User user){
		SimpleHash hash=new SimpleHash(Sha512Hash.ALGORITHM_NAME,user.getUsername());
		user.setPassword(hash.toString());
		service.changePassword(user);
	}
	
	@RequestMapping("delete")
	public void delete(String id){
		service.delete(id);
	}
	
	@RequestMapping("changeStatus")
	public void changeStatus(User user){
		service.changeStatus(user);
	}
	
	@RequestMapping("addRoles")
	public void addRole(String userId,String[] roleIds){
		service.addRole(userId,roleIds);
	}
	
}
