package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: AgentGroupChangeService 
* @Description: 坐席组坐席人员变更通知服务
* @auth weiyunbo
* @date 2019年3月26日 下午4:48:55 
* @version V1.0  
*/
@Slf4j
@Service
public class AgentGroupChangeService {
	@Autowired
	UserService userService; 
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	PrivilegeService privilegeService;
	@Autowired
	private QueueSender queueSender;
	
	/**
	 * 新增某些权限，校验是否需要调用呼叫中心新增坐席人员
	 * 只有新增角色绑定关系时，将该角色的所有用户都发送给呼叫中心新增坐席
	 * @param authType
	 * @param authId
	 * @param resourceType
	 * @param resourceIdList
	 */
	public void bindAgentMembers(Integer authType,String authId,Integer resourceType,List<String> resourceIdList) {
		if(AuthObjTypeEnum.ROLE.getCode()==authType 
				&& ResourceTypeEnum.MENU.getCode()==resourceType 
				&& resourceIdList!=null && !resourceIdList.isEmpty()
				&& resourceIdList.contains(String.valueOf(AuthConstants.MENU_AGENT_MEMBER))) {
			//如果新绑定了角色-菜单，且菜单中有坐席，那么将该拥有该角色的所有人发给转人工服务
			List<SysUser> agentUseList = userService.queryUserByRoleId(Integer.valueOf(authId));
			if(agentUseList!=null && !agentUseList.isEmpty()) {
				List<AgentMembrVO> agentMembers = new ArrayList<AgentMembrVO>();
				for(SysUser sysUser : agentUseList) {
					AgentMembrVO vo = new AgentMembrVO();
					vo.setCustomerId(sysUser.getId());
					vo.setCustomerName(sysUser.getUsername());
					vo.setLoginAccount(sysUser.getUsername());
					vo.setOrgCode(sysUser.getOrgCode());
					agentMembers.add(vo);
				}
				log.info("调用转人工服务,通知增加坐席：{}",agentMembers);
				queueSender.send("SyncAgentMembers.direct.Auth", JsonUtils.bean2Json(agentMembers));
			}
		}
	}
	
	/**
	 * 为用户绑定菜单
	 * @param userId
	 * @param roleId
	 */
	public void bindAgentMembers(SysUser sysUser,Long roleId) {
		if(sysUser!=null && roleId!=null) {
			List<SysPrivilege> privilegeList = privilegeService.queryPrivilegeListByAuth(roleId.toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
			if(privilegeList!=null && !privilegeList.isEmpty()) {
				for(SysPrivilege privilege : privilegeList) {
					if((AuthConstants.MENU_AGENT_MEMBER+"").equals(privilege.getResourceId())) {
						List<AgentMembrVO> agentMembers = new ArrayList<AgentMembrVO>();
						AgentMembrVO vo = new AgentMembrVO();
						vo.setCustomerId(sysUser.getId());
						vo.setCustomerName(sysUser.getUsername());
						vo.setLoginAccount(sysUser.getUsername());
						vo.setOrgCode(sysUser.getOrgCode());
						agentMembers.add(vo);
						log.info("调用转人工服务,通知增加坐席：{}",agentMembers);
						queueSender.send("SyncAgentMembers.direct.Auth", JsonUtils.bean2Json(agentMembers));
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 删除关系时为用户解除绑定-条件1
	 * @param authType
	 * @param authId
	 * @param resourceType
	 * @param resourceIdList
	 */
	public void unBindAgentMembers(Integer authType,String authId,Integer resourceType,List<String> resourceIdList) {
		if(AuthObjTypeEnum.ROLE.getCode()==authType 
				&& ResourceTypeEnum.MENU.getCode()==resourceType 
				&& resourceIdList!=null && !resourceIdList.isEmpty()
				&& resourceIdList.contains(String.valueOf(AuthConstants.MENU_AGENT_MEMBER))) {
			//如果新绑定了角色-菜单，且菜单中有坐席，那么将该拥有该角色的所有人发给转人工服务
			SysRoleUserExample userRoleExample = new SysRoleUserExample();
			userRoleExample.createCriteria().andRoleIdEqualTo(Integer.valueOf(authId)).andDelFlagEqualTo(0);
			List<SysRoleUser> roleUserList = sysRoleUserMapper.selectByExample(userRoleExample);
			if(roleUserList!=null && !roleUserList.isEmpty()) {
				List<Long> userList = new ArrayList<Long>();
				for(SysRoleUser roleUser : roleUserList) {
					userList.add(roleUser.getUserId());
				}
				log.info("调用转人工服务,通知删除坐席：{}",userList);
				queueSender.send("DelAgentMembers.direct.Auth", JsonUtils.bean2Json(userList));
			}
		}
	}

	public void updateBindAgentMembers(SysUser user, Long oldRoleId, Long newRoleId)
	{
		if(user==null || oldRoleId ==null || newRoleId==null) {return;}
		List<SysPrivilege> oldPrivilegeList = privilegeService.queryPrivilegeListByAuth(oldRoleId.toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
		List<SysPrivilege> newPrivilegeList = privilegeService.queryPrivilegeListByAuth(newRoleId.toString(), AuthObjTypeEnum.ROLE.getCode(), ResourceTypeEnum.MENU.getCode());
		List<String> oldResourceList = oldPrivilegeList.stream().map(privilege -> privilege.getResourceId()).collect(Collectors.toList());
		List<String> newResourceList = newPrivilegeList.stream().map(privilege -> privilege.getResourceId()).collect(Collectors.toList());
		if(oldResourceList.contains(AuthConstants.MENU_AGENT_MEMBER+"") && !newResourceList.contains(AuthConstants.MENU_AGENT_MEMBER+"")) //解绑
		{
			List<Long> userList = new ArrayList<Long>();
			userList.add(user.getId());
			queueSender.send("DelAgentMembers.direct.Auth", JsonUtils.bean2Json(userList));
		}
		if(!oldResourceList.contains(AuthConstants.MENU_AGENT_MEMBER+"") && newResourceList.contains(AuthConstants.MENU_AGENT_MEMBER+"")) //绑定
		{
			List<AgentMembrVO> agentMembers = new ArrayList<AgentMembrVO>();
			AgentMembrVO vo = new AgentMembrVO();
			vo.setCustomerId(user.getId());
			vo.setCustomerName(user.getUsername());
			vo.setLoginAccount(user.getUsername());
			vo.setOrgCode(user.getOrgCode());
			agentMembers.add(vo);
			queueSender.send("SyncAgentMembers.direct.Auth", JsonUtils.bean2Json(agentMembers));
		}
	}
	
	
	
}
