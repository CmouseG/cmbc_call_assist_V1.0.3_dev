package com.guiji.sms.listener;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmb.Notify.msgob.AtcAck;
import com.cmb.Notify.msgob.MessageAck;
import com.cmb.Notify.msgob.MsgFailInfo;
import com.cmb.Notify.msgob.RcvReport;
import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.queue.SendDetailQueue;
import com.guiji.utils.RedisUtil;

/**
 * 网经服接收通知平台回应监听器
 */
@Component
public class WJFSenderObserverListener implements Observer
{
	private static final Logger log = LoggerFactory.getLogger(WJFSenderObserverListener.class);
	@Autowired
	RedisUtil redisUtil;

	@Override
	public void update(Observable from, Object msgInfo)
	{
		if(msgInfo instanceof MsgFailInfo) // 由于异常导致消息未发送到通知平台，建议打印日志
		{
			MsgFailInfo fif = (MsgFailInfo) msgInfo;
			log.info("<---Get FailInfo. MsgID: " + fif.msgid.toString() + ", From: " + from.toString() + ", Err: ");
			fif.ex.printStackTrace();
			redisUtil.del(fif.msgid.toString());
		}
		/*
		 * MessageAck：重要，必须处理
			1）表示消息已发送到通知平台
			2）但还需判断MessageAck的isSendOK()来判断消息是否在通知平台校验失败而导致发送不出去：
				如果isSendOK()为true，则表示消息无误，可发送到客户；
				如果isSendOK()为false，则表示消息校验失败，业务系统可调用getErrcode()和getErrMsg()方法，获取校验失败原因；
			3）MessageAck的getMsgid()方法可获取消息ID，通过比较SendSMS或SendEmail返回的消息ID，可明确各条消息的发送状态；
		 */
		else if(msgInfo instanceof MessageAck)
		{
			MessageAck msgack = (MessageAck) msgInfo;
			SmsSendDetail record = redisUtil.getT(msgack.getMsgid().toString());
			if(msgack.isSendOk()){
				log.info("<---Msg SendOK. MsgID: " + msgack.getMsgid().toString() + ", From: " + from.toString());
				record.setSendStatus(1);
			}else{
				log.error("<---Msg SendFail. MsgID: " + msgack.getMsgid().toString() 
						+ ", Errcode: " + msgack.getErrcode() 
						+ ", Errmsg: " + msgack.getErrmsg() 
						+ ", From: " + from.toString());
				record.setSendStatus(0);
				record.setFailReason(msgack.getErrmsg());
			}
			SendDetailQueue.add(record);
			redisUtil.del(msgack.getMsgid().toString());
		}
		else if(msgInfo instanceof RcvReport){
			RcvReport rcvrpt = (RcvReport) msgInfo;
			log.info("<---Get RcvReport: " + rcvrpt.toString() + ", From: " + from.toString());
		} 
		else if(msgInfo instanceof AtcAck)
		{
			AtcAck actack = (AtcAck) msgInfo;
			if(actack.isSendOk()){
				log.info("<---Atc SendOK. tranID: " + actack.tranid + ", From: " + from.toString());
			}else{
				log.error("<---Atc SendFail. tranID: " + actack.tranid 
						+ ", Errcode: " + actack.errcode 
						+ ", Errmsg: " + actack.errmsg 
						+ ", From: " + from.toString());
			}
		}
		else if(msgInfo instanceof Exception){
			Exception e = (Exception) msgInfo;
			log.error("<---From: " + from.toString() + " Get exception:");
			e.printStackTrace();
		} 
		else if(msgInfo instanceof String){
			log.error((String) msgInfo);
		}
	}

}
