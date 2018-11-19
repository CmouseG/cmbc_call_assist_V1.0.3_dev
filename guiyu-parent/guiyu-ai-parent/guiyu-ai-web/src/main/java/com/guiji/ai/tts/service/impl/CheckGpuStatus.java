package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.guiji.ai.dao.entity.TtsModel;
import com.guiji.utils.RedisUtil;

public class CheckGpuStatus implements ApplicationRunner{
	
	private static Logger logger = LoggerFactory.getLogger(CheckGpuStatus.class);
	RedisUtil redisUtil = new RedisUtil();
	/**
	 * 查看当前GPU使用情况
	 */
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		logger.info("调用进程管理接口查看GPU分配情况");
		//TODO  调用进程管理接口查看GPU分配情况
		
		List<TtsModel> gList = new ArrayList<>(); //TODO 泛型待修改  进程管理接口返回值
		int j = 0; //计数
		String modelName="";
		//将model对应的GPU状态存入redis，key为"GUIYU_TTS_modelNamei" value是map
		for(int i = 0; i < gList.size(); i++){
			if(!gList.get(i).getModel().equals(modelName)){
				j=0;
				modelName = gList.get(i).getModel();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ip", gList.get(i).getTtsIp());
			map.put("port", gList.get(i).getTtsPort());
			map.put("status", "0");//0-空闲，1-忙碌
			redisUtil.hmset("GUIYU_TTS_"+ modelName +(j++), map); 
		}
	}

}
