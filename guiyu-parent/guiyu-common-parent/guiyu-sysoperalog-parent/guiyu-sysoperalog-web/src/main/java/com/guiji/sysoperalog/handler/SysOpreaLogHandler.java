package com.guiji.sysoperalog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.sysoperalog.queue.SysOpreaLogQueue;
import com.guiji.sysoperalog.service.ISysOperaLogService;

public class SysOpreaLogHandler
{
	private static final Logger logger = LoggerFactory.getLogger(SysOpreaLogHandler.class);
	
	public void run(ISysOperaLogService ISysOperaLogService)
	{
		SysUserAction sysUserAction = null;
		while(true)
		{
			try
			{
				sysUserAction = SysOpreaLogQueue.getInstance().get();
				if(sysUserAction == null) {
					continue;
				}
				
				ISysOperaLogService.insertSysUserAction(sysUserAction);
				
			} catch (InterruptedException e)
			{
				logger.error("处理异常", e);
				continue;
			}
		}
	}

}
