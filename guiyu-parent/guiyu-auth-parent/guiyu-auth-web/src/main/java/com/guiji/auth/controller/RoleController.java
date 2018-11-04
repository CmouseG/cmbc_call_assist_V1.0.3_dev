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
	public void insert(SysRole role){
		service.insert(role);
	}
	
	@RequestMapping("delete")
	public void delete(Long id){
		service.delete(id);
	}
	
	@RequestMapping("update")
	public void update(SysRole role){
		service.update(role);
	}
	
	@RequestMapping("getRoleId")
	public SysRole getRoleId(Long id){
		return service.getRoleId(id);
	}
	
	@RequestMapping("getRoles")
	public List<SysRole> getRoles(){
		return service.getRoles();
	}
	
	@RequestMapping("addMenus")
	public void addMenus(String roleId,String[] menuIds){
		service.addMenus(roleId,menuIds);
	}
}
