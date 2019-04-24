package com.guiji.sms.listener;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.dao.SmsTaskMapper;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.handler.SendMsgHandler;
import com.guiji.sms.utils.DateUtil;
import com.guiji.sms.utils.JsonUtil;
import com.guiji.utils.RedisUtil;

@RabbitListener(queues = "SendMessageTaskMQ.direct.Sms")
@Component
public class SendMsgTaskMQListener
{
	private static final Logger log = LoggerFactory.getLogger(SendMsgTaskMQListener.class);
	
	@Autowired
	SmsTaskMapper taskMapper;
	@Autowired
	RedisUtil redisUtil;
	
	@RabbitHandler
	public void process(String message)
	{
		try {
			SmsTask smsTask = JsonUtil.jsonStr2Bean(message, SmsTask.class);
			log.info("Task："+smsTask.toString());
			executeTask(smsTask); // 执行任务
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}

	// 执行任务
	private void executeTask(SmsTask smsTask)
	{
		smsTask.setSendStatus(1); // 发送中
		smsTask.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
		taskMapper.updateByPrimaryKey(smsTask);
		// 获取短信通道
		SmsTunnel tunnel = redisUtil.getT(smsTask.getTunnelName());
		if(tunnel == null) {log.error("未能获取到短信通道"); return;}
		// 获取短信平台
		SmsPlatform platform = redisUtil.getT(tunnel.getPlatformName());
		if(platform == null) {log.error("未能获取到短信平台"); return;}
		
		// 内部标识
		String identification = platform.getIdentification();
		// 手机号列表
		List<String> phoneList = redisUtil.getT("TASK_PHONES_"+smsTask.getId());
		// 平台配置参数 + 发送详情记录参数
		JSONObject params = JsonUtil.jsonStr2JsonObj(tunnel.getPlatformConfig());
		params.put("smsContent", smsTask.getSmsContent());
		params.put("orgCode", smsTask.getOrgCode());
		params.put("tunnelName", smsTask.getTunnelName());
		params.put("taskName", smsTask.getTaskName());
		params.put("createId", smsTask.getCreateId());
		params.put("createTime", new Date());
		
		SendMsgHandler.choosePlatformToSend(identification, params, phoneList); // 根据内部标识选择平台发送
	}
}
