package com.guiji.ai.tts.service;

import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;

/**
 * Created by ty on 2018/11/13.
 */
public interface TtsService {
    TtsRspVO translate(TtsReqVO ttsReqVO);
}
