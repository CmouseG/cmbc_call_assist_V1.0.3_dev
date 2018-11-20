package com.guiji.ai.tts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/14.
 */
public class TtsServiceFactory {
	private static Logger logger = LoggerFactory.getLogger(ITtsServiceProvide.class);
	static RedisUtil redisUtil = new RedisUtil();
	
    public static ITtsServiceProvide getTtsProvide(String model) {
    	Map map = new HashMap<>();
    	String ip = null;
    	String port = null;
    	String status = null; //0-空闲，1-忙碌
    	
    	int i = 0; //计数
    	while(true){
    		map = redisUtil.hmget("GUIYU_TTS_"+model+(i++));
    		if(map != null){
    			status = (String) map.get("status");
    			if(status.equals("0")){
    				ip = (String) map.get("ip");
    				port = (String) map.get("port");
    				break;
    			}
    		}
    	}
        // 获取一个可用的
    	logger.info("获取到可用的GPU，ip="+ip+"，port="+port);
        return new GuiyuTtsGpu(ip, port);
    }


}
