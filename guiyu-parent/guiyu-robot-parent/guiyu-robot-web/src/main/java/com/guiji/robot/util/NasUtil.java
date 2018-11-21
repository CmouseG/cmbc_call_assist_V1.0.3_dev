/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.robot.util;

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

import com.guiji.robot.exception.RobotException;
import com.guiji.utils.StrUtils;


/** 
 *@Description: SK文件系统工具类
 *@Author:weiyunbo
 *@date:2018年6月26日 上午10:36:27
 *@history:
 *@Version:v1.0 
 */
@Component
public class NasUtil {
	private static final Logger logger = LoggerFactory.getLogger(NasUtil.class);
	
	
	/**
	 * 上传本地文件
	 * @param skFileInfoReq
	 * @param filename
	 * @return
	 * @throws RobotException
	 */
	public void uploadSftp() throws RobotException {
    }
	
     
}
  
