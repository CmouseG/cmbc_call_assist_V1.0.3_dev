package com.guiji.botsentence.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.vo.AutoDeployExecuteVo;


@Service
public class AutoDeployExecuteService {
	
	private Logger logger = LoggerFactory.getLogger(AutoDeployExecuteService.class);
	
	@Value("${autoDeploy.path}")
	private String requestPath;
	
	@Value("${autoDeploy.token}")
	private String token;
	
	public boolean generateRequestJson(AutoDeployExecuteVo param) {
		
		List<Object> list=new ArrayList<Object>();
		list.add(param);
		
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("task", list);
		
		JSONObject json=(JSONObject) JSONObject.toJSON(data);
		logger.info("发送自动化部署报文...");
		logger.info(json.toString());
		return httpPostWithJson(json.toString(),requestPath);
	}
	
	
	private boolean httpPostWithJson(String param,String url){
	    boolean isSuccess = false;
	    
	    HttpPost post = null;
	    try {
	    	CloseableHttpClient httpclient=HttpClients.createDefault();

	        post = new HttpPost(url);
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("access-token", token);
	                    
	        // 构建消息实体
	        StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpclient.execute(post);
	            
	        // 检验返回码
	        BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuffer buffer=new StringBuffer();
	        String line=null;
	        while((line=reader.readLine())!=null){
	        	buffer.append(line);
	        }
	        
	        JSONObject json=JSONObject.parseObject(buffer.toString());
	        String status=json.getString("status");
	        System.out.println(status);
	        if("ok".equals(status)){
	        	isSuccess= true;
	        }
	        
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println(statusCode);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    return isSuccess;
	}
}
