package com.guiji.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.service.UserService;
import com.guiji.auth.util.AuthUtil;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.UserParamVo;

/**
 * Created by ty on 2018/10/22.
 */
@RestController
public class UserController implements IAuth{
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/user/regist")
	public SysUser insert(SysUser user,Long roleId,@RequestHeader Long userId) throws Exception{
		if(service.existUserName(user)){
			throw new CheckConditionException("00010005");
		}
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		user.setCreateId(userId);
		user.setUpdateId(userId);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		service.insert(user,roleId);
		return user;
	}
	
	@RequestMapping("/user/update")
	public void update(SysUser user,String[] roleId,@RequestHeader Long userId) throws CheckConditionException{
		if(service.existUserName(user)){
			throw new CheckConditionException("00010005");
		}
		user.setUpdateId(userId);
		user.setUpdateTime(new Date());
		user.setPassword(AuthUtil.encrypt(user.getPassword()));
		service.update(user,roleId);
	}
	
	@RequestMapping("/user/delete")
	public void delete(Long id){
		service.delete(id);
	}

	@RequestMapping("/user/getUserByPage")
	public Page<Object> getUserByPage(UserParamVo param){
		return service.getUserByPage(param);
	}
	
	@RequestMapping("/user/getUserById")
	public ReturnData<SysUser> getUserById(Long userId){
		SysUser sysUser=service.getUserById(userId);
		return Result.ok(sysUser);
	}
	
	@RequestMapping("/user/getUserByName")
	public List<Map<String,String>> getUserByName(String username){
		return service.getUserByName(username);
	}
	
	@RequestMapping("/user/changePassword")
	public void changePassword(String newPass,String oldPass,@RequestHeader Long userId) throws CheckConditionException{
		service.changePassword(newPass,oldPass,userId);
	}
	
	@RequestMapping("/user/updateUserData")
	public void updateUserData(SysUser user,@RequestHeader Long userId) {
		user.setId(userId);
		user.setUpdateId(userId);
		user.setUpdateTime(new Date());
		service.updateUserData(user);
	}
	
	@RequestMapping("/user/getUserInfo")
	public Map<String,Object> getUserInfo(@RequestHeader Long userId){
		return service.getUserInfo(userId);
	}
	
	@RequestMapping("/user/changeAccessKey")
	public void changeAccessKey(@RequestHeader Long userId){
		service.changeAccessKey(userId);
	}
	
	@RequestMapping("/user/changeSecretKey")
	public void changeSecretKey(@RequestHeader Long userId){
		service.changeSecretKey(userId);
	}
	
	
	@RequestMapping("/user/getRoleByUserId")
	public ReturnData<List<SysRole>> getRoleByUserId(Long userId){
		return Result.ok(service.getRoleByUserId(userId));
	}
	
}
