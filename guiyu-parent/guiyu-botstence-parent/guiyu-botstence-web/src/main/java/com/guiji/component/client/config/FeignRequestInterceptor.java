package com.guiji.component.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 * 
* @ClassName: FeignRequestInterceptor
* @Description: 用于解决fegin无法传递授权信息
* @author: weiyunbo
* @date 2018年5月30日 下午4:36:02 
* @version V1.0
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

	@Override
	public void apply(RequestTemplate arg0) {
		
	}


}
