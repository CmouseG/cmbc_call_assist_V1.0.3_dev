package com.guiji;

import com.guiji.component.interceptor.BusiExceptionHandler;
import com.guiji.component.result.ApplicationContextConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@ServletComponentScan
@EnableFeignClients(basePackages = {"com.guiji.*.api"}) // 启用feign
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ApplicationContextConfig.class, BusiExceptionHandler.class}))
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
