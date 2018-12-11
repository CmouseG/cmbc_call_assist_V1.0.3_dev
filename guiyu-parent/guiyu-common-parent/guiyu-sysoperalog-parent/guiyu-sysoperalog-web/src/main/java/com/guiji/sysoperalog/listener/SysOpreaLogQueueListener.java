package com.guiji.sysoperalog.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.sysoperalog.handler.SysOpreaLogHandler;
import com.guiji.sysoperalog.service.ISysOperaLogService;

@Component
public class SysOpreaLogQueueListener implements ApplicationRunner
{
	@Autowired
	ISysOperaLogService ISysOperaLogService;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		new Thread(() -> {
			new SysOpreaLogHandler().run(ISysOperaLogService);
		}).start();
		
	}

}
