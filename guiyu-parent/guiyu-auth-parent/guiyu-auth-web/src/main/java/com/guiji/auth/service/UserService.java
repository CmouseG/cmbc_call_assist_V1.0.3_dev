package com.guiji.auth.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.MenuTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.auth.util.AuthUtil;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.notice.api.INoticeSetting;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.UserAiCfgVO;
import com.guiji.user.dao.SysMenuMapper;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.SysUserExtMapper;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysMenu;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.dao.entity.SysUserExample;
import com.guiji.user.dao.entity.SysUserExt;
import com.guiji.user.vo.UserParamVo;
import com.guiji.utils.LocalCacheUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.vo.SendMsgReqVO;


@Service
public class UserService {

	@Autowired
	private SysUserMapper mapper;
	
	@Autowired
	private IRobotRemote iRobotRemote;
	
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SysUserExtMapper sysUserExtMapper;

	@Autowired
	private INoticeSetting noticeSetting;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	SysMenuMapper sysMenuMapper;
	@Autowired
	PrivilegeService privilegeService;

	@Autowired
	private LineMarketRemote lineMarketRemote;
	
	@Autowired
    private WeChatApi weChatApi;
	
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	
	@Autowired
	AgentGroupChangeService agentGroupChangeService;

	private static final String REDIS_USER_BY_ID = "REDIS_USER_BY_USERID_";

	private static final String REDIS_ROLE_BY_USERID = "REDIS_ROLE_BY_USERID_";

	private static final String REDIS_ORG_BY_USERID = "REDIS_ORG_BY_USERID_";
	/**
	 * 新增用户
	 * @param user
	 */
	public void insert(SysUser user,Long roleId){
		user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
		mapper.insertSelective(user);
		mapper.insertUserRole(user.getId(),roleId);
		mapper.addUserExt(user.getId());
		agentGroupChangeService.bindAgentMembers(user, roleId);
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
	
	public List<SysUser> getUserByName(String userName){
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
			String orgCode = sysUser.getOrgCode();
			sysOrganization = organizationService.getOrgByCode(orgCode);
			redisUtil.set(REDIS_ORG_BY_USERID+userId,sysOrganization);
		}
		return sysOrganization;
	}
	
	public Page<Object>  getUserByPage(UserParamVo param){
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
	
	
	public void changePassword(String newPass,String oldPass,Long userId) throws Exception{
		newPass=AuthUtil.encrypt(newPass);
		oldPass=AuthUtil.encrypt(oldPass);
		SysUser sysUser =mapper.selectByPrimaryKey(userId);
		if(sysUser!=null&&sysUser.getPassword().equals(oldPass)){
			sysUser.setPassword(newPass);
			sysUser.setUpdateTime(new Date());
			mapper.updateByPrimaryKeySelective(sysUser);
		}else{
			throw new GuiyuException("旧密码与原密码不符");
		}
	}
	
	public void updateUserData(SysUser user){
		mapper.updateByPrimaryKeySelective(user);
	}
	
	public Map<String,Object> getUserInfo(Long userId){
		Map<String,Object> result=new HashMap<>();
		SysUser user=mapper.selectByPrimaryKey(userId);
		ReturnData<UserAiCfgVO> custAccount=iRobotRemote.queryCustAccount(String.valueOf(userId));
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

	public List<Object>  selectLikeUserName(UserParamVo param, Long userId, Integer authLevel, String orgCode)
	{
		List<Object> userList=mapper.selectLikeUserName(param, userId.intValue(), authLevel, orgCode);
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
	
	/**
	 * 查询拥有某个角色的用户列表
	 * @param roleId
	 * @return
	 */
	public List<SysUser> queryUserByRoleId(Integer roleId){
		if(roleId != null) {
			SysRoleUserExample userRoleExample = new SysRoleUserExample();
			userRoleExample.createCriteria().andRoleIdEqualTo(roleId).andDelFlagEqualTo(0);
			List<SysRoleUser> roleUserList = sysRoleUserMapper.selectByExample(userRoleExample);
			if(roleUserList!=null && !roleUserList.isEmpty()) {
				List<Long> userList = new ArrayList<Long>();
				for(SysRoleUser roleUser : roleUserList) {
					userList.add(roleUser.getUserId());
				}
				SysUserExample userExample = new SysUserExample();
				userExample.createCriteria().andIdIn(userList).andDelFlagEqualTo(0);
				return mapper.selectByExample(userExample);
			}
		}
		return null;
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
		LocalCacheUtil.del("UserExt_"+sysUserExt.getUserId());
	}

	public SysUserExt getUserExtByUserId(Long id)
	{
		SysUserExt sysUserExt = LocalCacheUtil.getT("UserExt_"+id);
		if(sysUserExt == null){
			sysUserExt = mapper.getSysUserExtByUserId(id);
			LocalCacheUtil.set("UserExt_"+id, sysUserExt, LocalCacheUtil.TEN_MIN);
		}
		return sysUserExt;
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
		SendMsgReqVO sendMsgReqVO = new SendMsgReqVO();
		sendMsgReqVO.setOpenID(getUserExtByUserId(userId).getWechatOpenid());
	    sendMsgReqVO.setTemplateId("4czraHpgK6M0TNjfHVLoxAADaN18g0CefQHsXi2f6Bc"); //解绑模版
	    sendMsgReqVO.addData("first", "您已成功与硅基帐号解除绑定");
	    sendMsgReqVO.addData("keyword1", getUserById(userId).getUsername());
	    sendMsgReqVO.addData("keyword2", "该微信已不能接收到硅基的推送消息");
	    sendMsgReqVO.addData("remark", "如需再绑定，请在个人中心进行绑定");
		weChatApi.send(sendMsgReqVO);
	}
	
	/**
	 * 查询用户权限范围内的菜单或者按钮
	 * @param userId
	 * @param menuType
	 * @return
	 */
	public List<SysMenu> querySysMenuByUser(Integer userId,Integer menuType){
		if(userId!=null && menuType!=null) {
			List<SysRole> roleList = this.getRoleByUserId(Long.valueOf(userId));
			if(roleList!=null && !roleList.isEmpty()) {
				//现在用户只有1个角色
				SysRole sysRole = roleList.get(0);
				List<SysMenu> allMenus = privilegeService.queryMenuTreeByLowId(AuthObjTypeEnum.ROLE.getCode(), sysRole.getId().toString());
				List<SysMenu> buttonList = new ArrayList<SysMenu>();
				Iterator<SysMenu> it = allMenus.iterator();
				while(it.hasNext()){
					SysMenu sysMenu = it.next();
				    if(MenuTypeEnum.BUTTON.getCode()==sysMenu.getType()){
				    	//加入按钮列表
				    	buttonList.add(sysMenu);
				        //从菜单列表中移除
				    	it.remove();
				    }
				}
				if(MenuTypeEnum.MENU.getCode()==menuType) {
					return allMenus;
				}else if(MenuTypeEnum.BUTTON.getCode()==menuType) {
					return buttonList;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 是否坐席人员
	 * @param userId
	 * @return
	 */
	public boolean isAgentUser(Integer userId) {
		if(userId!=null) {
			List<SysRole> roleList = this.getRoleByUserId(Long.valueOf(userId));
			if(roleList!=null && !roleList.isEmpty()) {
				//现在用户只有1个角色
				SysRole sysRole = roleList.get(0);
				List<SysPrivilege> privilegeList = privilegeService.queryPrivilegeListByAuth(sysRole.getId().toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
				if(privilegeList!=null && !privilegeList.isEmpty()) {
					for(SysPrivilege sysPrivilege:privilegeList) {
						if(AuthConstants.MENU_AGENT_MEMBER == Integer.valueOf(sysPrivilege.getResourceId())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取首页账号数
	 */
	public Integer getUserCount(Long userId, Integer authLevel, String orgCode)
	{
		SysUserExample example = new SysUserExample();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(userId);
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(orgCode);
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(orgCode + "%");
		}
		return mapper.countByExample(example);
	}

	public List<SysUser> getAllUserByUserId(Long userId)
	{
		List<SysUser> userList = new ArrayList<>();
		// 根据用户查数据权限等级
		Integer authLevel = mapper.getAuthLevelByUserId(userId);
		if(authLevel == 1){ // 本人
			SysUser user = this.getUserById(userId);
			userList.add(user);
		}else if(authLevel == 2){ // 本组织
			userList = getAllUserByOrgCode(getOrgByUserId(userId).getCode());
		}else if(authLevel == 2) { // 本组织及下级组织
			List<Map> orgMap = organizationService.getSubOrgByAuthLevel(userId,authLevel,getOrgByUserId(userId).getCode());
			for(Map org : orgMap){
				String orgCode = (String) org.get("code");
				List<SysUser> users = getAllUserByOrgCode(orgCode);
				userList.addAll(users);
			}
		}
		return userList;
	}
}