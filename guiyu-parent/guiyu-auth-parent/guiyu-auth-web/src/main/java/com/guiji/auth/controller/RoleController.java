package com.guiji.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.RoleService;
import com.guiji.user.dao.entity.SysRole;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService service;
	
	@RequestMapping("insert")
	public void insert(SysRole role,String[] menuIds){
		service.insert(role,menuIds);
	}
	
	@RequestMapping("delete")
	public void delete(Long id){
		service.delete(id);
	}
	
	@RequestMapping("update")
	public void update(SysRole role,String[] menuIds){
		service.update(role,menuIds);
	}
	
	@RequestMapping("getRoleById")
	public SysRole getRoleId(Long id){
		return service.getRoleId(id);
	}
	
	@RequestMapping("getRoles")
	public List<SysRole> getRoles(){
		return service.getRoles();
	}
	
}
