package com.guiji.ws.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.ws.api.WsConnectApi;
import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.model.WebSocketConnect;
import com.guiji.ws.model.WsMsg;
import com.guiji.ws.model.WsSenceEnum;
import com.guiji.ws.service.RdCacheService;
import com.guiji.ws.service.WebSocketConnectService;
import com.guiji.ws.service.WsUserService;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: WsConnectApiController 
* @Description: websocket连接公共服务
* @date 2019年2月25日 下午8:51:20 
* @version V1.0  
*/
@Slf4j
@RestController
public class WsConnectApiController implements WsConnectApi{
	@Autowired
	WebSocketConnectService webSocketConnectService;
	@Autowired
	WsUserService wsUserService;
	@Autowired
	RdCacheService rdCacheService;
	
	/**
	 * 查询某个场景的ws连接
	 * @param seqId
	 * @return
	 */
	public Result.ReturnData<CopyOnWriteArraySet<WebSocketConnect>> queryWsConnects(@RequestParam(value="sence",required=true) String sence){
		CopyOnWriteArraySet<WebSocketConnect> list = webSocketConnectService.queryWsConnectBySence(sence);
		if(list!=null && !list.isEmpty()) {
			//查询时刷下数据，防止因停服务导致的缓存数据和实时连接数据不一致的情况
			this.flushOnlineUser(sence, list);
		}
		return Result.ok(list);
	}
    
    /**
     * 像某个websocket客户端发送消息
     * @param wsMsg
     * @return
     */
	public Result.ReturnData asyncSendMsg(@RequestBody WsMsg wsMsg){
		if(wsMsg!=null) {
			webSocketConnectService.asyncSendMsg(wsMsg.getConnect(), wsMsg.getMessage());
		}else {
			log.error("请求数据不能为空");
		}
		return Result.ok();
	}
	
	/**
     * 重新刷新下在线的用户
     * 在线用户存在redis缓存中，在websocket关闭时删除，但是如果应用直接停掉，没法实时删除redis缓存中无效数据，所以实时对比下
     * @param list
     */
    private void flushOnlineUser(String sence,CopyOnWriteArraySet<WebSocketConnect> list) {
    	if(list!=null) {
    		Set<String> orgList = new HashSet<String>();
        	Set<String> userList = new HashSet<String>();
        	if(list!=null && !list.isEmpty()) {
        		//获取实时监控的企业和用户
        		for(WebSocketConnect connect : list) {
        			orgList.add(connect.getOrgCode());
        			userList.add(connect.getUserId());
        		}
        		if(orgList!=null && !orgList.isEmpty()) {
        			for(String orgCode:orgList) {
    					List<OnlineUser> onlineUserList = wsUserService.queryOnlineUser(sence, orgCode);
        				if(onlineUserList!=null && !onlineUserList.isEmpty()) {
        					for(OnlineUser mUser : onlineUserList) {
        						//如果现在最新的连接不包括缓存中的，那么以实时数据为准
        						if(!userList.contains(mUser.getUserId())) {
        							rdCacheService.delOnlineUser(sence, mUser.getUserId());
            					}
        					}
        				}
    				}
        		}
        	}
    	}
    }
}
