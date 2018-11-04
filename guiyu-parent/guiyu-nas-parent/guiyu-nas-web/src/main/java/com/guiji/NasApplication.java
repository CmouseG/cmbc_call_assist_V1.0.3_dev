package com.guiji;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ty on 2018/10/18.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.guiji")
@EnableAutoResultPack
@EnableSwagger2
@Import(FdfsClientConfig.class)
@MapperScan("com.guiji.nas.dao")
public class NasApplication {
    public static void main(String[] args) {
        SpringApplication.run(NasApplication.class, args);
    }
}
