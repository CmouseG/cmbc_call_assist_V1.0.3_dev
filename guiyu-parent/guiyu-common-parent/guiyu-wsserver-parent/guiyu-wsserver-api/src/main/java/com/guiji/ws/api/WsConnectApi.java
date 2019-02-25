package com.guiji.ws.api;

import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;
import com.guiji.ws.model.WebSocketConnect;
import com.guiji.ws.model.WsMsg;

import io.swagger.annotations.Api;

/** 
* @ClassName: IDaRemote 
* @Description: websocket连接公共服务
* @date 2019年1月2日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="websocket连接公共服务")
@FeignClient("guiyu-wsserver-web")
public interface WsConnectApi {
	
	/**
	 * 查询某个场景的ws连接
	 * @param seqId
	 * @return
	 */
    @PostMapping(value = "/remote/queryWsConnects")
	Result.ReturnData<CopyOnWriteArraySet<WebSocketConnect>> queryWsConnects(@RequestParam(value="sence",required=true) String sence);
    
    /**
     * 像某个websocket客户端发送消息
     * @param wsMsg
     * @return
     */
    @PostMapping(value = "/remote/asyncSendMsg")
	Result.ReturnData asyncSendMsg(@RequestBody WsMsg wsMsg);
    
}
