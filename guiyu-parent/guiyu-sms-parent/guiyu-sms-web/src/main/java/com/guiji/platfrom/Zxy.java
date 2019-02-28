package com.guiji.platfrom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.platfrom.send.ISendMsgByContent;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 专信云
 */
public class Zxy implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(Zxy.class);
	
	private String url = "https://api.zhuanxinyun.com/api/v2/sendSms.json";

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		
		String appKey = MapUtil.getString(params, "app_key", 0);
		String appSecret = MapUtil.getString(params, "app_secret", 0);
		
		for(String phone : phoneList)
		{
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("appKey", appKey));
			paramsList.add(new BasicNameValuePair("appSecret", appSecret));
			paramsList.add(new BasicNameValuePair("phones", phone));
			paramsList.add(new BasicNameValuePair("content", msgContent));
			record = send(paramsList);
			record.setPhone(phone);
			records.add(record);
		}
		return records;
	}

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, String msgContent) throws Exception
	{
		SmsRecord record = null;
		String appKey = MapUtil.getString(params, "appKey", 0);
		String appSecret = MapUtil.getString(params, "appSecret", 0);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("appKey", appKey));
		paramsList.add(new BasicNameValuePair("appSecret", appSecret));
		paramsList.add(new BasicNameValuePair("phones", phone));
		paramsList.add(new BasicNameValuePair("content", msgContent));
		record = send(paramsList);
		record.setPhone(phone);
		return record;
	}

	private SmsRecord send(List<NameValuePair> paramsList)
	{
		SmsRecord record = new SmsRecord();
		String result = doPost(paramsList); // 发送请求
		JSONObject returnData = JSONObject.parseObject(result);
		// 返回参数
		String errorCode = returnData.getString("errorCode");
		String errorMsg = returnData.getString("errorMsg");

		if ("000000".equals(errorCode))
		{
			logger.info("发送成功:errorCode:{},errorMsg:{}", errorCode, errorMsg);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:errorCode:{},errorMsg:{},MsgID:{}", errorCode, errorMsg);
			record.setSendStatus(0);
		}

		record.setStatusCode(errorCode);
		record.setStatusMsg(errorMsg);
		return record;
	}

	private String doPost(List<NameValuePair> paramsList)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try
		{
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
		} 
		catch (Exception e){
			logger.error("调用接口异常！", e);
			result = "{\"errorCode\":\"404\",\"errorMsg\":\"调用接口异常\"}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}

}
