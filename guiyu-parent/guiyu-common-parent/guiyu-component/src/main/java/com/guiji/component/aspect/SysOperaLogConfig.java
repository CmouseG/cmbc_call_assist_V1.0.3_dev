package com.guiji.component.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SysOperaLogConfig
{

	/*
	 * 调用服务模版
	 */
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
}
