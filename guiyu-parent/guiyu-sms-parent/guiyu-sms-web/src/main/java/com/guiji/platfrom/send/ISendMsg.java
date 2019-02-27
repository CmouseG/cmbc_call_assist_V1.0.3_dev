package com.guiji.platfrom.send;

import java.util.List;
import java.util.Map;

import com.guiji.sms.dao.entity.SmsRecord;

public interface ISendMsg
{
	List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, Integer templateId) throws Exception;
	
	SmsRecord sendMessage(Map<String, Object> params, String phone, Integer templateId) throws Exception;
	
	List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception;
	
	SmsRecord sendMessage(Map<String, Object> params, String phone, String msgContent) throws Exception;
	
}
