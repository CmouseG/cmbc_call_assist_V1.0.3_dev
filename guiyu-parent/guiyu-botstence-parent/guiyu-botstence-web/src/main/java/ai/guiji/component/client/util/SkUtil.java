/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package ai.guiji.component.client.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.ServerResult;
import ai.guiji.component.model.SkFileInfoReq;
import ai.guiji.component.model.SkFileInfoRsp;


/** 
 *@Description: SK文件系统工具类
 *@Author:weiyunbo
 *@date:2018年6月26日 上午10:36:27
 *@history:
 *@Version:v1.0 
 */
@Component
public class SkUtil {
	private static final Logger logger = LoggerFactory.getLogger(SkUtil.class);
	@Value("${gateway.host:http://cloud-gateway:18080/}")
    private String gatewayUrl;		//网关服务器URL
	
	public ServerResult<SkFileInfoRsp> uploadSftp(SkFileInfoReq skFileInfoReq ,File filename) throws CommonException {
    	String jsonResult = null;
    	String url = gatewayUrl + "cloud-storekeeper/sk/uploadFile";
    	logger.info("开始文件上传,url="+url);
        RestTemplate rest = new RestTemplate();
        //重新更新rest，更新编码
        reInitMessageConverter(rest);
        FileSystemResource resource = new FileSystemResource(filename);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("file", resource);
        if(skFileInfoReq != null) {
        	param.add("sysCode", skFileInfoReq.getSysCode());
        	param.add("busiId", skFileInfoReq.getBusiId());
        	param.add("busiType", skFileInfoReq.getBusiType());
        	param.add("thumbImageFlag", skFileInfoReq.getThumbImageFlag());
        }
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
        try {
            ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity,
                    String.class);
            jsonResult = responseEntity.getBody();
        } catch (Exception e) {
            throw new CommonException(e);
        }
        logger.info("文件上传后返回报文："+jsonResult);
        if(StrUtils.isNotEmpty(jsonResult)) {
        	JSONObject obj = JSONObject.parseObject(jsonResult);
        	SkFileInfoRsp skFileInfoRsp = null;
        	if(StrUtils.isNotEmpty(obj.getString("data"))) {
        		skFileInfoRsp = JSONObject.parseObject(obj.getString("data"), SkFileInfoRsp.class);
        	}
        	return ServerResult.create(obj.getString("rspCode"), obj.getString("rspMsg"), skFileInfoRsp);
        }
        return null;
    }
	
	/*
     *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
     * 添加的StringHttpMessageConverter非UTF-8
     * 所以先要移除原有的StringHttpMessageConverter，
     * 再添加一个字符集为UTF-8的StringHttpMessageConvert
     * */
     private void reInitMessageConverter(RestTemplate restTemplate){
    	 List<HttpMessageConverter<?>> cos=new ArrayList<HttpMessageConverter<?>>();  
         StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(Charset.forName("utf-8"));  
         FormHttpMessageConverter formHttpMessageConverter=new FormHttpMessageConverter();  
         List<HttpMessageConverter<?>> cos2=new ArrayList<HttpMessageConverter<?>>();  
         cos2.add(stringHttpMessageConverter);  
         cos2.add(new ByteArrayHttpMessageConverter());  
         cos2.add(new ResourceHttpMessageConverter());  
         formHttpMessageConverter.setPartConverters(cos2);  
         cos.add(formHttpMessageConverter);  
         cos.add(stringHttpMessageConverter);  
         restTemplate.setMessageConverters(cos); 
     }
     
     /**
      * 获取根目录
      * @return the root path
      */
     public static String getRootPath() {
         String rootPath = "";
         /** For Windows */
         if ("\\".equals(File.separator)) {
             return System.getProperty("user.dir").substring(0, 2)+"/";
         }
         /** For Linux */
         if ("/".equals(File.separator)) {
           return "/";
         }
         return rootPath;
     }
}
  
