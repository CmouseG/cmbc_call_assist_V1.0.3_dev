package com.guiji.dispatch.job;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 *
 */
@JobHandler(value="planDistributeJobHandler")
@Component
public class PlanDistributeJobHandler extends IJobHandler {

	private static final Logger logger = LoggerFactory.getLogger(PlanDistributeJobHandler.class);

	@Autowired
	private IResourcePoolService resourcePoolService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB, planDistributeJobHandler start.");
		resourcePoolService.distributeByUser();// 默认锁设置
		XxlJobLogger.log("XXL-JOB, planDistributeJobHandler end.");
		return SUCCESS;
	}

}
