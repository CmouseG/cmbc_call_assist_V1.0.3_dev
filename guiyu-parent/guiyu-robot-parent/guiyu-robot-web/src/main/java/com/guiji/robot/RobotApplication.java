package com.guiji.robot;

import com.guiji.component.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.guiji")
@EnableAutoResultPack
@EnableSwagger2
public class RobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobotApplication.class, args);
    }
}
