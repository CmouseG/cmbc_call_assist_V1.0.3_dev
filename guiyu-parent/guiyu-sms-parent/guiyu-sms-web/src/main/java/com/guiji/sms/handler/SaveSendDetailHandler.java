package com.guiji.sms.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.sms.dao.SmsSendDetailMapper;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.queue.SendDetailQueue;
import com.guiji.sms.utils.AuthUtil;

/**
 * 保存短信发送详情处理类
 * @author Sun
 */
@Component
public class SaveSendDetailHandler implements ApplicationRunner
{
	private static final Logger log = LoggerFactory.getLogger(SaveSendDetailHandler.class);
	
	@Autowired
	SmsSendDetailMapper sendDetailMapper;
	@Autowired
	AuthUtil authUtil;

	@Override
	public void run(ApplicationArguments args)
	{
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(new Runnable()
		{
			@Override
			public void run() {
				while(true) {
					try {
						SmsSendDetail record = SendDetailQueue.get();
						if(record != null)
						{
							record.setOrgName(authUtil.getOrgNameByOrgCode(record.getOrgCode()));
							record.setCreateName(authUtil.getUserNameByUserId(record.getCreateId().longValue()));
							sendDetailMapper.insertSelective(record);
						}
						else{continue;}
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						continue;
					}
				}
			}
		});
	}
	
}
