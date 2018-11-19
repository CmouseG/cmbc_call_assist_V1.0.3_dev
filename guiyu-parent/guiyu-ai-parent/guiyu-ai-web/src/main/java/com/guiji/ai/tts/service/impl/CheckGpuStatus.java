package com.guiji.ai.tts.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CheckGpuStatus implements ApplicationRunner{
	
	private static Logger logger = LoggerFactory.getLogger(CheckGpuStatus.class);

	/**
	 * 查看当前GPU使用情况
	 */
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		logger.info("调用进程管理接口查看GPU分配情况");
		//TODO
	}

}
