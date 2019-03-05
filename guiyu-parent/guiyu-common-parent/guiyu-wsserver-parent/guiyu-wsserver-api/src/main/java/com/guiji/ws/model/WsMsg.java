package com.guiji.ws.model;

import lombok.Data;

/** 
* @ClassName: WsMsg 
* @Description: websocket消息发送
* @date 2019年2月25日 下午8:11:42 
* @version V1.0  
*/
@Data
public class WsMsg {
	//websocket连接
	private WebSocketConnect connect;
	//要发送的消息内容
	private String message;
}
