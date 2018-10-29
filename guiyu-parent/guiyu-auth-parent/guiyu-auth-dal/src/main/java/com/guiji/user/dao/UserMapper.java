package com.guiji.user.dao;

import org.apache.ibatis.annotations.Mapper;

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
	
}
