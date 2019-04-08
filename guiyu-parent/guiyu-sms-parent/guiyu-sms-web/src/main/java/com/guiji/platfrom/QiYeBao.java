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
 * 企业宝
 */
public class QiYeBao implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(QiYeBao.class);
	private String url = "http://www.qybor.com:8500/shortMessage";

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent)
			throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		String username = MapUtil.getString(params, "username", 0);
		String passwd = MapUtil.getString(params, "passwd", 0);
		for(String phone : phoneList)
		{
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("username", username));
			paramsList.add(new BasicNameValuePair("passwd", passwd));
			paramsList.add(new BasicNameValuePair("phone", phone));
			paramsList.add(new BasicNameValuePair("msg", msgContent));
			paramsList.add(new BasicNameValuePair("needstatus", "false"));
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
		String passwd = MapUtil.getString(params, "passwd", 0);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("username", username));
		paramsList.add(new BasicNameValuePair("passwd", passwd));
		paramsList.add(new BasicNameValuePair("phone", phone));
		paramsList.add(new BasicNameValuePair("msg", msgContent));
		paramsList.add(new BasicNameValuePair("needstatus", "false"));
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
		String respcode = returnData.getString("respcode");
		String respdesc = returnData.getString("respdesc");

		if ("0".equals(respcode))
		{
			logger.info("发送成功:respcode:{},respdesc:{}", respcode, respdesc);
			record.setSendStatus(1);
		} else
		{
			logger.info("发送失败:respcode:{},respdesc:{}", respcode, respdesc);
			record.setSendStatus(0);
		}

		record.setStatusCode(respcode);
		record.setStatusMsg(respdesc);
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
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
			response = httpClient.execute(httpPost); // 执行请求
			HttpEntity responseEntity = response.getEntity();
			result = EntityUtils.toString(responseEntity, "utf-8");
			EntityUtils.consume(responseEntity);
		} 
		catch (Exception e){
			logger.error("调用接口异常！", e);
			result = "{\"respcode\":\"404\",\"respdesc\":\"调用接口异常\"}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return result;
	}

}
