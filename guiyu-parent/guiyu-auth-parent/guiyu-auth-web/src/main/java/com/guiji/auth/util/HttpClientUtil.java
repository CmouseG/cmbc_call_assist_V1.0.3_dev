package com.guiji.auth.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.common.exception.GuiyuException;

/**
 * HttpClient工具类
 */
public class HttpClientUtil 
{

	/**
	 * post请求
	 */
	public static String post(String url, Object objParams) 
	{
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try
		{
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			setPostParams(httpPost, objParams);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity, "utf-8");
				EntityUtils.consume(entity);
			}
		} 
		catch (Exception e) {
			throw new GuiyuException("请求三方接口失败");
		} finally {
			IOUtils.close(response);
			IOUtils.close(httpClient);
		}
		return result;
	}
	
	/**
	 * 设置post参数
	 * json
	 */
	private static void setPostParams(HttpPost httpPost, Object obj) {
		String params = JSON.toJSONString(obj);
		StringEntity entity = new StringEntity(params, "UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
	}

}
