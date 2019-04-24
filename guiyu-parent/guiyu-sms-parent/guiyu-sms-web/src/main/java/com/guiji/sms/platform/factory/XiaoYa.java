package com.guiji.sms.platform.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

public class XiaoYa implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(XiaoYa.class);
	private String url = "http://sms.duoduo100.cn/api/sms/send";
	
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
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("account",  params.getString("account")));
		paramsList.add(new BasicNameValuePair("pwd", params.getString("pwd")));
		paramsList.add(new BasicNameValuePair("sign", params.getString("sign")));
		paramsList.add(new BasicNameValuePair("mobile", phone));
		paramsList.add(new BasicNameValuePair("content", params.getString("smsContent")));
		String result = doPost(paramsList); // 发送请求
		record = handleResult(result, params); // 处理结果
		record.setPhone(phone);
		return record;
	}
	
	// 发送请求
	private String doPost(List<NameValuePair> paramsList)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			EntityUtils.consume(responseEntity);
		} 
		catch (Exception e){
			log.error("调用短信平台商服务异常", e);
			result = "-1,调用短信平台商服务异常";
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
		String[] returnData = result.split(",");
		// 返回参数
		String respcode = returnData[0];
		String respdesc = "";
		if("0".equals(result)){
			respdesc = returnData[5];
			log.info("发送成功:respcode:{},respdesc:{}", respcode, respdesc);
			record.setSendStatus(1);
		}else{
			respdesc = returnData[1];
			log.info("发送失败:respcode:{},respdesc:{}", respcode, respdesc);
			record.setSendStatus(0);
			record.setFailReason(respdesc);
		}
		SetDetailParamsUtil.setParams(record,params); // 设置结果（发送详情）参数
		return record;
	}
}
