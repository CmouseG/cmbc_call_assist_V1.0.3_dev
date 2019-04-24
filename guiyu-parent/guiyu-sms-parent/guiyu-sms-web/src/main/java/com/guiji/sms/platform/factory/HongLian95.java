package com.guiji.sms.platform.factory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import com.guiji.sms.utils.SetDetailParamsUtil;

/**
 * 短信平台-鸿联九五
 * @author Sun
 */
public class HongLian95 implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(HongLian95.class);
	private String url = "http://q.hl95.com:8061";
	
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
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", params.getString("username"));
		paramMap.put("password", params.getString("password"));
		paramMap.put("epid", params.getString("epid"));
		paramMap.put("phone", phone);
		try{
			paramMap.put("message",URLEncoder.encode(params.getString("smsContent"), "gb2312"));
		} catch (UnsupportedEncodingException e){
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_SMS);
		}
		String paramStr = "";
		if (paramMap != null && paramMap.size() > 0){
			for (Map.Entry<String, String> entry : paramMap.entrySet()){
				paramStr += "&" + entry.getKey() + "=" + entry.getValue();
			}
			paramStr = paramStr.substring(1);
		}
		String result = doGet(paramStr); // 发送请求
		record = handleResult(result, params); // 处理结果
		record.setPhone(phone);
		return record;
	}

	// 发送请求
	private String doGet(String paramStr)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try{
			HttpGet httpGet = new HttpGet(url+"?"+paramStr);
			response = httpClient.execute(httpGet); // 执行请求
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			EntityUtils.consume(responseEntity);
		}
		catch (Exception e){
			log.error("调用短信平台商服务异常", e);
			result = "404";
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
		if("00".equals(result)){
			log.info("发送成功:result:{}",result);
			record.setSendStatus(1);
		}else{
			log.info("发送失败:result:{}",result);
			record.setSendStatus(0);
			record.setFailReason(result);
		}
		SetDetailParamsUtil.setParams(record,params); // 设置结果（发送详情）参数
		return record;
	}

}
