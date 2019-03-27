package com.guiji.cloud.zuul.white;

import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by ty on 2018/11/8.
 */
@Configuration
public class WhiteipProperties {
    @Value("${properties.whiteIP}")
    private String whiteIPPropertiesPath;
    @Value("${whiteIP:}")
    private String whiteIP;

    @Bean(name="whiteIPProperties")
    public Properties init() throws IOException {
        Properties props=new Properties();
        Resource defaultResource=new ClassPathResource(whiteIPPropertiesPath);
        props.load(new InputStreamReader(defaultResource.getInputStream(),"utf-8"));
        return props;
    }

    @Bean(name="getWhiteIP")
    public String getWhiteIP() throws IOException {
        return whiteIP;
    }


}
