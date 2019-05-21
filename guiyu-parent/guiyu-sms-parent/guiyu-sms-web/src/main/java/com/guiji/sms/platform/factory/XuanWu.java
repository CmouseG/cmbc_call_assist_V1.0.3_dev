package com.guiji.sms.platform.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.platform.ISendMessage;
import com.guiji.sms.queue.SendDetailQueue;
import com.guiji.sms.utils.SetDetailParamsUtil;

/**
 * 短信平台-玄武科技
 */
@Component
public class XuanWu implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(XuanWu.class);
	private String url = "http://211.147.239.62:9051/api/v1.0.0/message/mass/send";
	
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
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_SMS);
		}
	}

	private SmsSendDetail send(JSONObject params, String phone)
	{
		SmsSendDetail record = null;
		String username = params.getString("username");
		String password = params.getString("password");
		HttpURLConnection conn = getConnection(url, username, password);
		record = writeResponse(conn, getJsonContent(phone, params.getString("smsContent")));
		SetDetailParamsUtil.setParams(record,params); // 设置结果（发送详情）参数
		record.setPhone(phone);
		return record;
	}
	
	// 获取连接
	private HttpURLConnection getConnection(String serverURL, String username, String password)
	{
		HttpURLConnection conn = null;
		try{
			conn = (HttpURLConnection) new URL(serverURL).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setRequestProperty("Accept", "application/json");
			String authorization = generateAuthorization(username, password);
			conn.setRequestProperty("Authorization", authorization);
			conn.connect();
		}catch (Exception e){
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_XUANWU_GET_CONNECTION);
		}
		return conn;
	}
	
    // 生成http请求头Authorization串，用于鉴权
    private String generateAuthorization(String username, String password) {
        String md5Pwd = DigestUtils.md5Hex(password);
        String pair = username + ":" + md5Pwd;
        return Base64.encodeBase64String(pair.getBytes());
    }
    
	private String getJsonContent(String phone, String smsContent)
	{
		JSONObject json = new JSONObject();
		json.put("batchName", "硅基智能短信发送");
		json.put("content", smsContent);
		json.put("msgType", "sms");
		json.put("bizType", 100);
		List<Map<String, String>> items = new ArrayList<>();
		Map<String, String> item = new HashMap<>();
		item.put("to", phone);
		items.add(item);
		json.put("items", items);
		return json.toJSONString();
	}
    
	private SmsSendDetail writeResponse(HttpURLConnection conn, String requestContent)
	{
		SmsSendDetail record = new SmsSendDetail();
		try{
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(requestContent);
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String tmp;
			while((tmp = reader.readLine()) != null)
			{
				JSONObject returnData = JSONObject.parseObject(tmp);
				// 返回参数
				String code = returnData.getString("code");
				String msg = returnData.getString("msg");
				String uuid = returnData.getString("uuid");
				if("0".equals(code)){
					log.info("发送成功:code:{},msg:{},uuid:{}", code, msg, uuid);
					record.setSendStatus(1);
				}else{
					log.info("发送失败:code:{},msg:{},uuid:{}", code, msg, uuid);
					record.setSendStatus(0);
					record.setFailReason(msg);
				}
			}
		} catch (Exception e){
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_XUANWU_WRITE_RESPONSE);
		}
		return record;
	}

}
