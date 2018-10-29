package com.guiji.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.RoleMapper;
import com.guiji.user.dao.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleMapper mapper;
	
	public void insert(Role role){
		mapper.insert(role);
	}
	
	public void delete(String id){
		mapper.delete(id);
	}
	
	public void update(Role role){
		mapper.update(role);
	}
	
	public Role getRoleId(String id){
		return mapper.getRoleId(id);
	}
	
	public List<Role> getRoles(){
		return mapper.getRoles();
	}
}
