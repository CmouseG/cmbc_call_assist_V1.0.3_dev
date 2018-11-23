package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;
import com.guiji.process.server.core.ImServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * IM服务启动入口
 * @author yinjihuan
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.guiji.process.server.dao")
@EnableAutoResultPack
public class ImServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ImServerApp.class, args);
	}
}
