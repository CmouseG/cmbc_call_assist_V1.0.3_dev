package com.guiji.sms.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.guiji.sms.dao.entity.SmsSendDetail;

/**
 * 发送详情记录队列
 * @author Sun
 *
 */
public class SendDetailQueue
{
	private static final BlockingQueue<SmsSendDetail> queue = new LinkedBlockingQueue<SmsSendDetail>();

	public static void add(SmsSendDetail record)
	{
		try {
			queue.put(record);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static SmsSendDetail get()
	{
		SmsSendDetail record = null;
		try {
			record = queue.take();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		return record;
	}
	
}
