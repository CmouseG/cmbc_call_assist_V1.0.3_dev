package com.guiji.dict;

import com.guiji.common.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ty on 2018/10/26.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoResultPack
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }
}
