package com.guiji.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.RoleService;
import com.guiji.user.dao.entity.Role;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService service;
	
	public void insert(Role role){
		service.insert(role);
	}
	
	public void delete(String id){
		service.delete(id);
	}
	
	public void update(Role role){
		service.update(role);
	}
	
	public Role getRoleId(String id){
		return service.getRoleId(id);
	}
	
	public List<Role> getRoles(){
		return service.getRoles();
	}
}
