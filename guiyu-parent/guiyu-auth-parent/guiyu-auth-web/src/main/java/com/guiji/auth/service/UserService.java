package com.guiji.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysUser;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(SysUser user){
		mapper.insert(user);
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void changePassword(SysUser user){
		mapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 修改状态
	 * @param user
	 */
	public void changeStatus(SysUser user){
		mapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 添加角色
	 * @param userId
	 * @param roleIds
	 */
	public void addRole(String userId,String[] roleIds){
		mapper.addRole(userId,roleIds);
	}
	
	public Long getUserId(String username,String password){
		return mapper.getUserId( username, password);
	}
}
