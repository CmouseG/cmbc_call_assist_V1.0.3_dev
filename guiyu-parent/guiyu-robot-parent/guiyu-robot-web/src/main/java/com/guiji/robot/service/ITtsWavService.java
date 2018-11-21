package com.guiji.robot.service;

import com.guiji.robot.dao.entity.TtsWavHis;

/** 
* @ClassName: TtsWavService 
* @Description: TTS语音合成服务
* @date 2018年11月20日 上午11:51:36 
* @version V1.0  
*/
public interface ITtsWavService {
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 同时记录历史
	 * @param ttsWavHis
	 * @return
	 */
	TtsWavHis saveOrUpdate(TtsWavHis ttsWavHis);
	
	
	/**
	 * 根据条件查询TTS已合成的语音数据
	 * @param templateId
	 * @param ttsKey
	 * @param ttsParamKeys
	 * @param ttsParamValues
	 * @return
	 */
	TtsWavHis queryTtsWav(String templateId,String ttsKey,String ttsParamKeys,String ttsParamValues);
}
