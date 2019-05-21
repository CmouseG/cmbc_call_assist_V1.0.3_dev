package com.guiji.sms.platform.factory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cmb.Notify.NoticeSender;
import com.cmb.Notify.enums.Priority;
import com.cmb.Notify.exception.AddrException;
import com.cmb.Notify.exception.LoginException;
import com.cmb.Notify.exception.MyException;
import com.cmb.Notify.msgob.Address;
import com.cmb.Notify.msgob.MsgID;
import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.platform.ISendMessage;
import com.guiji.sms.utils.SetDetailParamsUtil;
import com.guiji.utils.RedisUtil;

/**
 * 短信平台-网经服
 */
@Component
public class WangJingFu implements ISendMessage
{
	private static final Logger log = LoggerFactory.getLogger(WangJingFu.class);
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public void sendMessage(JSONObject params, List<String> phoneList)
	{
		String hostAddress = params.getString("hostAddress"); // 通知系统服务器地址
		int port = params.getInteger("port"); // 端口
		String user = params.getString("user"); // 消息用户 
		String password = params.getString("password"); // 密码
		String emailSender = params.getString("emailSender"); // email地址，可自定义，但是域名必须为@message.cmbchina.com
		NoticeSender ns = new NoticeSender(hostAddress, port, user, password, emailSender); // 一个NoticeSender表示一个到通知平台的连接（长连接，可复用）
		try{
			phoneList.parallelStream().forEach(phone -> send(params,phone,ns));
		} catch (Exception e){
			log.error(e.getMessage(),e);
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_SMS);
		}
	}

	private void send(JSONObject params, String phone, NoticeSender ns)
	{
		Address address = new Address(phone,params.getString("clientID"),"");
		String message = params.getString("smsContent");
		String busiType = params.getString("busiType");
		String feeDept1 = params.getString("feeDept1"); // 成本核算使用：一级费用部门，一般为分行号 ,必须为非空值 
		String feeDept2 = params.getString("feeDept2"); // 成本核算使用：二级费用部门，一般为网点号,可以为空 
		MsgID msgID = null;
		try{
			msgID = ns.SendSMS(address, message, busiType, Priority.PRIORITY_NORMAL, 0, 0, feeDept1, feeDept2);
			log.info("sessionID: {}, msgNo: {}",msgID.sessionID, msgID.msgNo);
		} catch (AddrException | LoginException | MyException e){
			log.error(e.getMessage(),e);
			throw new SmsException(ExceptionEnum.ERROR_REQUEST_WJF); 
		}
		if(msgID!=null)
		{
			SmsSendDetail record = new SmsSendDetail();
			SetDetailParamsUtil.setParams(record,params); // 设置结果（发送详情）参数
			record.setPhone(phone);
			redisUtil.set(msgID.toString(), record);
		}
	}

}
