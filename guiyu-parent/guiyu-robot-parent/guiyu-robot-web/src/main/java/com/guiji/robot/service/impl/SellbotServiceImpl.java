package com.guiji.robot.service.impl;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.robot.util.HttpClientUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.SystemUtil;

/** 
* @ClassName: SellbotServiceImpl 
* @Description: Sellbot提供的服务 
* @date 2018年11月16日 下午3:07:22 
* @version V1.0  
*/
@Service
public class SellbotServiceImpl implements ISellbotService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HTTP_URL = "http://192.168.1.50:15000"; 
	
	
	/**
	 * sellbot初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	public String restore(AiBaseInfo ai,SellbotRestoreReq sellbotRestoreReq) {
		if(sellbotRestoreReq != null) {
			if("fpjkqzjhwsm".equals(sellbotRestoreReq.getCfg())) {
				//目前测试时只支持这个话术模板
				String url = HTTP_URL+"/restore";
//				String url = "http://"+ai.getIp()+":"+ai.getPort()+"/restore";
				String json = JsonUtils.bean2Json(sellbotRestoreReq);
				String sellbotRsp = HttpClientUtil.doPostJson(url, json);
				String result = StringEscapeUtils.unescapeJava(sellbotRsp);
				return result;
			}
		}
		return null;
	}
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	public String sayhello(AiBaseInfo ai,SellbotSayhelloReq sellbotSayhelloReq) {
		String url = HTTP_URL;
		String sellbotRsp = HttpClientUtil.doPostJson(url, JsonUtils.bean2Json(sellbotSayhelloReq));
		String result = StringEscapeUtils.unescapeJava(sellbotRsp);
		return result;
	}
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	public String match(AiBaseInfo ai,SellbotMatchReq sellbotMatchReq) {
		String url = HTTP_URL+"is_match";
		String sellbotRsp = HttpClientUtil.doPostJson(url, JsonUtils.bean2Json(sellbotMatchReq));
		String result = StringEscapeUtils.unescapeJava(sellbotRsp);
		return result;
	}
}
