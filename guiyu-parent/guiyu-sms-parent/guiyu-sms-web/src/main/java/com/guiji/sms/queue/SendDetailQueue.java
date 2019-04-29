package com.guiji.sms.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.sms.common.ExceptionEnum;
import com.guiji.sms.common.SmsException;
import com.guiji.sms.dao.entity.SmsSendDetail;

/**
 * 发送详情记录队列
 * @author Sun
 *
 */
public class SendDetailQueue
{
	private static final Logger log = LoggerFactory.getLogger(SendDetailQueue.class);
	private static final BlockingQueue<SmsSendDetail> queue = new LinkedBlockingQueue<SmsSendDetail>();

	public static void add(SmsSendDetail record)
	{
		try {
			queue.put(record);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_INTERRUPTED);
		}
	}
	
	public static SmsSendDetail get()
	{
		SmsSendDetail record = null;
		try {
			record = queue.take();
		} catch (Exception e){
			log.error(e.getMessage());
			throw new SmsException(ExceptionEnum.ERROR_INTERRUPTED);
		}
		return record;
	}
	
}
