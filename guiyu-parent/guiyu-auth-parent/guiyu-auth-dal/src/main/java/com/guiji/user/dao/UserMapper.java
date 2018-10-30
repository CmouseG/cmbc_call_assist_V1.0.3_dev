package com.guiji.user.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.guiji.user.dao.entity.User;

@Mapper
public interface UserMapper {

	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(User user);
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void changePassword(User user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 修改状态
	 * @param user
	 */
	public void changeStatus(User user);
	
	
	/**
	 * 获取用户的权限
	 */
	public Set<String> getPermissions(String principal);
	
	/**
	 * 获取用户的密码
	 */
	public String getPassword(String principal);
	
	
	public void addRole(@Param("userId")String userId,@Param("roleIds")String[] roleIds);
	
}
