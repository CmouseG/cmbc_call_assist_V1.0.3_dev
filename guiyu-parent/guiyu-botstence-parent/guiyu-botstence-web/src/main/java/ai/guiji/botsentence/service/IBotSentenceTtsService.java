package ai.guiji.botsentence.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.guiji.botsentence.vo.TtsBackup;
import ai.guiji.botsentence.vo.TtsBackupVO;
import ai.guiji.botsentence.vo.TtsParam;
import ai.guiji.botsentence.vo.TtsParamVO;

/**
 * tts合成相关服务
 * @author zhangpeng
 *
 */
@Service
public interface IBotSentenceTtsService {

	public void saveTtsParam(TtsParamVO param,Long userId);
	
	public void saveTtsBackup(TtsBackupVO param,Long userId);
	
	public List<TtsParam> queryTtsParamList(String processId);
	
	public List<TtsBackup> queryTtsBackupList(String processId);
	
	public void generateTTS(String processId,Long userId);
	
	public boolean validateProcessHasTTs(String processId);
}
