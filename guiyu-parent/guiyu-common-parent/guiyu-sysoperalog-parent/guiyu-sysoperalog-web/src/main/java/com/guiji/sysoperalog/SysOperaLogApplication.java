package com.guiji.sysoperalog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import com.guiji.component.result.EnableAutoResultPack;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoResultPack
@EnableFeignClients
@EnableSwagger2
@MapperScan("com.guiji.guiyu.sysoperalog.dao")
public class SysOperaLogApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SysOperaLogApplication.class, args);
	}
}
