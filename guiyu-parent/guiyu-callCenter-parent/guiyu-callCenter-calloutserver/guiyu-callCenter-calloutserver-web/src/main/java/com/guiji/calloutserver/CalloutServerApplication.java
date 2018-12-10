package com.guiji.calloutserver;

import com.guiji.component.aspect.ControllerLogAspect;
import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableAutoResultPack
@EnableSwagger2
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "com.guiji")
@MapperScan("com.guiji.*.dao")
public class CalloutServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalloutServerApplication.class, args);
	}

}
