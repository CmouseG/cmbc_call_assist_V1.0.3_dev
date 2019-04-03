package com.guiji.utils;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 10:59
 * @Description:
 */
public class FeignBuildUtil {

   /**
    * 获取指定url的请求接口，用于访问url的接口
    * @param apiType  请求接口.class
    * @param url 请求地址
    * @param <T> 请求接口
    * @return
    */
   public static  <T> T feignBuilderTarget(Class<T> apiType, String url){
      return  Feign.builder()
               .encoder(new JacksonEncoder())
               .decoder(new JacksonDecoder())
               .contract(new SpringMvcContract())
               .options(new Request.Options(180000, 180000))// 超时时间  180s
               .retryer(Retryer.NEVER_RETRY) //重试机制，重试次数，重试间隔
               .target( apiType,  url);

   }

}
