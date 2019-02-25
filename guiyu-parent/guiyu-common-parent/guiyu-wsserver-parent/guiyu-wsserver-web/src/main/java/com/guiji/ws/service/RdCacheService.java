package com.guiji.ws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.RedisUtil;
import com.guiji.ws.constants.WsConstants;
import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.util.DataLocalCacheUtil;

/** 
* @ClassName: AiCacheService 
* @Description: AI数据缓存服务
* @date 2018年11月19日 下午5:27:28 
* @version V1.0  
*/
@Service
public class RdCacheService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;

	/**
	 * 新增、更新一个实时通话监控的在线用户
	 * @param onlineUser
	 */
	public void cacheOnlineUser(String scene,OnlineUser onlineUser){
		if(onlineUser!=null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(onlineUser.getUserId(), onlineUser);
			redisUtil.hmset(WsConstants.WS_ONLINE_USER+scene+"_"+onlineUser.getOrgCode(), map);
		}
	}
	/**
	 * 根据用户ID和会话ID查询某通正在进行的通话
	 * @param sence 连接场景
	 * @param userId
	 * @return
	 */
	public OnlineUser queryOnlineUser(String sence,String userId) {
		SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(userId);
		//查询某个监控用户
		Object cacheObj = redisUtil.hget(WsConstants.WS_ONLINE_USER+sence+"_"+org.getCode(), userId);
		if(cacheObj != null) {
			return (OnlineUser) cacheObj;
		}
		return null;
	}
	/**
	 * 查询某个企业下在线的监控用户
	 * @param userId
	 * @return
	 */
	public List<OnlineUser> queryOnlineUserByOrgCode(String sence,String orgCode){
		Map<Object,Object> allMap = redisUtil.hmget(WsConstants.WS_ONLINE_USER+sence+"_"+orgCode);
		if(allMap != null && !allMap.isEmpty()) {
			List<OnlineUser> list = new ArrayList<OnlineUser>();
			for (Map.Entry<Object,Object> aiEntry : allMap.entrySet()) { 
				OnlineUser onlineUser = (OnlineUser) aiEntry.getValue();
				list.add(onlineUser);
			}
			return list;
		}
		return null;
	}
	/**
	 * 删除所有监控用户
	 */
	public void delAllOnlineUsers(String sence) {
		//模糊删除
		redisUtil.delVague(WsConstants.WS_ONLINE_USER+sence);
	}
	/**
	 * 删除某个监控用户
	 * @param userId
	 */
	public void delOnlineUser(String sence,String userId) {
		SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(userId);
		redisUtil.hdel(WsConstants.WS_ONLINE_USER+sence+"_"+org.getCode(),userId);
	}
	/**************************************end****************************************/
	
	
}
