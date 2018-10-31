package com.guiji.dispatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by ty on 2018/10/18.
 */
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})


//@ImportResource(locations={ "classpath*:config/spring/spring-database.xml", 
//		"classpath*:config/spring/spring-sharding.xml"
//})
//@EnableDiscoveryClient
@MapperScan("com.guiji.dispatch.dao")
@SpringBootApplication
@EnableScheduling
//@EnableAutoResultPack
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
