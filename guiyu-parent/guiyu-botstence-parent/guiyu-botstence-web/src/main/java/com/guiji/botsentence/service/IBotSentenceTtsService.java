package com.guiji.botsentence.service;

import java.util.List;

import com.guiji.botsentence.vo.*;
import org.springframework.stereotype.Service;

/**
 * tts合成相关服务
 * @author zhangpeng
 *
 */
@Service
public interface IBotSentenceTtsService {

	void saveSingleTtsBackup(TtsBackupReqVO ttsBackupReqVO, String userId);

	public void saveTtsParam(TtsParamVO param, String userId);
	
	public void saveTtsBackup(TtsBackupVO param, String userId);
	
	public List<TtsParam> queryTtsParamList(String processId);
	
	public List<TtsBackup> queryTtsBackupList(String processId);
	
	public void generateTTS(String processId, String userId);
	
	public boolean validateProcessHasTTs(String processId);
}
