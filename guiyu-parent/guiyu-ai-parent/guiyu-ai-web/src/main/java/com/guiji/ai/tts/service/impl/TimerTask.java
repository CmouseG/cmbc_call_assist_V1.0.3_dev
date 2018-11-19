package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.ai.dao.TtsModelMapper;
import com.guiji.ai.dao.entity.TtsModel;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.utils.RedisUtil;

@Component
public class TimerTask {
	private static Logger logger = LoggerFactory.getLogger(TimerTask.class);
	RedisUtil redisUtil = new RedisUtil();
	
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	TtsModelMapper ttsModelMapper;
	
	//定时任务，启动时运行（每3分钟执行一次）
	@Scheduled(fixedRate = 1000*60*3)
    public void task() throws InterruptedException {
    	Lock lock = new Lock("lockName","lockValue");
    	if(distributedLockHandler.tryLock(lock, 30*1000, 30, 3*60*1000)){ //尝试30s,每30ms尝试一次，持锁时间为3分钟
    		try {
    			logger.info("查询前10分钟内的GPU请求情况");
    			//查询当前时间之前10分钟的GPU分配情况
    			List<Map<String, Object>> resultList = ttsModelMapper.selectTenMinutesBefore(new Date()); // resultList中是<A, 3>，<B，5>的格式
    			logger.info("前10分钟GPU请求情况："+resultList);
    			
    			//删除原redis中分配记录
    			for(int i = 0; i < resultList.size(); i++){
    				for(Entry<String, Object> entry : resultList.get(i).entrySet()){
    					String modelName = entry.getKey();
    					int j = 0;
    	    			while(redisUtil.hasKey("GUIYU_TTS_"+modelName+(j++))){
    	    				redisUtil.del("GUIYU_TTS_"+modelName+(j++));
    	    			}
    				}
    			}
    			
    			logger.info("根据GPU请求比例重新分配GPU");
    			//TODO 根据数量比例重新分配GPU给各model 返回List
    			
    			//将新的分配记录保存到redis
    			List<TtsModel> gList = new ArrayList<>(); //TODO 泛型待修改，gList是调进程管理接口的返回值
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
    				map.put("status", "0"); //0-空闲，1-忙碌
    				redisUtil.hmset("GUIYU_TTS_"+modelName+(j++), map); 
    			}
    			
			} catch (Exception e) {
				logger.error("操作失败！");
			}
    		distributedLockHandler.releaseLock(lock);
    	}
    	else{
    		logger.warn("未能获取锁！！！");
    	}
    }
}
