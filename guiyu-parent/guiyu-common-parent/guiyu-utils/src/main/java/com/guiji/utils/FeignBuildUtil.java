package com.guiji.utils;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

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
//               .options(new Request.Options(1000, 3500))
//               .retryer(new Retryer.Default(5000, 5000, 3));
              .target( apiType,  url);

   }

}
