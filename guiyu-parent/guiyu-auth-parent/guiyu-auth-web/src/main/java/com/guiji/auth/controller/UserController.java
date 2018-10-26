package com.guiji.auth.controller;

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
	
	@RequestMapping("insert")
	public void insert(User user){
		service.insert(user);
	}
	
	@RequestMapping("changePassword")
	public void changePassword(User user){
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
}
