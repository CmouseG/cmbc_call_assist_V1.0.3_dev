package com.guiji.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guiji.user.dao.SysRoleMapper;
import com.guiji.user.dao.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.auth.util.AuthUtil;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgVO;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.UserParamVo;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	@Autowired
	private IRobotRemote iRobotRemote;
	
	@Autowired
	private ICallManagerOut iCallManagerOut;

	@Autowired
	private OrganizationService organizationService;
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(SysUser user,Long roleId){
        if (roleId == 4) {
			String orgCode = organizationService.getSubOrgCode(user.getOrgCode());
			user.setOrgCode(orgCode);
		}
		mapper.insert(user);
		mapper.insertUserRole(user.getId(),roleId);
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void update(SysUser user,Long roleIds){
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
		return mapper.getUserById(id);
	}
	
	public List<Map<String,String>> getUserByName(String userName){
		return mapper.getUserByName(userName);
	}
	
	public List<SysRole> getRoleByUserId(Long id){
		return mapper.getRoleByUserId(id);
	}

	public SysOrganization getOrgByUserId(Long userId){
		SysOrganization sysOrganization = null;
		SysUser sysUser = mapper.getUserById(userId);
		List<SysRole> sysRoleList = mapper.getRoleByUserId(userId);
		String orgCode = null;
		if (sysUser != null) {
			orgCode = sysUser.getOrgCode();
			if (sysRoleList != null && sysRoleList.size() > 0 && sysRoleList.get(0).getId() == 4) {
				orgCode = sysUser.getOrgCode().substring(0,sysUser.getOrgCode().lastIndexOf("."));
			}
			sysOrganization = organizationService.getOrgByCode(orgCode);
		}

		return sysOrganization;
	}
	
	public List<String> getPermByRoleId(Long roleId){
		return mapper.getPermByRoleId(roleId);
	}
	
	public Page<Object>  getUserByPage(UserParamVo param,Long userId){
		SysUser loginUser = mapper.getUserById(userId);
		if (loginUser != null) {
			param.setOrgCode(loginUser.getOrgCode());
		}
		Page<Object> page=new Page<>();
		int count=mapper.countByParamVo(param);
		List<Object> userList=mapper.selectByParamVo(param);
		page.setTotal(count);
		page.setRecords(userList);
		return page;
	}
	
	public boolean existUserName(SysUser user){
		return mapper.existUserName(user);
	}
	
	
	public void changePassword(String newPass,String oldPass,Long userId) throws CheckConditionException{
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
		ReturnData<UserAiCfgVO> custAccount=iRobotRemote.queryCustAccount(String.valueOf(userId));
		ReturnData<List<LineConcurrent>> callData=iCallManagerOut.getLineInfos(String.valueOf(userId));
		result.put("user", user);
		result.put("robot", custAccount.getBody());
		result.put("call", callData.getBody());
		return result;
	}
	
	public String changeAccessKey(Long userId){
		String key=AuthUtil.encryptMd2();
		SysUser record=new SysUser();
		record.setId(userId);
		record.setUpdateId(userId);
		record.setAccessKey(key);
		mapper.updateByPrimaryKeySelective(record);
		return key;
	}
	
	public String changeSecretKey(Long userId){
		String key=AuthUtil.encryptMd2();
		SysUser record=new SysUser();
		record.setId(userId);
		record.setUpdateId(userId);
		record.setSecretKey(key);
		mapper.updateByPrimaryKeySelective(record);
		return key;
	}
	
}
