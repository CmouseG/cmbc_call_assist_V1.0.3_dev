package com.guiji;

import com.guiji.component.result.EnableAutoResultPack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 语音网关启动类
* @Author: weiyunbo
* @date 2019年1月29日 下午4:13:20 
* @version V1.0
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient	//启用服务发现
@EnableAutoResultPack	
@EnableFeignClients(basePackages={"*.guiji.*.api"})
@EnableSwagger2	//启用swagger注解
@MapperScan("com.guiji.*.dao")
public class VoipGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoipGatewayApplication.class, args);
    }
}
