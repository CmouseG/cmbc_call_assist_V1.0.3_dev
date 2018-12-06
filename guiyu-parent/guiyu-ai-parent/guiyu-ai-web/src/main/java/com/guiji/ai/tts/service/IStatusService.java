package com.guiji.ai.tts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.guiji.ai.vo.TtsReqVO;

public interface IStatusService
{

    /**
     * 根据busiId查询TTS处理状态
     * @param busId
     * @return
     * @throws Exception 
     */
    public String getTransferStatusByBusId(String busId);
    
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
     * 保存到tts_status表
     * @param ttsReqVO
     * @throws Exception 
     */
	public void saveTtsStatus(TtsReqVO ttsReqVO) throws Exception;

	/**
	 * 根据busId修改表状态
	 * @param busId
	 * @param status
	 */
	public void updateStatusByBusId(String busId, String status); 
}
