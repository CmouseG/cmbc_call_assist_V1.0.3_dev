package com.guiji.robot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.AiBaseInfo;
import com.guiji.robot.service.vo.FlHelloReq;
import com.guiji.robot.service.vo.FlHelloRsp;
import com.guiji.robot.service.vo.FlMatchReq;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;
import com.guiji.robot.util.HttpClientUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: FlyDragonServiceImpl 
* @Description: 飞龙机器人服务 
* @date 2019年3月3日 下午6:17:05 
* @version V1.0  
*/
@Slf4j
@Service
public class FlyDragonServiceImpl implements ISellbotService{
	//飞龙服务地址
	@Value("${flydragon_server_url:''}")
	private String flydragonUrl;

	/**
	 * 飞龙初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	public String restore(AiBaseInfo ai,SellbotRestoreReq sellbotRestoreReq) {
		String url = flydragonUrl+"/api/hello";
		FlHelloReq flHelloReq = new FlHelloReq();
		flHelloReq.setSeqid(sellbotRestoreReq.getSeqid());
		flHelloReq.setCfg_name(sellbotRestoreReq.getCfg());
		flHelloReq.setCur_dialog_content("");
		flHelloReq.setCur_dialog_status(0); //开场白（类似于restore）
		flHelloReq.setVal(sellbotRestoreReq.getVal());	//业务参数（兼容老sellbot）
		flHelloReq.setBusiness_data(sellbotRestoreReq.getBusiness_data()); //(业务参数-飞龙
		String json = JsonUtils.bean2Json(flHelloReq);
		String flydragonRsp = HttpClientUtil.doPostJson(url, json);
		if(StrUtils.isNotEmpty(flydragonRsp)) {
			JSONObject jsonObject = JSON.parseObject(flydragonRsp);
			String code = jsonObject.getString("code");
			if(!"0".equals(code)) {
				log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
				throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
			}
			FlHelloRsp rsp = new FlHelloRsp();
			rsp.setSeqid(sellbotRestoreReq.getSeqid());
			//组装返回结果
			this.initFlHelloRsp(rsp, jsonObject);
			return JsonUtils.bean2Json(rsp);
		}else {
			log.error("调用飞龙接口返回异常，返回结果为空");
			throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
		}
	}
	
	
	/**
	 * 飞龙客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	public String sayhello(AiBaseInfo ai,SellbotSayhelloReq sellbotSayhelloReq) {
		String url = flydragonUrl+"/api/hello";
		FlHelloReq flHelloReq = new FlHelloReq();
		flHelloReq.setSeqid(sellbotSayhelloReq.getSeqId());
		flHelloReq.setCfg_name(sellbotSayhelloReq.getCfg());
		flHelloReq.setCur_dialog_content(sellbotSayhelloReq.getSentence());
		flHelloReq.setCur_dialog_status(1); //会话中
		String json = JsonUtils.bean2Json(flHelloReq);
		String flydragonRsp = HttpClientUtil.doPostJson(url, json);
		if(StrUtils.isNotEmpty(flydragonRsp)) {
			JSONObject jsonObject = JSON.parseObject(flydragonRsp);
			String code = jsonObject.getString("code");
			if(!"0".equals(code)) {
				log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
				throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
			}
			FlHelloRsp rsp = new FlHelloRsp();
			rsp.setSeqid(sellbotSayhelloReq.getSeqId());
			//组装返回结果
			this.initFlHelloRsp(rsp, jsonObject);
			return JsonUtils.bean2Json(rsp);
		}else {
			log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
			throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
		}
	}
	
	
	/**
	 * 飞龙关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	public String match(AiBaseInfo ai,SellbotMatchReq sellbotMatchReq) {
		String url = flydragonUrl+"/api/interrupt";
		FlMatchReq flMatchReq = new FlMatchReq();
		flMatchReq.setSeqid(sellbotMatchReq.getSeqId());
		flMatchReq.setCfg_name(sellbotMatchReq.getCfg());
		flMatchReq.setCur_dialog_content(sellbotMatchReq.getSentence());
		flMatchReq.setCur_dialog_status(1); //会话中
		String json = JsonUtils.bean2Json(flMatchReq);
		String flydragonRsp = HttpClientUtil.doPostJson(url, json);
		if(StrUtils.isNotEmpty(flydragonRsp)) {
			JSONObject jsonObject = JSON.parseObject(flydragonRsp);
			String matched = jsonObject.getString("matched");	//	返回是否匹配
			return "{\"matched\": "+matched+"}";
		}else {
			log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
			throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
		}
	}
	
	/**
	 * 电话挂断后做数据清理（目前只有飞龙需要，sellbot不需要，为接口统一需要统一封装下）
	 * @param flHelloReq
	 * @return
	 */
	@Override
	public void clean(FlHelloReq flHelloReq) {
		String url = flydragonUrl+"/api/hello";
		flHelloReq.setCur_dialog_status(2); //挂机
		String json = JsonUtils.bean2Json(flHelloReq);
		String flydragonRsp = HttpClientUtil.doPostJson(url, json);
		if(StrUtils.isNotEmpty(flydragonRsp)) {
			JSONObject jsonObject = JSON.parseObject(flydragonRsp);
			String code = jsonObject.getString("code");
			if(!"0".equals(code)) {
				log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
				throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
			}
		}else {
			log.error("调用飞龙接口返回异常，返回结果：{}!",flydragonRsp);
			throw new RobotException(AiErrorEnum.AI00060036.getErrorCode(),AiErrorEnum.AI00060036.getErrorMsg());
		}
	}
	
	
	/**
	 * 组装飞龙返回结果
	 * @param rsp
	 * @param jsonObject
	 * @return
	 */
	private FlHelloRsp initFlHelloRsp(FlHelloRsp rsp,JSONObject jsonObject) {
		rsp.setAnswered_domain("");
		rsp.setIntent(jsonObject.getString("accurate_intent"));
		rsp.setEnd(jsonObject.getString("end"));
		rsp.setAnswer(jsonObject.getString("answer"));
		rsp.setSentence(jsonObject.getString("sentence"));
		rsp.setReason(jsonObject.getString("reason"));
		JSONArray wids = jsonObject.getJSONArray("wids");	//要播放的录音，数组，转为原sellbot格式
		String wavFiles = "";
		if(wids!=null) {
			for (int i = 0; i < wids.size(); i++) {
	            //提取出family中的所有
	            String wavId = (String) wids.get(i);
	            wavFiles = wavFiles + "," + wavId;
	        }
			wavFiles = wavFiles.substring(1);
		}
		rsp.setWav_filename(wavFiles);
		return rsp;
	}
}
