package com.guiji.ai.tts.service;

import java.util.List;
import java.util.Map;

public interface IResultService
{

    /**
     * 根据busiId查询TTS处理结果
     * @param busId
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> getTtsTransferResultByBusId(String busId) throws Exception;
}
