package com.guiji.ws.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/** 
* @ClassName: WebSocketConfig 
* @Description: websocket配置，注册@ServerEndpoint服务
* @date 2019年2月25日 下午7:38:48 
* @version V1.0  
*/
@Configuration  
public class WebSocketConfig {  
    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
  
}