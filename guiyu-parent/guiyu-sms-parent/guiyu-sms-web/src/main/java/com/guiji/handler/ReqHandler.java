package com.guiji.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.guiji.common.exception.GuiyuException;
import com.guiji.platfrom.Cmpp;
import com.guiji.platfrom.Welink;
import com.guiji.platfrom.Ytx;
import com.guiji.service.ConfigService;
import com.guiji.service.RecordService;
import com.guiji.service.TaskDetailService;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;

@Component
public class ReqHandler
{
	private static final Logger logger = LoggerFactory.getLogger(ReqHandler.class);
	
	@Autowired
	TaskDetailService taskDetailService;
	@Autowired
	ConfigService configService;
	@Autowired
	RecordService recordService;
	@Autowired
	RedisUtil redisUtil;
	
	@Value("${cmppServiceUrl}")
	private String cmppServiceUrl;
	
	/**
	 * 处理短信请求
	 * @throws Exception 
	 */
	public void handleReq(SendMReqVO sendMReq) throws Exception
	{
		Map<String, Object> resultMap = isSend(sendMReq); // 判断配置
		SmsPlatform platform = (SmsPlatform) resultMap.get("platform");
		SmsTunnel tunnel = (SmsTunnel) resultMap.get("tunnel");
		SmsConfig config = (SmsConfig) resultMap.get("config");
		
		String phone = sendMReq.getPhone(); // 手机号
		Integer smsTemplateId = config.getSmsTemplateId(); // 短信模版
		String smsContent = config.getSmsContent(); // 短信内容
		
		Map params = JsonUtils.json2Bean(tunnel.getPlatformConfig(), Map.class); // 获取通道参数
		SmsRecord record = null;
		String identification = platform.getIdentification(); //根据内部标识选择平台
		
		if ("ytx".equals(identification)) {
			logger.info("通过<云讯>发送短信...");
			record = new Ytx().sendMessage(params, phone, smsTemplateId);
		} else if ("wl".equals(identification)) {
			logger.info("通过<微网通联>发送短信...");
			record = new Welink().sendMessage(params, phone, smsContent);
		} else if ("cmpp".equals(identification)) {
			logger.info("通过<CMPP>发送短信...");
			record = new Cmpp(cmppServiceUrl).sendMessage(params, phone, smsContent);
		}
		
		recordService.saveRecord(record, platform.getPlatformName()); //保存发送记录
		taskDetailService.saveTaskDetail(record, config, sendMReq); //保存短信发送详情
	}
	
	/**
	 * 判断配置
	 */
	private Map<String, Object> isSend(SendMReqVO sendMReq)
	{
		Map<String, Object> resultMap = new HashMap<>();
		// 获取配置
		SmsConfig config = configService.getConfigToSend(sendMReq.getIntentionTag(),sendMReq.getOrgCode(),sendMReq.getTemplateId());
		if (config.getAuditingStatus() == 0){
			logger.info("短信内容未审核，暂不能发送短信");
			throw new GuiyuException("短信内容未审核，暂不能发送短信");
		} else if (config.getRunStatus() == 0){
			logger.info("停止状态，不再发送短信");
			throw new GuiyuException("停止状态，不再发送短信");
		}
		// 获取通道
		SmsTunnel tunnel = (SmsTunnel) redisUtil.get(config.getTunnelName());
		if (tunnel == null){
			logger.info("没有短信通道，不发送短信");
			throw new GuiyuException("没有短信通道，不发送短信");
		}
		// 获取平台
		SmsPlatform platform = (SmsPlatform) redisUtil.get(tunnel.getPlatformName());
		if (platform == null){
			logger.info("没有短信平台，不发送短信");
			throw new GuiyuException("没有短信平台，不发送短信");
		}
		
		resultMap.put("platform", platform);
		resultMap.put("tunnel", tunnel);
		resultMap.put("config", config);
		return resultMap;
	}
}
