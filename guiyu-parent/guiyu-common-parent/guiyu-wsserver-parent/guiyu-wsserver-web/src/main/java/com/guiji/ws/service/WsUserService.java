package com.guiji.ws.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.utils.StrUtils;
import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.util.DataLocalCacheUtil;
import com.guiji.ws.websocket.MonitorCallsWebSocket;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: MonitorUserService 
* @Description: 监控用户服务
* @date 2019年2月22日 下午6:24:06 
* @version V1.0  
*/
@Slf4j
@Service
public class WsUserService {
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	@Autowired
	RdCacheService rdCacheService;
	
	
	/**
	 * 某个实时通话监控用户上线
	 * @param userId
	 */
	public OnlineUser wsUserOnLine(String sence,String userId) {
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(sence)) {
			OnlineUser user = new OnlineUser();
			SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(userId);
			user.setUserId(userId);	//用户编号
			user.setOrgCode(org.getCode());	//用户真实企业
			user.setAssistCallUser(this.hasAssistRole(userId)); //是否协呼
			user.setUuid(UUID.randomUUID().toString());
			//放进缓存
			rdCacheService.cacheOnlineUser(sence,user);
			return user;
		}
		return null;
	}
	
	/**
	 * 某个实时通话监控的用户下线
	 * @param userId
	 */
	@Synchronized
	public void wsUserOffLine(String sence,String userId) {
		if(StrUtils.isNotEmpty(sence) && StrUtils.isNotEmpty(userId)) {
			//查询所有在连的数据--本连接已经挂断了
			CopyOnWriteArraySet<MonitorCallsWebSocket> clients = MonitorCallsWebSocket.getClients();
			int count = 0;
			if(clients!=null) {
				for(MonitorCallsWebSocket socket:clients) {
					if(socket.getUserId().equals(userId)) {
						count++;
					}
				}
			}
			//除了本连接，该用户没有其他连接了，再清用户缓存
			if(count==0) {
				rdCacheService.delOnlineUser(sence,userId);
			}
		}
	}
	
	
	/**
	 * 查询某个场景的websocket在线用户
	 * @param sence
	 * @return
	 */
	public List<OnlineUser> queryOnlineUser(String sence,String orgCode){
		if(StrUtils.isNotEmpty(sence) && StrUtils.isNotEmpty(orgCode)) {
			return rdCacheService.queryOnlineUserByOrgCode(sence, orgCode);
		}
		return null;
	}
	
	
	
	/**
	 * 判断用户是否具有协呼角色
	 * @param userId
	 * @return
	 */
	private boolean hasAssistRole(String userId) {
		List<SysRole> roleList = dataLocalCacheUtil.queryUserRole(userId);
		if(roleList!=null && !roleList.isEmpty()) {
			for(SysRole role : roleList) {
				//TODO 现在没有协呼角色，暂时拥有企业管理员的用户就可以做协呼
				if(3==role.getId()) {
					//协呼
					return true;
				}
			}
		}
		return false;
	}
	
	
}
