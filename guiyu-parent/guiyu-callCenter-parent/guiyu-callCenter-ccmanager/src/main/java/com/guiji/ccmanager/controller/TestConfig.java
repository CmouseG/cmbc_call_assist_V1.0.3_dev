package com.guiji.ccmanager.controller;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 09:34
 * @Description:
 */

@Configuration
public class TestConfig {

    @Bean
    public Contract feignConfiguration(){
        return new feign.Contract.Default();
    }
}

