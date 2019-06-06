package com.guiji.calloutserver;

import com.guiji.component.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableAutoResultPack
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.guiji")
@EnableScheduling
@EnableFeignClients(basePackages = "com.guiji")
//@MapperScan("com.guiji.*.dao")
@EnableAsync
public class CalloutServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalloutServerApplication.class, args);
	}

}
