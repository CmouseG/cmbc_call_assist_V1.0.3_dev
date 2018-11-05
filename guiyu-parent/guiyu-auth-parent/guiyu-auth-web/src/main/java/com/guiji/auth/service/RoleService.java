package com.guiji.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.entity.SysRole;

@Service
public class RoleService {
	
	@Autowired
	private SysRoleMapper mapper;
	
	public void insert(SysRole role){
		mapper.insert(role);
	}
	
	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
	}
	
	public void update(SysRole role){
		mapper.updateByPrimaryKeySelective(role);
	}
	
	public SysRole getRoleId(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<SysRole> getRoles(){
		return mapper.getRoles();
	}
	
	public void addMenus(String roleId,String[] menuIds){
		mapper.addMenus(roleId,menuIds);
	}
}
