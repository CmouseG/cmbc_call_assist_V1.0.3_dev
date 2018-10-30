package com.guiji.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.guiji.auth.annotation.EnableAuthConfig;
import com.guiji.common.result.EnableAutoResultPack;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication
//@EnableDiscoveryClient
@MapperScan("com.guiji.user.dao")
@EnableAutoResultPack
@EnableAuthConfig
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
