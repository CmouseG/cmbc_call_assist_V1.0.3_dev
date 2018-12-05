package com.guiji.ai.tts.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.impl.TtsGpu;
import com.guiji.guiyu.message.model.RestoreModelResultMsgVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;

/**
 * @date 2018年12月3日 下午2:33:14
 * @version V1.0
 */
@Component
@RabbitListener(queues = "fanoutRestoreModel.TTS")
public class AiMqListener
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 监听进程管理队列消息，目前主要处理： 1、
	 * 
	 * @param message
	 */
	@RabbitHandler
	public void process(String message)
	{

		RestoreModelResultMsgVO restoreModelResultMsgVO = JsonUtils.json2Bean(message, RestoreModelResultMsgVO.class);
		
		if (restoreModelResultMsgVO.getResult() == 1)
		{
			return;
		}
		
		int toModel = restoreModelResultMsgVO.getTo();
		int fromMode = restoreModelResultMsgVO.getFrom();
		String ip = restoreModelResultMsgVO.getIp();
		String port = String.valueOf(restoreModelResultMsgVO.getPort());
		
		int index  =  0;
		List<Object> gpuList = redisUtil.lGet(AiConstants.GUIYUTTS + fromMode + AiConstants.CHANGING, 0, -1);
		for(int i = 0; i < gpuList.size(); i++)
		{
			TtsGpu gpu = (TtsGpu) gpuList.get(i);
			if(ip.equals(gpu.getIp()) && port.equals(gpu.getPort())){
				index = i;
			}
		}

		redisUtil.lSet(AiConstants.GUIYUTTS + toModel + AiConstants.AVALIABLE, new TtsGpu(ip, port));
		redisUtil.lRemove(AiConstants.GUIYUTTS + fromMode + AiConstants.CHANGING, 1, gpuList.get(index));

	}

}
