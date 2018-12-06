package com.guiji.ai.tts.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.handler.AcquireTTSHandler;

@Component
public class TtsListener
{
	private static final Logger logger = LoggerFactory.getLogger(TtsListener.class);

	@Autowired
	AcquireTTSHandler acquireTTSHandler;

	// 每隔3分钟获取一次TTS
	@Scheduled(fixedRate = 1000 * 60 * 3)
	public void task() throws InterruptedException
	{
		try
		{
			logger.info("定时获取TTS...");
			acquireTTSHandler.getAllTTS();

		} catch (Exception e)
		{
			logger.error("定时获取TTS发生异常！", e);
		}
	}
}
