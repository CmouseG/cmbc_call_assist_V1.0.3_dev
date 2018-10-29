package com.guiji.nas;

import com.guiji.common.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoResultPack
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
