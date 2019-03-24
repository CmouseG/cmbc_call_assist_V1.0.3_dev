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

import com.guiji.platfrom.send.ISendMsgByContent;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 企业信使
 */
public class Qyxs implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(Qyxs.class);

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		
		String sms_ip = MapUtil.getString(params, "sms_ip", 0);
		String sms_port = MapUtil.getString(params, "sms_port", 0);
		String url = "http://"+sms_ip+":"+sms_port+"/sms.aspx";
		
		String userid = MapUtil.getString(params, "userId", 0);
		String account = MapUtil.getString(params, "account", 0);
		String password = MapUtil.getString(params, "password", 0);
		
		for(String phone : phoneList)
		{
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("userid", userid));
			paramsList.add(new BasicNameValuePair("account", account));
			paramsList.add(new BasicNameValuePair("password", password));
			paramsList.add(new BasicNameValuePair("mobile", phone));
			paramsList.add(new BasicNameValuePair("content", msgContent));
			paramsList.add(new BasicNameValuePair("action", "send"));
			record = send(paramsList, url);
			record.setPhone(phone);
			records.add(record);
		}
		return records;
	}

	@Override
	public SmsRecord sendMessage(Map<String, Object> params, String phone, String msgContent) throws Exception
	{
		SmsRecord record = null;
		
		String sms_ip = MapUtil.getString(params, "sms_ip", 0);
		String sms_port = MapUtil.getString(params, "sms_port", 0);
		String url = "http://"+sms_ip+":"+sms_port+"/sms.aspx";
		
		String userid = MapUtil.getString(params, "userId", 0);
		String account = MapUtil.getString(params, "account", 0);
		String password = MapUtil.getString(params, "password", 0);
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("userid", userid));
		paramsList.add(new BasicNameValuePair("account", account));
		paramsList.add(new BasicNameValuePair("password", password));
		paramsList.add(new BasicNameValuePair("mobile", phone));
		paramsList.add(new BasicNameValuePair("content", msgContent));
		paramsList.add(new BasicNameValuePair("action", "send"));
		record = send(paramsList, url);
		record.setPhone(phone);
		return record;
	}
	
	private SmsRecord send(List<NameValuePair> paramsList, String url) throws JSONException
	{
		SmsRecord record = new SmsRecord();
		String result = doPost(paramsList, url); // 发送请求
		JSONObject jsonResult = XML.toJSONObject(result);
		// 返回参数
		JSONObject returnsms = jsonResult.getJSONObject("returnsms");
		String returnstatus = returnsms.getString("returnstatus");
		String message = returnsms.getString("message");
		String taskID = returnsms.getString("taskID");

		if ("Success".equals(returnstatus) && "ok".equals(message))
		{
			logger.info("发送成功:returnstatus:{},message:{},taskID:{}", returnstatus,message,taskID);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:returnstatus:{},message:{},taskID:{}", returnstatus,message,taskID);
			record.setSendStatus(0);
		}

		record.setStatusCode(returnstatus);
		record.setStatusMsg(message);
		return record;
	}
	
	private String doPost(List<NameValuePair> paramsList, String url)
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
			result = "{\"returnsms\":{\"returnstatus\":\"404\",\"message\":\"调用接口异常\"}}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}
	
}
