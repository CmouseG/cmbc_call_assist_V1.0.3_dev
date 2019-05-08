package com.guiji.sms.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.platform.factory.Cmpp;
import com.guiji.sms.platform.factory.DaiYi;
import com.guiji.sms.platform.factory.HongLian95;
import com.guiji.sms.platform.factory.JunLong;
import com.guiji.sms.platform.factory.QiYeBao;
import com.guiji.sms.platform.factory.QiYeXinShi;
import com.guiji.sms.platform.factory.Welink;
import com.guiji.sms.platform.factory.XiaoYa;
import com.guiji.sms.platform.factory.XuanWu;
import com.guiji.sms.platform.factory.YunTongXun;
import com.guiji.sms.platform.factory.YunXun;
import com.guiji.sms.platform.factory.ZhuanXinYun;

/**
 * 发送短信处理类
 * @author Sun
 */
public class SendMsgHandler
{
	private static final Logger log = LoggerFactory.getLogger(SendMsgHandler.class);

	public static void choosePlatformToSend(String identification, JSONObject params, List<String> phoneList)
	{
		if("ytx".equals(identification))
		{
			log.info("通过<云讯科技>发送短信...");
			new YunXun().sendMessage(params, phoneList);
		}
		else if("wl".equals(identification))
		{
			log.info("通过<微网通联>发送短信...");
			new Welink().sendMessage(params, phoneList);
		}
		else if("cmpp".equals(identification))
		{
			log.info("通过<CMPP>发送短信...");
			new Cmpp().sendMessage(params, phoneList);
		}
		else if("zxy".equals(identification))
		{
			log.info("通过<专信云>发送短信...");
			new ZhuanXinYun().sendMessage(params, phoneList);
		}
		else if("qyxs".equals(identification))
		{
			log.info("通过<企业信使>发送短信...");
			new QiYeXinShi().sendMessage(params, phoneList);
		}
		else if("jl".equals(identification))
		{
			log.info("通过<君隆科技>发送短信...");
			new JunLong().sendMessage(params, phoneList);
		}
		else if("xw".equals(identification))
		{
			log.info("通过<玄武科技>发送短信...");
			new XuanWu().sendMessage(params, phoneList);
		}
		else if("qyb".equals(identification))
		{
			log.info("通过<企业宝>发送短信...");
			new QiYeBao().sendMessage(params, phoneList);
		}
		else if("hl95".equals(identification))
		{
			log.info("通过<鸿联九五>发送短信...");
			new HongLian95().sendMessage(params, phoneList);
		}
		else if("dydx".equals(identification))
		{
			log.info("通过<岱亿短信>发送短信...");
			new DaiYi().sendMessage(params, phoneList);
		}
		else if("xy".equals(identification))
		{
			log.info("通过<小丫短信平台>发送短信...");
			new XiaoYa().sendMessage(params, phoneList);
		}
		else if("yuntongxun".equals(identification))
		{
			log.info("通过<云通讯>发送短信...");
			new YunTongXun().sendMessage(params, phoneList);
		}
	}
}
