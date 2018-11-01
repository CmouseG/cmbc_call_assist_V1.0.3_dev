package com.guiji.fsline;

import com.guiji.common.result.EnableAutoResultPack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoResultPack
@EnableSwagger2
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class FsLineApplication {
    public static void main(String[] args) {
        SpringApplication.run(FsLineApplication.class, args);
    }
}
