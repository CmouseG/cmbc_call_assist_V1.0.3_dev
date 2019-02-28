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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.platfrom.send.ISendMsg;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 深圳信用卡2
 */
public class ShenzhenCredit2 implements ISendMsg
{
	private static final Logger logger = LoggerFactory.getLogger(ShenzhenCredit2.class);
	
	private String url = "http://120.25.248.27:8888/sms.aspx";
	
	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, Integer templateId)
			throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, Integer templateId) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		List<NameValuePair> paramsList = setParams(params, msgContent);
		for(String phone : phoneList)
		{
			paramsList.add(new BasicNameValuePair("mobile", phone));
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
		List<NameValuePair> paramsList = setParams(params, msgContent);
		paramsList.add(new BasicNameValuePair("mobile", phone));
		record = send(paramsList);
		record.setPhone(phone);
		return record;
	}

	private List<NameValuePair> setParams(Map<String, Object> params, String msgContent) throws Exception
	{
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		String userid = MapUtil.getString(params, "userId", 0);
		String account = MapUtil.getString(params, "account", 0);
		String password = MapUtil.getString(params, "password", 0);
		paramsList.add(new BasicNameValuePair("userid", userid));
		paramsList.add(new BasicNameValuePair("account", account));
		paramsList.add(new BasicNameValuePair("password", password));
		paramsList.add(new BasicNameValuePair("content", msgContent));
		paramsList.add(new BasicNameValuePair("sendTime", ""));
		paramsList.add(new BasicNameValuePair("action", "send"));
		paramsList.add(new BasicNameValuePair("extno", ""));
		return paramsList;
	}
	
	private SmsRecord send(List<NameValuePair> paramsList) throws JSONException
	{
		SmsRecord record = new SmsRecord();
		String result = doPost(paramsList); // 发送请求
		JSONObject jsonResult = XML.toJSONObject(result);
		// 返回参数
		JSONObject returnsms = jsonResult.getJSONObject("returnsms");
		String returnstatus = returnsms.getString("returnstatus");
		String message = returnsms.getString("message");
		String taskID = returnsms.getString("taskID");

		if ("Success".equals(returnstatus) && "ok".equals(message))
		{
			logger.info("发送成功:returnstatus:{},message:{},taskID:{}", returnstatus, message,taskID);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:returnstatus:{},message:{},taskID:{}", returnstatus, message,taskID);
			record.setSendStatus(0);
		}

		record.setStatusCode(returnstatus);
		record.setStatusMsg(message);
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
