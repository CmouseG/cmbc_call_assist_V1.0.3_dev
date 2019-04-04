package com.guiji.platfrom;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.platfrom.send.ISendMsgByContent;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 鸿联九五
 */
public class HongLian95 implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(HongLian95.class);
	private String url = "http://q.hl95.com:8061";

	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent)throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		SmsRecord record = null;
		String username = MapUtil.getString(params, "username", 0);
		String password = MapUtil.getString(params, "password", 0);
		String epid = MapUtil.getString(params, "epid", 0);
		for(String phone : phoneList)
		{
			Map<String, String> pmap = new HashMap<String, String>();
			pmap.put("username", username);
			pmap.put("password", password);
			pmap.put("epid", epid);
			pmap.put("phone", phone);
			pmap.put("message",URLEncoder.encode(msgContent, "gb2312"));
			String pstr = "";
			if (pmap != null && pmap.size() > 0){
				for (Map.Entry<String, String> entry : pmap.entrySet()){
					pstr += "&" + entry.getKey() + "=" + entry.getValue();
				}
				pstr = pstr.substring(1);
			}
			record = send(pstr);
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
		String epid = MapUtil.getString(params, "epid", 0);
		
		Map<String, String> pmap = new HashMap<String, String>();
		pmap.put("username", username);
		pmap.put("password", password);
		pmap.put("epid", epid);
		pmap.put("phone", phone);
		pmap.put("message",URLEncoder.encode(msgContent, "gb2312"));
		String pstr = "";
		if (pmap != null && pmap.size() > 0){
			for (Map.Entry<String, String> entry : pmap.entrySet()){
				pstr += "&" + entry.getKey() + "=" + entry.getValue();
			}
			pstr = pstr.substring(1);
		}
		record = send(pstr);
		record.setPhone(phone);
		return record;
	}

	private SmsRecord send(String pstr)
	{
		SmsRecord record = new SmsRecord();
		String result = doGet(pstr); // 发送请求
		JSONObject returnData = JSONObject.parseObject(result);
		// 返回参数
		String respcode = returnData.getString("respcode");
		String respdesc = returnData.getString("respdesc");

		if ("00".equals(respcode))
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

	private String doGet(String pstr)
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try{
			HttpGet httpGet = new HttpGet(url+"?"+pstr);
			response = httpClient.execute(httpGet); // 执行请求
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
