package com.guiji.nas.property;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by hhw 
 */
@Configuration
public class AliyunProperties {
	
    @Value("${endpoint:}")
    private String endpoint;

    @Value("${accessKeyId:}")
    private String accessKeyId;
    
    @Value("${accessKeySecret:}")
    private String accessKeySecret;
    
    @Value("${bucketName:}")
    private String bucketName;
    
    @Value("${aliyunBaseUrl:}")
    private String aliyunBaseUrl;
    
    @Value("${fdfs.webServerUrl:}")
    private String webServerUrl;

    @Bean(name="AliyunProperties")
    public Properties init() throws IOException {
        Properties props=new Properties();
        
        Resource defaultResource =new ClassPathResource("application.yml");
        props.load(new InputStreamReader(defaultResource.getInputStream(),"utf-8"));
        
        return props;
    }

    @Bean(name="getEndpoint")
    public String getEndpoint() {
        return endpoint;
    }
    
    @Bean(name="getWebServerUrl")
    public String getWebServerUrl() {
        return webServerUrl;
    }
    
    @Bean(name="getAccessKeyId")
    public String getAccessKeyId() {
        return accessKeyId;
    }
    
    @Bean(name="getAccessKeySecret")
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    
    @Bean(name="getBucketName")
    public String getBucketName() {
        return bucketName;
    }
    
    @Bean(name="getAliyunBaseUrl")
    public String getAliyunBaseUrl() {
        return aliyunBaseUrl;
    }
    
}
