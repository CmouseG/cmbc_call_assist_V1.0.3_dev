package com.guiji.ai.tts.service;

import com.guiji.ai.vo.TtsReqVO;

/**
 * Created by ty on 2018/11/13.
 */
public interface ITtsService 
{
	/**
	 * 语音合成
	 * @param ttsReqVO
	 * @return
	 */
    public void translate(TtsReqVO ttsReqVO);

    /**
     * 任务插队
     * @param busId
     * @return
     */
	public Boolean taskJump(String busId);

	/**
	 * 将任务保存到redis
	 * @param ttsReqVO
	 */
	public void saveTask(TtsReqVO ttsReqVO);
    
}
