package com.guiji.ai.tts.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.guiji.ai.tts.vo.ModelRequestNumVO;

public interface IResultService
{

    /**
     * 根据busiId查询TTS处理结果
     * @param busId
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> getTtsTransferResultByBusId(String busId) throws Exception;

    /**
     * 查询前10分钟内各模型请求情况
     * @param date
     * @return
     */
	public List<ModelRequestNumVO> selectTenMinutesBefore(Date date);
}
