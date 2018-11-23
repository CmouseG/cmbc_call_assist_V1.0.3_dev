package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
		List<GuiyuTtsGpu> avaliableGpuList = new ArrayList<>(); // 可用list
		List<GuiyuTtsGpu> unavaliableGpuList = new ArrayList<>(); // 不可用list
		String ip = null;
		String port = null;
		
		try {
//			long startTime = System.currentTimeMillis();
//			do{
//				avaliableGpuList = (List<GuiyuTtsGpu>) redisUtil.get(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE);
//				if(avaliableGpuList != null && !avaliableGpuList.isEmpty()) 
//					break;
//				Thread.sleep(50); //间隔50ms
//			}while(System.currentTimeMillis() - startTime > 3000);
			
			avaliableGpuList.add(new GuiyuTtsGpu("192.168.1.157", "9202"));
			avaliableGpuList.add(new GuiyuTtsGpu("192.168.1.157", "9201"));//TODO 测试用
			
			if (!avaliableGpuList.isEmpty()) { // 取到可用GPU
				ip = avaliableGpuList.get(0).getIp();
				port = avaliableGpuList.get(0).getPort();
				logger.info("获取到可用的GPU，IP=" + ip + "，PORT=" + port);
				avaliableGpuList.remove(0); // 从可用list中移除此GPU
//				redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, avaliableGpuList);
//				unavaliableGpuList = (List<GuiyuTtsGpu>) redisUtil.get(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE);
				unavaliableGpuList.add(new GuiyuTtsGpu(ip, port)); // 添加到不可用list中
//				redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE, unavaliableGpuList);
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
