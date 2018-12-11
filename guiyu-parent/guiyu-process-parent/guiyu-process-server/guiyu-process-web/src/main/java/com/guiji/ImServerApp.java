package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;
import com.guiji.process.server.core.ImServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * IM服务启动入口
 * @author yinjihuan
 */
@EnableScheduling
@EnableRabbit
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.guiji.process.server.dao")
@EnableAutoResultPack
@EnableSwagger2
public class ImServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ImServerApp.class, args);
	}
}
