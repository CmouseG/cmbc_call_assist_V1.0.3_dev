package com.guiji.ai.tts.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.ai.dao.TtsStatusMapperExt;
import com.guiji.ai.tts.JumpTaskQueue;
import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.utils.RedisUtil;

public class TranslateHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(TranslateHandler.class);

	public void run(ITtsService ttsService, TtsStatusMapperExt ttsStatusMapperExt, RedisUtil redisUtil) 
	{
		TtsReqVO ttsReqVO = null;
		while(true)
		{
			try 
			{
				//如果插队任务队列不为空，优先执行插队任务
				if (!JumpTaskQueue.getInstance().isEmpty())
				{
					ttsReqVO = JumpTaskQueue.getInstance().get();
				} else
				{
					ttsReqVO = TtsReqQueue.getInstance().get();
				}
				if(ttsReqVO == null)
				{
					continue;
				}
				String busId = ttsReqVO.getBusId();
				//防止JumpTaskQueue中的任务在TtsReqQueue中重复执行
				Object obj = redisUtil.get(AiConstants.TASK + busId);
				if(obj == null){
					continue;
				}
				//删除此任务缓存
				redisUtil.del(AiConstants.TASK + busId);
				//修改表状态
				ttsStatusMapperExt.updateStatusByBusId(busId, AiConstants.DOING);
				//语言合成
				ttsService.translate(ttsReqVO);
				
			} catch (Exception e) {
				logger.error("处理异常", e);
				continue;
			}
		}
	}



}
