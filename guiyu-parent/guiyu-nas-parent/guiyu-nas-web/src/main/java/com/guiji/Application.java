package com.guiji;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.guiji.common.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoResultPack
@Import(FdfsClientConfig.class)
@MapperScan("com.guiji.nas.dao")
public class Application extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
