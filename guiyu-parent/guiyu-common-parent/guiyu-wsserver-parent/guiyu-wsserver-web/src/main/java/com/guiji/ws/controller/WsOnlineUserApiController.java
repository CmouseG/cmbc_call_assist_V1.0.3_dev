package com.guiji.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result;
import com.guiji.ws.api.WsOnlineUserApi;
import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.service.RdCacheService;
import com.guiji.ws.service.WsUserService;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: WsOnlineUserApiController 
* @Description: websocket在线用户服务
* @date 2019年2月25日 下午8:55:22 
* @version V1.0  
*/
@Slf4j
@RestController
public class WsOnlineUserApiController implements WsOnlineUserApi{
	@Autowired
	WsUserService wsUserService;
	@Autowired
	RdCacheService rdCacheService;
	
	
	/**
	 * 查询某个场景某个企业下所有在线用户
	 * @param sence
	 * @param orgCode
	 * @return
	 */
	public Result.ReturnData<List<OnlineUser>> queryOnlineUserByOrgCode(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="orgCode",required=true) String orgCode){
		List<OnlineUser> list = wsUserService.queryOnlineUser(sence, orgCode);
		return Result.ok(list);
	}
    
    
    /**
	 * 查询某个场景某个在线用户
	 * @param sence
	 * @param userId
	 * @return
	 */
	public Result.ReturnData<OnlineUser> queryOnlineUserByUserId(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="userId",required=true) String userId){
		OnlineUser user = rdCacheService.queryOnlineUser(sence, userId);
		return Result.ok(user);
	}
    
    
    /**
     * 删除某个在线用户缓存数据
     * @param sence
     * @param userId
     * @return
     */
	public Result.ReturnData<List<OnlineUser>> delOnlineUser(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="userId",required=true) String userId){
		rdCacheService.delOnlineUser(sence, userId);
		return Result.ok();
	}
}
