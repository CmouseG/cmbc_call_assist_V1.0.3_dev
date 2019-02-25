package com.guiji.ws.api;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;
import com.guiji.ws.model.OnlineUser;
import io.swagger.annotations.Api;

/** 
* @ClassName: WsOnlineUserApi 
* @Description: websocket在线用户服务
* @date 2019年2月25日 下午8:13:56 
* @version V1.0  
*/
@Api(tags="websocket在线用户服务")
@FeignClient("guiyu-wsserver-web")
public interface WsOnlineUserApi {

	
	/**
	 * 查询某个场景某个企业下所有在线用户
	 * @param sence
	 * @param orgCode
	 * @return
	 */
    @PostMapping(value = "/remote/queryOnlineUserByOrgCode")
	Result.ReturnData<List<OnlineUser>> queryOnlineUserByOrgCode(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="orgCode",required=true) String orgCode);
    
    
    /**
	 * 查询某个场景某个在线用户
	 * @param sence
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/remote/queryOnlineUserByUserId")
	Result.ReturnData<OnlineUser> queryOnlineUserByUserId(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="userId",required=true) String userId);
    
    
    /**
     * 删除某个在线用户缓存数据
     * @param sence
     * @param userId
     * @return
     */
    @PostMapping(value = "/remote/delOnlineUser")
	Result.ReturnData<List<OnlineUser>> delOnlineUser(@RequestParam(value="sence",required=true) String sence,
			@RequestParam(value="userId",required=true) String userId);
}
