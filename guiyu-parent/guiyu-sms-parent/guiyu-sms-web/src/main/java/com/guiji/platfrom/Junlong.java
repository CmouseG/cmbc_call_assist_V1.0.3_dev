package com.guiji.platfrom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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

import com.guiji.platfrom.send.ISendMsgByContent;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 君隆科技
 */
public class Junlong implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(Junlong.class);
	
	private String url = "http://hy.junlongtech.com:8086/getsms";

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		
		String username = MapUtil.getString(params, "username", 0);
		String password = MapUtil.getString(params, "password", 0);
		String extend = MapUtil.getString(params, "extend", 0);
		
		for(String phone : phoneList)
		{
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("username", username));
			paramsList.add(new BasicNameValuePair("password", DigestUtils.md5Hex(password).toUpperCase())); //32位大写MD5加密
			paramsList.add(new BasicNameValuePair("content", msgContent));
			paramsList.add(new BasicNameValuePair("extend", extend));
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
		String username = MapUtil.getString(params, "username", 0);
		String password = MapUtil.getString(params, "password", 0);
		String extend = MapUtil.getString(params, "extend", 0);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("username", username));
		paramsList.add(new BasicNameValuePair("password", DigestUtils.md5Hex(password).toUpperCase())); //32位大写MD5加密
		paramsList.add(new BasicNameValuePair("content", msgContent));
		paramsList.add(new BasicNameValuePair("extend", extend));
		paramsList.add(new BasicNameValuePair("mobile", phone));
		record = send(paramsList);
		record.setPhone(phone);
		return record;
	}
	
	private SmsRecord send(List<NameValuePair> paramsList) throws Exception
	{
		SmsRecord record = new SmsRecord();
		String resultStr = doPost(paramsList); // 发送请求
		Map<String, String> resultMap = MapUtil.handleStringToMap(resultStr);
		String result = MapUtil.getString(resultMap, "result", 0);
		String msgid = MapUtil.getString(resultMap, "msgid", 1);

		if ("0".equals(result))
		{
			logger.info("发送成功:result:{},msgid:{}", result, msgid);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:result:{},msgid:{}", result, msgid);
			record.setSendStatus(0);
		}

		record.setStatusCode(result);
		record.setStatusMsg(msgid);
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
			result = "result=404&msgid=调用接口异常";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}

}
