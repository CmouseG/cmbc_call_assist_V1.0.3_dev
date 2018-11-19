package com.guiji.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
* @ClassName: WebMvcConfig
* @Description: 配置系统拦截器
* @author: weiyunbo
* @date 2018年5月30日 下午4:36:02 
* @version V1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
	  public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowCredentials(true)
	        .allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
	        .maxAge(3600);
	  }

    /**
     * 跨域过滤器 
     * @return 
     */  
    @Bean  
    public CorsFilter corsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**", buildConfig()); // 4  
        return new CorsFilter(source);  
    }
    
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();  
        List<String> list = new ArrayList<>();
	    list.add("*");
	    corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.addAllowedOrigin("*");  
//        corsConfiguration.addAllowedOrigin("http://localhost:1000");
//        corsConfiguration.addAllowedOrigin("http://localhost:1001");
        corsConfiguration.addAllowedHeader("*");  
        corsConfiguration.addAllowedMethod("*");  
        return corsConfiguration;  
    }
}
