package com.guiji.dispatch.sms;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.dispatch.bean.sendMsgDto;
import com.guiji.dispatch.dao.SendMsgRecordsMapper;
import com.guiji.dispatch.dao.entity.SendMsgRecords;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "dispatch.MessageMQ")
public class MessageMQListtener {
	private static Logger logger = LoggerFactory.getLogger(MessageMQListtener.class);

	@Autowired
	private SendMsgRecordsMapper sendMsgMapper;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		try {
			sendMsgDto msgDto = JsonUtils.json2Bean(message, sendMsgDto.class);
			String url = "http://api.ytx.net/" + "201512/sid/" + msgDto.getAccountSID() + "/"
					+ "sms/TemplateSMS.wx?Sign=" + msgDto.getSign();
			// + "sms/TemplateSMS.wx";
			JSONObject json = new JSONObject();
			json.put("action", "templateSms");
			json.put("mobile", msgDto.getPhone());
			json.put("appid", msgDto.getAppid());
			json.put("templateId", msgDto.getSmsTemplateId());
			String doPostJson = doPostJson(url, json.toJSONString(), msgDto.getAuthorization());
			JSONObject msgRes = JSONObject.parseObject(doPostJson);
			// String test = "{\"statusCode\": \"0\",\"statusMsg\":
			// \"提交成功\",\"requestId\": \"20181235962383254861905920103\"}";
			String statusCode = (String) msgRes.get("statusCode");
			String statusMsg = (String) msgRes.get("statusMsg");
			String requestId = (String) msgRes.get("requestId");
			if (statusCode.equals("0")) {
				// 发送状态
				logger.info("发送成功:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
				// 数据库记录数据
			} else {
				logger.info("发送失败:statusCode:{},statusMsg:{},requestId:{}", statusCode, statusMsg, requestId);
			}
			SendMsgRecords msgRe = new SendMsgRecords();
			try {
				msgRe.setCreateTime(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.info("error", e);
			}
			msgRe.setPhone(msgDto.getPhone());
			msgRe.setRequestid(requestId);
			msgRe.setStatuscode(Integer.valueOf(statusCode));
			msgRe.setStatusmsg(statusMsg);
			int insert = sendMsgMapper.insert(msgRe);
		} catch (Exception e) {
			logger.info("短信消费的数据有问题---------------------------------------" + message);
			try {
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("短信消费的数据有问题，不会再重复发送消费有问题了");
			}
		}
	}

	public static String doPostJson(String url, String json, String Authorization) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
			httpPost.setHeader("Authorization", Authorization);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口异常！", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("关闭连接异常！", e);
			}
		}
		return resultString;
	}
}
