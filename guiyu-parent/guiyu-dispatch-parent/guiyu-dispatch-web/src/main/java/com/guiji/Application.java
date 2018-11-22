package com.guiji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.guiji.component.result.EnableAutoResultPack;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.guiji.dispatch.dao")
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "com.guiji")
@EnableDiscoveryClient
@EnableSwagger2
@EnableAutoResultPack
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
