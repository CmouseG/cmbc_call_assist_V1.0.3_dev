package com.guiji.botsentence.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.botsentence.manager.impl.IAIManagerImpl;

@Component
public class BotSentenceTask {

	private Logger logger = LoggerFactory.getLogger(BotSentenceTask.class);
	
	@Autowired
	IAIManagerImpl aIManager;
	
	@Scheduled(cron="0 0/2 *  * * ? ")   //每2分钟执行一次       
     public void execute() throws InterruptedException{
		logger.info("执行定时任务,当前使用中的机器人...");
		//Thread.sleep(new Random().nextInt(30) * 1000);
		ConcurrentMap<String, Integer> map = aIManager.getSellbotCache().asMap();
		Set<String> set = map.keySet();
		for(String key : set) {
			int value = map.get(key);
			logger.info("当前缓存对话数据: " + key + ", " + value);
		}
     }
}
