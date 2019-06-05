package com.guiji.cloud.zuul.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guiji.user.dao.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.SysUserMapper;

@Service
public class ZuulService {

	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysMenuMapper menuMapper;
	
	public Long getUserId(String username,String password){
		return userMapper.getUserIdForLogin(username, password);
	}

	public List<SysRole> getRoleByUserId(Long userId) {
		return userMapper.getRoleByUserId(userId);
	}
	
	public Map<String,String> getAllPermissions(){
		List<Map<String,String>> permList=menuMapper.getAllPermissions();
		Map<String,String> result=new HashMap<>();
		permList.forEach((item)->{
			result.put(item.get("url"), item.get("permission"));
		});
		
		return result;
	}
	
	public String getPermissionsByUrl(String url){
		return menuMapper.getPermissionsByUrl(url);
	}

	public Long checkEffective(Long userId){
		return userMapper.checkEffective(userId);
	}
	
}
