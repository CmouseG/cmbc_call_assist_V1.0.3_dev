package com.guiji.platfrom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.platfrom.send.ISendMsgByContent;
import com.guiji.sms.dao.entity.SmsRecord;
import com.guiji.utils.MapUtil;

/**
 * 玄武科技
 */
public class XuanWu implements ISendMsgByContent
{
	private static final Logger logger = LoggerFactory.getLogger(XuanWu.class);
	private static String URL = "http://211.147.239.62:9051/api/v1.0.0/message/mass/send";
	
	@Override
	public List<SmsRecord> sendMessage(Map<String, Object> params, List<String> phoneList, String msgContent) throws Exception
	{
		List<SmsRecord> records = new ArrayList<>();
		String username = MapUtil.getString(params, "username", 0);
		String password = MapUtil.getString(params, "password", 0);
		HttpURLConnection conn = getConnection(URL, username, password);
		for(String phone : phoneList)
		{
			SmsRecord record = writeResponse(conn, getJsonContent(phone, msgContent));
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
		HttpURLConnection conn = getConnection(URL, username, password);
		record = writeResponse(conn, getJsonContent(phone, msgContent));
		record.setPhone(phone);
		return record;
	}
	
    private SmsRecord writeResponse(HttpURLConnection conn, String requestContent) throws IOException {
    	SmsRecord record = new SmsRecord();
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(requestContent);
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String tmp;
        while ((tmp = reader.readLine()) != null) 
        {
        	JSONObject returnData = JSONObject.parseObject(tmp);
        	// 返回参数
     		String code = returnData.getString("code");
     		String msg = returnData.getString("msg");
     		String uuid = returnData.getString("uuid");

     		if ("0".equals(code))
     		{
     			logger.info("发送成功:code:{},msg:{},uuid:{}", code, msg, uuid);
     			record.setSendStatus(1);
     		} else
     		{
     			logger.info("发送失败:code:{},msg:{},uuid:{}", code, msg, uuid);
     			record.setSendStatus(0);
     		}
     		record.setStatusCode(code);
     		record.setStatusMsg(msg);	
        }
 		return record;
    }

	private String getJsonContent(String phone, String msgContent)
	{
		JSONObject json = new JSONObject();
    	json.put("batchName", "硅基智能短信发送");
    	json.put("content", msgContent);
    	json.put("msgType", "sms");
    	json.put("bizType", 100);
    	List<Map<String,String>> items = new ArrayList<>();
    	Map<String,String> item = new HashMap<>();
    	item.put("to", phone);
    	items.add(item);
    	json.put("items", items);
    	return json.toJSONString();
	}

	private HttpURLConnection getConnection(String serverURL, String username, String password) throws Exception
	{
		HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setRequestProperty("Accept", "application/json");
        String authorization = generateAuthorization(username, password);
        conn.setRequestProperty("Authorization", authorization);
        conn.connect();
        return conn;
	}
	
    /**
     * 生成http请求头Authorization串，用于鉴权
     */
    private String generateAuthorization(String username, String password) {
        String md5Pwd = DigestUtils.md5Hex(password);
        String pair = username + ":" + md5Pwd;
        return Base64.encodeBase64String(pair.getBytes());
    }

}
