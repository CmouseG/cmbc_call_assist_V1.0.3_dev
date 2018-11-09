package com.guiji;


import com.guiji.cloud.zuul.white.WhiteIPUtil;
import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by ty on 2018/10/22.
 */
@EnableZuulProxy
@SpringCloudApplication
@EnableDiscoveryClient
@MapperScan("com.guiji.user.dao")
@EnableAutoResultPack
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
