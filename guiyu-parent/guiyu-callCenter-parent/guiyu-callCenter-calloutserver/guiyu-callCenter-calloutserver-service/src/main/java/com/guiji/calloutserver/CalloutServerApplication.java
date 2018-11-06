package com.guiji.calloutserver;

import com.guiji.component.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoResultPack
@EnableSwagger2
public class CalloutServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalloutServerApplication.class, args);
    }
}
