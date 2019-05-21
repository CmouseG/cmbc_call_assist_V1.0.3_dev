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
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.platform.ISendMessage;
import com.guiji.sms.queue.SendDetailQueue;
import com.guiji.sms.utils.SetDetailParamsUtil;
import com.guiji.sms.utils.XmlUtil;

/**
 * 短信平台-玄都科技
 */
@Component
public class XuanDuKeji implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(XuanDuKeji.class);
	private String url = "http://api.xuanall.com:6666/sms.aspx";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void sendMessage(JSONObject params, List<String> phoneList)
	{
		try{
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			CompletableFuture[] cfs = phoneList.parallelStream().map( phone -> 
					CompletableFuture.supplyAsync( () -> 
							send(params, phone), executorService) // 多线程执行
					.whenComplete((record,exception) -> {
						SendDetailQueue.add(record);  // 结果回调
					})).toArray(CompletableFuture[]::new);
			CompletableFuture.allOf(cfs).join(); // 等待所有子线程执行完毕
		} catch (Exception e){
			log.error(e.getMessage(),e);
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_SMS);
		}
	}
	
	private SmsSendDetail send(JSONObject params, String phone)
	{
		SmsSendDetail record = null;
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("userid", params.getString("userid")));
		paramsList.add(new BasicNameValuePair("account", params.getString("account")));
		paramsList.add(new BasicNameValuePair("password", params.getString("password")));
		paramsList.add(new BasicNameValuePair("mobile", phone));
		paramsList.add(new BasicNameValuePair("content", params.getString("smsContent")));
		paramsList.add(new BasicNameValuePair("sendTime", ""));
		paramsList.add(new BasicNameValuePair("action", "send"));
		paramsList.add(new BasicNameValuePair("extno", ""));
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
		try{
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			EntityUtils.consume(responseEntity);
		}catch (Exception e){
			log.error("调用短信平台商服务异常", e);
			result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
				+ "<returnsms>"
					+ "<returnstatus>404</returnstatus>"
					+ "<message>调用短信平台商服务异常</message>"
				+ "</returnsms>";
		}finally{
			IOUtils.close(response);
			IOUtils.close(httpClient);
		}
		return result;
	}

	// 处理结果
	private SmsSendDetail handleResult(String result, JSONObject params)
	{
		SmsSendDetail record = new SmsSendDetail();
		org.json.JSONObject jsonResult = XmlUtil.xmlStr2Json(result);
		// 返回参数
		org.json.JSONObject returnsms = jsonResult.getJSONObject("returnsms");
		String returnstatus = returnsms.getString("returnstatus");
		String message = returnsms.getString("message");
		if("Success".equals(returnstatus) && "ok".equals(message)){
			log.info("发送成功:returnstatus:{},message:{}", returnstatus, message);
			record.setSendStatus(1);
		}else{
			log.info("发送失败:returnstatus:{},message:{}", returnstatus, message);
			record.setSendStatus(0);
			record.setFailReason(message);
		}
		SetDetailParamsUtil.setParams(record, params); // 设置结果（发送详情）参数
		return record;
	}

}
