package com.guiji.ws.service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;

import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import com.guiji.ws.model.WebSocketConnect;
import com.guiji.ws.model.WsSenceEnum;
import com.guiji.ws.websocket.MonitorCallsWebSocket;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: WebSocketConnectService 
* @Description: websocket连接服务
* @date 2019年2月25日 下午7:38:48 
* @version V1.0  
*/
@Slf4j
@Service
public class WebSocketConnectService {
	//线程安全的websocket map
	private static Map<String,CopyOnWriteArraySet<WebSocketConnect>> wsClientMap = new ConcurrentHashMap<String,CopyOnWriteArraySet<WebSocketConnect>>();
	
	
	/**
	 * 打开一个websocket连接
	 * @param socket
	 */
	public void openWs(MonitorCallsWebSocket socket) {
		if(socket!=null) {
			WebSocketConnect connect = new WebSocketConnect();
			BeanUtil.copyProperties(socket, connect);
			if(StrUtils.isNotEmpty(connect.getSence())) {
				//取出某个场景的连接list
				CopyOnWriteArraySet<WebSocketConnect> connectList = wsClientMap.get(connect.getSence());
				if(connectList==null) {
					connectList = new CopyOnWriteArraySet<WebSocketConnect>();
				}
				connectList.add(connect);
				wsClientMap.put(connect.getSence(), connectList);
			}
		}
	}
	
	/**
	 * 关闭一个websocket连接
	 * @param socket
	 */
	public void closeWs(MonitorCallsWebSocket socket) {
		if(socket!=null) {
			if(StrUtils.isNotEmpty(socket.getSence()) && StrUtils.isNotEmpty(socket.getUuid())) {
				//取出某个场景的连接list
				CopyOnWriteArraySet<WebSocketConnect> connectList = wsClientMap.get(socket.getSence());
				if(connectList!=null) {
					WebSocketConnect wc = null;
					Iterator<WebSocketConnect> it = connectList.iterator();
			    	while (it.hasNext()) {
			    		WebSocketConnect connect = it.next();
						if(socket.getUuid().equals(connect.getUuid())){
							wc = connect;
							break;
						}
			    	}
			    	if(wc!=null) {
			    		//删除
			    		connectList.remove(wc);
			    	}
				}
			}
		}
	}
	
	
	/**
	 * 查询某个场景的所有连接
	 * @param sence
	 * @return
	 */
	public CopyOnWriteArraySet<WebSocketConnect> queryWsConnectBySence(String sence){
		if(StrUtils.isNotEmpty(sence)) {
			return wsClientMap.get(sence);
		}
		return null;
	}
	
	
	/**
	 * 发送信息
	 * @param connect
	 * @param message
	 */
	public void asyncSendMsg(WebSocketConnect connect,String message) {
		if(connect!=null) {
			if(WsSenceEnum.montiorcall.toString().equals(connect.getSence())) {
				//通话实时监听服务
				MonitorCallsWebSocket socket = MonitorCallsWebSocket.queryWsSocketBy(connect);
				if(socket!=null) {
					try {
						if(StrUtils.isEmpty(message)) {
							//不能发送null
							message = "";
						}
						socket.sendMessage(message);
					} catch (Exception e) {
						log.error("websocket消息发送异常,",e);
					}
				}else {
					log.error("查不到websocket实时连接",connect);
				}
			}
		}
	}
}
