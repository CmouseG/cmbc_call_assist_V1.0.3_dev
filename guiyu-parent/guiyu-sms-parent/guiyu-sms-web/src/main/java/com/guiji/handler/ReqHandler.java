package com.guiji.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.platfrom.yunxun.Ytx;
import com.guiji.platfrom.yunxun.YtxParams;
import com.guiji.service.ConfigService;
import com.guiji.service.RecordService;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;

@Component
public class ReqHandler
{
	private static final Logger logger = LoggerFactory.getLogger(ReqHandler.class);
	
	@Autowired
	ConfigService configService;
	@Autowired
	RecordService recordService;
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 处理短信请求
	 */
	public void handleReq(SendMReqVO sendMReq)
	{
		//获取配置
		SmsConfig config = configService.getConfigByIntentionTagAndOrgCode(sendMReq.getIntentionTag(), sendMReq.getOrgCode());
		if(config == null){
			logger.info("没有短信配置，不发送短信");
			return;
		} else if(config.getAuditingStatus() == 0) {
			logger.info("短信内容未审核，暂不能发送短信");
			return;
		} else if(config.getRunStatus() == 0) {
			logger.info("已停止，不再发送短信");
			return;
		}
		//获取通道
		SmsTunnel tunnel =  (SmsTunnel) redisUtil.get(config.getTunnelName());
		if(tunnel == null){
			logger.info("没有短信通道，不发送短信");
			return;
		}
		//获取平台
		SmsPlatform platform = (SmsPlatform) redisUtil.get(tunnel.getPlatformName());
		if(platform == null){
			logger.info("没有短信平台，不发送短信");
			return;
		}
		
		//根据内部标识选择平台
		String identification = platform.getIdentification();
		
		// 云讯平台
		if("ytx".equals(identification))
		{
			YtxParams ytxParams = JsonUtils.json2Bean(tunnel.getPlatformConfig(), YtxParams.class);
			ytxParams.setTemplateId(config.getSmsTemplateId());
			ytxParams.setMobile(sendMReq.getPhone());
			JSONObject returnData = new Ytx().sendMessageByYunXun(ytxParams); //发送短信
			recordService.saveYtxRecord(returnData,platform.getPlatformName(),sendMReq.getPhone()); //保存发送记录
		}
	}
}
