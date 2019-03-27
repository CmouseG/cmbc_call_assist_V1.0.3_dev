package com.guiji.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.constants.AuthConstants;
import com.guiji.auth.enm.AuthObjTypeEnum;
import com.guiji.auth.enm.ResourceTypeEnum;
import com.guiji.component.result.Result;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.user.dao.SysRoleUserMapper;
import com.guiji.user.dao.entity.SysPrivilege;
import com.guiji.user.dao.entity.SysRoleUser;
import com.guiji.user.dao.entity.SysRoleUserExample;
import com.guiji.user.dao.entity.SysUser;

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
	IAgentGroup iAgentGroup;
	@Autowired
	UserService userService; 
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	PrivilegeService privilegeService;
	
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
				Result.ReturnData rspData = iAgentGroup.syncAgentMembers(agentMembers);
				log.info("调用转人工服务,通知增加坐席,返回数据：{}",rspData);
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
						Result.ReturnData rspData = iAgentGroup.syncAgentMembers(agentMembers);
						log.info("调用转人工服务,通知增加坐席,返回数据：{}",rspData);
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
				Result.ReturnData rspData = iAgentGroup.delAgentMembers(userList);
				log.info("调用转人工服务,通知删除坐席,返回数据：{}",rspData);
			}
		}
	}
	
	
	public void unBindAgentMembers(Integer userId,Long roleId) {
		
	}
	
}
