package com.guiji.process.server.task;

import com.guiji.process.server.model.ProcessTask;
import com.guiji.process.server.service.impl.ProcessManageService;
import com.guiji.utils.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1. 在POM中添加引用
 * <dependency>
 *             <groupId>guiji.task.job</groupId>
 *             <artifactId>guiyu-timerTask-client</artifactId>
 *             <version>1.0-SNAPSHOT</version>
 *  </dependency>
 *
 * 2.在yml文件中添加#xxl-job admin中心配置
 * xxl:
 *   job:
 *     admin:
 *       ### xxl-job admin address list, such as "http://address" or "http://address01,http://address02"
 *       addresses: http://192.168.1.32:18016/xxl-job-admin
 *     executor:
 *       ip:
 *       port: 9987
 *       logpath: /home/logs/process-web
 *       logretentiondays: -1
 *     accessToken:
 * 3、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 4、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 5、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 6、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author zhujiayu 2019-1-8 19:43:36
 */
@JobHandler(value="retryTaskJobHandler")
@Component
public class RetryTaskJobHandler extends IJobHandler {

	private static final Long RETRY_INTERVAL = 300000L;

	private static final int RETRY_TIME = 3;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ProcessManageService processManageService;

	private static Logger logger = LoggerFactory.getLogger(RetryTaskJobHandler.class);

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB, RetryTaskJobHandler start.");

		List<String> jobs = (List<String>)redisUtil.get("GY_PROCESS_JOB");
		if (jobs != null) {
			for (String jobId : jobs) {
				Map<String,ProcessTask> processTaskMap = (Map<String,ProcessTask>)redisUtil.get(jobId);
				Map<String,ProcessTask> retryMap = new ConcurrentHashMap<String,ProcessTask>();
				if (processTaskMap != null) {
					for (Map.Entry<String, ProcessTask> entry: processTaskMap.entrySet()) {
						ProcessTask processTask = entry.getValue();
						if (processTask != null && processTask.getResult() != null && 0 != processTask.getResult()) {
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

		XxlJobLogger.log("XXL-JOB, RetryTaskJobHandler end.");
		return SUCCESS;
	}

}
