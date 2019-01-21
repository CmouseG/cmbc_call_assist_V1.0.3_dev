package com.guiji.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.service.RecordService;
import com.guiji.sms.dao.SmsRecordMapper;
import com.guiji.sms.dao.entity.SmsRecord;

@Service
public class RecordServiceImpl implements RecordService
{
	private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);
	
	@Autowired
	SmsRecordMapper recordMapper;
	
	/*
	 * 保存云讯发送记录
	 */
	@Override
	public void saveYtxRecord(JSONObject returnData, String platformName, String phone)
	{
		//返回参数
		String requestId = (String) returnData.get("requestId");
		String statusCode = (String) returnData.get("statusCode");
		String statusMsg = (String) returnData.get("statusMsg");
		
		SmsRecord record = new SmsRecord();
		record.setPlatform(platformName);
		record.setPhone(phone);
		record.setStatusCode(statusCode);
		record.setStatusMsg(statusMsg);
		
		if("0".equals(statusCode)){
			logger.info("发送成功:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
			record.setSendStatus(1); // 1-发送成功
		}else{
			logger.info("发送失败:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
			record.setSendStatus(0); // 0-发送失败
		}
		recordMapper.insertSelective(record);
	}
	
}
