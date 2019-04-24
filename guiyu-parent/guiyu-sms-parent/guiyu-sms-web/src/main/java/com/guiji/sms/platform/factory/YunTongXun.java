package com.guiji.sms.platform.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.platform.ISendMessage;
import com.guiji.sms.queue.SendDetailQueue;
import com.guiji.sms.utils.CryptographyUtil;
import com.guiji.sms.utils.DateUtil;
import com.guiji.sms.utils.SetDetailParamsUtil;

/**
 * 云通讯短信平台
 */
public class YunTongXun implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(YunTongXun.class);

	@SuppressWarnings("rawtypes")
	@Override
	public void sendMessage(JSONObject params, List<String> phoneList)
	{
		try{
			ExecutorService executorService = Executors.newFixedThreadPool(20);
			CompletableFuture[] cfs = phoneList.stream().map( phone -> 
					CompletableFuture.supplyAsync( () -> 
							send(params, phone), executorService) // 多线程执行
					.whenComplete((record,exception) -> {
						SendDetailQueue.add(record);  // 结果回调
					})).toArray(CompletableFuture[]::new);
			CompletableFuture.allOf(cfs).join(); // 等待所有子线程执行完毕
		} catch (Exception e){
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_SMS);
		}
	}
	
	private SmsSendDetail send(JSONObject params, String phone)
	{
		SmsSendDetail record = null;
		String accountSID = params.getString("accountSID");
		String authToken = params.getString("authToken");
		String date = DateUtil.getSimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 时间戳：有效时间为24小时，格式"yyyyMMddHHmmss"
		String authorization = CryptographyUtil.encodeBase64(accountSID + ":" + date); // base64加密(账户Id + ":" + 时间戳)
		String sigParameter = CryptographyUtil.encodeMD5_32bit_UpperCase(accountSID + authToken + date); // MD5加密（账户Id + 账户授权令牌  + 时间戳)
		String url = "https://app.cloopen.com:8883/2013-12-26/Accounts/" 
				+ accountSID 
				+ "/SMS/TemplateSMS?sig=" 
				+ sigParameter;
		JSONObject json = new JSONObject();
		json.put("to", phone);
		json.put("appId", params.getString("appId"));
		json.put("templateId", params.getString("smsContent"));
		json.put("datas", new ArrayList<>());
		String result = doPost(url, json.toJSONString(), authorization); // 发送请求
		record = handleResult(result, params); // 处理结果
		record.setPhone(phone);
		return record;
	}

	// 发送请求
	private String doPost(String url, String jsonStr, String authorization)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			httpPost.setHeader("Authorization", authorization);
			StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			EntityUtils.consume(responseEntity);
		} 
		catch (Exception e){
			log.error("调用短信平台商服务异常", e);
			result = "{\"statusCode\":\"404\",\"statusMsg\":\"调用短信平台商服务异常\"}";
		}
		finally {
			IOUtils.close(response);
			IOUtils.close(httpClient);
		}
		return result;
	}
	
	// 处理结果
	private SmsSendDetail handleResult(String result, JSONObject params)
	{
		SmsSendDetail record = new SmsSendDetail();
		JSONObject returnData = JSONObject.parseObject(result);
		JSONObject templateSMS = returnData.getJSONObject("templateSMS");
		// 返回参数
		String statusCode = returnData.getString("statusCode");
		String dateCreated = templateSMS.getString("dateCreated");
		String smsMessageSid = templateSMS.getString("smsMessageSid");
		
		if("000000".equals(statusCode)){
			log.info("发送成功:statusCode:{},dateCreated:{},smsMessageSid:{}", statusCode, dateCreated, smsMessageSid);
			record.setSendStatus(1);
		}else{
			String statusMsg = returnData.getString("statusMsg");
			if(statusMsg == null){
				statusMsg = statusCode;
			}
			log.info("发送失败:statusCode:{},dateCreated:{},smsMessageSid:{}", statusCode, dateCreated, smsMessageSid);
			record.setSendStatus(0);
			record.setFailReason(statusMsg);
		}
		SetDetailParamsUtil.setParams(record,params); // 设置结果（发送详情）参数
		return record;
	}

}
