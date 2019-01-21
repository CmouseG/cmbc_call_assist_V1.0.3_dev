package com.guiji.platfrom.yunxun;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

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
import com.guiji.utils.Base64MD5Util;

/**
 * 云讯
 * @author Sun
 *
 */
public class Ytx
{
	private static final Logger logger = LoggerFactory.getLogger(Ytx.class);
	
	/**
	 * 
	 * 云讯-发送短信
	 */
	public JSONObject sendMessageByYunXun(YtxParams ytxParams)
	{
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 时间戳：有效时间为24小时，格式"yyyyMMddHHmmss"
		String authorization = Base64.getEncoder().encodeToString((ytxParams.getAccountSID() + "|" + date).getBytes()); // base64加密(账户Id + "|" + 时间戳)
		
		String sign = Base64MD5Util.encryption(ytxParams.getAccountSID() + ytxParams.getAuthToken() + date); // MD5加密（账户Id + 账户授权令牌  + 时间戳)
		
		String url = "http://api.ytx.net/201512/sid/" 
					+ ytxParams.getAccountSID() 
					+ "/sms/TemplateSMS.wx?Sign=" 
					+ sign;
		JSONObject json = new JSONObject();
		json.put("action", ytxParams.getAction());
		json.put("appid", ytxParams.getAppid());
		json.put("mobile", ytxParams.getMobile());
		json.put("templateId", ytxParams.getTemplateId());

		String result = doPost(url, json.toJSONString(), authorization); // 发送请求

		return JSONObject.parseObject(result);

	}

	/**
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
			result = "{\"statusCode\":\"404\",\"statusMsg\":\"调用接口异常\",\"requestId\":\"\"}";
		}
		finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		
		return result;
	}
}
