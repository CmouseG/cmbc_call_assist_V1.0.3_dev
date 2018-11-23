package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/14.
 */
@Component
public class TtsServiceFactory {
	private static Logger logger = LoggerFactory.getLogger(ITtsServiceProvide.class);
	
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	private GuiyuTtsGpu guiyuTtsGpu;

	public ITtsServiceProvide getTtsProvide(String model) {
		List<Object> avaliableGpuList = new ArrayList<>(); // 可用list
		List<Object> unavaliableGpuList = new ArrayList<>(); // 不可用list
		
		try {
			long startTime = System.currentTimeMillis();
			do{
				avaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0 ,-1);
				if(avaliableGpuList != null && !avaliableGpuList.isEmpty()) 
					break;
				Thread.sleep(50); //间隔50ms
			}while(System.currentTimeMillis() - startTime > 3000);
			
			if (!avaliableGpuList.isEmpty()) { // 取到可用GPU
				guiyuTtsGpu = (GuiyuTtsGpu) avaliableGpuList.get(0);
				logger.info("获取到可用的GPU，IP=" + guiyuTtsGpu.getIp() + "，PORT=" + guiyuTtsGpu.getPort());
				avaliableGpuList.remove(0); // 从可用list中移除此GPU
				redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, avaliableGpuList);
				unavaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE, 0 ,-1);
				unavaliableGpuList.add(guiyuTtsGpu); // 添加到不可用list中
				redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE, unavaliableGpuList);
				return guiyuTtsGpu;
			}else{ // 没有取到可用GPU
				logger.error("没有取到可用GPU！");
				return null;
			}
		} catch (Exception e) {
			logger.error("获取GPU失败", e);
			return null;
		}
	}

}
