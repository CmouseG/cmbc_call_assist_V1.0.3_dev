package com.guiji.ai.tts.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.impl.TtsGpu;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.utils.RedisUtil;

@Component
public class AcquireTTSHandler {
	private static Logger logger = LoggerFactory.getLogger(AcquireTTSHandler.class);
	
	@Autowired
    RedisUtil redisUtil;	
	@Autowired
	IProcessSchedule iProcessSchedule;

	/**
	 * 获取所有的TTS
	 */
	public void getAllTTS() throws Exception {
		List<ProcessInstanceVO> processInstanceList = new ArrayList<>();
		ReturnData<List<ProcessInstanceVO>> returnData = null;

		logger.info("getting all TTS...");
		try
		{
			returnData = iProcessSchedule.getAllTTS();
		} catch (Exception e)
		{
			logger.error("Process服务异常...", e);
		}

		if (returnData != null && returnData.getBody() != null && !returnData.getBody().isEmpty())
		{
			processInstanceList = returnData.getBody();
		} else
		{
			logger.info("没有TTS!");
			return;
		}
		logger.info("获取的TTS列表：" + processInstanceList);
		
		Collections.sort(processInstanceList, new Comparator<ProcessInstanceVO>() 
		{
			@Override
			public int compare(ProcessInstanceVO o1, ProcessInstanceVO o2) 
			{
				return o1.getProcessKey().compareTo(o2.getProcessKey());
			}
		});

		String modelName = processInstanceList.get(0).getProcessKey();
		List<Object> gpuList = new ArrayList<>();

		for (int i = 0; i < processInstanceList.size(); i++) 
		{
			TtsGpu gpu = new TtsGpu();
			gpu.setIp(processInstanceList.get(i).getIp());
			gpu.setPort(String.valueOf(processInstanceList.get(i).getPort()));

			if (!processInstanceList.get(i).getProcessKey().equals(modelName)) 
			{
				redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
				modelName = processInstanceList.get(i).getProcessKey();
				gpuList = new ArrayList<>();
			}
			gpuList.add(gpu);
		}
		redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
	}

}
