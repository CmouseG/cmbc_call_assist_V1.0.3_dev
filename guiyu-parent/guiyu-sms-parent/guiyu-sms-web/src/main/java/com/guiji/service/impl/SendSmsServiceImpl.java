package com.guiji.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.model.TaskReq;
import com.guiji.platfrom.Cmpp;
import com.guiji.platfrom.Junlong;
import com.guiji.platfrom.ShenzhenCredit2;
import com.guiji.platfrom.Welink;
import com.guiji.platfrom.Ytx;
import com.guiji.platfrom.Zxy;
import com.guiji.service.RecordService;
import com.guiji.service.SendSmsService;
import com.guiji.service.TaskDetailService;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.MapUtil;
import com.guiji.utils.RedisUtil;

@Service
public class SendSmsServiceImpl implements SendSmsService
{
	private static final Logger logger = LoggerFactory.getLogger(SendSmsServiceImpl.class);
	
	@Autowired
	TaskDetailService taskDetailService;
	@Autowired
	RecordService recordService;
	@Autowired
	QueueSender queueSender;
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 将请求推送到MQ
	 */
	@Override
	public void pushReqToMQ(SendMReqVO sendMReq)
	{
		queueSender.send("SendMessageMQ.Sms", JsonUtils.bean2Json(sendMReq));
	}
	
	/**
	 * 将群发任务推送到MQ
	 */
	@Override
	public void pushTaskToMQ(TaskReq taskReq)
	{
		queueSender.send("MessagesTaskMQ.Sms", JsonUtils.bean2Json(taskReq));
	}
	
	/**
	 * 群发短信
	 */
	@Override
	public void preSendMsg(TaskReq taskReq) throws Exception
	{
		// 获取通道
		SmsTunnel tunnel = (SmsTunnel) redisUtil.get(taskReq.getTunnelName());
		// 获取平台
		SmsPlatform platform = (SmsPlatform) redisUtil.get(tunnel.getPlatformName());
		// 根据内部标识选择平台
		String identification = platform.getIdentification();
		// 获取通道参数
		Map params = JsonUtils.json2Bean(tunnel.getPlatformConfig(), Map.class);
		
		List<SmsRecord> records = null;
		
		if ("ytx".equals(identification)) {
			logger.info("通过<云讯>群发短信...");
			records = new Ytx().sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsTemplateId());
		} else if ("wl".equals(identification)) {
			logger.info("通过<微网通联>群发短信...");
			records = new Welink().sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsContent());
		} else if ("cmpp".equals(identification)) {
			logger.info("通过<CMPP>群发短信...");
			records = new Cmpp(MapUtil.getString(params, "cmppServiceUrl", 0)).sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsContent());
		} else if ("zxy".equals(identification)) {
			logger.info("通过<专信云>群发短信...");
			records = new Zxy().sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsContent());
		} else if ("zxy".equals(identification)) {
			logger.info("通过<深圳信用卡2专属>群发短信...");
			records = new ShenzhenCredit2().sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsContent());
		} else if ("jl".equals(identification)) {
			logger.info("通过<君隆科技>群发短信...");
			records = new Junlong().sendMessage(params, taskReq.getPhoneList(), taskReq.getSmsContent());
		}
		
		recordService.saveRecord(records, platform.getPlatformName()); //保存发送记录
		taskDetailService.saveTaskDetail(records, taskReq); //保存短信任务发送详情	
	}

}
