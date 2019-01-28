package com.guiji.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wchi on 2017/4/18.
 * 用于配置拦截器
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public TokenAuthInterceptor tokenAuthInterceptor(){
        return new TokenAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenAuthInterceptor()).addPathPatterns("/rs/**");
    }
}
