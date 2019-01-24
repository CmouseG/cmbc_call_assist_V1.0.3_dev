package com.guiji.platfrom;

import java.util.List;
import java.util.Map;

import com.guiji.sms.dao.entity.SmsRecord;

public interface ISendMsg
{
	SmsRecord sendMessage(Map<String, Object> params, String phone, Integer templateId) throws Exception;
	
	List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, Integer templateId) throws Exception;
}
