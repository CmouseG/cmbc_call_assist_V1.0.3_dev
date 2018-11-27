package com.guiji.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.util.AuthUtil;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	@Autowired
	private IRobotRemote iRobotRemote;
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
	
	
	public void changePassword(String newPass,String oldPass,@RequestHeader Long userId) throws CheckConditionException{
		newPass=AuthUtil.encrypt(newPass);
		oldPass=AuthUtil.encrypt(oldPass);
		SysUser sysUser =mapper.selectByPrimaryKey(userId);
		if(sysUser!=null&&sysUser.getPassword().equals(oldPass)){
			sysUser.setPassword(newPass);
			sysUser.setUpdateTime(new Date());
			mapper.updateByPrimaryKeySelective(sysUser);
		}else{
			throw new CheckConditionException("00010006");
		}
	}
	
	public void updateUserData(SysUser user){
		mapper.updateByPrimaryKeySelective(user);
	}
	
	public Map<String,Object> getUserInfo(Long userId){
		Map<String,Object> result=new HashMap<>();
		SysUser user=mapper.selectByPrimaryKey(userId);
		ReturnData custAccount=iRobotRemote.queryCustAccount(String.valueOf(userId));
		
		result.put("user", user);
		result.put("robot", custAccount.getBody());
		return result;
	}
}
