package com.guiji.robot.service.impl;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.FlHelloReq;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.robot.util.HttpClientUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: SellbotServiceImpl 
* @Description: Sellbot提供的服务 
* @date 2018年11月16日 下午3:07:22 
* @version V1.0  
*/
@Service
public class SellbotServiceImpl implements ISellbotService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * sellbot初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	@Override
	public String restore(AiBaseInfo ai,SellbotRestoreReq sellbotRestoreReq) {
		if(sellbotRestoreReq != null) {
			String url = "http://"+ai.getIp()+":"+ai.getPort()+"/restore";
			String json = JsonUtils.bean2Json(sellbotRestoreReq);
			String sellbotRsp = HttpClientUtil.doPostJson(url, json);
			if(StrUtils.isNotEmpty(sellbotRsp)) {
				String result = null;
				JSONObject jsonObject = null;
				try {
					result = StringEscapeUtils.unescapeJava(sellbotRsp);
					jsonObject = JSON.parseObject(result);
				}catch (Exception e) {
					logger.error("调用Sellbot接口返回数据JSON格式异常，返回结果：{}!",sellbotRsp);
					throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
				}
				String state = jsonObject.getString("state");
				if(StrUtils.isEmpty(state)) {
					logger.error("调用Sellbot接口返回异常，返回结果：{}!",sellbotRsp);
					throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
				}
				return result;
			}else {
				logger.error("调用Sellbot接口返回异常，返回结果：{}!",sellbotRsp);
				throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
			}
		}
		return null;
	}
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	@Override
	public String sayhello(AiBaseInfo ai,SellbotSayhelloReq sellbotSayhelloReq) {
		String url = "http://"+ai.getIp()+":"+ai.getPort();
		if(sellbotSayhelloReq.getSentence()==null) {
			sellbotSayhelloReq.setSentence("");
		}
		String sellbotRsp = HttpClientUtil.doPostJson(url, JsonUtils.bean2Json(sellbotSayhelloReq));
		if(StrUtils.isNotEmpty(sellbotRsp)) {
			String result = null;
			JSONObject jsonObject = null;
			try {
				result = StringEscapeUtils.unescapeJava(sellbotRsp);
				jsonObject = JSON.parseObject(result);
			} catch (Exception e) {
				logger.error("调用Sellbot接口返回数据JSON格式异常，返回结果：{}!",sellbotRsp);
				throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
			}
			String state = jsonObject.getString("state");
			if(StrUtils.isEmpty(state)) {
				logger.error("调用Sellbot接口返回异常，返回结果：{}!",sellbotRsp);
				throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
			}
//			return result;
			//TODO 因为sellbot可能返回2个语音文件，callcenter需要做相应处理，但是还没有处理好，robot代码又需要尽快提交，所以先临时处理下，如果返回了多个语音文件，此处处理返回后边1个
			String wavFiles = jsonObject.getString("wav_filename");	//	语音文件
			if(StrUtils.isNotEmpty(wavFiles) && wavFiles.indexOf(",")>0) {
				wavFiles = wavFiles.substring(wavFiles.indexOf(",")+1);
				jsonObject.put("wav_filename", wavFiles);
			}
			return jsonObject.toJSONString();
			//END
		}else {
			logger.error("调用Sellbot接口返回异常，返回结果：{}!",sellbotRsp);
			throw new RobotException(AiErrorEnum.AI00060020.getErrorCode(),AiErrorEnum.AI00060020.getErrorMsg());
		}
	}
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	@Override
	public String match(AiBaseInfo ai,SellbotMatchReq sellbotMatchReq) {
		String url = "http://"+ai.getIp()+":"+ai.getPort()+"/is_match";
		String sellbotRsp = HttpClientUtil.doPostJson(url, JsonUtils.bean2Json(sellbotMatchReq));
		String result = StringEscapeUtils.unescapeJava(sellbotRsp);
		return result;
	}
	
	
	/**
	 * 电话挂断后做数据清理（目前只有飞龙需要，sellbot不需要，为接口统一需要统一封装下）
	 * @param flHelloReq
	 * @return
	 */
	@Override
	public void clean(FlHelloReq flHelloReq) {
		//sellbot 在restore清理
	}
}
