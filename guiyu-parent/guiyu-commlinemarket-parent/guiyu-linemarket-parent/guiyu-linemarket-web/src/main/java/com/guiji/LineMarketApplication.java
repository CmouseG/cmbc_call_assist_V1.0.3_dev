package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 线路市场启动
* @Author: weiyunbo
* @date 2019年1月29日 下午4:13:20 
* @version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient	//启用服务发现
@EnableAutoResultPack	
//@EnableFeignClients	//启用feign
@EnableFeignClients(basePackages={"*.guiji.*.api"})
@EnableSwagger2	//启用swagger注解
@EnableAsync  //启用异步
@MapperScan("com.guiji.*.dao")
public class LineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(LineMarketApplication.class, args);
    }
}
