package com.guiji.botsentence.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.guiji.botsentence.vo.TtsBackup;
import com.guiji.botsentence.vo.TtsBackupVO;
import com.guiji.botsentence.vo.TtsParam;
import com.guiji.botsentence.vo.TtsParamVO;

/**
 * tts合成相关服务
 * @author zhangpeng
 *
 */
@Service
public interface IBotSentenceTtsService {

	public void saveTtsParam(TtsParamVO param, String userId);
	
	public void saveTtsBackup(TtsBackupVO param, String userId);
	
	public List<TtsParam> queryTtsParamList(String processId);
	
	public List<TtsBackup> queryTtsBackupList(String processId);
	
	public void generateTTS(String processId, String userId);
	
	public boolean validateProcessHasTTs(String processId);
}
