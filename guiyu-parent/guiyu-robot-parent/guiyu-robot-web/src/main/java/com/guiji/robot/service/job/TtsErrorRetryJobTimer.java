package com.guiji.robot.service.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.api.ITts;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.dao.entity.TtsWavHisExample;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: TtsCheckJobTimer 
* @Description: tts查证服务
* @date 2019年1月9日 下午3:20:43 
* @version V1.0  
*/
@Component
@JobHandler(value="ttsErrorRetryJobTimer")
public class TtsErrorRetryJobTimer extends IJobHandler{
	@Autowired
	TtsWavHisMapper ttsWavHisMapper;
	@Autowired
	ITtsWavService iTtsWavService;
	@Autowired
	AiNewTransService aiNewTransService;
	@Autowired
	ITts iTts;
	
	/**
	 * TTS异常重试机制
	 * TTS合成状态为异常，且失败次数小于等于3次的服务重试下
	 * 此处只处理，本地合成失败的数据
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long beginTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，TTS重试...");
		//查询TTS数据状态为F，且异常类型为L（本地失败），且尝试次数<=3的数据
		TtsWavHisExample example = new TtsWavHisExample();
		example.createCriteria().andStatusEqualTo(RobotConstants.TTS_STATUS_F)
			.andErrorTypeEqualTo(RobotConstants.TTS_ERROR_TYPE_L).andErrorTryNumLessThanOrEqualTo(3);
		//查询tts本地失败，且尝试次数小于<=3的数据重试
		List<TtsWavHis> ttsWavHisList = ttsWavHisMapper.selectByExample(example);
		if(ListUtil.isNotEmpty(ttsWavHisList)) {
			List<TtsWavHis> errorTtsWavHisList = new ArrayList<TtsWavHis>();	//重新发起时校验没通过的数据
			//需要重新发起异步本地处理的list
			List<TtsCallback> ttsCallbackHistList = new ArrayList<TtsCallback>();
			for(TtsWavHis ttsWavHis : ttsWavHisList) {
				String busiId = ttsWavHis.getBusiId();	//调用TTS的业务编号
				if(StrUtils.isNotEmpty(busiId)) {
					//查询TTS回调本地历史表
					TtsCallbackHis ttsCallbackHis = iTtsWavService.queryTtsCallbackHisByBusiId(busiId);
					if(ttsCallbackHis != null) {
						TtsCallback ttsCallback = new TtsCallback();
						BeanUtil.copyProperties(ttsCallbackHis, ttsCallback);	//属性拷贝
						if(StrUtils.isNotEmpty(ttsCallbackHis.getTtsJsonData())) {
							//将JSON转对象
							Map<String,String> audios = JsonUtils.json2Bean(ttsCallbackHis.getTtsJsonData(), Map.class);
							ttsCallback.setAudios(audios);
						}
						ttsCallbackHistList.add(ttsCallback);
					}else {
						XxlJobLogger.log("TTS重新{}发起异常,查不到本地callback历史！",ttsWavHis);
						errorTtsWavHisList.add(ttsWavHis);
					}
				}else {
					XxlJobLogger.log("TTS重新{}发起异常,busiId为空！",ttsWavHis);
					errorTtsWavHisList.add(ttsWavHis);
				}
			}
			if(ListUtil.isNotEmpty(errorTtsWavHisList)) {
		    	XxlJobLogger.log("重新发起TTS本地处理，失败的数据，共计{}条",errorTtsWavHisList.size());
		    	for(TtsWavHis ttsWavHis : errorTtsWavHisList) {
		    		ttsWavHis.setErrorTryNum((ttsWavHis.getErrorTryNum()==null?0:ttsWavHis.getErrorTryNum())+1);	//失败次数+1
		    		aiNewTransService.recordTtsWav(ttsWavHis);
		    	}
		    }
			if(ListUtil.isNotEmpty(ttsCallbackHistList)) {
				XxlJobLogger.log("需要重新发起本地TTS处理的数据，共计{}条",ttsCallbackHistList.size());
				iTtsWavService.asynTtsCallback(ttsCallbackHistList);
			}
		}
		long endTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，用时{}S,[TTS查证]完成...",(endTime-beginTime)/1000);
		return null;
	}
	
}
