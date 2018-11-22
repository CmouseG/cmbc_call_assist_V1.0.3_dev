/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.utils.StrUtils;


/** 
 *@Description: NAS文件系统工具类
 *@Author:weiyunbo
 *@date:2018年6月26日 上午10:36:27
 *@history:
 *@Version:v1.0 
 */
public class NasUtil {
	private static final Logger logger = LoggerFactory.getLogger(NasUtil.class);
    private static final String gatewayUrl = "http://guiyu-cloud-zuul:18000/";		//网关服务器URL
	
	/**
	 * 上传本地文件
	 * @param skFileInfoReq
	 * @param filename
	 * @return
	 * @throws RobotException
	 */
	public SysFileRspVO uploadNas(SysFileReqVO sysFileReqVO ,File file) throws GuiyuException{
    	String jsonResult = null;
    	String url = gatewayUrl + "zuul/nas/upload";
    	logger.info("开始文件上传,url="+url);
        RestTemplate rest = new RestTemplate();
        //重新更新rest，更新编码
        reInitMessageConverter(rest);
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("file", resource);
        if(sysFileReqVO != null) {
        	param.add("sysCode", sysFileReqVO.getSysCode());
        	param.add("busiId", sysFileReqVO.getBusiId());
        	param.add("busiType", sysFileReqVO.getBusiType());
        	param.add("thumbImageFlag", sysFileReqVO.getThumbImageFlag());
        }else {
        	sysFileReqVO = new SysFileReqVO();
        }
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param);
        try {
            ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity,
                    String.class);
            jsonResult = responseEntity.getBody();
        } catch (Exception e) {
            throw new GuiyuException(e);
        }
        logger.info("文件上传后返回报文："+jsonResult);
        if(StrUtils.isNotEmpty(jsonResult)) {
        	JSONObject obj = JSONObject.parseObject(jsonResult);
        	SysFileRspVO skFileInfoRsp = null;
        	if(StrUtils.isNotEmpty(obj.getString("body"))) {
        		skFileInfoRsp = JSONObject.parseObject(obj.getString("body"), SysFileRspVO.class);
        		return skFileInfoRsp;
        	}else {
        		throw new GuiyuException(obj.getString("code"),obj.getString("rspMsg"));
        	}
        }
        return null;
    }
	
	/*
     * 初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
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
     
}
  
