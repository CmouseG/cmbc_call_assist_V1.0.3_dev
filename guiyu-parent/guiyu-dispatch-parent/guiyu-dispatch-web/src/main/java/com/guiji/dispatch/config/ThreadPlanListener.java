package com.guiji.dispatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.pushcallcenter.IPushPhonesHandler;
import com.guiji.utils.RedisUtil;

@Component
public class ThreadPlanListener implements ApplicationRunner {
	static Logger logger = LoggerFactory.getLogger(ThreadPlanListener.class);
	@Autowired
	private IPushPhonesHandler handler;
	@Autowired
	private RedisUtil redisUtils;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new Thread(() -> {
			try {
				//初始化当前redis计数器
				redisUtils.set("REDIS_CURRENTLY_COUNT", 0);
				handler.pushHandler();
			} catch (Exception e) {
				logger.error("PlanListener:" + e.getMessage());
			}
		}).start();
	}
}
