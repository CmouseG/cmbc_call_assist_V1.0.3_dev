package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.model.AiCallApplyReq;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.vo.CallBusiData;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.util.DataLocalCacheUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: CallBusiDataService 
* @Description: 电话过程中业务数据服务
* @date 2019年3月5日 下午6:47:05 
* @version V1.0  
*/
@Slf4j
@Service
public class CallBusiDataService {
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	ITtsWavService iTtsWavService;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	
	
	/**
	 * 电话业务数据初始化
	 * 机器人资源申请的时候去查询初始化
	 * @param aiCallApplyReq
	 */
	public void initCallBusiData(AiCallApplyReq aiCallApplyReq) {
		//获取模板
		HsReplace hsReplace = dataLocalCacheUtil.queryTemplate(aiCallApplyReq.getTemplateId());
		if(RobotConstants.HS_VERSION_FL==hsReplace.getVersion()) {
			//目前只有飞龙，需要查询用户历史数据
			//TODO 调用DA接口查询用户基本信息以及最近一次电话信息
			CallBusiData callBusiData = null;
			//资源准备时放入缓存，不影响打电话时的效率
			aiCacheService.putCallBusiData(callBusiData);
		}
	}
	
	/**
	 * 将业务资源数据填充到飞龙/sellbot请求信息中
	 * @param aiCallStartReq
	 * @param sellbotRestoreReq
	 */
	public void pushCallBusiData(AiCallStartReq aiCallStartReq,SellbotRestoreReq sellbotRestoreReq) {
		HsReplace hsReplace = dataLocalCacheUtil.queryTemplate(aiCallStartReq.getTemplateId());
		if(RobotConstants.HS_VERSION_FL==hsReplace.getVersion()) {
			JSONObject busiDataObject = null;
			//1、拼装tts参数
			//飞龙 新老参数映射关系,如："$0000":"客户姓名"
			Map<String,String> relationshipMap = hsReplace.getReplace_map_relationship();
			if(relationshipMap!=null) {
				//需要TTS合成，将参数信息也发给sellbot，用来交互过程中返回完成sentence信息
				TtsWavHis ttsWavHis = iTtsWavService.queryTtsWavBySeqId(aiCallStartReq.getDisSeqId());
				if(ttsWavHis!=null && StrUtils.isNotEmpty(ttsWavHis.getReqParams())) {
					if(busiDataObject==null) {
						busiDataObject = new JSONObject();
					}
					String[] params = ttsWavHis.getReqParams().split("\\|");
					List<String> valueList = new ArrayList<String>(relationshipMap.values());
					for(int i=0;i<valueList.size();i++) {
						busiDataObject.put(valueList.get(i), params[i]);
					}
				}
			}
			//2、获取业务数据
			CallBusiData callBusiData = aiCacheService.queryCallBusiData(aiCallStartReq.getPhoneNo());
			if(callBusiData!=null) {
				if(busiDataObject==null) {
					busiDataObject = new JSONObject();
				}
				busiDataObject.put("性别", callBusiData.getGender());
				busiDataObject.put("职业", callBusiData.getJob());
				busiDataObject.put("上次拨打意向", callBusiData.getIntentLevel());
			}
			if(busiDataObject!=null) {
				sellbotRestoreReq.setBusiness_data(busiDataObject.toJSONString());
			}
		}else {
			if(hsReplace !=null && hsReplace.isTemplate_tts_flag()) {
				//需要TTS合成，将参数信息也发给sellbot，用来交互过程中返回完成sentence信息
				TtsWavHis ttsWavHis = iTtsWavService.queryTtsWavBySeqId(aiCallStartReq.getDisSeqId());
				if(ttsWavHis != null) {
					sellbotRestoreReq.setVal(ttsWavHis.getReqParams());	//	参数值
				}
			}
		}
	}
	
}