package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoResultPack
@EnableFeignClients
@EnableScheduling
@EnableSwagger2
@MapperScan("com.guiji.billing.dao.mapper")
public class GuiyuBillingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiyuBillingWebApplication.class, args);
    }

}

