package com.guiji.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.UserMapper;
import com.guiji.user.dao.entity.User;


@Service
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(User user){
		mapper.insert(user);
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void changePassword(User user){
		mapper.changePassword(user);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(String id){
		mapper.delete(id);
	}
	
	/**
	 * 修改状态
	 * @param user
	 */
	public void changeStatus(User user){
		mapper.changeStatus(user);
	}
	
	/**
	 * 添加角色
	 * @param userId
	 * @param roleIds
	 */
	public void addRole(String userId,String[] roleIds){
		mapper.addRole(userId,roleIds);
	}
}
