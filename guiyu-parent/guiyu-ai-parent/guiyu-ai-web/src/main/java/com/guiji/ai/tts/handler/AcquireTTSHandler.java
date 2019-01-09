package com.guiji.ai.tts.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.tts.service.impl.func.TtsGpu;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.utils.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@JobHandler(value="acquireTtsTaskHandler")
@Component
public class AcquireTTSHandler extends IJobHandler{
	
	@Autowired
    RedisUtil redisUtil;
	@Autowired
	IModelService modelService;
	@Autowired
	IProcessSchedule iProcessSchedule;

	/**
	 * 定时任务-获取所有的TTS
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception
	{
		List<ProcessInstanceVO> processInstanceList = new ArrayList<>();
		ReturnData<List<ProcessInstanceVO>> returnData = null;

		XxlJobLogger.log("getting all TTS...");
		try
		{
			returnData = iProcessSchedule.getAllTTS();
		} catch (Exception e)
		{
			XxlJobLogger.log("Process服务异常...", e);
		}

		if (returnData != null && returnData.getBody() != null && !returnData.getBody().isEmpty())
		{
			processInstanceList = returnData.getBody();
		} else
		{
			XxlJobLogger.log("没有TTS!");
			return null;
		}
		XxlJobLogger.log("获取的TTS列表：" + processInstanceList);
		
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

		for (ProcessInstanceVO processInstance : processInstanceList) 
		{
			//入库
			modelService.saveModel(processInstance);
			
			TtsGpu gpu = new TtsGpu();
			gpu.setIp(processInstance.getIp());
			gpu.setPort(String.valueOf(processInstance.getPort()));

			if (!processInstance.getProcessKey().equals(modelName)) 
			{
				redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
				modelName = processInstance.getProcessKey();
				gpuList = new ArrayList<>();
			}
			gpuList.add(gpu);
		}
		redisUtil.lSet(AiConstants.GUIYUTTS + modelName + AiConstants.AVALIABLE, gpuList);
		return null;
	}

}
