package com.guiji.ccmanager;

import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.guiji.ccmanager","com.guiji.callcenter"})
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.guiji")
@EnableAutoResultPack
@MapperScan("com.guiji.*.dao")
public class CcManagerApplication{
    public static void main(String[] args) {
        SpringApplication.run(CcManagerApplication.class, args);
    }

}

