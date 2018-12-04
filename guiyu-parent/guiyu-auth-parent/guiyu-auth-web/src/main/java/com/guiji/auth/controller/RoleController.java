package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.service.RoleService;
import com.guiji.common.model.Page;
import com.guiji.user.dao.entity.SysRole;

@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService service;
	
	@RequestMapping("insert")
	public void insert(SysRole role,String[] menuIds,@RequestHeader Long userId){
		role.setCreateId(userId);
		role.setUpdateId(userId);
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		service.insert(role,menuIds);
	}
	
	@RequestMapping("delete")
	public void delete(Long id){
		service.delete(id);
	}
	
	@RequestMapping("update")
	public void update(SysRole role,String[] menuIds,@RequestHeader Long userId){
		role.setUpdateId(userId);
		role.setUpdateTime(new Date());
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
	
	@RequestMapping("/getRoleByPage")
	public Page<SysRole> getRoleByPage(Page<SysRole> page){
		service.getRoleByPage(page);
		return page;
	}
	
	@RequestMapping("getRoleByName")
	public List<SysRole> getRoleByName(String name){
		return service.getRoleByName(name);
	}
	
}
