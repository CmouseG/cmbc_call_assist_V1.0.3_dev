package com.guiji.robot.service;

import java.util.List;

import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.model.HsParam;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;

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
	 * @param seqId
	 * @return
	 */
	public TtsWavHis queryTtsWavBySeqId(String seqId);
	
	
	/**
	 * TTS语音合成
	 * 1、必输校验
	 * 2、根据话术模板读取本地json文件，解析json文件获取话术模板
	 * 3、校验参数是否有缺失
	 * 4、合成完整语句，并调用TTS工具服务，生成语音文件
	 * 5、合并TTS语音并生成wav文件
	 * 6、记录历史表，后续缓存使用
	 * @param  ttsVoiceReq
	 * @return 合成后的语音列表
	 */
	public List<TtsVoice> ttsCompose(TtsVoiceReq ttsVoiceReq);
	
	
	/**
	 * 异步TTS合成操作
	 * @param ttsVoiceReq
	 */
	public void asynTtsCompose(HsParam hsChecker);
	
}
