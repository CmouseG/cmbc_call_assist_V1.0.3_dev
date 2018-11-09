package com.guiji.cloud.zuul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.SysUserMapper;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	public Long getUserId(String username,String password){
		return mapper.getUserId( username, password);
	}
	
}
