package com.guiji.platfrom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.platfrom.send.ISendMsg;
import com.guiji.sms.dao.entity.SmsRecord;

public class Cmpp implements ISendMsg
{
	private static final Logger logger = LoggerFactory.getLogger(Cmpp.class);
	
	private String cmppServiceUrl;
	
	public Cmpp(String cmppServiceUrl)
	{
		this.cmppServiceUrl = cmppServiceUrl;
	}

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, Integer templateId) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, Integer templateId)
			throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, String msgContent) throws Exception
	{
		SmsRecord record = null;
		JSONObject json = new JSONObject();
		json.put("phone", phone);
		json.put("msgContent", msgContent);
		record = send(json);
		record.setPhone(phone);
		return record;
	}

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		
		JSONObject json = new JSONObject();
		json.put("msgContent", msgContent);
		for(String phone : phoneList)
		{
			json.put("phone", phone);
			record = send(json);
			record.setPhone(phone);
			records.add(record);
		}
		return records;
	}
	
	private SmsRecord send(JSONObject json)
	{
		SmsRecord record = new SmsRecord();
		String result = doPost(json.toJSONString()); // 发送请求
		JSONObject returnData = JSONObject.parseObject(result);
		// 返回参数
		String code = returnData.getString("code");
		String msg = returnData.getString("msg");
		String data = returnData.getString("data");
		
		if ("0".equals(code))
		{
			logger.info("发送成功:code:{},msg:{},data:{}", code, msg, data);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:code:{},msg:{},data:{}", code, msg, data);
			record.setSendStatus(0);
		}

		record.setStatusCode(code);
		record.setStatusMsg(msg);
		return record;
	}

	private String doPost(String json)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try
		{
			HttpPost httpPost = new HttpPost(cmppServiceUrl);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost); // 执行请求
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} 
		catch (Exception e){
			logger.error("调用接口异常！", e);
			result = "{\"code\":\"404\",\"msg\":\"调用接口异常\",\"data\":\"\"}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}

}
