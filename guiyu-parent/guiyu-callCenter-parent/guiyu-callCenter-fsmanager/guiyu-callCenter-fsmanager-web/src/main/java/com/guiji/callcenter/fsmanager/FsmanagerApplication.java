package com.guiji.callcenter.fsmanager;

import com.github.pagehelper.PageHelper;
import com.guiji.component.result.EnableAutoResultPack;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.guiji")
@EnableAutoResultPack
@SpringBootApplication(scanBasePackages = "com.guiji")
public class FsmanagerApplication {
    public static void main(String[] args){
        SpringApplication.run(FsmanagerApplication.class,args);
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        p.setProperty("supportMethodsArguments", "false");
        p.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
