package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;
import com.guiji.ws.websocket.MonitorCallsWebSocket;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ty on 2018/10/18.
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient	//启用服务发现
@EnableAutoResultPack	
@EnableFeignClients(basePackages={"*.guiji.*.api"})
@EnableSwagger2	//启用swagger注解
public class WsApplication {
    public static void main(String[] args) {
    	
    	ConfigurableApplicationContext applicationContext = SpringApplication.run(WsApplication.class, args);
    	
    	//为设置容器websocket属性（解决websocket不能使用使用spring注解获取service bean的问题）
    	MonitorCallsWebSocket.setApplicationContext(applicationContext); 
    	MonitorCallsWebSocket monitorCallsListener = (MonitorCallsWebSocket) applicationContext.getBean("monitorCallsWebSocket");
    }
}
