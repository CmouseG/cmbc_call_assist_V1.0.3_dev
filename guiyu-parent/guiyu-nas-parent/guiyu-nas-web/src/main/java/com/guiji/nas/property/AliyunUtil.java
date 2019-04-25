package com.guiji.nas.property;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by hhw
 */
public class AliyunUtil {

	private static Properties properties;
	
    private static String getEndpoint;
    
    private static String getAccessKeyId;
    
    private static String getAccessKeySecret;
    
    private static String getBucketName;
    
    private static String getAliyunBaseUrl;
    
    private static String getWebServerUrl;

    public static String getEndpoint(){
        return getEndpoint;
    }
    
    public static String getAccessKeyId(){
        return getAccessKeyId;
    }
    
    public static String getWebServerUrl(){
        return getWebServerUrl;
    }
    
    public static String getAccessKeySecret(){
        return getAccessKeySecret;
    }
    
    public static String getBucketName(){
        return getBucketName;
    }
    
    public static String getAliyunBaseUrl(){
        return getAliyunBaseUrl;
    }
    
    @Autowired(required = true)
    public void getWebServerUrl(@Qualifier("getWebServerUrl") String getWebServerUrl) {
        this.getWebServerUrl = getWebServerUrl;
    }

    @Autowired(required = true)
    public void getEndpoint(@Qualifier("getEndpoint") String getEndpoint) {
        this.getEndpoint = getEndpoint;
    }
    
    @Autowired(required = true)
    public void getAccessKeyId(@Qualifier("getAccessKeyId") String getAccessKeyId) {
        this.getAccessKeyId = getAccessKeyId;
    }
    
    @Autowired(required = true)
    public void getAccessKeySecret(@Qualifier("getAccessKeySecret") String getAccessKeySecret) {
        this.getAccessKeySecret = getAccessKeySecret;
    }
    
    @Autowired(required = true)
    public void getBucketName(@Qualifier("getBucketName") String getBucketName) {
        this.getBucketName = getBucketName;
    }
    
    @Autowired(required = true)
    public void getAliyunBaseUrl(@Qualifier("getAliyunBaseUrl") String getAliyunBaseUrl) {
        this.getAliyunBaseUrl = getAliyunBaseUrl;
    }
    

    @Autowired(required = true)
    public  void setProperties(@Qualifier("AliyunProperties") Properties properties) {
        this.properties = properties;
    }
    
}
