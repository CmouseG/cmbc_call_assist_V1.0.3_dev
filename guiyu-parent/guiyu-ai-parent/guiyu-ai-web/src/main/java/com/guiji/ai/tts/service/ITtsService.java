package com.guiji.ai.tts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.guiji.ai.vo.TtsReqVO;

/**
 * Created by ty on 2018/11/13.
 */
public interface ITtsService {
	/**
	 * 语音合成
	 * @param ttsReqVO
	 * @return
	 */
    public void translate(TtsReqVO ttsReqVO);
    
    /**
     * 根据busiId查询TTS处理状态
     * @param busId
     * @return
     * @throws Exception 
     */
    public String getTransferStatusByBusId(String busId) throws Exception;
    
    /**
     * 根据busiId查询TTS处理结果
     * @param busId
     * @return
     * @throws Exception
     */
	public List<Map<String, String>> getTtsTransferResult(String busId) throws Exception;
    
    /**
     * 查询TTS处理状态
     * @param startTime
     * @param endTime
     * @param model
     * @param status
     * @return
     */
    public List<Map<String, Object>> getTtsStatus(Date startTime, Date endTime, String model, String status) throws Exception;

    /**
     * 保存到状态表
     * @param ttsReqVO
     */
	public void saveTtsStatus(TtsReqVO ttsReqVO);

}
