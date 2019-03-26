package com.guiji.auth.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.user.dao.SysUserExtMapper;
import com.guiji.user.dao.entity.*;
import com.guiji.utils.RedisUtil;
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

	@Autowired
	private SysUserExtMapper sysUserExtMapper;

	@Autowired
	private INoticeSetting noticeSetting;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private LineMarketRemote lineMarketRemote;

	private static final String REDIS_USER_BY_ID = "REDIS_USER_BY_USERID_";

	private static final String REDIS_ROLE_BY_USERID = "REDIS_ROLE_BY_USERID_";

	private static final String REDIS_ORG_BY_USERID = "REDIS_ORG_BY_USERID_";
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(SysUser user,Long roleId){
        if (roleId == 4) {
			String orgCode = organizationService.getSubOrgCode(user.getOrgCode());
			user.setOrgCode(orgCode);
		}

		user.setOrgCode(user.getOrgCode() + ".");
		user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
		mapper.insertSelective(user);
		mapper.insertUserRole(user.getId(),roleId);
		mapper.addUserExt(user.getId());
		noticeSetting.addNoticeSettingReceiver(user.getId());
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void update(SysUser user,Long roleIds){
		user.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(user);
		mapper.addRole(user.getId(),roleIds);
		redisUtil.del(REDIS_USER_BY_ID+user.getId());
		redisUtil.del(REDIS_ROLE_BY_USERID+user.getId());
		redisUtil.del(REDIS_ORG_BY_USERID+user.getId());
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Long id){
		mapper.deleteByPrimaryKey(id);
		redisUtil.del(REDIS_USER_BY_ID+id);
		redisUtil.del(REDIS_ROLE_BY_USERID+id);
		redisUtil.del(REDIS_ORG_BY_USERID+id);
	}

	public Long getUserId(String username,String password){
		return mapper.getUserId( username, password);
	}
	
	public SysUser getUserById(Long id){
		SysUser sysUser = (SysUser) redisUtil.get(REDIS_USER_BY_ID+id);
		if (sysUser == null) {
			sysUser = mapper.getUserById(id);
			redisUtil.set(REDIS_USER_BY_ID+id,sysUser);
		}
		return sysUser;
	}
	
	public List<Map<String,String>> getUserByName(String userName){
		return mapper.getUserByName(userName);
	}
	
	public List<SysRole> getRoleByUserId(Long id){
		List<SysRole> sysRoleList = (List<SysRole>) redisUtil.get(REDIS_ROLE_BY_USERID+id);
		if (sysRoleList == null) {
			sysRoleList = mapper.getRoleByUserId(id);
			redisUtil.set(REDIS_ROLE_BY_USERID+id,sysRoleList);
		}
		return sysRoleList;
	}

	public SysOrganization getOrgByUserId(Long userId){
		SysOrganization sysOrganization = (SysOrganization) redisUtil.get(REDIS_ORG_BY_USERID+userId);
		if (sysOrganization == null) {
			SysUser sysUser = mapper.getUserById(userId);
			List<SysRole> sysRoleList = mapper.getRoleByUserId(userId);
			String orgCode = null;
			if (sysUser != null) {
				orgCode = sysUser.getOrgCode().substring(0,sysUser.getOrgCode().lastIndexOf("."));
				if (sysRoleList != null && sysRoleList.size() > 0 && (sysRoleList.get(0).getId() == 4 || sysRoleList.get(0).getId() == 5)) {
					orgCode = orgCode.substring(0,orgCode.lastIndexOf("."));
				}
				sysOrganization = organizationService.getOrgByCode(orgCode);
				//根据组织查询产品系列


			}
			redisUtil.set(REDIS_ORG_BY_USERID+userId,sysOrganization);
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
		//ReturnData<List<LineConcurrent>> callData=iCallManagerOut.getLineInfos(String.valueOf(userId));
		ReturnData<List<SipLineVO>> callData= lineMarketRemote.queryUserSipLineList(String.valueOf(userId));
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

	public List<Object>  selectLikeUserName(UserParamVo param,Long userId){
		SysUser loginUser = mapper.getUserById(userId);
		if (loginUser != null) {
			param.setOrgCode(loginUser.getOrgCode());
		}
		List<Object> userList=mapper.selectLikeUserName(param);
		return userList;
	}

	public List<SysUser> getAllCompanyUser(){
		List<SysUser> sysUserList = mapper.getAllCompanyUser();
		return sysUserList;
	}

    public SysUser getUserByAccess(String accessKey, String secretKey) {

		SysUserExample example =  new SysUserExample();
		example.createCriteria()
				.andAccessKeyEqualTo(accessKey)
				.andSecretKeyEqualTo(secretKey);
		List<SysUser> list = mapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;

    }

	public List<SysUser> getAllUserByOrgCode(String orgCode){
		List<SysUser> sysUserList = mapper.getAllUserByOrgCode(orgCode);
		return sysUserList;
	}

	public List<SysUser> getAllUserByOrgCodeForWeb(String orgCode){
		List<SysUser> sysUserList = mapper.getAllUserByOrgCodeForWeb(orgCode);
		return sysUserList;
	}

	public List<SysUserRoleVo> getAllUserRoleByOrgCode(String orgCode){
		List<SysUserRoleVo> sysUserRoleVoList = new ArrayList<SysUserRoleVo>();
		List<SysUser> sysUserList = mapper.getAllUserByOrgCode(orgCode);
		if (sysUserList != null) {
			for (SysUser sysUser : sysUserList) {
				SysUserRoleVo sysUserRoleVo = new SysUserRoleVo();
				sysUserRoleVo.setSysUser(sysUser);
				if (sysUser.getId() != null) {
					List<SysRole> sysRoleList = getRoleByUserId(sysUser.getId());
					if (sysRoleList != null && !sysRoleList.isEmpty()) {
						sysUserRoleVo.setSysRoleList(sysRoleList);
					}
				}
				sysUserRoleVoList.add(sysUserRoleVo);
			}
		}

		return sysUserRoleVoList;
	}

	public void updateUserExt(SysUserExt sysUserExt) {
		sysUserExt.setUpdateTime(new Date());
		sysUserExtMapper.updateByPrimaryKeySelective(sysUserExt);
	}

	public SysUserExt getUserExtByUserId(Long id){
		return mapper.getSysUserExtByUserId(id);
	}

	public void userBindWechat(Long userId,String weChat,String weChatOpenId) throws UnsupportedEncodingException {
		SysUserExt sysUserExt = new SysUserExt();
		sysUserExt.setUserId(userId);
		sysUserExt.setWechat(URLEncoder.encode(weChat, "utf-8"));
		sysUserExt.setWechatOpenid(weChatOpenId);
		sysUserExt.setWechatStatus(1);//已绑定
		sysUserExt.setUpdateTime(new Date());
		sysUserExtMapper.updateByUserId(sysUserExt);
		noticeSetting.addWeixinNoticeSettingReceiver(userId);
	}

	public void userUnBindWechat(Long userId) {
		SysUserExt sysUserExt = new SysUserExt();
		sysUserExt.setUserId(userId);
		sysUserExt.setWechatStatus(0);//未绑定
		sysUserExt.setUpdateTime(new Date());
		sysUserExtMapper.updateByUserId(sysUserExt);
	}

	public List<SysUser> getUserByOpenId(String openId)
	{
		return mapper.getUserByOpenId(openId);
	}
}