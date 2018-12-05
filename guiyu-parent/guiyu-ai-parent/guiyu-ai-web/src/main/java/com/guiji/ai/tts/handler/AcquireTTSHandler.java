package com.guiji.ai.tts.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.impl.TtsGpu;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.utils.RedisUtil;

@Component
public class AcquireTTSHandler implements ApplicationRunner {
	private static Logger logger = LoggerFactory.getLogger(AcquireTTSHandler.class);
	
	@Autowired
    private RedisUtil redisUtil;	
	@Autowired
	IProcessSchedule iProcessSchedule;

	/**
	 * 初始化
	 */
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		List<ProcessInstanceVO> returnList = new ArrayList<>();
		ReturnData<List<ProcessInstanceVO>> returnData = null;
		
		logger.info("getting all TTS...");
		while(true){
			try
			{
				returnData = iProcessSchedule.getAllTTS();
				if(returnData != null)
				{
					break;
				}
				
			} catch (Exception e)
			{
				logger.error("Process服务异常...");
				continue;
			}
		}
		
		if(returnData.getBody() != null && !returnData.getBody().isEmpty())
		{
			returnList = returnData.getBody();
		}else
		{
			logger.info("没有获取到TTS!");
			return;
		}
		logger.info("获取的TTS列表：" + returnList);
		
		Collections.sort(returnList, new Comparator<ProcessInstanceVO>() 
		{
			@Override
			public int compare(ProcessInstanceVO o1, ProcessInstanceVO o2) 
			{
				return o1.getProcessKey().compareTo(o2.getProcessKey());
			}
		});

		String modelName = returnList.get(0).getProcessKey();
		List<Object> gpuList = new ArrayList<>();

		for (int i = 0; i < returnList.size(); i++) 
		{
			TtsGpu gpu = new TtsGpu();
			gpu.setIp(returnList.get(i).getIp());
			gpu.setPort(String.valueOf(returnList.get(i).getPort()));

			if (!returnList.get(i).getProcessKey().equals(modelName)) 
			{
				redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
				modelName = returnList.get(i).getProcessKey();
				gpuList = new ArrayList<>();
			}
			gpuList.add(gpu);
		}
		redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
	}

}
