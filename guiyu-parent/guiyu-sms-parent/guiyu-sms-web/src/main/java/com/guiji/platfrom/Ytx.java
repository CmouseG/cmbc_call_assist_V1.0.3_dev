package com.guiji.platfrom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
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
import com.guiji.platfrom.send.ISendMsgByTemplateId;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.Base64MD5Util;
import com.guiji.utils.MapUtil;

/**
 * 云讯
 */
public class Ytx implements ISendMsgByTemplateId
{
	private static final Logger logger = LoggerFactory.getLogger(Ytx.class);

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, Integer templateId) throws Exception
	{
		SmsRecord record = null;
		Map<String, String> commonParams = prepareCommonParams(params);
		String url = commonParams.get("url");
		String authorization = commonParams.get("authorization");
		
		JSONObject json = setRequestParams(params,templateId);
		json.put("mobile", phone);

		record = send(authorization, url, json);
		record.setPhone(phone);
		return record;
	}

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, Integer templateId) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		
		Map<String, String> commonParams = prepareCommonParams(params);
		String url = commonParams.get("url");
		String authorization = commonParams.get("authorization");
		
		JSONObject json = setRequestParams(params,templateId);
		for(String phone : phoneList)
		{
			json.put("mobile", phone);
			record = send(authorization, url, json);
			record.setPhone(phone);
			records.add(record);
		}
		return records;
	}
	
	/*
	 * 发送
	 */
	private SmsRecord send(String authorization, String url, JSONObject json)
	{
		SmsRecord record = new SmsRecord();
		String result = doPost(url, json.toJSONString(), authorization); // 发送请求
		JSONObject returnData = JSONObject.parseObject(result);
		// 返回参数
		String statusCode = (String) returnData.get("statusCode");
		String statusMsg = (String) returnData.get("statusMsg");
		String requestId = (String) returnData.get("requestId");

		if ("0".equals(statusCode)){
			logger.info("发送成功:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
			record.setSendStatus(1);
		} else{
			logger.info("发送失败:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
			record.setSendStatus(0);
		}

		record.setStatusCode(statusCode);
		record.setStatusMsg(statusMsg);
		return record;
	}
	
	/*
	 * 云讯-发送请求
	 */
	private String doPost(String url, String json, String authorization)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try
		{
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Authorization", authorization);
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost); // 执行请求
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} 
		catch (Exception e){
			logger.error("调用接口异常！", e);
			result = "{\"statusCode\":\"404\",\"statusMsg\":\"调用接口异常\"}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}
	
	/*
	 * 公共参数
	 */
	private Map<String, String> prepareCommonParams(Map<String, Object> params) throws Exception{
		Map<String, String> commonParams = new HashMap<>();
		
		String accountSID = MapUtil.getString(params, "accountSID", 0);
		String authToken = MapUtil.getString(params, "authToken", 0);
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 时间戳：有效时间为24小时，格式"yyyyMMddHHmmss"
		String authorization = Base64.getEncoder().encodeToString((accountSID + "|" + date).getBytes()); // base64加密(账户Id + "|" + 时间戳)
		String sign = Base64MD5Util.encryption(accountSID + authToken + date); // MD5加密（账户Id + 账户授权令牌  + 时间戳)
		String url = "http://api.ytx.net/201512/sid/" 
					+ accountSID 
					+ "/sms/TemplateSMS.wx?Sign=" 
					+ sign;
		
		commonParams.put("authorization", authorization);
		commonParams.put("url", url);
		return commonParams;
	}
	
	/*
	 * 请求参数
	 */
	private JSONObject setRequestParams(Map<String, Object> params, Integer templateId) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("action", "templateSms");
		json.put("appid", MapUtil.getString(params, "appid", 0));
		json.put("templateId", templateId);
		json.put("spuid", "646");
		json.put("sppwd", "257693");
		return json;
	}
	
}
