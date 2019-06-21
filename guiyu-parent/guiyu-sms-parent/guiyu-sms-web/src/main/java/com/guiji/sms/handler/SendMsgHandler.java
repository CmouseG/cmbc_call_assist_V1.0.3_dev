package com.guiji.sms.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.platform.factory.Cmpp;
import com.guiji.sms.platform.factory.DaiYi;
import com.guiji.sms.platform.factory.HongLian95;
import com.guiji.sms.platform.factory.JunLong;
import com.guiji.sms.platform.factory.QiYeBao;
import com.guiji.sms.platform.factory.QiYeXinShi;
import com.guiji.sms.platform.factory.WangJingFu;
import com.guiji.sms.platform.factory.Welink;
import com.guiji.sms.platform.factory.XiangYunZhiTong;
import com.guiji.sms.platform.factory.XiaoYa;
import com.guiji.sms.platform.factory.XuanDuKeji;
import com.guiji.sms.platform.factory.XuanWu;
import com.guiji.sms.platform.factory.YunPian;
import com.guiji.sms.platform.factory.YunTongXun;
import com.guiji.sms.platform.factory.YunXun;
import com.guiji.sms.platform.factory.ZhuanXinYun;

/**
 * 发送短信处理类
 * @author Sun
 */
@Component
public class SendMsgHandler
{
	private static final Logger log = LoggerFactory.getLogger(SendMsgHandler.class);
	@Autowired
	YunXun yunXun;
	@Autowired
	Welink welink;
	@Autowired
	Cmpp cmpp;
	@Autowired
	ZhuanXinYun zhuanXinYun;
	@Autowired
	QiYeXinShi qiYeXinShi;
	@Autowired
	JunLong junLong;
	@Autowired
	XuanWu xuanWu;
	@Autowired
	QiYeBao qiYeBao;
	@Autowired
	HongLian95 hongLian95;
	@Autowired
	DaiYi daiYi;
	@Autowired
	XiaoYa xiaoYa;
	@Autowired
	YunTongXun yunTongXun;
	@Autowired
	XiangYunZhiTong xiangYunZhiTong;
	@Autowired
	XuanDuKeji xuanDuKeji;
	@Autowired
	WangJingFu wangJingFu;
	@Autowired
	YunPian yunPian;
	
	public void choosePlatformToSend(String identification, JSONObject params, List<String> phoneList)
	{
		if("ytx".equals(identification))
		{
			log.info("通过<云讯科技>发送短信...");
			yunXun.sendMessage(params, phoneList);
		}
		else if("wl".equals(identification))
		{
			log.info("通过<微网通联>发送短信...");
			welink.sendMessage(params, phoneList);
		}
		else if("cmpp".equals(identification))
		{
			log.info("通过<CMPP>发送短信...");
			cmpp.sendMessage(params, phoneList);
		}
		else if("zxy".equals(identification))
		{
			log.info("通过<专信云>发送短信...");
			zhuanXinYun.sendMessage(params, phoneList);
		}
		else if("qyxs".equals(identification))
		{
			log.info("通过<企业信使>发送短信...");
			qiYeXinShi.sendMessage(params, phoneList);
		}
		else if("jl".equals(identification))
		{
			log.info("通过<君隆科技>发送短信...");
			junLong.sendMessage(params, phoneList);
		}
		else if("xw".equals(identification))
		{
			log.info("通过<玄武科技>发送短信...");
			xuanWu.sendMessage(params, phoneList);
		}
		else if("qyb".equals(identification))
		{
			log.info("通过<企业宝>发送短信...");
			qiYeBao.sendMessage(params, phoneList);
		}
		else if("hl95".equals(identification))
		{
			log.info("通过<鸿联九五>发送短信...");
			hongLian95.sendMessage(params, phoneList);
		}
		else if("dydx".equals(identification))
		{
			log.info("通过<岱亿短信>发送短信...");
			daiYi.sendMessage(params, phoneList);
		}
		else if("xy".equals(identification))
		{
			log.info("通过<小丫短信平台>发送短信...");
			xiaoYa.sendMessage(params, phoneList);
		}
		else if("yuntongxun".equals(identification))
		{
			log.info("通过<云通讯>发送短信...");
			yunTongXun.sendMessage(params, phoneList);
		}
		else if("xyzt".equals(identification))
		{
			log.info("通过<祥云智通>发送短信...");
			xiangYunZhiTong.sendMessage(params, phoneList);
		}
		else if("xdkj".equals(identification))
		{
			log.info("通过<玄都科技>发送短信...");
			xuanDuKeji.sendMessage(params, phoneList);
		}
		else if("wjf".equals(identification))
		{
			log.info("通过<网经服>发送短信...");
			wangJingFu.sendMessage(params, phoneList);
		}
		else if("yp".equals(identification))
		{
			log.info("通过<云片>发送短信...");
			yunPian.sendMessage(params, phoneList);
		}
	}
}
