package com.guiji.process.agent.timer;

import com.guiji.process.agent.service.ProcessServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckStatusTask {
	private static Logger logger = LoggerFactory.getLogger(CheckStatusTask.class);
	@Autowired
	ProcessServiceImpl processService;
	
	//定时任务，启动时运行（每1分钟执行一次）
	@Scheduled(fixedRate = 1000*5)
    public void checkStatusTask() throws InterruptedException {
		String pid = processService.getPid("18001");
		System.out.println(pid);
    }
}
