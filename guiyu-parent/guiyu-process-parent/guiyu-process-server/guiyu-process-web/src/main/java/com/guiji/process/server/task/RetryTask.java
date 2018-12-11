package com.guiji.process.server.task;


import com.guiji.process.server.model.ProcessTask;
import com.guiji.process.server.service.impl.ProcessManageService;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RetryTask {

	private static Logger logger = LoggerFactory.getLogger(RetryTask.class);

	private static final Long RETRY_INTERVAL = 300000L;

	private static final int RETRY_TIME = 3;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ProcessManageService processManageService;

	//定时任务，启动时运行（每分钟执行一次）
	@Scheduled(fixedRate = 1000*60)
    public void retryPublish(){
		List<String> jobs = (List<String>)redisUtil.get("GY_PROCESS_JOB");
		if (jobs != null) {
			for (String jobId : jobs) {
				Map<String,ProcessTask> processTaskMap = (Map<String,ProcessTask>)redisUtil.get(jobId);
				Map<String,ProcessTask> retryMap = new ConcurrentHashMap<String,ProcessTask>();
				if (processTaskMap != null) {
					for (Map.Entry<String, ProcessTask> entry: processTaskMap.entrySet()) {
						ProcessTask processTask = entry.getValue();
						if (!"0".equals(processTask.getResult())) {
							if (processTask.getRetryCount() < RETRY_TIME) {//retry次数超过3次则不在retry
								long interval = System.currentTimeMillis() - processTask.getExeTime().getTime();
								if (interval > RETRY_INTERVAL) {//retry间隔10分钟
									int retryTime = processTask.getRetryCount() + 1;
									processTask.setRetryCount(retryTime);
									processTask.setExeTime(new Date());
									processTaskMap.put(entry.getKey(),processTask);
									retryMap.put(entry.getKey(),processTask);
								}
							}
						}
					}
					redisUtil.set(jobId,processTaskMap);

					for (Map.Entry<String, ProcessTask> entry: retryMap.entrySet()) {
						// Do Retry
						ProcessTask processTask = entry.getValue();
						processManageService.cmd(processTask.getProcessInstanceVO(), processTask.getCmdType(), processTask.getParameters(),processTask.getUserId(),entry.getKey());
					}

				}
			}
		}
    }
}
