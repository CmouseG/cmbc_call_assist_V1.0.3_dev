package com.guiji.botsentence.service;

import com.guiji.botsentence.dao.entity.BotSentenceTtsParam;
import com.guiji.botsentence.util.enums.TtsParamTypeEnum;
import com.guiji.botsentence.vo.TtsBackupReqVO;
import com.guiji.botsentence.vo.TtsVoiceDetailVO;
import com.guiji.botsentence.vo.TtsVoiceReqVO;
import com.guiji.botsentence.vo.TtsVoiceVO;

import java.util.List;

public interface ITtsService {

    List<TtsVoiceVO> queryTtsVoiceInfoVOList(String processId);

    TtsVoiceDetailVO queryTtsVoiceDetailByVoiceId(Long voiceId);

    void updateTtsVoice(TtsVoiceReqVO ttsVoiceReqVO, String userId);

    void saveSingleTtsBackup(TtsBackupReqVO ttsBackupReqVO, String userId);

    void saveTtsParam(String ttsParamKey, TtsParamTypeEnum ttsParamTypeEnum, String processId, String userId);

    List<BotSentenceTtsParam> getSortedParams(String processId);

    void ttsGenerateVoice(String processId, String userId);
}
