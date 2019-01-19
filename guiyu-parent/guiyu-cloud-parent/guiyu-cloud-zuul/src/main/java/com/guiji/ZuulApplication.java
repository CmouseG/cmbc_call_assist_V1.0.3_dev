package com.guiji;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.guiji.cloud.zuul.white.WhiteIPUtil;
import com.guiji.component.result.EnableAutoResultPack;

/**
 * Created by ty on 2018/10/22.
 */
@EnableZuulProxy
@SpringCloudApplication
@EnableDiscoveryClient
@MapperScan("com.guiji.user.dao")
@EnableAutoResultPack
@EnableFeignClients
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class,args);
    }

    @Bean(name = "whiteIPUtil")
    public WhiteIPUtil whiteIPUtil() {
        WhiteIPUtil whiteIPUtil = new WhiteIPUtil();
        return whiteIPUtil;
    }
}
