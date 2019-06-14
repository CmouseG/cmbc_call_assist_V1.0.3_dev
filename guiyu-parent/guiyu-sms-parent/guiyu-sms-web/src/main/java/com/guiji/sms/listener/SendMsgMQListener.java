package com.guiji.sms.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.api.bean.SendMReqVO;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.handler.SendMsgHandler;
import com.guiji.sms.service.ConfigService;
import com.guiji.sms.utils.JsonUtil;
import com.guiji.utils.RedisUtil;

@RabbitListener(queues = "SendMessageMQ.direct.Sms")
@Component
public class SendMsgMQListener
{
	private static final Logger log = LoggerFactory.getLogger(SendMsgMQListener.class);
	
	@Autowired
	ConfigService configService;
	@Autowired
	SendMsgHandler sendMsgHandler;
	@Autowired
	RedisUtil redisUtil;
	
	@RabbitHandler
	public void process(String message)
	{
		try {
			SendMReqVO sendMReq = JsonUtil.jsonStr2Bean(message, SendMReqVO.class);
//			log.info("MQ："+sendMReq.toString());
			execute(sendMReq); // 处理消息
		} catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}

	// 处理消息
	private void execute(SendMReqVO sendMReq)
	{
		String orgCode = sendMReq.getOrgCode();
		String templateId = sendMReq.getTemplateId();
		String intentionTag = sendMReq.getIntentionTag(); // 都是单个意向标签
		// 获取短信配置
		// 获取短信配置
		SmsConfig config = redisUtil.getT(orgCode+"_"+templateId+"_"+intentionTag);
		if(config == null){
			config = configService.getSendConfig(templateId, intentionTag, orgCode);
			if(config == null){
				SmsConfig config2 = new SmsConfig();
				redisUtil.set(orgCode +"_"+templateId+"_"+intentionTag, config2, 5*60);
				return;
			}
			redisUtil.set(orgCode+"_"+templateId+"_"+intentionTag, config);
		}
		else if(StringUtils.isEmpty(config.getTunnelName())){
			return;
		}
		// 获取短信通道
		SmsTunnel tunnel = redisUtil.getT(config.getTunnelName());
		if(tunnel == null) {log.error("未能获取到短信通道"); return;}
		// 获取短信平台
		SmsPlatform platform = redisUtil.getT(tunnel.getPlatformName());
		if(platform == null) {log.error("未能获取到短信平台"); return;}

		log.info("MQ："+sendMReq.toString());
		// 内部标识
		String identification = platform.getIdentification();
		// 手机号列表
		List<String> phoneList = new ArrayList<>();
		phoneList.add(sendMReq.getPhone());
		// 平台配置参数 + 发送详情记录参数
		JSONObject params = JsonUtil.jsonStr2JsonObj(tunnel.getPlatformConfig());
		params.put("smsContent", config.getSmsContent());
		params.put("orgCode", sendMReq.getOrgCode());
		params.put("tunnelName", config.getTunnelName());
		params.put("taskName", "挂机短信");
		params.put("createId", sendMReq.getUserId());
		params.put("createTime", new Date());
		
		sendMsgHandler.choosePlatformToSend(identification, params, phoneList); // 根据内部标识选择平台发送
	}	
	
}
