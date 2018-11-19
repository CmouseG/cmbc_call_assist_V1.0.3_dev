package com.guiji.ai.tts.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.ai.dao.TtsModelMapper;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;

@Component
public class TimerTask {
	private static Logger logger = LoggerFactory.getLogger(TimerTask.class);
	
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	TtsModelMapper ttsModelMapper;
	
	//定时任务，启动时运行（每3分钟执行一次）
	@Scheduled(fixedRate = 1000*60*3)
    public void task() throws InterruptedException {
		logger.info("查询前10分钟内model表数据");
    	Lock lock=new Lock("lockName","lockValue");
    	if(distributedLockHandler.tryLock(lock, 30*1000, 30, 3*60*1000)){ //尝试30s,每30ms尝试一次，持锁时间为3分钟
    		try {
    			logger.info("开始查询前10分钟内的GPU分配情况");
    			//查询当前时间之前10分钟的GPU分配情况
    			List<Map<String, Object>> resultList = ttsModelMapper.selectTenMinutesBefore(new Date());
    			for(Map<String, Object> resultMap : resultList){
    				System.out.println(resultMap.get("model") + "：" + resultMap.get("count")); //返回model和对应GPU的数量
    			}
    			//TODO 根据数量比例重新分配GPU给各model
			} catch (Exception e) {
				logger.error("查询失败！");
			}
    		
    		distributedLockHandler.releaseLock(lock);
    	}
    	else{
    		logger.warn("获取锁失败！！！");
    	}
    }
}
