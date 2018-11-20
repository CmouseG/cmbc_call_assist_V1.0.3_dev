package com.guiji.auth.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExample;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(SysUser user,Long roleId){
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		mapper.insert(user);
		mapper.insertUserRole(user.getId(),roleId);
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void update(SysUser user,String[] roleIds){
		user.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(user);
		mapper.addRole(user.getId(),roleIds);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
	}
	
	public Long getUserId(String username,String password){
		return mapper.getUserId( username, password);
	}
	
	public SysUser getUserById(Long id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public List<Map<String,String>> getUserByName(String userName){
		return mapper.getUserByName(userName);
	}
	
	public List<SysRole> getRoleByUserId(Long id){
		return mapper.getRoleByUserId(id);
	}
	
	public List<String> getPermByRoleId(Long roleId){
		return mapper.getPermByRoleId(roleId);
	}
	
	public void getUserByPage(Page<Map<String,String>> page){
		int count=mapper.count();
		List<Map<String,String>> userList=mapper.getUsersByPage(page);
		page.setTotal(count);
		page.setRecords(userList);
	}
	
	public boolean existUserName(SysUser user){
		return mapper.existUserName(user);
	}
	
}
